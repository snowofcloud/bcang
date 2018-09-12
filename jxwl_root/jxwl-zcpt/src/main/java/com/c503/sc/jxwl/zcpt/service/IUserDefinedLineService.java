/**
 * 文件名：IUserDefinedService.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2017-4-13
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.zcpt.service;

import java.util.List;

import com.c503.sc.jxwl.zcpt.bean.UserDefinedLine;

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
public interface IUserDefinedLineService {
    /**
     * 
     * 〈一句话功能简述〉保存
     * 〈功能详细描述〉
     * 
     * @param userDefinedLine
     * @return
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
    int saveUserDefinedLine(UserDefinedLine userDefinedLine)
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
    int deleteUserDefinedLine(String id)
        throws Exception;
    
    /**
     * 
     * 〈一句话功能简述〉查询
     * 〈功能详细描述〉
     * 
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<UserDefinedLine> findUserDefinedLines()
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
    UserDefinedLine findUserDefinedLineById(String id)
        throws Exception;
    
    /**
     * 
     * 〈一句话功能简述〉更新
     * 〈功能详细描述〉
     * 
     * @param userDefinedLine
     * @return
     * @see [类、类#方法、类#成员]
     */
    int updateUserDefinedLine(UserDefinedLine userDefinedLine)
        throws Exception;
}
