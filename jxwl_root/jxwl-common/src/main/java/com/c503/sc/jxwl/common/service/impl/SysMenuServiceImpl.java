/*
 * 文件名：SysMenuServiceImpl.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2015年12月8日
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.common.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import sc.c503.authclient.service.AuthFactory;
import sc.c503.authclient.service.IAuthService;

import com.c503.sc.base.entity.SysModuleEntity;
import com.c503.sc.jxwl.common.dao.IManagerDao;
import com.c503.sc.jxwl.common.service.ISysMenuService;
import com.c503.sc.jxwl.orgdata.bean.BizUserEntity;
import com.c503.sc.jxwl.orgdata.dao.SysUserDao;

/**
 * 〈一句话功能简述〉获取菜单服务层接口
 * 〈功能详细描述〉
 * 
 * @author luocb
 * @version [版本号, 2015年12月8日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Service(value = "sysMenuService")
public class SysMenuServiceImpl implements ISysMenuService {
    /** managerDao */
    @Resource(name = "managerDao")
    private IManagerDao managerDao;
    
    /** sysUserDao */
    @Resource(name = "sysUserDao")
    private SysUserDao sysUserDao;
    
    /** 获取系统菜单 */
    private IAuthService authService = AuthFactory.getAuthService();
    
    @Override
    public void getAllMenus() {
        try {
            // 初始化数据
            authService.getSystemModul();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public SysModuleEntity getUserRightByUserToken(String userToken)
        throws Exception {
        // 使用httpClient获取用户权限
        return authService.getBizUserRightAsTreeByUserToken(userToken);
    }
    
    @Override
    public List<HashMap<String, Object>> findStatus(Map<String, Object> map) {
        map.put("remove", "0");
        return this.managerDao.findStatus(map);
    }
    
    @Override
    public Map<String, Object> getUserByAccount(String account, String sysId) {
        BizUserEntity entity = this.sysUserDao.getUserByAccount(account, sysId);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("account", entity.getAccount());
        map.put("password", entity.getPassword());
        map.put("salt", entity.getSalt());
        return map;
    }
    
    @Override
    public int updatePassword(Map<String, Object> map) {
        map.put("remove", "0");
        return this.managerDao.updatePassword(map);
    }
    
    @Override
    public boolean isExist(String account) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("account", account);
        Map<String, String> result = this.managerDao.isExist(map);
        if (result != null && result.get("OCCUPATION_ID") != null
            && result.get("ENTERPRISE_ID") != null){
            return true;
        }
            return false;
    }
}
