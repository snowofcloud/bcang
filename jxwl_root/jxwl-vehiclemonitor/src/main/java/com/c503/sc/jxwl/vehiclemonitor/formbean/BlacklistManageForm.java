/**
 * 文件名：BlacklistManageForm.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-7-26
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.vehiclemonitor.formbean;

import java.util.Date;

import javax.validation.constraints.Pattern;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.c503.sc.base.formbean.PageForm;
import com.c503.sc.base.validation.Save;
import com.c503.sc.base.validation.Update;
import com.c503.sc.jxwl.common.constant.RegexpContant;

/**
 * 
 * 〈一句话功能简述〉 黑名单管理FormBean
 * 〈功能详细描述〉
 * 
 * @author guqh
 * @version [版本号, 2016-7-26]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class BlacklistManageForm extends PageForm {
    /** 序列号 */
    private static final long serialVersionUID = 572906951914304578L;
    
    /** 物流企业 */
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
    @Pattern(regexp = RegexpContant.ANY_1000_CHAR_MUST, message = "{blacklistManageForm.blacklistReason}", groups = {
        Save.class, Update.class})
    private String blacklistReason;
    
    /** 拉黑时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd", iso = ISO.DATE)
    private Date blacklistDate;
    
    /** 操作人 */
    private String operatePerson;
    
    /** 企业名称 */
    @Pattern(regexp = RegexpContant.ANY_200_CHAR_MUST, message = "{blacklistManageForm.enterpriseName}", groups = {
        Save.class, Update.class})
    private String enterpriseName;
    
    /** 开始时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd", iso = ISO.DATE)
    private Date blacklistDateStart;
    
    /** 结束时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd", iso = ISO.DATE)
    private Date blacklistDateEnd;
    
    
    /** 驾驶员、车辆拉黑的法人代码 */
    private String blackListno;
    
    
    /**
     *〈一句话功能简述〉获取 驾驶员、车辆拉黑的法人代码
     * 〈功能详细描述〉
     * @return blackListno
     * @see  [类、类#方法、类#成员]
     */
    public String getBlackListno() {
		return blackListno;
	}
    
    /**
     *〈一句话功能简述〉设置 驾驶员、车辆拉黑的法人代码
     * 〈功能详细描述〉
     * @param blackListno blackListno
     * @see  [类、类#方法、类#成员]
     */
	public void setBlackListno(String blackListno) {
		this.blackListno = blackListno;
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
        if (null != enterpriseName) {
            this.enterpriseName = enterpriseName.trim();
        }
    }
    
    /**
     * 〈一句话功能简述〉获得blacklistDateStart
     * 〈功能详细描述〉
     * 
     * @return blacklistDateStart
     * @see [类、类#方法、类#成员]
     */
    public Date getBlacklistDateStart() {
        return blacklistDateStart;
    }
    
    /**
     * 〈一句话功能简述〉设置blacklistDateStart
     * 〈功能详细描述〉
     * 
     * @param blacklistDateStart blacklistDateStart
     * @see [类、类#方法、类#成员]
     */
    public void setBlacklistDateStart(Date blacklistDateStart) {
        this.blacklistDateStart = blacklistDateStart;
    }
    
    /**
     * 〈一句话功能简述〉获得blacklistDateEnd
     * 〈功能详细描述〉
     * 
     * @return blacklistDateEnd
     * @see [类、类#方法、类#成员]
     */
    public Date getBlacklistDateEnd() {
        return blacklistDateEnd;
    }
    
    /**
     * 〈一句话功能简述〉设置blacklistDateEnd
     * 〈功能详细描述〉
     * 
     * @param blacklistDateEnd blacklistDateEnd
     * @see [类、类#方法、类#成员]
     */
    public void setBlacklistDateEnd(Date blacklistDateEnd) {
        this.blacklistDateEnd = blacklistDateEnd;
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
        if (null != driver) {
            this.driver = driver.trim();
        }
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
        if (null != vehicle) {
            this.vehicle = vehicle.trim();
        }
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

    /**
     *〈一句话功能简述〉获取法人代码
     * 〈功能详细描述〉
     * @return corporateNo
     * @see  [类、类#方法、类#成员]
     */
    public String getCorporateNo() {
        return corporateNo;
    }

    /**
     *〈一句话功能简述〉设置法人代码
     * 〈功能详细描述〉
     * @param corporateNo corporateNo
     * @see  [类、类#方法、类#成员]
     */
    public void setCorporateNo(String corporateNo) {
        this.corporateNo = corporateNo;
    }
}
