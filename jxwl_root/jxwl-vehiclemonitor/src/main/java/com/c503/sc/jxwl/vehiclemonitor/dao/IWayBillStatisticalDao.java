/**
 * 文件名：IWayBillStatisticalDao.java
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

import com.c503.sc.jxwl.vehiclemonitor.bean.WayBillStatisticalEntity;

/**
 * 〈一句话功能简述〉运单统计dao
 * 〈功能详细描述〉
 * 
 * @author mjw
 * @version [版本号, 2016-12-14]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Repository
public interface IWayBillStatisticalDao {
    /**
     * 〈一句话功能简述〉统计物流企业
     * 〈功能详细描述〉
     * 
     * @return {"LOCALENP":20, "NOLOCALENP":10}
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
	List<Map<String, Object>>  countwlEnp(@Param("year") String year)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉统计物流车辆
     * 〈功能详细描述〉
     * 
     * @return {"LOCALCAR":20, "NOLOCALCAR":10}
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    List<Map<String, Object>>  countCar(@Param("year") String year)
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
     * 〈一句话功能简述〉统计物流企业
     * 〈功能详细描述〉
     * 
     * @return {"LOCALENP":20, "NOLOCALENP":10}
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
	List<Map<String, Object>> countwlEnpMon(@Param("month") String month)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉统计物流车辆
     * 〈功能详细描述〉
     * 
     * @return {"LOCALCAR":20, "NOLOCALCAR":10}
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    List<Map<String, Object>> countCarMon(@Param("month") String month)
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
    List<Map<String, Object>> countWaybillMon(@Param("month") String month)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉统计物流企业
     * 〈功能详细描述〉
     * 
     * @return {"LOCALENP":20, "NOLOCALENP":10}
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
	List<Map<String, Object>> countwlEnpWeek(@Param("week") String week)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉统计物流车辆
     * 〈功能详细描述〉
     * 
     * @return {"LOCALCAR":20, "NOLOCALCAR":10}
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    List<Map<String, Object>> countCarWeek(@Param("week") String week)
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
    List<Map<String, Object>> countWaybillWeek(@Param("week") String week)
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
    List<WayBillStatisticalEntity> countAllByParams(Map<String, Object> map)
        throws Exception;
    
}
