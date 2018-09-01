package com.company;

import com.hthj.data.domain.DockWorkInformation;
import com.hthj.data.services.Ghsjzx;
import com.hthj.data.services.GhsjzxService_Service;
import com.hthj.data.setvice.RequestService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

import java.sql.SQLException;

public class Main {

    private static Logger log = Logger.getLogger(Main.class);

    public static void main(String[] args) throws SQLException {

        GhsjzxService_Service ghsjzxService_service = new GhsjzxService_Service();

        Ghsjzx ghsjzxPort = ghsjzxService_service.getGhsjzxPort();

        //码头作业信息
        String s = ghsjzxPort.ghsjzxService("HH_ZHGS", "HH_ZHGS",
                1, 3000, "V_WH_WHZYSBD", "0");

        //港口企业数据
        /*String ss = ghsjzxPort.ghsjzxService("HH_ZHGS", "HH_ZHGS",
                1, 10, "V_WH_SBQYXX", "0");*/
        //System.out.println(s);
        //log.info(ss);

        JSONObject responseJson = JSONObject.fromObject(s);
        //System.out.println(responseJson);
        //Object message = responseJson.get("message");
        //System.out.println(message);
        Object data = responseJson.get("data");
       // System.out.println(data);      对象不能遍历   [{key:value,key1:value1,...,keyN:valueN},{key:value,key1:value1,...}]
        JSONArray jsonArray = JSONArray.fromObject(data );
        //System.out.println(jsonArray); 集合可以遍历   [{key:value,key1:value1,...,keyN:valueN},{key:value,key1:value1,...}]
        int count = 0;
        for (int i = 0; i < jsonArray.size(); i++) {
            count++;
            JSONObject jsonObject = JSONObject.fromObject(jsonArray.get(i));
            //System.out.println(jsonObject.toString());//对象 {key:value,key1:value1,...,keyN:valueN}
           // System.out.println(jsonObject);
            Object appliid = jsonObject.get("APPLIID");
            //System.out.println(appliid);
            Object linkmannm = jsonObject.get("LINKMANNM");
           // System.out.println(linkmannm);
            Object telephonenumnb = jsonObject.get("TELEPHONENUMNB");
            //System.out.println(telephonenumnb);
            Object consigner = jsonObject.get("CONSIGNER");
            //System.out.println(consigner);

            /**码头作业信息进行封装*/
            DockWorkInformation dwi = new DockWorkInformation();
            dwi.setAPPLIID((String) appliid);
            dwi.setLINKMANNM((String)linkmannm);
            dwi.setTELEPHONENUMNB((String)telephonenumnb);
            dwi.setCONSIGNER((String)consigner);
           // String appliid1 = dwi.getAPPLIID();
            //System.out.println(appliid1);

            /**把dwi转发到serlet*/
            RequestService requestService = new RequestService();
            //requestService.saveData(DockWorkInformation dwi);
            requestService.saveData(dwi);


        }
        System.out.println("本次插入"+count+"条数据,总共插入");
    }




}
