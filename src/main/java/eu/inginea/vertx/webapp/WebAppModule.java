package eu.inginea.vertx.webapp;

import eu.inginea.vertx.vertxsupport.VertxModuleBase;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.json.JsonObject;

/**
 * Vertx module that represents a complete web application frontend.
 */
public class WebAppModule extends VertxModuleBase {

    @Override
    public void start() throws Exception {
        super.init();
        loadHttpServer();
    }

    private void loadHttpServer() {
        final String configName = "server";
        final String verticleName = HttpServerVerticle.class.getName();
        loadVerticle(configName, verticleName);
    }

    private void loadVerticle(final String configName, final String verticleName) {
        final JsonObject serverConfig = context.config().getJsonObject(configName);
        if (shouldLoadVerticle(serverConfig)) {
            final DeploymentOptions serverOptions = new DeploymentOptions().setConfig(serverConfig);
            vertx.deployVerticle(verticleName, serverOptions);
        }
    }

}
