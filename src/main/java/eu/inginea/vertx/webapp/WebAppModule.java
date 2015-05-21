package eu.inginea.vertx.webapp;

import eu.inginea.vertx.vertxsupport.BaseVertxModule;

/**
 * Vertx module that represents a complete web application frontend.
 */
public class WebAppModule extends BaseVertxModule {

    @Override
    public void start() throws Exception {
        super.init();
        vertx.deployVerticle(HttpServerVerticle.class.getName());
    }

}
