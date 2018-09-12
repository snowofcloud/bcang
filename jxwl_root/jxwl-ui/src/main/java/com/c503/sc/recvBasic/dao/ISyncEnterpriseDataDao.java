package com.c503.sc.recvBasic.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.c503.sc.jxwl.vehiclemonitor.bean.EnterpriseEntity;

/**
 * 同步企业信息到业务系统中的 T_ENTERPRISE
 * 
 * @author Administrator
 */
@Repository
public interface ISyncEnterpriseDataDao {

	/**
	 * 查询所有化工企业的企业法人代码
	 * 
	 * @return List<String>
	 * @throws Exception Exception
	 */
	@Select("select corporate_no from t_enterprise where enterprise_type = #{enpType} and remove = '0'")
	List<String> findAllCorporateNo(@Param("enpType") String enpType)
			throws Exception;

	/**
	 * 对T_ENTERPRISE数据库中不在在的企业信息进行save
	 * 
	 * @param enp EnterpriseEntity
	 * @throws Exception Exception
	 */
	void saveEnpInfo(EnterpriseEntity enp) throws Exception;

	/**
	 * 对数据库中T_ENTERPRISE已存在的企业进行update
	 * 
	 * @param enp EnterpriseEntity
	 * @throws Exception Exception
	 */
	void updateEnpInfo(EnterpriseEntity enp) throws Exception;

	/**
	 * 企业中的化工企业在公共平台中不存在, 则删除业务系统中的企业
	 * 
	 * @param enpCode
	 * @throws Exception
	 */
	@Delete("delete from t_enterprise where corporate_no = #{enpCode}")
	void deleteEnpInfo(@Param("enpCode") String enpCode) throws Exception;

}
