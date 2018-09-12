/**
 * 文件名：ICarInfoDao.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-8-4
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.zcpt.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.c503.sc.jxwl.zcpt.bean.CarInfoEntity;
import com.c503.sc.jxwl.zcpt.bean.WayBillEntity;

/**
 * 查询车辆信息
 * 
 * @author qianxq
 * @version [版本号, 2016-8-4]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@SuppressWarnings("deprecation")
@Repository(value = "carInfoDao")
public interface ICarInfoDao {
    
    /**
     * 跟据车牌号查询
     * 
     * @param params 查询条件
     * @return CarInfoEntity
     * @throws Exception 异常
     * @see [类、类#方法、类#成员]
     */
    CarInfoEntity findByLicencePlateNo(Map<String, Object> params)
        throws Exception;
    
    /**
     * 跟据车牌号查询车辆图片id
     * 
     * @param carrierName carrierName
     * @return String
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    String findCarPicId(String carrierName)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉注：此接口有可能返回多条运单记录
     * 〈功能详细描述〉
     * 
     * @param carNo carNo
     * @return List<WayBillEntity>
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    List<WayBillEntity> findWayBillData(String carNo)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉根据车牌号查询企业名称
     * 〈功能详细描述〉
     * 
     * @param carrierName 车牌号
     * @return carrierName 车牌号
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @Select("SELECT TE.ENTERPRISE_NAME, TE.CORPORATE_NO"
        + "    FROM T_ENTERPRISE TE "
        + "   WHERE TE.CORPORATE_NO = (SELECT CORPORATE_NO "
        + "                            FROM T_DANGER_VEHICLE T "
        + "                           WHERE T.LICENCE_PLATE_NO = #{carrierName} "
        + "                             AND REMOVE = '0') "
        + "     AND REMOVE = '0'")
    Map<String, String> findEnpNameByCarrierName(String carrierName)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉查询车辆是否重名
     * 〈功能详细描述〉
     * 
     * @param carrierName carrierName
     * @return int
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    int carrierNameIsExist(String carrierName)
        throws Exception;
    
}
