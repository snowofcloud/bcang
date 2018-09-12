/**
 * 文件名：FullScreenServiceImp.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-8-31
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.transacation.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.c503.sc.jxwl.transacation.bean.ParkArea;
import com.c503.sc.jxwl.transacation.dao.IParkAreaDao;
import com.c503.sc.jxwl.transacation.service.IParkAreaService;
import com.c503.sc.log.LoggingManager;
import com.c503.sc.utils.common.SystemContants;

/**
 * 〈一句话功能简述〉园区区域service
 * 〈功能详细描述〉
 * 
 * @author wangwenhuan
 * @version [版本号, 2016-11-21]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Service(value = "parkAreaService")
public class ParkAreaServiceImpl implements IParkAreaService {
    /** 日志器 */
    private static final LoggingManager LOGGER =
        LoggingManager.getLogger(ParkAreaServiceImpl.class);
    
    /** 园区区域dao */
    @Resource(name = "parkAreaDao")
    private IParkAreaDao parkAreaDao;
    
    @Override
    public ParkArea findParkArea()
        throws Exception {
        ParkArea park = this.parkAreaDao.findParkArea();
        LOGGER.debug(SystemContants.DEBUG_END, park);
        return park;
    }
}
