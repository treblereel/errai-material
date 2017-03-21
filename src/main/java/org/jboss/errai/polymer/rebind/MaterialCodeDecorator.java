/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jboss.errai.polymer.rebind;

import gwt.material.design.client.base.MaterialWidget;
import org.jboss.errai.codegen.Statement;
import org.jboss.errai.codegen.exception.GenerationException;
import org.jboss.errai.codegen.meta.MetaClass;
import org.jboss.errai.codegen.meta.MetaMethod;
import org.jboss.errai.codegen.util.Stmt;
import org.jboss.errai.ioc.client.api.CodeDecorator;
import org.jboss.errai.ioc.rebind.ioc.extension.IOCDecoratorExtension;
import org.jboss.errai.ioc.rebind.ioc.injector.api.Decorable;
import org.jboss.errai.ioc.rebind.ioc.injector.api.FactoryController;
import org.jboss.errai.polymer.shared.GwtMaterialBootstrap;
import org.jboss.errai.ui.rebind.DataFieldCodeDecorator;
import org.jboss.errai.ui.rebind.TemplatedCodeDecorator;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.enterprise.util.TypeLiteral;
import java.io.File;
import java.io.IOException;
import java.util.*;

import static org.jboss.errai.codegen.util.Stmt.*;

/**
 * Generates the code required for {@link Templated} classes.
 * @author Dmitrii Tikhomirov <chani@me.com>
 */
@CodeDecorator
public class MaterialCodeDecorator extends IOCDecoratorExtension<Templated> {
    private Document html;
    private List<Statement> stmts;
    private static final String gwtMaterialElementInnerHTMLContentMap = "gwtMaterialElementInnerHTMLContentMap";
    private static final String gwtMaterialElementMap = "gwtMaterialElementMap";

    private static final Logger logger = LoggerFactory.getLogger(MaterialCodeDecorator.class);

    public MaterialCodeDecorator(final Class<Templated> decoratesWith) {
        super(decoratesWith);
    }

    @Override
    public void generateDecorator(final Decorable decorable, final FactoryController controller) {
        final MetaClass declaringClass = decorable.getDecorableDeclaringType();

        List<MetaMethod> currentPostConstructs = declaringClass.getDeclaredMethodsAnnotatedWith(PostConstruct.class);
        if (currentPostConstructs.size() > 1) {
            throw new RuntimeException(declaringClass.getFullyQualifiedName() + " has multiple @PostConstruct methods.");
        }

        initTemplateParser(declaringClass); // ?

        stmts = new ArrayList<>();

        stmts.add(declareVariable(gwtMaterialElementInnerHTMLContentMap, new TypeLiteral<Map<String, String>>() {
                },
                newObject(new TypeLiteral<LinkedHashMap<String, String>>() {
                }))
        );

        stmts.add(declareVariable(gwtMaterialElementMap, new TypeLiteral<Map<String, MaterialWidget>>() {
                },
                newObject(new TypeLiteral<LinkedHashMap<String, MaterialWidget>>() {
                }))
        );

        Map<String, MetaClass> types = DataFieldCodeDecorator.aggregateDataFieldTypeMap(decorable, decorable.getEnclosingInjectable().getInjectedType());


        // only datafielded !!!
        for (final Map.Entry<String, MetaClass> field : types.entrySet()) {
            if (checkIfWidgetSupported(field.getValue()))
                processMaterialTag(field.getKey());
        }

        stmts.add(invokeStatic(GwtMaterialBootstrap.class, "processTemplate", loadVariable("parentElementForTemplateOfApp"), loadVariable("dataFieldElements"), loadVariable("gwtMaterialElementInnerHTMLContentMap")));

        /**
         *  Rework this after !!! TODO
         */
        controller.getFactoryInitializaionStatements().add(Stmt.loadVariable("context").invoke("getInstance", "Type_factory__o_j_e_p_s_Initializer__quals__j_e_i_Any_j_e_i_Default"));


        controller.addInitializationStatementsToEnd(stmts);
    }

    /**TODO REWORK check
     *
     * @param className
     * @return
     */
    private boolean checkIfWidgetSupported(MetaClass className) {
        return className.getFullyQualifiedName().startsWith("gwt.material.design.client.ui");
    }

    private void processMaterialTag(String name) {
        Element elm = getElementFromTemplate(name);
        StringBuilder sb = new StringBuilder();
        assert elm != null;
        for (int i = 0; i < elm.childNodes().size(); i++) {
            String c2 = elm.childNode(i).outerHtml();
            sb.append(c2);
        }
        stmts.add(Stmt.loadVariable(gwtMaterialElementInnerHTMLContentMap).invoke("put", name, sb.toString()));
    }

    private Element getElementFromTemplate(String name) {
        Elements elms = html.body().getElementsByAttributeValue("data-field", name);
        if (elms.size() > 1) {
            throw new IllegalArgumentException("Duplicated tags " + name);
        } else if (elms.size() == 1) {
            return elms.get(0);
        }
        return null;
    }

    private void initTemplateParser(MetaClass declaringClass) {
        Optional<String> template = getPathFromFileName(declaringClass);
        String filename = TemplatedCodeDecorator.getTemplateFileName(declaringClass);
        if (!template.isPresent()) {
            throw new GenerationException(
                    "Cannot find template ["
                            + filename + "] in class [" + declaringClass.getFullyQualifiedName()
                            + "].");
        }

        try {
            File file = new File(template.get());
            html = Jsoup.parse(file, "UTF-8", "http://localhost/");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Optional<String> getPathFromFileName(MetaClass declaringClass) {
        String fileName = TemplatedCodeDecorator.getTemplateFileName(declaringClass);
        return Optional.of(Thread.currentThread().getContextClassLoader().getResource(fileName).getFile());
    }

}
