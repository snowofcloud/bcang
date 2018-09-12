/**
 * 文件名：IDangerVehicleDao.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-7-20
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.vehiclemonitor.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import com.c503.sc.jxwl.common.bean.LocationEntity;
import com.c503.sc.jxwl.zcpt.bean.DangerVehicleEntity;
import com.c503.sc.jxwl.zcpt.vo.WlEnpCarVo;

/**
 * 
 * 〈一句话功能简述〉危险品车辆信息Dao
 * 〈功能详细描述〉
 * 
 * @author guqh
 * @version [版本号, 2016-7-20]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Repository(value = "dangerVehicleDao")
public interface IDangerVehicleDao {
    /**
     * 获取车辆实时位置（通过区域获取）
     * 
     * @param map map
     * @return List<LocationEntity>
     * @throws Exception Exception
     */
    List<LocationEntity> findRealLocationsByParams(Map<String, Object> map)
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
     * 获取业务库中所有车辆实时数据
     * 
     * @param curDate curDate
     * @return List<LocationEntity> 实时位置
     * @throws Exception Exception
     */
    List<LocationEntity> findRealLocationAll(@Param("curDate")
    String curDate)
        throws Exception;
    
    /**
     * 
     * 〈一句话功能简述〉分页查询危险品车辆信息
     * 〈功能详细描述〉
     * 
     * @param map 查询参数
     * @return list 结果集合
     * @throws Exception 异常
     * @see [类、类#方法、类#成员]
     */
    List<DangerVehicleEntity> findByParams(Map<String, Object> map)
        throws Exception;
    
    /**
     * 
     * 通过车牌号或运单号查询车辆信息，
     * 车辆信息包括：车辆基本信息、车辆当前运单信息、车辆当前位置信息、企业信息
     * 
     * @param map 查询参数
     * @return List<Map<String, Object>>
     * @throws Exception 异常
     * @see [类、类#方法、类#成员]
     */
    List<Map<String, Object>> findDangerVehicleByParams(Map<String, Object> map)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉查询物流企业下面的车牌号
     * 〈功能详细描述〉
     * 
     * @param corporateNo idcorporateNo
     * @return List<DangerVehicleEntity>
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    List<DangerVehicleEntity> findLicencePlateNo(String corporateNo)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉删除危险品车辆信息
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
     * 〈一句话功能简述〉查询车辆是否存在
     * 〈功能详细描述〉
     * 
     * @param id id
     * @return String
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @Select("SELECT REMOVE FROM T_DANGER_VEHICLE WHERE ID = #{id, jdbcType=VARCHAR}")
    String findExsitById(String id)
        throws Exception;
    
    /**
     * 
     * 〈一句话功能简述〉保存危险品车辆信息
     * 〈功能详细描述〉
     * 
     * @param entity DangerVehicleEntity
     * @return 保存条数
     * @throws Exception 异常
     * @see [类、类#方法、类#成员]
     */
    int save(DangerVehicleEntity entity)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉查询车辆
     * 〈功能详细描述〉
     * 
     * @param id id
     * @return String
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @Select("SELECT Ta.LICENCE_PLATE_NO  FROM T_DANGER_VEHICLE Ta WHERE Ta.Id = #{id,jdbcType=VARCHAR}")
    String findByVehicle(String id)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉findByco
     * 〈功能详细描述〉
     * 
     * @param str CORPORATE_NO
     * @return String
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @Select("SELECT ENTERPRISE_NAME FROM T_ENTERPRISE WHERE CORPORATE_NO = #{str, jdbcType=VARCHAR}")
    String findByco(String str)
        throws Exception;
    
    /**
     * 
     * 〈一句话功能简述〉编辑危险品车辆信息
     * 〈功能详细描述〉
     * 
     * @param entity DangerVehicleEntity
     * @return 保存条数
     * @throws Exception 异常
     * @see [类、类#方法、类#成员]
     */
    int update(DangerVehicleEntity entity)
        throws Exception;
    
    /**
     * 
     * 〈一句话功能简述〉编辑危险品车辆信息
     * 〈功能详细描述〉
     * 
     * @param entity DangerVehicleEntity
     * @return 保存条数
     * @throws Exception 异常
     * @see [类、类#方法、类#成员]
     */
    int updateLicenceInfo(DangerVehicleEntity entity)
        throws Exception;
    
    /**
     * 
     * 〈一句话功能简述〉通过id查询危险品车辆信息
     * 〈功能详细描述〉
     * 
     * @param map map
     * @return DangerVehicleEntity
     * @throws Exception 异常
     * @see [类、类#方法、类#成员]
     */
    DangerVehicleEntity findById(Map<String, String> map)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉解除危险品车辆附件和危险品车辆信息的关系
     * 〈功能详细描述〉
     * 
     * @param id id
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    void deleteRela(String id)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉通过法人代码或区域企业的车辆数量
     * 〈功能详细描述〉
     * 
     * @param corporateNo 法人代码
     * @return 企业车辆数量
     * @see [类、类#方法、类#成员]
     */
    Integer getDangerVehicNumByCorporateNo(@Param("corporateNo")
    String corporateNo);
    
    /**
     * 〈一句话功能简述〉查询车辆信息
     * 〈功能详细描述〉
     * 
     * @param carrierName 车牌号
     * @return 企业DangerVehicleEntity辆数量
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    DangerVehicleEntity findByName(String carrierName)
        throws Exception;
    
    /**
     * 左侧导航栏下车辆数据
     * 
     * @param map 法人代码
     * @return List<DangerVehicleEntity>
     * @throws Exception Exception
     */
    List<DangerVehicleEntity> findLeftManueName(Map<String, Object> map)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉判断车牌号唯一
     * 〈功能详细描述〉
     * 
     * @param licencePlateNo licencePlateNo
     * @param id id
     * @return object
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    String findLicencePlateNoExit(@Param("licencePlateNo")
    String licencePlateNo, @Param("id")
    String id)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉deleteVehicle
     * 〈功能详细描述〉
     * 
     * @param vehicle vehicle
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @Update("UPDATE T_BLACKLIST_MANAGE  SET REMOVE  = '1'  WHERE VEHICLE = #{vehicle,jdbcType=VARCHAR}")
    void deleteVehicle(String vehicle)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉findVehicle
     * 〈功能详细描述〉
     * 
     * @param vehicle vehicle
     * @return List<String>
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @Select("SELECT TA.ID FROM T_BLACKLIST_MANAGE TA WHERE VEHICLE = #{vehicle,jdbcType=VARCHAR}")
    List<String> findVehicleIdList(String vehicle)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉TODO 临时方法，获取指定车牌的当前运输中运单信息
     * 〈功能详细描述〉
     * 
     * @param carrierName carrierName
     * @return Map<String, Object>
     * @see [类、类#方法、类#成员]
     */
    Map<String, Object> findWayBillData(@Param("carrierName")
    String carrierName);
    
    /**
     * 〈一句话功能简述〉findTotal
     * 〈功能详细描述〉
     * 
     * @return int
     * @see [类、类#方法、类#成员]
     */
    int findTotal()
        throws Exception;
    
    /**
     * 〈一句话功能简述〉findAll
     * 〈功能详细描述〉
     * 
     * @return List<LocationEntity>
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    List<LocationEntity> findAll()
        throws Exception;
    
    /**
     * 〈一句话功能简述〉查询物流企业下的所有车辆
     * 〈功能详细描述〉
     * 
     * @param curDate curDate
     * @param corporateNo corporateNo
     * @return List<WlEnpCarVo>
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    List<WlEnpCarVo> findEnpCarForWl(@Param("curDate")
    String curDate, @Param("corporateNo")
    String corporateNo)
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
    @Select("select t.licence_plate_no from t_danger_vehicle t where "
        + "  t.corporate_no= #{corporateNo} and remove = '0'")
    List<String> findCarNamesBycorporateNo(@Param("corporateNo")
    String corporateNo)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉判断车牌号唯一
     * 〈功能详细描述〉
     * 
     * @param licencePlateNo licencePlateNo
     * @return String
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    String findCodeByLicencePlateNo(@Param("licencePlateNo")
    String licencePlateNo)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉通过车牌号找到所属企业名称
     * 〈功能详细描述〉
     * 
     * @param licencePlateNo licencePlateNo
     * @return String
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    String findNameByLicencePlateNo(String licencePlateNo)
        throws Exception;
    
    /**
     * 
     * 〈一句话功能简述〉通过id查询危险品车辆信息
     * 〈功能详细描述〉
     * 
     * @param map map
     * @return DangerVehicleEntity
     * @throws Exception 异常
     * @see [类、类#方法、类#成员]
     */
    DangerVehicleEntity findByCarrierName(Map<String, String> map)
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
     * 〈一句话功能简述〉 联想查询车辆信息
     * 〈功能详细描述〉
     * 
     * @param map map
     * @return List<DangerVehicleEntity>
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    List<DangerVehicleEntity> findVehicle(Map<String, Object> map)
        throws Exception;
    
    /****************************************** 企业相关车辆 FIXME **************************************/
    /**
     * 〈一句话功能简述〉查询企业车辆信息
     * 〈功能详细描述〉
     * 
     * @param map map
     * @return List<DangerVehicleEntity>
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @Select("select ta.id, tb.enterprise_name, "
        + "         ta.licence_plate_no, ta.vehicle_type, ta.status "
        + "    from t_danger_vehicle ta "
        + "    join t_enterprise tb on ta.corporate_no = tb.corporate_no "
        + "     and ta.corporate_no = #{corporateNo}"
        + "     and vehicle_type = #{vehicleType}"
        + "     and ta.remove = '0' ")
    @Results({
        @Result(id = true, column = "id", property = "id", jdbcType = JdbcType.VARCHAR),
        @Result(column = "enterprise_name", property = "enterpriseName", jdbcType = JdbcType.VARCHAR),
        @Result(column = "licence_plate_no", property = "licencePlateNo", jdbcType = JdbcType.VARCHAR),
        @Result(column = "vehicle_type", property = "vehicleType", jdbcType = JdbcType.VARCHAR),
        @Result(column = "status", property = "status", jdbcType = JdbcType.VARCHAR)})
    List<DangerVehicleEntity> findCarsByParams(Map<String, Object> map)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉查询车辆详情
     * 〈功能详细描述〉
     * 
     * @param map 车辆id
     * @return DangerVehicleEntity
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @Select("select TDV.*,"
        + "(CASE  WHEN (CEIL(((TO_DATE(#{curDate}, 'yyyy-mm-dd hh24-mi-ss') -"
        + " TCL.POINT_TIME)) * 24 * 60) > 0 AND"
        + " CEIL(((TO_DATE(#{curDate}, 'yyyy-mm-dd hh24-mi-ss') -"
        + " TCL.POINT_TIME)) * 24 * 60) < 30) THEN"
        + " 'ON' ELSE 'OFF' END) AS CARSTATUS" + " from T_DANGER_VEHICLE TDV"
        + " LEFT JOIN T_CAR_LOCATION TCL"
        + " ON TDV.LICENCE_PLATE_NO = TCL.CARRIER_NAME"
        + " where TDV.id = #{id}" + " and TDV.REMOVE='0'  ")
    @Results({
        @Result(id = true, column = "id", property = "id", jdbcType = JdbcType.VARCHAR),
        @Result(column = "licence_plate_no", property = "licencePlateNo", jdbcType = JdbcType.VARCHAR),
        @Result(column = "vehicle_type", property = "vehicleType", jdbcType = JdbcType.VARCHAR),
        @Result(column = "VEHICLE_BRAND", property = "vehicleBrand", jdbcType = JdbcType.VARCHAR),
        @Result(column = "VEHICLE_BRAND_TYPE", property = "vehicleBrandType", jdbcType = JdbcType.VARCHAR),
        @Result(column = "VEHICLE_OUTPUT", property = "vehicleOutput", jdbcType = JdbcType.VARCHAR),
        @Result(column = "MOTOR_NO", property = "motorNo", jdbcType = JdbcType.VARCHAR),
        @Result(column = "GRADUADED_NO", property = "graduadedNo", jdbcType = JdbcType.VARCHAR),
        @Result(column = "VEHICLE_NO", property = "vehicleNo", jdbcType = JdbcType.VARCHAR),
        @Result(column = "CROSS_DOMAIN_TYPE", property = "crossDomainType", jdbcType = JdbcType.VARCHAR),
        @Result(column = "enterprise_name", property = "enterpriseName", jdbcType = JdbcType.VARCHAR),
        @Result(column = "CARSTATUS", property = "status", jdbcType = JdbcType.VARCHAR)})
    DangerVehicleEntity findCarInfoById(Map<String, String> map)
        throws Exception;
}
