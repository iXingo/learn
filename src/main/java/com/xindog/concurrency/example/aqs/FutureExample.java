package com.xindog.concurrency.example.aqs;

import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Slf4j
public class FutureExample {

    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<String> future = executorService.submit(new MyCallable());
        String result = future.get();
        log.info(Instant.now() + "result：{}", result);
        log.info(Instant.now() + "do something in main");
        Thread.sleep(5000);
        result = future.get();
        log.info(Instant.now() + "result：{}", result);
        executorService.execute(new MyRunnable());
    }

    static class MyCallable implements Callable<String> {

        @Override
        public String call() throws Exception {
            log.info(Instant.now() + "do something in callable");
            Thread.sleep(10000);
            return Instant.now() + "Done";
        }
    }

    static class MyRunnable implements Runnable {

        @Override
        public void run() {
            log.info(Instant.now() + "do something in runable");
            try {
                Thread.sleep(5000);
                log.warn(Instant.now() + "Something happened");

                throw new RuntimeException();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
