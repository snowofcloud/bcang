/**
 * 文件名：IBlacklistManageDao.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-7-26
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.vehiclemonitor.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.c503.sc.jxwl.vehiclemonitor.bean.BlacklistManageEntity;
import com.c503.sc.jxwl.vehiclemonitor.vo.AlarmNumVo;

/**
 * 
 * 〈一句话功能简述〉黑名单管理Dao
 * 〈功能详细描述〉
 * 
 * @author guqh
 * @version [版本号, 2016-7-26]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Repository(value = "blacklistManageDao")
public interface IBlacklistManageDao {
    /**
     * 
     * 〈一句话功能简述〉
     * 〈功能详细描述〉
     * 
     * @param map 查询参数
     * @return list 结果集合
     * @throws Exception 异常
     * @see [类、类#方法、类#成员]
     */
    List<BlacklistManageEntity> findByParams(Map<String, Object> map)
        throws Exception;
    
    /**
     * 
     * 〈一句话功能简述〉
     * 〈功能详细描述〉
     * 
     * @param entity 实体类
     * @return 保存条数
     * @throws Exception 异常
     * @see [类、类#方法、类#成员]
     */
    int save(BlacklistManageEntity entity)
        throws Exception;
    
    /**
     * 
     * 〈一句话功能简述〉
     * 〈功能详细描述〉
     * 
     * @param entity 实体类
     * @return 保存条数
     * @throws Exception 异常
     * @see [类、类#方法、类#成员]
     */
    int update(BlacklistManageEntity entity)
        throws Exception;
    
    /**
     * 
     * 〈一句话功能简述〉
     * 〈功能详细描述〉
     * 
     * @param id id
     * @return BlacklistManageEntity
     * @throws Exception 异常
     * @see [类、类#方法、类#成员]
     */
    BlacklistManageEntity findById(String id)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉删除
     * 〈功能详细描述〉
     * 
     * @param map id、remove='1'、updateBy、updateTime
     * @return 影响条数
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    int delete(Map<String, Object> map)
        throws Exception;
    
    /**
     * 
     * 〈一句话功能简述〉设置报警次数
     * 〈功能详细描述〉
     * 
     * @param vo 报警次数VO
     * @return 保存条数
     * @throws Exception 异常
     * @see [类、类#方法、类#成员]
     */
    int updateAlarmNum(AlarmNumVo vo)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉查询报警数量
     * 〈功能详细描述〉
     * 
     * @param map map
     * @return AlarmNumVo
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    AlarmNumVo findAlarmNumById(Map<String, Object> map)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉查询拉黑的数据是否已经存在
     * 〈功能详细描述〉
     * 
     * @param map map
     * @return BlacklistManageEntity
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    List<BlacklistManageEntity> findBlackExist(Map<String, Object> map)
        throws Exception;
    
    
    /**
     * 
     * 〈一句话功能简述〉根据法人代码获取其在黑名单里的条数 〈功能详细描述〉
     * 
     * @param corporateNo
     *            企业的法人代码
     * @return int 获取条数
     * @throws Exception
     *             数据库异常
     * @see [类、类#方法、类#成员]
     */
    @Select("select COUNT(*) from T_BLACKLIST_MANAGE  where CORPORATE_NO = #{corporateNo,jdbcType=VARCHAR} AND REMOVE ='0'")
    int findNumById(String corporateNo)
    	throws Exception;
    
}
