/**
 * 文件名：AccessPlatformServiceTest.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-7-26 
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.vehiclemonitor.service;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.springframework.beans.factory.annotation.Autowired;

import com.c503.sc.jxwl.common.constant.DictConstant;
import com.c503.sc.jxwl.vehiclemonitor.base.BaseTest;
import com.c503.sc.jxwl.vehiclemonitor.bean.AccountVerifyEntity;
import com.c503.sc.jxwl.vehiclemonitor.dao.IAccountVerifyDao;


public class AccountVerifyServiceTest extends BaseTest{
    
    @InjectMocks
    @Autowired
	private IAccountVerifyService accountVerifyService;
    
    @Mock
    @Autowired
    private IAccountVerifyDao accountVerifyDao;
    
    @Before
    public void setup()throws Exception {
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void testRegister() throws Exception {
    	AccountVerifyEntity entity = new AccountVerifyEntity();
    	entity.setAccount("wang");
    	entity.setPassword("123d");
    	entity.setRegisterId("ddfdfdf");
    	this.accountVerifyService.register(entity);
    }
    @Test
    public void testFindOccupationPerson() throws Exception {
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("personName", "dddd");
    	this.accountVerifyService.findOccupationPerson(map);
    }
    @Test
    public void testFindRegisterInfo() throws Exception {
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("account", "dddd");
    	this.accountVerifyService.findRegisterInfo(map);
    }
    @Test
    public void testFindByParams() throws Exception {
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("account", "dddd");
    	this.accountVerifyService.findByParams(map);
    }
    @Test
    public void testFindByParameter() throws Exception {
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("account", "dddd");
    	this.accountVerifyService.findByParameter(map);
    }
    @Test
    public void testVerify() throws Exception {
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("verifyStatus", "100002");
    	this.accountVerifyService.verify(map);
    }
    @Test
    public void testCancel() throws Exception {
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("account", "dddd");
    	this.accountVerifyService.cancel(map);
    }
    @Test
    public void testUpdate() throws Exception {
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("personName", "dd");
    	map.put("account", "dddd");
    	map.put("occupationId", "2333442");
    	map.put("registerId", "2333442");
    	this.accountVerifyService.update(map);
    }
    @Test
    public void testAmount() throws Exception {
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("corporateNo", "111111");
		map.put("verifyStatus", DictConstant.ACCOUNT_VERIFY_WAIT);
    	this.accountVerifyService.amount(map);
    }
    @Test
    public void testFindBizUser() throws Exception {
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("account", "jsy");
		map.put("sysId", DictConstant.SYS_ID);
    	this.accountVerifyService.findBizUser(map);
    }
    
    @Test
    public void testisExist() throws Exception {
        Map<String, Object>  map = new HashMap<String, Object>();
        Map<String, String> result = null;
        PowerMockito.when(this.accountVerifyDao.isExist(map)).thenReturn(
            result);
        this.accountVerifyService.isExist(map);
        result = new HashMap<String, String>();
        String[] IDValues = {"id",null};
        String[] NOValues = {"no",null};
        for(String id:IDValues){
            result.put("ID", id);
             for(String no:NOValues){
                 result.put("NO", no);
                 PowerMockito.when(this.accountVerifyDao.isExist(map)).thenReturn(
                     result);
                 this.accountVerifyService.isExist(map);
             }
        }
    }
    
}
