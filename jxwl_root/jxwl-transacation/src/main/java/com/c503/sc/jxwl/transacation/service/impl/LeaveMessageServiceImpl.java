/**
 * 文件名：LeaveMessageServiceImpl.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-8-30
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.transacation.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.c503.sc.base.exception.CustomException;
import com.c503.sc.jxwl.common.constant.CommonConstant;
import com.c503.sc.jxwl.transacation.bean.LeaveMessageEntity;
import com.c503.sc.jxwl.transacation.dao.ILeaveMessageDao;
import com.c503.sc.jxwl.transacation.service.ILeaveMessageService;
import com.c503.sc.log.LoggingManager;
import com.c503.sc.utils.basetools.C503StringUtils;
import com.c503.sc.utils.common.SystemContants;

/**
 * 
 * 〈一句话功能简述〉留言信息业务层 〈功能详细描述〉
 * 
 * @author xinzhiwei
 * @version [版本号, 2016-8-30]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Service(value = "leaveMessageService")
public class LeaveMessageServiceImpl implements ILeaveMessageService {
    /** 日志器 */
    private static final LoggingManager LOGGER =
        LoggingManager.getLogger(LeaveMessageServiceImpl.class);
    
    /** 留言信息Dao */
    @Resource(name = "leaveMessageDao")
    private ILeaveMessageDao leaveMessageDao;
    
    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public List<LeaveMessageEntity> findByParams(Map<String, Object> map)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, map);
        List<LeaveMessageEntity> list = null;
        try {
            map.put("remove", SystemContants.ON);
            list = this.leaveMessageDao.findByParams(map);
        } catch (Exception e) {
            throw new CustomException(CommonConstant.SYS_EXCEPTION, e);
        }
        LOGGER.debug(SystemContants.DEBUG_END, map);
        return list;
    }
    
    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public boolean isLeaveMessageDeleted(String id)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, id);
        String remove = null;
        try {
            remove = this.leaveMessageDao.findLeaveMessageRemove(id);
        } catch (Exception e) {
            throw new CustomException(CommonConstant.SYS_EXCEPTION, e, id);
        }
        LOGGER.debug(SystemContants.DEBUG_END, id);
        
        return "1".equals(remove);
    }
    
    /**
     * {@inheritDoc}
     * 
     */
    @Override
    @Transactional(rollbackFor = java.lang.Exception.class)
    public int delete(Map<String, Object> map)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, map);
        int delLine = 0;
        map.put("remove", SystemContants.OFF);
        
        try {
            delLine = this.leaveMessageDao.delete(map);
        } catch (Exception e) {
            throw new CustomException(CommonConstant.SYS_EXCEPTION, e, map);
        }
        LOGGER.debug(SystemContants.DEBUG_END, map);
        
        return delLine;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Object save(LeaveMessageEntity entity)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, entity);
        entity.setId(C503StringUtils.createUUID());
        try {
            this.leaveMessageDao.save(entity);
        } catch (Exception e) {
            throw new CustomException(CommonConstant.SYS_EXCEPTION, e);
        }
        LOGGER.debug(SystemContants.DEBUG_END, entity);
        
        return entity.getId();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(rollbackFor = java.lang.Exception.class)
    public Object update(LeaveMessageEntity entity)
        throws Exception {
        // 判断留言是否存在
        /*
         * if
         * (StringUtils.isNotEmpty(this.leaveMessageDao.findLicencePlateNoExit
         * (entity.getLicencePlateNo(), entity.getId()))){ throw new
         * CustomException(BizExConstants.VEHICLE_LICENCEPLATENOEXIST); }
         */
        
        try {
            this.leaveMessageDao.update(entity);
            
        } catch (Exception e) {
            throw new CustomException(CommonConstant.SYS_EXCEPTION, e);
        }
        return entity;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public LeaveMessageEntity findById(String id, String hgCorporateNo,
        String wlCorporateNo)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, id);
        
        LeaveMessageEntity entity = null;
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("id", id);
            map.put("hgCorporateNo", hgCorporateNo);
            map.put("wlCorporateNo", wlCorporateNo);
            entity = this.leaveMessageDao.findById(map);
        } catch (Exception e) {
            throw new CustomException(CommonConstant.SYS_EXCEPTION, e, id);
        }
        LOGGER.debug(SystemContants.DEBUG_END, id);
        return entity;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String findChemicalEnterpriseByGoodNo(String goodsNo)
        throws Exception {
        String enterprise;
        try {
            enterprise =
                this.leaveMessageDao.findChemicalEnterpriseByGoodNo(goodsNo);
        } catch (Exception e) {
            throw new CustomException(CommonConstant.SYS_EXCEPTION, e, goodsNo);
        }
        return enterprise;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean checkGoodsStatus(String goodsNo)
        throws Exception {
        String result = "";
        try {
            result = this.leaveMessageDao.checkGoodsStatus(goodsNo);
        } catch (Exception e) {
            throw new CustomException(CommonConstant.SYS_EXCEPTION, e, goodsNo);
        }
        return "110001002".equals(result);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean findLeaveMessageExist(String logisticsEnterprise,
        String goodsNo)
        throws Exception {
        int result = 0;
        try {
            result =
                this.leaveMessageDao.findLeaveMessageExist(logisticsEnterprise,
                    goodsNo);
        } catch (Exception e) {
            /*
             * Map<String, Object> map = new HashMap<>();
             * map.put("logisticsEnterprise", logisticsEnterprise);
             * map.put("goodsNo", goodsNo);
             */
            throw new CustomException(CommonConstant.SYS_EXCEPTION, e, goodsNo);
        }
        return result > 0;
    }
}
