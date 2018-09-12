/**
 * 文件名：LicenceEntity.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-8-30
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.transacation.bean;

import java.util.Date;

import com.c503.sc.base.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 
 * 新闻对象，应用于大屏展示
 * 
 * @author wangwenhuan
 * @version [版本号, 2016-12-13]
 * @see
 * @since
 */
public class LicenceEntity extends BaseEntity {
    /*
     * 企业名称 法人代码 车牌号 人名 身份证 从业资格类型 资质名称 到期日期 发证机关 黑名单状态 审核状态 附件 未通过原因
     */
    /** 序列号 */
    private static final long serialVersionUID = 567899923645324455L;
    
    /** 资质ID */
    private String id;
    
    /** 企业名称 */
    private String enterpriseName;
    
    /** 法人代码 */
    private String corporateNo;
    
    /** 车牌号 */
    private String licencePlateNo;
    
    /** 人名 */
    private String personName;
    
    /** 身份证 */
    private String identificationCardNo;
    
    /** 从业资格类型 */
    private String occupationPersonType;
    
    /** 资质名称 */
    private String licenceName;
    
    /** 到期时间 */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date expireDate;
    
    /** 到期标识 */
    private String expireFlag;
    
    /** 发证机关 */
    private String licenceIssuingAuthority;
    
    /** 黑名单状态 */
    private String blackStatus;
    
    /** 审核状态 */
    private String verifyStatus;
    
    /** 附件 字符串 */
    private String[] fileIds;
    
    /** 附件名称 */
    private String[] fileNames;
    
    /** 未通过原因 */
    private String rejectReason;
    
    /** 资质类型 */
    private String licenceType;
    
    /**
     * 〈一句话功能简述〉资质类型
     * 〈功能详细描述〉
     * 
     * @return 资质类型
     * @see [类、类#方法、类#成员]
     */
    public String getLicenceType() {
        return licenceType;
    }
    
    /**
     * 〈一句话功能简述〉资质类型
     * 〈功能详细描述〉
     * 
     * @param licenceType 资质类型
     * @see [类、类#方法、类#成员]
     */
    public void setLicenceType(String licenceType) {
        this.licenceType = licenceType;
    }
    
    /**
     * 〈一句话功能简述〉附件名称
     * 〈功能详细描述〉
     * 
     * @return 附件名称
     * @see [类、类#方法、类#成员]
     */
    public String[] getFileNames() {
        return fileNames;
    }
    
    /**
     * 〈一句话功能简述〉附件名称
     * 〈功能详细描述〉
     * 
     * @param fileNames 附件名称
     * @see [类、类#方法、类#成员]
     */
    public void setFileNames(String[] fileNames) {
        this.fileNames = fileNames;
    }
    
    /**
     * 
     * 〈一句话功能简述〉获取资质id 〈功能详细描述〉
     * 
     * @return enterpriseName 企业类型名字
     * @see [类、类#方法、类#成员]
     */
    public String getId() {
        return id;
    }
    
    /**
     * 
     * 〈一句话功能简述〉设置资质id 〈功能详细描述〉
     * 
     * @param id id
     * @see [类、类#方法、类#成员]
     */
    public void setId(String id) {
        this.id = id;
    }
    
    /**
     * 
     * 〈一句话功能简述〉获取企业名字 〈功能详细描述〉
     * 
     * @return enterpriseName 企业类型名字
     * @see [类、类#方法、类#成员]
     */
    public String getEnterpriseName() {
        return enterpriseName;
    }
    
