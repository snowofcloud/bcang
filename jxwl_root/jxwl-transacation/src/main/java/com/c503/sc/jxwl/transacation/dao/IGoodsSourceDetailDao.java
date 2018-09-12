/**
 * 文件名：IOrderManageDao.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-8-31
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.transacation.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.c503.sc.jxwl.transacation.bean.LeaveMessageEntity;
import com.c503.sc.jxwl.transacation.bean.SrcGoods;

/**
 * 〈一句话功能简述〉订单管理dao
 * 〈功能详细描述〉
 * 
 * @author xiaoqx
 * @version [版本号, 2016-8-31]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Repository(value = "goodsSourceDetailDao")
public interface IGoodsSourceDetailDao {
    
    /**
     * 〈一句话功能简述〉分页查询订单信息
     * 〈功能详细描述〉
     * 
     * @param map map
     * @return SrcGoods
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    List<SrcGoods> findByParams(Map<String, Object> map)
        throws Exception;
    
    /**
     *〈一句话功能简述〉查看货源详情
     * 〈功能详细描述〉
     * @param map map
     * @return LeaveMessageEntity
     * @throws Exception Exception
     * @see  [类、类#方法、类#成员]
     */
    LeaveMessageEntity findById(Map<String, Object> map) throws Exception;
    
   
}
