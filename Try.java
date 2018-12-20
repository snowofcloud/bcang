package com.spring.boot.mybatis.server.entity;

/**
 * @auther xuxq
 * @date 2018/12/20 22:25
 */
public class Try {
    public static void main(String[] args) {
        Long start = System.nanoTime();
        int a = 0;
        for (int i = 0; i <1000000; i++){
            a++;
        }
        Long end = System.nanoTime();
        System.out.println(end - start);//5993550


        Long start2 = System.nanoTime();
        int aa = 0;
        for (int i = 0; i <1000000; i++){
            try {
                aa++;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Long end2 = System.nanoTime();
        System.out.println(end2 - start2);//5980163

        Long start3 = System.nanoTime();
        int aaa = 0,b =0;
        for (int i = 0; i <1000000; i++){
            if (aaa==999888){
                try {
                    aaa = aaa/b;
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    continue;
                }
            }

        }
        Long end3 = System.nanoTime();
        System.out.println(end3 - start3);//29535782  29毫秒
        //如果 try catch 没有抛出异常，那么其对性能几乎没有影响。但如果抛出异常，那对程序将造成几百倍的性能影响。
    }
}
