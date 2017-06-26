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
import com.google.gwt.regexp.shared.MatchResult;
import com.google.gwt.regexp.shared.RegExp;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Widget;
import gwt.material.design.client.base.MaterialWidget;
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.WavesType;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialListBox;
import gwt.material.design.client.ui.MaterialNavBar;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialTab;
import gwt.material.design.client.ui.MaterialTabItem;
import gwt.material.design.client.ui.MaterialTitle;
import gwt.material.design.client.ui.html.UnorderedList;
import org.jboss.errai.ioc.client.container.IOC;
import org.jboss.errai.ui.shared.Visit;
import org.jboss.errai.ui.shared.VisitContext;
import org.jboss.errai.ui.shared.VisitContextMutable;
import org.jboss.errai.ui.shared.Visitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;


/**
 * @author Dmitrii Tikhomirov <chani@me.com>
 *         Created by treblereel on 3/21/17.
 */
public class GwtMaterialUtil {
    private static final MaterialWidgetFactoryHelper helper = IOC.getBeanManager().lookupBean(MaterialWidgetFactoryHelper.class).getInstance();
    //private static  MaterialWidgetFactoryHelper helper ;
    private static final String HTML_VOID_TAG_PATTERN = "<([m,M]aterial-\\w*|div)(\"[^\"]*\"|[^'\">])*/>";
    //private static final String HTML_VOID_TAG_PATTERN = "<(\\w*)(\"[^\"]*\"|[^'\">])*/>";
    private static final String DATA_FIELD = "data-field";
    private static final String[] tag_attr_white_list = {"href", "style"};

    public static final Logger logger = LoggerFactory.getLogger(GwtMaterialUtil.class);

    static Class primitiveToBoxed(Class clazz) {
        if (clazz.equals(boolean.class)) {
            return Boolean.class;
        } else if (clazz.equals(double.class)) {
            return Double.class;
        } else if (clazz.equals(int.class)) {
            return Integer.class;
        }
        return clazz;
    }

    /**
     * Indicates the type of attribute a data field was discovered from.
     */
    public enum AttributeType {
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

    public static class TaggedElement {
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

    public static class Parent {

        private Optional<Widget> parent;

        public Parent(Optional<Widget> parent) {
            this.parent = parent;
        }

        public Optional<Widget> getParent() {
            return parent;
        }
    }

