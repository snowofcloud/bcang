/**
 * 文件名：IEmergencyInfoService.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-7-27 
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.vehiclemonitor.service;

import java.util.List;
import java.util.Map;

import com.c503.sc.jxwl.vehiclemonitor.bean.EmergencyEntity;

/**
 * 
 * 〈一句话功能简述〉突发信息业务接口
 * 〈功能详细描述〉
 * @author    yuanyl
 * @version   [版本号, 2016-7-27]
 * @see       [相关类/方法]
 * @since     [产品/模块版本]
 */
public interface IEmergencyInfoService {
    
    /**
     *〈一句话功能简述〉
     * 〈功能详细描述〉
     * @param entity entity
     * @return object
     * @throws Exception Exception
     * @see  [类、类#方法、类#成员]
     */
    Object save(EmergencyEntity entity) throws Exception;
    
    /**
     *〈一句话功能简述〉分页查询突发信息
     * 〈功能详细描述〉
     * @param map map
     * @return object
     * @throws Exception Exception
     * @see  [类、类#方法、类#成员]
     */
    List<EmergencyEntity> findByParams(Map<String, Object> map) throws Exception;
    
    /**
     * 
     *〈一句话功能简述〉通过id查询突发信息
     * 〈功能详细描述〉
     * @param id id
     * @return object
     * @throws Exception Exception
     * @see  [类、类#方法、类#成员]
     */
    EmergencyEntity findById(String id) throws Exception;
    
    /**
     * 
     *〈一句话功能简述〉删除突发信息
     * 〈功能详细描述〉
     * @param entity entity
     * @return object
     * @throws Exception Exception
     * @see  [类、类#方法、类#成员]
     */
    Object delete(EmergencyEntity entity) throws Exception;
    /**
     * 
     *〈一句话功能简述〉推送突发信息
     * 〈功能详细描述〉
     * @param entity entity
     * @throws Exception Exception
     * @see  [类、类#方法、类#成员]
     */
    void miPushEmergency(EmergencyEntity entity) throws Exception;
    
    /**
     * 〈一句话功能简述〉修改突发信息
     * 〈功能详细描述〉
     * 
     * @param entity EmergencyEntity
     * @return 返回影响条数
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    int update(EmergencyEntity emergency) throws Exception;
}
