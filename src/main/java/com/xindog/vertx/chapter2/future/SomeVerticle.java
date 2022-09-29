package com.xindog.vertx.chapter2.future;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SomeVerticle extends AbstractVerticle {

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new SomeVerticle());
    }

    @Override
    public void start(Promise<Void> promise) {   // <1>
        vertx.createHttpServer()
                .requestHandler(req -> req.response().end("Ok"))
                .listen(8082, ar -> {
                    if (ar.succeeded()) {
                        // <2>
                        log.warn("Succeed to listen 8082");
                        promise.complete();   // <3>
                    } else {
                        log.warn("Failed to listen 8082" + ar.cause());
                        promise.fail(ar.cause()); // <4>
                    }
                });
    }
}
