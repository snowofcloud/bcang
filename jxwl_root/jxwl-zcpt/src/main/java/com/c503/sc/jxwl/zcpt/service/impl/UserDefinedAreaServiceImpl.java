/**
 * 文件名：UserDefinedAreaServiceImpl.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2017-3-30
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.zcpt.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.c503.sc.jxwl.zcpt.bean.UserDefinedArea;
import com.c503.sc.jxwl.zcpt.dao.IUserDefinedAreaDao;
import com.c503.sc.jxwl.zcpt.service.IUserDefinedAreaService;

/**
 * 
 * 〈一句话功能简述〉用户自定义区域
 * 〈功能详细描述〉
 * 
 * @author guqh
 * @version [版本号, 2017-3-30]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Service(value = "userDefinedAreaService")
public class UserDefinedAreaServiceImpl implements IUserDefinedAreaService {
    /** 自定义区域dao */
    @Resource(name = "userDefinedAreaDao")
    private IUserDefinedAreaDao userDefinedAreaDao;
    
    @Override
    public int saveUserDefinedArea(UserDefinedArea userDefinedArea)
        throws Exception {
        this.userDefinedAreaDao.saveUserDefinedArea(userDefinedArea);
        return 1;
    }
    
    @Override
    public int deleteUserDefinedArea(String id)
        throws Exception {
        this.userDefinedAreaDao.deleteUserDefinedArea(id);
        return 1;
    }
    
    @Override
    public List<UserDefinedArea> findUserDefinedAreas()
        throws Exception {
        List<UserDefinedArea> definedArea =
            this.userDefinedAreaDao.findUserDefinedAreas();
        return definedArea;
    }
    
    @Override
    public UserDefinedArea findUserDefinedAreaById(String id)
        throws Exception {
        UserDefinedArea definedArea =
            this.userDefinedAreaDao.findUserDefinedAreaById(id);
        return definedArea;
    }
    
    @Override
    public int updateUserDefinedArea(UserDefinedArea userDefinedArea)
        throws Exception {
        this.userDefinedAreaDao.updateUserDefinedArea(userDefinedArea);
        return 1;
    }
    
}
