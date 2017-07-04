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
import com.google.gwt.user.client.ui.Widget;
import org.jboss.errai.ioc.client.container.IOC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.jboss.errai.material.client.local.GwtMaterialUtil.addWidgetToParent;
import static org.jboss.errai.material.client.local.GwtMaterialUtil.copyWidgetAttrsAndSetProperties;
import static org.jboss.errai.material.client.local.GwtMaterialUtil.getNodeChildren;
import static org.jboss.errai.material.client.local.GwtMaterialUtil.hasDataField;
import static org.jboss.errai.material.client.local.GwtMaterialUtil.isMaterialWidget;


/**
 * @author Dmitrii Tikhomirov <chani@me.com>
 *         Created by treblereel on 6/23/17.
 */
public class GwtMaterialPostInit {
    Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    private Map<String, Widget> templateFieldsMap;

    private final MaterialWidgetFactoryHelper helper = IOC.getBeanManager().lookupBean(MaterialWidgetFactoryHelper.class).getInstance();

    private String content;


    GwtMaterialPostInit(Element root, String content, Map<String, Widget> templateFieldsMap) {
        this.templateFieldsMap = templateFieldsMap;
        this.content = content;

        process(root);
        cleanup(root);
    }

    //TODO FIX IT
    private void cleanup(Element root) {
        if (GwtMaterialUtil.hasСhildren(root)) {
            for (Node c : getNodeChildren(root)) {
                if (c.getNodeType() == Node.ELEMENT_NODE && isMaterialWidget((Element) c)) {
                    root.removeChild(c);
                } else {
                    cleanup((Element) c);
                }
            }
        }

    }

    private Tuple<Widget, Element> process(Element element) {

        if (element.getNodeType() == 1){
            logger.warn("                                  process " + element.getTagName());

            logger.warn(" supported ? " + element.getTagName() + " " + GwtMaterialUtil.isMaterialWidget(element, templateFieldsMap));
        }


        List<Tuple<Widget, Element>> list = new LinkedList<>();

        Tuple<Widget, Element> result = new Tuple<>();
        if (element.getNodeType() == 1 && GwtMaterialUtil.isMaterialWidget(element)) {
            Widget child = getWidget(element);

            logger.warn("                                  process detail isMaterialWidget" + element.getTagName() + " " + child.getClass().getSimpleName());


            if (GwtMaterialUtil.hasСhildren(element)) {
                for (Node c : getNodeChildren(element)) {
                    list.add(process((Element) c));
                }
            }


            list.forEach(l -> {
                if (l.getKey() != null) {
                    addWidgetToParent(child, l.getKey());
                } else if (l.getValue() != null) {
                    child.getElement().appendChild(l.getValue());
                }
            });

            result.setKey(child);

/*
        } else if (element.getNodeType() == 1 && hasDataField(element) && GwtMaterialUtil.isMaterialWidget(element, templateFieldsMap)) {
            Widget child = getWidget(element);

            logger.warn("                                  process detail isMaterialWidget and isMaterialWidget" + element.getTagName() + " " + child.getClass().getSimpleName());


            copyWidgetAttrsAndSetProperties(element, child);

            if (GwtMaterialUtil.hasСhildren(element)) {
                for (Node c : getNodeChildren(element)) {
                    list.add(process((Element) c));
                }

            }

            list.forEach(l -> {
                if (l.getKey() != null) {
                    addWidgetToParent(child, l.getKey());
                    //     element.appendChild(l.getKey().getElement());
                } else if (l.getValue() != null) {
                    child.getElement().appendChild(l.getValue());
                    //element.appendChild(l.getValue());
                }
            });
            //result.setValue(element);

            result.setKey(child);


*/


        } else if (element.getNodeType() == 1) {
            logger.warn("                                  process detail simple " + element.getTagName() );


            if (GwtMaterialUtil.hasСhildren(element)) {
                for (Node child : getNodeChildren(element)) {
                    list.add(process((Element) child));
                }

            }

            list.forEach(l -> {
                if (l.getKey() != null) {
                    element.appendChild(l.getKey().getElement());
                } else if (l.getValue() != null) {
                    element.appendChild(l.getValue());
                }
            });
            result.setValue(element);
        }
        return result;
    }

    private Widget getWidget(Element element) {
        if (hasDataField(element)) {
            String id = GwtMaterialUtil.getDataFieldValue(element);
            return templateFieldsMap.get(id);
        } else {
            Optional<Tuple<Widget, Boolean>> maybeExist = helper.invoke(element);
            if (maybeExist.isPresent()) {
                if (maybeExist.get().getValue()) {
                    return maybeExist.get().getKey();
                }
            }
        }
        throw new RuntimeException("can't find widget " + element.getTagName());
    }
}
