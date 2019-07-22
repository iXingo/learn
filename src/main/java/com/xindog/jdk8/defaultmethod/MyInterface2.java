package com.xindog.jdk8.defaultmethod;


public interface MyInterface2 {

    default void myMethod() {
        System.out.println("MyInterface2");
    }
}
