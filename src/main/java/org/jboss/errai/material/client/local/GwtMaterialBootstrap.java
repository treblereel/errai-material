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

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import gwt.material.design.client.base.MaterialWidget;
import org.jboss.errai.ioc.client.container.IOC;
import org.jboss.errai.ui.shared.VisitContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;


/**
 * @author Dmitrii Tikhomirov <chani@me.com>
 *         <p>
 *         Created by treblereel on 3/8/17.
 */
public class GwtMaterialBootstrap { //TODO is template is null, add hasDataField null check

    private static final String DATA_FIELD = "data-field";

    private Composite component;
    private Element composite;
    private Element template;
    private Element scanRoot;

    final Set<Widget> templateFieldsMap = new HashSet();

    //TODO may contains root field
    private Map<String, Widget> dataFieldElements;
    private String originalTemplate;
    private String templateFileName;
    private String rootField;

    private final MaterialWidgetFactory widgetFactory = IOC.getBeanManager().lookupBean(MaterialWidgetFactory.class).getInstance();
    private final GWTMaterialInitializationContainer container = IOC.getBeanManager().lookupBean(GWTMaterialInitializationContainer.class).getInstance();
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

    private void processTemplateMaterialSelfClosedTags() { //TODO first replace, then attach
        template = DOM.createDiv();
        String parsed = GwtMaterialUtil.closeVoidTags(originalTemplate);
        template.setInnerHTML(parsed);
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
        //logger.warn("begin processing  processTemplateMaterialTags = " + composite.getInnerHTML());

        if (hasСhildren(composite))
            composite.removeAllChildren();

        String content = "";
        scanRoot = findRootByDataField(template, rootField);
        if (hasDataField(composite) && hasDataField(scanRoot.getFirstChildElement()) && scanRoot.getFirstChildElement().getAttribute(DATA_FIELD).equals(composite.getAttribute(DATA_FIELD))) {
            content = scanRoot.getFirstChildElement().getInnerHTML();
        } else {
            content = scanRoot.getInnerHTML();
        }

        composite.setInnerHTML(content);

        //logger.warn("process  processTemplateMaterialTags = " + composite.getInnerHTML());

        getNodeChildren(composite).forEach(c -> {
            process(composite, (Element) c);
        });


    }

    public void process(Element parent, Element element) {
        //logger.warn("process  SimpleWidget = " + parent.getTagName() + " " + element.getTagName());
        if (hasDataField(element)) {
            if (isMaterialWidget(element)) {
                if (dataFieldElements.containsKey(element.getAttribute(DATA_FIELD))) {
                    MaterialWidget widget = (MaterialWidget) dataFieldElements.get(element.getAttribute(DATA_FIELD));
                    processMaterialWidgetWithElementParent(parent, element, widget, false);
                } else {
                    throw new IllegalArgumentException("no such MaterialWidget with data-field = " + element.getAttribute(DATA_FIELD) + " in template " + templateFileName);
                }
            } else {
                getNodeChildren(element).forEach(child -> process(element, (Element) child));
            }
        } else if (element.getTagName().toLowerCase().contains("material")) {
            Optional<MaterialWidget> ifExist = widgetFactory.invoke(element);
            if (ifExist.isPresent()) {
                MaterialWidget widget = ifExist.get();
                processMaterialWidgetWithElementParent(parent, element, widget, true);
            }
        } else {
            //logger.warn("widget is simple " + element.getTagName() + " " + getNodeChildren(element).size());
            if (hasСhildren(element)) {
                getNodeChildren(element).forEach(p -> process(element, (Element) p));
            }
        }

    }

    public void process(MaterialWidget parent, Element element) {
        if (hasDataField(element)) {
            if (isMaterialWidget(element)) {
                if (dataFieldElements.containsKey(element.getAttribute(DATA_FIELD))) {
                    processMaterialWidgetWithMaterialParent(parent, element, (MaterialWidget) dataFieldElements.get(element.getAttribute(DATA_FIELD)), false);
                } else {
                    throw new IllegalArgumentException("no such MaterialWidget with data-field = " + element.getAttribute(DATA_FIELD) + " in template " + templateFileName);
                }
            } else {
                getNodeChildren(element).forEach(child -> process(element, (Element) child));
            }
        } else if (element.getTagName().toLowerCase().contains("material")) {
            Optional<MaterialWidget> ifExist = widgetFactory.invoke(element);
            if (ifExist.isPresent()) {
                processMaterialWidgetWithMaterialParent(parent, element, ifExist.get(), true);
            }
        } else {
            parent.getElement().appendChild(element);
            if (hasСhildren(element)) {
                getNodeChildren(element).forEach(p -> process(p.getParentElement(), (Element) p));
            }
        }
    }

    private void processMaterialWidgetWithElementParent(Element parent, Element element, MaterialWidget widget, Boolean doInit) {
        GwtMaterialUtil.copyWidgetAttrsAndSetProperties(element, widget);
        templateFieldsMap.add(widget);
        if (doInit) {
            container.add(widget);
        }
        parent.replaceChild(widget.getElement(), element);
        getNodeChildren(element).forEach(child -> process(widget, (Element) child));
        //GwtMaterialUtil.copyWidgetAttrsAndSetProperties(element, widget);
    }

    private void processMaterialWidgetWithMaterialParent(MaterialWidget parent, Element element, MaterialWidget widget, Boolean doInit) {
        templateFieldsMap.add(widget);
        if (doInit) {
            parent.add(widget);
        }else{
            parent.getElement().appendChild(widget.getElement());
        }
        getNodeChildren(element).forEach(child -> process(widget, (Element) child));
        GwtMaterialUtil.copyWidgetAttrsAndSetProperties(element, widget);
    }

    private boolean isMaterialWidget(Element element) {
        if (element.getTagName().toLowerCase().startsWith("material")) {
            return true;
        } else if (!GwtMaterialUtil.getAttributesByName(element, "data-field").equals("")) {
            String id = GwtMaterialUtil.getAttributesByName(element, "data-field");
            if (dataFieldElements.containsKey(id) && dataFieldElements.get(id).getClass().getSimpleName().toLowerCase().startsWith("material")) {
                return true;
            }
        }
        return false;
    }

    private boolean hasСhildren(Element elm) {
        if (elm != null && elm.getChildCount() != 0) {
            return true;
        }
        return false;
    }

    private boolean hasDataField(Element elm) {
        return elm.hasAttribute(DATA_FIELD);
    }

    private Element findRootByDataField(Element root, String rootField) {
        if (rootField != null && !rootField.trim().isEmpty()) {
            final VisitContext<GwtMaterialUtil.TaggedElement> context = GwtMaterialUtil.getElementByDataField(root, rootField);
            if (context.getResult().getElement() != null) {
                return context.getResult().getElement();
            } else {
                throw new IllegalArgumentException(" can't find root in template " + templateFileName);
            }
        } else {
            return root;
        }
    }

    public static void processTemplate(Element composite, String templateContents, String templateFileName, String rootField, Map<String, Widget> dataFieldElements) {
        new GwtMaterialBootstrap(composite, templateContents, templateFileName, rootField, dataFieldElements).processTemplate();
    }


}
