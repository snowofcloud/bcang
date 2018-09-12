/**
 * 文件名：WayBillStatisticalServiceImpl.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-8-8
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.vehiclemonitor.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.c503.sc.base.exception.CustomException;
import com.c503.sc.jxwl.common.constant.CommonConstant;
import com.c503.sc.jxwl.vehiclemonitor.bean.WayBillStatisticalEntity;
import com.c503.sc.jxwl.vehiclemonitor.dao.IWayBillStatisticalDao;
import com.c503.sc.jxwl.vehiclemonitor.service.IWayBillStatisticalService;
import com.c503.sc.log.LoggingManager;
import com.c503.sc.utils.basetools.C503DateUtils;
import com.c503.sc.utils.common.SystemContants;

/**
 * 〈一句话功能简述〉运单统计业务
 * 〈功能详细描述〉
 * 
 * @author mjw
 * @version [版本号, 2016-8-8]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Service
public class WayBillStatisticalServiceImpl implements
    IWayBillStatisticalService {
    /** 日志器 */
    private static final LoggingManager LOGGER =
        LoggingManager.getLogger(WayBillStatisticalServiceImpl.class);
    
    /** 平台信息接口 */
    @Resource
    private IWayBillStatisticalDao wayBillStatisticalDao;
    
    @Override
    public Map<String, Object> findPieVal()
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, "findPieVal");
        Map<String, Object> map = new HashMap<>();
        try {
            map.put("enp", this.wayBillStatisticalDao.countwlEnp(String.valueOf(Calendar.getInstance()
                    .get(Calendar.YEAR))));
            map.put("car", this.wayBillStatisticalDao.countCar(String.valueOf(Calendar.getInstance()
                    .get(Calendar.YEAR))));
            map.put("wbi",
                this.wayBillStatisticalDao.countWaybill(String.valueOf(Calendar.getInstance()
                    .get(Calendar.YEAR))));
            LOGGER.debug(SystemContants.DEBUG_END, map);
        } catch (Exception e) {
            throw new CustomException(CommonConstant.SYS_EXCEPTION, e,
                "findPieVal");
        }
        
        return map;
    }
    
    @Override
    public Map<String, Object> findPieValMon()
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, "findPieVal");
        Map<String, Object> map = new HashMap<>();
        try {
            map.put("enp", this.wayBillStatisticalDao.countwlEnpMon(String.valueOf(Calendar.getInstance()
                    .get(Calendar.MONTH))));
            map.put("car", this.wayBillStatisticalDao.countCarMon(String.valueOf(Calendar.getInstance()
                    .get(Calendar.MONTH))));
            map.put("wbi",
                this.wayBillStatisticalDao.countWaybillMon(String.valueOf(Calendar.getInstance()
                    .get(Calendar.MONTH))));
            LOGGER.debug(SystemContants.DEBUG_END, map);
        } catch (Exception e) {
            throw new CustomException(CommonConstant.SYS_EXCEPTION, e,
                "findPieVal");
        }
        
        return map;
    }
    
    @Override
    public Map<String, Object> findPieValWeek()
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, "findPieVal");
        Map<String, Object> map = new HashMap<>();
        try {
            map.put("enp", this.wayBillStatisticalDao.countwlEnpWeek(String.valueOf(Calendar.getInstance()
                    .get(Calendar.WEEK_OF_YEAR))));
            map.put("car", this.wayBillStatisticalDao.countCarWeek(String.valueOf(Calendar.getInstance()
                    .get(Calendar.WEEK_OF_YEAR))));
            map.put("wbi",
                this.wayBillStatisticalDao.countWaybillWeek(String.valueOf(Calendar.getInstance()
                    .get(Calendar.WEEK_OF_YEAR))));
            LOGGER.debug(SystemContants.DEBUG_END, map);
        } catch (Exception e) {
            throw new CustomException(CommonConstant.SYS_EXCEPTION, e,
                "findPieVal");
        }
        
        return map;
    }
    @Override
    public List<WayBillStatisticalEntity> countAllByParams(Map<String, Object> map)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, map);
        List<WayBillStatisticalEntity> result = null;
        
        Calendar calendar = Calendar.getInstance();
        Date d = calendar.getTime();
        String year = calendar.get(Calendar.YEAR) + "";
        String mon = calendar.get(Calendar.MONTH) + 1 + "";
        String week = calendar.get(Calendar.WEEK_OF_YEAR) + "";
        String curDate = C503DateUtils.dateToStr(d, "yyyy-MM-dd HH:mm:ss");
        
        while (2 > mon.length()) {
            mon = 0 + mon;
        }
        
        map.put("curDate", curDate);
        map.put("curMon", year + "-" + mon);
        map.put("curWeek", week);
        map.put("curYear", year);
        
        try {
            result = this.wayBillStatisticalDao.countAllByParams(map);
        } catch (Exception e) {
            throw new CustomException(CommonConstant.SYS_EXCEPTION, e, map);
        }
        LOGGER.debug(SystemContants.DEBUG_END, map);
        
        return result;
    }
   
}
