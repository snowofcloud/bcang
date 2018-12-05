package com.c503.hthj.asoco.dangerchemical.waste.controller;

/**
 * @auther xuxq
 * @date 2018/12/5 20:50
 */
public class Student {

    private String username;

    public Student(){
        //构造方法用this关键字可以相互调用，但this的调用必须放在构造方法第一行，否则编译报错；
        this("王小明");
    }

    public Student(String name){
        this.username = name;
    }

    public static void main(String[] args) {
        System.out.println(new Student());
        System.out.println(new Student("王小明"));

    }

}
