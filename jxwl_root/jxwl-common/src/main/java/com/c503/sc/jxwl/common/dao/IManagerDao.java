/**
 * 文件名：findCorpoorateCode.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-6-28
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.common.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.c503.sc.jxwl.common.bean.EnterpriseEntity;
import com.c503.sc.jxwl.common.bean.ManagerEntity;

/**
 * 〈一句话功能简述〉查询企业相关信息DAO
 * 〈功能详细描述〉
 * 
 * @author zz
 * @version [版本号, 2016-6-28]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Repository(value = "managerDao")
public interface IManagerDao {
    /**
     * 〈一句话功能简述〉根据用户id查询企业法人代码
     * 〈功能详细描述〉
     * 
     * @param id 用户id
     * @return ManagerEntity
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    ManagerEntity findCorporateCode(String id)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉查询所有企业信息
     * 〈功能详细描述〉
     * 
     * @return List<EnterpriseEntity>
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    List<EnterpriseEntity> findAllEnterpriseMsgs()
        throws Exception;
    
    /**
     * 〈一句话功能简述〉 findStatus
     * 〈功能详细描述〉
     * 
     * @param map map
     * @return Map<String, Object>
     * @see [类、类#方法、类#成员]
     */
    List<HashMap<String, Object>> findStatus(Map<String, Object> map);
    
    /**
     * 〈一句话功能简述〉updatePassword
     * 〈功能详细描述〉
     * 
     * @param map map
     * @return int
     * @see [类、类#方法、类#成员]
     */
    int updatePassword(Map<String, Object> map);
    
    
    /**
     * 〈一句话功能简述〉findRegIdByAccount
     * 〈功能详细描述〉
     * 
     * @param account account
     * @return String
     * @see [类、类#方法、类#成员]
     */
    String findRegIdByAccount(String account);
    
    /**
     * 〈一句话功能简述〉updateRegId
     * 〈功能详细描述〉
     * 
     * @param map map
     * @return int
     * @see [类、类#方法、类#成员]
     */
    int updateRegId(Map<String, String> map);
    
    /**
     * 〈一句话功能简述〉clearAllSameRegIds
     * 〈功能详细描述〉
     * 
     * @param map map
     * @return int
     * @see [类、类#方法、类#成员]
     */
    int clearAllSameRegIds(@Param("regId")String regId,
        @Param("account")String account);
    
    
    /**
     * 〈一句话功能简述〉isExist
     * 〈功能详细描述〉
     * 
     * @param map map
     * @return map
     * @see [类、类#方法、类#成员]
     */
    Map<String, String> isExist(Map<String, String> map);
    /**
     * 〈一句话功能简述〉updateLoginState
     * 〈功能详细描述〉
     * 
     * @param account account
     * @return int 影响的行数
     * @see [类、类#方法、类#成员]
     */
    int updateLoginState(String account);
    /**
     * 〈一句话功能简述〉updateExitState
     * 〈功能详细描述〉
     * 
     * @param account account
     * @return int 影响的行数
     * @see [类、类#方法、类#成员]
     */
    int updateExitState(String account);
    
    /**
     * 〈一句话功能简述〉findLoginState
     * 〈功能详细描述〉
     * 
     * @param driverid driverid
     * @return String 登录状态
     * @see [类、类#方法、类#成员]
     */
    String findLoginState(String driverid);
    
    /**
     * 〈一句话功能简述〉setAppAccount
     * 〈功能详细描述〉
     * 
     * @param driverid driverid
     * @return String 登录状态
     * @see [类、类#方法、类#成员]
     */
    String setAppAccount(String driverId);
    
    String findLoginStateByAccount(@Param("account")String account);
    
    List<EnterpriseEntity> findEnterpriseByCorporateCode(@Param("corporateNo")String corporateNo)
        throws Exception;
    
}
