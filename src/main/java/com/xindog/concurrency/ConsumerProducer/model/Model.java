package com.xindog.concurrency.ConsumerProducer.model;

public interface Model {
    Runnable newRunnableConsumer();
    Runnable newRunnableProducer();
}
