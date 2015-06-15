package eu.inginea.vertx.demowebapp;

import eu.inginea.vertx.vertxsupport.ConfigBase;
import eu.inginea.vertx.webapp.WebApp;
import io.vertx.core.Context;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.handler.sockjs.BridgeOptions;
import io.vertx.ext.web.handler.sockjs.SockJSHandler;

/**
 * Description of DemoWebApp
 */
public class DemoWebApp implements WebApp {

    private Vertx vertx;
    private Config config;

    public DemoWebApp(Config config, Vertx vertx) {
        this.vertx = vertx;
        this.config = config;
    }
    
    @Override
    public Router getRouter() {
        Router router = Router.router(vertx);
        router.route("/hello/*").handler(this::handleHelloWorldContent);
        router.route("/static/*").handler(createStaticHandler());
        router.route("/eventbus/*").handler(createSockjsEventBusHandler());
        
        return router;
    }

    /**
     * Example of a hello world handler - very raw access to building response.
     */
    private void handleHelloWorldContent(RoutingContext routingContext) {

        // This handler will be called for every request
        HttpServerResponse response = routingContext.response();
        response.putHeader("content-type", "text/plain");

        // Write to the response and end it
        response.end("Hello World from Vertx-web!");
    }

    
    /**
     * Example of using StaticHandler to server static resources (html, css, js).
     * Default folder to read static resources is webroot.
     * {@link http://vert-x3.github.io/docs/vertx-web/java/#_serving_static_resources}
     */
    private StaticHandler createStaticHandler() {
        StaticHandler handler = null;
        if (config.isDevelMode()) {
            // use resources on disk instead of classpath
            handler = StaticHandler.create("src/main/resources/webroot");
            // files can be changed while app is running in dev mode
            handler.setFilesReadOnly(false);
            // chcing of files in the browser disabled
            handler.setCachingEnabled(false);
        } else {
            handler = StaticHandler.create();
        }
        return handler;
    }

    private Handler<RoutingContext> createSockjsEventBusHandler() {
        SockJSHandler sockJSHandler = SockJSHandler.create(vertx);
        BridgeOptions options = new BridgeOptions();
        sockJSHandler.bridge(options);
        return sockJSHandler;
    }
    
    public static class Config extends ConfigBase {

        public Config(JsonObject config) {
            super(config);
        }
        
    } 
}
