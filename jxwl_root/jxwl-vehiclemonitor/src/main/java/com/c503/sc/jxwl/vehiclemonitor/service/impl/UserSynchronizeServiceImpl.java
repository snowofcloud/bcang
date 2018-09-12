/**
 * 文件名：UserSynchronizeServiceImpl.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2017-5-17
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.vehiclemonitor.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.c503.sc.jxwl.vehiclemonitor.bean.UserSynchronizeEntity;
import com.c503.sc.jxwl.vehiclemonitor.dao.IUserSynchronizeDao;
import com.c503.sc.jxwl.vehiclemonitor.service.IUserSynchronizeService;
import com.c503.sc.log.LoggingManager;
import com.c503.sc.utils.common.SystemContants;

/**
 * 
 * 〈一句话功能简述〉用户同步
 * 〈功能详细描述〉
 * 
 * @author guqh
 * @version [版本号, 2017-5-17]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Service(value = "userSynchronizeService")
public class UserSynchronizeServiceImpl implements IUserSynchronizeService {
    
    /** 日志器 */
    private static final LoggingManager LOGGER =
        LoggingManager.getLogger(UserSynchronizeServiceImpl.class);
    
    /** 企业数据接口 */
    @Resource(name = "userSynchronizeDao")
    private IUserSynchronizeDao userSynchronizeDao;
    
    @Override
    public int batchSynchronize(List<UserSynchronizeEntity> entity)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, entity);
        int i = this.userSynchronizeDao.batchSave(entity);
        LOGGER.debug(SystemContants.DEBUG_END, entity);
        return i;
    }
    
    @Override
    public List<UserSynchronizeEntity> findNewUserByParams(Map<String, Object> map)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, map);
        return this.userSynchronizeDao.findNewUserByParams(map);
    }
}
