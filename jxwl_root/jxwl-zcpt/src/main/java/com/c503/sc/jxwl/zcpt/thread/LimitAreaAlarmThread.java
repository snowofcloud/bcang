/*
 * 文件名：limitAreaAlarmThread.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2017-10-25
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.zcpt.thread;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.c503.sc.jxwl.common.bean.LocationEntity;
import com.c503.sc.jxwl.common.constant.NumberContant;
import com.c503.sc.jxwl.zcpt.service.IAlarmManageService;
import com.c503.sc.jxwl.zcpt.service.IDseMessageService;
import com.c503.sc.jxwl.zcpt.service.impl.DseMessageServiceImpl;
import com.c503.sc.log.LoggingManager;

/**
 * 〈一句话功能简述〉限制区域报警线程
 * 〈功能详细描述〉
 * 
 * @author duanhy
 * @version [版本号, 2017-10-25]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Service(value = "limitAreaAlarmThread")
public class LimitAreaAlarmThread extends Thread {
    
    /** 日志操作对象 */
    private static final LoggingManager LOGGER =
        LoggingManager.getLogger(LimitAreaAlarmThread.class);
    
    /** kafka实时数据处理业务接口 */
    @Resource(name = "dseMessageService")
    private IDseMessageService dseMessageService;
    
    /** 报警管理业务接口 */
    @Resource(name = "alarmManageService")
    private IAlarmManageService alarmManageService;
    
    /** 系统初始化时间 */
    private long initTime = System.currentTimeMillis();
    
    @Override
    public void run() {
        // 休眠时间：3S
        final int sleep = NumberContant.THREE_THOUSAND;
        while (true) {
            try {
                if (NumberContant.FIVE_HUNDRED <= DseMessageServiceImpl.getLimitAreaBlockingQueue()
                    .size()
                    || (!DseMessageServiceImpl.getLimitAreaBlockingQueue()
                        .isEmpty() && System.currentTimeMillis() - initTime >= sleep)) {
                    List<LocationEntity> locList =
                        new ArrayList<LocationEntity>();
                    DseMessageServiceImpl.getLimitAreaBlockingQueue()
                        .drainTo(locList, NumberContant.ONE_THOUSAND);
                    this.alarmManageService.handleAlarm(locList,
                        "limitAreaAlarm");
                }
                initTime = System.currentTimeMillis();
                LOGGER.info(1, "LimitAreaAlarmThread - 限制区域报警线程");
                try {
                    Thread.sleep(sleep);
                }
                catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
            catch (Exception e) {
                e.printStackTrace();
                try {
                    Thread.sleep(sleep);
                }
                catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
            
        }
    }
    
    /**
     * 
     * 〈一句话功能简述〉获得kafka实时位置信息
     * 〈功能详细描述〉
     * 
     * @return kafka实时位置信息
     * @see [类、类#方法、类#成员]
     */
    public IDseMessageService getDseMessageService() {
        return dseMessageService;
    }
    
    /**
     * 
     * 〈一句话功能简述〉设置kafka实时位置信息
     * 〈功能详细描述〉
     * 
     * @param dseMessageService kafka实时位置信息
     * @see [类、类#方法、类#成员]
     */
    public void setDseMessageService(IDseMessageService dseMessageService) {
        this.dseMessageService = dseMessageService;
    }
    
    public IAlarmManageService getAlarmManageService() {
        return alarmManageService;
    }
    
    public void setAlarmManageService(IAlarmManageService alarmManageService) {
        this.alarmManageService = alarmManageService;
    }
}
