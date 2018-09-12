/**
 * 文件名：ILeaveMessageService.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-8-30
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.transacation.service;

import java.util.List;
import java.util.Map;

import com.c503.sc.jxwl.transacation.bean.LeaveMessageEntity;

/**
 * 
 * 〈一句话功能简述〉留言信息业务层接口
 * 〈功能详细描述〉
 * 
 * @author xinzhiwei
 * @version [版本号, 2016-8-30]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface ILeaveMessageService {
    
    /**
     * 
     * 〈一句话功能简述〉分页查询留言
     * 〈功能详细描述〉
     * 
     * @param map 查询参数
     * @return list 留言信息集合
     * @throws Exception 异常
     * @see [类、类#方法、类#成员]
     */
    List<LeaveMessageEntity> findByParams(Map<String, Object> map)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉留言是否已被删除
     * 〈功能详细描述〉
     * 
     * @param id id
     * @return true or false
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    boolean isLeaveMessageDeleted(String id)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉删除留言
     * 〈功能详细描述〉
     * 
     * @param map id、remove='1'、updateBy、updateTime
     * @return 返回影响条数
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    int delete(Map<String, Object> map)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉保存留言
     * 〈功能详细描述〉
     * 
     * @param entity entity
     * @return object
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    Object save(LeaveMessageEntity entity)
        throws Exception;
    
    /**
     * 
     * 〈一句话功能简述〉编辑留言
     * 〈功能详细描述〉
     * 
     * @param entity 实体类
     * @return entity
     * @throws Exception 异常
     * @see [类、类#方法、类#成员]
     */
    Object update(LeaveMessageEntity entity)
        throws Exception;
    
    /**
     * 
     * 〈一句话功能简述〉按ID值查询留言
     * 〈功能详细描述〉
     * 
     * @param id id
     * @param hgCorporateNo hgCorporateNo
     * @param wlCorporateNo wlCorporateNo
     * @return LeaveMessageEntity
     * @throws Exception 异常
     * @see [类、类#方法、类#成员]
     */
    LeaveMessageEntity findById(String id, String hgCorporateNo,
        String wlCorporateNo)
        throws Exception;
    
    /**
     * 
     * 〈一句话功能简述〉通过货单号查询货单所属的化工企业
     * 〈功能详细描述〉
     * 
     * @param goodsNo 货单号
     * @return 化工企业名
     * @throws Exception 异常
     * @see [类、类#方法、类#成员]
     */
    String findChemicalEnterpriseByGoodNo(String goodsNo)
        throws Exception;
    
    /**
     * 
     * 〈一句话功能简述〉校验货源的状态是否为‘已发布’且‘未签订订单 ’
     * 〈功能详细描述〉
     * 
     * @param goodsNo 货源号
     * @return 布尔值true/false
     * @throws Exception 异常
     * @see [类、类#方法、类#成员]
     */
    boolean checkGoodsStatus(String goodsNo)
        throws Exception;
    
    /**
     * 
     * 〈一句话功能简述〉根据物流企业ID和货源ID查看是否已经存在留言
     * 〈功能详细描述〉
     * 
     * @param logisticsEnterprise 物流企业ID
     * @param goodsNo 货源ID
     * @return 布尔值true/false
     * @throws Exception 异常
     * @see [类、类#方法、类#成员]
     */
    boolean findLeaveMessageExist(String logisticsEnterprise, String goodsNo)
        throws Exception;
    
}
