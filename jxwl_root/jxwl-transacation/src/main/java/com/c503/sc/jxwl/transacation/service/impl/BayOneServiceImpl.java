package com.c503.sc.jxwl.transacation.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.c503.sc.jxwl.transacation.dao.IBayonetDao;
import com.c503.sc.jxwl.transacation.service.IBayOnetService;
import com.c503.sc.jxwl.transacation.vo.BayOne;
import com.c503.sc.log.LoggingManager;
import com.c503.sc.utils.basetools.C503StringUtils;
import com.c503.sc.utils.common.SystemContants;

/**
 * 〈一句话功能简述〉卡口审核
 * 〈功能详细描述〉
 * 
 * @author zhongz
 * @version [版本号, 2016-12-28]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Service(value = "bayOnetService")
public class BayOneServiceImpl implements IBayOnetService {
    
    /** 日志器 */
    private static final LoggingManager LOGGER =
        LoggingManager.getLogger(WayBillServiceImpl.class);
    
    /** 进出口管理Dao */
    @Resource(name = "bayonetdao")
    private IBayonetDao bayonetdao;
    
    @Override
    public List<BayOne> findByParams(Map<String, Object> map)
        throws Exception {
        
        LOGGER.debug(SystemContants.DEBUG_START, map);
        map.put("remove", SystemContants.ON);
        List<BayOne> list = this.bayonetdao.findByParams(map);
        LOGGER.debug(SystemContants.DEBUG_END, list);
        return list;
    }
    
    @Override
    public void save(Map<String, Object> map)
        throws Exception {
        
        LOGGER.debug(SystemContants.DEBUG_START, map);
        map.put("remove", SystemContants.ON);
        map.put("updateTime", new Date());
        map.put("createTime", new Date());
        map.put("verifytime", new Date());
        map.put("id", C503StringUtils.createUUID());
        this.bayonetdao.save(map);
        LOGGER.debug(SystemContants.DEBUG_END, map);
    }
    
    @Override
    public BayOne findByCarName(Map<String, Object> map)
        throws Exception {
        
        LOGGER.debug(SystemContants.DEBUG_START, map);
        BayOne bayOne = this.bayonetdao.findByCarName(map);
        LOGGER.debug(SystemContants.DEBUG_END, map);
        return bayOne;
    }
    
    @Override
    public  List<BayOne> findByOrders(Map<String, Object> map)
        throws Exception {
        
        LOGGER.debug(SystemContants.DEBUG_START, map);
        map.put("remove", SystemContants.ON);
        List<BayOne> list = this.bayonetdao.findByOrders(map);
        LOGGER.debug(SystemContants.DEBUG_END, list);
        return list;
    }
    
    @Override
    public  BayOne findRecordById(Map<String, Object> map)
        throws Exception {
        
        LOGGER.debug(SystemContants.DEBUG_START, map);
        map.put("remove", SystemContants.ON);
        BayOne list = this.bayonetdao.findRecordById(map);
        LOGGER.debug(SystemContants.DEBUG_END, list);
        return list;
    }
    
}
