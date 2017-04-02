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
import com.google.gwt.user.client.DOM;
import gwt.material.design.client.base.MaterialWidget;
import gwt.material.design.client.ui.html.Div;
import org.jboss.errai.ioc.client.container.IOC;
import org.jboss.errai.ui.shared.Visit;
import org.jboss.errai.ui.shared.VisitContext;
import org.jboss.errai.ui.shared.VisitContextMutable;
import org.jboss.errai.ui.shared.Visitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @author Dmitrii Tikhomirov <chani@me.com>
 *         <p>
 *         Created by treblereel on 3/8/17.
 */
public class GwtMaterialBootstrap {

    private Element composite;
    private Element originalComposite;
    private Map<String, Element> dataFieldElements;
    private Map<String, String> datafieldsInnerHtml;
    private String originalTemplate;
    private String templateFileName;
    private String rootField;

    private final MaterialWidgetFactory widgetFactory = IOC.getBeanManager().lookupBean(MaterialWidgetFactory.class).getInstance();
    private static final Logger logger = LoggerFactory.getLogger(GwtMaterialBootstrap.class);

    private GwtMaterialBootstrap(Element composite, String templateContents, String templateFileName, String rootField, Map<String, Element> dataFieldElements, Map<String, String> datafieldsInnerHtml) {
        this.composite = composite;
        this.datafieldsInnerHtml = datafieldsInnerHtml;
        this.dataFieldElements = dataFieldElements;
        this.originalTemplate = templateContents;
        this.templateFileName = templateFileName;
        this.rootField = rootField;
    }

    private void processTemplate() {
        processTemplateMaterialSelfClosedTags();
        processTemplateMaterialTags();
    }

    private void processTemplateMaterialSelfClosedTags() {

        Element parserDiv = DOM.createDiv();
        String parsed = GwtMaterialUtil.closeVoidTags(originalTemplate);
        parserDiv.setInnerHTML(parsed);

        if (rootField != null && !rootField.trim().isEmpty()) {
            final VisitContext<TaggedElement> context = Visit.depthFirst(parserDiv, new Visitor<TaggedElement>() {
                @Override
                public boolean visit(final VisitContextMutable<TaggedElement> context, final Element element) {
                    for (final AttributeType attrType : AttributeType.values()) {
                        final String attrName = attrType.getAttributeName();
                        final TaggedElement existingCandidate = context.getResult();
                        if (element.hasAttribute(attrName) && element.getAttribute(attrName).equals(rootField)
                                && (existingCandidate == null || existingCandidate.getAttributeType().ordinal() < attrType.ordinal())) {
                            context.setResult(new TaggedElement(attrType, element));
                        }
                    }
                    return true;
                }
            });


            if (context.getResult().getElement() != null) {
                originalComposite = context.getResult().getElement();
            }
        } else {
            originalComposite = parserDiv;
        }

        Element origin = DOM.createDiv();
        origin.appendChild(originalComposite);

        compareComposite(composite, origin);
    }

    /**
     * Indicates the type of attribute a data field was discovered from.
     */
    private enum AttributeType {
        CLASS("class"),
        ID("id"),
        DATA_FIELD("data-field");

        private final String attributeName;

        AttributeType(final String attributeName) {
            this.attributeName = attributeName;
        }

        public String getAttributeName() {
            return attributeName;
        }
    }

    private static class TaggedElement {
        private final AttributeType attributeType;
        private final Element element;

        public TaggedElement(final AttributeType attributeType, final Element element) {
            this.attributeType = attributeType;
            this.element = element;
        }

        public AttributeType getAttributeType() {
            return attributeType;
        }

        public Element getElement() {
            return element;
        }
    }

    private void compareComposite(Element composite, Element original) {
        List<Node> compChildren = getNodeChildren(composite);
        List<Node> origChildren = getNodeChildren(original);
        boolean rerun = false;

        if (hasСhildren(composite)) {
            for (int i = 0; i < compChildren.size(); i++) {
                Element compChild = (Element) compChildren.get(i);

                if (origChildren.size() > compChildren.size()) {
                    Element origChild = (Element) origChildren.get(i);
                    if (compChild.getNodeType() == Node.ELEMENT_NODE) {
                        if (compChildren.size() == 1 && origChild.hasAttribute("self-closed")) {
                            origChild.removeAttribute("self-closed");
                            insertChildren(composite, compChild);
                            rerun = true;

                            break;
                        } else if (compChildren.size() == 1 && origChild.getTagName() == null) {
                            insertChildren(composite, compChild.getFirstChildElement());
                            rerun = true;
                            break;
                        } else if (hasСhildren(compChild) && origChild.hasAttribute("self-closed")) {
                            origChild.removeAttribute("self-closed");
                            insertChildren(composite, compChild);
                            rerun = true;
                        } else if (!rerun) {
                            if (hasСhildren(compChild)) {
                                compareComposite(compChild, origChild);
                            }
                        }
                    } else {
                        insertChildren(composite.getParentElement(), compChild.getFirstChildElement());
                        rerun = true;
                    }
                } else if (origChildren.size() == compChildren.size()) {
                    Element originChild = (Element) origChildren.get(i);
                    compareComposite(compChild, originChild);
                }
            }
            if (rerun) {
                compareComposite(composite, original);
            }
        }
    }

