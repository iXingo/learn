package com.xindog.jdk8;


import java.util.Comparator;
import java.util.function.BinaryOperator;

public class BinaryOperatorTest {

    public static void main(String[] args) {
        BinaryOperatorTest binaryOperatorTest = new BinaryOperatorTest();

        System.out.println(binaryOperatorTest.compute(1, 2, Integer::sum));
        System.out.println(binaryOperatorTest.compute(1, 2, (a, b) -> a - b));

        System.out.println("----------");

        System.out.println(binaryOperatorTest.getShort("hello123", "world", Comparator.comparingInt(String::length)));
        System.out.println(binaryOperatorTest.getShort("hello123", "world", Comparator.comparingInt(a -> a.charAt(0))));

    }

    private int compute(int a, int b, BinaryOperator<Integer> binaryOperator) {
        return binaryOperator.apply(a, b);
    }

    private String getShort(String a, String b, Comparator<String> comparator) {
        return BinaryOperator.maxBy(comparator).apply(a, b);
    }
}
