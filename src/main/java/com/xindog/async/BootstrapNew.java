package com.xindog.async;

import java.util.concurrent.CompletableFuture;

/**
 * Created by Xindog.com(TM).
 * Author:  Shawn.Wang / i.am@shawn.wang
 * Date:    8/14/20
 * Time:    3:09 PM
 * Project: learn
 */
public class BootstrapNew {

    public static void main(String[] args) {
        BootstrapNew bootstrap = new BootstrapNew();

        Task<String> task = bootstrap.newTask();

        Wrapper<String> wrapper = new Wrapper<>();
        wrapper.setTask(task);
        wrapper.setParam("hello");
        wrapper.addHandler(System.out::println);

        CompletableFuture<Wrapper<String>> future = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "do work");
            System.out.printf("Thread_Name: %s, Daemon: %s%n", Thread.currentThread().getName(), Thread.currentThread().isDaemon());
            return bootstrap.doWork(wrapper);
        });

//        try {
//            System.out.println(Thread.currentThread() + "Future get");
////            future.get(1200, TimeUnit.MILLISECONDS);
//        } catch (InterruptedException | TimeoutException | ExecutionException e) {
//            System.out.println("is Canceled?"+future.isCancelled());
//            wrapper.getListener().result("time out exception");
//        }
        future.thenRun(()-> System.out.println("Future run again"+Thread.currentThread().getName())).whenComplete((wrapper2, throwable)-> {
            System.out.println(wrapper2);
        });
        future.whenComplete((wrapper1, throwable) -> System.out.println(wrapper1.getHandler()));
        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread());

    }

    private Wrapper<String> doWork(Wrapper<String> wrapper) {
        Task<String> task = wrapper.getTask();
        System.out.println(Thread.currentThread() + ",Task Start");
        String result = task.doTask(wrapper.getParam());
        System.out.println(Thread.currentThread() + ",Task Finish, notify listener");
        wrapper.getHandler().handle(result);
        System.out.println(wrapper.getHandler());
        System.out.println(Thread.currentThread() + ",Result noticed");
        return wrapper;
    }

    private Task<String> newTask() {
        return object -> {
            System.out.println(Thread.currentThread().getName());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return object + " world";
        };
    }

}
