/**
 * 文件名：IDangerVehicleService.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-7-20
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.zcpt.service;

import java.util.List;
import java.util.Map;

import com.c503.sc.jxwl.common.bean.LocationEntity;
import com.c503.sc.jxwl.zcpt.bean.DangerVehicleEntity;
import com.c503.sc.jxwl.zcpt.vo.WlEnpCarVo;

/**
 * 
 * 〈一句话功能简述〉危险品车辆信息业务层接口 〈功能详细描述〉
 * 
 * @author guqh
 * @version [版本号, 2016-7-20]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface IDangerVehicleService {
    
    /**
     * 
     * 〈一句话功能简述〉分页查询危险品车辆信息 〈功能详细描述〉
     * 
     * @param map
     *            查询参数
     * @return list 危险品车辆信息集合
     * @throws Exception
     *             异常
     * @see [类、类#方法、类#成员]
     */
    List<DangerVehicleEntity> findByParams(Map<String, Object> map)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉获取所有车辆的实施位置
     * 〈功能详细描述〉
     * 
     * @return List<LocationEntity>
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    List<LocationEntity> findRealLocationAll()
        throws Exception;
    
    /**
     * 获取车辆的实时位置
     * 
     * @param carrierName 车牌号
     * @return 实时位置
     * @throws Exception Exception
     */
    LocationEntity findRealLocation(String carrierName)
        throws Exception;
    
    /**
     * 
     * 通过车牌号或运单号查询车辆信息， 车辆信息包括：车辆基本信息、车辆当前运单信息、车辆当前位置信息、企业信息
     * 
     * @param map
     *            查询参数
     * @return list 危险品车辆信息集合
     * @throws Exception
     *             异常
     * @see [类、类#方法、类#成员]
     */
    List<Map<String, Object>> findDangerVehicleByParams(Map<String, Object> map)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉删除危险品车辆信息 〈功能详细描述〉
     * 
     * @param map
     *            id、remove='1'、updateBy、updateTime
     * @return 返回影响条数
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    int delete(Map<String, Object> map)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉保存危险品车辆信息 〈功能详细描述〉
     * 
     * @param entity
     *            DangerVehicleEntity
     * @return 返回影响条数
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    Object save(DangerVehicleEntity entity)
        throws Exception;
    
    /**
     * 
     * 〈一句话功能简述〉 〈功能详细描述〉
     * 
     * @param entity
     *            实体类
     * @return entity
     * @throws Exception
     *             异常
     * @see [类、类#方法、类#成员]
     */
    Object update(DangerVehicleEntity entity)
        throws Exception;
    
    /**
     * 
     * 〈一句话功能简述〉 〈功能详细描述〉
     * 
     * @param id id
     * @return DangerVehicleEntity
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    DangerVehicleEntity findById(String id)
        throws Exception;
    
    /**
     * 
     * 〈一句话功能简述〉 〈功能详细描述〉
     * 
     * @param id id
     * @return DangerVehicleEntity
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    DangerVehicleEntity findByCarrierName(String carrierName)
        throws Exception;
    
    /**
     * 
     * 〈一句话功能简述〉 查询当前登录物流企业下所有车辆的车牌号
     * 
     * @param id id
     * @return DangerVehicleEntity
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    List<String> findCarrierName(Map<String, Object> map)
        throws Exception;
    
    /**
     * 
     * 〈一句话功能简述〉 〈功能详细描述〉
     * 
     * @param co co
     * @return String
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    String findByco(String co)
        throws Exception;
    
    /**
     * 获取车辆实时位置
     * 
     * @param map map
     * @return List<LocationEntity>
     * @throws Exception Exception
     */
    List<LocationEntity> findRealLocationsByParams(Map<String, Object> map)
        throws Exception;
    
    /**
     * 获取车辆总数量
     * 
     * @return 车辆数量
     * @throws Exception Exception
     */
    int findTotal()
        throws Exception;
    
    /**
     * 获取车辆实时信息
     * 
     * @return 车辆数量
     * @throws Exception Exception
     */
    List<LocationEntity> findAll()
        throws Exception;
    
    /**
     * 〈一句话功能简述〉查询物流企业下的所有车辆
     * 〈功能详细描述〉
     * 
     * @param corporateNo corporateNo
     * @return List<WlEnpCarVo>
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    List<WlEnpCarVo> findEnpCarForWl(String corporateNo)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉通过法人代码查询车辆
     * 〈功能详细描述〉
     * 
     * @param corporateNo corporateNo
     * @return List<String>
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    List<String> findCarNamesBycorporateNo(String corporateNo)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉更改车源标记（将标记从‘车源’改为‘车辆’，或者从‘车辆’改为‘车源’）
     * 〈功能详细描述〉
     * 
     * @param corporateNo corporateNo
     * @return List<String>
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    int updateVehicleSrcFlag(Map<String, Object> map)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉获取车牌相关的运单、驾驶员、企业、车辆信息
     * 〈功能详细描述〉
     * 
     * @param carNo 车牌号
     * @return 车牌相关的运单、驾驶员、企业、车辆信息
     * @throws Exception 系统异常
     * @see [类、类#方法、类#成员]
     */
    Map<String, Object> findMapData(String carNo)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉获取业务数据库中车牌相关的运单、驾驶员、企业、车辆信息
     * 〈功能详细描述〉
     * 
     * @param map
     * @return 车辆相关信息
     * @throws Exception 系统异常
     * @see [类、类#方法、类#成员]
     */
    Map<String, Object> getRtLocation(Map<String, Object> map)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉批量更新实时位置
     * 〈功能详细描述〉
     * 
     * @param locList 实时位置
     * @return 成功/失败
     * @throws Exception 系统异常
     * @see [类、类#方法、类#成员]
     */
    boolean batchUpdateRealLocation(List<LocationEntity> locList)
        throws Exception;
    
    /**
     * 
     * 〈一句话功能简述>更新资质信息 〈功能详细描述〉
     * 
     * @param map map
     * @return 返回成功 影响的行数
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    List<DangerVehicleEntity> findVehicle(Map<String, Object> map)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉查询物流企业信息下的车牌 〈功能详细描述〉
     * 
     * @param corporateNo
     *            企业类型
     * @return List<DangerVehicleEntity>
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    List<DangerVehicleEntity> findLicencePlateNo(String corporateNo)
        throws Exception;
    
    /****************************************** 企业车辆相关 **************************************/
    /**
     * 〈一句话功能简述〉查询企业车辆信息
     * 〈功能详细描述〉
     * 
     * @param map map
     * @return List<DangerVehicleEntity>
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    List<DangerVehicleEntity> findCars(Map<String, Object> map)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉查询车辆详情
     * 〈功能详细描述〉
     * 
     * @param id 车辆id
     * @return DangerVehicleEntity
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    DangerVehicleEntity findCarInfoById(String id)
        throws Exception;
}
