/**
 * 文件名：IUserSynchronizeService.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2017-5-17
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.vehiclemonitor.service;

import java.util.List;
import java.util.Map;

import com.c503.sc.jxwl.vehiclemonitor.bean.UserSynchronizeEntity;

/**
 * 
 * 〈一句话功能简述〉用户同步
 * 〈功能详细描述〉
 * 
 * @author guqh
 * @version [版本号, 2017-5-17]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface IUserSynchronizeService {
    /**
     * 
     * 〈一句话功能简述〉批量同步用户信息
     * 〈功能详细描述〉
     * 
     * @param list 用户数据集合
     * @return int
     * @throws Exception 异常
     * @see [类、类#方法、类#成员]
     */
    int batchSynchronize(List<UserSynchronizeEntity> list)
        throws Exception;
    
    /**
     * 
     * 〈一句话功能简述〉查询新增同步的用户信息
     * 〈功能详细描述〉
     * 
     * @param map map
     * @return List
     * @throws Exception 异常
     * @see [类、类#方法、类#成员]
     */
    List<UserSynchronizeEntity> findNewUserByParams(Map<String, Object> map)
        throws Exception;
}
