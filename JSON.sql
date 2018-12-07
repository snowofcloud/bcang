


//
import com.alibaba.fastjson.JSONObject;
	JSONObject who = JSONObject.parseObject("who");
     JSONArray are = JSONObject.parseArray("are");
        Object you = JSONObject.parse("you");
		  String s = JSONObject.toJSONString(who);
          Object o = JSONObject.toJSON(who);
		  
	JSONObject we = who.getJSONObject("we");
     JSONArray us = who.getJSONArray("us");
        Object o1 = who.get("o");	  
		
				Object o2 = are.get(0);
	JSONObject jsonObject = are.getJSONObject(0);
	  JSONArray jsonArray = are.getJSONArray(0);
  
  
  
  
  
import com.alibaba.fastjson.JSONArray;
		JSONArray a = JSONArray.parseArray("a");
       JSONObject b = JSONArray.parseObject("b");
           Object c = JSONArray.parse("c");


      JSONObject a1 = a.getJSONObject(0);
       JSONArray a2 = a.getJSONArray(0);
		  Object a3 = a.get(0);

      JSONObject aa = b.getJSONObject("aa");
      JSONArray aaa = b.getJSONArray("aaa");





import net.sf.json.JSONObject;
	JSONObject student1 = JSONObject.fromObject(student);
			   Object o = JSONObject.toBean(student1);

              Object hu = student1.get("hu");//String的key或对象的key
           JSONArray hh = student1.getJSONArray("hh");
         JSONObject hhh = student1.getJSONObject("hhh");
	
import net.sf.json.JSONArray;
	JSONArray student2 = JSONArray.fromObject(student);
			 Object o1 = JSONArray.toArray(student2);

			 Object o2 = student2.get(0);
   JSONArray jsonArray = student2.getJSONArray(0);
 JSONObject jsonObject = student2.getJSONObject(0);























