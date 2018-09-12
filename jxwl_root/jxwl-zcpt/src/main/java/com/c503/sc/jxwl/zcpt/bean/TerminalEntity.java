/**
 * 文件名：TerminalEntity.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-7-26
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.zcpt.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.c503.sc.base.entity.BaseEntity;

/**
 * 
 * 〈一句话功能简述〉终端信息
 * 〈功能详细描述〉
 * 
 * @author qianxq
 * @version [版本号, 2016-7-26]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class TerminalEntity extends BaseEntity {
    /** 序列号 */
    private static final long serialVersionUID = -5784673734489446101L;
    
    /** 厂商终端ID */
    // @JSONField(name = "termianlSerialID")
    private String terminalSerialID;
    
    /** 制造商id */
    private String manufactureID;
    
    /** 载体名称（车牌号） */
    private String carrierName;
    
    /** 载体颜色（车牌颜色） */
    private String carcolor;
    
    /** 终端注册时间 */
    private String registerDate;
    
    /** 下次检修日期 */
    @JSONField(name = "nextrepairDate")
    private String nextrepairDate;
    
    /** 出厂日期 */
    //@JSONField(name = "ProductionDate")
    private String productionDate;
    
    /** 固件升级时间 */
    private String upgradeDate;
    
    /** SIM卡号 */
    @JSONField(name = "card_num")
    private String cardNum;
    
    /** SIM卡密码 */
    //@JSONField(name = "SimPass")
    private String simPass;
    
    /** 运行商（可选 */
    private String operator;
    
    /** 资费 */
    private String expenses;
    
    /** SIM卡余额 */
    private String balance;
    
    /** 设备状态（是否故障） */
    private String terminalState;
    
    /** 设备状态（是否故障） */
    @JSONField(name = "TerRegisterState")
    private String terRegisterState;
    
    /** 终端来源 */
    private String terminalSource;
    
    /**
     * 〈一句话功能简述〉terminalSerialID
     * 〈功能详细描述〉
     * 
     * @return terminalSerialID
     * @see [类、类#方法、类#成员]
     */
    public String getTerminalSerialID() {
        return terminalSerialID;
    }
    
    /**
     * 〈一句话功能简述〉terminalSerialID
     * 〈功能详细描述〉
     * 
     * @param terminalSerialID terminalSerialID
     * @see [类、类#方法、类#成员]
     */
    public void setTerminalSerialID(String terminalSerialID) {
        this.terminalSerialID = terminalSerialID;
    }
    
    /**
     * @return the manufactureID
     */
    public String getManufactureID() {
        return manufactureID;
    }
    
    /**
     * 设置manufactureID
     * 
     * @param manufactureID manufactureID
     */
    public void setManufactureID(String manufactureID) {
        this.manufactureID = manufactureID;
    }
    
    /**
     * @return the registerDate
     */
    public String getRegisterDate() {
        return registerDate;
    }
    
    /**
     * 设置registerDate
     * 
     * @param registerDate registerDate
     */
    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }
    
    /**
     * 〈一句话功能简述〉nextrepairDate
     * 〈功能详细描述〉
     * 
     * @return nextrepairDate
     * @see [类、类#方法、类#成员]
     */
    public String getNextrepairDate() {
        return nextrepairDate;
    }
    
    /**
     * 〈一句话功能简述〉nextrepairDate
     * 〈功能详细描述〉
     * 
     * @param nextrepairDate nextrepairDate
     * @see [类、类#方法、类#成员]
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
     * 
     * @param upgradeDate upgradeDate
     */
    public void setUpgradeDate(String upgradeDate) {
        this.upgradeDate = upgradeDate;
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
     * @return the cardNum
     */
    public String getCardNum() {
        return cardNum;
    }
    
    /**
     * 设置cardNum
     * 
     * @param cardNum cardNum
     */
    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }
    
    /**
     * @return the balance
     */
    public String getBalance() {
        return balance;
    }
    
    /**
     * 设置balance
     * 
     * @param balance balance
     */
    public void setBalance(String balance) {
        this.balance = balance;
    }
    
    /**
     * 获取终端状态
     * 
     * @return 终端状态
     */
    public String getTerminalState() {
        return terminalState;
    }
    
    /**
     * 设置终端状态
     * 
     * @param terminalState 终端状态
     */
    public void setTerminalState(String terminalState) {
        this.terminalState = terminalState;
    }
    
    /**
     * 获取终端来源
     * 
     * @return 终端来源
     */
    public String getTerminalSource() {
        return terminalSource;
    }
    
    /**
     * 设置终端来源
     * 
     * @param terminalSource 终端来源
     */
    public void setTerminalSource(String terminalSource) {
        this.terminalSource = terminalSource;
    }
    
    /**
     * 获取 出厂日期
     * 
     * @return 出厂日期
     */
    public String getProductionDate() {
        return productionDate;
    }
    
    /**
     * 设置出厂日期
     * 
     * @param productionDate 出厂日期
     */
    public void setProductionDate(String productionDate) {
        this.productionDate = productionDate;
    }
    
    /**
     * 获取sim卡密码
     * 
     * @return sim卡密码
     */
    public String getSimPass() {
        return simPass;
    }
    
    /**
     * 设置sim卡密码
     * 
     * @param simPass sim卡密码
     */
    public void setSimPass(String simPass) {
        this.simPass = simPass;
    }
    
    /**
     * 获取 运行商
     * 
     * @return 运行商
     */
    public String getOperator() {
        return operator;
    }
    
    /**
     * 设置 运行商
     * 
     * @param operator 运行商
     */
    public void setOperator(String operator) {
        this.operator = operator;
    }
    
    /**
     * 获取资费
     * 
     * @return 资费
     */
    public String getExpenses() {
        return expenses;
    }
    
    /**
     * 设置资费
     * 
     * @param expenses 资费
     */
    public void setExpenses(String expenses) {
        this.expenses = expenses;
    }
    
    /**
     * 获取设备状态
     * 
     * @return 设备状态
     */
    public String getTerRegisterState() {
        return terRegisterState;
    }
    
    /**
     * 设置设备状态
     * 
     * @param terRegisterState 设备状态
     */
    public void setTerRegisterState(String terRegisterState) {
        this.terRegisterState = terRegisterState;
    }
    
    @Override
    protected Object getBaseEntity() {
        return this;
    }

	@Override
	public String toString() {
		return "TerminalEntity [terminalSerialID=" + terminalSerialID
				+ ", manufactureID=" + manufactureID + ", carrierName="
				+ carrierName + ", carcolor=" + carcolor + ", registerDate="
				+ registerDate + ", nextrepairDate=" + nextrepairDate
				+ ", productionDate=" + productionDate + ", upgradeDate="
				+ upgradeDate + ", cardNum=" + cardNum + ", simPass=" + simPass
				+ ", operator=" + operator + ", expenses=" + expenses
				+ ", balance=" + balance + ", terminalState=" + terminalState
				+ ", terRegisterState=" + terRegisterState
				+ ", terminalSource=" + terminalSource + "]";
	}
    
}
