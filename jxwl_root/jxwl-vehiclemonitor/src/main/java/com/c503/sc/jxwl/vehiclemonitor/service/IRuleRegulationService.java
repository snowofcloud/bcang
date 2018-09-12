/**
 * 文件名：IRuleRegulationService.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-8-4 
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.vehiclemonitor.service;

import java.util.List;
import java.util.Map;

import com.c503.sc.jxwl.vehiclemonitor.bean.RuleRegulationEntity;


/**
 * 〈一句话功能简述〉规章制度接口
 * 〈功能详细描述〉
 * @author    xiaoqx
 * @version   [版本号, 2016-8-4]
 * @see       [相关类/方法]
 * @since     [产品/模块版本] 
 */
public interface IRuleRegulationService {
    /**
     *〈一句话功能简述〉分页查询规章制度信息
     * 〈功能详细描述〉
     * @param map map
     * @return object
     * @throws Exception Exception
     * @see  [类、类#方法、类#成员]
     */
    List<RuleRegulationEntity> findByParams(Map<String, Object> map) throws Exception;
    
    /**
     * 
     *〈一句话功能简述〉保存规章制度信息
     * 〈功能详细描述〉
     * @param entity entity
     * @return object
     * @throws Exception Exception
     * @see  [类、类#方法、类#成员]
     */
    Object save(RuleRegulationEntity entity) throws Exception;
    
    /**
     * 
     *〈一句话功能简述〉通过id查询规章制度信息
     * 〈功能详细描述〉
     * @param id id
     * @return object
     * @throws Exception Exception
     * @see  [类、类#方法、类#成员]
     */
    RuleRegulationEntity findById(String id) throws Exception;

    /**
     * 〈一句话功能简述〉编辑规章制度信息
     * 〈功能详细描述〉
     * 
     * @param ruleRegulation RuleRegulationEntity
     * @return 影响行数
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    int update(RuleRegulationEntity ruleRegulation)
        throws Exception;
    
    /**
     * 
     *〈一句话功能简述〉删除规章制度信息
     * 〈功能详细描述〉
     * @param entity entity
     * @return object
     * @throws Exception Exception
     * @see  [类、类#方法、类#成员]
     */
    Object delete(RuleRegulationEntity entity) throws Exception;
}
