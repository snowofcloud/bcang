package com.c503.sc.jxwl.transacation.formbean;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.c503.sc.base.entity.BaseEntity;
import com.c503.sc.jxwl.transacation.bean.WayBillGoodsEntity;

/**
 * 
 * 〈一句话功能简述〉运单信息实体类
 * 〈功能详细描述〉
 * 
 * @author wl
 * @version [版本号, 2016-08-30]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class WayBillTempForm extends BaseEntity {
    
    /** 序列号 */
    private static final long serialVersionUID = 2244230738127436665L;
    
    /** 驾驶员ID */
    private String driverId;
    
    /** 模板名称 */
    private String tempname;
    
    /** 押运员ID */
    private String escortId;
    
    /** 化工企业ID */
    private String chemicalId;
    
    /** 物流企业ID */
    private String logisticsId;
    
    /** 车辆ID */
    private String vehicleId;
    
    /** 托运单号 */
    private String checkno;
    
    /** 客户单号 */
    private String consumerno;
    
    /** 起点 */
    private String startpoint;
    
    /** 到点 */
    private String endpoint;
    
    /** 发货单位 */
    private String deliveryunit;
    
    /** 发货单位联系人 */
    private String deliveryunitperson;
    
    /** 运单状态 */
    private String checkstatus;
    
    /** 发货地址 */
    private String deliveryunitaddress;
    
    /** 发货时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd", iso = ISO.DATE)
    private Date deliveryunittime;
    
    /** 收货单位 */
    private String achieveuint;
    
    /** 收货单位联系人 */
    private String achieveperson;
    
    /** 收货单位联系电话 */
    private String achievephone;
    
    /** 发货单位联系电话 */
    private String deliveryunitphone;
    
    /** 收货地址 */
    private String achieveaddress;
    
    /** 卸货时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd", iso = ISO.DATE)
    private Date achievetime;
    
    /** 驾驶员 */
    private String driver;
    
    /** 驾驶员电话 */
    private String driverphone;
    
    /** 身份证号（驾驶员） */
    private String driverid;
    
    /** 从业证号（驾驶员） */
    private String driverno;
    
    /** 押运员 */
    private String escort;
    
    /** 电话（押运员） */
    private String escortphone;
    
    /** 挂车车牌 */
    private String handcarno;
    
    /** 押运员证号 */
    private String escortno;
    
    /** 身份证号（押运员） */
    private String escortid;
    
    /** 车牌号 */
    private String carno;
    
    /** 备注 */
    private String remark;
    
    /** 货物信息集合 */
    private List<WayBillGoodsEntity> listBillGoods;
    
    /** 运输公司名称 */
    private String wlEnterpriseName;
    
    /** 车辆状态 */
    private String onlineStatus;
    
    /** 创建时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd", iso = ISO.DATE)
    private Date createTime;
    
    /** 起点经度 */
    private String startLng;
    
    /** 起点纬度 */
    private String startLat;
    
    /** 到点经度 */
    private String endLng;
    
    /** 到点纬度 */
    private String endLat;
    
    /**
     * 〈一句话功能简述〉startLng
     * 〈功能详细描述〉
     * 
     * @return startLng
     * @see [类、类#方法、类#成员]
     */
    public String getStartLng() {
        return startLng;
    }
    /**
     * 〈一句话功能简述〉startLng
     * 〈功能详细描述〉
     * 
     * @param startLng startLng
     * @see [类、类#方法、类#成员]
     */
    public void setStartLng(String startLng) {
        this.startLng = startLng;
    }
    
    /**
     * 〈一句话功能简述〉startLat
     * 〈功能详细描述〉
     * 
     * @return startLat
     * @see [类、类#方法、类#成员]
     */
    public String getStartLat() {
        return startLat;
    }
    /**
     * 〈一句话功能简述〉startLat
     * 〈功能详细描述〉
     * 
     * @param startLat startLat
     * @see [类、类#方法、类#成员]
     */
    public void setStartLat(String startLat) {
        this.startLat = startLat;
    }
    /**
     * 〈一句话功能简述〉endLng
     * 〈功能详细描述〉
     * 
     * @return endLng
     * @see [类、类#方法、类#成员]
     */
    public String getEndLng() {
        return endLng;
    }
    /**
     * 〈一句话功能简述〉endLng
     * 〈功能详细描述〉
     * 
     * @param endLng endLng
     * @see [类、类#方法、类#成员]
     */
    public void setEndLng(String endLng) {
        this.endLng = endLng;
    }
    /**
     * 〈一句话功能简述〉endLat
     * 〈功能详细描述〉
     * 
     * @return endLat
     * @see [类、类#方法、类#成员]
     */
    public String getEndLat() {
        return endLat;
    }
    /**
     * 〈一句话功能简述〉endLat
     * 〈功能详细描述〉
     * 
     * @param endLat endLat
     * @see [类、类#方法、类#成员]
     */
    public void setEndLat(String endLat) {
        this.endLat = endLat;
    }
    
    /**
     * 〈一句话功能简述〉获取驾驶员ID
     * 〈功能详细描述〉
     * 
     * @return 驾驶员ID
     * @see [类、类#方法、类#成员]
     */
    public String getDriverId() {
        return driverId;
    }
    
    /**
     * 〈一句话功能简述〉设置驾驶员ID
     * 〈功能详细描述〉
     * 
     * @param driverId 驾驶员ID
     * @see [类、类#方法、类#成员]
     */
    public void setDriverId(String driverId) {
        this.driverId = driverId == null ? null : driverId.trim();
    }
    
    /**
     * 〈一句话功能简述〉模板名称
     * 〈功能详细描述〉
     * 
     * @return 驾驶员ID
     * @see [类、类#方法、类#成员]
     */
    public String getTempname() {
        return tempname;
    }
    
    /**
     * 〈一句话功能简述〉模板名称
     * 〈功能详细描述〉
     * 
     * @param driverId 驾驶员ID
     * @see [类、类#方法、类#成员]
     */
    public void setTempname(String driverId) {
        this.tempname = driverId == null ? null : driverId.trim();
    }
    
    /**
     * 〈一句话功能简述〉 获取押运员ID
     * 〈功能详细描述〉
     * 
     * @return 押运员ID
     * @see [类、类#方法、类#成员]
     */
    public String getEscortId() {
        return escortId;
    }
    
    /**
     * 〈一句话功能简述〉 设置押运员ID
     * 〈功能详细描述〉
     * 
     * @param escortId 押运员ID
     * @see [类、类#方法、类#成员]
     */
    public void setEscortId(String escortId) {
        this.escortId = escortId == null ? null : escortId.trim();
    }
    
    /**
     * 〈一句话功能简述〉 获取化工企业ID
     * 〈功能详细描述〉
     * 
     * @return 化工企业ID
     * @see [类、类#方法、类#成员]
     */
    public String getChemicalId() {
        return chemicalId;
    }
    
    /**
     * 〈一句话功能简述〉 设置化工企业ID
     * 〈功能详细描述〉
     * 
     * @param chemicalId 化工企业ID
     * @see [类、类#方法、类#成员]
     */
    public void setChemicalId(String chemicalId) {
        this.chemicalId = chemicalId == null ? null : chemicalId.trim();
    }
    
    /**
     * 〈一句话功能简述〉获取物流企业ID
     * 〈功能详细描述〉
     * 
     * @return 物流企业ID
     * @see [类、类#方法、类#成员]
     */
    public String getLogisticsId() {
        return logisticsId;
    }
    
    /**
     * 〈一句话功能简述〉 设置物流企业ID
     * 〈功能详细描述〉
     * 
     * @param logisticsId 物流企业ID
     * @see [类、类#方法、类#成员]
     */
    public void setLogisticsId(String logisticsId) {
        this.logisticsId = logisticsId == null ? null : logisticsId.trim();
    }
    
    /**
     * 〈一句话功能简述〉获取车辆ID
     * 〈功能详细描述〉
     * 
     * @return 车辆ID
     * @see [类、类#方法、类#成员]
     */
    public String getVehicleId() {
        return vehicleId;
    }
    
    /**
     * 〈一句话功能简述〉 设置车辆ID
     * 〈功能详细描述〉
     * 
     * @param vehicleId 车辆ID
     * @see [类、类#方法、类#成员]
     */
    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId == null ? null : vehicleId.trim();
    }
    
    /**
     * 〈一句话功能简述〉 获取托运单号
     * 〈功能详细描述〉
     * 
     * @return 托运单号
     * @see [类、类#方法、类#成员]
     */
    public String getCheckno() {
        return checkno;
    }
    
    /**
     * 〈一句话功能简述〉 设置托运单号
     * 〈功能详细描述〉
     * 
     * @param checkno 托运单号
     * @see [类、类#方法、类#成员]
     */
    public void setCheckno(String checkno) {
        this.checkno = checkno == null ? null : checkno.trim();
    }
    
    /**
     * 〈一句话功能简述〉 获取客户单号
     * 〈功能详细描述〉
     * 
     * @return 客户单号
     * @see [类、类#方法、类#成员]
     */
    public String getConsumerno() {
        return consumerno;
    }
    
    /**
     * 〈一句话功能简述〉 设置客户单号
     * 〈功能详细描述〉
     * 
     * @param consumerno 客户单号
     * @see [类、类#方法、类#成员]
     */
    public void setConsumerno(String consumerno) {
        this.consumerno = consumerno == null ? null : consumerno.trim();
    }
    
    /**
     * 〈一句话功能简述〉 获取起点
     * 〈功能详细描述〉
     * 
     * @return 起点
     * @see [类、类#方法、类#成员]
     */
    public String getStartpoint() {
        return startpoint;
    }
    
    /**
     * 〈一句话功能简述〉 设置起点
     * 〈功能详细描述〉
     * 
     * @param startpoint 起点
     * @see [类、类#方法、类#成员]
     */
    public void setStartpoint(String startpoint) {
        this.startpoint = startpoint == null ? null : startpoint.trim();
    }
    
    /**
     * 〈一句话功能简述〉获取到点
     * 〈功能详细描述〉
     * 
     * @return 到点
     * @see [类、类#方法、类#成员]
     */
    public String getEndpoint() {
        return endpoint;
    }
    
    /**
     * 〈一句话功能简述〉设置到点
     * 〈功能详细描述〉
     * 
     * @param endpoint 到点
     * @see [类、类#方法、类#成员]
     */
    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint == null ? null : endpoint.trim();
    }
    
    /**
     * 〈一句话功能简述〉获取发货单位
     * 〈功能详细描述〉
     * 
     * @return 发货单位
     * @see [类、类#方法、类#成员]
     */
    public String getDeliveryunit() {
        return deliveryunit;
    }
    
    /**
     * 〈一句话功能简述〉设置发货单位
     * 〈功能详细描述〉
     * 
     * @param deliveryunit 发货单位
     * @see [类、类#方法、类#成员]
     */
    public void setDeliveryunit(String deliveryunit) {
        this.deliveryunit = deliveryunit == null ? null : deliveryunit.trim();
    }
    
    /**
     * 〈一句话功能简述〉获取发货单位联系人
     * 〈功能详细描述〉
     * 
     * @return 发货单位联系人
     * @see [类、类#方法、类#成员]
     */
    public String getDeliveryunitperson() {
        return deliveryunitperson;
    }
    
    /**
     * 〈一句话功能简述〉 设置发货单位联系人
     * 〈功能详细描述〉
     * 
     * @param deliveryunitperson 发货单位联系人
     * @see [类、类#方法、类#成员]
     */
    public void setDeliveryunitperson(String deliveryunitperson) {
        this.deliveryunitperson =
            deliveryunitperson == null ? null : deliveryunitperson.trim();
    }
    
    /**
     * 〈一句话功能简述〉 获取运单状态
     * 〈功能详细描述〉
     * 
     * @return 运单状态
     * @see [类、类#方法、类#成员]
     */
    public String getCheckstatus() {
        return checkstatus;
    }
    
    /**
     * 〈一句话功能简述〉 设置运单状态
     * 〈功能详细描述〉
     * 
     * @param checkstatus 运单状态
     * @see [类、类#方法、类#成员]
     */
    public void setCheckstatus(String checkstatus) {
        this.checkstatus = checkstatus == null ? null : checkstatus.trim();
    }
    
    /**
     * 〈一句话功能简述〉 获取发货地址
     * 〈功能详细描述〉
     * 
     * @return 发货地址
     * @see [类、类#方法、类#成员]
     */
    public String getDeliveryunitaddress() {
        return deliveryunitaddress;
    }
    
    /**
     * 〈一句话功能简述〉 设置发货地址
     * 〈功能详细描述〉
     * 
     * @param deliveryunitaddress 发货地址
     * @see [类、类#方法、类#成员]
     */
    public void setDeliveryunitaddress(String deliveryunitaddress) {
        this.deliveryunitaddress =
            deliveryunitaddress == null ? null : deliveryunitaddress.trim();
    }
    
    /**
     * 〈一句话功能简述〉 获取发货时间
     * 〈功能详细描述〉
     * 
     * @return 发货时间
     * @see [类、类#方法、类#成员]
     */
    public Date getDeliveryunittime() {
        return deliveryunittime;
    }
    
    /**
     * 〈一句话功能简述〉 设置发货时间
     * 〈功能详细描述〉
     * 
     * @param deliveryunittime 发货时间
     * @see [类、类#方法、类#成员]
     */
    public void setDeliveryunittime(Date deliveryunittime) {
        this.deliveryunittime = deliveryunittime;
    }
    
    /**
     * 〈一句话功能简述〉 获取收货单位
     * 〈功能详细描述〉
     * 
     * @return 收货单位
     * @see [类、类#方法、类#成员]
     */
    public String getAchieveuint() {
        return achieveuint;
    }
    
    /**
     * 〈一句话功能简述〉设置收货单位
     * 〈功能详细描述〉
     * 
     * @param achieveuint 收货单位
     * @see [类、类#方法、类#成员]
     */
    public void setAchieveuint(String achieveuint) {
        this.achieveuint = achieveuint == null ? null : achieveuint.trim();
    }
    
    /**
     * 〈一句话功能简述〉 获取收货单位联系人
     * 〈功能详细描述〉
     * 
     * @return 收货单位联系人
     * @see [类、类#方法、类#成员]
     */
    public String getAchieveperson() {
        return achieveperson;
    }
    
    /**
     * 〈一句话功能简述〉设置收货单位联系人
     * 〈功能详细描述〉
     * 
     * @param achieveperson 收货单位联系人
     * @see [类、类#方法、类#成员]
     */
    public void setAchieveperson(String achieveperson) {
        this.achieveperson =
            achieveperson == null ? null : achieveperson.trim();
    }
    
    /**
     * 〈一句话功能简述〉 获取收货单位联系电话
     * 〈功能详细描述〉
     * 
     * @return 收货单位联系电话
     * @see [类、类#方法、类#成员]
     */
    public String getAchievephone() {
        return achievephone;
    }
    
    /**
     * 〈一句话功能简述〉设置收货单位联系电话
     * 〈功能详细描述〉
     * 
     * @param achievephone 收货单位联系电话
     * @see [类、类#方法、类#成员]
     */
    public void setAchievephone(String achievephone) {
        this.achievephone = achievephone == null ? null : achievephone.trim();
    }
    
    /**
     * 〈一句话功能简述〉 获取发货单位联系电话
     * 〈功能详细描述〉
     * 
     * @return 发货单位联系电话
     * @see [类、类#方法、类#成员]
     */
    public String getDeliveryunitphone() {
        return deliveryunitphone;
    }
    
    /**
     * 〈一句话功能简述〉设置发货单位联系电话
     * 〈功能详细描述〉
     * 
     * @param deliveryunitphone 发货单位联系电话
     * @see [类、类#方法、类#成员]
     */
    public void setDeliveryunitphone(String deliveryunitphone) {
        this.deliveryunitphone =
            deliveryunitphone == null ? null : deliveryunitphone.trim();
    }
    
    /**
     * 〈一句话功能简述〉 获取收货地址
     * 〈功能详细描述〉
     * 
     * @return 收货地址
     * @see [类、类#方法、类#成员]
     */
    public String getAchieveaddress() {
        return achieveaddress;
    }
    
    /**
     * 〈一句话功能简述〉 设置收货地址
     * 〈功能详细描述〉
     * 
     * @param achieveaddress 收货地址
     * @see [类、类#方法、类#成员]
     */
    public void setAchieveaddress(String achieveaddress) {
        this.achieveaddress =
            achieveaddress == null ? null : achieveaddress.trim();
    }
    
    /**
     * 〈一句话功能简述〉 获取卸货时间
     * 〈功能详细描述〉
     * 
     * @return 卸货时间
     * @see [类、类#方法、类#成员]
     */
    public Date getAchievetime() {
        return achievetime;
    }
    
    /**
     * 〈一句话功能简述〉设置卸货时间
     * 〈功能详细描述〉
     * 
     * @param achievetime 卸货时间
     * @see [类、类#方法、类#成员]
     */
    public void setAchievetime(Date achievetime) {
        this.achievetime = achievetime;
    }
    
    /**
     * 〈一句话功能简述〉 获取驾驶员
     * 〈功能详细描述〉
     * 
     * @return 驾驶员
     * @see [类、类#方法、类#成员]
     */
    public String getDriver() {
        return driver;
    }
    
    /**
     * 〈一句话功能简述〉设置驾驶员
     * 〈功能详细描述〉
     * 
     * @param driver 驾驶员
     * @see [类、类#方法、类#成员]
     */
    public void setDriver(String driver) {
        this.driver = driver == null ? null : driver.trim();
    }
    
    /**
     * 〈一句话功能简述〉 获取驾驶员电话
     * 〈功能详细描述〉
     * 
     * @return 驾驶员电话
     * @see [类、类#方法、类#成员]
     */
    public String getDriverphone() {
        return driverphone;
    }
    
    /**
     * 〈一句话功能简述〉设置驾驶员电话
     * 〈功能详细描述〉
     * 
     * @param driverphone 驾驶员电话
     * @see [类、类#方法、类#成员]
     */
    public void setDriverphone(String driverphone) {
        this.driverphone = driverphone == null ? null : driverphone.trim();
    }
    
    /**
     * 〈一句话功能简述〉获取身份证号（驾驶员
     * 〈功能详细描述〉
     * 
     * @return 身份证号（驾驶员
     * @see [类、类#方法、类#成员]
     */
    public String getDriverid() {
        return driverid;
    }
    
    /**
     * 〈一句话功能简述〉设置身份证号（驾驶员
     * 〈功能详细描述〉
     * 
     * @param driverid 身份证号（驾驶员
     * @see [类、类#方法、类#成员]
     */
    public void setDriverid(String driverid) {
        this.driverid = driverid == null ? null : driverid.trim();
    }
    
    /**
     * 〈一句话功能简述〉 获取从业证号（驾驶员）
     * 〈功能详细描述〉
     * 
     * @return 从业证号（驾驶员）
     * @see [类、类#方法、类#成员]
     */
    public String getDriverno() {
        return driverno;
    }
    
    /**
     * 〈一句话功能简述〉 设置从业证号（驾驶员）
     * 〈功能详细描述〉
     * 
     * @param driverno 从业证号（驾驶员）
     * @see [类、类#方法、类#成员]
     */
    public void setDriverno(String driverno) {
        this.driverno = driverno == null ? null : driverno.trim();
    }
    
    /**
     * 〈一句话功能简述〉 获取押运员
     * 〈功能详细描述〉
     * 
     * @return 押运员
     * @see [类、类#方法、类#成员]
     */
    public String getEscort() {
        return escort;
    }
    
    /**
     * 〈一句话功能简述〉设置押运员
     * 〈功能详细描述〉
     * 
     * @param escort 押运员
     * @see [类、类#方法、类#成员]
     */
    public void setEscort(String escort) {
        this.escort = escort == null ? null : escort.trim();
    }
    
    /**
     * 〈一句话功能简述〉 设置获取电话（押运员）
     * 〈功能详细描述〉
     * 
     * @return 电话（押运员）
     * @see [类、类#方法、类#成员]
     */
    public String getEscortphone() {
        return escortphone;
    }
    
    /**
     * 〈一句话功能简述〉 设置电话（押运员）
     * 〈功能详细描述〉
     * 
     * @param escortphone 电话（押运员）
     * @see [类、类#方法、类#成员]
     */
    public void setEscortphone(String escortphone) {
        this.escortphone = escortphone == null ? null : escortphone.trim();
    }
    
    /**
     * 〈一句话功能简述〉 获取挂车车牌
     * 〈功能详细描述〉
     * 
     * @return 挂车车牌
     * @see [类、类#方法、类#成员]
     */
    public String getHandcarno() {
        return handcarno;
    }
    
    /**
     * 〈一句话功能简述〉 设置挂车车牌
     * 〈功能详细描述〉
     * 
     * @param handcarno 挂车车牌
     * @see [类、类#方法、类#成员]
     */
    public void setHandcarno(String handcarno) {
        this.handcarno = handcarno == null ? null : handcarno.trim();
    }
    
    /**
     * 〈一句话功能简述〉 获取押运员证号
     * 〈功能详细描述〉
     * 
     * @return 押运员证号
     * @see [类、类#方法、类#成员]
     */
    public String getEscortno() {
        return escortno;
    }
    
    /**
     * 〈一句话功能简述〉 设置押运员证号
     * 〈功能详细描述〉
     * 
     * @param escortno 押运员证号
     * @see [类、类#方法、类#成员]
     */
    public void setEscortno(String escortno) {
        this.escortno = escortno == null ? null : escortno.trim();
    }
    
    /**
     * 〈一句话功能简述〉 获取身份证号（押运员）
     * 〈功能详细描述〉
     * 
     * @return 身份证号（押运员）
     * @see [类、类#方法、类#成员]
     */
    public String getEscortid() {
        return escortid;
    }
    
    /**
     * 〈一句话功能简述〉 设置身份证号（押运员）
     * 〈功能详细描述〉
     * 
     * @param escortid 身份证号（押运员）
     * @see [类、类#方法、类#成员]
     */
    public void setEscortid(String escortid) {
        this.escortid = escortid == null ? null : escortid.trim();
    }
    
    /**
     * 〈一句话功能简述〉 获取车牌号
     * 〈功能详细描述〉
     * 
     * @return 车牌号
     * @see [类、类#方法、类#成员]
     */
    public String getCarno() {
        return carno;
    }
    
    /**
     * 〈一句话功能简述〉 设置车牌号
     * 〈功能详细描述〉
     * 
     * @param carno 车牌号
     * @see [类、类#方法、类#成员]
     */
    public void setCarno(String carno) {
        this.carno = carno == null ? null : carno.trim();
    }
    
    /**
     * 〈一句话功能简述〉获取备注
     * 〈功能详细描述〉
     * 
     * @return 备注
     * @see [类、类#方法、类#成员]
     */
    public String getRemark() {
        return remark;
    }
    
    /**
     * 〈一句话功能简述〉设置备注
     * 〈功能详细描述〉
     * 
     * @param remark 备注
     * @see [类、类#方法、类#成员]
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
    
    /**
     * 〈一句话功能简述〉货物信息集合
     * 〈功能详细描述〉
     * 
     * @return 货物信息集合
     * @see [类、类#方法、类#成员]
     */
    public List<WayBillGoodsEntity> getListBillGoods() {
        return listBillGoods;
    }
    
    /**
     * 〈一句话功能简述〉货物信息集合
     * 〈功能详细描述〉
     * 
     * @param listBillGoods 货物信息集合
     * @see [类、类#方法、类#成员]
     */
    public void setListBillGoods(List<WayBillGoodsEntity> listBillGoods) {
        this.listBillGoods = listBillGoods;
    }
    
    /**
     * 〈一句话功能简述〉获取运输公司名称
     * 〈功能详细描述〉
     * 
     * @return wlEnterpriseName
     * @see [类、类#方法、类#成员]
     */
    public String getWlEnterpriseName() {
        return wlEnterpriseName;
    }
    
    /**
     * 〈一句话功能简述〉设置运输公司名称
     * 〈功能详细描述〉
     * 
     * @param wlEnterpriseName wlEnterpriseName
     * @see [类、类#方法、类#成员]
     */
    public void setWlEnterpriseName(String wlEnterpriseName) {
        this.wlEnterpriseName = wlEnterpriseName;
    }
    
    /**
     * 〈一句话功能简述〉获取车辆状态
     * 〈功能详细描述〉
     * 
     * @return onlineStatus
     * @see [类、类#方法、类#成员]
     */
    public String getOnlineStatus() {
        return onlineStatus;
    }
    
    /**
     * 〈一句话功能简述〉设置车辆状态
     * 〈功能详细描述〉
     * 
     * @param onlineStatus onlineStatus
     * @see [类、类#方法、类#成员]
     */
    public void setOnlineStatus(String onlineStatus) {
        this.onlineStatus = onlineStatus;
    }
    
    /**
     *〈一句话功能简述〉获取创建时间
     * 〈功能详细描述〉
     * @return createTime
     * @see  [类、类#方法、类#成员]
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     *〈一句话功能简述〉设置创建时间
     * 〈功能详细描述〉
     * @param createTime createTime
     * @see  [类、类#方法、类#成员]
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
    @Override
    protected Object getBaseEntity() {
        return this;
    }
    
}
