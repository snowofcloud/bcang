/**
 * 文件名：LogManage.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2017-4-5
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.transacation.bean;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 
 * 〈一句话功能简述〉订单日志管理
 * 〈功能详细描述〉
 * 
 * @author guqh
 * @version [版本号, 2017-4-5]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class LogManage {
    /** 主键ID */
    private String id;
    
    /** 订单ID */
    private String orderId;
    
    /** 企业法人号 */
    private String corporateNo;
    
    /** 交易状态 */
    private String tradeStatus;
    
    /** 提醒物流企业 */
    private String remindLogistics;
    
    /** 提醒化工企业 */
    private String remindChemical;
    
    /** 记录时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date recordTime;
    
    /** 日志内容 */
    private String content;
    
    /** 订单号 */
    private String orderNo;
    
    /** 物流企业名称 */
    private String wlEnterpriseName;
    
    /** 化工企业名称 */
    private String hgEnterpriseName;
    
    /**
     * 〈一句话功能简述〉获得wlEnterpriseName
     * 〈功能详细描述〉
     * 
     * @return wlEnterpriseName
     * @see [类、类#方法、类#成员]
     */
    public String getWlEnterpriseName() {
        return wlEnterpriseName;
    }
    
    /**
     * 〈一句话功能简述〉设置wlEnterpriseName
     * 〈功能详细描述〉
     * 
     * @param wlEnterpriseName wlEnterpriseName
     * @see [类、类#方法、类#成员]
     */
    public void setWlEnterpriseName(String wlEnterpriseName) {
        this.wlEnterpriseName = wlEnterpriseName;
    }
    
    /**
     * 〈一句话功能简述〉获得hgEnterpriseName
     * 〈功能详细描述〉
     * 
     * @return hgEnterpriseName
     * @see [类、类#方法、类#成员]
     */
    public String getHgEnterpriseName() {
        return hgEnterpriseName;
    }
    
    /**
     * 〈一句话功能简述〉设置hgEnterpriseName
     * 〈功能详细描述〉
     * 
     * @param hgEnterpriseName hgEnterpriseName
     * @see [类、类#方法、类#成员]
     */
    public void setHgEnterpriseName(String hgEnterpriseName) {
        this.hgEnterpriseName = hgEnterpriseName;
    }
    
    /**
     * 〈一句话功能简述〉获得orderNo
     * 〈功能详细描述〉
     * 
     * @return orderNo
     * @see [类、类#方法、类#成员]
     */
    public String getOrderNo() {
        return orderNo;
    }
    
    /**
     * 〈一句话功能简述〉设置orderNo
     * 〈功能详细描述〉
     * 
     * @param orderNo orderNo
     * @see [类、类#方法、类#成员]
     */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
    
    /**
     * 〈一句话功能简述〉获得content
     * 〈功能详细描述〉
     * 
     * @return content
     * @see [类、类#方法、类#成员]
     */
    public String getContent() {
        return content;
    }
    
    /**
     * 〈一句话功能简述〉设置content
     * 〈功能详细描述〉
     * 
     * @param content content
     * @see [类、类#方法、类#成员]
     */
    public void setContent(String content) {
        this.content = content;
    }
    
    /**
     * 〈一句话功能简述〉获得recordTime
     * 〈功能详细描述〉
     * 
     * @return recordTime
     * @see [类、类#方法、类#成员]
     */
    public Date getRecordTime() {
        return recordTime;
    }
    
    /**
     * 〈一句话功能简述〉设置recordTime
     * 〈功能详细描述〉
     * 
     * @param recordTime recordTime
     * @see [类、类#方法、类#成员]
     */
    public void setRecordTime(Date recordTime) {
        this.recordTime = recordTime;
    }
    
    /**
     * 〈一句话功能简述〉获得id
     * 〈功能详细描述〉
     * 
     * @return id
     * @see [类、类#方法、类#成员]
     */
    public String getId() {
        return id;
    }
    
    /**
     * 〈一句话功能简述〉设置id
     * 〈功能详细描述〉
     * 
     * @param id id
     * @see [类、类#方法、类#成员]
     */
    public void setId(String id) {
        this.id = id;
    }
    
    /**
     * 〈一句话功能简述〉获得orderId
     * 〈功能详细描述〉
     * 
     * @return orderId
     * @see [类、类#方法、类#成员]
     */
    public String getOrderId() {
        return orderId;
    }
    
    /**
     * 〈一句话功能简述〉设置orderId
     * 〈功能详细描述〉
     * 
     * @param orderId orderId
     * @see [类、类#方法、类#成员]
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    
    /**
     * 〈一句话功能简述〉获得corporateNo
     * 〈功能详细描述〉
     * 
     * @return corporateNo
     * @see [类、类#方法、类#成员]
     */
    public String getCorporateNo() {
        return corporateNo;
    }
    
    /**
     * 〈一句话功能简述〉设置corporateNo
     * 〈功能详细描述〉
     * 
     * @param corporateNo corporateNo
     * @see [类、类#方法、类#成员]
     */
    public void setCorporateNo(String corporateNo) {
        this.corporateNo = corporateNo;
    }
    
    /**
     * 〈一句话功能简述〉获得tradeStatus
     * 〈功能详细描述〉
     * 
     * @return tradeStatus
     * @see [类、类#方法、类#成员]
     */
    public String getTradeStatus() {
        return tradeStatus;
    }
    
    /**
     * 〈一句话功能简述〉设置tradeStatus
     * 〈功能详细描述〉
     * 
     * @param tradeStatus tradeStatus
     * @see [类、类#方法、类#成员]
     */
    public void setTradeStatus(String tradeStatus) {
        this.tradeStatus = tradeStatus;
    }
    
    /**
     * 〈一句话功能简述〉获得remindLogistics
     * 〈功能详细描述〉
     * 
     * @return remindLogistics
     * @see [类、类#方法、类#成员]
     */
    public String getRemindLogistics() {
        return remindLogistics;
    }
    
    /**
     * 〈一句话功能简述〉设置remindLogistics
     * 〈功能详细描述〉
     * 
     * @param remindLogistics remindLogistics
     * @see [类、类#方法、类#成员]
     */
    public void setRemindLogistics(String remindLogistics) {
        this.remindLogistics = remindLogistics;
    }
    
    /**
     * 〈一句话功能简述〉获得remindChemical
     * 〈功能详细描述〉
     * 
     * @return remindChemical
     * @see [类、类#方法、类#成员]
     */
    public String getRemindChemical() {
        return remindChemical;
    }
    
    /**
     * 〈一句话功能简述〉设置remindChemical
     * 〈功能详细描述〉
     * 
     * @param remindChemical remindChemical
     * @see [类、类#方法、类#成员]
     */
    public void setRemindChemical(String remindChemical) {
        this.remindChemical = remindChemical;
    }
    
}
