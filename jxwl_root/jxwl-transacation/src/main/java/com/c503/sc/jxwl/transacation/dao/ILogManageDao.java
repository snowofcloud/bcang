/**
 * 文件名：ILogManageDao.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2017-4-5
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.transacation.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.c503.sc.jxwl.transacation.bean.LogManage;

/**
 * 
 * 〈一句话功能简述〉订单日志管理
 * 〈功能详细描述〉
 * 
 * @author guqh
 * @version [版本号, 2017-4-5]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Repository(value = "logManageDao")
public interface ILogManageDao {
    /**
     * 〈一句话功能简述〉保存订单日志
     * 〈功能详细描述〉
     * 
     * @param logManage logManage
     * @return int
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    int saveLog(LogManage logManage)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉分页查询订单日志信息
     * 〈功能详细描述〉
     * 
     * @param map map
     * @return logManage
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    List<LogManage> findByParams(Map<String, Object> map)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉批量更新
     * 〈功能详细描述〉
     * 
     * @param list 批量更新的集合
     * @return 返回执行成功的条数
     * @throws Exception 系统异常
     * @see [类、类#方法、类#成员]
     */
    int updateBatch(List<LogManage> list)
        throws Exception;
    
    /**
     * 
     * 〈一句话功能简述〉查看提醒详细信息
     * 〈功能详细描述〉
     * 
     * @param id
     * @return
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
    LogManage findById(String id)
        throws Exception;
}
