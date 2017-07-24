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
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialSideNav;
import org.jboss.errai.ioc.client.container.IOC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Optional;

import static org.jboss.errai.material.client.local.GwtMaterialUtil.*;

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
        this.original = DOM.createDiv();
        this.original.setInnerHTML(content);

        if (hasDataField(root)) {
            GwtMaterialUtil.TaggedElement result = getElementByDataField(original, getDataFieldValue(root)).getResult();
            if (result != null) {
                root.setInnerHTML(result.getElement().getInnerHTML());
            } else {
                root.setInnerHTML(content);
            }
        } else {
            root.setInnerHTML(content);
        }

        processRootField(root);
        getNodeChildren(root).forEach(c -> process(false, (Element) c));
    }

    private void processRootField(Element root) {
        if(!hasDataField(root)){
            String id = DOM.createUniqueId();
            root.setAttribute(DATA_FIELD, id);
            root.setAttribute(ROOT_ELEMENT,"true");
            templateFieldsMap.put(id, new MaterialPanel());
        }else if(!templateFieldsMap.containsKey(getDataFieldValue(root))){
            String id = DOM.createUniqueId();
            root.setAttribute(DATA_FIELD, id);
            root.setAttribute(ROOT_ELEMENT,"true");
            root.setAttribute(HTML_FRAGMENT,"true");
            templateFieldsMap.put(id, new MaterialPanel());
        }

    }

    private void process(Boolean parent, Element root) {
        if (root.getNodeType() == Node.ELEMENT_NODE) {
            if (isMaterialWidget(root, templateFieldsMap)) {
                parent = true;
                if (hasDataField(root)) {
                    Widget childAsWidget = getDataFieldedWidget(root, templateFieldsMap);
                    checkIfMaterialSideNav(childAsWidget);
                } else {
                    doMaterialWidget(parent, root);
                }
            }else{
                parent = false;
            }
        }
        for (Node c : getNodeChildren(root)) {
            process(parent, (Element) c);
        }
    }

    private void checkIfMaterialSideNav(Widget childAsWidget) {
        if(childAsWidget instanceof MaterialSideNav){
            String activator = childAsWidget.getElement().getAttribute("id");
            childAsWidget.getElement().setId("");
            childAsWidget.getElement().setAttribute(ACTIVATOR, activator);
        }
    }

    private Boolean doMaterialWidget(Boolean parent, Element element) {
        Boolean result = false;
        if (parent == false || needsToBeInitExplicitly(getMaterialWidgetFieldValue(element))) {
            result = processMaterialWidget(element);
        }
        return result;

    }

    private Boolean processMaterialWidget(Element element) {
        Boolean result = false;
        Optional<Tuple<Widget, Boolean>> maybeExist = helper.invoke(element);
        if (maybeExist.isPresent()) {
            if (maybeExist.get().getValue()) {
                result = true;
                Widget candidate = maybeExist.get().getKey();
                String id = DOM.createUniqueId();

                candidate.getElement().setAttribute(DATA_FIELD, id);
                element.setAttribute(DATA_FIELD, id);
                checkIfMaterialSideNav(candidate);
                templateFieldsMap.put(id, candidate);
            } else {
                throw new RuntimeException("Widget doesn't exist " + element.getTagName());
            }
        } else {
            throw new RuntimeException("Widget doesn't exist " + element.getTagName());
        }
        return result;
    }

}
