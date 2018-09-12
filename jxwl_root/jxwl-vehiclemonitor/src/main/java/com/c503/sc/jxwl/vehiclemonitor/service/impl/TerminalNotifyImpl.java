//package com.c503.sc.jxwl.vehiclemonitor.service.impl;
//
//import java.math.BigDecimal;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.annotation.Resource;
//
//import org.apache.commons.lang.StringUtils;
//import org.springframework.stereotype.Service;
//import org.springframework.util.CollectionUtils;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import com.c503.sc.base.exception.CustomException;
//import com.c503.sc.dict.service.IDictionaryService;
//import com.c503.sc.dict.vo.DictDataVo;
//import com.c503.sc.jxwl.common.bean.AlarmEntity;
//import com.c503.sc.jxwl.common.bean.LocationEntity;
//import com.c503.sc.jxwl.common.constant.CommonConstant;
//import com.c503.sc.jxwl.common.constant.DictConstant;
//import com.c503.sc.jxwl.common.constant.NumberContant;
//import com.c503.sc.jxwl.common.service.IMiPushService;
//import com.c503.sc.jxwl.common.websocket.MonitorWebsocket;
//import com.c503.sc.jxwl.vehiclemonitor.bean.AcrossLogEntity;
//import com.c503.sc.jxwl.vehiclemonitor.bean.AdministrativeAreaEntity;
//import com.c503.sc.jxwl.vehiclemonitor.bean.DangerVehicleEntity;
//import com.c503.sc.jxwl.vehiclemonitor.bean.InOutAreaEntity;
//import com.c503.sc.jxwl.vehiclemonitor.dao.IDangerVehicleDao;
//import com.c503.sc.jxwl.vehiclemonitor.dao.IInOutAreaDao;
//import com.c503.sc.jxwl.vehiclemonitor.service.IAcrossLogService;
//import com.c503.sc.jxwl.vehiclemonitor.service.IAdministrativeAreaService;
//import com.c503.sc.jxwl.vehiclemonitor.service.IAlarmManageService;
//import com.c503.sc.jxwl.vehiclemonitor.service.IDangerVehicleService;
//import com.c503.sc.jxwl.zcpt.bean.Blacklist;
//import com.c503.sc.jxwl.zcpt.bean.BlacklistArg;
//import com.c503.sc.jxwl.zcpt.bean.LimitArea;
//import com.c503.sc.jxwl.zcpt.bean.SolveRoute;
//import com.c503.sc.jxwl.zcpt.bean.WayBillEntity;
//import com.c503.sc.jxwl.zcpt.dao.IAlarmManageDao;
//import com.c503.sc.jxwl.zcpt.dao.IAutoThrowBlacklistDao;
//import com.c503.sc.jxwl.zcpt.dao.ICarInfoDao;
//import com.c503.sc.jxwl.zcpt.dao.IShortcutDao;
//import com.c503.sc.jxwl.zcpt.dao.ISolveRouteDao;
//import com.c503.sc.jxwl.zcpt.pointInPolygon.IsPointInPolygon;
//import com.c503.sc.jxwl.zcpt.pointInPolygon.Point;
//import com.c503.sc.jxwl.zcpt.service.ITerminalNotify;
//import com.c503.sc.jxwl.zcpt.utils.GeometricAlgorithmUtils;
//import com.c503.sc.log.LoggingManager;
//import com.c503.sc.utils.basetools.C503DateUtils;
//import com.c503.sc.utils.basetools.C503StringUtils;
//import com.c503.sc.utils.common.SystemContants;
//import com.xiaomi.xmpush.server.Message;
//import com.xiaomi.xmpush.server.Result;
//
///**
// * 〈一句话功能简述〉TerminalNotifyImpl 〈功能详细描述〉
// * 
// * @author
// * @version [版本号, 2017-1-3]
// * @see [相关类/方法]
// * @since [产品/模块版本]
// */
//@Service("terminalNotify4Vehiclemonitor")
//public class TerminalNotifyImpl implements ITerminalNotify {
//    /** 日志器 */
//    private static final LoggingManager LOGGER =
//        LoggingManager.getLogger(TerminalNotifyImpl.class);
//    
//    /** 危险品车辆信息Dao */
//    @Resource(name = "dangerVehicleDao")
//    private IDangerVehicleDao dangerVehicleDao;
//    
//    /** dao */
//    @Resource(name = "carInfoDao")
//    private ICarInfoDao carInfoDao;
//    
//    /** 区域限制dao */
//    @Resource(name = "shortcutDao")
//    private IShortcutDao shortcutDao;
//    
//    /** 报警管理dao */
//    @Resource(name = "alarmManageDao")
//    private IAlarmManageDao alarmManageDao;
//    
//    /** 黑名单信息Dao */
//    @Resource(name = "autoThrowBlacklistDao")
//    private IAutoThrowBlacklistDao autoThrowBlacklistDao;
//    
//    /** 车辆进出区域信息Dao */
//    @Resource(name = "inOutAreaDao")
//    private IInOutAreaDao inOutAreaDao;
//    
//    /** 路径规划数据接口 */
//    @Resource(name = "solveRouteDao")
//    private ISolveRouteDao solveRouteDao;
//    
//    /** dangerVehicleService */
//    @Resource(name = "dangerVehicleService")
//    private IDangerVehicleService dangerVehicleService;
//    
//    /** dangerVehicleService */
//    @Resource(name = "alarmManageService")
//    private IAlarmManageService alarmManageService;
//    
//    @Resource(name = "acrossLogService")
//    private IAcrossLogService acrossLogService;
//    
//    @Resource(name = "administrativeAreaService")
//    private IAdministrativeAreaService administrativeAreaService;
//    
//    /** 操作数据字典的dictionaryService的实例 */
//    @Resource(name = "dictionaryService")
//    private IDictionaryService dictionaryService;
//    
//    /** miPushService的实例 */
//    @Resource(name = "miPushService")
//    private IMiPushService miPushService;
//    
//    @Override
//    public void notifyLocation(LocationEntity loc)
//        throws Exception {
//        try {
//            // 更新实时位置
//            // loc.setGpstime(new Date());
//            this.dangerVehicleService.updateRealLocation(loc);
//            // 行政区域进出记录
//            this.saveAcrossLog4AdministrativeArea(loc);
//            // 偏离路线报警
//            /* this.lineAlarm(loc); */
//            // 限制区域报警、区域进出记录、超速报警、超时停车报警
//            this.limitAreaAlarm(loc);
//            // 报警处理(保存报警信息到数据库，将报警信息推送给手机APP)
//            this.alarmHandle(loc);
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//    
//    // 测试驾驶员自动拉黑
//    // @Override
//    // @Scheduled(cron = "0/5 * * * * ?")
//    // public void notifyLocation() {
//    // try {
//    // LocationEntity loc = new LocationEntity();
//    // AlarmEntity alarmEntity = new AlarmEntity();
//    // alarmEntity.setId(C503StringUtils.createUUID());
//    // alarmEntity.setAlarmDate(new Date());
//    // alarmEntity.setCreateTime(new Date());
//    // alarmEntity.setCarrierName("浙YG4133");
//    // alarmEntity.setAlarmDetails("报警详情啊啊啊");
//    // List<AlarmEntity> alarms = new ArrayList<AlarmEntity>();
//    // alarms.add(alarmEntity);
//    // loc.setAlarms(alarms);
//    // loc.setCarrierName("浙YG4133");
//    // this.alarmHandle(loc);
//    // }
//    // catch (Exception e) {
//    // e.printStackTrace();
//    // }
//    // }
//    
//    /**
//     * 〈一句话功能简述〉是否进入限制区域 〈功能详细描述〉
//     * 
//     * @param loc
//     *            LocationEntity
//     * @return this
//     * @throws Exception
//     *             Exception
//     * @see [类、类#方法、类#成员]
//     */
//    private void limitAreaAlarm(LocationEntity loc)
//        throws Exception {
//        // 经度
//        String lng = loc.getLongitude();
//        // 纬度
//        String lat = loc.getLatitude();
//        /*
//         * lng = "-13662635.822752528"; lat = "-1661407.7896050988";
//         */
//        // 储存限制区域坐标点集
//        List<Point> pts = new ArrayList<Point>();
//        Point point =
//            new Point(Double.parseDouble(lng), Double.parseDouble(lat));
//        // 获取限制区域
//        List<LimitArea> limits = this.shortcutDao.findLimitAreas();
//        String points = null;
//        JSONArray pointArrs = null;
//        JSONArray ars = null;
//        double lng2 = 0;
//        double lat2 = 0;
//        Point point2 = null;
//        for (LimitArea limit : limits) {
//            pts.clear();
//            points = limit.getPoints();
//            if (C503StringUtils.isNotEmpty(points)) {
//                pointArrs = JSON.parseArray(points);
//                for (Object pointArr : pointArrs) {
//                    ars = JSONObject.parseArray(JSON.toJSONString(pointArr));
//                    lng2 = Double.parseDouble(String.valueOf(ars.get(0)));
//                    lat2 = Double.parseDouble(String.valueOf(ars.get(1)));
//                    point2 = new Point(lng2, lat2);
//                    pts.add(point2);
//                }
//                // 是否进入限制区域
//                this.isIntoLimitArea(limit, loc, point, pts);
//            }
//        }
//        this.notifyAlarmDealStatus(loc.getCarrierName());
//    }
//    
//    /**
//     * 〈一句话功能简述〉是否进入限制区域 〈功能详细描述〉
//     * 
//     * @param limit
//     *            LimitArea
//     * @param loc
//     *            LocationEntity
//     * @param point
//     *            Point
//     * @param pts
//     *            List<Point>
//     * @throws Exception
//     *             Exception
//     * @see [类、类#方法、类#成员]
//     */
//    private void isIntoLimitArea(LimitArea limit, LocationEntity loc,
//        Point point, List<Point> pts)
//        throws Exception {
//        String carrierName = loc.getCarrierName();
//        Date gpstime = loc.getGpstime();
//        if (IsPointInPolygon.isInPolygon(point, pts)) {
//            // 限制区域记录
//            saveAcrossLog(carrierName,
//                AcrossLogEntity.LIMIT_AREA,
//                limit.getId());
//            AlarmEntity alarm = new AlarmEntity();
//            // 进入限制区域类型
//            this.gointoLimitAreaType(limit,
//                alarm,
//                loc.getSpeed(),
//                loc.getCarrierName());
//            if (StringUtils.isNotEmpty(alarm.getAlarmType())) {
//                // 有报警信息
//                alarm.setId(C503StringUtils.createUUID());
//                alarm.setAlarmNo("B" + System.currentTimeMillis())
//                    .setTerminalId(loc.getTerminalID())
//                    .setAlarmDealStatus(DictConstant.ALARM_NOT_DEALED)
//                    .setAlarmDate(gpstime == null ? new Date() : gpstime)
//                    .setCreateTime(new Date());
//                alarm.setCarrierName(carrierName);
//                // 判断30分钟之内是否应经在当前限制区域已经报过警 因为车辆上下线的判断 是根据30分钟来定的
//                if (!hasAlarmInArea(alarm)) {
//                    // 暂时不用保存数据，有统一保存的地方
//                    loc.addAlarm(alarm);
//                }
//            }
//        }
//        else {
//            // 车辆没有驶入当前限制区域，则自动解除该车辆在该区域内之前的所有类型（包含限驶入、限停、限速）的报警信息
//            Map<String, Object> map = new HashMap<String, Object>();
//            map.put("carrierName", carrierName);
//            map.put("alarmDealStatus", DictConstant.ALARM_NOT_DEALED);
//            // map.put("alarmType", DictConstant.ALARM_AREA); //
//            // 驶出区域后要解除区域内所有3种类型的报警，而不是只解除限驶入一种类型的报警
//            map.put("limitAreaId", limit.getId());
//            List<AlarmEntity> alarms = alarmManageDao.findByParams(map);
//            if (!CollectionUtils.isEmpty(alarms)) {
//                for (AlarmEntity a : alarms) {
//                    a.setAlarmDealStatus(DictConstant.ALARM_DEALED);
//                    a.setAlarmRegisterHandle("自动解除报警");
//                    this.alarmManageDao.update(a);
//                }
//            }
//        }
//        
//    }
//    
//    /**
//     * 〈一句话功能简述〉进入限制区域类型 〈功能详细描述〉
//     * 
//     * @param limit
//     *            LimitArea
//     * @param alarm
//     *            AlarmEntity
//     * @param sp
//     *            当前车辆运行速度
//     * @throws Exception
//     * @see [类、类#方法、类#成员]
//     */
//    private void gointoLimitAreaType(LimitArea limit, AlarmEntity alarm,
//        String sp, String carrierName)
//        throws Exception {
//        String type = limit.getLimitType();
//        String liname = limit.getLimitName();
//        // 是否超速
//        if (StringUtils.equals(DictConstant.LIMIT_SPEED, type)) {
//            BigDecimal lisp = limit.getLimitSpeed();
//            if (StringUtils.isNotEmpty(sp) && null != lisp
//                && 0 < new BigDecimal(sp).compareTo(lisp)) {
//                alarm.setAlarmDetails("在" + liname + "区域超速");
//                alarm.setAlarmType(DictConstant.ALARM_OVERSPEED);
//                alarm.setLimitAreaId(limit.getId());// 保存报警产生时车辆所处的限制区域id
//            }
//            else {
//                // 不超速，则先去数据库查找是否之前有超速报警，如果有，则需要将该条报警信息状态更改为已处理，这样才能自动解除报警
//                Map<String, Object> map = new HashMap<String, Object>();
//                map.put("carrierName", carrierName);
//                map.put("alarmDealStatus", DictConstant.ALARM_NOT_DEALED);
//                map.put("alarmType", DictConstant.ALARM_OVERSPEED);
//                map.put("limitAreaId", limit.getId());
//                try {
//                    List<AlarmEntity> alarms = alarmManageDao.findByParams(map);
//                    if (!CollectionUtils.isEmpty(alarms)) {
//                        for (AlarmEntity a : alarms) {
//                            a.setAlarmDealStatus(DictConstant.ALARM_DEALED);
//                            a.setAlarmRegisterHandle("自动解除报警");
//                            alarmManageDao.update(a);
//                        }
//                    }
//                }
//                catch (Exception e) {
//                    throw new Exception("没有查找到当前车辆的状态为未处理的超速报警信息！");
//                }
//            }
//            
//        }
//        // 是否限停
//        else if (StringUtils.equals(DictConstant.LIMIT_STOP, type)) {
//            if (new BigDecimal(sp).compareTo(new BigDecimal(0)) <= 0) {
//                alarm.setAlarmDetails("在" + liname + "区域限停");
//                alarm.setAlarmType(DictConstant.ALARM_OVERTIME_PARK);
//                alarm.setLimitAreaId(limit.getId());// 保存报警产生时车辆所处的限制区域id
//            }
//            else {
//                // 在限停区域内速度大于0，则先去数据库查找是否之前有限停报警，如果有，则需要将该条报警信息状态更改为已处理，这样才能自动解除报警
//                Map<String, Object> map = new HashMap<String, Object>();
//                map.put("carrierName", carrierName);
//                map.put("alarmDealStatus", DictConstant.ALARM_NOT_DEALED);
//                map.put("alarmType", DictConstant.ALARM_OVERTIME_PARK);
//                map.put("limitAreaId", limit.getId());
//                try {
//                    List<AlarmEntity> alarms = alarmManageDao.findByParams(map);
//                    if (!CollectionUtils.isEmpty(alarms)) {
//                        for (AlarmEntity a : alarms) {
//                            a.setAlarmDealStatus(DictConstant.ALARM_DEALED);
//                            a.setAlarmRegisterHandle("自动解除报警");
//                            alarmManageDao.update(a);
//                        }
//                    }
//                }
//                catch (Exception e) {
//                    throw new Exception("没有查找到当前车辆的状态为未处理的限停报警信息！");
//                }
//                
//            }
//            
//        }
//        // 是否限驶入
//        else if (StringUtils.equals(DictConstant.LIMIT_DRIVE_IN, type)) {
//            alarm.setAlarmDetails("在" + liname + "区域限驶入");
//            alarm.setAlarmType(DictConstant.ALARM_AREA);
//            alarm.setLimitAreaId(limit.getId());// 保存报警产生时车辆所处的限制区域id
//        }
//    }
//    
//    /**
//     * 〈一句话功能简述〉判断该车是否在30分钟之内是否应经在当前限制区域已经报过警 〈功能详细描述〉
//     * 
//     * @param alarm
//     *            AlarmEntity
//     * @return true:30分钟内已经报过警; fase:没报过警
//     * @throws Exception
//     *             Exception
//     * @see [类、类#方法、类#成员]
//     */
//    private boolean hasAlarmInArea(AlarmEntity alarm)
//        throws Exception {
//        boolean flag = false;
//        List<String> alarmTime =
//            this.alarmManageDao.findLastAlarmByCarrierName(alarm.getCarrierName(),
//                alarm.getAlarmType(),
//                alarm.getLimitAreaId());
//        if (alarmTime != null && alarmTime.size() != 0) {
//            long dbMillis =
//                C503DateUtils.strToDate(alarmTime.get(0), "yyyy-MM-dd HH:mm:ss")
//                    .getTime();
//            long curMillis = System.currentTimeMillis();
//            if (curMillis - dbMillis < NumberContant.THREE * NumberContant.TEN
//                * NumberContant.SIXTY * NumberContant.ONE_THOUSAND) {
//                flag = true;
//            }
//        }
//        
//        return flag;
//    }
//    
//    /**
//     * 〈一句话功能简述〉报警处理 〈功能详细描述〉
//     * 
//     * @param loc
//     *            LocationEntity
//     * @throws Exception
//     *             Exception
//     * @see [类、类#方法、类#成员]
//     */
//    private void alarmHandle(LocationEntity loc)
//        throws Exception {
//        LOGGER.debug(SystemContants.DEBUG_START, loc);
//        List<AlarmEntity> alarms = loc.getAlarms();
//        if (alarms == null) {
//            LOGGER.debug(SystemContants.DEBUG_START, "无报警信息");
//            return;
//        }
//        // 补充报警信息
//        addAlarmInfo(loc, alarms);
//        // 保存报警信息
//        this.alarmManageService.saveByBatch(alarms);
//        // 推送报警信息至app端
//        final List<AlarmEntity> alarms2 = new ArrayList<AlarmEntity>(alarms);
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    miPushAlarm(alarms2);
//                }
//                catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//        
//        // 拉黑操作
//        // 获取自动拉黑设置的次数
//        BlacklistArg blacklistArg = this.autoThrowBlacklistDao.findAlarmOne();
//        // 车辆
//        int vehicleAlarmNum = blacklistArg.getVehicleAlarmNum().intValue();
//        // 驾驶员
//        int driverAlarmNum = blacklistArg.getDriverAlarmNum().intValue();
//        
//        // 报警处理：车辆
//        // 是否已被拉黑
//        String carrierName = loc.getCarrierName();
//        if (carrierName != null) {
//            String carPlateNo =
//                this.autoThrowBlacklistDao.findHasInBlacklist(carrierName);
//            // 不在黑名单中
//            if (StringUtils.isEmpty(carPlateNo)) {
//                // 查询报警次数
//                int times = 0;
//                Date maxDate1 =
//                    this.autoThrowBlacklistDao.findMaxDate4Vehicle(carrierName);
//                Date maxDate2 = blacklistArg.getUpdateTime();
//                // 每次更新自动拉黑次数后报警时间从此时开始计算
//                Date maxDate =
//                    maxDate1 == null ? maxDate2
//                        : maxDate1.getTime() > maxDate2.getTime() ? maxDate1
//                            : maxDate2;
//                String maxDateStr = null;
//                if (maxDate != null) {
//                    SimpleDateFormat sdf =
//                        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                    maxDateStr = sdf.format(maxDate);
//                }
//                Map<String, String> map = new HashMap<String, String>();
//                map.put("carrierName", carrierName);
//                map.put("maxDate", maxDateStr);
//                times = this.autoThrowBlacklistDao.findAlarmTimes4Vehicle(map);
//                // 报警次数大于自动拉黑设置的次数
//                if (times >= vehicleAlarmNum) {
//                    this.blackListSave(loc, null, null);
//                }
//            }
//            // 报警处理：驾驶员 运单状态处于运货或取货时，驾驶员拉黑
//            // 判别运单状态：根据车牌号 查询
//            List<WayBillEntity> wayBills =
//                this.carInfoDao.findWayBillData(carrierName);
//            if (!CollectionUtils.isEmpty(wayBills)) {
//                WayBillEntity wayBillEntity = wayBills.get(0);
//                if (wayBillEntity != null) {
//                    // 因为wayBillEntity中有两个Driverid，而且它们大小写不一样，但是调试发现数据库底层查询出来的字段只会set到声明在前的那个driverid,
//                    // 也就是driverId而不是driverid
//                    // String driverId = wayBillEntity.getDriverid();
//                    String driverId = wayBillEntity.getDriverId();
//                    // 通过身份证查询该驾驶员是否在黑名单中
//                    String cardNo =
//                        this.autoThrowBlacklistDao.findHasInBlack4Driver(driverId);
//                    String driver = wayBillEntity.getDriver();
//                    // 不在黑名单中
//                    if (StringUtils.isEmpty(cardNo)) {
//                        // 查询报警次数
//                        int times = 0;
//                        Date maxDate1 =
//                            this.autoThrowBlacklistDao.findMaxDate4Driver(driver);
//                        Date maxDate2 = blacklistArg.getUpdateTime();
//                        Date maxDate =
//                            maxDate1 == null ? maxDate2
//                                : maxDate1.getTime() > maxDate2.getTime() ? maxDate1
//                                    : maxDate2;
//                        String maxDateStr = null;
//                        if (maxDate != null) {
//                            SimpleDateFormat sdf =
//                                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                            maxDateStr = sdf.format(maxDate);
//                        }
//                        Map<String, String> map = new HashMap<String, String>();
//                        map.put("driverId", driverId);
//                        map.put("maxDate", maxDateStr);
//                        times =
//                            this.autoThrowBlacklistDao.findAlarmTimes4Driver(map);
//                        // 报警次数大于自动拉黑设置的次数
//                        if (times >= driverAlarmNum) {
//                            this.blackListSave(loc, driverId, driver);
//                        }
//                    }
//                }
//            }
//        }
//        
//    }
//    
//    /**
//     * 〈一句话功能简述〉自动拉黑处理 〈功能详细描述〉
//     * 
//     * @param loc
//     *            LocationEntity
//     * @param driverId
//     *            driverId
//     * @param driver
//     *            driver
//     * @throws Exception
//     *             Exception
//     * @see [类、类#方法、类#成员]
//     */
//    private void blackListSave(LocationEntity loc, String driverId,
//        String driver)
//        throws Exception {
//        // 报警车辆信息
//        String carrierName = loc.getCarrierName();
//        DangerVehicleEntity dangerVehicleEntity =
//            this.dangerVehicleDao.findByName(carrierName);
//        if (dangerVehicleEntity != null) {
//            
//            String corporateNo = dangerVehicleEntity.getCorporateNo();
//            Blacklist blacklist = new Blacklist();
//            // 封装拉黑实体对象
//            blacklist.setId(C503StringUtils.createUUID());
//            blacklist.setCorporateNo(corporateNo);
//            // 拉黑类型为自动拉黑
//            blacklist.setBlacklistType("104002002");
//            blacklist.setBlacklistDate(new Date());
//            if (driverId == null) {
//                // 被来黑对象类型：车辆
//                blacklist.setObjectType("104001003");
//                blacklist.setVehicle(carrierName);
//            }
//            else {
//                // 被来黑对象类型：驾驶员
//                blacklist.setObjectType("104001002");
//                blacklist.setDriverId(driverId);
//                blacklist.setDriver(driver);
//            }
//            blacklist.setBlacklistReason("报警次数到达自动拉黑设置次数");
//            blacklist.setRemove("0");
//            Date curDate = new Date();
//            blacklist.setCreateTime(curDate);
//            blacklist.setUpdateTime(curDate);
//            Blacklist bl = autoThrowBlacklistDao.findExsist(carrierName);
//            if (driverId == null && bl == null) {
//                // 车辆拉黑记录保存
//                this.autoThrowBlacklistDao.save(blacklist);
//            }
//            else if (driverId != null) {
//                // 驾驶员拉黑记录保存
//                this.autoThrowBlacklistDao.save(blacklist);
//            }
//        }
//    }
//    
//    /**
//     * 〈一句话功能简述〉小米推送报警〈功能详细描述〉
//     * 
//     * @param alarms List<AlarmEntity>
//     * @throws Exception Exception
//     * @see [类、类#方法、类#成员]
//     */
//    private void miPushAlarm(List<AlarmEntity> alarms)
//        throws Exception {
//        if (alarms == null) {
//            CustomException ce =
//                new CustomException(CommonConstant.SYS_EXCEPTION, "无报警信息");
//            ce.setErrorMessage("无报警信息");
//            LOGGER.error(CommonConstant.SYS_EXCEPTION, ce, alarms);
//            throw ce;
//        }
//        // 根据数据字典得到报警类型中文
//        String alarmCode = "130001";
//        List<DictDataVo> dictResult = new ArrayList<DictDataVo>();
//        try {
//            dictResult =
//                (ArrayList<DictDataVo>) this.dictionaryService.findDictFromDB(alarmCode);
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
//        for (int i = 0; i < alarms.size(); i++) {
//            AlarmEntity alarm = alarms.get(i);
//            // 如果驾驶员为空，则不推送报警信息给app！不论是一键呼叫报警信息还是车辆报警信息
//            if (C503StringUtils.isEmpty(alarm.getDriverId())) {
//                return;
//            }
//            String alarmType = alarm.getAlarmType();
//            for (int j = 0; j < dictResult.size(); j++) {
//                DictDataVo dict = dictResult.get(j);
//                if (dict.getCode().equals(alarmType)) {
//                    alarm.setAlarmType(dict.getName());
//                    break;
//                }
//            }
//            // 封装小米推送的message数据
//            String title = alarm.getAlarmType();
//            String des = alarm.getAlarmDetails();
//            // 根据身份证 查询驾驶员人名 和app账号 以及regId
//            Map<String, String> accountMap =
//                this.alarmManageDao.findRegId(alarm.getDriverId(),
//                    DictConstant.ACCOUNT_VERIFY_PASS);
//            if (accountMap == null) {
//                CustomException ce =
//                    new CustomException(CommonConstant.SYS_EXCEPTION,
//                        "alarm.getDriverId()=" + alarm.getDriverId());
//                ce.setErrorMessage("未查询到账号信息");
//                LOGGER.error(CommonConstant.SYS_EXCEPTION, ce, alarm);
//                throw ce;
//            }
//            if (accountMap.get("account") != null) {
//                alarm.setDriver(accountMap.get("account"));
//            }
//            String regId = null;
//            if (accountMap.get("regid") != null) {
//                regId = accountMap.get("regid");
//            }
//            // 得到messagePayload
//            Map<String, Object> map = this.getMsg(alarm);
//            String messagePayload = this.miPushService.getPayload(map, "1");
//            // 得到message
//            Message message = null;
//            
//            try {
//                message =
//                    this.miPushService.messageHandle(title,
//                        des,
//                        messagePayload,
//                        false);
//                LOGGER.info(SystemContants.DEBUG_START, "message: " + message
//                    + " regId: " + regId);
//                // 发送message 并得到result
//                Result result =
//                    this.miPushService.sendByRegId(message,
//                        regId,
//                        NumberContant.FIVE);
//                LOGGER.info(SystemContants.DEBUG_START, "message: s" + result);
//            }
//            catch (Exception e) {
//                e.printStackTrace();
//            }
//            
//        }
//    }
//    
//    /**
//     * 〈一句话功能简述〉封装msg 〈功能详细描述〉
//     * 
//     * @param alarm
//     *            AlarmEntity
//     * @return Map<String, Object>
//     * @see [类、类#方法、类#成员]
//     */
//    private Map<String, Object> getMsg(AlarmEntity alarm) {
//        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("state", "1");
//        map.put("user", alarm.getDriver());
//        map.put("des", alarm.getAlarmDetails());
//        // 托运单号
//        map.put("ticketNum", alarm.getWaybillNo());
//        map.put("alarmType", alarm.getAlarmType());
//        map.put("carNum", alarm.getCarrierName());
//        map.put("alarmTime",
//            C503DateUtils.dateToStr(alarm.getAlarmDate(), "YYYY-MM-dd HH:mm:ss"));
//        return map;
//    }
//    
//    /**
//     * 保存跨域记录
//     * 
//     * @param carrierName 车牌
//     * @param areaType 区域类型
//     * @param areaId 区域ID
//     * @throws Exception
//     */
//    private void saveAcrossLog(String carrierName, String areaType,
//        String areaId)
//        throws Exception {
//        if (C503StringUtils.isEmpty(carrierName)) {
//            LOGGER.error(0, new NullPointerException("保存跨域记录：carrierName="
//                + carrierName), carrierName);
//            return;
//        }
//        if (C503StringUtils.isEmpty(areaType)) {
//            LOGGER.error(0, new NullPointerException("保存跨域记录：areaType="
//                + areaType), areaType);
//            return;
//        }
//        if (C503StringUtils.isEmpty(areaId)) {
//            LOGGER.error(0,
//                new NullPointerException("保存跨域记录：areaId=" + areaId),
//                areaId);
//            return;
//        }
//        AcrossLogEntity lastAcrosslog =
//            acrossLogService.getLastAcrossLog(carrierName, areaType);
//        LOGGER.debug(0, "获取车辆最新的跨域记录：" + lastAcrosslog);
//        if (lastAcrosslog == null) {
//            lastAcrosslog = new AcrossLogEntity();
//            lastAcrosslog.setId(C503StringUtils.createUUID());
//            lastAcrosslog.setAreaId(areaId);
//            lastAcrosslog.setAreaType(areaType);
//            lastAcrosslog.setCarrierName(carrierName);
//            lastAcrosslog.setLogTime(new Date());
//            acrossLogService.save(lastAcrosslog);
//            InOutAreaEntity inOutAreaEntity = new InOutAreaEntity();
//            inOutAreaEntity.setId(C503StringUtils.createUUID());
//            inOutAreaEntity.setAreaId(areaId);
//            inOutAreaEntity.setAreaType(areaType);
//            inOutAreaEntity.setCarrierName(carrierName);
//            // 保存进入区域时间
//            inOutAreaEntity.setInTime(new Date());
//            inOutAreaEntity.setLogTime(new Date()); // new
//                                                    // Date(System.currentTimeMillis())
//            inOutAreaEntity.setInOutStatus("已进入");
//            inOutAreaDao.save(inOutAreaEntity);
//        }
//        else {
//            String dbAreaId = lastAcrosslog.getAreaId();
//            if (!areaId.equals(dbAreaId)) {
//                // 跨域了
//                lastAcrosslog.setId(C503StringUtils.createUUID());
//                lastAcrosslog.setAreaId(areaId);
//                lastAcrosslog.setPreAreaId(dbAreaId);
//                lastAcrosslog.setLogTime(new Date());
//                lastAcrosslog.setAreaType(areaType);
//                lastAcrosslog.setCarrierName(carrierName);
//                acrossLogService.save(lastAcrosslog);
//                InOutAreaEntity inOutAreaEntity = new InOutAreaEntity();
//                inOutAreaEntity.setAreaId(dbAreaId);
//                inOutAreaEntity.setCarrierName(carrierName);
//                // 保存上一个区域的离开区域时间
//                inOutAreaEntity.setOutTime(new Date());
//                inOutAreaEntity.setLogTime(new Date());
//                inOutAreaEntity.setInOutStatus("已离开");
//                inOutAreaDao.update(inOutAreaEntity);
//                
//                InOutAreaEntity inOutAreaEntity2 = new InOutAreaEntity();
//                inOutAreaEntity2.setId(C503StringUtils.createUUID());
//                inOutAreaEntity2.setAreaId(areaId);
//                inOutAreaEntity2.setAreaType(areaType);
//                inOutAreaEntity2.setCarrierName(carrierName);
//                // 保存下一个区域的进入区域时间
//                inOutAreaEntity2.setInTime(new Date());
//                inOutAreaEntity2.setLogTime(new Date());
//                inOutAreaEntity2.setInOutStatus("已进入");
//                inOutAreaDao.save(inOutAreaEntity2);
//            }
//            else {
//                LOGGER.debug(0, "进入区域，但没有跨域，且已经有记录了，不操作");
//            }
//        }
//    }
//    
//    /**
//     * 添加跨域记录（行政区域）
//     * 
//     * @param loc
//     * @throws Exception
//     */
//    private void saveAcrossLog4AdministrativeArea(LocationEntity loc)
//        throws Exception {
//        if (loc != null) {
//            String longitudeStr = loc.getLongitude();
//            String latitudeStr = loc.getLatitude();
//            String carrierName = loc.getCarrierName();
//            List<AdministrativeAreaEntity> areas =
//                administrativeAreaService.findAll();
//            if (!CollectionUtils.isEmpty(areas)) {
//                Point locPoint = null;
//                JSONArray ps = null;
//                String areaId = null;
//                for (AdministrativeAreaEntity area : areas) {
//                    locPoint =
//                        new Point(Double.parseDouble(longitudeStr),
//                            Double.parseDouble(latitudeStr));
//                    ps = JSON.parseArray(area.getPoints());
//                    int size = ps.size();
//                    List<Point> points = new ArrayList<Point>(size);
//                    JSONArray p = null;
//                    Point point = null;
//                    Double longitude = null;
//                    Double latitude = null;
//                    for (int i = 0; i < size; i++) {
//                        p = (JSONArray) ps.get(i);
//                        longitude = p.getDouble(0);
//                        latitude = p.getDouble(1);
//                        point = new Point(longitude, latitude);
//                        points.add(point);
//                    }
//                    if (IsPointInPolygon.isInPolygon(locPoint, points)) {
//                        areaId = area.getAreaId();
//                        // 在此区域内
//                        saveAcrossLog(carrierName,
//                            AcrossLogEntity.ADMINISTRATIVE_AREA,
//                            areaId);
//                    }
//                }
//            }
//        }
//    }
//    
//    /**
//     * 偏离路线报警
//     * 
//     * @param loc 位置
//     * @throws Exception
//     */
//    private void lineAlarm(LocationEntity loc)
//        throws Exception {
//        String carrierName = loc.getCarrierName();
//        String longitudeStr = loc.getLongitude();
//        String latitudeStr = loc.getLatitude();
//        Point actualPoint =
//            new Point(Double.parseDouble(longitudeStr),
//                Double.parseDouble(latitudeStr));
//        List<WayBillEntity> bills =
//            this.carInfoDao.findWayBillData(carrierName);
//        WayBillEntity bill = null;
//        if (!CollectionUtils.isEmpty(bills)) {
//            bill = bills.get(0);
//            if (bill != null) {
//                // 获取运单的规划线路，对线路进行匹配,然后报警
//                // 线路规划是针对园区的，所以偏离线路报警也要在园区内
//                // 运单开始运输后才会规划路径，所以运单运输过程中，才会有偏离路线报警
//                String billStatus = bill.getCheckstatus();
//                if (!C503StringUtils.isEmpty(billStatus)) {
//                    if (!C503StringUtils.equals(DictConstant.WAYBILL_FINISH,
//                        billStatus)) {
//                        String billId = bill.getId();
//                        SolveRoute route =
//                            this.solveRouteDao.findRouteByWaybillId(billId);
//                        if (null != route) {
//                            String pointStr = route.getPoints();
//                            if (C503StringUtils.isNotEmpty(pointStr)) {
//                                JSONArray pointArrs = JSON.parseArray(pointStr);
//                                int size = pointArrs.size();
//                                List<Point[]> lineSegments =
//                                    new ArrayList<Point[]>(size - 1);
//                                Point[] lineSegment = new Point[2];
//                                JSONArray pointObj = null;
//                                Double lng;
//                                Double lat;
//                                Point point = null;
//                                JSONArray nextPointObj = null;
//                                Double nextLng;
//                                Double nextLat;
//                                Point NextPoint = null;
//                                for (int i = 0; i < size; i++) {
//                                    pointObj = (JSONArray) pointArrs.get(i);
//                                    lng = pointObj.getDouble(0);
//                                    lat = pointObj.getDouble(1);
//                                    point = new Point(lng, lat);
//                                    for (int j = 1; j < size; j++) {
//                                        nextPointObj =
//                                            (JSONArray) pointArrs.get(j);
//                                        nextLng = nextPointObj.getDouble(0);
//                                        nextLat = nextPointObj.getDouble(1);
//                                        NextPoint = new Point(nextLng, nextLat);
//                                        lineSegment[0] = point;
//                                        lineSegment[1] = NextPoint;
//                                        lineSegments.add(lineSegment);
//                                    }
//                                }
//                                
//                                // 判断点到直线距离
//                                int lineSize = lineSegments.size();
//                                Point[] tmpLineSegment = null;
//                                double[] distance;
//                                for (int i = 0; i < lineSize; i++) {
//                                    tmpLineSegment = lineSegments.get(i);
//                                    distance =
//                                        GeometricAlgorithmUtils.vector(actualPoint,
//                                            tmpLineSegment);
//                                    double shortDistance = distance[1];
//                                    // 如果大于100米，则新增报警信息
//                                    if (shortDistance > 100) {
//                                        // 报警
//                                        Date gpstime = loc.getGpstime();
//                                        String terminalID = loc.getTerminalID();
//                                        String simNum = loc.getSimNum();
//                                        
//                                        AlarmEntity alarm = new AlarmEntity();
//                                        alarm.setId(C503StringUtils.createUUID());
//                                        alarm.setAlarmDealStatus(DictConstant.ALARM_NOT_DEALED);
//                                        alarm.setAlarmType(DictConstant.ALARM_ROUTE_DEVIATE);
//                                        alarm.setAlarmDate(gpstime);
//                                        alarm.setCarrierName(carrierName);
//                                        alarm.setTerminalId(terminalID);
//                                        alarm.setTelephone(simNum);
//                                        alarm.setCreateTime(new Date());
//                                        alarm.setUpdateTime(new Date());
//                                        this.alarmManageDao.save(alarm);
//                                        // 推送至前端
//                                        MonitorWebsocket.notifyAlarm(alarm);
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
//        
//    }
//    
//    private void notifyAlarmDealStatus(String carrierName)
//        throws Exception {
//        // 去后台查询当前车辆的剩余的报警个数
//        // 如果报警个数为0，则推送前端，否则，不推送
//        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("carrierName", carrierName);
//        int result = this.alarmManageDao.findAlarmNumForDiTu(map);
//        AlarmEntity alarmEntity = new AlarmEntity();
//        alarmEntity.setAlarmDealStatus("1");
//        alarmEntity.setCarrierName(carrierName);
//        if (result == 0) {
//            alarmEntity.setAlarmDealStatus("0");
//            MonitorWebsocket.notifyAlarmDealStatus(alarmEntity);
//        }
//    }
//    
//    /**
//     * 〈一句话功能简述〉填充报警信息：企业、运单、驾驶员信息 〈功能详细描述〉
//     * 
//     * @param loc
//     *            LocationEntity
//     * @param alarms
//     *            List<AlarmEntity>
//     * @throws Exception
//     * @see [类、类#方法、类#成员]
//     */
//    private void addAlarmInfo(LocationEntity loc, List<AlarmEntity> alarms)
//        throws Exception {
//        if (alarms == null) {
//            LOGGER.debug(SystemContants.DEBUG_START, "无报警信息");
//            return;
//        }
//        String carrierName = loc.getCarrierName();
//        String corporateNo = null;
//        String enterpriseName = null;
//        DangerVehicleEntity car = this.dangerVehicleDao.findByName(carrierName);
//        LOGGER.info(CommonConstant.FIND_SUC_OPTION, car);
//        if (car != null) {
//            // 企业信息
//            corporateNo = car.getCorporateNo();
//            enterpriseName = car.getEnterpriseName();
//        }
//        Map<String, Object> map = this.gwaybillNo(carrierName);
//        String waybillNo = (String) map.get("waybillNo");
//        String driverId = (String) map.get("driverId");
//        Date alarmDate = null;
//        Date gpstime = null;
//        Date nowTime = new Date();
//        for (AlarmEntity alarm : alarms) {
//            if (alarm != null) {
//                gpstime = loc.getGpstime();
//                alarmDate = gpstime == null ? nowTime : gpstime;
//                // 补充企业
//                alarm.setEnterpriseName(enterpriseName);
//                alarm.setCarrierName(carrierName);
//                alarm.setCorporateNo(corporateNo);
//                alarm.setWaybillNo(waybillNo);
//                alarm.setDriverId(driverId);
//                alarm.setCreateTime(new Date());
//                alarm.setUpdateTime(new Date());
//                alarm.setAlarmDate(alarmDate);
//            }
//        }
//    }
//    
//    /**
//     * 〈一句话功能简述〉gwaybillNo 〈功能详细描述〉
//     * 
//     * @param carrierName
//     *            carrierName
//     * @return Map<String, Object>
//     * @see [类、类#方法、类#成员]
//     */
//    private Map<String, Object> gwaybillNo(String carrierName) {
//        Map<String, Object> map = new HashMap<>();
//        // 运单信息
//        WayBillEntity wayBilldata = null;
//        try {
//            List<WayBillEntity> wayBills =
//                this.carInfoDao.findWayBillData(carrierName);
//            if (!CollectionUtils.isEmpty(wayBills)) {
//                wayBilldata =
//                    this.carInfoDao.findWayBillData(carrierName).get(0);
//                LOGGER.info(CommonConstant.FIND_SUC_OPTION, wayBilldata);
//            }
//            if (null != wayBilldata) {
//                // 货单号
//                map.put("waybillNo", wayBilldata.getCheckno());
//                // 驾驶员id
//                map.put("driverId", wayBilldata.getDriverId());
//                // 驾驶员手机
//                map.put("driverTel", wayBilldata.getDriverphone());
//            }
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
//        return map;
//    }
//    
//}
