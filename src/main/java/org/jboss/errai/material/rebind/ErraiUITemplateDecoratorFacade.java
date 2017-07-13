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

package org.jboss.errai.material.rebind;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import org.jboss.errai.codegen.Cast;
import org.jboss.errai.codegen.Statement;
import org.jboss.errai.codegen.meta.MetaClass;
import org.jboss.errai.codegen.meta.impl.build.BuildMetaClass;
import org.jboss.errai.codegen.util.Stmt;
import org.jboss.errai.ioc.rebind.ioc.injector.api.Decorable;
import org.jboss.errai.ioc.rebind.ioc.injector.api.FactoryController;
import org.jboss.errai.material.client.local.GwtMaterialUtil;
import org.jboss.errai.ui.rebind.DataFieldCodeDecorator;
import org.jboss.errai.ui.rebind.TemplatedCodeDecorator;
import org.jboss.errai.ui.shared.TemplateUtil;
import org.jboss.errai.ui.shared.api.annotations.Templated;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

import static org.jboss.errai.codegen.builder.impl.ObjectBuilder.newInstanceOf;
import static org.jboss.errai.codegen.meta.MetaClassFactory.parameterizedAs;
import static org.jboss.errai.codegen.meta.MetaClassFactory.typeParametersOf;
import static org.jboss.errai.codegen.util.Stmt.invokeStatic;

/**
 * @author Dmitrii Tikhomirov <chani@me.com>
 *         Created by treblereel on 6/22/17.
 */
public class ErraiUITemplateDecoratorFacade {
    private final Class<Templated> decoratesWith;
    private final TemplatedCodeDecorator templatedCodeDecorator;
    final static String ROOT_ELEMENT = "rootElement";


    ErraiUITemplateDecoratorFacade(Class<Templated> decoratesWith) {
        this.decoratesWith = decoratesWith;
        templatedCodeDecorator = new TemplatedCodeDecorator(decoratesWith);
    }

