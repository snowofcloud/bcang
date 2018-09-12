/**
 * 文件名：RuleRegulationForm.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-8-4 
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.vehiclemonitor.formbean;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.c503.sc.base.formbean.PageForm;


/**
 * 〈一句话功能简述〉规章制度FormBean
 * 〈功能详细描述〉
 * @author    xiaoqx
 * @version   [版本号, 2016-8-4]
 * @see       [相关类/方法]
 * @since     [产品/模块版本] 
 */
public class RuleRegulationForm extends PageForm{

    /** 序列号*/
    private static final long serialVersionUID = 1L;
    
    /** id*/
    private String id;

    /** 编号*/
    private String identifier;

    /** 名称*/
    private String ruleName;

    /** 签发单位*/
    private String company;

    /** 发布时间*/
    @DateTimeFormat(pattern = "yyyy-MM-dd", iso = ISO.DATE)
    private Date sendDate;

    /** 发布内容*/
    private String sendContent;

    /** 发布开始时间*/
    @DateTimeFormat(pattern = "yyyy-MM-dd", iso = ISO.DATE)
    private Date startDate;
    
    /** 发布结束时间*/
    @DateTimeFormat(pattern = "yyyy-MM-dd", iso = ISO.DATE)
    private Date endDate;
    
    /**
     * 
     * 〈一句话功能简述〉 获取id
     * 〈功能详细描述〉
     * 
     * @return id
     * @see [类、类#方法、类#成员]
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * 〈一句话功能简述〉 设置id
     * 〈功能详细描述〉
     * 
     * @param id id
     * @see [类、类#方法、类#成员]
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * 
     * 〈一句话功能简述〉 获取编号
     * 〈功能详细描述〉
     * 
     * @return 编号
     * @see [类、类#方法、类#成员]
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * 
     * 〈一句话功能简述〉 设置编号
     * 〈功能详细描述〉
     * 
     * @param identifier 编号
     * @see [类、类#方法、类#成员]
     */
    public void setIdentifier(String identifier) {
        this.identifier = identifier == null ? null : identifier.trim();
    }

    /**
     * 
     * 〈一句话功能简述〉 获取名称
     * 〈功能详细描述〉
     * 
     * @return 名称
     * @see [类、类#方法、类#成员]
     */
    public String getRuleName() {
        return ruleName;
    }

    /**
     * 
     * 〈一句话功能简述〉 设置名称
     * 〈功能详细描述〉
     * 
     * @param ruleName 名称
     * @see [类、类#方法、类#成员]
     */
    public void setRuleName(String ruleName) {
        this.ruleName = ruleName == null ? null : ruleName.trim();
    }

    /**
     * 
     * 〈一句话功能简述〉 获取签发单位
     * 〈功能详细描述〉
     * 
     * @return 签发单位
     * @see [类、类#方法、类#成员]
     */
    public String getCompany() {
        return company;
    }

    /**
     * 
     * 〈一句话功能简述〉 设置签发单位
     * 〈功能详细描述〉
     * 
     * @param company 签发单位
     * @see [类、类#方法、类#成员]
     */
    public void setCompany(String company) {
        this.company = company == null ? null : company.trim();
    }

    /**
     * 
     * 〈一句话功能简述〉 获取发布时间
     * 〈功能详细描述〉
     * 
     * @return 发布时间
     * @see [类、类#方法、类#成员]
     */
    public Date getSendDate() {
        return sendDate;
    }

    /**
     * 
     * 〈一句话功能简述〉 设置发布时间
     * 〈功能详细描述〉
     * 
     * @param sendDate 发布时间
     * @see [类、类#方法、类#成员]
     */
    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    /**
     * 
     * 〈一句话功能简述〉 获取发布内容
     * 〈功能详细描述〉
     * 
     * @return 发布内容
     * @see [类、类#方法、类#成员]
     */
    public String getSendContent() {
        return sendContent;
    }

    /**
     * 
     * 〈一句话功能简述〉 设置发布内容
     * 〈功能详细描述〉
     * 
     * @param sendContent 发布内容
     * @see [类、类#方法、类#成员]
     */
    public void setSendContent(String sendContent) {
        this.sendContent = sendContent == null ? null : sendContent.trim();
    }

    /**
     * 
     * 〈一句话功能简述〉 获取发布开始时间
     * 〈功能详细描述〉
     * 
     * @return 发布开始时间
     * @see [类、类#方法、类#成员]
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * 
     * 〈一句话功能简述〉 设置发布开始时间
     * 〈功能详细描述〉
     * 
     * @param startDate 发布开始时间
     * @see [类、类#方法、类#成员]
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * 
     * 〈一句话功能简述〉 获取发布结束时间
     * 〈功能详细描述〉
     * 
     * @return 发布结束时间
     * @see [类、类#方法、类#成员]
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * 
     * 〈一句话功能简述〉 设置发布结束时间
     * 〈功能详细描述〉
     * 
     * @param endDate 发布结束时间
     * @see [类、类#方法、类#成员]
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
