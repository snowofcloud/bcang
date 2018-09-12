/**
 * 文件名：RuleRegulationServiceImpl.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-8-4
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.vehiclemonitor.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.c503.sc.base.exception.CustomException;
import com.c503.sc.jxwl.common.constant.CommonConstant;
import com.c503.sc.jxwl.vehiclemonitor.bean.RuleRegulationEntity;
import com.c503.sc.jxwl.vehiclemonitor.dao.IRuleRegulationDao;
import com.c503.sc.jxwl.vehiclemonitor.service.IRuleRegulationService;
import com.c503.sc.log.LoggingManager;
import com.c503.sc.utils.basetools.C503StringUtils;
import com.c503.sc.utils.common.SystemContants;

/**
 * 〈一句话功能简述〉规章制度业务实现
 * 〈功能详细描述〉
 * 
 * @author xiaoqx
 * @version [版本号, 2016-8-4]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Service(value = "ruleRegulationService")
public class RuleRegulationServiceImpl implements IRuleRegulationService {
    
    /** 日志器 */
    private static final LoggingManager LOGGER =
        LoggingManager.getLogger(EmergencyInfoServiceImpl.class);
    
    /** 规章制度接口 */
    @Resource(name = "ruleRegulationDao")
    private IRuleRegulationDao ruleRegulationDao;
    
    @Override
    public List<RuleRegulationEntity> findByParams(Map<String, Object> map)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, map);
        List<RuleRegulationEntity> result = null;
        map.put("remove", SystemContants.ON);
        try {
            result = this.ruleRegulationDao.findByParams(map);
        }
        catch (Exception e) {
            throw new CustomException(CommonConstant.SYS_EXCEPTION, e, map);
        }
        LOGGER.debug(SystemContants.DEBUG_END, map);
        return result;
    }
    
    @Override
    public Object save(RuleRegulationEntity entity)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, entity);
        try {
            entity.setId(C503StringUtils.createUUID());
            this.ruleRegulationDao.save(entity);
        }
        catch (Exception e) {
            throw new CustomException(CommonConstant.SYS_EXCEPTION, e);
        }
        LOGGER.debug(SystemContants.DEBUG_END, entity);
        
        return entity.getId();
    }
    
    @Override
    public RuleRegulationEntity findById(String id)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, id);
        RuleRegulationEntity result = null;
        try {
            List<RuleRegulationEntity> entity =
                this.ruleRegulationDao.findById(id);
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
    public int update(RuleRegulationEntity ruleRegulation)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, ruleRegulation);
        int upLine = 0;
        
        try {
            upLine = this.ruleRegulationDao.update(ruleRegulation);
        }
        catch (Exception e) {
            throw new CustomException(CommonConstant.SYS_EXCEPTION, e,
                ruleRegulation);
        }
        LOGGER.debug(SystemContants.DEBUG_END, ruleRegulation);
        
        return upLine;
    }
    
    @Override
    public Object delete(RuleRegulationEntity entity)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, entity);
        int result = 0;
        try {
            result = this.ruleRegulationDao.delete(entity);
        }
        catch (Exception e) {
            throw new CustomException(CommonConstant.SYS_EXCEPTION, e, entity);
        }
        LOGGER.debug(SystemContants.DEBUG_END, entity);
        return result;
    }
    
}
