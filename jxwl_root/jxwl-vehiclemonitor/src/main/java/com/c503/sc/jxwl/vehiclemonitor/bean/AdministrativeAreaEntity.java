package com.c503.sc.jxwl.vehiclemonitor.bean;

import com.c503.sc.base.entity.BaseEntity;

/**
 * 
 * 行政区域实体类
 * 
 * @author huangtw
 * @version [版本号, 2016-11-10]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class AdministrativeAreaEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;
	/**行政区域名称*/
	private String areaName;
	/**行政区域ID*/
	private String areaId;
	/**坐标点集合*/
	private String points;
	@Override
	protected Object getBaseEntity() {
		return this;
	}
	
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public String getAreaId() {
		return areaId;
	}
	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
	public String getPoints() {
		return points;
	}
	public void setPoints(String points) {
		this.points = points;
	}
	
	@Override
	public String toString() {
		addKVFieldStyles("areaName", areaName);
		addKVFieldStyles("areaId", areaId);
		addKVFieldStyles("points", points);
		return super.toString();
	}

}
