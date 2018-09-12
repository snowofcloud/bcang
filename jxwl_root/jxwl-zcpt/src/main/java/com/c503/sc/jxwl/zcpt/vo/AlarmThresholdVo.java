/**
 * 文件名：AlarmThresholdVo.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-7-29
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.zcpt.vo;

import java.math.BigDecimal;

/**
 * 
 * 〈一句话功能简述〉报警阈值VO
 * 〈功能详细描述〉
 * 
 * @author guqh
 * @version [版本号, 2016-7-29]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class AlarmThresholdVo {
    /** 主键ID */
    private String id;
    
    /** 超速报警阈值 */
    private BigDecimal overSpeedValue;
    
    /** 超速持续时间阈值 */
    private BigDecimal speedContinueValue;
    
    /** 疲劳驾驶时间阈值 */
    private BigDecimal fatigueDriveValue;
    
    /** 超时停车报警阈值 */
    private BigDecimal overtimeParkValue;
    
    /**
     * 〈一句话功能简述〉获得主键ID
     * 〈功能详细描述〉
     * 
     * @return id
     * @see [类、类#方法、类#成员]
     */
    public String getId() {
        return id;
    }
    
    /**
     * 〈一句话功能简述〉设置主键ID
     * 〈功能详细描述〉
     * 
     * @param id 主键ID
     * @see [类、类#方法、类#成员]
     */
    public void setId(String id) {
        this.id = id;
    }
    
    /**
     * 〈一句话功能简述〉获得超速报警阈值
     * 〈功能详细描述〉
     * 
     * @return overSpeedValue
     * @see [类、类#方法、类#成员]
     */
    public BigDecimal getOverSpeedValue() {
        return overSpeedValue;
    }
    
    /**
     * 〈一句话功能简述〉设置超速报警阈值
     * 〈功能详细描述〉
     * 
     * @param overSpeedValue 超速报警阈值
     * @see [类、类#方法、类#成员]
     */
    public void setOverSpeedValue(BigDecimal overSpeedValue) {
        this.overSpeedValue = overSpeedValue;
    }
    
    /**
     * 〈一句话功能简述〉获得超速持续时间阈值
     * 〈功能详细描述〉
     * 
     * @return speedContinueValue
     * @see [类、类#方法、类#成员]
     */
    public BigDecimal getSpeedContinueValue() {
        return speedContinueValue;
    }
    
    /**
     * 〈一句话功能简述〉设置超速持续时间阈值
     * 〈功能详细描述〉
     * 
     * @param speedContinueValue 超速持续时间阈值
     * @see [类、类#方法、类#成员]
     */
    public void setSpeedContinueValue(BigDecimal speedContinueValue) {
        this.speedContinueValue = speedContinueValue;
    }
    
    /**
     * 〈一句话功能简述〉获得疲劳驾驶时间阈值
     * 〈功能详细描述〉
     * 
     * @return fatigueDriveValue
     * @see [类、类#方法、类#成员]
     */
    public BigDecimal getFatigueDriveValue() {
        return fatigueDriveValue;
    }
    
    /**
     * 〈一句话功能简述〉设置疲劳驾驶时间阈值
     * 〈功能详细描述〉
     * 
     * @param fatigueDriveValue 疲劳驾驶时间阈值
     * @see [类、类#方法、类#成员]
     */
    public void setFatigueDriveValue(BigDecimal fatigueDriveValue) {
        this.fatigueDriveValue = fatigueDriveValue;
    }
    
    /**
     * 〈一句话功能简述〉获得超时停车报警阈值
     * 〈功能详细描述〉
     * 
     * @return overtimeParkValue
     * @see [类、类#方法、类#成员]
     */
    public BigDecimal getOvertimeParkValue() {
        return overtimeParkValue;
    }
    
    /**
     * 〈一句话功能简述〉设置超时停车报警阈值
     * 〈功能详细描述〉
     * 
     * @param overtimeParkValue 超时停车报警阈值
     * @see [类、类#方法、类#成员]
     */
    public void setOvertimeParkValue(BigDecimal overtimeParkValue) {
        this.overtimeParkValue = overtimeParkValue;
    }
}
