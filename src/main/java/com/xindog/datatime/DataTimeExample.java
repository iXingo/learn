package com.xindog.datatime;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Created by Xindog.com(TM).
 * Author:  Shawn.Wang / i.am@shawn.wang
 * Date:    5/23/19
 * Time:    10:43 AM
 * Project: learn
 */
public class DataTimeExample {

    public static void main(String[] args) {
        LocalDateTime nowTime = LocalDateTime.of(LocalDate.of(2019,05,28 ), LocalTime.of(23,02,12));

        LocalDateTime nextTime = nowTime.minusWeeks(2);

        System.out.print("\n"+nextTime+"Z ");
        System.out.println(nowTime+"Z");
    }
}
