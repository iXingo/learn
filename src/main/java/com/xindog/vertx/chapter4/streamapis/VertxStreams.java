package com.xindog.vertx.chapter4.streamapis;

import io.vertx.core.Vertx;
import io.vertx.core.file.AsyncFile;
import io.vertx.core.file.OpenOptions;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class VertxStreams {

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        OpenOptions opts = new OpenOptions().setRead(true);
        vertx.fileSystem().open("build.gradle.kts", opts, ar -> {
            if (ar.succeeded()) {
                log.info(String.valueOf(ar.getClass()));
                AsyncFile file = ar.result();
                file.handler(buffer -> log.info("{}\n,{}",Thread.currentThread(),buffer.toString()))
                        .exceptionHandler(Throwable::printStackTrace)
                        .endHandler(done -> {
                            log.warn("DONE");
                            vertx.close();
                        });
            } else {
                ar.cause().printStackTrace();
            }
        });
        log.warn("Main Thread Finished");
    }
}
