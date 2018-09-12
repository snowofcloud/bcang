/**
 * 文件名：EmergentLinkmanForm.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-8-5 
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.vehiclemonitor.formbean;

import com.c503.sc.base.formbean.PageForm;


/**
 * 〈一句话功能简述〉紧急联系人FormBean
 * 〈功能详细描述〉
 * @author    xiaoqx
 * @version   [版本号, 2016-8-5]
 * @see       [相关类/方法]
 * @since     [产品/模块版本] 
 */
public class EmergentLinkmanForm extends PageForm{
    /** 序列号 */
    private static final long serialVersionUID = 1L;

    /** id*/
    private String id;

    /** 单位名称*/
    private String companyName;

    /** 姓名*/
    private String personName;

    /** 手机号码*/
    private String telephone;

    /** 固定号码*/
    private String fixedNo;

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
}
