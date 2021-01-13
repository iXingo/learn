package com.xindog;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Test {
    private static final Lock lock = new ReentrantLock();
    private static final Condition con = lock.newCondition();
    private static volatile int i = 0;

    private static final Runnable thread1 = () -> {
        while (true) {
            lock.lock();
            if (i == 100) break;
            while (i % 2 == 0) {
                try {
                    con.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            i += 1;
            System.out.println("线程1:" + i);
/*            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
            con.signal();
            lock.unlock();
        }
    };

    private static final Runnable thread2 = () -> {
        while (true) {
            lock.lock();
            if (i == 100) break;
            while (i % 2 == 1) {
                try {
                    con.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            i += 1;
            System.out.println("线程2:" + i);
/*            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
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
