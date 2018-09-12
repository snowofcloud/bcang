package com.c503.sc.jxwl.vehiclemonitor.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.c503.sc.base.dao.IBaseDao;
import com.c503.sc.base.service.impl.BaseServiceImpl;
import com.c503.sc.jxwl.common.bean.LocationEntity;
import com.c503.sc.jxwl.vehiclemonitor.bean.AdministrativeAreaEntity;
import com.c503.sc.jxwl.vehiclemonitor.bean.InOutAreaEntity;
import com.c503.sc.jxwl.vehiclemonitor.dao.IAcrossLogDao;
import com.c503.sc.jxwl.vehiclemonitor.dao.IAdministrativeAreaDao;
import com.c503.sc.jxwl.vehiclemonitor.dao.IInOutAreaDao;
import com.c503.sc.jxwl.zcpt.bean.AcrossLogEntity;
import com.c503.sc.jxwl.zcpt.pointInPolygon.IsPointInPolygon;
import com.c503.sc.jxwl.zcpt.pointInPolygon.Point;
import com.c503.sc.jxwl.zcpt.service.IAcrossLogService;
import com.c503.sc.log.LoggingManager;
import com.c503.sc.utils.basetools.C503StringUtils;

@Service("acrossLogService")
public class AcrossLogServiceImpl extends BaseServiceImpl<AcrossLogEntity>
    implements IAcrossLogService {
    /** 日志器 */
    private static final LoggingManager LOGGER =
        LoggingManager.getLogger(AcrossLogServiceImpl.class);
    
    /** 跨域记录Dao */
    @Resource(name = "acrossLogDao")
    private IAcrossLogDao acrossLogDao;
    
    /** 行政区域记录Dao */
    @Resource(name = "administrativeAreaDao")
    private IAdministrativeAreaDao administrativeAreaDao;
    
    /** 车辆进出区域信息Dao */
    @Resource(name = "inOutAreaDao")
    private IInOutAreaDao inOutAreaDao;
    
    @Override
    public boolean batchSaveAcross(List<LocationEntity> locList)
        throws Exception {
        boolean isSuc = false;
        int size = locList.size();
        LocationEntity loc = null;
        for (int i = 0; i < size; i++) {
            loc = locList.get(i);
            if (loc != null) {
                this.saveAcrossLog4AdministrativeArea(loc);
                isSuc = true;
            }
        }
        return isSuc;
    }
    
    @Override
    public IBaseDao<AcrossLogEntity> getBaseDao() {
        return acrossLogDao;
    }
    
    @Override
    protected LoggingManager logger() {
        return LOGGER;
    }
    
    /**
     * 添加跨域记录（行政区域）
     * 
     * @param loc
     * @throws Exception
     */
    private void saveAcrossLog4AdministrativeArea(LocationEntity loc)
        throws Exception {
        
        String longitudeStr = loc.getLongitude();
        String latitudeStr = loc.getLatitude();
        String carrierName = loc.getCarrierName();
        List<AdministrativeAreaEntity> areas = administrativeAreaDao.findAll();
        if (!CollectionUtils.isEmpty(areas)) {
            Point locPoint = null;
            JSONArray ps = null;
            String areaId = null;
            for (AdministrativeAreaEntity area : areas) {
                locPoint =
                    new Point(Double.parseDouble(longitudeStr),
                        Double.parseDouble(latitudeStr));
                ps = JSON.parseArray(area.getPoints());
                int size = ps.size();
                List<Point> points = new ArrayList<Point>(size);
                JSONArray p = null;
                Point point = null;
                Double longitude = null;
                Double latitude = null;
                for (int i = 0; i < size; i++) {
                    p = (JSONArray) ps.get(i);
                    longitude = p.getDouble(0);
                    latitude = p.getDouble(1);
                    point = new Point(longitude, latitude);
                    points.add(point);
                }
                if (IsPointInPolygon.isInPolygon(locPoint, points)) {
                    areaId = area.getAreaId();
                    // 在此区域内
                    saveAcrossLog(carrierName,
                        AcrossLogEntity.ADMINISTRATIVE_AREA,
                        areaId);
                }
            }
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
    
}
