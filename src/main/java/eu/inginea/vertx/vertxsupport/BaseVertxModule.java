package eu.inginea.vertx.vertxsupport;

/**
 * Base class to help building modules. A module existed in Vertx 2 to group
 * multiple verticles. In vertx 3, it does not exist. In order to group
 * verticles and run them together, you may use a loader pattern: verticles of a
 * moudle are deployed using a special loader verticle. This class helps to
 * build such loader verticles.
 */
public class BaseVertxModule extends BaseVerticle {

}
