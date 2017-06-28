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

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import static org.jboss.errai.material.client.local.GwtMaterialUtil.getMaterialIdFieldValue;
import static org.jboss.errai.material.client.local.GwtMaterialUtil.hasMaterialIdField;


/**
 * @author Dmitrii Tikhomirov <chani@me.com>
 *         Created by treblereel on 6/23/17.
 */
public class GwtMaterialPostInit {
    Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    private Map<String, Widget> templateFieldsMap;

    private String content;


    GwtMaterialPostInit(Element root, String content, Map<String, Widget> templateFieldsMap) {
        this.templateFieldsMap = templateFieldsMap;
        this.content = content;

        process(root);
    }

    private void process(Element element) {

        if (element.getNodeType() == 1 && hasMaterialIdField(element)) {
            String id = getMaterialIdFieldValue(element);
            element.getParentElement().replaceChild(templateFieldsMap.get(id).getElement(), element);
        } else if (element.getNodeType() == 1 && GwtMaterialUtil.isMaterialWidget(element) && GwtMaterialUtil.hasDataField(element)) {
            String id = GwtMaterialUtil.getDataFieldValue(element);
            element.getParentElement().replaceChild(templateFieldsMap.get(id).getElement(), element);
        }
        if (GwtMaterialUtil.hasСhildren(element)) {
            GwtMaterialUtil.getNodeChildren(element).forEach(child -> process((Element) child));
        }
    }
}
