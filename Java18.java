package com.xly.spring.boot.mybatisplus.server.controller;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * Java8新特性之Optional
 * @auther xuxq
 * @date 2018/12/24 21:24
 */
public class Java18 {
    public static void main(String[] args) {
        //Optional 类是一个可以为null的容器对象。
        //of 方法的使用可以说就和我们正常的使用是一样的.
        Optional<String> name = Optional.of("小虎");
        Optional<Integer> name1 = Optional.of(9);
            //of 方法不允许我们传入Null,否则就出抛出空指针异常,如下就是错误的.
        //Optional<String> name2 = Optional.of(null);

        //ofNullable 的使用就不会因为传入的是 Null 而出现空指针了,推荐使用.
        Optional<String> name3 = Optional.ofNullable("小狗");
        Optional<String> name4 = Optional.ofNullable(null);


        //使用 empty 方法来创建一个空 String.
        Optional<String> empty = Optional.empty();
        //System.out.println(name4.get());

        //这个方法的使用就要看我们创建的时候到底有没有给对象传入值了,如果有值,正常取出.如果空值.则会抛出NoSuchElementException异常.
        System.out.println(name3.get());

        //默认值,也就是说取值的时候如果对象有值.返回正常值.如果没值.返回默认值.
        System.out.println(name3.orElse("Default"));
        System.out.println(name4.orElse("Default"));

        // orElseGet 的使用结果与orElse的结果没差,只不能返回值为空的时候由Supplier接口生成的值
        System.out.println(name4.orElseGet(()->"Default"));
    }
}