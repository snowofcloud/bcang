/**
 * 文件名：EmergencyInfoService.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-7-27
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.vehiclemonitor.service;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;

import com.c503.sc.base.entity.Page;
import com.c503.sc.jxwl.vehiclemonitor.base.BaseTest;
import com.c503.sc.jxwl.vehiclemonitor.bean.EmergencyEntity;
import com.c503.sc.jxwl.vehiclemonitor.dao.IEmergencyInfoDao;
import com.c503.sc.jxwl.vehiclemonitor.service.impl.EmergencyInfoServiceImpl;

public class EmergencyInfoServiceTest extends BaseTest {
    
    @InjectMocks
    @Resource(name = "emergencyInfoService")
    private IEmergencyInfoService emergencyInfoService;
    
    @Mock
    @Resource(name = "emergencyInfoDao")
    private IEmergencyInfoDao emergencyInfoDao;
    
    Class<EmergencyInfoServiceImpl>  emergencyInfoServiceImpl = EmergencyInfoServiceImpl.class;
    
    @Before
    public void setup()
        throws Exception {
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void testSave()
        throws Exception {
        EmergencyEntity entity = new EmergencyEntity();
        entity.setContent("从河北至少嘉兴某路段山体滑坡");
        entity.setPublishDate(new Date());
        entity.setPushDate(new Date());
        entity.setSource("新华网");
        entity.setTitle("山体滑坡");
        this.emergencyInfoService.save(entity);
    }
    
    @Test
    public void testFindById()
        throws Exception {
        EmergencyEntity entity = new EmergencyEntity();
        entity.setContent("从河北至少嘉兴某路段山体滑坡");
        entity.setPublishDate(new Date());
        System.out.println("发布时间为：" + entity.getPublishDate());
        entity.setSource("新华网");
        entity.setTitle("山体滑坡");
        List<EmergencyEntity> tempResult = new LinkedList<EmergencyEntity>();
        tempResult.add(entity);
        PowerMockito.when(emergencyInfoDao.findById("1"))
            .thenReturn(tempResult);
        emergencyInfoService.findById("1");
        
        
        tempResult.clear();
        PowerMockito.when(emergencyInfoDao.findById("1"))
            .thenReturn(tempResult);
        emergencyInfoService.findById("1");
    }
    
    @Test
    public void testFindByParams()
        throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        Page page = new Page();
        map.put("page", page);
        java.sql.Date begin = new java.sql.Date(System.currentTimeMillis());
        java.sql.Date end = new java.sql.Date(System.currentTimeMillis());
        map.put("beginTime", begin);
        map.put("endTime", end);
        map.put("remove", "0");
        PowerMockito.when(emergencyInfoDao.findByParams(map)).thenReturn(null);
         emergencyInfoService.findByParams(map);
    }
    
    @Test
    public void testDelete()
        throws Exception {
        EmergencyEntity entity = new EmergencyEntity();
        String id = "1";
        entity.setId(id);
        PowerMockito.when(emergencyInfoDao.delete(entity)).thenReturn(1);
        emergencyInfoService.delete(entity);
    }
    @Test
    public void testMiPushEmergency()
        throws Exception {
    	EmergencyEntity entity = new EmergencyEntity();
        entity.setTitle("title");
        entity.setSource("source");
        entity.setContent("content");
        try {
        	this.emergencyInfoService.miPushEmergency(entity);
		} catch (Exception e) {
		}
        
    }
    
    
    @Test
    public void testGetMsg()
        throws Exception {
        Method m = emergencyInfoServiceImpl.getDeclaredMethod("getMsg", EmergencyEntity.class);
        m.setAccessible(true);
        EmergencyEntity entity = new EmergencyEntity();
        entity.setTitle("title");
        entity.setSource("source");
        entity.setContent("content");
        m.invoke(this.emergencyInfoService, entity);
    }
    
    @Test
    public void testUpdate()
        throws Exception {
    	EmergencyEntity entity = new EmergencyEntity();
    	entity.setContent("从河北至少嘉兴某路段山体滑坡");
        entity.setPublishDate(new Date());
        entity.setPushDate(new Date());
        entity.setSource("新华网");
        entity.setTitle("山体滑坡");
        entity.setSendTimes("10次");
        entity.setSendFrequency("10次");
        entity.setValidTime("10分钟");
        this.emergencyInfoService.update(entity);
    }
}
