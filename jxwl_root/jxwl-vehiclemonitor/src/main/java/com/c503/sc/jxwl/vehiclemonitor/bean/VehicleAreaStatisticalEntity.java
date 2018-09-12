/**
 * 文件名：vehicleStatisticalEntity.java
 * 版权： 航天恒星科技有限公司
 * 描述：车辆区域分布情况统计
 * 修改时间：2017-7-25 
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.vehiclemonitor.bean;

import java.io.Serializable;
import java.math.BigDecimal;


public class VehicleAreaStatisticalEntity implements Serializable {
    /** 序列号 */
    private static final long serialVersionUID = 851578333234534146L;
    
    /** 区域id */
    private String areaId;
    
    /** 区域名称  */
    private String areaName;
    
    /** 区域内车辆总数 */
    private BigDecimal totalCar;

    public String getAreaId()
    {
        return areaId;
    }

    public void setAreaId(String areaId)
    {
        this.areaId = areaId;
    }

    public String getAreaName()
    {
        return areaName;
    }

    public void setAreaName(String areaName)
    {
        this.areaName = areaName;
    }

    public BigDecimal getTotalCar()
    {
        return totalCar;
    }

    public void setTotalCar(BigDecimal totalCar)
    {
        this.totalCar = totalCar;
    }
    
    
    
}
