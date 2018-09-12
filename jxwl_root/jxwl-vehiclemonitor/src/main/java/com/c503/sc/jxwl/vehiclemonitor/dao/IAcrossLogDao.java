package com.c503.sc.jxwl.vehiclemonitor.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.c503.sc.base.dao.IBaseDao;
import com.c503.sc.jxwl.zcpt.bean.AcrossLogEntity;

@Repository(value = "acrossLogDao")
public interface IAcrossLogDao extends IBaseDao<AcrossLogEntity>{

	/**
	 * 获取车辆最新的跨域记录
	 * @param carrierName 车牌号
	 * @param areaType 区域类型
	 * @return 最新的跨域记录
	 */
	public AcrossLogEntity getLastAcrossLog(@Param("carrierName")String carrierName,@Param("areaType")String areaType);
}
