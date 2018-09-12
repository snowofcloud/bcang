/**
 * 文件名：CarDispatchServiceImpl.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2017-10-23
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.zcpt.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.c503.sc.base.exception.CustomException;
import com.c503.sc.jxwl.common.constant.CommonConstant;
import com.c503.sc.jxwl.zcpt.dao.IShortcutDao;
import com.c503.sc.jxwl.zcpt.service.ICarDispatchService;
import com.c503.sc.log.LoggingManager;
import com.c503.sc.utils.common.SystemContants;

/**
 * 〈一句话功能简述〉车辆调度业务实现类
 * 〈功能详细描述〉
 * 
 * @author duanhy
 * @version [版本号, 2017-10-23]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Service(value = "carDispatchService")
public class CarDispatchServiceImpl implements ICarDispatchService {
    
    /** 日志器 */
    private static final LoggingManager LOGGER =
        LoggingManager.getLogger(CarDispatchServiceImpl.class);
    
    /** 车辆调度数据接口 */
    @Resource(name = "shortcutDao")
    private IShortcutDao shortcutDao;
    
    @Override
    public String findRegidByCarrierName(String carrierName)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, carrierName);
        String regId = null;
        try {
            regId = this.shortcutDao.findRegidByCarrierName(carrierName);
            LOGGER.info(CommonConstant.FIND_SUC_OPTION, regId);
        }
        catch (Exception e) {
            e.printStackTrace();
            CustomException ce =
                new CustomException(CommonConstant.SYS_EXCEPTION, carrierName);
            throw ce;
        }
        LOGGER.debug(SystemContants.DEBUG_END, regId);
        return regId;
    }
    
    @Override
    public List<Map<String, Object>> findCarDispVal(String carrierName,
        String userName)
        throws Exception {
        return this.shortcutDao.findCarDispVal(carrierName, userName);
    }
    
    @Override
    public void sendTextMsg(Map<String, Object> map)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, map);
        // 保存发送的文本信息到本地数据库
        this.shortcutDao.saveCarDispatch(map);
        // 保存发送的文本下发数据
        LOGGER.debug(SystemContants.DEBUG_END, map);
    }
}
