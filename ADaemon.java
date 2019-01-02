package com.c503.hthj.asoco.dangerchemical.waste.base;

import java.util.concurrent.TimeUnit;

/**
 * @auther xuxq
 * @date 2019/1/2 22:57
 */
public class ADaemon implements Runnable {
    @Override
    public void run() {
        try {
            System.out.println("String ADaemon");
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            System.out.println(" exiting via InterruptedException !");
            e.printStackTrace();
        } finally {
            System.out.println("this should always run??");
        }
    }

    public static void main(String[] args) {
        Thread thread = new Thread(new ADaemon());
        thread.setDaemon(true);
        thread.start();
    }
    //当设置子线程为后台线程，main()方法结束，jvm会立即关闭所有的后台线程，所以finally语句块没有执行。

}
