/**
 * 文件名：UserDefinedAreaTest.java
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
import com.c503.sc.jxwl.zcpt.bean.UserDefinedArea;
import com.c503.sc.jxwl.zcpt.dao.IUserDefinedAreaDao;
import com.c503.sc.utils.basetools.C503StringUtils;

/**
 * 
 * 〈一句话功能简述〉自定义区域
 * 〈功能详细描述〉
 * 
 * @author guqh
 * @version [版本号, 2017-3-30]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class UserDefinedAreaServiceTest extends BaseTest {
    @InjectMocks
    @Autowired
    private IUserDefinedAreaService userDefinedAreaService;
    
    @Mock
    @Resource(name = "userDefinedAreaDao")
    private IUserDefinedAreaDao userDefinedAreaDao;
    
    UserDefinedArea userDefinedArea = null;
    
    String pk = null;
    
    @Before
    public void setUp()
        throws Exception {
        MockitoAnnotations.initMocks(this);
        pk = C503StringUtils.createUUID();
        userDefinedArea =
            new UserDefinedArea(pk, C503StringUtils.createUUID(),
                "[55.0,56.2]", "not in");
        
        try {
            this.userDefinedAreaService.saveUserDefinedArea(userDefinedArea);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void testDeleteUserDefinedArea() {
        try {
            this.userDefinedAreaService.deleteUserDefinedArea(pk);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void testUpdateUserDefinedArea() {
        try {
            this.userDefinedAreaService.updateUserDefinedArea(userDefinedArea);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void testFindUserDefinedAreas() {
        try {
            this.userDefinedAreaService.findUserDefinedAreas();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void testFindUserDefinedAreaById() {
        try {
            this.userDefinedAreaService.findUserDefinedAreaById(pk);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
