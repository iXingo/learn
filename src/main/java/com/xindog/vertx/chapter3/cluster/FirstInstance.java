package com.xindog.vertx.chapter3.cluster;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

import java.util.logging.LogManager;

public class FirstInstance {

    private static final Logger logger = LoggerFactory.getLogger(FirstInstance.class);

    public static void main(String[] args) {

        LogManager.getLogManager().reset();
        SLF4JBridgeHandler.install();

        Vertx.clusteredVertx(new VertxOptions(), ar -> {
            if (ar.succeeded()) {
                logger.info("First instance has been started");
                Vertx vertx = ar.result();
                logger.warn("Deploying the first instance, {}", vertx.hashCode());
                vertx.deployVerticle("com.xindog.vertx.chapter3.HeatSensor", new DeploymentOptions().setInstances(4));
                vertx.deployVerticle("com.xindog.vertx.chapter3.HttpServer");
            } else {
                logger.error("Could not start", ar.cause());
            }
        });
    }
}
