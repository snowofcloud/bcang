/**
 * 文件名：IUserDefinedAreaService.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2017-3-30
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.zcpt.service;

import java.util.List;

import com.c503.sc.jxwl.zcpt.bean.UserDefinedArea;

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
public interface IUserDefinedAreaService {
    /**
     * 
     * 〈一句话功能简述〉保存
     * 〈功能详细描述〉
     * 
     * @param userDefinedArea
     * @return
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
    int saveUserDefinedArea(UserDefinedArea userDefinedArea)
        throws Exception;
    
    /**
     * 
     * 〈一句话功能简述〉删除
     * 〈功能详细描述〉
     * 
     * @param id
     * @return
     * @see [类、类#方法、类#成员]
     */
    int deleteUserDefinedArea(String id)
        throws Exception;
    
    /**
     * 
     * 〈一句话功能简述〉查询
     * 〈功能详细描述〉
     * 
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<UserDefinedArea> findUserDefinedAreas()
        throws Exception;
    
    /**
     * 
     * 〈一句话功能简述〉查看
     * 〈功能详细描述〉
     * 
     * @param id
     * @return
     * @see [类、类#方法、类#成员]
     */
    UserDefinedArea findUserDefinedAreaById(String id)
        throws Exception;
    
    /**
     * 
     * 〈一句话功能简述〉更新
     * 〈功能详细描述〉
     * 
     * @param userDefinedArea
     * @return
     * @see [类、类#方法、类#成员]
     */
    int updateUserDefinedArea(UserDefinedArea userDefinedArea)
        throws Exception;
    
}
