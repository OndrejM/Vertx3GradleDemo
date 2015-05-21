package eu.inginea.vertx.webapp;

import eu.inginea.vertx.vertxsupport.BaseVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.apex.Router;

public class HttpServerVerticle extends BaseVerticle {

    @Override
    public void start() throws Exception {
        logger.info("Listening to HTTP requests on localhost:8080");
        final HttpServer server = vertx.createHttpServer();

        Router router = Router.router(vertx);

        router.route().handler(routingContext -> {

            // This handler will be called for every request
            HttpServerResponse response = routingContext.response();
            response.putHeader("content-type", "text/plain");

            // Write to the response and end it
            response.end("Hello World from Apex!");
        });

        server.requestHandler(router::accept).listen(8080);
    }
}
