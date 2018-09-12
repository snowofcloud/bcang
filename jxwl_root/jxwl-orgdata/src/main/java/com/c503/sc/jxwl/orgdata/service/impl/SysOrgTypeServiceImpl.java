/**
 * 文件名：OrganizationServiceImpl.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2015年10月21日
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.orgdata.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.c503.sc.base.exception.CustomException;
import com.c503.sc.jxwl.orgdata.bean.NumberContent;
import com.c503.sc.jxwl.orgdata.bean.SysOrganTypeEntity;
import com.c503.sc.jxwl.orgdata.dao.ISysOrganTypeDao;
import com.c503.sc.jxwl.orgdata.service.ISysOrgTypeService;
import com.c503.sc.log.LoggingManager;
import com.c503.sc.utils.common.SystemContants;

/**
 * 〈一句话功能简述〉实现企业信息与认证系统信息同步
 * 〈功能详细描述〉
 * 
 * @author yjh
 * @version [版本号, 2016年08月09日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Service(value = "sysOrgTypeService")
public class SysOrgTypeServiceImpl implements ISysOrgTypeService {
    
    /** 日志器 */
    private static final LoggingManager LOGGER =
        LoggingManager.getLogger(SysOrgTypeServiceImpl.class);
    
    /**
     * 对认证系统企业机构信息进行增删改查操作
     */
    @Resource(name = "sysOrganTypeDao")
    private ISysOrganTypeDao sysOrganTypeDao;
    
    @Override
    public int updateOrSave(SysOrganTypeEntity entity)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, entity);
        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", entity.getId());
        params.put("remove", SystemContants.ON);
        SysOrganTypeEntity org = sysOrganTypeDao.findById(params);
        int i = 0;
        if (org == null) {// 新增
            entity.setCreateTime(new Date());
            entity.setUpdateTime(new Date());
            entity.setRemove(SystemContants.ON);
            i = sysOrganTypeDao.save(entity);
        }
        else {// 修改
            entity.setUpdateTime(new Date());
            i = sysOrganTypeDao.updateInfo(entity);
        }
        LOGGER.debug(SystemContants.DEBUG_END, entity);
        return i;
    }
    
    // @Override
    /*
     * public int delete(Map<String, Object> map)
     * throws Exception {
     * LOGGER.debug(SystemContants.DEBUG_START, map);
     * int delLine = 0;
     * map.put("remove", SystemContants.OFF);
     * 
     * try {
     * delLine = this.sysOrganTypeDao.delete(map);
     * }
     * catch (Exception e) {
     * throw new CustomException(300000001, e, map);
     * }
     * LOGGER.debug(SystemContants.DEBUG_END, map);
     * 
     * return delLine;
     * }
     */
    
    @Override
    public String findEnCodeByOrgId(String orgId)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, orgId);
        String code = null;
        
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("orgId", orgId);
            params.put("remove", "0");
            code = this.sysOrganTypeDao.findEnCodeByOrgId(params);
        }
        catch (Exception e) {
            throw new CustomException(NumberContent.SYSTEMEXCEPTIONCODE, e,
                orgId);
        }
        LOGGER.debug(SystemContants.DEBUG_END, orgId);
        return code;
    }
    
    @Override
    public List<String> findUserRoleByUserId(String uerId)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, uerId);
        List<String> roleIds = null;
        
        try {
            roleIds = this.sysOrganTypeDao.findUserRoleByUserId(uerId);
        }
        catch (Exception e) {
            throw new CustomException(NumberContent.SYSTEMEXCEPTIONCODE, e,
                uerId);
        }
        LOGGER.debug(SystemContants.DEBUG_END, uerId);
        return roleIds;
    }
    
}
