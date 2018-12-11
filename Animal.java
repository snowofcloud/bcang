package com.spring.boot.mybatis.server.entity;

/**
 * @auther xuxq
 * @date 2018/12/11 23:51
 */
public class Animal {
    private String name = "animal";
    public Animal(){
        a();
    }
    public void a(){
        System.out.println(name);
    }
}

class Dog extends Animal{
    private String name = "dog";
    public void  a(){
        System.out.println(name);
    }

    public static void main(String[] args) {
        Animal animal = new Dog();
    }

}
