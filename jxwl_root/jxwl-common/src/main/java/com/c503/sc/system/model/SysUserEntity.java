/*
 * 文件名：SysUserEntity.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2015年11月3日
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.system.model;

import java.util.Date;

import com.c503.sc.base.entity.common.UserEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 〈一句话功能简述〉系统管理用户实体类
 * 〈功能详细描述〉
 * 
 * @author luocb
 * @version [版本号, 2015年11月3日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class SysUserEntity extends UserEntity {
    /** 序列号 */
    private static final long serialVersionUID = 1L;
    
    /** 用户所属系统 */
    private String sysId;
    
    /** 掩码 加密盐 */
    private String salt;
    
    /** 密码 */
    private String password;
    
    /** 账号 */
    //private String account;
    
    /** 用户简称 */
    private String alias;
    
    /** 用户身份证号 */
    private String idcard;
    
    /** 性别 */
    private String sex;
    
    /** 用户状态 */
    private String status;
    
    /** 通讯地址 */
    private String commAddress;
    
    /** 账号有效期起始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date beginTime;
    
    /** 账号有效结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date endTime;
    
    /** 启用时间 */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date startTime;
    
    /** 停用时间 */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date stopTime;
    
    /** 最后登录时间 */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date lastloginTime;
    
    /** 登录次数 */
    private String loginNum;
    
    /** 备注 */
    private String remark;
    
    /**
     * 〈一句话功能简述〉所属系统
     * 〈功能详细描述〉
     * 
     * @return 所属系统
     * @see [类、类#方法、类#成员]
     */
    public String getSysId() {
        return sysId;
    }
    
    /**
     * 〈一句话功能简述〉所属系统
     * 〈功能详细描述〉
     * 
     * @param sysId 所属系统
     * @see [类、类#方法、类#成员]
     */
    public void setSysId(String sysId) {
        this.sysId = sysId;
    }
    
    /**
     * 〈一句话功能简述〉用户简称
     * 〈功能详细描述〉
     * 
     * @return 用户简称
     * @see [类、类#方法、类#成员]
     */
    public String getAlias() {
        return alias;
    }
    
    /**
     * 〈一句话功能简述〉用户简称
     * 〈功能详细描述〉
     * 
     * @param alias 用户简称
     * @see [类、类#方法、类#成员]
     */
    public void setAlias(String alias) {
        this.alias = alias;
    }
    
    /**
     * 〈一句话功能简述〉身份证
     * 〈功能详细描述〉
     * 
     * @return 身份证
     * @see [类、类#方法、类#成员]
     */
    public String getIdcard() {
        return idcard;
    }
    
    /**
     * 〈一句话功能简述〉 身份证
     * 〈功能详细描述〉
     * 
     * @param idcard 身份证
     * @see [类、类#方法、类#成员]
     */
    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }
    
    /**
     * 〈一句话功能简述〉性别
     * 〈功能详细描述〉
     * 
     * @return 性别
     * @see [类、类#方法、类#成员]
     */
    public String getSex() {
        return sex;
    }
    
    /**
     * 〈一句话功能简述〉 性别
     * 〈功能详细描述〉
     * 
     * @param sex 性别
     * @see [类、类#方法、类#成员]
     */
    public void setSex(String sex) {
        this.sex = sex;
    }
    

    
    
    /**
     * 〈一句话功能简述〉联系地址
     * 〈功能详细描述〉
     * 
     * @return 联系地址
     * @see [类、类#方法、类#成员]
     */
    public String getCommAddress() {
        return commAddress;
    }
    
    /**
     * 〈一句话功能简述〉 联系地址
     * 〈功能详细描述〉
     * 
     * @param commAddress 联系地址
     * @see [类、类#方法、类#成员]
     */
    public void setCommAddress(String commAddress) {
        this.commAddress = commAddress;
    }
    
    /**
     * 〈一句话功能简述〉 有效起始时间
     * 〈功能详细描述〉
     * 
     * @return 有效起始时间
     * @see [类、类#方法、类#成员]
     */
    public Date getBeginTime() {
        return beginTime;
    }
    
    /**
     * 〈一句话功能简述〉 有效起始时间
     * 〈功能详细描述〉
     * 
     * @param beginTime 有效起始时间
     * @see [类、类#方法、类#成员]
     */
    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }
    
    /**
     * 〈一句话功能简述〉有效结束时间
     * 〈功能详细描述〉
     * 
     * @return 有效结束时间
     * @see [类、类#方法、类#成员]
     */
    public Date getEndTime() {
        return endTime;
    }
    
    /**
     * 〈一句话功能简述〉有效结束时间
     * 〈功能详细描述〉
     * 
     * @param endTime 有效结束时间
     * @see [类、类#方法、类#成员]
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
    
    /**
     * 〈一句话功能简述〉启用时间
     * 〈功能详细描述〉
     * 
     * @return 启用时间
     * @see [类、类#方法、类#成员]
     */
    public Date getStartTime() {
        return startTime;
    }
    
    /**
     * 〈一句话功能简述〉 启用时间
     * 〈功能详细描述〉
     * 
     * @param startTime 启用时间
     * @see [类、类#方法、类#成员]
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
    
    /**
     * 〈一句话功能简述〉停用时间
     * 〈功能详细描述〉
     * 
     * @return 停用时间
     * @see [类、类#方法、类#成员]
     */
    public Date getStopTime() {
        return stopTime;
    }
    
    /**
     * 〈一句话功能简述〉停用时间
     * 〈功能详细描述〉
     * 
     * @param stopTime 停用时间
     * @see [类、类#方法、类#成员]
     */
    public void setStopTime(Date stopTime) {
        this.stopTime = stopTime;
    }
    
    /**
     * 〈一句话功能简述〉最后登录时间
     * 〈功能详细描述〉
     * 
     * @return 最后登录时间
     * @see [类、类#方法、类#成员]
     */
    public Date getLastloginTime() {
        return lastloginTime;
    }
    
    /**
     * 〈一句话功能简述〉最后登录时间
     * 〈功能详细描述〉
     * 
     * @param lastloginTime 最后登录时间
     * @see [类、类#方法、类#成员]
     */
    public void setLastloginTime(Date lastloginTime) {
        this.lastloginTime = lastloginTime;
    }
    
    /**
     * 〈一句话功能简述〉登录次数
     * 〈功能详细描述〉
     * 
     * @return 登录次数
     * @see [类、类#方法、类#成员]
     */
    public String getLoginNum() {
        return loginNum;
    }
    
    /**
     * 〈一句话功能简述〉登录次数
     * 〈功能详细描述〉
     * 
     * @param loginNum 登录次数
     * @see [类、类#方法、类#成员]
     */
    public void setLoginNum(String loginNum) {
        this.loginNum = loginNum;
    }
    
    /**
     * 〈一句话功能简述〉备注
     * 〈功能详细描述〉
     * 
     * @return 备注
     * @see [类、类#方法、类#成员]
     */
    public String getRemark() {
        return remark;
    }
    
    /**
     * 〈一句话功能简述〉备注
     * 〈功能详细描述〉
     * 
     * @param remark 备注
     * @see [类、类#方法、类#成员]
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    /**
     * 
     * 〈一句话功能简述〉获取加密盐（掩码） 〈功能详细描述〉
     * 
     * @return 加密盐（掩码）
     * @see [类、类#方法、类#成员]
     */
    public String getSalt() {
        return salt;
    }
    
    /**
     * 
     * 〈一句话功能简述〉设置用户加密掩码 〈功能详细描述〉
     * 
     * @param salt
     *            用户加密掩码
     * @see [类、类#方法、类#成员]
     */
    public void setSalt(String salt) {
        this.salt = salt == null ? null : salt.trim();
    }
    
    /**
     * 
     * 〈一句话功能简述〉获取用户密码 〈功能详细描述〉
     * 
     * @return 用户密码
     * @see [类、类#方法、类#成员]
     */
    public String getPassword() {
        return password;
    }
    
    /**
     * 
     * 〈一句话功能简述〉设置设置用户密码 〈功能详细描述〉
     * 
     * @param password
     *            用户密码
     * @see [类、类#方法、类#成员]
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }
    
    /**
     * 
     * 〈一句话功能简述〉获取用户账号 〈功能详细描述〉
     * 
     * @return 用户账号
     * @see [类、类#方法、类#成员]
     */
