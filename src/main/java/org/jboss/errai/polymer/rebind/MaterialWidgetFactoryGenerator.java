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

package org.jboss.errai.polymer.rebind;

import com.google.gwt.thirdparty.guava.common.collect.ImmutableSet;
import com.google.gwt.thirdparty.guava.common.reflect.ClassPath;
import com.google.gwt.thirdparty.guava.common.reflect.ClassPath.ClassInfo;
import com.google.gwt.user.client.ui.Widget;
import com.sun.codemodel.*;
import com.sun.codemodel.writer.SingleStreamCodeWriter;
import gwt.material.design.client.base.MaterialWidget;
import gwt.material.design.client.constants.Type;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.reflect.ConstructorUtils;
import org.jboss.errai.polymer.shared.MaterialWidgetDefinition;
import org.jboss.errai.polymer.shared.MaterialWidgetFactory;
import org.jboss.errai.polymer.shared.Tuple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;


/**
 * @author Dmitrii Tikhomirov <chani@me.com>
 *
 * Created by treblereel on 3/9/17.
 */
public class MaterialWidgetFactoryGenerator {

    private static final Logger logger = LoggerFactory.getLogger(MaterialWidgetFactoryGenerator.class);
    private static final String DEFAULT_BUILD_LOCATION = "target/classes";
    private static final String GWT_MATERIAL_COMMON_PACKAGE = "gwt.material.design.client.ui.";
    private static final String GWT_MATERIAL_METHOD_PACKAGE = "gwt.material.design.client.base";
    private static final String GWT_MATERIAL_FACTORY_PACKAGE_NAME = "org.jboss.errai.polymer.shared";
    private static final String GWT_MATERIAL_FACTORY_CLASS_NAME = "MaterialWidgetFactoryImpl";
    private final Set<String> defaultMethods = new HashSet<>();
    private JMethod invoke;

    private static final String[] BLACKLISTED_PROPERTIES = {"FlexAlignItems", "TargetHistoryToken", "Activates", "InitialClasses",
            "Id", "AccessKey", "Scrollspy", "DataAttribute", "ErrorHandler", "ErrorHandlerType", "Validators", "FlexJustifyContent",
            "Class", "Input", "Fullscreen", "Parent"};


