package eu.inginea.vertx.vertxsupport;

import io.vertx.core.Context;
import io.vertx.core.json.JsonObject;


/**
 * 
 */
public abstract class ConfigBase {

    public static final String GLOBAL_CONFIG_NAME = "global";
    
    protected JsonObject config;

    public ConfigBase(JsonObject config) {
        if (config != null) {
            this.config = config;
        } else {
            this.config = new JsonObject();
        }
    }
    
    public boolean isDevelMode() {
        return getGlobalConfig().getBoolean("develMode", Boolean.FALSE);
    }

    protected JsonObject getGlobalConfig() {
        return config.getJsonObject(GLOBAL_CONFIG_NAME, new JsonObject());
    }
    
    protected JsonObject getSubConfig(String key) {
        JsonObject subConfig = config.getJsonObject(key, new JsonObject());
        if (!subConfig.containsKey(GLOBAL_CONFIG_NAME)) {
            subConfig.put(GLOBAL_CONFIG_NAME, getGlobalConfig());
        }
        return subConfig;
    }
    
}
