/**
 * 文件名：EnpEveryTimes.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-8-9
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.vehiclemonitor.bean;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 * 
 * 〈一句话功能简述〉统计
 * 〈功能详细描述〉
 * 报警实体类请使用 {@link AlarmEntity}
 * 
 * @author guqh
 * @version [版本号, 2016-7-25]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class EnpEveryTimes implements Serializable {
    /** 序列号 */
    private static final long serialVersionUID = 851578333234534146L;
    
    /** id */
    private String id;
    
    /** 企业名称 */
    private String enterpriseName;
    
    /** 企业法人代码 */
    private String corporateNo;
    
    /** 港区分布 */
    private String portArea;
    
    /** 车载终端安装率 */
    private String installRate;
    
    /** 在线车辆 */
    private BigDecimal onlineCar;
    
    /** 离线车辆 */
    private BigDecimal offlineCar;
    
    /** 本月报警次数 */
    private BigDecimal curMonthAlarm;
    
    /** 本季度报警次数 */
    private BigDecimal curQuarterAlarm;
    
    /** 本年度报警次数 */
    private BigDecimal curYearAlarm;
    
    /** 本月运单数 */
    private BigDecimal curMonthWaybill;
    
    /** 本季度运单数 */
    private BigDecimal curQuarterWaybill;
    
    /** 本年度运单数 */
    private BigDecimal curYearWaybill;
    
    /**
     * 〈一句话功能简述〉id
     * 〈功能详细描述〉
     * 
     * @return id
     * @see [类、类#方法、类#成员]
     */
    public String getId() {
        return id;
    }
    
    /**
     * 〈一句话功能简述〉id
     * 〈功能详细描述〉
     * 
     * @param id id
     * @see [类、类#方法 、类#成员]
     */
    public void setId(String id) {
        this.id = id;
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
     * @see [类、类#方法、类#成员]
     */
    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }
    
    public String getCorporateNo()
    {
        return corporateNo;
    }

    public void setCorporateNo(String corporateNo)
    {
        this.corporateNo = corporateNo;
    }

    /**
     * 〈一句话功能简述〉港区分布
     * 〈功能详细描述〉
     * 
     * @return 港区分布
     * @see [类、类#方法、类#成员]
     */
    public String getPortArea() {
        return portArea;
    }
    
    /**
     * 〈一句话功能简述〉港区分布
     * 〈功能详细描述〉
     * 
     * @param portArea 港区分布
     * @see [类、类#方法、类#成员]
     */
    public void setPortArea(String portArea) {
        this.portArea = portArea;
    }
    
    public String getInstallRate()
    {
        return installRate;
    }

    public void setInstallRate(String installRate)
    {
        this.installRate = installRate;
    }

    /**
     * 〈一句话功能简述〉在线车辆
     * 〈功能详细描述〉
     * 
     * @return 在线车辆
     * @see [类、类#方法、类#成员]
     */
    public BigDecimal getOnlineCar() {
        return onlineCar;
    }
    
    /**
     * 〈一句话功能简述〉在线车辆
     * 〈功能详细描述〉
     * 
     * @param onlineCar 在线车辆
     * @see [类、类#方法、类#成员]
     */
    public void setOnlineCar(BigDecimal onlineCar) {
        this.onlineCar = onlineCar;
    }
    
    /**
     * 〈一句话功能简述〉离线车辆
     * 〈功能详细描述〉
     * 
     * @return 离线车辆
     * @see [类、类#方法、类#成员]
     */
    public BigDecimal getOfflineCar() {
        return offlineCar;
    }
    
    /**
     * 〈一句话功能简述〉离线车辆
     * 〈功能详细描述〉
     * 
     * @param offlineCar 离线车辆
     * @see [类、类#方法、类#成员]
     */
    public void setOfflineCar(BigDecimal offlineCar) {
        this.offlineCar = offlineCar;
    }
    
    /**
     * 〈一句话功能简述〉本月报警次数
     * 〈功能详细描述〉
     * 
     * @return 本月报警次数
     * @see [类、类#方法、类#成员]
     */
    public BigDecimal getCurMonthAlarm() {
        return curMonthAlarm;
    }
    
    /**
     * 〈一句话功能简述〉本月报警次数
     * 〈功能详细描述〉
     * 
     * @param curMonthAlarm 本月报警次数
     * @see [类、类#方法、类#成员]
     */
    public void setCurMonthAlarm(BigDecimal curMonthAlarm) {
        this.curMonthAlarm = curMonthAlarm;
    }
    
    /**
     * 〈一句话功能简述〉本季度报警次数
     * 〈功能详细描述〉
     * 
     * @return 本季度报警次数
     * @see [类、类#方法、类#成员]
     */
    public BigDecimal getCurQuarterAlarm() {
        return curQuarterAlarm;
    }
    
    /**
     * 〈一句话功能简述〉本季度报警次数
     * 〈功能详细描述〉
     * 
     * @param curQuarterAlarm 本季度报警次数
     * @see [类、类#方法、类#成员]
     */
    public void setCurQuarterAlarm(BigDecimal curQuarterAlarm) {
        this.curQuarterAlarm = curQuarterAlarm;
    }
    
    /**
     * 〈一句话功能简述〉本年度报警次数
     * 〈功能详细描述〉
     * 
     * @return 本年度报警次数
     * @see [类、类#方法、类#成员]
     */
    public BigDecimal getCurYearAlarm() {
        return curYearAlarm;
    }
    
    /**
     * 〈一句话功能简述〉本年度报警次数
     * 〈功能详细描述〉
     * 
     * @param curYearAlarm 本年度报警次数
     * @see [类、类#方法、类#成员]
     */
    public void setCurYearAlarm(BigDecimal curYearAlarm) {
        this.curYearAlarm = curYearAlarm;
    }
    
    /**
     * 〈一句话功能简述〉本月运单数
     * 〈功能详细描述〉
     * 
     * @return 本月运单数
     * @see [类、类#方法、类#成员]
     */
    public BigDecimal getCurMonthWaybill() {
        return curMonthWaybill;
    }
    
    /**
     * 〈一句话功能简述〉本月运单数
     * 〈功能详细描述〉
     * 
     * @param curMonthWaybill 本月运单数
     * @see [类、类#方法、类#成员]
     */
    public void setCurMonthWaybill(BigDecimal curMonthWaybill) {
        this.curMonthWaybill = curMonthWaybill;
    }
    
    /**
     * 〈一句话功能简述〉本季度运单数
     * 〈功能详细描述〉
     * 
     * @return 本季度运单数
     * @see [类、类#方法、类#成员]
     */
    public BigDecimal getCurQuarterWaybill() {
        return curQuarterWaybill;
    }
    
    /**
     * 〈一句话功能简述〉本季度运单数
     * 〈功能详细描述〉
     * 
     * @param curQuarterWaybill 本季度运单数
     * @see [类、类#方法、类#成员]
     */
    public void setCurQuarterWaybill(BigDecimal curQuarterWaybill) {
        this.curQuarterWaybill = curQuarterWaybill;
    }
    
    /**
     * 〈一句话功能简述〉本年度运单数
     * 〈功能详细描述〉
     * 
     * @return 本年度运单数
     * @see [类、类#方法、类#成员]
     */
    public BigDecimal getCurYearWaybill() {
        return curYearWaybill;
    }
    
    /**
     * 〈一句话功能简述〉本年度运单数
     * 〈功能详细描述〉
     * 
     * @param curYearWaybill 本年度运单数
     * @see [类、类#方法、类#成员]
     */
    public void setCurYearWaybill(BigDecimal curYearWaybill) {
        this.curYearWaybill = curYearWaybill;
    }
    
}
