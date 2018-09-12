/**
 * 文件名：DataServiceEngine.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2015年12月16日
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.zcpt.init;

import com.c503.sc.jxwl.zcpt.consumer.MessageConsumer;
import com.c503.sc.jxwl.zcpt.thread.AdministrativeAreaAcrossThread;
import com.c503.sc.jxwl.zcpt.thread.AlarmHandleThread;
import com.c503.sc.jxwl.zcpt.thread.LimitAreaAlarmThread;
import com.c503.sc.jxwl.zcpt.thread.LineAlarmThread;
import com.c503.sc.jxwl.zcpt.thread.RealLocationThread;
import com.c503.sc.jxwl.zcpt.utils.ConfigInstance;
import com.c503.sc.jxwl.zcpt.utils.LogCode;
import com.c503.sc.log.LoggingManager;

/**
 * 
 * 数据服务引擎初始化
 * 〈功能详细描述〉
 * 
 * @author zhangjy
 * @version [版本号, 2015年12月17日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public final class DataServiceEngine {
    
    /** 日志操作对象 */
    private static final LoggingManager LOGGER =
        LoggingManager.getLogger(DataServiceEngine.class);
    
    /** monitor消费者 */
    private static MessageConsumer messageConsumer;
    
    /** 实时位置线程 */
    private RealLocationThread realLocationThread;
    
    /** 行政区域跨域线程 */
    private AdministrativeAreaAcrossThread administrativeAreaAcrossThread;
    
    /** 偏离区域报警线程 */
    private LineAlarmThread lineAlarmThread;
    
    /** 限制区域报警线程 */
    private LimitAreaAlarmThread limitAreaAlarmThread;
    
    /** 报警处理线程 */
    private AlarmHandleThread alarmHandleThread;
    
    /**
     * 
     * 构造方法
     * 〈功能详细描述〉
     * 
     * @see [类、类#方法、类#成员]
     */
    public DataServiceEngine() {
        // 项目配置
        try {
            ConfigInstance.init();
        }
        catch (Exception e) {
            LOGGER.error(LogCode.CONFIG_READ_ERROR, e);
        }
        
    }
    
    /**
     * 
     * Spring注入messageConsumer
     * 〈功能详细描述〉
     * 
     * @param messageConsumer 消息消费者
     * @see [类、类#方法、类#成员]
     */
    public static void setMessageConsumer(MessageConsumer messageConsumer) {
        DataServiceEngine.messageConsumer = messageConsumer;
    }
    
    /**
     * 
     * 数据服务引擎初始化
     * 〈功能详细描述〉
     * 
     * @throws Exception 初始化异常
     * @return true 成功 false 失败
     * @see [类、类#方法、类#成员]
     */
    public boolean init()
        throws Exception {
        try {
            // 短信及短报文消费
            messageConsumer.consumeMessage(ConfigInstance.getTopic(),
                ConfigInstance.getGroupID());
            // 更新实时位置
            realLocationThread.setName("realLocationThread -- 车辆实时位置线程");
            realLocationThread.start();
            // 行政区域跨域记录
            administrativeAreaAcrossThread.setName("administrativeAreaAcrossThread -- 行政区域出入记录线程");
            administrativeAreaAcrossThread.start();
            // 偏离路线报警
            // lineAlarmThread.start();
            // 限制区域报警
            limitAreaAlarmThread.setName("limitAreaAlarmThread -- 限制区域报警线程");
            limitAreaAlarmThread.start();
            // 报警处理
            alarmHandleThread.setName("alarmHandleThread -- 报警处理线程");
            alarmHandleThread.start();
        }
        catch (Exception e) {
            LOGGER.error(LogCode.KAFKA_CONSUME_ERROR, e);
            return false;
        }
        return true;
    }
    
    public void setRealLocationThread(RealLocationThread realLocationThread) {
        this.realLocationThread = realLocationThread;
    }
    
    public void setAdministrativeAreaAcrossThread(
        AdministrativeAreaAcrossThread administrativeAreaAcrossThread) {
        this.administrativeAreaAcrossThread = administrativeAreaAcrossThread;
    }
    
    public void setLineAlarmThread(LineAlarmThread lineAlarmThread) {
        this.lineAlarmThread = lineAlarmThread;
    }
    
    public void setLimitAreaAlarmThread(
        LimitAreaAlarmThread limitAreaAlarmThread) {
        this.limitAreaAlarmThread = limitAreaAlarmThread;
    }
    
    public void setAlarmHandleThread(AlarmHandleThread alarmHandleThread) {
        this.alarmHandleThread = alarmHandleThread;
    }
}
