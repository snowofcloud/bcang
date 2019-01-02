package com.c503.hthj.asoco.dangerchemical.waste.base;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @auther xuxq
 * @date 2019/1/2 22:40
 */
public class DaemonFormFactory implements Runnable {
    @Override
    public void run() {
        try {
            while (true){
                TimeUnit.MILLISECONDS.sleep(100);
                System.out.println(Thread.currentThread()+ "" +this);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool(new DaemonThreadFactory());
        for (int i = 0; i<10;i++){
            executorService.execute(new DaemonFormFactory());
        }
        System.out.println("all daemon started...");
        TimeUnit.MICROSECONDS.sleep(500);
    }
    //Executors.newCachedThreadPool() ，传递自定义线程工厂，
    // 这个时候Exectors会根据这个线程工厂创建线程实例来执行DaemonFormFactory的run()方法
}
