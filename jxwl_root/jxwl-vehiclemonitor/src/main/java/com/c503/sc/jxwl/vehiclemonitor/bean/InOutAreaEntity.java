/**
 * 文件名：InOutAreaEntity.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2017-7-26 
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.vehiclemonitor.bean;

import java.util.Date;

import com.c503.sc.base.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;


public class InOutAreaEntity extends BaseEntity
{
    private static final long serialVersionUID = 1L;
    
    /**车牌号*/
    private String carrierName;
    
    /** 企业名称  */
    private String enpName;
    
    /**区域id*/
    private String areaId;
    
    /**区域名称*/
    private String areaName;
    
    /**区域类型*/
    private String areaType;
    
    /**进入时间*/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date inTime;
    
    /**离开时间*/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date outTime;
    
    /**记录时间*/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date logTime;
    
    /** 进出情况 */
    private String inOutStatus;

    public String getCarrierName()
    {
        return carrierName;
    }

    public void setCarrierName(String carrierName)
    {
        this.carrierName = carrierName;
    }

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

    public String getAreaType()
    {
        return areaType;
    }

    public void setAreaType(String areaType)
    {
        this.areaType = areaType;
    }

    public Date getInTime()
    {
        return inTime;
    }

    public void setInTime(Date inTime)
    {
        this.inTime = inTime;
    }

    public Date getOutTime()
    {
        return outTime;
    }

    public void setOutTime(Date outTime)
    {
        this.outTime = outTime;
    }
    
    public String getEnpName()
    {
        return enpName;
    }

    public void setEnpName(String enpName)
    {
        this.enpName = enpName;
    }

    public Date getLogTime()
    {
        return logTime;
    }

    public void setLogTime(Date logTime)
    {
        this.logTime = logTime;
    }

    public String getInOutStatus()
    {
        return inOutStatus;
    }

    public void setInOutStatus(String inOutStatus)
    {
        this.inOutStatus = inOutStatus;
    }

    @Override
    protected Object getBaseEntity() {
        return this;
    }
}
