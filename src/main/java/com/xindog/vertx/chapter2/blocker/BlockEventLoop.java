package com.xindog.vertx.chapter2.blocker;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BlockEventLoop extends AbstractVerticle {

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new BlockEventLoop());
    }

    @Override
    public void start() {
        vertx.setTimer(1000, id -> {
            while (true) ;
        });
    }
}
