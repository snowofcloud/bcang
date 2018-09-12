/**
 * 文件名：BlacklistManageEntity.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-7-26
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.zcpt.bean;

import java.util.Date;

import com.c503.sc.base.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 
 * 〈一句话功能简述〉黑名单管理实体类
 * 〈功能详细描述〉
 * 
 * @author guqh
 * @version [版本号, 2016-7-26]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class Blacklist extends BaseEntity {
    
    /** 序列号 */
    private static final long serialVersionUID = -5420683333958123635L;
    
    /** 物流企业法人代码 */
    private String corporateNo;
    
    /** 驾驶员 */
    private String driver;
    
    /** 车辆 */
    private String vehicle;
    
    /** 对象类型 */
    private String objectType;
    
    /** 拉黑类型 */
    private String blacklistType;
    
    /** 拉黑原因 */
    private String blacklistReason;
    
    /** 拉黑时间 */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date blacklistDate;
    
    /** 操作人 */
    private String operatePerson;
    
    /** 企业名称 */
    private String enterpriseName;
    
    /** driverId */
    private String driverId;
    
    /**
     * 〈一句话功能简述〉获得driverId
     * 〈功能详细描述〉
     * 
     * @return driverId
     * @see [类、类#方法、类#成员]
     */
    public String getDriverId() {
        return driverId;
    }
    
    /**
     * 〈一句话功能简述〉设置driverId
     * 〈功能详细描述〉
     * 
     * @param driverId driverId
     * @see [类、类#方法、类#成员]
     */
    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }
    
    /**
     * 〈一句话功能简述〉获得enterpriseName
     * 〈功能详细描述〉
     * 
     * @return enterpriseName
     * @see [类、类#方法、类#成员]
     */
    public String getEnterpriseName() {
        return enterpriseName;
    }
    
    /**
     * 〈一句话功能简述〉设置enterpriseName
     * 〈功能详细描述〉
     * 
     * @param enterpriseName enterpriseName
     * @see [类、类#方法、类#成员]
     */
    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }
    
    /**
     * 〈一句话功能简述〉获得operatePerson
     * 〈功能详细描述〉
     * 
     * @return operatePerson
     * @see [类、类#方法、类#成员]
     */
    public String getOperatePerson() {
        return operatePerson;
    }
    
    /**
     * 〈一句话功能简述〉设置operatePerson
     * 〈功能详细描述〉
     * 
     * @param operatePerson operatePerson
     * @see [类、类#方法、类#成员]
     */
    public void setOperatePerson(String operatePerson) {
        this.operatePerson = operatePerson;
    }
    
    /**
     * 〈一句话功能简述〉物流企业法人代码
     * 〈功能详细描述〉
     * 
     * @return 物流企业法人代码
     * @see [类、类#方法、类#成员]
     */
    public String getCorporateNo() {
        return corporateNo;
    }
    
    /**
     * 〈一句话功能简述〉物流企业法人代码
     * 〈功能详细描述〉
     * 
     * @param corporateNo 物流企业法人代码
     * @see [类、类#方法、类#成员]
     */
    public void setCorporateNo(String corporateNo) {
        this.corporateNo = corporateNo;
    }
    
    /**
     * 〈一句话功能简述〉获得驾驶员
     * 〈功能详细描述〉
     * 
     * @return driver
     * @see [类、类#方法、类#成员]
     */
    public String getDriver() {
        return driver;
    }
    
    /**
     * 〈一句话功能简述〉设置驾驶员
     * 〈功能详细描述〉
     * 
     * @param driver 驾驶员
     * @see [类、类#方法、类#成员]
     */
    public void setDriver(String driver) {
        this.driver = driver;
    }
    
    /**
     * 〈一句话功能简述〉获得车辆
     * 〈功能详细描述〉
     * 
     * @return vehicle
     * @see [类、类#方法、类#成员]
     */
    public String getVehicle() {
        return vehicle;
    }
    
    /**
     * 〈一句话功能简述〉设置车辆
     * 〈功能详细描述〉
     * 
     * @param vehicle 车辆
     * @see [类、类#方法、类#成员]
     */
    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }
    
    /**
     * 〈一句话功能简述〉获得对象类型
     * 〈功能详细描述〉
     * 
     * @return objectType
     * @see [类、类#方法、类#成员]
     */
    public String getObjectType() {
        return objectType;
    }
    
    /**
     * 〈一句话功能简述〉设置对象类型
     * 〈功能详细描述〉
     * 
     * @param objectType 对象类型
     * @see [类、类#方法、类#成员]
     */
    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }
    
    /**
     * 〈一句话功能简述〉获得拉黑类型
     * 〈功能详细描述〉
     * 
     * @return blacklistType
     * @see [类、类#方法、类#成员]
     */
    public String getBlacklistType() {
        return blacklistType;
    }
    
    /**
     * 〈一句话功能简述〉设置拉黑类型
     * 〈功能详细描述〉
     * 
     * @param blacklistType 拉黑类型
     * @see [类、类#方法、类#成员]
     */
    public void setBlacklistType(String blacklistType) {
        this.blacklistType = blacklistType;
    }
    
    /**
     * 〈一句话功能简述〉获得拉黑原因
     * 〈功能详细描述〉
     * 
     * @return blacklistReason
     * @see [类、类#方法、类#成员]
     */
    public String getBlacklistReason() {
        return blacklistReason;
    }
    
    /**
     * 〈一句话功能简述〉设置拉黑原因
     * 〈功能详细描述〉
     * 
     * @param blacklistReason 拉黑原因
     * @see [类、类#方法、类#成员]
     */
    public void setBlacklistReason(String blacklistReason) {
        this.blacklistReason = blacklistReason;
    }
    
    /**
     * 〈一句话功能简述〉获得拉黑时间
     * 〈功能详细描述〉
     * 
     * @return blacklistDate
     * @see [类、类#方法、类#成员]
     */
    public Date getBlacklistDate() {
        return blacklistDate;
    }
    
    /**
     * 〈一句话功能简述〉设置拉黑时间
     * 〈功能详细描述〉
     * 
     * @param blacklistDate 拉黑时间
     * @see [类、类#方法、类#成员]
     */
    public void setBlacklistDate(Date blacklistDate) {
        this.blacklistDate = blacklistDate;
    }
    
    @Override
    protected Object getBaseEntity() {
        return this;
    }
    
}
