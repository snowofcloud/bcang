/**
 * 文件名：EmergentLinkmanServiceImpl.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-8-5
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.vehiclemonitor.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.c503.sc.base.exception.CustomException;
import com.c503.sc.jxwl.common.constant.CommonConstant;
import com.c503.sc.jxwl.vehiclemonitor.bean.EmergentLinkmanEntity;
import com.c503.sc.jxwl.vehiclemonitor.dao.IEmergentLinkmanDao;
import com.c503.sc.jxwl.vehiclemonitor.service.IEmergentLinkmanService;
import com.c503.sc.log.LoggingManager;
import com.c503.sc.utils.basetools.C503StringUtils;
import com.c503.sc.utils.common.SystemContants;

/**
 * 〈一句话功能简述〉紧急联系人业务实现层
 * 〈功能详细描述〉
 * 
 * @author xiaoqx
 * @version [版本号, 2016-8-5]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Service(value = "emergentLinkmanService")
public class EmergentLinkmanServiceImpl implements IEmergentLinkmanService {
    /** 日志器 */
    private static final LoggingManager LOGGER =
        LoggingManager.getLogger(EmergencyInfoServiceImpl.class);
    
    /** 紧急联系人接口 */
    @Resource(name = "emergentLinkmanDao")
    private IEmergentLinkmanDao emergentLinkmanDao;
    
    @Override
    public List<EmergentLinkmanEntity> findByParams(Map<String, Object> map)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, map);
        List<EmergentLinkmanEntity> result = null;
        map.put("remove", SystemContants.ON);
        try {
            result = this.emergentLinkmanDao.findByParams(map);
        }
        catch (Exception e) {
            throw new CustomException(CommonConstant.SYS_EXCEPTION, e, map);
        }
        LOGGER.debug(SystemContants.DEBUG_END, map);
        return result;
    }
    
    @Override
    public Object save(EmergentLinkmanEntity entity)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, entity);
        try {
            entity.setId(C503StringUtils.createUUID());
            this.emergentLinkmanDao.save(entity);
        }
        catch (Exception e) {
            throw new CustomException(CommonConstant.SYS_EXCEPTION, e);
        }
        LOGGER.debug(SystemContants.DEBUG_END, entity);
        
        return entity.getId();
    }
    
    @Override
    public EmergentLinkmanEntity findById(String id)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, id);
        EmergentLinkmanEntity result = null;
        try {
            List<EmergentLinkmanEntity> entity =
                this.emergentLinkmanDao.findById(id);
            if (entity.size() > 0) {
                result = entity.get(0);
            }
        }
        catch (Exception e) {
            throw new CustomException(CommonConstant.SYS_EXCEPTION, e, id);
        }
        LOGGER.debug(SystemContants.DEBUG_END, id);
        return result;
    }
    
    @Override
    public int update(EmergentLinkmanEntity emergentLinkman)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, emergentLinkman);
        int upLine = 0;
        
        try {
            upLine = this.emergentLinkmanDao.update(emergentLinkman);
        }
        catch (Exception e) {
            throw new CustomException(CommonConstant.SYS_EXCEPTION, e,
                emergentLinkman);
        }
        LOGGER.debug(SystemContants.DEBUG_END, emergentLinkman);
        
        return upLine;
    }
    
    @Override
    public Object delete(EmergentLinkmanEntity entity)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, entity);
        int result = 0;
        try {
            result = this.emergentLinkmanDao.delete(entity);
        }
        catch (Exception e) {
            throw new CustomException(CommonConstant.SYS_EXCEPTION, e, entity);
        }
        LOGGER.debug(SystemContants.DEBUG_END, entity);
        return result;
    }
}
