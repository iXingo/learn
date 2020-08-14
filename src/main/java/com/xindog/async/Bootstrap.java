package com.xindog.async;

/**
 * Created by Xindog.com(TM).
 * Author:  Shawn.Wang / i.am@shawn.wang
 * Date:    8/14/20
 * Time:    2:21 PM
 * Project: learn
 */
public class Bootstrap {
    public static void main(String[] args) {
        Bootstrap bootstrap = new Bootstrap();

        Task task = bootstrap.newTask();

        Wrapper wrapper = new Wrapper();
        wrapper.setTask(task);
        wrapper.setParam("hello");

        bootstrap.doWork(wrapper).addHandler(result -> {
            System.out.printf("[Thread-%s] Add Listener \n", Thread.currentThread().getName());
            System.out.println("[Start]: Add Listener " + System.currentTimeMillis());
            System.out.println(result);
            System.out.println("[Finish]: Add Listener " + System.currentTimeMillis());
        });

        System.err.println(Thread.currentThread().getName());

    }

    private Wrapper doWork(Wrapper wrapper) {
        new Thread(() -> {
            System.out.printf("[Thread-%s] Do Work\n",Thread.currentThread().getName());
            System.out.println("[start]: Do Work " + System.currentTimeMillis());
            Task task = wrapper.getTask();
            String result = task.doTask(wrapper.getParam());
            wrapper.getHandler().handle(result);
            System.out.println("[Finish]: Do Work " + System.currentTimeMillis());
        }).start();

        return wrapper;
    }

    private Task newTask() {
        return object -> {
            System.out.printf("[Thread-%s] Task Content\n",Thread.currentThread().getName());
            try {
                System.out.println("[start]: do Task - Sleeping" + System.currentTimeMillis());
                Thread.sleep(1000);
                System.out.println("[Finish]: do Task - Waking" + System.currentTimeMillis());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("new Task Result " + System.currentTimeMillis());
            return object + " world";
        };
    }
}
