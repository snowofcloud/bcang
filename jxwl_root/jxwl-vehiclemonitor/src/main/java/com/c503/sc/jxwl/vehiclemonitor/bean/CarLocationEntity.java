package com.c503.sc.jxwl.vehiclemonitor.bean;

import java.math.BigDecimal;
import java.util.Date;

import com.c503.sc.base.entity.BaseEntity;

/**
 * 〈一句话功能简述〉车辆位置信息实体
 * 〈功能详细描述〉
 * @author    xiaoqx
 * @version   [版本号, 2016-9-8]
 * @see       [相关类/方法]
 * @since     [产品/模块版本] 
 */
public class CarLocationEntity extends BaseEntity{
    /** 序列号 */
    private static final long serialVersionUID = 1L;

    /** 车牌号 */
    private String carrierName;

    /** 坐标来源类型 */
    private String pointSourceType;

    /** 状态*/
    private String onlineStatus;

    /** 时间 */
    private Date pointTime;

    /** 经度 */
    private BigDecimal longitude;

    /** 纬度 */
    private BigDecimal latitude;

    /** 海拔 */
    private BigDecimal elevation;

    /** 速度 */
    private BigDecimal speed;

    /** 方向 */    
    private BigDecimal direction;

    /** 温度 */
    private BigDecimal temperature;

    /** 压力 */
    private BigDecimal pressure;

    /** 液位 */
    private BigDecimal liquidLevel;

    /** 胎压 */
    private BigDecimal tyrePressure;

    
    /**
     *〈一句话功能简述〉获取车牌号
     * 〈功能详细描述〉
     * @return carrierName
     * @see  [类、类#方法、类#成员]
     */
    public String getCarrierName() {
        return carrierName;
    }

    /**
     *〈一句话功能简述〉设置车牌号
     * 〈功能详细描述〉
     * @param carrierName carrierName
     * @see  [类、类#方法、类#成员]
     */
    public void setCarrierName(String carrierName) {
        this.carrierName = carrierName == null ? null : carrierName.trim();
    }

    /**
     *〈一句话功能简述〉获取坐标来源类型
     * 〈功能详细描述〉
     * @return pointSourceType
     * @see  [类、类#方法、类#成员]
     */
    public String getPointSourceType() {
        return pointSourceType;
    }

    /**
     *〈一句话功能简述〉设置坐标来源类型
     * 〈功能详细描述〉
     * @param pointSourceType pointSourceType
     * @see  [类、类#方法、类#成员]
     */
    public void setPointSourceType(String pointSourceType) {
        this.pointSourceType = pointSourceType == null ? null : pointSourceType.trim();
    }

    /**
     *〈一句话功能简述〉获取状态
     * 〈功能详细描述〉
     * @return onlineStatus
     * @see  [类、类#方法、类#成员]
     */
    public String getOnlineStatus() {
        return onlineStatus;
    }

    /**
     *〈一句话功能简述〉设置状态
     * 〈功能详细描述〉
     * @param onlineStatus onlineStatus
     * @see  [类、类#方法、类#成员]
     */
    public void setOnlineStatus(String onlineStatus) {
        this.onlineStatus = onlineStatus == null ? null : onlineStatus.trim();
    }

    /**
     *〈一句话功能简述〉获取时间
     * 〈功能详细描述〉
     * @return pointTime
     * @see  [类、类#方法、类#成员]
     */
    public Date getPointTime() {
        return pointTime;
    }

    /**
     *〈一句话功能简述〉设置时间
     * 〈功能详细描述〉
     * @param pointTime pointTime
     * @see  [类、类#方法、类#成员]
     */
    public void setPointTime(Date pointTime) {
        this.pointTime = pointTime;
    }

    /**
     *〈一句话功能简述〉获取经度
     * 〈功能详细描述〉
     * @return longitude
     * @see  [类、类#方法、类#成员]
     */
    public BigDecimal getLongitude() {
        return longitude;
    }

