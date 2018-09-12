/**
 * 文件名：EnterpriseServiceImpl.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-7-20
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.transacation.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.c503.sc.base.exception.CustomException;
import com.c503.sc.jxwl.common.constant.CommonConstant;
import com.c503.sc.jxwl.transacation.bean.WayBillGoodsEntity;
import com.c503.sc.jxwl.transacation.dao.IWayBillGoodsDao;
import com.c503.sc.jxwl.transacation.service.IWayBillGoodsService;
import com.c503.sc.jxwl.zcpt.dao.ISolveRouteDao;
import com.c503.sc.log.LoggingManager;
import com.c503.sc.utils.common.SystemContants;

/**
 * 
 * 〈一句话功能简述〉物流企业信息业务层
 * 〈功能详细描述〉
 * 
 * @author wl
 * @version [版本号, 2016-7-20]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Service(value = "wayBillGoodsService")
public class WayBillGoodsServiceImpl implements IWayBillGoodsService {
    /** 日志器 */
    private static final LoggingManager LOGGER =
        LoggingManager.getLogger(WayBillGoodsServiceImpl.class);
    
    /** 运单--货物Dao */
    @Resource(name = "wayBillGoodsDao")
    private IWayBillGoodsDao wayBillGoodsDao;
    
    /** 路径规划dao */
    @SuppressWarnings("unused")
    @Resource(name = "solveRouteDao")
    private ISolveRouteDao solveRouteDao;
    
    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public List<WayBillGoodsEntity> findByParams(Map<String, Object> map)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, map);
        List<WayBillGoodsEntity> list = null;
        map.put("remove", SystemContants.ON);
        try {
            list = this.wayBillGoodsDao.findByParams(map);
        } catch (Exception e) {
            throw new CustomException(CommonConstant.SYS_EXCEPTION, e, map);
        }
        LOGGER.debug(SystemContants.DEBUG_END, map);
        return list;
    }
    
    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public WayBillGoodsEntity save(WayBillGoodsEntity entity)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, entity);
        try {
            // 保存业务数据库
            this.wayBillGoodsDao.save(entity);
        } catch (Exception e) {
            throw new CustomException(CommonConstant.SYS_EXCEPTION, e, entity);
        }
        LOGGER.debug(SystemContants.DEBUG_END, entity);
        
        return entity;
    }
    
    @Override
    public int delete(Map<String, Object> map)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, map);
        int delLine = 0;
        map.put("remove", SystemContants.OFF);
        delLine = this.wayBillGoodsDao.delete(map);
        // sysOrgTypeService.delete(map);
        LOGGER.debug(SystemContants.DEBUG_END, map);
        return delLine;
    }
    
    @Override
    public WayBillGoodsEntity findById(String id)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, id);
        WayBillGoodsEntity goods = null;
        try {
            goods = this.wayBillGoodsDao.findById(id);
            LOGGER.info(CommonConstant.FIND_SUC_OPTION, goods);
        } catch (Exception e) {
            throw new CustomException(CommonConstant.SYS_EXCEPTION, e);
        }
        LOGGER.debug(SystemContants.DEBUG_END, id);
        
        return goods;
    }
    
    @Override
    public int update(WayBillGoodsEntity goods)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, goods);
        int upLine = 0;
        
        try {
            upLine = this.wayBillGoodsDao.update(goods);
        } catch (Exception e) {
            throw new CustomException(CommonConstant.SYS_EXCEPTION, e, goods);
        }
        LOGGER.debug(SystemContants.DEBUG_END, goods);
        
        return upLine;
    }
    
}
