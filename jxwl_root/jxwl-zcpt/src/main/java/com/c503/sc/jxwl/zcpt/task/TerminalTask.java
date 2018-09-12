/**
 * 文件名：TerminalTask.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2017-11-9 
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.zcpt.task;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.c503.sc.jxwl.zcpt.service.ITerminalService;

/**
 * 〈一句话功能简述〉获取终端基础信息定时任务
 * 〈功能详细描述〉
 * 
 * @author xinzw
 * @version [版本号, 2017-11-09]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Component("terminalTask")
public class TerminalTask {
    
    /** 终端信息业务接口 */
    @Resource(name = "terminalService")
    private ITerminalService terminalService;
    
    /**
     * 〈一句话功能简述〉定时清空缓存 〈功能详细描述〉
     * 
     * @see [类、类#方法、类#成员]
     */
    //定时任务每15分钟触发一次
    @Scheduled(cron = "0 0/15 * * * ?")
    public void getTerminalInfo() {
        try {
            this.terminalService.refreshTerminalBaseInfo();
        }
        catch (Exception e) {
            e.printStackTrace();
            System.err.println("获取车辆上下线数据失败，通知浏览器失败！");
        }
    }
}