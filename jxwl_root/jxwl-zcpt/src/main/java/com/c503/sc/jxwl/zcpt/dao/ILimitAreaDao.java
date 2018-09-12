/**
 * 文件名：ILimitAreaDao.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-8-4
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.zcpt.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;

import com.c503.sc.jxwl.zcpt.bean.LimitArea;

/**
 * 限制区域数据
 * 
 * @author qianxq
 * @version [版本号, 2016-8-4]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@ComponentScan(value = "com.c503.sc.jxwl.zcpt.dao")
@Repository(value = "limitAreaDao")
public interface ILimitAreaDao {
    /**
     * 存储区域数据
     * 
     * @param limitArea LimitArea
     * @return int
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    int save(LimitArea limitArea)
        throws Exception;
    
    
    /**
     *〈一句话功能简述〉 删除
     * 〈功能详细描述〉
     * @param id id
     * @return object
     * @throws Exception Exception
     * @see  [类、类#方法、类#成员]
     */
    int delete(String id)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉查询所有区域限制
     * 〈功能详细描述〉
     * 
     * @return List<LimitArea>
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    List<LimitArea> findAllAreaLimit()
        throws Exception;
    
    /**
     * 〈一句话功能简述〉修改区域限制
     * 〈功能详细描述〉
     * 
     * @param limitArea LimitArea
     * @return 影响条数
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    int update(LimitArea limitArea)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉查看区域限制
     * 〈功能详细描述〉
     * 
     * @param id id
     * @return LimitArea
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    LimitArea findByLimitAreaId(String id)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉查询到所有的运输车辆
     * 〈功能详细描述〉
     * 
     * @return waybillId
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @Select("SELECT ID FROM T_WAYBILL WHERE CHECKSTATUS IN ('111001003','111001002')" +
    		" AND REMOVE ='0'")
    List<String> findAllWaybillIdByTransport()
    		throws Exception;
}
