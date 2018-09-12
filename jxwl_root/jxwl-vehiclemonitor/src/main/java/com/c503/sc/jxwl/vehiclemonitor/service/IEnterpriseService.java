/**
 * 文件名：IEnterpriseService.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-7-20
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.vehiclemonitor.service;

import java.util.List;
import java.util.Map;

import com.c503.sc.jxwl.vehiclemonitor.bean.EnterpriseEntity;

/**
 * 
 * 〈一句话功能简述〉物流企业信息业务层接口
 * 〈功能详细描述〉
 * 
 * @author guqh
 * @version [版本号, 2016-7-20]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface IEnterpriseService {
    /**
     * 
     * 〈一句话功能简述〉分页查询物流企业信息
     * 〈功能详细描述〉
     * 
     * @param map 查询参数
     * @return list 物流企业信息集合
     * @throws Exception 异常
     * @see [类、类#方法、类#成员]
     */
    List<EnterpriseEntity> findByParams(Map<String, Object> map)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉
     * 〈功能详细描述〉
     * 
     * @param entity entity
     * @return object
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    EnterpriseEntity save(EnterpriseEntity entity)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉
     * 〈功能详细描述〉
     * 
     * @param entity entity
     * @return object
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    Object update(EnterpriseEntity entity)
        throws Exception;
    
    /**
     * 
     * 〈一句话功能简述〉
     * 〈功能详细描述〉
     * 
     * @param id id
     * @return EnterpriseEntity
     * @throws Exception 异常
     * @see [类、类#方法、类#成员]
     */
    EnterpriseEntity findById(String id)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉删除企业信息
     * 〈功能详细描述〉
     * 
     * @param map id、remove='1'、updateBy、updateTime
     * @return 影响条数
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    int deleteLogisticst(Map<String, Object> map)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉删除信息
     * 〈功能详细描述〉
     * 
     * @param map id、remove='1'、updateBy、updateTime
     * @return 影响条数
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    int delete(Map<String, Object> map)
        throws Exception;
    
    /**
     * 
     * 〈一句话功能简述〉导出表单
     * 〈功能详细描述〉
     * 
     * @param map 查询条件
     * @return 导出的表单
     * @throws Exception 异常
     * @see [类、类#方法、类#成员]
     */
    Map<String, Object> exportExcel(Map<String, Object> map)
        throws Exception;
    
    /**
     * 
     * 〈一句话功能简述〉导出表单
     * 〈功能详细描述〉
     * 
     * @param corporateNo corporateNo件
     * @return string name
     * @throws Exception 异常
     * @see [类、类#方法、类#成员]
     */
    String findNameByNo(String corporateNo)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉检查是否存在
     * 〈功能详细描述〉
     * 
     * @param map map
     * @return String
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    String isExist(Map<String, String> map)
        throws Exception;
    
    /**
     * 
     * 〈一句话功能简述〉 查询所有企业名称
     * 〈功能详细描述〉
     * 
     * @param map map
     * @return list
     * @throws Exception 异常
     * @see [类、类#方法、类#成员]
     */
    List<EnterpriseEntity> findAllName(Map<String, Object> map)
        throws Exception;
    
    /**
     * 
     * 〈一句话功能简述〉批量同步企业信息
     * 〈功能详细描述〉
     * 
     * @param list 企业数据集合
     * @return int
     * @throws Exception 异常
     * @see [类、类#方法、类#成员]
     */
    int batchSynchronize(List<EnterpriseEntity> list)
        throws Exception;
}
