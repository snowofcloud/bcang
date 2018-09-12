/**
 * 文件名：IEnterpriseLicenceDao.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-12-14
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.transacation.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.c503.sc.jxwl.transacation.bean.LicenceEntity;
import com.c503.sc.jxwl.vehiclemonitor.bean.EnterpriseEntity;
import com.c503.sc.jxwl.vehiclemonitor.bean.OccupationPersonEntity;

/**
 * 〈一句话功能简述〉企业资质dao
 * 〈功能详细描述〉
 * 
 * @author wangwenhuan
 * @version [版本号, 2016-12-13]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Repository(value = "licenceInfoDao")
public interface ILicenceInfoDao {
    
    /**
     * 〈一句话功能简述〉分页查询订单信息
     * 〈功能详细描述〉
     * 
     * @param map map
     * @return SrcGoodsEntity
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    List<LicenceEntity> findByParams(Map<String, Object> map)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉分页查询订单信息
     * 〈功能详细描述〉
     * 
     * @param map map
     * @return LicenceEntity
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    LicenceEntity findById(Map<String, Object> map)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉更新信息
     * 〈功能详细描述〉
     * 
     * @param entity identity
     * @return int
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    int save(LicenceEntity entity)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉更新信息
     * 〈功能详细描述〉
     * 
     * @param entity entity
     * @return int
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    int update(LicenceEntity entity)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉更新信息
     * 〈功能详细描述〉
     * 
     * @param id id
     * @return int
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    int delete(String id)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉更新信息
     * 〈功能详细描述〉
     * 
     * @param map map
     * @return List<OccupationPersonEntity>
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    List<OccupationPersonEntity> findPerson(Map<String, Object> map)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉更新信息
     * 〈功能详细描述〉
     * 
     * @param map map
     * @return List<EnterpriseEntity>
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    List<EnterpriseEntity> findEnterprise(Map<String, Object> map)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉更新信息
     * 〈功能详细描述〉
     * 
     * @param map map
     * @return String
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    String findCar4match(Map<String, Object> map)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉更新信息
     * 〈功能详细描述〉
     * 
     * @param map map
     * @return String
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    String findPerson4match(Map<String, Object> map)
        throws Exception;
    /**
     * 〈一句话功能简述〉判断是否存在
     * 
     * @param map map
     * @return LicenceEntity
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    LicenceEntity isExit(Map<String, String> map)
        throws Exception;
    
}
