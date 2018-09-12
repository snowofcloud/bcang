package com.c503.sc.jxwl.common.service;

import java.util.List;
import java.util.Map;

import com.xiaomi.xmpush.server.Message;
import com.xiaomi.xmpush.server.Result;
import com.xiaomi.xmpush.server.Sender;

/**
 * 〈一句话功能简述〉小米推送接口 〈功能详细描述〉
 * 
 * @author wangwh
 * @version [版本号, 2016年12月30日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface IMiPushService {
    
    /**
     * 
     * 〈一句话功能简述〉初始化小米推送的sender
     * 〈功能详细描述〉
     * 
     * @param map map
     * @param code code
     * @return Sender Sender
     * @see [类、类#方法、类#成员]
     */
    String getPayload(Map<String, Object> map, String code);
    
    /**
     * 
     * 〈一句话功能简述〉初始化小米推送的sender
     * 〈功能详细描述〉
     * 
     * @param msg msg
     * @param code code
     * @return Sender Sender
     * @see [类、类#方法、类#成员]
     */
    String getPayload(String msg, String code);
    
    /**
     * 
     * 〈一句话功能简述〉初始化小米推送的sender
     * 〈功能详细描述〉
     * 
     * @return Sender Sender
     * @throws Exception 系统异常
     * @see [类、类#方法、类#成员]
     */
    Sender senderInit()
        throws Exception;
    
    /**
     * 
     * 〈一句话功能简述〉封装推送消息数据
     * 〈功能详细描述〉
     * 
     * @param title title
     * @param des des
     * @param messagePayload messagePayload
     * @param messageTypeFlag messageTypeFlag
     * @return Message Message
     * @throws Exception 系统异常
     * @see [类、类#方法、类#成员]
     */
    Message messageHandle(String title, String des, String messagePayload,
        Boolean messageTypeFlag)
        throws Exception;
    
    /**
     * 
     * 〈一句话功能简述〉广播方式发送
     * 〈功能详细描述〉
     * 
     * @param message message
     * @param retries retries
     * @return Result Result
     * @throws Exception 系统异常
     * @see [类、类#方法、类#成员]
     */
    Result broadcastAll(Message message, int retries)
        throws Exception;
    /**
     * 
     * 〈一句话功能简述〉根据regId发送消息
     * 〈功能详细描述〉
     * 
     * @param message message
     * @param retries retries
     * @param regId regId
     * @return Result Result
     * @throws Exception 系统异常
     * @see [类、类#方法、类#成员]
     */
    Result sendByRegId(Message message, String regId, int retries)
            throws Exception;
    /**
     * 
     * 〈一句话功能简述〉给一组regId发送消息
     * 〈功能详细描述〉
     * 
     * @param message message
     * @param retries retries
     * @param regId regId
     * @return Result Result
     * @throws Exception 系统异常
     * @see [类、类#方法、类#成员]
     */
    Result sendByRegIds(Message message, List<String> regIds, int retries)
            throws Exception;
    
}
