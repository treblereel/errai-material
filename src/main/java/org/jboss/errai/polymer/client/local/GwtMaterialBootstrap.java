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

package org.jboss.errai.polymer.client.local;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;
import gwt.material.design.client.base.MaterialWidget;
import org.jboss.errai.ioc.client.container.IOC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Map;
import java.util.Optional;

/**
 * @author Dmitrii Tikhomirov <chani@me.com>
 *         <p>
 *         Created by treblereel on 3/8/17.
 */
public class GwtMaterialBootstrap {

    private Element composite;
    private Map<String, Element> dataFieldElements;
    private Map<String, String> datafieldsInnerHtml;

    private final MaterialWidgetFactory widgetFactory = IOC.getBeanManager().lookupBean(MaterialWidgetFactory.class).getInstance();
    private static final Logger logger = LoggerFactory.getLogger(GwtMaterialBootstrap.class);

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
        MaterialWidget candidate = widgetFactory.invoke(element.getTagName());
        copyAttrs(element, candidate);
        return replace(element, candidate.getElement());
    }

    private void copyAttrs(Element e, MaterialWidget obj) {
        if (widgetFactory.getWidgetDefIfExist(e.getTagName()).isPresent()) {


            if (obj.getInitialClasses() != null) {
                StringBuffer sb = new StringBuffer();
                Arrays.stream(obj.getInitialClasses()).forEach(s -> sb.append(s + " "));
                logger.warn("OBJ " + obj.getClass().getSimpleName() + " " + sb.toString());

            }


            JsArray<Node> nodes = getAttributes(e);
            for (int t = 0; t < nodes.length(); t++) {
                Node n = nodes.get(t);
                logger.warn("Node " + obj.getClass() + " " + n.getNodeName() + " " + n.getNodeValue() + " " + widgetFactory.getMethodDefIfExist(e.getTagName(), n.getNodeName()).isPresent());
                if (widgetFactory.getMethodDefIfExist(e.getTagName(), n.getNodeName()).isPresent()) {
                    MaterialMethodDefinition definition = widgetFactory.getMethodDefIfExist(e.getTagName(), n.getNodeName()).get();

                    logger.warn(parseAttrValue(definition.getParameter(), n.getNodeValue()).getClass().getName());
                    definition.getFunction().accept(obj, parseAttrValue(definition.getParameter(), n.getNodeValue()));
                } else {
                    obj.getElement().setAttribute(n.getNodeName(), n.getNodeValue());
                }
              //  obj.getElement().setAttribute(n.getNodeName(), n.getNodeValue()); //? TODO REMOVE

            }
        }
    }

    private Object parseAttrValue(Class clazz, String value) {
        logger.warn(" parse " + clazz + " " + clazz.isEnum() + " " + value);


        if (clazz.equals(Boolean.class) || clazz.equals(boolean.class)) {
            return new Boolean(value);
        } else if (clazz.equals(String.class)) {
            return value;
        } else if (clazz.equals(Double.class) || clazz.equals(double.class)) {
            return new Double(value);
        } else if (clazz.equals(Integer.class) || clazz.equals(int.class)) {
            return new Integer(value);
        } else if (clazz.isEnum()) {
            return EnumSet.allOf(clazz).stream().filter(e -> e.toString().equals(value.toUpperCase())).findFirst().orElse(null);
        }
        throw new IllegalArgumentException();
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
