package com.xindog.async;

/**
 * Created by Xindog.com(TM).
 * Author:  Shawn.Wang / i.am@shawn.wang
 * Date:    8/14/20
 * Time:    2:21 PM
 * Project: learn
 */
public class Wrapper {
    private Object param;
    private Task task;
    private Handler handler;

    public Object getParam() {
        return param;
    }

    public void setParam(Object param) {
        this.param = param;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public Handler getHandler() {
        return handler;
    }

    public void addHandler(Handler handler) {
        this.handler = handler;
    }
}
