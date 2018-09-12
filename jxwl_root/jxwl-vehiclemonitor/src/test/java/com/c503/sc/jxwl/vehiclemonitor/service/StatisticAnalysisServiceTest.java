/**
 * 文件名：StatisticAnalysisServiceTest.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-12-27
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.vehiclemonitor.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import com.c503.sc.jxwl.vehiclemonitor.base.BaseTest;
import com.c503.sc.jxwl.vehiclemonitor.dao.IStatisticalAnalysisDao;

public class StatisticAnalysisServiceTest extends BaseTest {
    @InjectMocks
    @Autowired
    private IStatisticalAnalysisService statisticalAnalysisService;
    
    @SuppressWarnings("unused")
    @Mock
    @Resource
    private IStatisticalAnalysisDao statisticalAnalysisDao;
    
    @Before
    public void setup()
        throws Exception {
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void testFindPieVal()
        throws Exception {
        this.statisticalAnalysisService.findPieVal();
    }
    
    @Test
    public void testCountAllByParams()
        throws Exception {
        Map<String, Object> map = new HashMap<>();
        this.statisticalAnalysisService.countAllByParams(map);
    }
    
    @Test
    public void testCountColumn()
        throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("flag", "car");
        this.statisticalAnalysisService.countColumn(map);
        
        map.put("flag", "alarm");
        this.statisticalAnalysisService.countColumn(map);
        
        map.put("flag", "waybill");
        this.statisticalAnalysisService.countColumn(map);
    }
}
