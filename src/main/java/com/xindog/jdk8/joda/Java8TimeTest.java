package com.xindog.jdk8.joda;


import lombok.extern.slf4j.Slf4j;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

@Slf4j
public class Java8TimeTest {

    public static void main(String[] args) {

        Date date = new Date(System.currentTimeMillis());
        LocalDate localDate = LocalDate.now();
        log.info(String.valueOf(localDate));

        log.info(localDate.getYear() + ", " + localDate.getMonthValue() + ", " + localDate.getDayOfMonth());

        log.info("-------");

        LocalDate localDate2 = LocalDate.of(2017, 3, 3);
        log.info(String.valueOf(localDate2));

        log.info("-------");

        LocalDate localDate3 = LocalDate.of(2010, 3, 25);
        MonthDay monthDay = MonthDay.of(localDate3.getMonth(), localDate3.getDayOfMonth());
        MonthDay monthDay2 = MonthDay.from(LocalDate.of(2011, 3, 26));

        if (monthDay.equals(monthDay2)) {
            log.info("equals");
        } else {
            log.info("not equals");
        }

        log.info("-------");

        LocalTime time = LocalTime.now();
        log.info(String.valueOf(time));

        LocalTime time2 = time.plusHours(3).plusMinutes(20);
        log.info(String.valueOf(time2));

        log.info("-------");

        LocalDate localDate1 = localDate.plus(2, ChronoUnit.WEEKS);
        log.info(String.valueOf(localDate1));

        log.info("-------");

        LocalDate localDate4 = localDate.minus(2, ChronoUnit.MONTHS);
        log.info(String.valueOf(localDate4));

        log.info("-------");

        Clock clock = Clock.systemDefaultZone();
        log.info(String.valueOf(clock.millis()));

        log.info("-------");

        LocalDate localDate5 = LocalDate.now();
        LocalDate localDate6 = LocalDate.of(2017, 3, 18);

        log.info(String.valueOf(localDate5.isAfter(localDate6)));
        log.info(String.valueOf(localDate5.isBefore(localDate6)));
        log.info(String.valueOf(localDate5.equals(localDate6)));

        log.info("-------");

        Set<String> set = ZoneId.getAvailableZoneIds();
        Set<String> treeSet = new TreeSet<String>() {
            {
                addAll(set);
            }
        };
        treeSet.forEach(System.out::println);

        log.info("-------");

        ZoneId zoneId = ZoneId.of("Asia/Shanghai");
        LocalDateTime localDateTime = LocalDateTime.now();
        log.info(String.valueOf(localDateTime));

        ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime, zoneId);
        log.info(String.valueOf(zonedDateTime));

        log.info("-------");

        YearMonth yearMonth = YearMonth.now();
        log.info(String.valueOf(yearMonth));
        log.info(String.valueOf(yearMonth.lengthOfMonth()));
        log.info(String.valueOf(yearMonth.isLeapYear()));

        log.info("-------");

        YearMonth yearMonth1 = YearMonth.of(2016, 2);
        log.info(String.valueOf(yearMonth1));
        log.info(String.valueOf(yearMonth1.lengthOfMonth()));
        log.info(String.valueOf(yearMonth1.lengthOfYear()));
        log.info(String.valueOf(yearMonth1.isLeapYear()));

        log.info("-------");

        LocalDate localDate7 = LocalDate.now();
        LocalDate localDate8 = LocalDate.of(2018, 3, 16);

        Period period = Period.between(localDate7, localDate8);
        log.info(String.valueOf(period.getYears()));
        log.info(String.valueOf(period.getMonths()));
        log.info(String.valueOf(period.getDays()));

        log.info("-------");

        log.info(String.valueOf(Instant.now()));
        LocalDate d = LocalDate.now();
        System.out.print(d.minusDays(9651).getYear() + "-");
        System.out.print(d.minusDays(9651).getMonthValue() + "-");
        System.out.print(d.minusDays(9651).getDayOfMonth() + 1 + "-");

        LocalDate localDate9 = LocalDate.now();
        log.info(String.valueOf(localDate9.minusDays(9154).getDayOfMonth()));
        log.info(String.valueOf(localDate9.minusDays(9154).getMonthValue()));
        log.info(String.valueOf(localDate9.minusDays(9154).getYear()));

    }
}
