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
import com.sun.codemodel.*;
import com.sun.codemodel.writer.SingleStreamCodeWriter;
import gwt.material.design.client.base.MaterialWidget;
import gwt.material.design.client.constants.Type;
import org.apache.commons.lang3.ClassUtils;
import org.jboss.errai.material.client.local.MaterialMethodDefinition;
import org.jboss.errai.material.client.local.MaterialWidgetDefinition;
import org.jboss.errai.material.client.local.MaterialWidgetFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.function.BiConsumer;


/**
 * @author Dmitrii Tikhomirov <chani@me.com>
 *         <p>
 *         Created by treblereel on 3/9/17.
 */

public class MaterialWidgetFactoryGenerator {

    private static final Logger logger = LoggerFactory.getLogger(MaterialWidgetFactoryGenerator.class);
    private static final String DEFAULT_BUILD_LOCATION = "target/classes";
    private static final String GWT_MATERIAL_FACTORY_PACKAGE_NAME = "org.jboss.errai.material.client";
    private static final String GWT_MATERIAL_FACTORY_CLASS_NAME = "MaterialWidgetFactoryImpl";
    private final Set<String> defaultMethods = new HashSet<>();
    private final Map<String, MethodHolder> allMethods = new HashMap<>();

    private JMethod invoke;


    // TODO some of the this props should be activated other way
    private static final String[] BLACKLISTED_PROPERTIES = {"FlexAlignItems", "TargetHistoryToken", "InitialClasses",
            "Id", "AccessKey", "Scrollspy", "DataAttribute", "ErrorHandler", "ErrorHandlerType", "Validators", "FlexJustifyContent",
            "Class", "Input", "Fullscreen", "Parent", "StylePrimaryName", "StyleName"};


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
        jc.annotate(Singleton.class);

        JMethod constructor = jc.constructor(JMod.PUBLIC);

        JClass keyClass = jCodeModel.ref(String.class);
        JClass valueClass = jCodeModel.ref(MaterialWidgetDefinition.class);
        String detailName = "widgets";
        JClass rawLLclazz = jCodeModel.ref(Map.class);
        JClass fieldClazz = rawLLclazz.narrow(keyClass, valueClass);
        JClass defClazz = jCodeModel.ref(HashMap.class);
        JClass defFieldClazz = defClazz.narrow(keyClass, valueClass);
        jc.field(JMod.PRIVATE, fieldClazz, detailName, JExpr._new(defFieldClazz));


        constructInvokeMethodDeclaration(jCodeModel, jc);

        JClass defaultMethodsClass = jCodeModel.ref(Map.class);
        JClass fieldDefaultMethodsClass = defaultMethodsClass.narrow(jCodeModel.ref(String.class), jCodeModel.ref(MaterialMethodDefinition.class));

        JClass defDefaultMethodsClass = jCodeModel.ref(HashMap.class);
        JClass defFieldDefaultMethodsClass = defDefaultMethodsClass.narrow(jCodeModel.ref(String.class), jCodeModel.ref(MaterialMethodDefinition.class));
        jc.field(JMod.PRIVATE, fieldDefaultMethodsClass, "defaultMethods", JExpr._new(defFieldDefaultMethodsClass));

        constructGetWidgetDefIfExist(jc, jCodeModel);
        constructGetMethodDefIfExist(jc, jCodeModel);

        try {
            processGwtMaterialWidgets(jc, jCodeModel, constructor);
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }

        constractInvokeMethodBody(jCodeModel);

        processNativeMethods(jc);

