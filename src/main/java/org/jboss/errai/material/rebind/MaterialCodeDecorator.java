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

package org.jboss.errai.material.rebind;

import org.jboss.errai.codegen.Statement;
import org.jboss.errai.codegen.exception.GenerationException;
import org.jboss.errai.codegen.meta.MetaClass;
import org.jboss.errai.codegen.meta.MetaMethod;
import org.jboss.errai.codegen.util.Refs;
import org.jboss.errai.codegen.util.Stmt;
import org.jboss.errai.ioc.client.api.CodeDecorator;
import org.jboss.errai.ioc.rebind.ioc.extension.IOCDecoratorExtension;
import org.jboss.errai.ioc.rebind.ioc.injector.api.Decorable;
import org.jboss.errai.ioc.rebind.ioc.injector.api.FactoryController;
import org.jboss.errai.material.client.local.GwtMaterialBootstrap;
import org.jboss.errai.material.client.local.GwtMaterialInitializer;
import org.jboss.errai.ui.rebind.TemplatedCodeDecorator;
import org.jboss.errai.ui.shared.TemplateUtil;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.*;

import static org.jboss.errai.codegen.util.Stmt.*;
import static org.jboss.errai.codegen.util.Stmt.invokeStatic;

/**
 * Generates the code required for {@link Templated} classes.
 *
 * @author Dmitrii Tikhomirov <chani@me.com>
 */
@CodeDecorator
public class MaterialCodeDecorator extends IOCDecoratorExtension<Templated> {
    private Document html;
    private List<Statement> stmts;

    private static final Logger logger = LoggerFactory.getLogger(MaterialCodeDecorator.class);

    public MaterialCodeDecorator(final Class<Templated> decoratesWith) {
        super(decoratesWith);
    }

    @Override
    public void generateDecorator(final Decorable decorable, final FactoryController controller) {

        final MetaClass declaringClass = decorable.getDecorableDeclaringType();
        final String parentOfRootTemplateElementVarName = "parentElementForTemplateOf" + decorable.getDecorableDeclaringType().getName();
        final String templateVarName = "templateFor" + decorable.getDecorableDeclaringType().getName();

        List<MetaMethod> currentPostConstructs = declaringClass.getDeclaredMethodsAnnotatedWith(PostConstruct.class);
        if (currentPostConstructs.size() > 1) {
            throw new RuntimeException(declaringClass.getFullyQualifiedName() + " has multiple @PostConstruct methods.");
        }


        initTemplateParser(declaringClass); // ?

        final Statement component = Refs.get("instance");


        final Statement rootTemplateElement = Stmt.invokeStatic(TemplateUtil.class, "getRootTemplateElement",
                Stmt.loadVariable(parentOfRootTemplateElementVarName));

        stmts = new ArrayList<>();
        stmts.add(invokeStatic(GwtMaterialBootstrap.class, "processTemplate", rootTemplateElement,
                Stmt.loadVariable(templateVarName).invoke("getContents").invoke("getText"),
                TemplatedCodeDecorator.getTemplateFileName(declaringClass),
                TemplatedCodeDecorator.getTemplateFragmentName(declaringClass), loadVariable("templateFieldsMap")));

        controller.addInitializationStatementsToEnd(stmts);
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
