package com.xindog.jdk8;


import lombok.extern.slf4j.Slf4j;

import java.util.Comparator;
import java.util.function.BinaryOperator;


@Slf4j
public class BinaryOperatorTest {

    public static void main(String[] args) {
        BinaryOperatorTest binaryOperatorTest = new BinaryOperatorTest();

        log.info("binaryOperator Test Result => {}", binaryOperatorTest.compute(1, 2, Integer::sum));
        log.info("binaryOperator Test Result => {}", binaryOperatorTest.compute(1, 2, (a, b) -> a - b));

        log.info("----------");

        log.info("binaryOperator Test Result => {}", binaryOperatorTest.getShort("hello123", "world", Comparator.comparingInt(String::length)));
        log.info("binaryOperator Test Result => {}", binaryOperatorTest.getShort("hello123", "world", Comparator.comparingInt(a -> a.charAt(0))));

    }

    private int compute(int a, int b, BinaryOperator<Integer> binaryOperator) {
        return binaryOperator.apply(a, b);
    }

    private String getShort(String a, String b, Comparator<String> comparator) {
        return BinaryOperator.maxBy(comparator).apply(a, b);
    }
}
