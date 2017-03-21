package org.jboss.errai.polymer.shared;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Dmitrii Tikhomirov <chani@me.com>
 * Created by treblereel on 3/10/17.
 */
public class MaterialWidgetDefinition {

    private Class clazz;
    private String tag;

    private Boolean extendsMaterialWidget = false;

    private Map<String, Class> methods = new HashMap<>();

    /**
     * TODO Fix string value
     * @param tag
     * @param clazz
     * @param extendsMaterialWidget
     */
    public MaterialWidgetDefinition(String tag, Class clazz, Boolean extendsMaterialWidget){
        this.tag = tag;
        this.clazz = clazz;
        this.extendsMaterialWidget = extendsMaterialWidget;

    }

    public Class getClazz() {
        return clazz;
    }

    public String getTag() {
        return tag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MaterialWidgetDefinition)) return false;

        MaterialWidgetDefinition that = (MaterialWidgetDefinition) o;

        if (!getClazz().equals(that.getClazz())) return false;
        return getTag().equals(that.getTag()) && (getMethods() != null ? getMethods().equals(that.getMethods()) : that.getMethods() == null);

    }

    @Override
    public int hashCode() {
        int result = getClazz().hashCode();
        result = 31 * result + getTag().hashCode();
        result = 31 * result + (getMethods() != null ? getMethods().hashCode() : 0);
        return result;
    }

    public Map<String, Class> getMethods() {
        return methods;
    }

    public void setMethods(Map<String, Class> methods) {
        this.methods = methods;
    }

    public Boolean getExtendsMaterialWidget() {
        return extendsMaterialWidget;
    }

}
