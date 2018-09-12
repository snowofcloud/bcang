///**
// * 文件名：AccessPlatformServiceTest.java
// * 版权： 航天恒星科技有限公司
// * 描述：〈描述〉
// * 修改时间：2016-7-26
// * 修改内容：〈修改内容〉
// */
//package com.c503.sc.jxwl.vehiclemonitor.service;
//
//import java.lang.reflect.Method;
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.annotation.Resource;
//
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.powermock.api.mockito.PowerMockito;
//
//import com.c503.sc.jxwl.common.bean.AlarmEntity;
//import com.c503.sc.jxwl.common.bean.LocationEntity;
//import com.c503.sc.jxwl.common.constant.DictConstant;
//import com.c503.sc.jxwl.vehiclemonitor.base.BaseTest;
//import com.c503.sc.jxwl.vehiclemonitor.bean.DangerVehicleEntity;
//import com.c503.sc.jxwl.vehiclemonitor.dao.IDangerVehicleDao;
//import com.c503.sc.jxwl.vehiclemonitor.service.impl.TerminalNotifyImpl;
//import com.c503.sc.jxwl.zcpt.bean.BlacklistArg;
//import com.c503.sc.jxwl.zcpt.bean.LimitArea;
//import com.c503.sc.jxwl.zcpt.bean.WayBillEntity;
//import com.c503.sc.jxwl.zcpt.dao.IAlarmManageDao;
//import com.c503.sc.jxwl.zcpt.dao.IAutoThrowBlacklistDao;
//import com.c503.sc.jxwl.zcpt.dao.ICarInfoDao;
//import com.c503.sc.jxwl.zcpt.dao.IShortcutDao;
//import com.c503.sc.jxwl.zcpt.pointInPolygon.Point;
//import com.c503.sc.jxwl.zcpt.service.ITerminalNotify;
//
//public class TerminalNotifyServiceTest extends BaseTest {
//    @InjectMocks
//    @Resource(name = "terminalNotify4Vehiclemonitor")
//    private ITerminalNotify terminalNotify;
//    
//    LocationEntity loc = new LocationEntity();
//    
//    AlarmEntity alarm = new AlarmEntity();
//    
//    List<AlarmEntity> alarms = new ArrayList<AlarmEntity>();
//    
//    Class<TerminalNotifyImpl> terminalNotifyImp = TerminalNotifyImpl.class;
//    
//    /** 区域限制dao */
//    @Mock
//    @Resource(name = "shortcutDao")
//    private IShortcutDao shortcutDao;
//    
//    /** 黑名单信息Dao */
//    @Mock
//    @Resource(name = "autoThrowBlacklistDao")
//    private IAutoThrowBlacklistDao autoThrowBlacklistDao;
//    
//    /** dao */
//    @Mock
//    @Resource(name = "carInfoDao")
//    private ICarInfoDao carInfoDao;
//    
//    /** 报警管理dao */
//    @Mock
//    @Resource(name = "alarmManageDao")
//    private IAlarmManageDao alarmManageDao;
//    
//    @Mock
//    @Resource(name = "dangerVehicleDao")
//    private IDangerVehicleDao dangerVehicleDao;
//    
//    String lng = "285415.4803196717";
//    
//    String lat = "207867.74709661846";
//    
//    @Before
//    public void setup()
//        throws Exception {
//        MockitoAnnotations.initMocks(this);
//        
//        loc.setLongitude(lng);
//        loc.setLatitude(lat);
//        String carrierName = "黑A12345";
//        loc.setCarrierName(carrierName);
//        loc.setSimNum("18328344321");
//        loc.setGpstime(new Date());
//        loc.setTemperature(30 + ".5");
//        loc.setPressure(80 + ".6");
//        loc.setLiquidLevel(30 + ".7");
//        loc.setTirePressure(60 + ".8");
//        loc.setOnlineStatus("1");
//        loc.setSpeed(120 + ".9");
//        loc.setAlarms(alarms);
//        loc.setTerminalID("1324657980");
//        
//        alarm.setCarrierName(carrierName);
//        alarm.setAlarmType("103001004");
//    }
//    
//    @Test
//    public void testNotifyLocation()
//        throws Exception {
//        this.terminalNotify.notifyLocation(loc);
//        AlarmEntity entity = new AlarmEntity();
//        alarms.add(entity);
//        loc.setAlarms(alarms);
//        this.terminalNotify.notifyLocation(loc);
//        
//    }
//    
//    @Test
//    public void testAddAlarmInfo()
//        throws Exception {
//        Method m =
//            terminalNotifyImp.getDeclaredMethod("addAlarmInfo",
//                LocationEntity.class,
//                List.class);
//        m.setAccessible(true);
//        m.invoke(this.terminalNotify, loc, alarms);
//        AlarmEntity entity = new AlarmEntity();
//        alarms.add(entity);
//        m.invoke(this.terminalNotify, loc, alarms);
//        
//    }
//    
//    @Test
//    public void testgwaybillNo()
//        throws Exception {
//        Method m =
//            terminalNotifyImp.getDeclaredMethod("gwaybillNo", String.class);
//        m.setAccessible(true);
//        String carrierName = "黑A12345";
//        try {
//            m.invoke(this.terminalNotify, carrierName);
//        }
//        catch (Exception e) {
//        }
//        
//    }
//    
//    @Test
//    public void testisIntoArea()
//        throws Exception {
//        Method m =
//            terminalNotifyImp.getDeclaredMethod("isIntoArea",
//                LimitArea.class,
//                LocationEntity.class,
//                Point.class,
//                List.class);
//        m.setAccessible(true);
//        LimitArea limitArea = new LimitArea();
//        Point point =
//            new Point(Double.parseDouble(lng), Double.parseDouble(lat));
//        
//        Point point1 =
//            new Point(Double.parseDouble(lng) + 1, Double.parseDouble(lat) + 1);
//        List<Point> points = new ArrayList<Point>();
//        points.add(point);
//        points.add(point1);
//        BigDecimal speed = new BigDecimal("123.2");
//        limitArea.setLimitSpeed(speed);
//        m.invoke(this.terminalNotify, limitArea, loc, point, points);
//        limitArea.setLimitType(DictConstant.LIMIT_SPEED);
//        loc.setSpeed("445.3");
//        try {
//            m.invoke(this.terminalNotify, limitArea, loc, point, points);
//        }
//        catch (Exception e) {
//        }
//        
//    }
//    
//    @Test
//    public void testhasAlarmInArea()
//        throws Exception {
//        Method m =
//            terminalNotifyImp.getDeclaredMethod("hasAlarmInArea",
//                AlarmEntity.class);
//        m.setAccessible(true);
//        m.invoke(this.terminalNotify, alarm);
//    }
//    
//    @Test
//    public void testgointoLimitAreaType()
//        throws Exception {
//        Method m =
//            terminalNotifyImp.getDeclaredMethod("gointoLimitAreaType",
//                LimitArea.class,
//                AlarmEntity.class,
//                String.class);
//        m.setAccessible(true);
//        LimitArea limitArea = new LimitArea();
//        BigDecimal speed = new BigDecimal("123.2");
//        limitArea.setLimitSpeed(speed);
//        limitArea.setLimitName("DFD");
//        String sp = null;
//        m.invoke(this.terminalNotify, limitArea, alarm, sp);
//        limitArea.setLimitType(DictConstant.LIMIT_SPEED);
//        m.invoke(this.terminalNotify, limitArea, alarm, sp);
//        sp = "334.3";
//        m.invoke(this.terminalNotify, limitArea, alarm, sp);
//        limitArea.setLimitType(DictConstant.LIMIT_STOP);
//        m.invoke(this.terminalNotify, limitArea, alarm, sp);
//        limitArea.setLimitType(DictConstant.LIMIT_DRIVE_IN);
//        m.invoke(this.terminalNotify, limitArea, alarm, sp);
//    }
//    
//    @Test
//    public void testlimitAreaAlarm()
//        throws Exception {
//        List<LimitArea> limits = new ArrayList<>();
//        LimitArea limitArea = new LimitArea();
//        limitArea.setPoints("[[285654.6751308331,208067.27493784908],[285678.5349623704,207842.9925213985],[286088.92406481196,207914.57201601038],[285917.1332777434,208219.9778596878],[285654.6751308331,208067.27493784908]]");
//        limits.add(limitArea);
//        
//        loc.setLongitude("285852.71173259267");
//        loc.setLatitude("208029.9939510721");
//        PowerMockito.when(this.shortcutDao.findLimitAreas()).thenReturn(limits);
//        
//        Map<String, String> map = new HashMap<>();
//        map.put("CORPORATE_NO", "774351711");
//        map.put("ENTERPRISE_NAME", "物流企业");
//        PowerMockito.when(this.carInfoDao.findEnpNameByCarrierName(loc.getCarrierName()))
//            .thenReturn(map);
//        
//        Method m =
//            terminalNotifyImp.getDeclaredMethod("limitAreaAlarm",
//                LocationEntity.class);
//        m.setAccessible(true);
//        try {
//            m.invoke(this.terminalNotify, loc);
//        }
//        catch (Exception e) {
//        }
//    }
//    
//    @Test
//    public void testGwaybillNo() {
//        WayBillEntity wayBill = new WayBillEntity();
//        wayBill.setCheckno("63636363");
//        String carrierName = "xxx";
//        try {
//            PowerMockito.when(this.carInfoDao.findWayBillData(carrierName)
//                .get(0)).thenReturn(wayBill);
//            Method m =
//                terminalNotifyImp.getDeclaredMethod("gwaybillNo", String.class);
//            m.setAccessible(true);
//            m.invoke(this.terminalNotify, carrierName);
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//    
//    @Test
//    public void testHasAlarmInArea() {
//        AlarmEntity alarm = new AlarmEntity();
//        try {
//            Method m =
//                terminalNotifyImp.getDeclaredMethod("hasAlarmInArea",
//                    AlarmEntity.class);
//            m.setAccessible(true);
//            m.invoke(this.terminalNotify, alarm);
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//    
//    @Test
//    public void testAddAlarmInfo2() {
//        LocationEntity loc = new LocationEntity();
//        loc.setCarrierName("xxxccc");
//        
//        List<AlarmEntity> list = new ArrayList<>();
//        AlarmEntity alarm = null;
//        list.add(alarm);
//        
//        try {
//            Method m =
//                terminalNotifyImp.getDeclaredMethod("addAlarmInfo",
//                    LocationEntity.class,
//                    List.class);
//            m.setAccessible(true);
//            m.invoke(this.terminalNotify, loc, list);
//        }
//        catch (Exception e) {
//        }
//    }
//    
//    @Test
//    public void testAlarmHandle()
//        throws Exception {
//        LocationEntity loc = new LocationEntity();
//        List<AlarmEntity> alarms = new ArrayList<AlarmEntity>();
//        AlarmEntity alarm = new AlarmEntity();
//        alarm.setAlarmType("130001005");
//        String driverId = "driverId";
//        alarm.setDriverId(driverId);
//        alarms.add(alarm);
//        String carrierName = "浙A15963";
//        loc.setCarrierName(carrierName);
//        loc.setAlarms(alarms);
//        BlacklistArg arg = new BlacklistArg();
//        BigDecimal vehicleTime = new BigDecimal("1");
//        BigDecimal driverTime = new BigDecimal("1");
//        arg.setVehicleAlarmNum(vehicleTime);
//        arg.setDriverAlarmNum(driverTime);
//        Method m =
//            terminalNotifyImp.getDeclaredMethod("alarmHandle",
//                LocationEntity.class);
//        m.setAccessible(true);
//        
//        // 1.carPlateNo = null
//        String carPlateNo = null;
//        Map<String, String> accountMap = new HashMap<String, String>();
//        accountMap.put("ACCOUNT", "account");
//        accountMap.put("REGID", "regid");
//        PowerMockito.when(this.autoThrowBlacklistDao.findAlarmOne())
//            .thenReturn(arg);
//        PowerMockito.when(this.autoThrowBlacklistDao.findHasInBlacklist(carrierName))
//            .thenReturn(carPlateNo);
//        PowerMockito.when(this.alarmManageDao.findRegId(driverId,
//            DictConstant.ACCOUNT_VERIFY_PASS)).thenReturn(accountMap);
//        try {
//            m.invoke(this.terminalNotify, loc);
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
//        
//        // 2. carPlateNo = carrierName
//        alarms.get(0).setAlarmType("130001005");
//        carPlateNo = carrierName;
//        PowerMockito.when(this.autoThrowBlacklistDao.findAlarmOne())
//            .thenReturn(arg);
//        PowerMockito.when(this.autoThrowBlacklistDao.findHasInBlacklist(carrierName))
//            .thenReturn(carPlateNo);
//        try {
//            m.invoke(this.terminalNotify, loc);
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
//        
//        // 3. time > vehicleTime
//        alarms.get(0).setAlarmType("130001005");
//        carPlateNo = null;
//        int times = 5;
//        Date date = new Date();
//        String maxDateS = "2017-1-09 12:21:23.11";
//        String maxDate = "2017-1-09 12:21:23";
//        Map<String, String> map = new HashMap<String, String>();
//        map.put("carrierName", carrierName);
//        map.put("maxDate", maxDate);
//        
//        PowerMockito.when(this.autoThrowBlacklistDao.findMaxDate4Vehicle(carrierName))
//            .thenReturn(date);
//        
//        PowerMockito.when(this.autoThrowBlacklistDao.findAlarmTimes4Vehicle(map))
//            .thenReturn(times);
//        PowerMockito.when(this.autoThrowBlacklistDao.findAlarmOne())
//            .thenReturn(arg);
//        PowerMockito.when(this.autoThrowBlacklistDao.findHasInBlacklist(carrierName))
//            .thenReturn(carPlateNo);
//        try {
//            m.invoke(this.terminalNotify, loc);
//        }
//        catch (Exception e) {
//            Assert.assertTrue(e instanceof Exception);
//        }
//        
//        // 4 wayBillEntity != NULL cardNo = null times < vehicleAlarmNum
//        alarms.get(0).setAlarmType("130001005");
//        WayBillEntity wayBillEntity = new WayBillEntity();
//        String driverid = "1111";
//        String driver = "driver";
//        wayBillEntity.setDriverId(driverid);
//        wayBillEntity.setDriver(driver);
//        
//        PowerMockito.when(this.autoThrowBlacklistDao.findHasInBlacklist(carrierName))
//            .thenReturn(carPlateNo);
//        
//        PowerMockito.when(this.carInfoDao.findWayBillData(carrierName).get(0))
//            .thenReturn(wayBillEntity);
//        String cardNo = null;
//        PowerMockito.when(this.autoThrowBlacklistDao.findHasInBlack4Driver(driverid))
//            .thenReturn(cardNo);
//        
//        PowerMockito.when(this.autoThrowBlacklistDao.findMaxDate4Driver(driver))
//            .thenReturn(date);
//        
//        times = 0;
//        PowerMockito.when(this.autoThrowBlacklistDao.findAlarmTimes4Vehicle(map))
//            .thenReturn(times);
//        int time = 1;
//        PowerMockito.when(this.autoThrowBlacklistDao.findAlarmTimes4Vehicle(map))
//            .thenReturn(time);
//        try {
//            m.invoke(this.terminalNotify, loc);
//        }
//        catch (Exception e) {
//            Assert.assertTrue(e instanceof Exception);
//        }
//        // 5 wayBillEntity != NULL cardNo = null times >= vehicleAlarmNum
//        alarms.get(0).setAlarmType("130001005");
//        driverTime = new BigDecimal("0");
//        arg.setDriverAlarmNum(driverTime);
//        PowerMockito.when(this.autoThrowBlacklistDao.findAlarmOne())
//            .thenReturn(arg);
//        try {
//            m.invoke(this.terminalNotify, loc);
//        }
//        catch (Exception e) {
//            Assert.assertTrue(e instanceof Exception);
//        }
//        // 6 wayBillEntity != NULL cardNo != null
//        alarms.get(0).setAlarmType("130001005");
//        cardNo = "ddd";
//        PowerMockito.when(this.autoThrowBlacklistDao.findHasInBlack4Driver(driverid))
//            .thenReturn(cardNo);
//        try {
//            m.invoke(this.terminalNotify, loc);
//        }
//        catch (Exception e) {
//            Assert.assertTrue(e instanceof Exception);
//        }
//    }
//    
//    @Test
//    public void testBlackListSave()
//        throws Exception {
//        LocationEntity loc = new LocationEntity();
//        String carrierName = "浙A15963";
//        loc.setCarrierName(carrierName);
//        DangerVehicleEntity entity = new DangerVehicleEntity();
//        String corporateNo = "111";
//        entity.setCorporateNo(corporateNo);
//        
//        Method m =
//            terminalNotifyImp.getDeclaredMethod("blackListSave",
//                LocationEntity.class,
//                String.class,
//                String.class);
//        m.setAccessible(true);
//        
//        PowerMockito.when(this.dangerVehicleDao.findByName(carrierName))
//            .thenReturn(entity);
//        String driverId = null;
//        String driver = "driver";
//        m.invoke(this.terminalNotify, loc, driverId, driver);
//        driverId = "111";
//        m.invoke(this.terminalNotify, loc, driverId, driver);
//    }
//    
//    @Test
//    public void testMiPushAlarm()
//        throws Exception {
//        List<AlarmEntity> alarms = new ArrayList<AlarmEntity>();
//        AlarmEntity alarm = new AlarmEntity();
//        alarm.setAlarmType("130001005");
//        alarm.setAlarmDetails("details");
//        alarm.setDriver("driver");
//        alarm.setSimNum("33");
//        alarm.setCarrierName("name");
//        alarm.setAlarmDate(new Date());
//        alarms.add(alarm);
//        Method m =
//            terminalNotifyImp.getDeclaredMethod("miPushAlarm", List.class);
//        m.setAccessible(true);
//        
//        Map<String, String> accountMap = new HashMap<String, String>();
//        accountMap.put("ACCOUNT", "account");
//        accountMap.put("REGID", "regid");
//        PowerMockito.when(this.alarmManageDao.findRegId(null,
//            DictConstant.ACCOUNT_VERIFY_PASS)).thenReturn(accountMap);
//        try {
//            m.invoke(this.terminalNotify, alarms);
//        }
//        catch (Exception e) {
//        }
//        
//    }
//    
//    @Test
//    public void testGetMsg()
//        throws Exception {
//        AlarmEntity alarm = new AlarmEntity();
//        alarm.setAlarmType("130001005");
//        alarm.setAlarmDetails("details");
//        alarm.setDriver("driver");
//        alarm.setSimNum("33");
//        alarm.setCarrierName("name");
//        alarm.setAlarmDate(new Date());
//        Method m =
//            terminalNotifyImp.getDeclaredMethod("getMsg", AlarmEntity.class);
//        m.setAccessible(true);
//        
//        m.invoke(this.terminalNotify, alarm);
//    }
//    
//}
