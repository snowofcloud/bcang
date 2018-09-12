/**
 * 文件名：BlacklistManageServiceImpl.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-11-10
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.vehiclemonitor.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.c503.sc.jxwl.vehiclemonitor.bean.AccountVerifyEntity;

/**
 * 
 * 〈一句话功能简述〉账号审核dao
 * 〈功能详细描述〉
 * 
 * @author wangwenhuan
 * @version [版本号, 2016-11-10]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Repository(value = "accountVerifyDao")
public interface IAccountVerifyDao {
    
    /**
     * 
     * 〈一句话功能简述〉 〈功能详细描述〉注册用户信息 保存信息
     * 
     * @param entity entity
     * @return int 影响行数
     * @see [类、类#方法、类#成员]
     */
    int register(AccountVerifyEntity entity);
    
    /**
     * 
     * 〈一句话功能简述〉 〈功能详细描述〉查找从业信息对象
     * 
     * @param map Map
     * @return list 集合信息
     * @see [类、类#方法、类#成员]
     */
    List<AccountVerifyEntity> findOccupationPerson(Map<String, Object> map);
    
    /**
     * 
     * 〈一句话功能简述〉 〈功能详细描述〉查找注册信息对象
     * 
     * @param map Map
     * @return list 集合信息
     * @see [类、类#方法、类#成员]
     */
    List<AccountVerifyEntity> findRegisterInfo(Map<String, Object> map);
    
    /**
     * 
     * 〈一句话功能简述〉 〈功能详细描述〉分页查询
     * 
     * @param map Map
     * @return list 集合信息
     * @see [类、类#方法、类#成员]
     */
    List<AccountVerifyEntity> findByParams(Map<String, Object> map);
    
    /**
     * 
     * 〈一句话功能简述〉 〈功能详细描述〉依据参数查找对象
     * 
     * @param map Map
     * @return AccountVerifyEntity AccountVerifyEntity
     * @see [类、类#方法、类#成员]
     */
    AccountVerifyEntity findByParameter(Map<String, Object> map);
    
    /**
     * 
     * 〈一句话功能简述〉 〈功能详细描述〉审核 更新字段
     * 
     * @param map Map
     * @return int 受影响的行数
     * @see [类、类#方法、类#成员]
     */
    int verify(Map<String, Object> map);
    
    /**
     * 
     * 〈一句话功能简述〉 〈功能详细描述〉注销 更新字段
     * 
     * @param map Map
     * @return int 受影响的行数
     * @see [类、类#方法、类#成员]
     */
    int cancel(Map<String, Object> map);
    
    /**
     * 
     * 〈一句话功能简述〉 〈功能详细描述〉更新信息 更新字段
     * 
     * @param map Map
     * @return int 受影响的行数
     * @see [类、类#方法、类#成员]
     */
    int update(Map<String, Object> map);
    
    /**
     * 
     * 〈一句话功能简述〉 〈功能详细描述〉总计 查询
     * 
     * @param map Map
     * @return int 返回符合条件的行数
     * @see [类、类#方法、类#成员]
     */
    int amount(Map<String, Object> map);
    
    /**
     * 
     * 〈一句话功能简述〉 〈功能详细描述〉该账号以及所属企业是否存在
     * 
     * @param map Map
     * @return Map Map
     * @see [类、类#方法、类#成员]
     */
    Map<String, String> isExist(Map<String, Object> map);
}
