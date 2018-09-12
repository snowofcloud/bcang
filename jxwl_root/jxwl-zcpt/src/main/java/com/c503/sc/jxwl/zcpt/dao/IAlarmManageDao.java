/**
 * 文件名：IAlarmManageDao.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-7-25
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.zcpt.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.c503.sc.jxwl.common.bean.AlarmEntity;
import com.c503.sc.jxwl.zcpt.vo.AlarmThresholdVo;

/**
 * 
 * 〈一句话功能简述〉报警管理Dao 〈功能详细描述〉
 * 
 * @author guqh
 * @version [版本号, 2016-7-25]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Repository(value = "alarmManageDao")
public interface IAlarmManageDao {
    /**
     * 
     * 〈一句话功能简述〉 〈功能详细描述〉
     * 
     * @param map
     *            查询参数
     * @return list 结果集合
     * @throws Exception
     *             异常
     * @see [类、类#方法、类#成员]
     */
    List<AlarmEntity> findByParams(Map<String, Object> map)
        throws Exception;
    
    /**
     * 
     * 〈一句话功能简述〉保存报警信息 〈功能详细描述〉
     * 
     * @param entity
     *            实体类
     * @return 保存条数
     * @throws Exception
     *             异常
     * @see [类、类#方法、类#成员]
     */
    int save(AlarmEntity entity)
        throws Exception;
    
    /**
     * 
     * 〈一句话功能简述〉 〈功能详细描述〉
     * 
     * @param entity
     *            实体类
     * @return 保存条数
     * @throws Exception
     *             异常
     * @see [类、类#方法、类#成员]
     */
    int update(AlarmEntity entity)
        throws Exception;
    
    /**
     * 
     * 〈一句话功能简述〉 〈功能详细描述〉
     * 
     * @param id
     *            id
     * @return AlarmManageEntity
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
     * @param map
     *            map
     * @return List
     * @throws Exception
     *             异常
     * @see [类、类#方法、类#成员]
     */
    List<AlarmEntity> findByAccount(Map<String, String> map)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉删除 〈功能详细描述〉
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
     * 〈一句话功能简述〉设置报警阈值 〈功能详细描述〉
     * 
     * @param vo
     *            报警阈值VO
     * @return 保存条数
     * @throws Exception
     *             异常
     * @see [类、类#方法、类#成员]
     */
    int updateThreshold(AlarmThresholdVo vo)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉 〈功能详细描述〉
     * 
     * @param map
     *            map
     * @return AlarmThresholdVo
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    AlarmThresholdVo findThresholdById(Map<String, Object> map)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉 〈功能详细描述〉
     * 
     * @param alarmNo
     *            alarmNo
     * @return int
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    int alarmNoIsRepeated(String alarmNo)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉在地图上展示报警数据 〈功能详细描述〉
     * 
     * @param map
     *            车牌号
     * @return List<AlarmEntity>
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    @ResultMap(value = "BaseResultMap")
    @Select("SELECT * FROM (SELECT T.ID,T.LICENCE_PLATE_NO, T.WAYBILL_NO,   "
        + "         T.ALARM_DATE,    T.ALARM_TYPE, "
        + "         T.ALARM_DETAILS, T.ALARM_DEAL_STATUS, "
        + "         TB.PERSON_NAME AS DRIVER,  TB.TELEPHONE "
        + "    FROM T_ALARM_MANAGE T "
        + "    LEFT JOIN T_OCCUPATION_PERSON TB "
        + "      ON T.DRIVER_ID = TB.IDENTIFICATION_CARD_NO "
        + "    WHERE T.LICENCE_PLATE_NO = #{carrierName}  AND T.ALARM_DEAL_STATUS='103002002' AND T.REMOVE = '0' "
        + "    ORDER BY ALARM_DATE DESC) TEMP WHERE ROWNUM <= 30")
    List<AlarmEntity> findAlarmForDiTuParams(Map<String, Object> map)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉通过车牌号查询最近的一次报警 〈功能详细描述〉
     * 
     * @param carrierName
     *            车牌号
     * @param alarmType
     *            报警类型
     * @return alarmDate of String
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    @Select("SELECT TO_CHAR(ALARM_DATE, 'yyyy-mm-dd hh24:mi:ss') AS ALARMTIME "
        + "    FROM T_ALARM_MANAGE T "
        + "   WHERE T.ALARM_DATE = (SELECT MAX(ALARM_DATE) "
        + "                           FROM T_ALARM_MANAGE "
        + "                          WHERE LICENCE_PLATE_NO = #{carrierName,jdbcType=VARCHAR} "
        + "                            AND ALARM_TYPE = #{alarmType,jdbcType=VARCHAR} "
        + "                            AND REMOVE = '0' "
        + "                            AND LIMIT_AREA_ID = #{limitAreaId,jdbcType=VARCHAR} "
        + "                            AND ALARM_DEAL_STATUS = '103002002') "// 报警状态为‘未处理’
        + "   AND LICENCE_PLATE_NO = #{carrierName,jdbcType=VARCHAR} "
        + "   AND ALARM_TYPE = #{alarmType,jdbcType=VARCHAR} "
        + "   AND REMOVE = '0' "
        + "   AND LIMIT_AREA_ID = #{limitAreaId,jdbcType=VARCHAR} "
        + "   AND ALARM_DEAL_STATUS = '103002002' " // 报警状态为‘未处理’
        + "   ORDER BY ALARM_DATE DESC")
    List<String> findLastAlarmByCarrierName(@Param("carrierName")
    String carrierName, @Param("alarmType")
    String alarmType, @Param("limitAreaId")
    String limitAreaId)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉通过车牌号查询最近的一次报警 〈功能详细描述〉
     * 
     * @param map
     *            map
     * @return int
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    int findAlarmNumForDiTu(Map<String, Object> map)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉通过车牌号查询最近的一次报警 〈功能详细描述〉
     * 
     * @param identificationCardNo 驾驶员身份证号
     * @param verifyStatus APP审核状态（已通过）
     * @return Map Map
     * @see [类、类#方法、类#成员]
     */
    Map<String, String> findRegId(@Param("identificationCardNo")
    String identificationCardNo, @Param("verifyStatus")
    String verifyStatus)
        throws Exception;
    
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
     * 〈一句话功能简述〉根据车牌号和GPS时间查询报警记录
     * 〈功能详细描述〉
     * 
     * @param
     * @return
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    List<AlarmEntity> findAlarmByCarrierNameAndAlarmTime(@Param("carrierName")
    String carrierName, @Param("gpsTime")
    String gpsTime)
        // //Map<String,Object> map
        throws Exception;
    
    /**
     * 〈一句话功能简述〉通过账号查询运单 〈功能详细描述〉
     * 
     * @param String
     *            account
     * @return List<WayBillEntity>
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    List<String> findForAppByAccount(String account)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉通过车牌号和起始时间和结束时间查询报警信息〈功能详细描述〉
     * 
     * @param String
     *            carrierName
     * @param String
     *            startTime
     * @param String
     *            endTime
     * @return List<AlarmEntity>
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    List<AlarmEntity> findAlarmByCarrierNameAndStartEndTime(
        @Param("carrierName")
        String carrierName, @Param("startTime")
        String startTime, @Param("endTime")
        String endTime)
        throws Exception;
}
