/**
 * 文件名：DangerVehicleForm.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-7-20
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.vehiclemonitor.formbean;

import com.c503.sc.base.formbean.PageForm;

/**
 * 
 * 〈一句话功能简述〉危险品车辆信息Form
 * 〈功能详细描述〉
 * 
 * @author guqh
 * @version [版本号, 2016-7-20]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class DangerVehicleForm extends PageForm {
    
    /** 序列号 */
    private static final long serialVersionUID = 2143451561339348085L;
    
    /** 法人代码 */
    private String corporateNo;
    
    /** 企业ID */
    private String enterpriseId;
    
    /** 终端ID */
    private String terminalId;
    
    /** 车牌号 */
    private String licencePlateNo;
    
    /** 车辆品牌 */
    private String vehicleBrand;
    
    /** 车辆品牌类型 */
    private String vehicleBrandType;
    
    /** 车辆颜色 */
    private String vehicleColor;
    
    /** 车辆类型 */
    private String vehicleType;
    
    /** 车辆排量 */
    private String vehicleOutput;
    
    /** 发动机号码 */
    private String motorNo;
    
    /** 底盘号码 */
    private String graduadedNo;
    
    /** 车辆在线状态 */
    private String status;
    
    /** 车辆编号 */
    private String vehicleNo;
    
    /** 运输线路 */
    private String transportRoute;
    
    /** 可运输货物类别 */
    private String goodstype;
    
    /** 可运输货物数量 */
    private String quantity;
    
    /** 最大载重 */
    private String maxLoadWeight;
    
    /** 联系方式 */
    private String contactPhone;
    
    /** 搜索条件-企业名称 */
    private String enterpriseName;
    
    /** 搜索条件-跨域类型(本地、外地) */
    private String crossDomainType;
    
    /**
     * 〈一句话功能简述〉获得法人代码
     * 〈功能详细描述〉
     * 
     * @return corporateNo
     * @see [类、类#方法、类#成员]
     */
    public String getCorporateNo() {
        return corporateNo;
    }
    
    /**
     * 〈一句话功能简述〉设置法人代码
     * 〈功能详细描述〉
     * 
     * @param corporateNo 法人代码
     * @see [类、类#方法、类#成员]
     */
    public void setCorporateNo(String corporateNo) {
        this.corporateNo = corporateNo;
    }
    
    /**
     * 〈一句话功能简述〉获得企业ID
     * 〈功能详细描述〉
     * 
     * @return enterpriseId
     * @see [类、类#方法、类#成员]
     */
    public String getEnterpriseId() {
        return enterpriseId;
    }
    
    /**
     * 〈一句话功能简述〉设置企业ID
     * 〈功能详细描述〉
     * 
     * @param enterpriseId 企业ID
     * @see [类、类#方法、类#成员]
     */
    public void setEnterpriseId(String enterpriseId) {
        this.enterpriseId = enterpriseId;
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
     * 〈一句话功能简述〉获得车辆品牌
     * 〈功能详细描述〉
     * 
     * @return vehicleBrand
     * @see [类、类#方法、类#成员]
     */
    public String getVehicleBrand() {
        return vehicleBrand;
    }
    
    /**
     * 〈一句话功能简述〉设置车辆品牌
     * 〈功能详细描述〉
     * 
     * @param vehicleBrand 车辆品牌
     * @see [类、类#方法、类#成员]
     */
    public void setVehicleBrand(String vehicleBrand) {
        this.vehicleBrand = vehicleBrand;
    }
    
    /**
     * 〈一句话功能简述〉获得车辆品牌类型
     * 〈功能详细描述〉
     * 
     * @return vehicleBrandType
     * @see [类、类#方法、类#成员]
     */
    public String getVehicleBrandType() {
        return vehicleBrandType;
    }
    
    /**
     * 〈一句话功能简述〉设置车辆品牌类型
     * 〈功能详细描述〉
     * 
     * @param vehicleBrandType 车辆品牌类型
     * @see [类、类#方法、类#成员]
     */
    public void setVehicleBrandType(String vehicleBrandType) {
        this.vehicleBrandType = vehicleBrandType;
    }
    
    /**
     * 〈一句话功能简述〉获得车辆颜色
     * 〈功能详细描述〉
     * 
     * @return vehicleColor
     * @see [类、类#方法、类#成员]
     */
    public String getVehicleColor() {
        return vehicleColor;
    }
    
    /**
     * 〈一句话功能简述〉设置车辆颜色
     * 〈功能详细描述〉
     * 
     * @param vehicleColor 车辆颜色
     * @see [类、类#方法、类#成员]
     */
    public void setVehicleColor(String vehicleColor) {
        this.vehicleColor = vehicleColor;
    }
    
    /**
     * 〈一句话功能简述〉获得车辆类型
     * 〈功能详细描述〉
     * 
     * @return vehicleType
     * @see [类、类#方法、类#成员]
     */
    public String getVehicleType() {
        return vehicleType;
    }
    
    /**
     * 〈一句话功能简述〉设置车辆类型
     * 〈功能详细描述〉
     * 
     * @param vehicleType 车辆类型
     * @see [类、类#方法、类#成员]
     */
    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }
    
    /**
     * 〈一句话功能简述〉获得车辆排量
     * 〈功能详细描述〉
     * 
     * @return vehicleOutput
     * @see [类、类#方法、类#成员]
     */
    public String getVehicleOutput() {
        return vehicleOutput;
    }
    
    /**
     * 〈一句话功能简述〉设置车辆排量
     * 〈功能详细描述〉
     * 
     * @param vehicleOutput 车辆排量
     * @see [类、类#方法、类#成员]
     */
    public void setVehicleOutput(String vehicleOutput) {
        this.vehicleOutput = vehicleOutput;
    }
    
    /**
     * 〈一句话功能简述〉获得发动机号码
     * 〈功能详细描述〉
     * 
     * @return motorNo
     * @see [类、类#方法、类#成员]
     */
    public String getMotorNo() {
        return motorNo;
    }
    
    /**
     * 〈一句话功能简述〉设置发动机号码
     * 〈功能详细描述〉
     * 
     * @param motorNo 发动机号码
     * @see [类、类#方法、类#成员]
     */
    public void setMotorNo(String motorNo) {
        this.motorNo = motorNo;
    }
    
    /**
     * 〈一句话功能简述〉获得跨域类型
     * 〈功能详细描述〉
     * 
     * @return crossDomainType
     * @see [类、类#方法、类#成员]
     */
    public String getCrossDomainType() {
        return crossDomainType;
    }
    
    /**
     * 〈一句话功能简述〉设置跨域类型
     * 〈功能详细描述〉
     * 
     * @param crossDomainType 跨域类型
     * @see [类、类#方法、类#成员]
     */
    public void setCrossDomainType(String crossDomainType) {
        this.crossDomainType =
            crossDomainType == null ? null : crossDomainType.trim();
    }
    
    /**
     * 〈一句话功能简述〉获得底盘号码
     * 〈功能详细描述〉
     * 
     * @return graduadedNo
     * @see [类、类#方法、类#成员]
     */
    public String getGraduadedNo() {
        return graduadedNo;
    }
    
    /**
     * 〈一句话功能简述〉设置底盘号码
     * 〈功能详细描述〉
     * 
     * @param graduadedNo 底盘号码
     * @see [类、类#方法、类#成员]
     */
    public void setGraduadedNo(String graduadedNo) {
        this.graduadedNo = graduadedNo;
    }
    
    /**
     * 〈一句话功能简述〉获取车辆状态
     * 〈功能详细描述〉
     * 
     * @return 车辆状态
     * @see [类、类#方法、类#成员]
     */
    public String getStatus() {
        return status;
    }
    
    /**
     * 〈一句话功能简述〉设置车辆状态
     * 〈功能详细描述〉
     * 
     * @param status 车辆状态
     * @see [类、类#方法、类#成员]
     */
    public void setStatus(String status) {
        this.status = status;
    }
    
    /**
     * 〈一句话功能简述〉获取车辆编号
     * 〈功能详细描述〉
     * 
     * @return 车辆编号
     * @see [类、类#方法、类#成员]
     */
    public String getVehicleNo() {
        return vehicleNo;
    }
    
    /**
     * 〈一句话功能简述〉设置车辆编号
     * 〈功能详细描述〉
     * 
     * @param vehicleNo 车辆编号
     * @see [类、类#方法、类#成员]
     */
    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }
    
    /**
     * 〈一句话功能简述〉获取企业名称
     * 〈功能详细描述〉
     * 
     * @return 企业名称
     * @see [类、类#方法、类#成员]
     */
    public String getEnterpriseName() {
        return enterpriseName;
    }
    
    /**
     * 〈一句话功能简述〉设置企业名称
     * 〈功能详细描述〉
     * 
     * @param enterpriseName 企业名称
     * @see [类、类#方法、类#成员]
     */
    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName =
            enterpriseName == null ? null : enterpriseName.trim();
    }
    
    public String getTransportRoute() {
        return transportRoute;
    }
    
    public void setTransportRoute(String transportRoute) {
        this.transportRoute = transportRoute;
    }
    
    public String getGoodstype() {
        return goodstype;
    }
    
    public void setGoodstype(String goodstype) {
        this.goodstype = goodstype;
    }
    
    public String getQuantity() {
        return quantity;
    }
    
    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
    
    public String getMaxLoadWeight() {
        return maxLoadWeight;
    }
    
    public void setMaxLoadWeight(String maxLoadWeight) {
        this.maxLoadWeight = maxLoadWeight;
    }
    
    public String getContactPhone() {
        return contactPhone;
    }
    
    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }
    
}
