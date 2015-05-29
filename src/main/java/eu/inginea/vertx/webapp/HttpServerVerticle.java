package eu.inginea.vertx.webapp;

import eu.inginea.vertx.vertxsupport.ConfigBase;
import eu.inginea.vertx.vertxsupport.VerticleBase;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;

public class HttpServerVerticle extends VerticleBase {

    private Config config;

    @Override
    public void start() throws Exception {
        super.init();
        logger.debug("reload");
        this.config = new Config(context.config());
        final HttpServerOptions serverOptions = new HttpServerOptions();
        serverOptions
            .setPort(config.getPort())
            .setHost(config.getHostName());
        final HttpServer server = vertx.createHttpServer(serverOptions);

        Router router = Router.router(vertx);

        router.route().handler(routingContext -> {

            // This handler will be called for every request
            HttpServerResponse response = routingContext.response();
            response.putHeader("content-type", "text/plain");

            // Write to the response and end it
            response.end("Hello World from Apex!");
        });
        logger.info(String.format("Listening to HTTP requests on %s:%d", serverOptions.getHost(), serverOptions.getPort()));
        server.requestHandler(router::accept).listen();
    }
    
    private static class Config extends ConfigBase {

        public Config(JsonObject config) {
            super(config);
        }
        
        int getPort() {
            return config.getInteger("port", 8080);
        }
        
        String getHostName() {
            return config.getString("hostname", "0.0.0.0");
        }
    }
}