    List<Statement> generateTemplateDestruction(final Decorable decorable) {
        List<Statement> result;
        try {
            Method m = TemplatedCodeDecorator.class.getDeclaredMethod("generateTemplateDestruction", Decorable.class);
            m.setAccessible(true);
            result = (List<Statement>) m.invoke(templatedCodeDecorator, decorable);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        if (result == null) {
            result = new ArrayList<>();
        }
        return result;
    }

    Map<MetaClass, BuildMetaClass> getConstructedTemplateTypes(final Decorable decorable) {
        Map<MetaClass, BuildMetaClass> result;
        try {
            Method m = TemplatedCodeDecorator.class.getDeclaredMethod("getConstructedTemplateTypes", Decorable.class);
            m.setAccessible(true);
            result = (Map<MetaClass, BuildMetaClass>) m.invoke(templatedCodeDecorator, decorable);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        if (result == null) {
            result = new HashMap<>();
        }
        return result;

    }

    Statement supplierOf(final Statement value) {
        return newInstanceOf(parameterizedAs(Supplier.class, typeParametersOf(value.getType())))
                .extend()
                .publicOverridesMethod("get")
                .append(Stmt.nestedCall(value).returnValue())
                .finish()
                .finish();
    }

    Optional<String> getResolvedStyleSheetPath(final Optional<String> declaredStylesheetPath,
                                               final MetaClass declaringClass) {
        Optional<String> result;
        try {
            Method m = TemplatedCodeDecorator.class.getDeclaredMethod("getResolvedStyleSheetPath", Optional.class, MetaClass.class);
            m.setAccessible(true);
            result = (Optional<String>) m.invoke(templatedCodeDecorator, declaredStylesheetPath, declaringClass);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        if (result == null) {
            result = Optional.empty();
        }
        return result;

    }

    void generateTemplateResourceInterface(final Decorable decorable, final MetaClass type, final boolean customProvider, final Optional<String> cssPath) {
        try {
            Method m = TemplatedCodeDecorator.class.getDeclaredMethod("generateTemplateResourceInterface", Decorable.class, MetaClass.class, boolean.class, Optional.class);
            m.setAccessible(true);
            m.invoke(templatedCodeDecorator, decorable, type, customProvider, cssPath);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    void translateTemplate(final Decorable decorable, final List<Statement> initStmts,
                           final Statement rootTemplateElement) {
        try {
            Method m = TemplatedCodeDecorator.class.getDeclaredMethod("translateTemplate", Decorable.class, List.class, Statement.class);
            m.setAccessible(true);
            m.invoke(templatedCodeDecorator, decorable, initStmts, rootTemplateElement);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    List<Statement> generateDataFieldMetas(final String dataFieldMetasVarName, final Decorable decorable) {
        List<Statement> result;
        try {
            Method m = TemplatedCodeDecorator.class.getDeclaredMethod("generateDataFieldMetas", String.class, Decorable.class);
            m.setAccessible(true);
            result = (List<Statement>) m.invoke(templatedCodeDecorator, dataFieldMetasVarName, decorable);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        if (result == null) {
            result = new ArrayList<>();
        }
        return result;
    }

    void generateComponentCompositions(final Decorable decorable,
                                               final List<Statement> initStmts,
                                               final Statement component,
                                               final Statement rootTemplateElement,
                                               final Statement dataFieldElements,
                                               final Statement fieldsMap,
                                               final Statement fieldsMetaMap) {

        final boolean composite = decorable.getEnclosingInjectable().getInjectedType().isAssignableTo(Composite.class);


    /*
     * Merge each field's Widget Element into the DOM in place of the
     * corresponding data-field
     */
        final Map<String, Statement> dataFields = DataFieldCodeDecorator.aggregateDataFieldMap(decorable, decorable.getEnclosingInjectable().getInjectedType());
        for (final Map.Entry<String, Statement> field : dataFields.entrySet()) {
            initStmts.add(invokeStatic(TemplateUtil.class, "compositeComponentReplace", decorable.getDecorableDeclaringType()
                            .getFullyQualifiedName(), TemplatedCodeDecorator.getTemplateFileName(decorable.getDecorableDeclaringType()), supplierOf(Cast.to(Widget.class, field.getValue())),
                    dataFieldElements, fieldsMetaMap, field.getKey()));
        }

    /*
     * Add each field to the Collection of children of the new Composite
     * Template
     */
        for (final Map.Entry<String, Statement> field : dataFields.entrySet()) {
            initStmts.add(Stmt.nestedCall(fieldsMap).invoke("put", field.getKey(), field.getValue()));
        }
                initStmts.add(Stmt.declareVariable(Element.class).named(ROOT_ELEMENT).
                        initializeWith(rootTemplateElement));

        initStmts.add(invokeStatic(GwtMaterialUtil.class, "compositeComponentReplace", Stmt.loadVariable(ROOT_ELEMENT), decorable.getDecorableDeclaringType()
                        .getFullyQualifiedName(), TemplatedCodeDecorator.getTemplateFileName(decorable.getDecorableDeclaringType()),
                dataFieldElements, fieldsMetaMap, fieldsMap));


        initStmts.add(invokeStatic(GwtMaterialUtil.class,"beforeTemplateInitInvoke", Stmt.loadVariable(ROOT_ELEMENT), Stmt.loadVariable(MaterialCodeDecorator.FINAL_HTML_CONTENT), fieldsMap));



        final String initMethodName;
        if (composite) {
      /*
       * Attach the Template to the Component, and set up the GWT Widget hierarchy
       * to preserve Handlers and DOM events.
       */
            initMethodName = "initWidget";
        } else {
            initMethodName = "initTemplated";
        }

        final String parentOfRootTemplateElementVarName = "parentElementForTemplateOf" + decorable.getDecorableDeclaringType().getName();


        initStmts.add(Stmt.invokeStatic(TemplateUtil.class, initMethodName, component, Stmt.loadVariable(ROOT_ELEMENT),
                Stmt.nestedCall(fieldsMap).invoke("values")));

    }

    void generateEventHandlerMethodClasses(final Decorable decorable, final FactoryController controller,
                                                   final List<Statement> initStmts, final String dataFieldElementsVarName, final Statement fieldsMap) {
        try {
            Method m = TemplatedCodeDecorator.class.getDeclaredMethod("generateEventHandlerMethodClasses", Decorable.class, FactoryController.class, List.class, String.class, Statement.class);
            m.setAccessible(true);
            m.invoke(templatedCodeDecorator, decorable, controller, initStmts, dataFieldElementsVarName, fieldsMap);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    void generateAfterTemplateInitInvoke(final List<Statement> initStmts, final Statement fieldsMap){
        initStmts.add(invokeStatic(GwtMaterialUtil.class,"afterTemplateInitInvoke", Stmt.loadVariable(ROOT_ELEMENT), Stmt.loadVariable(MaterialCodeDecorator.FINAL_HTML_CONTENT), fieldsMap));
    }
}