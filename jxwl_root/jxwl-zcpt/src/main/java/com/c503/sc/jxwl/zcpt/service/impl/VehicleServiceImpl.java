/*
 * 文件名：VehicleServiceImpl
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2017-10-23
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.zcpt.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.c503.sc.jxwl.common.bean.LocationEntity;
import com.c503.sc.jxwl.common.constant.CommonConstant;
import com.c503.sc.jxwl.common.websocket.MonitorWebsocket;
import com.c503.sc.jxwl.zcpt.bean.TerminalEntity;
import com.c503.sc.jxwl.zcpt.dao.IVehicleDao;
import com.c503.sc.jxwl.zcpt.service.ITerminalService;
import com.c503.sc.jxwl.zcpt.service.IVehicleService;
import com.c503.sc.log.LoggingManager;
import com.c503.sc.utils.common.NumberContant;
import com.c503.sc.utils.common.SystemContants;

@Service(value = "vehicleService")
public class VehicleServiceImpl implements IVehicleService {
    /** 日志器 */
    private static final LoggingManager LOGGER =
        LoggingManager.getLogger(VehicleServiceImpl.class);
    
    /** 车辆信息Dao */
    @Resource(name = "vehicleDao")
    private IVehicleDao vehicleDao;
    
    @Resource(name = "terminalService")
    private ITerminalService terminalService;
    
    /** 初始化在线车辆列表 */
    private static List<LocationEntity> onlineList =
        new ArrayList<LocationEntity>();
    
    /** 初始化离线车辆列表 */
    private static List<LocationEntity> offlineList =
        new ArrayList<LocationEntity>();
    
    /** 上线车辆集合 */
    private List<LocationEntity> carOnLineList =
        new ArrayList<LocationEntity>();
    
    /** 下线车辆集合 */
    private List<LocationEntity> carOffLineList =
        new ArrayList<LocationEntity>();
    
    /** 第一次标识 */
    private boolean first = true;
    
    @Override
    public void getCarOnOffline()
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        try {
            // 第一次 则从数据库中查询
            if (first) {// onlineList offlineList 初始化
                List<LocationEntity> list = this.vehicleDao.findAll();
                LOGGER.info(CommonConstant.FIND_SUC_OPTION, list);
                for (int i = 0; i < list.size(); i++) {
                    LocationEntity entity = list.get(i);
                    Date gpsTime = entity.getGpstime();
                    if (isOnline(gpsTime)) {
                        onlineList.add(entity);
                    }
                    else {
                        offlineList.add(entity);
                    }
                }
                first = false;
            }
            else {
                List<LocationEntity> list = this.vehicleDao.findAll();
                LOGGER.info(CommonConstant.FIND_SUC_OPTION, list);
                // 上线推送集合
                List<LocationEntity> onNOticeList =
                    new ArrayList<LocationEntity>();
                // 下线推送集合
                List<LocationEntity> offNOticeList =
                    new ArrayList<LocationEntity>();
                // 循环给车辆来源赋值
                for (LocationEntity loc : list) {
                    String carrierName = loc.getCarrierName();
                    TerminalEntity terminalEntity =
                        this.terminalService.findByCarrierName(carrierName);
                    if (terminalEntity != null) {
                        String terminalSource =
                            terminalEntity.getTerminalSource();
                        loc.setOrgin(terminalSource);
                    }
                }
                for (int i = 0; i < list.size(); i++) {
                    LocationEntity entity = list.get(i);
                    // 既不在 在线集合 也不在 离线集合 则添加到在线集合 和在线推送集合中
                    if (!onlineList.contains(entity)
                        && !offlineList.contains(entity)) {
                        onlineList.add(entity);
                        onNOticeList.add(entity);
                    }
                    else {
                        Date gpsTime = entity.getGpstime();
                        // 在 在线集合中 但现在离线了 则下线
                        if (onlineList.contains(entity)) {
                            if (!isOnline(gpsTime)) {
                                offNOticeList.add(entity);
                                onlineList.remove(entity);
                                offlineList.add(entity);
                            }
                        }
                        else if (offlineList.contains(entity)) {// 在 离线集合中
                                                                // 但现在在线了
                                                                // 则上线
                            if (isOnline(gpsTime)) {
                                onNOticeList.add(entity);
                                onlineList.add(entity);
                                offlineList.remove(entity);
                            }
                        }
                    }
                }
                setCarOnLineList(onNOticeList);
                setCarOffLineList(offNOticeList);
                Map<String, List<LocationEntity>> map =
                    new HashMap<String, List<LocationEntity>>();
                map.put("on", onNOticeList);
                map.put("off", offNOticeList);
                
                // 通知浏览器
                MonitorWebsocket.notifyOnOff(map);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            System.err.println("获取车辆上下线数据失败，通知浏览器失败！");
            LOGGER.error(CommonConstant.SYS_EXCEPTION, e);
        }
    }
    
    public List<LocationEntity> getCarOnLineList() {
        return carOnLineList;
    }
    
    public void setCarOnLineList(List<LocationEntity> carOnLineList) {
        this.carOnLineList = carOnLineList;
    }
    
    public List<LocationEntity> getCarOffLineList() {
        return carOffLineList;
    }
    
    public void setCarOffLineList(List<LocationEntity> carOffLineList) {
        this.carOffLineList = carOffLineList;
    }
    
    /**
     * 〈一句话功能简述〉是否在线 〈功能详细描述〉
     * 
     * @param date
     *            date
     * @return boolean boolean
     * @see [类、类#方法、类#成员]
     */
    private boolean isOnline(Date date) {
        if (date == null) {
            return false;
        }
        Date now = new Date();
        long time = now.getTime() - date.getTime();
        long minutes =
            time
                / (NumberContant.ONE_THOUSAND * NumberContant.SIX * NumberContant.TEN);
        if (minutes <= NumberContant.THIRTY) {
            return true;
        }
        return false;
    }
    
}