//    public String getAccount() {
//        return account;
//    }
    
    /**
     * 
     * 〈一句话功能简述〉设置用户账号 〈功能详细描述〉
     * 
     * @param account
     *            用户账号
     * @see [类、类#方法、类#成员]
     */
//    public void setAccount(String account) {
//        this.account = account == null ? null : account.trim();
//    }
    
    
    /**
     * 
     * 〈一句话功能简述〉获取用户状态 〈功能详细描述〉
     * 
     * @return 用户状态
     * @see [类、类#方法、类#成员]
     */
    public String getStatus() {
        return status;
    }
    
    /**
     * 
     * 〈一句话功能简述〉 〈功能详细描述〉
     * 
     * @param status
     *            用户状态
     * @see [类、类#方法、类#成员]
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        addKVFieldStyles("sysId", sysId).addKVFieldStyles("alias", alias)
            .addKVFieldStyles("idcard", idcard)
            .addKVFieldStyles("sex", sex)
            .addKVFieldStyles("commAddress", commAddress)
            .addKVFieldStyles("beginTime", beginTime)
            .addKVFieldStyles("endTime", endTime)
            .addKVFieldStyles("startTime", startTime)
            .addKVFieldStyles("stopTime", stopTime)
            .addKVFieldStyles("lastloginTime", lastloginTime)
            .addKVFieldStyles("loginNum", loginNum)
            .addKVFieldStyles("remark", remark)
            .addKVFieldStyles("salt", salt)
            .addKVFieldStyles("password", password)
            //.addKVFieldStyles("account", account)
            .addKVFieldStyles("status", status);
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
