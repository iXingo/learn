package com.xindog.jdk8.stream;


import java.util.stream.Collectors;
import java.util.stream.Stream;


public class StreamTest8 {

    public static void main(String[] args) {
//        IntStream.iterate(0, i -> (i + 1)).limit(6).distinct().forEach(System.out::println);
        Stream<String> stringStream = Stream.of("String", "Hello", "World");
        stringStream.map(String::length).filter(item -> item > 5).collect(Collectors.toList()).forEach(System.out::println);

    }
}
