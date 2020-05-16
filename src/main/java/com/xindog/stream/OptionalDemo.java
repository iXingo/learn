package com.xindog.stream;

import java.util.Optional;

/**
 * Created by Xindog.com(TM).
 * Author:  Shawn.Wang / i.am@shawn.wang
 * Date:    7/22/19
 * Time:    2:19 PM
 * Project: learn
 */
public class OptionalDemo {

    public static void main(String[] args) {
        Optional<String> stringOptional = Optional.empty();
        stringOptional.ifPresent(System.out::println);
        String s = stringOptional.orElse("Hello World");
        System.out.println(s);
        String t = stringOptional.orElseGet(() -> "Hello");
        System.out.println(t);
        String str = stringOptional.orElseThrow(() -> new NullPointerException());


        System.out.println(s);
    }
}
