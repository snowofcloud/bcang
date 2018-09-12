/**
 * 文件名：ICarLocationDao.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-9-6
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.zcpt.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import com.c503.sc.jxwl.zcpt.bean.SolveRoute;

/**
 * 〈一句话功能简述〉路径规划dao
 * 〈功能详细描述〉
 * 
 * @author zz
 * @version [版本号, 2016-9-6]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Repository(value = "solveRouteDao")
public interface ISolveRouteDao {
    /**
     * 〈一句话功能简述〉保存路径规划
     * 〈功能详细描述〉
     * 
     * @param sovleRoute SolveRoute
     * @return 1
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @Insert("insert into T_SOLVE_ROUTE (ID, CREATE_TIME, REMOVE, "
        + "    CARRIER_NAME, POINTS, WAYBILL_ID) "
        + "  values (#{id,jdbcType=VARCHAR},    #{createTime,jdbcType=TIMESTAMP}, '0', "
        + "    #{carrierName,jdbcType=VARCHAR}, #{points,jdbcType=CLOB}, #{waybillId,jdbcType=VARCHAR})")
    int saveSolveRoute(SolveRoute sovleRoute)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉查询车辆最近的路径规划
     * 〈功能详细描述〉
     * 
     * @param carrierName carrierName
     * @return SolveRoute
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @Select("SELECT ID, CARRIER_NAME, POINTS "
        + "    FROM (SELECT ID, CARRIER_NAME, POINTS  "
        + "            FROM T_SOLVE_ROUTE "
        + "           WHERE CARRIER_NAME = #{carrierName,jdbcType=VARCHAR} "
        + "           ORDER BY CREATE_TIME DESC) TEMP "
        + "    WHERE ROWNUM = 1  ")
    @Results({
        @Result(id = true, column = "id", property = "id", jdbcType = JdbcType.VARCHAR),
        @Result(column = "CARRIER_NAME", property = "carrierName", jdbcType = JdbcType.VARCHAR),
        @Result(column = "POINTS", property = "points", jdbcType = JdbcType.CLOB)})
    SolveRoute findLastRoute(String carrierName)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉根据运单号查询路径规划
     * 〈功能详细描述〉
     * 
     * @param waybillId waybillId
     * @return SolveRoute
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @Select("SELECT ID, CARRIER_NAME, POINTS, WAYBILL_ID "
        + "    FROM (SELECT ID, CARRIER_NAME, POINTS, WAYBILL_ID  "
        + "            FROM T_SOLVE_ROUTE "
        + "           WHERE WAYBILL_ID = #{waybillId,jdbcType=VARCHAR} "
        + "           ORDER BY CREATE_TIME DESC) TEMP "
        + "    WHERE ROWNUM = 1  ")
    @Results({
        @Result(id = true, column = "id", property = "id", jdbcType = JdbcType.VARCHAR),
        @Result(column = "CARRIER_NAME", property = "carrierName", jdbcType = JdbcType.VARCHAR),
        @Result(column = "WAYBILL_ID", property = "waybillId", jdbcType = JdbcType.VARCHAR),
        @Result(column = "POINTS", property = "points", jdbcType = JdbcType.CLOB)})
    SolveRoute findRouteByWaybillId(String waybillId)
        throws Exception;
}
