/**
 * 文件名：IOrganizationServiceTest.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2015年10月28日
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.orgdata.service;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.c503.sc.jxwl.orgdata.base.BaseTest;
import com.c503.sc.jxwl.orgdata.bean.SysOrganTypeEntity;
import com.c503.sc.jxwl.orgdata.dao.ISysOrganTypeDao;
import com.c503.sc.utils.basetools.C503StringUtils;

/**
 * 〈一句话功能简述〉机构操作测试类
 * 〈功能详细描述〉
 * 
 * @author luocb
 * @version [版本号, 2015年12月31日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class IOrganizationServiceTest extends BaseTest {
    
    @Resource(name = "sysOrgTypeService")
    private ISysOrgTypeService sysOrgTypeService;
    
    /**
     * 对认证系统企业机构信息进行增删改查操作
     */
    @Resource(name = "sysOrganTypeDao")
    private ISysOrganTypeDao sysOrganTypeDao;
    
    public SysOrganTypeEntity topEntity = new SysOrganTypeEntity();
    
    @Before
    public void setup()
        throws Exception {
        topEntity = init(topEntity);
        
    }
    
    @Test
    public void testFindEntityByCode() {
        String code = null;
        try {
            code = this.sysOrgTypeService.findEnCodeByOrgId(topEntity.getId());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        Assert.assertNotNull(code);
    }
    
    @Test
    public void testfindUserRoleByUserId() {
        List<String> code = null;
        try {
            code =
                this.sysOrgTypeService.findUserRoleByUserId(topEntity.getCreateBy());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        // TODO 说明,需要模拟保存用户信息。暂时不模拟，提供信息就行
        Assert.assertTrue(code.size() == 0);
    }
    
    @Test
    public void saveEntiy() {
        SysOrganTypeEntity entity = new SysOrganTypeEntity();
        entity.setId(C503StringUtils.createUUID());
        entity.setCode(C503StringUtils.createUUID());
        // 4.政府，5.化工，6，物流企业
        entity.setOrganTypeId("106001002");
        entity.setName("盛通物流");
        // 物流系系统id为6
        entity.setSysId("6");
        entity.setPid("1000000");
        int i = 0;
        try {
            i = this.sysOrgTypeService.updateOrSave(entity);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        entity.setOrganTypeId("106001002");
        
        int j = 0;
        try {
            j = this.sysOrgTypeService.updateOrSave(entity);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        Assert.assertEquals(1, j);
        Assert.assertEquals(1, i);
        
    }
    
    public SysOrganTypeEntity init(SysOrganTypeEntity entity) {
        entity.setId(C503StringUtils.createUUID());
        entity.setCode(C503StringUtils.createUUID());
        // 4.政府，5.化工，6，物流企业
        entity.setOrganTypeId("106001002");
        entity.setName("盛通物流");
        // 物流系系统id为6
        entity.setSysId("6");
        entity.setPid("1000000");
        entity.setRemove("0");
        this.sysOrganTypeDao.save(entity);
        return entity;
    }
}
