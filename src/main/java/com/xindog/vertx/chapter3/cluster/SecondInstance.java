package com.xindog.vertx.chapter3.cluster;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

import java.util.logging.LogManager;

public class SecondInstance {

    private static final Logger logger = LoggerFactory.getLogger(SecondInstance.class);

    public static void main(String[] args) {

        LogManager.getLogManager().reset();
        SLF4JBridgeHandler.install();

        Vertx.clusteredVertx(new VertxOptions(), ar -> {
            if (ar.succeeded()) {
                logger.info("Second instance has been started");
                Vertx vertx = ar.result();
                logger.warn("Deploying the second instance, {}", vertx.hashCode());
                vertx.deployVerticle("com.xindog.vertx.chapter3.HeatSensor", new DeploymentOptions().setInstances(4));
                vertx.deployVerticle("com.xindog.vertx.chapter3.Listener");
                vertx.deployVerticle("com.xindog.vertx.chapter3.SensorData");
                JsonObject conf = new JsonObject().put("port", 8081);
                vertx.deployVerticle("com.xindog.vertx.chapter3.HttpServer", new DeploymentOptions().setConfig(conf));
            } else {
                logger.error("Could not start", ar.cause());
            }
        });
    }
}
