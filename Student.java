package com.c503.hthj.asoco.dangerchemical.waste.controller;

/**
 * @auther xuxq
 * @date 2018/12/5 20:50
 */
public class Student {

    public Student(){
        System.out.println("构造方法");
    }

    {
        System.out.println("普通代码块");
    }

    static {
        System.out.println("静态代码块");
    }
    //创建一个对象，方法的执行顺序？
    public static void main(String[] args) {
        new Student();//第一个对象
        System.out.println("-----------------------------------");
        new Student();//第二个对象

        //类只会初始化一次，而对象各自初始化一次

    }

}
