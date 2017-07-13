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
import com.google.gwt.dom.client.Node;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Widget;
import org.jboss.errai.ioc.client.container.IOC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Optional;

import static org.jboss.errai.material.client.local.GwtMaterialUtil.DATA_FIELD;
import static org.jboss.errai.material.client.local.GwtMaterialUtil.ROOT_ELEMENT;
import static org.jboss.errai.material.client.local.GwtMaterialUtil.copyWidgetAttrsAndSetProperties;
import static org.jboss.errai.material.client.local.GwtMaterialUtil.getDataFieldValue;
import static org.jboss.errai.material.client.local.GwtMaterialUtil.getDataFieldedWidget;
import static org.jboss.errai.material.client.local.GwtMaterialUtil.getElementByDataField;
import static org.jboss.errai.material.client.local.GwtMaterialUtil.getNodeChildren;
import static org.jboss.errai.material.client.local.GwtMaterialUtil.hasDataField;
import static org.jboss.errai.material.client.local.GwtMaterialUtil.needsToBeInitExplicitly;

/**
 * @author Dmitrii Tikhomirov <chani@me.com>
 *         Created by treblereel on 6/23/17.
 */
public class GwtMaterialPreInit {

    private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    private final MaterialWidgetFactoryHelper helper = IOC.getBeanManager().lookupBean(MaterialWidgetFactoryHelper.class).getInstance();

    private Map<String, Widget> templateFieldsMap;

    private Element original;

    GwtMaterialPreInit(Element root, String content, Map<String, Widget> templateFieldsMap) {
        this.templateFieldsMap = templateFieldsMap;
        original = DOM.createDiv();
        original.setInnerHTML(content);
        process(null, root);

    }

    private void process(Widget parent, Element root) {
        if (root.getNodeType() == Node.ELEMENT_NODE) {
            if (helper.isWidgetSupported((root).getTagName())) {
                if (hasDataField(root)) {
                    Widget childAsWidget = getDataFieldedWidget(root, templateFieldsMap);
                    //copyWidgetAttrsAndSetProperties(root, childAsWidget);
                    //TODO
                    //addWidgetToParent(parent, childAsWidget);
                    root.setInnerHTML(getRootContent(root));
                    parent = childAsWidget;
                } else {
                    parent = doMaterialWidget(parent, root);
                }
            }
        }

        for (Node c : getNodeChildren(root)) {
            process(parent, (Element) c);
        }
    }

    private String getRootContent(Element root) {
        if(root.hasAttribute(ROOT_ELEMENT)){
            return root.getInnerHTML();
        }else{
            return getElementByDataField(original, getDataFieldValue(root)).getResult().getElement().getInnerHTML();
        }
    }

    private Widget doMaterialWidget(Widget parent, Element element) {
        Widget candidate = null;
        if (parent == null || needsToBeInitExplicitly(element)) {
            candidate = processMaterialWidget(element);
        }
        return candidate;

    }

    private Widget processMaterialWidget(Element element) {
        Widget candidate;
        Optional<Tuple<Widget, Boolean>> maybeExist = helper.invoke(element);
        if (maybeExist.isPresent()) {
            if (maybeExist.get().getValue()) {
                candidate = maybeExist.get().getKey();
                String id = DOM.createUniqueId();
                element.setAttribute(DATA_FIELD, id);
                templateFieldsMap.put(id, candidate);
            } else {
                throw new RuntimeException("Widget doesn't exist " + element.getTagName());
            }
        } else {
            throw new RuntimeException("Widget doesn't exist " + element.getTagName());
        }
        return candidate;
    }
}
