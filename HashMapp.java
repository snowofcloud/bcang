package com.spring.boot.mybatis.server;

import java.util.*;

/**
 * @auther xuxq
 * @date 2019/1/15 20:07
 */
public class HashMapp {
    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>();
        Map<String, String> map1 = Collections.synchronizedMap(map);//线程安全
        map.put("牛郎","织女");
        map.put("梁山伯","祝英台");
        map.put("司马相如","卓文君");
        //map.
        //迭代器遍历 法一：
        Set<String> keySet = map.keySet();            //利用map的keySet方法，将集合中的key封装到set集合中
        Iterator<String> iterator = keySet.iterator();//获取迭代器
        while(iterator.hasNext()){
            String key = iterator.next();
            String value = map.get(key);
            System.out.println(key + "=" + value);
        }
        System.out.println("=====================================================================");
        //迭代器遍历 法二：
        Set<Map.Entry<String, String>> entries = map.entrySet(); //利用map的entrySet方法，将键值封装到entry中
        Iterator<Map.Entry<String, String>> iterator1 = entries.iterator();
        while (iterator1.hasNext()){
            Map.Entry<String, String> next = iterator1.next();
            String key = next.getKey();
            String value = next.getValue();
            System.out.println(key + "=" + value);

        }
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        //增强for循环
        for (String s: keySet) {
            String s1 = map.get(s);
            System.out.println(s + "=" + s1);
        }
        System.out.println("=====================================================================");
        for (Map.Entry<String, String> entry: entries){
            String key = entry.getKey();
            String value = entry.getValue();
            System.out.println(key + "=" + value);
        }

    }
}
