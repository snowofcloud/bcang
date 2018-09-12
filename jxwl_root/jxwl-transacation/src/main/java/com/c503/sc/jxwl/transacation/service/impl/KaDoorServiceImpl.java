/**
 * 文件名：FullScreenServiceImp.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-8-31
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.transacation.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.c503.sc.jxwl.transacation.bean.KaDoor;
import com.c503.sc.jxwl.transacation.dao.IKaDoorDao;
import com.c503.sc.jxwl.transacation.service.IKaDoorService;
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
@Service(value = "kaDoorService")
public class KaDoorServiceImpl implements IKaDoorService {
    /** 日志器 */
    private static final LoggingManager LOGGER =
        LoggingManager.getLogger(KaDoorServiceImpl.class);
    
    /** 园区区域dao */
    @Resource(name = "kaDoorDao")
    private IKaDoorDao kaDoorDao;
    
    @Override
    public List<KaDoor> findAllKaDoor()
        throws Exception {
        List<KaDoor> kaDoors = this.kaDoorDao.findAllKaDoor();
        LOGGER.debug(SystemContants.DEBUG_END, kaDoors);
        return kaDoors;
    }
}
