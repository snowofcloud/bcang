/**
 * 文件名：ConfigInstance.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2015年10月26日
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.zcpt.utils;

import java.io.InputStream;
import java.util.Properties;

/**
 * 
 * 〈一句话功能简述〉kafka连接配置、历史轨迹存储线程配置
 * 〈功能详细描述〉
 * 
 * @author Zhangjy
 * @version [版本号, 2015年10月13日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ConfigInstance {
    
    /** Server是否为Master Server */
    private static int masterStatus = 0;
    
    /** Kafka连接URL */
    private static String zkConnect = null;
    
    /** Kafka连接groupID */
    private static String groupID = null;
    
    /** Kafka连接topic */
    private static String topic = null;
    
    /** kafka连接线程数 */
    private static int threadNum = 0;
    
    /**
     * 
     * 〈一句话功能简述〉 读取配置文件
     * 〈功能详细描述〉
     * 
     * @throws Exception 配置文件读取异常
     * @see [类、类#方法、类#成员]
     */
    public static final void init()
        throws Exception {
        InputStream in =
            ConfigInstance.class.getResourceAsStream("/kafka.properties");
        Properties prb = new Properties();
        prb.load(in);
        
        // kafka配置
        groupID = prb.getProperty("kafka.groupId.jxwl");
        topic = prb.getProperty("Topic");
        System.out.println("groupID=" + groupID + ",topic=" + topic);
    }
    
    /**
     * 
     * 〈一句话功能简述〉 获取kafka连接URL
     * 〈功能详细描述〉
     * 
     * @return Kafka连接URL
     * @see [类、类#方法、类#成员]
     */
    public static String getZkConnect() {
        return zkConnect;
    }
    
    /**
     * 
     * 〈一句话功能简述〉 获取kafka连接GroupID
     * 〈功能详细描述〉
     * 
     * @return Kafka连接groupID
     * @see [类、类#方法、类#成员]
     */
    public static String getGroupID() {
        return groupID;
    }
    
    /**
     * 
     * 〈一句话功能简述〉获取kafka连接topic
     * 〈功能详细描述〉
     * 
     * @return Kafka连接topic
     * @see [类、类#方法、类#成员]
     */
    public static String getTopic() {
        return topic;
    }
    
    /**
     * 
     * 〈一句话功能简述〉获取历史轨迹存储线程数
     * 〈功能详细描述〉
     * 
     * @return Kafka连接线程数
     * @see [类、类#方法、类#成员]
     */
    public static int getThreadNum() {
        return threadNum;
    }
    
    /**
     * 
     * 〈一句话功能简述〉server配置，是否为master
     * 〈功能详细描述〉
     * 
     * @return MasterStatus
     * @see [类、类#方法、类#成员]
     */
    public static int getMasterStatus() {
        return masterStatus;
    }
    
}
