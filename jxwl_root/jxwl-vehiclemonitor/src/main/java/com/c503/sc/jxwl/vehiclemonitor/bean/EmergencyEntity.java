/**
 * 文件名：EmergencyEntity.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-7-27
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.vehiclemonitor.bean;

import java.util.Date;

import com.c503.sc.base.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 
 * 〈一句话功能简述〉突然信息实体类
 * 〈功能详细描述〉
 * 
 * @author yuanyl
 * @version [版本号, 2016-7-27]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class EmergencyEntity extends BaseEntity {
    
    /** 序列号 */
    private static final long serialVersionUID = -3379709603388381409L;
    
    /** 标题 */
    private String title;
    
    /** 内容 */
    private String content;
    
    /** 信息来源 */
    private String source;
    
    /** 发送次数 */
    private String sendTimes;
    
    /** 发送频率 */
    private String sendFrequency;
    
    /** 有效时间 */
    private String validTime;
    
    /** 发布时间 */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date publishDate;
    
    /** 推送时间 */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date pushDate;
    
    /**
     * 
     * 〈一句话功能简述〉获取标题
     * 〈功能详细描述〉
     * 
     * @return title
     * @see [类、类#方法、类#成员]
     */
    public String getTitle() {
        return title;
    }
    
    /**
     * 
     * 〈一句话功能简述〉设置标题
     * 〈功能详细描述〉
     * 
     * @param title 标题
     * @see [类、类#方法、类#成员]
     */
    public void setTitle(String title) {
        this.title = title;
    }
    
    /**
     * 
     * 〈一句话功能简述〉获取内容
     * 〈功能详细描述〉
     * 
     * @return content
     * @see [类、类#方法、类#成员]
     */
    public String getContent() {
        return content;
    }
    
    /**
     * 
     * 〈一句话功能简述〉设置内容
     * 〈功能详细描述〉
     * 
     * @param content 内容
     * @see [类、类#方法、类#成员]
     */
    public void setContent(String content) {
        this.content = content;
    }
    
    /**
     * 
     * 〈一句话功能简述〉获取发送次数
     * 〈功能详细描述〉
     * 
     * @return sendTimes
     * @see [类、类#方法、类#成员]
     */
    public String getSendTimes() {
        return sendTimes;
    }
    
    /**
     * 
     * 〈一句话功能简述〉设置发送次数
     * 〈功能详细描述〉
     * 
     * @param sendTimes 发送次数
     * @see [类、类#方法、类#成员]
     */
    public void setSendTimes(String sendTimes) {
        this.sendTimes = sendTimes;
    }
    
    /**
     * 
     * 〈一句话功能简述〉获取发送频率
     * 〈功能详细描述〉
     * 
     * @return sendFrequency
     * @see [类、类#方法、类#成员]
     */
    public String getSendFrequency() {
        return sendFrequency;
    }
    
    /**
     * 
     * 〈一句话功能简述〉设置发送频率
     * 〈功能详细描述〉
     * 
     * @param sendFrequency 发送频率
     * @see [类、类#方法、类#成员]
     */
    public void setSendFrequency(String sendFrequency) {
        this.sendFrequency = sendFrequency;
    }
    
    /**
     * 
     * 〈一句话功能简述〉获取有效时间
     * 〈功能详细描述〉
     * 
     * @return validTime
     * @see [类、类#方法、类#成员]
     */
    public String getValidTime() {
        return validTime;
    }
    
    /**
     * 
     * 〈一句话功能简述〉设置有效时间
     * 〈功能详细描述〉
     * 
     * @param validTime 有效时间
     * @see [类、类#方法、类#成员]
     */
    public void setValidTime(String validTime) {
        this.validTime = validTime;
    }
    
    /**
     * 
     * 〈一句话功能简述〉获取信息来源
     * 〈功能详细描述〉
     * 
     * @return source
     * @see [类、类#方法、类#成员]
     */
    public String getSource() {
        return source;
    }
    
    /**
     * 
     * 〈一句话功能简述〉设置信息来源
     * 〈功能详细描述〉
     * 
     * @param source 信息来源
     * @see [类、类#方法、类#成员]
     */
    public void setSource(String source) {
        this.source = source;
    }
    
    /**
     * 
     * 〈一句话功能简述〉获取发布时间
     * 〈功能详细描述〉
     * 
     * @return publishDate
     * @see [类、类#方法、类#成员]
     */
    public Date getPublishDate() {
        return publishDate;
    }
    
    /**
     * 
     * 〈一句话功能简述〉设置发布时间
     * 〈功能详细描述〉
     * 
     * @param publishDate 发布时间
     * @see [类、类#方法、类#成员]
     */
    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }
    
    /**
     * 
     * 〈一句话功能简述〉获取推送时间
     * 〈功能详细描述〉
     * 
     * @return pushDate
     * @see [类、类#方法、类#成员]
     */
    public Date getPushDate() {
        return pushDate;
    }
    
    /**
     * 
     * 〈一句话功能简述〉设置推送时间
     * 〈功能详细描述〉
     * 
     * @param pushDate 推送时间
     * @see [类、类#方法、类#成员]
     */
    public void setPushDate(Date pushDate) {
        this.pushDate = pushDate;
    }
    
    @Override
    protected Object getBaseEntity() {
        return this;
    }
    
}
