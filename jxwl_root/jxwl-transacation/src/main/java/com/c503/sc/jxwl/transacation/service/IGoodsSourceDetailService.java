package com.c503.sc.jxwl.transacation.service;

import java.util.List;
import java.util.Map;

import com.c503.sc.jxwl.transacation.bean.LeaveMessageEntity;
import com.c503.sc.jxwl.transacation.bean.SrcGoods;

/**
 * 〈一句话功能简述〉货源信息--接口
 * 〈功能详细描述〉
 * 
 * @author wangwenhuan
 * @version [版本号, 2016-9-3]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface IGoodsSourceDetailService {
	
	/**
     * 〈一句话功能简述〉查询所有货源信息
     * 〈功能详细描述〉
     * 
     * @param map 货单号, 录入时间
     * @return List<SrcGoods>
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    List<SrcGoods> findByParams(Map<String, Object> map)
        throws Exception;
    
    /**
     *〈一句话功能简述〉查看货源详情 〈功能详细描述〉
     * 〈功能详细描述〉
     * @param map map
     * @return LeaveMessageEntity
     * @throws Exception Exception
     * @see  [类、类#方法、类#成员]
     */
    LeaveMessageEntity findById(Map<String, Object> map)
            throws Exception;
}
