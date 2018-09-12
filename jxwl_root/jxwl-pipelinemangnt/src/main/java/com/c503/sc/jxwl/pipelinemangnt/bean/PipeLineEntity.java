package com.c503.sc.jxwl.pipelinemangnt.bean;

import com.c503.sc.base.entity.BaseEntity;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * 
 * @author
 * @version [版本号, 2017-1-3]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class PipeLineEntity extends BaseEntity {
    
    /** serialVersionUID */
    private static final long serialVersionUID = 1L;
    
    /** 管道/储罐编号 */
    private String pipeNo;
    
    /** 管道/储罐名称 */
    private String pipeName;
    
    /** 危险品名称 */
    private String dangerName;
    
    /** 危险品类型 */
    private String dangerType;
    
    /** 责任人 */
    private String dutyPerson;
    
    /** 流量 */
    private Integer flow;
    
    /** 联系电话 */
    private String telephone;
    
    /** 储罐存量 */
    private Integer stockAllowance;
    
    /** 储罐容量 */
    private Integer stockVolume;
    
    /** 管道OR储罐 */
    private String pipeType;
    
    /** 储罐液位 */
    private Integer liquidLevel;
    
    /** 材质 */
    private String material;
    
    /** 地址*/
    private String address;
    
    /**
     * 〈一句话功能简述〉 地址编号
     * 〈功能详细描述〉
     * 
     * @return  地址编号
     * @see [类、类#方法、类#成员]
     */
    public String getAddress() {
		return address;
	}
    
    /**
     * 〈一句话功能简述〉 地址编号
     * 〈功能详细描述〉
     * @param address 管道/储罐编号
     * @see [类、类#方法、类#成员]
     */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
     * 〈一句话功能简述〉管道/储罐编号
     * 〈功能详细描述〉
     * 
     * @return pipeNo  管道/储罐编号
     * @see [类、类#方法、类#成员]
     */
    public String getPipeNo() {
        return pipeNo;
    }
    
    /**
     * 〈一句话功能简述〉管道/储罐编号
     * 〈功能详细描述〉
     * 
     * @param pipeNo 管道/储罐编号
     * @see [类、类#方法、类#成员]
     */
    public void setPipeNo(String pipeNo) {
        this.pipeNo = pipeNo;
    }
    
    /**
     * 〈一句话功能简述〉管道/储罐名称
     * 〈功能详细描述〉
     * 
     * @return 管道/储罐名称
     * @see [类、类#方法、类#成员]
     */
    public String getPipeName() {
        return pipeName;
    }
    
    /**
     * 〈一句话功能简述〉管道/储罐名称
     * 〈功能详细描述〉
     * 
     * @param pipeName 管道/储罐名称
     * @see [类、类#方法、类#成员]
     */
    public void setPipeName(String pipeName) {
        this.pipeName = pipeName;
    }
    
    /**
     * 〈一句话功能简述〉危险品名称
     * 〈功能详细描述〉
     * 
     * @return 危险品名称
     * @see [类、类#方法、类#成员]
     */
    public String getDangerName() {
        return dangerName;
    }
    
    /**
     * 〈一句话功能简述〉危险品名称
     * 〈功能详细描述〉
     * 
     * @param dangerName 危险品名称
     * @see [类、类#方法、类#成 员]
     */
    public void setDangerName(String dangerName) {
        this.dangerName = dangerName;
    }
    
    /**
     * 〈一句话功能简述〉危险品类型
     * 〈功能详细描述〉
     * 
     * @return 危险品类型
     * @see [类、类#方法、类#成员]
     */
    public String getDangerType() {
        return dangerType;
    }
    
    /**
     * 〈一句话功能简述〉危险品类型
     * 〈功能详细描述〉
     * 
     * @param dangerType 危险品类型
     * @see [类、类#方法、类#成员]
     */
    public void setDangerType(String dangerType) {
        this.dangerType = dangerType;
    }
    
    /**
     * 〈一句话功能简述〉责任人
     * 〈功能详细描述〉
     * 
     * @return 责任人
     * @see [类、类#方法、类#成员]
     */
    public String getDutyPerson() {
        return dutyPerson;
    }
    
    /**
     * 〈一句话功能简述〉责任人
     * 〈功能详细描述〉
     * 
     * @param dutyPerson 责任人
     * @see [类、类#方法、类#成员]
     */
    public void setDutyPerson(String dutyPerson) {
        this.dutyPerson = dutyPerson;
    }
    
    /**
     * 〈一句话功能简述〉流量
     * 〈功能详细描述〉
     * 
     * @return 流量
     * @see [类、类#方法、类#成员]
     */
    public Integer getFlow() {
        return flow;
    }
    
    /**
     * 〈一句话功能简述〉流量
     * 〈功能详细描述〉
     * 
     * @param flow 流量
     * @see [类、类#方法、类#成员]
     */
    public void setFlow(Integer flow) {
        this.flow = flow;
    }
    
    /**
     * 〈一句话功能简述〉联系电话
     * 〈功能详细描述〉
     * 
     * @return 联系电话
     * @see [类、类#方法、类#成员]
     */
    public String getTelephone() {
        return telephone;
    }
    
    /**
     * 〈一句话功能简述〉 联系电话
     * 〈功能详细描述〉
     * 
     * @param telephone 联系电话
     * @see [类、类#方法、类#成员]
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    
    /**
     * 〈一句话功能简述〉储罐存量
     * 〈功能详细描述〉
     * 
     * @return 储罐存量
     * @see [类、类#方法、类#成员]
     */
    public Integer getStockAllowance() {
        return stockAllowance;
    }
    
    /**
     * 〈一句话功能简述〉储罐存量
     * 〈功能详细描述〉
     * 
     * @param stockAllowance 储罐存量
     * @see [类、类#方法、类#成员]
     */
    public void setStockAllowance(Integer stockAllowance) {
        this.stockAllowance = stockAllowance;
    }
    
    /**
     * 〈一句话功能简述〉储罐容量
     * 〈功能详细描述〉
     * 
     * @return 储罐容量
     * @see [类、类#方法、类#成员]
     */
    public Integer getStockVolume() {
        return stockVolume;
    }
    
    /**
     * 〈一句话功能简述〉储罐容量
     * 〈功能详细描述〉
     * 
     * @param stockVolume 储罐容量
     * @see [类、类#方法、类#成员]
     */
    public void setStockVolume(Integer stockVolume) {
        this.stockVolume = stockVolume;
    }
    
    /**
     * 〈一句话功能简述〉管道OR储罐
     * 〈功能详细描述〉
     * 
     * @return 管道OR储罐
     * @see [类、类#方法、类#成员]
     */
    public String getPipeType() {
        return pipeType;
    }
    
    /**
     * 〈一句话功能简述〉管道OR储罐
     * 〈功能详细描述〉
     * 
     * @param pipeType 管道OR储罐
     * @see [类、类#方法、类#成员]
     */
    public void setPipeType(String pipeType) {
        this.pipeType = pipeType;
    }
    
    /**
     * 〈一句话功能简述〉储罐液位
     * 〈功能详细描述〉
     * 
     * @return 储罐液位
     * @see [类、类#方法、类#成员]
     */
    public Integer getLiquidLevel() {
        return liquidLevel;
    }
    
    /**
     * 〈一句话功能简述〉储罐液位
     * 〈功能详细描述〉
     * 
     * @param liquidLevel 储罐液位
     * @see [类、类#方法、类#成员]
     */
    public void setLiquidLevel(Integer liquidLevel) {
        this.liquidLevel = liquidLevel;
    }
    
    /**
     * 〈一句话功能简述〉材质
     * 〈功能详细描述〉
     * 
     * @return 材质
     * @see [类、类#方法、类#成员]
     */
    public String getMaterial() {
        return material;
    }
    
    /**
     * 〈一句话功能简述〉材质
     * 〈功能详细描述〉
     * 
     * @param material 材质
     * @see [类、类#方法、类#成员]
     */
    public void setMaterial(String material) {
        this.material = material;
    }
    
    @Override
    protected Object getBaseEntity() {
        return this;
    }
    
}
