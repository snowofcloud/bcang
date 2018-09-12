/*
 * 文件名：TerminalServiceImpl
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-8-1
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.zcpt.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Service;

import sc.c503.authclient.cache.CacheManagerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.c503.sc.base.common.NumberContant;
import com.c503.sc.base.exception.CustomException;
import com.c503.sc.jxwl.common.bean.AlarmEntity;
import com.c503.sc.jxwl.common.bean.LocationEntity;
import com.c503.sc.jxwl.common.bean.PageEntity;
import com.c503.sc.jxwl.common.constant.CommonConstant;
import com.c503.sc.jxwl.common.constant.SysCommonConstant;
import com.c503.sc.jxwl.common.util.network.JXWLHttpClientUtils;
import com.c503.sc.jxwl.zcpt.bean.TerminalEntity;
import com.c503.sc.jxwl.zcpt.bean.TerminalParamEntity;
import com.c503.sc.jxwl.zcpt.bean.TerminalUpgrade;
import com.c503.sc.jxwl.zcpt.bean.WayBillEntity;
import com.c503.sc.jxwl.zcpt.constant.BizExConstants;
import com.c503.sc.jxwl.zcpt.dao.IAlarmManageDao;
import com.c503.sc.jxwl.zcpt.dao.ICarInfoDao;
import com.c503.sc.jxwl.zcpt.dao.ITerminalDao;
import com.c503.sc.jxwl.zcpt.service.ITerminalService;
import com.c503.sc.log.LoggingManager;
import com.c503.sc.log.resource.ResourceManager;
import com.c503.sc.utils.basetools.C503PropertiesUtils;
import com.c503.sc.utils.basetools.C503StringUtils;
import com.c503.sc.utils.cache.CacheManager;
import com.c503.sc.utils.cache.ICache;
import com.c503.sc.utils.common.SystemContants;

/**
 * 位置服务平台服务类实现
 * 
 * @author qianxq
 * @version [版本号, 2016-8-1]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Service(value = "terminalService")
public class TerminalServiceImpl implements ITerminalService {
    
    /** 日志器 */
    private static final LoggingManager LOGGER =
        LoggingManager.getLogger(TerminalServiceImpl.class);
    
    /** 缓存管理器 */
    private static final CacheManager MANAGER =
        CacheManagerFactory.getInstence().getCacheManager();
    
    /** carInfoDao */
    @Resource(name = "carInfoDao")
    private ICarInfoDao carInfoDao;
    
    @Resource(name = "terminalDao")
    private ITerminalDao terminalDao;
    
    @Resource(name = "alarmManageDao")
    private IAlarmManageDao alarmManageDao;
    
    private List<TerminalEntity> terminalBaseInfoList = new ArrayList<TerminalEntity>();
    
    private Map<String, Object> carNo2TerminalSourceMap = new HashMap<String, Object>();
    
    
    @Override
    public void refreshTerminalBaseInfo()
        throws Exception{
        Map<String, String> map = new HashMap<String, String>();
        map.put("manufactureID", null);
        map.put("terminalSerialID", null);
        map.put("carrierName", null);
        map.put("page", null);
        map.put("rows", null);
        map.put("simNum", null);
        String url = this.getUrl();
        String result =
            JXWLHttpClientUtils.doPost(url + "QueryTerminalBasicInfo.action",
                findALLVal(map));
        JSONObject jsonObject = JSON.parseObject(result);
        Object data = jsonObject.get("data");
        String str = JSONObject.toJSONString(data);
        List<TerminalEntity> terminalListAll =
            JSONArray.parseArray(str, TerminalEntity.class);
        //this.setTerminalBaseInfoList(terminalListAll);
        //创建从车牌号到终端来源的映射
        this.generateHashMap1(terminalListAll);
        //使用redis缓存，将从车牌号到终端来源的映射存储到缓存中
        this.generateHashMap2(terminalListAll);
    }
    
    private void generateHashMap2(List<TerminalEntity> list)
        throws Exception{
        String key = null;
        String value = null;
        // 存储至缓存
        ICache<String, String> cache =
            MANAGER.getCache("carNo2TerminalSourceCache");
        for(TerminalEntity entity : list){
            key = entity.getCarrierName();
            value = entity.getTerminalSource();
            if(!StringUtils.isEmpty(key)){//终端来源可以为空，为空代表是嘉兴位置服务平台 
                cache.put(key, value);
            }
        }
    }
    
    private void generateHashMap1(List<TerminalEntity> list)
        throws Exception{
        String key = null;
        String value = null;
        for(TerminalEntity entity : list){
            key = entity.getCarrierName();
            value = entity.getTerminalSource();
            if(!StringUtils.isEmpty(key)){//终端来源可以为空，为空代表是嘉兴位置服务平台 
                carNo2TerminalSourceMap.put(key, value);
            }
        }
    }
    
    @Override
    public PageEntity<TerminalEntity> findByParams(Map<String, String> map)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, map);
        String url = this.getUrl();
        String result =
            JXWLHttpClientUtils.doPost(url + "QueryTerminalBasicInfo.action",
                findVal(map));
        
        // 数据解析
        PageEntity<TerminalEntity> page = new PageEntity<TerminalEntity>();
        try {
            JSONObject jsonObject = JSON.parseObject(result);
            page.setTotalCount(jsonObject.getIntValue("records"));
            Object data = jsonObject.get("data");
            LOGGER.info(1, data);
            if (null != data) {
                String str = JSONObject.toJSONString(data);
                LOGGER.info(1, str);
                page.setRows(JSONArray.parseArray(str, TerminalEntity.class));
            }
            
        }
        catch (Exception e) {
            e.printStackTrace();
            CustomException ce =
                new CustomException(BizExConstants.TERMINAL_SEARCH_EXP, map);
            throw ce;
        }
        LOGGER.debug(SystemContants.DEBUG_END, page);
        return page;
    }
    
    /**
     * 〈一句话功能简述〉得到所有终端电话号码 〈功能详细描述〉
     * 
     * @return String
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    public String getAllTerminalTel()
        throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("manufactureID", null);
        map.put("terminalSerialID", null);
        map.put("carrierName", null);
        map.put("page", null);
        map.put("rows", null);
        map.put("simNum", null);
        String url = this.getUrl();
        String result =
            JXWLHttpClientUtils.doPost(url + "QueryTerminalBasicInfo.action",
                findALLVal(map));
        LOGGER.info(1L, " getAllTerminalTel result" + result);
        JSONObject jsonObject = JSON.parseObject(result);
        Object data = jsonObject.get("data");
        String str = JSONObject.toJSONString(data);
        List<TerminalEntity> terminalListAll =
            JSONArray.parseArray(str, TerminalEntity.class);
        // 过滤第三方
        String terminalSource = null;
        String cardNum = null;
        StringBuffer sb = new StringBuffer();
        String tel = "";
        String terminalSourceJx = getTerminalSourceJx();
        String terminalSerialID = "";
        String num = "";
        for (TerminalEntity entity : terminalListAll) {
            terminalSerialID = entity.getTerminalSerialID();
            cardNum = entity.getCardNum();
            // 取手机卡号前3位+后4位
            if (StringUtils.isNotEmpty(cardNum)) {
                num =
                    cardNum.substring(1, 4)
                        + cardNum.substring(cardNum.length() - 4,
                            cardNum.length());
            }
            // 终端id等于手机卡号前3位加后4位，说明是手机终端，则过滤掉
            if (terminalSerialID.equals(num)) {
                continue;
            }
            terminalSource = entity.getTerminalSource();
            if (terminalSourceJx.equals(terminalSource)) {
                cardNum = entity.getCardNum();
                tel += cardNum + ";";
                sb.append(cardNum);
                sb.append(";");
            }
        }
        if (sb.length() > 0) {
            tel = sb.substring(0, sb.length() - 1).toString();
        }
        return tel;
    }
    
    @Override
    public TerminalEntity save(TerminalEntity terminal)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, terminal);
        if (terminal == null) {
            CustomException ce =
                new CustomException(BizExConstants.PARAM_E, terminal);
            ce.setErrorMessage("保存终端异常,参数terminal=null");
            throw ce;
        }
        String carrierName = terminal.getCarrierName();
        int num = carrierNameIsExist(carrierName);
        if (num < 1) {
            Map<String, String> expMsgs4Data = new HashMap<>();
            String result4Data = "\"002\"";
            expMsgs4Data.put("\"002\"", "车牌号不存在");
            handleException4Data(result4Data, expMsgs4Data);
        }
        String url = this.getUrl();
        String result =
            JXWLHttpClientUtils.doPost(url + "AddNewTerminal.action",
                saveVal(terminal));
        Map<String, String> expMsgs = new HashMap<>();
        expMsgs.put("\"002\"", "关键字段输入异常");
        expMsgs.put("\"003\"", "终端存在已绑定");
        expMsgs.put("\"004\"", "终端存在未绑定");
        expMsgs.put("\"005\"", "车辆已绑定，请重新录入");
        expMsgs.put("\"006\"", "手机号已绑定终端，请重新录入");
        // 异常处理
        handleException(result, expMsgs);
        LOGGER.debug(SystemContants.DEBUG_END, terminal);
        return terminal;
    }
    
    @Override
    public TerminalEntity bind(TerminalEntity terminal)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, terminal);
        if (terminal == null) {
            CustomException ce =
                new CustomException(BizExConstants.PARAM_E, terminal);
            ce.setErrorMessage("终端绑定异常,参数terminal=null");
            throw ce;
        }
        String carrierName = terminal.getCarrierName();
        // 检测车是否在业务系统存在
        int num = carrierNameIsExist(carrierName);
        if (num < 1) {
            Map<String, String> expMsgs4Data = new HashMap<>();
            String result4Data = "\"002\"";
            expMsgs4Data.put("\"002\"", "车牌号不存在");
            handleException4Data(result4Data, expMsgs4Data);
        }
        if ("app".equals(terminal.getTerminalSource())) {
            LOGGER.info(SystemContants.DEBUG_START, "2 terminal.bind.source:"
                + "app");
            String cardNum = terminal.getCardNum();
            String simNum = fillSimCard(cardNum);
            // String serialID = cardNumToTerminalSerialID(cardNum);
            TerminalEntity t = findByCarrierNameAndcardNum(carrierName, simNum);
            LOGGER.info(SystemContants.DEBUG_START, "3 terminal.bind.t:" + t);
            // 手机号和车辆刚好是绑定在一起的
            if (null != t && simNum.equals(t.getCardNum())) {
                return terminal;
            }
            TerminalEntity t1 = findByCarrierName(carrierName);
            TerminalEntity t2 = findBySimNum(simNum);
            String terminalSerialID = cardNumToTerminalSerialID(cardNum);
            LOGGER.info(SystemContants.DEBUG_START, "4 terminal.bind.t1:" + t1);
            LOGGER.info(SystemContants.DEBUG_START, "5 terminal.bind.t2:" + t2);
            if (null == t1 && null == t2) {
                // 该车辆和该手机号都没绑定 则开户
                TerminalEntity entity = new TerminalEntity();
                entity.setCardNum(simNum);
                entity.setCarrierName(carrierName);
                entity.setTerminalSerialID(terminalSerialID);
                entity.setManufactureID(" ");
                LOGGER.info(SystemContants.DEBUG_START, "6 terminal.bind.save:"
                    + entity);
                save(entity);
                return entity;
            }
            else if (null == t1 && null != t2) {
                // 车辆没绑定 手机绑定
                String t2carrierName = t2.getCarrierName();
                if (null != t2carrierName) {
                    LOGGER.info(SystemContants.DEBUG_START,
                        "6 terminal.bind.unbind:" + t2carrierName);
                    // 手机绑定 且有车注册中 则解绑手机 然后再去绑定
                    unbind(t2.getTerminalSerialID());
                }
                else {
                    // 手机绑定 且没有车注册 则绑定
                    LOGGER.info(SystemContants.DEBUG_START,
                        "7 terminal.bind.bind:" + t2carrierName);
                    // return terminal;
                }
                
            }
            else if (null != t1 && null == t2) {
                // 车辆绑定 手机没绑定
                String terminalSerialId = t1.getTerminalSerialID();
                String t1cardNum = t1.getCardNum();
                //t1cardNum为空导致isPhone(t1cardNum, terminalSerialId)方法直接报错
                if (isPhone(t1cardNum, terminalSerialId)) {
                    LOGGER.info(SystemContants.DEBUG_START,
                        "8 terminal.bind.isNumeric:" + terminalSerialId);
                    // 纯数字 手机号绑定 则解绑 然后录入
                    unbind(terminalSerialId);
                    TerminalEntity entity = new TerminalEntity();
                    entity.setCardNum(simNum);
                    entity.setCarrierName(carrierName);
                    entity.setTerminalSerialID(terminalSerialID);
                    entity.setManufactureID(" ");
                    save(entity);
                    return entity;
                }
                else {
                    LOGGER.info(SystemContants.DEBUG_START,
                        "9 terminal.bind.isNumeric:" + terminalSerialId);
                    // 不是纯数字 车辆绑定的是终端 然后返回
                    return t1;
                }
            }
            else if (null != t1 && null != t2) {
                // 车辆绑定 手机也绑定
                String terminalSerialId = t1.getTerminalSerialID();
                String t1cardNum = t1.getCardNum();
                //t1cardNum为空导致isPhone(t1cardNum, terminalSerialId)方法直接报错
                if (isPhone(t1cardNum, terminalSerialId)) {
                    LOGGER.info(SystemContants.DEBUG_START,
                        "10 terminal.bind.isNumeric:" + terminalSerialId);
                    // 纯数字 车辆解绑
                    unbind(terminalSerialId);
                    // 手机号有车辆 则解绑 然后再去绑定
                    if (null != t2.getCarrierName()) {
                        String terminalSerialId2 = t2.getTerminalSerialID();
                        unbind(terminalSerialId2);
                    }
                    else {
                        LOGGER.info(SystemContants.DEBUG_START,
                            "10A terminal.bind.isNumeric:" + terminalSerialId);
                    }
                }
                else {
                    LOGGER.info(SystemContants.DEBUG_START,
                        "11 terminal.bind.isNumeric:" + terminalSerialId);
                    // 不是纯数字 车辆绑定的是终端
                    // 如果手机又车牌 则解绑手机 然后返回
                    if (null != t2.getCarrierName()) {
                        String terminalSerialId2 = t2.getTerminalSerialID();
                        unbind(terminalSerialId2);
                    }
                    return t1;
                }
            }
            String cardNum1 = terminal.getCardNum();
            String terminalSerialID1 = cardNumToTerminalSerialID(cardNum1);
            terminal.setTerminalSerialID(terminalSerialID1);
        }
        // 手机终端已注册未绑定车辆，绑定车辆
        // String result = httpPost("BindOperation.action", bindVal(terminal));
        String url = this.getUrl();
        String result =
            JXWLHttpClientUtils.doPost(url + "BindOperation.action",
                bindVal(terminal));
        Map<String, String> expMsgs = new HashMap<>();
        expMsgs.put("\"002\"", "重复绑定");
        expMsgs.put("\"003\"", "关键字段未填");
        expMsgs.put("\"004\"", "该终端不存在，绑定失败");
        expMsgs.put("\"005\"", "终端载体已绑定");
        // 异常处理
        handleException(result, expMsgs);
        LOGGER.debug(SystemContants.DEBUG_END, terminal);
        return terminal;
    }
    
    /**
     * 〈一句话功能简述〉通过电话号码返回终端 〈功能详细描述〉
     * 
     * @param simNum
     *            电话号
     * @return TerminalEntity
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    public TerminalEntity findBySimNum(String simNum)
        throws Exception {
        
        LOGGER.debug(SystemContants.DEBUG_START, simNum);
        if (simNum == null) {
            CustomException ce =
                new CustomException(BizExConstants.PARAM_E, simNum);
            ce.setErrorMessage("查看终端异常,参数simNum=null");
            throw ce;
        }
        // 由于位置服务平台尚未提供通过ID查询的接口，所以此处使用分页查询，然后取第一个
        HashMap<String, String> map = new HashMap<>();
        // 厂商终端ID
        map.put("simNum", simNum);
        String url = this.getUrl();
        String result =
            JXWLHttpClientUtils.doPost(url + "QueryTerminalBasicInfo.action",
                findVal(map));
        // 数据解析
        TerminalEntity terminal = null;
        try {
            JSONObject jsonObject = JSON.parseObject(result);
            
            JSONArray data = jsonObject.getJSONArray("data");
            if (data != null && data.size() > 0) {
                terminal = data.getObject(0, TerminalEntity.class);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            CustomException ce =
                new CustomException(BizExConstants.TERMINAL_SEE_EXP, map);
            throw ce;
        }
        LOGGER.debug(SystemContants.DEBUG_END, terminal);
        return terminal;
    }
    
    /**
     * 〈一句话功能简述〉判断字符串是否是纯数字 〈功能详细描述〉
     * 
     * @param str
     *            str
     * @return String
     * @see [类、类#方法、类#成员]
     */
    // 判断是否是纯数字
    public boolean isNumeric(String str) {
        if (str == null) {
            return false;
        }
        for (int i = str.length() - 1; i >= 0; i--) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * 〈一句话功能简述〉判断是否是手机号的前三后四位组成〈功能详细描述〉
     * 
     * @param str
     *            str
     * @return String
     * @throws CustomException
     * @see [类、类#方法、类#成员]
     */
    // 判断是否是由手机代替的终端
    public boolean isPhone(String cardNum, String terminalSerialId)
        throws CustomException {
        if(StringUtils.isEmpty(cardNum)){
            return false;
        }
        String sub = cardNumToTerminalSerialID(cardNum);
        return terminalSerialId.equals(sub);
    }
    
    @Override
    public TerminalEntity update(TerminalEntity terminal)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, terminal);
        if (terminal == null) {
            CustomException ce =
                new CustomException(BizExConstants.PARAM_E, terminal);
            ce.setErrorMessage("修改终端异常,参数terminal=null");
            throw ce;
        }
        String url = this.getUrl();
        String result =
            JXWLHttpClientUtils.doPost(url + "UpdateTerminalInfo.action",
                updateVal(terminal));
        Map<String, String> expMsgs = new HashMap<>();
        expMsgs.put("\"002\"", "该终端处于解绑状态，如需更新载体信息，先绑定");
        expMsgs.put("\"003\"", "关键字段未填");
        expMsgs.put("\"004\"", "该更新的终端不存在");
        // 异常处理
        handleException(result, expMsgs);
        LOGGER.debug(SystemContants.DEBUG_END, terminal);
        return terminal;
    }
    
    @Override
    public TerminalEntity findById(String id)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, id);
        if (id == null) {
            CustomException ce =
                new CustomException(BizExConstants.PARAM_E, id);
            ce.setErrorMessage("查看终端异常,参数id=null");
            throw ce;
        }
        // 由于位置服务平台尚未提供通过ID查询的接口，所以此处使用分页查询，然后取第一个
        HashMap<String, String> map = new HashMap<>();
        // 厂商终端ID
        map.put("terminalSerialID", id);
        String url = this.getUrl();
        String result =
            JXWLHttpClientUtils.doPost(url + "QueryTerminalBasicInfo.action",
                findVal(map));
        // 数据解析
        TerminalEntity terminal = null;
        try {
            JSONObject jsonObject = JSON.parseObject(result);
            
            JSONArray data = jsonObject.getJSONArray("data");
            if (data != null && data.size() > 0) {
                terminal = data.getObject(0, TerminalEntity.class);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            CustomException ce =
                new CustomException(BizExConstants.TERMINAL_SEE_EXP, map);
            throw ce;
        }
        LOGGER.debug(SystemContants.DEBUG_END, terminal);
        return terminal;
    }
    
    @Override
    public void delete(String id)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, id);
        if (id == null) {
            CustomException ce =
                new CustomException(BizExConstants.PARAM_E, id);
            ce.setErrorMessage("删除终端异常,参数id=null");
            throw ce;
        }
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("terminalSerialID", id));
        // String result = httpPost("RemoveTerminalInfo.action", params);
        String url = this.getUrl();
        String result =
            JXWLHttpClientUtils.doPost(url + "RemoveTerminalInfo.action",
                params);
        Map<String, String> expMsgs = new HashMap<>();
        expMsgs.put("\"002\"", "要删除的终端不存在");
        expMsgs.put("\"003\"", "关键字段未填");
        // 异常处理
        handleException(result, expMsgs);
        LOGGER.debug(SystemContants.DEBUG_END, result);
    }
    
    @Override
    public void unbind(String id)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, id);
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("terminalSerialID", id));
        String url = this.getUrl();
        String result =
            JXWLHttpClientUtils.doPost(url + "UnbindOperation.action", params);
        // String result = httpPost("UnbindOperation.action", params);
        Map<String, String> expMsgs = new HashMap<>();
        expMsgs.put("\"002\"", "重复解绑，解绑失败");
        expMsgs.put("\"003\"", "关键字段未填");
        expMsgs.put("\"004\"", "该终端不存在，不能进行解绑");
        // 异常处理
        handleException(result, expMsgs);
        LOGGER.debug(SystemContants.DEBUG_END, result);
    }
    
    @Override
    public List<LocationEntity> getCarHistoryLocation(String carrierName,
        String startTime, String endTime)
        throws Exception {
        LOGGER.info(SystemContants.DEBUG_START, "service carrierName:"
            + carrierName + ",startTime:" + startTime + ",endTime:" + endTime);
        CustomException ce = null;
        if (C503StringUtils.isEmpty(carrierName)) {
            ce = new CustomException(BizExConstants.PARAM_E, carrierName);
            ce.setErrorMessage("查询车辆历史轨迹异常,参数错误，carrierName(车牌号)=null");
            throw ce;
        }
        // 日期格式检查
        if (C503StringUtils.isEmpty(startTime)) {
            ce = new CustomException(BizExConstants.PARAM_E, startTime);
            ce.setErrorMessage("查询车辆历史轨迹异常,参数错误，startTime=" + startTime);
            throw ce;
        }
        // 日期格式检查
        if (C503StringUtils.isEmpty(endTime)) {
            ce = new CustomException(BizExConstants.PARAM_E, endTime);
            ce.setErrorMessage("查询车辆历史轨迹异常,参数错误，endTime=" + endTime);
            throw ce;
        }
        
        String carrierNo = carrierName;
        
        Map<String, String> map = new HashMap<String, String>();
        map.put("carrierName", carrierNo);
        map.put("ScreateTime", startTime);
        map.put("EcreateTime", endTime);
        String str = JSONObject.toJSONString(map);
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("histrack", str));
        LOGGER.info(SystemContants.DEBUG_START, "histrack:"
            + params.get(0).getValue());
        String url = this.getUrl();
        String result =
            JXWLHttpClientUtils.doPost(url + "getHistrackData.action", params);
        Map<String, String> expMsgs = new HashMap<>();
        expMsgs.put("\"002\"", "关键字段输入异常");
        expMsgs.put("\"003\"", "关键字段未填");
        expMsgs.put("\"005\"", "其他未知错误");
        // 异常处理
        handleException4Data(result, expMsgs);
        // 数据解析
        List<LocationEntity> locs = null;
        try {
            locs = JSON.parseArray(result, LocationEntity.class);
        }
        catch (Exception e) {
            e.printStackTrace();
            ce =
                new CustomException(
                    BizExConstants.TERMINAL_HISTORY_TRACK_SEARCH_EXP, result);
            throw ce;
        }
        
        // 查询车辆在startTime和endTime时间段内的报警信息
        List<AlarmEntity> alarmList =
            alarmManageDao.findAlarmByCarrierNameAndStartEndTime(carrierName,
                startTime,
                endTime);
        int alarmSize = alarmList.size();
        List<AlarmEntity> alarms = null;
        AlarmEntity alarmEntity = null;
        Date alarmDate = null;
        Date gpstime = null;
        long alarmDateL;
        long gpstimeL;
        long tmpTime;
        long x;
        for (LocationEntity loc : locs) {
            alarms = new ArrayList<AlarmEntity>();
            for (int i = 0; i < alarmSize; i++) {
                alarmEntity = alarmList.get(i);
                alarmDate = alarmEntity.getAlarmDate();
                gpstime = loc.getGpstime();
                alarmDateL = alarmDate.getTime();
                gpstimeL = gpstime.getTime();
                tmpTime = alarmDateL - gpstimeL;
                x = Math.abs(tmpTime);
                if (x <= CommonConstant.FIFTEEN_THOUSAND) {
                    alarms.add(alarmEntity);
                    alarmList.remove(i);// 防止多个轨迹点重复匹配到同一个报警信息，即一个报警信息只能被一个轨迹点匹配到
                    --alarmSize;// alarmSize减1是可以影响到内层循环的alarmSize值的，即影响内层循环的循环次数
                    i--;
                }
            }
            loc.setAlarms(alarms);
        }
        LOGGER.debug(SystemContants.DEBUG_START, "getHistrackData  result:"
            + locs);
        LOGGER.debug(SystemContants.DEBUG_END, locs);
        return locs;
    }
    
    // 为所有终端统一设置终端参数（终端报警参数：最高速度，超速持续时间，连续驾驶时间上限，超时停车阈值）
    @Override
    public void setTerminalParam(Map<String, String> map)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, map);
        if (map == null) {
            CustomException ce =
                new CustomException(BizExConstants.PARAM_E, map);
            ce.setErrorMessage("设置终端参数异常,参数错误，map=null");
            throw ce;
        }
        String tel = getAllTerminalTel();
        // 只针对嘉兴位置服务平台的终端进行设置
        if (C503StringUtils.isNotEmpty(tel)) {
            // 协议转换
            Map<String, String> map808 = new HashMap<>();
            map808.put("00000000", tel);
            map808.put("00000001", "0x8103");
            map808.put("00000002", System.currentTimeMillis() + "");
            map808.put("81030000", "4");
            // 最高速度
            map808.put("81030049", map.get("overSpeedValue"));
            // 超速持续时间
            map808.put("81030050", map.get("speedContinueValue"));
            // 连续驾驶时间上限
            map808.put("81030051", map.get("fatigueDriveValue"));
            // 当天累计驾驶时间上限
            // map808.put("81030052", "");
            // 超时停车阈值(秒)
            map808.put("81030054", map.get("overtimeParkValue"));
            LOGGER.info(SystemContants.DEBUG_END, map808);
            this.sendCommand808(map808,
                "sendCommand808.action",
                "setTerminalWarnParam");
        }
        
    }
    
    // 为单个终端设置终端参数（主服务器地址，备份服务器地址，主服务器TCP端口，备份服务器TCP端口）
    @Override
    public void setTerminalParam(TerminalParamEntity entity)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, entity);
        if (entity == null) {
            CustomException ce =
                new CustomException(BizExConstants.PARAM_E, entity);
            ce.setErrorMessage("设置终端参数异常,参数错误，entity=null");
            throw ce;
        }
        else if (C503StringUtils.isEmpty(entity.getTerminalSerialId())) {
            CustomException ce =
                new CustomException(BizExConstants.PARAM_E, entity);
            ce.setErrorMessage("设置终端参数异常,参数错误，TerminalSerialId=null");
            throw ce;
        }
        // 协议转换
        Map<String, String> map808 = new HashMap<>();
        map808.put("00000000", entity.getPhone());
        map808.put("00000001", "0x8103");
        map808.put("00000002", System.currentTimeMillis() + "");
        map808.put("81030000", "4");
        // 主服务器地址
        map808.put("81030011", entity.getMainServerAddr());
        // 备份服务器地址
        map808.put("81030015", entity.getBackupServerAddr());
        // 主服务器TCP端口
        map808.put("81030016", entity.getMainServerTcpPort());
        // 备份服务器TCP端口
        map808.put("80130017", entity.getBackupServerTcpPort());
        LOGGER.info(SystemContants.DEBUG_END, map808);
        this.sendCommand808(map808, "sendCommand808.action", "setTerminalParam");
        TerminalParamEntity oldTerminalParam =
            findTerminalParamById(entity.getTerminalSerialId());
        if (oldTerminalParam != null) {
            this.terminalDao.updateTerminalParam(entity);
        }
        else {
            this.terminalDao.saveTerminalParam(entity);
        }
    }
    
    @Override
    public TerminalParamEntity findTerminalParamById(String id)
        throws Exception {
        List<TerminalParamEntity> list =
            this.terminalDao.findTerminalParamById(id);
        return list.isEmpty() ? null : list.get(0);
    }
    
    @Override
    public void upgrade(TerminalUpgrade terminal)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, terminal);
        if (terminal == null) {
            CustomException ce =
                new CustomException(BizExConstants.PARAM_E, terminal);
            ce.setErrorMessage("终端升级异常,参数错误，terminal=null");
            throw ce;
        }
        String tel = terminal.getTerminalTelNo();
        if (tel.startsWith(",")) {
            tel = tel.substring(1);
        }
        tel = fillSimCard(tel);
        terminal.setTerminalTelNo(tel);
        // 协议转换
        Map<String, String> map808 = new HashMap<>();
        this.setParams2MapForUpgrade(map808, terminal);
        LOGGER.info(SystemContants.DEBUG_END, "upgrade:" + map808);
        this.sendCommand808(map808, "sendCommand808.action", "upgrade");
        LOGGER.debug(SystemContants.DEBUG_END, map808);
    }
    
    /**
     * 〈一句话功能简述〉修改 〈功能详细描述〉
     * 
     * @param terminal
     *            TerminalUpgrade
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
    public void upgradeAll(TerminalUpgrade terminal)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, terminal);
        if (terminal == null) {
            CustomException ce =
                new CustomException(BizExConstants.PARAM_E, terminal);
            ce.setErrorMessage("终端升级异常,参数错误，terminal=null");
            throw ce;
        }
        String tel = getAllTerminalTel();
        terminal.setTerminalTelNo(tel);
        Map<String, String> map808 = new HashMap<String, String>();
        this.setParams2MapForUpgrade(map808, terminal);
        LOGGER.info(SystemContants.DEBUG_END, "upgradeALL:" + map808);
        this.sendCommand808(map808, "sendCommand808.action", "upgrate");
    }
    
   
    
    /**
     * 根据查询协议，手机号位12位，不足的前端补零
     * 
     * @param simCard
     *            手机号
     * @return 补零后的手机号
     */
    public String fillSimCard(String simCard) {
        if (simCard == null) {
            return simCard;
        }
        int len = simCard.length();
        if (len >= NumberContant.ONE_TWO || len <= 0) {
            return simCard;
        }
        while (len < NumberContant.ONE_TWO) {
            simCard = "0" + simCard;
            len = simCard.length();
        }
        return simCard;
    }
    
    @Override
    public int carrierNameIsExist(String carrierName)
        throws Exception {
        return this.carInfoDao.carrierNameIsExist(carrierName);
    }
    
    @Override
    public TerminalEntity findByCarrierName(String carrierName)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, carrierName);
        if (carrierName == null) {
            CustomException ce =
                new CustomException(BizExConstants.PARAM_E, carrierName);
            ce.setErrorMessage("查看终端异常,参数carrierName=null");
            throw ce;
        }
        // 由于位置服务平台尚未提供通过ID查询的接口，所以此处使用分页查询，然后取第一个
        Map<String, String> map = new HashMap<String, String>();
        // 厂商终端ID
        map.put("carrierName", carrierName);
        String url = this.getUrl();
        String result;
        try {
             result =
                 JXWLHttpClientUtils.doPost(url + "QueryTerminalBasicInfo.action",
                     findVal(map));  
        }
        catch (Exception e) {
            e.printStackTrace();
            CustomException ce =
                new CustomException(BizExConstants.POSITION_PLATEFORM_E, map);
            ce.setErrorMessage("位置服务平台异常，无法获取终端信息");
            throw ce;
        }
        // 数据解析
        TerminalEntity terminal = null;
        try {
            JSONObject jsonObject = JSON.parseObject(result);
            
            JSONArray data = jsonObject.getJSONArray("data");
            if (data != null && data.size() > 0) {
                terminal = data.getObject(0, TerminalEntity.class);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            CustomException ce =
                new CustomException(BizExConstants.POSITION_PLATEFORM_E, map);
            ce.setErrorMessage("查看终端异常,格式化位置服务平台响应数据出错");
            throw ce;
        }
        LOGGER.debug(SystemContants.DEBUG_END, terminal);
        return terminal;
    }
    
    @Override
    public TerminalEntity findByCarrierNameAndcardNum(String carrierName,
        String cardNum)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, carrierName);
        if (carrierName == null) {
            CustomException ce =
                new CustomException(BizExConstants.PARAM_E, carrierName);
            ce.setErrorMessage("查看终端异常,参数carrierName=null");
            throw ce;
        }
        // 由于位置服务平台尚未提供通过ID查询的接口，所以此处使用分页查询，然后取第一个
        HashMap<String, String> map = new HashMap<>();
        // 厂商终端ID
        map.put("carrierName", carrierName);
        map.put("card_num", cardNum);
        String url = this.getUrl();
        String result =
            JXWLHttpClientUtils.doPost(url + "QueryTerminalBasicInfo.action",
                findVal(map));
        // 数据解析
        TerminalEntity terminal = null;
        try {
            JSONObject jsonObject = JSON.parseObject(result);
            
            JSONArray data = jsonObject.getJSONArray("data");
            if (data != null && data.size() > 0) {
                terminal = data.getObject(0, TerminalEntity.class);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            CustomException ce =
                new CustomException(BizExConstants.TERMINAL_SEE_EXP, map);
            throw ce;
        }
        LOGGER.debug(SystemContants.DEBUG_END, terminal);
        return terminal;
    }
    
    /**
     * <b>终端与载体绑定</b>按照协议准备好数据
     * 
     * @param terminal
     *            终端对象
     * @return List<NameValuePair>
     * @see [类、类#方法、类#成员]
     */
    private List<NameValuePair> bindVal(TerminalEntity terminal) {
        LOGGER.debug(SystemContants.DEBUG_START, terminal);
        Map<String, String> map = new HashMap<String, String>();
        map.put("terminalSerialID", terminal.getTerminalSerialID());
        map.put("carcolor", terminal.getCarcolor());
        map.put("carrierName", terminal.getCarrierName());
        String str = JSONObject.toJSONString(map);
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        parameters.add(new BasicNameValuePair("bindingInfo", str));
        LOGGER.debug(SystemContants.DEBUG_END, parameters);
        return parameters;
    }
    
    /**
     * 
     * 〈一句话功能简述〉当手机号为终端时，终端编号为手机号前三位和后四位拼接〈功能详细描述〉
     * 
     * @param cardNum
     *            卡号
     * @return terminalSerialID
     * @throws CustomException
     *             异常
     * @see [类、类#方法、类#成员]
     */
    private String cardNumToTerminalSerialID(String cardNum)
        throws CustomException {
        String terminalSerialID = "";
        String sub = cardNum.substring(NumberContant.ZERO, NumberContant.ONE);
        if (C503StringUtils.equals("0", sub)) {
            cardNum = cardNum.substring(NumberContant.ONE);
        }
        if (cardNum.length() == CommonConstant.ONE_ONE) {
            terminalSerialID =
                cardNum.substring(NumberContant.ZERO, NumberContant.THREE)
                    + cardNum.substring(BizExConstants.SEVEN);
        }
        else {
            throw new CustomException(BizExConstants.CARDNUM_E, cardNum);
        }
        return terminalSerialID;
    }
    
    /**
     * 〈一句话功能简述〉批处理异常
     * 〈功能详细描述〉
     * 
     * @param paramStr 参数字符串
     * @param result 响应对象
     * @param sb 错误结果字符串
     * @see [类、类#方法、类#成员]
     */
    private void exceptionBatchHandle(String paramStr, JSONObject result,
        StringBuffer sb) {
        String phoneNum = result.getString("phoneNum");
        int status = result.getIntValue("status");
        if (status == 1) {
            sb.append(phoneNum);
            sb.append("绑定的终端当前不在线！</br>");
        }
        else if (status == 2) {
            sb.append(phoneNum);
            sb.append("参数不合法！</br>");
        }
        else if (status != 0) {
            sb.append(phoneNum);
            sb.append("发送808协议命令异常或失败，参数为");
            sb.append(paramStr);
            sb.append("</br>");
        }
    }
    
    /**
     * 〈一句话功能简述〉处理异常 〈功能详细描述〉
     * 
     * @param paramStr 参数字符串
     * @param String
     *            result
     * @param int status
     * @throws CustomException
     * @see [类、类#方法、类#成员]
     */
    private void exceptionHandle(String paramStr, String result, int status)
        throws CustomException {
        if (status == 1) {
            CustomException ce =
                new CustomException(BizExConstants.POSITION_PLATEFORM_E, result);
            ce.setErrorMessage("当前设备不在线！");
            throw ce;
        }
        else if (status == 2) {
            CustomException ce =
                new CustomException(BizExConstants.POSITION_PLATEFORM_E, result);
            ce.setErrorMessage("参数不合法！");
            throw ce;
        }
        else if (status != 0) {
            CustomException ce =
                new CustomException(BizExConstants.POSITION_PLATEFORM_E, result);
            ce.setErrorMessage("发送808协议命令异常,失败，param=" + paramStr);
            throw ce;
        }
    }
    
    /**
     * 〈一句话功能简述〉 <b>不分页终端基础数据查询</b>按照协议准备好数据 〈功能详细描述〉
     * 
     * @param map
     *            终端对象
     * @return List<NameValuePair>
     * @see [类、类#方法、类#成员]
     */
    private List<NameValuePair> findALLVal(Map<String, String> map) {
        LOGGER.debug(SystemContants.DEBUG_START, map);
        if (map == null) {
            map = new HashMap<String, String>();
        }
        if (map.get("manufactureID") == null) {
            // 制造商ID
            map.put("manufactureID", "");
        }
        if (map.get("terminalSerialID") == null) {
            // 厂商终端ID
            map.put("terminalSerialID", "");
        }
        if (map.get("carrierName") == null) {
            // 车牌号
            map.put("carrierName", "");
        }
        else {
            try {
                String carrierName = map.get("carrierName");
                map.put("carrierName", carrierName);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (map.get("simNum") == null) {
            // 手机号
            map.put("card_num", "");
        }
        else {
            String cardnum = map.get("simNum");
            map.remove("simNum");
            map.put("card_num", cardnum);
        }
        map.put("start", map.get("page"));
        if (map.get("start") == null) {
            map.put("start", "");
        }
        map.put("limit", map.get("rows"));
        if (map.get("limit") == null) {
            map.put("limit", "");
        }
        String str = JSONObject.toJSONString(map);
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("QueryInfo", str));
        LOGGER.info(SystemContants.DEBUG_END, params);
        return params;
    }
    
    /**
     * 〈一句话功能简述〉 <b>终端基础数据查询</b>按照协议准备好数据 〈功能详细描述〉
     * 
     * @param map
     *            终端对象
     * @return List<NameValuePair>
     * @see [类、类#方法、类#成员]
     */
    private List<NameValuePair> findVal(Map<String, String> map) {
        LOGGER.debug(SystemContants.DEBUG_START, map);
        if (map == null) {
            map = new HashMap<String, String>();
        }
        if (map.get("manufactureID") == null) {
            // 制造商ID
            map.put("manufactureID", "");
        }
        if (map.get("terminalSerialID") == null) {
            // 厂商终端ID
            map.put("terminalSerialID", "");
        }
        if (map.get("carrierName") == null) {
            // 车牌号
            map.put("carrierName", "");
        }
        else {
            try {
                String carrierName = map.get("carrierName");
                map.put("carrierName", carrierName);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (map.get("simNum") == null) {
            // 手机号
            map.put("card_num", "");
        }
        else {
            String cardnum = map.get("simNum");
            map.remove("simNum");
            map.put("card_num", cardnum);
        }
        map.put("start", map.get("page"));
        if (map.get("start") == null) {
            // 查询第几页
            map.put("start", "");
        }
        map.put("limit", map.get("rows"));
        if (map.get("limit") == null) {
            // 每页条数
            map.put("limit", "");
        }
        String str = JSONObject.toJSONString(map);
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("QueryInfo", str));
        LOGGER.info(SystemContants.DEBUG_END, params);
        return params;
    }
    
    /**
     * 〈一句话功能简述〉获得访问路径 〈功能详细描述〉
     * 
     * @return 访问路径
     * @throws Exception
     *             系统异常
     * @see [类、类#方法、类#成员]
     */
    private String getUrl()
        throws Exception {
        String url =
            C503PropertiesUtils.getValue("zcpt_config.properties",
                "postionServicePlatformUrl");
        return url;
    }
    
    /**
     * 异常处理，适合处理没有业务数据的情况
     * 
     * @param result
     *            要检验的结果
     * @param expMsgs
     *            接口定义的异常信息
     * @throws Exception
     *             Exception
     */
    private void handleException(String result, Map<String, String> expMsgs)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, result);
        if (!"\"001\"".equals(result)) {
            String expMsg = expMsgs.get(result);
            // 接口已定了错误
            if (expMsg != null) {
                CustomException exp =
                    new CustomException(CommonConstant.SYS_EXCEPTION, expMsg);
                exp.setErrorMessage(expMsg);
                throw exp;
                // 接口未定义的错误
            }
            else {
                CustomException ce =
                    new CustomException(
                        BizExConstants.TERMINAL_POSITION_PLAT_NO_RETURN_EXP,
                        result);
                throw ce;
            }
        }
        LOGGER.debug(SystemContants.DEBUG_END, result);
    }
    
    /**
     * 异常处理，适合处理有业务数据的情况
     * 
     * @param result
     *            要检验的结果
     * @param expMsgs
     *            接口定义的异常信息
     * @throws Exception
     *             Exception
     */
    private void handleException4Data(String result, Map<String, String> expMsgs)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, result);
        String expMsg = expMsgs.get(result);
        // 接口已定了错误
        if (expMsg != null) {
            CustomException exp =
                new CustomException(CommonConstant.SYS_EXCEPTION, expMsg);
            exp.setErrorMessage(expMsg);
            throw exp;
        }
        LOGGER.debug(SystemContants.DEBUG_END, result);
    }
    
    /**
     * <b>终端基础数据录入</b>按照协议准备好数据
     * 
     * @param terminal
     *            终端对象TerminalEntity
     * @return List<NameValuePair>
     * @see [类、类#方法、类#成员]
     */
    private List<NameValuePair> saveVal(TerminalEntity terminal) {
        LOGGER.debug(SystemContants.DEBUG_START, terminal);
        Map<String, String> map = new HashMap<String, String>();
        if (terminal != null) {
            // 厂商终端ID
            map.put("terminalSerialID", terminal.getTerminalSerialID());
            // 制造商ID
            map.put("manufactureID", terminal.getManufactureID());
            map.put("carcolor", terminal.getCarcolor());
            map.put("carrierName", terminal.getCarrierName());
            map.put("registerDate", terminal.getRegisterDate());
            map.put("nextrepairDate", terminal.getNextrepairDate());
            map.put("ProductionDate", terminal.getProductionDate());
            map.put("card_num", terminal.getCardNum());
            map.put("SimPass", terminal.getSimPass());
            map.put("operator", terminal.getOperator());
            map.put("expenses", terminal.getExpenses());
        }
        
        String str = JSONObject.toJSONString(map);
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        parameters.add(new BasicNameValuePair("terminalBasicInfo", str));
        LOGGER.info(SystemContants.DEBUG_END, parameters);
        return parameters;
    }
    
    /**
     * 〈一句话功能简述〉发送808协议命令
     * 〈功能详细描述〉
     * 
     * @param map 条件参数
     * @param action 远程连接方法
     * @param actionFlag 方法标识
     * @throws Exception 系统异常
     * @see [类、类#方法、类#成员]
     */
    public void sendCommand808(Map<String, String> map, String action,
        String actionFlag)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, "sendCommand808  map:" + map);
        String str = JSONObject.toJSONString(map);
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("commandInfo", str));
        String url = this.getUrl();
        String result = JXWLHttpClientUtils.doPost(url + action, nvps);
        LOGGER.info(SystemContants.DEBUG_START, "sendCommand808  param:" + str);
        LOGGER.info(SystemContants.DEBUG_START, "sendCommand808  result:"
            + result);
        
        int status = -1;
        JSONObject json = null;
        
        JSONArray jsonArray = JSON.parseArray(result);
        if (jsonArray != null) {
            int size = jsonArray.size();
            if (size <= 0) {
                CustomException ce =
                    new CustomException(BizExConstants.POSITION_PLATEFORM_E,
                        result);
                ce.setErrorMessage("发送808协议命令异常,失败，param=" + str);
                throw ce;
            }
            else if (size == 1) {
                if (null != jsonArray) {
                    JSONObject obj = jsonArray.getJSONObject(0);
                    if (null == obj) {
                        int exceptionCode = 3;
                        if (C503StringUtils.equals("upgrade", actionFlag)) {
                            exceptionCode =
                                BizExConstants.TERMINAL_UPGRADE_UNSUPPORT_EXP;
                        }
                        if (C503StringUtils.equals("setTerminalParam",
                            actionFlag)) {
                            exceptionCode =
                                BizExConstants.TERMINAL_SET_PARAMS_UNSUPPORT_EXP;
                        }
                        if (C503StringUtils.equals("setTerminalWarnParam",
                            actionFlag)) {
                            exceptionCode =
                                BizExConstants.WARN_SET_PARAMS_UNSUPPORT_EXP;
                        }
                        CustomException ce =
                            new CustomException(exceptionCode, result);
                        throw ce;
                    }
                    else {
                        status = obj.getIntValue("status");
                        this.exceptionHandle(str, result, status);
                    }
                }
            }
            else {
                StringBuffer sb = new StringBuffer();
                for (int i = 0; i < size; i++) {
                    json = jsonArray.getJSONObject(i);
                    System.err.println("sendCommand808---->status:" + i + ":"
                        + status);
                    this.exceptionBatchHandle(str, json, sb);
                }
                if (sb.length() > 0) {
                    CustomException ce =
                        new CustomException(
                            BizExConstants.POSITION_PLATEFORM_E, result);
                    ce.setErrorMessage(sb.toString());
                    throw ce;
                }
            }
        }
        
        LOGGER.debug(SystemContants.DEBUG_END,
            "sendCommand808 carDisPatch json:" + json);
        
    }
    
    /**
     * 〈一句话功能简述〉设置终端升级参数到map 〈功能详细描述〉
     * 
     * @param map808
     *            装入终端参数
     * @param ter
     *            TerminalUpgrade
     * @see [类、类#方法、类#成员]
     */
    private void setParams2MapForUpgrade(Map<String, String> map808,
        TerminalUpgrade ter) {
        map808.put("00000000", ter.getTerminalTelNo());
        map808.put("00000001", "0x8105");
        map808.put("00000002", System.currentTimeMillis() + "");
        map808.put("81050000", "6");
        // 命令字（1：无线升级）
        map808.put("81050001", "1");
        map808.put("81050003", ter.getDialPointName());
        map808.put("81050004", ter.getDialUserName());
        map808.put("81050005", ter.getDialPassword());
        map808.put("81050006", ter.getAddress());
        map808.put("81050007", ter.getTcpPort());
        map808.put("81050008", ter.getUdpPort());
        map808.put("81050009", ter.getManufactureID());
        map808.put("81050011", ter.getHardwareVersion());
        map808.put("81050012", ter.getFirmwareVersion());
        map808.put("81050013", ter.getUrl());
        map808.put("81050014", ter.getTimeLimit());
    }
    
    /**
     * <b>终端基础数据修改</b>按照协议准备好数据
     * 
     * @param terminal
     *            终端对象TerminalEntity
     * @return List<NameValuePair>
     * @see [类、类#方法、类#成员]
     */
    private List<NameValuePair> updateVal(TerminalEntity terminal) {
        LOGGER.debug(SystemContants.DEBUG_START, terminal);
        Map<String, String> map = new HashMap<String, String>();
        if (terminal != null) {
            // 厂商终端ID
            map.put("terminalSerialID", terminal.getTerminalSerialID());
            // 制造商ID
            map.put("manufactureID", terminal.getManufactureID());
            map.put("carcolor", terminal.getCarcolor());
            map.put("carrierName", terminal.getCarrierName());
            map.put("registerDate", terminal.getRegisterDate());
            map.put("nextrepairDate", terminal.getNextrepairDate());
            map.put("ProductionDate", terminal.getProductionDate());
            map.put("card_num", terminal.getCardNum());
            map.put("SimPass", terminal.getSimPass());
            map.put("operator", terminal.getOperator());
            map.put("expenses", terminal.getExpenses());
        }
        String str = JSONObject.toJSONString(map);
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        parameters.add(new BasicNameValuePair("terminalBasicInfo", str));
        LOGGER.debug(SystemContants.DEBUG_END, parameters);
        return parameters;
    }
    
    /**
     * 〈一句话功能简述〉获取终端来源-对应汉字
     * 〈功能详细描述〉
     * 
     * @return 终端来源-嘉兴位置服务平台
     * @see [类、类#方法、类#成员]
     */
    private String getTerminalSourceJx() {
        String platName =
            ResourceManager.getMessage(SysCommonConstant.TERMINAL_SOURCE_JX);
        return platName;
    }
    
    @Override
    public WayBillEntity findCarrierNameByAccount(String account)
        throws Exception {
        List<WayBillEntity> list =
            this.terminalDao.findCarrierNameByAccount(account);
        return list.size() == 0 ? null : list.get(0);
    }


    public Map<String, Object> getCarNo2TerminalSourceMap()
    {
        return carNo2TerminalSourceMap;
    }

    @Override
    public void setTerminalBaseInfoList(
        List<TerminalEntity> terminalBaseInfoList)
        throws Exception
    {
        // TODO Auto-generated method stub
        
    }

    
}
