/**
 * 文件名：IOccupationPersonService.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-7-22
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.vehiclemonitor.service;

import java.util.List;
import java.util.Map;

import com.c503.sc.jxwl.vehiclemonitor.bean.OccupationPersonEntity;

/**
 * 
 * 〈一句话功能简述〉从业人员新业务接口
 * 〈功能详细描述〉
 * 
 * @author guqh
 * @version [版本号, 2016-7-22]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface IOccupationPersonService {
    /**
     * 
     * 〈一句话功能简述〉分页查询从业人员信息
     * 〈功能详细描述〉
     * 
     * @param map 查询参数
     * @return list 物流企业信息集合
     * @throws Exception 异常
     * @see [类、类#方法、类#成员]
     */
    List<OccupationPersonEntity> findByParams(Map<String, Object> map)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉保存从业人员信息
     * 〈功能详细描述〉
     * 
     * @param entity EnterpriseEntity
     * @return 返回影响条数
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    Object save(OccupationPersonEntity entity)
        throws Exception;
    
    /**
     * 
     * 〈一句话功能简述〉
     * 〈功能详细描述〉
     * 
     * @param entity 实体类
     * @return entity
     * @throws Exception 异常
     * @see [类、类#方法、类#成员]
     */
    Object update(OccupationPersonEntity entity)
        throws Exception;
    
    /**
     * 
     * 〈一句话功能简述〉
     * 〈功能详细描述〉
     * 
     * @param id id
     * @return OccupationPersonEntity
     * @throws Exception 异常
     * @see [类、类#方法、类#成员]
     */
    OccupationPersonEntity findById(String id)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉删除从业人员信息
     * 〈功能详细描述〉
     * 
     * @param map id、remove='1'、updateBy、updateTime
     * @return 影响条数
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    int delete(Map<String, Object> map)
        throws Exception;
    
   
}
