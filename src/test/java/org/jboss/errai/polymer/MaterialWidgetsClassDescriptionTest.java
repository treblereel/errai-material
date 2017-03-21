package org.jboss.errai.polymer;


import com.google.gwt.dom.client.Style;
import com.google.gwt.thirdparty.guava.common.collect.ImmutableSet;
import com.google.gwt.thirdparty.guava.common.reflect.ClassPath;
import gwt.material.design.client.constants.TextAlign;
import gwt.material.design.client.ui.MaterialDatePicker;
import gwt.material.design.client.ui.MaterialTextArea;
import gwt.material.design.client.ui.MaterialTitle;
import org.apache.commons.lang3.ClassUtils;
import org.jboss.errai.polymer.rebind.MaterialWidgetFactoryGenerator;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Dmitrii Tikhomirov <chani@me.com>
 * Created by treblereel on 3/10/17.
 */
public class MaterialWidgetsClassDescriptionTest {
    private static final Logger logger = LoggerFactory.getLogger(MaterialWidgetsClassDescriptionTest.class.getName());


    /**
     * TODO MaterialValueBox is not in the list
     *
     * @throws IOException
     */

    @Test
    public void lookupClassesByPackage() throws IOException {
        final ClassLoader loader = Thread.currentThread().getContextClassLoader();
        ImmutableSet<ClassPath.ClassInfo> topLevelClasses = com.google.gwt.thirdparty.guava.common.reflect.ClassPath.from(loader).getTopLevelClasses();
        Set<String> methods = new TreeSet<>();

        for (final com.google.gwt.thirdparty.guava.common.reflect.ClassPath.ClassInfo info : topLevelClasses) {
            if (info.getName().equals("gwt.material.design.client.ui.MaterialLink")) {
                final Class<?> clazz = info.load();
                ClassUtils.getAllSuperclasses(clazz).stream().forEach(s -> ClassUtils.getAllInterfaces(s).stream().forEach(ifaces -> {
                    if (ifaces.getName().startsWith("gwt.material.design.client.base") ||
                            ifaces.getName().startsWith("com.google.gwt.user.client.ui.HasVisibility") ||
                            ifaces.getName().startsWith("com.google.gwt.user.client.ui.HasEnabled") ||
                            ifaces.getName().startsWith("com.google.gwt.user.client.ui.Focusable")
                            ) {
                        Arrays.stream(ifaces.getMethods()).forEach(method -> {
                            if (method.getName().startsWith("set")) {
                                methods.add(method.getName());
                            }
                        });
                    }
                }));
            }
        }

        Arrays.stream(MaterialTitle.class.getDeclaredMethods()).forEach(m -> {
            if (m.getName().startsWith("set")) {
                methods.add(m.getName());
            }
        });


        Assert.assertEquals(67, methods.size());
    }

    @Test
    public void gwtMaterialWidgetStoreTest() {
        new MaterialWidgetFactoryGenerator();

    }

    @Test
    public void invokeTextAlignProperty() {
        Assert.assertEquals(TextAlign.LEFT,    TextAlign.valueOf("LEFT"));
        Assert.assertEquals(TextAlign.DEFAULT, TextAlign.valueOf("DEFAULT"));
        Assert.assertEquals(TextAlign.RIGHT,   TextAlign.valueOf("RIGHT"));
        Assert.assertEquals(TextAlign.CENTER,  TextAlign.valueOf("CENTER"));
    }

    @Test
    public void invokeResizeRuleProperty() {
        Assert.assertEquals(MaterialTextArea.ResizeRule.AUTO,    MaterialTextArea.ResizeRule.valueOf("AUTO"));
        Assert.assertEquals(MaterialTextArea.ResizeRule.FOCUS,   MaterialTextArea.ResizeRule.valueOf("FOCUS"));
        Assert.assertEquals(MaterialTextArea.ResizeRule.NONE,    MaterialTextArea.ResizeRule.valueOf("NONE"));
    }

    @Test
    public void invokeMaterialDatePickerTypeProperty() {
        Assert.assertEquals(MaterialDatePicker.MaterialDatePickerType.DAY,       MaterialDatePicker.MaterialDatePickerType.valueOf("DAY"));
        Assert.assertEquals(MaterialDatePicker.MaterialDatePickerType.MONTH_DAY, MaterialDatePicker.MaterialDatePickerType.valueOf("MONTH_DAY"));
        Assert.assertEquals(MaterialDatePicker.MaterialDatePickerType.YEAR,      MaterialDatePicker.MaterialDatePickerType.valueOf("YEAR"));
        Assert.assertEquals(MaterialDatePicker.MaterialDatePickerType.YEAR_MONTH_DAY,    MaterialDatePicker.MaterialDatePickerType.valueOf("YEAR_MONTH_DAY"));
    }

    @Test
    public void invokeMaterialVisibilityProperty() {
        Assert.assertEquals(Style.Visibility.VISIBLE,   com.google.gwt.dom.client.Style.Visibility.valueOf("VISIBLE"));
        Assert.assertEquals(Style.Visibility.HIDDEN,    com.google.gwt.dom.client.Style.Visibility.valueOf("HIDDEN"));
    }

}
