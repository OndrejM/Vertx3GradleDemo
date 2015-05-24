package eu.inginea.vertx.vertxsupport;

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

}