    private void insertChildren(Element parent, Element child) {
        List<Node> children = getNodeChildren(child);
        child.removeAllChildren();
        Node after = child;
        for (Node elm : children) {
            parent.insertAfter(elm, after);
            after = elm;
        }
    }

    private int getNodeChildrenSize(Node node) {
        return getNodeChildren(node).size();
    }

    private List<Node> getNodeChildren(Node node) {
        List<Node> result = new LinkedList<>();
        for (int i = 0; i < node.getChildNodes().getLength(); i++) {
            Node child = node.getChildNodes().getItem(i);
            if (child.getNodeType() == Node.ELEMENT_NODE) {
                result.add(child);
            }
        }
        return result;
    }

    private void processTemplateMaterialTags() {
        if (composite.getChildCount() != 0) {
            for (int i = 0; i < composite.getChildNodes().getLength(); i++) {
                process(composite, (Element) composite.getChild(i));
            }
        }
    }

    private MaterialWidget processMaterialWidget(Element element) {
        List<MaterialWidget> children = new ArrayList<>();
        if (hasСhildren(element)) {
            for (int i = 0; i < element.getChildNodes().getLength(); i++) {
                Node node = element.getChildNodes().getItem(i);
                if (node != null && node.getNodeType() == 1) {
                    Element elm = (Element) element.getChildNodes().getItem(i);
                    children.add(processMaterialWidget(elm));
                }
            }
        }
        MaterialWidget widget = replace(element);
        for (MaterialWidget w : children) {
            widget.add(w);
        }
        return widget;
    }


    private void process(Element parent, Element element) {
        if (isMaterial(element)) {
            MaterialWidget materialWidget = processMaterialWidget(element);
            Div wrapper = new Div() {
                public Div attach(MaterialWidget widget) {
                    if (!widget.isAttached()) {
                        this.setDataAttribute("material-wrapper", materialWidget.getClass().getSimpleName());
                        try {
                            super.doAttachChildren();
                        } catch (java.lang.IllegalStateException e) {
                            // do nothing
                        }
                    }
                    return this;
                }

                public Div addWidget(MaterialWidget widget) {
                    this.add(widget);
                    attach(widget);
                    return this;
                }
            }.addWidget(materialWidget);
            parent.replaceChild(wrapper.getElement(), element);
        } else {
            if (hasСhildren(element)) {
                for (int i = 0; i < element.getChildNodes().getLength(); i++) {
                    Element child = (Element) element.getChildNodes().getItem(i);
                    process(element, child);
                }
            }
        }
    }

    private boolean isMaterial(Element elm) {
        if (elm.getTagName() != null && elm.getTagName().toLowerCase().contains("material")) {
            return true;
        }
        return false;
    }

    private boolean hasСhildren(Element elm) {
        if (elm.getChildCount() != 0) {
            return true;
        }
        return false;
    }

    private Optional<String> checkDataFieldActivateWidget(Element elm) {
        if (!elm.getAttribute("data-field").equals("")) {
            return Optional.of(getElementContentByDataField(elm));
        } else {
            return Optional.empty();
        }
    }

    private MaterialWidget replace(Element element) {
        MaterialWidget candidate = widgetFactory.invoke(element.getTagName());
        copyAttrs(element, candidate);
        return candidate;
    }

    private void copyAttrs(Element e, MaterialWidget obj) {
        if (widgetFactory.getWidgetDefIfExist(e.getTagName()).isPresent()) {

            JsArray<Node> nodes = getAttributes(e);
            for (int t = 0; t < nodes.length(); t++) {
                Node n = nodes.get(t);
                if (widgetFactory.getMethodDefIfExist(e.getTagName(), n.getNodeName()).isPresent()) {
                    MaterialMethodDefinition definition = widgetFactory.getMethodDefIfExist(e.getTagName(), n.getNodeName()).get();
                    definition.getFunction().accept(obj, parseAttrValue(definition.getParameter(), n.getNodeValue()));
                } else {
                    obj.getElement().setAttribute(n.getNodeName(), n.getNodeValue());
                }
            }
        }
    }

    private Object parseAttrValue(Class clazz, String value) {
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

    public static void processTemplate(Element composite, String templateContents, String templateFileName, String rootField, Map<String, Element> dataFieldElements, Map<String, String> datafieldsInnerHtml) {
        new GwtMaterialBootstrap(composite, templateContents, templateFileName, rootField, dataFieldElements, datafieldsInnerHtml).processTemplate();
    }


    public static String getAttributesByName(Element elem, String name) {
        JsArray<Node> attributes = getAttributes(elem);

        for (int i = 0; i < attributes.length(); i++) {
            final Node node = attributes.get(i);
            String attributeName = node.getNodeName();
            String attributeValue = node.getNodeValue();
            logger.warn("getAttributesByName" + attributeName + " " + attributeValue);

            if (attributeName.equals(name)) {
                return attributeValue;
            }
        }


        return "";
    }


    public static native JsArray<Node> getAttributes(Element elem) /*-{
        return elem.attributes;
    }-*/;

}


