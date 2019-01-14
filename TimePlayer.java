package com.spring.boot.mybatis.server;

/**
 * @auther xuxq
 * @date 2019/1/14 20:00
 */
public class TimePlayer {
    public static void main(String[] args) {
        StringBuilder builder = new StringBuilder();

        StringBuffer buffer = new StringBuffer();

        for (int i = 0; i < 10 ;i++){
            new A(builder,buffer).start();
        }
    }
}

class A extends Thread{
    private StringBuilder builder;
    private StringBuffer buffer;
    A(StringBuilder builder,StringBuffer buffer){
        this.buffer = buffer;
        this.builder = builder;
    }

    @Override
    public void run() {
        super.run();
        for (int i = 0; i < 100; i++){
            builder.append("C");
            buffer.append("C");

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("[" + Thread.currentThread().getName() + "]builder:" + builder.length());
        System.out.println("[" + Thread.currentThread().getName() + "]buffer:" + buffer.length());
    }
    //buffer总能达到1000，而builder达不到；
//    [Thread-1]builder:935
//    [Thread-1]buffer:996
//    [Thread-4]builder:935
//    [Thread-9]builder:935
//    [Thread-4]buffer:996
//    [Thread-8]builder:935
//    [Thread-8]buffer:996
//    [Thread-2]builder:935
//    [Thread-2]buffer:996
//    [Thread-3]builder:935
//    [Thread-3]buffer:996
//    [Thread-7]builder:935
//    [Thread-7]buffer:996
//    [Thread-6]builder:935
//    [Thread-9]buffer:996
//    [Thread-6]buffer:996
//    [Thread-5]builder:936
//    [Thread-5]buffer:997
//    [Thread-0]builder:939
//    [Thread-0]buffer:1000
}