    public MaterialWidgetFactoryGenerator() {
        try {
            buildFactory();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        logger.info("generate MaterialWidgetFactory");
        new MaterialWidgetFactoryGenerator();
    }

    private void buildFactory() throws IOException {

        JCodeModel jCodeModel = new JCodeModel();
        JPackage jp = jCodeModel._package(GWT_MATERIAL_FACTORY_PACKAGE_NAME);
        JDefinedClass jc = null;
        try {
            jc = jp._class(GWT_MATERIAL_FACTORY_CLASS_NAME);
        } catch (JClassAlreadyExistsException e) {
            e.printStackTrace();
        }
        assert jc != null;
        jc._implements(MaterialWidgetFactory.class);
        jc.annotate(ApplicationScoped.class);

        JMethod constructor = jc.constructor(JMod.PUBLIC);

        JClass keyClass = jCodeModel.ref(String.class);
        JClass valueClass = jCodeModel.ref(MaterialWidgetDefinition.class);
        String detailName = "widgets";
        JClass rawLLclazz = jCodeModel.ref(Map.class);
        JClass fieldClazz = rawLLclazz.narrow(keyClass, valueClass);
        JClass defClazz = jCodeModel.ref(HashMap.class);
        JClass defFieldClazz = defClazz.narrow(keyClass, valueClass);
        jc.field(JMod.PRIVATE, fieldClazz, detailName, JExpr._new(defFieldClazz));


        constractInvokeMethodDeclaration(jCodeModel, jc);

        JClass defaultMethodsClass = jCodeModel.ref(Map.class);
        JClass fieldDefaultMethodsClass = defaultMethodsClass.narrow(jCodeModel.ref(String.class), jCodeModel.ref(Class.class));

        JClass defDefaultMethodsClass = jCodeModel.ref(HashMap.class);
        JClass defFieldDefaultMethodsClass = defDefaultMethodsClass.narrow(jCodeModel.ref(String.class), jCodeModel.ref(Class.class));
        jc.field(JMod.PRIVATE, fieldDefaultMethodsClass, "defaultMethods", JExpr._new(defFieldDefaultMethodsClass));

        constructGetWidgetDefIfExist(jc, jCodeModel);
        constructGetMethodDefIfExist(jc, jCodeModel);

        try {
            processGwtMaterialWidgets(jc, jCodeModel, constructor);
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }

        constractInvokeMethodBody(jCodeModel);

        jCodeModel.build(new File(DEFAULT_BUILD_LOCATION));

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        CodeWriter codeWriter = new SingleStreamCodeWriter(baos);
        jCodeModel.build(codeWriter);
    }

    private void constractInvokeMethodDeclaration(JCodeModel jCodeModel, JDefinedClass jc) {
        invoke = jc.method(JMod.PUBLIC, Widget.class, "invoke");
        invoke.param(String.class, "tag");
        invoke.annotate(Override.class);
        invoke.body().decl(jCodeModel.ref(Widget.class), "result").init(JExpr._null());
        invoke.body().assign(JExpr.ref("tag"), JExpr.ref("tag").invoke("toLowerCase").invoke("replaceAll").arg("-").arg(""));
    }

    private void constractInvokeMethodBody(JCodeModel jCodeModel) {
        invoke.body()._if(JExpr.ref("result").eq(JExpr._null()))._then()._throw(JExpr._new(jCodeModel.ref(RuntimeException.class)).arg("No such MaterialWidget found  "));

        invoke.body().decl(jCodeModel.ref(Optional.class).narrow(jCodeModel.ref(MaterialWidgetDefinition.class)), "def").init(JExpr._this().invoke("getWidgetDefIfExist").arg(JExpr.ref("tag")));
        JBlock jInvokeBlock = invoke.body()._if(JExpr.ref("def").invoke("isPresent").eq(JExpr.lit(true)).cand(JExpr.ref("def").invoke("get").invoke("getExtendsMaterialWidget").eq(JExpr.lit(true))))
                ._then();
        jInvokeBlock.decl(jCodeModel.ref(MaterialWidget.class), "candidate").init(JExpr.cast(jCodeModel.ref(MaterialWidget.class), JExpr.ref("result")));
        JBlock jInvokeBlockCandidate = jInvokeBlock._if(JExpr.ref("candidate").invoke("getInitialClasses").ne(JExpr._null()))._then();
        jInvokeBlockCandidate.decl(jCodeModel.ref(StringBuffer.class), "sb").init(JExpr._new(jCodeModel.ref(StringBuffer.class)));
        jInvokeBlockCandidate.forEach(jCodeModel.ref(String.class), "css", JExpr.ref("candidate").invoke("getInitialClasses")).body()
                .add(JExpr.ref("sb").invoke("append").arg(JExpr.ref("css")).invoke("append").arg(" "));
        jInvokeBlockCandidate.add(JExpr.ref("candidate").invoke("getElement").invoke("setAttribute").arg("class").arg(JExpr.ref("sb").invoke("toString").invoke("trim")));
        invoke.body()._return(JExpr.ref("result"));
    }

    private void constructGetWidgetDefIfExist(JDefinedClass jc, JCodeModel jCodeModel) {
        JType returnType = jCodeModel.ref(Optional.class).narrow(MaterialWidgetDefinition.class);
        JClass optional = jCodeModel.directClass(Optional.class.getCanonicalName());

        JMethod method = jc.method(JMod.PUBLIC, returnType, "getWidgetDefIfExist");
        method.param(java.lang.String.class, "tag");
        method.annotate(Override.class);

        method.body().assign(JExpr.ref("tag"), JExpr.ref("tag").invoke("toLowerCase").invoke("replaceAll").arg("-").arg(""));


        JBlock block = method.body()._if(JExpr.ref("widgets")
                .invoke("containsKey").arg(JExpr.ref("tag"))
        )._then();

        JVar var = block.decl(jCodeModel.ref(MaterialWidgetDefinition.class), "field");
        var.init(JExpr.ref("widgets").invoke("get").arg(JExpr.ref("tag")));

        block._if(JExpr.ref("field").invoke("getExtendsMaterialWidget")
                .eq(JExpr.lit(true)))._then().add(JExpr.ref("field").invoke("getMethods").invoke("putAll").arg(JExpr.ref("defaultMethods")));

        block._return(optional.staticInvoke("of").arg(JExpr.ref("field")));

        method.body()._return(optional.staticInvoke("empty"));
    }

    private void constructGetMethodDefIfExist(JDefinedClass jc, JCodeModel jCodeModel) {

        JType returnTuple = jCodeModel.ref(Tuple.class).narrow(String.class, Class.class);
        JType returnType = jCodeModel.ref(Optional.class).narrow(returnTuple);
        JClass optional = jCodeModel.directClass(Optional.class.getCanonicalName());

        JMethod method = jc.method(JMod.PUBLIC, returnType, "getMethodDefIfExist");
        method.param(java.lang.String.class, "tag");
        method.param(java.lang.String.class, "method");
        method.annotate(Override.class);

        method.body().assign(JExpr.ref("tag"), JExpr.ref("tag").invoke("toLowerCase").invoke("replaceAll").arg("-").arg(""));

        method.body()._if(JExpr.ref("widgets").invoke("containsKey").arg(JExpr.ref("tag")).eq(JExpr.lit(false)))._then()._return(optional.staticInvoke("empty"));


        method.body()._if(JExpr.ref("widgets").invoke("get").arg(JExpr.ref("tag"))
                .invoke("getMethods")
                .invoke("containsKey")
                .arg(JExpr.ref("method")))._then()._return(optional.staticInvoke("of").arg(JExpr._new(jCodeModel.ref(Tuple.class)
                .narrow(String.class).narrow(Class.class)).arg(JExpr.ref("tag")).arg(JExpr.ref("widgets")
                .invoke("get").arg(JExpr.ref("tag"))
                .invoke("getMethods")
                .invoke("get")
                .arg(JExpr.ref("method")))));

        method.body()._if(JExpr.ref("defaultMethods").invoke("containsKey").arg(JExpr.ref("tag")))
                ._then()._return(optional.staticInvoke("of").arg(JExpr._new(jCodeModel.ref(Tuple.class)
                .narrow(String.class).narrow(Class.class)).arg(JExpr.ref("tag")).arg(JExpr.ref("defaultMethods")
                .invoke("get")
                .arg(JExpr.ref("tag").invoke("toLowerCase")))));


        method.body()._return(optional.staticInvoke("empty"));
    }

    private boolean hasParameterlessConstructor(Class<?> clazz) {
        Constructor[] allConstructors = clazz.getDeclaredConstructors();
        for (Constructor ctor : allConstructors) {
            if (ctor.getParameterCount() == 0 && ConstructorUtils.getAccessibleConstructor(ctor) != null) {
                return true;
            }
        }
        return false;
    }

    private void processGwtMaterialWidgets(JDefinedClass jc, JCodeModel jCodeModel, JMethod constructor) throws IOException, ClassNotFoundException {

        final ClassLoader loader = Thread.currentThread().getContextClassLoader();
        ImmutableSet<ClassPath.ClassInfo> topLevelClasses = com.google.gwt.thirdparty.guava.common.reflect.ClassPath.from(loader).getTopLevelClasses();
        Set<Method> methods = new HashSet<>();
        Arrays.stream(MaterialWidget.class.getDeclaredMethods()).forEach(m -> methods.add(m));
        generateMaterialBaseWidgetMethodsDeclaretion(jc, jCodeModel, constructor, methods);

        for (final com.google.gwt.thirdparty.guava.common.reflect.ClassPath.ClassInfo info : topLevelClasses) {
            if (isWidgetSupported(info)) {
                if (info.load().getCanonicalName().equals(GWT_MATERIAL_COMMON_PACKAGE + info.load().getSimpleName())) {
                    Set<Method> properties = new HashSet<>();
                    processMethods(info, properties);
                    generateWidgetDeclaretion(jc, jCodeModel, constructor, info.load(), properties, isExtendsMaterialWidget(info.load()));
                }
            }
        }
    }

    private boolean isWidgetSupported(ClassInfo info) {
        if (!info.getName().startsWith(GWT_MATERIAL_COMMON_PACKAGE)) {
            return false;
        }
        return !Modifier.isAbstract(info.getClass().getModifiers()) && findParent(info.load()) && hasParameterlessConstructor(info.load());
    }

    private boolean findParent(Class<?> clazz) {
        if (clazz.getCanonicalName().equals(MaterialWidget.class.getCanonicalName()) ||
                clazz.getCanonicalName().equals(MaterialWidget.class.getCanonicalName())) {
            return true;
        } else if (clazz.getCanonicalName().equals(Object.class.getCanonicalName())) {
            return false;
        }
        return findParent(clazz.getSuperclass());

    }

    private void generateMaterialBaseWidgetMethodsDeclaretion(JDefinedClass jc, JCodeModel jCodeModel, JMethod constructor, Set<Method> methods) {
        List<String> blackList = Arrays.asList(BLACKLISTED_PROPERTIES);
        JMethod method = jc.method(JMod.PRIVATE, void.class, "addMaterialBaseWidgetMethods");
        for (Method s : new HashSet<>(methods)) {
            if (s.getName().startsWith("set")) {
                String candidate = s.getName().replace("set", "");
                Class<?>[] pType = s.getParameterTypes();
                Class param = pType[0];
                if (!blackList.contains(candidate) &&
                        !param.getCanonicalName().equals(Type.class.getCanonicalName())) {
                    param = processMethodSignature(param);
                    method.body().add(JExpr._this().ref("defaultMethods").invoke("put").arg(candidate).arg(jCodeModel.ref(param).dotclass()));
                    defaultMethods.add(candidate);

                }
            }
        }
        method.body().add(JExpr._this().ref("defaultMethods").invoke("put").arg("Offset").arg(jCodeModel.ref(String.class).dotclass()));
        method.body().add(JExpr._this().ref("defaultMethods").invoke("put").arg("Visible").arg(jCodeModel.ref(Boolean.class).dotclass()));
        defaultMethods.add("Offset");
        defaultMethods.add("Visible");

        constructor.body().invoke("addMaterialBaseWidgetMethods");
    }

    private void processMethods(ClassInfo materialWidget, Set<Method> methods) {
        ClassUtils.getAllSuperclasses(materialWidget.load()).stream().forEach(s -> {
            if (s.getSimpleName().equals(MaterialWidget.class.getSimpleName()))
                ClassUtils.getAllInterfaces(s).stream().forEach(ifaces -> {
                    if (ifaces.getName().startsWith("gwt.material.design.client.base") ||
                            ifaces.getName().startsWith("com.google.gwt.user.client.ui.HasVisibility") ||
                            ifaces.getName().startsWith("com.google.gwt.user.client.ui.HasEnabled") ||
                            ifaces.getName().startsWith("com.google.gwt.user.client.ui.Focusable")
                            ) {
                        Arrays.stream(ifaces.getMethods()).forEach(method -> {
                            if (method.getName().startsWith("set")) {
                                maybeAddProperty(methods, method);
                            }
                        });
                    }
                });
        });
        Arrays.stream(materialWidget.load().getDeclaredMethods()).forEach(method -> {
            if (method.getName().startsWith("set")) {
                maybeAddProperty(methods, method);
            }
        });

    }

    private Boolean isExtendsMaterialWidget(Class<?> widget) {
        for (Class<?> clazz : ClassUtils.getAllSuperclasses(widget)) {
            if (clazz.getSimpleName().equals(MaterialWidget.class.getSimpleName())) {
                return true;
            }
        }
        return false;
    }


    private void generateWidgetDeclaretion(JDefinedClass jc, JCodeModel jCodeModel, JMethod constructor, Class clazz, Set<Method> properties, Boolean isExtendsMaterialWidget) {
        generateWidgetInvoke(jCodeModel, clazz);
        String tag = clazz.getSimpleName();
        JMethod method = jc.method(JMod.PRIVATE, void.class, "add" + tag);
        JVar var = method.body().decl(jCodeModel.ref(MaterialWidgetDefinition.class), "field");
        var.init(JExpr._new(jCodeModel.ref(MaterialWidgetDefinition.class)).arg(tag).arg(jCodeModel.ref(clazz).dotclass()).arg(JExpr.lit(isExtendsMaterialWidget)));
        for (Method s : properties) {
            Class<?>[] pType = s.getParameterTypes();
            Class param = pType[0];
            if (!defaultMethods.contains(s.getName().replace("set", "")) && !param.getCanonicalName().equals(Type.class.getCanonicalName())) {


                param = processMethodSignature(param);

                method.body().add(var.invoke("getMethods").invoke("put").arg(s.getName().replace("set", "")).arg(jCodeModel.ref(param).dotclass()));
            }
        }
        method.body().add(JExpr._this().ref("widgets").invoke("put").arg(tag.toLowerCase()).arg(JExpr.ref("field")));
        constructor.body().invoke("add" + tag);
    }

    private Class processMethodSignature(Class param) {
        if (param.isPrimitive()) {
            if (param.equals(boolean.class)) {
                param = Boolean.class;
            } else if (param.equals(int.class)) {
                param = Integer.class;
            } else if (param.equals(long.class)) {
                param = Long.class;
            } else if (param.equals(double.class)) {
                param = Double.class;
            } else if (param.equals(float.class)) {
                param = Float.class;
            }
        }
        return param;
    }

    private void generateWidgetInvoke(JCodeModel jCodeModel, Class clazz) {
        invoke.body()._if(JExpr.ref("tag").invoke("equals").arg(clazz.getSimpleName().toLowerCase()))._then()
                .assign(JExpr.ref("result"), JExpr._new(jCodeModel.ref(clazz)));
    }


    private void maybeAddProperty(Set<Method> properties, Method property) {
        if (property.getName().startsWith("set")) {
            String candidate = property.getName().replaceFirst("set", "");
            if (!Arrays.asList(BLACKLISTED_PROPERTIES).contains(candidate)) {
                properties.add(property);
            }
        }
    }

}
