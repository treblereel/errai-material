/*
 *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *       http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package org.jboss.errai.material.rebind;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Widget;
import com.ibm.icu.impl.IllegalIcuArgumentException;
import com.sun.codemodel.*;
import com.sun.codemodel.writer.SingleStreamCodeWriter;
import gwt.material.design.client.base.BaseCheckBox;
import gwt.material.design.client.constants.Type;
import gwt.material.design.client.ui.MaterialCheckBox;
import gwt.material.design.client.ui.MaterialRadioButton;
import org.jboss.errai.material.client.local.GwtMaterialUtil;
import org.jboss.errai.material.client.local.MaterialMethodDefinition;
import org.jboss.errai.material.client.local.MaterialWidgetDefinition;
import org.jboss.errai.material.client.local.factory.AbstractBaseWidgetFactory;
import org.jboss.errai.material.client.local.factory.MaterialWidgetFactory;
import org.jboss.errai.material.client.local.factory.WidgetQualifier;

import javax.inject.Singleton;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;

/**
 * @author Dmitrii Tikhomirov <chani@me.com>
 *         Created by treblereel on 5/23/17.
 */
public class CustomeMaterialWidgetGenerator extends MaterialWidgetGenerator {
    private static final String MATERIAL_RADIO_BUTTON = "GwtNonMaterialWidgetFactoryImpl";

    private static final Class[] classes = {MaterialRadioButton.class, MaterialCheckBox.class};

    private JMethod invoke;

    public CustomeMaterialWidgetGenerator() {
        try {
            buildFactory();
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalIcuArgumentException(e.getMessage());
        }
    }

    private void buildFactory() throws IOException {

        JCodeModel jCodeModel = new JCodeModel();
        JPackage jp = jCodeModel._package(MaterialWidgetFactoryGenerator.GWT_MATERIAL_CLIENT_PACKAGE_NAME);
        JDefinedClass jc = null;
        try {
            jc = jp._class(MATERIAL_RADIO_BUTTON);
        } catch (JClassAlreadyExistsException e) {
            e.printStackTrace();
        }
        assert jc != null;

        jc.annotate(WidgetQualifier.class);
        jc._implements(MaterialWidgetFactory.class);
        jc._extends(AbstractBaseWidgetFactory.class);
        jc.annotate(Singleton.class);
        JMethod constructor = jc.constructor(JMod.PUBLIC);

        constructInvokeMethodDeclarationPre(jCodeModel, jc);
        for (Class clazz : classes) {
            processGwtMaterialWidget(jc, jCodeModel, constructor, clazz);
        }
        constructInvokeMethodDeclarationPost(jCodeModel, jc);


        processNativeMethods(jc);

        jCodeModel.build(new File(MaterialWidgetFactoryGenerator.DEFAULT_BUILD_LOCATION));

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        CodeWriter codeWriter = new SingleStreamCodeWriter(baos);
        jCodeModel.build(codeWriter);
    }

    @Override
    public void generateNativeMethod(JDefinedClass jc, MethodHolder holder) {
        jc.direct("    public void " + holder.method.getName() + "(" + holder.iface.getCanonicalName() + " x, " + holder.param.getCanonicalName() + " y){  x." + holder.name + "(y);       }\n");
    }

    private void constructInvokeMethodDeclarationPre(JCodeModel jCodeModel, JDefinedClass jc) {
        invoke = jc.method(JMod.PUBLIC, jCodeModel.ref(Optional.class).narrow(jCodeModel.ref(Widget.class)), "invoke");
        invoke.param(Element.class, "tagged");
        invoke.annotate(Override.class);
        invoke.body().decl(jCodeModel.ref(String.class), "tag").init(jCodeModel.ref(GwtMaterialUtil.class).staticInvoke("getMaterialWidgetFieldValue").arg(JExpr.ref("tagged")).invoke("toLowerCase").invoke("replaceAll").arg("-").arg(""));
        invoke.body().decl(jCodeModel.ref(Widget.class), "result").init(JExpr._null());
    }

    private void constructInvokeMethodDeclarationPost(JCodeModel jCodeModel, JDefinedClass jc) {
        JClass optional = jCodeModel.directClass(Optional.class.getCanonicalName());
        invoke.body()._if(JExpr.ref("result").ne(JExpr._null()))._then()._return(optional.staticInvoke("of").arg(JExpr.ref("result")));
        invoke.body()._return(optional.staticInvoke("empty"));
    }

    private void processGwtMaterialWidget(JDefinedClass jc, JCodeModel jCodeModel, JMethod constructor, Class clazz) {
        Set<Method> methods = new HashSet<>();
        generateWidgetInvoke(jCodeModel, clazz);
        findAllSettableMethods(clazz, methods);
        System.out.println("NUMBER " + methods.size() + " " + clazz.getSimpleName());
        generateWidgetDeclaretion(jc, jCodeModel, constructor, clazz, methods);
    }

