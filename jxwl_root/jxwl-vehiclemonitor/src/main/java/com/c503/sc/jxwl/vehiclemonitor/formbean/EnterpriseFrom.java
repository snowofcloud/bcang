/**
 * 文件名：EnterpriseFrom.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-7-20
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.vehiclemonitor.formbean;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.c503.sc.base.formbean.PageForm;
import com.c503.sc.base.validation.Save;
import com.c503.sc.base.validation.Update;
import com.c503.sc.jxwl.common.constant.RegexpContant;

/**
 * 
 * 〈一句话功能简述〉物流企业信息Form
 * 〈功能详细描述〉
 * 
 * @author guqh
 * @version [版本号, 2016-7-20]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class EnterpriseFrom extends PageForm {
    
    /** 序列号 */
    private static final long serialVersionUID = -171012045649301365L;
    
    /** 法人代码 */
    @Pattern(regexp = RegexpContant.ANY_200_CHAR_MUST, message = "{enterpriseFrom.corporateNo}", groups = {
        Save.class, Update.class})
    private String corporateNo;
    
    /** 企业名称 */
    @Pattern(regexp = RegexpContant.ANY_200_CHAR_MUST, message = "{enterpriseFrom.enterpriseName}", groups = {
        Save.class, Update.class})
    private String enterpriseName;
    
    /** 法人代表 */
    // @Pattern(regexp = RegexpContant.ANY_200_CHAR_MUST, message =
    // "{enterpriseFrom.corporateRep}", groups = {
    // Save.class, Update.class})
    private String corporateRep;
    
    /** 企业简介 */
    @Pattern(regexp = RegexpContant.ANY_1000_CHAR, message = "{enterpriseFrom.enterpriseIntroduce}", groups = {
        Save.class, Update.class})
    private String enterpriseIntroduce;
    
    /** 登记机关 */
    @Pattern(regexp = RegexpContant.ANY_200_CHAR, message = "{enterpriseFrom.registerOffice}", groups = {
        Save.class, Update.class})
    private String registerOffice;
    
    /** 注册资本 */
    @NotNull(message = "{enterpriseFrom.logonCapital}", groups = Save.class)
    private BigDecimal logonCapital;
    
    /** 主营路线 */
    @Pattern(regexp = RegexpContant.ANY_1000_CHAR, message = "{enterpriseFrom.majorBusinessRoute}", groups = {
        Save.class, Update.class})
    private String majorBusinessRoute;
    
    /** 成立时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd", iso = ISO.DATE)
    private Date establishDate;
    
    /** 开户行 */
    @Pattern(regexp = RegexpContant.ANY_200_CHAR, message = "{enterpriseFrom.openAccountBank}", groups = {
        Save.class, Update.class})
    private String openAccountBank;
    
    /** 工商注册号 */
    @Pattern(regexp = RegexpContant.ANY_200_CHAR, message = "{enterpriseFrom.registrationNo}", groups = {
        Save.class, Update.class})
    private String registrationNo;
    
    /** 主营业务 */
    @Pattern(regexp = RegexpContant.ANY_1000_CHAR, message = "{enterpriseFrom.professionalWork}", groups = {
        Save.class, Update.class})
    private String professionalWork;
    
    /** 税务证号 */
    @Pattern(regexp = RegexpContant.ANY_200_CHAR, message = "{enterpriseFrom.taxationNo}", groups = {
        Save.class, Update.class})
    private String taxationNo;
    
    /** 经营区域 */
    private String operateArea;
    
    /** 经营地址 */
    @Pattern(regexp = RegexpContant.ANY_200_CHAR_MUST, message = "{enterpriseFrom.address}", groups = {
        Save.class, Update.class})
    private String address;
    
    /** 网址 */
    @Pattern(regexp = RegexpContant.ANY_200_CHAR, message = "{enterpriseFrom.networkAddr}", groups = {
        Save.class, Update.class})
    private String networkAddr;
    
    /** 车辆数量 */
    private BigDecimal vehicleNum;
    
    /** 联系电话 */
    @Pattern(regexp = RegexpContant.TELPHONE_OR_MOBILE, message = "{enterpriseFrom.telephone}", groups = {
        Save.class, Update.class})
    private String telephone;
    
    /** 附件Id */
    private String[] fileIds;
    
    /** 跨域类型*/
	private String crossDomainType4Enter;
	
	/** 企业类型*/
	private String enterpriseType;
	
	/**
     * 〈一句话功能简述〉获得企业类型 〈功能详细描述〉
     * 
     * @return enterpriseType
     * @see [类、类#方法、类#成员]
     */
    public String getEnterpriseType() {
        return enterpriseType;
    }
    
    /**
     * 〈一句话功能简述〉设置企业类型 〈功能详细描述〉
     * 
     * @param enterpriseType
     *            企业类型
     * @see [类、类#方法、类#成员]
     */
    public void setEnterpriseType(String enterpriseType) {
        this.enterpriseType = enterpriseType;
    }
	
	/**
	 * 
	 * 〈一句话功能简述〉获取crossDomainType4Enter
	 * 
	 * @return crossDomainType4Enter crossDomainType4Enter
	 * @see [类、类#方法、类#成员]
	 */
	public String getCrossDomainType4Enter() {
		return crossDomainType4Enter;
	}
	/**
	 * 
	 * 〈一句话功能简述〉设crossDomainType4Enter 〈功能详细描述〉
	 * 
	 * @param crossDomainType4Enter
	 *            crossDomainType4Enter
	 * @see [类、类#方法、类#成员]
	 */
	public void setCrossDomainType4Enter(String crossDomainType4Enter) {
		this.crossDomainType4Enter = crossDomainType4Enter;
	}
    
    /**
     * 〈一句话功能简述〉获得fileIds
     * 〈功能详细描述〉
     * 
     * @return fileIds
     * @see [类、类#方法、类#成员]
     */
    public String[] getFileIds() {
        return fileIds;
    }
    
    /**
     * 〈一句话功能简述〉设置fileIds
     * 〈功能详细描述〉
     * 
     * @param fileIds fileIds
     * @see [类、类#方法、类#成员]
     */
    public void setFileIds(String[] fileIds) {
        this.fileIds = fileIds;
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
     * 〈一句话功能简述〉获得企业名称
     * 〈功能详细描述〉
     * 
     * @return enterpriseName
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
     * 〈一句话功能简述〉获得法人代表
     * 〈功能详细描述〉
     * 
     * @return corporateRep
     * @see [类、类#方法、类#成员]
     */
    public String getCorporateRep() {
        return corporateRep;
    }
    
    /**
     * 〈一句话功能简述〉设置法人代表
     * 〈功能详细描述〉
     * 
     * @param corporateRep 法人代表
     * @see [类、类#方法、类#成员]
     */
    public void setCorporateRep(String corporateRep) {
        this.corporateRep = corporateRep;
    }
    
    /**
     * 〈一句话功能简述〉获得企业简介
     * 〈功能详细描述〉
     * 
     * @return enterpriseIntroduce
     * @see [类、类#方法、类#成员]
     */
    public String getEnterpriseIntroduce() {
        return enterpriseIntroduce;
    }
    
    /**
     * 〈一句话功能简述〉设置企业简介
     * 〈功能详细描述〉
     * 
     * @param enterpriseIntroduce 企业简介
     * @see [类、类#方法、类#成员]
     */
    public void setEnterpriseIntroduce(String enterpriseIntroduce) {
        this.enterpriseIntroduce = enterpriseIntroduce;
    }
    
    /**
     * 〈一句话功能简述〉获得登记机关
     * 〈功能详细描述〉
     * 
     * @return registerOffice
     * @see [类、类#方法、类#成员]
     */
    public String getRegisterOffice() {
        return registerOffice;
    }
    
    /**
     * 〈一句话功能简述〉设置登记机关
     * 〈功能详细描述〉
     * 
     * @param registerOffice 登记机关
     * @see [类、类#方法、类#成员]
     */
    public void setRegisterOffice(String registerOffice) {
        this.registerOffice = registerOffice;
    }
    
    /**
     * 〈一句话功能简述〉获得注册资本
     * 〈功能详细描述〉
     * 
     * @return logonCapital
     * @see [类、类#方法、类#成员]
     */
    public BigDecimal getLogonCapital() {
        return logonCapital;
    }
    
    /**
     * 〈一句话功能简述〉设置注册资本
     * 〈功能详细描述〉
     * 
     * @param logonCapital 注册资本
     * @see [类、类#方法、类#成员]
     */
    public void setLogonCapital(BigDecimal logonCapital) {
        this.logonCapital = logonCapital;
    }
    
    /**
     * 〈一句话功能简述〉获得主营路线
     * 〈功能详细描述〉
     * 
     * @return majorBusinessRoute
     * @see [类、类#方法、类#成员]
     */
    public String getMajorBusinessRoute() {
        return majorBusinessRoute;
    }
    
    /**
     * 〈一句话功能简述〉设置主营路线
     * 〈功能详细描述〉
     * 
     * @param majorBusinessRoute 主营路线
     * @see [类、类#方法、类#成员]
     */
    public void setMajorBusinessRoute(String majorBusinessRoute) {
        this.majorBusinessRoute = majorBusinessRoute;
    }
    
    /**
     * 〈一句话功能简述〉获得成立时间
     * 〈功能详细描述〉
     * 
     * @return establishDate
     * @see [类、类#方法、类#成员]
     */
    public Date getEstablishDate() {
        return establishDate;
    }
    
    /**
     * 〈一句话功能简述〉设置成立时间
     * 〈功能详细描述〉
     * 
     * @param establishDate 成立时间
     * @see [类、类#方法、类#成员]
     */
    public void setEstablishDate(Date establishDate) {
        this.establishDate = establishDate;
    }
    
    /**
     * 〈一句话功能简述〉获得开户行
     * 〈功能详细描述〉
     * 
     * @return openAccountBank
     * @see [类、类#方法、类#成员]
     */
    public String getOpenAccountBank() {
        return openAccountBank;
    }
    
    /**
     * 〈一句话功能简述〉设置开户行
     * 〈功能详细描述〉
     * 
     * @param openAccountBank 开户行
     * @see [类、类#方法、类#成员]
     */
    public void setOpenAccountBank(String openAccountBank) {
        this.openAccountBank = openAccountBank;
    }
    
    /**
     * 〈一句话功能简述〉获得工商注册号
     * 〈功能详细描述〉
     * 
     * @return registrationNo
     * @see [类、类#方法、类#成员]
     */
    public String getRegistrationNo() {
        return registrationNo;
    }
    
    /**
     * 〈一句话功能简述〉设置工商注册号
     * 〈功能详细描述〉
     * 
     * @param registrationNo 工商注册号
     * @see [类、类#方法、类#成员]
     */
    public void setRegistrationNo(String registrationNo) {
        this.registrationNo = registrationNo;
    }
    
    /**
     * 〈一句话功能简述〉获得主营业务
     * 〈功能详细描述〉
     * 
     * @return professionalWork
     * @see [类、类#方法、类#成员]
     */
    public String getProfessionalWork() {
        return professionalWork;
    }
    
    /**
     * 〈一句话功能简述〉设置主营业务
     * 〈功能详细描述〉
     * 
     * @param professionalWork 主营业务
     * @see [类、类#方法、类#成员]
     */
    public void setProfessionalWork(String professionalWork) {
        this.professionalWork = professionalWork;
    }
    
    /**
     * 〈一句话功能简述〉获得税务证号
     * 〈功能详细描述〉
     * 
     * @return taxationNo
     * @see [类、类#方法、类#成员]
     */
    public String getTaxationNo() {
        return taxationNo;
    }
    
    /**
     * 〈一句话功能简述〉设置税务证号
     * 〈功能详细描述〉
     * 
     * @param taxationNo 税务证号
     * @see [类、类#方法、类#成员]
     */
    public void setTaxationNo(String taxationNo) {
        this.taxationNo = taxationNo;
    }
    
    /**
     * 〈一句话功能简述〉获得经营区域
     * 〈功能详细描述〉
     * 
     * @return operateArea
     * @see [类、类#方法、类#成员]
     */
    public String getOperateArea() {
        return operateArea;
    }
    
    /**
     * 〈一句话功能简述〉设置经营区域
     * 〈功能详细描述〉
     * 
     * @param operateArea 经营区域
     * @see [类、类#方法、类#成员]
     */
    public void setOperateArea(String operateArea) {
        this.operateArea = operateArea;
    }
    
    /**
     * 〈一句话功能简述〉获得经营地址
     * 〈功能详细描述〉
     * 
     * @return address
     * @see [类、类#方法、类#成员]
     */
    public String getAddress() {
        return address;
    }
    
    /**
     * 〈一句话功能简述〉设置经营地址
     * 〈功能详细描述〉
     * 
     * @param address 经营地址
     * @see [类、类#方法、类#成员]
     */
    public void setAddress(String address) {
        this.address = address;
    }
    
    /**
     * 〈一句话功能简述〉获得网址
     * 〈功能详细描述〉
     * 
     * @return networkAddr
     * @see [类、类#方法、类#成员]
     */
    public String getNetworkAddr() {
        return networkAddr;
    }
    
    /**
     * 〈一句话功能简述〉设置网址
     * 〈功能详细描述〉
     * 
     * @param networkAddr 网址
     * @see [类、类#方法、类#成员]
     */
    public void setNetworkAddr(String networkAddr) {
        this.networkAddr = networkAddr;
    }
    
    /**
     * 〈一句话功能简述〉获得车辆数量
     * 〈功能详细描述〉
     * 
     * @return vehicleNum
     * @see [类、类#方法、类#成员]
     */
    public BigDecimal getVehicleNum() {
        return vehicleNum;
    }
    
    /**
     * 〈一句话功能简述〉设置车辆数量
     * 〈功能详细描述〉
     * 
     * @param vehicleNum 车辆数量
     * @see [类、类#方法、类#成员]
     */
    public void setVehicleNum(BigDecimal vehicleNum) {
        this.vehicleNum = vehicleNum;
    }
    
    /**
     * 〈一句话功能简述〉获得联系电话
     * 〈功能详细描述〉
     * 
     * @return telephone
     * @see [类、类#方法、类#成员]
     */
    public String getTelephone() {
        return telephone;
    }
    
    /**
     * 〈一句话功能简述〉设置联系电话
     * 〈功能详细描述〉
     * 
     * @param telephone 联系电话
     * @see [类、类#方法、类#成员]
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
