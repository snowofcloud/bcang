/**
 * 文件名：PlatformController.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-8-8
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.vehiclemonitor.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.c503.sc.base.entity.Page;
import com.c503.sc.base.service.IBaseService;
import com.c503.sc.jxwl.common.constant.CommonConstant;
import com.c503.sc.jxwl.common.constant.DictConstant;
import com.c503.sc.jxwl.common.constant.NumberContant;
import com.c503.sc.jxwl.common.controller.ResultController;
import com.c503.sc.jxwl.vehiclemonitor.bean.EnpEveryTimes;
import com.c503.sc.jxwl.vehiclemonitor.bean.InOutAreaEntity;
import com.c503.sc.jxwl.vehiclemonitor.bean.PtStatisticEntity;
import com.c503.sc.jxwl.vehiclemonitor.bean.VehicleAreaStatisticalEntity;
import com.c503.sc.jxwl.vehiclemonitor.service.IStatisticalAnalysisService;
import com.c503.sc.log.LoggingManager;
import com.c503.sc.utils.basetools.C503DateUtils;
import com.c503.sc.utils.common.SystemContants;
import com.c503.sc.utils.response.ResultJQGrid;

/**
 * 〈一句话功能简述〉统计分析action
 * 〈功能详细描述〉
 * 
 * @author zz
 * @version [版本号, 2016-8-8]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Controller
@Scope(value = "prototype")
@RequestMapping(value = "/statisticalAnalysis")
public class StatisticalAnalysisController extends ResultController {
    /** 日志器 */
    private static final LoggingManager LOGGER =
        LoggingManager.getLogger(StatisticalAnalysisController.class);
    
    /** 统计分析service */
    @Resource
    private IStatisticalAnalysisService statisticalAnalysisService;
    
    /**
     * 〈一句话功能简述〉查询所有车辆
     * 〈功能详细描述〉
     * 
     * @return object
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/statistics", method = RequestMethod.GET)
    @ResponseBody
    public Object statistics()
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        
        List<String> roleCode = this.getUser().getRoleCodes();
        if (!roleCode.contains(DictConstant.GOVERNMENT_USER)) {
            return sendCode(NumberContant.THREE, "not permission access");
        }
        
        // 2、接口调用
        Object obj = this.statisticalAnalysisService.findPieVal();
        
        // 3、数据返回
        this.sendData(obj, CommonConstant.FIND_SUC_OPTION);
        LOGGER.debug(SystemContants.DEBUG_END, obj);
        
        return this.sendMessage();
    }
    
    /**
     * 〈一句话功能简述〉分页查询平台信息
     * 〈功能详细描述〉
     * 
     * @param page page
     * @param rows rows
     * @return object
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/findByPage", method = RequestMethod.POST)
    @ResponseBody
    public Object findByPage(Integer page, Integer rows)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        Map<String, Object> map = new HashMap<>();
        Page pageEntity = new Page();
        pageEntity.setCurrentPage(page);
        pageEntity.setPageSize(rows);
        map.put("page", pageEntity);
        
        List<String> roleCode = this.getUser().getRoleCodes();
        if (!roleCode.contains(DictConstant.GOVERNMENT_USER)) {
            return sendCode(NumberContant.THREE, "not permission access");
        }
        
        // 2、接口调用
        List<EnpEveryTimes> list =
            this.statisticalAnalysisService.countAllByParams(map);
        // 3、数据返回
        setJQGrid(list,
            pageEntity.getTotalCount(),
            page,
            rows,
            CommonConstant.FIND_SUC_OPTION);
        LOGGER.debug(SystemContants.DEBUG_END);
        return (ResultJQGrid) this.sendMessage();
    }
    
    /**
     * 〈一句话功能简述〉查询柱状图
     * 〈功能详细描述〉
     * 
     * @param startTime startTime
     * @param endTime endTime
     * @param flag flag
     * @return object
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/countColumn", method = RequestMethod.GET)
    @ResponseBody
    public Object statistics(
        @DateTimeFormat(pattern = "yyyy-MM-dd", iso = ISO.DATE) Date startTime,
        @DateTimeFormat(pattern = "yyyy-MM-dd", iso = ISO.DATE) Date endTime,
        String flag)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, flag);
        
        Map<String, Object> map = new HashMap<>();
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        map.put("flag", flag);
        
        if (null != endTime) {
            endTime = C503DateUtils.addDays(endTime, 1);
            map.put("endTime", endTime);
        }
        
        List<String> roleCode = this.getUser().getRoleCodes();
        if (!roleCode.contains(DictConstant.GOVERNMENT_USER)) {
            return sendCode(NumberContant.THREE, "not permission access");
        }
        
        map.put("curYear", Calendar.getInstance().get(Calendar.YEAR));
        
        // 2、接口调用
        Object obj = this.statisticalAnalysisService.countColumn(map);
        
        // 3、数据返回
        this.sendData(obj, CommonConstant.FIND_SUC_OPTION);
        LOGGER.debug(SystemContants.DEBUG_END, obj);
        
        return this.sendMessage();
    }
    
    /**
     * 〈一句话功能简述〉车辆分布区域统计
     * 〈功能详细描述〉
     * 
     * @return object
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/findVehicleAreaStatistics", method = RequestMethod.POST)
    @ResponseBody
    public Object findVehicleAreaStatistics() throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        // 2、接口调用
        List<VehicleAreaStatisticalEntity> list =
            this.statisticalAnalysisService.findVehicleAreaStatistics();
        // 3、数据返回
        this.sendData(list, CommonConstant.FIND_SUC_OPTION);
        LOGGER.debug(SystemContants.DEBUG_END, list);
        return this.sendMessage();
    }
    
    /**
     * 〈一句话功能简述〉车辆跨越及进出域信息
     * 〈功能详细描述〉
     * 
     * @return object
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/findVehicleInOutAreaStatistics", method = RequestMethod.POST)
    @ResponseBody
    public Object findVehicleInOutAreaStatistics(String carrierName, String areaId, Integer page, Integer rows) throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        Page pageEntity = new Page();
        pageEntity.setCurrentPage(page);
        pageEntity.setPageSize(rows);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("page", pageEntity);
        map.put("carrierName", carrierName);
        //截取areaId从第3位开始后的值，如areaId为008001004，截取后为8001004。因为t_car_inout_area表中存放的area_id都是从8开始
        if(!StringUtils.isEmpty(areaId)){
            areaId = areaId.substring(2, areaId.length());
        }
        map.put("areaId", areaId);
        // 2、接口调用
        List<InOutAreaEntity> list =
            this.statisticalAnalysisService.findVehicleInOutAreaStatistics(map);
        // 3、数据返回
        setJQGrid(list,
            pageEntity.getTotalCount(),
            page,
            rows,
            CommonConstant.FIND_SUC_OPTION);
        LOGGER.debug(SystemContants.DEBUG_END, list);
        return (ResultJQGrid) this.sendMessage();
    }
    
    /**
     * 〈一句话功能简述〉平台统计信息
     * 〈功能详细描述〉
     * 
     * @return object
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/findPtStatistics", method = RequestMethod.POST)
    @ResponseBody
    public Object findPtStatistics(Integer page, Integer rows) throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        Page pageEntity = new Page();
        pageEntity.setCurrentPage(page);
        pageEntity.setPageSize(rows);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("page", pageEntity);
        // 2、接口调用
        List<PtStatisticEntity> list =
            this.statisticalAnalysisService.findPtStatistics(map);
        // 3、数据返回
        setJQGrid(list,
            pageEntity.getTotalCount(),
            page,
            rows,
            CommonConstant.FIND_SUC_OPTION);
        LOGGER.debug(SystemContants.DEBUG_END, list);
        return (ResultJQGrid) this.sendMessage();
    }
    
    
    @SuppressWarnings("unchecked")
    @Override
    protected <T> IBaseService<T> getBaseService() {
        return (IBaseService<T>) this.statisticalAnalysisService;
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
