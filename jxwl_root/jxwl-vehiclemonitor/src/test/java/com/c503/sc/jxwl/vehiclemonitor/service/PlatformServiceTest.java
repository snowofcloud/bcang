/**
 * 文件名：PlatformServiceTest.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-8-8 
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.vehiclemonitor.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.c503.sc.jxwl.vehiclemonitor.base.BaseTest;
import com.c503.sc.jxwl.vehiclemonitor.bean.PlatformEntity;


public class PlatformServiceTest extends BaseTest{
    @Autowired
    private IplatformService platformService;
    
    @Test
    public void testFindByParams()
        throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("remove", "0");
        List<PlatformEntity> platform =
            this.platformService.findByParams(map);
        Assert.assertTrue(!platform.isEmpty());
    }
    
}
