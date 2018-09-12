/**
 * 文件名：EmergentLinkmanEntity.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-8-5
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.vehiclemonitor.bean;

import com.c503.sc.base.entity.BaseEntity;

/**
 * 〈一句话功能简述〉紧急联系人实体类
 * 〈功能详细描述〉
 * 
 * @author xiaoqx
 * @version [版本号, 2016-8-5]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class EmergentLinkmanEntity extends BaseEntity {
    
    /** 序列号 */
    private static final long serialVersionUID = 1L;
    
    /** 单位名称 */
    private String companyName;
    
    /** 姓名 */
    private String personName;
    
    /** 手机号码 */
    private String telephone;
    
    /** 固定号码 */
    private String fixedNo;
    
    /**
     * 
     * 〈一句话功能简述〉 获取单位名称
     * 〈功能详细描述〉
     * 
     * @return companyName
     * @see [类、类#方法、类#成员]
     */
    public String getCompanyName() {
        return companyName;
    }
    
    /**
     * 
     * 〈一句话功能简述〉 设置单位名称
     * 〈功能详细描述〉
     * 
     * @param companyName companyName
     * @see [类、类#方法、类#成员]
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
    }
    
    /**
     * 
     * 〈一句话功能简述〉 获取姓名
     * 〈功能详细描述〉
     * 
     * @return personName
     * @see [类、类#方法、类#成员]
     */
    public String getPersonName() {
        return personName;
    }
    
    /**
     * 
     * 〈一句话功能简述〉 设置姓名
     * 〈功能详细描述〉
     * 
     * @param personName personName
     * @see [类、类#方法、类#成员]
     */
    public void setPersonName(String personName) {
        this.personName = personName == null ? null : personName.trim();
    }
    
    /**
     * 
     * 〈一句话功能简述〉 获取手机号码
     * 〈功能详细描述〉
     * 
     * @return telephone
     * @see [类、类#方法、类#成员]
     */
    public String getTelephone() {
        return telephone;
    }
    
    /**
     * 
     * 〈一句话功能简述〉 设置手机号码
     * 〈功能详细描述〉
     * 
     * @param telephone telephone
     * @see [类、类#方法、类#成员]
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone == null ? null : telephone.trim();
    }
    
    /**
     * 
     * 〈一句话功能简述〉 获取固定号码
     * 〈功能详细描述〉
     * 
     * @return fixedNo
     * @see [类、类#方法、类#成员]
     */
    public String getFixedNo() {
        return fixedNo;
    }
    
    /**
     * 
     * 〈一句话功能简述〉 设置固定号码
     * 〈功能详细描述〉
     * 
     * @param fixedNo fixedNo
     * @see [类、类#方法、类#成员]
     */
    public void setFixedNo(String fixedNo) {
        this.fixedNo = fixedNo == null ? null : fixedNo.trim();
    }
    
    @Override
    protected Object getBaseEntity() {
        return this;
    }
}
