/**
 * 文件名：IOrganizationServiceTest.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2015年10月28日
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.orgdata.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;

import com.c503.sc.jxwl.orgdata.base.BaseTest;
import com.c503.sc.utils.basetools.C503DateUtils;
import com.c503.sc.utils.basetools.C503StringUtils;
import com.c503.sc.utils.basetools.C503UserUtil;

/**
 * 〈一句话功能简述〉机构操作测试类
 * 〈功能详细描述〉
 * 
 * @author wangwenhuan
 * @version [版本号, 2016年11月25日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class SysUserServiceTest extends BaseTest {
    
	@Resource
	private ISysUserService sysUserService;
    
    
    @Before
    public void setup()throws Exception {
    }
    
    @Test
    public void testSaveSysUser() throws Exception {
    	Map<String, Object> map = new HashMap<String, Object>();
    	String salt = "1209";
    	String password = C503UserUtil.createPassword(
				"111111", "1209");
    	map.put("account","danyuancs");
		map.put("password", password);
		map.put("salt", salt);
		map.put("sysId", "6");
		map.put("id", C503StringUtils.createUUID());
		Date beginTime = new Date();
		Date endTime = C503DateUtils.getDay(360,
				beginTime);
		map.put("beginTime", beginTime);
		map.put("endTime", endTime);
		// T_USER_REL_ROLE
		map.put("userRoleId", C503StringUtils.createUUID());
		map.put("roleId", "b0bf7c2cff8641e68e2aeec2840c483f");
		map.put("userId", C503StringUtils.createUUID());
		int i = this.sysUserService.saveSysUser(map);
		System.out.println(i);
    }
    @Test
    public void testUpdateInfo() throws Exception {
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("account","danyuancs");
		map.put("telephone","12345678902");
		this.sysUserService.updateInfo(map);
    }
    
}
