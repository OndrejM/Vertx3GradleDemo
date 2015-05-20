package eu.inginea.vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.impl.LoggerFactory;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
public class HelloWorldVerticle extends AbstractVerticle {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void start() throws Exception {
      // Create an HTTP server which simply returns "Hello World!" to each request.
        logger.info("Listening to HTTP requests on localhost:8080");
        vertx.createHttpServer().requestHandler(req -> req.response().end("Hello World!")).listen(8080);
    }
}
