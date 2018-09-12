/**
 * 文件名：OccupationPersonEntity.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-7-20
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.vehiclemonitor.bean;

import com.c503.sc.base.entity.BaseEntity;

/**
 * 
 * 〈一句话功能简述〉从业人员信息实体类
 * 〈功能详细描述〉
 * 
 * @author guqh
 * @version [版本号, 2016-7-20]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class OccupationPersonEntity extends BaseEntity {
    
    /** 序列号 */
    private static final long serialVersionUID = 915934626764917991L;
    
    /** 企业ID */
    private String enterpriseId;
    
    /** 从业人员类型 */
    private String occupationPersonType;
    
    /** 姓名 */
    private String personName;
    
    /** 性别 */
    private String personSex;
    
    /** 民族 */
    private String nation;
    
    /** 文化程度 */
    private String educationDegree;
    
    /** 身份证号码 */
    private String identificationCardNo;
    
    /** 联系方式 */
    private String telephone;
    
    /** 犯罪记录 */
    private String crimeRecord;
    
    /** 重大交通事故 */
    private String trafficAccident;
    
    /** 准驾车型 */
    private String driveVehicleType;
    
    /** 从业证编号 */
    private String occupationNo;
    
    /** 驾驶证编号 */
    private String driveCardNo;
    
    /** 健康状况 */
    private String healthCondition;
    
    /** 法人代码 */
    private String corporateNo;
    
    /** 企业名称 */
    private String enterpriseName;
    
    /** 行号 */
    private int rowNum;
    
    /** 驾驶员姓名 */
    private String name;
    
    /** 黑名单状态*/
    private String blackStatus;
    
    
    
    /**
     *〈一句话功能简述〉获取blackStatus
     * 〈功能详细描述〉
     * @return blackStatus blackStatus
     * @see  [类、类#方法、类#成员]
     */
    public String getBlackStatus() {
		return blackStatus;
	}
    /**
     *〈一句话功能简述〉设置blackStatus
     * 〈功能详细描述〉
     * @param blackStatus blackStatus
     * @see  [类、类#方法、类#成员]
     */
	public void setBlackStatus(String blackStatus) {
		this.blackStatus = blackStatus;
	}

	/**
     *〈一句话功能简述〉获取驾驶员姓名
     * 〈功能详细描述〉
     * @return name
     * @see  [类、类#方法、类#成员]
     */
    public String getName() {
        return name;
    }

    /**
     *〈一句话功能简述〉设置驾驶员姓名
     * 〈功能详细描述〉
     * @param name name
     * @see  [类、类#方法、类#成员]
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * 〈一句话功能简述〉获得rowNum
     * 〈功能详细描述〉
     * 
     * @return rowNum
     * @see [类、类#方法、类#成员]
     */
    public int getRowNum() {
        return rowNum;
    }
    
    /**
     * 〈一句话功能简述〉设置rowNum
     * 〈功能详细描述〉
     * 
     * @param rowNum rowNum
     * @see [类、类#方法、类#成员]
     */
    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }
    
    /**
     * 〈一句话功能简述〉获得enterpriseName
     * 〈功能详细描述〉
     * 
     * @return enterpriseName
     * @see [类、类#方法、类#成员]
     */
    public String getEnterpriseName() {
        return enterpriseName;
    }
    
    /**
     * 〈一句话功能简述〉设置enterpriseName
     * 〈功能详细描述〉
     * 
     * @param enterpriseName enterpriseName
     * @see [类、类#方法、类#成员]
     */
    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
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
     * 〈一句话功能简述〉获得从业人员类型
     * 〈功能详细描述〉
     * 
     * @return occupationPersonType
     * @see [类、类#方法、类#成员]
     */
    public String getOccupationPersonType() {
        return occupationPersonType;
    }
    
    /**
     * 〈一句话功能简述〉设置从业人员类型
     * 〈功能详细描述〉
     * 
     * @param occupationPersonType 从业人员类型
     * @see [类、类#方法、类#成员]
     */
    public void setOccupationPersonType(String occupationPersonType) {
        this.occupationPersonType = occupationPersonType;
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
     * 
     * @param personName 姓名
     * @see [类、类#方法、类#成员]
     */
    public void setPersonName(String personName) {
        this.personName = personName;
        this.name = personName;
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
     * 
     * @param personSex 性别
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
     * 
     * @param nation 民族
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
     * 
     * @param educationDegree 文化程度
     * @see [类、类#方法、类#成员]
     */
    public void setEducationDegree(String educationDegree) {
        this.educationDegree = educationDegree;
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
     * 
     * @param identificationCardNo 身份证号码
     * @see [类、类#方法、类#成员]
     */
    public void setIdentificationCardNo(String identificationCardNo) {
        this.identificationCardNo = identificationCardNo;
    }
    
    /**
     * 〈一句话功能简述〉获得联系方式
     * 〈功能详细描述〉
     * 
     * @return telephone
     * @see [类、类#方法、类#成员]
     */
    public String getTelephone() {
        return telephone;
    }
    
    /**
     * 〈一句话功能简述〉设置联系方式
     * 〈功能详细描述〉
     * 
     * @param telephone 联系方式
     * @see [类、类#方法、类#成员]
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    
    /**
     * 〈一句话功能简述〉获得犯罪记录
     * 〈功能详细描述〉
     * 
     * @return crimeRecord
     * @see [类、类#方法、类#成员]
     */
    public String getCrimeRecord() {
        return crimeRecord;
    }
    
    /**
     * 〈一句话功能简述〉设置犯罪记录
     * 〈功能详细描述〉
     * 
     * @param crimeRecord 犯罪记录
     * @see [类、类#方法、类#成员]
     */
    public void setCrimeRecord(String crimeRecord) {
        this.crimeRecord = crimeRecord;
    }
    
    /**
     * 〈一句话功能简述〉获得重大交通事故
     * 〈功能详细描述〉
     * 
     * @return trafficAccident
     * @see [类、类#方法、类#成员]
     */
    public String getTrafficAccident() {
        return trafficAccident;
    }
    
    /**
     * 〈一句话功能简述〉设置重大交通事故
     * 〈功能详细描述〉
     * 
     * @param trafficAccident 重大交通事故
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
     * 
     * @param driveVehicleType 准驾车型
     * @see [类、类#方法、类#成员]
     */
    public void setDriveVehicleType(String driveVehicleType) {
        this.driveVehicleType = driveVehicleType;
    }
    
    /**
     * 〈一句话功能简述〉获得从业证编号
     * 〈功能详细描述〉
     * 
     * @return occupationNo
     * @see [类、类#方法、类#成员]
     */
    public String getOccupationNo() {
        return occupationNo;
    }
    
    /**
     * 〈一句话功能简述〉设置从业证编号
     * 〈功能详细描述〉
     * 
     * @param occupationNo 从业证编号
     * @see [类、类#方法、类#成员]
     */
    public void setOccupationNo(String occupationNo) {
        this.occupationNo = occupationNo;
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
     * 
     * @param driveCardNo 驾驶证编号
     * @see [类、类#方法、类#成员]
     */
    public void setDriveCardNo(String driveCardNo) {
        this.driveCardNo = driveCardNo;
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
     * 
     * @param healthCondition 健康状况
     * @see [类、类#方法、类#成员]
     */
    public void setHealthCondition(String healthCondition) {
        this.healthCondition = healthCondition;
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
    
    @Override
    protected Object getBaseEntity() {
        return this;
    }
}
