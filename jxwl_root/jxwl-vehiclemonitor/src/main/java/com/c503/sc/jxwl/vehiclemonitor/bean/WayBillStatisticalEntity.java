/**
 * 文件名：WayBillStatisticalEntity.java
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
 * 
 * @author mjw
 * @version [版本号, 2016-7-25]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class WayBillStatisticalEntity implements Serializable {
    /** 序列号 */
    private static final long serialVersionUID = 851578333234534146L;
    
    /** id */
    private String id;
    
    /** 车牌号 */
    private String licencePlateNo;
    
    /** 本周运单数量 */
    private BigDecimal wayBillWeek;
    
    /** 本月运单数量 */
    private BigDecimal wayBillMonth;
    
    /** 本年运单数量 */
    private BigDecimal wayBillYear;
    
    /** 本周危险品种类数量 */
    private BigDecimal dangerousTypeWeek;
    
    /** 本月危险品种类数量 */
    private BigDecimal dangerousTypeMonth;
    
    /** 本年危险品种类数量 */
    private BigDecimal dangerousTypeYear;
    
    /** 本周危险品总吨数 */
    private BigDecimal dangerousWeightWeek;
    
    /** 本月危险品总吨数 */
    private BigDecimal dangerousWeightMonth;
    
    /** 本年危险品总吨数 */
    private BigDecimal dangerousWeightYear;
    
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
     * 〈一句话功能简述〉车牌号
     * 〈功能详细描述〉
     * 
     * @return 车牌号
     * @see [类、类#方法、类#成员]
     */
    public String getLicencePlateNo() {
        return licencePlateNo;
    }
    
    /**
     * 〈一句话功能简述〉车牌号
     * 〈功能详细描述〉
     * 
     * @param licencePlateNo 车牌号
     * @see [类、类#方法、类#成员]
     */
    public void setLicencePlateNo(String licencePlateNo) {
        this.licencePlateNo = licencePlateNo;
    }
    
    /**
     * 〈一句话功能简述〉本周运单数量
     * 〈功能详细描述〉
     * 
     * @return 本周运单数量
     * @see [类、类#方法、类#成员]
     */
    public BigDecimal getWayBillWeek() {
        return wayBillWeek;
    }
    
    /**
     * 〈一句话功能简述〉本周运单数量
     * 〈功能详细描述〉
     * 
     * @param wayBillWeek 本周运单数量
     * @see [类、类#方法、类#成员]
     */
    public void setWayBillWeek(BigDecimal wayBillWeek) {
        this.wayBillWeek = wayBillWeek;
    }
    
    /**
     * 〈一句话功能简述〉本月运单数量
     * 〈功能详细描述〉
     * 
     * @return 本月运单数量
     * @see [类、类#方法、类#成员]
     */
    public BigDecimal getWayBillMonth() {
        return wayBillMonth;
    }
    
    /**
     * 〈一句话功能简述〉本月运单数量
     * 〈功能详细描述〉
     * 
     * @param wayBillMonth 本月运单数量
     * @see [类、类#方法、类#成员]
     */
    public void setWayBillMonth(BigDecimal wayBillMonth) {
        this.wayBillMonth = wayBillMonth;
    }
    
    /**
     * 〈一句话功能简述〉本年运单数量
     * 〈功能详细描述〉
     * 
     * @return 本年运单数量
     * @see [类、类#方法、类#成员]
     */
    public BigDecimal getWayBillYear() {
        return wayBillYear;
    }
    
    /**
     * 〈一句话功能简述〉本年运单数量
     * 〈功能详细描述〉
     * 
     * @param wayBillYear 本年运单数量
     * @see [类、类#方法、类#成员]
     */
    public void setWayBillYear(BigDecimal wayBillYear) {
        this.wayBillYear = wayBillYear;
    }
    
    /**
     * 〈一句话功能简述〉本周危险品种类数量
     * 〈功能详细描述〉
     * 
     * @return 本周危险品种类数量
     * @see [类、类#方法、类#成员]
     */
    public BigDecimal getDangerousTypeWeek() {
        return dangerousTypeWeek;
    }
    
    /**
     * 〈一句话功能简述〉本周危险品种类数量
     * 〈功能详细描述〉
     * 
     * @param dangerousTypeWeek 本周危险品种类数量
     * @see [类、类#方法、类#成员]
     */
    public void setDangerousTypeWeek(BigDecimal dangerousTypeWeek) {
        this.dangerousTypeWeek = dangerousTypeWeek;
    }
    
    /**
     * 〈一句话功能简述〉本月危险品种类数量
     * 〈功能详细描述〉
     * 
     * @return 本月危险品种类数量
     * @see [类、类#方法、类#成员]
     */
    public BigDecimal getDangerousTypeMonth() {
        return dangerousTypeMonth;
    }
    
    /**
     * 〈一句话功能简述〉本月危险品种类数量
     * 〈功能详细描述〉
     * 
     * @param dangerousTypeMonth 本月危险品种类数量
     * @see [类、类#方法、类#成员]
     */
    public void setDangerousTypeMonth(BigDecimal dangerousTypeMonth) {
        this.dangerousTypeMonth = dangerousTypeMonth;
    }
    
    /**
     * 〈一句话功能简述〉本年危险品种类数量
     * 〈功能详细描述〉
     * 
     * @return 本年危险品种类数量
     * @see [类、类#方法、类#成员]
     */
    public BigDecimal getDangerousTypeYear() {
        return dangerousTypeYear;
    }
    
    /**
     * 〈一句话功能简述〉本年危险品种类数量
     * 〈功能详细描述〉
     * 
     * @param curYearAlarm 本年危险品种类数量
     * @see [类、类#方法、类#成员]
     */
    public void setDangerousTypeYear(BigDecimal dangerousTypeYear) {
        this.dangerousTypeYear = dangerousTypeYear;
    }
    
    /**
     * 〈一句话功能简述〉本周危险品总吨数
     * 〈功能详细描述〉
     * 
     * @return 本周危险品总吨数
     * @see [类、类#方法、类#成员]
     */
    public BigDecimal getDangerousWeightWeek() {
        return dangerousWeightWeek;
    }
    
    /**
     * 〈一句话功能简述〉本周危险品总吨数
     * 〈功能详细描述〉
     * 
     * @param dangerousWeightWeek 本周危险品总吨数
     * @see [类、类#方法、类#成员]
     */
    public void setDangerousWeightWeek(BigDecimal dangerousWeightWeek) {
        this.dangerousWeightWeek = dangerousWeightWeek;
    }
    
    /**
     * 〈一句话功能简述〉本月危险品总吨数
     * 〈功能详细描述〉
     * 
     * @return 本月危险品总吨数
     * @see [类、类#方法、类#成员]
     */
    public BigDecimal getDangerousWeightMonth() {
        return dangerousWeightMonth;
    }
    
    /**
     * 〈一句话功能简述〉本月危险品总吨数
     * 〈功能详细描述〉
     * 
     * @param curQuarterWaybill 本月危险品总吨数
     * @see [类、类#方法、类#成员]
     */
    public void setDangerousWeightMonth(BigDecimal dangerousWeightMonth) {
        this.dangerousWeightMonth = dangerousWeightMonth;
    }
    
    /**
     * 〈一句话功能简述〉本年危险品总吨数 
     * 〈功能详细描述〉
     * 
     * @return 本年危险品总吨数 
     * @see [类、类#方法、类#成员]
     */
    public BigDecimal getDangerousWeightYear() {
        return dangerousWeightYear;
    }
    
    /**
     * 〈一句话功能简述〉本年危险品总吨数 
     * 〈功能详细描述〉
     * 
     * @param dangerousWeightYear 本年危险品总吨数 
     * @see [类、类#方法、类#成员]
     */
    public void setDangerousWeightYear(BigDecimal dangerousWeightYear) {
        this.dangerousWeightYear = dangerousWeightYear;
    }
    
}
