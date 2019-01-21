package com.xly.spring.boot.mybatisplus.server.entity.base;

import java.lang.reflect.Field;

/**
 * @auther xuxq
 * @date 2018/12/26 23:39
 */
public class Test {
    public static void main(String[] args) throws Exception {
        // 创建字符串"Hello World"， 并赋给引用s
        String s = "Hello World";
        //把这个s保存一份用于对比
        String temp = s;
        System.out.println("s和temp是否相等？ " + s.equals(temp));
        System.out.println("s = " + s); // Hello World
        // 获取String类中的value字段
        Field valueFieldOfString = String.class.getDeclaredField("value");
        // 改变value属性的访问权限
        valueFieldOfString.setAccessible(true);
        // 获取s对象上的value属性的值
        char[] value = (char[]) valueFieldOfString.get(s);
        // 改变value所引用的数组中的第5个字符
        value[5] = 'D';
        System.out.println(s); // Hello_World
        System.out.println("s和temp是否相等？ " + s.equals(temp));

        //利用反射绕过了私有权限修改了String底层的 char[]数组
    }

}
