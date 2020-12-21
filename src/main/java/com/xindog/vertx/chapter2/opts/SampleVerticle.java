package com.xindog.vertx.chapter2.opts;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
public class SampleVerticle extends AbstractVerticle {

  private final Logger logger = LoggerFactory.getLogger(SampleVerticle.class);

  @Override
  public void start() {
    logger.info("n = {}", config().getInteger("n", -1));
  }

  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    for (int n = 0; n < 4; n++) {
      JsonObject conf = new JsonObject().put("n", n);
      DeploymentOptions opts = new DeploymentOptions()
        .setConfig(conf)
        .setInstances(n);
      vertx.deployVerticle("chapter2.opts.SampleVerticle", opts);
    }
    for (int i = 0; i < 4; i++) {
      vertx.runOnContext(t->{
        try {
          Thread.sleep(5000);
          log.error("Finished");
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      });
    }
  }
}
