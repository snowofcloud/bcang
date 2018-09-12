/**
 * 文件名：TerminalServiceImpl.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-7-28
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.zcpt.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.c503.sc.base.exception.CustomException;
import com.c503.sc.jxwl.common.constant.CommonConstant;
import com.c503.sc.jxwl.zcpt.bean.LimitArea;
import com.c503.sc.jxwl.zcpt.constant.BizExConstants;
import com.c503.sc.jxwl.zcpt.dao.ILimitAreaDao;
import com.c503.sc.jxwl.zcpt.service.ILimitAreaService;
import com.c503.sc.log.LoggingManager;
import com.c503.sc.utils.common.SystemContants;

/**
 * 
 * 〈一句话功能简述〉限制区域业务实现类
 * 〈功能详细描述〉
 * 
 * @author zz
 * @version [版本号, 2016-7-26]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Service(value = "limitAreaService")
public class LimitAreaServiceImpl implements ILimitAreaService {
    
    /** 日志器 */
    private static final LoggingManager LOGGER =
        LoggingManager.getLogger(LimitAreaServiceImpl.class);
    
    /** 获取区域限制dao */
    @Autowired
    private ILimitAreaDao limitAreaDao;
    
    @Override
    public List<LimitArea> findAllAreaLimit()
        throws Exception {
        List<LimitArea> limitAreas = null;
        try {
            limitAreas = this.limitAreaDao.findAllAreaLimit();
            LOGGER.info(CommonConstant.FIND_SUC_OPTION, "查询区域限制成功!");
        }
        catch (Exception e) {
            throw new CustomException(CommonConstant.SYS_EXCEPTION, e);
        }
        
        return limitAreas;
    }
    
    @Override
    public LimitArea findByLimitAreaId(String id)
        throws Exception {
        LimitArea limitArea = null;
        try {
            limitArea = this.limitAreaDao.findByLimitAreaId(id);
        }
        catch (Exception e) {
            throw new CustomException(CommonConstant.SYS_EXCEPTION, e);
        }
        
        return limitArea;
    }
    
    @Override
    public int update(LimitArea limitArea)
        throws Exception {
        int count = 0;
        try {
            count = this.limitAreaDao.update(limitArea);
        }
        catch (Exception e) {
            throw new CustomException(CommonConstant.SYS_EXCEPTION, e);
        }
        return count;
    }
    
    @Override
    public int save(LimitArea limitArea)
        throws Exception {
        int count = 0;
        try {
            count = this.limitAreaDao.save(limitArea);
        }
        catch (Exception e) {
            throw new CustomException(CommonConstant.SYS_EXCEPTION, e);
        }
        return count;
    }
    
    @Override
    public int delLimitArea(String id)
        throws Exception {
        int count = 0;
        try {
            count = this.limitAreaDao.delete(id);
        }
        catch (Exception e) {
            throw new CustomException(CommonConstant.SYS_EXCEPTION, e);
        }
        return count;
    }
    
    
    @Override
    public List<String> findAllWaybillIdByTransport()
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        List<String> list = null;
        try {
            list = this.limitAreaDao.findAllWaybillIdByTransport();
            LOGGER.info(CommonConstant.FIND_SUC_OPTION, list);
        }
        catch (Exception e) {
            e.printStackTrace();
            CustomException ce =
                new CustomException(BizExConstants.TERMINAL_SEARCH_EXP, list);
            throw ce;
        }
        LOGGER.debug(SystemContants.DEBUG_END, list);
        return list;
    }
}
