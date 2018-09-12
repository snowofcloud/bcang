/**
 * 文件名：LeaveMessageForm.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-8-30
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.transacation.formbean;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.c503.sc.base.formbean.PageForm;

/**
 * 
 * 〈一句话功能简述〉留言信息Form
 * 〈功能详细描述〉
 * 
 * @author xinzhiwei
 * @version [版本号, 2016-8-30]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class LeaveMessageForm extends PageForm {
    /** 序列号 */
    private static final long serialVersionUID = 2143451561339348085L;
    
    /** 留言ID */
    private String id;
    
    /** 留言的物流企业 */
    private String logisticsEnterprise;
    
    /** 货源号 */
    private String goodsNo;
    
    /** 留言内容 */
    private String messageContent;
    
    /** 货源所属化工企业 */
    private String chemicalEnterprise;
    
    /** 留言时间 */
    //private String createdTime;
    /** 留言时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss", iso = ISO.DATE)
    //这里原来写的是@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")然后报错说不能将string类型转换为date类型
    private Date createdTime;
    
    
    /**
     * 〈一句话功能简述〉获得留言ID
     * 〈功能详细描述〉
     * 
     * @return id
     * @see [类、类#方法、类#成员]
     */
    public String getId() {
        return id;
    }
    
	/**
     * 〈一句话功能简述〉设置留言ID
     * 〈功能详细描述〉
     * 
     * @param id 留言ID
     * @see [类、类#方法、类#成员]
     */
    public void setId(String id) {
        this.id = id;
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
     * @return goodsNo
     * @see [类、类#方法、类#成员]
     */
    public String getGoodsNo() {
        return goodsNo;
    }
    
    /**
     * 〈一句话功能简述〉设置货源号
     * 〈功能详细描述〉
     * 
     * @param goodsNo 货源号
     * @see [类、类#方法、类#成员]
     */
    public void setGoodsNo(String goodsNo) {
        this.goodsNo = goodsNo;
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
     * 〈一句话功能简述〉获得化工企业
     * 〈功能详细描述〉
     * 
     * @return chemicalEnterprise
     * @see [类、类#方法、类#成员]
     */
    public String getChemicalEnterprise() {
        return chemicalEnterprise;
    }
    
    /**
     * 〈一句话功能简述〉设置化工企业
     * 〈功能详细描述〉
     * 
     * @param chemicalEnterprise 化工企业
     * @see [类、类#方法、类#成员]
     */
    public void setChemicalEnterprise(String chemicalEnterprise) {
        this.chemicalEnterprise = chemicalEnterprise;
    }
    
    /**
     * 〈一句话功能简述〉获得留言时间
     * 〈功能详细描述〉
     * 
     * @return createdTime
     * @see [类、类#方法、类#成员]
     */
    public Date getCreatedTime() {
        return createdTime;
    }
    
    /**
     * 〈一句话功能简述〉设置留言时间
     * 〈功能详细描述〉
     * 
     * @param createdTime 留言时间
     * @see [类、类#方法、类#成员]
     */
    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }
    
    
}
