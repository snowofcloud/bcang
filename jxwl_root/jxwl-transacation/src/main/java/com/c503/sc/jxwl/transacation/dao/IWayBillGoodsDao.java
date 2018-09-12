/**
 * 文件名：ISrcGoodsDao.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-8-30
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.transacation.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.c503.sc.jxwl.transacation.bean.WayBillGoodsEntity;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * 
 * @author wl
 * @version [版本号, 2016-8-31]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Repository(value = "wayBillGoodsDao")
public interface IWayBillGoodsDao {
    
    /**
     * 〈一句话功能简述〉分页查询留言信息
     * 〈功能详细描述〉
     * 
     * @param map map
     * @return List<WayBillGoodsEntity>
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    List<WayBillGoodsEntity> findByParams(Map<String, Object> map)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉查询货物信息
     * 〈功能详细描述〉
     * 
     * @param id id
     * @return List<WayBillGoodsEntity>
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    WayBillGoodsEntity findById(String id)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉 查询所有货物信息
     * 〈功能详细描述〉
     * 
     * @param id id
     * @return List<WayBillGoodsEntity>
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    List<WayBillGoodsEntity> findAllGoods(String id)
        throws Exception;
    
    /**
     * 
     * 〈一句话功能简述〉保存货物信息
     * 〈功能详细描述〉
     * 
     * @param entity WayBillGoodsEntity
     * @return 影响行数
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    int save(WayBillGoodsEntity entity)
        throws Exception;
    
    /**
     * 
     * 〈一句话功能简述〉 修改货物信息
     * 〈功能详细描述〉
     * 
     * @param entity 实体类
     * @return 保存条数
     * @throws Exception 异常
     * @see [类、类#方法、类#成员]
     */
    int update(WayBillGoodsEntity entity)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉删除
     * 〈功能详细描述〉
     * 
     * @param map id、remove='1'、updateBy、updateTime
     * @return 影响条数
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    int delete(Map<String, Object> map)
        throws Exception;
    
}
