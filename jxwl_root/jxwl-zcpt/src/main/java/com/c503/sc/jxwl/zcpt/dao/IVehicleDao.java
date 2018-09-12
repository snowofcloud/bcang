/**
 * 文件名：IVehicleDao.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2017-10-23
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.zcpt.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.c503.sc.jxwl.common.bean.LocationEntity;

@Repository(value = "vehicleDao")
public interface IVehicleDao {
    
    /**
     * 〈一句话功能简述〉查询所有车辆位置信息
     * 〈功能详细描述〉
     * 
     * @return 车辆位置信息
     * @throws Exception 数据库异常
     * @see [类、类#方法、类#成员]
     */
    List<LocationEntity> findAll()
        throws Exception;
    
}
