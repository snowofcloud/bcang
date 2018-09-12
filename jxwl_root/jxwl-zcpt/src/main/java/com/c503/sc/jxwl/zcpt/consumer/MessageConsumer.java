/*
 * 文件名：MessageConsumer.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2015年10月26日
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.zcpt.consumer;

import javax.annotation.Resource;

import com.c503.sc.jxwl.common.constant.NumberContant;
import com.c503.sc.jxwl.zcpt.service.IDseMessageService;
import com.c503.sc.jxwl.zcpt.service.impl.DseMessageServiceImpl;
import com.c503.sc.jxwl.zcpt.utils.LogCode;
import com.c503.sc.kafka.clients.ConsumerClient;
import com.c503.sc.log.LoggingManager;
import com.c503.sc.utils.basetools.C503StringUtils;

/**
 * 
 * 〈一句话功能简述〉kafka消息拉取
 * 〈功能详细描述〉
 * 
 * @author Zhangjy
 * @version [版本号, 2015年10月26日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class MessageConsumer extends ConsumerClient {
    /** 记录日志 */
    private static final LoggingManager LOGGER =
        LoggingManager.getLogger(MessageConsumer.class);
    
    /** 短信存业务数据库 */
    @Resource(name = "dseMessageService")
    private IDseMessageService dseMessageService;
    
    @Override
    public void handleMessage(String message) {
        if (!C503StringUtils.isEmpty(message)) {
            try {
                if (NumberContant.TEN < DseMessageServiceImpl.getRealLocationBlockingQueue()
                    .size()
                    || NumberContant.FIVE_HUNDRED < DseMessageServiceImpl.getAdminAreaBlockingQueue()
                        .size()
                    || NumberContant.FIVE_HUNDRED < DseMessageServiceImpl.getLineAlarmBlockingQueue()
                        .size()
                    || NumberContant.FIVE_HUNDRED < DseMessageServiceImpl.getLimitAreaBlockingQueue()
                        .size()
                    || NumberContant.FIVE_HUNDRED < DseMessageServiceImpl.getAlarmBlockingQueue()
                        .size()) {
                    Thread.sleep(NumberContant.ONE_THOUSAND);
                }
                dseMessageService.saveMessage(message);
            }
            catch (Exception e) {
                LOGGER.error(LogCode.SYSTEM_ERROR, e);
                e.printStackTrace();
                try {
                    Thread.sleep(NumberContant.ONE_THOUSAND);
                }
                catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }
        
    }
}
