package eu.inginea.vertx.vertxsupport;

import io.vertx.core.AbstractVerticle;

/**
 * Base class that helps to build verticles. Adds common functionality to all
 * verticles on top of AbstractVerticle.
 */
public abstract class VerticleBase extends AbstractVerticle {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * To be called at the start of each start method to initialize verticle.
     * <p>
     * Although it is awkward to remember to execute for each start method, it
     * is necessary to be able to choose which start method of the Verticle
     * interface to implement.
     */
    public void init() {
        if (logger.isDebugEnabled()) {
            logger.debug("Verticle " + getClass().getSimpleName() + " starting");
        }
    }
}
