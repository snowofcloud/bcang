/**
 * 文件名：DangerVehicleServiceImpl.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-7-20
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.vehiclemonitor.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.c503.sc.base.exception.CustomException;
import com.c503.sc.jxwl.common.bean.LocationEntity;
import com.c503.sc.jxwl.common.constant.CommonConstant;
import com.c503.sc.jxwl.common.constant.SysCommonConstant;
import com.c503.sc.jxwl.common.websocket.MonitorWebsocket;
import com.c503.sc.jxwl.vehiclemonitor.bean.EnterpriseEntity;
import com.c503.sc.jxwl.vehiclemonitor.constant.BizExConstants;
import com.c503.sc.jxwl.vehiclemonitor.dao.ICarLocationDao;
import com.c503.sc.jxwl.vehiclemonitor.dao.IDangerFileRelaDao;
import com.c503.sc.jxwl.vehiclemonitor.dao.IDangerVehicleDao;
import com.c503.sc.jxwl.vehiclemonitor.dao.IEnterpriseDao;
import com.c503.sc.jxwl.zcpt.bean.DangerFileRelaEntity;
import com.c503.sc.jxwl.zcpt.bean.DangerVehicleEntity;
import com.c503.sc.jxwl.zcpt.bean.TerminalEntity;
import com.c503.sc.jxwl.zcpt.bean.WayBillEntity;
import com.c503.sc.jxwl.zcpt.dao.ICarInfoDao;
import com.c503.sc.jxwl.zcpt.service.IDangerVehicleService;
import com.c503.sc.jxwl.zcpt.service.ITerminalService;
import com.c503.sc.jxwl.zcpt.vo.WlEnpCarVo;
import com.c503.sc.log.LoggingManager;
import com.c503.sc.log.resource.ResourceManager;
import com.c503.sc.utils.basetools.C503DateUtils;
import com.c503.sc.utils.basetools.C503StringUtils;
import com.c503.sc.utils.common.SystemContants;

/**
 * 
 * 〈一句话功能简述〉危险品车辆信息业务层 〈功能详细描述〉
 * 
 * @author guqh
 * @version [版本号, 2016-7-20]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Service(value = "dangerVehicleService")
public class DangerVehicleServiceImpl implements IDangerVehicleService {
    /** 日志器 */
    private static final LoggingManager LOGGER =
        LoggingManager.getLogger(DangerVehicleServiceImpl.class);
    
    /** 危险品车辆信息Dao */
    @Resource(name = "dangerVehicleDao")
    private IDangerVehicleDao dangerVehicleDao;
    
    /** 危险品车辆附件关系dao */
    @Autowired
    private IDangerFileRelaDao dangerFileRelaDao;
    
    /** 企业信息Dao */
    @Resource(name = "enterpriseDao")
    private IEnterpriseDao enterpriseDao;
    
    /** 企业信息Dao */
    @Resource
    private ITerminalService terminalService;
    
    /** 车辆信息数据接口 */
    @Resource(name = "carInfoDao")
    private ICarInfoDao carInfoDao;
    
    /** 车辆实时位置信息数据接口 */
    @Resource(name = "carLocationDao")
    private ICarLocationDao carLocationDao;
    
    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public List<DangerVehicleEntity> findByParams(Map<String, Object> map)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, map);
        List<DangerVehicleEntity> list = null;
        String curDate =
            C503DateUtils.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss");
        map.put("remove", SystemContants.ON);
        map.put("curDate", curDate);
        list = this.dangerVehicleDao.findByParams(map);
        LOGGER.debug(SystemContants.DEBUG_END, map);
        return list;
    }
    
    @Override
    public String findByco(String co)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, co);
        String entname = "";
        entname = this.dangerVehicleDao.findByco(co);
        LOGGER.debug(SystemContants.DEBUG_END, co);
        return entname;
    }
    
    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public Object save(DangerVehicleEntity entity)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, entity);
        // 判断车牌号是否存在
        if (StringUtils.isNotEmpty(this.dangerVehicleDao.findLicencePlateNoExit(entity.getLicencePlateNo(),
            null))) {
            throw new CustomException(
                BizExConstants.VEHICLE_LICENCEPLATENOEXIST);
        }
        
        entity.setId(C503StringUtils.createUUID());
        Date curDate = new Date();
        entity.setCreateTime(curDate);
        entity.setUpdateTime(curDate);
        entity.setRemove(SystemContants.ON);
        
        try {
            this.dangerVehicleDao.save(entity);
            String[] fileIds = entity.getFileIds();
            if (null != fileIds && 0 < fileIds.length) {
                List<DangerFileRelaEntity> list =
                    new ArrayList<DangerFileRelaEntity>();
                this.createFileRelationVal(list, entity);
                this.dangerFileRelaDao.saves(list);
            }
            // 更新企业车辆数据
            this.updateEnterpriseVerchierNum(entity.getCorporateNo());
        }
        catch (Exception e) {
            throw new CustomException(CommonConstant.SYS_EXCEPTION, e, entity);
        }
        LOGGER.debug(SystemContants.DEBUG_END, entity);
        
        return entity.getId();
    }
    
    @Override
    @Transactional(rollbackFor = java.lang.Exception.class)
    public int delete(Map<String, Object> map)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, map);
        int delLine = 0;
        map.put("remove", SystemContants.OFF);
        String vehicle =
            this.dangerVehicleDao.findByVehicle((String) map.get("id"));
        
        if (StringUtils.equals("1",
            this.dangerVehicleDao.findExsitById((String) map.get("id")))) {
            throw new CustomException(BizExConstants.DATAISNOTEXIST);
        }
        try {
            delLine = this.dangerVehicleDao.delete(map);
            // 更新企业车辆数据
            this.updateEnterpriseVerchierNum((String) map.get("conporateNo"));
            List<String> vE = this.dangerVehicleDao.findVehicleIdList(vehicle);
            if (vE.size() != 0) {
                this.dangerVehicleDao.deleteVehicle(vehicle);
            }
        }
        catch (Exception e) {
            throw new CustomException(CommonConstant.SYS_EXCEPTION, e, map);
        }
        // 删除后的车辆 如果在资质管理模块中 则变为注销状态
        DangerVehicleEntity entity = findById((String) map.get("id"));
        this.dangerVehicleDao.updateLicenceInfo(entity);
        LOGGER.debug(SystemContants.DEBUG_END, map);
        
        return delLine;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(rollbackFor = java.lang.Exception.class)
    public Object update(DangerVehicleEntity entity)
        throws Exception {
        // 判断车牌号是否存在
        // 编辑时车牌号与id肯定都是已存在的，不能判断是否已存在
        // if (StringUtils.isNotEmpty(this.dangerVehicleDao
        // .findLicencePlateNoExit(entity.getLicencePlateNo(),
        // entity.getId()))) {
        // throw new CustomException(
        // BizExConstants.VEHICLE_LICENCEPLATENOEXIST);
        // }
        if (StringUtils.equals("1",
            this.dangerVehicleDao.findExsitById(entity.getId()))) {
            throw new CustomException(BizExConstants.DATAISNOTEXIST);
        }
        
        this.dangerVehicleDao.update(entity);
        
        String[] fileIds = entity.getFileIds();
        if (fileIds.length > 1) {
            throw new CustomException(BizExConstants.VEHICLE_FILE_ONLY_ONE);
        }
        if (fileIds.length > 0) {// 附件id不为空，才执行保存，否则不保存，即不向数据库插入记录
            this.dangerVehicleDao.deleteRela(entity.getId());
            List<DangerFileRelaEntity> list =
                new ArrayList<DangerFileRelaEntity>();
            if (null != fileIds && 0 < fileIds.length) {
                this.createFileRelationVal(list, entity);
            }
            if (list.size() > 0) {
                this.dangerFileRelaDao.saves(list);
            }
        }
        
        return entity;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public DangerVehicleEntity findById(String id)
        throws Exception {
        DangerVehicleEntity entity = null;
        Map<String, String> map = new HashMap<String, String>();
        map.put("id", id);
        String curDate =
            C503DateUtils.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss");
        map.put("curDate", curDate);
        entity = this.dangerVehicleDao.findById(map);
        
        return entity;
    }
    
    @Override
    public List<Map<String, Object>> findDangerVehicleByParams(
        Map<String, Object> map)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, map);
        map.put("remove", SystemContants.ON);
        List<Map<String, Object>> list =
            this.dangerVehicleDao.findDangerVehicleByParams(map);
        LOGGER.debug(SystemContants.DEBUG_END, map);
        return list;
    }
    
    public LocationEntity findRealLocation(String carrierName)
        throws Exception {
        return dangerVehicleDao.findRealLocation(carrierName);
    }
    
    @Override
    public List<LocationEntity> findRealLocationAll()
        throws Exception {
        String curDate =
            C503DateUtils.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss");
        List<LocationEntity> list =
            dangerVehicleDao.findRealLocationAll(curDate);
        return list;
    }
    
    @Override
    public List<LocationEntity> findRealLocationsByParams(
        Map<String, Object> map)
        throws Exception {
        // 查询车辆实时位置
        return dangerVehicleDao.findRealLocationsByParams(map);
    }
    
    @Override
    public int findTotal()
        throws Exception {
        return this.dangerVehicleDao.findTotal();
    }
    
    @Override
    public List<LocationEntity> findAll()
        throws Exception {
        return this.dangerVehicleDao.findAll();
    }
    
    @Override
    public List<WlEnpCarVo> findEnpCarForWl(String corporateNo)
        throws Exception {
        String curDate =
            C503DateUtils.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss");
        return this.dangerVehicleDao.findEnpCarForWl(curDate, corporateNo);
    }
    
    @Override
    public List<String> findCarNamesBycorporateNo(String corporateNo)
        throws Exception {
        return this.dangerVehicleDao.findCarNamesBycorporateNo(corporateNo);
    }
    
    @Override
    public DangerVehicleEntity findByCarrierName(String carrierName)
        throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("carrierName", carrierName);
        String curDate =
            C503DateUtils.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss");
        map.put("curDate", curDate);
        return this.dangerVehicleDao.findByCarrierName(map);
    }
    
    @Override
    public List<String> findCarrierName(Map<String, Object> map)
        throws Exception {
        return this.dangerVehicleDao.findCarrierName(map);
    }
    
    @Override
    public int updateVehicleSrcFlag(Map<String, Object> map)
        throws Exception {
        return this.dangerVehicleDao.updateVehicleSrcFlag(map);
    }
    
    @Override
    public Map<String, Object> findMapData(String carNo)
        throws Exception {
        // 记录程序进入方法调试日志
        LOGGER.debug(SystemContants.DEBUG_START, carNo);
        
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            // 组装数据
            // 获取车辆信息
            DangerVehicleEntity carInfor = dangerVehicleDao.findByName(carNo);
            map.put("carInfo", carInfor);
            // 获取运单信息 (findWayBillData接口会返回多条运单记录，但是只取第一条返回给前端)
            List<WayBillEntity> wayBills =
                this.carInfoDao.findWayBillData(carNo);
            if (!CollectionUtils.isEmpty(wayBills)) {
                WayBillEntity billInfo = wayBills.get(0);
                map.put("WayBillInfo", billInfo);
            }
            // 车辆照片
            String carPhoto = this.findCarPicId(carNo);
            map.put("carPicId", carPhoto);
            // 记录操作成功信息
            LOGGER.debug(CommonConstant.FIND_SUC_OPTION, map);
        }
        catch (Exception e) {
            throw new CustomException(CommonConstant.SYS_EXCEPTION, e, carNo);
        }
        // 记录程序进入方法调试日志
        LOGGER.debug(SystemContants.DEBUG_END, map);
        
        return map;
    }
    
    @Override
    public List<DangerVehicleEntity> findVehicle(Map<String, Object> map)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, map);
        List<DangerVehicleEntity> list = this.dangerVehicleDao.findVehicle(map);
        LOGGER.debug(SystemContants.DEBUG_END, map);
        return list;
    }
    
    @Override
    public List<DangerVehicleEntity> findLicencePlateNo(String corporateNo)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, corporateNo);
        List<DangerVehicleEntity> licencePlateNo = null;
        try {
            licencePlateNo =
                this.dangerVehicleDao.findLicencePlateNo(corporateNo);
            LOGGER.info(CommonConstant.FIND_SUC_OPTION, licencePlateNo);
        }
        catch (Exception e) {
            throw new CustomException(CommonConstant.SYS_EXCEPTION, e);
        }
        LOGGER.debug(SystemContants.DEBUG_END, licencePlateNo);
        
        return licencePlateNo;
    }
    
    /****************************************** 企业相关车辆 **************************************/
    
    /**
     * 〈一句话功能简述〉findCars
     * 〈功能详细描述〉
     * 
     * @param map map 根据企业名称（含法人代码）、匹配条件
     * @return List<DangerVehicleEntity>
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @Override
    public List<DangerVehicleEntity> findCars(Map<String, Object> map)
        throws Exception {
        return this.dangerVehicleDao.findCarsByParams(map);
    }
    
    /**
     * 〈一句话功能简述〉findCarInfoById
     * 〈功能详细描述〉
     * 
     * @param id id
     * @return DangerVehicleEntity
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @Override
    public DangerVehicleEntity findCarInfoById(String id)
        throws Exception {
        String curDate =
            C503DateUtils.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss");
        Map<String, String> map = new HashMap<String, String>();
        map.put("id", id);
        map.put("curDate", curDate);
        return this.dangerVehicleDao.findCarInfoById(map);
    }
    
    @Override
    public Map<String, Object> getRtLocation(Map<String, Object> map)
        throws Exception {
        Map<String, Object> data = new HashMap<String, Object>();
        List<LocationEntity> dvs =
            dangerVehicleDao.findRealLocationsByParams(map);
        // 组装数据
        for (LocationEntity loc : dvs) {
            String carrierName = loc.getCarrierName();
            loc.setPoint();
            // 获取车辆信息
            DangerVehicleEntity dv = dangerVehicleDao.findByName(carrierName);
            
            // 获取终端信息
            TerminalEntity terminal =
                terminalService.findById(loc.getTerminalID());
            
            data.put("location", loc);
            data.put("carInfo", dv);
            data.put("terminalInfo", terminal);
            // 获取运单信息
            data.put("WayBillInfo",
                dangerVehicleDao.findWayBillData(carrierName));
            data.put("carPicId", this.findCarPicId(carrierName));
        }
        return data;
    }
    
    @Override
    public boolean batchUpdateRealLocation(List<LocationEntity> locList)
        throws Exception {
        boolean isSuc = false;
        int size = locList.size();
        String speed = "";
        LocationEntity loc = null;
        for (int i = 0; i < size; i++) {
            loc = locList.get(i);
            speed = loc.getSpeed();
            if(!StringUtils.isEmpty(speed)){
                int s = (Integer.valueOf(speed))/10;
                loc.setSpeed(String.valueOf(s));
            }
            this.updateRealLocation(loc);
            isSuc = true;
        }
        return isSuc;
    }
    
    private void updateRealLocation(LocationEntity loc)
        throws Exception {
        if (loc == null) {
            // TODO 参数检查
            // CustomException ce = new
            // CustomException(BizExConstants.PARAM_E,terminal);
            // ce.setErrorMessage("保存终端异常,参数terminal=null");
            // throw ce;
            return;
        }
        /*
         * 1、更新（或保存）车辆实时位置 2、通知websocet更新车辆位置
         */
        String name = loc.getCarrierName();
        if (StringUtils.isNotEmpty(name)) {
            // 1、更新（或保存）车辆实时位置
            LocationEntity locDB = dangerVehicleDao.findRealLocation(name);
            if (locDB == null) {
                try {
                    this.carLocationDao.save(loc);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else {
                this.carLocationDao.update(loc);
            }
            // 2、通知websocet更新车辆位置
            String carrierName = loc.getCarrierName();
            locDB = dangerVehicleDao.findRealLocation(name);
            // 通过接口查询车辆来源
            TerminalEntity terminalEntity =
                terminalService.findByCarrierName(carrierName);
            if (terminalEntity != null) {
                String terminalSource = terminalEntity.getTerminalSource();
                // 终端来源为空
                if (C503StringUtils.isEmpty(terminalSource)) {
                    terminalSource = this.getTerminalSourceJx();
                }
                locDB.setOrgin(terminalSource);
            }
            locDB.setSimNum(loc.getSimNum());
            LOGGER.debug(SystemContants.DEBUG_START,
                "MonitorWebsocket.notifyLocation :" + locDB);
            MonitorWebsocket.notifyLocation(locDB);
        }
    }
    
    /**
     * 〈一句话功能简述〉创建危险品车辆附件关系 〈功能详细描述〉
     * 
     * @param dangerRelaVals
     *            List<DangerFileRelaEntity>
     * @param dangerVehicle
     *            DangerVehicleEntity
     * @see [类、类#方法、类#成员]
     */
    private void createFileRelationVal(
        List<DangerFileRelaEntity> dangerRelaVals,
        DangerVehicleEntity dangerVehicle) {
        Date curDate = new Date();
        for (String fileId : dangerVehicle.getFileIds()) {
            DangerFileRelaEntity relaVal = new DangerFileRelaEntity();
            relaVal.setId(C503StringUtils.createUUID());
            relaVal.setDangerVehicleId(dangerVehicle.getId());
            relaVal.setFileId(fileId);
            relaVal.setCreateBy(dangerVehicle.getCreateBy());
            relaVal.setCreateTime(curDate);
            relaVal.setUpdateBy(dangerVehicle.getUpdateBy());
            relaVal.setUpdateTime(curDate);
            relaVal.setRemove(SystemContants.ON);
            
            dangerRelaVals.add(relaVal);
        }
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
    
    /**
     * 〈一句话功能简述〉新增或删除车辆信息的时候更新对应企业的车辆数量 〈功能详细描述〉
     * 
     * @param corporateNo
     *            corporateNo
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    private void updateEnterpriseVerchierNum(String corporateNo)
        throws Exception {
        int verchiverNum = 0;
        
        verchiverNum =
            this.dangerVehicleDao.getDangerVehicNumByCorporateNo(corporateNo);
        EnterpriseEntity entity = new EnterpriseEntity();
        entity.setVehicleNum(new BigDecimal(verchiverNum));
        
        entity.setCorporateNo(corporateNo);
        this.enterpriseDao.updateVerchierNumByCorporateNo(entity);
        
    }
    
    /**
     * 〈一句话功能简述〉获得车辆图片
     * 〈功能详细描述〉
     * 
     * @param carNo 车牌号
     * @return 车辆照片ID
     * @throws Exception 系统异常
     * @see [类、类#方法、类#成员]
     */
    private String findCarPicId(String carNo)
        throws Exception {
        String carPhoto = dangerFileRelaDao.findCarPicId(carNo);
        return carPhoto;
    }
}
