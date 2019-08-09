package com.xindog.concurrency.threadlocal;

/**
 * Created by Xindog.com(TM).
 * Author:  Shawn.Wang / i.am@shawn.wang
 * Date:    8/9/19
 * Time:    5:38 PM
 * Project: learn
 */
public class Demo {


    ThreadLocal<String> name = new ThreadLocal<>();

    public static void main(String[] args) {
        Demo demo = new Demo();

        Runnable runnable1 = () -> {
            System.out.println(demo.name);
        };

        Runnable runnable2 = () -> {
            System.out.println(demo.name);
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
