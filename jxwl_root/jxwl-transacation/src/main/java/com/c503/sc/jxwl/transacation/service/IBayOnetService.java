package com.c503.sc.jxwl.transacation.service;

import java.util.List;
import java.util.Map;

import com.c503.sc.jxwl.transacation.vo.BayOne;

/**
 * 〈一句话功能简述〉卡口审核
 * 〈功能详细描述〉
 * 
 * @author
 * @version [版本号, 2016-12-28]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface IBayOnetService {
    /**
     * 〈一句话功能简述〉findByParams
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
     * 〈一句话功能简述〉卡口审核保存
     * 〈功能详细描述〉
     * 
     * @param map map
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    void save(Map<String, Object> map)
        throws Exception;
    /**
     * 〈一句话功能简述〉findByOrders
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
     * 〈一句话功能简述〉findByCarName
     * 〈功能详细描述〉
     * 
     * @param map map
     * @return BayOne
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    BayOne findByCarName(Map<String, Object> map)
        throws Exception;
    /**
     * 〈一句话功能简述〉findRecordById
     * 〈功能详细描述〉
     * 
     * @param map map
     * @return BayOne
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
	BayOne findRecordById(Map<String, Object> map) 
		throws Exception;
}
