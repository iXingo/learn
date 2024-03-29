package com.xindog.vertx.chapter2.dissecting;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Context;
import io.vertx.core.Vertx;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

public class MixedThreading extends AbstractVerticle {

    private final Logger logger = LoggerFactory.getLogger(MixedThreading.class);

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new MixedThreading());
    }

    @Override
    public void start() {
        Context context = vertx.getOrCreateContext();
        new Thread(() -> {
            try {
                run(context);
            } catch (InterruptedException e) {
                logger.error("Oops, {}", ExceptionUtils.getStackTrace(e));
            }
        }).start();
    }

    private void run(Context context) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        logger.info("I am in a non-Vert.x thread, {}, {}", context.isWorkerContext(), context.isEventLoopContext());
        context.runOnContext(v -> {
            logger.info("I am on the event-loop, {}, {}", context.isWorkerContext(), context.isEventLoopContext());
            vertx.setTimer(1000, id -> {
                logger.info("This is the final countdown");
                latch.countDown();
            });
        });
        logger.info("Waiting on the countdown latch...");
        latch.await();
        logger.info("Bye!");
    }
}
