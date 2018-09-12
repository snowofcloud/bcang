/**
 * 文件名：TrainingServiceTest.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-7-13
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.transacation.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.springframework.beans.factory.annotation.Autowired;

import com.c503.sc.jxwl.transacation.base.BaseTest;
import com.c503.sc.jxwl.transacation.bean.CommentComplain;
import com.c503.sc.jxwl.transacation.bean.OrderForFull;
import com.c503.sc.jxwl.transacation.bean.SrcGoods;
import com.c503.sc.jxwl.transacation.dao.IOrderManageDao;
import com.c503.sc.utils.common.SystemContants;

public class OrderManageServiceTest extends BaseTest {
    @InjectMocks
    @Autowired
    private IOrderManageService orderManageService;
    
    @Mock
    @Autowired
    private IOrderManageDao orderManageDao;
    
    @Before
    public void setUp()
        throws Exception {
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void testFindByparams()
        throws Exception {
        SrcGoods srcGood = new SrcGoods();
        List<SrcGoods> list = new LinkedList<>();
        list.add(srcGood);
        Map<String, Object> map = new HashMap<>();
        map.put("remove", SystemContants.ON);
        map.put("hgCorporateNo", "111");
        PowerMockito.when(this.orderManageDao.findByParams(map))
            .thenReturn(list);
        this.orderManageService.findByParams(map);
        Exception e = new Exception();
        PowerMockito.when(this.orderManageDao.findByParams(map)).thenThrow(e);
        try {
            this.orderManageService.findByParams(map);
        } catch (Exception e2) {
            Assert.assertTrue(e2 instanceof Exception);
        }
    }
    
    @Test
    public void testFindAllOrders()
        throws Exception {
        OrderForFull orderforfull = new OrderForFull();
        List<OrderForFull> list = new LinkedList<>();
        list.add(orderforfull);
        Map<String, Object> map = new HashMap<String, Object>();
        PowerMockito.when(this.orderManageDao.findAllOrders(map))
            .thenReturn(list);
        this.orderManageService.findAllOrders(map);
    }
    
    @Test
    public void testFindById()
        throws Exception {
        SrcGoods result = new SrcGoods();
        String id = "1";
        String hgCorporateNo = "";
        String wlCorporateNo = "111";
        String tradeObjCode = "1111t";
        PowerMockito.when(this.orderManageDao.findById(id,
            hgCorporateNo,
            wlCorporateNo)).thenReturn(result);
        this.orderManageService.findById(id,
            hgCorporateNo,
            wlCorporateNo,
            tradeObjCode);
        
        String hgCorporateNo2 = "12345";
        String wlCorporateNo2 = "";
        PowerMockito.when(this.orderManageDao.findById(id,
            hgCorporateNo,
            wlCorporateNo)).thenReturn(result);
        this.orderManageService.findById(id,
            hgCorporateNo2,
            wlCorporateNo2,
            tradeObjCode);
        
        String hgCorporateNo3 = "";
        String wlCorporateNo3 = "";
        this.orderManageService.findById(id,
            hgCorporateNo3,
            wlCorporateNo3,
            tradeObjCode);
        
    }
    
    @Test
    public void testFindTradeStatus()
        throws Exception {
        String result = null;
        String id = "111";
        PowerMockito.when(this.orderManageDao.findTradeStatus(id))
            .thenReturn(result);
        this.orderManageService.findTradeStatus(id);
    }
    
    @Test
    public void testUpdateTradeStatus()
        throws Exception {
        int upLine = 0;
        String id = "111";
        String tradeStatus = "111";
        String updateBy = "22";
        PowerMockito.when(this.orderManageDao.updateTradeStatus(id,
            tradeStatus,
            updateBy,
            new Date())).thenReturn(upLine);
        this.orderManageService.updateTradeStatus(id,
            tradeStatus,
            updateBy,
            new Date());
    }
    
    @Test
    public void testCommentComplain()
        throws Exception {
        CommentComplain commentComplain = new CommentComplain();
        commentComplain.setContent("content");
        commentComplain.setId("content");
        commentComplain.setOrderId("content");
        commentComplain.setType("content");
        BigDecimal bd = new BigDecimal("3");
        commentComplain.setScore(bd);
        commentComplain.setOtherSideNo("content");
        commentComplain.setCorporateNo("content");
        commentComplain.setCreateBy("content");
        commentComplain.setCreateTime(new Date());
        commentComplain.setUpdateBy("updateByupdateBy");
        commentComplain.setUpdateTime(new Date());
        this.orderManageService.commentComplain(commentComplain);
    }
    
    @Test
    public void testfindDataForWeixinByParams()
        throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("corporateNo", "222");
        this.orderManageService.findDataForWeixinByParams(map);
    }
    
    @Test
    public void testfindByIdForWeixin()
        throws Exception {
        String id = "id";
        String tradeObjCode = "code";
        this.orderManageService.findByIdForWeixin(id, tradeObjCode);
    }
    
}