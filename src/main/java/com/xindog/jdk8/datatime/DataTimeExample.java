package com.xindog.jdk8.datatime;


import java.time.LocalDateTime;

/**
 * Created by Xindog.com(TM).
 * Author:  Shawn.Wang / i.am@shawn.wang
 * Date:    5/23/19
 * Time:    10:43 AM
 * Project: learn
 */
public class DataTimeExample {

    public static void main(String[] args) {
//        LocalDateTime nowTime = LocalDateTime.of(LocalDate.of(2019,05,22 ), LocalTime.of(0,06,41));
//
//        LocalDateTime nextTime = nowTime.minusWeeks(2);
//
//        System.out.print("\n"+nextTime+"Z ");
//        System.out.println(nowTime+"Z");
//
//        System.out.println(Instant.now());
//        Runnable runnable = () -> {
//            System.out.println("Hello World");
//        };
//        Executor executor = Executors.newSingleThreadExecutor();


        LocalDateTime now = LocalDateTime.now();
        LocalDateTime xiqishan = now.minusDays(9029);
        LocalDateTime wenxiu = now.minusDays(8906);
        System.out.println(wenxiu);
        System.out.println(xiqishan);
    }
}
