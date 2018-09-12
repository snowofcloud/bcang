/**
 * 文件名：AlarmNumVo.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-7-29
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.zcpt.bean;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * 〈一句话功能简述〉拉入黑名单参数
 * 〈功能详细描述〉
 * 
 * @author guqh
 * @version [版本号, 2016-7-29]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class BlacklistArg {
    /** 主键ID */
    private String id;
    
    /** 企业报警 */
    private BigDecimal enterpriseAlarmNum;
    
    /** 车辆报警次数 */
    private BigDecimal vehicleAlarmNum;
    
    /** 驾驶员报警次数 */
    private BigDecimal driverAlarmNum;
    
    /** 更新时间 */
    private Date updateTime;
    
    /**
     * 〈一句话功能简述〉获得更新时间
     * 〈功能详细描述〉
     * 
     * @return Date 更新时间
     * @see [类、类#方法、类#成员]
     */
    public Date getUpdateTime() {
		return updateTime;
	}

	/**
     * 〈一句话功能简述〉设置更新时间
     * 〈功能详细描述〉
     * 
     * @param Date 更新时间
     * @see [类、类#方法、类#成员]
     */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
    /**
     * 〈一句话功能简述〉获得主键ID
     * 〈功能详细描述〉
     * 
     * @return id
     * @see [类、类#方法、类#成员]
     */
    public String getId() {
        return id;
    }
    
    /**
     * 〈一句话功能简述〉设置主键ID
     * 〈功能详细描述〉
     * 
     * @param id 主键ID
     * @see [类、类#方法、类#成员]
     */
    public void setId(String id) {
        this.id = id;
    }
    
    /**
     * 〈一句话功能简述〉获得企业报警
     * 〈功能详细描述〉
     * 
     * @return enterpriseAlarmNum
     * @see [类、类#方法、类#成员]
     */
    public BigDecimal getEnterpriseAlarmNum() {
        return enterpriseAlarmNum;
    }
    
    /**
     * 〈一句话功能简述〉设置企业报警
     * 〈功能详细描述〉
     * 
     * @param enterpriseAlarmNum 企业报警
     * @see [类、类#方法、类#成员]
     */
    public void setEnterpriseAlarmNum(BigDecimal enterpriseAlarmNum) {
        this.enterpriseAlarmNum = enterpriseAlarmNum;
    }
    
    /**
     * 〈一句话功能简述〉获得车辆报警次数
     * 〈功能详细描述〉
     * 
     * @return vehicleAlarmNum
     * @see [类、类#方法、类#成员]
     */
    public BigDecimal getVehicleAlarmNum() {
        return vehicleAlarmNum;
    }
    
    /**
     * 〈一句话功能简述〉设置车辆报警次数
     * 〈功能详细描述〉
     * 
     * @param vehicleAlarmNum 车辆报警次数
     * @see [类、类#方法、类#成员]
     */
    public void setVehicleAlarmNum(BigDecimal vehicleAlarmNum) {
        this.vehicleAlarmNum = vehicleAlarmNum;
    }
    
    /**
     * 〈一句话功能简述〉获得驾驶员报警次数
     * 〈功能详细描述〉
     * 
     * @return driverAlarmNum
     * @see [类、类#方法、类#成员]
     */
    public BigDecimal getDriverAlarmNum() {
        return driverAlarmNum;
    }
    
    /**
     * 〈一句话功能简述〉设置驾驶员报警次数
     * 〈功能详细描述〉
     * 
     * @param driverAlarmNum 驾驶员报警次数
     * @see [类、类#方法、类#成员]
     */
    public void setDriverAlarmNum(BigDecimal driverAlarmNum) {
        this.driverAlarmNum = driverAlarmNum;
    }
    
}
