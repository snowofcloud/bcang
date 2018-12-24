package com.xly.spring.boot.mybatisplus.server.controller;

/**
 * Java8新特性之方法引用--------------构造方法引用（Class::new）
 * @auther xuxq
 * @date 2018/12/24 21:24
 */
public class Java18 {
    public static void main(String[] args) {
        A a = C::new;
        a.test();
        B b = C::new;
        b.test("B interface");
    }
}
interface A{
    void test();
}

interface B{
    void test(String ss);
}

class C{
    private String name;
    public C(){

    }

    public C(String name){
        this.name = name;
    }
}