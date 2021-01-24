package com.xindog.database.example;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Created by Xindog.com(TM).
 * Author:  Shawn.Wang / i.am@shawn.wang
 * Date:    12/29/20
 * Time:    11:00 AM
 * Project: learn
 */
@Slf4j
public class ExecutionExample {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<String> future = executorService.submit(() ->
        {
            Thread.sleep(5000);
            log.warn("Finished!");
            return "success";
        });

        try {
            String result = future.get(2, TimeUnit.SECONDS);
            //String result=future.get(50,TimeUnit.SECONDS);
            log.debug("result:" + result);
        } catch (Throwable e) {
            log.warn("Timeout!");
        }
        executorService.shutdownNow();
        log.debug("Closed");
    }
}
