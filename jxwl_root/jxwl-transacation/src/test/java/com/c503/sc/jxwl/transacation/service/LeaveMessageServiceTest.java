/**
 * 文件名：LeaveMessageServiceTest.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-9-6
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.transacation.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.c503.sc.jxwl.transacation.base.BaseTest;
import com.c503.sc.jxwl.transacation.bean.LeaveMessageEntity;
import com.c503.sc.jxwl.transacation.bean.SrcGoods;
import com.c503.sc.jxwl.transacation.bean.SrcGoodsInfo;
import com.c503.sc.jxwl.transacation.dao.ILeaveMessageDao;
import com.c503.sc.jxwl.vehiclemonitor.bean.EnterpriseEntity;
import com.c503.sc.utils.basetools.C503StringUtils;

public class LeaveMessageServiceTest extends BaseTest {
    @Autowired
    private ILeaveMessageService leaveMessageService;
    
    @Autowired
    private ILeaveMessageDao leaveMessageDao;
    
    SrcGoods srcGood = new SrcGoods();
    
    SrcGoodsInfo srcGoodsInfo = new SrcGoodsInfo();
    
    LeaveMessageEntity leaveMessageEntity = new LeaveMessageEntity();
    
    SrcGoods srcGoodEntity = new SrcGoods();
    
    EnterpriseEntity hgEnterpriseEntity = new EnterpriseEntity();
    
    EnterpriseEntity wlEnterpriseEntity = new EnterpriseEntity();
    
    String leaveMessagePk = null;
    
    String userId = null;
    
    @Before
    public void setUp()
        throws Exception {
        leaveMessageEntity.setId(C503StringUtils.createUUID());
        srcGoodEntity.setId(C503StringUtils.createUUID());
        hgEnterpriseEntity.setId(C503StringUtils.createUUID());
        wlEnterpriseEntity.setId(C503StringUtils.createUUID());
        
        leaveMessageEntity.setLogisticsEnterprise(wlEnterpriseEntity.getId());
        leaveMessageEntity.setChemicalEnterprise(hgEnterpriseEntity.getId());
        leaveMessageEntity.setGoodsNo(srcGoodEntity.getId());
        
        this.leaveMessageDao.save(leaveMessageEntity);
        
        leaveMessagePk = leaveMessageEntity.getId();
    }
    
    @Test
    public void testFindByParams()
        throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        List<LeaveMessageEntity> rec =
            this.leaveMessageService.findByParams(map);
        if (!rec.isEmpty()) {
            Assert.assertNotNull(rec);
        }
    }
    
    @Test
    public void testFindById()
        throws Exception {
        String id = null;
        String hgCorporateNo = null;
        String wlCorporateNo = null;
        try {
            this.leaveMessageService.findById(id, hgCorporateNo, wlCorporateNo);
            
        } catch (Exception e) {
            Assert.assertTrue(e instanceof Exception);
        }
        
        id = this.leaveMessageEntity.getId();
        hgCorporateNo = this.hgEnterpriseEntity.getId();
        wlCorporateNo = this.wlEnterpriseEntity.getId();
        this.leaveMessageService.findById(id, hgCorporateNo, wlCorporateNo);
    }
    
    @Test
    public void testIsLeaveMessageDeleted()
        throws Exception {
        String id = "";
        try {
            this.leaveMessageService.isLeaveMessageDeleted(id);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof Exception);
        }
        
        this.leaveMessageService.isLeaveMessageDeleted(leaveMessageEntity.getId());
    }
    
    @Test
    public void testDelete()
        throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            this.leaveMessageService.delete(map);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof Exception);
        }
        
        map.put("id", leaveMessagePk);
        // map.put("updateBy", this.getUser().getId());
        userId = "dbd4486029834386b5ba5bcf8a001964";
        map.put("updateBy", userId);
        map.put("updateTime", new Date());
        this.leaveMessageService.delete(map);
    }
    
    @Test
    public void testSave()
        throws Exception {
        LeaveMessageEntity nullEntity = new LeaveMessageEntity();
        
        try {
            this.leaveMessageService.save(nullEntity);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof Exception);
        }
        
        this.leaveMessageService.save(leaveMessageEntity);
    }
    
    @Test
    public void testUpdate()
        throws Exception {
        LeaveMessageEntity nullEntity = new LeaveMessageEntity();
        try {
            this.leaveMessageService.update(nullEntity);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof Exception);
        }
        leaveMessageEntity.setMessageContent("更新的留言内容");
        this.leaveMessageService.update(leaveMessageEntity);
    }
    
    @Test
    public void testFindChemicalEnterpriseByGoodNo()
        throws Exception {
        String goodNo = "";
        try {
            this.leaveMessageService.findChemicalEnterpriseByGoodNo(goodNo);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof Exception);
        }
        
        this.leaveMessageService.findChemicalEnterpriseByGoodNo(leaveMessageEntity.getGoodsNo());
    }
    
    @Test
    public void testCheckGoodsStatus()
        throws Exception {
        String goodNo = "";
        try {
            this.leaveMessageService.checkGoodsStatus(goodNo);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof Exception);
        }
        
        this.leaveMessageService.checkGoodsStatus("6b77861eb12b48e1b653ce44dfda82fa");
    }
    
    @Test
    public void testFindLeaveMessageExist()
        throws Exception {
        String goodNo = "";
        String wlEnterprise = "";
        try {
            this.leaveMessageService.findLeaveMessageExist(wlEnterprise, goodNo);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof Exception);
        }
        goodNo = leaveMessageEntity.getGoodsNo();
        wlEnterprise = leaveMessageEntity.getLogisticsEnterprise();
        this.leaveMessageService.findLeaveMessageExist(wlEnterprise, goodNo);
    }
}