package eu.inginea.vertx.demowebapp;

import eu.inginea.vertx.webapp.WebApp;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.StaticHandler;

/**
 * Description of DemoWebApp
 */
public class DemoWebApp implements WebApp {

    private Vertx vertx;

    public DemoWebApp(Vertx vertx) {
        this.vertx = vertx;
    }
    
    @Override
    public Router getRouter() {
        Router router = Router.router(vertx);
        router.route("/hello/*").handler(this::handleHelloWorldContent);
        router.route("/static/*").handler(createStaticHandler());
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
        return StaticHandler.create();
    }
}
