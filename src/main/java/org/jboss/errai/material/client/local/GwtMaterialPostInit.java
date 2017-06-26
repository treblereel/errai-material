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
import org.jboss.errai.ui.shared.Visit;
import org.jboss.errai.ui.shared.VisitContextMutable;
import org.jboss.errai.ui.shared.Visitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

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

        if (element.getNodeType() == 1 && element.hasAttribute(GwtMaterialPreInit.MATERIAL_ID)) {
            String id = element.getAttribute(GwtMaterialPreInit.MATERIAL_ID);
            element.getParentElement().replaceChild(templateFieldsMap.get(id).getElement(), element);
        }else{
            if(GwtMaterialUtil.hasСhildren(element)) {
                GwtMaterialUtil.getNodeChildren(element).forEach(child -> process((Element) child));
            }
        }
    }
}
