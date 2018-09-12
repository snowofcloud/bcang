/**
 * 文件名：EquipmentInfo.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-7-27
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.zcpt.bean;

/**
 * 
 * 〈一句话功能简述〉设备信息
 * 〈功能详细描述〉
 * 
 * @author qianxq
 * @version [版本号, 2016-7-27]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class EquipmentInfo {
    /** 制造商id */
    private String manufactureID;
    
    /** 终端注册状态 */
    private String terregistestate;
    
    /** 终端注册日期 */
    private String registerDate;
    
    /** 下次检修日期 */
    private String nextrepairDate;
    
    /** 固件升级时间 */
    private String upgradeDate;
    
    /**
     * 终端状态
     * 0：在线；1：离线；2：故障
     */
    private String terminalstate;
    
    /** 终端型号 */
    private String terminalmodel;

    /**
     * @return the manufactureID
     */
    public String getManufactureID() {
        return manufactureID;
    }

    /**
     * 设置manufactureID 
     * @param manufactureID manufactureID 
     */
    public void setManufactureID(String manufactureID) {
        this.manufactureID = manufactureID;
    }

    /**
     * @return the terregistestate
     */
    public String getTerregistestate() {
        return terregistestate;
    }

    /**
     * 设置terregistestate 
     * @param terregistestate terregistestate 
     */
    public void setTerregistestate(String terregistestate) {
        this.terregistestate = terregistestate;
    }

    /**
     * @return the registerDate
     */
    public String getRegisterDate() {
        return registerDate;
    }

    /**
     * 设置registerDate 
     * @param registerDate registerDate 
     */
    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    /**
     * @return the nextrepairDate
     */
    public String getNextrepairDate() {
        return nextrepairDate;
    }

    /**
     * 设置nextrepairDate 
     * @param nextrepairDate nextrepairDate 
     */
    public void setNextrepairDate(String nextrepairDate) {
        this.nextrepairDate = nextrepairDate;
    }

    /**
     * @return the upgradeDate
     */
    public String getUpgradeDate() {
        return upgradeDate;
    }

    /**
     * 设置upgradeDate 
     * @param upgradeDate upgradeDate 
     */
    public void setUpgradeDate(String upgradeDate) {
        this.upgradeDate = upgradeDate;
    }

    /**
     * @return the terminalstate
     */
    public String getTerminalstate() {
        return terminalstate;
    }

    /**
     * 设置terminalstate 
     * @param terminalstate terminalstate 
     */
    public void setTerminalstate(String terminalstate) {
        this.terminalstate = terminalstate;
    }

    /**
     * @return the terminalmodel
     */
    public String getTerminalmodel() {
        return terminalmodel;
    }

    /**
     * 设置terminalmodel 
     * @param terminalmodel terminalmodel 
     */
    public void setTerminalmodel(String terminalmodel) {
        this.terminalmodel = terminalmodel;
    }
}
