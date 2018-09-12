package com.c503.sc.jxwl.orgdata.service;

import java.util.Map;

/**
 * 〈一句话功能简述〉ISysUserService
 * 〈功能详细描述〉
 * 
 * @author
 * @version [版本号, 2016-12-29]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface ISysUserService {
    /**
     * 
     * 〈一句话功能简述〉保存新增的用户信息〈功能详细描述〉
     * 
     * @param map map
     * @return 返回成功 影响的行数
     * @see [类、类#方法、类#成员]
     */
    int saveSysUser(Map<String, Object> map);
    
    /**
     * 
     * 〈一句话功能简述〉更新的用户信息〈功能详细描述〉
     * 
     * @param map map
     * @return 返回成功 影响的行数
     * @see [类、类#方法、类#成员]
     */
    int updateInfo(Map<String, Object> map);
}
