/**
 * 文件名：Carrier.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-7-28
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.zcpt.bean;

/**
 * 
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * 
 * @author qianxq
 * @version [版本号, 2016-7-28]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class Carrier {
    /** 终端ID */
    private String terminalSerialID;
    
    /** 车牌颜色 */
    private String carcolor;
    
    /** 车牌号 */
    private String carrierName;
    
    /**
     * @return the terminalSerialID
     */
    public String getTerminalSerialID() {
        return terminalSerialID;
    }
    
    /**
     * 设置terminalSerialID
     * 
     * @param terminalSerialID terminalSerialID
     */
    public void setTerminalSerialID(String terminalSerialID) {
        this.terminalSerialID = terminalSerialID;
    }
    
    /**
     * @return the carcolor
     */
    public String getCarcolor() {
        return carcolor;
    }
    
    /**
     * 设置carcolor
     * 
     * @param carcolor carcolor
     */
    public void setCarcolor(String carcolor) {
        this.carcolor = carcolor;
    }
    
    /**
     * @return the carrierName
     */
    public String getCarrierName() {
        return carrierName;
    }
    
    /**
     * 设置carrierName
     * 
     * @param carrierName carrierName
     */
    public void setCarrierName(String carrierName) {
        this.carrierName = carrierName;
    }
}
