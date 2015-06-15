package eu.inginea.vertx.vertxsupport;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.json.JsonObject;

/**
 * Base class to help building modules. A module existed in Vertx 2 to group
 * multiple verticles. In vertx 3, it does not exist. In order to group
 * verticles and run them together, you may use a loader pattern: verticles of a
 * moudle are deployed using a special loader verticle. This class helps to
 * build such loader verticles.
 */
public class VertxModuleBase extends VerticleBase {
    public static boolean shouldLoadVerticle(final JsonObject verticleConfig) {
        boolean shouldLoadAsDefault = true;
        final Boolean load = verticleConfig.getBoolean("load");
        return load != null ? load : shouldLoadAsDefault;
    }

    /**
     * Load a child verticle using verticle configuration stored under configName.
     * Copies global configuration from this verticle onto the configuration of child verticle.
     * 
     * @param configName Name of sub-configuration to be used with child verticle
     * @param verticleName Name of veticle to load
     */
    protected void loadVerticle(final String configName, final String verticleName) {
        JsonObject serverConfig = context.config().getJsonObject(configName);
        JsonObject globalConfig = context.config().getJsonObject(ConfigBase.GLOBAL_CONFIG_NAME);
        if (shouldLoadVerticle(serverConfig)) {
            serverConfig.put(ConfigBase.GLOBAL_CONFIG_NAME, globalConfig);
            final DeploymentOptions serverOptions = new DeploymentOptions().setConfig(serverConfig);
            vertx.deployVerticle(verticleName, serverOptions);
        }
    }

}
