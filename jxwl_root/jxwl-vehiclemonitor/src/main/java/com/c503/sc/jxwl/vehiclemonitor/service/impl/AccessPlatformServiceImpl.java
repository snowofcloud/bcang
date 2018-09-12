/**
 * 文件名：AccessPlatformServiceImpl.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-7-26
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.vehiclemonitor.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.c503.sc.jxwl.vehiclemonitor.bean.AccessPlatformEntity;
import com.c503.sc.jxwl.vehiclemonitor.dao.IAccessPlatformDao;
import com.c503.sc.jxwl.vehiclemonitor.service.IAccessPlatformService;

/**
 * 
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * 
 * @author yuanyl
 * @version [版本号, 2016-7-26]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Service(value = "accessPlatformService")
public class AccessPlatformServiceImpl implements IAccessPlatformService {
    /** accessPlatformDao */
    @Resource(name = "accessPlatformDao")
    private IAccessPlatformDao accessPlatformDao;
    
    @Override
    public List<AccessPlatformEntity> findAccessPlatformData(
        Map<String, Object> map)
        throws Exception {
        List<AccessPlatformEntity> result =
            accessPlatformDao.findAccessPlatformByParams(map);
        return result;
    }
    
}
