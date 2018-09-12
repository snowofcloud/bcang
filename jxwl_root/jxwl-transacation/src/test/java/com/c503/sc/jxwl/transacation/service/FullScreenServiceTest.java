/**
 * 文件名：TrainingServiceTest.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-7-13
 * 修改内容：〈修改内容〉
 *//*
package com.c503.sc.jxwl.transacation.service;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.c503.sc.jxwl.transacation.base.BaseTest;
import com.c503.sc.jxwl.transacation.service.impl.FullScreenServiceImp;

public class FullScreenServiceTest extends BaseTest {
    @Autowired
    private IFullScreenService fullScreenService;
    
    Class<FullScreenServiceImp> full = FullScreenServiceImp.class;
    
    @Before
    public void setUp()
        throws Exception {
        
    }
    
    @Test
    public void testFindByPage4Announcement()
        throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("page", 1);// 查询第几页
        map.put("rows", 12);// 每页条数
        this.fullScreenService.findByPage4Announcement(map);
    }
    
    @Test
    public void testFindByPage4News()
        throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("page", 1);// 查询第几页
        map.put("rows", 12);// 每页条数
        this.fullScreenService.findByPage4News(map);
    }
    
    @Test
    public void testAnnouncement4FullScreenEntityParse()
        throws Exception {
        Method m =
            full.getDeclaredMethod("announcement4FullScreenEntityParse",
                String.class);
        m.setAccessible(true);
        Dt d = new Dt();
        Map<String, Object> map = new HashMap<>();
        map.put("datas", null);
        d.setCode("001").setData(map);
        m.invoke(this.fullScreenService, JSON.toJSONString(d));
        
        try {
        	map.put("datas", "string");
            d.setCode("001").setData(map);
            m.invoke(this.fullScreenService, JSON.toJSONString(d));
		} catch (Exception e) {
			Assert.assertTrue(e instanceof Exception);
		}
        
    }
    
    @Test
    public void testNews4FullScreenEntityParse()
        throws Exception {
        Method m =
            full.getDeclaredMethod("news4FullScreenEntityParse", String.class);
        m.setAccessible(true);
        Dt d = new Dt();
        Map<String, Object> map = new HashMap<>();
        map.put("datas", null);
        d.setCode("001").setData(map);
        m.invoke(this.fullScreenService, JSON.toJSONString(d));
        
        try {
        	map.put("datas", "string");
            d.setCode("001").setData(map);
            m.invoke(this.fullScreenService, JSON.toJSONString(d));
		} catch (Exception e) {
			Assert.assertTrue(e instanceof Exception);
		}
        
        
    }
}

class Dt {
    String code;
    
    Map<String, Object> data;
    
    public String getCode() {
        return code;
    }
    
    public Dt setCode(String code) {
        this.code = code;
        return this;
    }
    
    public Map<String, Object> getData() {
        return data;
    }
    
    public Dt setData(Map<String, Object> data) {
        this.data = data;
        return this;
    }
}*/