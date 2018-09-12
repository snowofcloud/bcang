/*
 * 文件名：ImageShowServiceImpl.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-8-31
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.transacation.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.c503.sc.base.exception.CustomException;
import com.c503.sc.jxwl.common.constant.CommonConstant;
import com.c503.sc.jxwl.transacation.dao.IImageShowDao;
import com.c503.sc.jxwl.transacation.service.IImageShowService;
import com.c503.sc.jxwl.vehiclemonitor.bean.EnterpriseEntity;
import com.c503.sc.log.LoggingManager;
import com.c503.sc.utils.common.SystemContants;

/**
 * 〈一句话功能简述〉企业形象展示实现层
 * 〈功能详细描述〉
 * 
 * @author xiaoqx
 * @version [版本号, 2016-9-8]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Service(value = "imageShowService")
public class ImageShowServiceImpl implements IImageShowService {
    /** 日志器 */
    private static final LoggingManager LOGGER =
        LoggingManager.getLogger(ImageShowServiceImpl.class);
    
    /** 形象展示Dao */
    @Resource(name = "imageShowDao")
    private IImageShowDao imageShowDao;
    
    @Override
    public List<EnterpriseEntity> findByNameOrCode(String queryStr)
        throws CustomException {
    	LOGGER.debug(SystemContants.DEBUG_START, queryStr);
        List<EnterpriseEntity> result = null;
        try {
            result = this.imageShowDao.findByNameOrCode(queryStr);
        }
        catch (Exception e) {
            throw new CustomException(CommonConstant.SYS_EXCEPTION, e);
        }
        LOGGER.debug(SystemContants.DEBUG_END, queryStr);
        return result;
    }
    
    @Override
    public EnterpriseEntity findByCode(String code)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, code);
        EnterpriseEntity result = null;
        try {
            result = this.imageShowDao.findByCode(code);
        }
        catch (Exception e) {
            throw new CustomException(CommonConstant.SYS_EXCEPTION, e);
        }
        LOGGER.debug(SystemContants.DEBUG_END, code);
        return result;
    }

	@Override
	public EnterpriseEntity findByGOVER() 
			throws Exception{
		LOGGER.debug(SystemContants.DEBUG_START);
        EnterpriseEntity result = null;
        try {
            String code = this.imageShowDao.findByGOVER();
            result = findByCode(code);
        }
        catch (Exception e) {
            throw new CustomException(CommonConstant.SYS_EXCEPTION, e);
        }
        LOGGER.debug(SystemContants.DEBUG_END);
        return result;
	}
    
}
