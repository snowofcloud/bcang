/**
 * 文件名：BlacklistManageServiceImpl.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-11-10
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.vehiclemonitor.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.c503.sc.jxwl.orgdata.bean.BizUserEntity;
import com.c503.sc.jxwl.orgdata.dao.SysUserDao;
import com.c503.sc.jxwl.vehiclemonitor.bean.AccountVerifyEntity;
import com.c503.sc.jxwl.vehiclemonitor.dao.IAccountVerifyDao;
import com.c503.sc.jxwl.vehiclemonitor.service.IAccountVerifyService;
import com.c503.sc.log.LoggingManager;
import com.c503.sc.utils.common.SystemContants;

/**
 * 
 * 〈一句话功能简述〉账号审核业务实现
 * 〈功能详细描述〉
 * 
 * @author wangwenhuan
 * @version [版本号, 2016-11-10]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Service(value = "accountVerifyService")
public class AccountVerifyServiceImpl implements IAccountVerifyService {
    
    /** 日志器 */
    private static final LoggingManager LOGGER =
        LoggingManager.getLogger(AccountVerifyServiceImpl.class);
    
    /** 账号审核Dao */
    @Resource(name = "accountVerifyDao")
    private IAccountVerifyDao accountVerifyDao;
    
    /** 账号审核Dao */
    @Resource(name = "sysUserDao")
    private SysUserDao sysUserDao;
    
    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public int register(AccountVerifyEntity entity) {
        return this.accountVerifyDao.register(entity);
    }
    
    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public List<AccountVerifyEntity> findOccupationPerson(
        Map<String, Object> map) {
        LOGGER.debug(SystemContants.DEBUG_START, map);
        map.put("remove", SystemContants.ON);
        LOGGER.debug(SystemContants.DEBUG_END, map);
        return this.accountVerifyDao.findOccupationPerson(map);
        
    }
    
    /**
     * 
     * 〈一句话功能简述〉 〈功能详细描述〉查找注册信息对象
     * 
     * @param map account
     * @return boolean
     * @see [类、类#方法、类#成员]
     */
    @Override
    public List<AccountVerifyEntity> findRegisterInfo(Map<String, Object> map) {
        LOGGER.debug(SystemContants.DEBUG_START, map);
        map.put("remove", SystemContants.ON);
        LOGGER.debug(SystemContants.DEBUG_END, map);
        return this.accountVerifyDao.findRegisterInfo(map);
    }
    
    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public List<AccountVerifyEntity> findByParams(Map<String, Object> map) {
        LOGGER.debug(SystemContants.DEBUG_START, map);
        map.put("remove", SystemContants.ON);
        LOGGER.debug(SystemContants.DEBUG_END, map);
        return this.accountVerifyDao.findByParams(map);
    }
    
    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public AccountVerifyEntity findByParameter(Map<String, Object> map) {
        LOGGER.debug(SystemContants.DEBUG_START, map);
        map.put("remove", SystemContants.ON);
        LOGGER.debug(SystemContants.DEBUG_END, map);
        return this.accountVerifyDao.findByParameter(map);
    }
    
    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public int verify(Map<String, Object> map) {
        LOGGER.debug(SystemContants.DEBUG_START, map);
        map.put("remove", SystemContants.ON);
        LOGGER.debug(SystemContants.DEBUG_END, map);
        return this.accountVerifyDao.verify(map);
    }
    
    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public int cancel(Map<String, Object> map) {
        LOGGER.debug(SystemContants.DEBUG_START, map);
        map.put("remove", SystemContants.ON);
        LOGGER.debug(SystemContants.DEBUG_END, map);
        return this.accountVerifyDao.cancel(map);
    }
    
    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public int update(Map<String, Object> map) {
        map.put("remove", SystemContants.ON);
        return this.accountVerifyDao.update(map);
    }
    
    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public int amount(Map<String, Object> map) {
        LOGGER.debug(SystemContants.DEBUG_START, map);
        map.put("remove", SystemContants.ON);
        LOGGER.debug(SystemContants.DEBUG_END, map);
        return this.accountVerifyDao.amount(map);
    }
    
    @Override
    public BizUserEntity findBizUser(Map<String, Object> map) {
        map.put("remove", SystemContants.ON);
        String account = (String) map.get("account");
        String sysId = (String) map.get("sysId");
        return this.sysUserDao.getUserByAccount(account, sysId);
    }
    
    @Override
    public boolean isExist(Map<String, Object> map) {
        map.put("remove", SystemContants.ON);
        Map<String, String> result = this.accountVerifyDao.isExist(map);
        if (result != null && result.get("NO") != null
            && result.get("ID") != null) {
            return true;
        }
        return false;
    }
    
}
