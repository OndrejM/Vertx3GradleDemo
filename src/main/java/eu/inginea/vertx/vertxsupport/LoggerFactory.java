package eu.inginea.vertx.vertxsupport;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Map;

/**
 * Custom factory to enable extend vertx logger.
 */
public class LoggerFactory {

    private static final Map<io.vertx.core.logging.Logger, Logger> loggerWrappers
            = Collections.synchronizedMap(new IdentityHashMap<>());

    
    public static Logger getLogger(final Class<?> clazz) {
        final io.vertx.core.logging.Logger vertxLogger = io.vertx.core.logging.LoggerFactory.getLogger(clazz);
        return getLoggerWrapper(vertxLogger);
    }

    public static Logger getLogger(final String name) {
        final io.vertx.core.logging.Logger vertxLogger = io.vertx.core.logging.LoggerFactory.getLogger(name);
        return getLoggerWrapper(vertxLogger);
    }

    private static Logger getLoggerWrapper(final io.vertx.core.logging.Logger vertxLogger) {
        Logger logger = loggerWrappers.get(vertxLogger);
        if (logger == null) {
            logger = new Logger(vertxLogger);
            loggerWrappers.putIfAbsent(vertxLogger, logger);
        }
        return logger;
    }

}
