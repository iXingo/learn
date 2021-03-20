package com.xindog.jdk8.async;


import javafx.concurrent.Task;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.concurrent.*;

@Slf4j
public class ExecutorDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);
        Task<String> task = new Task<String>() {
            @Override
            protected String call() {
                log.info("Execute the task");
                return "This is a task";
            }
        };

        ScheduledFuture result = executor.scheduleAtFixedRate(task,1,2, TimeUnit.SECONDS);
        for (int i = 0; i < 10; i++) {
            log.info("Main: Delay: {}", result.getDelay(TimeUnit.MILLISECONDS));
        }

        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executor.shutdown();
        try {
            TimeUnit.SECONDS.sleep(5);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        log.info("Main: Finished: {}", new Date());
    }

}
