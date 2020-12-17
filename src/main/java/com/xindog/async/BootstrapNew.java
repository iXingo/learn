package com.xindog.async;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeoutException;

/**
 * Created by Xindog.com(TM).
 * Author:  Shawn.Wang / i.am@shawn.wang
 * Date:    8/14/20
 * Time:    3:09 PM
 * Project: learn
 */
@Slf4j
public class BootstrapNew {

    public static void main(String[] args) throws ExecutionException {
        BootstrapNew bootstrap = new BootstrapNew();

        Task<String> task = bootstrap.newTask();

        Wrapper<String> wrapper = new Wrapper<>();
        wrapper.setTask(task);
        wrapper.setParam("hello");
        wrapper.addHandler(log::error);

        CompletableFuture<Wrapper<String>> future = CompletableFuture.supplyAsync(() -> {
            log.info("Do work now");
            return bootstrap.doWork(wrapper);
        });

        future.thenRun(
                () -> {log.warn("Then Run");
                System.exit(0);}
        );
        future.whenComplete((wrapper1, throwable) -> {
                log.info(wrapper1.getHandler().toString());
                log.warn("When Complete1, {}", wrapper1.toString());
        });
        try {
            log.info("1 Finish, {}", Thread.currentThread());
            Thread.currentThread().join();
            log.info("2 Finish, {}", Thread.currentThread());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("Exit");


    }

    private Wrapper<String> doWork(Wrapper<String> wrapper) {
        Task<String> task = wrapper.getTask();
        log.info("Task Start");
        String result = task.doTask(wrapper.getParam());
        log.info("Task Finish, notify listener");
        wrapper.getHandler().handle(result);
        log.info(wrapper.getHandler().toString());
        log.info("Result noticed");
        return wrapper;
    }

    private Task<String> newTask() {
        return object -> {
            log.info("This is Task Content");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("This is End of Task Content");
            return object + " world";
        };
    }

}
