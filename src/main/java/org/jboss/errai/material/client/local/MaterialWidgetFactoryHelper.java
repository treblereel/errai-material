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
import com.google.gwt.user.client.ui.Widget;
import org.jboss.errai.material.client.local.factory.MaterialWidgetFactory;
import org.jboss.errai.material.client.local.factory.MaterialWidgetQualifier;
import org.jboss.errai.material.client.local.factory.WidgetQualifier;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Optional;

/**
 * @author Dmitrii Tikhomirov <chani@me.com>
 *         Created by treblereel on 5/27/17.
 */
@Singleton
public class MaterialWidgetFactoryHelper {

    @Inject
    @MaterialWidgetQualifier
    private MaterialWidgetFactory materialWidgetFactory;

    @Inject
    @WidgetQualifier
    private MaterialWidgetFactory gwtNonMaterialWidgetFactory;

    public Boolean isWidgetSupported(String tag){
        if(materialWidgetFactory.isWidgetExist(tag) || gwtNonMaterialWidgetFactory.isWidgetExist(tag)){
            return true;
        }
        return false;
    }

    public Boolean isExtendsMaterialWidget(Widget tagged){
        return isExtendsMaterialWidget(tagged.getClass().getSimpleName());
    }

    public Boolean isExtendsMaterialWidget(String tag){
        Optional<Boolean> material = materialWidgetFactory.isExtendsMaterialWidget(tag);
        if (material.isPresent()) {
            return material.get();
        }
        material = gwtNonMaterialWidgetFactory.isExtendsMaterialWidget(tag);
        if (material.isPresent()) {
            return material.get();
        }
        return false;
    }


    public Optional<MaterialWidgetDefinition> getMaterialWidgetDefinition(String tag) {
        Optional<MaterialWidgetDefinition> material = materialWidgetFactory.getWidgetDefIfExist(tag);
        if (material.isPresent()) {
             return Optional.of(material.get());
        }
        material = gwtNonMaterialWidgetFactory.getWidgetDefIfExist(tag);
        if (material.isPresent()) {
            return Optional.of(material.get());
        }

        return Optional.empty();
    }

    public Optional<Tuple<Widget, Boolean>> invoke(Element elm) {
        Optional<Widget> material = materialWidgetFactory.invoke(elm);
        if (material.isPresent()) {
            return Optional.of(new Tuple<>(material.get(), true));
        }
        material = gwtNonMaterialWidgetFactory.invoke(elm);
        if (material.isPresent()) {
            return Optional.of(new Tuple<>(material.get(), false));
        }
        return Optional.empty();
    }

    public MaterialWidgetFactory getFactory(Class clazz){
        if(clazz.equals(MaterialWidgetQualifier.class)){
            return materialWidgetFactory;
        }else if(clazz.equals(WidgetQualifier.class)){
            return  gwtNonMaterialWidgetFactory;
        }
        return null;
    }


}
