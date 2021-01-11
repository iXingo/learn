package com.xindog.async;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by Xindog.com(TM).
 * Author:  Shawn.Wang / i.am@shawn.wang
 * Date:    8/14/20
 * Time:    2:21 PM
 * Project: learn
 */
@Slf4j
public class Bootstrap {
    public static void main(String[] args) {
        Bootstrap bootstrap = new Bootstrap();

        Task<String> task = bootstrap.newTask();

        Wrapper<String> wrapper = new Wrapper<>();
        wrapper.setTask(task);
        wrapper.setParam("hello");

        bootstrap.doWork(wrapper).addHandler(result -> {
            log.info("[Start]: Add Listener " + System.currentTimeMillis());
            log.info(result);
            log.info("[Finish]: Add Listener " + System.currentTimeMillis());
        });

        log.error(String.valueOf(wrapper));
        log.error("End");

    }

    private Wrapper<String> doWork(Wrapper<String> wrapper) {
        new Thread(() -> {
            log.info("[start]: Do Work " + System.currentTimeMillis());
            Task<String> task = wrapper.getTask();
            String result = task.doTask(wrapper.getParam());
            wrapper.getHandler().handle(result);
            log.info("[Finish]: Do Work ");
        }).start();
        log.error(String.valueOf(wrapper));
        return wrapper;
    }

    private Task<String> newTask() {
        return object -> {
            log.info("This is task Content");
            try {
                log.info("[start]: do Task - Sleeping" + System.currentTimeMillis());
                Thread.sleep(1000);
                log.info("[Finish]: do Task - Waking" + System.currentTimeMillis());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("new Task Result " + System.currentTimeMillis());
            return object + " world";
        };
    }
}
