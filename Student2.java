package com.spring.boot.mybatis.server.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @auther xuxq
 * @date 2018/12/6 21:50
 */
public class Student2 {

    public static void main(String[] args) {

        //System.out.println(s.toString());
        //List<Long> arrayList = new ArrayList<>();
        HashMap<Long, Long> map = new HashMap<>();
        for (Long i = 1L; i <=1009;i++){
            Long a = (2*i*i - i);
            map.put(i,a);
            //arrayList.add(new Long(i).intValue(),a);
        }
        Long ss = 0L;
        for (Long i = 1L; i <=1009;i++){
            ss = ss + map.get(i);
        }
        System.out.println(ss);


        HashMap<Long, Long> mapp = new HashMap<>();
        for (Long j = 1L; j <=1008;j++){
            Long a = (2*j*j + j);
            mapp.put(j,a);
            //arrayList.add(new Long(i).intValue(),a);
        }
        Long sss = 0L;
        for (Long j = 1L; j <=1008;j++){
            sss = sss + mapp.get(j);
        }
        System.out.println(sss);
        System.out.println(ss+sss);


        Long  as = 1009*2018*2019L;
        System.out.println(as);
        Long ass = as-ss-sss;
        System.out.println(ass);

        System.out.println("==================================================");
        HashMap<Long, Long> map1 = new HashMap<>();
        for (Long k = 1L; k <=2018;k++){
            Long a = k*k ;
            map1.put(k,a);
        }
        Long ssss = 0L;
        for (Long k = 1L; k <=2018;k++){
            ssss = ssss + map1.get(k);
        }
        System.out.println(ssss);

    }


}
