package com.xindog.jdk8.stream2;


import java.util.function.Consumer;
import java.util.function.IntConsumer;

public class ConsumerTest {

    public static void main(String[] args) {
        ConsumerTest consumerTest = new ConsumerTest();

        Consumer<Integer> consumer = System.out::println;
        IntConsumer intConsumer = System.out::println;

        System.out.println(true);
        System.out.println(true);

        consumerTest.test(consumer); //面向对象方式
        consumerTest.test(consumer); //函数式方式
        consumerTest.test(intConsumer::accept); //函数式方式
    }

    public void test(Consumer<Integer> consumer) {
        consumer.accept(100);
    }
}
