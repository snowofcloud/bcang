/**
 * 文件名：IOrderManageDao.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-8-31
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.transacation.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.c503.sc.jxwl.transacation.bean.CommentComplain;
import com.c503.sc.jxwl.transacation.bean.OrderForFull;
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
@Repository(value = "orderManageDao")
public interface IOrderManageDao {
    
    /**
     * 〈一句话功能简述〉分页查询订单信息
     * 〈功能详细描述〉
     * 
     * @param map map
     * @return SrcGoodsEntity
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    List<SrcGoods> findByParams(Map<String, Object> map)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉查询所有已签订的订单
     * 〈功能详细描述〉
     * 
     * @param map map
     * @return SrcGoods
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    List<OrderForFull> findAllOrders(Map<String, Object> map)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉
     * 〈功能详细描述〉
     * 
     * @param id id
     * @param hgCorporateNo hgCorporateNo
     * @param wlCorporateNo wlCorporateNo
     * @return SrcGoods
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    SrcGoods findById(@Param("id")
    String id, @Param("hgCorporateNo")
    String hgCorporateNo, @Param("wlCorporateNo")
    String wlCorporateNo)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉查询交易状态
     * 〈功能详细描述〉
     * 
     * @param id id
     * @return tradeStatus
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    String findTradeStatus(String id)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉更新交易状态
     * 〈功能详细描述〉
     * 
     * @param id id
     * @param tradeStatus tradeStatus
     * @param updateBy updateBy
     * @param publishDate publishDate
     * @return object
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    int updateTradeStatus(@Param("id")
    String id, @Param("tradeStatus")
    String tradeStatus, @Param("updateBy")
    String updateBy, @Param("publishDate")
    Date publishDate)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉findCommentComplainByOrderId
     * 〈功能详细描述〉物流、化工查看评价投诉
     * 
     * @param id id
     * @param tradeObjCode tradeObjCode
     * @return List<CommentComplain>
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    List<CommentComplain> findCommentComplainByOrderId(@Param("orderId")
    String id, @Param("tradeObjCode")
    String tradeObjCode)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉findComComByOrderIdForzf
     * 〈功能详细描述〉政府查看评价（只看化工对物流的评价、投诉）
     * 
     * @param id id
     * @param tradeObjCode tradeObjCode
     * @return List<CommentComplain>
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    List<CommentComplain> findComComByOrderIdForzf(@Param("orderId")
    String id, @Param("tradeObjCode")
    String tradeObjCode)
        throws Exception;
    
    /************************************** 查询所有订单（微信公众号） ************************************/
    
    /**
     * 〈一句话功能简述〉查询当前选中企业所有订单
     * 〈功能详细描述〉
     * 
     * @param map orderNo, corporateNo
     * @return List<Map<String, Object>>
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    List<Map<String, Object>> findDataForWeixinByParams(Map<String, Object> map)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉微信查看详情
     * 〈功能详细描述〉
     * 
     * @param id id
     * @return SrcGoods
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    SrcGoods findByIdForWeixin(String id)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉模糊查询订单信息
     * 〈功能详细描述〉
     * 
     * @param map map
     * @return List
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    List<SrcGoods> findOrderNo(Map<String, Object> map)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉精确查询订单信息
     * 〈功能详细描述〉
     * 
     * @param map map
     * @return List
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    List<SrcGoods> findAccurateOrderNo(Map<String, Object> map)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉化工确认订单状态
     * 〈功能详细描述〉
     * 
     * @param map map
     * @return List
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    List<String> hgConfirmOrderStatus(Map<String, Object> map)
        throws Exception;
}
