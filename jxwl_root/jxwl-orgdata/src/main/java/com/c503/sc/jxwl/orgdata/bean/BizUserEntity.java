/**
 * 文件名：Snippet.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-8-11
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.orgdata.bean;

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
    
    /** password */
    private String password;
    
    /** salt */
    private String salt;
    
    /** sysId */
    private String sysId;
    
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
     * @return 角色code
     * @see [类、类#方法、类#成员]
     */
    public List<String> getRoleCodes() {
        return roleCodes;
    }
    
    /**
     * 〈一句话功能简述〉角色code
     * 〈功能详细描述〉
     * 
     * @param roleCodes roleCodes
     * @see [类、类#方法、类#成员]
     */
    public void setRoleCodes(List<String> roleCodes) {
        this.roleCodes = roleCodes;
    }
    
    /**
     * 〈一句话功能简述〉password
     * 〈功能详细描述〉
     * 
     * @return password
     * @see [类、类#方法、类#成员]
     */
    public String getPassword() {
        return password;
    }
    
    /**
     * 〈一句话功能简述〉password
     * 〈功能详细描述〉
     * 
     * @param password password
     * @see [类、类#方法、类#成员]
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
    /**
     * 〈一句话功能简述〉sysId
     * 〈功能详细描述〉
     * 
     * @return sysId
     * @see [类、类#方法、类#成员]
     */
    public String getSysId() {
        return sysId;
    }
    
    /**
     * 〈一句话功能简述〉sysId
     * 〈功能详细描述〉
     * 
     * @param sysId sysId
     * @see [类、类#方法、类#成员]
     */
    public void setSysId(String sysId) {
        this.sysId = sysId;
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
    
    /**
     * 〈一句话功能简述〉salt
     * 〈功能详细描述〉
     * 
     * @return salt
     * @see [类、类#方法、类#成员]
     */
    public String getSalt() {
        return salt;
    }
    
    /**
     * 〈一句话功能简述〉salt
     * 〈功能详细描述〉
     * 
     * @param salt salt
     * @see [类、类#方法、类#成员]
     */
    public void setSalt(String salt) {
        this.salt = salt;
    }
    
}
