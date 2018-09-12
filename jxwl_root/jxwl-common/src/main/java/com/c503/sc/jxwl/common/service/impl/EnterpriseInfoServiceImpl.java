/**
 * 文件名：EnterpriseInfoServiceImpl.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-6-28
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.common.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.c503.sc.jxwl.common.bean.ManagerEntity;
import com.c503.sc.jxwl.common.dao.IManagerDao;
import com.c503.sc.jxwl.common.service.IEnterpriseInfoService;
import com.c503.sc.jxwl.common.bean.EnterpriseEntity;
import com.c503.sc.log.LoggingManager;
import com.c503.sc.utils.common.SystemContants;

/**
 * 〈一句话功能简述〉获取企业相关信息
 * 〈功能详细描述〉
 * 
 * @author zz
 * @version [版本号, 2016-6-28]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Service(value = "enterpriseInfoService")
public class EnterpriseInfoServiceImpl implements IEnterpriseInfoService {
    /** 管理员信息DAO */
    @Resource(name = "managerDao")
    // @Autowired
    private IManagerDao managerDao;
    
    /** 日志器 */
    private static final LoggingManager LOGGER =
        LoggingManager.getLogger(EnterpriseInfoServiceImpl.class);
    
    @Override
    public ManagerEntity findCorporateCode(String id)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, id);
        ManagerEntity managerEn = this.managerDao.findCorporateCode(id);
        LOGGER.debug(SystemContants.DEBUG_END, id);
        
        return managerEn;
    }
    
    @Override
    public List<EnterpriseEntity> findEnterpriseByCorporateCode(String corporateNo)
        throws Exception {
        List<EnterpriseEntity> list = null;
        list = this.managerDao.findEnterpriseByCorporateCode(corporateNo);
        return list;
    }
    
}
