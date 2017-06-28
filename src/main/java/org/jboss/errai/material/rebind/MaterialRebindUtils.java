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

import com.google.gwt.thirdparty.guava.common.reflect.ClassPath;
import gwt.material.design.client.base.MaterialWidget;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.reflect.ConstructorUtils;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

/**
 * @author Dmitrii Tikhomirov <chani@me.com>
 *         Created by treblereel on 4/6/17.
 */
public class MaterialRebindUtils {
    static final String GWT_MATERIAL_COMMON_PACKAGE = "gwt.material.design.client.ui.";
    static final String GWT_MATERIAL_ADDINS_PACKAGE = "gwt.material.design.addins.client.";
    static final String GWT_MATERIAL_HTML_PACKAGE = "gwt.material.design.client.ui.html.";


    static boolean hasParameterlessConstructor(Class<?> clazz) {
        Constructor[] allConstructors = clazz.getDeclaredConstructors();
        for (Constructor ctor : allConstructors) {
            if (ctor.getParameterCount() == 0 && ConstructorUtils.getAccessibleConstructor(ctor) != null) {
                return true;
            }
        }
        return false;
    }

    static boolean isWidgetSupported(ClassPath.ClassInfo info) {
        if (!info.getName().startsWith(GWT_MATERIAL_COMMON_PACKAGE) && !info.getName().startsWith(GWT_MATERIAL_ADDINS_PACKAGE)
                && !info.getName().startsWith(GWT_MATERIAL_HTML_PACKAGE)
                ) {
            return false;
        }
        return !Modifier.isAbstract(info.getClass().getModifiers()) && isExtendsMaterialWidget(info.load()) && hasParameterlessConstructor(info.load());
    }

    public static Boolean isExtendsMaterialWidget(Class<?> widget) {
        return isExtends(widget, MaterialWidget.class);
    }

    public static Boolean isExtends(Class<?> widget, Class isExtends) {
        for (Class<?> clazz : ClassUtils.getAllSuperclasses(widget)) {
            if (clazz.equals(isExtends)) {
                return true;
            }
        }
        return false;
    }

    static Class primitiveToBoxed(Class clazz) {
        if (clazz.equals(boolean.class)) {
            return Boolean.class;
        } else if (clazz.equals(double.class)) {
            return Double.class;
        } else if (clazz.equals(int.class)) {
            return Integer.class;
        }
        return clazz;
    }



}