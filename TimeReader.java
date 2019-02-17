package com.spring.boot.mybatis.demo.controller;

/**
 * @auther xuxq
 * @date 2019/2/14 20:22
 */
public class TimeReader {

    private static final Object lock1 = new Object();
    private static final Object lock2 = new Object();

    public static void main(String[] args) {
        /*死锁*/

//        Thread t1 = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                synchronized (lock1) {
//                    System.out.println(Thread.currentThread().getName() + "锁住lock1");
//                    synchronized (lock2) {
//                        System.out.println(Thread.currentThread().getName() + "锁住lock2");
//                    }
//                }
//            }
//        },"t11");
//
//        Thread t2 = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                synchronized (lock2) {
//                    System.out.println(Thread.currentThread().getName() + "锁住lock2");
//                    synchronized (lock1) {
//                        System.out.println(Thread.currentThread().getName() + "锁住lock1");
//                    }
//                }
//            }
//        },"t22");
//
//        t1.start();
//        t2.start();


        /*解开死锁*/

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock1) {
                    System.out.println(Thread.currentThread().getName() + "锁住lock1");
                    synchronized (lock2) {
                        System.out.println(Thread.currentThread().getName() + "锁住lock2");
                    }
                }
            }
        },"t11");

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock1) {
                    System.out.println(Thread.currentThread().getName() + "锁住lock1");
                    synchronized (lock2) {
                        System.out.println(Thread.currentThread().getName() + "锁住lock2");
                    }
                }
            }
        },"t22");

        t1.start();
        t2.start();

    }

}
