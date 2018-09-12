/**
 * 文件名：IAccountVerifyService.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-7-26
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.vehiclemonitor.service;

import java.util.List;
import java.util.Map;

import com.c503.sc.jxwl.orgdata.bean.BizUserEntity;
import com.c503.sc.jxwl.vehiclemonitor.bean.AccountVerifyEntity;

/**
 * 
 * 〈一句话功能简述〉账号审核业务
 * 〈功能详细描述〉
 * 
 * @author wangwenhuan
 * @version [版本号, 2016-11-10]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface IAccountVerifyService {
    
    /**
     * 
     * 〈一句话功能简述〉 〈功能详细描述〉注册信息保存相关信息
     * 
     * @param entity AccountVerifyEntity
     * @return int
     * @see [类、类#方法、类#成员]
     */
    int register(AccountVerifyEntity entity);
    
    /**
     * 
     * 〈一句话功能简述〉 〈功能详细描述〉判断账号是否重复
     * 
     * @param map Map
     * @return list 集合信息
     * @see [类、类#方法、类#成员]
     */
    List<AccountVerifyEntity> findOccupationPerson(Map<String, Object> map);
    
    /**
     * 
     * 〈一句话功能简述〉 〈功能详细描述〉查找注册信息
     * 
     * @param map Map
     * @return list 集合信息
     * @see [类、类#方法、类#成员]
     */
    List<AccountVerifyEntity> findRegisterInfo(Map<String, Object> map);
    
    /**
     * 
     * 〈一句话功能简述〉 〈功能详细描述〉查询个数
     * 
     * @param map Map
     * @return int int
     * @see [类、类#方法、类#成员]
     */
    int amount(Map<String, Object> map);
    
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
     * 〈一句话功能简述〉 〈功能详细描述〉依据参数查询AccountVerifyEntity
     * 
     * @param map Map
     * @return AccountVerify EntityAccountVerifyEntity
     * @see [类、类#方法、类#成员]
     */
    AccountVerifyEntity findByParameter(Map<String, Object> map);
    
    /**
     * 
     * 〈一句话功能简述〉 〈功能详细描述〉更新字段
     * 
     * @param map Map
     * @return int int
     * @see [类、类#方法、类#成员]
     */
    int verify(Map<String, Object> map);
    
    /**
     * 
     * 〈一句话功能简述〉 〈功能详细描述〉更新字段
     * 
     * @param map Map
     * @return int int
     * @see [类、类#方法、类#成员]
     */
    int cancel(Map<String, Object> map);
    
    /**
     * 
     * 〈一句话功能简述〉 〈功能详细描述〉更新信息
     * 
     * @param map Map
     * @return int int
     * @see [类、类#方法、类#成员]
     */
    int update(Map<String, Object> map);
    
    /**
     * 
     * 〈一句话功能简述〉 〈功能详细描述〉查询认证平台用户数据
     * 
     * @param map Map
     * @return BizUserEntity BizUserEntity
     * @see [类、类#方法、类#成员]
     */
    BizUserEntity findBizUser(Map<String, Object> map);
    
    /**
     * 
     * 〈一句话功能简述〉 〈功能详细描述〉是否存在
     * 
     * @param map Map
     * @return boolean boolean
     * @see [类、类#方法、类#成员]
     */
    boolean isExist(Map<String, Object> map);
}
