/**
 * 文件名：EmergencyInfoDao.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-7-27
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.vehiclemonitor.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.c503.sc.jxwl.vehiclemonitor.bean.EmergencyEntity;

/**
 * 
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * 
 * @author yuanyl
 * @version [版本号, 2016-7-27]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Repository(value = "emergencyInfoDao")
public interface IEmergencyInfoDao {
    
    /**
     * 〈一句话功能简述〉
     * 〈功能详细描述〉
     * 
     * @param map map
     * @return List<EmergencyEntity>
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    List<EmergencyEntity> findByParams(Map<String, Object> map)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉
     * 〈功能详细描述〉
     * 
     * @param id id
     * @return List<EmergencyEntity>
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    List<EmergencyEntity> findById(String id)
        throws Exception;
    
    /**
     * 
     * 〈一句话功能简述〉保存突发信息
     * 〈功能详细描述〉
     * 
     * @param entity EmergencyEntity
     * @return 影响行数
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    int save(EmergencyEntity entity)
        throws Exception;
    
    /**
     * 
     * 〈一句话功能简述〉删除突发信息
     * 〈功能详细描述〉
     * 
     * @param entity EmergencyEntity
     * @return 影响行数
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    int delete(EmergencyEntity entity)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉修改突发信息
     * 〈功能详细描述〉
     * 
     * @param entity EmergencyEntity
     * @return 返回影响条数
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    int update(EmergencyEntity emergency)
        throws Exception;
}
