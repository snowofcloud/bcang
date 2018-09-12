/**
 * 文件名：LocationData.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-8-4
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.common.bean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.c503.sc.base.entity.BaseEntity;

/**
 * 位置数据
 * 
 * @author huangtw
 * @version [版本号, 2016-8-4]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class LocationEntity extends BaseEntity {
    /** 序列号 */
    private static final long serialVersionUID = 2454064236822354373L;
    
    /** 数据来源，808协议还是第三方服务平台 */
    private String orgin;
    
    /** 终端手机号 */
    private String simNum;
    
    /** 终端上报位置的纬度（度） */
    private String latitude;
    
    /** 终端上报位置的经度（度） */
    private String longitude;
    
    /** 海拔高度，单位为米(m) */
    private String elevation;
    
    /** 终端上报位置时的速度（km/h） */
    private String speed;
    
    /** 终端上报位置时的方向 (度) */
    private String direction;
    
    /** 终端上报位置时的时间 */
    private Date gpstime;
    
    /** 终端类型（分为普通终端、高精度终端、北斗终端，分别用0、1、2表示） */
    private String protocoltype;
    
    /** 终端ID */
    private String terminalID;
    
    /** 车牌号 */
    private String carrierName;
    
    /** 坐标点 */
    private String[] point;
    
    /** 温度 */
    private String temperature;
    
    /** 压力 */
    private String pressure;
    
    /** 液位 */
    private String liquidLevel;
    
    /** 胎压 */
    private String tirePressure;
    
    /** 在线状态 */
    private String onlineStatus;
    
    /** 所属物流企业 */
    private String enterpriseName;
    
    /** 报警信息 */
    private List<AlarmEntity> alarms;
   
    /** 该车辆的未处理报警的数量 */
    private Integer alarmNum;
    
    /**
     * @return onlineStatus
     */
    public String getOnlineStatus() {
        return onlineStatus;
    }
    
    /**
     * 设置onlineStatus
     * 
     * @param onlineStatus
     *            onlineStatus
     */
    public void setOnlineStatus(String onlineStatus) {
        this.onlineStatus = onlineStatus;
    }
    
    /**
     * @return enterpriseName
     */
    public String getEnterpriseName() {
        return enterpriseName;
    }
    
    /**
     * 设置enterpriseName
     * 
     * @param enterpriseName
     *            enterpriseName
     */
    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }
    
    /**
     * @return the direction
     */
    public String getDirection() {
        return direction;
    }
    
    /**
     * 设置direction
     * 
     * @param direction
     *            direction
     */
    public void setDirection(String direction) {
        this.direction = direction;
    }
    
    /**
     * @return the gpstime
     */
    public Date getGpstime() {
        return gpstime;
    }
    
    /**
     * 设置gpstime
     * 
     * @param gpstime
     *            gpstime
     */
    public void setGpstime(Date gpstime) {
        this.gpstime = gpstime;
    }
    
    /**
     * @return the latitude
     */
    public String getLatitude() {
        return latitude;
    }
    
    /**
     * 设置latitude
     * 
     * @param latitude
     *            latitude
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
    
    /**
     * @return the longitude
     */
    public String getLongitude() {
        return longitude;
    }
    
    /**
     * 设置longitude
     * 
     * @param longitude
     *            longitude
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
    
    /**
     * @return the protocoltype
     */
    public String getProtocoltype() {
        return protocoltype;
    }
    
    /**
     * 设置protocoltype
     * 
     * @param protocoltype
     *            protocoltype
     */
    public void setProtocoltype(String protocoltype) {
        this.protocoltype = protocoltype;
    }
    
    /**
     * @return the speed
     */
    public String getSpeed() {
        return speed;
    }
    
    /**
     * 设置speed
     * 
     * @param speed
     *            speed
     */
    public void setSpeed(String speed) {
        this.speed = speed;
    }
    
    /**
     * @return the terminalID
     */
    public String getTerminalID() {
        return terminalID;
    }
    
    /**
     * 设置terminalID
     * 
     * @param terminalID
     *            terminalID
     */
    public void setTerminalID(String terminalID) {
        this.terminalID = terminalID;
    }
    
    /**
     * @return the carrierName
     */
    public String getCarrierName() {
        return carrierName;
    }
    
    /**
     * 设置carrierName
     * 
     * @param carrierName
     *            carrierName
     */
    public void setCarrierName(String carrierName) {
        this.carrierName = carrierName;
    }
    
    /**
     * @return the point
     */
    public String[] getPoint() {
        return point;
    }
    
    /**
     * 〈一句话功能简述〉设置point 〈功能详细描述〉
     * 
     * @see [类、类#方法、类#成员]
     */
    public void setPoint() {
        this.point = new String[] {longitude, latitude};
    }
    
    /**
     * @return the temperature
     */
    public String getTemperature() {
        return temperature;
    }
    
    /**
     * 设置temperature
     * 
     * @param temperature
     *            temperature
     */
    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }
    
    /**
     * @return the pressure
     */
    public String getPressure() {
        return pressure;
    }
    
    /**
     * 设置pressure
     * 
     * @param pressure
     *            pressure
     */
    public void setPressure(String pressure) {
        this.pressure = pressure;
    }
    
    /**
     * @return the liquidLevel
     */
    public String getLiquidLevel() {
        return liquidLevel;
    }
    
    /**
     * 设置liquidLevel
     * 
     * @param liquidLevel
     *            liquidLevel
     */
    public void setLiquidLevel(String liquidLevel) {
        this.liquidLevel = liquidLevel;
    }
    
    /**
     * @return the tirePressure
     */
    public String getTirePressure() {
        return tirePressure;
    }
    
    /**
     * 设置tirePressure
     * 
     * @param tirePressure
     *            tirePressure
     */
    public void setTirePressure(String tirePressure) {
        this.tirePressure = tirePressure;
    }
    
    /**
     * 设置point
     * 
     * @param point
     *            point
     */
    public void setPoint(String[] point) {
        this.point = point;
    }
    
    @Override
    protected Object getBaseEntity() {
        return this;
    }
    
    /**
     * 获取数据来源
     * 
     * @return 数据来源
     */
    public String getOrgin() {
        return orgin;
    }
    
    /**
     * 设置数据来源
     * 
     * @param orgin
     *            数据来源
     */
    public void setOrgin(String orgin) {
        this.orgin = orgin;
    }
    
    /**
     * 获取手机号
     * 
     * @return 手机号
     */
    public String getSimNum() {
        return simNum;
    }
    
    /**
     * 设置手机号
     * 
     * @param simNum
     *            手机号
     */
    public void setSimNum(String simNum) {
        this.simNum = simNum;
    }
    
    /**
     * 获取海拔
     * 
     * @return 海拔
     */
    public String getElevation() {
        return elevation;
    }
    
    /**
     * 设置海拔
     * 
     * @param elevation
     *            海拔
     */
    public void setElevation(String elevation) {
        this.elevation = elevation;
    }
    
    /**
     * 获取报警信息
     * 
     * @return 报警信息
     */
    public List<AlarmEntity> getAlarms() {
        return alarms;
    }
    
    /**
     * 设置报警信息
     * 
     * @param alarms
     *            报警信息
     */
    public void setAlarms(List<AlarmEntity> alarms) {
        this.alarms = alarms;
    }
    
    public void addAlarm(AlarmEntity alarm) {
        if(alarms==null){
        	alarms = new ArrayList<>();
        }
        alarms.add(alarm);
    }
    
    public Integer getAlarmNum()
    {
        return alarmNum;
    }

    public void setAlarmNum(Integer alarmNum)
    {
        this.alarmNum = alarmNum;
    }

    @Override
    public String toString() {
        return "LocationEntity [orgin=" + orgin + ", simNum=" + simNum
            + ", latitude=" + latitude + ", longitude=" + longitude
            + ", elevation=" + elevation + ", speed=" + speed + ", direction="
            + direction + ", gpstime=" + gpstime + ", protocoltype="
            + protocoltype + ", terminalID=" + terminalID + ", carrierName="
            + carrierName + ", point=" + Arrays.toString(point)
            + ", temperature=" + temperature + ", pressure=" + pressure
            + ", liquidLevel=" + liquidLevel + ", tirePressure=" + tirePressure
            + ", alarms=" + alarms + "]";
    }
    
    @Override
    public int hashCode() {
        return carrierName.hashCode();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        final LocationEntity entity = (LocationEntity) obj;
        if( entity !=null){
        	String carrierName = entity.getCarrierName();
            
            if (null != carrierName && carrierName.equals(this.carrierName)) {
                return true;
            }
        }
        
        return false;
    }
    
}
