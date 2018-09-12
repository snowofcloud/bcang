/**
 * 文件名：CarOnlineController.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-8-3
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.vehiclemonitor.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.c503.sc.base.service.IBaseService;
import com.c503.sc.jxwl.common.bean.LocationEntity;
import com.c503.sc.jxwl.common.constant.CommonConstant;
import com.c503.sc.jxwl.common.controller.ResultController;
import com.c503.sc.jxwl.zcpt.service.IVehicleService;
import com.c503.sc.log.LoggingManager;

/**
 * 上下线通知
 * 
 * @author wangwenhuan
 * @version [版本号, 2016-11-24]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Controller
@RequestMapping(value = "/carOnline")
public class CarOnlineController extends ResultController {
    
    /** 日志器 */
    private static final LoggingManager LOGGER =
        LoggingManager.getLogger(CarOnlineController.class);
    
    /** 车辆信息业务接口 */
    @Resource(name = "vehicleService")
    private IVehicleService vehicleService;
    
    // IE请求车辆上线集合
    @RequestMapping(value = "/getCarOnline4IE")
    @ResponseBody
    public Object getCarOnline4IE()
        throws Exception {
        List<LocationEntity> list = this.vehicleService.getCarOnLineList();
        sendData(list, CommonConstant.FIND_SUC_OPTION);
        return sendMessage();
    }
    
    // IE请求车辆下线集合
    @RequestMapping(value = "/getCarOffline4IE")
    @ResponseBody
    public Object getCarOffline4IE()
        throws Exception {
        List<LocationEntity> list = this.vehicleService.getCarOffLineList();
        sendData(list, CommonConstant.FIND_SUC_OPTION);
        return sendMessage();
    }
    
    @SuppressWarnings("unchecked")
    @Override
    protected <T> IBaseService<T> getBaseService() {
        return (IBaseService<T>) this.vehicleService;
    }
    
    @Override
    protected Object show() {
        return null;
    }
    
    @Override
    protected LoggingManager logger() {
        return LOGGER;
    }
}
