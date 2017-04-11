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

package org.jboss.errai.material.client.local;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Widget;
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
    private Map<String, Widget> dataFieldElements;
    private String originalTemplate;
    private String templateFileName;
    private String rootField;

    private final MaterialWidgetFactory widgetFactory = IOC.getBeanManager().lookupBean(MaterialWidgetFactory.class).getInstance();
    private static final Logger logger = LoggerFactory.getLogger(GwtMaterialBootstrap.class);

    private GwtMaterialBootstrap(Element composite, String templateContents, String templateFileName, String rootField, Map<String, Widget> dataFieldElements) {
        this.composite = composite;
        this.originalTemplate = templateContents;
        this.templateFileName = templateFileName;
        this.rootField = rootField;
        this.dataFieldElements = dataFieldElements;
    }

    private void processTemplate() {
        processTemplateMaterialSelfClosedTags();
        processTemplateMaterialTags();
    }

    private void processTemplateMaterialSelfClosedTags() {
        Element origin;
        Element parserDiv = DOM.createDiv();
        String parsed = GwtMaterialUtil.closeVoidTags(originalTemplate);
        parserDiv.setInnerHTML(parsed);
        if (rootField != null && !rootField.trim().isEmpty()) {
            final VisitContext<TaggedElement> context = getElementByAttribute(parserDiv, rootField);
            if (context.getResult().getElement() != null) {
                originalComposite = context.getResult().getElement();
            }
            origin = DOM.createDiv();
            origin.appendChild(originalComposite);
        } else {
            origin = parserDiv;
        }
        originalComposite = origin;
        compareComposite(composite, origin);
    }

    private VisitContext<TaggedElement> getElementByAttribute(Element parserDiv, String lookup) {
        return Visit.depthFirst(parserDiv, new Visitor<TaggedElement>() {
            @Override
            public boolean visit(final VisitContextMutable<TaggedElement> context, final Element element) {
                for (final AttributeType attrType : AttributeType.values()) {
                    final String attrName = attrType.getAttributeName();
                    final TaggedElement existingCandidate = context.getResult();
                    if (element.hasAttribute(attrName) && element.getAttribute(attrName).equals(lookup)
                            && (existingCandidate == null || existingCandidate.getAttributeType().ordinal() < attrType.ordinal())) {
                        context.setResult(new TaggedElement(attrType, element));
                    }
                }
                return true;
            }
        });
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

    private void replaceMaterialWidget(MaterialWidget parent, Element element) {
        if (isUnbundedMaterialWidget(element)) {
            MaterialWidget materialWidget = processMaterialWidget(element);
            if (element.hasParentElement())
                element.removeFromParent();
            parent.add(materialWidget);
        } else if (isDataFieldedMaterialWidget(element)) {
            Widget widget = getWiredMaterialWidgetDataFieldName(element).get();
            if (isMaterialWidget(widget.getClass().getSimpleName())) {
                GwtMaterialUtil.copyWidgetAttrsAndSetProperties(element, (MaterialWidget) widget);
                parent.add(widget);
                String dataFieldName = GwtMaterialUtil.getAttributesByName(element, "data-field");
                if (originalComposite != null) {
                    final VisitContext<TaggedElement> context = getElementByAttribute(originalComposite, dataFieldName);
                    if (widget.getElement().getInnerHTML().equals("") && !context.getResult().getElement().getInnerHTML().equals("")) {
                        List<Node> compChildren = getNodeChildren(context.getResult().getElement());
                        for (Node n : compChildren) {
                            replaceMaterialWidget((MaterialWidget) widget, ((Element) n));
                        }
                    }
                }
            }
        }
    }

    private void process(Element parent, Element element) {
        if (isUnbundedMaterialWidget(element)) {
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
        } else if (isDataFieldedMaterialWidget(element)) {
            Widget widget = getWiredMaterialWidgetDataFieldName(element).get();
            if (isMaterialWidget(widget.getClass().getSimpleName())) {
                GwtMaterialUtil.copyWidgetAttrsAndSetProperties(widget.getElement() ,(MaterialWidget) widget);
                String dataFieldName = GwtMaterialUtil.getAttributesByName(element, "data-field");
                if (originalComposite != null) {
                    final VisitContext<TaggedElement> context = getElementByAttribute(originalComposite, dataFieldName);
                    if (!widget.getElement().getInnerHTML().equals("") && !context.getResult().getElement().getInnerHTML().equals("")) {
                        widget.getElement().setInnerHTML(context.getResult().getElement().getInnerHTML());
                    }
                }
            }
        } else {
            if (hasСhildren(element)) {
                for (int i = 0; i < element.getChildNodes().getLength(); i++) {
                    Element child = (Element) element.getChildNodes().getItem(i);
                    process(element, child);
                }
            }
        }
    }

    private void processMaterialDataFieldedWidget(Widget widget) {
        List<Node> attrToRemove = new ArrayList<>();
        JsArray<Node> nodes = GwtMaterialUtil.getAttributes(widget.getElement());
        for (int t = 0; t < nodes.length(); t++) {
            Node n = nodes.get(t);
            Optional<MaterialMethodDefinition> methodDefinition = widgetFactory.getMethodDefIfExist(widget.getClass().getSimpleName(), n.getNodeName());
            if (methodDefinition.isPresent()) {
                if (!n.getNodeName().equals("type")) {
                    methodDefinition.get().getFunction().accept(widget, GwtMaterialUtil.parseAttrValue(methodDefinition.get().getParameter(), n.getNodeValue()));
                    attrToRemove.add(n);
                }
            }
        }
        //remove attrs
        if (attrToRemove.size() > 0) {
            attrToRemove.stream().forEach(r -> {
                widget.getElement().removeAttribute(r.getNodeName());
            });
        }
    }

    private boolean isUnbundedMaterialWidget(Element elm) {
        if (elm.getTagName() != null && isMaterialWidget(elm.getTagName()) && GwtMaterialUtil.getAttributesByName(elm, "data-field").equals("")) {
            return true;
        }
        return false;
    }

    private boolean isDataFieldedMaterialWidget(Element elm) {
        if (getWiredMaterialWidgetDataFieldName(elm).isPresent()) {
            Widget widget = getWiredMaterialWidgetDataFieldName(elm).get();
            if (isMaterialWidget(widget.getClass().getSimpleName())) {
                return true;
            }
        }
        return false;
    }


    private boolean isMaterialWidget(String tag) {
        return tag.toLowerCase().startsWith("material");
    }

    private boolean hasСhildren(Element elm) {
        if (elm.getChildCount() != 0) {
            return true;
        }
        return false;
    }


    // check if widget is data-fielded
    private Optional<Widget> getWiredMaterialWidgetDataFieldName(Element elm) {
        String id = GwtMaterialUtil.getAttributesByName(elm, "data-field");
        if (id.equals("") || !dataFieldElements.containsKey(id) || !dataFieldElements.get(id).getClass().getSimpleName().toLowerCase().startsWith("material")) {
            return Optional.empty();
        }
        return Optional.of(dataFieldElements.get(id));
    }

    private MaterialWidget replace(Element element) {
        if (!GwtMaterialUtil.getAttributesByName(element, "data-field").equals("")) {
            String id = GwtMaterialUtil.getAttributesByName(element, "data-field");
            assert dataFieldElements.containsKey(id);
            if (dataFieldElements.containsKey(id) && dataFieldElements.get(id).getClass().getSimpleName().toLowerCase().startsWith("material")) {
                MaterialWidget candidate = (MaterialWidget) dataFieldElements.get(id);
                GwtMaterialUtil.copyWidgetAttrsAndSetProperties(element, candidate);
                String dataFieldName = GwtMaterialUtil.getAttributesByName(element, "data-field");
                final VisitContext<TaggedElement> context = getElementByAttribute(originalComposite, dataFieldName);
                List<Node> compChildren = getNodeChildren(context.getResult().getElement());
                for (Node n : compChildren) {
                    replaceMaterialWidget(candidate, ((Element) n));
                }
                return candidate;
            } else if (dataFieldElements.containsKey(id) && !dataFieldElements.get(id).getClass().getSimpleName().toLowerCase().startsWith("material")) {
                throw new RuntimeException("Widget has data-field but it's not a material  " + element.getTagName());
            }
        } else {
            Optional<MaterialWidget> ifExist = widgetFactory.invoke(element.getTagName());
            if (ifExist.isPresent()) {
                MaterialWidget candidate = ifExist.get();
                GwtMaterialUtil.copyWidgetAttrsAndSetProperties(element, candidate);
                return candidate;
            }
        }
        throw new RuntimeException("No such MaterialWidget found  " + element.getTagName() + " " + GwtMaterialUtil.getAttributesByName(element, "data-field"));
    }


    public static void processTemplate(Element composite, String templateContents, String templateFileName, String rootField, Map<String, Widget> dataFieldElements) {
        new GwtMaterialBootstrap(composite, templateContents, templateFileName, rootField, dataFieldElements).processTemplate();
    }


}


