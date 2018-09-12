/**
 * 文件名：OccupationPersonServiceImpl.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-7-22
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.vehiclemonitor.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.c503.sc.base.exception.CustomException;
import com.c503.sc.jxwl.common.constant.CommonConstant;
import com.c503.sc.jxwl.vehiclemonitor.bean.OccupationPersonEntity;
import com.c503.sc.jxwl.vehiclemonitor.dao.IOccupationPersonDao;
import com.c503.sc.jxwl.vehiclemonitor.service.IOccupationPersonService;
import com.c503.sc.log.LoggingManager;
import com.c503.sc.utils.basetools.C503StringUtils;
import com.c503.sc.utils.common.SystemContants;


/**
 * 
 * 〈一句话功能简述〉从业人员信息业务层
 * 〈功能详细描述〉
 * 
 * @author guqh
 * @version [版本号, 2016-7-22]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Service(value = "occupationPersonService")
public class OccupationPersonServiceImpl implements IOccupationPersonService {
    /** 日志器 */
    private static final LoggingManager LOGGER =
        LoggingManager.getLogger(EnterpriseServiceImpl.class);
    
    /** 从业人员信息Dao */
    @Resource(name = "occupationPersonDao")
    private IOccupationPersonDao occupationPersonDao;
    
    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public List<OccupationPersonEntity> findByParams(Map<String, Object> map)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, map);
        List<OccupationPersonEntity> list = null;
        try {
            map.put("remove", SystemContants.ON);
            list = this.occupationPersonDao.findByParams(map);
        }
        catch (Exception e) {
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
    public Object save(OccupationPersonEntity entity)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, entity);
        
     // 判断身份证好号码是否存在
//        if (StringUtils.isNotEmpty(this.occupationPersonDao.findIdCardNoExist(entity.getIdentificationCardNo(), null))) {
//            throw new CustomException(BizExConstants.OCCUPATIONPERSON_IDCARDNOEXIST);
//        }
        
        entity.setId(C503StringUtils.createUUID());
        entity.setRemove(SystemContants.ON);
        Date curDate = new Date();
        entity.setCreateTime(curDate);
        entity.setUpdateTime(curDate);
        
        try {
            this.occupationPersonDao.save(entity);
        }
        catch (Exception e) {
            throw new CustomException(CommonConstant.SYS_EXCEPTION, e, entity);
        }
        LOGGER.debug(SystemContants.DEBUG_END, entity);
        
        return entity.getId();
    }
    
    /**
     * {@inheritDoc}
     * 
     */
    @Override
    @Transactional(rollbackFor = java.lang.Exception.class)
    public Object update(OccupationPersonEntity entity)
        throws Exception {
        
     // 判断法人代码是否存在
//        if (StringUtils.isNotEmpty(this.occupationPersonDao.findIdCardNoExist(entity.getIdentificationCardNo(), entity.getId()))) {
//            throw new CustomException(BizExConstants.ENTERPRISE_CONRPORATREXIST);
//        }
        
        try {
            this.occupationPersonDao.update(entity);
        }
        catch (Exception e) {
            throw new CustomException(CommonConstant.SYS_EXCEPTION, e);
        }
        return entity;
    }
    
    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public OccupationPersonEntity findById(String id)
        throws Exception {
        OccupationPersonEntity entity = null;
        
        try {
            entity = this.occupationPersonDao.findById(id);
        }
        catch (Exception e) {
            throw new CustomException(CommonConstant.SYS_EXCEPTION, e, id);
        }
        return entity;
    }
    
    @Override
    public int delete(Map<String, Object> map)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, map);
        int delLine = 0;
        map.put("remove", SystemContants.OFF);
        
        try {
            delLine = this.occupationPersonDao.delete(map);
            this.occupationPersonDao.deleteAccount(map);
        }
        catch (Exception e) {
            throw new CustomException(CommonConstant.SYS_EXCEPTION, e, map);
        }
        LOGGER.debug(SystemContants.DEBUG_END, map);
        
        return delLine;
    }
}
