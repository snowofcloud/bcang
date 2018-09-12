/**
 * 文件名：AlarmRecord.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-7-20
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.common.bean;

import java.util.Date;

import com.c503.sc.base.entity.BaseEntity;
import com.c503.sc.utils.basetools.C503StringUtils;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 
 * 报警对象
 * 
 * @author huangtw
 * @version [版本号, 2016-7-25]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class AlarmEntity extends BaseEntity {
    
    /** 序列号 */
    private static final long serialVersionUID = 851578707234534146L;
    
    /** id */
    private String id;

    /** 报警记录编号 */
    private String alarmNo;
    
    /** 终端ID */
    private String terminalId;
    
    /** 车牌号 */
    private String carrierName;
    
    /** 运单编号 */
    private String waybillNo;
    
    /** 运单状态 */
    private String checkStatus;
    
    /** 报警类型 */
    private String alarmType;
    
    /** 报警处理状态 */
    private String alarmDealStatus;
    
    /** 报警产生(如果产生在某个限制区域内)的限制区域的区域id*/
    private String limitAreaId;
    
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
    
    /** 终端手机号 */
    private String simNum;
    
    /** 驾驶员id */
    private String driverId;
    
    /** 驾驶员 */
    private String driver;
    
    /** 驾驶员手机号 */
    private String telephone;
    
    /** 用于websocket推送是的标志 */
    private String alarm = "1";
    
    /**
     * 〈一句话功能简述〉无参构造器
     * 〈功能详细描述〉
     * 
     * @see [类、类#方法、类#成员]
     */
    public AlarmEntity() {
    }
    
    /**
     * 〈一句话功能简述〉有参构造器
     * 〈功能详细描述〉
     * 
     * @param terminalId terminalId
     * @param carrierName carrierName
     * @param alarmType alarmType
     * @param alarmDate alarmDate
     * @param alarmDetails alarmDetails
     * @param status status
     * @param corporateNo corporateNo
     * @see [类、类#方法、类#成员]
     */
    public AlarmEntity(String terminalId, String carrierName, String alarmType,
        Date alarmDate, String alarmDetails, String status, String corporateNo) {
        super.setId(C503StringUtils.createUUID());
        this.terminalId = terminalId;
        this.carrierName = carrierName;
        this.alarmType = alarmType;
        this.alarmDate = alarmDate;
        this.alarmDetails = alarmDetails;
        this.alarmDealStatus = status;
        this.corporateNo = corporateNo;
    }
    /**
     * 〈一句话功能简述〉获得报警记录编号
     * 〈功能详细描述〉
     * 
     * @return id
     * @see [类、类#方法、类#成员]
     */
    public String getId()
    {
        return id;
    }
    /**
     * 〈一句话功能简述〉设置id
     * 〈功能详细描述〉
     * 
     *  @param id id
     * @see [类、类#方法、类#成员]
     */
    public void setId(String id)
    {
        this.id = id;
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
     * @return this
     * @see [类、类#方法、类#成员]
     */
    public AlarmEntity setAlarmNo(String alarmNo) {
        this.alarmNo = alarmNo;
        return this;
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
     * @return AlarmEntity
     * @see [类、类#方法、类#成员]
     */
    public AlarmEntity setTerminalId(String terminalId) {
        this.terminalId = terminalId;
        return this;
    }
    
    /**
     * 〈一句话功能简述〉运单状态
     * 〈功能详细描述〉
     * 
     * @return 运单状态
     * @see [类、类#方法、类#成员]
     */
    public String getCheckStatus() {
        return checkStatus;
    }
    
    /**
     * 〈一句话功能简述〉运单状态
     * 〈功能详细描述〉
     * 
     * @param checkstatus 运单状态
     * @return this
     * @see [类、类#方法、类#成员]
     */
    public AlarmEntity setCheckStatus(String checkstatus) {
        this.checkStatus = checkstatus;
        return this;
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
     * @return this
     * @see [类、类#方法、类#成员]
     */
    public AlarmEntity setWaybillNo(String waybillNo) {
        this.waybillNo = waybillNo;
        return this;
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
     * @return this
     * @see [类、类#方法、类#成员]
     */
    public AlarmEntity setAlarmType(String alarmType) {
        this.alarmType = alarmType;
        return this;
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
     * @return this
     * @see [类、类#方法、类#成员]
     */
    public AlarmEntity setAlarmDealStatus(String alarmDealStatus) {
        this.alarmDealStatus = alarmDealStatus;
        return this;
    }
    
    public String getLimitAreaId()
    {
        return limitAreaId;
    }

    public void setLimitAreaId(String limitAreaId)
    {
        this.limitAreaId = limitAreaId;
    }

    /**
     * 〈一句话功能简述〉获得报警时间
     * 〈功能详细描述〉
     * 
     * @return alarmDate
     * @see [类、类#方法、类#成员]
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date getAlarmDate() {
        return alarmDate;
    }
    
    /**
     * 〈一句话功能简述〉设置报警时间
     * 〈功能详细描述〉
     * 
     * @param alarmDate 报警时间
     * @return AlarmEntity
     * @see [类、类#方法、类#成员]
     */
    public AlarmEntity setAlarmDate(Date alarmDate) {
        this.alarmDate = alarmDate;
        return this;
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
     * @return AlarmEntity
     * @see [类、类#方法、类#成员]
     */
    public AlarmEntity setAlarmDetails(String alarmDetails) {
        this.alarmDetails = alarmDetails;
        return this;
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
     * @return this
     * @see [类、类#方法、类#成员]
     */
    public AlarmEntity setAlarmRegisterHandle(String alarmRegisterHandle) {
        this.alarmRegisterHandle = alarmRegisterHandle;
        return this;
    }
    
    /**
     * 〈一句话功能简述〉法人代码
     * 〈功能详细描述〉
     * 
     * @return 法人代码
     * @see [类、类#方法、类#成员]
     */
    public String getCorporateNo() {
        return corporateNo;
    }
    
    /**
     * 〈一句话功能简述〉法人代码
     * 〈功能详细描述〉
     * 
     * @param corporateNo 法人代码
     * @return this
     * @see [类、类#方法、类#成员]
     */
    public AlarmEntity setCorporateNo(String corporateNo) {
        this.corporateNo = corporateNo;
        return this;
    }
    
    /**
     * 〈一句话功能简述〉车牌号
     * 〈功能详细描述〉
     * 
     * @return 车牌号
     * @see [类、类#方法、类#成员]
     */
    public String getCarrierName() {
        return carrierName;
    }
    
    /**
     * 〈一句话功能简述〉车牌号
     * 〈功能详细描述〉
     * 
     * @param carrierName 车牌号
     * @return this
     * @see [类、类#方法、类#成员]
     */
    public AlarmEntity setCarrierName(String carrierName) {
        this.carrierName = carrierName;
        return this;
    }
    
    /**
     * 〈一句话功能简述〉企业名称
     * 〈功能详细描述〉
     * 
     * @return 企业名称
     * @see [类、类#方法、类#成员]
     */
    public String getEnterpriseName() {
        return enterpriseName;
    }
    
    /**
     * 〈一句话功能简述〉企业名称
     * 〈功能详细描述〉
     * 
     * @param enterpriseName 企业名称
     * @return this
     * @see [类、类#方法、类#成员]
     */
    public AlarmEntity setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
        return this;
    }
    
    /**
     * 获取sim卡号
     * 
     * @return sim卡号
     */
    public String getSimNum() {
        return simNum;
    }
    
    /**
     * 设置sim卡号
     * 
     * @param simNum sim卡号
     * @return this
     */
    public AlarmEntity setSimNum(String simNum) {
        this.simNum = simNum;
        return this;
    }
    
    /**
     * 获取驾驶员ID
     * 
     * @return 驾驶员ID
     */
    public String getDriverId() {
        return driverId;
    }
    
    /**
     * 设置驾驶员ID
     * 
     * @param driverId 驾驶员ID
     * @return this
     */
    public AlarmEntity setDriverId(String driverId) {
        this.driverId = driverId;
        return this;
    }
    
    /**
     * 〈一句话功能简述〉用于websocket推送是的标志
     * 〈功能详细描述〉
     * 
     * @return 用于websocket推送是的标志
     * @see [类、类#方法、类#成员]
     */
    public String getAlarm() {
        return alarm;
    }
    
    /**
     * 〈一句话功能简述〉用于websocket推送是的标志
     * 〈功能详细描述〉
     * 
     * @param alarm 用于websocket推送是的标志
     * @return this
     * @see [类、类#方法、类#成员]
     */
    public AlarmEntity setAlarm(String alarm) {
        this.alarm = alarm;
        return this;
    }
    
    /**
     * 〈一句话功能简述〉驾驶员
     * 〈功能详细描述〉
     * 
     * @return 驾驶员
     * @see [类、类#方法、类#成员]
     */
    public String getDriver() {
        return driver;
    }
    
    /**
     * 〈一句话功能简述〉驾驶员
     * 〈功能详细描述〉
     * 
     * @param driver 驾驶员
     * @return this
     * @see [类、类#方法、类#成员]
     */
    public AlarmEntity setDriver(String driver) {
        this.driver = driver;
        return this;
    }
    
    /**
     * 〈一句话功能简述〉
     * 〈功能详细描述〉
     * 
     * @return telephone
     * @see [类、类#方法、类#成员]
     */
    public String getTelephone() {
        return telephone;
    }
    
    /**
     * 〈一句话功能简述〉
     * 〈功能详细描述〉
     * 
     * @param telephone AlarmEntity
     * @return this
     * @see [类、类#方法、类#成员]
     */
    public AlarmEntity setTelephone(String telephone) {
        this.telephone = telephone;
        return this;
    }
    
    @Override
    protected Object getBaseEntity() {
        return this;
    }
    
    @Override
    public String toString() {
        return "AlarmEntity [alarmNo=" + alarmNo + ", terminalId=" + terminalId
            + ", waybillNo=" + waybillNo + ", alarmType=" + alarmType
            + ", alarmDealStatus=" + alarmDealStatus + ", alarmDate="
            + alarmDate + ", alarmDetails=" + alarmDetails
            + ", alarmRegisterHandle=" + alarmRegisterHandle + ", corporateNo="
            + corporateNo + ", carrierName=" + carrierName
            + ", enterpriseName=" + enterpriseName + ", simNum=" + simNum
            + ", driverId=" + driverId + "]";
    }
}
