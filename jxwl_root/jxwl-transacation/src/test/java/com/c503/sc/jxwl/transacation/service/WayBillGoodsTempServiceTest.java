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
import com.c503.sc.jxwl.transacation.bean.WayBillGoodsTempEntity;
import com.c503.sc.jxwl.transacation.dao.IWayBillGoodsTempDao;

public class WayBillGoodsTempServiceTest extends BaseTest {
    @InjectMocks
    @Autowired
    private IWayBillGoodsTempService wayBillGoodsTempService;
    
    @Mock
    @Autowired
    private IWayBillGoodsTempDao wayBillGoodsTempDao;
    
    @Before
    public void setUp()
        throws Exception {
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void testFindByParams()
        throws Exception {
        WayBillGoodsTempEntity wayBill = new WayBillGoodsTempEntity();
        List<WayBillGoodsTempEntity> list = new LinkedList<>();
        list.add(wayBill);
        Map<String, Object> map = new HashMap<>();
        PowerMockito.when(this.wayBillGoodsTempDao.findByParams(map)).thenReturn(list);
        this.wayBillGoodsTempService.findByParams(map);
    }
    
    @Test
    public void testSave()
        throws Exception {
        WayBillGoodsTempEntity wayBill = new WayBillGoodsTempEntity();
        this.wayBillGoodsTempService.save(wayBill);
    }
    
    @Test
    public void testUpdate()
        throws Exception {
        WayBillGoodsTempEntity wayBill = new WayBillGoodsTempEntity();
        this.wayBillGoodsTempService.update(wayBill);
    }
    
    @Test
    public void testDelete()
        throws Exception {
        Map<String, Object> map = new HashMap<>();
        this.wayBillGoodsTempService.delete(map);
    }
    
    @Test
    public void testFindById()
        throws Exception {
        String id = "1";
        WayBillGoodsTempEntity result = null;
        PowerMockito.when(this.wayBillGoodsTempDao.findById(id)).thenReturn(result);
        this.wayBillGoodsTempService.findById(id);
    }
    
    
}