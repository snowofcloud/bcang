package com.company;

import com.hthj.data.dao.RequestDao;
import com.hthj.data.domain.BasicInformationOfShip;
import com.hthj.data.services.Cbjbxx;
import com.hthj.data.services.CbjbxxService_Service;
import com.hthj.data.utils.DataTransformUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

import java.sql.SQLException;

public class Main {

    private static Logger log = Logger.getLogger(Main.class);

    public static void main(String[] args) throws SQLException {
        CbjbxxService_Service cbjbxxService_service = new CbjbxxService_Service();
        Cbjbxx cbjbxxPort= cbjbxxService_service.getCbjbxxPort();
        String ss = cbjbxxPort.cbjbxxService("HH_ZHGS","HH_ZHGS",
                1,1,
                "v_cb_cbjbxx_qg","0");
        //统计用时时间
        long start = System.currentTimeMillis();
        /**将String数据转化为JSON数据*/
        JSONObject responseJson1 = JSONObject.fromObject(ss);

        /**实现分页*/
        Object count1 = responseJson1.get("count");
        Integer count2 =  (Integer) count1;
        System.out.println(count2);
        Integer a = count2/2000; //page=40;
        Integer b = count2%2000;  //pageSize=1065;
        System.out.println(a);
        System.out.println(b);
        if (b>=1){
            a = a + 1;
        }
        /**统计插入数据数量*/
        int count3 = 0;
        for (int j = 71; j <= a ; j++) {
            String s = cbjbxxPort.cbjbxxService("HH_ZHGS","HH_ZHGS",
                    j,2000,
                    "v_cb_cbjbxx_qg","0");
            JSONObject responseJson = JSONObject.fromObject(s);
            /**获取data数据*/
            Object data = responseJson.get("data");
            // System.out.println(data);对象不能遍历   [{key:value,key1:value1,...,keyN:valueN},{key:value,key1:value1,...}]
            JSONArray jsonArray = JSONArray.fromObject(data );
            //System.out.println(jsonArray); 集合可以遍历   [{key:value,key1:value1,...,keyN:valueN},{key:value,key1:value1,...}]
            int count=0;
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonObject = JSONObject.fromObject(jsonArray.get(i));
                count++;
                /**将查询的数据封装到对象DockWorkInformation*/
                BasicInformationOfShip bis = DataTransformUtils.getData(jsonObject);

                /**对象DockWorkInformation里的数据获取，通过sql存入MySql*/
                RequestDao requestDao = new RequestDao();
                requestDao.saveData(bis);
            }
            count3 = count3 + count;
        }
        System.out.println("本次插入"+count3+"条数据.");
        long end = System.currentTimeMillis();
        long total = end - start;
        long totalSeconds = total / 1000;
        long totalMintues = totalSeconds / 60;
        long totalHours = totalMintues / 60;

        System.out.println("本次耗时："+totalHours+"时="+totalMintues+"分="+totalSeconds+"s秒="+total+"毫秒");

    }
}
