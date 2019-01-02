package com.c503.hthj.asoco.dangerchemical.waste.base;

import java.util.concurrent.TimeUnit;

/**
 * @auther xuxq
 * @date 2019/1/2 22:23
 */
public class SimpleDaemon implements Runnable {
    @Override
    public void run() {
        try {
            while (true){
                TimeUnit.MILLISECONDS.sleep(100);
                System.out.println(Thread.currentThread()+ "" +this);
            }
        } catch (InterruptedException e) {
            System.out.println("sleep() interrupted");
        }
    }

    public static void main(String[] args) throws Exception {
        for (int i = 0; i<10; i++){
            Thread thread = new Thread(new SimpleDaemon());
            thread.setDaemon(true);//在start前设置thread实例的setDaemon(true)将线程设置为后台守护线程。
            thread.start();
        }
        System.out.println("All daemons started");
        TimeUnit.MILLISECONDS.sleep(185);
    }
    //执行结果
  /*All daemons started
    Thread[Thread-0,5,main]com.c503.hthj.asoco.dangerchemical.waste.base.SimpleDaemon@5bfdf791
    Thread[Thread-1,5,main]com.c503.hthj.asoco.dangerchemical.waste.base.SimpleDaemon@f448da4
    Thread[Thread-2,5,main]com.c503.hthj.asoco.dangerchemical.waste.base.SimpleDaemon@1dc022f6
    Thread[Thread-3,5,main]com.c503.hthj.asoco.dangerchemical.waste.base.SimpleDaemon@47a2a66c
    Thread[Thread-5,5,main]com.c503.hthj.asoco.dangerchemical.waste.base.SimpleDaemon@710a3057
    Thread[Thread-4,5,main]com.c503.hthj.asoco.dangerchemical.waste.base.SimpleDaemon@6f9777db
    Thread[Thread-6,5,main]com.c503.hthj.asoco.dangerchemical.waste.base.SimpleDaemon@7f05b150
    Thread[Thread-8,5,main]com.c503.hthj.asoco.dangerchemical.waste.base.SimpleDaemon@655a2155
    Thread[Thread-7,5,main]com.c503.hthj.asoco.dangerchemical.waste.base.SimpleDaemon@2cf9cd59
    Thread[Thread-9,5,main]com.c503.hthj.asoco.dangerchemical.waste.base.SimpleDaemon@5169ce60*/

}
