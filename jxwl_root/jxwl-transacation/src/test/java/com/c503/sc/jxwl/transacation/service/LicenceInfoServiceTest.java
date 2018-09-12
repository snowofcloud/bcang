/**
 * 文件名：TrainingServiceTest.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-7-13
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.transacation.service;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
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

import com.c503.sc.filemanage.bean.FileInfoEntity;
import com.c503.sc.filemanage.dao.IFileManageDao;
import com.c503.sc.jxwl.transacation.base.BaseTest;
import com.c503.sc.jxwl.transacation.bean.LicenceEntity;
import com.c503.sc.jxwl.transacation.bean.LicenceFileRelaEntity;
import com.c503.sc.jxwl.transacation.dao.ILicenceFileRelaDao;
import com.c503.sc.jxwl.transacation.dao.ILicenceInfoDao;
import com.c503.sc.jxwl.transacation.service.impl.LicenceInfoServiceImpl;

public class LicenceInfoServiceTest extends BaseTest {
    @InjectMocks
    @Autowired
    private ILicenceInfoService licenceInfoService;
    
    Class<LicenceInfoServiceImpl> licenceInfoServiceClass =
        LicenceInfoServiceImpl.class;
    
    @Mock
    @Autowired
    private ILicenceInfoDao licenceInfoDao;
    
    @Mock
    @Autowired
    private ILicenceFileRelaDao licenceFileRelaDao;
    @Mock
    @Autowired
    private IFileManageDao fileManageDao;
    
    @Before
    public void setUp()
        throws Exception {
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void testFindByparams()
        throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("enterpriseName", "111");
        map.put("licenceType", "car");
        map.put("carNo", "川A12345");
        map.put("personName", "");
        this.licenceInfoService.findByParams(map);
    }
    
    @Test
    public void testUpdate()
        throws Exception {
        LicenceEntity entity = new LicenceEntity();
        String action = "update";
        // action为update
        // fileIds字段为null
        entity.setId("2eaaa9d0571844fda2211119d5ee06fb");
        entity.setLicenceType("car");
        entity.setCorporateNo("111111");
        entity.setLicencePlateNo("折A12345");
        entity.setFileIds(null);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("corporateNo", "111111");
        map.put("licencePlateNo","折A12345");
        PowerMockito.when(this.licenceInfoDao.findCar4match(map))
            .thenReturn("yes");
        
        try {
            this.licenceInfoService.update(entity, action);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof Exception);
        }
        // fileIds字段为空数组
        String[] fileIds = {};
        entity.setFileIds(fileIds);
        try {
            this.licenceInfoService.update(entity, action);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof Exception);
        }
        // fileIds字段为非空数组
        String[] fileId = {"111", "3333"};
        entity.setFileIds(fileId);
        try {
            this.licenceInfoService.update(entity, action);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof Exception);
        }
        
        // aciton不为update
        action = "verify";
        try {
            this.licenceInfoService.update(entity, action);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof Exception);
        }
    }
    
    @Test
    public void testFindById()
        throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        String licenceType = "licenceType";
        String id = "id";
        map.put("id", id);
        map.put("licenceType",licenceType);
        //构造result结果值
        LicenceEntity resultNULL = null;
        LicenceEntity resultNotNULL = new LicenceEntity();
        LicenceEntity[] entitys = new LicenceEntity[]{resultNULL,resultNotNULL};
        for(LicenceEntity entity:entitys){
            PowerMockito.when(this.licenceInfoDao.findById(map)).thenReturn(
                entity);
            try {
                this.licenceInfoService.findById(licenceType, id);
            } catch (Exception e) {
                Assert.assertTrue(e instanceof Exception);
            }
        }
        //构造返回值
        List<ArrayList<String>> fileIdList = new ArrayList<ArrayList<String>>();
        List<String> fileIdNull = new ArrayList<String>();
        List<String> fileIdNotNull = new ArrayList<String>();
        fileIdNotNull.add("id1");
        fileIdNotNull.add("id2");
        fileIdList.add((ArrayList<String>) fileIdNull);
        fileIdList.add((ArrayList<String>) fileIdNotNull);
        
        
        List<ArrayList<FileInfoEntity>> fileInfoEntities = new ArrayList<ArrayList<FileInfoEntity>>();
        ArrayList<FileInfoEntity> fileNotNull = new ArrayList<FileInfoEntity>();
        FileInfoEntity file = new FileInfoEntity();
        fileNotNull.add(file);
        
        for(ArrayList<String> fileId :fileIdList){
            PowerMockito.when(this.licenceFileRelaDao.findFileIdById(id)).thenReturn(
                fileId);
            String[] fileIdsArray = new String[fileId.size()];
            fileId.toArray(fileIdsArray);
            Map<String, Object> map1 = new HashMap<>();
            map1.put("ids", fileIdsArray);
            map1.put("remove", "0");
            for(ArrayList<FileInfoEntity> fileInfo:fileInfoEntities){
                PowerMockito.when(this.fileManageDao.findByIds(map1)).thenReturn(
                    fileInfo);
            }
            try {
                this.licenceInfoService.findById(licenceType, id);
            } catch (Exception e) {
                Assert.assertTrue(e instanceof Exception);
            }
        }
        
        
        /*
        // 查询结果为空的情况
        String id = "111";
        String licenceType = "car";
        try {
            this.licenceInfoService.findById(licenceType, id);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof Exception);
        }
        // 有查询结果不为空的情况 且 有附件的情况
        id = "2eaaa9d0571844fda2211119d5ee06fb";
        this.licenceInfoService.findById(licenceType, id);
        
        // 有查询结果不为空的情况 且 没有附件的情况
        id = "189888ec94044012aede5b5e84ce6082";
        //this.licenceInfoService.findById(licenceType, id);
        try {
            this.licenceInfoService.findById(licenceType, id);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof Exception);
        }*/
    }
    
    @Test
    public void testSave()
        throws Exception {
        LicenceEntity entity = new LicenceEntity();
        entity.setLicenceType("car");
        entity.setCorporateNo("111111");
        entity.setLicencePlateNo("这C12345");
        // fileIds字段为null
        entity.setFileIds(null);
        try {
            this.licenceInfoService.save(entity);
        } catch (Exception e) {
        }
        // fileIds字段为空数组
        String[] fileIds = {};
        entity.setFileIds(fileIds);
        try {
            this.licenceInfoService.save(entity);
        } catch (Exception e) {
            // TODO: handle exception
        }
        // fileIds字段为非空数组
        String[] fileId = {"111", "3333"};
        entity.setFileIds(fileId);
        try {
            this.licenceInfoService.save(entity);
        } catch (Exception e) {
        }
    }
    
    @Test
    public void testCreateFileRelationVal()
        throws Exception {
        List<LicenceFileRelaEntity> licenceRelaVals =
            new ArrayList<LicenceFileRelaEntity>();
        LicenceEntity entity = new LicenceEntity();
        // fileIds字段为null
        entity.setFileIds(null);
        try {
            this.licenceInfoService.createFileRelationVal(licenceRelaVals,
                entity);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof Exception);
        }
        
        // fileIds字段为空数组
        String[] fileIds = {};
        entity.setFileIds(fileIds);
        this.licenceInfoService.createFileRelationVal(licenceRelaVals, entity);
        // fileIds字段为非空数组
        String[] fileId = {"111", "3333"};
        entity.setFileIds(fileId);
        this.licenceInfoService.createFileRelationVal(licenceRelaVals, entity);
    }
    
    @Test
    public void testDelete()
        throws Exception {
        String id = "2eaaa9d0571844fda2211119d5ee06fb";
        this.licenceInfoService.delete(id);
    }
    
    @Test
    public void testFindPerson()
        throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("personName", "11");
        map.put("corporateNo", "111111");
        this.licenceInfoService.findPerson(map);
    }
    
    @Test
    public void testFindEnterprise()
        throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("corporateNo", "111111");
        this.licenceInfoService.findEnterprise(map);
    }
    
    @Test
    public void testFindFileId()
        throws Exception {
        String id = "2eaaa9d0571844fda2211119d5ee06fb";
        Method m =
            licenceInfoServiceClass.getDeclaredMethod("findFileId",
                String.class);
        m.setAccessible(true);
        m.invoke(this.licenceInfoService, id);
    }
    
    @Test
    public void testCheck()
        throws Exception {
        
        Map<String, Object> map = new HashMap<>();
        map.put("personName", null);
        map.put("corporateNo", null);
        map.put("identificationCardNo", null);
        map.put("licencePlateNo", null);
        String[] licenceTypeValues = new String[]{"car","person"};
        LicenceEntity entity = new LicenceEntity();
        
        String[] findCar = new String[]{"car",null};
        String[] findPerson = new String[]{"person",null};
        for(String licenceType: licenceTypeValues){
            entity.setLicenceType(licenceType);
            for(String car:findCar){
                PowerMockito.when(this.licenceInfoDao.findCar4match(map)).thenReturn(
                    car);
                for(String person:findPerson){
                    PowerMockito.when(this.licenceInfoDao.findPerson4match(map)).thenReturn(
                        person);
                    try {
                        this.licenceInfoService.check(entity);
                    } catch (Exception e) {
                        Assert.assertTrue(e instanceof Exception);
                    }
                }
            }
            
        }
        
        
        /*// 情况1：car
        // 情况1.1 car 车辆匹配
        entity.setLicenceType("car");
        entity.setCorporateNo("111111");
        entity.setLicencePlateNo("黑A12345");
        this.licenceInfoService.check(entity);
        // 情况1.2 car 车辆不匹配
        entity.setLicencePlateNo("来A00000");
        try {
            this.licenceInfoService.check(entity);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof Exception);
        }
        
        // 情况2：person
        // 情况2.1 person 人员匹配
        entity.setLicenceType("person");
        entity.setPersonName("单元测试不可动");
        entity.setIdentificationCardNo("610122139009094014");
        try {
            this.licenceInfoService.check(entity);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof Exception);
        }
        // 情况2.2 person 人员不匹配
        entity.setIdentificationCardNo("123445667677777");
        try {
            this.licenceInfoService.check(entity);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof Exception);
        }
        
        // 情况3：anything
        entity.setLicenceType("enterprise");
        this.licenceInfoService.check(entity);
        */
    }
    
    @Test
    public void testisExit()
        throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("licenceType", "enterprise");
        LicenceEntity entityNull = null;
        LicenceEntity entityNotNULL = new  LicenceEntity();
        LicenceEntity[] entitys = new LicenceEntity[]{entityNull,entityNotNULL};
        for(LicenceEntity e:entitys){
            PowerMockito.when(this.licenceInfoDao.isExit(map)).thenReturn(
                e);
            this.licenceInfoService.isExit(map);
        }
        
        String[] licenceTypeValues = new String[]{"enterprise","car","person"};
        String[] corporateNoValues = new String[]{null,"111111"};
        String[] licencePlateNoValues = new String[]{null,"川A12345"};
        String[] identificationCardNoValues = new String[]{null,"12324343432432432433234"};
        for(String licenceTypeValue: licenceTypeValues){
            map.put("licenceType", licenceTypeValue);
            for(String corporateNoValue:corporateNoValues){
                entityNotNULL.setCorporateNo(corporateNoValue);
                for(String licencePlateNoValue:licencePlateNoValues){
                    entityNotNULL.setLicencePlateNo(licencePlateNoValue);
                    for(String identificationCardNoValue:identificationCardNoValues){
                        entityNotNULL.setIdentificationCardNo(identificationCardNoValue);
                        PowerMockito.when(this.licenceInfoDao.isExit(map)).thenReturn(
                            entityNotNULL);
                        this.licenceInfoService.isExit(map);
                    }
                }
            }
        }
        
       /* //entity = null
       
        LicenceEntity entity = null;
        PowerMockito.when(this.licenceInfoDao.isExit(map)).thenReturn(
            entity);
        this.licenceInfoService.isExit(map);
        //entity != null 
        //licenceType = enterprise
        //entity.getCorporateNo() = null
        entity = new LicenceEntity();
        PowerMockito.when(this.licenceInfoDao.isExit(map)).thenReturn(
            entity);
        this.licenceInfoService.isExit(map);
        //entity.getCorporateNo() = null
        entity.setCorporateNo("111111");
        PowerMockito.when(this.licenceInfoDao.isExit(map)).thenReturn(
            entity);
        this.licenceInfoService.isExit(map);
        //licenceType = car
       //entity.getCorporateNo() = null
       //entity.getLicencePlateNo() = null 
        entity.setCorporateNo(null);
        entity.setLicencePlateNo(null);
        PowerMockito.when(this.licenceInfoDao.isExit(map)).thenReturn(
            entity);
        this.licenceInfoService.isExit(map);
      //entity.getLicencePlateNo() != null 
        entity.setLicencePlateNo("川A12345");
        PowerMockito.when(this.licenceInfoDao.isExit(map)).thenReturn(
            entity);
        this.licenceInfoService.isExit(map);
        
        //entity.getCorporateNo() != null
        entity.setCorporateNo("11111");
        entity.setLicencePlateNo(null);
        PowerMockito.when(this.licenceInfoDao.isExit(map)).thenReturn(
            entity);
        this.licenceInfoService.isExit(map);
      //entity.getLicencePlateNo() != null 
        entity.setLicencePlateNo("川A12345");
        PowerMockito.when(this.licenceInfoDao.isExit(map)).thenReturn(
            entity);
        this.licenceInfoService.isExit(map);
        */
         
        
    }
    
}