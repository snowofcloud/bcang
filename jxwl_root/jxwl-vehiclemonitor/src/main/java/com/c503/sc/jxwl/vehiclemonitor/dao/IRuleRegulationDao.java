/**
 * 文件名：IRuleRegulationDao.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-8-4
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.vehiclemonitor.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.c503.sc.jxwl.vehiclemonitor.bean.RuleRegulationEntity;

/**
 * 〈一句话功能简述〉规章制度信息Dao
 * 〈功能详细描述〉
 * 
 * @author xiaoqx
 * @version [版本号, 2016-8-4]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Repository(value = "ruleRegulationDao")
public interface IRuleRegulationDao {
    /**
     * 
     * 〈一句话功能简述〉分页查询规章制度信息
     * 〈功能详细描述〉
     * 
     * @param map map
     * @return List<RuleRegulationEntity>
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    List<RuleRegulationEntity> findByParams(Map<String, Object> map)
        throws Exception;
    
    /**
     * 
     * 〈一句话功能简述〉保存规章制度信息
     * 〈功能详细描述〉
     * 
     * @param entity RuleRegulationEntity
     * @return 影响行数
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    int save(RuleRegulationEntity entity)
        throws Exception;
    
    /**
     * 
     * 〈一句话功能简述〉通过id查询规章制度信息
     * 〈功能详细描述〉
     * 
     * @param id id
     * @return List<RuleRegulationEntity>
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    List<RuleRegulationEntity> findById(String id)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉修改规章制度信息
     * 〈功能详细描述〉
     * 
     * @param ruleRegulation RuleRegulationEntity
     * @return 返回影响条数
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    int update(RuleRegulationEntity ruleRegulation)
        throws Exception;
    
    /**
     * 
     * 〈一句话功能简述〉删除规章制度信息
     * 〈功能详细描述〉
     * 
     * @param entity RuleRegulationEntity
     * @return 影响行数
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    int delete(RuleRegulationEntity entity)
        throws Exception;
}
