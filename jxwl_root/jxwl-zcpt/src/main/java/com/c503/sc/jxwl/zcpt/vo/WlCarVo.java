/**
 * 文件名：AlarmNumVo.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-7-29
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.zcpt.vo;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * 〈一句话功能简述〉物流企业下的车辆
 * 〈功能详细描述〉
 * 
 * @author zz
 * @version [版本号, 2016-7-29]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class WlCarVo {
    /** 车牌号 */
    private String carrierName;
    
    /** 当前车牌号的车辆数 */
    private int carCnt;
    
    /** 在线（"on"）、 离线（"off"） */
    private String onOffLine;
    
    /** 在线离线 */
    private String onOff;
    
    /**
     * 〈一句话功能简述〉车牌号
     * 〈功能详细描述〉
     * 
     * @return 车牌号
     * @see [类、类#方法、类#成员]
     */
    public String getCarrierName() {
        return carrierName;
    }
    
    /**
     * 〈一句话功能简述〉车牌号
     * 〈功能详细描述〉
     * 
     * @param carrierName 车牌号
     * @see [类、类#方法、类#成员]
     */
    public void setCarrierName(String carrierName) {
        this.carrierName = carrierName;
    }
    
    /**
     * 〈一句话功能简述〉 当前车牌号的车辆数
     * 〈功能详细描述〉
     * 
     * @return 当前车牌号的车辆数
     * @see [类、类#方法、类#成员]
     */
    public int getCarCnt() {
        return carCnt;
    }
    
    /**
     * 〈一句话功能简述〉当前车牌号的车辆数
     * 〈功能详细描述〉
     * 
     * @param carCnt 当前车牌号的车辆数
     * @see [类、类#方法、类#成员]
     */
    public void setCarCnt(int carCnt) {
        this.carCnt = carCnt;
    }
    
    /**
     * 〈一句话功能简述〉在线（"on"）、 离线（"off"）
     * 〈功能详细描述〉
     * 
     * @return 在线（"on"）、 离线（"off"）
     * @see [类、类#方法、类#成员]
     */
    public String getOnOffLine() {
        return onOffLine;
    }
    
    /**
     * 〈一句话功能简述〉在线（"on"）、 离线（"off"）
     * 〈功能详细描述〉
     * 
     * @param onOffLine 在线（"on"）、 离线（"off"）
     * @see [类、类#方法、类#成员]
     */
    public void setOnOffLine(String onOffLine) {
        this.onOffLine = onOffLine;
        if (StringUtils.equals("on", onOffLine)) {
            this.onOff = "在线";
        } else {
            this.onOff = "离线";
        }
    }
    
    /**
     * 〈一句话功能简述〉在线、离线
     * 〈功能详细描述〉
     * 
     * @return 在线、离线
     * @see [类、类#方法、类#成员]
     */
    public String getOnOff() {
        return onOff;
    }
    
}
