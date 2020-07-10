package com.xindog;


/**
 * Hello world!
 * This is a project for Socket, NIO, Concurrent and Multi-Thread, and Java8 stream ,new Date/time API
 */
public class App {
    public static void main(String[] args) {
        double num1 = 0.719;
        System.out.println(num1*128);
        System.out.println((num1*128)+0.1);
        System.out.println((int)((num1*128)+0.1));
        double num2 = 0.711;
        System.out.println(num2*128);
        System.out.println((num2*128)+0.1);
        System.out.println((int)((num2*128)+0.1));
    }
}
