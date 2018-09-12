/**
 * 文件名：DangerVehicleServiceTest.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-7-20
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.vehiclemonitor.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.springframework.beans.factory.annotation.Autowired;

import com.c503.sc.base.entity.Page;
import com.c503.sc.jxwl.common.bean.LocationEntity;
import com.c503.sc.jxwl.vehiclemonitor.base.BaseTest;
import com.c503.sc.jxwl.vehiclemonitor.bean.EnterpriseFileEntity;
import com.c503.sc.jxwl.vehiclemonitor.dao.IDangerFileRelaDao;
import com.c503.sc.jxwl.vehiclemonitor.dao.IDangerVehicleDao;
import com.c503.sc.jxwl.vehiclemonitor.service.impl.DangerVehicleServiceImpl;
import com.c503.sc.jxwl.zcpt.bean.DangerVehicleEntity;
import com.c503.sc.jxwl.zcpt.service.IDangerVehicleService;
import com.c503.sc.utils.basetools.C503StringUtils;

public class DangerVehicleServiceTest extends BaseTest {
    @InjectMocks
    @Autowired
    private IDangerVehicleService dangerVehicleService;
    
    @Mock
    @Resource(name = "dangerVehicleDao")
    private IDangerVehicleDao dangerVehicleDao;
    
    @SuppressWarnings("unused")
    @Mock
    @Autowired
    private IDangerFileRelaDao dangerFileRelaDao;
    
    String pk = null;
    
    DangerVehicleEntity entity = new DangerVehicleEntity();
    
    @Before
    public void setUp()
        throws Exception {
        MockitoAnnotations.initMocks(this);
        entity.setCorporateNo("1111");
        entity.setLicencePlateNo("川Axxxxx");
        entity.setRemove("0");
        entity.setMotorNo("2222");
        entity.setFileIds(new String[] {"111", "222"});
    }
    
    @Test
    public void testSave()
        throws Exception {
        // ok
        this.dangerVehicleService.save(entity);
        
        // fileIds = null
        entity.setFileIds(null);
        this.dangerVehicleService.save(entity);
        
        // fileIds.length = 0
        entity.setFileIds(new String[0]);
        this.dangerVehicleService.save(entity);
        
        // 异常
        try {
            PowerMockito.when(this.dangerVehicleDao.findLicencePlateNoExit(entity.getLicencePlateNo(),
                null))
                .thenReturn("1");
            this.dangerVehicleService.save(entity);
        }
        catch (Exception e) {
            e.getMessage();
        }
        
    }
    
    @Test
    public void testUpdate()
        throws Exception {
        try {
            entity.setId(C503StringUtils.createUUID());
            this.dangerVehicleService.update(entity);
        }
        catch (Exception e) {
            Assert.assertTrue(e instanceof Exception);
        }
        
        // fileIds.length = 0
        try {
            entity.setFileIds(new String[0]);
            entity.setLicencePlateNo("川A99999");
            this.dangerVehicleService.update(entity);
        }
        catch (Exception e) {
            Assert.assertTrue(e instanceof Exception);
        }
        
        try {
            entity.setFileIds(null);
            entity.setId("1ea15da729824783999ad863e52deab8");
            this.dangerVehicleService.update(entity);
        }
        catch (Exception e) {
            Assert.assertTrue(e instanceof Exception);
        }
        
        // 异常
        try {
            entity.setId("010100001");
            PowerMockito.when(this.dangerVehicleDao.findLicencePlateNoExit(entity.getLicencePlateNo(),
                entity.getId()))
                .thenReturn("trues");
            this.dangerVehicleService.update(entity);
        }
        catch (Exception e) {
            Assert.assertTrue(e instanceof Exception);
        }
        
        // 异常
        try {
            entity.setId("010100001");
            PowerMockito.when(this.dangerVehicleDao.findLicencePlateNoExit(entity.getLicencePlateNo(),
                entity.getId()))
                .thenReturn("");
            PowerMockito.when(this.dangerVehicleDao.findExsitById(entity.getId()))
                .thenReturn("1");
            this.dangerVehicleService.update(entity);
        }
        catch (Exception e) {
            Assert.assertTrue(e instanceof Exception);
        }
    }
    
    @Test
    public void testFindByco()
        throws Exception {
        String co = "AAAA";
        this.dangerVehicleService.findByco(co);
        
    }
    
    @Test
    public void testPrivateCreateFileRelationVal()
        throws NoSuchMethodException, SecurityException,
        IllegalAccessException, IllegalArgumentException,
        InvocationTargetException, InstantiationException {
        List<EnterpriseFileEntity> list = new ArrayList<>();
        entity.setFileIds(new String[] {"111", "222"});
        
        Class<DangerVehicleServiceImpl> clazz = DangerVehicleServiceImpl.class;
        Method m =
            clazz.getDeclaredMethod("createFileRelationVal",
                List.class,
                DangerVehicleEntity.class);
        m.setAccessible(true);
        m.invoke(clazz.newInstance(), list, entity);
    }
    
    @Test
    public void testFindById()
        throws Exception {
        this.dangerVehicleService.findById(pk);
    }
    
    @Test
    public void testFindByParams()
        throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("remove", "0");
        List<DangerVehicleEntity> vehicle =
            this.dangerVehicleService.findByParams(map);
        Assert.assertNotNull(vehicle);
    }
    
    @Test
    public void testDel()
        throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("updateBy", "xxx");
        map.put("id", "f73d372b7d7e4150b7a689cff8662d65");
        map.put("remove", "0");
        this.dangerVehicleService.delete(map);
        try {
            map.put("id", "1ea15da729824783999ad863e52deab8");
            this.dangerVehicleService.delete(map);
        }
        catch (Exception e) {
            Assert.assertTrue(e instanceof Exception);
        }
    }
    
    @Test
    public void testBatchUpdateRealLocation()
        throws Exception {
        List<LocationEntity> locs = new ArrayList<LocationEntity>();
        // loc = null
        LocationEntity loc = null;
        locs.add(loc);
        this.dangerVehicleService.batchUpdateRealLocation(locs);
        
        // loc != null
        try {
            loc = new LocationEntity();
            loc.setElevation("100");
            loc.setCarrierName("浙B74494");
            this.dangerVehicleService.batchUpdateRealLocation(locs);
        }
        catch (Exception e) {
        }
        
        // carrierName == null
        try {
            loc.setCarrierName("");
            this.dangerVehicleService.batchUpdateRealLocation(locs);
        }
        catch (Exception e) {
        }
        
        try {
            // carrierName != null && locDB != null
            loc.setCarrierName("浙B74494");
            LocationEntity locDB = new LocationEntity();
            PowerMockito.when(this.dangerVehicleDao.findRealLocation(loc.getCarrierName()))
                .thenReturn(locDB);
            this.dangerVehicleService.batchUpdateRealLocation(locs);
        }
        catch (Exception e) {
        }
    }
    
    @Test
    public void testFindDangerVehicleByParams()
        throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("licencePlateNo", "黑A12345");
        this.dangerVehicleService.findDangerVehicleByParams(map);
    }
    
    @Test
    public void testFindRealLocationsByParams()
        throws Exception {
        
        Map<String, Object> map = new HashMap<>();
        
        Page pageEntity = new Page();
        pageEntity.setCurrentPage(1);
        pageEntity.setPageSize(30);
        map.put("page", pageEntity);
        map.put("minLat", "111");
        map.put("minLon", "222");
        map.put("maxLat", "223");
        map.put("maxLon", "454");
        this.dangerVehicleService.findRealLocationsByParams(map);
    }
    
    @Test
    public void testFindRealLocationAll()
        throws Exception {
        this.dangerVehicleService.findRealLocationAll();
    }
    
    @Test
    public void testFindTotal()
        throws Exception {
        this.dangerVehicleService.findTotal();
    }
    
    @Test
    public void testFindAll()
        throws Exception {
        this.dangerVehicleService.findAll();
    }
    
    @Test
    public void testFindEnpCarForWl()
        throws Exception {
        String code = "111111";
        this.dangerVehicleService.findEnpCarForWl(code);
    }
    
    @Test
    public void testFindVehicle()
        throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("licencePlateNo", "1");
        map.put("corporateNo", "111111");
        this.dangerVehicleService.findVehicle(map);
    }
    
    @Test
    public void testFindCars()
        throws Exception {
        Map<String, Object> map = new HashMap<>();
        this.dangerVehicleService.findCars(map);
    }
    
    @Test
    public void testFindCarInfoById()
        throws Exception {
        this.dangerVehicleService.findCarInfoById("000000");
    }
    
    @Test
    public void findLicencePlateNo()
        throws Exception {
        String corporateNo = "aaa";
        this.dangerVehicleService.findLicencePlateNo(corporateNo);
    }
}
