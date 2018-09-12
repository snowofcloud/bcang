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

import com.c503.sc.jxwl.transacation.bean.WayBillGoodsTempEntity;

 

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * 
 * @author wl
 * @version [版本号, 2016-8-31]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Repository(value = "wayBillGoodsTempDao")
public interface IWayBillGoodsTempDao  {
   
    /**
     * 〈一句话功能简述〉分页查询模板--货物信息
     * 〈功能详细描述〉
     * 
     * @param map map
     * @return List<WayBillGoodsTempEntity>
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    List<WayBillGoodsTempEntity> findByParams(Map<String, Object> map)
        throws Exception;
    
    
    /**
     * 〈一句话功能简述〉查询模板--货物信息
     * 〈功能详细描述〉
     * 
     * @param id id
     * @return List<WayBillGoodsTempEntity>
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    WayBillGoodsTempEntity findById(String id)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉查询模板--所有货物信息
     * 〈功能详细描述〉
     * 
     * @param id id
     * @return List<WayBillGoodsTempEntity>
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    List<WayBillGoodsTempEntity> findAllGoods(String id)
        throws Exception;
    
    
    /**
     * 
     * 〈一句话功能简述〉保存模板--货物信息
     * 〈功能详细描述〉
     * 
     * @param entity WayBillGoodsTempEntity
     * @return 影响行数
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    int save(WayBillGoodsTempEntity entity)
        throws Exception;
    
    
    /**
     * 
     * 〈一句话功能简述〉修改模板--货物信息
     * 〈功能详细描述〉
     * 
     * @param entity 实体类
     * @return 保存条数
     * @throws Exception 异常
     * @see [类、类#方法、类#成员]
     */
    int update(WayBillGoodsTempEntity entity)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉删除模板--货物信息
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
