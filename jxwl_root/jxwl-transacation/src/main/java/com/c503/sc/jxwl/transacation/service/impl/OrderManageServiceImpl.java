/**
 * 文件名：OrderManageServiceImpl.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-8-31
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.transacation.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.c503.sc.base.exception.CustomException;
import com.c503.sc.jxwl.common.constant.CommonConstant;
import com.c503.sc.jxwl.transacation.bean.CommentComplain;
import com.c503.sc.jxwl.transacation.bean.OrderForFull;
import com.c503.sc.jxwl.transacation.bean.SrcGoods;
import com.c503.sc.jxwl.transacation.dao.ICommentComplainDao;
import com.c503.sc.jxwl.transacation.dao.IOrderManageDao;
import com.c503.sc.jxwl.transacation.service.IOrderManageService;
import com.c503.sc.log.LoggingManager;
import com.c503.sc.utils.common.SystemContants;

/**
 * 〈一句话功能简述〉订单管理 〈功能详细描述〉
 * 
 * @author xiaoqx
 * @version [版本号, 2016-8-31]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Service(value = "orderManageService")
public class OrderManageServiceImpl implements IOrderManageService {
    /** 日志器 */
    private static final LoggingManager LOGGER =
        LoggingManager.getLogger(OrderManageServiceImpl.class);
    
    /** 订单管理接口 */
    @Resource(name = "orderManageDao")
    private IOrderManageDao orderManageDao;
    
    /** 评价、投诉dao */
    @Resource
    private ICommentComplainDao commentComplainDao;
    
    @Override
    public List<SrcGoods> findByParams(Map<String, Object> map)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, map);
        List<SrcGoods> result = null;
        map.put("remove", SystemContants.ON);
        try {
            result = this.orderManageDao.findByParams(map);
        }
        catch (Exception e) {
            throw new CustomException(CommonConstant.SYS_EXCEPTION, e, map);
        }
        LOGGER.debug(SystemContants.DEBUG_END, map);
        return result;
    }
    
    @Override
    public List<OrderForFull> findAllOrders(Map<String, Object> map)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, map);
        List<OrderForFull> result = null;
        map.put("remove", SystemContants.ON);
        result = this.orderManageDao.findAllOrders(map);
        LOGGER.debug(SystemContants.DEBUG_END, map);
        return result;
    }
    
    @Override
    public Object findById(String id, String hgCorporateNo,
        String wlCorporateNo, String tradeObjCode)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, id);
        List<CommentComplain> comcom = null;
        
        Map<String, Object> map = new HashMap<>();
        // 查询货物信息
        SrcGoods srcGoods =
            this.orderManageDao.findById(id, hgCorporateNo, wlCorporateNo);
        // 政府查询订单相关的评价、投诉信息
        if (StringUtils.isEmpty(hgCorporateNo)
            && StringUtils.isEmpty(wlCorporateNo)) {
            comcom =
                this.orderManageDao.findComComByOrderIdForzf(id, tradeObjCode);
            // 化工、物流查看评价投诉
        }
        else {
            comcom =
                this.orderManageDao.findCommentComplainByOrderId(id,
                    tradeObjCode);
        }
        map.put("srcGoods", srcGoods);
        map.put("comcom", comcom);
        LOGGER.debug(SystemContants.DEBUG_END, id);
        
        return map;
    }
    
    @Override
    public String findTradeStatus(String id)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, id);
        String result = this.orderManageDao.findTradeStatus(id);
        LOGGER.debug(SystemContants.DEBUG_END, id);
        
        return result;
    }
    
    @Override
    @Transactional(rollbackFor = java.lang.Exception.class)
    public int updateTradeStatus(String id, String tradeStatus,
        String updateBy, Date publishDate)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, id);
        int upLine =
            this.orderManageDao.updateTradeStatus(id,
                tradeStatus,
                updateBy,
                publishDate);
        LOGGER.debug(SystemContants.DEBUG_END, id);
        
        return upLine;
    }
    
    @Override
    public int commentComplain(CommentComplain commentComplain)
        throws Exception {
        try {
            this.commentComplainDao.save(commentComplain);
        }
        catch (Exception e) {
            throw new CustomException(CommonConstant.SYS_EXCEPTION, e,
                commentComplain);
        }
        
        return 1;
    }
    
    // 查询所有订单（微信公众号
    
    @Override
    public List<Map<String, Object>> findDataForWeixinByParams(
        Map<String, Object> map)
        throws Exception {
        return this.orderManageDao.findDataForWeixinByParams(map);
    }
    
    /**
     * 〈一句话功能简述〉微信查看详情
     * 〈功能详细描述〉
     * 
     * @param id id
     * @param tradeObjCode tradeObjCode
     * @return SrcGoods
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @Override
    public Object findByIdForWeixin(String id, String tradeObjCode)
        throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        SrcGoods order = this.orderManageDao.findByIdForWeixin(id);
        // 查询订单相关的评价、投诉信息
        List<CommentComplain> comcom =
            this.orderManageDao.findCommentComplainByOrderId(id, tradeObjCode);
        
        map.put("order", order);
        map.put("comcom", comcom);
        
        return map;
    }
    
    @Override
    public List<SrcGoods> findOrderNo(Map<String, Object> map)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, map);
        map.put("remove", "0");
        List<SrcGoods> result = this.orderManageDao.findOrderNo(map);
        return result;
    }
    
    @Override
    public List<SrcGoods> findAccurateOrderNo(Map<String, Object> map)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, map);
        map.put("remove", "0");
        List<SrcGoods> result = this.orderManageDao.findAccurateOrderNo(map);
        return result;
    }
    
    @Override
    public List<String> hgConfirmOrderStatus(Map<String, Object> map)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, map);
        map.put("remove", "0");
        List<String> result = this.orderManageDao.hgConfirmOrderStatus(map);
        return result;
    }
}
