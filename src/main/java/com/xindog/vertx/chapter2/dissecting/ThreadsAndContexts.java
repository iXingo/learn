package com.xindog.vertx.chapter2.dissecting;

import io.vertx.core.Context;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThreadsAndContexts {

    private static final Logger logger = LoggerFactory.getLogger(ThreadsAndContexts.class);

    public static void main(String[] args) {
        createAndRun();
        dataAndExceptions();
    }

    private static void createAndRun() {
        Vertx vertx = Vertx.vertx();
        logger.warn("{}, {}", vertx.getOrCreateContext().getInstanceCount(), vertx.hashCode());

        vertx.getOrCreateContext()
                .runOnContext(v -> logger.info("ABC"));

        vertx.getOrCreateContext()
                .runOnContext(v -> logger.info("123"));
    }

    private static void dataAndExceptions() {
        Vertx vertx = Vertx.vertx();
        logger.warn("{}, {}", vertx.getOrCreateContext().getInstanceCount(), vertx.hashCode());
        Context ctx = vertx.getOrCreateContext();
        ctx.put("foo", "bar");

        ctx.exceptionHandler(t -> {
            //[vert.x-eventloop-thread-0]
            if ("Tada".equals(t.getMessage())) {
                logger.info("Got a _Tada_ exception");
            } else {
                logger.error("Woops", t);
            }
        });

        ctx.runOnContext(v -> {
            throw new RuntimeException("Tada");
        });

        ctx.runOnContext(v -> {
            logger.info("foo = {}", (String) ctx.get("foo"));
        });

        //worker thread[vert.x-worker-thread-0]
        ctx.executeBlocking(t ->{
            try {
                Thread.sleep(1000);
                logger.warn("====1000 seconds later");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
