/*
 * 文件名：IVehicleService
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-7-20
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.zcpt.service;

import java.util.List;

import com.c503.sc.jxwl.common.bean.LocationEntity;

/**
 * 
 * 〈一句话功能简述〉车辆信息业务层接口 〈功能详细描述〉
 * 
 * @author guqh
 * @version [版本号, 2016-7-20]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface IVehicleService {
    
    /**
     * 〈一句话功能简述〉获得车辆上下线信息并通知浏览器
     * 〈功能详细描述〉
     * 
     * @throws Exception 系统异常
     * @see [类、类#方法、类#成员]
     */
    void getCarOnOffline()
        throws Exception;
    
    /**
     * 〈一句话功能简述〉获得上线车辆位置信息
     * 〈功能详细描述〉
     * 
     * @return 上线车辆位置信息
     * @see [类、类#方法、类#成员]
     */
    List<LocationEntity> getCarOnLineList();
    
    /**
     * 〈一句话功能简述〉设置下线车辆位置信息
     * 〈功能详细描述〉
     * 
     * @return 上线车辆位置信息
     * @see [类、类#方法、类#成员]
     */
    List<LocationEntity> getCarOffLineList();
}
