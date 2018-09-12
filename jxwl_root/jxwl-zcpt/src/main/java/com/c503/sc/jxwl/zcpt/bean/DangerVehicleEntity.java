/**
 * 文件名：DangerVehicleEntity.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-7-20
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.zcpt.bean;

import java.util.Date;
import java.util.List;

import com.c503.sc.base.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 
 * 〈一句话功能简述〉危险品车辆信息实体类
 * 〈功能详细描述〉
 * 
 * @author guqh
 * @version [版本号, 2016-7-20]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class DangerVehicleEntity extends BaseEntity {
    
    /** 序列号 */
    private static final long serialVersionUID = 8468325966364289479L;
    
    /** 法人代码 */
    private String corporateNo;
    
    /** 企业ID */
    private String enterpriseId;
    
    /** 终端ID */
    private String terminalId;
    
    /** 车牌号 */
    private String licencePlateNo;
    
    /** 车牌号 */
    private String name;
    
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
    
    /** 跨域类型 */
    private String crossDomainType;
    
    /** 底盘号码 */
    private String graduadedNo;
    
    /** 区分是车源还是车辆的标记，0为车辆，1为车源 */
    private String vehicleSrcFlag;
    
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
    
    /** 发布日期 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date publishDate;
    
    /** 附件 ids 数组 */
    private String[] fileIds;
    
    /** 附件名字数组 */
    private String fileName;
    
    /** 附件对象 */
    private List<DangerFileRelaEntity> dangerFileRela;
    
    /** 
     * 车辆在线状态
     * 0-在线；1-离线
     */
    private String status;
    
    /** 企业名称 */
    private String enterpriseName;
    
    /** 车辆编号 */
    private String vehicleNo;
    
    /** 黑名单状态*/
    private String blackStatus;
    
    
    /**
     * 〈一句话功能简述〉获得blackStatus
     * 〈功能详细描述〉
     * 
     * @return blackStatus
     * @see [类、类#方法、类#成员]
     */
    public String getBlackStatus() {
		return blackStatus;
	}
    /**
     * 〈一句话功能简述〉设置blackStatus
     * 〈功能详细描述〉
     * 
     * @param blackStatus blackStatus
     * @see [类、类#方法、类#成员]
     */
	public void setBlackStatus(String blackStatus) {
		this.blackStatus = blackStatus;
	}

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
        this.name = licencePlateNo;
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
        this.crossDomainType = crossDomainType;
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
     * 〈一句话功能简述〉附件 ids 数组
     * 〈功能详细描述〉
     * 
     * @return 附件 ids 数组
     * @see [类、类#方法、类#成员]
     */
    public String[] getFileIds() {
        return fileIds;
    }
    
    /**
     * 〈一句话功能简述〉附件 ids 数组
     * 〈功能详细描述〉
     * 
     * @param fileIds 附件 ids 数组
     * @see [类、类#方法、类#成员]
     */
    public void setFileIds(String[] fileIds) {
        this.fileIds = fileIds;
    }
    
    
    /**
     * 〈一句话功能简述〉附件数据
     * 〈功能详细描述〉
     * 
     * @return 附件数据
     * @see [类、类#方法、类#成员]
     */
    public List<DangerFileRelaEntity> getDangerFileRela() {
        return dangerFileRela;
    }
    
    /**
     * 〈一句话功能简述〉附件对象
     * 〈功能详细描述〉
     * 
     * @param dangerFileRela 附件对象
     * @see [类、类#方法、类#成员]
     */
    public void setDangerFileRela(List<DangerFileRelaEntity> dangerFileRela) {
        this.dangerFileRela = dangerFileRela;
    }
    
    /**
     * 〈一句话功能简述〉获取附件名字
     * 〈功能详细描述〉
     * 
     * @return 附件名字
     * @see [类、类#方法、类#成员]
     */
    public String getFileName() {
        return fileName;
    }
    
    /**
     * 〈一句话功能简述〉设置附件名字
     * 〈功能详细描述〉
     * 
     * @param fileName 附件名字
     * @see [类、类#方法、类#成员]
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
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
		this.enterpriseName = enterpriseName;
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
	
	@Override
    protected Object getBaseEntity() {
        return this;
    }

    /**
     *〈一句话功能简述〉获取车牌号
     * 〈功能详细描述〉
     * @return name
     * @see  [类、类#方法、类#成员]
     */
    public String getName() {
        return name;
    }

    /**
     *〈一句话功能简述〉设置车牌号
     * 〈功能详细描述〉
     * @param name name
     * @see  [类、类#方法、类#成员]
     */
    public void setName(String name) {
        this.name = name;
    }
    
    public String getVehicleSrcFlag()
    {
        return vehicleSrcFlag;
    }
    
    public void setVehicleSrcFlag(String vehicleSrcFlag)
    {
        this.vehicleSrcFlag = vehicleSrcFlag;
    }
    
    public String getTransportRoute()
    {
        return transportRoute;
    }
    
    public void setTransportRoute(String transportRoute)
    {
        this.transportRoute = transportRoute;
    }
    
    public String getGoodstype()
    {
        return goodstype;
    }
    
    public void setGoodstype(String goodstype)
    {
        this.goodstype = goodstype;
    }
    
    public String getQuantity()
    {
        return quantity;
    }
    
    public void setQuantity(String quantity)
    {
        this.quantity = quantity;
    }
    
    public String getMaxLoadWeight()
    {
        return maxLoadWeight;
    }
    
    public void setMaxLoadWeight(String maxLoadWeight)
    {
        this.maxLoadWeight = maxLoadWeight;
    }
    
    public String getContactPhone()
    {
        return contactPhone;
    }
    
    public void setContactPhone(String contactPhone)
    {
        this.contactPhone = contactPhone;
    }
    
    public Date getPublishDate()
    {
        return publishDate;
    }
    
    public void setPublishDate(Date publishDate)
    {
        this.publishDate = publishDate;
    }
    
    
}
