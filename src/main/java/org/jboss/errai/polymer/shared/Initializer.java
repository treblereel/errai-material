package org.jboss.errai.polymer.shared;

import gwt.material.design.client.MaterialDesignBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;

/**
 * @author Dmitrii Tikhomirov <chani@me.com>
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
