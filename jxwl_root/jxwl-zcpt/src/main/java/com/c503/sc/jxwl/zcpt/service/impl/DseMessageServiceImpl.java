/**
 * 文件名：DseMessageServiceImpl.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2015年12月30日
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.zcpt.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.c503.sc.base.common.NumberContant;
import com.c503.sc.jxwl.common.bean.AlarmEntity;
import com.c503.sc.jxwl.common.bean.LocationEntity;
import com.c503.sc.jxwl.common.constant.CommonConstant;
import com.c503.sc.jxwl.zcpt.bean.TerminalEntity;
import com.c503.sc.jxwl.zcpt.service.IDseMessageService;
import com.c503.sc.jxwl.zcpt.service.ITerminalService;
import com.c503.sc.log.LoggingManager;
import com.c503.sc.utils.basetools.C503StringUtils;
import com.c503.sc.utils.common.SystemContants;

/**
 * 
 * 〈一句话功能简述〉〈功能详细描述〉提供〉短信、状态存储service实现
 * 
 * @author Luy
 * @version [版本号, 2015年12月30日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Service(value = "dseMessageService")
public class DseMessageServiceImpl implements IDseMessageService {

    /** 记录日志 */
    private static final LoggingManager LOGGER =
        LoggingManager.getLogger(DseMessageServiceImpl.class);
    
    /** terminalService */
    @Resource(name = "terminalService")
    private ITerminalService terminalService;
    
    /** 车辆实时位置 */
    private static LinkedBlockingQueue<LocationEntity> realLocationBlockingQueue =
        new LinkedBlockingQueue<LocationEntity>();
    
    /** 行政区域出入境记录 */
    private static LinkedBlockingQueue<LocationEntity> adminAreaBlockingQueue =
        new LinkedBlockingQueue<LocationEntity>();
    
    /** 偏离路线报警记录 */
    private static LinkedBlockingQueue<LocationEntity> lineAlarmBlockingQueue =
        new LinkedBlockingQueue<LocationEntity>();
    
    /** 限制区域报警记录 */
    private static LinkedBlockingQueue<LocationEntity> limitAreaBlockingQueue =
        new LinkedBlockingQueue<LocationEntity>();
    
    /** 车辆报警信息 */
    private static LinkedBlockingQueue<LocationEntity> alarmBlockingQueue =
        new LinkedBlockingQueue<LocationEntity>();
    
    /**
     * {@inheritDoc}
     */
    public boolean saveMessage(String message)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, message);
        LOGGER.info(CommonConstant.CONSUMER_SUC_OPTION,
            "fetch data from kafka ---------> " + message);
        JSONObject json = JSONObject.parseObject(message);
        // 协议解析参考网关项目（jxwl-gateway）JT808Handler类
        String type = json.getString("type");
        // 位置信息
        if ("LocationData".equals(type)) {
            LocationEntity loc = json.getObject("data", LocationEntity.class);
            // 解析报警信息
            JSONObject data = json.getJSONObject("data");
            
            // 调用终端接口，通过手机号查询车牌
            String simNum = loc.getSimNum();
            simNum = simNum == null ? "" : simNum;
            int len = simNum.length();
            while (len < NumberContant.ONE_TWO) {
                simNum = "0" + simNum;
                ++len;
            }
            String cn = loc.getCarrierName();
            if (StringUtils.isEmpty(cn)) {
                LOGGER.debug(SystemContants.DEBUG_START,
                    "没有车牌号信息，通过手机号获取：simNum=" + simNum);
                TerminalEntity terminal =
                    this.terminalService.findBySimNum(simNum);
                if (terminal == null) {
                    System.err.println("通过手机号没有查询到终端：" + simNum);
                    LOGGER.info(SystemContants.DEBUG_START,
                        "没有车牌号信息，通过手机号获取：simNum=" + simNum);
                    return false;
                }
                // 补充车牌信息
                loc.setCarrierName(terminal.getCarrierName());
                // 补充手机卡号
                loc.setSimNum(terminal.getCardNum());
                // 补充终端ID
                loc.setTerminalID(terminal.getTerminalSerialID());
            }
            realLocationBlockingQueue.add(loc);
            adminAreaBlockingQueue.add(loc);
            lineAlarmBlockingQueue.add(loc);
            limitAreaBlockingQueue.add(loc);
            
            this.parseAlarmInfo(loc, data);
            List<AlarmEntity> alarms = loc.getAlarms();
            if (alarms != null) {
                for (AlarmEntity alarm : alarms) {
                    if (alarm != null) {
                        alarm.setSimNum(loc.getSimNum());
                        alarm.setCarrierName(loc.getCarrierName());
                        alarm.setTerminalId(loc.getTerminalID());
                    }
                }
            }
            alarmBlockingQueue.add(loc);
            LOGGER.info(SystemContants.DEBUG_START,
                "fetch data from kafka loc is ---------> " + loc);
        }
        return false;
    }
    
    /**
     * 〈一句话功能简述〉获得实时位置队列
     * 〈功能详细描述〉
     * 
     * @return 实时位置队列
     * @see [类、类#方法、类#成员]
     */
    public static LinkedBlockingQueue<LocationEntity> getRealLocationBlockingQueue() {
        return realLocationBlockingQueue;
    }
    
    /**
     * 〈一句话功能简述〉获得报警信息队列
     * 〈功能详细描述〉
     * 
     * @return 报警信息队列
     * @see [类、类#方法、类#成员]
     */
    public static LinkedBlockingQueue<LocationEntity> getAlarmBlockingQueue() {
        return alarmBlockingQueue;
    }
    
    /**
     * 〈一句话功能简述〉获得行政区域出入境记录队列
     * 〈功能详细描述〉
     * 
     * @return 行政区域出入境记录队列
     * @see [类、类#方法、类#成员]
     */
    public static LinkedBlockingQueue<LocationEntity> getAdminAreaBlockingQueue() {
        return adminAreaBlockingQueue;
    }
    
    /**
     * 〈一句话功能简述〉获得偏离路线报警队列
     * 〈功能详细描述〉
     * 
     * @return 偏离路线报警队列
     * @see [类、类#方法、类#成员]
     */
    public static LinkedBlockingQueue<LocationEntity> getLineAlarmBlockingQueue() {
        return lineAlarmBlockingQueue;
    }
    
    /**
     * 〈一句话功能简述〉获得限制区域报警队列
     * 〈功能详细描述〉
     * 
     * @return 限制区域报警队列
     * @see [类、类#方法、类#成员]
     */
    public static LinkedBlockingQueue<LocationEntity> getLimitAreaBlockingQueue() {
        return limitAreaBlockingQueue;
    }
    
    /**
     * 〈一句话功能简述〉解析报警信息
     * 〈功能详细描述〉
     * 
     * @param loc LocationEntity
     * @param data JSONObject
     * @see [类、类#方法、类#成员]
     */
    private void parseAlarmInfo(LocationEntity loc, JSONObject data) {
        if (null == data) {
            return;
        }
        String alrs = data.getString("alarms");
        if (null != alrs) {
            List<AlarmEntity> listalarms = new ArrayList<>();
            JSONArray arrs = JSON.parseArray(alrs);
            Date alarmDate = null;
            Date gpstime = null;
            Date nowTime = new Date();
            for (Object arr : arrs) {
                gpstime = loc.getGpstime();
                alarmDate = gpstime == null ? nowTime : gpstime;
                AlarmEntity alarm = new AlarmEntity();
                JSONObject obj = JSON.parseObject(JSON.toJSONString(arr));
                String alarmDealStatus = obj.getString("alarmDealStatus");
                String alarmDetails = obj.getString("alarmDetails");
                String alarmType = obj.getString("alarmType");
                String simNum = obj.getString("simNum");
                alarm.setId(C503StringUtils.createUUID());
                alarm.setAlarmDealStatus(alarmDealStatus)
                    .setAlarmDetails(alarmDetails)
                    .setAlarmType(alarmType)
                    .setSimNum(simNum)
                    .setAlarmDate(alarmDate)
                    .setAlarmNo("B" + System.currentTimeMillis());
                listalarms.add(alarm);
            }
            loc.setAlarms(listalarms);
        }
    }
    
}
