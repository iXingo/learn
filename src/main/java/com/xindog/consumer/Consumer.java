package com.xindog.consumer;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class Consumer {

    public static void main(String[] args) {
        ExecutorService executors = Executors.newCachedThreadPool();
        executors.submit(() -> {
            System.out.println("Test1");
            try {
                log.warn("Test1" + System.currentTimeMillis());
                Thread.sleep(2000);
                log.warn("Test1" + System.currentTimeMillis());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        executors.submit(() -> {
            System.out.println("Test2");
            try {
                log.warn("Test2" + System.currentTimeMillis());
                Thread.sleep(2000);
                log.warn("Test2" + System.currentTimeMillis());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        executors.shutdown();
        log.error("shundown");
    }
}
