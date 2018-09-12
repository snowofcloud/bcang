/**
 * 文件名：KeepTalk.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-9-8
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.transacation.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 〈一句话功能简述〉留言信息
 * 〈功能详细描述〉
 * 
 * @author zz
 * @version [版本号, 2016-9-8]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class KeepTalk {
    
    /** 货源号 */
    private String goodsNo;
    
    /** 留言的物流企业法人代码 */
    private String logisticsEnterprise;
    
    /** 货单号(用于从后台加载信息到前台展示，如查看详情) */
    private String waybilllNo;
    
    /** 留言内容 */
    private String messageContent;
    
    /** 留言企业名称 */
    private String enterpriseName;
    
    /** 留言时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date keepTalkTime;
    
    /**
     * 
     * 〈一句话功能简述〉获得货源号
     * 〈功能详细描述〉
     * 
     * @return goodsNo
     * @see [类、类#方法、类#成员]
     */
    public String getGoodsNo() {
        return goodsNo;
    }
    
    /**
     * 
     * 〈一句话功能简述〉设置货源号
     * 〈功能详细描述〉
     * 
     * @param goodsNo goodsNo
     * @see [类、类#方法、类#成员]
     */
    public void setGoodsNo(String goodsNo) {
        this.goodsNo = goodsNo;
    }
    
    /**
     * 〈一句话功能简述〉获得物流企业
     * 〈功能详细描述〉
     * 
     * @return logisticsEnterprise
     * @see [类、类#方法、类#成员]
     */
    public String getLogisticsEnterprise() {
        return logisticsEnterprise;
    }
    
    /**
     * 〈一句话功能简述〉设置物流企业
     * 〈功能详细描述〉
     * 
     * @param logisticsEnterprise 物流企业
     * @see [类、类#方法、类#成员]
     */
    public void setLogisticsEnterprise(String logisticsEnterprise) {
        this.logisticsEnterprise = logisticsEnterprise;
    }
    
    /**
     * 〈一句话功能简述〉获得货源号
     * 〈功能详细描述〉
     * 
     * @return waybilllNo
     * @see [类、类#方法、类#成员]
     */
    public String getWaybilllNo() {
        return waybilllNo;
    }
    
    /**
     * 〈一句话功能简述〉设置货源号
     * 〈功能详细描述〉
     * 
     * @param waybilllNo 货源号
     * @see [类、类#方法、类#成员]
     */
    public void setWaybilllNo(String waybilllNo) {
        this.waybilllNo = waybilllNo;
    }
    
    /**
     * 〈一句话功能简述〉获得留言内容
     * 〈功能详细描述〉
     * 
     * @return messageContent
     * @see [类、类#方法、类#成员]
     */
    public String getMessageContent() {
        return messageContent;
    }
    
    /**
     * 〈一句话功能简述〉设置留言内容
     * 〈功能详细描述〉
     * 
     * @param messageContent 留言内容
     * @see [类、类#方法、类#成员]
     */
    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }
    
    /**
     * 〈一句话功能简述〉 留言企业名称
     * 〈功能详细描述〉
     * 
     * @return 留言企业名称
     * @see [类、类#方法、类#成员]
     */
    public String getEnterpriseName() {
        return enterpriseName;
    }
    
    /**
     * 〈一句话功能简述〉留言企业名称
     * 〈功能详细描述〉
     * 
     * @param enterpriseName 留言企业名称
     * @see [类、类#方法、类#成员]
     */
    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }
    
    /**
     * 〈一句话功能简述〉留言时间
     * 〈功能详细描述〉
     * 
     * @return 留言时间
     * @see [类、类#方法、类#成员]
     */
    public Date getKeepTalkTime() {
        return keepTalkTime;
    }
    
    /**
     * 〈一句话功能简述〉留言时间
     * 〈功能详细描述〉
     * 
     * @param keepTalkTime 留言时间
     * @see [类、类#方法、类#成员]
     */
    public void setKeepTalkTime(Date keepTalkTime) {
        this.keepTalkTime = keepTalkTime;
    }
    
}
