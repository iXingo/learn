package com.xindog.concurrency.consumerproducer;

public interface Consumer {
    void consume() throws InterruptedException;
}
