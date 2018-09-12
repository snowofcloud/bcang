/**
 * 文件名：IUserDefinedFacilityDao.java
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

import com.c503.sc.jxwl.zcpt.bean.UserDefinedFacility;

/**
 * 
 * 〈一句话功能简述〉自定义设施
 * 〈功能详细描述〉
 * 
 * @author guqh
 * @version [版本号, 2017-3-30]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Repository(value = "userDefinedFacilityDao")
public interface IUserDefinedFacilityDao {
    /************************************** 自定义设施 FIXME *********************************/
    /**
     * 〈一句话功能简述〉保存自定义设施
     * 〈功能详细描述〉
     * 
     * @param userDefinedFacility userDefinedFacility
     * @return int
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @Insert("INSERT INTO T_USER_DEFINED_FACILITY (ID, ICON_ID, FACILITY_NAME, POINTS, REMOVE,"
        + " CREATE_BY, CREATE_TIME, UPDATE_BY,  UPDATE_TIME, longitude, latitude)"
        + " VALUES (#{id,jdbcType=VARCHAR}, #{iconId,jdbcType=VARCHAR}, #{facilityName,jdbcType=VARCHAR}, #{points,jdbcType=CLOB}, '0', "
        + " #{createBy,jdbcType=VARCHAR}, #{createTime,jdbcType=DATE},"
        + " #{updateBy,jdbcType=VARCHAR},  #{updateTime,jdbcType=DATE}," 
        + " #{longitude,jdbcType=VARCHAR},  #{latitude,jdbcType=VARCHAR})")
    int saveUserDefinedFacility(UserDefinedFacility userDefinedFacility)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉删除自定义设施
     * 〈功能详细描述〉
     * 
     * @param id id
     * @return 删除条数
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @Delete("UPDATE T_USER_DEFINED_FACILITY " +
    		"SET REMOVE = '1' " +
    		"WHERE ID = #{id,jdbcType=VARCHAR}")
    int deleteUserDefinedFacility(@Param("id")
    String id)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉更新自定义设施
     * 〈功能详细描述〉
     * 
     * @param userDefinedFacility userDefinedFacility
     * @return 影响条数
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @Update("UPDATE T_USER_DEFINED_FACILITY SET"
        + " FACILITY_NAME  = #{facilityName,jdbcType=VARCHAR}, "
    	+ " ICON_ID = #{iconId,jdbcType=VARCHAR}, "
        + " UPDATE_BY   = #{updateBy,jdbcType=VARCHAR}, "
        + " UPDATE_TIME = #{updateTime,jdbcType=DATE} "
        + " WHERE ID = #{id,jdbcType=VARCHAR}")
    int updateUserDefinedFacility(UserDefinedFacility userDefinedFacility)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉查询所有有效的限制设施
     * 〈功能详细描述〉
     * 
     * @return List<UserDefinedFacility>
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @Select("SELECT ID, FACILITY_NAME, ICON_ID, POINTS,longitude, latitude FROM T_USER_DEFINED_FACILITY " +
    		"WHERE REMOVE = '0'")
    @Results({
        @Result(id = true, column = "id", property = "id", jdbcType = JdbcType.VARCHAR),
        @Result(column = "FACILITY_NAME", property = "facilityName", jdbcType = JdbcType.VARCHAR),
        @Result(column = "ICON_ID", property = "iconId", jdbcType = JdbcType.VARCHAR),
        @Result(column = "POINTS", property = "points", jdbcType = JdbcType.CLOB),
        @Result(column = "longitude", property = "longitude", jdbcType = JdbcType.VARCHAR),
        @Result(column = "latitude", property = "latitude", jdbcType = JdbcType.VARCHAR)})
    List<UserDefinedFacility> findUserDefinedFacilitys()
        throws Exception;
    
    /**
     * 〈一句话功能简述〉查询自定义设施（根据id查询）
     * 〈功能详细描述〉
     * 
     * @param id 设施id
     * @return UserDefinedFacility
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @Select("SELECT ID, FACILITY_NAME, ICON_ID, POINTS, longitude, latitude FROM T_USER_DEFINED_FACILITY WHERE ID = #{id,jdbcType=VARCHAR}")
    @Results({
        @Result(id = true, column = "id", property = "id", jdbcType = JdbcType.VARCHAR),
        @Result(column = "FACILITY_NAME", property = "facilityName", jdbcType = JdbcType.VARCHAR),
        @Result(column = "ICON_ID", property = "iconId", jdbcType = JdbcType.VARCHAR),
        @Result(column = "POINTS", property = "points", jdbcType = JdbcType.CLOB),
        @Result(column = "longitude", property = "longitude", jdbcType = JdbcType.VARCHAR),
        @Result(column = "latitude", property = "latitude", jdbcType = JdbcType.VARCHAR)})
    UserDefinedFacility findUserDefinedFacilityById(@Param("id")
    String id)
        throws Exception;
}
