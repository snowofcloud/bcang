package com.spring.boot.mybatis.server.entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

/**
 * @auther xuxq
 * @date 2018/12/8 18:33
 */
public class TImePlayer {

    public static void main(String[] args) {

        System.out.println("请输入生日，格式19940607");
        Scanner scanner = new Scanner(System.in);

        while (true){

            String birthday = scanner.next();
            LocalDate birthdayDate = LocalDate.parse(birthday, DateTimeFormatter.ofPattern("yyyyMMdd"));

            LocalDate now = LocalDate.now();
            long days = birthdayDate.until(now, ChronoUnit.DAYS);
            System.out.println("已苟活"+days+"天");
        }
    }
}
