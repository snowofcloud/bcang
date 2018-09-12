/**
 * 文件名：RuleRegulationServiceTest.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-8-5
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
import com.c503.sc.jxwl.vehiclemonitor.bean.RuleRegulationEntity;

public class RuleRegulationServiceTest extends BaseTest {
    @Autowired
    private IRuleRegulationService ruleRegulationService;
    
    String a = null;
    
    RuleRegulationEntity val = new RuleRegulationEntity();
    
    @Before
    public void setUp()
        throws Exception {
        val.setCompany("嘉兴物流");
        val.setIdentifier("1");
        val.setRuleName("嘉兴");
        val.setSendContent("发布内容测试");
        val.setSendDate(new Date());
        val.setRemove("0");
        
        a = (String) this.ruleRegulationService.save(val);
    }
    
    @Test
    public void testFindByParams()
        throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("remove", "0");
        List<RuleRegulationEntity> ruleRegulation =
            this.ruleRegulationService.findByParams(map);
        Assert.assertTrue(!ruleRegulation.isEmpty());
    }
    
    @Test
    public void testSave()
        throws Exception {
        this.ruleRegulationService.save(val);
    }
    
    @Test
    public void testFindById()
        throws Exception {
        String id = a;
        this.ruleRegulationService.findById(id);
        
        this.ruleRegulationService.findById("xxxx");
        
    }
    
    @Test
    public void testUpdate()
        throws Exception {
        this.ruleRegulationService.update(val);
    }
    
    @Test
    public void testDel()
        throws Exception {
        RuleRegulationEntity ruleRegulation = new RuleRegulationEntity();
        ruleRegulation.setUpdateBy("小张");
        ruleRegulation.setUpdateTime(new Date());
        ruleRegulation.setRemove("1");
        ruleRegulation.setId("1");
        this.ruleRegulationService.delete(ruleRegulation);
    }
}
