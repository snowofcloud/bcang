/**
 * 文件名：TrainingServiceTest.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-7-13
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.transacation.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.c503.sc.jxwl.transacation.base.BaseTest;

public class ImageShowServiceTest extends BaseTest {
    @Autowired
    private IImageShowService imageShowService;
    
    @Before
    public void setUp()
        throws Exception {
        
    }
    
    @Test
    public void testfindByNameOrCode()
        throws Exception {
        String queryStr = null;
        try {
            this.imageShowService.findByNameOrCode(queryStr);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof Exception);
        }
        queryStr = "666";
        this.imageShowService.findByNameOrCode(queryStr);
    }
    
    @Test
    public void testfindByCode()
        throws Exception {
        String code = null;
        try {
            this.imageShowService.findByCode(code);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof Exception);
        }
        
        code = "222222";
        this.imageShowService.findByCode(code);
    }
    
    @Test
    public void findByGOVER()
        throws Exception {
        try {
            this.imageShowService.findByGOVER();
        } catch (Exception e) {
            Assert.assertTrue(e instanceof Exception);
        }
        
    }
    
}