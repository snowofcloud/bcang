/*
 * 文件名：IAlarmManageService.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-7-25
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.zcpt.service;

import java.util.List;
import java.util.Map;

import com.c503.sc.jxwl.common.bean.AlarmEntity;
import com.c503.sc.jxwl.common.bean.LocationEntity;
import com.c503.sc.jxwl.zcpt.vo.AlarmThresholdVo;

/**
 * 
 * 〈一句话功能简述〉报警管理业务接口 〈功能详细描述〉
 * 
 * @author guqh
 * @version [版本号, 2016-7-25]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface IAlarmManageService {
    /**
     * 
     * 〈一句话功能简述〉分页查询 〈功能详细描述〉
     * 
     * @param map
     *            查询参数
     * @return list 信息集合
     * @throws Exception
     *             异常
     * @see [类、类#方法、类#成员]
     */
    List<AlarmEntity> findByParams(Map<String, Object> map)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉保存 〈功能详细描述〉
     * 
     * @param entity
     *            AlarmEntity
     * @return 返回影响条数
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    Object save(AlarmEntity entity)
        throws Exception;
    
    /**
     * 
     * 〈一句话功能简述〉 〈功能详细描述〉
     * 
     * @param entity
     *            实体类
     * @return entity
     * @throws Exception
     *             异常
     * @see [类、类#方法、类#成员]
     */
    Object update(AlarmEntity entity)
        throws Exception;
    
    /**
     * 
     * 〈一句话功能简述〉 〈功能详细描述〉
     * 
     * @param id
     *            id
     * @return AlarmEntity
     * @throws Exception
     *             异常
     * @see [类、类#方法、类#成员]
     */
    AlarmEntity findById(String id)
        throws Exception;
    
    /**
     * 
     * 〈一句话功能简述〉 〈功能详细描述〉
     * 
     * @param account
     *            account
     * @return AlarmEntity
     * @throws Exception
     *             异常
     * @see [类、类#方法、类#成员]
     */
    List<AlarmEntity> findByAccount(String account)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉删除信息 〈功能详细描述〉
     * 
     * @param map
     *            id、remove='1'、updateBy、updateTime
     * @return 影响条数
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    int delete(Map<String, Object> map)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉定时一个月删除数据库的报警信息〈功能详细描述〉
     * 
     * @return 影响条数
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    int deleteAlarmInfo()
        throws Exception;
    
    /**
     * 
     * 〈一句话功能简述〉导出表单 〈功能详细描述〉
     * 
     * @param map
     *            查询条件
     * @return 导出的表单
     * @throws Exception
     *             异常
     * @see [类、类#方法、类#成员]
     */
    Map<String, Object> exportExcel(Map<String, Object> map)
        throws Exception;
    
    /**
     * 
     * 〈一句话功能简述〉报警设置 〈功能详细描述〉
     * 
     * @param vo
     *            实体类
     * @return vo
     * @throws Exception
     *             异常
     * @see [类、类#方法、类#成员]
     */
    Object updateThreshold(AlarmThresholdVo vo)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉 〈功能详细描述〉
     * 
     * @param map
     *            map
     * @return object
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    AlarmThresholdVo findThresholdById(Map<String, Object> map)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉查询报警4地图
     * 〈功能详细描述〉
     * 
     * @param map 车牌号
     * @return List<AlarmEntity>
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    List<AlarmEntity> findAlarmForDiTu(Map<String, Object> map)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉查询报警4地图
     * 〈功能详细描述〉
     * 
     * @param map 车牌号
     * @return int
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    int findAlarmNumForDiTu(Map<String, Object> map)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉查询报警4地图
     * 〈功能详细描述〉
     * 
     * @param account 账号
     * @return String
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    String findNameByAccount(String account)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉删除报警信息中车牌号相同，报警状态相同，时间差小于30分钟数据
     * 〈功能详细描述〉
     * 
     * @param
     * @return
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    void deleteAlarm(AlarmEntity entity)
        throws Exception;
    
    /**
     * 
     * @param String account
     * @return
     * @throws Exception
     */
    List<String> findForAppByAccount(String account)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉小米推送报警
     * 〈功能详细描述〉
     * 
     * @param alarms 报警信息
     * @throws Exception 系统异常
     * @see [类、类#方法、类#成员]
     */
    void miPushAlarm(List<AlarmEntity> alarms)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉根据实时位置进行报警处理
     * 〈功能详细描述〉
     * 
     * @param locList 实时位置
     * @param handleType 处理类型（lineAlarm/limitAreaAlarm/alarmHandle）
     * @return 成功/失败
     * @throws Exception 系统异常
     * @see [类、类#方法、类#成员]
     */
    boolean handleAlarm(List<LocationEntity> locList, String handleType)
        throws Exception;
    
    String findNameByCode(String value)
        throws Exception;
    
}
