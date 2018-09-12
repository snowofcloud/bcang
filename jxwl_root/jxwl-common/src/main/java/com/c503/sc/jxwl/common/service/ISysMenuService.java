/*
 * 文件名：ISysMenuService.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2015年12月8日
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.common.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.c503.sc.base.entity.SysModuleEntity;

/**
 * 〈一句话功能简述〉获取系统菜单服务层接口
 * 〈功能详细描述〉
 * 
 * @author luocb
 * @version [版本号, 2015年12月8日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface ISysMenuService {
    
    /**
     * 
     * 〈一句话功能简述〉获取业务系统所有菜单接口
     * 〈功能详细描述〉
     * 
     * @throws Exception 系统异常
     * @see [类、类#方法、类#成员]
     */
    void getAllMenus()
        throws Exception;
    
    /**
     * 
     * 〈一句话功能简述〉根据用户TOken获取用户的所有权限信息
     * 〈功能详细描述〉
     * 
     * @param userToken 用户token
     * @return SysModuleEntity 用户的权限
     * @throws Exception 系统异常
     * @see [类、类#方法、类#成员]
     */
    SysModuleEntity getUserRightByUserToken(String userToken)
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
     * 〈一句话功能简述〉 getUserByAccount
     * 〈功能详细描述〉
     * 
     * @param account account
     * @param sysId sysId
     * @return Map<String, Object>
     * @see [类、类#方法、类#成员]
     */
    Map<String, Object> getUserByAccount(String account, String sysId);
    
    /**
     * 〈一句话功能简述〉修改业务数据的驾驶员密码
     * 〈功能详细描述〉
     * 
     * @param map map
     * @return int
     * @see [类、类#方法、类#成员]
     */
    int updatePassword(Map<String, Object> map);
    
    /**
     * 〈一句话功能简述〉该账号是否存在
     * 〈功能详细描述〉
     * 
     * @param account account
     * @return boolean
     * @see [类、类#方法、类#成员]
     */
    boolean isExist(String account);
}
