package com.c503.hthj.data.methods;

import com.c503.hthj.data.dao.first.BasicPersonnelInformationRequestDao;
import com.c503.hthj.data.domain.BasicPersonnelInformation;
import com.c503.hthj.data.services.first.Ghsjzx;
import com.c503.hthj.data.services.first.GhsjzxService_Service;
import com.c503.hthj.data.utils.BasicPersonnelInformationTransformUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;

public class BasicPersonnelInformations {
    /**
     * 增加日志
     */
    private static Logger log = Logger.getLogger(BasicPersonnelInformations.class);

    public static void method() {

        Timer timer = new Timer();
        //2秒后开始执行，12小时后开始循环执行    //12*60*60*1000
        timer.schedule(new MyTask3(), 2000, 12*60*60*1000);//1秒后开始执行，60秒后开始循环执行

    }
}

class MyTask3 extends TimerTask {

    @Override
    public void run() {

        /**调取接口方法*/
        GhsjzxService_Service ghsjzxService_service = new GhsjzxService_Service();

        /**获取接口端口*/
        Ghsjzx ghsjzxPort = ghsjzxService_service.getGhsjzxPort();

        /**获取港口企业数据*/
        String ss = ghsjzxPort.ghsjzxService("HH_ZHGS", "HH_ZHGS",
                1, 1, "V_WH_RYJCXX", "0");

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
        System.out.println("人员基础信息数据总数:" + count2);
        Integer a = count2 / 2000;
        Integer b = count2 % 2000;
        if (b >= 1) {
            a = a + 1;
        }
        System.out.println("人员基础信息总页数:" + a + "页");
        System.out.println("人员基础信息最后一页数据个数:" + b + "条");
        /**统计插入数据数量*/
        int count3 = 0;
        for (int j = 1; j <= a; j++) {
            String s = ghsjzxPort.ghsjzxService("HH_ZHGS", "HH_ZHGS",
                    j, 2000, "V_WH_RYJCXX", "0");
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
                String id = BasicPersonnelInformationTransformUtils.queryData(jsonObject);
//                System.out.println(mapid);
                BasicPersonnelInformationRequestDao basicPersonnelInformationRequestDao =
                        new BasicPersonnelInformationRequestDao();
                BasicPersonnelInformation bpi = null;
                try {
                    bpi = basicPersonnelInformationRequestDao.queryData(id);
                } catch (SQLException e) {
                    e.printStackTrace();
                    System.out.println("查询数据出现问题，请仔细检查");
                }
                if (bpi != null) {
                    continue;
                }
                /**将查询的数据封装到对象DockWorkInformation*/
                count++;
                bpi = BasicPersonnelInformationTransformUtils.getData(jsonObject);

                /**对象DockWorkInformation里的数据获取，通过sql存入MySql数据库*/
                try {
                    basicPersonnelInformationRequestDao.saveData(bpi);
                } catch (SQLException e) {
                    e.printStackTrace();
                    System.out.println("保存数据出现异常，请及时处理");
                }
            }
            count3 = count3 + count;
        }
        System.out.println("人员基础信息本次插入" + count3 + "条数据.");
        long end = System.currentTimeMillis();
        long total = end - start;
        long totalSeconds = total / 1000;
        long totalMintues = totalSeconds / 60;
        long totalHours = totalMintues / 60;

        System.out.println("人员基础信息本次耗时：" + totalHours + "时=" + totalMintues + "分=" + totalSeconds + "s秒=" + total + "毫秒");

    }

}