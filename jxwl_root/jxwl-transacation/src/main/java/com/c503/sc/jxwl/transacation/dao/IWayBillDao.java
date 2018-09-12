/**
 * 文件名：EmergencyInfoDao.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-7-27
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.transacation.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.c503.sc.jxwl.common.bean.EnterpriseEntity;
import com.c503.sc.jxwl.transacation.bean.WayBillEntity;
import com.c503.sc.jxwl.transacation.bean.WayBillGoodsEntity;
import com.c503.sc.jxwl.vehiclemonitor.bean.OccupationPersonEntity;

/**
 * 
 * 〈一句话功能简述〉 〈功能详细描述〉
 * 
 * @author wl
 * @version [版本号, 2016-08-30]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Repository(value = "wayBillDao")
public interface IWayBillDao {

	/**
	 * 〈一句话功能简述〉 〈功能详细描述〉
	 * 
	 * @param map
	 *            map
	 * @return List<WayBillEntity>
	 * @throws Exception
	 *             Exception
	 * @see [类、类#方法、类#成员]
	 */
	List<WayBillEntity> findByParams(Map<String, Object> map) throws Exception;
	/**
	 * 〈一句话功能简述〉 〈功能详细描述〉
	 * 
	 * @param map
	 *            map
	 * @return List<WayBillEntity>
	 * @throws Exception
	 *             Exception
	 * @see [类、类#方法、类#成员]
	 */
	List<WayBillEntity> findForAppByParams(Map<String, Object> map) throws Exception;
	
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
	 * 〈一句话功能简述〉查询运单信息 〈功能详细描述〉
	 * 
	 * @param id
	 *            id
	 * @return List<WayBillEntity>
	 * @throws Exception
	 *             Exception
	 * @see [类、类#方法、类#成员]
	 */
	WayBillEntity findById(String id) throws Exception;

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
	@Select("SELECT  CORPORATE_NO FROM T_DANGER_VEHICLE WHERE LICENCE_PLATE_NO  = #{carrierName} AND REMOVE = '0' AND ROWNUM= 1 ")
	String findCorporate(String carrierName) throws Exception;

	/**
	 * 〈一句话功能简述〉查询物流企业信息 〈功能详细描述〉
	 * 
	 * @param enterpriseType
	 *            enterpriseType
	 * @return EnterpriseEntity
	 * @throws Exception
	 *             Exception
	 * @see [类、类#方法、类#成员]
	 */
	List<EnterpriseEntity> findCorporateNo(String enterpriseType)
			throws Exception;

	/**
	 * 〈一句话功能简述〉查询物流企业下面的驾驶员、押运员 〈功能详细描述〉
	 * 
	 * @param map
	 *            map
	 * @return List<OccupationPersonEntity>
	 * @throws Exception
	 *             Exception
	 * @see [类、类#方法、类#成员]
	 */
	List<OccupationPersonEntity> findPersonType(Map<String, Object> map)
			throws Exception;

	/**
	 * 〈一句话功能简述〉查询所有货物 〈功能详细描述〉
	 * 
	 * @param id
	 *            id
	 * @return List<WayBillEntity>
	 * @throws Exception
	 *             Exception
	 * @see [类、类#方法、类#成员]
	 */
	List<WayBillEntity> findAllGoods(String id) throws Exception;

	/**
	 * 
	 * 〈一句话功能简述〉保存运单信息 〈功能详细描述〉
	 * 
	 * @param entity
	 *            WayBillEntity
	 * @return 影响行数
	 * @throws Exception
	 *             Exception
	 * @see [类、类#方法、类#成员]
	 */
	int save(WayBillEntity entity) throws Exception;

	/**
	 * 〈一句话功能简述〉查询是否存在模板 〈功能详细描述〉
	 * 
	 * @param id
	 *            id
	 * @return tradeStatus
	 * @throws Exception
	 *             Exception
	 * @see [类、类#方法、类#成员]
	 */
	@Select("SELECT ID FROM T_WAYBILL_TEMP WHERE ID = #{id, jdbcType=VARCHAR} AND REMOVE = '0' AND ROWNUM= 1 ")
	String findExist(String id) throws Exception;

	/**
	 * 〈一句话功能简述〉删除已存在的模板 〈功能详细描述〉
	 * 
	 * @param id
	 *            id
	 * @return tradeStatus
	 * @throws Exception
	 *             Exception
	 * @see [类、类#方法、类#成员]
	 */
	String deleteTemp(String id) throws Exception;

	/**
	 * 
	 * 〈一句话功能简述〉保存运单模板信息 〈功能详细描述〉
	 * 
	 * @param entity
	 *            WayBillEntity
	 * @return 影响行数
	 * @throws Exception
	 *             Exception
	 * @see [类、类#方法、类#成员]
	 */
	int saveTemp(WayBillEntity entity) throws Exception;

	/**
	 * 〈一句话功能简述〉删除运单信息 〈功能详细描述〉
	 * 
	 * @param map
	 *            map
	 * @return 影响行数
	 * @throws Exception
	 *             Exception
	 * @see [类、类#方法、类#成员]
	 */
	int delete(Map<String, Object> map) throws Exception;

	/**
	 * 
	 * 〈一句话功能简述〉修改运单信息 〈功能详细描述〉
	 * 
	 * @param entity
	 *            WayBillEntity
	 * @return 影响行数
	 * @throws Exception
	 *             Exception
	 * @see [类、类#方法、类#成员]
	 */
	int update(WayBillEntity entity) throws Exception;

	/**
	 * 
	 * 〈一句话功能简述〉修改运单信息--App接口 〈功能详细描述〉
	 * 
	 * @param entity
	 *            WayBillEntity
	 * @return 影响行数
	 * @throws Exception
	 *             Exception
	 * @see [类、类#方法、类#成员]
	 */
	int updateForApp(WayBillEntity entity) throws Exception;

	/**
	 * 
	 * 〈一句话功能简述〉修改运单信息--App查询驾驶员当前是否有运单接口 〈功能详细描述〉
	 * 
	 * @param driverid
	 *            driverid
	 * @return List<WayBillEntity>
	 * @throws Exception
	 *             Exception
	 * @see [类、类#方法、类#成员]
	 */
	List<WayBillEntity> findWayBillStatus(String driverid) throws Exception;

	/**
	 * 〈一句话功能简述〉保存 货物信息 〈功能详细描述〉
	 * 
	 * @param wayBillGoods
	 *            wayBillGoods
	 * @return 影响行数
	 * @throws Exception
	 *             Exception
	 * @see [类、类#方法、类#成员]
	 */
	int saveTempVal(List<WayBillGoodsEntity> wayBillGoods) throws Exception;

	/**
	 * 〈一句话功能简述〉 〈功能详细描述〉
	 * 
	 * @param carNo
	 *            carNo
	 * @return List<WayBillGoodsEntity>
	 * @throws Exception
	 *             Exception
	 * @see [类、类#方法、类#成员]
	 */
	List<WayBillGoodsEntity> findGoodsData(String carNo) throws Exception;

	/**
	 * 〈一句话功能简述〉findWaybilllNoHasExist 〈功能详细描述〉
	 * 
	 * @param checkNo
	 *            checkNo
	 * @return String
	 * @see [类、类#方法、类#成员]
	 */
	String findWaybilllNoHasExist(@Param(value = "checkNo") String checkNo);

	/**
	 * 〈一句话功能简述〉查询REGID 〈功能详细描述〉
	 * 
	 * @param id
	 *            id
	 * @return String
	 * @see [类、类#方法、类#成员]
	 */
	String findRegIdByWayBill(String id);

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
	 * 〈一句话功能简述〉查询运单信息 〈功能详细描述〉
	 * 
	 * @param map
	 *            map
	 * @return WayBillEntity
	 * @throws Exception
	 *             Exception
	 * @see [类、类#方法、类#成员]
	 */
	WayBillEntity isCarExist(Map<String, String> map) throws Exception;

	
	/**
	 * 〈一句话功能简述〉是否完成 〈功能详细描述〉
	 * 
	 * @param map
	 *            map
	 * @return String
	 * @throws Exception
	 *             Exception
	 * @see [类、类#方法、类#成员]
	 */
	String isDeal(Map<String, Object> map) throws Exception;

	/**
	 * 〈一句话功能简述〉查询运单信息 〈功能详细描述〉
	 * 
	 * @param orderNo
	 *            orderNo
	 * @return WayBillEntity
	 * @throws Exception
	 *             Exception
	 * @see [类、类#方法、类#成员]
	 */
	WayBillEntity findOrderMessage(String orderNo) throws Exception;

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
	WayBillEntity findForBind(Map<String, String> map) throws Exception;
	String isHasWayBillNo(Map<String, String> map) throws Exception;
}
