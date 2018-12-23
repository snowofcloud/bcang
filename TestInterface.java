package com.spring.boot.mybatis.server.entity;

/**
 * @auther xuxq
 * @date 2018/12/23 23:54
 */
public class TestInterface {

    interface  Animal{
        void move();
        //void eat();
        default void eat(){
            System.out.println("come on");
        }
    }

    class Dog implements Animal{
        @Override
        public void move() {

        }
    }

    class Cat implements Animal{
        @Override
        public void move() {

        }
    }

}
