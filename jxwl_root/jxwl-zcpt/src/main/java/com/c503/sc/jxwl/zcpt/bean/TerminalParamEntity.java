/**
 * 文件名：TerminalParamEntity.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2017-7-13 
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.zcpt.bean;

import com.c503.sc.base.entity.BaseEntity;


public class TerminalParamEntity extends BaseEntity {
    /** 序列号 */
    private static final long serialVersionUID = -5784673734489446101L;
    
    /**终端编号*/
    private String terminalSerialId;
    
    /**终端手机号*/
    private String phone;
    
    /**消息id*/
    private String msgId;
    
    /**消息流水号*/
    private String msgFlowNumber;
    
    /**参数总数*/
    private String paramTotal;
    
    /**终端心跳发送间隔（秒）*/
    private String heartbeatSendInterval;
    
    /**TCP消息应答超时时间（秒）*/
    private String tcpResponseTimeout;
    
    /**TCP消息重传次数*/
    private String tcpMsgRepeatTimes;
    
    /**UDP消息应答超时时间（秒）*/
    private String udpResponseTimeout;
    
    /**UDP消息重传次数*/
    private String udpMsgRepeatTimes;
    
    /**主服务器地址*/
    private String mainServerAddr;
    
    /**备份服务器地址*/
    private String backupServerAddr;
    
    /**服务器TCP端口*/
    private String mainServerTcpPort;
    
    /**备用服务器TCP端口*/
    private String backupServerTcpPort;
    
    /**位置汇报策略（0,1,2）*/
//    private String tcpMsgRepeatTimes;
    
    /**位置汇报方案（0,1）*/
//    private String tcpMsgRepeatTimes;
    
    /**报警时间间隔（秒）*/
//    private String tcpMsgRepeatTimes;
    
    /**报警距离间隔（米）*/
//    private String tcpMsgRepeatTimes;
    
    /**恢复出厂设置电话号码*/
//    private String tcpMsgRepeatTimes;
    
    /**最高速度（km/h）*/
//    private String tcpMsgRepeatTimes;
    
    /**超速持续时间（秒）*/
//    private String tcpMsgRepeatTimes;
    
    /**连续驾驶时间上限（秒）*/
//    private String tcpMsgRepeatTimes;
    
    /**当天累计驾驶时间上限（秒）*/
//    private String tcpMsgRepeatTimes;
    
    /**超时停车阈值（秒）*/
//    private String tcpMsgRepeatTimes;
    
    public String getTerminalSerialId()
    {
        return terminalSerialId;
    }

    public void setTerminalSerialId(String terminalSerialId)
    {
        this.terminalSerialId = terminalSerialId;
    }
    
    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getMsgId()
    {
        return msgId;
    }

    public void setMsgId(String msgId)
    {
        this.msgId = msgId;
    }

    public String getMsgFlowNumber()
    {
        return msgFlowNumber;
    }

    public void setMsgFlowNumber(String msgFlowNumber)
    {
        this.msgFlowNumber = msgFlowNumber;
    }

    public String getParamTotal()
    {
        return paramTotal;
    }

    public void setParamTotal(String paramTotal)
    {
        this.paramTotal = paramTotal;
    }

    public String getHeartbeatSendInterval()
    {
        return heartbeatSendInterval;
    }

    public void setHeartbeatSendInterval(String heartbeatSendInterval)
    {
        this.heartbeatSendInterval = heartbeatSendInterval;
    }

    public String getTcpResponseTimeout()
    {
        return tcpResponseTimeout;
    }

    public void setTcpResponseTimeout(String tcpResponseTimeout)
    {
        this.tcpResponseTimeout = tcpResponseTimeout;
    }

    public String getTcpMsgRepeatTimes()
    {
        return tcpMsgRepeatTimes;
    }

    public void setTcpMsgRepeatTimes(String tcpMsgRepeatTimes)
    {
        this.tcpMsgRepeatTimes = tcpMsgRepeatTimes;
    }

    public String getUdpResponseTimeout()
    {
        return udpResponseTimeout;
    }

    public void setUdpResponseTimeout(String udpResponseTimeout)
    {
        this.udpResponseTimeout = udpResponseTimeout;
    }

    public String getUdpMsgRepeatTimes()
    {
        return udpMsgRepeatTimes;
    }

    public void setUdpMsgRepeatTimes(String udpMsgRepeatTimes)
    {
        this.udpMsgRepeatTimes = udpMsgRepeatTimes;
    }

    public String getMainServerAddr()
    {
        return mainServerAddr;
    }

    public void setMainServerAddr(String mainServerAddr)
    {
        this.mainServerAddr = mainServerAddr;
    }

    public String getBackupServerAddr()
    {
        return backupServerAddr;
    }

    public void setBackupServerAddr(String backupServerAddr)
    {
        this.backupServerAddr = backupServerAddr;
    }

    public String getMainServerTcpPort()
    {
        return mainServerTcpPort;
    }

    public void setMainServerTcpPort(String mainServerTcpPort)
    {
        this.mainServerTcpPort = mainServerTcpPort;
    }

    public String getBackupServerTcpPort()
    {
        return backupServerTcpPort;
    }

    public void setBackupServerTcpPort(String backupServerTcpPort)
    {
        this.backupServerTcpPort = backupServerTcpPort;
    }
    
    @Override
    protected Object getBaseEntity() {
        return this;
    }

    @Override
    public String toString() {
        return "TerminalEntity [phone=" + phone
                + ", msgId=" + msgId + ", msgFlowNumber="
                + msgFlowNumber + ", paramTotal=" + paramTotal + ", heartbeatSendInterval="
                + heartbeatSendInterval + ", tcpResponseTimeout=" + tcpResponseTimeout
                /**
                 * TODO: 字段未填写完整，后续补上
                 * 
                 * */
                + "]";
    }
}
