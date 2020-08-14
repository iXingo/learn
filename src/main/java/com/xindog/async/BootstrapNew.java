package com.xindog.async;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

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

        Task task = bootstrap.newTask();

        Wrapper wrapper = new Wrapper();
        wrapper.setTask(task);
        wrapper.setParam("hello");
        wrapper.addListener(System.out::println);

        CompletableFuture<Wrapper> future = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName());
            return bootstrap.doWork(wrapper);
        });
        try {
            future.get(800, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | TimeoutException | ExecutionException e) {
            wrapper.getListener().result("time out exception");
        }
        System.out.println(Thread.currentThread());

    }

    private Wrapper doWork(Wrapper wrapper) {
        Task task = wrapper.getTask();
        String result = task.doTask(wrapper.getParam());
        wrapper.getListener().result(result);
        return wrapper;
    }

    private Task newTask() {
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