    /**
     * 
     * 〈一句话功能简述〉设置企业名字 〈功能详细描述〉
     * 
     * @param enterpriseName
     *            企业类型名字
     * @see [类、类#方法、类#成员]
     */
    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }
    
    /**
     * 
     * 〈一句话功能简述〉获取企业法人代码 〈功能详细描述〉
     * 
     * @return corporateNo 企业类型名字
     * @see [类、类#方法、类#成员]
     */
    public String getCorporateNo() {
        return corporateNo;
    }
    
    /**
     * 
     * 〈一句话功能简述〉设置企业名字 〈功能详细描述〉
     * 
     * @param corporateNo corporateNo
     *            企业类型名字
     * @see [类、类#方法、类#成员]
     */
    public void setCorporateNo(String corporateNo) {
        this.corporateNo = corporateNo;
    }
    
    /**
     * 
     * 〈一句话功能简述〉获取企业法人代码 〈功能详细描述〉
     * 
     * @return corporateNo 企业类型名字
     * @see [类、类#方法、类#成员]
     */
    public String getLicencePlateNo() {
        return licencePlateNo;
    }
    
    /**
     * 
     * 〈一句话功能简述〉设置企业名字 〈功能详细描述〉
     * 
     * @param licencePlateNo licencePlateNo
     * @see [类、类#方法、类#成员]
     */
    public void setLicencePlateNo(String licencePlateNo) {
        this.licencePlateNo = licencePlateNo;
    }
    
    /**
     * 
     * 〈一句话功能简述〉获取企业法人代码 〈功能详细描述〉
     * 
     * @return corporateNo 企业类型名字
     * @see [类、类#方法、类#成员]
     */
    public String getPersonName() {
        return personName;
    }
    
    /**
     * 
     * 〈一句话功能简述〉设置企业名字 〈功能详细描述〉
     * 
     * @param personName
     *            personName
     * @see [类、类#方法、类#成员]
     */
    public void setPersonName(String personName) {
        this.personName = personName;
    }
    
    /**
     * 
     * 〈一句话功能简述〉获取企业法人代码 〈功能详细描述〉
     * 
     * @return identificationCardNo identificationCardNo
     * @see [类、类#方法、类#成员]
     */
    public String getIdentificationCardNo() {
        return identificationCardNo;
    }
    
    /**
     * 
     * 〈一句话功能简述〉设置企业名字 〈功能详细描述〉
     * 
     * @param identificationCardNo
     *            identificationCardNo
     * @see [类、类#方法、类#成员]
     */
    public void setIdentificationCardNo(String identificationCardNo) {
        this.identificationCardNo = identificationCardNo;
    }
    
    /**
     * 
     * 〈一句话功能简述〉获取企业法人代码 〈功能详细描述〉
     * 
     * @return occupationPersonType occupationPersonType
     * @see [类、类#方法、类#成员]
     */
    public String getOccupationPersonType() {
        return occupationPersonType;
    }
    
    /**
     * 
     * 〈一句话功能简述〉设置企业名字 〈功能详细描述〉
     * 
     * @param occupationPersonType
     *            occupationPersonType
     * @see [类、类#方法、类#成员]
     */
    public void setOccupationPersonType(String occupationPersonType) {
        this.occupationPersonType = occupationPersonType;
    }
    
    /**
     * 
     * 〈一句话功能简述〉获取企业法人代码 〈功能详细描述〉
     * 
     * @return licenceName licenceName
     * @see [类、类#方法、类#成员]
     */
    public String getLicenceName() {
        return licenceName;
    }
    
    /**
     * 
     * 〈一句话功能简述〉设置企业名字 〈功能详细描述〉
     * 
     * @param licenceName
     *            licenceName
     * @see [类、类#方法、类#成员]
     */
    public void setLicenceName(String licenceName) {
        this.licenceName = licenceName;
    }
    
    /**
     * 
     * 〈一句话功能简述〉获取expireFlag 〈功能详细描述〉
     * 
     * @return expireFlag expireFlag
     * @see [类、类#方法、类#成员]
     */
    public String getExpireFlag() {
        return expireFlag;
    }
    
    /**
     * 
     * 〈一句话功能简述〉设置expireFlag 〈功能详细描述〉
     * 
     * @param expireFlag
     *            expireFlag
     * @see [类、类#方法、类#成员]
     */
    public void setExpireFlag(String expireFlag) {
        this.expireFlag = expireFlag;
    }
    
    /**
     * 
     * 〈一句话功能简述〉获取企业法人代码 〈功能详细描述〉
     * 
     * @return expireDate expireDate
     * @see [类、类#方法、类#成员]
     */
    public Date getExpireDate() {
        return expireDate;
    }
    
    /**
     * 
     * 〈一句话功能简述〉设置expireFlag 〈功能详细描述〉
     * 
     * @param expireDate
     *            expireDate
     * @see [类、类#方法、类#成员]
     */
    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }
    
    /**
     * 
     * 〈一句话功能简述〉获取企业法人代码 〈功能详细描述〉
     * 
     * @return licenceIssuingAuthority licenceIssuingAuthority
     * @see [类、类#方法、类#成员]
     */
    public String getLicenceIssuingAuthority() {
        return licenceIssuingAuthority;
    }
    
    /**
     * 
     * 〈一句话功能简述〉设置企业名字 〈功能详细描述〉
     * 
     * @param licenceIssuingAuthority
     *            licenceIssuingAuthority
     * @see [类、类#方法、类#成员]
     */
    public void setLicenceIssuingAuthority(String licenceIssuingAuthority) {
        this.licenceIssuingAuthority = licenceIssuingAuthority;
    }
    
    /**
     * 
     * 〈一句话功能简述〉获取企业法人代码 〈功能详细描述〉
     * 
     * @return blackStatus blackStatus
     * @see [类、类#方法、类#成员]
     */
    public String getBlackStatus() {
        return blackStatus;
    }
    
    /**
     * 
     * 〈一句话功能简述〉设置企业名字 〈功能详细描述〉
     * 
     * @param blackStatus
     *            blackStatus
     * @see [类、类#方法、类#成员]
     */
    public void setBlackStatus(String blackStatus) {
        this.blackStatus = blackStatus;
    }
    
    /**
     * 
     * 〈一句话功能简述〉获取企业法人代码 〈功能详细描述〉
     * 
     * @return verifyStatus verifyStatus
     * @see [类、类#方法、类#成员]
     */
    public String getVerifyStatus() {
        return verifyStatus;
    }
    
    /**
     * 
     * 〈一句话功能简述〉设置企业名字 〈功能详细描述〉
     * 
     * @param verifyStatus
     *            verifyStatus
     * @see [类、类#方法、类#成员]
     */
    public void setVerifyStatus(String verifyStatus) {
        this.verifyStatus = verifyStatus;
    }
    
    /**
     * 
     * 〈一句话功能简述〉获取企业法人代码 〈功能详细描述〉
     * 
     * @return fileIds fileIds
     * @see [类、类#方法、类#成员]
     */
    public String[] getFileIds() {
        return fileIds;
    }
    
    /**
     * 
     * 〈一句话功能简述〉设置企业名字 〈功能详细描述〉
     * 
     * @param fileIds
     *            fileIds
     * @see [类、类#方法、类#成员]
     */
    public void setFileIds(String[] fileIds) {
        this.fileIds = fileIds;
    }
    
    /**
     * 
     * 〈一句话功能简述〉获取企业法人代码 〈功能详细描述〉
     * 
     * @return rejectReason rejectReason
     * @see [类、类#方法、类#成员]
     */
    public String getRejectReason() {
        return rejectReason;
    }
    
    /**
     * 
     * 〈一句话功能简述〉设置未通过原因 〈功能详细描述〉
     * 
     * @param rejectReason
     *            rejectReason
     * @see [类、类#方法、类#成员]
     */
    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }
    
    @Override
    protected Object getBaseEntity() {
        return this;
    }
}
