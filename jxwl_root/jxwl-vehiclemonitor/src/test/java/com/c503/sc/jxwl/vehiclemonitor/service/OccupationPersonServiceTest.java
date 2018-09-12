/**
 * 文件名：OccupationPersonServiceTest.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-7-22
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.vehiclemonitor.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.c503.sc.jxwl.vehiclemonitor.base.BaseTest;
import com.c503.sc.jxwl.vehiclemonitor.bean.OccupationPersonEntity;

public class OccupationPersonServiceTest extends BaseTest {
    @Autowired
    private IOccupationPersonService occupationPersonService;
    
    String pk = null;
    
    OccupationPersonEntity entity = new OccupationPersonEntity();
    
    @Before
    public void setUp()
        throws Exception {
        entity.setId("111111111111");
        entity.setCorporateNo("1111");
        entity.setEnterpriseName("嘉兴");
        entity.setRemove("0");
        entity.setCorporateNo("1111");
        entity.setOccupationPersonType("101001001");
        
        pk = (String) this.occupationPersonService.save(entity);
    }
    
    @Test
    public void testFindById()
        throws Exception {
        OccupationPersonEntity enenrgyManager =
            this.occupationPersonService.findById(pk);
        Assert.assertNotNull(enenrgyManager);
    }
    
    @Test
    public void testSave()
        throws Exception {
        this.occupationPersonService.save(entity);
    }
    
    @Test
    public void testFindByParams()
        throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("occupationPersonType", "101001001");
        map.put("remove", "0");
        List<OccupationPersonEntity> enterprise =
            this.occupationPersonService.findByParams(map);
        Assert.assertNotNull(enterprise);
    }
    
    @Test
    public void testUpdate()
        throws Exception {
        entity.setCorporateNo("1111222");
        this.occupationPersonService.update(entity);
    }
    
    @Test
    public void testDel()
        throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("updateBy", "x");
        map.put("updateTime", new Date());
        map.put("remove", "1");
        map.put("ids", new String[] {"1"});
        this.occupationPersonService.delete(map);
    }
}
