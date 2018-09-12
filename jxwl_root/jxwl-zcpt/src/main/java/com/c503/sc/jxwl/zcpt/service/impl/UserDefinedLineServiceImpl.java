/**
 * 文件名：UserDefinedLineServiceImpl.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2017-4-13
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.zcpt.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.c503.sc.jxwl.zcpt.bean.UserDefinedLine;
import com.c503.sc.jxwl.zcpt.dao.IUserDefinedLineDao;
import com.c503.sc.jxwl.zcpt.service.IUserDefinedLineService;

/**
 * 
 * 〈一句话功能简述〉自定义线条
 * 〈功能详细描述〉
 * 
 * @author guqh
 * @version [版本号, 2017-4-13]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Service(value = "userDefinedLineService")
public class UserDefinedLineServiceImpl implements IUserDefinedLineService {
    /** 自定义线条dao */
    @Resource(name = "userDefinedLineDao")
    private IUserDefinedLineDao userDefinedLineDao;
    
    @Override
    public int saveUserDefinedLine(UserDefinedLine userDefinedLine)
        throws Exception {
        this.userDefinedLineDao.saveUserDefinedLine(userDefinedLine);
        return 1;
    }
    
    @Override
    public int deleteUserDefinedLine(String id)
        throws Exception {
        this.userDefinedLineDao.deleteUserDefinedLine(id);
        return 1;
    }
    
    @Override
    public List<UserDefinedLine> findUserDefinedLines()
        throws Exception {
        List<UserDefinedLine> definedLine =
            this.userDefinedLineDao.findUserDefinedLines();
        return definedLine;
    }
    
    @Override
    public UserDefinedLine findUserDefinedLineById(String id)
        throws Exception {
        UserDefinedLine definedLine =
            this.userDefinedLineDao.findUserDefinedLineById(id);
        return definedLine;
    }
    
    @Override
    public int updateUserDefinedLine(UserDefinedLine userDefinedLine)
        throws Exception {
        this.userDefinedLineDao.updateUserDefinedLine(userDefinedLine);
        return 1;
    }
}
