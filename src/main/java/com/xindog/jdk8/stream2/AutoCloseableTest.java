package com.xindog.jdk8.stream2;


public class AutoCloseableTest implements AutoCloseable {

    public static void main(String[] args) throws Exception {

        try (AutoCloseableTest autoCloseableTest = new AutoCloseableTest()) {
            autoCloseableTest.doSomething();
        }


    }

    public void doSomething() {
        System.out.println("doSomething invoked!");
    }

    @Override
    public void close() throws Exception {
        System.out.println("close invoked!");
    }
}
