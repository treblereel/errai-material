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
import org.jboss.errai.ioc.client.container.IOC;
import org.jboss.errai.ui.shared.Visit;
import org.jboss.errai.ui.shared.VisitContext;
import org.jboss.errai.ui.shared.VisitContextMutable;
import org.jboss.errai.ui.shared.Visitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Optional;

import static org.jboss.errai.material.client.local.GwtMaterialUtil.copyWidgetAttrsAndSetProperties;
import static org.jboss.errai.material.client.local.GwtMaterialUtil.getDataFieldValue;
import static org.jboss.errai.material.client.local.GwtMaterialUtil.getDataFieldedWidget;
import static org.jboss.errai.material.client.local.GwtMaterialUtil.getNodeChildren;
import static org.jboss.errai.material.client.local.GwtMaterialUtil.hasDataField;
import static org.jboss.errai.material.client.local.GwtMaterialUtil.hasСhildren;

/**
 * @author Dmitrii Tikhomirov <chani@me.com>
 *         Created by treblereel on 6/23/17.
 */
public class GwtMaterialPreInit {

    private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    private final MaterialWidgetFactoryHelper helper = IOC.getBeanManager().lookupBean(MaterialWidgetFactoryHelper.class).getInstance();

    static final String MATERIAL_ID = "material_id";

    private Map<String, Widget> templateFieldsMap;

    private Element original;


    GwtMaterialPreInit(Element root, String content, Map<String, Widget> templateFieldsMap) {
        this.templateFieldsMap = templateFieldsMap;
        original = DOM.createDiv();
        original.setInnerHTML(content);
        logger.warn(" original html " + original.getInnerHTML());

        process(null, root, false);

    }

    private void process(Widget parent, Element root, boolean parentDataFielded) {
        if (hasСhildren(root)) {
            getNodeChildren(root).forEach(child -> {
                logger.warn("inProcess  " + ((Element) child).getTagName() + "  " + " " + child.getNodeType());
                if (child.getNodeType() == Node.ELEMENT_NODE) {
                    if (helper.isWidgetSupported(((Element) child).getTagName())) {
                        if (hasDataField((Element) child)) {
                            Widget childAsWidget = getDataFieldedWidget((Element) child, templateFieldsMap);
                            copyWidgetAttrsAndSetProperties((Element) child, childAsWidget);

                            VisitContext<GwtMaterialUtil.TaggedElement> context = getElementByDataField(original, getDataFieldValue((Element) child));
                            if (context.getResult() != null) {
                                if (context.getResult().getElement() != null) {
                                    Element originDataFielded = context.getResult().getElement();
                                    ((Element) child).setInnerHTML(originDataFielded.getInnerHTML());
                                }
                            }
                            process(childAsWidget, (Element) child, true);
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
        logger.debug("getElementByDataField [" + lookup + "]" + " " + parserDiv.getInnerHTML());
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

        if (parent != null) {

            logger.warn("parent  " + parent.getClass().getSimpleName());
            logger.warn("looking for  " + element.getTagName());
        }

        Optional<Tuple<Widget, Boolean>> maybeExist = helper.invoke(element);
        if (maybeExist.isPresent()) {
            if (maybeExist.get().getValue()) {
                Widget candidate = maybeExist.get().getKey();

                logger.warn(".. find  " + candidate.getClass().getSimpleName());

                copyWidgetAttrsAndSetProperties(element, candidate);

                if (parent != null) {
                    element.getParentElement().removeChild(element);
                    //parent.getElement().removeChild(element);

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
            throw new RuntimeException("widget doent't exist " + element.getTagName());
        }

    }
}
