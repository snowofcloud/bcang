/**
 * 文件名：ICarLocationDao.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-9-6
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.vehiclemonitor.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.c503.sc.jxwl.common.bean.LocationEntity;
import com.c503.sc.jxwl.vehiclemonitor.bean.CarLocationEntity;

/**
 * 〈一句话功能简述〉车辆位置信息Dao
 * 〈功能详细描述〉
 * 
 * @author xiaoqx
 * @version [版本号, 2016-9-6]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Repository(value = "carLocationDao")
public interface ICarLocationDao {
    /**
     * 〈一句话功能简述〉通过车牌号查询车辆详细信息
     * 〈功能详细描述〉
     * 
     * @param carrierName carrierName
     * @return CarLocationEntity
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    CarLocationEntity findByCarrierName(@Param("carrierName")
    String carrierName)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉根据运单号查询车辆位置
     * 〈功能详细描述〉
     * 
     * @param id 运单号id
     * @return CarLocationEntity
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    CarLocationEntity findLocByWaybillId(@Param("id")
    String id)
        throws Exception;
    
    List<CarLocationEntity> findAll()
        throws Exception;
    
    /**
     * 〈一句话功能简述〉保存
     * 〈功能详细描述〉
     * 
     * @param loc 实时位置
     * @return 受影响行数
     * @throws Exception 系统异常
     * @see [类、类#方法、类#成员]
     */
    int save(LocationEntity loc)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉更新
     * 〈功能详细描述〉
     * 
     * @param loc 实时位置
      * @return 受影响行数
     * @throws Exception 系统异常
     * @see [类、类#方法、类#成员]
     */
    int update(LocationEntity loc)
        throws Exception;
    
}
