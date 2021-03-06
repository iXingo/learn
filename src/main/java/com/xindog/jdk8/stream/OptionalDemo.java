package com.xindog.jdk8.stream;

import java.util.Optional;

/**
 * Created by Xindog.com(TM).
 * Author:  Shawn.Wang / i.am@shawn.wang
 * Date:    7/22/19
 * Time:    2:19 PM
 * Project: learn
 *
 * @author shawang
 */
public class OptionalDemo {

    public static void main(String[] args) {
        Optional<String> stringOptional = Optional.empty();
        stringOptional.ifPresent(System.out::println);
        String s = stringOptional.orElse("Hello World");
        System.out.println(s);
        String t = stringOptional.orElse("Hello");
        System.out.println(t);
        String str = stringOptional.orElseThrow(NullPointerException::new);


        System.out.println(str);
    }
}
