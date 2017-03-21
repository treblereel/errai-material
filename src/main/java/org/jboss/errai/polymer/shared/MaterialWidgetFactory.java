package org.jboss.errai.polymer.shared;

import com.google.gwt.user.client.ui.Widget;

/**
 * @author Dmitrii Tikhomirov <chani@me.com>
 * Created by treblereel on 3/10/17.
 */
public interface MaterialWidgetFactory {

    java.util.Optional<MaterialWidgetDefinition> getWidgetDefIfExist(String tag);

    java.util.Optional<Tuple<String,Class>> getMethodDefIfExist(String tag, String method);

    Widget invoke(String tag);


}
