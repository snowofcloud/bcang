package com.spring.boot.mybatis.server.entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

/**
 * @auther xuxq
 * @date 2018/12/8 15:50
 */

public class Student3 {

    public static void main(String[] args) {
        int dum = Student3.dum(10);
        System.out.println(dum);
    }


    public static int dum( int number){
        if (1 == number){
            return number;
        }
        return number*dum(number-1);
    }

}
