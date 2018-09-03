package com.company;

import com.hthj.data.domain.PortEnterpriseData;
import com.hthj.data.service.RequestService;
import com.hthj.data.services.Ghsjzx;
import com.hthj.data.services.GhsjzxService_Service;
import com.hthj.data.utils.DataTransformUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

import java.sql.SQLException;

public class Main {

    /**增加日志*/
    private static Logger log = Logger.getLogger(Main.class);

    public static void main(String[] args) throws SQLException {

        /**调取接口方法*/
        GhsjzxService_Service ghsjzxService_service = new GhsjzxService_Service();

        /**获取接口端口*/
        Ghsjzx ghsjzxPort = ghsjzxService_service.getGhsjzxPort();

        /**获取码头作业信息*/
        /*String s = ghsjzxPort.ghsjzxService("HH_ZHGS", "HH_ZHGS",
                4, 10000, "V_WH_WHZYSBD", "0");*/

        /**获取港口企业数据*/
        String ss = ghsjzxPort.ghsjzxService("HH_ZHGS", "HH_ZHGS",
                1, 1000, "V_WH_SBQYXX", "0");
        //每次每页获取一千条数据

        /**将数据打印到日志里面*/
        //log.info(ss);
//        System.out.println(ss);

        /**将String数据转化为JSON数据*/
        JSONObject responseJson = JSONObject.fromObject(ss);

        /**获取data数据*/
        Object data = responseJson.get("data");
       // System.out.println(data);对象不能遍历   [{key:value,key1:value1,...,keyN:valueN},{key:value,key1:value1,...}]
        JSONArray jsonArray = JSONArray.fromObject(data );
        //System.out.println(jsonArray); 集合可以遍历   [{key:value,key1:value1,...,keyN:valueN},{key:value,key1:value1,...}]
        /**统计插入数据数量*/
        int count = 0;
        for (int i = 0; i < jsonArray.size(); i++) {
            count++;
            JSONObject jsonObject = JSONObject.fromObject(jsonArray.get(i));

            /**将查询的数据封装到对象DockWorkInformation*/
            PortEnterpriseData ped = DataTransformUtils.getData(jsonObject);

            /**对象DockWorkInformation里的数据获取，通过sql存入MySql*/
            RequestService requestService = new RequestService();
            requestService.saveData(ped);
        }

        System.out.println("本次插入"+count+"条数据.");

    }




}
