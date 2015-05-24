package eu.inginea.vertx;

import eu.inginea.vertx.webapp.WebAppModule;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.json.JsonObject;
import java.util.function.Consumer;

/**
 *
 * @author media
 */
public class AppLauncher {

    public static void main(String[] args) {
        /* vertxOptions configure how vertx is executed - cluster, number of threads in pool, etc.
         Can be configured using JSON - best pattern is to pass JSON configuration/file as a parameter/property
         */
        final VertxOptions vertxOptions = new VertxOptions();

        // create core vertx framework instance (container)
        Vertx vertx = Vertx.vertx(vertxOptions);

        // deploy verticles, usually loader verticles (modules) that load other verticles
        deployVerticles(vertx);

    }

    private static void deployVerticles(Vertx vertx) {

        DeploymentOptions deploymentOptions = newCommonDeploymentOptions();
        configureWebAppModule(deploymentOptions, (options) -> {
            vertx.deployVerticle(WebAppModule.class.getName(), options);
        });
    }

    /**
     * Creates common deployment options - should be tuned for environment E.g.
     * in development, redeploy is true.
     * <p>
     * Can be configured using JSON - best pattern is to pass JSON
     * configuration/file as a parameter/property.
     * <p>
     * For development, it would be also good to implement a scanner for changes
     * in config and automatically redeply verticles if config changed
     */
    // TODO implement verticle redeploy mechanism when config changes on the fly
    private static DeploymentOptions newCommonDeploymentOptions() {
        final DeploymentOptions deploymentOptions = new DeploymentOptions();
        deploymentOptions.setRedeploy(true);
        return deploymentOptions;
    }

    /**
     * Calls setConfig with custom configuration for the module
     * <p>
     * Best pattern is to configure using external JSON file. Path to the file
     * can be passed as a parrameter/property, or even better, path to
     * configuration directory can be passed, which contains configuration for
     * all modules a file per module
     * <p>
     * Configuration for a module is split to configurations for single
     * verticles, e.g. server. Each verticle receives only its configuration.
     * Configuration for every verticle contains option load - when it is false,
     * verticle is not loaded
     */
    private static void configureWebAppModule(DeploymentOptions deploymentOptions, Consumer<DeploymentOptions> consumer) {
        final String jsonConfig = "{'server' : {'port':8081}}".replaceAll("'", "\"");
        deploymentOptions.setConfig(new JsonObject(jsonConfig));
        consumer.accept(deploymentOptions);
    }

}
