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

import org.springframework.stereotype.Repository;

import com.c503.sc.jxwl.common.bean.AlarmEntity;

/**
 * 报警信息<br/>
 * 已过时，参考 {@link IAlarmManageDao}
 * 
 * @author qianxq
 * @version [版本号, 2016-8-4]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Deprecated
@Repository(value = "alarmInfoDao")
public interface IAlarmInfoDao {
    /**
     * @param alarm AlarmRecord
     * @return int
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    int save(AlarmEntity alarm)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉 分页查询
     * 〈功能详细描述〉
     * 
     * @param map map
     * @return object
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    List<AlarmEntity> findAlarmRecordByParams(Map<String, Object> map)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉查看报警记录
     * 〈功能详细描述〉
     * 
     * @param id id
     * @return List<AlarmRecord>
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    AlarmEntity findById(String id)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉根据车牌号查询法人代码以及企业名称
     * 〈功能详细描述〉
     * 
     * @param carrierName carrierName
     * @return Map<String, Object>
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    Map<String, Object> findCorporateCodeByCarrierName(String carrierName)
        throws Exception;
}
