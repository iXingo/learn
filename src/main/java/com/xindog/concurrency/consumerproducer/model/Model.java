package com.xindog.concurrency.consumerproducer.model;

public interface Model {
    Runnable newRunnableConsumer();

    Runnable newRunnableProducer();
}
