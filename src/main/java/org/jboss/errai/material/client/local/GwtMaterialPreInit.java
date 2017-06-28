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
import gwt.material.design.client.base.MaterialWidget;
import org.jboss.errai.ioc.client.container.IOC;
import org.jboss.errai.ui.shared.Visit;
import org.jboss.errai.ui.shared.VisitContext;
import org.jboss.errai.ui.shared.VisitContextMutable;
import org.jboss.errai.ui.shared.Visitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Optional;

import static org.jboss.errai.material.client.local.GwtMaterialUtil.*;

/**
 * @author Dmitrii Tikhomirov <chani@me.com>
 *         Created by treblereel on 6/23/17.
 */
public class GwtMaterialPreInit {

    private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    private final MaterialWidgetFactoryHelper helper = IOC.getBeanManager().lookupBean(MaterialWidgetFactoryHelper.class).getInstance();

    private Map<String, Widget> templateFieldsMap;

    private Element original;


    GwtMaterialPreInit(Element root, String content, Map<String, Widget> templateFieldsMap) {
        this.templateFieldsMap = templateFieldsMap;
        original = DOM.createDiv();
        original.setInnerHTML(content);
        process(null, root, false);
    }

    private void process(Widget parent, Element root, boolean parentDataFielded) {
        if (root.getNodeType() == Node.ELEMENT_NODE) {

            logger.warn("process  " + ((Element) root).getTagName() + "  " + " " + root.getId());
        }

        if (hasСhildren(root)) {
            getNodeChildren(root).forEach(child -> {
                if (child.getNodeType() == Node.ELEMENT_NODE) {
                    if (helper.isWidgetSupported(((Element) child).getTagName())) {
                        if (hasDataField((Element) child)) {
                            Widget childAsWidget = getDataFieldedWidget((Element) child, templateFieldsMap);
                            copyWidgetAttrsAndSetProperties((Element) child, childAsWidget);

                         //   logger.warn(" .. child before replace  " + childAsWidget.getElement().getInnerHTML());
                         //   logger.warn(" .. looking for  " + getDataFieldValue((Element) child) + " in " + original.getInnerHTML());

                            VisitContext<GwtMaterialUtil.TaggedElement> context = getElementByDataField(original, getDataFieldValue((Element) child));
                            if (context.getResult() != null) {
                                if (context.getResult().getElement() != null) {
                                    Element originDataFielded = context.getResult().getElement();
                                    childAsWidget.getElement().setInnerHTML(originDataFielded.getInnerHTML());
                                    ((Element) child).setInnerHTML(originDataFielded.getInnerHTML());
                                }
                            }
                            if(parent !=null){
                                if(parent instanceof MaterialWidget){
                                    addWidgetToParent(parent, childAsWidget);
                                }else{
                                    parent.getElement().appendChild(child);
                                }
                            }

                            process(childAsWidget, childAsWidget.getElement(), true);
                        } else {
                            doMaterialWidget(parent, (Element) child, parentDataFielded);
                        }


                    } else {
                        process(null, (Element) child, parentDataFielded);
                    }
                }
            });
        }
    }

    public VisitContext<GwtMaterialUtil.TaggedElement> getElementByDataField(Element parserDiv, String lookup) {
        return Visit.depthFirst(parserDiv, new Visitor<GwtMaterialUtil.TaggedElement>() {
            @Override
            public boolean visit(final VisitContextMutable<GwtMaterialUtil.TaggedElement> context, final Element element) {
                if (hasDataField(element) && getDataFieldValue(element).equals(lookup)) {
                    context.setResult(new GwtMaterialUtil.TaggedElement(GwtMaterialUtil.AttributeType.DATA_FIELD, element));
                    return false;
                }
                return true;
            }
        });
    }

    private void doElement(Element elm) {


    }

    private void doMaterialWidget(Widget parent, Element element, boolean parentDateFielded) {
        Optional<Tuple<Widget, Boolean>> maybeExist = helper.invoke(element);
        if (maybeExist.isPresent()) {
            if (maybeExist.get().getValue()) {
                Widget candidate = maybeExist.get().getKey();
                copyWidgetAttrsAndSetProperties(element, candidate);
                if (parent != null) {
                    element.getParentElement().removeChild(element);
                    GwtMaterialUtil.addWidgetToParent(parent, candidate);
                } else {
                    String id = DOM.createUniqueId();
                    element.setAttribute("material_id", id);
                    templateFieldsMap.put(id, candidate);
                }
                process(candidate, element, parentDateFielded);
            } else {

            }
        } else {
            throw new RuntimeException("widget doesn't exist " + element.getTagName());
        }

    }
}
