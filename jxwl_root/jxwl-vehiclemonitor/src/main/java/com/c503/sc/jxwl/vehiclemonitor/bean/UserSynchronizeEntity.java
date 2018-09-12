/**
 * 文件名：UserSynchronizeEntity
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.vehiclemonitor.bean;

import java.util.Date;

import com.c503.sc.base.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 
 * 〈一句话功能简述〉用户同步
 * 〈功能详细描述〉
 * 
 * @author guqh
 * @version [版本号, 2017-5-17]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class UserSynchronizeEntity extends BaseEntity {
    /** 序列化号 */
    private static final long serialVersionUID = -6980700885603357155L;
    
    /** 用户账号 */
    private String account;
    
    /** 用户名称 */
    private String name;
    
    /** 联系方式 */
    private String mobile;
    
    /** 密码 */
    private String password;
    
    /** 机构ID */
    private String organId;
    
    /** 性别 */
    private String sex;
    
    /** 标识符 */
    private String flag;
    
    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
    
    /**
     * 〈一句话功能简述〉获得updateTime
     * 〈功能详细描述〉
     * 
     * @return updateTime
     * @see [类、类#方法、类#成员]
     */
    public Date getUpdateTime() {
        return updateTime;
    }
    
    /**
     * 〈一句话功能简述〉设置updateTime
     * 〈功能详细描述〉
     * 
     * @param updateTime updateTime
     * @see [类、类#方法、类#成员]
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    
    /**
     * 〈一句话功能简述〉获得account
     * 〈功能详细描述〉
     * 
     * @return account
     * @see [类、类#方法、类#成员]
     */
    public String getAccount() {
        return account;
    }
    
    /**
     * 〈一句话功能简述〉设置account
     * 〈功能详细描述〉
     * 
     * @param account account
     * @see [类、类#方法、类#成员]
     */
    public void setAccount(String account) {
        this.account = account;
    }
    
    /**
     * 〈一句话功能简述〉获得name
     * 〈功能详细描述〉
     * 
     * @return name
     * @see [类、类#方法、类#成员]
     */
    public String getName() {
        return name;
    }
    
    /**
     * 〈一句话功能简述〉设置name
     * 〈功能详细描述〉
     * 
     * @param name name
     * @see [类、类#方法、类#成员]
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * 〈一句话功能简述〉获得mobile
     * 〈功能详细描述〉
     * 
     * @return mobile
     * @see [类、类#方法、类#成员]
     */
    public String getMobile() {
        return mobile;
    }
    
    /**
     * 〈一句话功能简述〉设置mobile
     * 〈功能详细描述〉
     * 
     * @param mobile mobile
     * @see [类、类#方法、类#成员]
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    
    /**
     * 〈一句话功能简述〉获得password
     * 〈功能详细描述〉
     * 
     * @return password
     * @see [类、类#方法、类#成员]
     */
    public String getPassword() {
        return password;
    }
    
    /**
     * 〈一句话功能简述〉设置password
     * 〈功能详细描述〉
     * 
     * @param password password
     * @see [类、类#方法、类#成员]
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
    /**
     * 〈一句话功能简述〉获得organId
     * 〈功能详细描述〉
     * 
     * @return organId
     * @see [类、类#方法、类#成员]
     */
    public String getOrganId() {
        return organId;
    }
    
    /**
     * 〈一句话功能简述〉设置organId
     * 〈功能详细描述〉
     * 
     * @param organId organId
     * @see [类、类#方法、类#成员]
     */
    public void setOrganId(String organId) {
        this.organId = organId;
    }
    
    /**
     * 〈一句话功能简述〉获得sex
     * 〈功能详细描述〉
     * 
     * @return sex
     * @see [类、类#方法、类#成员]
     */
    public String getSex() {
        return sex;
    }
    
    /**
     * 〈一句话功能简述〉设置sex
     * 〈功能详细描述〉
     * 
     * @param sex sex
     * @see [类、类#方法、类#成员]
     */
    public void setSex(String sex) {
        this.sex = sex;
    }
    
    /**
     * 〈一句话功能简述〉获得flag
     * 〈功能详细描述〉
     * 
     * @return flag
     * @see [类、类#方法、类#成员]
     */
    public String getFlag() {
        return flag;
    }
    
    /**
     * 〈一句话功能简述〉设置flag
     * 〈功能详细描述〉
     * 
     * @param flag flag
     * @see [类、类#方法、类#成员]
     */
    public void setFlag(String flag) {
        this.flag = flag;
    }
    
    @Override
    protected Object getBaseEntity() {
        return this;
    }
}
