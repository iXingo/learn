package com.xindog;


import java.util.ArrayList;

/**
 * Hello world!
 * This is a project for Socket, NIO, Concurrent and Multi-Thread, and Java8 stream ,new Date/time API
 */
public class App {
    //    public static void main(String[] args) {
//        double num1 = 0.719;
//        System.out.println(num1 * 128);
//        System.out.println((num1 * 128) + 0.1);
//        System.out.println((int) ((num1 * 128) + 0.1));
//        double num2 = 0.711;
//        System.out.println(num2 * 128);
//        System.out.println((num2 * 128) + 0.1);
//        System.out.println((int) ((num2 * 128) + 0.1));
//    }
    static ArrayList<Integer> list = new ArrayList<>();

    public static void permutation(char[] s, int from, int to) {
        if (to <= 1)
            return;
        if (from == to) {
//            System.out.println(s);
            Integer integer = Integer.parseInt(String.valueOf(s));
            list.add(integer);
        } else {
            for (int i = from; i <= to; i++) {
                swap(s, i, from);
                permutation(s, from + 1, to);
                swap(s, from, i);
            }
        }
    }

    public static void swap(char[] s, int i, int j) {
        char tmp = s[i];
        s[i] = s[j];
        s[j] = tmp;
    }

    public static void main(String[] args) {
        char[] s = {'1', '2', '2', '4', '5', '5'};
        permutation(s, 0, 5);
        list.stream().filter(x -> x > 300000).forEach(System.out::println);
    }


}
