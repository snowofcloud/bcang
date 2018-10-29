package com.c503.hthj.asoco.dangerchemical.waste.controller;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @auther xuxq
 * @date 2018/10/29 21:19
 */
public class Test {
    public static void main(String[] args) {

        Long id = 1L;
        //实现高位补0;
        DecimalFormat df=new DecimalFormat("00000");
        String str2=df.format(id);
        //获取系统时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date1 = new Date();
        String format = sdf.format(date1);
        //组合编号
        String code="DZLDS"+format+str2;
        System.out.println(code);
    }
}
