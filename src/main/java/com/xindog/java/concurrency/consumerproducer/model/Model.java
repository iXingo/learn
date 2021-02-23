package com.xindog.java.concurrency.consumerproducer.model;

public interface Model {
    Runnable newRunnableConsumer();

    Runnable newRunnableProducer();
}
