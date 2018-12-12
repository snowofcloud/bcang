package com.spring.boot.mybatis.server.entity;

/**
 * @auther xuxq
 * @date 2018/12/12 22:55
 */
public class Student4 {
    public static void main(String[] args) {
        Object object = null;
        System.out.println("(String)null和\"null\"比较的结果:"+"null".equals((String) object));
        System.out.println("String.valueof(null)和\"null\"比较的结果:" + "null".equals(String.valueOf(object)));
        System.out.println("\"\"和\"null\"比较的结果"+ "null".equals(""+object));
    }
}
