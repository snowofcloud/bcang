package com.spring.boot.mybatis.server;

import org.junit.Test;
import com.google.gson.Gson;
/**
 * @auther xuxq
 * @date 2018/12/30 23:26
 */
public class GSONTest {
    @Test  //json工具之gjson的使用
    public void test() {
        Gson gson = new Gson();
        Person person = new Person();
        person.setName("zhangsan");
        person.setAge(20);
        String json = gson.toJson(person);
        // {"name":"zhangsan","age":20}
        System.out.println(json);
        Person fromJson = gson.fromJson(json, Person.class);
        // Person [name=zhangsan, age=20]
         System.out.println(fromJson);
     }
}
