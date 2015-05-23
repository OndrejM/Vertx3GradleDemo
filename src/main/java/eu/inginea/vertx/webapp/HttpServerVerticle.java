package eu.inginea.vertx.webapp;

import eu.inginea.vertx.vertxsupport.BaseVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.apex.Router;

public class HttpServerVerticle extends BaseVerticle {

    @Override
    public void start() throws Exception {
        int port = context.config().getInteger("port", 8080);
        logger.info(String.format("Listening to HTTP requests on localhost:%d", port));
        final HttpServer server = vertx.createHttpServer();

        Router router = Router.router(vertx);

        router.route().handler(routingContext -> {

            // This handler will be called for every request
            HttpServerResponse response = routingContext.response();
            response.putHeader("content-type", "text/plain");

            // Write to the response and end it
            response.end("Hello World from Apex!");
        });

        // TODO configure verticle
        server.requestHandler(router::accept).listen(port);
    }
}
