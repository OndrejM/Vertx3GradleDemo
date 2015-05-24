package eu.inginea.vertx;

import eu.inginea.vertx.vertxsupport.VerticleBase;
import eu.inginea.vertx.webapp.WebAppModule;
import io.vertx.core.AsyncResult;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.json.JsonObject;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 *
 * @author media
 */
public class AppLauncher extends VerticleBase {

    public static void main(String[] args) {
        /* vertxOptions configure how vertx is executed - cluster, number of threads in pool, etc.
         Can be configured using JSON - best pattern is to pass JSON configuration/file as a parameter/property
         */
        final VertxOptions vertxOptions = new VertxOptions();

        // create core vertx framework instance (container)
        Vertx vertx = Vertx.vertx(vertxOptions);

        // deploy AppLauncher as root loader verticle, which loads other loader verticles (modules) that load other verticles
        vertx.deployVerticle(new AppLauncher(), newCommonDeploymentOptions());

    }

    @Override
    public void start() throws Exception {
        initLogging();
        init();
        deployVerticles(vertx);
    }
    
    

    private void deployVerticles(Vertx vertx) {

        DeploymentOptions deploymentOptions = newCommonDeploymentOptions();
        final String webAppModuleName = WebAppModule.class.getName();
        vertx.undeploy(webAppModuleName, (AsyncResult) -> {
            configureWebAppModule(deploymentOptions, (options) -> {
                vertx.deployVerticle(webAppModuleName, options);
            });
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
    private void configureWebAppModule(DeploymentOptions deploymentOptions, Consumer<DeploymentOptions> consumer) {
        final String jsonConfig = "{'server' : {'port':8082}}".replaceAll("'", "\"");
        deploymentOptions.setConfig(new JsonObject(jsonConfig));
        consumer.accept(deploymentOptions);
    }

    private void initLogging() {
        final Logger rootLogger = LogManager.getLogManager().getLogger("");
        rootLogger.setLevel(Level.FINEST);
    }

}
