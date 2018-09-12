/**
 * 文件名：EmergentLinkmanServiceTest.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-8-8
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
import com.c503.sc.jxwl.vehiclemonitor.bean.EmergentLinkmanEntity;

public class EmergentLinkmanServiceTest extends BaseTest {
    @Autowired
    private IEmergentLinkmanService emergentLinkmanService;
    
    String a = null;
    
    EmergentLinkmanEntity vaLinkmanEntity = new EmergentLinkmanEntity();
    
    @Before
    public void setUp()
        throws Exception {
        vaLinkmanEntity.setPersonName("小张");
        vaLinkmanEntity.setTelephone("13536663656");
        vaLinkmanEntity.setCompanyName("嘉兴物流公司");
        vaLinkmanEntity.setFixedNo("0871-2626262");
        vaLinkmanEntity.setRemove("0");
        
        a = (String) this.emergentLinkmanService.save(vaLinkmanEntity);
    }
    
    @Test
    public void testFindByParams()
        throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("remove", "0");
        List<EmergentLinkmanEntity> emergentLinkmanEntities =
            this.emergentLinkmanService.findByParams(map);
        Assert.assertTrue(!emergentLinkmanEntities.isEmpty());
    }
    
    @Test
    public void testSave()
        throws Exception {
        this.emergentLinkmanService.save(vaLinkmanEntity);
    }
    
    @Test
    public void testFindById()
        throws Exception {
        String id = a;
        this.emergentLinkmanService.findById(id);
        
        this.emergentLinkmanService.findById("xx");
        
    }
    
    @Test
    public void testUpdate()
        throws Exception {
        this.emergentLinkmanService.update(vaLinkmanEntity);
    }
    
    @Test
    public void testDel()
        throws Exception {
        EmergentLinkmanEntity emergentLinkmanEntity =
            new EmergentLinkmanEntity();
        emergentLinkmanEntity.setUpdateBy("小李");
        emergentLinkmanEntity.setUpdateTime(new Date());
        emergentLinkmanEntity.setRemove("1");
        emergentLinkmanEntity.setId("1");
        this.emergentLinkmanService.delete(emergentLinkmanEntity);
    }
}
