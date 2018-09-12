/**
 * 文件名：IUserService.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-6-29
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.common.service;

import java.util.List;

import com.c503.sc.base.entity.SysLoginParaEntity;
import com.c503.sc.base.entity.SysModuleEntity;
import com.c503.sc.jxwl.common.bean.BizUserEntity;

/**
 * 〈一句话功能简述〉 用户业务接口
 * 〈功能详细描述〉
 * 
 * @author huangtw
 * @version [版本号, 2016-6-29]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface IUserService {
    
    /**
     * 〈一句话功能简述〉 验证登录用户
     * 〈功能详细描述〉 验证登录用户，用户登录验证规则
     * 1、到大数据中心验证
     * 2、大数据中心服务不可用时到本地验证
     * 
     * @param loginPara SysLoginParaEntity
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    void validateLoginUser(SysLoginParaEntity loginPara)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉 获取用户对象
     * 〈功能详细描述〉通过用户登录Token获取用户对象
     * 
     * @param token 用户登录Token
     * @return 用户对象
     * @see [类、类#方法、类#成员]
     */
    BizUserEntity getUserByToken(String token);
    
    /**
     * 〈一句话功能简述〉获取用户的系统模块
     * 〈功能详细描述〉通过用户登录的Token获取用户的系统模块(用户拥有的权限，包括菜单、功能按钮等)
     * 
     * @param token token
     * @return 系统模块对象
     * @see [类、类#方法、类#成员]
     */
    SysModuleEntity getSysModuleByToken(String token);
    
    /**
     * 
     * 〈一句话功能简述〉获取企业的法人代码
     * 〈功能详细描述〉
     * 
     * @param orgId 企业的机构id
     * @return 法人代码
     * @throws Exception 系统异常
     * @see [类、类#方法、类#成员]
     */
    String findEnCodeByOrgId(String orgId)
        throws Exception;
    
    /**
     * 
     * 〈一句话功能简述〉获取用户的角色
     * 〈功能详细描述〉
     * 
     * @param uerId 用户id
     * @return list型的所有角色
     * @throws Exception 系统异常
     * @see [类、类#方法、类#成员]
     */
    List<String> findUserRoleByUserId(String uerId)
        throws Exception;
    
}
