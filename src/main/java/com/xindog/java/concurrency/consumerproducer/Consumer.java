package com.xindog.java.concurrency.consumerproducer;

public interface Consumer {
    void consume() throws InterruptedException;
}
