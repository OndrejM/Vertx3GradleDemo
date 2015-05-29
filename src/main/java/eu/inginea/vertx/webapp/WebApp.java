package eu.inginea.vertx.webapp;

import io.vertx.ext.web.Router;

/**
 * Interface for encapsulated web application, which can be deployed into a http server. Resembles Java EE WAR module.
 */
public interface WebApp {
    Router getRouter();
}
