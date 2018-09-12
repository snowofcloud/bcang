/**
 * 文件名：IEmergentLinkmanService.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-8-5 
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.vehiclemonitor.service;

import java.util.List;
import java.util.Map;

import com.c503.sc.jxwl.vehiclemonitor.bean.EmergentLinkmanEntity;


/**
 * 〈一句话功能简述〉紧急联系人接口
 * 〈功能详细描述〉
 * @author    xiaoqx
 * @version   [版本号, 2016-8-5]
 * @see       [相关类/方法]
 * @since     [产品/模块版本] 
 */
public interface IEmergentLinkmanService {
    /**
     *〈一句话功能简述〉分页查询紧急联系人信息
     * 〈功能详细描述〉
     * @param map map
     * @return object
     * @throws Exception Exception
     * @see  [类、类#方法、类#成员]
     */
    List<EmergentLinkmanEntity> findByParams(Map<String, Object> map) throws Exception;
    
    /**
     * 
     *〈一句话功能简述〉保存紧急联系人信息
     * 〈功能详细描述〉
     * @param entity entity
     * @return object
     * @throws Exception Exception
     * @see  [类、类#方法、类#成员]
     */
    Object save(EmergentLinkmanEntity entity) throws Exception;
    
    /**
     * 
     *〈一句话功能简述〉通过id查询紧急联系人信息
     * 〈功能详细描述〉
     * @param id id
     * @return object
     * @throws Exception Exception
     * @see  [类、类#方法、类#成员]
     */
    EmergentLinkmanEntity findById(String id) throws Exception;
    
    /**
     * 〈一句话功能简述〉编辑紧急联系人信息
     * 〈功能详细描述〉
     * 
     * @param emergentLinkman EmergentLinkmanEntity
     * @return 影响行数
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    int update(EmergentLinkmanEntity emergentLinkman)
        throws Exception;
    
    /**
     * 
     *〈一句话功能简述〉删除紧急联系人信息
     * 〈功能详细描述〉
     * @param entity entity
     * @return object
     * @throws Exception Exception
     * @see  [类、类#方法、类#成员]
     */
    Object delete(EmergentLinkmanEntity entity) throws Exception;
}
