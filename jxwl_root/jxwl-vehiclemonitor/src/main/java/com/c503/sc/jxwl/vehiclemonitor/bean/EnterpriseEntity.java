/**
 * 文件名：EnterpriseEntity.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-7-20
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.vehiclemonitor.bean;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.c503.sc.base.entity.BaseEntity;
import com.c503.sc.jxwl.common.constant.DictConstant;
import com.c503.sc.jxwl.orgdata.bean.SysOrganTypeEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 
 * 〈一句话功能简述〉物流企业信息实体类 〈功能详细描述〉
 * 
 * @author guqh
 * @version [版本号, 2016-7-20]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class EnterpriseEntity extends BaseEntity {
    
    /** 序列号 */
    private static final long serialVersionUID = -3626321030601592139L;
    
    /** 法人代码 */
    private String corporateNo;
    
    /** 企业名称 */
    private String enterpriseName;
    
    /** 企业类型 */
    private String enterpriseType;
    
    /** 企业类型 */
    private String enterpriseTypeFlag;
    
    /** 法人代表 */
    private String corporateRep;
    
    /** 企业简介 */
    private String enterpriseIntroduce;
    
    /** 登记机关 */
    private String registerOffice;
    
    /** 注册资本 */
    private BigDecimal logonCapital;
    
    /** 主营路线 */
    private String majorBusinessRoute;
    
    /** 成立时间 */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date establishDate;
    
    /** 开户行 */
    private String openAccountBank;
    
    /** 工商注册号 */
    private String registrationNo;
    
    /** 主营业务 */
    private String professionalWork;
    
    /** 税务证号 */
    private String taxationNo;
    
    /** 经营区域 */
    private String operateArea;
    
    /** 经营地址 */
    private String address;
    
    /** 网址 */
    private String networkAddr;
    
    /** 车辆数量 */
    private BigDecimal vehicleNum;
    
    /** 联系电话 */
    private String telephone;
    
    /** 附件 ids 数组 */
    private String[] fileIds;
    
    /** 附件名称 */
    private String[] fileNames;
    
    /** 企业名称 */
    private String name;
    
    /** 行号 */
    private int rowNum;
    
    /** 附件 字符串 */
    private String fileIds2;
    
    /** 黑名单状态 */
    private String blackStatus;
    
    /** 跨域类型 */
    private String crossDomainType4Enter;
    
    /** 成立时间 */
    private String establishTime;
    
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
     * 
     * 〈一句话功能简述〉获取blackStatus
     * 
     * @return blackStatus blackStatus
     * @see [类、类#方法、类#成员]
     */
    public String getBlackStatus() {
        return blackStatus;
    }
    
    /**
     * 
     * 〈一句话功能简述〉设blackStatus 〈功能详细描述〉
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
     * 〈一句话功能简述〉获取企业类型标志 〈功能详细描述〉 TODO 后面应该去掉此属性，
     * 
     * @return enterpriseTypeFlag 企业类型标志
     * @see [类、类#方法、类#成员]
     */
    public String getEnterpriseTypeFlag() {
        return enterpriseTypeFlag;
    }
    
    /**
     * 
     * 〈一句话功能简述〉设置企业类型标志 〈功能详细描述〉
     * 
     * @param enterpriseTypeFlag
     *            企业类型标志
     * @see [类、类#方法、类#成员]
     */
    public void setEnterpriseTypeFlag(String enterpriseTypeFlag) {
        this.enterpriseTypeFlag = enterpriseTypeFlag;
    }
    
    /**
     * 
     * 〈一句话功能简述〉获取附件名称 〈功能详细描述〉
     * 
     * @return 企业上传附件的名称
     * @see [类、类#方法、类#成员]
     */
    public String[] getFileNames() {
        return fileNames;
    }
    
    /**
     * 
     * 〈一句话功能简述〉设置附件的名称 〈功能详细描述〉
     * 
     * @param fileNames
     *            附件的名称
     * @see [类、类#方法、类#成员]
     */
    public void setFileNames(String[] fileNames) {
        this.fileNames = fileNames;
    }
    
    /**
     * 〈一句话功能简述〉获得rowNum 〈功能详细描述〉
     * 
     * @return rowNum
     * @see [类、类#方法、类#成员]
     */
    public int getRowNum() {
        return rowNum;
    }
    
    /**
     * 〈一句话功能简述〉设置rowNum 〈功能详细描述〉
     * 
     * @param rowNum
     *            rowNum
     * @see [类、类#方法、类#成员]
     */
    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }
    
    /**
     * 〈一句话功能简述〉获得fileIds 〈功能详细描述〉
     * 
     * @return fileIds
     * @see [类、类#方法、类#成员]
     */
    public String[] getFileIds() {
        return fileIds;
    }
    
    /**
     * 〈一句话功能简述〉设置fileIds 〈功能详细描述〉
     * 
     * @param fileIds
     *            fileIds
     * @see [类、类#方法、类#成员]
     */
    public void setFileIds(String[] fileIds) {
        this.fileIds = fileIds;
    }
    
    /**
     * 〈一句话功能简述〉获得法人代码 〈功能详细描述〉
     * 
     * @return corporateNo
     * @see [类、类#方法、类#成员]
     */
    public String getCorporateNo() {
        return corporateNo;
    }
    
    /**
     * 〈一句话功能简述〉设置法人代码 〈功能详细描述〉
     * 
     * @param corporateNo
     *            法人代码
     * @see [类、类#方法、类#成员]
     */
    public void setCorporateNo(String corporateNo) {
        this.corporateNo = corporateNo;
    }
    
    /**
     * 〈一句话功能简述〉获得企业名称 〈功能详细描述〉
     * 
     * @return enterpriseName
     * @see [类、类#方法、类#成员]
     */
    public String getEnterpriseName() {
        return enterpriseName;
    }
    
    /**
     * 〈一句话功能简述〉设置企业名称 〈功能详细描述〉
     * 
     * @param enterpriseName
     *            企业名称
     * @see [类、类#方法、类#成员]
     */
    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
        this.name = enterpriseName;
    }
    
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
     * 〈一句话功能简述〉获得法人代表 〈功能详细描述〉
     * 
     * @return corporateRep
     * @see [类、类#方法、类#成员]
     */
    public String getCorporateRep() {
        return corporateRep;
    }
    
    /**
     * 〈一句话功能简述〉设置法人代表 〈功能详细描述〉
     * 
     * @param corporateRep
     *            法人代表
     * @see [类、类#方法、类#成员]
     */
    public void setCorporateRep(String corporateRep) {
        this.corporateRep = corporateRep;
    }
    
    /**
     * 〈一句话功能简述〉获得企业简介 〈功能详细描述〉
     * 
     * @return enterpriseIntroduce
     * @see [类、类#方法、类#成员]
     */
    public String getEnterpriseIntroduce() {
        return enterpriseIntroduce;
    }
    
    /**
     * 〈一句话功能简述〉设置企业简介 〈功能详细描述〉
     * 
     * @param enterpriseIntroduce
     *            企业简介
     * @see [类、类#方法、类#成员]
     */
    public void setEnterpriseIntroduce(String enterpriseIntroduce) {
        this.enterpriseIntroduce = enterpriseIntroduce;
    }
    
    /**
     * 〈一句话功能简述〉获得登记机关 〈功能详细描述〉
     * 
     * @return registerOffice
     * @see [类、类#方法、类#成员]
     */
    public String getRegisterOffice() {
        return registerOffice;
    }
    
    /**
     * 〈一句话功能简述〉设置登记机关 〈功能详细描述〉
     * 
     * @param registerOffice
     *            登记机关
     * @see [类、类#方法、类#成员]
     */
    public void setRegisterOffice(String registerOffice) {
        this.registerOffice = registerOffice;
    }
    
    /**
     * 〈一句话功能简述〉获得注册资本 〈功能详细描述〉
     * 
     * @return logonCapital
     * @see [类、类#方法、类#成员]
     */
    public BigDecimal getLogonCapital() {
        return logonCapital;
    }
    
    /**
     * 〈一句话功能简述〉设置注册资本 〈功能详细描述〉
     * 
     * @param logonCapital
     *            注册资本
     * @see [类、类#方法、类#成员]
     */
    public void setLogonCapital(BigDecimal logonCapital) {
        this.logonCapital = logonCapital;
    }
    
    /**
     * 〈一句话功能简述〉获得主营路线 〈功能详细描述〉
     * 
     * @return majorBusinessRoute
     * @see [类、类#方法、类#成员]
     */
    public String getMajorBusinessRoute() {
        return majorBusinessRoute;
    }
    
    /**
     * 〈一句话功能简述〉设置主营路线 〈功能详细描述〉
     * 
     * @param majorBusinessRoute
     *            主营路线
     * @see [类、类#方法、类#成员]
     */
    public void setMajorBusinessRoute(String majorBusinessRoute) {
        this.majorBusinessRoute = majorBusinessRoute;
    }
    
    /**
     * 〈一句话功能简述〉获得成立时间 〈功能详细描述〉
     * 
     * @return establishDate
     * @see [类、类#方法、类#成员]
     */
    public Date getEstablishDate() {
        return establishDate;
    }
    
    /**
     * 〈一句话功能简述〉设置成立时间 〈功能详细描述〉
     * 
     * @param establishDate
     *            成立时间
     * @see [类、类#方法、类#成员]
     */
    public void setEstablishDate(Date establishDate) {
        this.establishDate = establishDate;
        if (null != establishDate) {
            this.setEstablishTime(new SimpleDateFormat("yyyy-MM-dd").format(establishDate));
        }
    }
    
    /**
     * 〈一句话功能简述〉获得开户行 〈功能详细描述〉
     * 
     * @return openAccountBank
     * @see [类、类#方法、类#成员]
     */
    public String getOpenAccountBank() {
        return openAccountBank;
    }
    
    /**
     * 〈一句话功能简述〉设置开户行 〈功能详细描述〉
     * 
     * @param openAccountBank
     *            开户行
     * @see [类、类#方法、类#成员]
     */
    public void setOpenAccountBank(String openAccountBank) {
        this.openAccountBank = openAccountBank;
    }
    
    /**
     * 〈一句话功能简述〉获得工商注册号 〈功能详细描述〉
     * 
     * @return registrationNo
     * @see [类、类#方法、类#成员]
     */
    public String getRegistrationNo() {
        return registrationNo;
    }
    
    /**
     * 〈一句话功能简述〉设置工商注册号 〈功能详细描述〉
     * 
     * @param registrationNo
     *            工商注册号
     * @see [类、类#方法、类#成员]
     */
    public void setRegistrationNo(String registrationNo) {
        this.registrationNo = registrationNo;
    }
    
    /**
     * 〈一句话功能简述〉获得主营业务 〈功能详细描述〉
     * 
     * @return professionalWork
     * @see [类、类#方法、类#成员]
     */
    public String getProfessionalWork() {
        return professionalWork;
    }
    
    /**
     * 〈一句话功能简述〉设置主营业务 〈功能详细描述〉
     * 
     * @param professionalWork
     *            主营业务
     * @see [类、类#方法、类#成员]
     */
    public void setProfessionalWork(String professionalWork) {
        this.professionalWork = professionalWork;
    }
    
    /**
     * 〈一句话功能简述〉获得税务证号 〈功能详细描述〉
     * 
     * @return taxationNo
     * @see [类、类#方法、类#成员]
     */
    public String getTaxationNo() {
        return taxationNo;
    }
    
    /**
     * 〈一句话功能简述〉设置税务证号 〈功能详细描述〉
     * 
     * @param taxationNo
     *            税务证号
     * @see [类、类#方法、类#成员]
     */
    public void setTaxationNo(String taxationNo) {
        this.taxationNo = taxationNo;
    }
    
    /**
     * 〈一句话功能简述〉获得经营区域 〈功能详细描述〉
     * 
     * @return operateArea
     * @see [类、类#方法、类#成员]
     */
    public String getOperateArea() {
        return operateArea;
    }
    
    /**
     * 〈一句话功能简述〉设置经营区域 〈功能详细描述〉
     * 
     * @param operateArea
     *            经营区域
     * @see [类、类#方法、类#成员]
     */
    public void setOperateArea(String operateArea) {
        this.operateArea = operateArea;
    }
    
    /**
     * 〈一句话功能简述〉获得经营地址 〈功能详细描述〉
     * 
     * @return address
     * @see [类、类#方法、类#成员]
     */
    public String getAddress() {
        return address;
    }
    
    /**
     * 〈一句话功能简述〉设置经营地址 〈功能详细描述〉
     * 
     * @param address
     *            经营地址
     * @see [类、类#方法、类#成员]
     */
    public void setAddress(String address) {
        this.address = address;
    }
    
    /**
     * 〈一句话功能简述〉获得网址 〈功能详细描述〉
     * 
     * @return networkAddr
     * @see [类、类#方法、类#成员]
     */
    public String getNetworkAddr() {
        return networkAddr;
    }
    
    /**
     * 〈一句话功能简述〉设置网址 〈功能详细描述〉
     * 
     * @param networkAddr
     *            网址
     * @see [类、类#方法、类#成员]
     */
    public void setNetworkAddr(String networkAddr) {
        this.networkAddr = networkAddr;
    }
    
    /**
     * 〈一句话功能简述〉获得车辆数量 〈功能详细描述〉
     * 
     * @return vehicleNum
     * @see [类、类#方法、类#成员]
     */
    public BigDecimal getVehicleNum() {
        return vehicleNum;
    }
    
    /**
     * 〈一句话功能简述〉设置车辆数量 〈功能详细描述〉
     * 
     * @param vehicleNum
     *            车辆数量
     * @see [类、类#方法、类#成员]
     */
    public void setVehicleNum(BigDecimal vehicleNum) {
        this.vehicleNum = vehicleNum;
    }
    
    /**
     * 〈一句话功能简述〉获得联系电话 〈功能详细描述〉
     * 
     * @return telephone
     * @see [类、类#方法、类#成员]
     */
    public String getTelephone() {
        return telephone;
    }
    
    /**
     * 〈一句话功能简述〉设置联系电话 〈功能详细描述〉
     * 
     * @param telephone
     *            联系电话
     * @see [类、类#方法、类#成员]
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    
    /**
     * 〈一句话功能简述〉企业名称 〈功能详细描述〉
     * 
     * @return 企业名称
     * @see [类、类#方法、类#成员]
     */
    public String getName() {
        return name;
    }
    
    /**
     * 〈一句话功能简述〉企业名称 〈功能详细描述〉
     * 
     * @param name
     *            企业名称
     * @see [类、类#方法、类#成员]
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * 〈一句话功能简述〉附件id 〈功能详细描述〉
     * 
     * @return 附件id
     * @see [类、类#方法、类#成员]
     */
    public String getFileIds2() {
        return fileIds2;
    }
    
    /**
     * 〈一句话功能简述〉附件id 〈功能详细描述〉
     * 
     * @param fileIds2 fileIds2
     * @see [类、类#方法、类#成员]
     */
    public void setFileIds2(String fileIds2) {
        this.fileIds2 = fileIds2;
    }
    
    /**
     * 〈一句话功能简述〉成立时间
     * 〈功能详细描述〉
     * 
     * @return 成立时间
     * @see [类、类#方法、类#成员]
     */
    public String getEstablishTime() {
        return establishTime;
    }
    
    /**
     * 〈一句话功能简述〉成立时间
     * 〈功能详细描述〉
     * 
     * @param establishTime 成立时间
     * @see [类、类#方法、类#成员]
     */
    public void setEstablishTime(String establishTime) {
        this.establishTime = establishTime;
    }
    
    @Override
    protected Object getBaseEntity() {
        return this;
    }
    
    /**
     * 〈一句话功能简述〉把业务数据库的企业对象转化为认证数据库的机构对象 〈功能详细描述〉约束条件：1、
     * id相同，2、机构类型和企业类型相同，3、只转换物流机构和化工机构，政府机构不转换，4、系统id是物流系统id，5、发人代码对应机构代码
     * 
     * @param entity
     *            entity
     * @return reEntity
     * @see [类、类#方法、类#成员]
     */
    public SysOrganTypeEntity bean2auth(EnterpriseEntity entity) {
        SysOrganTypeEntity reEntity = new SysOrganTypeEntity();
        // 企业id==>机构ID
        reEntity.setId(getId());
        // 法人代码==>机构代码
        reEntity.setCode(entity.getCorporateNo());
        // 企业名称==>机构名称
        reEntity.setName(entity.getEnterpriseName());
        // 企业类型==>机构类型
        // 这里有个约束认证系统的机构类型ID(物流企业、化工企业)和业务系统的企业类型（物流企业、化工企业）的数字词典的值相同
        reEntity.setOrganTypeId(getEnterpriseType());
        // 根据企业类型设置机构的上级id(pid)
        if (DictConstant.ENTERPRISE_TYPE_CHEMICAL.equals(entity.getEnterpriseType())) {// 化工企业
            reEntity.setPid(DictConstant.CHEMICAL_PID);
        } else {// 物流企业
            reEntity.setPid(DictConstant.LOGISTICS_PID);
        }
        reEntity.setCreateBy(getCreateBy());
        reEntity.setUpdateBy(getUpdateBy());
        reEntity.setSysId(DictConstant.SYS_ID);
        return reEntity;
        
    }
}
