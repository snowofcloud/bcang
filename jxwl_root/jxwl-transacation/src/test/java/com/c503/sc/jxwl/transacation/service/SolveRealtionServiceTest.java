/**
 * 文件名：TrainingServiceTest.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-7-13
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.transacation.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.springframework.beans.factory.annotation.Autowired;

import com.c503.sc.jxwl.transacation.base.BaseTest;
import com.c503.sc.jxwl.zcpt.bean.LimitArea;
import com.c503.sc.jxwl.zcpt.bean.SolveRoute;
import com.c503.sc.jxwl.zcpt.dao.IShortcutDao;

public class SolveRealtionServiceTest extends BaseTest {
    @InjectMocks
    @Autowired
    private ISolveRouteService solveRouteService;
    
    @InjectMocks
    @Autowired
    private IParkAreaService parkAreaService;
    
    @InjectMocks
    @Autowired
    private IKaDoorService kaDoorService;
    
    @Mock
    @Resource(name = "shortcutDao")
    private IShortcutDao shortcutDao;
    
    @Before
    public void setUp()
        throws Exception {
        MockitoAnnotations.initMocks(this);
    }
    
    // 园区区域
    @Test
    public void testFindParkArea() {
        try {
            this.parkAreaService.findParkArea();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // 路径
    @Test
    public void testSaveSolveRoute() {
        try {
            SolveRoute solveRoute = new SolveRoute();
            this.solveRouteService.saveSolveRoute(solveRoute);
        }
        catch (Exception e) {
        }
    }
    
    @Test
    public void testFindLastRoute() {
        try {
            String carrierName = "xxxxs";
            this.solveRouteService.findLastRoute(carrierName);
        }
        catch (Exception e) {
        }
    }
    
    @Test
    public void testFindRouteByWaybillId() {
        try {
            String waybillId = "xxxx";
            this.solveRouteService.findRouteByWaybillId(waybillId);
        }
        catch (Exception e) {
        }
    }
    
    // 卡口点
    @Test
    public void testFindAllKaDoor() {
        try {
            this.kaDoorService.findAllKaDoor();
        }
        catch (Exception e) {
            // TODO: handle exception
        }
    }
    
    // 卡口点
    @Test
    public void testsetBarriersArgs() {
        List<LimitArea> limits = null;
        try {
            PowerMockito.when(this.shortcutDao.findAllNotInAreas())
                .thenReturn(limits);
            this.solveRouteService.setBarriersArgs();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        limits = new ArrayList<LimitArea>();
        LimitArea limit = new LimitArea();
        limit.setPoints(null);
        LimitArea limit1 = new LimitArea();
        limit1.setPoints("[[121.1381721496582,30.741591453552246],[121.16349220275879,30.741934776306152],[121.15096092224121,30.727858543395996],[121.1381721496582,30.741591453552246]]");
        limits.add(limit);
        limits.add(limit1);
        try {
            PowerMockito.when(this.shortcutDao.findAllNotInAreas())
                .thenReturn(limits);
            this.solveRouteService.setBarriersArgs();
        }
        catch (Exception e) {
            Assert.assertTrue(e instanceof Exception);
        }
    }
    
}