package com.c503.sc.jxwl.zcpt.bean;

import java.util.Date;

import com.c503.sc.base.entity.BaseEntity;

/**
 * 
 * 跨域记录实体对象
 * 
 * @author huangtw
 * @version [版本号, 2016-11-10]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class AcrossLogEntity extends BaseEntity{
    
    private static final long serialVersionUID = 1L;

    /**区域类型-行政区域*/
	public static final String ADMINISTRATIVE_AREA = "171001001";
	
	/**区域类型-限制区域*/
	public static final String LIMIT_AREA = "171001002";
	
	/**区域类型-自定义区域*/
    public static final String USER_DEFINED_AREA = "171001003";
    
	/**车牌号*/
	private String carrierName;
	
	/**上一个区域ID*/
	private String preAreaId;
	
	/**当前区域ID*/
	private String areaId;
	
	/**当前区域类型：行政区域(171001001)、限制区域(171001002)*/
	private String areaType;
	
	/**跨域时间*/
	private Date logTime;
	
	@Override
	protected Object getBaseEntity() {
		return this;
	}

	public String getCarrierName() {
		return carrierName;
	}

	public void setCarrierName(String carrierName) {
		this.carrierName = carrierName;
	}

	public String getPreAreaId() {
		return preAreaId;
	}

	public void setPreAreaId(String preAreaId) {
		this.preAreaId = preAreaId;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

    public String getAreaType() {
		return areaType;
	}

	public void setAreaType(String areaType) {
		this.areaType = areaType;
	}

	public Date getLogTime() {
		return logTime;
	}

	public void setLogTime(Date logTime) {
		this.logTime = logTime;
	}
	
	@Override
	public String toString() {
		addKVFieldStyles("carrierName", carrierName);
		addKVFieldStyles("preAreaId", preAreaId);
		addKVFieldStyles("areaId", areaId);
		addKVFieldStyles("areaType", areaType);
		addKVFieldStyles("logTime", logTime);
		return super.toString();
	}
	
}

