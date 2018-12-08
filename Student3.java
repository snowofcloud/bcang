package com.spring.boot.mybatis.server.entity;

/**
 * @auther xuxq
 * @date 2018/12/8 15:50
 */

class Teacher {

    private final void speak(){
        System.out.println("teach student that how to speak English ");
    }
}

public class Student3 extends Teacher {

    private final void speak(){
        System.out.println("learning to speak English");
    }

    public static void main(String[] args) {
        Student3 student3 = new Student3();
        student3.speak();//learning to speak English
        //private和final修饰的方法是不能被重写的；
    }

}
