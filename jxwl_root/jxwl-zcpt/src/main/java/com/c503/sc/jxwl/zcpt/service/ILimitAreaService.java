/**
 * 文件名：ISendToTerminal.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-8-1
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.zcpt.service;

import java.util.List;

import com.c503.sc.jxwl.zcpt.bean.LimitArea;

/**
 * 区域限制
 * 
 * @author zz
 * @version [版本号, 2016-11-16]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface ILimitAreaService {
    /**
     * 〈一句话功能简述〉保存区域限制
     * 〈功能详细描述〉
     * 
     * @param limitArea limitArea
     * @return 影响条数
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    int save(LimitArea limitArea)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉删除区域限制
     * 〈功能详细描述〉
     * 
     * @param id id
     * @return 删除条数
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    int delLimitArea(String id)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉更新区域限制
     * 〈功能详细描述〉
     * 
     * @param limitArea limitArea
     * @return 影响条数
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    int update(LimitArea limitArea)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉查询所有有效的限制区域
     * 〈功能详细描述〉
     * 
     * @return List<LimitArea>
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    List<LimitArea> findAllAreaLimit()
        throws Exception;
    
    /**
     * 〈一句话功能简述〉查询区域限制（根据id查询）
     * 〈功能详细描述〉
     * 
     * @param id 区域id
     * @return LimitArea
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    LimitArea findByLimitAreaId(String id)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉通过运单获得所有的运单
     * 〈功能详细描述〉
     * 
     * @return
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
    List<String> findAllWaybillIdByTransport()
        throws Exception;
}
