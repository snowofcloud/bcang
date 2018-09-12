/**
 * 文件名：TrainingServiceTest.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-7-13
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.transacation.service;

import java.util.ArrayList;
import java.util.Date;
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

import com.c503.sc.jxwl.common.constant.DictConstant;
import com.c503.sc.jxwl.transacation.base.BaseTest;
import com.c503.sc.jxwl.transacation.bean.LicenceEntity;
import com.c503.sc.jxwl.transacation.dao.ILicenceInfoDao;
import com.c503.sc.jxwl.transacation.dao.ILicenceWarnDao;
import com.c503.sc.utils.common.SystemContants;

public class LicenceWarnServiceTest extends BaseTest {
    @InjectMocks
    @Autowired
    private ILicenceWarnService licenceWarnService;
    
    @Mock
    @Autowired
    private ILicenceWarnDao licenceWarnDao;
    
    @Mock
    @Resource(name = "licenceInfoDao")
    private ILicenceInfoDao licenceInfoDao;
    
    @Before
    public void setUp()
        throws Exception {
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void testFindByparams()
        throws Exception {
        List<LicenceEntity> result = new ArrayList<LicenceEntity>();
        Map<String, Object> map = new HashMap<>();
        map.put("remove", SystemContants.ON);
        map.put("enterpriseName", "nononon");
        PowerMockito.when(this.licenceWarnDao.findByParams(map))
            .thenReturn(result);
        this.licenceWarnService.findByParams(map);
    }
    
    @Test
    public void testUpdate()
        throws Exception {
        MockitoAnnotations.initMocks(this);
        LicenceEntity entity = new LicenceEntity();
        entity.setUpdateTime(new Date());
        entity.setVerifyStatus(DictConstant.LICENCE_VERIFY_WAIT);
        entity.setLicenceType("car");
        entity.setCorporateNo("111111");
        entity.setLicencePlateNo("折A12345");
        entity.setFileIds(null);
        Map<String, Object> map = new HashMap<>();
        map.put("corporateNo", "111111");
        map.put("licencePlateNo", "折A12345");
        String car = "car";
        PowerMockito.when(this.licenceInfoDao.findCar4match(map))
            .thenReturn(car);
        this.licenceWarnService.update(entity);
        
        String[] fileIds = {};
        entity.setFileIds(fileIds);
        this.licenceWarnService.update(entity);
        fileIds = new String[] {"33", "dd"};
        entity.setFileIds(fileIds);
        try {
            this.licenceWarnService.update(entity);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof Exception);
        }
        
        /*
         * LicenceEntity entity = new LicenceEntity();
         * entity.setId("11111");
         * entity.setLicenceType("person");
         * entity.setPersonName("单元测试不可动");
         * entity.setIdentificationCardNo("610122139009094014");
         * entity.setCorporateNo("111111");
         * entity.setRemove("0");
         * //fileIds字段为null
         * entity.setFileIds(null);
         * this.licenceWarnService.update(entity);
         * //fileIds字段为空数组
         * String[] fileIds = {};
         * entity.setFileIds(fileIds);
         * this.licenceWarnService.update(entity);
         * //fileIds字段为非空数组
         * String[] fileId = {"111","3333"};
         * entity.setFileIds(fileId);
         * this.licenceWarnService.update(entity);
         */
        
    }
    
    @Test
    public void testAmount()
        throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("remove", SystemContants.ON);
        this.licenceWarnService.amount(map);
    }
    
}