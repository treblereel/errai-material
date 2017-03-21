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

package org.jboss.errai.polymer.client.local;

import java.util.EnumSet;

/**
 * @author Dmitrii Tikhomirov <chani@me.com>
 *         Created by treblereel on 3/21/17.
 */
public class GwtMaterialUtil {

    public static void processWidgetProperties(){

    }


    public static Class primitiveToBoxed(Class clazz){
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
