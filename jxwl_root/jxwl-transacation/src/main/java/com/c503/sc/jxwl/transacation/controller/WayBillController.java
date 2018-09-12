/**
 * 、 * 文件名：EnterpriseController.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-7-20
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.transacation.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.c503.sc.base.common.NumberContant;
import com.c503.sc.base.entity.Page;
import com.c503.sc.base.exception.CustomException;
import com.c503.sc.base.service.IBaseService;
import com.c503.sc.base.validation.Save;
import com.c503.sc.base.validation.Update;
import com.c503.sc.jxwl.common.bean.BizUserEntity;
import com.c503.sc.jxwl.common.constant.CommonConstant;
import com.c503.sc.jxwl.common.constant.DictConstant;
import com.c503.sc.jxwl.common.controller.IFileUploadValidate;
import com.c503.sc.jxwl.common.controller.ResultController;
import com.c503.sc.jxwl.common.service.IMiPushService;
import com.c503.sc.jxwl.common.thrpool.ThreadPool;
import com.c503.sc.jxwl.transacation.bean.WayBillEntity;
import com.c503.sc.jxwl.transacation.bean.WayBillGoodsEntity;
import com.c503.sc.jxwl.transacation.constant.BizExConstant;
import com.c503.sc.jxwl.transacation.formbean.WayBillForm;
import com.c503.sc.jxwl.transacation.formbean.WayBillGoodsForm;
import com.c503.sc.jxwl.transacation.service.ISolveRouteService;
import com.c503.sc.jxwl.transacation.service.IWayBillGoodsService;
import com.c503.sc.jxwl.transacation.service.IWayBillService;
import com.c503.sc.jxwl.vehiclemonitor.bean.CarLocationEntity;
import com.c503.sc.jxwl.vehiclemonitor.bean.EnterpriseEntity;
import com.c503.sc.jxwl.vehiclemonitor.bean.OccupationPersonEntity;
import com.c503.sc.jxwl.zcpt.bean.DangerVehicleEntity;
import com.c503.sc.jxwl.zcpt.bean.SolveRoute;
import com.c503.sc.jxwl.zcpt.bean.TerminalEntity;
import com.c503.sc.jxwl.zcpt.service.IDangerVehicleService;
import com.c503.sc.jxwl.zcpt.service.ITerminalService;
import com.c503.sc.log.ControlLogModel;
import com.c503.sc.log.LoggingManager;
import com.c503.sc.utils.basetools.C503StringUtils;
import com.c503.sc.utils.common.SystemContants;
import com.c503.sc.utils.response.ResultJQGrid;
import com.xiaomi.xmpush.server.Message;
import com.xiaomi.xmpush.server.Result;

/**
 * 
 * 〈一句话功能简述〉运单信息Controller 〈功能详细描述〉
 * 
 * @author wl
 * @version [版本号, 2016-08-30]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Controller
@Scope(value = "prototype")
@RequestMapping(value = "/wayBill")
public class WayBillController extends ResultController implements
    IFileUploadValidate {
    /** 日志器 */
    private static final LoggingManager LOGGER =
        LoggingManager.getLogger(WayBillController.class);
    
    /** 运单信息业务接口 */
    @Resource
    private IWayBillService wayBillService;
    
    /** 运单信息---货物业务接口 */
    @Resource
    private IWayBillGoodsService wayBillGoodsService;
    
    /** 路径规划service */
    @Resource(name = "solveRouteService")
    private ISolveRouteService solveRouteService;
    
    /** 路径规划操作 */
    @Resource
    private RouteOperate routeOperate;
    
    /** 终端接口 */
    @Resource(name = "terminalService")
    private ITerminalService terminalService;
    
    /** miPushService的实例 */
    @Resource(name = "miPushService")
    private IMiPushService miPushService;
    
    /** 危险品车辆信息业务接口 */
    @Resource
    private IDangerVehicleService dangerVehicleService;
    
    /**
     * 〈一句话功能简述〉分页查询运单信息 〈功能详细描述〉
     * 
     * @param checkstatus
     *            checkstatus
     * @param carno
     *            carno
     * @param startTime
     *            startTime
     * @param endTime
     *            endTime
     * @param page
     *            page
     * @param rows
     *            rows
     * @return Object
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/findByPage")
    @ResponseBody
    public Object findByPage(String checkstatus, String carno,
        @DateTimeFormat(pattern = "yyyy-MM-dd", iso = ISO.DATE)
        Date startTime, @DateTimeFormat(pattern = "yyyy-MM-dd", iso = ISO.DATE)
        Date endTime, Integer page, Integer rows)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        // 1、参数设置
        Map<String, Object> map = new HashMap<String, Object>();
        checkstatus = checkstatus == null ? null : checkstatus.trim();
        map.put("checkstatus", checkstatus);
        carno = carno == null ? null : carno.trim();
        map.put("carno", carno);
        map.put("startTime", startTime);
        // endTime = endTime == null ? null : C503DateUtils.getDay(1, endTime);
        map.put("endTime", endTime);
        Page pageEntity = new Page();
        pageEntity.setCurrentPage(page);
        pageEntity.setPageSize(rows);
        map.put("page", pageEntity);
        BizUserEntity user = this.getUser();
        List<String> roleCodes = user.getRoleCodes();
        String corporateCode = user.getCorporateCode();
        if (roleCodes.contains(DictConstant.CHEMICAL_ENTERPRISE_USER)) {
            // 化工
            map.put("chemicalId", corporateCode);
        }
        else if (roleCodes.contains(DictConstant.LOGISTICS_ENTERPRISE_USER)) {
            // 物流
            map.put("logisticsId", corporateCode);
        }
        else if (roleCodes.contains(DictConstant.DRIVER_USER)) {
            // 驾驶员
            map.put("driverid", user.getIdCard());
        }
        
        // 2、接口调用
        List<WayBillEntity> list = this.wayBillService.findByParams(map);
        // 3、数据返回
        setJQGrid(list,
            pageEntity.getTotalCount(),
            page,
            rows,
            CommonConstant.FIND_SUC_OPTION);
        LOGGER.debug(SystemContants.DEBUG_END);
        return (ResultJQGrid) this.sendMessage();
    }
    
    /**
     * 〈一句话功能简述〉bu分页查询运单信息 〈功能详细描述〉
     * 
     * @param checkstatus
     *            checkstatus
     * @param carno
     *            carno
     * @param account
     *            account
     * @param startTime
     *            startTime
     * @param endTime
     *            endTime
     * @param page
     *            page
     * @param rows
     *            rows
     * @return Object
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/findByPageForApp")
    @ResponseBody
    public Object findByPageForApp(String checkstatus, String carno,
        String account, @DateTimeFormat(pattern = "yyyy-MM-dd", iso = ISO.DATE)
        Date startTime, @DateTimeFormat(pattern = "yyyy-MM-dd", iso = ISO.DATE)
        Date endTime, Integer page, Integer rows)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        // 1、参数设置
        Map<String, Object> map = new HashMap<String, Object>();
        checkstatus = checkstatus == null ? null : checkstatus.trim();
        map.put("checkstatus", checkstatus);
        carno = carno == null ? null : carno.trim();
        map.put("carno", carno);
        if (account == null) {
            throw new CustomException(
                com.c503.sc.jxwl.common.constant.NumberContant.THREE_LONG,
                "account shoud not be null");
        }
        account = account == null ? null : account.trim();
        map.put("account", account);
        map.put("startTime", startTime);
        // endTime = endTime == null ? null : C503DateUtils.getDay(1, endTime);
        map.put("endTime", endTime);
        Page pageEntity = new Page();
        pageEntity.setCurrentPage(page);
        pageEntity.setPageSize(rows);
        map.put("page", pageEntity);
        // 2、接口调用
        List<WayBillEntity> list = this.wayBillService.findForAppByParams(map);
        // 3、数据返回
        setJQGrid(list,
            pageEntity.getTotalCount(),
            page,
            rows,
            CommonConstant.FIND_SUC_OPTION);
        LOGGER.debug(SystemContants.DEBUG_END);
        return (ResultJQGrid) this.sendMessage();
    }
    
    /**
     * 
     * 〈一句话功能简述〉 〈功能详细描述〉获取车辆对应的运单的状态（返回：0为待运输和已完成 2为取货中 3为送货中）
     * 
     * @param carrierName
     *            carrierName
     * @return object
     * @throws Exception
     *             系统异常
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/findCheckStatus")
    @ResponseBody
    public Object findCheckStatus(String carrierName)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, carrierName);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("carno", carrierName);
        List<WayBillEntity> list = this.wayBillService.findByParams(map);
        LOGGER.info(CommonConstant.SAVE_SUC_OPTION, list);
        // statusFlag 标识运单的状态 0为待运输和已完成 2为取货中 3为送货中
        int statusFlag = 0;
        for (WayBillEntity wayBillEntity : list) {
            String status = wayBillEntity.getCheckstatus();
            if (status.equals(DictConstant.WAYBILL_SEND)) {
                statusFlag = NumberContant.THREE;
                break;
            }
            else if (status.equals(DictConstant.WAYBILL_GET)) {
                statusFlag = NumberContant.TWO;
            }
        }
        sendData(statusFlag, CommonConstant.SAVE_SUC_OPTION);
        LOGGER.debug(SystemContants.DEBUG_END, list);
        return this.sendMessage();
    }
    
    /**
     * 
     * 〈一句话功能简述〉 〈功能详细描述〉保存运单信息
     * 
     * @param form
     *            实体
     * @param bindingResult
     *            表单验证错误集合
     * @return object
     * @throws Exception
     *             系统异常
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public Object save(@Validated(value = Save.class)
    WayBillForm form, BindingResult bindingResult)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, form);
        boolean isResult = this.addErorrs(bindingResult);
        if (isResult) {
            sendData(getValidErorrs(), CommonConstant.FORMVALID_FAIL_OPTION);
            // 记录操作失败信息（存入文件）
            LOGGER.info(CommonConstant.FORMVALID_FAIL_OPTION, form);
            // 保存操作日志 记录操作失败（存入数据库）
            controlLog(ControlLogModel.CONTROL_RESULT_FAIL,
                CommonConstant.FORMVALID_FAIL_OPTION,
                getValidErorrs()).setErrorMessage(CommonConstant.FORMVALID_FAIL_OPTION)
                .recordLog();
        }
        else {
            // 2、实体赋值
            WayBillEntity entity = new WayBillEntity();
            this.copyProperties(form, entity);
            
            String returnId = C503StringUtils.createUUID();
            entity.setId(returnId);
            entity.setCheckstatus(DictConstant.WAYBILL_WAIT);
            entity.setCreateBy(this.getUser().getId());
            entity.setCreateTime(new Date());
            entity.setUpdateBy(this.getUser().getId());
            entity.setUpdateTime(new Date());
            entity.setRemove(SystemContants.ON);
            entity.setChemicalId(this.getUser().getCorporateCode());
            // 3、调用接口信息
            this.wayBillService.save(entity);
            // 4、打印日志
            LOGGER.info(CommonConstant.SAVE_SUC_OPTION, entity);
            sendData(returnId, CommonConstant.SAVE_SUC_OPTION);
        }
        LOGGER.debug(SystemContants.DEBUG_END, form);
        return this.sendMessage();
    }
    
    /**
     * 
     * 〈一句话功能简述〉 〈功能详细描述〉保存模板信息
     * 
     * @param form
     *            实体
     * @param bindingResult
     *            表单验证错误集合
     * @return object
     * @throws Exception
     *             系统异常
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/saveTemp", method = RequestMethod.POST)
    @ResponseBody
    public Object saveTemp(@Validated(value = Save.class)
    WayBillEntity form, BindingResult bindingResult)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, form);
        boolean isResult = this.addErorrs(bindingResult);
        if (isResult) {
            this.loggerFormValidate(LOGGER, form);
        }
        else {
            // 2、实体赋值
            WayBillEntity entity = new WayBillEntity();
            this.copyProperties(form, entity);
            // 3、调用接口信息
            this.wayBillService.saveTemp(entity);
            // 4、打印日志
            LOGGER.info(CommonConstant.SAVE_SUC_OPTION, entity);
            sendCode(CommonConstant.SAVE_SUC_OPTION);
        }
        LOGGER.debug(SystemContants.DEBUG_END, form);
        return this.sendMessage();
    }
    
    /**
     * 〈一句话功能简述〉删除运单 〈功能详细描述〉
     * 
     * @param id
     *            id
     * @return 响应到前台的数据
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public Object delete(@PathVariable
    String id)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, id);
        if (StringUtils.isNotEmpty(id)) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", id);
            map.put("updateBy", this.getUser().getId());
            map.put("updateTime", new Date());
            
            int line = this.wayBillService.delete(map);
            
            LOGGER.info(CommonConstant.DELETE_SUC_OPTION, line);
            // 保存操作日志 记录操作成功
            controlLog(ControlLogModel.CONTROL_RESULT_SUCCESS,
                CommonConstant.DELETE_SUC_OPTION,
                map).recordLog();
            sendCode(CommonConstant.DELETE_SUC_OPTION);
        }
        // 传入参数无效
        else {
            this.sendCode(CommonConstant.ARGS_INVALID);
        }
        LOGGER.debug(SystemContants.DEBUG_END);
        
        return sendMessage();
        
    }
    
    /**
     * 〈一句话功能简述〉修改运单信息 〈功能详细描述〉
     * 
     * @param id
     *            id
     * @param form
     *            能管员信息
     * @param bindingResult
     *            验错误信息结果集
     * @return 返回修改结果信息
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Object update(@PathVariable
    String id, @Validated(value = Update.class)
    WayBillForm form, BindingResult bindingResult)
        throws Exception {
        
        LOGGER.debug(SystemContants.DEBUG_START, form);
        boolean isResult = this.addErorrs(bindingResult);
        if (isResult) {
            sendData(getValidErorrs(), CommonConstant.FORMVALID_FAIL_OPTION);
            LOGGER.info(CommonConstant.FORMVALID_FAIL_OPTION, form);
            controlLog(ControlLogModel.CONTROL_RESULT_FAIL,
                CommonConstant.FORMVALID_FAIL_OPTION,
                getValidErorrs()).setErrorMessage(CommonConstant.FORMVALID_FAIL_OPTION)
                .recordLog();
        }
        else {
            if (C503StringUtils.isNotEmpty(id)) {
                
                /*
                 * boolean isDeal = this.wayBillService.isDeal(form.getOrderNo()
                 * .trim());
                 */
                /*
                 * if (!isDeal) { sendData(isDeal,
                 * CommonConstant.FIND_SUC_OPTION); return this.sendMessage(); }
                 */
                
                // 编辑前判断该条数据是否存在
                if (null == this.wayBillService.findById(id)) {
                    throw new CustomException(BizExConstant.WAYBILL_NOEXIST);
                }
                
                WayBillEntity ruleRegulation = new WayBillEntity();
                
                this.copyProperties(form, ruleRegulation);
                // //获取车牌号码
                // String carrierName = ruleRegulation.getCarno();
                // //根据车牌号 获取所在物流企业的法人代码
                // String data = this.wayBillService.findCorporate(carrierName);
                // ruleRegulation.setLogisticsId(data);
                
                ruleRegulation.setId(id);
                if (C503StringUtils.isEmpty(form.getCheckstatus())) {
                    ruleRegulation.setCheckstatus(DictConstant.WAYBILL_WAIT);
                }
                ruleRegulation.setUpdateTime(new Date());
                ruleRegulation.setUpdateBy(this.getUser().getId());
                ruleRegulation.setRemove(SystemContants.ON);
                
                this.wayBillService.update(ruleRegulation);
                
                LOGGER.info(CommonConstant.UPDATE_SUC_OPTION, ruleRegulation);
                controlLog(ControlLogModel.CONTROL_RESULT_SUCCESS,
                    CommonConstant.UPDATE_SUC_OPTION,
                    ruleRegulation).recordLog();
                sendCode(CommonConstant.UPDATE_SUC_OPTION);
            }
            else {
                this.sendCode(CommonConstant.ARGS_INVALID, id);
            }
        }
        LOGGER.debug(SystemContants.DEBUG_END, form);
        
        // 发送响应消息
        return sendMessage();
    }
    
    /**
     * 〈一句话功能简述〉修改运单信息（app进行操作时修改状态） (根据状态进行路径规划) 〈功能详细描述〉
     * 
     * @param id
     *            id
     * @param form
     *            能管员信息
     * @param bindingResult
     *            验错误信息结果集
     * @return 返回修改结果信息
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/updateForApp/{id}")
    @ResponseBody
    public Object updateForApp(@PathVariable
    String id, @Validated(value = Update.class)
    WayBillForm form, BindingResult bindingResult)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, form);
        boolean isResult = this.addErorrs(bindingResult);
        if (isResult) {
            this.loggerFormValidate(LOGGER, form);
        }
        else {
            if (C503StringUtils.isNotEmpty(id)) {
                final String waybillId = id;
                // 编辑前判断该条数据是否存在
                if (null == this.wayBillService.findById(id)) {
                    throw new CustomException(BizExConstant.WAYBILL_GOODS_EXIST);
                }
                WayBillEntity ruleRegulation = new WayBillEntity();
                this.copyProperties(form, ruleRegulation);
                ruleRegulation.setId(id);
                ruleRegulation.setUpdateTime(new Date());
                ruleRegulation.setUpdateBy(this.getUser().getId());
                ruleRegulation.setRemove(SystemContants.ON);
                this.wayBillService.updateForApp(ruleRegulation);
                
                // 状态更新为取货中 则绑定
                if (StringUtils.equals(DictConstant.WAYBILL_GET,
                    form.getCheckstatus())) {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("id", id);
                    map.put("remove", "0");
                    // 查询运单信息 并封装TerminalEntity 以便绑定
                    WayBillEntity data = this.wayBillService.findForBind(map);
                    LOGGER.info(SystemContants.DEBUG_START, data);
                    TerminalEntity entity = new TerminalEntity();
                    entity.setCarrierName(data.getCarno());
                    entity.setCarcolor(data.getVehicleColor() == null ? ""
                        : data.getVehicleColor());
                    entity.setCardNum(data.getDriverphone());
                    // 绑定操作来自于app端
                    entity.setTerminalSource("app");
                    LOGGER.info(SystemContants.DEBUG_START,
                        "1 terminal.bind.entity:" + entity);
                    this.terminalService.bind(entity);
                }
                // 运单状态更新（状态变为‘取货中’）成功后进行路径规划（线程执行）
                if (StringUtils.equals(DictConstant.WAYBILL_GET,
                    form.getCheckstatus())) {
                    final String idValue = id;
                    
                    final Future<String> fu =
                        ThreadPool.getThreadPool()
                            .submit(new Callable<String>() {
                                @Override
                                public String call()
                                    throws Exception {
                                    return routeOperate.route(waybillId);
                                }
                            });
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                if (fu.get().equals("ok")) {
                                    miPushRuleRegulation(idValue);
                                }
                            }
                            catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
                LOGGER.info(CommonConstant.UPDATE_SUC_OPTION, ruleRegulation);
                controlLog(ControlLogModel.CONTROL_RESULT_SUCCESS,
                    CommonConstant.UPDATE_SUC_OPTION,
                    ruleRegulation).recordLog();
                sendCode(CommonConstant.UPDATE_SUC_OPTION);
            }
            else {
                this.sendCode(CommonConstant.ARGS_INVALID, id);
            }
        }
        LOGGER.debug(SystemContants.DEBUG_END, form);
        // 发送响应消息
        return sendMessage();
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
    public void miPushRuleRegulation(String id)
        throws Exception {
        // 根据运单id查询驾驶员 然后账号注册表查询regid
        String regId = this.wayBillService.findRegIdByWayBill(id);
        String msg = "ok";
        String messagePayload = this.miPushService.getPayload(msg, "3");
        String title = "路径规划";
        String des = "route";
        // 得到message
        Message message = null;
        Result result = null;
        try {
            message =
                this.miPushService.messageHandle(title,
                    des,
                    messagePayload,
                    false);
            LOGGER.info(SystemContants.DEBUG_END, "message: " + message);
            // 发送message 并得到result
            result =
                this.miPushService.sendByRegId(message,
                    regId,
                    NumberContant.FIVE);
            LOGGER.info(SystemContants.DEBUG_END, "result: " + result);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    /**
     * 
     * 〈一句话功能简述〉查询物流企业 〈功能详细描述〉
     * 
     * @param enterpriseType
     *            enterpriseType
     * @return 返回成功 的数据信息
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/findLogistics/{enterpriseType}", method = RequestMethod.POST)
    @ResponseBody
    public Object findLogistics(@PathVariable
    String enterpriseType)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        List<EnterpriseEntity> list =
            this.wayBillService.findCorporateNo(enterpriseType);
        this.sendData(list, CommonConstant.FIND_SUC_OPTION);
        LOGGER.debug(SystemContants.DEBUG_END);
        return this.sendMessage();
    }
    
    /**
     * 
     * 〈一句话功能简述〉查询物流企业下的车牌 〈功能详细描述〉
     * 
     * @param corporateNo
     *            corporateNo
     * @return 返回成功 的数据信息
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/findLicencePlateNo/{corporateNo}", method = RequestMethod.POST)
    @ResponseBody
    public Object findLicencePlateNo(@PathVariable
    String corporateNo)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        List<DangerVehicleEntity> list =
            this.dangerVehicleService.findLicencePlateNo(corporateNo);
        this.sendData(list, CommonConstant.FIND_SUC_OPTION);
        LOGGER.debug(SystemContants.DEBUG_END);
        return this.sendMessage();
    }
    
    /**
     * 
     * 〈一句话功能简述〉查询物流企业下的驾驶员、押运员 〈功能详细描述〉
     * 
     * @param personType
     *            查询的类型
     * @param corporateNo
     *            物流企业法人代码
     * @return 返回成功 的数据信息
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    
    @RequestMapping(value = "/findPersonType", method = RequestMethod.POST)
    @ResponseBody
    public Object findPersonType(String personType, String corporateNo)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("personType", personType);
        map.put("corporateNo", corporateNo);
        // 2、接口调用
        List<OccupationPersonEntity> list =
            this.wayBillService.findPersonType(map);
        // 3、数据返回
        this.sendData(list, CommonConstant.FIND_SUC_OPTION);
        LOGGER.debug(SystemContants.DEBUG_END);
        return this.sendMessage();
    }
    
    /**
     * 
     * 〈一句话功能简述〉通过id查询运单信息 〈功能详细描述〉
     * 
     * @param id
     *            id
     * @return 返回成功 的数据信息
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/findById/{id}")
    @ResponseBody
    public Object findById(@PathVariable
    String id)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        if (C503StringUtils.isNotEmpty(id)) {
            WayBillEntity data = this.wayBillService.findById(id);
            WayBillEntity removeEntity = this.wayBillService.isCarExist(id);
            data.setEnterpriseRemove(removeEntity.getEnterpriseRemove());
            data.setCarRemove(removeEntity.getCarRemove());
            sendData(data, CommonConstant.FIND_SUC_OPTION);
        } // 传入参数无效
        else {
            this.sendCode(CommonConstant.ARGS_INVALID, id);
        }
        LOGGER.debug(SystemContants.DEBUG_END);
        return sendMessage();
    }
    
    /**
     * 〈一句话功能简述〉通过运单号查询运单信息 〈功能详细描述〉
     * 
     * @param orderNo
     *            orderNo
     * @return Object
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/findOrderMessage")
    @ResponseBody
    public Object findOrderMessage(String orderNo)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        WayBillEntity data = this.wayBillService.findOrderMessage(orderNo);
        sendData(data, CommonConstant.FIND_SUC_OPTION);
        LOGGER.debug(SystemContants.DEBUG_END);
        return sendMessage();
    }
    
    /**
     * 〈一句话功能简述〉分页查询运单---货物 〈功能详细描述〉
     * 
     * @param page
     *            page
     * @param rows
     *            rows
     * @param id
     *            id
     * @return object
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/goodsFindByPagePrint")
    @ResponseBody
    public Object goodsFindByPagePrint(Integer page, String id)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        // 1、参数设置
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);
        
        // 2、接口调用
        List<WayBillGoodsEntity> list =
            this.wayBillGoodsService.findByParams(map);
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setNum(String.valueOf(i + 1));
        }
        // 3、数据返回
        sendData(list, CommonConstant.FIND_SUC_OPTION);
        LOGGER.debug(SystemContants.DEBUG_END);
        return this.sendMessage();
    }
    
    /**
     * 〈一句话功能简述〉分页查询运单---货物 〈功能详细描述〉
     * 
     * @param page
     *            page
     * @param rows
     *            rows
     * @param id
     *            id
     * @return object
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/goodsFindByPage", method = RequestMethod.POST)
    @ResponseBody
    public Object goodsFindByPage(Integer page, Integer rows, String id)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        // 1、参数设置
        Map<String, Object> map = new HashMap<String, Object>();
        Page pageEntity = new Page();
        pageEntity.setCurrentPage(page);
        pageEntity.setPageSize(rows);
        map.put("page", pageEntity);
        map.put("id", id);
        
        // 2、接口调用
        List<WayBillGoodsEntity> list =
            this.wayBillGoodsService.findByParams(map);
        // 3、数据返回
        setJQGrid(list,
            pageEntity.getTotalCount(),
            page,
            rows,
            CommonConstant.FIND_SUC_OPTION);
        LOGGER.debug(SystemContants.DEBUG_END);
        return (ResultJQGrid) this.sendMessage();
    }
    
    /**
     * 
     * 〈一句话功能简述〉 〈功能详细描述〉保存货物信息
     * 
     * @param form
     *            实体
     * @param bindingResult
     *            表单验证错误集合
     * @return object
     * @throws Exception
     *             系统异常
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/goodsSave", method = RequestMethod.POST)
    @ResponseBody
    public Object goodsSave(@Validated(value = Save.class)
    WayBillGoodsForm form, BindingResult bindingResult)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, form);
        boolean isResult = this.addErorrs(bindingResult);
        if (isResult) {
            sendData(getValidErorrs(), CommonConstant.FORMVALID_FAIL_OPTION);
            // 记录操作失败信息（存入文件）
            LOGGER.info(CommonConstant.FORMVALID_FAIL_OPTION, form);
            // 保存操作日志 记录操作失败（存入数据库）
            controlLog(ControlLogModel.CONTROL_RESULT_FAIL,
                CommonConstant.FORMVALID_FAIL_OPTION,
                getValidErorrs()).setErrorMessage(CommonConstant.FORMVALID_FAIL_OPTION)
                .recordLog();
        }
        else {
            // 2、实体赋值
            WayBillGoodsEntity entity = new WayBillGoodsEntity();
            this.copyProperties(form, entity);
            entity.setId(C503StringUtils.createUUID());
            entity.setCreateBy(this.getUser().getId());
            entity.setCreateTime(new Date());
            entity.setUpdateBy(this.getUser().getId());
            entity.setUpdateTime(new Date());
            entity.setRemove(SystemContants.ON);
            // 3、调用接口信息
            this.wayBillGoodsService.save(entity);
            // 4、打印日志
            LOGGER.info(CommonConstant.SAVE_SUC_OPTION, entity);
            sendCode(CommonConstant.SAVE_SUC_OPTION);
        }
        LOGGER.debug(SystemContants.DEBUG_END, form);
        return this.sendMessage();
    }
    
    /**
     * 〈一句话功能简述〉删除货物信息 〈功能详细描述〉
     * 
     * @param ids
     *            ids
     * @return 响应到前台的数据
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/goodsDelete/{ids}", method = RequestMethod.DELETE)
    @ResponseBody
    public Object goodsDelete(@PathVariable
    String... ids)
        throws Exception {
        
        LOGGER.debug(SystemContants.DEBUG_START, ids);
        if (null != ids && 0 < ids.length) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("ids", ids);
            map.put("updateBy", this.getUser().getId());
            map.put("updateTime", new Date());
            
            int line = this.wayBillGoodsService.delete(map);
            
            LOGGER.info(CommonConstant.DELETE_SUC_OPTION, line);
            // 保存操作日志 记录操作成功
            controlLog(ControlLogModel.CONTROL_RESULT_SUCCESS,
                CommonConstant.DELETE_SUC_OPTION,
                map).recordLog();
            sendCode(CommonConstant.DELETE_SUC_OPTION);
        }
        // 传入参数无效
        else {
            this.sendCode(CommonConstant.ARGS_INVALID, (Object[]) ids);
        }
        LOGGER.debug(SystemContants.DEBUG_END);
        
        return sendMessage();
        
    }
    
    /**
     * 
     * 〈一句话功能简述〉通过id查询货物信息 〈功能详细描述〉
     * 
     * @param id
     *            id
     * @return 返回成功 的数据信息
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/goodsFindById/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Object goodsfindById(@PathVariable
    String id)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        if (C503StringUtils.isNotEmpty(id)) {
            WayBillGoodsEntity data = this.wayBillGoodsService.findById(id);
            sendData(data, CommonConstant.FIND_SUC_OPTION);
        } // 传入参数无效
        else {
            this.sendCode(CommonConstant.ARGS_INVALID, id);
        }
        LOGGER.debug(SystemContants.DEBUG_END);
        return sendMessage();
    }
    
    /**
     * 〈一句话功能简述〉修改货物信息 〈功能详细描述〉
     * 
     * @param id
     *            id
     * @param form
     *            能管员信息
     * @param bindingResult
     *            验错误信息结果集
     * @return 返回修改结果信息
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/goodsupdate/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Object goodsupdate(@PathVariable
    String id, @Validated(value = Update.class)
    WayBillGoodsForm form, BindingResult bindingResult)
        throws Exception {
        
        LOGGER.debug(SystemContants.DEBUG_START, form);
        boolean isResult = this.addErorrs(bindingResult);
        if (isResult) {
            sendData(getValidErorrs(), CommonConstant.FORMVALID_FAIL_OPTION);
            LOGGER.info(CommonConstant.FORMVALID_FAIL_OPTION, form);
            controlLog(ControlLogModel.CONTROL_RESULT_FAIL,
                CommonConstant.FORMVALID_FAIL_OPTION,
                getValidErorrs()).setErrorMessage(CommonConstant.FORMVALID_FAIL_OPTION)
                .recordLog();
        }
        else {
            if (C503StringUtils.isNotEmpty(id)) {
                
                // 编辑前判断该条数据是否存在
                if (null == this.wayBillGoodsService.findById(id)) {
                    throw new CustomException(BizExConstant.WAYBILL_GOODS_EXIST);
                }
                
                WayBillGoodsEntity ruleRegulation = new WayBillGoodsEntity();
                this.copyProperties(form, ruleRegulation);
                ruleRegulation.setId(id);
                ruleRegulation.setUpdateTime(new Date());
                ruleRegulation.setUpdateBy(this.getUser().getId());
                ruleRegulation.setRemove(SystemContants.ON);
                
                this.wayBillGoodsService.update(ruleRegulation);
                
                LOGGER.info(CommonConstant.UPDATE_SUC_OPTION, ruleRegulation);
                controlLog(ControlLogModel.CONTROL_RESULT_SUCCESS,
                    CommonConstant.UPDATE_SUC_OPTION,
                    ruleRegulation).recordLog();
                sendCode(CommonConstant.UPDATE_SUC_OPTION);
            }
            else {
                this.sendCode(CommonConstant.ARGS_INVALID, id);
            }
        }
        LOGGER.debug(SystemContants.DEBUG_END, form);
        
        // 发送响应消息
        return sendMessage();
    }
    
    /**
     * 〈一句话功能简述〉查询运输货物的车辆的信息 〈功能详细描述〉
     * 
     * @param carno
     *            carno
     * @param page
     *            page
     * @param rows
     *            rows
     * @return object
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/findVehicleData", method = RequestMethod.POST)
    @ResponseBody
    public Object findVehicleData(String carno, Integer page, Integer rows)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        // 1、参数设置
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("carno", carno);
        Page pageEntity = new Page();
        pageEntity.setCurrentPage(page);
        pageEntity.setPageSize(rows);
        map.put("page", pageEntity);
        
        // 2、接口调用
        List<WayBillEntity> list = this.wayBillService.findVehicleData(map);
        // 3、数据返回
        setJQGrid(list,
            pageEntity.getTotalCount(),
            page,
            rows,
            CommonConstant.FIND_SUC_OPTION);
        LOGGER.debug(SystemContants.DEBUG_END);
        return (ResultJQGrid) this.sendMessage();
    }
    
    /**
     * 〈一句话功能简述〉通过车牌号查询车辆信息 〈功能详细描述〉
     * 
     * @param carrierName
     *            carrierName
     * @return CarLocationEntity
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/findByCarrierName/{carrierName}", method = RequestMethod.GET)
    @ResponseBody
    public Object findByCarrierName(@PathVariable
    String carrierName)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        if (C503StringUtils.isNotEmpty(carrierName)) {
            CarLocationEntity data =
                this.wayBillService.findByCarrierName(carrierName);
            sendData(data, CommonConstant.FIND_SUC_OPTION);
        } // 传入参数无效
        else {
            this.sendCode(CommonConstant.ARGS_INVALID, carrierName);
        }
        LOGGER.debug(SystemContants.DEBUG_END);
        return sendMessage();
    }
    
    /**
     * 〈一句话功能简述〉通过车牌号查询货物信息 〈功能详细描述〉
     * 
     * @param carNo
     *            carNo
     * @return List<WayBillGoodsEntity>
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/findGoodsData/{carNo}")
    @ResponseBody
    public Object findGoodsData(@PathVariable
    String carNo)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        if (C503StringUtils.isNotEmpty(carNo)) {
            List<WayBillGoodsEntity> data =
                this.wayBillService.findGoodsData(carNo);
            sendData(data, CommonConstant.FIND_SUC_OPTION);
        } // 传入参数无效
        else {
            this.sendCode(CommonConstant.ARGS_INVALID, carNo);
        }
        LOGGER.debug(SystemContants.DEBUG_END);
        return sendMessage();
    }
    
    @SuppressWarnings("unchecked")
    @Override
    protected <T> IBaseService<T> getBaseService() {
        return (IBaseService<T>) this.wayBillService;
    }
    
    @Override
    protected Object show() {
        return null;
    }
    
    @Override
    protected LoggingManager logger() {
        return LOGGER;
    }
    
    /**
     * 〈一句话功能简述〉根据运单获取车辆的路径规划 〈功能详细描述〉
     * 
     * @param waybillId
     *            waybillId
     * @return Object
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/findRoute/{waybillId}")
    @ResponseBody
    public Object findRoute(@PathVariable
    String waybillId)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, waybillId);
        if (C503StringUtils.isNotEmpty(waybillId)) {
            SolveRoute data = null;
            try {
                data = this.solveRouteService.findRouteByWaybillId(waybillId);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            
            sendData(data, CommonConstant.FIND_SUC_OPTION);
        } // 传入参数无效
        else {
            this.sendCode(CommonConstant.ARGS_INVALID, waybillId);
        }
        LOGGER.debug(SystemContants.DEBUG_END);
        return sendMessage();
    }
    
    // ///////////////////////////////运单 （微信）////////////////////////////////
    
    /**
     * 〈一句话功能简述〉分页查询运单信息(微信) 〈功能详细描述〉
     * 
     * @param corporateNo
     *            corporateNo
     * @param waybillNo
     *            waybillNo
     * @param page
     *            page
     * @param rows
     *            rows
     * @return Object
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/findByPageForWeixin")
    @ResponseBody
    public Object findByPageForWeixin(String corporateNo, String waybillNo,
        Integer page, Integer rows)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        Map<String, Object> map = new HashMap<String, Object>();
        corporateNo = corporateNo == null ? null : corporateNo.trim();
        waybillNo = waybillNo == null ? null : waybillNo.trim();
        map.put("corporateNo", corporateNo);
        map.put("waybillNo", waybillNo);
        Page pageEntity = new Page();
        pageEntity.setCurrentPage(page);
        pageEntity.setPageSize(rows);
        map.put("page", pageEntity);
        BizUserEntity user = this.getUser();
        List<String> roleCodes = user.getRoleCodes();
        List<Map<String, Object>> list = null;
        if (roleCodes.contains(DictConstant.GOVERNMENT_USER)) {
            list = this.wayBillService.findWaybillForWeixinParams(map);
        }
        setJQGrid(list,
            pageEntity.getTotalCount(),
            page,
            rows,
            CommonConstant.FIND_SUC_OPTION);
        LOGGER.debug(SystemContants.DEBUG_END);
        return (ResultJQGrid) this.sendMessage();
        
    }
    
    /**
     * 
     * 〈一句话功能简述〉 〈功能详细描述〉保存模板信息
     * 
     * @param form
     *            实体
     * @param bindingResult
     *            表单验证错误集合
     * @return object
     * @throws Exception
     *             系统异常
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/isHasWayBillNo/{wayBillNo}", method = RequestMethod.POST)
    @ResponseBody
    public Object isHasWayBillNo(@PathVariable
    String wayBillNo)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, wayBillNo);
        if (null != wayBillNo) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("wayBillNo", wayBillNo);
            // 3、调用接口信息
            String checkNo = this.wayBillService.isHasWayBillNo(map);
            boolean result = true;
            if (null == checkNo) {
                result = false;
            }
            sendData(result, CommonConstant.FIND_SUC_OPTION);
        }
        else {
            this.sendCode(CommonConstant.ARGS_INVALID, wayBillNo);
        }
        // 4、打印日志
        LOGGER.info(CommonConstant.FIND_SUC_OPTION, wayBillNo);
        return this.sendMessage();
    }
    
    /**
     * 
     * 〈一句话功能简述〉 通过车牌号查询路径规划〈功能详细描述〉
     * 
     * @param carrierName
     *            carrierName
     * @return String
     * @throws Exception
     *             系统异常
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/findLastRoute")
    @ResponseBody
    public Object findLastRoute(
        @RequestParam(value = "carrierName", required = true)
        String carrierName)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, carrierName);
        SolveRoute sr = null;
        try {
            sr = this.solveRouteService.findLastRoute(carrierName);
        }
        catch (Exception e) {
            sendCode(NumberContant.THREE, e.getMessage());
            LOGGER.debug(SystemContants.DEBUG_END, "message" + e.getMessage());
            return this.sendMessage();
        }
        LOGGER.debug(SystemContants.DEBUG_END, sr);
        sendData(sr, CommonConstant.FIND_SUC_OPTION);
        return this.sendMessage();
    }
}
