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

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.c503.sc.jxwl.transacation.bean.WayBillGoodsTempEntity;
import com.c503.sc.jxwl.transacation.bean.WayBillTempEntity;

/**
 * 
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * 
 * @author wl
 * @version [版本号, 2016-08-30]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Repository(value = "wayBillTempDao")
public interface IWayBillTempDao {
    
    /**
     * 〈一句话功能简述〉分页查询模板信息
     * 〈功能详细描述〉
     * 
     * @param map map
     * @return List<WayBillTempEntity>
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    List<WayBillTempEntity> findByParams(Map<String, Object> map)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉查询运输货物的车辆信息
     * 〈功能详细描述〉
     * 
     * @param map map
     * @return WayBillTempEntity
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    List<WayBillTempEntity> findVehicleData(Map<String, Object> map)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉查询模板信息
     * 〈功能详细描述〉
     * 
     * @param id id
     * @return List<WayBillTempEntity>
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    WayBillTempEntity findById(String id)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉查询所有货物信息
     * 〈功能详细描述〉
     * 
     * @param id id
     * @return List<WayBillTempEntity>
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    List<WayBillTempEntity> findAllGoods(String id)
        throws Exception;
    
    /**
     * 
     * 〈一句话功能简述〉保存运单信息
     * 〈功能详细描述〉
     * 
     * @param entity WayBillTempEntity
     * @return 影响行数
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    int save(WayBillTempEntity entity)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉查询是否存在模板
     * 〈功能详细描述〉
     * 
     * @param id id
     * @return tradeStatus
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @Select("SELECT ID FROM T_WAYBILL_TEMP WHERE ID = #{id, jdbcType=VARCHAR} AND REMOVE = '0' AND ROWNUM= 1 ")
    String findExist(String id)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉删除已存在的模板
     * 〈功能详细描述〉
     * 
     * @param id id
     * @return tradeStatus
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    String deleteTemp(String id)
        throws Exception;
    
    /**
     * 
     * 〈一句话功能简述〉删除模板信息
     * 〈功能详细描述〉
     * 
     * @param map map
     * @return 影响行数
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    int delete(Map<String, Object> map)
        throws Exception;
    
    /**
     * 
     * 〈一句话功能简述〉修改模板信息
     * 〈功能详细描述〉
     * 
     * @param entity WayBillTempEntity
     * @return 影响行数
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    int update(WayBillTempEntity entity)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉保存运单信息
     * 〈功能详细描述〉
     * 
     * @param entity WayBillTempEntity
     * @return 影响行数
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    int saveWayBill(WayBillTempEntity entity)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉保存运单--货物信息
     * 〈功能详细描述〉
     * 
     * @param wayBillGoodsTemp wayBillGoodsTemp
     * @return object
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    int saveWayBillVal(List<WayBillGoodsTempEntity> wayBillGoodsTemp)
        throws Exception;
    
    /**
     * 
     * 〈一句话功能简述〉查询运单模板名称是否存在
     * 〈功能详细描述〉
     * 
     * @param tempName 模板名称
     * @return 模板名称
     * @throws Exception 异常
     * @see [类、类#方法、类#成员]
     */
    String isExistTempName(String tempName)
        throws Exception;
   
    /**
     * 
     * 〈一句话功能简述〉模糊匹配地名
     * 〈功能详细描述〉
     * 
     * @param tempName 模板名称
     * @return 模板名称
     * @throws Exception 异常
     * @see [类、类#方法、类#成员]
     */
	List<Map<String, Object>> placeAndPoi(String orderNo)
			throws Exception;
}
