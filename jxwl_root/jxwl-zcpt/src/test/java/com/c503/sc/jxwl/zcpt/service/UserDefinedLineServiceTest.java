/**
 * 文件名：IUserDefinedServiceTest.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2017-4-13
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
import com.c503.sc.jxwl.zcpt.bean.UserDefinedLine;
import com.c503.sc.jxwl.zcpt.dao.IUserDefinedLineDao;
import com.c503.sc.utils.basetools.C503StringUtils;

/**
 * 
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * 
 * @author guqh
 * @version [版本号, 2017-4-13]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class UserDefinedLineServiceTest extends BaseTest {
    @InjectMocks
    @Autowired
    private IUserDefinedLineService userDefinedLineService;
    
    @Mock
    @Resource(name = "userDefinedLineDao")
    private IUserDefinedLineDao userDefinedLineDao;
    
    UserDefinedLine userDefinedLine = null;
    
    String pk = null;
    
    @Before
    public void setUp()
        throws Exception {
        MockitoAnnotations.initMocks(this);
        pk = C503StringUtils.createUUID();
        userDefinedLine =
            new UserDefinedLine(pk, C503StringUtils.createUUID(),
                "[55.0,56.2]", "not in", null, null, null, null);
        
        try {
            this.userDefinedLineService.saveUserDefinedLine(userDefinedLine);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void testDeleteUserDefinedLine() {
        try {
            this.userDefinedLineService.deleteUserDefinedLine(pk);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void testUpdateUserDefinedLine() {
        try {
            this.userDefinedLineService.updateUserDefinedLine(userDefinedLine);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void testFindUserDefinedLines() {
        try {
            this.userDefinedLineService.findUserDefinedLines();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void testFindUserDefinedLineById() {
        try {
            this.userDefinedLineService.findUserDefinedLineById(pk);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
