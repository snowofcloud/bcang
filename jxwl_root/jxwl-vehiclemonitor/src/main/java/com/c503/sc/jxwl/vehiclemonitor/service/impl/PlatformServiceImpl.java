/**
 * 文件名：PlatformServiceImpl.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-8-8
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.vehiclemonitor.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.c503.sc.base.exception.CustomException;
import com.c503.sc.jxwl.common.constant.CommonConstant;
import com.c503.sc.jxwl.vehiclemonitor.bean.PlatformEntity;
import com.c503.sc.jxwl.vehiclemonitor.dao.IplatformDao;
import com.c503.sc.jxwl.vehiclemonitor.service.IplatformService;
import com.c503.sc.log.LoggingManager;
import com.c503.sc.utils.common.SystemContants;

/**
 * 〈一句话功能简述〉平台信息业务实现层
 * 〈功能详细描述〉
 * 
 * @author xiaoqx
 * @version [版本号, 2016-8-8]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Service(value = "platformService")
public class PlatformServiceImpl implements IplatformService {
    /** 日志器 */
    private static final LoggingManager LOGGER =
        LoggingManager.getLogger(EmergencyInfoServiceImpl.class);
    
    /** 平台信息接口 */
    @Resource(name = "platformDao")
    private IplatformDao platformDao;
    
    @Override
    public List<PlatformEntity> findByParams(Map<String, Object> map)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, map);
        List<PlatformEntity> result = null;
        map.put("remove", SystemContants.ON);
        try {
            result = this.platformDao.findByParams(map);
        }
        catch (Exception e) {
            throw new CustomException(CommonConstant.SYS_EXCEPTION, e, map);
        }
        LOGGER.debug(SystemContants.DEBUG_END, map);
        return result;
    }
    
}
