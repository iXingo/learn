package com.xindog.consumer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Consumer {

    public static void main(String[] args) {
        ExecutorService executors = Executors.newCachedThreadPool();
        executors.submit(() -> {
            System.out.println("Test1");
            try {
                System.out.println("Test1" + System.currentTimeMillis());
                Thread.sleep(2000);
                System.out.println("Test1" + System.currentTimeMillis());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        executors.submit(() -> {
            System.out.println("Test2");
            try {
                System.out.println("Test2" + System.currentTimeMillis());
                Thread.sleep(2000);
                System.out.println("Test2" + System.currentTimeMillis());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        executors.shutdown();
    }
}
