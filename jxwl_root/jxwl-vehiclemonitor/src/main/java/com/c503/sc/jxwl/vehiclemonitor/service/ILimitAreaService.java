package com.c503.sc.jxwl.vehiclemonitor.service;

import java.util.List;

import com.c503.sc.jxwl.zcpt.bean.LimitArea;

/**
 * 〈一句话功能简述〉限制区域业务接口
 * 〈功能详细描述〉
 * 
 * @author xiaoqx
 * @version [版本号, 2016-8-9]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface ILimitAreaService {
    
    /**
     * 〈一句话功能简述〉查询所有区域限制
     * 〈功能详细描述〉
     * 
     * @return List<LimitArea>
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    List<LimitArea> findAllAreaLimit()
        throws Exception;
    
    /**
     * 〈一句话功能简述〉查看区域限制
     * 〈功能详细描述〉
     * 
     * @param id id
     * @return LimitArea
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    LimitArea findByLimitAreaId(String id)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉修改限制区域
     * 〈功能详细描述〉
     * 
     * @param limitArea limitArea
     * @return int
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    int update(LimitArea limitArea)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉保存限制区域
     * 〈功能详细描述〉
     * 
     * @param limitArea limitArea
     * @return int
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    int save(LimitArea limitArea)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉删除限制区域
     * 〈功能详细描述〉
     * 
     * @param id id
     * @return int
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    int delLimitArea(String id)
        throws Exception;
    
}
