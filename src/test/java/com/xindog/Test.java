package com.xindog;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


@Slf4j
public class Test {
    private static final Lock lock = new ReentrantLock();
    private static final Condition con = lock.newCondition();
    private static volatile int i = 0;

    private static final Runnable thread1 = () -> {
        while (true) {
            lock.lock();

            while (i % 2 == 1) {
                try {
                    con.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (i == 100) {
                log.info("线程1: {}", i);
                break;
            }
            log.info("线程1: {}", i++);
            con.signal();
            lock.unlock();
        }
    };

    private static final Runnable thread2 = () -> {
        while (true) {
            lock.lock();
            while (i % 2 == 0) {
                try {
                    con.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (i == 100) {
                log.info("线程2: {}", i);
                break;
            }
            log.info("线程2: {}", i++);
            con.signal();
            lock.unlock();
        }
    };

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.execute(thread1);
        executorService.execute(thread2);
    }


}
