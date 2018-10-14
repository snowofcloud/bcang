package com.c503.hthj.data.methods;

import com.c503.hthj.data.dao.first.PortEnterpriseDataRequestDao;
import com.c503.hthj.data.domain.PortEnterpriseData;
import com.c503.hthj.data.services.first.Ghsjzx;
import com.c503.hthj.data.services.first.GhsjzxService_Service;
import com.c503.hthj.data.utils.PortEnterpriseDataTransformUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;

public class PortEnterpriseDatas {

    private static Logger log = Logger.getLogger(PortEnterpriseDatas.class);

    public static void method() {

        Timer timer = new Timer();
        //1秒后开始执行，12小时后开始循环执行 //12*60*60*1000
        timer.schedule(new MyTask1(), 1000, 12*60*60*1000);

    }
}
class MyTask1 extends TimerTask {

    @Override
    public void run() {

        /**调取接口方法*/
        GhsjzxService_Service ghsjzxService_service = new GhsjzxService_Service();

        /**获取接口端口*/
        Ghsjzx ghsjzxPort = ghsjzxService_service.getGhsjzxPort();

        /**获取港口企业数据*/
        String ss = ghsjzxPort.ghsjzxService("HH_ZHGS", "HH_ZHGS",
                1, 1, "V_WH_SBQYXX", "0");

        /**将数据打印到日志里面*/
//        log.info(ss);
//        System.out.println(ss);

        /**统计用时时间*/
        long start = System.currentTimeMillis();
        /**将String数据转化为JSON数据*/
        JSONObject responseJson1 = JSONObject.fromObject(ss);
        /**实现分页*/
        Object count1 = responseJson1.get("count");
        Integer count2 = (Integer) count1;
        System.out.println("港口企业数据总数:" + count2);
        Integer a = count2 / 2000;
        Integer b = count2 % 2000;
        if (b >= 1) {
            a = a + 1;
        }
        System.out.println("港口企业数据总页数:" + a + "页");
        System.out.println("港口企业数据最后一页数据个数:" + b + "条");
        /**统计插入数据数量*/
        int count3 = 0;
        for (int j = 1; j <= a; j++) {
            String s = ghsjzxPort.ghsjzxService("HH_ZHGS", "HH_ZHGS",
                    j, 2000, "V_WH_SBQYXX", "0");
            JSONObject responseJson = JSONObject.fromObject(s);
            /**获取data数据*/
            Object data = responseJson.get("data");
            // System.out.println(data);对象不能遍历   [{key:value,key1:value1,...,keyN:valueN},{key:value,key1:value1,...}]
            JSONArray jsonArray = JSONArray.fromObject(data);
            //System.out.println(jsonArray); 集合可以遍历   [{key:value,key1:value1,...,keyN:valueN},{key:value,key1:value1,...}]
            int count = 0;
            for (int i = 0; i < jsonArray.size(); i++) {

                JSONObject jsonObject = JSONObject.fromObject(jsonArray.get(i));

                //调用查询工具
                String mapid = PortEnterpriseDataTransformUtils.queryData(jsonObject);
//                System.out.println(mapid);
                PortEnterpriseDataRequestDao portEnterpriseDataRequestDao = new PortEnterpriseDataRequestDao();
                PortEnterpriseData ped = null;
                try {
                    ped = portEnterpriseDataRequestDao.queryData(mapid);
                } catch (SQLException e) {
                    e.printStackTrace();
                    System.out.println("查询数据出现问题，请仔细检查");
                }
                if (ped != null) {
                    continue;
                }
                /**将查询的数据封装到对象DockWorkInformation*/
                count++;
                ped = PortEnterpriseDataTransformUtils.getData(jsonObject);

                /**对象DockWorkInformation里的数据获取，通过sql存入MySql数据库*/
                try {
                    portEnterpriseDataRequestDao.saveData(ped);
                } catch (SQLException e) {
                    e.printStackTrace();
                    System.out.println("保存数据出现异常，请及时处理");
                }
            }
            count3 = count3 + count;
        }
        System.out.println("港口企业数据：本次插入" + count3 + "条数据.");
        long end = System.currentTimeMillis();
        long total = end - start;
        long totalSeconds = total / 1000;
        long totalMintues = totalSeconds / 60;
        long totalHours = totalMintues / 60;

        System.out.println("港口企业数据本次耗时：" + totalHours + "时=" + totalMintues + "分=" + totalSeconds + "s秒=" + total + "毫秒");

    }

}