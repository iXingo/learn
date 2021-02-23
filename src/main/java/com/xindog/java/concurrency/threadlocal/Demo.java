package com.xindog.java.concurrency.threadlocal;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by Xindog.com(TM).
 * Author:  Shawn.Wang / i.am@shawn.wang
 * Date:    8/9/19
 * Time:    5:38 PM
 * Project: learn
 */
@Slf4j
public class Demo {


    ThreadLocal<String> name = new ThreadLocal<>();

    public static void main(String[] args) {
        Demo demo = new Demo();

        Runnable runnable1 = () -> {
            demo.name.set("1");
            Thread t = Thread.currentThread();
            log.warn(demo.name.get());
        };

        Runnable runnable2 = () -> {
            demo.name.set("2");
            Thread t = Thread.currentThread();
            log.warn(demo.name.get());
        };

        Thread thread1 = new Thread(runnable1);
        Thread thread2 = new Thread(runnable2);

        thread1.start();
        thread2.start();
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println();

    }


}
