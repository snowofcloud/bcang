/*
 * 文件名：AlarmManageServiceTest.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-7-25
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.vehiclemonitor.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.c503.sc.base.entity.Page;
import com.c503.sc.jxwl.common.bean.AlarmEntity;
import com.c503.sc.jxwl.vehiclemonitor.base.BaseTest;
import com.c503.sc.jxwl.zcpt.service.IAlarmManageService;
import com.c503.sc.jxwl.zcpt.vo.AlarmThresholdVo;

public class AlarmManageServiceTest extends BaseTest {
    @Autowired
    private IAlarmManageService alarmManageService;
    
    String pk = null;
    
    AlarmEntity entity = new AlarmEntity();
    
    @Before
    public void setUp()
        throws Exception {
		 entity.setAlarmDetails("报警详情");
		entity.setCorporateNo("11111");
		 entity.setAlarmNo("507");
		 entity.setAlarmDate(new Date());
		 entity.setRemove("0");
		
		 pk = (String) this.alarmManageService.save(entity);
    }
    
    @Test
    public void testFindById()
        throws Exception {
    	AlarmEntity enenrgyManager = this.alarmManageService.findById(pk);
        Assert.assertNotNull(enenrgyManager);
    }
    
    @Test
    public void testFindByParams()
        throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("remove", "0");
        List<AlarmEntity> enterprise =
            this.alarmManageService.findByParams(map);
        Assert.assertNotNull(enterprise);
        
    }
    
    @Test
    public void testUpdate()
        throws Exception {
        entity.setAlarmDetails("报警详情555");
        entity.setCorporateNo("22222");
        entity.setAlarmNo("509");
        entity.setAlarmDate(new Date());
        this.alarmManageService.update(entity);
    }
    /*@Test
    public void testDeleteAlarm()
        throws Exception {   	
		AlarmEntity entity=new AlarmEntity();
		entity.setCorporateNo("22222");
		entity.setAlarmDate(new Date());
		entity.setAlarmType("区域报警");
		entity.setCarrierName("川AFX123");
		entity.setAlarmNo("511");
		Object i=this.alarmManageService.save(entity);
		System.out.println("i:"+i);
    }*/
    
    @Test
    public void testUpdateThreshold()
        throws Exception {
        AlarmThresholdVo entity =new AlarmThresholdVo();
        entity.setId("sssssssss");
        BigDecimal aa = new BigDecimal(1111);
        entity.setFatigueDriveValue(aa);
        entity.setOverSpeedValue(aa);
        entity.setOvertimeParkValue(aa);
        entity.setFatigueDriveValue(aa);
        this.alarmManageService.updateThreshold(entity);
    }
    
    @Test
    public void testFindThresholdById()
        throws Exception {
         Map<String, Object> map = new HashMap<>();
        this.alarmManageService.findThresholdById(map);
    }
    
    @Test
    public void testDelete()
        throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("updateBy", "1");
        map.put("updateTime", new Date());
        map.put("remove", "1");
        map.put("ids", new String[] {"1"});
        this.alarmManageService.delete(map);
    }
    
    
    @Test
    public void testExportExcel()
        throws Exception {
        // 有数据
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> recvMap = this.alarmManageService.exportExcel(map);
        Assert.assertNotNull(recvMap);
    }
    @Test
    public void testFindByAccount()
        throws Exception {
        // 有数据
    	String account ="1234e";
    	this.alarmManageService.findByAccount(account);
    }
    
    @Test
    public void testFindAlarmForDiTu()
        throws Exception {
    	 Map<String, Object> map = new HashMap<>();
         map.put("carrierName", "黑A12345");
         Page pageEntity = new Page();
         pageEntity.setCurrentPage(1);
         pageEntity.setPageSize(10);
         map.put("page", pageEntity);
    	this.alarmManageService.findAlarmForDiTu(map);
    }
    
    @Test
    public void testfindAlarmNumForDiTu()
        throws Exception {
    	 Map<String, Object> map = new HashMap<>();
         map.put("carrierName", "黑A12345");
    	this.alarmManageService.findAlarmNumForDiTu(map);
    }
}
