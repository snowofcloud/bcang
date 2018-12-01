package com.c503.hthj.data.methods;

import com.c503.hthj.data.dao.thrid.AllWorkInfoRequestDao;
import com.c503.hthj.data.domain.AllWorkInfo;
import com.c503.hthj.data.services.third.JobService;
import com.c503.hthj.data.services.third.JobServiceImplService;
import com.c503.hthj.data.utils.AllWorkInfoTransformUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @auther xuxq
 * @date 2018/12/1 0:19
 */
public class AllWorkInfos {
    private static Logger log = Logger.getLogger(AllShipInfos.class);

    public static void method() {
        /**增加定时器*/
        Timer timer = new Timer();
        //1秒后开始执行，12*60*60*1000秒后开始循环执行
        ///timer.schedule(new MyTask1(), 1000, 12 * 60 * 60 * 1000);
        timer.schedule(new MyTask11(), 1000, 12 * 60 * 60 * 1000);
        System.out.println((((((((((((((((((((((((((666666))))))))))))))))))))))))));
    }
}

class MyTask11 extends TimerTask {

    @Override
    public void run() {

        JobServiceImplService jobServiceImplService = new JobServiceImplService();
        JobService port = jobServiceImplService.getJobServiceImplPort();
        String ss = port.getAllWorkInfo(1, 1, "2018-10-11 00:00:00");//查询危废作业主表
        System.out.println(ss);
        //统计用时时间
        long start = System.currentTimeMillis();
        /**将String数据转化为JSON数据*/
        JSONObject responseJson1 = JSONObject.fromObject(ss);

        /**实现分页*/
        Object count1 = responseJson1.get("count");
        Integer count2 = (Integer) count1;
        Integer a = count2 / 2000; //page=40;
        Integer b = count2 % 2000;  //pageSize=1065;
        System.out.println("船舶基本信息(全国船舶基本信息)数据总数:" + count2);
        if (b >= 1) {
            a = a + 1;
        }
        System.out.println("船舶基本信息(全国船舶基本信息)总页数:" + a + "页");
        System.out.println("船舶基本信息(全国船舶基本信息)最后一页数据个数:" + b + "条");
        /**统计插入数据数量*/
        int count3 = 0;
        for (int j = 1; j <= a; j++) {
            String s = port.getAllWorkInfo(j, 1000, "2018-10-11 00:00:00");//查询所有船舶信息

            JSONObject responseJson = JSONObject.fromObject(s);
            /**获取data数据*/
            Object data = responseJson.get("data");
            // System.out.println(data);对象不能遍历   [{key:value,key1:value1,...,keyN:valueN},{key:value,key1:value1,...}]
            JSONArray jsonArray = JSONArray.fromObject(data);
            System.out.println(jsonArray); //集合可以遍历   [{key:value,key1:value1,...,keyN:valueN},{key:value,key1:value1,...}]
            int count = 0;
            for (int i = 0; i < jsonArray.size(); i++) {

                JSONObject jsonObject = JSONObject.fromObject(jsonArray.get(i));
                //调用查询工具
                String fid = AllWorkInfoTransformUtils.queryData(jsonObject);
                AllWorkInfoRequestDao allWorkInfoRequestDao = new AllWorkInfoRequestDao();
                AllWorkInfo bis = null;
                try {
                    bis = allWorkInfoRequestDao.queryData(fid);
                } catch (SQLException e) {
                    e.printStackTrace();
                    System.out.println("查询船舶基本信息主键有误，请仔细检查。");
                }
                if (bis != null) {
                    continue;
                }
                count++;
                /**将查询的数据封装到对象AllShipInfo*/
                bis = AllWorkInfoTransformUtils.getData(jsonObject);
                /**对象AllShipInfo里的数据获取，通过sql存入MySql*/
                try {
                    allWorkInfoRequestDao.saveData(bis);

                } catch (SQLException e) {
                    e.printStackTrace();
                    System.out.println("保存船舶基本信息数据有误，请仔细检查。");
                }
            }
            count3 = count3 + count;
        }

        System.out.println("船舶基本信息(全国船舶基本信息)本次插入" + count3 + "条数据.");
        long end = System.currentTimeMillis();
        long total = end - start;
        long totalSeconds = total / 1000;
        long totalMintues = totalSeconds / 60;
        long totalHours = totalMintues / 60;

        System.out.println("船舶基本信息(全国船舶基本信息)本次耗时：" + totalHours + "时=" + totalMintues + "分=" + totalSeconds + "s秒=" + total + "毫秒");

    }
}
