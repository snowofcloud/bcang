package com.spring.boot.mybatis.server;

import org.junit.Test;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @auther xuxq
 * @date 2018/12/30 23:03
 */
public class NetsfJSON {
     /**
      * 创建JSON
      */
     @Test
     public void test1() {
         String str = "{\"name\":\"zhangsan\",\"age\":20}";
         JSONObject json1 = JSONObject.fromObject(str);
         System.out.println(json1.toString());
         JSONObject json2 = new JSONObject();
         json2.put("name", "zhnagsan");
         json2.put("age", 20);
         json2.element("telphone", "15000000000");
         System.out.println(json2);

     }

    /**
      * 解析JSON
      */
     @Test
     public void test2() {
         String str = "{\"name\":\"zhangsan\",\"age\":20}";
         JSONObject json = JSONObject.fromObject(str);
         String name = json.getString("name");
         int age = json.getInt("age");
         System.out.println("name = " + name + ", age = " + age);
     }
     /**
     * JSONArray
     */
    @Test
    public void test3() {
        // JSONObject内嵌JSONArray
        JSONObject json = new JSONObject();
        json.put("name", "zhnagsan");
        json.put("age", 20);
        JSONObject json3 = new JSONObject();
        json3.put("math", 90);
        json3.put("english", 88);
        JSONArray array = new JSONArray();
        array.add(json3);
        json.put("class", array);
        System.out.println(json);
        JSONArray jsonArray = json.getJSONArray("class");
        System.out.println(jsonArray.toString());
        // 创建JSONArray
        JSONArray jsonArray2 = new JSONArray();
        jsonArray2.add(0, "zhangsan");
        jsonArray2.add(1, "lisi");
        jsonArray2.element(2, "wangwu");
        System.out.println(jsonArray2);
        // 解析
        Object object = jsonArray2.get(0);
        System.out.println(object);

    }


}
