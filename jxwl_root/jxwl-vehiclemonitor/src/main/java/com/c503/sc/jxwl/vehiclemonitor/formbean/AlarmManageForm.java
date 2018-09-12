/**
 * 文件名：AlarmManageForm.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-7-26
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.vehiclemonitor.formbean;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.c503.sc.base.formbean.PageForm;

/**
 * 
 * 〈一句话功能简述〉报警管理FormBean
 * 〈功能详细描述〉
 * 
 * @author guqh
 * @version [版本号, 2016-7-25]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class AlarmManageForm extends PageForm {
    
    /** 序列号 */
    private static final long serialVersionUID = -6535997667594474835L;
    
    /** 报警记录id */
    private String alarmId;
    
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
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss", iso = ISO.DATE)
    private Date alarmDate;
    
    /** 报警起始 */
    @DateTimeFormat(pattern = "yyyy-MM-dd", iso = ISO.DATE)
    private Date starAlarmDate;
    
    /** 报警起始 */
    @DateTimeFormat(pattern = "yyyy-MM-dd", iso = ISO.DATE)
    private Date endAlarmDate;
    
    /** 报警详情 */
    private String alarmDetails;
    
    /** 报警登记情况 */
    private String alarmRegisterHandle;
    
    /** 法人代码 */
    private String corporateNo;
    
    /** 企业名称 */
    private String enterpriseName;
    
    
    
    /**
     *〈一句话功能简述〉获取企业名称
     * 〈功能详细描述〉
     * @return alarmId
     * @see  [类、类#方法、类#成员]
     */
    public String getAlarmId() {
        return alarmId;
    }
    /**
     *〈一句话功能简述〉设置企业名称
     * 〈功能详细描述〉
     * @param alarmId alarmId
     * @see  [类、类#方法、类#成员]
     */
    public void setAlarmId(String alarmId) {
        this.alarmId = alarmId;
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
     * 
     * 〈一句话功能简述〉获取起始报警时间
     * 〈功能详细描述〉
     * 
     * @return starAlarmDate 报警时间
     * @see [类、类#方法、类#成员]
     */
    public Date getStarAlarmDate() {
        return starAlarmDate;
    }
    
    /**
     * 
     * 〈一句话功能简述〉设置搜索报警时间
     * 〈功能详细描述〉
     * 
     * @param starAlarmDate 报警时间
     * @see [类、类#方法、类#成员]
     */
    public void setStarAlarmDate(Date starAlarmDate) {
        this.starAlarmDate = starAlarmDate;
    }
    
    /**
     * 
     * 〈一句话功能简述〉获取搜索的结束报警时间
     * 〈功能详细描述〉
     * 
     * @return endAlarmDate 结束报警时间
     * @see [类、类#方法、类#成员]
     */
    public Date getEndAlarmDate() {
        return endAlarmDate;
    }
    
    /**
     * 
     * 〈一句话功能简述〉设置搜索的报警结束时间
     * 〈功能详细描述〉
     * 
     * @param endAlarmDate 结束时间
     * @see [类、类#方法、类#成员]
     */
    public void setEndAlarmDate(Date endAlarmDate) {
        this.endAlarmDate = endAlarmDate;
    }
    
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
}
