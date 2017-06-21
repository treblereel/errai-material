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

import org.jboss.errai.codegen.Statement;
import org.jboss.errai.codegen.util.Stmt;
import org.jboss.errai.ioc.client.api.CodeDecorator;
import org.jboss.errai.ioc.client.api.EntryPoint;
import org.jboss.errai.ioc.rebind.ioc.extension.IOCDecoratorExtension;
import org.jboss.errai.ioc.rebind.ioc.injector.api.Decorable;
import org.jboss.errai.ioc.rebind.ioc.injector.api.FactoryController;
import org.jboss.errai.material.client.local.GwtMaterialInitializer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dmitrii Tikhomirov <chani@me.com>
 *         Created by treblereel on 6/21/17.
 */
@CodeDecorator
public class GwtMaterialJsInjectorDecorator extends IOCDecoratorExtension<EntryPoint> {

    public GwtMaterialJsInjectorDecorator(Class<EntryPoint> decoratesWith) {
        super(decoratesWith);
    }

    @Override
    public void generateDecorator(Decorable decorable, FactoryController controller) {
        List<Statement> stmts = new ArrayList<>();
        controller.getFactoryInitializaionStatements().add(Stmt.invokeStatic(GwtMaterialInitializer.class, "get").invoke("check"));
        controller.addInitializationStatementsToEnd(stmts);
    }
}
