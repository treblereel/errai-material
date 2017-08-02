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
import gwt.material.design.client.constants.Position;
import gwt.material.design.client.ui.MaterialTooltip;
import org.jboss.errai.ioc.client.container.IOC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.jboss.errai.material.client.local.GwtMaterialUtil.*;

/**
 * @author Dmitrii Tikhomirov <chani@me.com>
 *         Created by treblereel on 6/23/17.
 */
public class GwtMaterialPostInit {
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    private final String templateFilename;

    private final Map<String, Widget> templateFieldsMap;

    private final MaterialWidgetFactoryHelper helper = IOC.getBeanManager().lookupBean(MaterialWidgetFactoryHelper.class).getInstance();

    private Element original = DOM.createDiv();

    private Widget root;

    private final Map<String, Widget> temp = new HashMap();


    GwtMaterialPostInit(final Element elm, final String content, final String templateFilename,  Map<String, Widget> map) {
        this.templateFieldsMap = map;
        this.templateFilename = templateFilename;

        map.forEach((k, v) -> {
            temp.put(k, v);
        });
        root = getDataFieldedWidget(elm, temp);
        root.getElement().setInnerHTML(elm.getInnerHTML());
        this.original = elm;
        process(root.getElement());
        if (root.getElement().hasAttribute(ROOT_ELEMENT)) {
            elm.removeAllChildren();
            getNodeChildren(root.getElement().hasAttribute(HTML_FRAGMENT) == true ? root.getElement() :
                    root.getElement().getFirstChildElement()).forEach(c -> elm.appendChild(c));
        }
    }


    private Result process(Element element) {
        List<Result> list = new LinkedList<>();
        Result result = new Result();
        if (element.getNodeType() == 1) {
            if (GwtMaterialUtil.isMaterialWidget(element, temp)) {
                Widget child = getWidget(element);

                if (GwtMaterialUtil.hasСhildren(element)) {
                    for (Node c : getNodeChildren(element)) {
                        list.add(process((Element) c));
                    }
                }
                element.removeAllChildren();
                list.forEach(l -> {
                    if (l.getWidget() != null) {
                        addWidgetToParent(child, l.getWidget(), temp);
                    } else if (l.getElement() != null) {
                        child.getElement().appendChild(l.getElement());
                    }else if (l.getNode() != null) {
                        child.getElement().setInnerText(l.getNode().getNodeValue());
                    }
                });

                copyWidgetAttrsAndSetProperties(element, child);

                result.setWidget(child);
                result.setElement(element);
            } else {
                if (hasDataField(element)) {
                    result.setWidget(templateFieldsMap.get(getDataFieldValue(element)));
                }
                result.setElement(element);
                if (GwtMaterialUtil.hasСhildren(element)) {
                    for (Node c : getNodeChildren(element)) {
                        list.add(process((Element) c));
                    }
                }

                list.forEach(l -> {
                    if (l.getWidget() != null) {
                        if (result.getWidget() != null) {
                            element.replaceChild(result.getWidget().getElement(), result.getElement());
                        }
                    }
                });
            }
        }else if(element.getNodeType() == 3) {
            if(!isStringBlank(element.getInnerText())){
                result.setNode(element);
            }
        }

        return result;
    }

    private Widget getWidget(Element element) {
        if (hasDataField(element)) {
            String id = GwtMaterialUtil.getDataFieldValue(element);
            Widget candidate = templateFieldsMap.get(id);
            if(candidate == null){
                throw new IllegalArgumentException("There is no widget with data-field=["+id+"] declareted in template " + templateFilename);
            }
            candidate.getElement().setAttribute(DATA_FIELD, id);
            return candidate;
        } else {
            Optional<Tuple<Widget, Boolean>> maybeExist = helper.invoke(element);
            if (maybeExist.isPresent()) {
                if (maybeExist.get().getValue()) {
                    Widget candidate = maybeExist.get().getKey();
                    return candidate;
                    // this widget is material but it doesn't extends MaterialWidget
                } else {
                    Widget candidate = maybeExist.get().getKey();
                    return candidate;
                }
            } else if (getTag(element).equals("materialtooltip")) { //TODO if data-field
                return processMaterialTooltip(element);
            }
        }
        throw new RuntimeException("Can't find widget " + element.getTagName() + " in template " + templateFilename);
    }

    private Widget processMaterialTooltip(Element element) {
        if (GwtMaterialUtil.hasСhildren(element) && getNodeChildren(element, Node.ELEMENT_NODE).size() == 1) {
            Widget widget = getWidget(element.getFirstChildElement());
            MaterialTooltip materialTooltip = new MaterialTooltip(widget);
            if (element.hasAttribute("text")) {
                materialTooltip.setText(element.getAttribute("text"));
            }
            if (element.hasAttribute("tooltipHTML")) {
                materialTooltip.setHtml(element.getAttribute("tooltipHTML"));
            }
            if (element.hasAttribute("delayMs")) {
                materialTooltip.setDelayMs(Integer.parseInt(element.getAttribute("delayMs")));
            }
            if (element.hasAttribute("position")) {
                materialTooltip.setPosition((Position) GwtMaterialUtil.parseAttrValue(Position.class, element.getAttribute("position")));
            }
            element.removeAllChildren(); // prevent iterating child twice
            return widget;
        }
        throw new IllegalStateException("MaterialTooltip must contain only one child widget");

    }

}
