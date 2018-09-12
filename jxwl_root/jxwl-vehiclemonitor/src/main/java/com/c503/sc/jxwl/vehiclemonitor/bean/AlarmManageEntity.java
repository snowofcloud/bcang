/**
 * 文件名：AlarmManageEntity.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-8-9
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.vehiclemonitor.bean;

import java.util.Date;

import com.c503.sc.base.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 
 * 〈一句话功能简述〉报警管理实体类
 * 〈功能详细描述〉
 *  报警实体类请使用 {@link AlarmEntity}
 * @author guqh
 * @version [版本号, 2016-7-25]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Deprecated
public class AlarmManageEntity extends BaseEntity {
    
    /** 序列号 */
    private static final long serialVersionUID = 851578707234534146L;
    
    /** 报警记录编号 */
    private String alarmNo;
    
    /** 终端ID */
    private String terminalId;
    
    /** 车牌号 */
    private String licencePlateNo;
    
    /** 运单编号 */
    private String waybillNo;
    
    /** 报警类型 */
    private String alarmType;
    
    /** 报警处理状态 */
    private String alarmDealStatus;
    
    /** 报警时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date alarmDate;
    
    /** 报警详情 */
    private String alarmDetails;
    
    /** 法人代码 */
    private String corporateNo;
    
    /** 企业名称 */
    private String enterpriseName;
    
    /** 报警登记情况 */
    private String alarmRegisterHandle;
    
    /**
     * 〈一句话功能简述〉获得报警记录编号
     * 〈功能详细描述〉
     * 
     * @return alarmNo
     * @see [类、类#方法、类#成员]
     */
    public String getAlarmNo() {
        return alarmNo;
    }
    
    /**
     * 〈一句话功能简述〉设置报警记录编号
     * 〈功能详细描述〉
     * 
     * @param alarmNo 报警记录编号
     * @see [类、类#方法、类#成员]
     */
    public void setAlarmNo(String alarmNo) {
        this.alarmNo = alarmNo;
    }
    
    /**
     * 〈一句话功能简述〉获得终端ID
     * 〈功能详细描述〉
     * 
     * @return terminalId
     * @see [类、类#方法、类#成员]
     */
    public String getTerminalId() {
        return terminalId;
    }
    
    /**
     * 〈一句话功能简述〉设置终端ID
     * 〈功能详细描述〉
     * 
     * @param terminalId 终端ID
     * @see [类、类#方法、类#成员]
     */
    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }
    
    /**
     * 〈一句话功能简述〉获得车牌号
     * 〈功能详细描述〉
     * 
     * @return licencePlateNo
     * @see [类、类#方法、类#成员]
     */
    public String getLicencePlateNo() {
        return licencePlateNo;
    }
    
    /**
     * 〈一句话功能简述〉设置车牌号
     * 〈功能详细描述〉
     * 
     * @param licencePlateNo 车牌号
     * @see [类、类#方法、类#成员]
     */
    public void setLicencePlateNo(String licencePlateNo) {
        this.licencePlateNo = licencePlateNo;
    }
    
    /**
     * 〈一句话功能简述〉获得运单编号
     * 〈功能详细描述〉
     * 
     * @return waybillNo
     * @see [类、类#方法、类#成员]
     */
    public String getWaybillNo() {
        return waybillNo;
    }
    
    /**
     * 〈一句话功能简述〉设置运单编号
     * 〈功能详细描述〉
     * 
     * @param waybillNo 运单编号
     * @see [类、类#方法、类#成员]
     */
    public void setWaybillNo(String waybillNo) {
        this.waybillNo = waybillNo;
    }
    
    /**
     * 〈一句话功能简述〉获得报警类型
     * 〈功能详细描述〉
     * 
     * @return alarmType
     * @see [类、类#方法、类#成员]
     */
    public String getAlarmType() {
        return alarmType;
    }
    
    /**
     * 〈一句话功能简述〉设置报警类型
     * 〈功能详细描述〉
     * 
     * @param alarmType 报警类型
     * @see [类、类#方法、类#成员]
     */
    public void setAlarmType(String alarmType) {
        this.alarmType = alarmType;
    }
    
    /**
     * 〈一句话功能简述〉获得报警处理状态
     * 〈功能详细描述〉
     * 
     * @return alarmDealStatus
     * @see [类、类#方法、类#成员]
     */
    public String getAlarmDealStatus() {
        return alarmDealStatus;
    }
    
    /**
     * 〈一句话功能简述〉设置报警处理状态
     * 〈功能详细描述〉
     * 
     * @param alarmDealStatus 报警处理状态
     * @see [类、类#方法、类#成员]
     */
    public void setAlarmDealStatus(String alarmDealStatus) {
        this.alarmDealStatus = alarmDealStatus;
    }
    
    /**
     * 〈一句话功能简述〉获得报警时间
     * 〈功能详细描述〉
     * 
     * @return alarmDate
     * @see [类、类#方法、类#成员]
     */
    public Date getAlarmDate() {
        return alarmDate;
    }
    
    /**
     * 〈一句话功能简述〉设置报警时间
     * 〈功能详细描述〉
     * 
     * @param alarmDate 报警时间
     * @see [类、类#方法、类#成员]
     */
    public void setAlarmDate(Date alarmDate) {
        this.alarmDate = alarmDate;
    }
    
    /**
     * 〈一句话功能简述〉获得报警详情
     * 〈功能详细描述〉
     * 
     * @return alarmDetails
     * @see [类、类#方法、类#成员]
     */
    public String getAlarmDetails() {
        return alarmDetails;
    }
    
    /**
     * 〈一句话功能简述〉设置报警详情
     * 〈功能详细描述〉
     * 
     * @param alarmDetails 报警详情
     * @see [类、类#方法、类#成员]
     */
    public void setAlarmDetails(String alarmDetails) {
        this.alarmDetails = alarmDetails;
    }
    
    /**
     * 〈一句话功能简述〉获得报警登记情况
     * 〈功能详细描述〉
     * 
     * @return alarmRegisterHandle
     * @see [类、类#方法、类#成员]
     */
    public String getAlarmRegisterHandle() {
        return alarmRegisterHandle;
    }
    
    /**
     * 〈一句话功能简述〉设置报警登记情况
     * 〈功能详细描述〉
     * 
     * @param alarmRegisterHandle 报警登记情况
     * @see [类、类#方法、类#成员]
     */
    public void setAlarmRegisterHandle(String alarmRegisterHandle) {
        this.alarmRegisterHandle = alarmRegisterHandle;
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
    
    /**
     *〈一句话功能简述〉获取企业名称
     * 〈功能详细描述〉
     * @return enterpriseName
     * @see  [类、类#方法、类#成员]
     */
    public String getEnterpriseName() {
        return enterpriseName;
    }

    /**
     *〈一句话功能简述〉设置企业名称
     * 〈功能详细描述〉
     * @param enterpriseName enterpriseName
     * @see  [类、类#方法、类#成员]
     */
    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }
    
    @Override
    protected Object getBaseEntity() {
        return this;
    }

}
