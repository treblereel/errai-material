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

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.StyleInjector;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import org.jboss.errai.codegen.Parameter;
import org.jboss.errai.codegen.Statement;
import org.jboss.errai.codegen.StringStatement;
import org.jboss.errai.codegen.Variable;
import org.jboss.errai.codegen.exception.GenerationException;
import org.jboss.errai.codegen.meta.MetaClass;
import org.jboss.errai.codegen.meta.impl.build.BuildMetaClass;
import org.jboss.errai.codegen.util.Refs;
import org.jboss.errai.codegen.util.Stmt;
import org.jboss.errai.ioc.client.api.CodeDecorator;
import org.jboss.errai.ioc.rebind.ioc.extension.IOCDecoratorExtension;
import org.jboss.errai.ioc.rebind.ioc.injector.api.Decorable;
import org.jboss.errai.ioc.rebind.ioc.injector.api.FactoryController;
import org.jboss.errai.material.client.local.GwtMaterialUtil;
import org.jboss.errai.ui.client.local.spi.TemplateRenderingCallback;
import org.jboss.errai.ui.rebind.ClassPathResource;
import org.jboss.errai.ui.rebind.TemplatedCodeDecorator;
import org.jboss.errai.ui.shared.TemplateUtil;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.jsoup.nodes.Document;
import org.lesscss.LessCompiler;
import org.lesscss.LessException;
import org.lesscss.LessSource;
import org.lesscss.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.util.TypeLiteral;
import java.io.IOException;
import java.util.*;

import static java.util.Collections.singletonList;
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
    final Class<Templated> decoratesWith;
    final ErraiUITemplateDecoratorFacade facade;
    final static String FINAL_HTML_CONTENT = "finalHtmlContent";

    private static final Logger logger = LoggerFactory.getLogger(MaterialCodeDecorator.class);

    public MaterialCodeDecorator(final Class<Templated> decoratesWith) {
        super(decoratesWith);
        this.decoratesWith = decoratesWith;
        this.facade = new ErraiUITemplateDecoratorFacade(decoratesWith);
    }

    @Override
    public void generateDecorator(final Decorable decorable, final FactoryController controller) {
        final MetaClass declaringClass = decorable.getDecorableDeclaringType();

        final Templated anno = (Templated) decorable.getAnnotation();
        final Class<?> templateProvider = anno.provider();
        final boolean customProvider = templateProvider != Templated.DEFAULT_PROVIDER.class;
        final Optional<String> styleSheetPath = TemplatedCodeDecorator.getTemplateStyleSheetPath(declaringClass);
        final boolean explicitStyleSheetPresent = styleSheetPath.filter(path -> Thread.currentThread().getContextClassLoader().getResource(path) != null).isPresent();

        if (declaringClass.isAssignableTo(Composite.class)) {
            logger.warn("The @Templated class, {}, extends Composite. This will not be supported in future versions.", declaringClass.getFullyQualifiedName());
        }
        if (styleSheetPath.isPresent() && !explicitStyleSheetPresent) {
            throw new GenerationException("@Templated class [" + declaringClass.getFullyQualifiedName()
                    + "] declared a stylesheet [" + styleSheetPath + "] that could not be found.");
        }

        final List<Statement> preInitializationStatements = new ArrayList<>();
        final List<Statement> postInitializationStatements = new ArrayList<>();

        generateTemplatedInitialization(decorable, controller, preInitializationStatements, customProvider);

        if (customProvider) {
            final Statement init =
                    Stmt.invokeStatic(TemplateUtil.class, "provideTemplate",
                            templateProvider,
                            TemplatedCodeDecorator.getTemplateUrl(declaringClass),
                            Stmt.newObject(TemplateRenderingCallback.class)
                                    .extend()
                                    .publicOverridesMethod("renderTemplate", Parameter.of(String.class, "template", true))
                                    .appendAll(preInitializationStatements)
                                    .finish()
                                    .finish());

            controller.addInitializationStatements(Collections.singletonList(init));
        }
        else {
            postInitializationStatements.add(StringStatement.of("if(false){ //"));
            controller.addInitializationStatements(preInitializationStatements);
        }

        final String parentOfRootTemplateElementVarName = "parentElementForTemplateOf" + decorable.getDecorableDeclaringType().getName();
        final Statement rootTemplateElement = Stmt.invokeStatic(TemplateUtil.class, "getRootTemplateElement",
                Stmt.loadVariable(parentOfRootTemplateElementVarName));
        final String templateVarName = "templateFor" + decorable.getDecorableDeclaringType().getName();

        postInitializationStatements.add(StringStatement.of("}"));
/*        postInitializationStatements.add(invokeStatic(GwtMaterialBootstrap.class, "processTemplate", rootTemplateElement,
                Stmt.loadVariable(templateVarName).invoke("getContents").invoke("getText"),
                TemplatedCodeDecorator.getTemplateFileName(declaringClass),
                TemplatedCodeDecorator.getTemplateFragmentName(declaringClass), loadVariable("templateFieldsMap")));*/


        controller.addInitializationStatementsToEnd(postInitializationStatements);
        controller.addDestructionStatements(facade.generateTemplateDestruction(decorable));





/*        final MetaClass declaringClass = decorable.getDecorableDeclaringType();
        final String parentOfRootTemplateElementVarName = "parentElementForTemplateOf" + decorable.getDecorableDeclaringType().getName();
        final String templateVarName = "templateFor" + decorable.getDecorableDeclaringType().getName();


        initTemplateParser(declaringClass); // ?

        final Statement component = Refs.get("instance");



        List<Statement> preInitializationStatements = new ArrayList<>();
        List<Statement> postInitializationStatements = new ArrayList<>();

        preInitializationStatements.add(Stmt.codeComment("dirty dirty hack"));
        generateTemplatedInitialization(decorable, controller, preInitializationStatements);
        preInitializationStatements.add(StringStatement.of(" if(false){  //   "));
        controller.addInitializationStatements(preInitializationStatements);




 *//*       final Statement rootTemplateElement = Stmt.invokeStatic(TemplateUtil.class, "getRootTemplateElement",
                Stmt.loadVariable(parentOfRootTemplateElementVarName));

        stmts = new ArrayList<>();
        stmts.add(invokeStatic(GwtMaterialBootstrap.class, "processTemplate", rootTemplateElement,
                Stmt.loadVariable(templateVarName).invoke("getContents").invoke("getText"),
                TemplatedCodeDecorator.getTemplateFileName(declaringClass),
                TemplatedCodeDecorator.getTemplateFragmentName(declaringClass), loadVariable("templateFieldsMap")));*//*

        postInitializationStatements.add(StringStatement.of(" }"));

        controller.addInitializationStatementsToEnd(postInitializationStatements);*/
    }

