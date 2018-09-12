/**
 * 文件名：IOrderManageService.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-8-31
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.transacation.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.c503.sc.jxwl.transacation.bean.CommentComplain;
import com.c503.sc.jxwl.transacation.bean.OrderForFull;
import com.c503.sc.jxwl.transacation.bean.SrcGoods;

/**
 * 〈一句话功能简述〉订单管理
 * 〈功能详细描述〉
 * 
 * @author xiaoqx
 * @version [版本号, 2016-8-31]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface IOrderManageService {
    
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
     * 〈一句话功能简述〉通过id查询订单管理信息
     * 〈功能详细描述〉
     * 
     * @param id id
     * @param hgCorporateNo hgCorporateNo
     * @param wlCorporateNo wlCorporateNo
     * @param tradeObjCode 交易对象法人代码
     * @return SrcGoods
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    Object findById(String id, String hgCorporateNo, String wlCorporateNo,
        String tradeObjCode)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉查询订单的交易状态
     * 〈功能详细描述〉
     * 
     * @param id id
     * @return object
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    String findTradeStatus(String id)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉更新订单信息状态
     * 〈功能详细描述〉
     * 
     * @param id id
     * @param tradeStatus tradeStatus
     * @param updateBy updateBy
     * @param publishDate publishDate
     * @return 更新条数
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    int updateTradeStatus(String id, String tradeStatus, String updateBy,
        Date publishDate)
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
     * 〈一句话功能简述〉评价、投诉
     * 〈功能详细描述〉
     * 
     * @param commentComplain CommentComplain
     * @return 影响条数
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    int commentComplain(CommentComplain commentComplain)
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
     * @param tradeObjCode tradeObjCode
     * @return Object
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    Object findByIdForWeixin(String id, String tradeObjCode)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉模糊查询订单信息
     * 〈功能详细描述〉
     * 
     * @param map map
     * @return SrcGoodsEntity
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    List<SrcGoods> findOrderNo(Map<String, Object> map)
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
    
    /**
     * 〈一句话功能简述〉精确查询订单信息
     * 〈功能详细描述〉
     * 
     * @param map map
     * @return SrcGoodsEntity
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    List<SrcGoods> findAccurateOrderNo(Map<String, Object> map)
        throws Exception;
}
