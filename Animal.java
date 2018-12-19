package com.c503.hthj.asoco.dangerchemical.waste.base;

/**
 * @auther xuxq
 * @date 2018/12/19 23:30
 */
public class Animal {
    public Animal(){
        System.out.println("Animal构造方法");
    }
    {
        System.out.println("Animal普通方法方法");
    }
    static {
        System.out.println("Animal静态方法");
    }
}

class Dog extends Animal{

    public Dog(){
        System.out.println("Dog构造方法");
    }
    {
        System.out.println("Dog普通方法");
    }
    static {
        System.out.println("Dog静态方法");
    }
    public static void main(String[] args) {
        new Dog();
        //先静后对象，先父后子
    }

//    执行结果：
//    Animal静态方法
//    Dog静态方法
//    Animal普通方法方法
//    Animal构造方法
//    Dog普通方法
//    Dog构造方法
}
