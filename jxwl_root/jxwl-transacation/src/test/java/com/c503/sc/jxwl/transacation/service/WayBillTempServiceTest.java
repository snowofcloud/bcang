/**
 * 文件名：TrainingServiceTest.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-7-13
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.transacation.service;

import java.util.ArrayList;
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
import com.c503.sc.jxwl.transacation.bean.WayBillTempEntity;
import com.c503.sc.jxwl.transacation.dao.IWayBillGoodsTempDao;
import com.c503.sc.jxwl.transacation.dao.IWayBillTempDao;

public class WayBillTempServiceTest extends BaseTest {
    @InjectMocks
    @Autowired
    private IWayBillTempService wayBillTempService;
    
    @Mock
    @Autowired
    private IWayBillTempDao wayBillTempDao;
    
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
        WayBillTempEntity wayBill = new WayBillTempEntity();
        List<WayBillTempEntity> list = new LinkedList<>();
        list.add(wayBill);
        Map<String, Object> map = new HashMap<>();
        PowerMockito.when(this.wayBillTempDao.findByParams(map))
            .thenReturn(list);
        this.wayBillTempService.findByParams(map);
    }
    
    @Test
    public void findVehicleData()
        throws Exception {
        Map<String, Object> map = new HashMap<>();
        this.wayBillTempService.findVehicleData(map);
    }
    
    @Test
    public void findByCarrierName()
        throws Exception {
        String carrierName = "aaa";
        this.wayBillTempService.findByCarrierName(carrierName);
    }
    
    @Test
    public void testSave()
        throws Exception {
        WayBillTempEntity wayBill = new WayBillTempEntity();
        this.wayBillTempService.save(wayBill);
    }
    
    @Test
    public void saveWayBill()
        throws Exception {
        WayBillTempEntity wayBill = new WayBillTempEntity();
        String id = "1111";
        wayBill.setId(id);
        wayBill.setCheckno("2010101023145698");
        PowerMockito.when(this.wayBillTempDao.findById(id)).thenReturn(wayBill);
        
        List<WayBillGoodsTempEntity> wayBillGoodsList = new ArrayList<>();
        WayBillGoodsTempEntity waybillGood = new WayBillGoodsTempEntity();
        wayBillGoodsList.add(waybillGood);
        PowerMockito.when(this.wayBillGoodsTempDao.findAllGoods(id))
            .thenReturn(wayBillGoodsList);
        this.wayBillTempService.saveWayBill(wayBill);
    }
    
    @Test
    public void saveWayBill2()
        throws Exception {
        WayBillTempEntity wayBill = new WayBillTempEntity();
        String id = "1111";
        wayBill.setId(id);
        wayBill.setCheckno("2010101023145698");
        PowerMockito.when(this.wayBillTempDao.findById(id)).thenReturn(wayBill);
        
        List<WayBillGoodsTempEntity> wayBillGoodsList = new ArrayList<>();
        PowerMockito.when(this.wayBillGoodsTempDao.findAllGoods(id))
            .thenReturn(wayBillGoodsList);
        this.wayBillTempService.saveWayBill(wayBill);
    }
    
    @Test
    public void testUpdate()
        throws Exception {
        WayBillTempEntity wayBill = new WayBillTempEntity();
        this.wayBillTempService.update(wayBill);
    }
    
    @Test
    public void testDelete()
        throws Exception {
        Map<String, Object> map = new HashMap<>();
        this.wayBillTempService.delete(map);
    }
    
    @Test
    public void testFindById()
        throws Exception {
        String id = "1";
        WayBillTempEntity result = null;
        PowerMockito.when(this.wayBillTempDao.findById(id)).thenReturn(result);
        this.wayBillTempService.findById(id);
    }
    
    @Test
    public void isExistTempName()
        throws Exception {
        this.wayBillTempDao.isExistTempName("sergfdgfdgfghsfdhsfh");
    }
    
}