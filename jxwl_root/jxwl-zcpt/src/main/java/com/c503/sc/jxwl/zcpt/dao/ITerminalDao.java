/**
 * 文件名：ITerminalDao.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2017-7-13 
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.zcpt.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.c503.sc.jxwl.zcpt.bean.TerminalParamEntity;
import com.c503.sc.jxwl.zcpt.bean.WayBillEntity;

@Repository(value = "terminalDao")
public interface ITerminalDao {

	List<TerminalParamEntity> findTerminalParamById(@Param("id") String id)
			throws Exception;

	void saveTerminalParam(TerminalParamEntity entity) throws Exception;

	void updateTerminalParam(TerminalParamEntity entity) throws Exception;

	/**
	 * 〈一句话功能简述〉通过账号查询车牌号 〈功能详细描述〉
	 * 
	 * @param String
	 *            account
	 * @return List<String> carrierNames
	 * @throws Exception
	 */
	List<WayBillEntity> findCarrierNameByAccount(@Param("account") String account)
			throws Exception;
}
