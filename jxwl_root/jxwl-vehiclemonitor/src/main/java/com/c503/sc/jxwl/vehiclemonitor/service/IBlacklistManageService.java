/**
 * 文件名：IBlacklistManageService.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-7-26
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.vehiclemonitor.service;

import java.util.List;
import java.util.Map;

import com.c503.sc.jxwl.vehiclemonitor.bean.BlacklistManageEntity;
import com.c503.sc.jxwl.vehiclemonitor.vo.AlarmNumVo;

/**
 * 
 * 〈一句话功能简述〉黑名单管理业务接口
 * 〈功能详细描述〉
 * 
 * @author guqh
 * @version [版本号, 2016-7-26]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface IBlacklistManageService {
    /**
     * 
     * 〈一句话功能简述〉分页查询
     * 〈功能详细描述〉
     * 
     * @param map 查询参数
     * @return list 信息集合
     * @throws Exception 异常
     * @see [类、类#方法、类#成员]
     */
    List<BlacklistManageEntity> findByParams(Map<String, Object> map)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉保存
     * 〈功能详细描述〉
     * 
     * @param entity BlacklistManageEntity
     * @return 返回影响条数
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    Object save(BlacklistManageEntity entity)
        throws Exception;
    
    /**
     * 
     * 〈一句话功能简述〉
     * 〈功能详细描述〉
     * 
     * @param entity 实体类
     * @return entity
     * @throws Exception 异常
     * @see [类、类#方法、类#成员]
     */
    Object update(BlacklistManageEntity entity)
        throws Exception;
    
    /**
     * 
     * 〈一句话功能简述〉
     * 〈功能详细描述〉
     * 
     * @param id id
     * @return AlarmManageEntity
     * @throws Exception 异常
     * @see [类、类#方法、类#成员]
     */
    BlacklistManageEntity findById(String id)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉删除信息
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
     * 〈一句话功能简述〉导出表单
     * 〈功能详细描述〉
     * 
     * @param map 查询条件
     * @return 导出的表单
     * @throws Exception 异常
     * @see [类、类#方法、类#成员]
     */
    Map<String, Object> exportExcel(Map<String, Object> map)
        throws Exception;
    
    /**
     * 
     * 〈一句话功能简述〉报警设置
     * 〈功能详细描述〉
     * 
     * @param vo 实体类
     * @return vo
     * @throws Exception 异常
     * @see [类、类#方法、类#成员]
     */
    Object updateAlarmNum(AlarmNumVo vo)
        throws Exception;
    
    /**
     *〈一句话功能简述〉
     * 〈功能详细描述〉
     * @param map map
     * @return object
     * @throws Exception Exception
     * @see  [类、类#方法、类#成员]
     */
    AlarmNumVo findAlarmNumById(Map<String, Object> map)
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
    int findNumById(String corporateNo)
    		throws Exception;
}
