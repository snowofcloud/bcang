/*
 * 文件名：UserEntity.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉用户实体模型
 * 修改时间：2015年6月5日
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.common.bean;

import java.util.List;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * 
 * @author xiaoqx
 * @version [版本号, 2016-8-16]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class BizUserEntity extends com.c503.sc.base.entity.common.UserEntity {
    /** 序列号 */
    private static final long serialVersionUID = -5311750492794597464L;
    
    /** 机构ID */
    private String deptId;
    
    /** 拓展字段 部门名称 */
    private String deptName;
    
    /** 法人代码 */
    private String corporateCode;
    
    /** 角色code */
    private List<String> roleCodes;
    
    /** 身份证号码 */
    private String idCard;
    
    /**
     * 〈一句话功能简述〉获取部门名称 〈功能详细描述〉
     * 
     * @return 部门名称
     * @see [类、类#方法、类#成员]
     */
    public String getDeptName() {
        return deptName;
    }
    
    /**
     * 〈一句话功能简述〉设置部门名称 〈功能详细描述〉
     * 
     * @param deptName
     *            部门名称
     * @see [类、类#方法、类#成员]
     */
    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
    
    /**
     * 〈一句话功能简述〉获取机构ID 〈功能详细描述〉
     * 
     * @return 机构ID
     * @see [类、类#方法、类#成员]
     */
    public String getDeptId() {
        return this.getOrgId();
    }
    
    /**
     * 〈一句话功能简述〉设置机构ID 〈功能详细描述〉
     * 
     * @param deptId
     *            机构ID
     * @see [类、类#方法、类#成员]
     */
    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }
    
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
     * 〈一句话功能简述〉角色code
     * 〈功能详细描述〉
     * 
     * @return roleCodes
     * @see [类、类#方法、类#成员]
     */
    public List<String> getRoleCodes() {
        return roleCodes;
    }
    
    /**
     * 〈一句话功能简述〉
     * 〈功能详细描述〉
     * 
     * @param roleCodes roleCodes
     * @see [类、类#方法、类#成员]
     */
    public void setRoleCodes(List<String> roleCodes) {
        this.roleCodes = roleCodes;
    }
    
    /**
     * 〈一句话功能简述〉角色code
     * 〈功能详细描述〉
     * 
     * @return idCard
     * @see [类、类#方法、类#成员]
     */
    public String getIdCard() {
        return idCard;
    }
    
    /**
     * 〈一句话功能简述〉
     * 〈功能详细描述〉
     * 
     * @param idCard idCard
     * @see [类、类#方法、类#成员]
     */
    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        addKVFieldStyles("name", getName()).addKVFieldStyles("deptId", deptId)
            .addKVFieldStyles("deptName", deptName);
        return super.toString();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected Object getBaseEntity() {
        return this;
    }
    
}
