package com.xindog.java.async;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by Xindog.com(TM).
 * Author:  Shawn.Wang / i.am@shawn.wang
 * Date:    8/14/20
 * Time:    2:21 PM
 * Project: learn
 */
@Slf4j
public class Wrapper<T> {
    private T param;
    private Task<T> task;
    private Handler<T> handler;

    public T getParam() {
        return param;
    }

    public void setParam(T param) {
        this.param = param;
    }

    public Task<T> getTask() {
        return task;
    }

    public void setTask(Task<T> task) {
        this.task = task;
    }

    public Handler<T> getHandler() {
        return handler;
    }

    public void addHandler(Handler<T> handler) {
        this.handler = handler;
        log.error(handler.getClass().getName());
    }

    @Override
    public String toString() {
        return "Wrapper{" +
                "param=" + param +
                ", task=" + task +
                ", handler=" + handler +
                '}';
    }
}
