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
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;
import gwt.material.design.client.base.MaterialWidget;
import org.jboss.errai.common.client.ui.ElementWrapperWidget;
import org.jboss.errai.ioc.client.container.IOC;
import org.jboss.errai.ui.shared.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.function.Supplier;

/**
 * @author Dmitrii Tikhomirov <chani@me.com>
 *         Created by treblereel on 3/21/17.
 */
public class GwtMaterialUtil {
    private static final MaterialWidgetFactory materialWidgetFactory = IOC.getBeanManager().lookupBean(MaterialWidgetFactory.class).getInstance();

    private static final String HTML_VOID_TAG_PATTERN = "<([m,M]aterial-\\w*)(\"[^\"]*\"|[^'\">])*/>";
    private static final String DATA_FIELD = "data-field";
    private static final String[] tag_attr_white_list = {"href","style"};

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

    private static boolean hasСhildren(Element elm) {
        if (elm != null && elm.getChildCount() != 0) {
            return true;
        }
        return false;
    }

    private static boolean hasDataField(Element elm) {
        return elm.hasAttribute(DATA_FIELD);
    }

    private static boolean isMaterialWidget(Element element) {
        if (element.getTagName().toLowerCase().startsWith("material")) {
            return true;
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

    public static void copyWidgetAttrsAndSetProperties(Element e, MaterialWidget obj) {

        String tag = obj.getClass().getSimpleName();
        if (materialWidgetFactory.getWidgetDefIfExist(tag).isPresent()) {
            List<Node> attrToRemove = new ArrayList<>();
            JsArray<Node> nodes = getAttributes(e);
            for (int t = 0; t < nodes.length(); t++) {
                Node n = nodes.get(t);
                if (materialWidgetFactory.getMethodDefIfExist(tag, n.getNodeName()).isPresent()) {
                    MaterialMethodDefinition definition = materialWidgetFactory.getMethodDefIfExist(tag, n.getNodeName()).get();
                    Object value = parseAttrValue(definition.getParameter(), n.getNodeValue());
                    if (value != null) {
                        definition.getFunction().accept(obj, value);
                        attrToRemove.add(n);
                    }
                } else if (n.getNodeName().toLowerCase().equals("addstylenames")) {
                    if (!n.getNodeValue().trim().equals("")) {
                        obj.addStyleName(n.getNodeValue());
/*                        obj.getElement().addClassName(n.getNodeValue());
                        logger.warn("addstylenames " + n.getNodeValue());*/
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
                    if(!isWhiteListedAttr(r.getNodeName()))
                        obj.getElement().removeAttribute(r.getNodeName());
                });
            }
        }
    }

    private static boolean isWhiteListedAttr(String attr){
       for(String a: tag_attr_white_list){
           if(a.equals(attr)){
               return true;
           }
       }
       return false;
    }

    public static void copyWidgetAttrsAndSetProperties(Element e, MaterialWidget obj, String tag) {
        if (materialWidgetFactory.getWidgetDefIfExist(tag).isPresent()) {
            List<Node> attrToRemove = new ArrayList<>();
            JsArray<Node> nodes = getAttributes(e);
            for (int t = 0; t < nodes.length(); t++) {
                Node n = nodes.get(t);
                if (materialWidgetFactory.getMethodDefIfExist(tag, n.getNodeName()).isPresent()) {
                    MaterialMethodDefinition definition = materialWidgetFactory.getMethodDefIfExist(tag, n.getNodeName()).get();
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
                    obj.getElement().removeAttribute(r.getNodeName());
                });
            }
        }
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

}
