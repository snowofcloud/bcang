/**
 * 文件名：TrainingServiceTest.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-7-13
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.transacation.service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.springframework.beans.factory.annotation.Autowired;

import com.c503.sc.jxwl.transacation.base.BaseTest;
import com.c503.sc.jxwl.transacation.bean.WayBillGoodsEntity;
import com.c503.sc.jxwl.transacation.dao.IWayBillGoodsDao;

public class WayBillGoodsServiceTest extends BaseTest {
    @InjectMocks
    @Autowired
    private IWayBillGoodsService wayBillGoodsService;
    
    @Mock
    @Autowired
    private IWayBillGoodsDao wayBillGoodsDao;
    
    @Before
    public void setUp()
        throws Exception {
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void testFindByParams()
        throws Exception {
        WayBillGoodsEntity wayBill = new WayBillGoodsEntity();
        List<WayBillGoodsEntity> list = new LinkedList<>();
        list.add(wayBill);
        Map<String, Object> map = new HashMap<>();
        PowerMockito.when(this.wayBillGoodsDao.findByParams(map)).thenReturn(list);
        this.wayBillGoodsService.findByParams(map);
    }
    
    @Test
    public void testSave()
        throws Exception {
        WayBillGoodsEntity wayBill = new WayBillGoodsEntity();
        this.wayBillGoodsService.save(wayBill);
    }
    
    @Test
    public void testUpdate()
        throws Exception {
        WayBillGoodsEntity wayBill = new WayBillGoodsEntity();
        this.wayBillGoodsService.update(wayBill);
    }
    
    @Test
    public void testDelete()
        throws Exception {
        Map<String, Object> map = new HashMap<>();
        this.wayBillGoodsService.delete(map);
    }
    
    @Test
    public void testFindById()
        throws Exception {
        String id = "1";
        WayBillGoodsEntity result = null;
        PowerMockito.when(this.wayBillGoodsDao.findById(id)).thenReturn(result);
        this.wayBillGoodsService.findById(id);
    }
    
    
}