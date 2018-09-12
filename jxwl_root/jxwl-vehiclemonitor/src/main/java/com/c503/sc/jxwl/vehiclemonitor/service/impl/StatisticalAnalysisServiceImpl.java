/**
 * 文件名：StatisticalAnalysisServiceImpl.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-8-8
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.vehiclemonitor.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.c503.sc.base.exception.CustomException;
import com.c503.sc.jxwl.common.bean.PageEntity;
import com.c503.sc.jxwl.common.constant.CommonConstant;
import com.c503.sc.jxwl.common.constant.SysCommonConstant;
import com.c503.sc.jxwl.vehiclemonitor.bean.AdministrativeAreaEntity;
import com.c503.sc.jxwl.vehiclemonitor.bean.CarLocationEntity;
import com.c503.sc.jxwl.vehiclemonitor.bean.EnpEveryTimes;
import com.c503.sc.jxwl.vehiclemonitor.bean.InOutAreaEntity;
import com.c503.sc.jxwl.vehiclemonitor.bean.PlatformEntity;
import com.c503.sc.jxwl.vehiclemonitor.bean.PtStatisticEntity;
import com.c503.sc.jxwl.vehiclemonitor.bean.VehicleAreaStatisticalEntity;
import com.c503.sc.jxwl.vehiclemonitor.dao.IAdministrativeAreaDao;
import com.c503.sc.jxwl.vehiclemonitor.dao.ICarLocationDao;
import com.c503.sc.jxwl.vehiclemonitor.dao.IDangerVehicleDao;
import com.c503.sc.jxwl.vehiclemonitor.dao.IInOutAreaDao;
import com.c503.sc.jxwl.vehiclemonitor.dao.IStatisticalAnalysisDao;
import com.c503.sc.jxwl.vehiclemonitor.service.IStatisticalAnalysisService;
import com.c503.sc.jxwl.vehiclemonitor.service.IplatformService;
import com.c503.sc.jxwl.zcpt.bean.AcrossLogEntity;
import com.c503.sc.jxwl.zcpt.bean.DangerVehicleEntity;
import com.c503.sc.jxwl.zcpt.bean.TerminalEntity;
import com.c503.sc.jxwl.zcpt.pointInPolygon.IsPointInPolygon;
import com.c503.sc.jxwl.zcpt.pointInPolygon.Point;
import com.c503.sc.jxwl.zcpt.service.ITerminalService;
import com.c503.sc.log.LoggingManager;
import com.c503.sc.log.resource.ResourceManager;
import com.c503.sc.utils.basetools.C503DateUtils;
import com.c503.sc.utils.basetools.C503StringUtils;
import com.c503.sc.utils.common.SystemContants;

/**
 * 〈一句话功能简述〉统计分析业务
 * 〈功能详细描述〉
 * 
 * @author xiaoqx
 * @version [版本号, 2016-8-8]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Service
public class StatisticalAnalysisServiceImpl implements
    IStatisticalAnalysisService {
    /** 日志器 */
    private static final LoggingManager LOGGER =
        LoggingManager.getLogger(StatisticalAnalysisServiceImpl.class);
    
    /** 平台信息接口 */
    @Resource
    private IStatisticalAnalysisDao statisticalAnalysisDao;
    
    @Resource(name = "administrativeAreaDao")
    private IAdministrativeAreaDao administrativeAreaDao;
    
    @Resource(name = "carLocationDao")
    private ICarLocationDao carLocationDao;
    
    /** 车辆进出区域信息Dao */
    @Resource(name = "inOutAreaDao")
    private IInOutAreaDao inOutAreaDao;
    
    /** 危险品车辆信息Dao */
    @Resource(name = "dangerVehicleDao")
    private IDangerVehicleDao dangerVehicleDao;
    
    /** 终端业务处理 */
    @Resource(name = "terminalService")
    private ITerminalService terminalService;
    
    @Resource(name = "platformService")
    private IplatformService platformService;
    
    /** 季度常量 */
    private static String[][] quarters = { {"01", "02", "03"},
        {"04", "05", "06"}, {"07", "08", "09"}, {"10", "11", "12"}};
    
    @Override
    public Object findPieVal()
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, "findPieVal");
        Map<String, Object> map = new HashMap<>();
        try {
            map.put("enp", this.statisticalAnalysisDao.countwlEnp());
            map.put("car", this.statisticalAnalysisDao.countCar());
            map.put("wbi",
                this.statisticalAnalysisDao.countWaybill(String.valueOf(Calendar.getInstance()
                    .get(Calendar.YEAR))));
            LOGGER.debug(SystemContants.DEBUG_END, map);
        }
        catch (Exception e) {
            throw new CustomException(CommonConstant.SYS_EXCEPTION, e,
                "findPieVal");
        }
        
        return map;
    }
    
    @Override
    public List<EnpEveryTimes> countAllByParams(Map<String, Object> map)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, map);
        List<EnpEveryTimes> result = null;
        
        Calendar calendar = Calendar.getInstance();
        Date d = calendar.getTime();
        String year = calendar.get(Calendar.YEAR) + "";
        String mon = calendar.get(Calendar.MONTH) + 1 + "";
        String curDate = C503DateUtils.dateToStr(d, "yyyy-MM-dd HH:mm:ss");
        String[] quarter = null;
        
        while (2 > mon.length()) {
            mon = 0 + mon;
        }
        
        for (int i = 0; i < quarters.length; i++) {
            String[] quarte = quarters[i];
            if (0 <= Arrays.binarySearch(quarte, mon)) {
                quarter = (String[]) ArrayUtils.clone(quarte);
                break;
            }
        }
        
        for (int i = 0; i < quarter.length; i++) {
            String quarmon = quarter[i];
            quarter[i] = year + "-" + quarmon;
        }
        
        map.put("curDate", curDate);
        map.put("curMon", year + "-" + mon);
        map.put("quarter", quarter);
        map.put("curYear", year);
        
        try {
            result = this.statisticalAnalysisDao.countAllByParams(map);
        }
        catch (Exception e) {
            throw new CustomException(CommonConstant.SYS_EXCEPTION, e, map);
        }
        for (EnpEveryTimes e : result) {
            String corporateNo = e.getCorporateNo();
            int installNum = 0;
            int carNum = 0;
            double installRate = 0.0;
            Map<String, String> map2 = new HashMap<String, String>();
            PageEntity<TerminalEntity> pageEntity =
                this.terminalService.findByParams(map2);
            carNum =
                dangerVehicleDao.getDangerVehicNumByCorporateNo(corporateNo);
            List<String> carNames =
                dangerVehicleDao.findCarNamesBycorporateNo(corporateNo);
            Collection<TerminalEntity> ters = pageEntity.getRows();
            for (TerminalEntity ter : ters) {
                String carName = ter.getCarrierName();
                if ((carName != null) && (carNames.contains(carName))) {
                    installNum += 1;
                }
            }
            if (carNum != 0) {
                installRate = div(installNum, carNum, 2);
            }
            else {
                installRate = 0.0;
            }
            if (installRate > 1.0) {
                // 终端安装率会存在大于1.0的情况，原因是有的车辆安装了不止一个终端
                installRate = 1.0;
            }
            String rate = String.valueOf((int) (installRate * 100)) + "%";
            e.setInstallRate(rate);
        }
        LOGGER.debug(SystemContants.DEBUG_END, map);
        
        return result;
    }
    
    @Override
    public List<?> countColumn(Map<String, Object> map)
        throws Exception {
        List<Map<String, Object>> listmap = null;
        
        String flag = (String) map.get("flag");
        // 在线、离线车辆
        if (StringUtils.equals(flag, "car")) {
            String curDate =
                C503DateUtils.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss");
            map.put("curDate", curDate);
            listmap = this.statisticalAnalysisDao.countCarCol(map);
            // 报警
        }
        else if (StringUtils.equals(flag, "alarm")) {
            listmap = this.statisticalAnalysisDao.countAlrCol(map);
            // 运单
        }
        else {
            listmap = this.statisticalAnalysisDao.countWbiCol(map);
        }
        
        return listmap;
    }
    
    /**
     * 统计各个行政区域的车辆数量
     * */
    @Override
    public List<VehicleAreaStatisticalEntity> findVehicleAreaStatistics()
        throws Exception {
        List<AdministrativeAreaEntity> areas = administrativeAreaDao.findAll();
        List<CarLocationEntity> carlocs = carLocationDao.findAll();
        List<VehicleAreaStatisticalEntity> list =
            new ArrayList<VehicleAreaStatisticalEntity>();
        for (AdministrativeAreaEntity area : areas) {
            BigDecimal totalCar = new BigDecimal(0);
            for (CarLocationEntity loc : carlocs) {
                Point locPoint =
                    new Point(
                        Double.parseDouble(loc.getLongitude().toString()),
                        Double.parseDouble(loc.getLatitude().toString()));
                JSONArray ps = JSON.parseArray(area.getPoints());
                int size = ps.size();
                ArrayList<Point> points = new ArrayList<Point>(size);
                for (int i = 0; i < size; i++) {
                    JSONArray p = ps.getJSONArray(i);
                    Point point = new Point(p.getDouble(0), p.getDouble(1));
                    points.add(point);
                }
                if (IsPointInPolygon.isInPolygon(locPoint, points)) {
                    totalCar = totalCar.add(new BigDecimal(1));// 车辆数量加1
                }
            }
            VehicleAreaStatisticalEntity statis =
                new VehicleAreaStatisticalEntity();
            statis.setAreaId(area.getAreaId());
            statis.setAreaName(area.getAreaName());
            statis.setTotalCar(totalCar);
            list.add(statis);
        }
        return list;
    }
    
    /**
     * 统计车辆跨域，进出区域（行政区域，限制区域，自定义区域）的情况，目前只统计行政区域
     * */
    @Override
    public List<InOutAreaEntity> findVehicleInOutAreaStatistics(
        Map<String, Object> map)
        throws Exception {
        // 区域类型为行政区域
        String areaType = AcrossLogEntity.ADMINISTRATIVE_AREA;
        map.put("areaType", areaType);
        List<InOutAreaEntity> list = inOutAreaDao.findByParams(map);
        // areaType = AcrossLogEntity.LIMIT_AREA;
        // list.addAll(inOutAreaDao.findAll(areaType));
        // areaType = AcrossLogEntity.USER_DEFINED_AREA;
        // list.addAll(inOutAreaDao.findAll(areaType));
        for (InOutAreaEntity entity : list) {
            if (entity.getOutTime() != null) {
                entity.setInOutStatus("已离开");
            }
            else {
                entity.setInOutStatus("已进入");
            }
        }
        return list;
    }
    
    /**
     * 平台统计信息
     * */
    @Override
    public List<PtStatisticEntity> findPtStatistics(Map<String, Object> map)
        throws Exception {
        List<PtStatisticEntity> ptsList = new ArrayList<PtStatisticEntity>();
        int jxPlatformJoinCarNum = 0;
        int thirdPlatformJoinCarNum = 0;
        int jxPlatformOnlineCarNum = 0;
        int thirdPlatformOnlineCarNum = 0;
        // int n = 0;
        // 获取所有平台
        List<PlatformEntity> ptAll = this.platformService.findByParams(map);
        // 获取所有车辆
        Map<String, Object> map2 = new HashMap<String, Object>();
        map2.put("curDate",
            C503DateUtils.dateToStr(new Date(), "yyyy-MM-dd hh:mm:ss"));
        List<DangerVehicleEntity> vehicleAll =
            this.dangerVehicleDao.findByParams(map2);
        for (DangerVehicleEntity lo : vehicleAll) {
            TerminalEntity terminalEntity =
                terminalService.findByCarrierName(lo.getName());
            if (terminalEntity != null) {
                String source = terminalEntity.getTerminalSource();
                String terminalSource = this.getTerminalSourceJx();
                if (C503StringUtils.isEmpty(source)
                    || C503StringUtils.equals(terminalSource, source)) {
                    // 车辆属于嘉兴位置服务平台
                    jxPlatformJoinCarNum += 1;
                    if ("0".equals(lo.getStatus())) {
                        jxPlatformOnlineCarNum += 1;
                    }
                }
                else {
                    thirdPlatformJoinCarNum += 1;
                    if ("0".equals(lo.getStatus())) {
                        thirdPlatformOnlineCarNum += 1;
                    }
                }
            }
        }
        for (PlatformEntity p : ptAll) {
            PtStatisticEntity pt = new PtStatisticEntity();
            pt.setName(p.getMainData());
            if ("位置服务平台".equals(p.getMainData())) {
                // 嘉兴位置服务平台
                pt.setIsOnline("在线");
                pt.setJoinCarNum(jxPlatformJoinCarNum);
                pt.setOnlineCarNum(jxPlatformOnlineCarNum);
            }
            else {
                // 第三方服务平台
                pt.setIsOnline("在线");
                pt.setJoinCarNum(thirdPlatformJoinCarNum);
                pt.setOnlineCarNum(thirdPlatformOnlineCarNum);
            }
            ptsList.add(pt);
        }
        return ptsList;
    }
    
    private double div(int a, int b, int scale) {
        // scale为小数点后精确的位数
        if (scale < 0) {
            throw new IllegalArgumentException("scale必须是正整数或者0");
        }
        BigDecimal b1 = new BigDecimal(a);
        BigDecimal b2 = new BigDecimal(b);
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
        
    }
    
    /**
     * 〈一句话功能简述〉获取终端来源-对应汉字
     * 〈功能详细描述〉
     * 
     * @return
     * @see [类、类#方法、类#成员]
     */
    private String getTerminalSourceJx() {
        String platName =
            ResourceManager.getMessage(SysCommonConstant.TERMINAL_SOURCE_JX);
        return platName;
    }
}
