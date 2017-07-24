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

import com.google.gwt.user.client.ui.RootPanel;
import gwt.material.design.client.ui.MaterialPanel;

import javax.ejb.Startup;
import javax.inject.Singleton;

/**
 * @author Dmitrii Tikhomirov <chani@me.com>
 *         Created by treblereel on 7/20/17.
 */

@Startup
@Singleton
public class GwtMaterialContainer extends MaterialPanel{


    public GwtMaterialContainer(){
        RootPanel.get().add(this);
    }

}
