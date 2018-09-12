/**
 * 文件名：UserDefinedFacilityServiceImpl.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2017-3-30
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.zcpt.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.c503.sc.jxwl.zcpt.bean.UserDefinedFacility;
import com.c503.sc.jxwl.zcpt.dao.IUserDefinedFacilityDao;
import com.c503.sc.jxwl.zcpt.service.IUserDefinedFacilityService;

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
@Service(value = "userDefinedFacilityService")
public class UserDefinedFacilityServiceImpl implements
    IUserDefinedFacilityService {
    /** 自定义设施dao */
    @Resource(name = "userDefinedFacilityDao")
    private IUserDefinedFacilityDao userDefinedFacilityDao;
    
    @Override
    public int saveUserDefinedFacility(UserDefinedFacility userDefinedFacility)
        throws Exception {
        this.userDefinedFacilityDao.saveUserDefinedFacility(userDefinedFacility);
        return 1;
    }
    
    @Override
    public int deleteUserDefinedFacility(String id)
        throws Exception {
        this.userDefinedFacilityDao.deleteUserDefinedFacility(id);
        return 1;
    }
    
    @Override
    public List<UserDefinedFacility> findUserDefinedFacilitys()
        throws Exception {
        List<UserDefinedFacility> definedFacility =
            this.userDefinedFacilityDao.findUserDefinedFacilitys();
        return definedFacility;
    }
    
    @Override
    public UserDefinedFacility findUserDefinedFacilityById(String id)
        throws Exception {
        UserDefinedFacility definedFacility =
            this.userDefinedFacilityDao.findUserDefinedFacilityById(id);
        return definedFacility;
    }
    
    @Override
    public int updateUserDefinedFacility(UserDefinedFacility userDefinedFacility)
        throws Exception {
        this.userDefinedFacilityDao.updateUserDefinedFacility(userDefinedFacility);
        return 1;
    }
    
}
