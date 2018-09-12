/**
 * 文件名：ITextMsgDao.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-8-3
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.zcpt.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import com.c503.sc.jxwl.zcpt.bean.LimitArea;

/**
 * @author zz
 * @version [版本号, 2016-8-3]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Repository(value = "shortcutDao")
public interface IShortcutDao {
    /************************************** 车辆调度 FIXME *********************************/
    /**
     * 车辆调度：保存车辆调度信息
     * 
     * @param params 调度信息
     * @return 影响的条数
     * @throws Exception 异常
     * @see [类、类#方法、类#成员]
     */
    @Insert("INSERT INTO T_CAR_DISPATCH (ID, TERMINAL_TEL, CONTENT, "
        + " MARK,         MSG_ID,  MSG_FLOW_ID, "
        + " CARRIER_NAME, REMOVE,  MSG_DATE, " + " USER_NAME, SIGN) VALUES ("
        + " #{id,jdbcType=VARCHAR},    #{terminalTel,jdbcType=VARCHAR}, #{content,jdbcType=VARCHAR}, "
        + " #{mark,jdbcType=VARCHAR},  #{msgId,jdbcType=VARCHAR},       #{msgFlowId,jdbcType=VARCHAR}, "
        + " #{carrierName,jdbcType=VARCHAR}, '0',      #{createTime,jdbcType=VARCHAR}, "
        + " #{username,jdbcType=VARCHAR}, #{sign,jdbcType=CHAR})")
    void saveCarDispatch(Map<String, Object> params)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉查询车辆调度信息
     * 〈功能详细描述〉
     * 
     * @param carrierName 车牌号
     * @param userName 用户名
     * @return List<Map<String, Object>>
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @Select("SELECT TEMP.* " + "    FROM (SELECT T.* "
        + "            FROM T_CAR_DISPATCH T "
        + "           WHERE T.MSG_DATE IS NOT NULL "
        + "             AND T.REMOVE = '0' "
        + "             AND T.CARRIER_NAME = #{carrierName} "
        + "             AND T.USER_NAME = #{userName} "
        + "           ORDER BY T.MSG_DATE) TEMP " + "   WHERE ROWNUM <= 200")
    List<Map<String, Object>> findCarDispVal(@Param("carrierName")
    String carrierName, @Param("userName")
    String userName)
        throws Exception;
    
    /************************************** 区域限制 FIXME *********************************/
    /**
     * 〈一句话功能简述〉保存区域限制
     * 〈功能详细描述〉
     * 
     * @param limitArea limitArea
     * @return int
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @Insert("INSERT INTO T_LIMIT_AREA (ID, REMOVE, CREATE_BY,"
        + " CREATE_TIME, LIMIT_NAME, LIMIT_TYPE, "
        + " LIMIT_SPEED, UPDATE_BY,  UPDATE_TIME,"
        + " POINTS) "
        + " VALUES (#{id,jdbcType=VARCHAR}, '0', #{createBy,jdbcType=VARCHAR}, "
        + " #{createTime,jdbcType=DATE},    #{limitName,jdbcType=VARCHAR}, #{limitType,jdbcType=VARCHAR},"
        + " #{limitSpeed,jdbcType=DECIMAL}, #{updateBy,jdbcType=VARCHAR},  #{updateTime,jdbcType=DATE},"
        + " #{points,jdbcType=CLOB})")
    int saveLimitArea(LimitArea limitArea)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉删除区域限制
     * 〈功能详细描述〉
     * 
     * @param id id
     * @return 删除条数
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @Delete("UPDATE T_LIMIT_AREA " + "SET REMOVE = '1' "
        + "WHERE ID = #{id,jdbcType=VARCHAR}")
    int deleteLimitArea(@Param("id")
    String id)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉更新区域限制
     * 〈功能详细描述〉
     * 
     * @param limitArea limitArea
     * @return 影响条数
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @Update("UPDATE T_LIMIT_AREA SET"
        + " LIMIT_NAME  = #{limitName,jdbcType=VARCHAR}, "
        + " LIMIT_TYPE  = #{limitType,jdbcType=VARCHAR}, "
        + " LIMIT_SPEED = #{limitSpeed,jdbcType=DECIMAL},"
        + " UPDATE_BY   = #{updateBy,jdbcType=VARCHAR}, "
        + " UPDATE_TIME = #{updateTime,jdbcType=DATE} "
        + " WHERE ID = #{id,jdbcType=VARCHAR}")
    int updateLimitArea(LimitArea limitArea)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉查询所有有效的限制区域
     * 〈功能详细描述〉
     * 
     * @return List<LimitArea>
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @Select("SELECT ID, LIMIT_NAME, LIMIT_TYPE, LIMIT_SPEED, POINTS FROM T_LIMIT_AREA "
        + "WHERE REMOVE = '0' ")
    @Results({
        @Result(id = true, column = "id", property = "id", jdbcType = JdbcType.VARCHAR),
        @Result(column = "LIMIT_NAME", property = "limitName", jdbcType = JdbcType.VARCHAR),
        @Result(column = "LIMIT_TYPE", property = "limitType", jdbcType = JdbcType.VARCHAR),
        @Result(column = "LIMIT_SPEED", property = "limitSpeed", jdbcType = JdbcType.DECIMAL),
        @Result(column = "POINTS", property = "points", jdbcType = JdbcType.CLOB)})
    List<LimitArea> findLimitAreas()
        throws Exception;
    
    /**
     * 〈一句话功能简述〉查询所有不能进入的限制区域
     * 〈功能详细描述〉
     * 
     * @return List<LimitArea>
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @Select("SELECT ID, LIMIT_NAME, LIMIT_TYPE, LIMIT_SPEED, POINTS FROM T_LIMIT_AREA WHERE LIMIT_TYPE= '112001003'")
    @Results({
        @Result(id = true, column = "id", property = "id", jdbcType = JdbcType.VARCHAR),
        @Result(column = "LIMIT_NAME", property = "limitName", jdbcType = JdbcType.VARCHAR),
        @Result(column = "LIMIT_TYPE", property = "limitType", jdbcType = JdbcType.VARCHAR),
        @Result(column = "LIMIT_SPEED", property = "limitSpeed", jdbcType = JdbcType.DECIMAL),
        @Result(column = "POINTS", property = "points", jdbcType = JdbcType.CLOB)})
    List<LimitArea> findAllNotInAreas()
        throws Exception;
    
    /**
     * 〈一句话功能简述〉查询区域限制（根据id查询）
     * 〈功能详细描述〉
     * 
     * @param id 区域id
     * @return LimitArea
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @Select("SELECT ID, LIMIT_NAME, LIMIT_TYPE, LIMIT_SPEED, POINTS FROM T_LIMIT_AREA WHERE ID = #{id,jdbcType=VARCHAR}")
    @Results({
        @Result(id = true, column = "id", property = "id", jdbcType = JdbcType.VARCHAR),
        @Result(column = "LIMIT_NAME", property = "limitName", jdbcType = JdbcType.VARCHAR),
        @Result(column = "LIMIT_TYPE", property = "limitType", jdbcType = JdbcType.VARCHAR),
        @Result(column = "LIMIT_SPEED", property = "limitSpeed", jdbcType = JdbcType.DECIMAL),
        @Result(column = "POINTS", property = "points", jdbcType = JdbcType.CLOB)})
    LimitArea findLimitAreaById(@Param("id")
    String id)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉根据车牌号查询regid
     * 〈功能详细描述〉
     * 
     * @param id 区域id
     * @return LimitArea
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @Select("select TR.REGID " + "from T_WAYBILL TW "
        + "LEFT JOIN T_OCCUPATION_PERSON TOC "
        + "ON TOC.IDENTIFICATION_CARD_NO = TW.DRIVERID "
        + "AND TOC.REMOVE = '0' " + "LEFT JOIN T_REGISTER_INFO TR "
        + "ON TR.OCCUPATION_ID = TOC.ID " + "AND TR.REMOVE = '0' "
        + "WHERE TW.CARNO = #{carrierName,jdbcType=VARCHAR} "
        + "AND TW.CHECKSTATUS IN ('111001002', '111001003')"
        + "AND TW.REMOVE = '0' ")
    String findRegidByCarrierName(String carrierName)
        throws Exception;
    
}
