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

package org.jboss.errai.polymer.shared;

import gwt.material.design.client.MaterialDesignBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;

/**
 * @author Dmitrii Tikhomirov <chani@me.com>
 *
 * Created by treblereel on 3/7/17.
 */
@Singleton
public class Initializer extends MaterialDesignBase {
    private final Logger logger = LoggerFactory.getLogger(Initializer.class);
    private boolean loaded = false;

    @PostConstruct
    public void init() {
        loadJs();
    }

    private void loadJs() {
        logger.warn(" load material javascripts ");
        if (!loaded) {
            load();
            loaded = true;
        }
    }


}
