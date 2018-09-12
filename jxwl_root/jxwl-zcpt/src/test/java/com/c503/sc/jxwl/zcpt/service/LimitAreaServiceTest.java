/**
 * 文件名：TrainingServiceTest.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-7-13
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.zcpt.service;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import com.c503.sc.jxwl.zcpt.base.BaseTest;
import com.c503.sc.jxwl.zcpt.bean.LimitArea;
import com.c503.sc.utils.basetools.C503StringUtils;

public class LimitAreaServiceTest extends BaseTest {
    @InjectMocks
    @Autowired
    private ILimitAreaService limitAreaService;
    
    LimitArea limitArea = null;
    
    String pk = null;
    
    @Before
    public void setUp()
        throws Exception {
        MockitoAnnotations.initMocks(this);
        pk = C503StringUtils.createUUID();
        limitArea =
            new LimitArea(pk, C503StringUtils.createUUID(), "[55.0,56.2]",
                "not in", "103001004", new BigDecimal(60));
        
        try {
            this.limitAreaService.save(limitArea);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void testDeleteLimitArea() {
        try {
            this.limitAreaService.delLimitArea(pk);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void testUpdateLimitArea() {
        try {
            this.limitAreaService.update(limitArea);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void testFindLimitAreas() {
        try {
            this.limitAreaService.findAllAreaLimit();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void testFindLimitAreaById() {
        try {
            this.limitAreaService.findByLimitAreaId(pk);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /*
     * @Test
     * public void testFindCarDispVal() {
     * try {
     * this.limitAreaService.findCarDispVal("黑A12345");
     * } catch (Exception e) {
     * e.printStackTrace();
     * }
     * }
     */
}