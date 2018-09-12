/**
 * 文件名：IWayBillStatisticalService.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-7-26
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.vehiclemonitor.service;

import java.util.List;
import java.util.Map;

import com.c503.sc.jxwl.vehiclemonitor.bean.WayBillStatisticalEntity;

/**
 * 〈一句话功能简述〉运单统计
 * 〈功能详细描述〉
 * 
 * @author mjw
 * @version [版本号, 2016-12-15]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface IWayBillStatisticalService {
    
    /**
     * 〈一句话功能简述〉统计企业分布、车辆分布、本年运单
     * 〈功能详细描述〉
     * 
     * @return Object
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
	Map<String, Object> findPieVal()
        throws Exception;
    
	/**
     * 〈一句话功能简述〉统计企业分布、车辆分布、本年运单
     * 〈功能详细描述〉
     * 
     * @return Object
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
	Map<String, Object> findPieValMon()
        throws Exception;
	
	/**
     * 〈一句话功能简述〉统计企业分布、车辆分布、本年运单
     * 〈功能详细描述〉
     * 
     * @return Object
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
	Map<String, Object> findPieValWeek()
        throws Exception;
    /**
     * 
     * 〈一句话功能简述〉分页查询统计数据
     * 〈功能详细描述〉
     * 
     * @param map page、rows、curDate、curMon、curThrMon、curYear
     * @return List<EnpEveryTimes>
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    List<WayBillStatisticalEntity> countAllByParams(Map<String, Object> map)
        throws Exception;
    
}
