/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.vertx.example;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Starter;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

/**
 *
 * @author media
 */
public class AppLauncher {
    public static void main(String[] args) {
        //Starter.main(new String[] { "run", HelloWorldVerticle.class.getName()});
        Vertx vertx = Vertx.vertx();
        final DeploymentOptions deploymentOptions = new DeploymentOptions();
        deploymentOptions.setRedeploy(true);
        vertx.deployVerticle(HelloWorldVerticle.class.getName(), deploymentOptions);
        
    }
}
