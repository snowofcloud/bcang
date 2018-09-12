package com.c503.sc.jxwl.transacation.service;

import java.util.List;
import java.util.Map;

import com.c503.sc.jxwl.transacation.bean.WayBillGoodsTempEntity;

/**
 * 〈一句话功能简述〉货源接口
 * 〈功能详细描述〉
 * 
 * @author wl
 * @version [版本号, 2016-8-30]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface IWayBillGoodsTempService {
    
   
    /**
     *〈一句话功能简述〉 分页查询货物信息
     * 〈功能详细描述〉
     * @param map 发布开始时间、结束时间
     * @return  List<WayBillGoodsTempEntity>
     * @throws Exception Exception
     * @see  [类、类#方法、类#成员]
     */
    List<WayBillGoodsTempEntity> findByParams(Map<String, Object> map)
        throws Exception;
    
    /**
     *〈一句话功能简述〉保存货物信息
     * 〈功能详细描述〉
     * @param entity entity
     * @return Object
     * @throws Exception Exception
     * @see  [类、类#方法、类#成员]
     */
    Object save(WayBillGoodsTempEntity entity)
        throws Exception;
    
    
    /**
     *〈一句话功能简述〉 删除货源
     * 〈功能详细描述〉
     * @param map id
     * @return 影响行数
     * @throws Exception Exception
     * @see  [类、类#方法、类#成员]
     */
    int delete(Map<String, Object> map)
        throws Exception;
    
    /**
     *〈一句话功能简述〉 修改货源
     * 〈功能详细描述〉
     * @param entity entity
     * @return entity
     * @throws Exception Exception
     * @see  [类、类#方法、类#成员]
     */
    int update(WayBillGoodsTempEntity entity)
        throws Exception;
    
    /** 
     *〈一句话功能简述〉 查询货物信息
     * 〈功能详细描述〉
     * @param id id
     * @return entity entity
     * @throws Exception Exception
     * @see  [类、类#方法、类#成员]
     */
    WayBillGoodsTempEntity findById(String id)
        throws Exception;

    
}