    /**
     *〈一句话功能简述〉设置经度
     * 〈功能详细描述〉
     * @param longitude longitude
     * @see  [类、类#方法、类#成员]
     */
    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    /**
     *〈一句话功能简述〉获取纬度
     * 〈功能详细描述〉
     * @return latitude
     * @see  [类、类#方法、类#成员]
     */
    public BigDecimal getLatitude() {
        return latitude;
    }

    /**
     *〈一句话功能简述〉设置纬度
     * 〈功能详细描述〉
     * @param latitude latitude
     * @see  [类、类#方法、类#成员]
     */
    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    /**
     *〈一句话功能简述〉获取海拔
     * 〈功能详细描述〉
     * @return elevation
     * @see  [类、类#方法、类#成员]
     */
    public BigDecimal getElevation() {
        return elevation;
    }

    /**
     *〈一句话功能简述〉设置海拔
     * 〈功能详细描述〉
     * @param elevation elevation
     * @see  [类、类#方法、类#成员]
     */
    public void setElevation(BigDecimal elevation) {
        this.elevation = elevation;
    }

    /**
     *〈一句话功能简述〉获取速度
     * 〈功能详细描述〉
     * @return speed
     * @see  [类、类#方法、类#成员]
     */
    public BigDecimal getSpeed() {
        return speed;
    }

    /**
     *〈一句话功能简述〉设置速度
     * 〈功能详细描述〉
     * @param speed speed
     * @see  [类、类#方法、类#成员]
     */
    public void setSpeed(BigDecimal speed) {
        this.speed = speed;
    }

    /**
     *〈一句话功能简述〉获取方向
     * 〈功能详细描述〉 
     * @return direction
     * @see  [类、类#方法、类#成员]
     */
    public BigDecimal getDirection() {
        return direction;
    }

    /**
     *〈一句话功能简述〉设置方向
     * 〈功能详细描述〉
     * @param direction direction
     * @see  [类、类#方法、类#成员]
     */
    public void setDirection(BigDecimal direction) {
        this.direction = direction;
    }

    /**
     *〈一句话功能简述〉获取温度
     * 〈功能详细描述〉
     * @return temperature
     * @see  [类、类#方法、类#成员]
     */
    public BigDecimal getTemperature() {
        return temperature;
    }

    /**
     *〈一句话功能简述〉设置温度
     * 〈功能详细描述〉
     * @param temperature temperature
     * @see  [类、类#方法、类#成员]
     */
    public void setTemperature(BigDecimal temperature) {
        this.temperature = temperature;
    }

    /**
     *〈一句话功能简述〉获取压力
     * 〈功能详细描述〉
     * @return pressure
     * @see  [类、类#方法、类#成员]
     */
    public BigDecimal getPressure() {
        return pressure;
    }

    /**
     *〈一句话功能简述〉设置压力
     * 〈功能详细描述〉
     * @param pressure pressure
     * @see  [类、类#方法、类#成员]
     */
    public void setPressure(BigDecimal pressure) {
        this.pressure = pressure;
    }

    /**
     *〈一句话功能简述〉获取液压
     * 〈功能详细描述〉
     * @return liquidLevel
     * @see  [类、类#方法、类#成员]
     */
    public BigDecimal getLiquidLevel() {
        return liquidLevel;
    }

    /**
     *〈一句话功能简述〉设置液压
     * 〈功能详细描述〉
     * @param liquidLevel liquidLevel
     * @see  [类、类#方法、类#成员]
     */
    public void setLiquidLevel(BigDecimal liquidLevel) {
        this.liquidLevel = liquidLevel;
    }

    /**
     *〈一句话功能简述〉获取胎压
     * 〈功能详细描述〉 
     * @return  tyrePressure
     * @see  [类、类#方法、类#成员]
     */
    public BigDecimal getTyrePressure() {
        return tyrePressure;
    }

    /**
     *〈一句话功能简述〉设置胎压
     * 〈功能详细描述〉
     * @param tyrePressure tyrePressure
     * @see  [类、类#方法、类#成员]
     */
    public void setTyrePressure(BigDecimal tyrePressure) {
        this.tyrePressure = tyrePressure;
    }

    @Override
    protected Object getBaseEntity() {
        return this;
    }
}