    private void generateWidgetInvoke(JCodeModel jCodeModel, Class clazz) {
        if (MaterialRebindUtils.isExtends(clazz, BaseCheckBox.class) || MaterialRebindUtils.isExtends(clazz, CheckBox.class)) {
            JBlock block = invoke.body()._if(JExpr.ref("tag").invoke("equals").arg(clazz.getSimpleName().toLowerCase()))
                    ._then().block();
            JConditional jConditional = block._if(JExpr.ref("tagged").invoke("hasAttribute")
                    .arg("text"));
            jConditional._then().assign(JExpr.ref("result"), JExpr._new(jCodeModel.ref(clazz))
                    .arg(JExpr.ref("tagged").invoke("getAttribute").arg("text")));
            jConditional._else().block().assign(JExpr.ref("result"), JExpr._new(jCodeModel.ref(clazz)));
        } else {
            invoke.body()._if(JExpr.ref("tag").invoke("equals").arg(clazz.getSimpleName().toLowerCase()))._then()
                    .assign(JExpr.ref("result"), JExpr._new(jCodeModel.ref(clazz)));
        }
    }


    private void generateWidgetDeclaretion(JDefinedClass jc, JCodeModel jCodeModel, JMethod constructor, Class clazz, Set<Method> methods) {

        String tag = clazz.getSimpleName();
        JMethod method = jc.method(JMod.PRIVATE, void.class, "add" + tag);
        JVar var = method.body().decl(jCodeModel.ref(MaterialWidgetDefinition.class), "field");
        var.init(JExpr._new(jCodeModel.ref(MaterialWidgetDefinition.class)).arg(tag).arg(jCodeModel.ref(clazz).dotclass()).arg(JExpr.lit(false)));

        for (Method s : methods) {
            Class<?>[] pType = s.getParameterTypes();
            Class param = pType[0];
            String name = s.getName().replaceFirst("set", "").toLowerCase();
            if (!param.getCanonicalName().equals(Type.class.getCanonicalName()) &&
                    !param.getCanonicalName().equals(Object.class.getCanonicalName())) {
                String methodName = generateNativeMethodName(s);
                CommonMaterialWidgetGenerator.MethodHolder holder = allMethods.get(methodName);
                Class methodParam = holder.param;
                if (methodParam.isPrimitive()) {
                    methodParam = MaterialRebindUtils.primitiveToBoxed(methodParam);
                }
                methodName = holder.method.getName();
                JVar func = method.body().decl(jCodeModel.ref(BiConsumer.class).narrow(jCodeModel.ref(holder.iface.getCanonicalName())).narrow(jCodeModel.ref(methodParam)), methodName);
                func.init(JExpr.direct("this::" + methodName));
                method.body().add(var.invoke("getMethods").invoke("put").arg(name).arg(JExpr._new(jCodeModel.ref(MaterialMethodDefinition.class)).arg(doJExpressionMethodDefinition(jCodeModel, param)).arg(func)));
            }

        }
        method.body().add(JExpr.ref("widgets").invoke("put").arg(tag.toLowerCase()).arg(JExpr.ref("field")));
        constructor.body().invoke("add" + tag);
    }

    @Override
    public void constructGetMethodDefIfExist(JDefinedClass jc, JCodeModel jCodeModel) {

        JType returnValue = jCodeModel.ref(MaterialMethodDefinition.class);
        JType returnType = jCodeModel.ref(Optional.class).narrow(returnValue);
        JClass optional = jCodeModel.directClass(Optional.class.getCanonicalName());

        JMethod method = jc.method(JMod.PUBLIC, returnType, "getMethodDefIfExist");
        method.param(java.lang.String.class, "tag");
        method.param(java.lang.String.class, "method");
        method.annotate(Override.class);

        method.body().assign(JExpr.ref("tag"), JExpr.ref("tag").invoke("toLowerCase").invoke("replaceAll").arg("-").arg(""));
        method.body().assign(JExpr.ref("method"), JExpr.ref("method").invoke("toLowerCase"));

        method.body()._if(JExpr.ref("widgets").invoke("containsKey").arg(JExpr.ref("tag")).eq(JExpr.lit(false)))._then()._return(optional.staticInvoke("empty"));


        method.body()._if(JExpr.ref("widgets").invoke("get").arg(JExpr.ref("tag"))
                .invoke("getMethods")
                .invoke("containsKey")
                .arg(JExpr.ref("method")))._then()._return(optional.staticInvoke("of").arg(JExpr.ref("widgets")
                .invoke("get").arg(JExpr.ref("tag"))
                .invoke("getMethods")
                .invoke("get")
                .arg(JExpr.ref("method"))));

        method.body()._return(optional.staticInvoke("empty"));
    }
}
