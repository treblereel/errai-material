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

import gwt.material.design.client.MaterialDesignDebugBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Dmitrii Tikhomirov <chani@me.com>
 *
 * Created by treblereel on 3/7/17.
 */
public class GwtMaterialInitializer extends MaterialDesignDebugBase {
    private static GwtMaterialInitializer INSTANCE = new GwtMaterialInitializer();
    private final Logger logger = LoggerFactory.getLogger(GwtMaterialInitializer.class);
    private boolean loaded = false;

    public static GwtMaterialInitializer get(){
        return INSTANCE;
    }

    public void check() {
        logger.warn("GwtMaterialInitializer check");
        if (!loaded) {
            load();
            loaded = true;
        }
    }

}
