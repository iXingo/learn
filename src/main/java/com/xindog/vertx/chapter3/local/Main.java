package com.xindog.vertx.chapter3.local;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;

public class Main {

  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle("com.xindog.vertx.chapter3.HeatSensor", new DeploymentOptions().setInstances(4));
    vertx.deployVerticle("com.xindog.vertx.chapter3.Listener");
    vertx.deployVerticle("com.xindog.vertx.chapter3.SensorData");
    vertx.deployVerticle("com.xindog.vertx.chapter3.HttpServer");
  }
}
