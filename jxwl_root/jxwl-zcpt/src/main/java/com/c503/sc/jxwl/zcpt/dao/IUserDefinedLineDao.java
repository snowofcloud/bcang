/**
 * 文件名：IUserDefinedLineDao.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2017-4-13
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.zcpt.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import com.c503.sc.jxwl.zcpt.bean.UserDefinedLine;

/**
 * 
 * 〈一句话功能简述〉自定义线条
 * 〈功能详细描述〉
 * 
 * @author guqh
 * @version [版本号, 2017-4-13]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Repository(value = "userDefinedLineDao")
public interface IUserDefinedLineDao {
    
    /************************************** 自定义线条 FIXME *********************************/
    /**
     * 〈一句话功能简述〉保存自定义线条
     * 〈功能详细描述〉
     * 
     * @param userDefinedLine userDefinedLine
     * @return int
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @Insert("INSERT INTO T_USER_DEFINED_LINE (ID, POINTS, LINE_NAME, REMOVE,"
        + " CREATE_BY, CREATE_TIME, UPDATE_BY,  UPDATE_TIME,"
        + " START_LAT, START_LNG, END_LAT, END_LNG)"
        + " VALUES (#{id,jdbcType=VARCHAR}, #{points,jdbcType=CLOB}, #{lineName,jdbcType=VARCHAR},'0', "
        + " #{createBy,jdbcType=VARCHAR}, #{createTime,jdbcType=DATE},"
        + " #{updateBy,jdbcType=VARCHAR},  #{updateTime,jdbcType=DATE},"
        + " #{startLat,jdbcType=VARCHAR},  #{startLng,jdbcType=VARCHAR},"
        + " #{endLat,jdbcType=VARCHAR},  #{endLng,jdbcType=VARCHAR})")
    int saveUserDefinedLine(UserDefinedLine userDefinedLine)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉删除自定义线条
     * 〈功能详细描述〉
     * 
     * @param id id
     * @return 删除条数
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @Delete("UPDATE  T_USER_DEFINED_LINE " +
    		"SET REMOVE = '1' " +
    		"WHERE ID = #{id,jdbcType=VARCHAR}")
    int deleteUserDefinedLine(@Param("id")
    String id)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉更新自定义线条
     * 〈功能详细描述〉
     * 
     * @param userDefinedLine userDefinedLine
     * @return 影响条数
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @Update("UPDATE T_USER_DEFINED_LINE SET"
        + " LINE_NAME  = #{lineName,jdbcType=VARCHAR}, "
        + " UPDATE_BY   = #{updateBy,jdbcType=VARCHAR}, "
        + " UPDATE_TIME = #{updateTime,jdbcType=DATE} "
        + " WHERE ID = #{id,jdbcType=VARCHAR}")
    int updateUserDefinedLine(UserDefinedLine userDefinedLine)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉查询所有有效的限制线条
     * 〈功能详细描述〉
     * 
     * @return List<UserDefinedLine>
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @Select("SELECT ID, LINE_NAME, POINTS, START_LAT, START_LNG, END_LAT, END_LNG FROM T_USER_DEFINED_LINE " +
    		"WHERE REMOVE = '0' ")
    @Results({
        @Result(id = true, column = "id", property = "id", jdbcType = JdbcType.VARCHAR),
        @Result(column = "LINE_NAME", property = "lineName", jdbcType = JdbcType.VARCHAR),
        @Result(column = "POINTS", property = "points", jdbcType = JdbcType.CLOB),
        @Result(column = "START_LAT", property = "startLat", jdbcType = JdbcType.VARCHAR),
        @Result(column = "START_LNG", property = "startLng", jdbcType = JdbcType.VARCHAR),
        @Result(column = "END_LAT", property = "endLat", jdbcType = JdbcType.VARCHAR),
        @Result(column = "END_LNG", property = "endLng", jdbcType = JdbcType.VARCHAR)})
    List<UserDefinedLine> findUserDefinedLines()
        throws Exception;
    
    /**
     * 〈一句话功能简述〉查询自定义线条（根据id查询）
     * 〈功能详细描述〉
     * 
     * @param id 线条id
     * @return UserDefinedLine
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @Select("SELECT ID, LINE_NAME, POINTS, START_LAT, START_LNG, END_LAT, END_LNG  FROM T_USER_DEFINED_LINE WHERE ID = #{id,jdbcType=VARCHAR}")
    @Results({
        @Result(id = true, column = "id", property = "id", jdbcType = JdbcType.VARCHAR),
        @Result(column = "LINE_NAME", property = "lineName", jdbcType = JdbcType.VARCHAR),
        @Result(column = "POINTS", property = "points", jdbcType = JdbcType.CLOB),
        @Result(column = "START_LAT", property = "startLat", jdbcType = JdbcType.VARCHAR),
        @Result(column = "START_LNG", property = "startLng", jdbcType = JdbcType.VARCHAR),
        @Result(column = "END_LAT", property = "endLat", jdbcType = JdbcType.VARCHAR),
        @Result(column = "END_LNG", property = "endLng", jdbcType = JdbcType.VARCHAR)})
    UserDefinedLine findUserDefinedLineById(@Param("id")
    String id)
        throws Exception;
}
