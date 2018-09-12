/**
 * 文件名：IEnterpriseLicenceService.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-12-13
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.transacation.service;

import java.util.List;
import java.util.Map;

import com.c503.sc.jxwl.transacation.bean.LicenceEntity;
import com.c503.sc.jxwl.transacation.bean.LicenceFileRelaEntity;
import com.c503.sc.jxwl.vehiclemonitor.bean.EnterpriseEntity;
import com.c503.sc.jxwl.vehiclemonitor.bean.OccupationPersonEntity;

/**
 * 〈一句话功能简述〉形象展示接口 〈功能详细描述〉
 * 
 * @author wangwenhuan
 * @version [版本号, 2016-12-13]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */

public interface ILicenceInfoService {
    /**
     * 
     * 〈一句话功能简述〉分页查询企业资质信息 〈功能详细描述〉
     * 
     * @param map
     *            查询参数
     * @return List<LicenceEntity>
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    List<LicenceEntity> findByParams(Map<String, Object> map)
        throws Exception;
    
    /**
     * 
     * 〈一句话功能简述〉通过ID查询企业资质信息 〈功能详细描述〉
     * 
     * @param id id
     * @param licenceType licenceType
     * @return LicenceEntity
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    LicenceEntity findById(String licenceType, String id)
        throws Exception;
    
    /**
     * 
     * 〈一句话功能简述>保存资质信息 〈功能详细描述〉
     * 
     * @param entity LicenceEntity
     * @return 返回成功 影响的行数
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    Object save(LicenceEntity entity)
        throws Exception;
    
    /**
     * 
     * 〈一句话功能简述>更新资质信息 〈功能详细描述〉
     * 
     * @param entity LicenceEntity
     * @param action action
     * @return 返回成功 影响的行数
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    int update(LicenceEntity entity, String action)
        throws Exception;
    
    /**
     * 
     * 〈一句话功能简述>更新资质信息 〈功能详细描述〉
     * 
     * @param id id
     * @return 返回成功 影响的行数
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    int delete(String id)
        throws Exception;
    
    /**
     * 
     * 〈一句话功能简述>更新资质信息 〈功能详细描述〉
     * 
     * @param map map
     * @return 返回成功 影响的行数
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    List<OccupationPersonEntity> findPerson(Map<String, Object> map)
        throws Exception;
    
    /**
     * 
     * 〈一句话功能简述>更新资质信息 〈功能详细描述〉
     * 
     * @param map map
     * @return 返回成功 影响的行数
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    List<EnterpriseEntity> findEnterprise(Map<String, Object> map)
        throws Exception;
    
    /**
     * 
     * 〈一句话功能简述>更新资质信息 〈功能详细描述〉
     * 
     * @param licenceRelaVals List<LicenceFileRelaEntity>
     * @param licenceEntity LicenceEntity
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    void createFileRelationVal(List<LicenceFileRelaEntity> licenceRelaVals,
        LicenceEntity licenceEntity)
        throws Exception;
    
    /**
     * 
     * 〈一句话功能简述>更新资质信息 〈功能详细描述〉
     * 
     * @param licenceEntity LicenceEntity
     * @return 返回成功 影响的行数
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    boolean check(LicenceEntity licenceEntity)
        throws Exception;
    
    /**
     * 
     * 〈一句话功能简述判断所属企业人员或车辆是否存在 〈功能详细描述〉
     * 
     * @param map map
     * @return boolean
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    boolean isExit(Map<String, String> map)
        throws Exception;
    
    
}
