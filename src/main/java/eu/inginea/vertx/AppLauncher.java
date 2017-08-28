package eu.inginea.vertx;

import eu.inginea.vertx.vertxsupport.VerticleBase;
import eu.inginea.vertx.webapp.WebAppModule;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.json.JsonObject;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author media
 */
public class AppLauncher extends VerticleBase {

    public static void main(String[] args) {
        initLogging();

        /* vertxOptions configure how vertx is executed - cluster, number of threads in pool, etc.
         Can be configured using JSON - best pattern is to pass JSON configuration/file as a parameter/property
         */
        VertxOptions vertxOptions = new VertxOptions();

        // create core vertx framework instance (container)
        Vertx vertx = Vertx.vertx(vertxOptions);

        // deploy AppLauncher as top level loader verticle, which loads other loader verticles (modules) that load other verticles
        // NOTE: do not deploy using new AppLauncher() - redeploy does not work, see the code, where redeployer is directly null
        vertx.deployVerticle(verticleName(), topLevelDeploymentOptions());
    }

    private static String verticleName() {
        return AppLauncher.class.getName();
    }

    @Override
    public void start() throws Exception {
        init();
        deployVerticles(vertx);
    }

    private void deployVerticles(Vertx vertx) {

        DeploymentOptions deploymentOptions = new DeploymentOptions();
        final String webAppModuleName = WebAppModule.class.getName();
        vertx.undeploy(webAppModuleName, (AsyncResult) -> {
            configureWebAppModule(deploymentOptions, (options) -> {
                vertx.deployVerticle(webAppModuleName, options);
            });
        });
    }

    /**
     * Creates deployment options for top level deployment of initial Verticle -
     * should be tuned for environment. E.g. in development, redeploy is true.
     * <p>
     * Can be configured using JSON - best pattern is to pass JSON
     * configuration/file as a parameter/property. For automatic redeploy,
     * development config file should be on classpath.
     * <p>
     * <b>Redeploy</b> is valid only for top-level deployment. All resources in
     * classpath will be scanned for changes, included configuration files. When
     * application is built and executed using gradle, it will scan for changes
     * in build/classes and build/resources.
     */
    private static DeploymentOptions topLevelDeploymentOptions() {
        return new DeploymentOptions();
                //.setRedeploy(true);
    }

    private void configureWebAppModule(DeploymentOptions deploymentOptions, Consumer<DeploymentOptions> consumer) {
        String moduleName = "webAppModule";
        configureModule(moduleName, deploymentOptions, consumer);
    }

    /**
     * Calls setConfig with custom configuration for the module
     * <p>
     * Best pattern is to configure using external JSON file. Path to the file
     * can be passed as a parameter/property, or even better, path to
     * configuration directory can be passed, which contains configuration for
     * all modules a file per module
     * <p>
     * Configuration for a module is split to configurations for single
     * verticles, e.g. server. Each verticle receives only its configuration.
     * Configuration for every verticle contains option load - when it is false,
     * verticle is not loaded
     */
    private void configureModule(String moduleName, DeploymentOptions deploymentOptions, Consumer<DeploymentOptions> consumer) {
        String configPath = "config/" + moduleName + ".json";
        URL resource = getClass().getClassLoader().getResource(configPath);
        if (resource != null) {
            try {
                warnPotentiallyBlocking("Reading config file in blocking way, should be refactored to a worker verticle to unblock loading other modules");
                byte[] fileBytes = Files.readAllBytes(Paths.get(resource.toURI()));
                String jsonConfig = new String(fileBytes, StandardCharsets.UTF_8);
                JsonObject config = new JsonObject(jsonConfig);
                deploymentOptions.setConfig(config);
            } catch (URISyntaxException | IOException ex) {
                logger.warn("Module " + moduleName + ": config resource " + configPath + " could not be loaded, continuing with default settings.");
            }
        } else {
            logger.warn("Module " + moduleName + ": config resource " + configPath + " does not exist, continuing with default settings.");
        }
        consumer.accept(deploymentOptions);
    }

    /* Initialize logging. This one dynamically configures JUL. 
     Better pattern is to configure logging by a config file 
     so that no direct dependency on underlying logging API is introduced.
     This is a temporary solution.
     */
    private static void initLogging() {
        String rootPackageOfThisApp = AppLauncher.class.getPackage().getName();
        Logger rootAppLogger = Logger.getLogger(rootPackageOfThisApp);
        rootAppLogger.setLevel(Level.ALL);
        getHandler(ConsoleHandler.class).ifPresent((consoleHandler)
                -> consoleHandler.setLevel(Level.ALL)
        );
    }

    private static <T> Optional<T> getHandler(Class<T> cls) {
        Logger rootLogger = Logger.getGlobal();
        Handler[] handlers = rootLogger.getHandlers();
        for (Handler handler : handlers) {
            if (cls.isInstance(handler)) {
                T clsHandler = cls.cast(handler);
                return Optional.of(clsHandler);
            }
        }
        return Optional.empty();
    }

}
