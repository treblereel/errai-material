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
import com.google.gwt.thirdparty.guava.common.collect.ImmutableSet;
import com.google.gwt.thirdparty.guava.common.reflect.ClassPath;
import com.google.gwt.user.client.ui.Widget;
import com.sun.codemodel.*;
import com.sun.codemodel.writer.SingleStreamCodeWriter;
import gwt.material.design.client.base.MaterialWidget;
import gwt.material.design.client.constants.Type;
import org.jboss.errai.material.client.local.MaterialMethodDefinition;
import org.jboss.errai.material.client.local.MaterialWidgetDefinition;
import org.jboss.errai.material.client.local.factory.AbstractBaseWidgetFactory;
import org.jboss.errai.material.client.local.factory.MaterialWidgetQualifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
public class CommonMaterialWidgetGenerator extends MaterialWidgetGenerator {
    private static final Logger logger = LoggerFactory.getLogger(CommonMaterialWidgetGenerator.class);
    private static final String GWT_MATERIAL_FACTORY_CLASS_NAME = "MaterialWidgetFactoryImpl";
    private final Set<String> defaultMethods = new HashSet<>();
    private JMethod invoke;


    public CommonMaterialWidgetGenerator() {
        try {
            buildFactory();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void buildFactory() throws IOException {

        JPackage jp = jCodeModel._package(MaterialWidgetFactoryGenerator.GWT_MATERIAL_CLIENT_PACKAGE_NAME);
        JDefinedClass jc = null;
        try {
            jc = jp._class(GWT_MATERIAL_FACTORY_CLASS_NAME);
        } catch (JClassAlreadyExistsException e) {
            e.printStackTrace();
        }
        assert jc != null;
        jc.annotate(MaterialWidgetQualifier.class);
        jc._extends(AbstractBaseWidgetFactory.class);
        jc.annotate(Singleton.class);

        JMethod constructor = jc.constructor(JMod.PUBLIC);

        constructInvokeMethodDeclaration(jc, jCodeModel);
        try {
            processGwtMaterialWidgets(jc, jCodeModel, constructor);
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }

        constractInvokeMethodBody(jCodeModel);

        processNativeMethods(jc);

        jCodeModel.build(new File(MaterialWidgetFactoryGenerator.DEFAULT_BUILD_LOCATION));

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        CodeWriter codeWriter = new SingleStreamCodeWriter(baos);
        jCodeModel.build(codeWriter);
    }

    private void constructInvokeMethodDeclaration(JDefinedClass jc, JCodeModel jCodeModel) {
        invoke = jc.method(JMod.PUBLIC, jCodeModel.ref(Optional.class).narrow(jCodeModel.ref(Widget.class)), "invoke");
        invoke.param(Element.class, "tagged");
        invoke.annotate(Override.class);
        invoke.body().decl(jCodeModel.ref(String.class), "tag").init(JExpr.ref("tagged").invoke("getTagName").invoke("toLowerCase").invoke("replaceAll").arg("-").arg(""));
        invoke.body().decl(jCodeModel.ref(Widget.class), "result").init(JExpr._null());
    }

    private void constractInvokeMethodBody(JCodeModel jCodeModel) {
        invoke.body().decl(jCodeModel.ref(Optional.class).narrow(jCodeModel.ref(MaterialWidgetDefinition.class)), "def").init(JExpr._this().invoke("getWidgetDefIfExist").arg(JExpr.ref("tag")));
        JBlock jInvokeBlock = invoke.body()._if(JExpr.ref("def").invoke("isPresent").eq(JExpr.lit(true)).cand(JExpr.ref("def").invoke("get").invoke("getExtendsMaterialWidget").eq(JExpr.lit(true))))
                ._then();
        jInvokeBlock.decl(jCodeModel.ref(MaterialWidget.class), "candidate").init(JExpr.cast(jCodeModel.ref(MaterialWidget.class), JExpr.ref("result")));
        JBlock jInvokeBlockCandidate = jInvokeBlock._if(JExpr.ref("candidate").invoke("getInitialClasses").ne(JExpr._null()))._then();
        jInvokeBlockCandidate.decl(jCodeModel.ref(StringBuffer.class), "sb").init(JExpr._new(jCodeModel.ref(StringBuffer.class)));
        jInvokeBlockCandidate.forEach(jCodeModel.ref(String.class), "css", JExpr.ref("candidate").invoke("getInitialClasses")).body()
                .add(JExpr.ref("sb").invoke("append").arg(JExpr.ref("css")).invoke("append").arg(" "));
        jInvokeBlockCandidate.add(JExpr.ref("candidate").invoke("getElement").invoke("setAttribute").arg("class").arg(JExpr.ref("sb").invoke("toString").invoke("trim")));

        JClass optional = jCodeModel.directClass(Optional.class.getCanonicalName());
        invoke.body()._if(JExpr.ref("result").ne(JExpr._null()))._then()._return(optional.staticInvoke("of").arg(JExpr.ref("result")));

        invoke.body()._return(optional.staticInvoke("empty"));
    }

    private void processGwtMaterialWidgets(JDefinedClass jc, JCodeModel jCodeModel, JMethod constructor) throws IOException, ClassNotFoundException {

        final ClassLoader loader = Thread.currentThread().getContextClassLoader();
        ImmutableSet<ClassPath.ClassInfo> topLevelClasses = com.google.gwt.thirdparty.guava.common.reflect.ClassPath.from(loader).getTopLevelClasses();

        Set<Method> properties = new HashSet<>();
        processMethods(MaterialWidget.class, properties);
        generateWidgetDeclaretion(jc, jCodeModel, constructor, MaterialWidget.class, properties, MaterialRebindUtils.isExtendsMaterialWidget(MaterialWidget.class));

        for (final com.google.gwt.thirdparty.guava.common.reflect.ClassPath.ClassInfo classInfo : topLevelClasses) {
            if (MaterialRebindUtils.isWidgetSupported(classInfo)) {
                if (classInfo.load().equals(MaterialWidget.class)) continue;
                if (classInfo.load().getCanonicalName().equals(MaterialRebindUtils.GWT_MATERIAL_COMMON_PACKAGE + classInfo.load().getSimpleName())
                        || (classInfo.load().getCanonicalName().startsWith(MaterialRebindUtils.GWT_MATERIAL_ADDINS_PACKAGE) && classInfo.load().getCanonicalName().endsWith(classInfo.load().getSimpleName()))
                        || (classInfo.load().getCanonicalName().startsWith(MaterialRebindUtils.GWT_MATERIAL_HTML_PACKAGE) && classInfo.load().getCanonicalName().endsWith(classInfo.load().getSimpleName()))) {
                    Set<Method> methods = new HashSet<>();
                    processMethods(classInfo.load(), methods);
                    generateWidgetDeclaretion(jc, jCodeModel, constructor, classInfo.load(), methods, MaterialRebindUtils.isExtendsMaterialWidget(classInfo.load()));
                }
            }
        }
    }

    private void generateWidgetDeclaretion(JDefinedClass jc, JCodeModel jCodeModel, JMethod constructor, Class clazz, Set<Method> methods, Boolean isExtendsMaterialWidget) {
        generateWidgetInvoke(jCodeModel, clazz);
        String tag = clazz.getSimpleName();
        JMethod method = jc.method(JMod.PRIVATE, void.class, "add" + tag);
        JVar var = method.body().decl(jCodeModel.ref(MaterialWidgetDefinition.class), "field");
        var.init(JExpr._new(jCodeModel.ref(MaterialWidgetDefinition.class)).arg(tag).arg(jCodeModel.ref(clazz).dotclass()).arg(JExpr.lit(isExtendsMaterialWidget)));
        for (Method s : methods) {
            Class<?>[] pType = s.getParameterTypes();
            Class param = pType[0];
            String name = s.getName().replaceFirst("set", "").toLowerCase();
            if (!param.getCanonicalName().equals(Type.class.getCanonicalName()) &&
                    !param.getCanonicalName().equals(Object.class.getCanonicalName())) {
                String methodName = generateNativeMethodName(s);


                if (clazz.equals(MaterialWidget.class)) {
                    MethodHolder holder = allMethods.get(methodName);
                    Class methodParam = holder.param;
                    if (methodParam.isPrimitive()) {
                        methodParam = MaterialRebindUtils.primitiveToBoxed(methodParam);
                    }

                    methodName = holder.iface.getSimpleName().toLowerCase()+"_"+holder.method.getName();
                    JVar func = method.body().decl(jCodeModel.ref(BiConsumer.class).narrow(jCodeModel.ref(holder.iface.getCanonicalName())).narrow(jCodeModel.ref(methodParam)), methodName);
                    func.init(JExpr.direct("this::" + holder.method.getName()));
                    method.body().add(var.invoke("getMethods").invoke("put").arg(name).arg(JExpr._new(jCodeModel.ref(MaterialMethodDefinition.class)).arg(doJExpressionMethodDefinition(jCodeModel, param)).arg(func)));

                    allMethods.put(generateNativeMethodName(s), holder);
                    defaultMethods.add(generateNativeMethodName(s));
                } else if (!defaultMethods.contains(methodName)) {
                    CommonMaterialWidgetGenerator.MethodHolder holder = allMethods.get(methodName);
                    Class methodParam = holder.param;
                    if (methodParam.isPrimitive()) {
                        methodParam = MaterialRebindUtils.primitiveToBoxed(methodParam);
                    }
                    methodName = holder.iface.getSimpleName().toLowerCase()+"_"+holder.method.getName();
                    JVar func = method.body().decl(jCodeModel.ref(BiConsumer.class).narrow(jCodeModel.ref(holder.iface.getCanonicalName())).narrow(jCodeModel.ref(methodParam)), methodName);

                    func.init(JExpr.direct("this::" + holder.method.getName()));
                    method.body().add(var.invoke("getMethods").invoke("put").arg(name).arg(JExpr._new(jCodeModel.ref(MaterialMethodDefinition.class)).arg(doJExpressionMethodDefinition(jCodeModel, param)).arg(func)));
                    //     }
                }
            }
        }
        if (clazz.equals(MaterialWidget.class)) {
            method.body().add(JExpr.ref("defaultMethods").invoke("putAll").arg(var.invoke("getMethods")));
        }

        method.body().add(JExpr.ref("widgets").invoke("put").arg(tag.toLowerCase()).arg(JExpr.ref("field")));
        constructor.body().invoke("add" + tag);
    }

    private void generateWidgetInvoke(JCodeModel jCodeModel, Class clazz) {
        invoke.body()._if(JExpr.ref("tag").invoke("equals").arg(clazz.getSimpleName().toLowerCase()))._then()
                .assign(JExpr.ref("result"), JExpr._new(jCodeModel.ref(clazz)));

    }

}
