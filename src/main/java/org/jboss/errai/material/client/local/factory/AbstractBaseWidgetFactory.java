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

package org.jboss.errai.material.client.local.factory;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;
import org.jboss.errai.material.client.local.MaterialMethodDefinition;
import org.jboss.errai.material.client.local.MaterialWidgetDefinition;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.HashMap;
import java.util.Optional;

/**
 * @author Dmitrii Tikhomirov <chani@me.com>
 *         Created by treblereel on 5/29/17.
 */
public abstract class AbstractBaseWidgetFactory implements MaterialWidgetFactory {

    protected Map<String, MaterialWidgetDefinition> widgets = new HashMap<>();
    protected Map<String, MaterialMethodDefinition> defaultMethods = new HashMap<>();


    public Optional<Boolean> isExtendsMaterialWidget(String tag){
        LoggerFactory.getLogger(this.getClass().getSimpleName()).warn(" tag " + tag.toLowerCase() + " " + widgets.containsKey(tag.toLowerCase()) + " " + widgets.size());
        if (widgets.containsKey(tag.toLowerCase())) {
            return Optional.of(widgets.get(tag.toLowerCase()).getExtendsMaterialWidget());
        }
        return Optional.empty();
    }

    public Optional<MaterialWidgetDefinition> getWidgetDefIfExist(String tag) {
        tag = tag.toLowerCase().replaceAll("-", "");
        if (widgets.containsKey(tag)) {
            MaterialWidgetDefinition field = widgets.get(tag);
            if (field.getExtendsMaterialWidget() == true) {
                field.getMethods().putAll(defaultMethods);
            }
            return Optional.of(field);
        }
        return Optional.empty();
    }

    public Optional<MaterialMethodDefinition> getMethodDefIfExist(String tag, String method) {
        tag = tag.toLowerCase().replaceAll("-", "");
        method = method.toLowerCase();
        if (widgets.containsKey(tag) == false) {
            return Optional.empty();
        }
        if (widgets.get(tag).getMethods().containsKey(method)) {
            return Optional.of(widgets.get(tag).getMethods().get(method));
        }
        if (defaultMethods.containsKey(method)) {
            return Optional.of(defaultMethods.get(method.toLowerCase()));
        }
        return Optional.empty();
    }

    public abstract Optional<Widget> invoke(Element element);

}
