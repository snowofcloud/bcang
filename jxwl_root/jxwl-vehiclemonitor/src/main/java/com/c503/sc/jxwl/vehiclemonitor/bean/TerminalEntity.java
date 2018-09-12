/**
 * 文件名：TerminalEntity.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-7-26
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.vehiclemonitor.bean;

import java.io.Serializable;

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
public class TerminalEntity implements Serializable {
    /** 序列号 */
    private static final long serialVersionUID = -5784673734489446101L;
    
    /** id 用于前端暂时 */
    private String id;
    
    /** 终端序列号 */
    private String terminalSerialID;
    
    /** 制造商id */
    private String manufactureID;
    
    // 注册
    /** 车牌颜色 */
    private String carcolor;
    
    /** 车牌号 */
    private String carrierName;
    
    /** 终端注册时间 */
    private String registerDate;
    
    /** 下次检修日期 */
    private String nextrepairDate;
    
    /** SIM卡号 */
    private String cardNum;
    
    /** SIM卡密 */
    private String simPass;
    
    /** 终端型号 */
    private String terminalModel;
    
    /** 固件升级时间 */
    private String upgradeDate;
    
    /** 载体颜色 */
    private String carrierColour;
    
    /** SIM卡序列号 */
    private String serialNum;
    
    /** 运营商 */
    private String operator;
    
    /** 资费 */
    private String expense;
    
    /** 余额 */
    private String balance;
    
    // 升级数据
    /** 拨号点名称 */
    private String dialPointName;
    
    /** 拨号用户名 */
    private String dialUserName;
    
    /** 拨号密码 */
    private String dialPassword;
    
    /** 升级服务器地址 */
    private String address;
    
    /** 升级服务器端口 */
    private String port;
    
    /** 硬件版本 */
    private String hardwareVersion;
    
    /** 固件版本 */
    private String firmwareVersion;
    
    /** 升级终端的完整url地址 */
    private String url;
    
    /** 连接到升级服务器时限 */
    private String timeLimit;
    
    // 返回状态
    /** 终端注册状态（0：未注册；1：已注册） */
    private String terregistestate;
    
    /** 终端状态（0：在线；1：离线；2：故障） */
    private String terminalstate;
    
    /** 终端型号 */
    private String terminalmodel;
    
    /** SIM卡启用时间 */
    private String sTime;
    
    /** SIM卡到期时间 */
    private String eTime;
    
    /** 使用者联系人 */
    private String departUser;
    
    /** 联系人电话 */
    private String userPhone;
    
    /** 出厂日期 */
    private String productionDate;
    
    /** 设备来源 */
    private String terminalSource;
    
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
        this.id = terminalSerialID;
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
     * @return the terminalModel
     */
    public String getTerminalModel() {
        return terminalModel;
    }
    
    /**
     * 设置terminalModel
     * 
     * @param terminalModel terminalModel
     */
    public void setTerminalModel(String terminalModel) {
        this.terminalModel = terminalModel;
    }
    
    /**
     * 〈一句话功能简述〉下次检修日期
     * 〈功能详细描述〉
     * 
     * @return 下次检修日期
     * @see [类、类#方法、类#成员]
     */
    public String getNextrepairDate() {
        return nextrepairDate;
    }
    
    /**
     * 〈一句话功能简述〉下次检修日期
     * 〈功能详细描述〉
     * 
     * @param nextrepairDate 下次检修日期
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
     * @return the carrierColour
     */
    public String getCarrierColour() {
        return carrierColour;
    }
    
    /**
     * 设置carrierColour
     * 
     * @param carrierColour carrierColour
     */
    public void setCarrierColour(String carrierColour) {
        this.carrierColour = carrierColour;
    }
    
    /**
     * 〈一句话功能简述〉SIM卡号
     * 〈功能详细描述〉
     * 
     * @return SIM卡号
     * @see [类、类#方法、类#成员]
     */
    public String getCardNum() {
        return cardNum;
    }
    
    /**
     * 〈一句话功能简述〉SIM卡号
     * 〈功能详细描述〉
     * 
     * @param cardNum SIM卡号
     * @see [类、类#方法、类#成员]
     */
    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }
    
    /**
     * 〈一句话功能简述〉SIM卡密
     * 〈功能详细描述〉
     * 
     * @return SIM卡密
     * @see [类、类#方法、类#成员]
     */
    public String getSimPass() {
        return simPass;
    }
    
    /**
     * 〈一句话功能简述〉SIM卡密
     * 〈功能详细描述〉
     * 
     * @param simPass SIM卡密
     * @see [类、类#方法、类#成员]
     */
    public void setSimPass(String simPass) {
        this.simPass = simPass;
    }
    
    /**
     * @return the serialNum
     */
    public String getSerialNum() {
        return serialNum;
    }
    
    /**
     * 设置serialNum
     * 
     * @param serialNum serialNum
     */
    public void setSerialNum(String serialNum) {
        this.serialNum = serialNum;
    }
    
    /**
     * @return the sTime
     */
    public String getsTime() {
        return sTime;
    }
    
    /**
     * 设置sTime
     * 
     * @param sTime sTime
     */
    public void setsTime(String sTime) {
        this.sTime = sTime;
    }
    
    /**
     * @return the eTime
     */
    public String geteTime() {
        return eTime;
    }
    
    /**
     * 设置eTime
     * 
     * @param eTime eTime
     */
    public void seteTime(String eTime) {
        this.eTime = eTime;
    }
    
    /**
     * @return the departUser
     */
    public String getDepartUser() {
        return departUser;
    }
    
    /**
     * 设置departUser
     * 
     * @param departUser departUser
     */
    public void setDepartUser(String departUser) {
        this.departUser = departUser;
    }
    
    /**
     * @return the userPhone
     */
    public String getUserPhone() {
        return userPhone;
    }
    
    /**
     * 设置userPhone
     * 
     * @param userPhone userPhone
     */
    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }
    
    /**
     * 〈一句话功能简述〉出厂日期
     * 〈功能详细描述〉
     * 
     * @return 出厂日期
     * @see [类、类#方法、类#成员]
     */
    public String getProductionDate() {
        return productionDate;
    }
    
    /**
     * 〈一句话功能简述〉出厂日期
     * 〈功能详细描述〉
     * 
     * @param productionDate 出厂日期
     * @see [类、类#方法、类#成员]
     */
    public void setProductionDate(String productionDate) {
        this.productionDate = productionDate;
    }
    
    /**
     * @return the terminalSource
     */
    public String getTerminalSource() {
        return terminalSource;
    }
    
    /**
     * 设置terminalSource
     * 
     * @param terminalSource terminalSource
     */
    public void setTerminalSource(String terminalSource) {
        this.terminalSource = terminalSource;
    }
    
    /**
     * 〈一句话功能简述〉车牌颜色
     * 〈功能详细描述〉
     * 
     * @return 车牌颜色
     * @see [类、类#方法、类#成员]
     */
    public String getCarcolor() {
        return carcolor;
    }
    
    /**
     * 〈一句话功能简述〉车牌颜色
     * 〈功能详细描述〉
     * 
     * @param carcolor 车牌颜色
     * @see [类、类#方法、类#成员]
     */
    public void setCarcolor(String carcolor) {
        this.carcolor = carcolor;
    }
    
    /**
     * 〈一句话功能简述〉运营商
     * 〈功能详细描述〉
     * 
     * @return 运营商
     * @see [类、类#方法、类#成员]
     */
    public String getOperator() {
        return operator;
    }
    
    /**
     * 〈一句话功能简述〉运营商
     * 〈功能详细描述〉
     * 
     * @param operator 运营商
     * @see [类、类#方法、类#成员]
     */
    public void setOperator(String operator) {
        this.operator = operator;
    }
    
    /**
     * 〈一句话功能简述〉资费
     * 〈功能详细描述〉
     * 
     * @return 资费
     * @see [类、类#方法、类#成员]
     */
    public String getExpense() {
        return expense;
    }
    
    /**
     * 〈一句话功能简述〉资费
     * 〈功能详细描述〉
     * 
     * @param expense 资费
     * @see [类、类#方法、类#成员]
     */
    public void setExpense(String expense) {
        this.expense = expense;
    }
    
    /**
     * 〈一句话功能简述〉余额
     * 〈功能详细描述〉
     * 
     * @return 余额
     * @see [类、类#方法、类#成员]
     */
    public String getBalance() {
        return balance;
    }
    
    /**
     * 〈一句话功能简述〉余额
     * 〈功能详细描述〉
     * 
     * @param balance 余额
     * @see [类、类#方法、类#成员]
     */
    public void setBalance(String balance) {
        this.balance = balance;
    }
    
    /**
     * 〈一句话功能简述〉拨号点名称
     * 〈功能详细描述〉
     * 
     * @return 拨号点名称
     * @see [类、类#方法、类#成员]
     */
    public String getDialPointName() {
        return dialPointName;
    }
    
    /**
     * 〈一句话功能简述〉拨号点名称
     * 〈功能详细描述〉
     * 
     * @param dialPointName 拨号点名称
     * @see [类、类#方法、类#成员]
     */
    public void setDialPointName(String dialPointName) {
        this.dialPointName = dialPointName;
    }
    
    /**
     * 〈一句话功能简述〉拨号用户名
     * 〈功能详细描述〉
     * 
     * @return 拨号用户名
     * @see [类、类#方法、类#成员]
     */
    public String getDialUserName() {
        return dialUserName;
    }
    
    /**
     * 〈一句话功能简述〉拨号用户名
     * 〈功能详细描述〉
     * 
     * @param dialUserName 拨号用户名
     * @see [类、类#方法、类#成员]
     */
    public void setDialUserName(String dialUserName) {
        this.dialUserName = dialUserName;
    }
    
    /**
     * 〈一句话功能简述〉拨号密码
     * 〈功能详细描述〉
     * 
     * @return 拨号密码
     * @see [类、类#方法、类#成员]
     */
    public String getDialPassword() {
        return dialPassword;
    }
    
    /**
     * 〈一句话功能简述〉拨号密码
     * 〈功能详细描述〉
     * 
     * @param dialPassword 拨号密码
     * @see [类、类#方法、类#成员]
     */
    public void setDialPassword(String dialPassword) {
        this.dialPassword = dialPassword;
    }
    
    /**
     * 〈一句话功能简述〉升级服务器地址
     * 〈功能详细描述〉
     * 
     * @return 升级服务器地址
     * @see [类、类#方法、类#成员]
     */
    public String getAddress() {
        return address;
    }
    
    /**
     * 〈一句话功能简述〉升级服务器地址
     * 〈功能详细描述〉
     * 
     * @param address 升级服务器地址
     * @see [类、类#方法、类#成员]
     */
    public void setAddress(String address) {
        this.address = address;
    }
    
    /**
     * 〈一句话功能简述〉升级服务器端口
     * 〈功能详细描述〉
     * 
     * @return 升级服务器端口
     * @see [类、类#方法、类#成员]
     */
    public String getPort() {
        return port;
    }
    
    /**
     * 〈一句话功能简述〉升级服务器端口
     * 〈功能详细描述〉
     * 
     * @param port 升级服务器端口
     * @see [类、类#方法、类#成员]
     */
    public void setPort(String port) {
        this.port = port;
    }
    
    /**
     * 〈一句话功能简述〉硬件版本
     * 〈功能详细描述〉
     * 
     * @return 硬件版本
     * @see [类、类#方法、类#成员]
     */
    public String getHardwareVersion() {
        return hardwareVersion;
    }
    
    /**
     * 〈一句话功能简述〉硬件版本
     * 〈功能详细描述〉
     * 
     * @param hardwareVersion 硬件版本
     * @see [类、类#方法、类#成员]
     */
    public void setHardwareVersion(String hardwareVersion) {
        this.hardwareVersion = hardwareVersion;
    }
    
    /**
     * 〈一句话功能简述〉固件版本
     * 〈功能详细描述〉
     * 
     * @return 固件版本
     * @see [类、类#方法、类#成员]
     */
    public String getFirmwareVersion() {
        return firmwareVersion;
    }
    
    /**
     * 〈一句话功能简述〉固件版本
     * 〈功能详细描述〉
     * 
     * @param firmwareVersion 固件版本
     * @see [类、类#方法、类#成员]
     */
    public void setFirmwareVersion(String firmwareVersion) {
        this.firmwareVersion = firmwareVersion;
    }
    
    /**
     * 〈一句话功能简述〉升级终端的完整url地址
     * 〈功能详细描述〉
     * 
     * @return 升级终端的完整url地址
     * @see [类、类#方法、类#成员]
     */
    public String getUrl() {
        return url;
    }
    
    /**
     * 〈一句话功能简述〉升级终端的完整url地址
     * 〈功能详细描述〉
     * 
     * @param url 升级终端的完整url地址
     * @see [类、类#方法、类#成员]
     */
    public void setUrl(String url) {
        this.url = url;
    }
    
    /**
     * 〈一句话功能简述〉连接到升级服务器时限
     * 〈功能详细描述〉
     * 
     * @return 连接到升级服务器时限
     * @see [类、类#方法、类#成员]
     */
    public String getTimeLimit() {
        return timeLimit;
    }
    
    /**
     * 〈一句话功能简述〉连接到升级服务器时限
     * 〈功能详细描述〉
     * 
     * @param timeLimit 连接到升级服务器时限
     * @see [类、类#方法、类#成员]
     */
    public void setTimeLimit(String timeLimit) {
        this.timeLimit = timeLimit;
    }
    
    /**
     * 〈一句话功能简述〉终端注册状态（0：未注册；1：已注册）
     * 〈功能详细描述〉
     * 
     * @return 终端注册状态（0：未注册；1：已注册）
     * @see [类、类#方法、类#成员]
     */
    public String getTerregistestate() {
        return terregistestate;
    }
    
    /**
     * 〈一句话功能简述〉终端注册状态（0：未注册；1：已注册）
     * 〈功能详细描述〉
     * 
     * @param terregistestate 终端注册状态（0：未注册；1：已注册）
     * @see [类、类#方法、类#成员]
     */
    public void setTerregistestate(String terregistestate) {
        this.terregistestate = terregistestate;
    }
    
    /**
     * 〈一句话功能简述〉终端状态（0：在线；1：离线；2：故障）
     * 〈功能详细描述〉
     * 
     * @return 终端状态（0：在线；1：离线；2：故障）
     * @see [类、类#方法、类#成员]
     */
    public String getTerminalstate() {
        return terminalstate;
    }
    
    /**
     * 〈一句话功能简述〉终端状态（0：在线；1：离线；2：故障）
     * 〈功能详细描述〉
     * 
     * @param terminalstate 终端状态（0：在线；1：离线；2：故障）
     * @see [类、类#方法、类#成员]
     */
    public void setTerminalstate(String terminalstate) {
        this.terminalstate = terminalstate;
    }
    
    /**
     * 〈一句话功能简述〉终端型号
     * 〈功能详细描述〉
     * 
     * @return 终端型号
     * @see [类、类#方法、类#成员]
     */
    public String getTerminalmodel() {
        return terminalmodel;
    }
    
    /**
     * 〈一句话功能简述〉终端型号
     * 〈功能详细描述〉
     * 
     * @param terminalmodel 终端型号
     * @see [类、类#方法、类#成员]
     */
    public void setTerminalmodel(String terminalmodel) {
        this.terminalmodel = terminalmodel;
    }
    
    /**
     * 〈一句话功能简述〉id 用于前端暂时
     * 〈功能详细描述〉
     * 
     * @return id 用于前端暂时
     * @see [类、类#方法、类#成员]
     */
    public String getId() {
        return id;
    }
    
    /**
     * 〈一句话功能简述〉id 用于前端暂时
     * 〈功能详细描述〉
     * 
     * @param id id 用于前端暂时
     * @see [类、类#方法、类#成员]
     */
    public void setId(String id) {
        this.id = id;
    }
    
}
