package com.spring.boot.mybatis.server;
import org.json.simple.JSONArray;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;
/**
 * @auther xuxq
 * @date 2018/12/30 23:19
 */
public class JSONSimple {
    @Test     //json工具之json.simple的使用
    public void test1() throws ParseException {
         // 创建
        JSONObject obj = new JSONObject();
        obj.put("name", "zhangsan");
        obj.put("age", 20);
        obj.put("tel", "15000000000");
        System.out.println(obj);//{"name":"zhangsan","tel":"15000000000","age":20}
        // 解析
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(obj.toString());
        System.out.println(json);//{"name":"zhangsan","tel":"15000000000","age":20}

        Object name = json.get("name");
        Object age = json.get("age");
        System.out.println("name = " + name + ", age = " + age);//name = zhangsan, age = 20

    }
}
