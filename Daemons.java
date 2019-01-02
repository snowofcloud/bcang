package com.c503.hthj.asoco.dangerchemical.waste.base;

import java.util.concurrent.TimeUnit;

/**
 * @auther xuxq
 * @date 2019/1/2 22:45
 */
class Daemon implements Runnable {

    Thread[] t = new Thread[10];

    @Override
    public void run() {
        for (int i = 0; i < t.length;i++){
            t[i] = new Thread(new DaemonSpaw());
            t[i].start();
            System.out.println("DaemonSpaw" + i + "started....");
        }
        for(int i = 0; i < t.length;i++){
            System.out.println("t[" + i + "].isDaemon()" + t[i].isDaemon() + ",");
        }
        while (true){
            Thread.yield();
        }
    }
}

class DaemonSpaw implements Runnable{
    @Override
    public void run() {
        while (true){
            Thread.yield();
        }
    }
}

public class Daemons{
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new Daemon());
        thread.setDaemon(true);
        thread.start();
        System.out.println("d.isDaemon()=" + thread.isDaemon());
        TimeUnit.SECONDS.sleep(10);
    }
    //后台线程创建的任何线程都是后台线程


    //将一个线程设置为后台线程后，其后台线程继续创建新的线程，其全部都是后台线程，
    //可以通过isDaemon()测试，我们看下面的think in java的例子程序：

    //我们将Daemon类设置为后台线程，由它继续创建的线程实例自动被设置为后台线程，通过输出isDaemon() true 验证




}
