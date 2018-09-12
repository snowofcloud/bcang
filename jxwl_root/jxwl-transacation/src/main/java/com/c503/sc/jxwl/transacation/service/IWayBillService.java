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

import com.c503.sc.jxwl.transacation.bean.WayBillEntity;
import com.c503.sc.jxwl.transacation.bean.WayBillGoodsEntity;
import com.c503.sc.jxwl.vehiclemonitor.bean.CarLocationEntity;
import com.c503.sc.jxwl.vehiclemonitor.bean.EnterpriseEntity;
import com.c503.sc.jxwl.vehiclemonitor.bean.OccupationPersonEntity;

/**
 * 
 * 〈一句话功能简述〉运单业务层接口 〈功能详细描述〉
 * 
 * @author wl
 * @version [版本号, 2016-08-30]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface IWayBillService {
    
    /**
     * 
     * 〈一句话功能简述〉分页查询运单信息 〈功能详细描述〉
     * 
     * @param map
     *            查询参数
     * @return list 运单信息集合
     * @throws Exception
     *             异常
     * @see [类、类#方法、类#成员]
     */
    List<WayBillEntity> findByParams(Map<String, Object> map)
        throws Exception;
    
    /**
     * 
     * 〈一句话功能简述〉分页查询运单信息 〈功能详细描述〉
     * 
     * @param map
     *            查询参数
     * @return list 运单信息集合
     * @throws Exception
     *             异常
     * @see [类、类#方法、类#成员]
     */
    List<WayBillEntity> findForAppByParams(Map<String, Object> map)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉查询运输货物的车辆信息 〈功能详细描述〉
     * 
     * @param map
     *            map
     * @return WayBillEntity
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    List<WayBillEntity> findVehicleData(Map<String, Object> map)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉查询车辆信息 〈功能详细描述〉
     * 
     * @param carrierName
     *            车辆名称
     * @return DangerVehicleEntity
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    String findCorporate(String carrierName)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉查询物流企业信息 〈功能详细描述〉
     * 
     * @param enterpriseType
     *            企业类型
     * @return List<EnterpriseEntity>
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    List<EnterpriseEntity> findCorporateNo(String enterpriseType)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉查询物流企业的驾驶员、押运员 〈功能详细描述〉
     * 
     * @param map
     *            map辆名称
     * @return OccupationPersonEntity
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    List<OccupationPersonEntity> findPersonType(Map<String, Object> map)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉保存运单信息 〈功能详细描述〉
     * 
     * @param entity
     *            entity
     * @return object
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    WayBillEntity save(WayBillEntity entity)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉保存模板信息 〈功能详细描述〉
     * 
     * @param entity
     *            entity
     * @return object
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    WayBillEntity saveTemp(WayBillEntity entity)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉修改运单信息 〈功能详细描述〉
     * 
     * @param entity
     *            entity
     * @return 更新条数
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    int update(WayBillEntity entity)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉app更新 〈功能详细描述〉
     * 
     * @param entity
     *            entity
     * @return 更新条数
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    int updateForApp(WayBillEntity entity)
        throws Exception;
    
    /**
     * 
     * 〈一句话功能简述〉查询运单信息 〈功能详细描述〉
     * 
     * @param id
     *            id
     * @return WayBillEntity
     * @throws Exception
     *             异常
     * @see [类、类#方法、类#成员]
     */
    WayBillEntity findById(String id)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉删除信息 〈功能详细描述〉
     * 
     * @param map
     *            id、remove='1'、updateBy、updateTime
     * @return 影响条数
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    int delete(Map<String, Object> map)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉通过车牌号查询车辆位置信息 〈功能详细描述〉
     * 
     * @param carrierName
     *            carrierName
     * @return CarLocationEntity
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    CarLocationEntity findByCarrierName(String carrierName)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉 根据车牌号查询货物信息 〈功能详细描述〉
     * 
     * @param carNo
     *            carNo
     * @return List<WayBillGoodsEntity>
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    List<WayBillGoodsEntity> findGoodsData(String carNo)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉根据运单号查询车辆位置 〈功能详细描述〉
     * 
     * @param id
     *            运单号id
     * @return CarLocationEntity
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    CarLocationEntity findLocByWaybillId(String id)
        throws Exception;
    
    // ///////////////////////////////运单 （微信）////////////////////////////////
    /**
     * 〈一句话功能简述〉查询运单 for 微信 〈功能详细描述〉
     * 
     * @param map
     *            corporateNo
     * @return List<Map<String, Object>>
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    List<Map<String, Object>> findWaybillForWeixinParams(Map<String, Object> map)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉查询运单运单车辆是否存在 〈功能详细描述〉
     * 
     * @param id
     *            id
     * @return WayBillEntity
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    WayBillEntity isCarExist(String id)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉判断是否完成订单 〈功能详细描述〉
     * 
     * @param orderNo
     *            orderNo
     * @return boolean
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    
    boolean isDeal(String orderNo)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉通过运单号查询运单信息 〈功能详细描述〉
     * 
     * @param orderNo
     *            orderNo
     * @return WayBillEntity
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    WayBillEntity findOrderMessage(String orderNo)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉绑定查询 〈功能详细描述〉
     * 
     * @param map
     *            map
     * @return WayBillEntity
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    WayBillEntity findForBind(Map<String, String> map)
        throws Exception;
    
    String isHasWayBillNo(Map<String, String> map)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉通过运单ID获得手机注册ID
     * 〈功能详细描述〉
     * 
     * @param id 运单ID
     * @return 手机注册ID
     * @throws Exception 系统异常
     * @see [类、类#方法、类#成员]
     */
    String findRegIdByWayBill(String id)
        throws Exception;
}
