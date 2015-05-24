package eu.inginea.vertx.vertxsupport;

import io.vertx.core.json.JsonObject;


/**
 * 
 */
public abstract class ConfigBase {
    protected JsonObject config;

    public ConfigBase(JsonObject config) {
        this.config = config;
    }
    
}
