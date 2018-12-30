package com.spring.boot.mybatis.server.entity;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
/**
 * @auther xuxq
 * @date 2018/12/30 22:44
 */
public class OrgJsonTest {
    /**
     * java原生json处理
     */
        /**
       * JSON的初始化
       */
         @Test
         public void test1() {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", "zhangsan");
            jsonObject.put("age", 20);
            jsonObject.put("married", false);
            System.out.println(jsonObject);// {"name":"zhangsan","married":false,"age":20}

            JSONArray jsonArray = new JSONArray();
            jsonArray.put(0, true);
            jsonArray.put(1, "lisi");
            jsonArray.put(2, 22);
            System.out.println(jsonArray);// [true,"lisi",22]
        }



         /**
      * JSON的解析
      */
        @Test
        public void test2() {
            // 初始化
            JSONObject jsonObject = new JSONObject("{\"name\":\"zhangsan\",\"married\":false,\"age\":20}");
            JSONArray jsonArray = new JSONArray("[true,\"lisi\",22]");
            String name = jsonObject.getString("name");
            int age = jsonObject.getInt("age");
            boolean married = jsonObject.getBoolean("married");
            System.out.println("name=" + name + "\nage=" + age + "\nmarried=" + married);
            // name=zhangsan
            // age=20
            // married=false
            boolean arr1 = jsonArray.getBoolean(0);
            String arr2 = jsonArray.getString(1);
            int arr3 = jsonArray.getInt(2);
            System.out.println(arr1 + "\t" + arr2 + "\t" + arr3);//true	lisi	22
         }

        /**
      * JSONObject和JSONArray的相互嵌套
      */
         @Test
         public void test3() {
            // 初始化
            JSONObject jsonObject = new JSONObject("{\"name\":\"zhangsan\",\"married\":false,\"age\":20}");
            JSONObject address = new JSONObject("{\"address\":\"China\"}");
            JSONArray education = new JSONArray("[\"小学\",\"初中\",\"高中\"]");

            jsonObject.put("addr", address);
            jsonObject.put("edu", education);
            System.out.println(jsonObject);// {"edu":["小学","初中","高中"],"name":"zhangsan","addr":{"address":"China"},"married":false,"age":20}

         }

}
