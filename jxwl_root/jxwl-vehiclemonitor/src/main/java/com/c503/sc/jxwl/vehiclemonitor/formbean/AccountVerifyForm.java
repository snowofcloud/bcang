/**
 * 文件名：AccountVerifyForm.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-11-10
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.vehiclemonitor.formbean;

import java.util.Date;

import javax.validation.constraints.Pattern;

import com.c503.sc.base.formbean.PageForm;
import com.c503.sc.base.validation.Save;
import com.c503.sc.jxwl.common.constant.RegexpContant;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 
 * 〈一句话功能简述〉 账号审核信息FormBean 〈功能详细描述〉
 * 
 * @author wangwenhuan
 * @version [版本号, 2016-11-10]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class AccountVerifyForm extends PageForm {

	/** 序列号 */
	private static final long serialVersionUID = 3522634133186530342L;
	/****** T_REGISTER_INFO 字段 ****/

	/** registerId */
	private String registerId;
	
	/** 账号 */
	@Pattern(regexp = RegexpContant.ACCOUNT_REGEXP, message = "{accountVerifyForm.account}", groups = {
	        Save.class})
	private String account;

	/** 审核状态 */
	private String verifyStatus;

	/** 密码 */
	@Pattern(regexp = RegexpContant.ACCOUNT_REGEXP, message = "{accountVerifyForm.password}", groups = {
	        Save.class})
	private String password;

	/** 口令盐 */
	private String salt;

	/** 拒绝原因 */
	private String rejectReason;

	/** 从业人员ID */
	private String occupationId;
	
	/** 注册时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date registerTime;
    
	/****** T_OCCUPATION_PERSON 字段 ****/
	
    /** 姓名 */
	@Pattern(regexp = RegexpContant.NAME_REGEXP_MUST, message = "{accountVerifyForm.personName}", groups = { Save.class })
	private String personName;

	/** 手机号码 */
	private String telephone;

	/** 企业法人代码 */
	private String corporateNo;

	/** 身份证号码 */
	@Pattern(regexp = RegexpContant.IDCARD_REGEXP_MUST, message = "{accountVerifyForm.identificationCardNo}", groups = {
	        Save.class})
	private String identificationCardNo;

	/** 性别 */
	private String personSex;

	/** 民族 */
	private String nation;

	/** 文化程度 */
	private String educationDegree;

	/** 危险品运输从业证编号 */
	private String occupationNo;

	/** 健康状况 */
	private String healthCondition;

	/** 有无犯罪记录 */
	private String crimeRecord;

	/** 有无重大交通事故 */
	private String trafficAccident;

	/** 准驾车型 */
	private String driveVehicleType;

	/** 驾驶证编号 */
	private String driveCardNo;
	
	/** 所属物流企业名称 */
	private String enterpriseName;
	
	/** 机构 */
	private String orgId;
	
	/** regId */
	private String regId;
	
	/**
     * 〈一句话功能简述〉获得RegId
     * 〈功能详细描述〉
     * 
     * @return registerId
     * @see [类、类#方法、类#成员]
     */
	public String getRegId() {
		return regId;
	}
	/**
     * 〈一句话功能简述〉设置regId
     * 〈功能详细描述〉
     * @param regId regId
     * @see [类、类#方法、类#成员]
     */
	public void setRegId(String regId) {
		this.regId = regId;
	}
	/**
     * 〈一句话功能简述〉获得注册id
     * 〈功能详细描述〉
     * 
     * @return registerId
     * @see [类、类#方法、类#成员]
     */
	public String getRegisterId() {
		return registerId;
	}
	/**
     * 〈一句话功能简述〉设置注册id
     * 〈功能详细描述〉
     * @param registerId 注册id
     * @see [类、类#方法、类#成员]
     */
	public void setRegisterId(String registerId) {
		this.registerId = registerId;
	}
	/**
     * 〈一句话功能简述〉获得用户账号
     * 〈功能详细描述〉
     * 
     * @return account
     * @see [类、类#方法、类#成员]
     */
	public String getAccount() {
		return account;
	}
	/**
     * 〈一句话功能简述〉设置账号
     * 〈功能详细描述〉
     * @param account account
     * @see [类、类#方法、类#成员]
     */
	public void setAccount(String account) {
		this.account = account;
	}
	 /**
     * 〈一句话功能简述〉获得口令盐
     * 〈功能详细描述〉
     * 
     * @return salt
     * @see [类、类#方法、类#成员]
     */
	public String getSalt() {
		return salt;
	}
	/**
     * 〈一句话功能简述〉设置口令盐
     * 〈功能详细描述〉
     * @param salt salt
     * @see [类、类#方法、类#成员]
     */
	public void setSalt(String salt) {
		this.salt = salt;
	}
	 /**
     * 〈一句话功能简述〉获得密码
     * 〈功能详细描述〉
     * 
     * @return password
     * @see [类、类#方法、类#成员]
     */
	public String getPassword() {
		return password;
	}
	/**
     * 〈一句话功能简述〉设置密码
     * 〈功能详细描述〉
     * @param password password
     * @see [类、类#方法、类#成员]
     */
	public void setPassword(String password) {
		this.password = password;
	}

	 /**
     * 〈一句话功能简述〉获得姓名
     * 〈功能详细描述〉
     * 
     * @return personName
     * @see [类、类#方法、类#成员]
     */
	public String getPersonName() {
		return personName;
	}
	/**
     * 〈一句话功能简述〉设置姓名
     * 〈功能详细描述〉
     * @param personName personName
     * @see [类、类#方法、类#成员]
     */
	public void setPersonName(String personName) {
		this.personName = personName;
	}
	 /**
     * 〈一句话功能简述〉获得手机号码
     * 〈功能详细描述〉
     * 
     * @return telephone
     * @see [类、类#方法、类#成员]
     */
	public String getTelephone() {
		return telephone;
	}
	/**
     * 〈一句话功能简述〉设置手机号码
     * 〈功能详细描述〉
     * @param telephone telephone
     * @see [类、类#方法、类#成员]
     */
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	 /**
     * 〈一句话功能简述〉获得从业人员ID
     * 〈功能详细描述〉
     * 
     * @return occupationId
     * @see [类、类#方法、类#成员]
     */
	public String getOccupationId() {
		return occupationId;
	}
	/**
     * 〈一句话功能简述〉设置从业人员ID
     * 〈功能详细描述〉
     * @param occupationId occupationId
     * @see [类、类#方法、类#成员]
     */
	public void setOccupationId(String occupationId) {
		this.occupationId = occupationId;
	}

	
	 /**
     * 〈一句话功能简述〉获得注册时间
     * 〈功能详细描述〉
     * 
     * @return registerTime
     * @see [类、类#方法、类#成员]
     */
	public Date getRegisterTime() {
		return registerTime;
	}
	/**
     * 〈一句话功能简述〉设置注册时间
     * 〈功能详细描述〉
     * @param registerTime registerTime
     * @see [类、类#方法、类#成员]
     */
	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}
	 /**
     * 〈一句话功能简述〉获得企业法人代码
     * 〈功能详细描述〉
     * 
     * @return corporateNo
     * @see [类、类#方法、类#成员]
     */
	public String getCorporateNo() {
		return corporateNo;
	}
	/**
     * 〈一句话功能简述〉设置企业法人代码
     * 〈功能详细描述〉
     * @param corporateNo corporateNo
     * @see [类、类#方法、类#成员]
     */
	public void setCorporateNo(String corporateNo) {
		this.corporateNo = corporateNo;
	}
	 /**
     * 〈一句话功能简述〉获得身份证号码
     * 〈功能详细描述〉
     * 
     * @return identificationCardNo
     * @see [类、类#方法、类#成员]
     */
	public String getIdentificationCardNo() {
		return identificationCardNo;
	}
	/**
     * 〈一句话功能简述〉设置身份证号码
     * 〈功能详细描述〉
     * @param identificationCardNo identificationCardNo
     * @see [类、类#方法、类#成员]
     */
	public void setIdentificationCardNo(String identificationCardNo) {
		this.identificationCardNo = identificationCardNo;
	}
	 /**
     * 〈一句话功能简述〉获得拒绝原因
     * 〈功能详细描述〉
     * 
     * @return rejectReason
     * @see [类、类#方法、类#成员]
     */
	public String getRejectReason() {
		return rejectReason;
	}
	/**
     * 〈一句话功能简述〉设置拒绝原因
     * 〈功能详细描述〉
     * @param rejectReason rejectReason
     * @see [类、类#方法、类#成员]
     */
	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}
	 /**
     * 〈一句话功能简述〉获得审核状态
     * 〈功能详细描述〉
     * 
     * @return verifyStatus
     * @see [类、类#方法、类#成员]
     */
	public String getVerifyStatus() {
		return verifyStatus;
	}
	/**
     * 〈一句话功能简述〉设置审核状态
     * 〈功能详细描述〉
     * @param verifyStatus verifyStatus
     * @see [类、类#方法、类#成员]
     */
	public void setVerifyStatus(String verifyStatus) {
		this.verifyStatus = verifyStatus;
	}
	 /**
     * 〈一句话功能简述〉获得性别
     * 〈功能详细描述〉
     * 
     * @return personSex
     * @see [类、类#方法、类#成员]
     */
	public String getPersonSex() {
		return personSex;
	}
	/**
     * 〈一句话功能简述〉设置性别
     * 〈功能详细描述〉
     * @param personSex personSex
     * @see [类、类#方法、类#成员]
     */
	public void setPersonSex(String personSex) {
		this.personSex = personSex;
	}
	 /**
     * 〈一句话功能简述〉获得民族
     * 〈功能详细描述〉
     * 
     * @return nation
     * @see [类、类#方法、类#成员]
     */
	public String getNation() {
		return nation;
	}
	/**
     * 〈一句话功能简述〉设置民族
     * 〈功能详细描述〉
     * @param nation nation
     * @see [类、类#方法、类#成员]
     */
	public void setNation(String nation) {
		this.nation = nation;
	}
	 /**
     * 〈一句话功能简述〉获得文化程度 
     * 〈功能详细描述〉
     * 
     * @return educationDegree
     * @see [类、类#方法、类#成员]
     */
	public String getEducationDegree() {
		return educationDegree;
	}
	/**
     * 〈一句话功能简述〉设置文化程度 
     * 〈功能详细描述〉
     * @param educationDegree educationDegree
     * @see [类、类#方法、类#成员]
     */
	public void setEducationDegree(String educationDegree) {
		this.educationDegree = educationDegree;
	}
	 /**
     * 〈一句话功能简述〉获得危险品运输从业证编号
     * 〈功能详细描述〉
     * 
     * @return occupationNo
     * @see [类、类#方法、类#成员]
     */
	public String getOccupationNo() {
		return occupationNo;
	}
	/**
     * 〈一句话功能简述〉设置危险品运输从业证编号
     * 〈功能详细描述〉
     * @param occupationNo occupationNo
     * @see [类、类#方法、类#成员]
     */
	public void setOccupationNo(String occupationNo) {
		this.occupationNo = occupationNo;
	}
	 /**
     * 〈一句话功能简述〉获得健康状况
     * 〈功能详细描述〉
     * 
     * @return healthCondition
     * @see [类、类#方法、类#成员]
     */
	public String getHealthCondition() {
		return healthCondition;
	}
	/**
     * 〈一句话功能简述〉设置健康状况
     * 〈功能详细描述〉
     * @param healthCondition healthCondition
     * @see [类、类#方法、类#成员]
     */
	public void setHealthCondition(String healthCondition) {
		this.healthCondition = healthCondition;
	}
	 /**
     * 〈一句话功能简述〉获得有无犯罪记录
     * 〈功能详细描述〉
     * 
     * @return crimeRecord
     * @see [类、类#方法、类#成员]
     */
	public String getCrimeRecord() {
		return crimeRecord;
	}
	/**
     * 〈一句话功能简述〉设置有无犯罪记录
     * 〈功能详细描述〉
     * @param crimeRecord crimeRecord
     * @see [类、类#方法、类#成员]
     */
	public void setCrimeRecord(String crimeRecord) {
		this.crimeRecord = crimeRecord;
	}
	 /**
     * 〈一句话功能简述〉获得有无重大交通事故 
     * 〈功能详细描述〉
     * 
     * @return trafficAccident
     * @see [类、类#方法、类#成员]
     */
	public String getTrafficAccident() {
		return trafficAccident;
	}
	/**
     * 〈一句话功能简述〉设置有无重大交通事故 
     * 〈功能详细描述〉
     * @param trafficAccident trafficAccident
     * @see [类、类#方法、类#成员]
     */
	public void setTrafficAccident(String trafficAccident) {
		this.trafficAccident = trafficAccident;
	}
	 /**
     * 〈一句话功能简述〉获得准驾车型
     * 〈功能详细描述〉
     * 
     * @return driveVehicleType
     * @see [类、类#方法、类#成员]
     */
	public String getDriveVehicleType() {
		return driveVehicleType;
	}
	/**
     * 〈一句话功能简述〉设置准驾车型
     * 〈功能详细描述〉
     * @param driveVehicleType driveVehicleType
     * @see [类、类#方法、类#成员]
     */
	public void setDriveVehicleType(String driveVehicleType) {
		this.driveVehicleType = driveVehicleType;
	}
	 /**
     * 〈一句话功能简述〉获得驾驶证编号
     * 〈功能详细描述〉
     * 
     * @return driveCardNo
     * @see [类、类#方法、类#成员]
     */
	public String getDriveCardNo() {
		return driveCardNo;
	}
	/**
     * 〈一句话功能简述〉设置驾驶证编号
     * 〈功能详细描述〉
     * @param driveCardNo driveCardNo
     * @see [类、类#方法、类#成员]
     */
	public void setDriveCardNo(String driveCardNo) {
		this.driveCardNo = driveCardNo;
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
     * @param enterpriseName enterpriseName
     * @see [类、类#方法、类#成员]
     */
	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}
	 /**
     * 〈一句话功能简述〉获得机构orgId
     * 〈功能详细描述〉
     * 
     * @return orgId
     * @see [类、类#方法、类#成员]
     */
	public String getOrgId() {
		return orgId;
	}
	/**
     * 〈一句话功能简述〉设置机构orgId
     * 〈功能详细描述〉
     * @param orgId 注册orgId
     * @see [类、类#方法、类#成员]
     */
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	
}
