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

package org.jboss.errai.material.client.local;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Dmitrii Tikhomirov <chani@me.com>
 *
 * Created by treblereel on 3/10/17.
 */
public class MaterialWidgetDefinition {

    private Class clazz;
    private String tag;

    private Boolean extendsMaterialWidget = false;

    private Map<String, MaterialMethodDefinition> methods = new HashMap<>();

    /**
     * TODO Fix string value
     * @param tag
     * @param clazz
     * @param extendsMaterialWidget
     */
    public MaterialWidgetDefinition(String tag, Class clazz, Boolean extendsMaterialWidget){
        this.tag = tag;
        this.clazz = clazz;
        this.extendsMaterialWidget = extendsMaterialWidget;

    }

    public Class getClazz() {
        return clazz;
    }

    public String getTag() {
        return tag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MaterialWidgetDefinition)) return false;

        MaterialWidgetDefinition that = (MaterialWidgetDefinition) o;

        if (!getClazz().equals(that.getClazz())) return false;
        return getTag().equals(that.getTag()) && (getMethods() != null ? getMethods().equals(that.getMethods()) : that.getMethods() == null);

    }

    @Override
    public int hashCode() {
        int result = getClazz().hashCode();
        result = 31 * result + getTag().hashCode();
        result = 31 * result + (getMethods() != null ? getMethods().hashCode() : 0);
        return result;
    }

    public Map<String, MaterialMethodDefinition> getMethods() {
        return methods;
    }

    public void setMethods(Map<String, MaterialMethodDefinition> methods) {
        this.methods = methods;
    }

    public Boolean getExtendsMaterialWidget() {
        return extendsMaterialWidget;
    }

}
