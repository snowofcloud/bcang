package com.spring.boot.mybatis.server.entity;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;


/**
 * @auther xuxq
 * @date 2018/12/8 18:33
 */
public class TImePlayer {
    //在java1.8之前的版本中，处理时间类型使用的是java.util包下的类，
    // java.util.Date是非线程安全的，所有的日期都是可变的；日期/时间类的定义并不一致，
    // 在java.util和java.sql的包下都含有Date类，在开发过程中容易出错；
    //日期类并不提供国际化，没有时区支持。
    //
    //为了解决上述问题：java1.8在java.time包下提供了很多新的API，常用类包括LocalTime、LocalDate、LocalDateTime；
    public static void main(String[] args) {
        //jdk1.8的新增时间类的应用2

        //LocalTime类
        //LocalTime与LocalDate一样，也是一个不可变的类，默认格式是hh:mm:ss.zzz，它提供了对时间的各种操作
        LocalTime localTime = LocalTime.now();
        int hour = localTime.getHour();
        int minute = localTime.getMinute();
        int second = localTime.getSecond();
        System.out.println(hour + "//" + minute + "//" + second);
            //获取自定义时间
            LocalTime of1 = LocalTime.of(23, 34, 9);
            System.out.println(of1);
            //比较两个时间的先后顺序
            //equals方法比较两个时间是否相等
            if(localTime.equals(of1)) {
                System.out.println("localTime与of1相等！");
            } else {
                //isAfter、isBefore方法比较两个时间的先后顺序
                if(localTime.isAfter(of1)) {
                    System.out.println("localTime晚于of1！");
                }
                if(localTime.isBefore(of1)) {
                    System.out.println("localTime早于of1！");
                }
            }
            //对时间做加减运算
            System.out.println("当前时间：" + localTime);
            System.out.println("2小时后时间：" + localTime.plusHours(2));
            System.out.println("30分钟后时间：" + localTime.plusMinutes(30));
            System.out.println("500秒后日时间：" + localTime.plusSeconds(500));
            System.out.println("2小时前时间：" + localTime.minusHours(2));
            System.out.println("30分钟前时间：" + localTime.minusMinutes(30));
            System.out.println("500秒前时间：" + localTime.minusSeconds(500));



        //LocalDate类
        //LocalDate是一个不可变类，在不考虑时区的情况下可以对日期（不包括时间）进行各种操作，它的默认格式是yyyy-MM-dd
        LocalDate localDate = LocalDate.now();
        int year = localDate.getYear();
        Month month = localDate.getMonth();
        int monthValue = localDate.getMonthValue();
        DayOfWeek dayOfWeek = localDate.getDayOfWeek();
        int dayOfMonth = localDate.getDayOfMonth();
        int dayOfYear = localDate.getDayOfYear();
        System.out.println("year:" + year + ";month:" + month + ";monthValue:" + monthValue + ";dayOfWeek:" + dayOfWeek + ";dayOfMonth:" + dayOfMonth + ";dayOfYear:" + dayOfYear);
            //结果--->>year:2018;month:DECEMBER;monthValue:12;dayOfWeek:SATURDAY;dayOfMonth:15;dayOfYear:349
            //获取指定日期
            LocalDate of = LocalDate.of(2018, 5, 23);
            System.out.println(of);
            //比较连个时间的先后顺序是否相等
            if (localDate.equals(of)){
                System.out.println("localDate和of相等");
            } else {
                System.out.println("localDate和of不相等");
                if (localDate.isAfter(of)){
                    System.out.println("localDate晚于of");
                }
                if (localDate.isBefore(of)){
                    System.out.println("localDate早于of");
                }
            }
            //对日期做加减
            LocalDate localDate1 = localDate.plusYears(2);//两年后的日期
            LocalDate localDate2 = localDate.plusMonths(3);//三个月后的日期
            LocalDate localDate3 = localDate.plusWeeks(3);//三个周后的日期
            LocalDate localDate4 = localDate.plusDays(5);//五天后日期
            System.out.println(localDate1 + "///" + localDate2 + "///" +  localDate3+ "///" + localDate4);
            //获取日期间隔的天数
            LocalDate localDate5 = LocalDate.ofYearDay(2018, 23);//获取23天后的日期
            //获取两个日期的间隔天数
            long l = localDate.toEpochDay() - localDate1.toEpochDay();
            System.out.println(localDate5 + "////" + l);




        //LocalDateTime类
        //LocalDateTime是一个不可变的日期-时间对象，它既包含了日期同时又含有时间，默认格式是yyyy-MM-ddTHH-mm-ss.zzz
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(localDateTime);
            //获取自定义的的日期时间
            LocalDateTime of2 = LocalDateTime.of(LocalDate.now(), LocalTime.now());
            System.out.println(of2);
            //转化为日期和时间
            LocalDate localDate6 = localDateTime.toLocalDate();
            LocalTime localTime1 = localDateTime.toLocalTime();
            System.out.println(localDate6 + "///" + localTime1);

    }
}
