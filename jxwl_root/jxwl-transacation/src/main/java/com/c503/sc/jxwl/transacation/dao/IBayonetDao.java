package com.c503.sc.jxwl.transacation.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.c503.sc.jxwl.transacation.vo.BayOne;

/**
 * 〈一句话功能简述〉卡口审核dao
 * 〈功能详细描述〉
 * 
 * @author
 * @version [版本号, 2016-12-28]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Repository(value = "bayonetdao")
public interface IBayonetDao {
    
    /**
     * 〈一句话功能简述〉查询所有记录
     * 〈功能详细描述〉
     * 
     * @param map map
     * @return List<BayOne>
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    List<BayOne> findByParams(Map<String, Object> map)
        throws Exception;
    /**
     * 〈一句话功能简述〉保存审核
     * 〈功能详细描述〉
     * 
     * @param map map
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    void save(Map<String, Object> map)
        throws Exception;
    /**
     * 〈一句话功能简述〉根据订单号
     * 〈功能详细描述〉
     * 
     * @param map map
     * @return BayOne
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    List<BayOne> findByOrders(Map<String, Object> map)
        throws Exception;
    /**
     * 〈一句话功能简述〉根据订单号
     * 〈功能详细描述〉
     * 
     * @param map map
     * @return BayOne
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    BayOne findRecordById(Map<String, Object> map)
        throws Exception;
    /**
     * 〈一句话功能简述〉查询车辆信息
     * 〈功能详细描述〉
     * 
     * @param map map
     * @return String
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @Select("SELECT  ID FROM T_BAYONET WHERE ORDERS  = #{orders} AND REMOVE = #{remove}")
    String findBayId(Map<String, Object> map)
        throws Exception;
    /**
     * 〈一句话功能简述〉查询车辆信息
     * 〈功能详细描述〉
     * 
     * @param map map
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    void update(Map<String, Object> map)
        throws Exception;
    /**
     * 〈一句话功能简述〉查询车辆信息
     * 〈功能详细描述〉
     * 
     * @param map map
     * @return BayOne
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    BayOne findByCarName(Map<String, Object> map)
        throws Exception;
    
}
