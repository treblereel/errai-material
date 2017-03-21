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

package org.jboss.errai.polymer.shared;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;
import com.google.gwt.user.client.ui.Widget;
import org.jboss.errai.ioc.client.container.IOC;

import java.util.Map;
import java.util.Optional;

/**
 * @author Dmitrii Tikhomirov <chani@me.com>
 *
 * Created by treblereel on 3/8/17.
 */
public class GwtMaterialBootstrap {

    private Element composite;
    private Map<String, Element> dataFieldElements;
    private Map<String, String> datafieldsInnerHtml;

    private final MaterialWidgetFactory widgetFactory = IOC.getBeanManager().lookupBean(MaterialWidgetFactory.class).getInstance();

    private GwtMaterialBootstrap(Element composite, Map<String, Element> dataFieldElements, Map<String, String> datafieldsInnerHtml) {
        this.composite = composite;
        this.datafieldsInnerHtml = datafieldsInnerHtml;
        this.dataFieldElements = dataFieldElements;
    }

    private void processTemplate() {
        process(composite);
    }

    private Element process(Element element) {
        if (element.getChildCount() != 0) {
            for (int i = 0; i < element.getChildNodes().getLength(); i++) {
                Element child = (Element) element.getChildNodes().getItem(i);
                if (child.getChildNodes().getLength() > 0) {
                    process(child);
                    if (child.getTagName() != null) {
                        if (child.getTagName().toLowerCase().contains("material")) {
                            if (child.getAttribute("data-field").equals("")) {
                                replace(child);
                            } else {
                                Element datafielded = dataFieldElements.get(child.getAttribute("data-field"));
                                replace(child, datafielded);
                            }
                        }
                    }
                } else {
                    if (child.getTagName() != null) {
                        if (child.getTagName().toLowerCase().contains("material")) {
                            if (child.getAttribute("data-field").equals("")) {
                                replace(child);
                            } else {
                                Element datafielded = dataFieldElements.get(child.getAttribute("data-field"));
                                replace(child, datafielded);
                            }
                        } else {
                            if (!child.getAttribute("data-field").equals("") && child.getInnerHTML().trim().equals("")) {
                                Optional<String> opt = checkDataFieldActivateWidget(child);
                                if (opt.isPresent()) {
                                    child.setInnerHTML(opt.get());
                                    process(child);
                                }
                            }
                        }
                    }
                }
            }
        } else {
            replace(element);
        }
        return element;
    }

    private Optional<String> checkDataFieldActivateWidget(Element elm) {
        if (!elm.getAttribute("data-field").equals("")) {
            return Optional.of(getElementContentByDataField(elm));
        } else {
            return Optional.empty();
        }
    }

    private Element replace(Element oldElm, Element newElm) {
        final Element parentElement = oldElm.getParentElement();
        Node firstNode = oldElm.getFirstChild();
        while (firstNode != null) {
            if (firstNode != oldElm.getFirstChildElement())
                newElm.appendChild(oldElm.getFirstChild());
            else {
                newElm.appendChild(oldElm.getFirstChildElement());
            }
            firstNode = oldElm.getFirstChild();
        }

        parentElement.replaceChild(newElm, oldElm);
        return newElm;
    }

    private Element replace(Element element) {
        Widget candidate =  widgetFactory.invoke(element.getTagName());
        copyAttrs(element, candidate.getElement());
        return replace(element, candidate.getElement());
    }

    private void copyAttrs(Element e, Element obj) {
        JsArray<Node> nodes = getAttributes(e);
        for (int t = 0; t < nodes.length(); t++) {
            Node n = nodes.get(t);
            obj.setAttribute(n.getNodeName(), n.getNodeValue());
        }
    }

    private String getElementContentByDataField(Element lookup) {
        String result = datafieldsInnerHtml.get(lookup.getAttribute("data-field"));
        if (result != null) {
            return result;
        }
        throw new IllegalArgumentException("there is no such element with data-field=" + lookup.getAttribute("data-field"));
    }

    public static void processTemplate(Element composite, Map<String, Element> dataFieldElements, Map<String, String> datafieldsInnerHtml) {
        new GwtMaterialBootstrap(composite, dataFieldElements, datafieldsInnerHtml).processTemplate();
    }

    public static native JsArray<Node> getAttributes(Element elem) /*-{
        return elem.attributes;
    }-*/;

}
