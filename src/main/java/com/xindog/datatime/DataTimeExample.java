package com.xindog.datatime;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by Xindog.com(TM).
 * Author:  Shawn.Wang / i.am@shawn.wang
 * Date:    5/23/19
 * Time:    10:43 AM
 * Project: learn
 */
public class DataTimeExample {

    public static void main(String[] args) {
        LocalDateTime nowTime = LocalDateTime.of(LocalDate.of(2019,05,22 ), LocalTime.of(0,06,41));

        LocalDateTime nextTime = nowTime.minusWeeks(2);

        System.out.print("\n"+nextTime+"Z ");
        System.out.println(nowTime+"Z");

        System.out.println(Instant.now());
        Runnable runnable = () -> {
            System.out.println("Hello World");
        };
        Executor executor = Executors.newSingleThreadExecutor();
    }
}
