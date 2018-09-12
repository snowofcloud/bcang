/**
 * 文件名：LogManageServiceImpl.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2017-4-5
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.transacation.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.c503.sc.base.exception.CustomException;
import com.c503.sc.jxwl.common.bean.LogRemind;
import com.c503.sc.jxwl.common.constant.CommonConstant;
import com.c503.sc.jxwl.common.websocket.MonitorWebsocket;
import com.c503.sc.jxwl.transacation.bean.LogManage;
import com.c503.sc.jxwl.transacation.dao.ILogManageDao;
import com.c503.sc.jxwl.transacation.service.ILogManageService;
import com.c503.sc.log.LoggingManager;
import com.c503.sc.utils.basetools.C503StringUtils;
import com.c503.sc.utils.common.SystemContants;

/**
 * 
 * 〈一句话功能简述〉订单日志
 * 〈功能详细描述〉
 * 
 * @author guqh
 * @version [版本号, 2017-4-5]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Service(value = "logManageService")
public class LogManageServiceImpl implements ILogManageService {
    /** 日志器 */
    private static final LoggingManager LOGGER =
        LoggingManager.getLogger(LogManageServiceImpl.class);
    
    /** 订单管理接口 */
    @Resource(name = "logManageDao")
    private ILogManageDao logManageDao;
    
    @Override
    public int saveLog(String orderId, String corporateNo, String tradeStatus,
        String content)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, orderId);
        LogManage logManage = new LogManage();
        logManage.setId(C503StringUtils.createUUID());
        logManage.setOrderId(orderId);
        logManage.setCorporateNo(corporateNo);
        logManage.setContent(content);
        logManage.setTradeStatus(tradeStatus);
        logManage.setRecordTime(new Date());
        logManage.setRemindLogistics(SystemContants.ON);
        logManage.setRemindChemical(SystemContants.ON);
        int num = this.logManageDao.saveLog(logManage);
        LOGGER.debug(SystemContants.DEBUG_END, orderId);
        // 通知客户端
        List<LogRemind> remindList = new ArrayList<LogRemind>();
        LogRemind remind = null;
        remind = new LogRemind();
        this.copyProperties(logManage, remind);
        remindList.add(remind);
        // 通知浏览器
        MonitorWebsocket.notifyLogRemind(remindList);
        
        return num;
    }
    
    @Override
    public List<LogManage> findByParams(Map<String, Object> map)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, map);
        List<LogManage> result = null;
        try {
            result = this.logManageDao.findByParams(map);
        }
        catch (Exception e) {
            throw new CustomException(CommonConstant.SYS_EXCEPTION, e, map);
        }
        LOGGER.debug(SystemContants.DEBUG_END, map);
        return result;
    }
    
    private void copyProperties(LogManage entity, LogRemind remind) {
        remind.setId(entity.getId());
        remind.setOrderId(entity.getOrderId());
        remind.setCorporateNo(entity.getCorporateNo());
        remind.setContent(entity.getContent());
        remind.setTradeStatus(entity.getTradeStatus());
        remind.setRecordTime(entity.getRecordTime());
        remind.setRemindLogistics(entity.getRemindLogistics());
        remind.setRemindChemical(entity.getRemindChemical());
    }
    
    @Override
    public LogManage findById(String id)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, id);
        LogManage entity = null;
        try {
            entity = this.logManageDao.findById(id);
            LOGGER.info(CommonConstant.FIND_SUC_OPTION, entity);
        }
        catch (Exception e) {
            throw new CustomException(CommonConstant.SYS_EXCEPTION, e);
        }
        LOGGER.debug(SystemContants.DEBUG_END, id);
        
        return entity;
    }
    
    @Override
    public int updateBatch(List<LogManage> list)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, list);
        int num = 0;
        try {
            num = this.logManageDao.updateBatch(list);
        }
        catch (Exception e) {
            throw new CustomException(CommonConstant.SYS_EXCEPTION, e, list);
        }
        LOGGER.debug(SystemContants.DEBUG_END, list);
        return num;
    }
    
}
