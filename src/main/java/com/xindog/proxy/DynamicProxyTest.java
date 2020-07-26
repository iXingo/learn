package com.xindog.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by Xindog.com(TM).
 * Author:  Shawn.Wang / i.am@shawn.wang
 * Date:    7/12/19
 * Time:    4:50 PM
 * Project: learn
 */
public class DynamicProxyTest {

    public static void main(String[] args) {
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
//        IHello hello = (IHello) new DynamicProxy().bind(new Hello());
        Hello h = new Hello();
        IHello hello = (IHello) Proxy.newProxyInstance(h.getClass().getClassLoader(), h.getClass().getInterfaces(), new DynamicProxy(h));
        hello.sayHello();
    }

    interface IHello {
        void sayHello();
    }

    static class Hello implements IHello {
        @Override
        public void sayHello() {
            System.out.println("hello world");
        }
    }

    static class DynamicProxy implements InvocationHandler {

        Object originalObj;

        DynamicProxy(Object target) {
            originalObj = target;
        }

        Object bind(Object originalObj) {
            this.originalObj = originalObj;
            return Proxy.newProxyInstance(originalObj.getClass().getClassLoader(), originalObj.getClass().getInterfaces(), this);
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            String sayHello = "sayHello";
            if (sayHello.equals(method.getName())) {
                System.out.println("--------pre method------------");
                Object result = method.invoke(originalObj, args);
                System.out.println(method.getName() + "()");
                System.out.println("--------post method-----------");
                return result;
            } else {
                return method.invoke(originalObj, args);
            }
        }
    }
}

