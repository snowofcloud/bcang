/*
 * 文件名：AlarmManageServiceImpl.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-7-25
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.vehiclemonitor.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.c503.sc.base.exception.CustomException;
import com.c503.sc.dict.bean.DictionaryValueEntity;
import com.c503.sc.dict.dao.IDictionaryValueDao;
import com.c503.sc.dict.service.IDictionaryService;
import com.c503.sc.dict.vo.DictDataVo;
import com.c503.sc.jxwl.common.bean.AlarmEntity;
import com.c503.sc.jxwl.common.bean.LocationEntity;
import com.c503.sc.jxwl.common.bean.PageEntity;
import com.c503.sc.jxwl.common.constant.CommonConstant;
import com.c503.sc.jxwl.common.constant.DictConstant;
import com.c503.sc.jxwl.common.constant.NumberContant;
import com.c503.sc.jxwl.common.login.AccountValue;
import com.c503.sc.jxwl.common.service.IMiPushService;
import com.c503.sc.jxwl.common.websocket.MonitorWebsocket;
import com.c503.sc.jxwl.vehiclemonitor.bean.InOutAreaEntity;
import com.c503.sc.jxwl.vehiclemonitor.dao.IAcrossLogDao;
import com.c503.sc.jxwl.vehiclemonitor.dao.IDangerVehicleDao;
import com.c503.sc.jxwl.vehiclemonitor.dao.IInOutAreaDao;
import com.c503.sc.jxwl.zcpt.bean.AcrossLogEntity;
import com.c503.sc.jxwl.zcpt.bean.Blacklist;
import com.c503.sc.jxwl.zcpt.bean.BlacklistArg;
import com.c503.sc.jxwl.zcpt.bean.DangerVehicleEntity;
import com.c503.sc.jxwl.zcpt.bean.LimitArea;
import com.c503.sc.jxwl.zcpt.bean.SolveRoute;
import com.c503.sc.jxwl.zcpt.bean.TerminalEntity;
import com.c503.sc.jxwl.zcpt.bean.WayBillEntity;
import com.c503.sc.jxwl.zcpt.dao.IAlarmManageDao;
import com.c503.sc.jxwl.zcpt.dao.IAutoThrowBlacklistDao;
import com.c503.sc.jxwl.zcpt.dao.ICarInfoDao;
import com.c503.sc.jxwl.zcpt.dao.IShortcutDao;
import com.c503.sc.jxwl.zcpt.dao.ISolveRouteDao;
import com.c503.sc.jxwl.zcpt.pointInPolygon.IsPointInPolygon;
import com.c503.sc.jxwl.zcpt.pointInPolygon.Point;
import com.c503.sc.jxwl.zcpt.service.IAlarmManageService;
import com.c503.sc.jxwl.zcpt.service.ITerminalService;
import com.c503.sc.jxwl.zcpt.utils.GeometricAlgorithmUtils;
import com.c503.sc.jxwl.zcpt.vo.AlarmThresholdVo;
import com.c503.sc.log.LoggingManager;
import com.c503.sc.utils.basetools.C503DateUtils;
import com.c503.sc.utils.basetools.C503StringUtils;
import com.c503.sc.utils.common.SystemContants;
import com.c503.sc.utils.exceltools.Content;
import com.c503.sc.utils.exceltools.ExportSheet;
import com.c503.sc.utils.exceltools.Header;
import com.c503.sc.utils.exceltools.Title;
import com.xiaomi.xmpush.server.Message;
import com.xiaomi.xmpush.server.Result;

/**
 * 
 * 〈一句话功能简述〉报警管理业务层 〈功能详细描述〉
 * 
 * @author guqh
 * @version [版本号, 2016-7-25]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Service(value = "alarmManageService")
public class AlarmManageServiceImpl implements IAlarmManageService {
    /** 日志器 */
    private static final LoggingManager LOGGER =
        LoggingManager.getLogger(AlarmManageServiceImpl.class);
    
    /** 报警信息Dao */
    @Resource(name = "alarmManageDao")
    private IAlarmManageDao alarmManageDao;
    
    /** 数据字典接口 */
    @Resource(name = "dictionaryValueDao")
    private IDictionaryValueDao dictionaryValueDao;
    
    /** 危险品车辆信息Dao */
    @Resource(name = "dangerVehicleDao")
    private IDangerVehicleDao dangerVehicleDao;
    
    @Resource(name = "terminalService")
    private ITerminalService terminalService;
    
    /** 操作数据字典的dictionaryService的实例 */
    @Resource(name = "dictionaryService")
    private IDictionaryService dictionaryService;
    
    /** miPushService的实例 */
    @Resource(name = "miPushService")
    private IMiPushService miPushService;
    
    /** 车辆信息dao */
    @Resource(name = "carInfoDao")
    private ICarInfoDao carInfoDao;
    
    /** 区域限制dao */
    @Resource(name = "shortcutDao")
    private IShortcutDao shortcutDao;
    
    /** 跨域记录Dao */
    @Resource(name = "acrossLogDao")
    private IAcrossLogDao acrossLogDao;
    
    /** 车辆进出区域信息Dao */
    @Resource(name = "inOutAreaDao")
    private IInOutAreaDao inOutAreaDao;
    
    /** 黑名单信息Dao */
    @Resource(name = "autoThrowBlacklistDao")
    private IAutoThrowBlacklistDao autoThrowBlacklistDao;
    
    /** 路径规划数据接口 */
    @Resource(name = "solveRouteDao")
    private ISolveRouteDao solveRouteDao;
    
    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public List<AlarmEntity> findByParams(Map<String, Object> map)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, map);
        List<AlarmEntity> list = null;
        try {
            map.put("remove", SystemContants.ON);
            list = this.alarmManageDao.findByParams(map);
            int size = list.size();
            
            Map<String, String> map1 = new HashMap<String, String>();
            map1.put("page", null);
            map1.put("rows", null);
            // 制造商ID
            map1.put("manufactureID", null);
            // 厂商终端ID
            map1.put("terminalSerialID", null);
            // 车牌号
            // map1.put("carrierName", (String) map.get("carrierName"));
            // 接口暂不支持车牌号模糊查询，故选择查询全部
            map1.put("carrierName", null);
            // 手机号
            map1.put("simNum", null);
            PageEntity<com.c503.sc.jxwl.zcpt.bean.TerminalEntity> pageEntity =
                this.terminalService.findByParams(map1);
            Collection<TerminalEntity> ters = pageEntity.getRows();
            
            if (0 < size) {
                for (AlarmEntity entity : list) {
                    String carrierName = entity.getCarrierName();
                    for (TerminalEntity ter : ters) {
                        if (carrierName.equals(ter.getCarrierName())) {
                            entity.setTerminalId(ter.getTerminalSerialID());
                        }
                    }
                    String dealStatus =
                        findNameByCode(entity.getAlarmDealStatus());
                    String alarmType = findNameByCode(entity.getAlarmType());
                    entity.setAlarmDealStatus(dealStatus);
                    entity.setAlarmType(alarmType);
                }
            }
        }
        catch (Exception e) {
            throw new CustomException(CommonConstant.SYS_EXCEPTION, e);
        }
        LOGGER.debug(SystemContants.DEBUG_END, map);
        return list;
    }
    
    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public Object save(AlarmEntity entity)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, entity);
        // 数据补充(企业、运单、驾驶员、终端等)
        entity.setId(C503StringUtils.createUUID());
        entity.setRemove(SystemContants.ON);
        Date curDate = new Date();
        entity.setCreateTime(curDate);
        entity.setUpdateTime(curDate);
        this.alarmManageDao.save(entity);
        MonitorWebsocket.notifyAlarm(entity);
        /*
         * 1、通知websocket 2、保存报警 3、检测报警次数拉黑（TODO）
         */
        
        LOGGER.debug(SystemContants.DEBUG_END, entity);
        return entity.getId();
    }
    
    /**
     * {@inheritDoc}
     * 
     */
    @Override
    @Transactional(rollbackFor = java.lang.Exception.class)
    public Object update(AlarmEntity entity)
        throws Exception {
        
        this.alarmManageDao.update(entity);
        // 判断该报警记录中的车辆还有没有未处理的报警 如果没有 则推送至前端
        String carrierName = entity.getCarrierName();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("carrierName", carrierName);
        int result = this.findAlarmNumForDiTu(map);
        AlarmEntity alarmEntity = new AlarmEntity();
        alarmEntity.setAlarmDealStatus("1");
        alarmEntity.setCarrierName(carrierName);
        if (null != carrierName && !AccountValue.isCarrierNameNull()
            && carrierName.equals(AccountValue.getCarrierName())) {
            if (result == 0) {
                alarmEntity.setAlarmDealStatus("0");
            }
            MonitorWebsocket.notifyAlarmDealStatus(alarmEntity);
            return result;
        }
        if (result == 0) {
            alarmEntity.setAlarmDealStatus("0");
            MonitorWebsocket.notifyAlarmDealStatus(alarmEntity);
        }
        return result;
    }
    
    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public AlarmEntity findById(String id)
        throws Exception {
        AlarmEntity entity = null;
        try {
            entity = this.alarmManageDao.findById(id);
            String dealStatus = findNameByCode(entity.getAlarmDealStatus());
            String alarmType = findNameByCode(entity.getAlarmType());
            entity.setAlarmDealStatus(dealStatus);
            entity.setAlarmType(alarmType);
        }
        catch (Exception e) {
            throw new CustomException(CommonConstant.SYS_EXCEPTION, e, id);
        }
        return entity;
    }
    
    @Override
    public int delete(Map<String, Object> map)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, map);
        int delLine = 0;
        map.put("remove", SystemContants.OFF);
        
        delLine = this.alarmManageDao.delete(map);
        
        LOGGER.debug(SystemContants.DEBUG_END, map);
        
        return delLine;
    }
    
    @Override
    public int deleteAlarmInfo()
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        int delAlarmInfo = 0;
        delAlarmInfo = this.alarmManageDao.deleteAlarmInfo();
        LOGGER.debug(SystemContants.DEBUG_END);
        return delAlarmInfo;
    }
    
    /**
     * {@inheritDoc}
     * 
     */
    @Override
    @Transactional(rollbackFor = java.lang.Exception.class)
    public Object updateThreshold(AlarmThresholdVo entity)
        throws Exception {
        
        this.alarmManageDao.updateThreshold(entity);
        
        return entity;
    }
    
    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public AlarmThresholdVo findThresholdById(Map<String, Object> map)
        throws Exception {
        AlarmThresholdVo vo = null;
        
        vo = this.alarmManageDao.findThresholdById(map);
        
        return vo;
    }
    
    /**
     * {@inheritDoc}
     */
    public Map<String, Object> exportExcel(Map<String, Object> map)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, map);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        // 查询满足条件的渔港信息集合
        map.put("remove", SystemContants.ON);
        List<AlarmEntity> list = null;
        try {
            list = this.alarmManageDao.findByParams(map);
            
        }
        catch (Exception e) {
            throw new CustomException(CommonConstant.SYS_EXCEPTION, e, map);
        }
        // 生成sheet对象
        ExportSheet sheet = createSheet(list);
        resultMap.put("sheet", sheet);
        resultMap.put("excelName", "报警信息表");
        LOGGER.debug(SystemContants.DEBUG_END, map);
        return resultMap;
    }
    
    /**
     * 〈一句话功能简述〉小米推送报警〈功能详细描述〉
     * 
     * @param alarms List<AlarmEntity>
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings({"unchecked"})
    public void miPushAlarm(List<AlarmEntity> alarms)
        throws Exception {
        if (alarms == null) {
            CustomException ce =
                new CustomException(CommonConstant.SYS_EXCEPTION, "无报警信息");
            ce.setErrorMessage("无报警信息");
            LOGGER.error(CommonConstant.SYS_EXCEPTION, ce, alarms);
            throw ce;
        }
        // 根据数据字典得到报警类型中文
        String alarmCode = "130001";
        List<DictDataVo> dictResult = new ArrayList<DictDataVo>();
        try {
            dictResult =
                (ArrayList<DictDataVo>) this.dictionaryService.findDictFromDB(alarmCode);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < alarms.size(); i++) {
            AlarmEntity alarm = alarms.get(i);
            // 如果驾驶员为空，则不推送报警信息给app！不论是一键呼叫报警信息还是车辆报警信息
            if (C503StringUtils.isEmpty(alarm.getDriverId())) {
                return;
            }
            String alarmType = alarm.getAlarmType();
            for (int j = 0; j < dictResult.size(); j++) {
                DictDataVo dict = dictResult.get(j);
                if (dict.getCode().equals(alarmType)) {
                    alarm.setAlarmType(dict.getName());
                    break;
                }
            }
            // 封装小米推送的message数据
            String title = alarm.getAlarmType();
            String des = alarm.getAlarmDetails();
            Map<String, String> accountMap = null;
            // 根据身份证 查询驾驶员人名 和app账号 以及regId
            try{
                accountMap =
                    this.alarmManageDao.findRegId(alarm.getDriverId(),
                        DictConstant.ACCOUNT_VERIFY_PASS);
            }catch(Exception e){
                if(e instanceof MyBatisSystemException){
                    CustomException ce =
                        new CustomException(CommonConstant.SYS_EXCEPTION,
                            "alarm.getDriverId()=" + alarm.getDriverId());
                    ce.setErrorMessage("数据错误，一个驾驶员身份证号查询出多于一个account!");
                    throw ce;
                }
            }
            if (accountMap == null) {
                CustomException ce =
                    new CustomException(CommonConstant.SYS_EXCEPTION,
                        "alarm.getDriverId()=" + alarm.getDriverId());
                ce.setErrorMessage("未查询到账号信息");
                LOGGER.error(CommonConstant.SYS_EXCEPTION, ce, alarm);
                throw ce;
            }
            if (accountMap.get("ACCOUNT") != null) {
                alarm.setDriver(accountMap.get("ACCOUNT"));
            }
            String regId = null;
            if (accountMap.get("REGID") != null) {
                regId = accountMap.get("REGID");
            }
            // 得到messagePayload
            Map<String, Object> map = this.getMsg(alarm);
            String messagePayload = this.miPushService.getPayload(map, "1");
            // 得到message
            Message message = null;
            
            try {
                message =
                    this.miPushService.messageHandle(title,
                        des,
                        messagePayload,
                        false);
                LOGGER.info(SystemContants.DEBUG_START, "message: " + message
                    + " regId: " + regId);
                // 发送message 并得到result
                Result result =
                    this.miPushService.sendByRegId(message,
                        regId,
                        NumberContant.FIVE);
                LOGGER.info(SystemContants.DEBUG_START, "message: s" + result);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            
        }
    }
    
    /**
     * 
     * 〈一句话功能简述〉根据account查询相关信息
     * 
     * @param account
     *            字典的account
     * @return AlarmEntity
     * @throws Exception
     *             数据库异常
     * @see [类、类#方法、类#成员]
     */
    @Override
    public List<AlarmEntity> findByAccount(String account)
        throws Exception {
        /*
         * 查找T_REGISTER_INFO 得到occupation_id 再查找T_OCCUPATION_PERSON 得到身份证号
         * 再查找T_WAYBILL 得到相关信息 再查找T_DANGER_VEHICLE 得到终端等相关信息
         */
        Map<String, String> map = new HashMap<String, String>();
        map.put("remove", "0");
        map.put("account", account);
        List<AlarmEntity> entity = this.alarmManageDao.findByAccount(map);
        return entity;
    }
    
    @Override
    public List<AlarmEntity> findAlarmForDiTu(Map<String, Object> map)
        throws Exception {
        return this.alarmManageDao.findAlarmForDiTuParams(map);
    }
    
    @Override
    public int findAlarmNumForDiTu(Map<String, Object> map)
        throws Exception {
        return this.alarmManageDao.findAlarmNumForDiTu(map);
    }
    
    @Override
    public String findNameByAccount(String account)
        throws Exception {
        return this.alarmManageDao.findNameByAccount(account);
    }
    
    @Override
    public void deleteAlarm(AlarmEntity entity)
        throws Exception {
        alarmManageDao.deleteAlarm(entity);
        
    }
    
    @Override
    public List<String> findForAppByAccount(String account)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, account);
        List<String> list = null;
        try {
            list = this.alarmManageDao.findForAppByAccount(account);
        }
        catch (Exception e) {
            throw new CustomException(CommonConstant.SYS_EXCEPTION, e, account);
        }
        LOGGER.debug(SystemContants.DEBUG_END, account);
        return list;
    }
    
    @Override
    public boolean handleAlarm(List<LocationEntity> locList, String handleType)
        throws Exception {
        boolean isSuc = false;
        int size = locList.size();
        String speed = "";
        LocationEntity loc = null;
        for (int i = 0; i < size; i++) {
            loc = locList.get(i);
            speed = loc.getSpeed();
            if(!StringUtils.isEmpty(speed)){
             //   String.format("%.2f");
               Double  s = (Double.valueOf(speed))/10;
                loc.setSpeed(String.format(String.valueOf(s),"%.2f"));
            }
            if (loc != null) {
                if (C503StringUtils.equals("lineAlarm", handleType)) {
                    this.saveLineAlarm(loc);
                    this.saveAlarmHandle(loc);
                }
                if (C503StringUtils.equals("limitAreaAlarm", handleType)) {
                    this.saveLimitAreaAlarm(loc);
                    this.saveAlarmHandle(loc);
                }
                if (C503StringUtils.equals("alarmHandle", handleType)) {
                    this.saveAlarmHandle(loc);
                }
                isSuc = true;
            }
        }
        return isSuc;
    }
    
    /**
     * 〈一句话功能简述〉偏离路线报警
     * 〈功能详细描述〉
     * 
     * @param loc 位置
     * @throws Exception 系统异常
     * @see [类、类#方法、类#成员]
     */
    private void saveLineAlarm(LocationEntity loc)
        throws Exception {
        String carrierName = loc.getCarrierName();
        String longitudeStr = loc.getLongitude();
        String latitudeStr = loc.getLatitude();
        Point actualPoint =
            new Point(Double.parseDouble(longitudeStr),
                Double.parseDouble(latitudeStr));
        List<WayBillEntity> bills =
            this.carInfoDao.findWayBillData(carrierName);
        WayBillEntity bill = null;
        if (!CollectionUtils.isEmpty(bills)) {
            bill = bills.get(0);
            if (bill != null) {
                // 获取运单的规划线路，对线路进行匹配,然后报警
                // 线路规划是针对园区的，所以偏离线路报警也要在园区内
                // 运单开始运输后才会规划路径，所以运单运输过程中，才会有偏离路线报警
                String billStatus = bill.getCheckstatus();
                if (!C503StringUtils.isEmpty(billStatus)) {
                    if (!C503StringUtils.equals(DictConstant.WAYBILL_FINISH,
                        billStatus)) {
                        String billId = bill.getId();
                        SolveRoute route =
                            this.solveRouteDao.findRouteByWaybillId(billId);
                        if (null != route) {
                            String pointStr = route.getPoints();
                            if (C503StringUtils.isNotEmpty(pointStr)) {
                                JSONArray pointArrs = JSON.parseArray(pointStr);
                                int size = pointArrs.size();
                                List<Point[]> lineSegments =
                                    new ArrayList<Point[]>(size - 1);
                                Point[] lineSegment = new Point[2];
                                JSONArray pointObj = null;
                                Double lng;
                                Double lat;
                                Point point = null;
                                JSONArray nextPointObj = null;
                                Double nextLng;
                                Double nextLat;
                                Point NextPoint = null;
                                for (int i = 0; i < size; i++) {
                                    pointObj = (JSONArray) pointArrs.get(i);
                                    lng = pointObj.getDouble(0);
                                    lat = pointObj.getDouble(1);
                                    point = new Point(lng, lat);
                                    for (int j = 1; j < size; j++) {
                                        nextPointObj =
                                            (JSONArray) pointArrs.get(j);
                                        nextLng = nextPointObj.getDouble(0);
                                        nextLat = nextPointObj.getDouble(1);
                                        NextPoint = new Point(nextLng, nextLat);
                                        lineSegment[0] = point;
                                        lineSegment[1] = NextPoint;
                                        lineSegments.add(lineSegment);
                                    }
                                }
                                
                                // 判断点到直线距离
                                int lineSize = lineSegments.size();
                                Point[] tmpLineSegment = null;
                                double[] distance;
                                for (int i = 0; i < lineSize; i++) {
                                    tmpLineSegment = lineSegments.get(i);
                                    distance =
                                        GeometricAlgorithmUtils.vector(actualPoint,
                                            tmpLineSegment);
                                    double shortDistance = distance[1];
                                    // 如果大于100米，则新增报警信息
                                    if (shortDistance > 100) {
                                        // 报警
                                        Date gpstime = loc.getGpstime();
                                        String terminalID = loc.getTerminalID();
                                        String simNum = loc.getSimNum();
                                        
                                        AlarmEntity alarm = new AlarmEntity();
                                        alarm.setId(C503StringUtils.createUUID());
                                        alarm.setAlarmDealStatus(DictConstant.ALARM_NOT_DEALED);
                                        alarm.setAlarmType(DictConstant.ALARM_ROUTE_DEVIATE);
                                        alarm.setAlarmDate(gpstime);
                                        alarm.setCarrierName(carrierName);
                                        alarm.setTerminalId(terminalID);
                                        alarm.setTelephone(simNum);
                                        alarm.setCreateTime(new Date());
                                        alarm.setUpdateTime(new Date());
                                        this.alarmManageDao.save(alarm);
                                        // 推送至前端
                                        MonitorWebsocket.notifyAlarm(alarm);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    /**
     * 
     * 〈一句话功能简述〉将数据组装为excel表单 〈功能详细描述〉
     * 
     * @param dataList
     *            数据结合
     * @return excel表单
     * @see [类、类#方法、类#成员]
     */
    private ExportSheet createSheet(List<AlarmEntity> dataList) {
        ExportSheet sheet = new ExportSheet();
        Header header = new Header();
        Object[] headNames =
            new Object[] {"序号", "报警记录编号", "终端编号", "车牌号", "运单编号", "报警类型",
                "报警处理状态", "报警时间", "报警详情", "报警登记情况"};
        header.setHeadNames(headNames);
        // 设置表的标题
        Title title = new Title();
        title.setTitleName("报警信息表");
        // 设置表头对应的属性
        String[] fields =
            new String[] {"rowNum", "alarmNo", "terminalId", "licencePlateNo",
                "waybillNo", "alarmType", "alarmDealStatus", "alarmDate",
                "alarmDetails", "alarmRegisterHandle"};
        
        Content content = new Content();
        content.setFieldNames(fields);
        content.setDataList(dataList);
        sheet.setHeader(header);
        sheet.setTitle(title);
        sheet.setContent(content);
        return sheet;
    }
    
    /**
     * 〈一句话功能简述〉封装msg 〈功能详细描述〉
     * 
     * @param alarm
     *            AlarmEntity
     * @return Map<String, Object>
     * @see [类、类#方法、类#成员]
     */
    private Map<String, Object> getMsg(AlarmEntity alarm) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("state", "1");
        map.put("id", alarm.getId());
        map.put("user", alarm.getDriver());
        map.put("des", alarm.getAlarmDetails());
        // 托运单号
        map.put("ticketNum", alarm.getWaybillNo());
        map.put("alarmType", alarm.getAlarmType());
        map.put("carNum", alarm.getCarrierName());
        map.put("alarmTime",
            C503DateUtils.dateToStr(alarm.getAlarmDate(), "YYYY-MM-dd HH:mm:ss"));
        return map;
    }
    
    /**
     * 
     * 〈一句话功能简述〉根据字典的value值获取字典的名称 〈功能详细描述〉
     * 
     * @param value
     *            字典的value
     * @return 获取字典的名称
     * @throws Exception
     *             数据库异常
     * @see [类、类#方法、类#成员]
     */
    public String findNameByCode(String value)
        throws Exception {
        String name = "";
        if (C503StringUtils.isNotEmpty(value)) {
            DictionaryValueEntity dic =
                this.dictionaryValueDao.findDicByValue(value);
            name = null == dic ? "" : dic.getName();
        }
        return name;
    }
    
    /**
     * 〈一句话功能简述〉是否进入限制区域 〈功能详细描述〉
     * 
     * @param loc
     *            LocationEntity
     * @return this
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    private void saveLimitAreaAlarm(LocationEntity loc)
        throws Exception {
        // 经度
        String lng = loc.getLongitude();
        // 纬度
        String lat = loc.getLatitude();
        // 储存限制区域坐标点集
        List<Point> pts = new ArrayList<Point>();
        Point point =
            new Point(Double.parseDouble(lng), Double.parseDouble(lat));
        // 获取限制区域
        List<LimitArea> limits = this.shortcutDao.findLimitAreas();
        String points = null;
        JSONArray pointArrs = null;
        JSONArray ars = null;
        double lng2 = 0;
        double lat2 = 0;
        Point point2 = null;
        for (LimitArea limit : limits) {
            pts.clear();
            points = limit.getPoints();
            if (C503StringUtils.isNotEmpty(points)) {
                pointArrs = JSON.parseArray(points);
                for (Object pointArr : pointArrs) {
                    ars = JSONObject.parseArray(JSON.toJSONString(pointArr));
                    lng2 = Double.parseDouble(String.valueOf(ars.get(0)));
                    lat2 = Double.parseDouble(String.valueOf(ars.get(1)));
                    point2 = new Point(lng2, lat2);
                    pts.add(point2);
                }
                // 是否进入限制区域
                this.isIntoLimitArea(limit, loc, point, pts);
            }
        }
        this.notifyAlarmDealStatus(loc.getCarrierName());
    }
    
    /**
     * 〈一句话功能简述〉是否进入限制区域 〈功能详细描述〉
     * 
     * @param limit
     *            LimitArea
     * @param loc
     *            LocationEntity
     * @param point
     *            Point
     * @param pts
     *            List<Point>
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    private void isIntoLimitArea(LimitArea limit, LocationEntity loc,
        Point point, List<Point> pts)
        throws Exception {
        String carrierName = loc.getCarrierName();
        Date gpstime = loc.getGpstime();
        if (IsPointInPolygon.isInPolygon(point, pts)) {
            // 限制区域记录
            saveAcrossLog(carrierName,
                AcrossLogEntity.LIMIT_AREA,
                limit.getId());
            AlarmEntity alarm = new AlarmEntity();
            // 进入限制区域类型
            this.gointoLimitAreaType(limit,
                alarm,
                loc.getSpeed(),
                loc.getCarrierName());
            if (StringUtils.isNotEmpty(alarm.getAlarmType())) {
                // 有报警信息
                alarm.setId(C503StringUtils.createUUID());
                alarm.setAlarmNo("B" + System.currentTimeMillis())
                    .setTerminalId(loc.getTerminalID())
                    .setAlarmDealStatus(DictConstant.ALARM_NOT_DEALED)
                    .setAlarmDate(gpstime == null ? new Date() : gpstime)
                    .setCreateTime(new Date());
                alarm.setCarrierName(carrierName);
                // 判断30分钟之内是否应经在当前限制区域已经报过警 因为车辆上下线的判断 是根据30分钟来定的
                if (!hasAlarmInArea(alarm)) {
                    // 暂时不用保存数据，有统一保存的地方
                    loc.addAlarm(alarm);
                }
            }
        }
        else {
            // 车辆没有驶入当前限制区域，则自动解除该车辆在该区域内之前的所有类型（包含限驶入、限停、限速）的报警信息
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("carrierName", carrierName);
            map.put("alarmDealStatus", DictConstant.ALARM_NOT_DEALED);
            // map.put("alarmType", DictConstant.ALARM_AREA); //
            // 驶出区域后要解除区域内所有3种类型的报警，而不是只解除限驶入一种类型的报警
            map.put("limitAreaId", limit.getId());
            List<AlarmEntity> alarms = alarmManageDao.findByParams(map);
            if (!CollectionUtils.isEmpty(alarms)) {
                for (AlarmEntity a : alarms) {
                    a.setAlarmDealStatus(DictConstant.ALARM_DEALED);
                    a.setAlarmRegisterHandle("自动解除报警");
                    this.alarmManageDao.update(a);
                }
            }
        }
        
    }
    
    /**
     * 〈一句话功能简述〉判断该车是否在30分钟之内是否应经在当前限制区域已经报过警 〈功能详细描述〉
     * 
     * @param alarm
     *            AlarmEntity
     * @return true:30分钟内已经报过警; fase:没报过警
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    private boolean hasAlarmInArea(AlarmEntity alarm)
        throws Exception {
        boolean flag = false;
        List<String> alarmTime =
            this.alarmManageDao.findLastAlarmByCarrierName(alarm.getCarrierName(),
                alarm.getAlarmType(),
                alarm.getLimitAreaId());
        if (alarmTime != null && alarmTime.size() != 0) {
            long dbMillis =
                C503DateUtils.strToDate(alarmTime.get(0), "yyyy-MM-dd HH:mm:ss")
                    .getTime();
            long curMillis = System.currentTimeMillis();
            if (curMillis - dbMillis < NumberContant.THREE * NumberContant.TEN
                * NumberContant.SIXTY * NumberContant.ONE_THOUSAND) {
                flag = true;
            }
        }
        
        return flag;
    }
    
    /**
     * 〈一句话功能简述〉进入限制区域类型 〈功能详细描述〉
     * 
     * @param limit
     *            LimitArea
     * @param alarm
     *            AlarmEntity
     * @param sp
     *            当前车辆运行速度
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
    private void gointoLimitAreaType(LimitArea limit, AlarmEntity alarm,
        String sp, String carrierName)
        throws Exception {
        String type = limit.getLimitType();
        String liname = limit.getLimitName();
        // 是否超速
        if (StringUtils.equals(DictConstant.LIMIT_SPEED, type)) {
            BigDecimal lisp = limit.getLimitSpeed();
            if (StringUtils.isNotEmpty(sp) && null != lisp
                && 0 < new BigDecimal(sp).compareTo(lisp)) {
                alarm.setAlarmDetails("在" + liname + "区域超速");
                alarm.setAlarmType(DictConstant.ALARM_OVERSPEED);
                alarm.setLimitAreaId(limit.getId());// 保存报警产生时车辆所处的限制区域id
            }
            else {
                // 不超速，则先去数据库查找是否之前有超速报警，如果有，则需要将该条报警信息状态更改为已处理，这样才能自动解除报警
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("carrierName", carrierName);
                map.put("alarmDealStatus", DictConstant.ALARM_NOT_DEALED);
                map.put("alarmType", DictConstant.ALARM_OVERSPEED);
                map.put("limitAreaId", limit.getId());
                try {
                    List<AlarmEntity> alarms = alarmManageDao.findByParams(map);
                    if (!CollectionUtils.isEmpty(alarms)) {
                        for (AlarmEntity a : alarms) {
                            a.setAlarmDealStatus(DictConstant.ALARM_DEALED);
                            a.setAlarmRegisterHandle("自动解除报警");
                            alarmManageDao.update(a);
                        }
                    }
                }
                catch (Exception e) {
                    throw new Exception("没有查找到当前车辆的状态为未处理的超速报警信息！");
                }
            }
            
        }
        // 是否限停
        else if (StringUtils.equals(DictConstant.LIMIT_STOP, type)) {
            if (new BigDecimal(sp).compareTo(new BigDecimal(0)) <= 0) {
                alarm.setAlarmDetails("在" + liname + "区域限停");
                alarm.setAlarmType(DictConstant.ALARM_OVERTIME_PARK);
                alarm.setLimitAreaId(limit.getId());// 保存报警产生时车辆所处的限制区域id
            }
            else {
                // 在限停区域内速度大于0，则先去数据库查找是否之前有限停报警，如果有，则需要将该条报警信息状态更改为已处理，这样才能自动解除报警
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("carrierName", carrierName);
                map.put("alarmDealStatus", DictConstant.ALARM_NOT_DEALED);
                map.put("alarmType", DictConstant.ALARM_OVERTIME_PARK);
                map.put("limitAreaId", limit.getId());
                try {
                    List<AlarmEntity> alarms = alarmManageDao.findByParams(map);
                    if (!CollectionUtils.isEmpty(alarms)) {
                        for (AlarmEntity a : alarms) {
                            a.setAlarmDealStatus(DictConstant.ALARM_DEALED);
                            a.setAlarmRegisterHandle("自动解除报警");
                            alarmManageDao.update(a);
                        }
                    }
                }
                catch (Exception e) {
                    throw new Exception("没有查找到当前车辆的状态为未处理的限停报警信息！");
                }
                
            }
            
        }
        // 是否限驶入
        else if (StringUtils.equals(DictConstant.LIMIT_DRIVE_IN, type)) {
            alarm.setAlarmDetails("在" + liname + "区域限驶入");
            alarm.setAlarmType(DictConstant.ALARM_AREA);
            alarm.setLimitAreaId(limit.getId());// 保存报警产生时车辆所处的限制区域id
        }
    }
    
    /**
     * 保存跨域记录
     * 
     * @param carrierName 车牌
     * @param areaType 区域类型
     * @param areaId 区域ID
     * @throws Exception
     */
    private void saveAcrossLog(String carrierName, String areaType,
        String areaId)
        throws Exception {
        if (C503StringUtils.isEmpty(carrierName)) {
            LOGGER.error(0, new NullPointerException("保存跨域记录：carrierName="
                + carrierName), carrierName);
            return;
        }
        if (C503StringUtils.isEmpty(areaType)) {
            LOGGER.error(0, new NullPointerException("保存跨域记录：areaType="
                + areaType), areaType);
            return;
        }
        if (C503StringUtils.isEmpty(areaId)) {
            LOGGER.error(0,
                new NullPointerException("保存跨域记录：areaId=" + areaId),
                areaId);
            return;
        }
        AcrossLogEntity lastAcrosslog =
            acrossLogDao.getLastAcrossLog(carrierName, areaType);
        LOGGER.debug(0, "获取车辆最新的跨域记录：" + lastAcrosslog);
        if (lastAcrosslog == null) {
            lastAcrosslog = new AcrossLogEntity();
            lastAcrosslog.setId(C503StringUtils.createUUID());
            lastAcrosslog.setAreaId(areaId);
            lastAcrosslog.setAreaType(areaType);
            lastAcrosslog.setCarrierName(carrierName);
            lastAcrosslog.setLogTime(new Date());
            acrossLogDao.save(lastAcrosslog);
            InOutAreaEntity inOutAreaEntity = new InOutAreaEntity();
            inOutAreaEntity.setId(C503StringUtils.createUUID());
            inOutAreaEntity.setAreaId(areaId);
            inOutAreaEntity.setAreaType(areaType);
            inOutAreaEntity.setCarrierName(carrierName);
            // 保存进入区域时间
            inOutAreaEntity.setInTime(new Date());
            inOutAreaEntity.setLogTime(new Date()); // new
                                                    // Date(System.currentTimeMillis())
            inOutAreaEntity.setInOutStatus("已进入");
            inOutAreaDao.save(inOutAreaEntity);
        }
        else {
            String dbAreaId = lastAcrosslog.getAreaId();
            if (!areaId.equals(dbAreaId)) {
                // 跨域了
                lastAcrosslog.setId(C503StringUtils.createUUID());
                lastAcrosslog.setAreaId(areaId);
                lastAcrosslog.setPreAreaId(dbAreaId);
                lastAcrosslog.setLogTime(new Date());
                lastAcrosslog.setAreaType(areaType);
                lastAcrosslog.setCarrierName(carrierName);
                acrossLogDao.save(lastAcrosslog);
                InOutAreaEntity inOutAreaEntity = new InOutAreaEntity();
                inOutAreaEntity.setAreaId(dbAreaId);
                inOutAreaEntity.setCarrierName(carrierName);
                // 保存上一个区域的离开区域时间
                inOutAreaEntity.setOutTime(new Date());
                inOutAreaEntity.setLogTime(new Date());
                inOutAreaEntity.setInOutStatus("已离开");
                inOutAreaDao.update(inOutAreaEntity);
                
                InOutAreaEntity inOutAreaEntity2 = new InOutAreaEntity();
                inOutAreaEntity2.setId(C503StringUtils.createUUID());
                inOutAreaEntity2.setAreaId(areaId);
                inOutAreaEntity2.setAreaType(areaType);
                inOutAreaEntity2.setCarrierName(carrierName);
                // 保存下一个区域的进入区域时间
                inOutAreaEntity2.setInTime(new Date());
                inOutAreaEntity2.setLogTime(new Date());
                inOutAreaEntity2.setInOutStatus("已进入");
                inOutAreaDao.save(inOutAreaEntity2);
            }
            else {
                LOGGER.debug(0, "进入区域，但没有跨域，且已经有记录了，不操作");
            }
        }
    }
    
    private void notifyAlarmDealStatus(String carrierName)
        throws Exception {
        // 去后台查询当前车辆的剩余的报警个数
        // 如果报警个数为0，则推送前端，否则，不推送
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("carrierName", carrierName);
        int result = this.alarmManageDao.findAlarmNumForDiTu(map);
        AlarmEntity alarmEntity = new AlarmEntity();
        alarmEntity.setAlarmDealStatus("1");
        alarmEntity.setCarrierName(carrierName);
        if (result == 0) {
            alarmEntity.setAlarmDealStatus("0");
            MonitorWebsocket.notifyAlarmDealStatus(alarmEntity);
        }
    }
    
    /**
     * 〈一句话功能简述〉报警处理 〈功能详细描述〉
     * 
     * @param loc
     *            LocationEntity
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    private void saveAlarmHandle(LocationEntity loc)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, loc);
        List<AlarmEntity> alarms = loc.getAlarms();
        if (alarms == null) {
            LOGGER.debug(SystemContants.DEBUG_START, "无报警信息");
            return;
        }
        // 补充报警信息
        addAlarmInfo(loc, alarms);
        // 保存报警信息
        this.saveByBatch(alarms);
        // 推送报警信息至app端
        final List<AlarmEntity> alarms2 = new ArrayList<AlarmEntity>(alarms);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    miPushAlarm(alarms2);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        
        // 拉黑操作
        // 获取自动拉黑设置的次数
        BlacklistArg blacklistArg = this.autoThrowBlacklistDao.findAlarmOne();
        // 车辆
        int vehicleAlarmNum = blacklistArg.getVehicleAlarmNum().intValue();
        // 驾驶员
        int driverAlarmNum = blacklistArg.getDriverAlarmNum().intValue();
        
        // 报警处理：车辆
        // 是否已被拉黑
        String carrierName = loc.getCarrierName();
        if (carrierName != null) {
            String carPlateNo =
                this.autoThrowBlacklistDao.findHasInBlacklist(carrierName);
            // 不在黑名单中
            if (StringUtils.isEmpty(carPlateNo)) {
                // 查询报警次数
                int times = 0;
                Date maxDate1 =
                    this.autoThrowBlacklistDao.findMaxDate4Vehicle(carrierName);
                Date maxDate2 = blacklistArg.getUpdateTime();
                // 每次更新自动拉黑次数后报警时间从此时开始计算
                Date maxDate =
                    maxDate1 == null ? maxDate2
                        : maxDate1.getTime() > maxDate2.getTime() ? maxDate1
                            : maxDate2;
                String maxDateStr = null;
                if (maxDate != null) {
                    SimpleDateFormat sdf =
                        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    maxDateStr = sdf.format(maxDate);
                }
                Map<String, String> map = new HashMap<String, String>();
                map.put("carrierName", carrierName);
                map.put("maxDate", maxDateStr);
                times = this.autoThrowBlacklistDao.findAlarmTimes4Vehicle(map);
                // 报警次数大于自动拉黑设置的次数
                if (times >= vehicleAlarmNum) {
                    this.blackListSave(loc, null, null);
                }
            }
            // 报警处理：驾驶员 运单状态处于运货或取货时，驾驶员拉黑
            // 判别运单状态：根据车牌号 查询
            List<WayBillEntity> wayBills =
                this.carInfoDao.findWayBillData(carrierName);
            if (!CollectionUtils.isEmpty(wayBills)) {
                WayBillEntity wayBillEntity = wayBills.get(0);
                if (wayBillEntity != null) {
                    // 因为wayBillEntity中有两个Driverid，而且它们大小写不一样，但是调试发现数据库底层查询出来的字段只会set到声明在前的那个driverid,
                    // 也就是driverId而不是driverid
                    // String driverId = wayBillEntity.getDriverid();
                    String driverId = wayBillEntity.getDriverId();
                    // 通过身份证查询该驾驶员是否在黑名单中
                    String cardNo =
                        this.autoThrowBlacklistDao.findHasInBlack4Driver(driverId);
                    String driver = wayBillEntity.getDriver();
                    // 不在黑名单中
                    if (StringUtils.isEmpty(cardNo)) {
                        // 查询报警次数
                        int times = 0;
                        Date maxDate1 =
                            this.autoThrowBlacklistDao.findMaxDate4Driver(driver);
                        Date maxDate2 = blacklistArg.getUpdateTime();
                        Date maxDate =
                            maxDate1 == null ? maxDate2
                                : maxDate1.getTime() > maxDate2.getTime() ? maxDate1
                                    : maxDate2;
                        String maxDateStr = null;
                        if (maxDate != null) {
                            SimpleDateFormat sdf =
                                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            maxDateStr = sdf.format(maxDate);
                        }
                        Map<String, String> map = new HashMap<String, String>();
                        map.put("driverId", driverId);
                        map.put("maxDate", maxDateStr);
                        times =
                            this.autoThrowBlacklistDao.findAlarmTimes4Driver(map);
                        // 报警次数大于自动拉黑设置的次数
                        if (times >= driverAlarmNum) {
                            this.blackListSave(loc, driverId, driver);
                        }
                    }
                }
            }
        }
        
    }
    
    private void saveByBatch(List<AlarmEntity> alarms)
        throws Exception {
        if (alarms == null) {
            LOGGER.debug(SystemContants.DEBUG_START, "无报警信息");
            return;
        }
        // 新增报警次数
        int time = 0;
        for (AlarmEntity alarm : alarms) {
            try {
                Date curDate = new Date();
                alarm.setCreateTime(curDate);
                alarm.setUpdateTime(curDate);
                // 1、保存报警信息 ,集群下可能其他节点已经保存，此时会抛出异常
                alarmManageDao.save(alarm);
                String enterpriseName =
                    this.dangerVehicleDao.findNameByLicencePlateNo(alarm.getCarrierName());
                alarm.setEnterpriseName(enterpriseName);
                // 2、通知websocket
                MonitorWebsocket.notifyAlarm(alarm);
                time++;
            }
            catch (Exception e) {
                // 如果异常为是主键已存在，则代表其他节点已经添加此报警信息，此处不需要处理
                // TODO 其他异常处理
                e.getMessage();
            }
        }
        // 3、TODO 更新报警次数
    }
    
    /**
     * 〈一句话功能简述〉自动拉黑处理 〈功能详细描述〉
     * 
     * @param loc
     *            LocationEntity
     * @param driverId
     *            driverId
     * @param driver
     *            driver
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    private void blackListSave(LocationEntity loc, String driverId,
        String driver)
        throws Exception {
        // 报警车辆信息
        String carrierName = loc.getCarrierName();
        DangerVehicleEntity dangerVehicleEntity =
            this.dangerVehicleDao.findByName(carrierName);
        if (dangerVehicleEntity != null) {
            
            String corporateNo = dangerVehicleEntity.getCorporateNo();
            Blacklist blacklist = new Blacklist();
            // 封装拉黑实体对象
            blacklist.setId(C503StringUtils.createUUID());
            blacklist.setCorporateNo(corporateNo);
            // 拉黑类型为自动拉黑
            blacklist.setBlacklistType("104002002");
            blacklist.setBlacklistDate(new Date());
            if (driverId == null) {
                // 被来黑对象类型：车辆
                blacklist.setObjectType("104001003");
                blacklist.setVehicle(carrierName);
            }
            else {
                // 被来黑对象类型：驾驶员
                blacklist.setObjectType("104001002");
                blacklist.setDriverId(driverId);
                blacklist.setDriver(driver);
            }
            blacklist.setBlacklistReason("报警次数到达自动拉黑设置次数");
            blacklist.setRemove("0");
            Date curDate = new Date();
            blacklist.setCreateTime(curDate);
            blacklist.setUpdateTime(curDate);
            Blacklist bl = autoThrowBlacklistDao.findExsist(carrierName);
            if (driverId == null && bl == null) {
                // 车辆拉黑记录保存
                this.autoThrowBlacklistDao.save(blacklist);
            }
            else if (driverId != null) {
                // 驾驶员拉黑记录保存
                this.autoThrowBlacklistDao.save(blacklist);
            }
        }
    }
    
    /**
     * 〈一句话功能简述〉填充报警信息：企业、运单、驾驶员信息 〈功能详细描述〉
     * 
     * @param loc
     *            LocationEntity
     * @param alarms
     *            List<AlarmEntity>
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
    private void addAlarmInfo(LocationEntity loc, List<AlarmEntity> alarms)
        throws Exception {
        if (alarms == null) {
            LOGGER.debug(SystemContants.DEBUG_START, "无报警信息");
            return;
        }
        String carrierName = loc.getCarrierName();
        String corporateNo = null;
        String enterpriseName = null;
        DangerVehicleEntity car = this.dangerVehicleDao.findByName(carrierName);
        LOGGER.info(CommonConstant.FIND_SUC_OPTION, car);
        if (car != null) {
            // 企业信息
            corporateNo = car.getCorporateNo();
            enterpriseName = car.getEnterpriseName();
        }
        Map<String, Object> map = this.gwaybillNo(carrierName);
        String waybillNo = (String) map.get("waybillNo");
        String driverId = (String) map.get("driverId");
        Date alarmDate = null;
        Date gpstime = null;
        Date nowTime = new Date();
        String alarmDetails = "";
        for (AlarmEntity alarm : alarms) {
            if (alarm != null) {
                alarmDetails = alarm.getAlarmDetails();
                String header = "当前速度";
                String end = "km/h";
                //String reg = "当前速度([0-9]{1,})km/h";
                //java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(reg);
                //Matcher matcher =  pattern.matcher(alarmDetails);
                gpstime = loc.getGpstime();
                alarmDate = gpstime == null ? nowTime : gpstime;
                // 补充企业
                alarm.setEnterpriseName(enterpriseName);
                alarm.setCarrierName(carrierName);
                alarm.setCorporateNo(corporateNo);
                alarm.setWaybillNo(waybillNo);
                alarm.setDriverId(driverId);
                alarm.setCreateTime(new Date());
                alarm.setUpdateTime(new Date());
                alarm.setAlarmDate(alarmDate);
                if(alarmDetails.indexOf(header) > -1 && alarmDetails.indexOf(end) > -1){
                    int speed = Integer.parseInt(alarmDetails.split(header)[1].split(end)[0]) / 10;
                    /*if(matcher.find()){
                        alarm.setAlarmDetails(matcher.replaceAll("当前速度"+Integer.valueOf(matcher.group(0))/10+"km/h"));
                    }*/
                    //报警描述中的速度值不应该来源于loc,loc.getSpeed()而应该取自其本身的值并除以10
                    alarm.setAlarmDetails(alarmDetails.split(header)[0] + header + String.valueOf(speed) + end);
                }
            }
        }
    }
    
    /**
     * 〈一句话功能简述〉gwaybillNo 〈功能详细描述〉
     * 
     * @param carrierName
     *            carrierName
     * @return Map<String, Object>
     * @see [类、类#方法、类#成员]
     */
    private Map<String, Object> gwaybillNo(String carrierName) {
        Map<String, Object> map = new HashMap<>();
        // 运单信息
        WayBillEntity wayBilldata = null;
        try {
            List<WayBillEntity> wayBills =
                this.carInfoDao.findWayBillData(carrierName);
            if (!CollectionUtils.isEmpty(wayBills)) {
                wayBilldata =
                    this.carInfoDao.findWayBillData(carrierName).get(0);
                LOGGER.info(CommonConstant.FIND_SUC_OPTION, wayBilldata);
            }
            if (null != wayBilldata) {
                // 货单号
                map.put("waybillNo", wayBilldata.getCheckno());
                // 驾驶员id
                map.put("driverId", wayBilldata.getDriverId());
                // 驾驶员手机
                map.put("driverTel", wayBilldata.getDriverphone());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}
