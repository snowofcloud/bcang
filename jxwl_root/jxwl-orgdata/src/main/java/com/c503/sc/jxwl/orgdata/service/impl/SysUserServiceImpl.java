package com.c503.sc.jxwl.orgdata.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.c503.sc.jxwl.orgdata.dao.SysUserDao;
import com.c503.sc.jxwl.orgdata.service.ISysUserService;
import com.c503.sc.utils.common.SystemContants;

/**
 * 〈一句话功能简述〉SysUserServiceImpl
 * 〈功能详细描述〉
 * 
 * @author
 * @version [版本号, 2016-12-29]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Service(value = "sysUserService")
public class SysUserServiceImpl implements ISysUserService {
    /** sysUserDao */
    @Resource(name = "sysUserDao")
    private SysUserDao sysUserDao;
    
    @Override
    public int saveSysUser(Map<String, Object> map) {
        map.put("remove", SystemContants.ON);
        return this.sysUserDao.saveSysUser(map);
    }
    
    @Override
    public int updateInfo(Map<String, Object> map) {
        return this.sysUserDao.updateInfo(map);
    }
    
}
