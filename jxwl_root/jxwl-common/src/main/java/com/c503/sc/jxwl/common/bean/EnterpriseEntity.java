/*
 * 文件名：EnterpriseEntity
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉用户实体模型
 * 修改时间：2015年6月5日
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.common.bean;

import java.io.Serializable;

import com.c503.sc.base.entity.BaseEntity;

/**
 * 〈一句话功能简述〉企业实体
 * 〈功能详细描述〉
 * 
 * @author zz
 * @version [版本号, 2016-6-28]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class EnterpriseEntity extends BaseEntity implements Serializable {
    /** 序列号 */
    private static final long serialVersionUID = 2819452015297847668L;
    
    /** 法人代码 */
    private String corporateCode;
    
    /** 企业名称 */
    private String name;
    
    /** 行业类型 */
    private String tradeTypeCode;
    
    /** 行业中类 */
    private String tradeClassCode;
    
    /** 地址 */
    private String addr;
    
    /** 电话 */
    private String tel;
    
    /** 邮箱 */
    private String email;
    
    /** 经营范围 */
    private String brief;
    
    /**
     * 〈一句话功能简述〉法人代码
     * 〈功能详细描述〉
     * 
     * @return 法人代码
     * @see [类、类#方法、类#成员]
     */
    public String getCorporateCode() {
        return corporateCode;
    }
    
    /**
     * 〈一句话功能简述〉法人代码
     * 〈功能详细描述〉
     * 
     * @param corporateCode 法人代码
     * @see [类、类#方法、类#成员]
     */
    public void setCorporateCode(String corporateCode) {
        this.corporateCode =
            corporateCode == null ? null : corporateCode.trim();
    }
    
    /**
     * 〈一句话功能简述〉企业名称
     * 〈功能详细描述〉
     * 
     * @return 企业名称
     * @see [类、类#方法、类#成员]
     */
    public String getName() {
        return name;
    }
    
    /**
     * 〈一句话功能简述〉企业名称
     * 〈功能详细描述〉
     * 
     * @param name 企业名称
     * @see [类、类#方法、类#成员]
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
    
    /**
     * 〈一句话功能简述〉行业类型
     * 〈功能详细描述〉
     * 
     * @return 行业类型
     * @see [类、类#方法、类#成员]
     */
    public String getTradeTypeCode() {
        return tradeTypeCode;
    }
    
    /**
     * 〈一句话功能简述〉行业类型
     * 〈功能详细描述〉
     * 
     * @param tradeTypeCode 行业类型
     * @see [类、类#方法、类#成员]
     */
    public void setTradeTypeCode(String tradeTypeCode) {
        this.tradeTypeCode =
            tradeTypeCode == null ? null : tradeTypeCode.trim();
    }
    
    /**
     * 〈一句话功能简述〉行业中类
     * 〈功能详细描述〉
     * 
     * @return 行业中类
     * @see [类、类#方法、类#成员]
     */
    public String getTradeClassCode() {
        return tradeClassCode;
    }
    
    /**
     * 〈一句话功能简述〉行业中类
     * 〈功能详细描述〉
     * 
     * @param tradeClassCode 行业中类
     * @see [类、类#方法、类#成员]
     */
    public void setTradeClassCode(String tradeClassCode) {
        this.tradeClassCode =
            tradeClassCode == null ? null : tradeClassCode.trim();
    }
    
    /**
     * 〈一句话功能简述〉地址
     * 〈功能详细描述〉
     * 
     * @return 地址
     * @see [类、类#方法、类#成员]
     */
    public String getAddr() {
        return addr;
    }
    
    /**
     * 〈一句话功能简述〉地址
     * 〈功能详细描述〉
     * 
     * @param addr 地址
     * @see [类、类#方法、类#成员]
     */
    public void setAddr(String addr) {
        this.addr = addr == null ? null : addr.trim();
    }
    
    /**
     * 〈一句话功能简述〉电话
     * 〈功能详细描述〉
     * 
     * @return 电话
     * @see [类、类#方法、类#成员]
     */
    public String getTel() {
        return tel;
    }
    
    /**
     * 〈一句话功能简述〉电话
     * 〈功能详细描述〉
     * 
     * @param tel 电话
     * @see [类、类#方法、类#成员]
     */
    public void setTel(String tel) {
        this.tel = tel == null ? null : tel.trim();
    }
    
    /**
     * 〈一句话功能简述〉邮箱
     * 〈功能详细描述〉
     * 
     * @return 邮箱
     * @see [类、类#方法、类#成员]
     */
    public String getEmail() {
        return email;
    }
    
    /**
     * 〈一句话功能简述〉邮箱
     * 〈功能详细描述〉
     * 
     * @param email 邮箱
     * @see [类、类#方法、类#成员]
     */
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }
    
    /**
     * 〈一句话功能简述〉经营范围
     * 〈功能详细描述〉
     * 
     * @return 经营范围
     * @see [类、类#方法、类#成员]
     */
    public String getBrief() {
        return brief;
    }
    
    /**
     * 〈一句话功能简述〉经营范围
     * 〈功能详细描述〉
     * 
     * @param brief 经营范围
     * @see [类、类#方法、类#成员]
     */
    public void setBrief(String brief) {
        this.brief = brief == null ? null : brief.trim();
    }
    
    @Override
    protected Object getBaseEntity() {
        return this;
    }
}
