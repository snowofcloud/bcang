/**
 * 文件名：IAlarmInfoDao.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-8-4
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.vehiclemonitor.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.c503.sc.jxwl.vehiclemonitor.bean.EnpEveryTimes;

/**
 * 〈一句话功能简述〉统计分析dao
 * 〈功能详细描述〉
 * 
 * @author zz
 * @version [版本号, 2016-12-14]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Repository
public interface IStatisticalAnalysisDao {
    /**
     * 〈一句话功能简述〉统计物流企业
     * 〈功能详细描述〉
     * 
     * @return {"LOCALENP":20, "NOLOCALENP":10}
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    Map<String, Object> countwlEnp()
        throws Exception;
    
    /**
     * 〈一句话功能简述〉统计物流车辆
     * 〈功能详细描述〉
     * 
     * @return {"LOCALCAR":20, "NOLOCALCAR":10}
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    Map<String, Object> countCar()
        throws Exception;
    
    /**
     * 〈一句话功能简述〉统计运单排名前8家的物流企业
     * 〈功能详细描述〉
     * 
     * @param year year
     * @return map
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    List<Map<String, Object>> countWaybill(@Param("year") String year)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉统计所有的数据
     * 〈功能详细描述〉
     * 
     * @param map curDate、curMon、curThrMon、curYear
     * @return List<EnpEveryTimes>
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    List<EnpEveryTimes> countAllByParams(Map<String, Object> map)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉查询车辆在线、离线柱状图
     * 〈功能详细描述〉
     * 
     * @param map curDate
     * @return [{ENTERPRISE_NAME:xxx, ONS:23, OFFS:12}, ... ]
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    List<Map<String, Object>> countCarCol(Map<String, Object> map)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉查询报警数柱状图
     * 〈功能详细描述〉
     * 
     * @param map startTime, endTime
     * @return [{ENTERPRISE_NAME:xxx, CUR_ALARM:999}, ...]
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    List<Map<String, Object>> countAlrCol(Map<String, Object> map)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉查询运单柱状图
     * 〈功能详细描述〉
     * 
     * @param map startTime, endTime
     * @return [{ENTERPRISE_NAME:xxx, CUR_ALARM:999}, ...]
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    List<Map<String, Object>> countWbiCol(Map<String, Object> map)
        throws Exception;
    
}
