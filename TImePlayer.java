package com.spring.boot.mybatis.server.entity;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;


/**
 * @auther xuxq
 * @date 2018/12/8 18:33
 */
public class TImePlayer {

    public static void main(String[] args) {
        //jdk1.8的新增时间类

        LocalDate currentDate = LocalDate.now();//获取当前日期  eg:2018-12-08
        LocalTime currentTime = LocalTime.now();//获取当前时间  eg:18:45:25.409
        LocalDateTime currentDateTime = LocalDateTime.now();//获取当前日期时间 eg:2018-12-08T18:45:25.409

        //格式化时间
        LocalDate localDate = LocalDate.parse("1995/07/07", DateTimeFormatter.ofPattern("yyyy/MM/dd"));//1995-07-07

        //获取本月最后一天
        LocalDate lastDayOfMonth = currentDate.with(TemporalAdjusters.lastDayOfMonth());//2018-12-31
        LocalDate lastDayOfYear = currentDate.with(TemporalAdjusters.lastDayOfYear());//2018-12-31

        //获取本年最后一个星期一
        LocalDate lastMonthLastMonday = LocalDate.parse("2018-12-08").with(TemporalAdjusters.lastInMonth(DayOfWeek.MONDAY));

        //获取昨天 明天的日期
        LocalDate yesterday = LocalDate.now().plusDays(-1);
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        //获取明年的今天
        LocalDate nextYearToday = LocalDate.now().plusYears(1);

    }
}
