package com.xindog.async;

/**
 * Created by Xindog.com(TM).
 * Author:  Shawn.Wang / i.am@shawn.wang
 * Date:    8/14/20
 * Time:    2:20 PM
 * Project: learn
 */
public interface Task<T> {
    String doTask(T object);
}
