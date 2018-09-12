/**
 * 文件名：IUserDefinedAreaDao.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2017-3-30
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

import com.c503.sc.jxwl.zcpt.bean.UserDefinedArea;

/**
 * 
 * 〈一句话功能简述〉自定义区域
 * 〈功能详细描述〉
 * 
 * @author guqh
 * @version [版本号, 2017-3-30]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Repository(value = "userDefinedAreaDao")
public interface IUserDefinedAreaDao {
    /************************************** 自定义区域 FIXME *********************************/
    /**
     * 〈一句话功能简述〉保存自定义区域
     * 〈功能详细描述〉
     * 
     * @param userDefinedArea userDefinedArea
     * @return int
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @Insert("INSERT INTO T_USER_DEFINED_AREA (ID, POINTS, AREA_NAME, REMOVE,"
        + " CREATE_BY, CREATE_TIME, UPDATE_BY,  UPDATE_TIME)"
        + " VALUES (#{id,jdbcType=VARCHAR}, #{points,jdbcType=CLOB}, #{areaName,jdbcType=VARCHAR},'0', "
        + " #{createBy,jdbcType=VARCHAR}, #{createTime,jdbcType=DATE},"
        + " #{updateBy,jdbcType=VARCHAR},  #{updateTime,jdbcType=DATE})")
    int saveUserDefinedArea(UserDefinedArea userDefinedArea)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉删除自定义区域
     * 〈功能详细描述〉
     * 
     * @param id id
     * @return 删除条数
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @Update("UPDATE T_USER_DEFINED_AREA SET"
            + " REMOVE  = '1' "
    		+ " WHERE ID = #{id,jdbcType=VARCHAR}")
    int deleteUserDefinedArea(@Param("id")
    String id)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉更新自定义区域
     * 〈功能详细描述〉
     * 
     * @param userDefinedArea userDefinedArea
     * @return 影响条数
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @Update("UPDATE T_USER_DEFINED_AREA SET"
        + " AREA_NAME  = #{areaName,jdbcType=VARCHAR}, "
        + " UPDATE_BY   = #{updateBy,jdbcType=VARCHAR}, "
        + " UPDATE_TIME = #{updateTime,jdbcType=DATE} "
        + " WHERE ID = #{id,jdbcType=VARCHAR}")
    int updateUserDefinedArea(UserDefinedArea userDefinedArea)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉查询所有有效的限制区域
     * 〈功能详细描述〉
     * 
     * @return List<UserDefinedArea>
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @Select("SELECT ID, AREA_NAME, POINTS FROM T_USER_DEFINED_AREA " +
    		"WHERE REMOVE = '0'")
    @Results({
        @Result(id = true, column = "id", property = "id", jdbcType = JdbcType.VARCHAR),
        @Result(column = "AREA_NAME", property = "areaName", jdbcType = JdbcType.VARCHAR),
        @Result(column = "POINTS", property = "points", jdbcType = JdbcType.CLOB)})
    List<UserDefinedArea> findUserDefinedAreas()
        throws Exception;
    
    /**
     * 〈一句话功能简述〉查询自定义区域（根据id查询）
     * 〈功能详细描述〉
     * 
     * @param id 区域id
     * @return UserDefinedArea
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @Select("SELECT ID, AREA_NAME, POINTS FROM T_USER_DEFINED_AREA WHERE ID = #{id,jdbcType=VARCHAR}")
    @Results({
        @Result(id = true, column = "id", property = "id", jdbcType = JdbcType.VARCHAR),
        @Result(column = "AREA_NAME", property = "areaName", jdbcType = JdbcType.VARCHAR),
        @Result(column = "POINTS", property = "points", jdbcType = JdbcType.CLOB)})
    UserDefinedArea findUserDefinedAreaById(@Param("id")
    String id)
        throws Exception;
}
