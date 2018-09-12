/**
 * 文件名：IEnterpriseDao.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-7-20
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.vehiclemonitor.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.c503.sc.jxwl.vehiclemonitor.bean.EnterpriseEntity;

/**
 * 
 * 〈一句话功能简述〉物流企业信息Dao
 * 〈功能详细描述〉
 * 
 * @author guqh
 * @version [版本号, 2016-7-20]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Repository(value = "enterpriseDao")
public interface IEnterpriseDao {
    /**
     * 
     * 〈一句话功能简述〉
     * 〈功能详细描述〉
     * 
     * @param map 查询参数
     * @return list 结果集合
     * @throws Exception 异常
     * @see [类、类#方法、类#成员]
     */
    List<EnterpriseEntity> findByParams(Map<String, Object> map)
        throws Exception;
    
    /**
     * 
     * 〈一句话功能简述〉
     * 〈功能详细描述〉
     * 
     * @param enterpriseType enterpriseType
     * @return List<EnterpriseEntity>
     * @throws Exception 异常
     * @see [类、类#方法、类#成员]
     */
    List<EnterpriseEntity> findCorporateNo(String enterpriseType)
        throws Exception;
    
    /**
     * 
     * 〈一句话功能简述〉
     * 〈功能详细描述〉
     * 
     * @param entity 实体类
     * @return 保存条数
     * @throws Exception 异常
     * @see [类、类#方法、类#成员]
     */
    int save(EnterpriseEntity entity)
        throws Exception;
    
    /**
     * 
     * 〈一句话功能简述〉
     * 〈功能详细描述〉
     * 
     * @param entity 实体类
     * @return 保存条数
     * @throws Exception 异常
     * @see [类、类#方法、类#成员]
     */
    int update(EnterpriseEntity entity)
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
     * 〈一句话功能简述〉删除
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
     * 〈一句话功能简述〉删除企业
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
     * 〈一句话功能简述〉删除企业
     * 〈功能详细描述〉
     * 
     * @param map id、remove='1'、updateBy、updateTime
     * @return 影响条数
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    int updateLicenceInfo(Map<String, Object> map)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉删除企业
     * 〈功能详细描述〉
     * 
     * @param map id、remove='1'、updateBy、updateTime
     * @return 影响条数
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    int updatePersonAndCar(Map<String, Object> map)
        throws Exception;
    
    /**
     * 
     * 〈一句话功能简述〉 通过企业id来查找附件的id
     * 〈功能详细描述〉
     * 
     * @param id 企业的id
     * @return list型的文件id
     * @throws Exception 数据库异常
     * @see [类、类#方法、类#成员]
     */
    List<String> findFileIdById(String id)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉通過法人代碼更新車輛數量
     * 〈功能详细描述〉
     * 
     * @param entity entity
     * @return object
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    int updateVerchierNumByCorporateNo(EnterpriseEntity entity)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉
     * 〈功能详细描述〉
     * 
     * @param corporateNo corporateNo
     * @param id id
     * @return corporateNo
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    String findCorporateNoHasExist(@Param("corporateNo")
    String corporateNo, @Param(value = "id")
    String id)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉通過法人代碼更新車輛數量
     * 〈功能详细描述〉
     * 
     * @param corporateNo corporateNo
     * @return object
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    String findNameByNo(String corporateNo)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉通過法人代碼更新車輛數量
     * 〈功能详细描述〉
     * 
     * @param company company
     * @return String
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    List<String> findCopmanyHasExist(String company)
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
     * 〈一句话功能简述〉查询已存在企业名称
     * 〈功能详细描述〉
     * 
     * @param map map
     * @return List
     * @throws Exception 异常
     * @see [类、类#方法、类#成员]
     */
    List<EnterpriseEntity> findAllName(Map<String, Object> map)
        throws Exception;
    
    /**
     * 
     * 〈一句话功能简述〉批量保存企业的用户信息
     * 〈功能详细描述〉
     * 
     * @param entity entity
     * @return 保存条数
     * @see [类、类#方法、类#成员]
     */
    int batchSave(List<EnterpriseEntity> entity);
}
