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

package org.jboss.errai.polymer.client.local;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;
import com.google.gwt.regexp.shared.MatchResult;
import com.google.gwt.regexp.shared.RegExp;
import gwt.material.design.client.base.MaterialWidget;
import org.jboss.errai.ioc.client.container.IOC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.EnumSet;
import java.util.List;
import java.util.ArrayList;

/**
 * @author Dmitrii Tikhomirov <chani@me.com>
 *         Created by treblereel on 3/21/17.
 */
public class GwtMaterialUtil {
    private static final MaterialWidgetFactory materialWidgetFactory = IOC.getBeanManager().lookupBean(MaterialWidgetFactory.class).getInstance();

    private static final String HTML_VOID_TAG_PATTERN = "<([m,M]aterial-\\w*)(\"[^\"]*\"|[^'\">])*/>";
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

    public static String closeVoidTags(String html){
        RegExp regExp = RegExp.compile(HTML_VOID_TAG_PATTERN, "g");
        for (MatchResult matcher = regExp.exec(html); matcher != null; matcher = regExp.exec(html)) {
            String tag = matcher.getGroup(0);
            int index  = tag.lastIndexOf("/");
            String mark = " self-closed=\"true\">";
            String tagFixed = tag.substring(0, index).trim() + mark;
            String tagName = matcher.getGroup(1);
            html = html.replace(tag, tagFixed+"</"+tagName+">");
        }
        return html;
    }

    static void copyWidgetAttrsAndSetProperties(Element e, MaterialWidget obj) {
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
                }else if(n.getNodeName().toLowerCase().equals("addstylenames")) {
                    if (!n.getNodeValue().trim().equals("")) {
                        obj.addStyleName(n.getNodeValue());
                    }
                    attrToRemove.add(n);
                }else if(n.getNodeName().toLowerCase().equals("self-closed")){
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
        if(attributes == null){
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

    public static native JsArray<Node> getAttributes(Element elem) /*-{
        return elem.attributes;
    }-*/;

}
