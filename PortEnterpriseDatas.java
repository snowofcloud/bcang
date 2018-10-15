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
		//打印日期格式
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式，精确到日期
		Date date = new Date();
		System.out.println("此刻时间："+df.format(date));// new Date()为获取当前系统时间
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式，精确到时分秒
        System.out.println("此刻时间："+df.format(new Date()));// new Date()为获取当前系统时间
        String fileName = "企业污泥利用处置台账信息" +format+ ".xls";
	}

}