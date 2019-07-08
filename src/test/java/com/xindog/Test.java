package com.xindog;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Test {
    static Lock lock = new ReentrantLock();
    static Condition con = lock.newCondition();
    static volatile int i = 0;

    static Runnable thread1 = ()->{
        while(true){
            lock.lock();
            if(i == 100) break;
            while(i % 2 == 0){
                try {
                    con.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            i += 1;
            System.out.println("线程1:"+i);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            con.signal();
            lock.unlock();
        }
    };

    static Runnable thread2 = ()->{
        while(true){
            lock.lock();
            if(i == 100) break;
            while(i % 2 == 1){
                try {
                    con.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            i += 1;
            System.out.println("线程2:"+i);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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