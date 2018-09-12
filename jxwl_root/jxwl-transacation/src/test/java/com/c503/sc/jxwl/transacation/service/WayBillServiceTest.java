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
import com.c503.sc.jxwl.transacation.bean.WayBillEntity;
import com.c503.sc.jxwl.transacation.bean.WayBillGoodsEntity;
import com.c503.sc.jxwl.transacation.dao.IWayBillDao;
import com.c503.sc.jxwl.transacation.dao.IWayBillGoodsDao;

public class WayBillServiceTest extends BaseTest {
    @InjectMocks
    @Autowired
    private IWayBillService wayBillService;
    
    @Mock
    @Autowired
    private IWayBillDao wayBillDao;
    
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
        WayBillEntity wayBill = new WayBillEntity();
        List<WayBillEntity> list = new LinkedList<>();
        list.add(wayBill);
        Map<String, Object> map = new HashMap<>();
        PowerMockito.when(this.wayBillDao.findByParams(map)).thenReturn(list);
        this.wayBillService.findByParams(map);
    }
    
    @Test
    public void testSave()
        throws Exception {
        WayBillEntity wayBill = new WayBillEntity();
        this.wayBillService.save(wayBill);
    }
    
    @Test
    public void testUpdate()
        throws Exception {
        // 运单号不足12位
        WayBillEntity wayBill = new WayBillEntity();
        wayBill.setCheckno("16256225");
        this.wayBillService.update(wayBill);
        
        // 运单号满足12位
        wayBill.setCheckno("012345678900");
        this.wayBillService.update(wayBill);
    }
    
    @Test
    public void testDelete()
        throws Exception {
        Map<String, Object> map = new HashMap<>();
        this.wayBillService.delete(map);
    }
    
    @Test
    public void testFindById()
        throws Exception {
        String id = "1";
        WayBillEntity result = null;
        PowerMockito.when(this.wayBillDao.findById(id)).thenReturn(result);
        this.wayBillService.findById(id);
    }
    
    @Test
    public void updateForApp()
        throws Exception {
        WayBillEntity goods = new WayBillEntity();
        this.wayBillService.updateForApp(goods);
    }
    
    @Test
    public void findGoodsData()
        throws Exception {
        String carNo = "111";
        this.wayBillService.findGoodsData(carNo);
    }
    
    @Test
    public void saveTemp()
        throws Exception {
        WayBillEntity wayBill = new WayBillEntity();
        String id = "1111";
        wayBill.setId(id);
        wayBill.setTempname("ss");
        PowerMockito.when(this.wayBillDao.findById(id)).thenReturn(wayBill);
        
        // 货物不空
        List<WayBillGoodsEntity> wayBillGoodsList = new ArrayList<>();
        WayBillGoodsEntity waybillGood = new WayBillGoodsEntity();
        wayBillGoodsList.add(waybillGood);
        PowerMockito.when(this.wayBillGoodsDao.findAllGoods(id))
            .thenReturn(wayBillGoodsList);
        this.wayBillService.saveTemp(wayBill);
    }
    
    @Test
    public void saveTemp2()
        throws Exception {
        WayBillEntity wayBill = new WayBillEntity();
        String id = "1111";
        wayBill.setId(id);
        wayBill.setTempname("ss");
        PowerMockito.when(this.wayBillDao.findById(id)).thenReturn(wayBill);
        
        // 货物不空
        List<WayBillGoodsEntity> wayBillGoodsList = new ArrayList<>();
        PowerMockito.when(this.wayBillGoodsDao.findAllGoods(id))
            .thenReturn(wayBillGoodsList);
        this.wayBillService.saveTemp(wayBill);
        
    }
    
    @Test
    public void findVehicleData()
        throws Exception {
        Map<String, Object> map = new HashMap<>();
        this.wayBillService.findVehicleData(map);
    }
    
    @Test
    public void findByCarrierName()
        throws Exception {
        String carrierName = "aaa";
        this.wayBillService.findByCarrierName(carrierName);
    }
    
    @Test
    public void findCorporate()
        throws Exception {
        String carrierName = "aaa";
        wayBillService.findCorporate(carrierName);
    }
    
    @Test
    public void findCorporateNo()
        throws Exception {
        String enterpriseType = "aaa";
        this.wayBillService.findCorporateNo(enterpriseType);
    }
    
    @Test
    public void findPersonType()
        throws Exception {
        Map<String, Object> map = new HashMap<>();
        this.wayBillService.findPersonType(map);
    }
    
    @Test
    public void findWaybillForWeixinParams()
        throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("corporateNo", "111");
        this.wayBillService.findWaybillForWeixinParams(map);
    }
    
    
    @Test
    public void findisCarExist()
        throws Exception {
    	String id = "ddffddfdss";
        this.wayBillService.isCarExist(id);
    }
    
    @Test
    public void findisDeal()
        throws Exception {
    	String orderNo = "ddffddfdss";
        this.wayBillService.isDeal(orderNo);
    }
    
    @Test
    public void findOrderMessage()
        throws Exception {
    	String orderNo = "ddffddfdss";
        this.wayBillService.findOrderMessage(orderNo);
    }
    @Test
    public void findForBind()
        throws Exception {
    	Map<String, String> map = new HashMap<String, String>();
        this.wayBillService.findForBind(map);
    }
    
    
}