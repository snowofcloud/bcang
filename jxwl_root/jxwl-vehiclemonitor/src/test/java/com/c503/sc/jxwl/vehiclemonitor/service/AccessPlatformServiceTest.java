/**
 * 文件名：AccessPlatformServiceTest.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-7-26 
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.vehiclemonitor.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;

import com.c503.sc.base.entity.Page;
import com.c503.sc.jxwl.vehiclemonitor.base.BaseTest;
import com.c503.sc.jxwl.vehiclemonitor.bean.AccessPlatformEntity;
import com.c503.sc.jxwl.vehiclemonitor.dao.IAccessPlatformDao;


public class AccessPlatformServiceTest extends BaseTest{
    
    @InjectMocks
    @Resource(name = "accessPlatformService")
    private IAccessPlatformService accessPlatformService;
    
    @Mock
    @Resource(name = "accessPlatformDao")
    private IAccessPlatformDao accessPlatformDao;
    
    @Before
    public void setup()
        throws Exception {
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void testFind() throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        String platformName = "A064896";
        Page pageEntity = new Page();
        map.put("platformName", platformName);
        map.put("page", pageEntity);
        List<AccessPlatformEntity> temp = new ArrayList<AccessPlatformEntity>();
        AccessPlatformEntity entity = new AccessPlatformEntity();
        temp.add(entity);
        PowerMockito.when(accessPlatformDao.findAccessPlatformByParams(map))
        .thenReturn(temp);
        List<AccessPlatformEntity> result = accessPlatformService.findAccessPlatformData(map);
        assertEquals(1, result.size());
        
    }
}
