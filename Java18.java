package com.xly.spring.boot.mybatisplus.server.controller;

import java.util.Arrays;
import java.util.function.Consumer;

/**
 * Java8新特性之方法引用---静态方法引用（Class::static_method）
 * @auther xuxq
 * @date 2018/12/24 21:24
 */
public class Java18 {
    public static void main(String[] args) {
        //传统方式
        Arrays.asList("a","b","c").forEach(new Consumer<String>() {
            @Override
            public void accept(String s) {
                C.sayString(s);
            }
        });

        //引用方式
        Arrays.asList("a","b","c").forEach(C::sayString);
    }
}

class C{

    public C(){ }

    public static void sayString(String s){
        System.out.println("this is statis ="+s);
    }
}