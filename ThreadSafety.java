package com.xly.spring.boot.mybatisplus.server.entity.base;

/**
 * @auther xuxq
 * @date 2018/12/26 21:23
 */
public class ThreadSafety implements Runnable {

    //公共数据
    public static int i = 0;
    public synchronized void increase(){
        for (int j = 0; j<10; j++){
            i++;
        }
    }

    @Override
    public void run() {
        increase();
    }

    public static void main(String[] args) throws Exception{
        ThreadSafety threadSafety = new ThreadSafety();
        Thread thread1 = new Thread();
        Thread thread2 = new Thread();
        thread1.start();
        thread2.start();
        //System.out.println(i);
    }


}
