/**
 * 文件名：ICarLocationDao.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-9-6
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.transacation.dao;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import com.c503.sc.jxwl.transacation.bean.KaDoor;

/**
 * 〈一句话功能简述〉dao
 * 〈功能详细描述〉
 * 
 * @author zz
 * @version [版本号, 2016-9-6]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Repository(value = "kaDoorDao")
public interface IKaDoorDao {
    
    /**
     * 
     * 〈一句话功能简述〉查询卡口〈功能详细描述〉
     * 
     * @return 返回成功 的数据信息
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @Select("SELECT T.ID, T.NAME, T.LNG, T.LAT FROM T_KA_OPEN T")
    @Results({
        @Result(id = true, column = "id", property = "id", jdbcType = JdbcType.VARCHAR),
        @Result(column = "NAME", property = "name", jdbcType = JdbcType.VARCHAR),
        @Result(column = "LNG", property = "lng", jdbcType = JdbcType.VARCHAR),
        @Result(column = "LAT", property = "lat", jdbcType = JdbcType.VARCHAR)})
    List<KaDoor> findAllKaDoor()
        throws Exception;
}
