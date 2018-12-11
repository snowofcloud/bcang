package com.spring.boot.mybatis.server.entity;

import java.math.BigDecimal;
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
        int number = 100;
        BigDecimal dum = Student3.dum(new BigDecimal(number));
        System.out.println(dum.toString());
    }


    public static BigDecimal dum(BigDecimal number){
        if ( number.toString().equals(new BigDecimal("1").toString())){
            return number;
        }
        return number.multiply(dum(number.subtract(new BigDecimal("1"))));
    }

}
