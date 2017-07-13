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

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.jboss.errai.material.client.local.GwtMaterialUtil.ROOT_ELEMENT;
import static org.jboss.errai.material.client.local.GwtMaterialUtil.addWidgetToParent;
import static org.jboss.errai.material.client.local.GwtMaterialUtil.copyWidgetAttrsAndSetProperties;
import static org.jboss.errai.material.client.local.GwtMaterialUtil.getDataFieldValue;
import static org.jboss.errai.material.client.local.GwtMaterialUtil.getDataFieldedWidget;
import static org.jboss.errai.material.client.local.GwtMaterialUtil.getElementByDataField;
import static org.jboss.errai.material.client.local.GwtMaterialUtil.getNodeChildren;
import static org.jboss.errai.material.client.local.GwtMaterialUtil.hasDataField;


/**
 * @author Dmitrii Tikhomirov <chani@me.com>
 *         Created by treblereel on 6/23/17.
 */
public class GwtMaterialPostInit {
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    private final Map<String, Widget> templateFieldsMap;

    private final MaterialWidgetFactoryHelper helper = IOC.getBeanManager().lookupBean(MaterialWidgetFactoryHelper.class).getInstance();

    private Element original = DOM.createDiv();

    private Widget root;


    GwtMaterialPostInit(final Element elm, final String content, final Map<String, Widget> map) {
        this.templateFieldsMap = map;
        this.original.setInnerHTML(content);
        if (hasDataField(elm)) {
            root = getDataFieldedWidget(elm, templateFieldsMap);
            if (elm.hasAttribute(ROOT_ELEMENT)) {
                GwtMaterialUtil.TaggedElement result = getElementByDataField(original, getDataFieldValue(elm)).getResult();
                if (result != null) {
                    root.getElement().setInnerHTML(result.getElement().getInnerHTML());
                } else {
                    root.getElement().setInnerHTML(content);
                }
                elm.removeAllChildren();
            } else {
                root.getElement().setInnerHTML(getElementByDataField(original, getDataFieldValue(elm)).getResult().getElement().getInnerHTML());
            }
            process(root.getElement());
            if (elm.hasAttribute(ROOT_ELEMENT)) {
                elm.removeAttribute(ROOT_ELEMENT);
                getNodeChildren(root.getElement()).forEach(child -> elm.appendChild(child));
            }

        } else {
            process(elm);
        }
    }


    private Tuple<Widget, Element> process(Element element) {
        List<Tuple<Widget, Element>> list = new LinkedList<>();
        Tuple<Widget, Element> result = new Tuple<>();
        if (element.getNodeType() == 1) {
            if (GwtMaterialUtil.isMaterialWidget(element, templateFieldsMap)) {
                Widget child = getWidget(element);
                if (GwtMaterialUtil.hasСhildren(element)) {
                    for (Node c : getNodeChildren(element)) {
                        list.add(process((Element) c));
                    }
                }
                element.removeAllChildren();
                list.forEach(l -> {
                    if (l.getKey() != null) {
                        addWidgetToParent(child, l.getKey(), templateFieldsMap);
                    } else if (l.getValue() != null) {
                        child.getElement().appendChild(l.getValue());
                    }
                });

                result.setKey(child);
            }
        }
        return result;
    }

    private Widget getWidget(Element element) {
        if (hasDataField(element)) {
            String id = GwtMaterialUtil.getDataFieldValue(element);
            Widget candidate = templateFieldsMap.get(id);
            copyWidgetAttrsAndSetProperties(element, candidate);
            return candidate;
        } else {
            Optional<Tuple<Widget, Boolean>> maybeExist = helper.invoke(element);
            if (maybeExist.isPresent()) {
                if (maybeExist.get().getValue()) {
                    Widget candidate = maybeExist.get().getKey();
                    copyWidgetAttrsAndSetProperties(element, candidate);
                    return candidate;

                }
            }
        }
        throw new RuntimeException("Can't find widget " + element.getTagName());
    }

}
