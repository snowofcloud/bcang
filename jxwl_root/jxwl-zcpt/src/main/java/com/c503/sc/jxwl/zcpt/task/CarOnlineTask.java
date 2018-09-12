/*
 * 文件名：CarOnlineTask.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2017-10-23
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.zcpt.task;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.c503.sc.jxwl.zcpt.service.IVehicleService;

/**
 * 〈一句话功能简述〉车辆上线定时任务
 * 〈功能详细描述〉
 * 
 * @author duanhy
 * @version [版本号, 2017-10-23]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Component("carOnlineTask")
public class CarOnlineTask {
    
    /** 车辆信息业务接口 */
    @Resource(name = "vehicleService")
    private IVehicleService vehicleService;
    
   
    
    /**
     * 〈一句话功能简述〉定时处理 获得所有车辆位置表中的所有数据 依据之前的状态 处理数据
     * 〈功能详细描述〉
     * 
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @Scheduled(cron = "0/5 * * * * ?")
    public void getCarOfflineTaskWork() {
        try {
            this.vehicleService.getCarOnOffline();
        }
        catch (Exception e) {
            e.printStackTrace();
            System.err.println("获取车辆上下线数据失败，通知浏览器失败！");
        }
    }
    
}
