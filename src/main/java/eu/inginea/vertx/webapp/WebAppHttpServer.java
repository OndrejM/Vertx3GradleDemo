package eu.inginea.vertx.webapp;

import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.ext.web.Router;

/**
 * HttpServer component, which listens to requests and forwards them to handler.
 * The idea is to encapsulate server configuration and execution into this class to make the code readable.
 * Also, only necessary methods are exposed from underlying httpServer vertx object.
 */
public class WebAppHttpServer {
    private final HttpServerOptions options;
    private final HttpServer server;

    public WebAppHttpServer(HttpServerVerticle.Config config, Vertx vertx) {
        options = new HttpServerOptions().setPort(config.getPort())
            .setHost(config.getHostName());
        server = vertx.createHttpServer(options);
    }

    public WebAppHttpServer listen() {
        server.listen();
        return this;
    }

    public String getHost() {
        return options.getHost();
    }

    public int getPort() {
        return options.getPort();
    }

    WebAppHttpServer router(Router router) {
        server.requestHandler(router::accept);
        return this;
    }
    
}
