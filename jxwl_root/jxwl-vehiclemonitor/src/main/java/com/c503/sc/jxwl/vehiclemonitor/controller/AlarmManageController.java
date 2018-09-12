/*
 * 文件名：AlarmManageController.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-7-25
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.vehiclemonitor.controller;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.c503.sc.base.common.NumberContant;
import com.c503.sc.base.entity.Page;
import com.c503.sc.base.exception.CustomException;
import com.c503.sc.base.service.IBaseService;
import com.c503.sc.base.validation.Save;
import com.c503.sc.base.validation.Update;
import com.c503.sc.jxwl.common.bean.AlarmEntity;
import com.c503.sc.jxwl.common.constant.CommonConstant;
import com.c503.sc.jxwl.common.constant.DictConstant;
import com.c503.sc.jxwl.common.controller.ResultController;
import com.c503.sc.jxwl.vehiclemonitor.constant.BizExConstants;
import com.c503.sc.jxwl.vehiclemonitor.formbean.AlarmManageForm;
import com.c503.sc.jxwl.zcpt.bean.WayBillEntity;
import com.c503.sc.jxwl.zcpt.constant.ReturnMsg;
import com.c503.sc.jxwl.zcpt.dao.ICarInfoDao;
import com.c503.sc.jxwl.zcpt.service.IAlarmManageService;
import com.c503.sc.jxwl.zcpt.service.ITerminalService;
import com.c503.sc.jxwl.zcpt.vo.AlarmThresholdVo;
import com.c503.sc.log.ControlLogModel;
import com.c503.sc.log.LoggingManager;
import com.c503.sc.utils.basetools.C503DateUtils;
import com.c503.sc.utils.basetools.C503ExcelUtils;
import com.c503.sc.utils.basetools.C503StringUtils;
import com.c503.sc.utils.common.SystemContants;
import com.c503.sc.utils.exceltools.ExportSheet;
import com.c503.sc.utils.response.ResultJQGrid;

/**
 * 
 * 〈一句话功能简述〉报警管理Controller 〈功能详细描述〉
 * 
 * @author guqh
 * @version [版本号, 2016-7-25]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Controller
@Scope(value = "prototype")
@RequestMapping(value = "/alarmManage")
public class AlarmManageController extends ResultController {
    /** 日志器 */
    private static final LoggingManager LOGGER =
        LoggingManager.getLogger(AlarmManageController.class);
    
    /** 报警信息业务接口 */
    @Resource
    private IAlarmManageService alarmManageService;
    
    /** dao */
    @Resource(name = "carInfoDao")
    private ICarInfoDao carInfoDao;
    
    /** 发送报警参数服务 */
    @Autowired
    private ITerminalService terminalService;
    
    /**
     * 
     * 〈一句话功能简述〉 保存报警信息 【app一键呼叫报警】〈功能详细描述〉
     * 
     * @param account
     *            account
     * @param message
     *            message
     * @return object
     * @throws Exception
     *             系统异常
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/saveForAPP", method = {RequestMethod.POST,
        RequestMethod.GET})
    @ResponseBody
    public Object saveForAPP(String account, String message)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, account);
        Date nowDate = new Date();
        if (C503StringUtils.isNotEmpty(account)
            && C503StringUtils.isNotEmpty(message)) {
            
            List<String> list =
                this.alarmManageService.findForAppByAccount(account);
            if (null == list || list.size() == 0) {
                this.sendCode(CommonConstant.CANNOTE_ALARM);
                LOGGER.debug(SystemContants.DEBUG_END);
                return this.sendMessage();
            }
            /*
             * 查找T_REGISTER_INFO 得到occupation_id 再查找T_OCCUPATION_PERSON 得到身份证号
             * 再查找T_WAYBILL 得到相关信息 再查找T_DANGER_VEHICLE 得到终端等相关信息
             */
            List<AlarmEntity> alarmEntityList =
                this.alarmManageService.findByAccount(account);
            /*
             * alarmEntity.setCorporateNo(map.get("CORPORATENO"));
             * alarmEntity.setDriverId(map.get("CARDNO"));
             */
            AlarmEntity alarmEntity = new AlarmEntity();
            // 设置相关参数
            if (alarmEntityList.size() <= 0) {
                String enterpriseName =
                    this.alarmManageService.findNameByAccount(account);
                alarmEntity.setEnterpriseName(enterpriseName);
                alarmEntity.setCarrierName("无");
                alarmEntity.setWaybillNo("无");
            }
            else {
                alarmEntity = alarmEntityList.get(0);
                if (null == alarmEntity.getCarrierName()) {
                    alarmEntity.setCarrierName("无");
                }
                if (null == alarmEntity.getWaybillNo()) {
                    alarmEntity.setWaybillNo("无");
                }
            }
            WayBillEntity wayBilldata;
            wayBilldata =
                this.carInfoDao.findWayBillData(alarmEntity.getCarrierName())
                    .get(0);
            if (null != wayBilldata) {
                // 货单号
                alarmEntity.setWaybillNo(wayBilldata.getCheckno());
                // 驾驶员id
                alarmEntity.setDriverId(wayBilldata.getDriverId());
                // 驾驶员手机
                alarmEntity.setTelephone(wayBilldata.getDriverphone());
            }
            alarmEntity.setAlarmDetails(message);
            alarmEntity.setAlarmType(DictConstant.ALARM_IMMEDIATELY_APP);
            alarmEntity.setAlarmDealStatus(DictConstant.ALARM_NOT_DEALED);
            String id = C503StringUtils.createUUID();
            alarmEntity.setId(id);
            alarmEntity.setAlarmNo("B" + System.currentTimeMillis());
            alarmEntity.setCreateBy(this.getUser().getId());
            alarmEntity.setCreateTime(nowDate);
            alarmEntity.setUpdateBy(this.getUser().getId());
            alarmEntity.setUpdateTime(nowDate);
            alarmEntity.setAlarmDate(nowDate);
            alarmEntity.setRemove(SystemContants.ON);
            // 保存报警信息 并推送至前端
            // this.alarmManageService.deleteAlarm(alarmEntity);
            this.alarmManageService.save(alarmEntity);
            LOGGER.info(CommonConstant.SAVE_SUC_OPTION);
            controlLog(ControlLogModel.CONTROL_RESULT_SUCCESS,
                CommonConstant.SAVE_SUC_OPTION,
                account).recordLog();
            // sendCode(BizExConstants.ALARM_SUC);
            // 开启线程 启动小米推送
            final AlarmEntity alarm = alarmEntity;
            //推送给手机的报警类型要翻译
            //alarm.setAlarmType("移动APP一键呼叫报警");
            String alarmType = this.alarmManageService.findNameByCode(DictConstant.ALARM_IMMEDIATELY_APP);
            alarm.setAlarmType(alarmType);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        List<AlarmEntity> alarms = new ArrayList<AlarmEntity>();
                        alarms.add(alarm);
                        alarmManageService.miPushAlarm(alarms);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            // 小米推送完成以后再返回结果给前端
            this.sendCode(BizExConstants.ALARM_SUC);
        }
        else {
            this.sendCode(CommonConstant.ARGS_INVALID);
        }
        
        LOGGER.debug(SystemContants.DEBUG_END);
        return this.sendMessage();
    }
    
    /**
     * 
     * 〈一句话功能简述〉保存报警信息 〈功能详细描述〉
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
    AlarmManageForm form, BindingResult bindingResult)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, form);
        boolean isResult = this.addErorrs(bindingResult);
        // 1、后台表单验证
        // 1、后台表单验证
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
            AlarmEntity entity = new AlarmEntity();
            this.copyProperties(form, entity);
            
            String id = C503StringUtils.createUUID();
            entity.setId(id);
            entity.setAlarmNo(id);
            entity.setCreateBy(this.getUser().getId());
            entity.setCreateTime(new Date());
            entity.setUpdateBy(this.getUser().getId());
            entity.setUpdateTime(new Date());
            entity.setRemove(SystemContants.ON);
            // 3、调用接口信息
            this.alarmManageService.save(entity);
            // 4、打印日志
            LOGGER.info(CommonConstant.SAVE_SUC_OPTION, entity);
            controlLog(ControlLogModel.CONTROL_RESULT_SUCCESS,
                CommonConstant.SAVE_SUC_OPTION,
                entity).recordLog();
            sendCode(CommonConstant.SAVE_SUC_OPTION);
        }
        LOGGER.debug(SystemContants.DEBUG_END, form);
        return this.sendMessage();
    }
    
    /**
     * 
     * 〈一句话功能简述〉处理报警 〈功能详细描述〉修改数据信息
     * 
     * @param id
     *            id
     * @param form
     *            实体信息
     * @param bindingResult
     *            错误集合
     * @return object
     * @throws Exception
     *             系统异常
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Object update(@PathVariable
    String id, @Validated(value = Update.class)
    AlarmManageForm form, BindingResult bindingResult)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, form);
        // 权限判断
        boolean isResult = this.addErorrs(bindingResult);
        // 1、后台表单验证
        if (isResult) {
            this.loggerFormValidate(LOGGER, form);
        }
        else {
            // 2、实体赋值
            AlarmEntity entity = new AlarmEntity();
            entity.setAlarmRegisterHandle(form.getAlarmRegisterHandle());
            entity.setId(id);
            entity.setUpdateBy(this.getUser().getId());
            entity.setUpdateTime(new Date());
            entity.setAlarmDealStatus(DictConstant.ALARM_DEALED);
            
            // 处理前查询该条数据状态
            AlarmEntity val = this.alarmManageService.findById(id);
            if (StringUtils.equals(val.getAlarmDealStatus(), "已处理")) {
                throw new CustomException(BizExConstants.DATA_DEL_ALREADY);
            }
            entity.setCarrierName(val.getCarrierName());
            // 3、处理
            this.alarmManageService.update(entity);
            
            // 4、打印日志
            LOGGER.info(CommonConstant.UPDATE_SUC_OPTION, entity);
            controlLog(ControlLogModel.CONTROL_RESULT_SUCCESS,
                CommonConstant.UPDATE_SUC_OPTION,
                entity).recordLog();
            sendCode(CommonConstant.UPDATE_SUC_OPTION);
        }
        LOGGER.debug(SystemContants.DEBUG_END, form);
        return this.sendMessage();
    }
    
    /**
     * 〈一句话功能简述〉分页查询报警管理信息 〈功能详细描述〉
     * 
     * @param form
     *            form
     * @return 数据信息
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/findByPage", method = RequestMethod.POST)
    @ResponseBody
    public Object findByPage(AlarmManageForm form)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        Timer t = new Timer();
        t.schedule(new TimerTask() {
            private IAlarmManageService alarmManageService;
            
            public void run() {
                try {
                    this.alarmManageService.deleteAlarmInfo();
                }
                catch (Exception e) {
                    logger().error(0, e);
                }
            }
        }, 259200000);
        
        // 1、参数设置
        Map<String, Object> map = form.handlePageParas();
        map.put("alarmDealStatus", form.getAlarmDealStatus());
        map.put("carrierName", form.getLicencePlateNo() == null ? null
            : form.getLicencePlateNo().trim());
        map.put("alarmId", form.getAlarmId() == null ? null : form.getAlarmId()
            .trim());
        map.put("starAlarmDate", form.getStarAlarmDate());
        
        List<String> roleCode = this.getUser().getRoleCodes();
        if (!roleCode.contains(DictConstant.GOVERNMENT_USER)) {
            map.put("corporateNo", this.getUser().getCorporateCode());
        }
        
        if (null != form.getEndAlarmDate()) {
            map.put("endAlarmDate",
                C503DateUtils.getDay(NumberContant.ONE, form.getEndAlarmDate()));
        }
        
        map.put("remove", SystemContants.ON);
        // 2、接口调用
        List<AlarmEntity> list = this.alarmManageService.findByParams(map);
        // 3、数据返回
        Page page = (Page) map.get("page");
        setJQGrid(list,
            page.getTotalCount(),
            form.getCurrentPage(),
            form.getPageSize(),
            CommonConstant.FIND_SUC_OPTION);
        LOGGER.debug(SystemContants.DEBUG_END);
        return (ResultJQGrid) this.sendMessage();
    }
    
    /**
     * 〈一句话功能简述〉ie9定时请求报警信息 〈功能详细描述〉
     * 
     * @return 数据信息
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/updateAlarmIE9", method = RequestMethod.GET)
    @ResponseBody
    public Object updateAlarmIE9()
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        // 1、参数设置
        Map<String, Object> map = new HashMap<String, Object>();
        Date starAlarmDate = new Date();
        starAlarmDate.setTime(new Date().getTime()
            - CommonConstant.FIVE_THOUSAND);
        map.put("starAlarmDate", starAlarmDate);
        List<String> roleCode = this.getUser().getRoleCodes();
        if (!roleCode.contains(DictConstant.GOVERNMENT_USER)) {
            map.put("corporateNo", this.getUser().getCorporateCode());
        }
        // 2、接口调用
        List<AlarmEntity> list = this.alarmManageService.findByParams(map);
        sendData(list, CommonConstant.FIND_SUC_OPTION);
        return this.sendMessage();
    }
    
    /**
     * 
     * 〈一句话功能简述〉通过id查询企业信息 〈功能详细描述〉
     * 
     * @param id
     *            id
     * @return 返回成功 的数据信息
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/findById/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Object findById(@PathVariable
    String id)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        if (C503StringUtils.isNotEmpty(id)) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", id);
            
            AlarmEntity val = this.alarmManageService.findById(id);
            
            sendData(val, CommonConstant.FIND_SUC_OPTION);
        } // 传入参数无效
        else {
            this.sendCode(CommonConstant.ARGS_INVALID, id);
        }
        
        LOGGER.debug(SystemContants.DEBUG_END);
        
        return sendMessage();
    }
    
    /**
     * 
     * 〈一句话功能简述〉 〈功能详细描述〉修改报警设置
     * 
     * @param id
     *            id
     * @param vo
     *            实体信息
     * @param bindingResult
     *            错误集合
     * @return object
     * @throws Exception
     *             系统异常
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/updateThreshold/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Object updateThreshold(@PathVariable
    String id, @Validated(value = Update.class)
    AlarmThresholdVo vo, BindingResult bindingResult)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, vo);
        
        boolean isResult = this.addErorrs(bindingResult);
        // 1、后台表单验证
        if (isResult) {
            this.loggerFormValidate(LOGGER, vo);
        }
        else {
            // 1、设置报警参数至远程
            
            Map<String, String> map = new HashMap<String, String>();
            // 最高速度
            map.put("overSpeedValue", String.valueOf(vo.getOverSpeedValue()));
            // 超速持续时间
            map.put("speedContinueValue",
                String.valueOf(vo.getSpeedContinueValue()));
            // 连续驾驶时间上限
            map.put("fatigueDriveValue",
                String.valueOf(vo.getFatigueDriveValue()));
            // 超时停车阈值
            map.put("overtimeParkValue",
                String.valueOf(vo.getOvertimeParkValue()));
            String result = null;
            
            // 协议不能在业务类处理，需要把参数传递给支持平台
            this.terminalService.setTerminalParam(map);
            
            ReturnMsg.handleException(result);
            
            // 2、实体赋值
            vo.setId(id);
            // 3、调用接口
            this.alarmManageService.updateThreshold(vo);
            
            // 4、打印日志
            LOGGER.info(CommonConstant.UPDATE_SUC_OPTION, vo);
            controlLog(ControlLogModel.CONTROL_RESULT_SUCCESS,
                CommonConstant.UPDATE_SUC_OPTION,
                vo).recordLog();
            
            sendCode(CommonConstant.UPDATE_SUC_OPTION);
        }
        LOGGER.debug(SystemContants.DEBUG_END, vo);
        return this.sendMessage();
    }
    
    /**
     * 〈一句话功能简述〉 〈功能详细描述〉
     * 
     * @return object
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/findThresholdById", method = RequestMethod.GET)
    @ResponseBody
    public Object findThresholdById()
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        Map<String, Object> map = new HashMap<String, Object>();
        
        AlarmThresholdVo val = this.alarmManageService.findThresholdById(map);
        
        sendData(val, CommonConstant.FIND_SUC_OPTION);
        
        LOGGER.debug(SystemContants.DEBUG_END);
        
        return sendMessage();
    }
    
    /**
     * 
     * 〈一句话功能简述〉导出报警信息 〈功能详细描述〉
     * 
     * @param entity
     *            查询条件
     * @param response
     *            响应对象
     * @throws Exception
     *             异常
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/export", method = RequestMethod.GET)
    @ResponseBody
    public void exportExcel(AlarmManageForm entity,
        HttpServletResponse response, HttpServletRequest request)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, entity);
        
        Map<String, Object> map = entity.handlePageParas();
        map.put("deptId", getUser().getDeptId());
        C503ExcelUtils excelUtils = new C503ExcelUtils();
        
        Map<String, Object> recvMap = this.alarmManageService.exportExcel(map);
        ExportSheet sheet = (ExportSheet) recvMap.get("sheet");
        excelUtils.addSheet(sheet);
        OutputStream out = response.getOutputStream();
        // 设置响应文本类型
        response.setContentType("application;charset=gb2312");
        // 设置响应文件名
        String excelName = (String) recvMap.get("excelName");
        response.setHeader("Content-Disposition", "attachment;filename="
            + compatibilityFileName(request, excelName, ".xls"));
        
        excelUtils.writeToStream(out);
        out.close();
        response.flushBuffer();
        // 记录操作成功信息
        LOGGER.info(CommonConstant.EXPORT_SUC_OPTION, recvMap);
        // 保存操作日志 记录操作成功
        controlLog(ControlLogModel.CONTROL_RESULT_SUCCESS,
            CommonConstant.EXPORT_SUC_OPTION,
            recvMap).recordLog();
        LOGGER.debug(SystemContants.DEBUG_END, entity);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    protected <T> IBaseService<T> getBaseService() {
        return (IBaseService<T>) this.alarmManageService;
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
     * 〈一句话功能简述〉分页查询报警管理信息 〈功能详细描述〉
     * 
     * @param carrierName
     *            carrierName
     * @param page
     *            page
     * @param rows
     *            rows
     * @return 数据信息
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/findAlarmForDiTu")
    @ResponseBody
    public Object findAlarmForDiTu(String carrierName, Integer page,
        Integer rows)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        // 1、参数设置
        Map<String, Object> map = new HashMap<>();
        map.put("carrierName", carrierName);
        Page pageEntity = new Page();
        pageEntity.setCurrentPage(page);
        pageEntity.setPageSize(rows);
        map.put("page", pageEntity);
        
        // List<String> roleCode = this.getUser().getRoleCodes();
        // if (!roleCode.contains(DictConstant.GOVERNMENT_USER)){
        // }
        
        // 2、接口调用
        List<AlarmEntity> list = this.alarmManageService.findAlarmForDiTu(map);
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
     * 〈一句话功能简述〉分页查询报警管理信息 〈功能详细描述〉
     * 
     * @param carrierName
     *            carrierName
     * @return 数据信息
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/findAlarmNumForDiTu")
    @ResponseBody
    public Object findAlarmNumForDiTu(String carrierName)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        // 1、参数设置
        Map<String, Object> map = new HashMap<>();
        map.put("carrierName", carrierName);
        
        // List<String> roleCode = this.getUser().getRoleCodes();
        // if (!roleCode.contains(DictConstant.GOVERNMENT_USER)) {
        // }
        
        // 2、接口调用
        int result = this.alarmManageService.findAlarmNumForDiTu(map);
        // 3、数据返回
        sendData(result, CommonConstant.FIND_SUC_OPTION);
        LOGGER.debug(SystemContants.DEBUG_END);
        return this.sendMessage();
    }
    
    /**
     * 〈一句话功能简述〉定时一个月物理删除数据库报警信息〈功能详细描述〉
     * 
     * @return 响应到前台的数据
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/deleteAlarmInfo", method = RequestMethod.DELETE)
    @ResponseBody
    public Object deleteAlarmInfo()
        throws Exception {
        // 记录程序进入方法调试日志
        LOGGER.debug(SystemContants.DEBUG_START);
        
        int line = this.alarmManageService.deleteAlarmInfo();
        
        LOGGER.info(CommonConstant.DELETE_SUC_OPTION, line);
        // 保存操作日志 记录操作成功
        controlLog(ControlLogModel.CONTROL_RESULT_SUCCESS,
            CommonConstant.DELETE_SUC_OPTION).recordLog();
        sendCode(CommonConstant.DELETE_SUC_OPTION);
        LOGGER.debug(SystemContants.DEBUG_END);
        
        return sendMessage();
    }
}
