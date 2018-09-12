/**
 * 货源管理
 */
package com.c503.sc.jxwl.transacation.service;

import java.util.ArrayList;
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

import com.c503.sc.jxwl.common.constant.DictConstant;
import com.c503.sc.jxwl.transacation.base.BaseTest;
import com.c503.sc.jxwl.transacation.bean.SrcGoods;
import com.c503.sc.jxwl.transacation.bean.SrcGoodsInfo;
import com.c503.sc.jxwl.transacation.dao.ISrcGoodsDao;

public class SrcGoodsServiceTest extends BaseTest {
    @InjectMocks
    @Autowired
    private ISrcGoodsService srcGoodsService;
    
    @Mock
    @Autowired
    private ISrcGoodsDao srcGoodsDao;
    
    SrcGoods srcGoods = new SrcGoods();
    
    List<SrcGoodsInfo> goodss = new ArrayList<>();
    
    @Before
    public void setUp()
        throws Exception {
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void testFindByParams()
        throws Exception {
        SrcGoods srcGood = new SrcGoods();
        List<SrcGoods> list = new LinkedList<>();
        list.add(srcGood);
        Map<String, Object> map = new HashMap<>();
        PowerMockito.when(this.srcGoodsDao.findByParams(map)).thenReturn(list);
        try {
            this.srcGoodsService.findByParams(map);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof Exception);
        }
    }
    
    @Test
    public void testSave()
        throws Exception {
        String waybillno = "20160907";
        SrcGoods srcGoods = new SrcGoods();
        srcGoods.setGoodsInfos(goodss);
        srcGoods.setWaybilllNo(waybillno);
        
        PowerMockito.when(this.srcGoodsDao.findWaybilllNoHasExist(srcGoods.getWaybilllNo(),
            waybillno))
            .thenReturn(waybillno);
        try {
            this.srcGoodsService.save(srcGoods);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof Exception);
        }
        
    }
    
    @Test
    public void testDelete()
        throws Exception {
        String id = "0503400389", updateBy = "xxxxxxx";
        
        // 是否存在
        PowerMockito.when(this.srcGoodsDao.findExsitById(id))
            .thenReturn("456456456");
        try {
            this.srcGoodsService.delete(id, updateBy);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof Exception);
        }
        
        // 状态不在未发布或已发布则不能进行删除
        PowerMockito.when(this.srcGoodsDao.findTradeStatus(id))
            .thenReturn("456456456");
        try {
            this.srcGoodsService.delete(id, updateBy);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof Exception);
        }
        
        // 可以删除
        PowerMockito.when(this.srcGoodsDao.findTradeStatus(id))
            .thenReturn(DictConstant.SRC_GOODS1_NOT_PUBLISH);
        try {
            this.srcGoodsService.delete(id, updateBy);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof Exception);
        }
        
    }
    
    @Test
    public void testUpdate()
        throws Exception {
        SrcGoods srcGoods = new SrcGoods();
        try {
            this.srcGoodsService.update(srcGoods);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof Exception);
        }
        
    }
    
    @Test
    public void testFindById()
        throws Exception {
        SrcGoods srcGoods = new SrcGoods();
        srcGoods.setId("542512154");
        PowerMockito.when(this.srcGoodsDao.findById("22222xxx"))
            .thenReturn(srcGoods);
        this.srcGoodsService.findById("22222xxx");
    }
    
    @Test
    public void testPublish()
        throws Exception {
        String id = "22222xxx", updateBy = "xxxxxxx";
        String[] pubOrSigns = {"0", "7896541423"};
        // 发布 ok
        PowerMockito.when(this.srcGoodsDao.findTradeStatus(id))
            .thenReturn(DictConstant.SRC_GOODS1_NOT_PUBLISH);
        this.srcGoodsService.publishOrSign(id,
            DictConstant.SRC_GOODS2_PUBLISHED,
            updateBy,
            pubOrSigns);
        // 是否存在
        PowerMockito.when(this.srcGoodsDao.findExsitById(id))
            .thenReturn(DictConstant.SRC_GOODS1_NOT_PUBLISH);
        this.srcGoodsService.publishOrSign(id,
            DictConstant.SRC_GOODS2_PUBLISHED,
            updateBy,
            pubOrSigns);
        
        // 发布异常 (已发布)
        PowerMockito.when(this.srcGoodsDao.findTradeStatus(id))
            .thenReturn(DictConstant.SRC_GOODS2_PUBLISHED);
        try {
            this.srcGoodsService.publishOrSign(id,
                DictConstant.SRC_GOODS2_PUBLISHED,
                updateBy,
                pubOrSigns);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof Exception);
        }
    }
    
    @Test
    public void testSign()
        throws Exception {
        String id = "22222xxx", updateBy = "xxxxxxx";
        String[] pubOrSigns = {"1", "7896541423"};
        // 签订 ok
        PowerMockito.when(this.srcGoodsDao.findTradeStatus(id))
            .thenReturn(DictConstant.SRC_GOODS2_PUBLISHED);
        this.srcGoodsService.publishOrSign(id,
            DictConstant.SRC_GOODS3_WAIT_SURE,
            updateBy,
            pubOrSigns);
        
        // 签订 异常 (已签订)
        PowerMockito.when(this.srcGoodsDao.findTradeStatus(id))
            .thenReturn(DictConstant.SRC_GOODS3_WAIT_SURE);
        try {
            this.srcGoodsService.publishOrSign(id,
                DictConstant.SRC_GOODS4_REFUSED,
                updateBy,
                pubOrSigns);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof Exception);
        }
    }
    
    @Test
    public void testFindEnpInfos()
        throws Exception {
        Map<String, Object> map = new HashMap<>();
        int i = 3;
        do {
            map.put("matchCondition", "00" + i);
            this.srcGoodsService.findEnpInfos(map);
            i--;
        } while (0 < i);
    }
    
    @Test
    public void testFindAllForFull()
        throws Exception {
        this.srcGoodsService.findAll("000000");
    }
    
}