package com.c503.hthj.data.dao.four;

/**
 * 单例 饿汉式
 * @auther xuxq
 * @date 2018/12/1 15:59
 */
public class Student {

    private static Student student = new Student();

    private Student(){

    }

    public static Student getInstance(){
        return student;
    }


}
