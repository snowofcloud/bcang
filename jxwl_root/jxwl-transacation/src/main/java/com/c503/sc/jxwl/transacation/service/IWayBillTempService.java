/**
 * 文件名：IEnterpriseService.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-7-20
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.transacation.service;

import java.util.List;
import java.util.Map;

import com.c503.sc.jxwl.transacation.bean.WayBillTempEntity;
import com.c503.sc.jxwl.vehiclemonitor.bean.CarLocationEntity;

/**
 * 
 * 〈一句话功能简述〉模板业务层接口
 * 〈功能详细描述〉
 * 
 * @author wl
 * @version [版本号, 2016-08-30]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface IWayBillTempService {
    
    /**
     * 
     * 〈一句话功能简述〉分页查询运单信息
     * 〈功能详细描述〉
     * 
     * @param map 查询参数
     * @return list 运单信息集合
     * @throws Exception 异常
     * @see [类、类#方法、类#成员]
     */
    List<WayBillTempEntity> findByParams(Map<String, Object> map)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉查询运输货物的车辆信息
     * 〈功能详细描述〉
     * 
     * @param map map
     * @return WayBillEntity
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    List<WayBillTempEntity> findVehicleData(Map<String, Object> map)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉保存货物信息
     * 〈功能详细描述〉
     * 
     * @param entity entity
     * @return object
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    WayBillTempEntity save(WayBillTempEntity entity)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉保存运单信息
     * 〈功能详细描述〉
     * 
     * @param entity entity
     * @return object
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    WayBillTempEntity saveWayBill(WayBillTempEntity entity)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉
     * 〈功能详细描述〉
     * 
     * @param entity entity
     * @return 更新条数
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    int update(WayBillTempEntity entity)
        throws Exception;
    
    /**
     * 
     * 〈一句话功能简述〉查询模板信息
     * 〈功能详细描述〉
     * 
     * @param id id
     * @return WayBillEntity
     * @throws Exception 异常
     * @see [类、类#方法、类#成员]
     */
    WayBillTempEntity findById(String id)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉删除信息
     * 〈功能详细描述〉
     * 
     * @param map id、remove='1'、updateBy、updateTime
     * @return 影响条数
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    int delete(Map<String, Object> map)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉通过车牌号查询车辆位置信息
     * 〈功能详细描述〉
     * 
     * @param carrierName carrierName
     * @return CarLocationEntity
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    CarLocationEntity findByCarrierName(String carrierName)
        throws Exception;
    
    /**
     * 
     * 〈一句话功能简述〉查询运单模板名称是否存在
     * 〈功能详细描述〉
     * 
     * @param name 模板名称
     * @return 模板名称
     * @throws Exception 异常
     * @see [类、类#方法、类#成员]
     */
    boolean isExistTempName(String name)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉地点模糊匹配
     * 〈功能详细描述〉
     * 
     * @param map map
     * @return WayBillEntity
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    List<Map<String, Object>> placeAndPoi(String orderNo)
        throws Exception;
}
