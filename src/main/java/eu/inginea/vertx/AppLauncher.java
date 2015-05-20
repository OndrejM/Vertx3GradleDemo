package eu.inginea.vertx;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

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
        /* deployment options - should be tuned for environment
           E.g. in development, redeploy is true
           Can be configured using JSON - best pattern is to pass JSON configuration/file as a parameter/property
        */
        final DeploymentOptions deploymentOptions = new DeploymentOptions();
        deploymentOptions.setRedeploy(true);
        
        vertx.deployVerticle(HelloWorldVerticle.class.getName(), deploymentOptions);
    }
}
