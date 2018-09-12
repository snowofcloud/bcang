/**
 * 文件名：TerminalEntity.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-7-26
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.zcpt.bean;

import java.io.Serializable;

/**
 * 
 * 〈一句话功能简述〉终端信息
 * 〈功能详细描述〉
 * 
 * @author zz
 * @version [版本号, 2016-7-26]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class TerminalUpgrade implements Serializable {
    /** 序列号 */
    private static final long serialVersionUID = -5784673734489446101L;
    
    /** 00000000：终端手机号 */
    private String terminalTelNo;
    
    /** 00000001：消息ID */
    private String msgId;
    
    /** 00000002：消息流水号 */
    private String msgFlowNo;
    
    /**
     * 81050001：命令字（1：无线升级；2：控制终端连接指定服务器；3： 终端关机:4：终端复位；5：终端恢复出厂设置:6：关闭数据通
     * 信:7：关闭所有无线通信）
     */
    private String commandWord;
    
    /** 81050002：连接控制 */
    private String connCtrl;
    
    /** 81050003：拨号点名称 */
    private String dialPointName;
    
    /** 81050004：拨号用户名 */
    private String dialUserName;
    
    /** 81050005：拨号密码 */
    private String dialPassword;
    
    /** 81050006：升级服务器地址 */
    private String address;
    
    /** 81050007：TCP端口 */
    private String tcpPort;
    
    /** 81050008：UDP端口 */
    private String udpPort;
    
    /** 81050009：制造商ID */
    private String manufactureID;
    
    /** 81050010：监管平台鉴权码 */
    private String authenticationNo;
    
    /** 81050011：硬件版本 */
    private String hardwareVersion;
    
    /** 81050012：固件版本 */
    private String firmwareVersion;
    
    /** 81050013：升级终端的完整url地址 */
    private String url;
    
    /** 81050014：连接到升级服务器时限 (单位:分钟) */
    private String timeLimit;
    
    /** 终端序列号 */
    private String terminalSerialID;
    
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
     * 〈一句话功能简述〉00000000：终端手机号
     * 〈功能详细描述〉
     * 
     * @return 00000000：终端手机号
     * @see [类、类#方法、类#成员]
     */
    public String getTerminalTelNo() {
        return terminalTelNo;
    }
    
    /**
     * 〈一句话功能简述〉00000000：终端手机号
     * 〈功能详细描述〉
     * 
     * @param terminalTelNo 00000000：终端手机号
     * @see [类、类#方法、类#成员]
     */
    public void setTerminalTelNo(String terminalTelNo) {
        this.terminalTelNo = terminalTelNo;
    }
    
    /**
     * 〈一句话功能简述〉00000001：消息ID
     * 〈功能详细描述〉
     * 
     * @return 00000001：消息ID
     * @see [类、类#方法、类#成员]
     */
    public String getMsgId() {
        return msgId;
    }
    
    /**
     * 〈一句话功能简述〉00000001：消息ID
     * 〈功能详细描述〉
     * 
     * @param msgId 00000001：消息ID
     * @see [类、类#方法、类#成员]
     */
    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }
    
    /**
     * 〈一句话功能简述〉00000002：消息流水号
     * 〈功能详细描述〉
     * 
     * @return 00000002：消息流水号
     * @see [类、类#方法、类#成员]
     */
    public String getMsgFlowNo() {
        return msgFlowNo;
    }
    
    /**
     * 〈一句话功能简述〉00000002：消息流水号
     * 〈功能详细描述〉
     * 
     * @param msgFlowNo 00000002：消息流水号
     * @see [类、类#方法、类#成员]
     */
    public void setMsgFlowNo(String msgFlowNo) {
        this.msgFlowNo = msgFlowNo;
    }
    
    /**
     * 〈一句话功能简述〉81050001：命令字
     * 〈功能详细描述〉
     * 
     * @return 81050001：命令字
     * @see [类、类#方法、类#成员]
     */
    public String getCommandWord() {
        return commandWord;
    }
    
    /**
     * 〈一句话功能简述〉81050001：命令字
     * 〈功能详细描述〉
     * 
     * @param commandWord 81050001：命令字
     * @see [类、类#方法、类#成员]
     */
    public void setCommandWord(String commandWord) {
        this.commandWord = commandWord;
    }
    
    /**
     * 〈一句话功能简述〉 81050002：连接控制
     * 〈功能详细描述〉
     * 
     * @return 81050002：连接控制
     * @see [类、类#方法、类#成员]
     */
    public String getConnCtrl() {
        return connCtrl;
    }
    
    /**
     * 〈一句话功能简述〉81050002：连接控制
     * 〈功能详细描述〉
     * 
     * @param connCtrl 81050002：连接控制
     * @see [类、类#方法、类#成员]
     */
    public void setConnCtrl(String connCtrl) {
        this.connCtrl = connCtrl;
    }
    
    /**
     * 〈一句话功能简述〉81050007：TCP端口
     * 〈功能详细描述〉
     * 
     * @return 81050007：TCP端口
     * @see [类、类#方法、类#成员]
     */
    public String getTcpPort() {
        return tcpPort;
    }
    
    /**
     * 〈一句话功能简述〉81050007：TCP端口
     * 〈功能详细描述〉
     * 
     * @param tcpPort 81050007：TCP端口
     * @see [类、类#方法、类#成员]
     */
    public void setTcpPort(String tcpPort) {
        this.tcpPort = tcpPort;
    }
    
    /**
     * 〈一句话功能简述〉81050008：UDP端口
     * 〈功能详细描述〉
     * 
     * @return 81050008：UDP端口
     * @see [类、类#方法、类#成员]
     */
    public String getUdpPort() {
        return udpPort;
    }
    
    /**
     * 〈一句话功能简述〉81050008：UDP端口
     * 〈功能详细描述〉
     * 
     * @param udpPort 81050008：UDP端口
     * @see [类、类#方法、类#成员]
     */
    public void setUdpPort(String udpPort) {
        this.udpPort = udpPort;
    }
    
    /**
     * 〈一句话功能简述〉81050010：监管平台鉴权码
     * 〈功能详细描述〉
     * 
     * @return 81050010：监管平台鉴权码
     * @see [类、类#方法、类#成员]
     */
    public String getAuthenticationNo() {
        return authenticationNo;
    }
    
    /**
     * 〈一句话功能简述〉81050010：监管平台鉴权码
     * 〈功能详细描述〉
     * 
     * @param authenticationNo 81050010：监管平台鉴权码
     * @see [类、类#方法、类#成员]
     */
    public void setAuthenticationNo(String authenticationNo) {
        this.authenticationNo = authenticationNo;
    }
    
    @Override
    public String toString() {
        return "TerminalUpgrade [terminalTelNo=" + terminalTelNo + ", msgId="
            + msgId + ", msgFlowNo=" + msgFlowNo + ", commandWord="
            + commandWord + ", connCtrl=" + connCtrl + ", dialPointName="
            + dialPointName + ", dialUserName=" + dialUserName
            + ", dialPassword=" + dialPassword + ", address=" + address
            + ", tcpPort=" + tcpPort + ", udpPort=" + udpPort
            + ", manufactureID=" + manufactureID + ", authenticationNo="
            + authenticationNo + ", hardwareVersion=" + hardwareVersion
            + ", firmwareVersion=" + firmwareVersion + ", url=" + url
            + ", timeLimit=" + timeLimit + "]";
    }
    
}
