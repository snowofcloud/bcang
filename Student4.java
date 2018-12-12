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

        //object转String的几种方法对比
        /*
        1）.object.toString()方法
        这种方法要注意的是object不能为null,否则会报NullPointException，一般不用这种方法。
        2）.String.valueOf(object)方法
        这种方法不必担心object为null的问题，若为null，会将其转换为"null"字符串，而不是null。这一点要特别注意。"null"和null不是一个概念。
        3）.(String)(object)方法
        这种方法也不必担心object为null的问题。但是，object要是能转换为String的对象。若Object object = 1,再(String)1，会报类转换异常。
        4). “”+object方法
        这种方法也不必担心object为null的问题。但若object为null,会返回"null"字符串，和String.valueOf(object)一样。
        */
    }
}