/*    private void initTemplateParser(MetaClass declaringClass) {
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
    }*/

/*    public static Optional<String> getPathFromFileName(MetaClass declaringClass) {
        String fileName = TemplatedCodeDecorator.getTemplateFileName(declaringClass);
        return Optional.of(Thread.currentThread().getContextClassLoader().getResource(fileName).getFile());
    }*/


    private void generateTemplatedInitialization(final Decorable decorable,
                                                 final FactoryController controller,
                                                 final List<Statement> initStmts,
                                                 final boolean customProvider) {

        final String parentOfRootTemplateElementVarName = "parentElementForTemplateOf" + decorable.getDecorableDeclaringType().getName();

        final Statement rootTemplateElement = Stmt.invokeStatic(TemplateUtil.class, "getRootTemplateElement",
                Stmt.loadVariable(parentOfRootTemplateElementVarName));

        initStmts.add(Stmt.codeComment("<---- my code --->)"));

        final Map<MetaClass, BuildMetaClass> constructed = facade.getConstructedTemplateTypes(decorable);
        final MetaClass declaringClass = decorable.getDecorableDeclaringType();

        if (!constructed.containsKey(declaringClass)) {
            final String templateVarName = "templateFor" + decorable.getDecorableDeclaringType().getName();
            final Optional<String> resolvedStylesheetPath = facade.getResolvedStyleSheetPath(TemplatedCodeDecorator.getTemplateStyleSheetPath(declaringClass), declaringClass);
            final boolean lessStylesheet = resolvedStylesheetPath.filter(path -> path.endsWith(".less")).isPresent();

      /*
       * Generate this component's ClientBundle resource if necessary
       */
            final boolean generateCssBundle = resolvedStylesheetPath.isPresent() && !lessStylesheet;
            if (!customProvider || generateCssBundle) {
                facade.generateTemplateResourceInterface(decorable, declaringClass, customProvider, resolvedStylesheetPath.filter(path -> path.endsWith(".css")));

      /*
       * Instantiate the ClientBundle Template resource
       */
                initStmts.add(declareVariable(constructed.get(declaringClass)).named(templateVarName)
                        .initializeWith(invokeStatic(GWT.class, "create", constructed.get(declaringClass))));

                initStmts.add(Stmt.declareVariable(String.class).named(FINAL_HTML_CONTENT).
                        initializeWith(Stmt.invokeStatic(GwtMaterialUtil.class,"closeVoidTags",Stmt.loadVariable(templateVarName).
                                invoke("getContents").invoke("getText"))));

/*                initStmts.add(Stmt.declareVariable(Element.class).named(ROOT_ELEMENT).
                        initializeWith(rootTemplateElement));*/





                if (generateCssBundle) {
                    controller.addFactoryInitializationStatements(singletonList(castTo(constructed.get(declaringClass),
                            invokeStatic(GWT.class, "create", constructed.get(declaringClass))).invoke("getStyle")
                            .invoke("ensureInjected")));
                }
            }

      /*
       * Compile LESS stylesheet to CSS and generate StyleInjector code
       */
            if (resolvedStylesheetPath.isPresent() && lessStylesheet) {
                try {
                    final Resource lessResource = new ClassPathResource(resolvedStylesheetPath.get(), Thread.currentThread().getContextClassLoader());
                    final LessSource source = new LessSource(lessResource);
                    final LessCompiler compiler = new LessCompiler();
                    final String compiledCss = compiler.compile(source);

                    controller.addFactoryInitializationStatements(singletonList(invokeStatic(StyleInjector.class, "inject", loadLiteral(compiledCss))));
                } catch (IOException | LessException e) {
                    throw new RuntimeException("Error while attempting to compile the LESS stylesheet [" + resolvedStylesheetPath.get() + "].", e);
                }
            }

      /*
       * Get root Template Element
       */
            initStmts.add(Stmt
                    .declareVariable(Element.class)
                    .named(parentOfRootTemplateElementVarName)
                    .initializeWith(
                            Stmt.invokeStatic(TemplateUtil.class, "getRootTemplateParentElement",
                                    (customProvider) ? Variable.get("template") :
                                            Stmt.loadVariable(FINAL_HTML_CONTENT),
                                    TemplatedCodeDecorator.getTemplateFileName(declaringClass),
                                    TemplatedCodeDecorator.getTemplateFragmentName(declaringClass))));

      /*
       * If i18n is enabled for this module, translate the root template element here
       */
            if (!customProvider) {
                facade.translateTemplate(decorable, initStmts, rootTemplateElement);
            }

      /*
       * Get a reference to the actual Composite component being created
       */
            final Statement component = Refs.get("instance");

      /*
       * Get all of the data-field Elements from the Template
       */
            final String dataFieldElementsVarName = "dataFieldElements";
            initStmts.add(Stmt.declareVariable(dataFieldElementsVarName,
                    new TypeLiteral<Map<String, Element>>() {},
                    Stmt.invokeStatic(TemplateUtil.class, "getDataFieldElements",
                            rootTemplateElement))
            );

            final String dataFieldMetasVarName = "dataFieldMetas";
            initStmts.addAll(facade.generateDataFieldMetas(dataFieldMetasVarName, decorable));

      /*
       * Attach Widget field children Elements to the Template DOM
       */
            final String fieldsMapVarName = "templateFieldsMap";

      /*
       * The Map<String, Widget> to store actual component field references.
       */
            initStmts.add(declareVariable(fieldsMapVarName, new TypeLiteral<Map<String, Widget>>() {},
                    newObject(new TypeLiteral<LinkedHashMap<String, Widget>>() {}))
            );
            final Statement fieldsMap = Stmt.loadVariable(fieldsMapVarName);

            facade.generateComponentCompositions(decorable, initStmts, component, rootTemplateElement,
                    loadVariable(dataFieldElementsVarName), fieldsMap, loadVariable(dataFieldMetasVarName));

            facade.generateEventHandlerMethodClasses(decorable, controller, initStmts, dataFieldElementsVarName, fieldsMap);
        }





    }
}
