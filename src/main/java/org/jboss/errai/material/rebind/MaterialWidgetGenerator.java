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

import com.sun.codemodel.*;
import org.apache.commons.lang3.ClassUtils;
import org.jboss.errai.material.client.local.MaterialMethodDefinition;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * @author Dmitrii Tikhomirov <chani@me.com>
 *         Created by treblereel on 5/23/17.
 */
public abstract class MaterialWidgetGenerator {

    protected final Map<String, MethodHolder> allMethods = new HashMap<>();
    protected final Set<String> defaultMethods = new HashSet<>();

    // TODO some of the this props should be activated other way
    protected static final String[] BLACKLISTED_PROPERTIES = {"FlexAlignItems", "TargetHistoryToken", "InitialClasses",
            "Id", "AccessKey", "DataAttribute", "ErrorHandler", "ErrorHandlerType", "Validators", "FlexJustifyContent",
            "Class", "Input", "Fullscreen", "Parent", "StylePrimaryName", "StyleName"};

    protected JCodeModel jCodeModel = new JCodeModel();

    protected String generateNativeMethodName(Method method) {
        return (method.getName() + "_" + method.getDeclaringClass().getCanonicalName().replaceAll("\\.", "_")).toLowerCase();
    }

    protected Boolean isMethodParamSupported(Class clazz) {
        if (clazz.equals(Boolean.class) || clazz.equals(boolean.class)
                || clazz.equals(String.class) || clazz.equals(Double.class)
                || clazz.equals(double.class) || clazz.equals(Integer.class)
                || clazz.equals(int.class) || clazz.isEnum()) {
            return true;
        }
        return false;
    }

    public void generateNativeMethod(JDefinedClass jc, MethodHolder holder) {
        jc.direct("    public void " + holder.method.getName() + "(" + holder.iface.getCanonicalName() + " x, " + holder.param.getCanonicalName() + " y){  x." + holder.name + "(y);       }\n");
    }

    protected void processMethods(Class materialWidget, Set<Method> methods) {
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

    protected void processNativeMethods(JDefinedClass jc) {
        allMethods.entrySet().stream()
                .sorted(Map.Entry.<String, MethodHolder>comparingByKey())
                .forEach((k) -> {
                        generateNativeMethod(jc, k.getValue());
                });
    }

    protected String doJNDIMethodSignature(Class param) {
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

    public void findAllSettableMethods(Class clazz, java.util.Set<Method> methods) {
        ClassUtils.getAllInterfaces(clazz).stream().forEach(ifaces -> {
            if (ifaces.getName().startsWith("com.google.gwt.user.client.ui") ||
                    ifaces.getName().startsWith("com.google.gwt.user.client.ui")
                    ) {
                Arrays.stream(ifaces.getMethods()).forEach(m -> {
                    if (m.getName().startsWith("set")) {
                        maybeAddProperty(methods, m, clazz);

                    }
                });
            }
        });
        Arrays.stream(clazz.getMethods()).forEach(m -> {
            if (m.getName().startsWith("set")) {
                Optional<Method> check = methods.stream().filter(mm -> mm.getName().equals(m.getName())).findFirst();
                if (!check.isPresent()) {
                    maybeAddProperty(methods, m, clazz);
                }
            }
        });
    }

    protected JExpression doJExpressionMethodDefinition(JCodeModel jCodeModel, Class clazz) {
        if (clazz.isPrimitive()) {
            return JExpr.direct(clazz.getCanonicalName() + ".class");
        }
        return jCodeModel.ref(clazz).dotclass();
    }


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

        method.body()._if(JExpr.ref("defaultMethods").invoke("containsKey").arg(JExpr.ref("method")))
                ._then()._return(optional.staticInvoke("of").arg(JExpr.ref("defaultMethods")
                .invoke("get")
                .arg(JExpr.ref("method").invoke("toLowerCase"))));


        method.body()._return(optional.staticInvoke("empty"));
    }

    private void maybeAddProperty(Set<Method> properties, Method property, Class materialWidget) {
        if (property.getParameters().length == 1 && Modifier.isPublic(property.getModifiers()) && !Modifier.isStatic(property.getModifiers())) {
            if (!Arrays.asList(BLACKLISTED_PROPERTIES).contains(property.getName().replaceFirst("set", ""))
                    && isMethodParamSupported(property.getParameters()[0].getType())) {
                Class type = property.getParameters()[0].getType();
                String methodName = generateNativeMethodName(property);
             //   if (!allMethods.containsKey(methodName)) {
                    allMethods.put(methodName, new MethodHolder(materialWidget, property.getDeclaringClass(), type, property, property.getName()));
                    properties.add(property);
             //   }
            }
        }
    }


    protected class MethodHolder {
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
