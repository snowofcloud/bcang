/**
 * 文件名：IImageShowController.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-8-31
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.transacation.service;

import java.util.Map;

import com.c503.sc.jxwl.zcpt.bean.SolveRoute;

/**
 * 〈一句话功能简述〉园区区域 〈功能详细描述〉
 * 
 * @author zz
 * @version [版本号, 2016-11-21]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */

public interface ISolveRouteService {
	/**
	 * 〈一句话功能简述〉保存路径规划 〈功能详细描述〉
	 * 
	 * @param solveRoute
	 *            solveRoute
	 * @throws Exception
	 *             Exception
	 * @see [类、类#方法、类#成员]
	 */
	void saveSolveRoute(SolveRoute solveRoute) throws Exception;

	/**
	 * 〈一句话功能简述〉查询车辆最近的路径规划 〈功能详细描述〉
	 * 
	 * @param carrierName
	 *            carrierName
	 * @return SolveRoute
	 * @throws Exception
	 *             Exception
	 * @see [类、类#方法、类#成员]
	 */
	SolveRoute findLastRoute(String carrierName) throws Exception;

	/**
	 * 〈一句话功能简述〉根据运单号查询路径规划 〈功能详细描述〉
	 * 
	 * @param waybillId
	 *            waybillId
	 * @return SolveRoute
	 * @throws Exception
	 *             Exception
	 * @see [类、类#方法、类#成员]
	 */
	SolveRoute findRouteByWaybillId(String waybillId) throws Exception;

	/**
	 * 设置障碍参数 应该包括区域障碍、线路障碍、点障碍
	 * 
	 * @return 障碍参数
	 */
	 Map<String, Object> setBarriersArgs();
}
