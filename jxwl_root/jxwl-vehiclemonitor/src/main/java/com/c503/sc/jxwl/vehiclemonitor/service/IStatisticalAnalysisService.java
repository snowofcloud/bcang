/**
 * 文件名：IStatisticalAnalysisService.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-7-26
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.vehiclemonitor.service;

import java.util.List;
import java.util.Map;

import com.c503.sc.jxwl.vehiclemonitor.bean.EnpEveryTimes;
import com.c503.sc.jxwl.vehiclemonitor.bean.InOutAreaEntity;
import com.c503.sc.jxwl.vehiclemonitor.bean.PtStatisticEntity;
import com.c503.sc.jxwl.vehiclemonitor.bean.VehicleAreaStatisticalEntity;

/**
 * 〈一句话功能简述〉统计分析
 * 〈功能详细描述〉
 * 
 * @author zhongz
 * @version [版本号, 2016-12-15]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface IStatisticalAnalysisService {
    
    /**
     * 〈一句话功能简述〉统计企业分布、车辆分布、本年运单
     * 〈功能详细描述〉
     * 
     * @return Object
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    Object findPieVal()
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
    List<EnpEveryTimes> countAllByParams(Map<String, Object> map)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉获取柱状图数据
     * 〈功能详细描述〉
     * 
     * @param map startTime、endTime
     * @return List<Object>
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    List<?> countColumn(Map<String, Object> map)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉获取车辆分布区域信息
     * 〈功能详细描述〉
     * 
     * @return List<Object>
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    List<VehicleAreaStatisticalEntity> findVehicleAreaStatistics()
        throws Exception;
    
    /**
     * 〈一句话功能简述〉获取车辆跨域，进出区域（行政区域，限制区域，自定义区域）记录信息
     * 〈功能详细描述〉
     * 
     * @return List<Object>
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    List<InOutAreaEntity> findVehicleInOutAreaStatistics(Map<String, Object> map)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉获取平台统计信息
     * 〈功能详细描述〉
     * 
     * @return List<Object>
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    List<PtStatisticEntity> findPtStatistics(Map<String, Object> map)
        throws Exception;
    
}
