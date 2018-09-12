package com.c503.sc.jxwl.transacation.bean;

import java.math.BigDecimal;
import java.util.Date;

import com.c503.sc.base.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 〈一句话功能简述〉评价投诉对象
 * 〈功能详细描述〉对应t_comment_complain
 * 
 * @author zhongz
 * @version [版本号, 2016-12-20]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class CommentComplain extends BaseEntity {
    
    /** 序列号 */
    private static final long serialVersionUID = 1949124225858324968L;
    
    /** 订单id */
    private String orderId;
    
    /** 内容 */
    private String content;
    
    /** 评价分数 */
    private BigDecimal score;
    
    /** 类型（0：评价、1：投诉） */
    private String type;
    
    /** 被评价、投诉方的法人代码 */
    private String otherSideNo;
    
    /** 当前进行评价、投诉的企业法人代码 */
    private String corporateNo;
    
    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
    
    // 额外添加属性
    /** 企业名称 */
    private String enterpriseName;
    
    /** 订单号 */
    private String orderNo;
    
    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    
    /** 评价、投诉时间 */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date complainDate;
    
    
    /**
     *〈一句话功能简述〉获取评价、投诉时间 
     * 〈功能详细描述〉
     * @return complainDate
     * @see  [类、类#方法、类#成员]
     */
    public Date getComplainDate() {
        return complainDate;
    }

    /**
     *〈一句话功能简述〉设置评价、投诉时间 
     * 〈功能详细描述〉
     * @param complainDate complainDate
     * @see  [类、类#方法、类#成员]
     */
    public void setComplainDate(Date complainDate) {
        this.complainDate = complainDate;
    }

    
    
    
    /**
     * 〈一句话功能简述〉createTime
     * 〈功能详细描述〉
     * 
     * @return createTime
     * @see [类、类#方法、类#成员]
     */
    public Date getCreateTime() {
		return createTime;
	}
    /**
     * 〈一句话功能简述〉createTime
     * 〈功能详细描述〉
     * 
     * @param createTime createTime
     * @see [类、类#方法、类#成员]
     */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
     * 〈一句话功能简述〉订单id
     * 〈功能详细描述〉
     * 
     * @return 订单id
     * @see [类、类#方法、类#成员]
     */
    public String getOrderId() {
        return orderId;
    }
    
    /**
     * 〈一句话功能简述〉订单id
     * 〈功能详细描述〉
     * 
     * @param orderId 订单id
     * @see [类、类#方法、类#成员]
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }
    
    /**
     * 〈一句话功能简述〉内容
     * 〈功能详细描述〉
     * 
     * @return 内容
     * @see [类、类#方法、类#成员]
     */
    public String getContent() {
        return content;
    }
    
    /**
     * 〈一句话功能简述〉内容
     * 〈功能详细描述〉
     * 
     * @param content 内容
     * @see [类、类#方法、类#成员]
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
    
    /**
     * 〈一句话功能简述〉评价分数
     * 〈功能详细描述〉
     * 
     * @return 评价分数
     * @see [类、类#方法、类#成员]
     */
    public BigDecimal getScore() {
        return score;
    }
    
    /**
     * 〈一句话功能简述〉评价分数
     * 〈功能详细描述〉
     * 
     * @param score 评价分数
     * @see [类、类#方法、类#成员]
     */
    public void setScore(BigDecimal score) {
        this.score = score;
    }
    
    /**
     * 〈一句话功能简述〉类型（0：评价、1：投诉）
     * 〈功能详细描述〉
     * 
     * @return 类型（0：评价、1：投诉）
     * @see [类、类#方法、类#成员]
     */
    public String getType() {
        return type;
    }
    
    /**
     * 〈一句话功能简述〉类型（0：评价、1：投诉）
     * 〈功能详细描述〉
     * 
     * @param type 类型（0：评价、1：投诉）
     * @see [类、类#方法、类#成员]
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }
    
    /**
     * 〈一句话功能简述〉被评价、投诉方的法人代码
     * 〈功能详细描述〉
     * 
     * @return 被评价、投诉方的法人代码
     * @see [类、类#方法、类#成员]
     */
    public String getOtherSideNo() {
        return otherSideNo;
    }
    
    /**
     * 〈一句话功能简述〉被评价、投诉方的法人代码
     * 〈功能详细描述〉
     * 
     * @param otherSideNo 被评价、投诉方的法人代码
     * @see [类、类#方法、类#成员]
     */
    public void setOtherSideNo(String otherSideNo) {
        this.otherSideNo = otherSideNo;
    }
    
    /**
     * 〈一句话功能简述〉当前进行评价、投诉的企业法人代码
     * 〈功能详细描述〉
     * 
     * @return 当前进行评价、投诉的企业法人代码
     * @see [类、类#方法、类#成员]
     */
    public String getCorporateNo() {
        return corporateNo;
    }
    
    /**
     * 〈一句话功能简述〉当前进行评价、投诉的企业法人代码
     * 〈功能详细描述〉
     * 
     * @param corporateNo 当前进行评价、投诉的企业法人代码
     * @see [类、类#方法、类#成员]
     */
    public void setCorporateNo(String corporateNo) {
        this.corporateNo = corporateNo;
    }
    
    /**
     * 〈一句话功能简述〉创建时间
     * 〈功能详细描述〉
     * 
     * @return 创建时间
     * @see [类、类#方法、类#成员]
     */
    public Date getUpdateTime() {
        return updateTime;
    }
    
    /**
     * 〈一句话功能简述〉创建时间
     * 〈功能详细描述〉
     * 
     * @param updateTime 创建时间
     * @see [类、类#方法、类#成员]
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    
    /**
     * 〈一句话功能简述〉企业名称
     * 〈功能详细描述〉
     * 
     * @return 企业名称
     * @see [类、类#方法、类#成员]
     */
    public String getEnterpriseName() {
        return enterpriseName;
    }
    
    /**
     * 〈一句话功能简述〉企业名称
     * 〈功能详细描述〉
     * 
     * @param enterpriseName 企业名称
     * @see [类、类#方法、类#成员]
     */
    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }
    
    /**
     * 〈一句话功能简述〉订单号
     * 〈功能详细描述〉
     * 
     * @return 订单号
     * @see [类、类#方法、类#成员]
     */
    public String getOrderNo() {
        return orderNo;
    }
    
    /**
     * 〈一句话功能简述〉订单号
     * 〈功能详细描述〉
     * 
     * @param orderNo 订单号
     * @see [类、类#方法、类#成员]
     */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
    
    @Override
    public String toString() {
        return "CommentComplain [orderId=" + orderId + ", content=" + content
            + ", score=" + score + ", type=" + type + ", otherSideNo="
            + otherSideNo + ", corporateNo=" + corporateNo + ", updateTime="
            + updateTime + ", enterpriseName=" + enterpriseName + ", orderNo="
            + orderNo + "]";
    }
    
    @Override
    protected Object getBaseEntity() {
        return this;
    }
    
}
