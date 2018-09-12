/**
 * 文件名：AlarmTask.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2017-10-24
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.zcpt.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.c503.sc.jxwl.zcpt.constant.ReturnMsg;

/**
 * 〈一句话功能简述〉报警相关定时任务
 * 〈功能详细描述〉
 * 
 * @author duanhy
 * @version [版本号, 2017-10-24]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Component("alarmTask")
public class AlarmTask {
    
    /**
     * 〈一句话功能简述〉定时清空缓存 〈功能详细描述〉
     * 
     * @see [类、类#方法、类#成员]
     */
    @Scheduled(cron = "0/16 * * * * ?")
    public void timingClear() {
        ReturnMsg.getAlarmMap().remove("alarm");
    }
}
