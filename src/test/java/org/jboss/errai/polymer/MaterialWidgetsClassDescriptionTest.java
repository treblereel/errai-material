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

package org.jboss.errai.polymer;


import com.google.common.reflect.TypeToken;
import com.google.gwt.dom.client.Style;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.ui.HasText;
import gwt.material.design.client.base.AbstractButton;
import gwt.material.design.client.base.MaterialWidget;
import gwt.material.design.client.constants.TextAlign;
import gwt.material.design.client.constants.WavesType;
import gwt.material.design.client.ui.*;
import org.apache.commons.lang3.ClassUtils;
import org.jboss.errai.polymer.client.local.GwtMaterialUtil;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Dmitrii Tikhomirov <chani@me.com>
 *         <p>
 *         Created by treblereel on 3/10/17.
 */
public class MaterialWidgetsClassDescriptionTest {
    private static final Logger logger = LoggerFactory.getLogger(MaterialWidgetsClassDescriptionTest.class.getName());


    /**
     * TODO MaterialValueBox is not in the list
     *
     * @throws IOException
     */

    @Test
    public void materialLinkTest() throws IOException {
        Set<Method> methods = parseMethods(MaterialLink.class);
        Assert.assertEquals(89, methods.size());
    }

    @Test
    public void materialDatePickerTest() throws IOException {
        Set<Method> methods = parseMethods(MaterialDatePicker.class);
        //     Assert.assertEquals(82, methods.size());
    }

    @Test
    public void materialMaterialListValueBoxTest() throws IOException {
        Set<Method> methods = parseMethods(MaterialListValueBox.class);

        //   Assert.assertEquals(78, methods.size());
    }

    private Set<Method> parseMethods(Class c) throws IOException {
        Set<Method> methods = new HashSet<>();
        ClassUtils.getAllInterfaces(c).stream().forEach(ifaces -> {
            if (ifaces.getName().startsWith("gwt.material.design.client.base") ||
                    ifaces.getName().startsWith("com.google.gwt.user.client.ui.")
                    ) {
                Arrays.stream(ifaces.getMethods()).forEach(m -> {
                    if (m.getName().startsWith("set")) {
                        methods.add(m);
                    }
                });
            }
        });

        Arrays.stream(c.getMethods()).forEach(m -> {
            if (m.getName().startsWith("set")) {
                Optional<Method> check = methods.stream().filter(mm -> mm.getName().equals(m.getName())).findFirst();
                if (!check.isPresent()) {
                    methods.add(m);
                }
            }

        });

        methods.stream().forEach(m -> {
            //    logger.warn(m.getName() + m.getParameters().length + m.getModifiers() + " " + Modifier.isPublic(m.getModifiers())+ " " + m.getDeclaringClass().getCanonicalName());

        });


        return methods;
    }

    @Test
    public void materialMaterialWidgetTest() throws IOException {
        Set<Method> methods = parseMethods(MaterialWidget.class);
        //  Assert.assertEquals(76, methods.size());
    }

    @Test
    public void materialMaterialButtonTest() throws IOException {
        Set<Method> methods = parseMethods(MaterialButton.class);
        //      Assert.assertEquals(65, methods.size());
    }


    @Test
    public void gwtMaterialWidgetStoreTest() {
/*
        new MaterialWidgetFactoryGenerator();
*/

    }

    @Test
    public void invokeTextAlignProperty() {
        Assert.assertEquals(TextAlign.LEFT, TextAlign.valueOf("LEFT"));
        Assert.assertEquals(TextAlign.DEFAULT, TextAlign.valueOf("DEFAULT"));
        Assert.assertEquals(TextAlign.RIGHT, TextAlign.valueOf("RIGHT"));
        Assert.assertEquals(TextAlign.CENTER, TextAlign.valueOf("CENTER"));
    }

    @Test
    public void invokeResizeRuleProperty() {
        Assert.assertEquals(MaterialTextArea.ResizeRule.AUTO, MaterialTextArea.ResizeRule.valueOf("AUTO"));
        Assert.assertEquals(MaterialTextArea.ResizeRule.FOCUS, MaterialTextArea.ResizeRule.valueOf("FOCUS"));
        Assert.assertEquals(MaterialTextArea.ResizeRule.NONE, MaterialTextArea.ResizeRule.valueOf("NONE"));
    }

    @Test
    public void invokeMaterialDatePickerTypeProperty() {
        Assert.assertEquals(MaterialDatePicker.MaterialDatePickerType.DAY, MaterialDatePicker.MaterialDatePickerType.valueOf("DAY"));
        Assert.assertEquals(MaterialDatePicker.MaterialDatePickerType.MONTH_DAY, MaterialDatePicker.MaterialDatePickerType.valueOf("MONTH_DAY"));
        Assert.assertEquals(MaterialDatePicker.MaterialDatePickerType.YEAR, MaterialDatePicker.MaterialDatePickerType.valueOf("YEAR"));
        Assert.assertEquals(MaterialDatePicker.MaterialDatePickerType.YEAR_MONTH_DAY, MaterialDatePicker.MaterialDatePickerType.valueOf("YEAR_MONTH_DAY"));
    }

