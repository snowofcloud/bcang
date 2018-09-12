/**
 * 文件名：Constants.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-7-29
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.zcpt.constant;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 〈一句话功能简述〉数据
 * 〈功能详细描述〉
 * 
 * @author xiaoqx
 * @version [版本号, 2016-8-16]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class Constants {
    /** 浮点类型计算时候与0比较时候的容差 */
    public static final double PRECISION = 2e-10;
    
    /** baseUrl */
    private static String baseUrl;
    
    /** http连接延迟时间 */
    private static int timeOut;
    
    /** 设备信息 */
    private static String equipmentInfo;
    
    /** 历史轨迹数据 */
    private static String hisTrackData;
    
    /** 载体信息 */
    private static String carrierInfo;
    
    /** 实时位置 */
    private static String rtLocation;
    
    /** 终端录入 */
    private static String entering;
    
    /** 终端注销 */
    private static String deleteTerminal;
    
    /** 终端参数设置 */
    private static String paramSetter;
    
    /** 录音 */
    private static String audioRecord;
    
    /** 拍照 */
    private static String takePhoto;
    
    /** 发送调度信息 */
    private static String sendText;
    
    /** 固件升级 */
    private static String upgrade;
    
    /** 设置限制区域 */
    private static String limitAreaSet;
    
    /** 删除限制区域 */
    private static String limitAreaDelete;
    
    static {
        init();
    }
    
    /**
     * @see [类、类#方法、类#成员]
     */
    private static void init() {
        Properties pr = new Properties();
        InputStream is =
            Constants.class.getResourceAsStream("/zcpt_config.properties");
        
        try {
            pr.load(is);
            baseUrl = pr.getProperty("baseUrl").trim();
            timeOut = Integer.parseInt(pr.getProperty("timeout").trim());
            
            // 请求数据
            equipmentInfo = pr.getProperty("equipmentInfo").trim();
            hisTrackData = pr.getProperty("hisTrackData").trim();
            carrierInfo = pr.getProperty("carrierInfo").trim();
            rtLocation = pr.getProperty("rtLocation").trim();
            
            // 提交数据
            entering = pr.getProperty("entering").trim();
            deleteTerminal = pr.getProperty("deleteTerminal").trim();
            paramSetter = pr.getProperty("paramSetter").trim();
            
            // 发送命令
            audioRecord = pr.getProperty("audioRecord").trim();
            takePhoto = pr.getProperty("takePhoto").trim();
            sendText = pr.getProperty("sendText").trim();
            upgrade = pr.getProperty("upgrade").trim();
            limitAreaSet = pr.getProperty("setLimitArea").trim();
            limitAreaDelete = pr.getProperty("deleteLimitArea").trim();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * @return the baseUrl
     */
    public static String getBaseUrl() {
        return baseUrl;
    }
    
    /**
     * @return the timeOut
     */
    public static int getTimeOut() {
        return timeOut;
    }
    
    /**
     * @return the equipmentInfo
     */
    public static String getEquipmentInfo() {
        return equipmentInfo;
    }
    
    /**
     * @return the hisTrackData
     */
    public static String getHisTrackData() {
        return hisTrackData;
    }
    
    /**
     * @return the carrierInfo
     */
    public static String getCarrierInfo() {
        return carrierInfo;
    }
    
    /**
     * @return the rtLocation
     */
    public static String getRtLocation() {
        return rtLocation;
    }
    
    /**
     * @return the entering
     */
    public static String getEntering() {
        return entering;
    }
    
    /**
     * @return the deleteTerminal
     */
    public static String getDeleteTerminal() {
        return deleteTerminal;
    }
    
    /**
     * @return the paramSetter
     */
    public static String getParamSetter() {
        return paramSetter;
    }
    
    /**
     * @return the audioRecord
     */
    public static String getAudioRecord() {
        return audioRecord;
    }
    
    /**
     * @return the takePhoto
     */
    public static String getTakePhoto() {
        return takePhoto;
    }
    
    /**
     * @return the sendText
     */
    public static String getSendText() {
        return sendText;
    }
    
    /**
     * @return the upgrade
     */
    public static String getUpgrade() {
        return upgrade;
    }
    
    /**
     * @return the limitAreaSet
     */
    public static String getLimitAreaSet() {
        return limitAreaSet;
    }
    
    /**
     * @return the limitAreaDelete
     */
    public static String getLimitAreaDelete() {
        return limitAreaDelete;
    }
}
