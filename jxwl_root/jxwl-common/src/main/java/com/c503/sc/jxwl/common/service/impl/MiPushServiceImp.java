package com.c503.sc.jxwl.common.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.c503.sc.base.exception.CustomException;
import com.c503.sc.jxwl.common.bean.MiPushMessageEntity;
import com.c503.sc.jxwl.common.constant.NumberContant;
import com.c503.sc.jxwl.common.service.IMiPushService;
import com.c503.sc.log.LoggingManager;
import com.c503.sc.utils.common.SystemContants;
import com.xiaomi.xmpush.server.Constants;
import com.xiaomi.xmpush.server.Message;
import com.xiaomi.xmpush.server.Message.Builder;
import com.xiaomi.xmpush.server.Result;
import com.xiaomi.xmpush.server.Sender;

/**
 * 〈一句话功能简述〉小米推送接口实现层 〈功能详细描述〉
 * 
 * @author wangwh
 * @version [版本号,2016年12月30日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Service(value = "miPushService")
public class MiPushServiceImp implements IMiPushService {
    /** APP_SECRET_KEY */
    @Value("#{configProperties['appSecretKey']}")
    private String appSecretKey;
    
    /** MY_PACKAGE_NAME */
    @Value("#{configProperties['myPackageName']}")
    private String myPackageName;
    
    /** 日志器 */
    private static final LoggingManager LOGGER =
        LoggingManager.getLogger(MiPushServiceImp.class);
    
    @Override
    public Sender senderInit()
        throws Exception {
        LOGGER.info(SystemContants.DEBUG_START, "MiPush senderInit Start");
        // 开发环境
        Constants.useOfficial();
        // 创建sender对象
        Sender sender = new Sender(appSecretKey);
        LOGGER.info(SystemContants.DEBUG_END, "MiPush senderInit end");
        return sender;
    }
    
    @Override
    public String getPayload(Map<String, Object> map, String code) {
        MiPushMessageEntity entity = new MiPushMessageEntity();
        entity.setCode(code).setMsg(map);
        String messagePayload = JSON.toJSONString(entity);
        return messagePayload;
        
    }
    
    @Override
    public String getPayload(String msg, String code) {
        MiPushMessageEntity entity = new MiPushMessageEntity();
        entity.setCode(code).setMsg(msg);
        String messagePayload = JSON.toJSONString(entity);
        return messagePayload;
        
    }
    
    @Override
    public Message messageHandle(String title, String des,
        String messagePayload, Boolean messageTypeFlag)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, "MiPush messageHandle Start");
        Builder builder = new Message.Builder();
        // 透传消息
        if (messageTypeFlag) {
            builder.passThrough(1);
        }
        Message message =
            builder.title(title)
                .description(des)
                .payload(messagePayload)
                .restrictedPackageName(myPackageName)
                .notifyType(1)
                .build();
        LOGGER.info(SystemContants.DEBUG_END, "MiPush messageHandle end"
            + message);
        return message;
    }
    
    @Override
    public Result broadcastAll(Message message, int retries)
        throws Exception {
        Sender sender = senderInit();
        Result result = sender.broadcastAll(message, retries);
        return result;
    }
    
	@Override
	public Result sendByRegIds(Message message, List<String> regIds, int retries)
			throws Exception {
		Sender sender = senderInit();
        
        Result result = null;
        if (regIds != null) {
            result = sender.send(message, regIds, retries);
            LOGGER.info(SystemContants.DEBUG_END, "MiPush messageHandle end"
                + regIds);
        }
        else {
            LOGGER.info(SystemContants.DEBUG_END,
                "wrong MiPush regId shoud not be null");
            throw new CustomException(NumberContant.THREE_LONG,
                "regId shoud not be null");
        }
		return result;
	}
    
    @Override
    public Result sendByRegId(Message message, String regId, int retries)
        throws Exception {
        Sender sender = senderInit();
        Result result = null;
        if (regId != null) {
            result = sender.send(message, regId, retries);
            LOGGER.info(SystemContants.DEBUG_END, "MiPush messageHandle end"
                + regId);
        }
        else {
            LOGGER.info(SystemContants.DEBUG_END,
                "wrong MiPush regId shoud not be null");
            throw new CustomException(NumberContant.THREE_LONG,
                "regId shoud not be null");
        }
        return result;
    }
    
    /**
     * 〈一句话功能简述〉获得appSecretKey
     * 〈功能详细描述〉
     * 
     * @return appSecretKey
     * @see [类、类#方法、类#成员]
     */
    public String getAppSecretKey() {
        return appSecretKey;
    }
    
    /**
     * 〈一句话功能简述〉设置appSecretKey
     * 〈功能详细描述〉
     * 
     * @param appSecretKey appSecretKey
     * @see [类、类#方法、类#成员]
     */
    public void setAppSecretKey(String appSecretKey) {
        this.appSecretKey = appSecretKey;
    }
    
    /**
     * 〈一句话功能简述〉获得myPackageName
     * 〈功能详细描述〉
     * 
     * @return myPackageName
     * @see [类、类#方法、类#成员]
     */
    public String getMyPackageName() {
        return myPackageName;
    }
    
    /**
     * 〈一句话功能简述〉设置appSecretKey
     * 〈功能详细描述〉
     * 
     * @param myPackageName myPackageName
     * @see [类、类#方法、类#成员]
     */
    public void setMyPackageName(String myPackageName) {
        this.myPackageName = myPackageName;
    }
 
}