        jCodeModel.build(new File(DEFAULT_BUILD_LOCATION));

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        CodeWriter codeWriter = new SingleStreamCodeWriter(baos);
        jCodeModel.build(codeWriter);
    }

    private void processNativeMethods(JDefinedClass jc) {
        allMethods.entrySet().stream()
                .sorted(Map.Entry.<String, MethodHolder>comparingByKey())
                .forEach((k) -> {
                    generateNativeMethod(jc, k.getValue());
                });
    }

    private void generateNativeMethod(JDefinedClass jc, MethodHolder holder) {
        if (Modifier.isAbstract(holder.iface.getModifiers()) && !holder.iface.isInterface()) {
            String methodName = generateNativeMethodName(holder.method) +holder.widget.getSimpleName();
            jc.direct("    public void " + methodName + "(" + MaterialWidget.class.getCanonicalName() + " x, " + holder.param.getCanonicalName() + " y){  (("+holder.iface.getCanonicalName()+")x)."+holder.name+"(y);       }\n");

        } else {
            jc.direct("    public native void " + generateNativeMethodName(holder.method) + "(Object x, Object s) /*-{\nx.@" + holder.iface.getCanonicalName() + "::" + holder.name + "(" + doJNDIMethodSignature(holder.param) + ")(s);\n}-*/;\n");
        }

    }

    private String doJNDIMethodSignature(Class param) {
        String result = "";
        if (param.isPrimitive()) {
            if (param.equals(boolean.class)) {
                result = "Z";
            } else if (param.equals(int.class)) {
                result = "I";
            } else if (param.equals(long.class)) {
                throw new RuntimeException("long primitive is not supported as method parameter");
            } else if (param.equals(double.class)) {
                result = "D";
            }
        } else {
            if (param.getEnclosingClass() != null) {
                result = doJNDIEnumMethodSignature(param);
            } else {
                result = "L" + param.getCanonicalName().replaceAll("\\.", "/") + ";";
            }
        }
        return result;
    }

    private String doJNDIEnumMethodSignature(Class param) {
        StringBuilder sb = new StringBuilder("L");
        sb.append(param.getPackage().getName().replaceAll("\\.", "/"));
        sb.append("/");
        sb.append(param.getEnclosingClass().getSimpleName());
        sb.append("$");
        sb.append(param.getSimpleName());
        sb.append(";");
        return sb.toString();
    }

    private void constructInvokeMethodDeclaration(JCodeModel jCodeModel, JDefinedClass jc) {
        invoke = jc.method(JMod.PUBLIC, jCodeModel.ref(Optional.class).narrow(jCodeModel.ref(MaterialWidget.class)), "invoke");
        invoke.param(Element.class, "tagged");
        invoke.annotate(Override.class);
        invoke.body().decl(jCodeModel.ref(String.class), "tag").init(JExpr.ref("tagged").invoke("getTagName").invoke("toLowerCase").invoke("replaceAll").arg("-").arg(""));
        invoke.body().decl(jCodeModel.ref(MaterialWidget.class), "result").init(JExpr._null());
    }

    private void constractInvokeMethodBody(JCodeModel jCodeModel) {
        //invoke.body()._if(JExpr.ref("result").eq(JExpr._null()))._then()._throw(JExpr._new(jCodeModel.ref(RuntimeException.class)).arg(JExpr.lit("No such MaterialWidget found  ").plus(JExpr.ref("tag"))));

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

        //invoke.body()._return(JExpr.ref("result"));
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

        method.body()._if(JExpr.ref("defaultMethods").invoke("containsKey").arg(JExpr.ref("method")))
                ._then()._return(optional.staticInvoke("of").arg(JExpr.ref("defaultMethods")
                .invoke("get")
                .arg(JExpr.ref("method").invoke("toLowerCase"))));


        method.body()._return(optional.staticInvoke("empty"));
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

    private JExpression doJExpressionMethodDefinition(JCodeModel jCodeModel, Class clazz) {
        if (clazz.isPrimitive()) {
            return JExpr.direct(clazz.getCanonicalName() + ".class");
        }
        return jCodeModel.ref(clazz).dotclass();
    }


    private void processMethods(Class materialWidget, Set<Method> methods) {
        ClassUtils.getAllInterfaces(materialWidget).stream().forEach(ifaces -> {
            if (ifaces.getName().startsWith("gwt.material.design.client.base.") ||
                    ifaces.getName().startsWith("com.google.gwt.user.client.ui.")
                    ) {
                Arrays.stream(ifaces.getMethods()).forEach(m -> {
                    if (m.getName().startsWith("set")) {
                        maybeAddProperty(methods, m, materialWidget);
                    }
                });
            }
        });

        Arrays.stream(materialWidget.getMethods()).forEach(m -> {
            if (m.getName().startsWith("set")) {
                Optional<Method> check = methods.stream().filter(mm -> mm.getName().equals(m.getName())).findFirst();
                if (!check.isPresent()) {
                    maybeAddProperty(methods, m, materialWidget);
                }
            }

        });
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
            String name = s.getName().replaceFirst("set", "").toLowerCase();
            if (!param.getCanonicalName().equals(Type.class.getCanonicalName()) &&
                    !param.getCanonicalName().equals(Object.class.getCanonicalName())) {
                String methodName = generateNativeMethodName(s);
                if (clazz.equals(MaterialWidget.class)) {
                    methodName = generateNativeMethodName(s);
                    MethodHolder holder = allMethods.get(methodName);
                    if (Modifier.isAbstract(holder.iface.getModifiers()) && !holder.iface.isInterface()) {
                        Class methodParam = holder.param;
                        if(methodParam.isPrimitive()){
                            methodParam = MaterialRebindUtils.primitiveToBoxed(methodParam);
                        }
                        methodName +=""+holder.widget.getSimpleName();
                        JVar func = method.body().decl(jCodeModel.ref(BiConsumer.class).narrow(jCodeModel.ref(MaterialWidget.class)).narrow(jCodeModel.ref(methodParam)), methodName);
                        func.init(JExpr.direct("this::" + methodName));
                        method.body().add(var.invoke("getMethods").invoke("put").arg(name).arg(JExpr._new(jCodeModel.ref(MaterialMethodDefinition.class)).arg(doJExpressionMethodDefinition(jCodeModel, param)).arg(func)));
                    } else {
                        method.body().add(var.invoke("getMethods").invoke("put").arg(name).arg(JExpr._new(jCodeModel.ref(MaterialMethodDefinition.class)).arg(doJExpressionMethodDefinition(jCodeModel, param)).arg(JExpr.direct("this::" + generateNativeMethodName(s)))));
                    }
                } else if (!defaultMethods.contains(methodName)) {
                    MethodHolder holder = allMethods.get(methodName);
                    if (Modifier.isAbstract(holder.iface.getModifiers()) && !holder.iface.isInterface()) {
                        Class methodParam = holder.param;
                        if(methodParam.isPrimitive()){
                            methodParam = MaterialRebindUtils.primitiveToBoxed(methodParam);
                        }
                        methodName +=""+holder.widget.getSimpleName();
                        JVar func = method.body().decl(jCodeModel.ref(BiConsumer.class).narrow(jCodeModel.ref(MaterialWidget.class)).narrow(jCodeModel.ref(methodParam)), methodName);
                        func.init(JExpr.direct("this::" + methodName));
                        method.body().add(var.invoke("getMethods").invoke("put").arg(name).arg(JExpr._new(jCodeModel.ref(MaterialMethodDefinition.class)).arg(doJExpressionMethodDefinition(jCodeModel, param)).arg(func)));
                    } else {
                        method.body().add(var.invoke("getMethods").invoke("put").arg(name).arg(JExpr._new(jCodeModel.ref(MaterialMethodDefinition.class)).arg(doJExpressionMethodDefinition(jCodeModel, param)).arg(JExpr.direct("this::" + methodName))));
                    }
                }
            }
        }
        if (clazz.equals(MaterialWidget.class)) {
            method.body().add(JExpr._this().ref("defaultMethods").invoke("putAll").arg(var.invoke("getMethods")));
        }

        method.body().add(JExpr._this().ref("widgets").invoke("put").arg(tag.toLowerCase()).arg(JExpr.ref("field")));
        constructor.body().invoke("add" + tag);
    }

    private void generateWidgetInvoke(JCodeModel jCodeModel, Class clazz) {
        invoke.body()._if(JExpr.ref("tag").invoke("equals").arg(clazz.getSimpleName().toLowerCase()))._then()
                .assign(JExpr.ref("result"), JExpr._new(jCodeModel.ref(clazz)));

    }


    private void maybeAddProperty(Set<Method> properties, Method property, Class materialWidget) {
        if (property.getParameters().length == 1 && Modifier.isPublic(property.getModifiers()) && !Modifier.isStatic(property.getModifiers())) {
            if (!Arrays.asList(BLACKLISTED_PROPERTIES).contains(property.getName().replaceFirst("set", ""))
                    && isMethodParamSupported(property.getParameters()[0].getType())) {
                Class type = property.getParameters()[0].getType();
                String methodName = generateNativeMethodName(property);
                if (materialWidget.equals(MaterialWidget.class)) {
                    properties.add(property);
                    defaultMethods.add(generateNativeMethodName(property));
                } else if (MaterialRebindUtils.isExtendsMaterialWidget(materialWidget)) {
                    properties.add(property);

                } else {
                    //        properties.add(property);
                }

                if (!allMethods.containsKey(methodName)) {
                    allMethods.put(methodName, new MethodHolder(materialWidget, property.getDeclaringClass(), type, property, property.getName()));
                }
            }
        }
    }

    private Boolean isMethodParamSupported(Class clazz) {
        if (clazz.equals(Boolean.class) || clazz.equals(boolean.class)
                || clazz.equals(String.class) || clazz.equals(Double.class)
                || clazz.equals(double.class) || clazz.equals(Integer.class)
                || clazz.equals(int.class) || clazz.isEnum()) {
            return true;
        }
        return false;
    }

    private String generateNativeMethodName(Method method) {
        return (method.getName() + "_" + method.getDeclaringClass().getCanonicalName().replaceAll("\\.", "_")).toLowerCase();
    }

    private class MethodHolder {
        Class widget;
        Class iface;
        Class param;
        Method method;
        String name;

        MethodHolder(Class widget, Class iface, Class param, Method method, String name) {
            this.widget = widget;
            this.iface = iface;
            this.param = param;
            this.method = method;
            this.name = name;
        }

    }

}