    @Test
    public void invokeMaterialVisibilityProperty() {
        Assert.assertEquals(Style.Visibility.VISIBLE, com.google.gwt.dom.client.Style.Visibility.valueOf("VISIBLE"));
        Assert.assertEquals(Style.Visibility.HIDDEN, com.google.gwt.dom.client.Style.Visibility.valueOf("HIDDEN"));
    }

    @Test
    public void checkMaterialButtonMethodsNumberFounded() {
        AtomicInteger counter = new AtomicInteger();
        Set<Class<?>> types = (Set) TypeToken.of(MaterialButton.class).getTypes().rawTypes();
        types.stream().forEach(s -> {
            counter.incrementAndGet();
        });
        Assert.assertEquals(78, counter.get());
    }

    @Test
    public void checkWavesTypeFounded() {
        String value = "DEFAULT";
        Class clazz = WavesType.class;
        Object wavesTypeObject = EnumSet.allOf(clazz).stream().filter(e -> e.toString().equals(value.toUpperCase())).findFirst().orElse(null);

        Assert.assertNotNull(wavesTypeObject);
        Assert.assertEquals("waves-default", ((WavesType) wavesTypeObject).getCssName());
        Assert.assertEquals("DEFAULT", ((WavesType) wavesTypeObject).name());
    }

    @Test
    public void classToString() {
        //gwt.material.design.client.base.MaterialWidget
        Class param = boolean.class;
        if (param.equals(boolean.class)) {
            //   logger.warn("yeap");
        } else {
            //   logger.warn("nope");
        }


        //   logger.warn("classToString " + param.getCanonicalName().replaceAll("\\.","/"));
        // Assert.assertEquals(Style.Visibility.HIDDEN, com.google.gwt.dom.client.Style.Visibility.valueOf("HIDDEN"));
    }

    @Test
    public void checkAbstractClass() {

        System.out.println(Modifier.isAbstract(AbstractButton.class.getModifiers()) + " " + AbstractButton.class.isInterface() + " " + Modifier.isAbstract(HasText.class.getModifiers()));

    }

/*    @Test
    public void testSelfClosingTagReplacer() {
        String input = "<!DOCTYPE html\n" +
                "<div data-field=\"root\" id=\"root\"><material-button/>" +
                "<material-button />" +
                "<Material-button/>" +
                "<Material-button />" +
                "<Material-button text=\"Primary\" waves=\"LIGHT\" textColor=\"WHITE\" iconType=\"POLYMER\" iconPosition=\"LEFT\" />\n" +
                "<material-button text=\"Primary\" waves=\"LIGHT\" textColor=\"WHITE\" iconType=\"POLYMER\" iconPosition=\"LEFT\" />\n" +
                "<material-dropdown activator=\"dp-4\" belowOrigin=\"false\" constrainWidth=\"false\">" +
                "<material-link text=\"First\"/>" +
                "<material-link text=\" Second \">" +
                "<material-badge text=\"1 new \" textColor=\"WHITE\"/>" +
                "</material-link>" +
                "<material-link text=\"Third\"/>" +
                "</material-dropdown>" +
                "\n" +
                "</div>";

        String result = "<!DOCTYPE html\n" +
                "<div data-field=\"root\" id=\"root\"><material-button self-closed=\"true\"></material-button><material-button self-closed=\"true\"></material-button><Material-button self-closed=\"true\"></Material-button><Material-button self-closed=\"true\"></Material-button><Material-button text=\"Primary\" waves=\"LIGHT\" textColor=\"WHITE\" iconType=\"POLYMER\" iconPosition=\"LEFT\" self-closed=\"true\"></Material-button>\n" +
                "<material-button text=\"Primary\" waves=\"LIGHT\" textColor=\"WHITE\" iconType=\"POLYMER\" iconPosition=\"LEFT\" self-closed=\"true\"></material-button>\n" +
                "<material-dropdown activator=\"dp-4\" belowOrigin=\"false\" constrainWidth=\"false\"><material-link text=\"First\" self-closed=\"true\"></material-link><material-link text=\" Second \"><material-badge text=\"1 new \" textColor=\"WHITE\" self-closed=\"true\"></material-badge></material-link><material-link text=\"Third\" self-closed=\"true\"></material-link></material-dropdown>\n" +
                "</div>";

        Assert.assertEquals(result, GwtMaterialUtil.closeVoidTags(input));

    }*/



}
