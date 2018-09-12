/**
 * 文件名：UserDefinedFacilityServiceTest.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2017-3-30
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.zcpt.service;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import com.c503.sc.jxwl.zcpt.base.BaseTest;
import com.c503.sc.jxwl.zcpt.bean.UserDefinedFacility;
import com.c503.sc.jxwl.zcpt.dao.IUserDefinedFacilityDao;
import com.c503.sc.utils.basetools.C503StringUtils;

/**
 * 
 * 〈一句话功能简述〉自定义设施
 * 〈功能详细描述〉
 * 
 * @author guqh
 * @version [版本号, 2017-3-30]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class UserDefinedFacilityServiceTest extends BaseTest {
    @InjectMocks
    @Autowired
    private IUserDefinedFacilityService userDefinedFacilityService;
    
    @Mock
    @Resource(name = "userDefinedFacilityDao")
    private IUserDefinedFacilityDao userDefinedFacilityDao;
    
    UserDefinedFacility userDefinedFacility = null;
    
    String pk = null;
    
    @Before
    public void setUp()
        throws Exception {
        MockitoAnnotations.initMocks(this);
        pk = C503StringUtils.createUUID();
        userDefinedFacility =
            new UserDefinedFacility(pk, C503StringUtils.createUUID(),
                "[55.0,56.2]", "not in", pk, null, null);
        
        try {
            this.userDefinedFacilityService.saveUserDefinedFacility(userDefinedFacility);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void testDeleteUserDefinedFacility() {
        try {
            this.userDefinedFacilityService.deleteUserDefinedFacility(pk);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void testUpdateUserDefinedFacility() {
        try {
            this.userDefinedFacilityService.updateUserDefinedFacility(userDefinedFacility);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void testFindUserDefinedFacilitys() {
        try {
            this.userDefinedFacilityService.findUserDefinedFacilitys();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void testFindUserDefinedFacilityById() {
        try {
            this.userDefinedFacilityService.findUserDefinedFacilityById(pk);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
