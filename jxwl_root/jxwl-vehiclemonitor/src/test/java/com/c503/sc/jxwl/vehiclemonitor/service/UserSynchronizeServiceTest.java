/**
 * 文件名：IUserSynchronizeServiceTest.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2017-5-23
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.vehiclemonitor.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;

import com.c503.sc.jxwl.vehiclemonitor.base.BaseTest;
import com.c503.sc.jxwl.vehiclemonitor.bean.UserSynchronizeEntity;
import com.c503.sc.utils.basetools.C503StringUtils;
import com.c503.sc.utils.common.SystemContants;

public class UserSynchronizeServiceTest extends BaseTest {
    @InjectMocks
    @Autowired
    IUserSynchronizeService userSynchronizeService;
    
    @Test
    public void testSave()
        throws Exception {
        List<UserSynchronizeEntity> list =
            new ArrayList<UserSynchronizeEntity>();
        UserSynchronizeEntity entity = null;
        for (int i = 0; i < 5; i++) {
            entity = new UserSynchronizeEntity();
            entity.setAccount("1" + i);
            entity.setId(C503StringUtils.createUUID());
            list.add(entity);
        }
        this.userSynchronizeService.batchSynchronize(list);
        
    }
    
    @Test
    public void testFindNewUserByParams()
        throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("remove", SystemContants.ON);
        this.userSynchronizeService.findNewUserByParams(map);
        
    }
}
