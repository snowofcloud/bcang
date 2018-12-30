package com.spring.boot.mybatis.server;

import org.junit.Test;

import com.alibaba.fastjson.JSON;

import com.alibaba.fastjson.JSONArray;

import com.alibaba.fastjson.JSONObject;
/**
 * @auther xuxq
 * @date 2018/12/30 23:36
 */
public class FastjsonTest {//json工具之fastjson的使用

        /**
      * 创建JSON
      */
        @Test
        public void test1() {
            // 创建一个 Person 对象
             Person person = new Person();
            person.setName("zhangsan");
            person.setAge(20);
            // 将 Person 对象转为 json 字符串
            String json = JSON.toJSONString(person);
            // 输出结果：{"age":20,"name":"zhangsan"}
            System.out.println(json);
            // 将 Person 对象转为 json 字符串, 这个字符串是经过格式化的
             String format_json = JSON.toJSONString(person, true);
            System.out.println(format_json);
        }
        @Test
        public void test2() {
            String json_array = "[{\"age\":20,\"name\":\"zhangsan\"},{\"age\":22,\"name\":\"lisi\"}]";
            // 将 json 字符串转为 JSONArray 对象
            JSONArray array = JSON.parseArray(json_array);
            System.out.println(array);
            // 将 json 字符串转为 List 集合
            List<Person> list = JSON.parseArray(json_array, Person.class);
            for (Person person : list) {
                System.out.println(person);
            }
         }

         /**
      * JSON解析
      */
        @Test
        public void test3() {
            String json = "{\"age\":20,\"name\":\"zhangsan\"}";
            // 将 json 字符串转为 JSONObject 对象
            JSONObject object = JSON.parseObject(json);
            System.out.println(object);
            // 将 json 字符串转为 Student 对象
             Person person = JSON.parseObject(json, Person.class);
             System.out.println(person);
        }
}
