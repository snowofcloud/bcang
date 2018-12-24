package com.xly.spring.boot.mybatisplus.server.controller;

import java.util.Arrays;
import java.util.function.Consumer;

/**
 * Java8新特性之方法引用---特定对象方法引用（Class::method）
 * @auther xuxq
 * @date 2018/12/24 21:24
 */
public class Java18 {
    public static void main(String[] args) {
        //List中的对象就是后面传入的方法的参数，
        //所以再C类中参数是一个C类对象
        Arrays.asList("a","b","c").forEach(new C():: sayString);
    }
}

class C{

    public  void sayString(String c){
        System.out.println("this is not statis ="+c);
    }
}