    public static VisitContext<TaggedElement> getElementByDataField(Element parserDiv, String lookup) {
        logger.debug("getElementByDataField [" + lookup + "] ");
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

    public static Element getElementByAttribute(String html, String attrName, String value) {
        Element parserDiv = DOM.createDiv();
        parserDiv.setInnerHTML(html);

        VisitContext<TaggedElement> result = Visit.depthFirst(parserDiv, new Visitor<TaggedElement>() {
            @Override
            public boolean visit(final VisitContextMutable<TaggedElement> context, final Element element) {
                if (element.hasAttribute(attrName) && element.getAttribute(attrName).equals(value)) {
                    context.setResult(new TaggedElement(AttributeType.DATA_FIELD, element));
                    return false;
                }
                return true;
            }
        });
        if (result.getResult() != null)
            return result.getResult().getElement();
        else
            return null;
    }

    public static boolean hasСhildren(Element elm) {
        if (elm != null && elm.getChildCount() != 0) {
            return true;
        }
        return false;
    }

    public static List<Node> getNodeChildren(Node node) {
        List<Node> result = new LinkedList<>();
        for (int i = 0; i < node.getChildNodes().getLength(); i++) {
            Node child = node.getChildNodes().getItem(i);
            if (child.getNodeType() == Node.ELEMENT_NODE || child.getNodeType() == Node.TEXT_NODE) {
                result.add(child);
            }
        }
        return result;
    }

    public static boolean hasDataField(Element elm) {
        return elm.hasAttribute(DATA_FIELD);
    }

    public static boolean isMaterialWidget(Element element) {
        if (element.getTagName().toLowerCase().startsWith("material")) {
            return true;
        }
        return false;
    }

    public static String getDataFieldValue(Element elm) {
        return elm.getAttribute(DATA_FIELD);
    }

    public static Widget getDataFieldedWidget(Element element, Map<String, Widget> dataFieldElements) {
        return dataFieldElements.get(getDataFieldValue(element));
    }

    public Boolean isMaterialWidget(String tag) {
        if (helper.isWidgetSupported(tag)) {
            return true;
        }
        return false;
    }

    public boolean isMaterialWidget(Element element, Map<String, Widget> dataFieldElements) {
        if (isMaterialWidget(element.getTagName())) {
            return true;
        } else if (!GwtMaterialUtil.getAttributesByName(element, "data-field").equals("")) {
            String id = GwtMaterialUtil.getAttributesByName(element, "data-field");
            if (dataFieldElements.containsKey(id) && dataFieldElements.get(id).getClass().getSimpleName().toLowerCase().startsWith("material")) {
                return true;
            }
        }
        return false;
    }


    public static String closeVoidTags(String html) {
        RegExp regExp = RegExp.compile(HTML_VOID_TAG_PATTERN, "g");
        for (MatchResult matcher = regExp.exec(html); matcher != null; matcher = regExp.exec(html)) {
            String tag = matcher.getGroup(0);
            int index = tag.lastIndexOf("/");
            String mark = " self-closed=\"true\">";
            String tagFixed = tag.substring(0, index).trim() + mark;
            String tagName = matcher.getGroup(1);
            html = html.replace(tag, tagFixed + "</" + tagName + ">");
        }
        return html;
    }

    public static void test(Object instance) {
        logger.warn(" test " + instance.getClass().getSimpleName());
    }

    public static String getTag(Element elm) {
        return elm.getTagName().toLowerCase().replaceAll("-", "");
    }

    public static void copyWidgetAttrsAndSetProperties(Element e, Widget obj) {
        String tag = obj.getClass().getSimpleName();
        java.util.Optional<MaterialWidgetDefinition> materialWidgetDefinition = helper.getMaterialWidgetDefinition(tag);
        copyWidgetAttrsAndSetProperties(e, obj, materialWidgetDefinition);
    }

    public static void beforeTemplateInitInvoke(Element root, String content, Map<String, Widget> templateFieldsMap) {
        logger.warn("beforeTemplateInitInvoke " + root.getInnerHTML());
        logger.warn("beforeTemplateInitInvoke html " + content);

        if (hasСhildren(root)) {
            new GwtMaterialPreInit(root, content, templateFieldsMap);
        }
    }

    private static class Context {
        private Element parent;

        public Context(Element parent) {
            this.parent = parent;
        }

    }

    public static void addWidgetToParent(Widget parent, Widget child) {
        if (parent.getClass().equals(MaterialListBox.class)) {
            GwtMaterialUtil.addOptionToListBox((MaterialListBox) parent, (gwt.material.design.client.ui.html.Option) child);
        } else if (parent.getClass().equals(MaterialTab.class)) {
            GwtMaterialUtil.addWidgetItemToUnorderedList((MaterialTab) parent, child);
        } else if (parent.getClass().equals(MaterialNavBar.class)) {
            GwtMaterialUtil.addWidgetToMaterialNavBar(parent, child);
        } else {
            ((MaterialWidget) parent).add(child);
        }
    }


    public static void afterTemplateInitInvoke(Element root, String content, Map<String, Widget> templateFieldsMap) {
        logger.warn("afterTemplateInitInvoke " + root.getInnerHTML());

        new GwtMaterialPostInit(root, content, templateFieldsMap);
    }

    public static void warn(String s) {
        logger.warn(s);
    }


    public static void addMeterialDataFieldsToMap(Map<String, Widget> map) {
        map.put("id_1", new MaterialLabel("OLOLO"));
        map.put("id_2", new MaterialButton("OLOLO____"));
        map.put("id_3", new MaterialTitle("OLOLOZZZZZZ"));


        MaterialTab tab = new MaterialTab();


        MaterialTabItem t1 = new MaterialTabItem();
        t1.setWaves(WavesType.ORANGE);
        MaterialLink l1 = new MaterialLink();
        l1.setText("Tab 1");
        l1.setHref("#tab1");
        l1.setTextColor(Color.AMBER);
        t1.add(l1);
        tab.add(t1);

        MaterialTabItem t2 = new MaterialTabItem();
        t2.setWaves(WavesType.ORANGE);
        MaterialLink l2 = new MaterialLink();
        l2.setText("Tab 2");
        l2.setHref("#tab2");
        l2.setTextColor(Color.AMBER);
        t2.add(l2);
        tab.add(t2);

        MaterialTabItem t3 = new MaterialTabItem();
        t3.setWaves(WavesType.ORANGE);
        MaterialLink l3 = new MaterialLink();
        l3.setText("Tab 3");
        l3.setHref("#tab3");
        l3.setTextColor(Color.AMBER);
        t3.add(l3);
        tab.add(t3);

        MaterialTabItem t4 = new MaterialTabItem();
        t4.setWaves(WavesType.ORANGE);
        MaterialLink l4 = new MaterialLink();
        l4.setText("Tab 4");
        l4.setHref("#tab4");
        l4.setTextColor(Color.AMBER);
        t4.add(l4);
        tab.add(t4);


        MaterialRow row = new MaterialRow();

        MaterialColumn c1 = new MaterialColumn();
        c1.setId("tab1");
        c1.setGrid("s12");
        c1.add(new MaterialLabel("C1"));
        row.add(c1);


        MaterialColumn c2 = new MaterialColumn();
        c2.setId("tab2");
        c2.setGrid("s12");
        c2.add(new MaterialLabel("C2"));
        row.add(c2);

        MaterialColumn c3 = new MaterialColumn();
        c3.setId("tab3");
        c3.setGrid("s12");
        c3.add(new MaterialLabel("C3"));
        row.add(c3);

        MaterialColumn c4 = new MaterialColumn();
        c4.setId("tab4");
        c4.setGrid("s12");
        c4.add(new MaterialLabel("C4"));


        map.put("id_4", tab);
        map.put("id_5", row);
    }

    public static void copyWidgetAttrsAndSetProperties(Element e, Widget obj, Optional<MaterialWidgetDefinition> materialWidgetDefinition) {
        List<Node> attrToRemove = new ArrayList<>();
        if (materialWidgetDefinition.isPresent()) {
            JsArray<Node> nodes = getAttributes(e);
            for (int t = 0; t < nodes.length(); t++) {
                Node n = nodes.get(t);
                if (materialWidgetDefinition.get().getMethods().containsKey(n.getNodeName())) {
                    MaterialMethodDefinition definition = materialWidgetDefinition.get().getMethods().get(n.getNodeName());
                    Object value = parseAttrValue(definition.getParameter(), n.getNodeValue());
                    if (value != null) {
                        definition.getFunction().accept(obj, value);
                        attrToRemove.add(n);
                    }
                } else if (n.getNodeName().toLowerCase().equals("addstylenames")) {
                    if (!n.getNodeValue().trim().equals("")) {
                        obj.addStyleName(n.getNodeValue());
                    }
                    attrToRemove.add(n);
                } else if (n.getNodeName().toLowerCase().equals("self-closed")) {
                    attrToRemove.add(n);
                } else {
                    obj.getElement().setAttribute(n.getNodeName(), n.getNodeValue());
                }
            }
            //remove attrs
            if (attrToRemove.size() > 0) {
                attrToRemove.stream().forEach(r -> {
                    if (!isWhiteListedAttr(r.getNodeName()))
                        obj.getElement().removeAttribute(r.getNodeName());
                });
            }
        }

    }

    private static boolean isWhiteListedAttr(String attr) {
        for (String a : tag_attr_white_list) {
            if (a.equals(attr)) {
                return true;
            }
        }
        return false;
    }

    static Object parseAttrValue(Class clazz, String value) {
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

    public static String getAttributesByName(Element elem, String name) {
        JsArray<Node> attributes = getAttributes(elem);
        if (attributes == null) {
            return "";
        }

        for (int i = 0; i < attributes.length(); i++) {
            final Node node = attributes.get(i);
            String attributeName = node.getNodeName();
            String attributeValue = node.getNodeValue();
            if (attributeName.equals(name)) {
                return attributeValue;
            }
        }
        return "";
    }

    public static void compositeComponentReplace(final String componentType, final String templateFile, final Element field,
                                                 final Map<String, Element> dataFieldElements, final String fieldName) {
        try {
            compositeComponentReplace(componentType, templateFile, field, dataFieldElements, fieldName);
        } catch (final Throwable t) {
            throw new RuntimeException("There was an error initializing the @DataField " + fieldName + " in the @Templated "
                    + componentType + ": " + t.getMessage(), t);
        }
    }

    public static native JsArray<Node> getAttributes(Element elem) /*-{
        return elem.attributes;
    }-*/;

    public static native void attach(Widget x) /*-{
        x.@com.google.gwt.user.client.ui.Widget::onAttach()();
    }-*/;

    public static native void add(Widget parent, Widget child) /*-{
        parent.@gwt.material.design.client.base.MaterialWidget::add(Lcom/google/gwt/user/client/ui/Widget;)(child);
    }-*/;

    public static native void addOptionToListBox(MaterialListBox x, gwt.material.design.client.ui.html.Option s) /*-{
        x.@gwt.material.design.client.ui.MaterialListBox::add(Lgwt/material/design/client/ui/html/Option;)(s);
    }-*/;

    public static native void initializeMaterialTab(MaterialTab x) /*-{
        x.@gwt.material.design.client.ui.MaterialTab::initialize()();
    }-*/;

    public static native void addWidgetItemToMaterialTab(MaterialTab x, Widget s) /*-{
        x.@gwt.material.design.client.ui.MaterialTab::add(Lcom/google/gwt/user/client/ui/Widget;)(s);
    }-*/;

    public static native void addWidgetItemToUnorderedList(UnorderedList x, Widget s) /*-{
        x.@gwt.material.design.client.ui.html.UnorderedList::add(Lcom/google/gwt/user/client/ui/Widget;)(s);
    }-*/;

    public static native void addWidgetToMaterialNavBar(Widget x, Widget s) /*-{
        x.@gwt.material.design.client.ui.MaterialNavBar::add(Lcom/google/gwt/user/client/ui/Widget;)(s);
    }-*/;

}
