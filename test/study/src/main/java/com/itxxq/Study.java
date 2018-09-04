package com.itxxq;

public class Study {
    public static void main(String[] args) {

        /**测试null和空字符串*/
        /*String a=null;
        int i = a.indexOf(0);
        System.out.println(i);*/

        /**测试系统时间*/
        long start = System.currentTimeMillis();
        int[] arr = new int[100000000];
        arr[0] = 1;
        for (int i = 0; i <arr.length-1 ; i++) {
            arr[i+1] = arr[i]+1;
        }
        System.out.println(arr[10000000]);
        long end = System.currentTimeMillis();
        long total = end - start;
        long totalSeconds = total / 1000;
        long totalMintues = totalSeconds / 60;
        long totalHours = totalMintues / 60;
        System.out.println(totalHours+"时="+totalMintues+"分="+totalSeconds+"秒="+total+"毫秒");



    }
}
