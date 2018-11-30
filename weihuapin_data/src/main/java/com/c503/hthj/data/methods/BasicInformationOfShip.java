package com.c503.hthj.data.methods;

import com.c503.hthj.data.services.third.JobService;
import com.c503.hthj.data.services.third.JobServiceImplService;
import org.apache.log4j.Logger;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @auther xuxq
 * @date 2018/11/28 19:26
 */
public class BasicInformationOfShip {
    private static Logger log = Logger.getLogger(BasicInformationOfShips.class);

    public static void method() {
        /**增加定时器*/
        Timer timer = new Timer();
        //1秒后开始执行，12*60*60*1000秒后开始循环执行
        timer.schedule(new MyTask8(), 1000, 12 * 60 * 60 * 1000);
    }
}

class MyTask8 extends TimerTask {
    @Override
    public void run() {
        JobServiceImplService jobServiceImplService = new JobServiceImplService();
        JobService port = jobServiceImplService.getJobServiceImplPort();

        String s1 = port.getAllShipInfo(1, 1, "2018-10-11 00:00:00");//查询所有船舶信息

        String s2 = port.getAllWork(1, 1, "2018-10-11 00:00:00");//查询危废作业主表

        String s3 = port.getAllWorkInfo(1, 1, "2018-10-11 00:00:00");//查询危废作业附表

        String s4 = port.queryShiInfoByName("新航驳69038");//根据船舶名称查询

        String s5 = port.queryWorkInfo("");//不知查询参数？？？？？

        System.out.println(s1);
        System.out.println(s2);
        System.out.println(s3);
        System.out.println(s4);
        System.out.println(s5);

    }
}
