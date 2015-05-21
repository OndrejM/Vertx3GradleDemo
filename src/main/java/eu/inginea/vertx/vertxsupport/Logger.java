package eu.inginea.vertx.vertxsupport;

/**
 * Custom logger to enable to extend vertx logger, e.g. by writing all log
 * messages to a file in separate worker verticle, so that logging does not block.
 */
public class Logger {

    private final io.vertx.core.logging.Logger vertxLogger;

    public Logger(io.vertx.core.logging.Logger vertxLogger) {
        this.vertxLogger = vertxLogger;
    }

    public boolean isInfoEnabled() {
        return vertxLogger.isInfoEnabled();
    }

    public boolean isDebugEnabled() {
        return vertxLogger.isDebugEnabled();
    }

    public boolean isTraceEnabled() {
        return vertxLogger.isTraceEnabled();
    }

    public void fatal(Object message) {
        vertxLogger.fatal(message);
    }

    public void fatal(Object message, Throwable t) {
        vertxLogger.fatal(message, t);
    }

    public void error(Object message) {
        vertxLogger.error(message);
    }

    public void error(Object message, Throwable t) {
        vertxLogger.error(message, t);
    }

    public void warn(Object message) {
        vertxLogger.warn(message);
    }

    public void warn(Object message, Throwable t) {
        vertxLogger.warn(message, t);
    }

    public void info(Object message) {
        vertxLogger.info(message);
    }

    public void info(Object message, Throwable t) {
        vertxLogger.info(message, t);
    }

    public void debug(Object message) {
        vertxLogger.debug(message);
    }

    public void debug(Object message, Throwable t) {
        vertxLogger.debug(message, t);
    }

    public void trace(Object message) {
        vertxLogger.trace(message);
    }

    public void trace(Object message, Throwable t) {
        vertxLogger.trace(message, t);
    }

}
