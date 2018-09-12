/**
 * 文件名：ICarLocationDao.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-9-6
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.transacation.dao;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import com.c503.sc.jxwl.transacation.bean.ParkArea;

/**
 * 〈一句话功能简述〉园区区域dao
 * 〈功能详细描述〉
 * 
 * @author zz
 * @version [版本号, 2016-9-6]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Repository(value = "parkAreaDao")
public interface IParkAreaDao {
    
    /**
     * 〈一句话功能简述〉查询车辆最近的路径规划
     * 〈功能详细描述〉
     * 
     * @return ParkArea
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @Select("SELECT ID, POINTS FROM (SELECT ID, POINTS  "
        + "            FROM T_PARK_AREA "
        + "           ORDER BY CREATE_TIME DESC) TEMP "
        + "    WHERE ROWNUM = 1  ")
    @Results({
        @Result(id = true, column = "id", property = "id", jdbcType = JdbcType.VARCHAR),
        @Result(column = "POINTS", property = "points", jdbcType = JdbcType.CLOB)})
    ParkArea findParkArea()
        throws Exception;
}
