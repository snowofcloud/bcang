/**
 * 文件名：BlacklistManageServiceTest.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-7-26
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.vehiclemonitor.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.c503.sc.jxwl.orgdata.bean.SysOrganTypeEntity;
import com.c503.sc.jxwl.orgdata.dao.ISysOrganTypeDao;
import com.c503.sc.jxwl.vehiclemonitor.base.BaseTest;
import com.c503.sc.jxwl.vehiclemonitor.bean.BlacklistManageEntity;
import com.c503.sc.jxwl.vehiclemonitor.bean.EnterpriseEntity;
import com.c503.sc.jxwl.vehiclemonitor.vo.AlarmNumVo;
import com.c503.sc.utils.basetools.C503StringUtils;

public class BlacklistManageServiceTest extends BaseTest {
    @Autowired
    private IBlacklistManageService blacklistManageService;
    
    @Autowired
    private IEnterpriseService enterpriseService;
    
    String pk = null;
    
    BlacklistManageEntity entity = new BlacklistManageEntity();
    
    EnterpriseEntity enterprise = new EnterpriseEntity();
    
    /**
     * 对认证系统企业机构信息进行增删改查操作
     */
    @Resource(name = "sysOrganTypeDao")
    private ISysOrganTypeDao sysOrganTypeDao;
    
    @Before
    public void setUp()
        throws Exception {
        // 数据准备
        SysOrganTypeEntity entity2 = new SysOrganTypeEntity();
        
        entity2.setId(C503StringUtils.createUUID());
        entity2.setCode(C503StringUtils.createUUID());
        // 4.政府，5.化工，6，物流企业
        entity2.setOrganTypeId("4");
        entity2.setName("盛通物流");
        // 物流系系统id为6
        entity2.setSysId("6");
        entity2.setPid("03003");
        this.sysOrganTypeDao.save(entity2);
        EnterpriseEntity entity3 = new EnterpriseEntity();
        entity3.setAddress("成都");
        entity3.setCorporateNo("asdasd1111");
        entity3.setEnterpriseName("嘉兴");
        entity3.setRegistrationNo("2222");
        entity3.setRemove("0");
        entity3.setTaxationNo("3333");
        this.enterpriseService.save(entity3);
        // 数据准备结束
        
        entity.setBlacklistType("001");
        entity.setBlacklistReason("超速超载");
        entity.setBlacklistDate(new Date());
        entity.setRemove("0");
        
        this.blacklistManageService.save(entity);
    }
    
    @Test
    public void testFindById()
        throws Exception {
        BlacklistManageEntity enenrgyManager =
            this.blacklistManageService.findById(entity.getId());
        Assert.assertNotNull(enenrgyManager);
    }
    
    @Test
    public void testFindByParams()
        throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("remove", "0");
        List<BlacklistManageEntity> enterprise =
            this.blacklistManageService.findByParams(map);
        Assert.assertNotNull(enterprise);
    }
    
    @Test
    public void testUpdate()
        throws Exception {
        entity.setBlacklistType("002");
        entity.setBlacklistReason("你咬我");
        entity.setBlacklistDate(new Date());
        this.blacklistManageService.update(entity);
    }
    
    @Test
    public void testDelete()
        throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("updateBy", "1");
        map.put("updateTime", new Date());
        map.put("remove", "1");
        map.put("ids", new String[] {"1"});
        this.blacklistManageService.delete(map);
    }
    
    @Test
    public void testExportExcel()
        throws Exception {
        // 有数据
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> recvMap =
            this.blacklistManageService.exportExcel(map);
        Assert.assertNotNull(recvMap);
    }
    
    @Test
    public void testUpdateAlarmNum()
        throws Exception {
    	AlarmNumVo vo = new AlarmNumVo();
    	BigDecimal a = new BigDecimal("1");
    	vo.setDriverAlarmNum(a);
    	vo.setEnterpriseAlarmNum(a);
    	vo.setId("asdaweq2123");
    	vo.setVehicleAlarmNum(a);
        this.blacklistManageService.updateAlarmNum(vo);
    }
    
    @Test
    public void testFindAlarmNumById()
        throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        this.blacklistManageService.findAlarmNumById(map);
        
    }
    
    @Test
    public void testFindNumById()
        throws Exception {
    	String corporateNo= "aaaaaaa";
        this.blacklistManageService.findNumById(corporateNo);
    }
    
    
}
