/*
 * 文件名：ShortcutController.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-8-3
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.zcpt.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.c503.sc.base.common.NumberContant;
import com.c503.sc.base.exception.CustomException;
import com.c503.sc.base.service.IBaseService;
import com.c503.sc.jxwl.common.bean.LocationEntity;
import com.c503.sc.jxwl.common.constant.CommonConstant;
import com.c503.sc.jxwl.common.controller.ResultController;
import com.c503.sc.jxwl.common.service.IMiPushService;
import com.c503.sc.jxwl.zcpt.bean.TerminalEntity;
import com.c503.sc.jxwl.zcpt.bean.WayBillEntity;
import com.c503.sc.jxwl.zcpt.formbean.CarDispatchForm;
import com.c503.sc.jxwl.zcpt.service.ICarDispatchService;
import com.c503.sc.jxwl.zcpt.service.ITerminalService;
import com.c503.sc.log.LoggingManager;
import com.c503.sc.utils.basetools.C503DateUtils;
import com.c503.sc.utils.basetools.C503StringUtils;
import com.c503.sc.utils.common.SystemContants;
import com.xiaomi.xmpush.server.Message;
import com.xiaomi.xmpush.server.Result;

/**
 * 实时位置数据
 * 
 * @author qianxq
 * @version [版本号, 2016-8-3]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Controller
@Scope(value = "prototype")
@RequestMapping(value = "/shortcutOperation")
public class ShortcutController extends ResultController {
    
    /** 日志器 */
    private static final LoggingManager LOGGER =
        LoggingManager.getLogger(ShortcutController.class);
    
    /** 获取终端信息信息 */
    @Resource(name = "terminalService")
    private ITerminalService terminalService;
    
    /***/
    @Resource(name = "carDispatchService")
    private ICarDispatchService carDispatchService;
    
    /** miPushService的实例 */
    @Resource(name = "miPushService")
    private IMiPushService miPushService;
    
    /**
     * 〈一句话功能简述〉车辆调度 〈功能详细描述〉
     * 
     * @param form 车辆调度表单对象
     * @return object
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/carDispatch")
    @ResponseBody
    public Object carDispatch(CarDispatchForm form)
        throws Exception {
        LOGGER.info(SystemContants.DEBUG_START, form);
        //TODO:特别注意；app回复调度信息时传上来的是userName,平台下发给app调度信息时必须传输username而非userName,否则导致app解析失败
          /*form.setAccount("gmm1");
          form.setSign("3");
          form.setContent("回复调度辛志薇");
          form.setCarrierName("浙KG6193");
          form.setUserName("张丹丹");*/
        
        //测试下发给手机或终端
        /*form.setSign("1");
        form.setContent("con");
        //form.setCarrierName("浙FJ3626");
        form.setCarrierName("浙FB8789");*/
        
        // 手机回复调度信息
        String telePhone = "";
        WayBillEntity wayBill = null;
        String sign = form.getSign();
        String carrierName = form.getCarrierName();
        carrierName = carrierName == null ? "" : carrierName;
        String userName = form.getUserName();
        String content = form.getContent();
        String account = form.getAccount();
        // app回复调度信息
        if ("3".equals(sign)) {
            //注意terminalService.findCarrierNameByAccount返回多个运单，且这多个运单的CARNO即车牌号都不相同，但driverphone是相同的
            wayBill = this.terminalService.findCarrierNameByAccount(account);
            if (null != wayBill) {
                //手机端没有上传carrierName字段，后台必须填写carrierName，否则导致调度框展示不出回复的调度消息
                carrierName = wayBill.getCarno();
                form.setCarrierName(carrierName);
                telePhone = wayBill.getDriverphone();
                form.setTelePhone(telePhone);
                saveAndPush(form);
            }
            else {
                this.sendCode(CommonConstant.CARDISPATCH_ALARM);
            }
        }
        // 下发调度信息
        else if ("1".equals(sign)) {
            userName = this.getUser().getName();
            form.setTelePhone(telePhone);
            form.setUserName(userName);
            saveAndPush(form);
        }
        else {
            this.sendCode(CommonConstant.ARGS_INVALID);
        }
        LOGGER.debug(SystemContants.DEBUG_END);
        return sendMessage();
    }
    
    /**
     * 〈一句话功能简述〉获取车辆调度信息 〈功能详细描述〉
     * 
     * @param carrierName
     *            车牌号
     * @return Object
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/carDispVal")
    @ResponseBody
    public Object getCarDispVal(String carrierName)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        List<Map<String, Object>> result =
            this.carDispatchService.findCarDispVal(carrierName, this.getUser()
                .getName());
        this.sendData(result, CommonConstant.FIND_SUC_OPTION);
        LOGGER.debug(SystemContants.DEBUG_END);
        return sendMessage();
    }
    
    /**
     * 〈一句话功能简述〉查询历史轨迹 〈功能详细描述〉
     * 
     * @param startTime
     *            startTime
     * @param endTime
     *            endTime
     * @param carrierName
     *            carrierName
     * @return Object
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/historyTrack", method = {RequestMethod.GET,
        RequestMethod.POST})
    @ResponseBody
    public Object getHistoryTrack(String startTime, String endTime,
        String carrierName)
        throws Exception {
        LOGGER.info(SystemContants.DEBUG_START,
            "ShortcutController  startTime:" + startTime + " endTime:"
                + endTime + " carrierName:" + carrierName);
        // carrierName = "渝F00002";
        // startTime = "2016-10-19 12:04:50";
        // endTime = "2017-8-13 12:07:03";
        List<LocationEntity> hisTracks = null;
        if (StringUtils.isNotEmpty(startTime)
            && StringUtils.isNotEmpty(endTime)
            && StringUtils.isNotEmpty(carrierName)) {
            hisTracks =
                this.terminalService.getCarHistoryLocation(carrierName,
                    startTime,
                    endTime);
            this.sendData(hisTracks, CommonConstant.FIND_SUC_OPTION);
        }
        // 传入参数无效
        else {
            this.sendCode(CommonConstant.ARGS_INVALID, carrierName);
        }
        LOGGER.debug(SystemContants.DEBUG_END);
        return sendMessage();
    }
    
    @SuppressWarnings("unchecked")
    @Override
    protected <T> IBaseService<T> getBaseService() {
        return (IBaseService<T>) this.terminalService;
    }
    
    @Override
    protected Object show() {
        return null;
    }
    
    @Override
    protected LoggingManager logger() {
        return LOGGER;
    }
    
    private void saveAndPush(CarDispatchForm form)
        throws Exception {
        String nowDateStr =
            C503DateUtils.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss");
        String nowTimeStr = String.valueOf(System.currentTimeMillis());
        String sign = form.getSign();
        Map<String, Object> map = form.handleParas();
        map.put("createTime", nowDateStr);
        map.put("msgId", nowTimeStr);
        map.put("msgFlowId", nowTimeStr);
        map.put("id", C503StringUtils.createUUID());
        map.put("terminalTel", form.getTelePhone());
        String mark = form.getMark() == null ? "2" : form.getMark();
        map.put("mark", mark);
        map.put("username", form.getUserName());
        String carrierName = form.getCarrierName();
        final String carrierNameCopy = carrierName;
        String terminalSerialID = "";
        String cardNum = "";
        String num = "";
        if ("1".equals(sign)) {//下发给手机app或终端
            TerminalEntity terminalEntity =
                terminalService.findByCarrierName(carrierName);
            if(null != terminalEntity){
                terminalSerialID = terminalEntity.getTerminalSerialID();
                cardNum = terminalEntity.getCardNum();
                // 取手机卡号前3位+后4位
                if (StringUtils.isNotEmpty(cardNum)) {
                    num =
                        cardNum.substring(1, 4)
                            + cardNum.substring(cardNum.length() - 4,
                                cardNum.length());
                }
                // 终端id等于手机卡号前3位加后4位，则下发给手机app，否则，下发给终端
                if (terminalSerialID.equals(num)) {
                    // 下发给手机
//                  Map<String, Object> map2 = form.handleParas();
//                  //因为form中有多余的字段，推送给app，app无法解析导致推送失败，故推送前必须删除掉这些字段
//                  map2.remove("serialVersionUID");
//                  map2.remove("telePhone");
//                  map2.remove("userName");//app端接受和解析调度消息时用的是username而非userName！
//                  map2.put("username", form.getUserName());
//                  map2.put("createTime", nowDateStr);
                    Map<String, Object> map2 = new HashMap<String, Object>();
                    map2.put("sign", form.getSign());
                    map2.put("carrierName", form.getCarrierName());
                    map2.put("content", form.getContent());
                    map2.put("username", form.getUserName());
                    map2.put("createTime", nowDateStr);
                    final String jsonStr = JSONObject.toJSONString(map2);
                    try {
                        miPushRuleRegulation(carrierNameCopy, jsonStr);
                    }
                    catch(CustomException e){
                        this.sendCode(CommonConstant.SAVE_FAIL_OPTION,"系统异常");
                        //findRegidByCarrierName根据车牌号查询regid为空
                        e.setErrorMessage("该驾驶员已离线");
                        throw e;
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else {
                    // 下发给终端
                    Map<String, String> map808 = new HashMap<String, String>();
                    map808.put("00000000", cardNum);// 终端对应的手机号(如果有多个手机号，则手机号之间用;分隔)
                    map808.put("00000001", "0x8300");
                    map808.put("00000002", System.currentTimeMillis() + "");
                    int dispatchFlag = 0;
                    String carDispatchFlag = form.getCarDispatchFlag();
                    //carDispatchFlag值有可能为空
                    if(!StringUtils.isEmpty(carDispatchFlag)){
                        String[] flags = carDispatchFlag.split(",");
                        if (flags.length > 0) {
                            for (int i = 0; i < flags.length; i++) {
                                dispatchFlag += Integer.valueOf(flags[i]);
                            }
                        }
                    }
                    else {
                        dispatchFlag = 4;// 终端默认为显示器显示
                    }
                    map808.put("83000000", String.valueOf(dispatchFlag));
                    String content = form.getContent();
                    map808.put("83000001", content);
                    this.terminalService.sendCommand808(map808,
                        "sendCommand808.action",
                        null);
                }
            }
        }
        this.carDispatchService.sendTextMsg(map);
        this.sendCode(CommonConstant.SAVE_SUC_OPTION);
    }
    
    /**
     * 〈一句话功能简述〉小米推送 〈功能详细描述〉
     * 
     * @param id
     *            id
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    private void miPushRuleRegulation(String carrierName, String msg)
        throws Exception {
        // 根据通过车牌号查询运单再通过运单id查询驾驶员 然后账号注册表查询regid
        String regId =
            this.carDispatchService.findRegidByCarrierName(carrierName);
        String messagePayload = this.miPushService.getPayload(msg, "5");
        String title = "调度信息";
        JSONObject json = JSONObject.parseObject(msg);
        String messageStr = ((String) json.get("content")).trim();
        String des = messageStr.length() > 7 ? messageStr + "..." : messageStr;
        // 得到message
        Message message = null;
        Result result = null;
        message =
            this.miPushService.messageHandle(title, des, messagePayload, false);
        LOGGER.info(SystemContants.DEBUG_END, "message: " + message);
        // 发送message 并得到result
        result =
            this.miPushService.sendByRegId(message, regId, NumberContant.FIVE);
        LOGGER.info(SystemContants.DEBUG_END, "result: " + result);
    }
}
