/*
 * 文件名：DangerVehicleController.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-7-20
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.vehiclemonitor.controller;

import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import sc.c503.authclient.cache.CacheManagerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.c503.sc.base.common.NumberContant;
import com.c503.sc.base.entity.Page;
import com.c503.sc.base.exception.CustomException;
import com.c503.sc.base.service.IBaseService;
import com.c503.sc.base.validation.Save;
import com.c503.sc.base.validation.Update;
import com.c503.sc.filemanage.bean.FileInfoEntity;
import com.c503.sc.filemanage.service.IFileManageService;
import com.c503.sc.jxwl.common.bean.BizUserEntity;
import com.c503.sc.jxwl.common.bean.LocationEntity;
import com.c503.sc.jxwl.common.bean.PageEntity;
import com.c503.sc.jxwl.common.constant.BizConstants;
import com.c503.sc.jxwl.common.constant.CommonConstant;
import com.c503.sc.jxwl.common.constant.DictConstant;
import com.c503.sc.jxwl.common.constant.SysCommonConstant;
import com.c503.sc.jxwl.common.controller.ResultController;
import com.c503.sc.jxwl.vehiclemonitor.constant.BizExConstants;
import com.c503.sc.jxwl.vehiclemonitor.dao.IDangerVehicleDao;
import com.c503.sc.jxwl.vehiclemonitor.formbean.DangerVehicleForm;
import com.c503.sc.jxwl.zcpt.bean.DangerVehicleEntity;
import com.c503.sc.jxwl.zcpt.bean.TerminalEntity;
import com.c503.sc.jxwl.zcpt.service.IDangerVehicleService;
import com.c503.sc.jxwl.zcpt.service.ITerminalService;
import com.c503.sc.jxwl.zcpt.vo.WlEnpCarVo;
import com.c503.sc.log.ControlLogModel;
import com.c503.sc.log.LoggingManager;
import com.c503.sc.log.resource.ResourceManager;
import com.c503.sc.utils.basetools.C503ExcelUtils;
import com.c503.sc.utils.basetools.C503StringUtils;
import com.c503.sc.utils.cache.CacheManager;
import com.c503.sc.utils.cache.ICache;
import com.c503.sc.utils.common.SystemContants;
import com.c503.sc.utils.exceltools.Content;
import com.c503.sc.utils.exceltools.ExportSheet;
import com.c503.sc.utils.exceltools.Header;
import com.c503.sc.utils.response.ResultJQGrid;

/**
 * 
 * 〈一句话功能简述〉危险品车辆信息Controller 〈功能详细描述〉
 * 
 * @author guqh
 * @version [版本号, 2016-7-20]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Controller
@Scope(value = "prototype")
@RequestMapping(value = "/dangerVehicle")
public class DangerVehicleController extends ResultController {
    /** 日志器 */
    private static final LoggingManager LOGGER =
        LoggingManager.getLogger(DangerVehicleController.class);
    
    /** 缓存管理器 */
    private static final CacheManager MANAGER =
        CacheManagerFactory.getInstence().getCacheManager();
    
    /** 危险品车辆信息业务接口 */
    @Resource
    private IDangerVehicleService dangerVehicleService;
    
    /** dao */
    @Resource(name = "dangerVehicleDao")
    private IDangerVehicleDao dangerVehicleDao;
    
    /** terminalService */
    @Resource
    private ITerminalService terminalService;
    
    /** 附件接口 */
    @Autowired
    private IFileManageService fileManageService;
    
    /**
     * 〈一句话功能简述〉保存危险品车辆信息 〈功能详细描述〉
     * 
     * @param fileIds
     *            附件ids
     * @param form
     *            项目管理信息
     * @param bindingResult
     *            校验错误信息结果集
     * @return 返回保存信息结果
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public Object saveDangerObject(String[] fileIds,
        @Validated(value = Save.class)
        DangerVehicleForm form, BindingResult bindingResult)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, form);
        
        boolean isResult = this.addErorrs(bindingResult);
        if (isResult) {
            this.loggerFormValidate(LOGGER, form);
        }
        else {
            if (isSame()) {
                DangerVehicleEntity dangerVehicle = new DangerVehicleEntity();
                this.copyProperties(form, dangerVehicle);
                Date curDate = new Date();
                dangerVehicle.setCreateBy(this.getUser().getId());
                dangerVehicle.setUpdateBy(this.getUser().getId());
                dangerVehicle.setCreateTime(curDate);
                dangerVehicle.setUpdateTime(curDate);
                dangerVehicle.setFileIds(fileIds);
                dangerVehicle.setCorporateNo(this.getUser().getCorporateCode());
                dangerVehicle.setVehicleSrcFlag("0");
                dangerVehicle.setRemove(SystemContants.ON);
                
                Object result = this.dangerVehicleService.save(dangerVehicle);
                
                LOGGER.info(CommonConstant.SAVE_SUC_OPTION, result);
                // 保存操作日志 记录操作成功
                this.controlLog(ControlLogModel.CONTROL_RESULT_SUCCESS,
                    CommonConstant.SAVE_SUC_OPTION,
                    result).recordLog();
                
                this.sendCode(CommonConstant.SAVE_SUC_OPTION);
            }
            else {
                throw new CustomException(BizConstants.DANGERCAR_ADD, form);
            }
        }
        LOGGER.debug(SystemContants.DEBUG_END, form);
        
        return this.sendMessage();
    }
    
    /**
     * 〈一句话功能简述〉删除危险品车辆信息 〈功能详细描述〉
     * 
     * @param id
     *            危险品车辆id
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
        // 记录程序进入方法调试日志
        LOGGER.debug(SystemContants.DEBUG_START, id);
        
        if (C503StringUtils.isNotEmpty(id)) {
            if (isSame()) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("id", id);
                map.put("updateBy", this.getUser().getId());
                map.put("updateTime", new Date());
                map.put("conporateNo", this.getUser().getCorporateCode());
                
                int line = this.dangerVehicleService.delete(map);
                
                LOGGER.info(CommonConstant.DELETE_SUC_OPTION, line);
                // 保存操作日志 记录操作成功
                controlLog(ControlLogModel.CONTROL_RESULT_SUCCESS,
                    CommonConstant.DELETE_SUC_OPTION,
                    map).recordLog();
                sendCode(CommonConstant.DELETE_SUC_OPTION);
            }
            else {
                throw new CustomException(BizConstants.DANGERCAR_DELETE, id);
            }
        }
        // 传入参数无效
        else {
            this.sendCode(CommonConstant.ARGS_INVALID, id);
        }
        LOGGER.debug(SystemContants.DEBUG_END);
        
        return sendMessage();
    }
    
    /**
     * 〈一句话功能简述〉上传附件 〈功能详细描述〉
     * 
     * @param fileCode
     *            文件类型
     * @param request
     *            MultipartHttpServletRequest
     * @return success :{"code":1,msg:"上传成功",data:[ids,names]}
     *         exception:{"code":3,"msg":"xxx",data:null}
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST, produces = {"text/html;charset=utf-8"})
    @ResponseBody
    public String uploadFile(String fileCode,
        MultipartHttpServletRequest request)
        throws Exception {
        // 记录程序进入方法调试日志
        LOGGER.debug(SystemContants.DEBUG_START);
        
        Object obj = this.uploadFiles(this, request);
        if (obj instanceof HashMap) {
            this.loggerFormValidate(LOGGER, obj);
        }
        else {
            List<FileInfoEntity> fileInfoes = (List<FileInfoEntity>) obj;
            JSONObject json = new JSONObject();
            List<String> ids = new ArrayList<String>();
            List<String> names = new ArrayList<String>();
            
            if (0 < fileInfoes.size()) {
                for (FileInfoEntity fie : fileInfoes) {
                    FileInfoEntity refie =
                        this.fileManageService.saveFileAndInfo(fie);
                    ids.add(refie.getId());
                    names.add(fie.getOrgFileName());
                }
                json.put("ids", ids);
                json.put("names", names);
            }
            // 设置响应消息
            this.sendData(json, CommonConstant.UPLOAD_SUC_OPTION);
            // 记录操作成功信息
            LOGGER.info(CommonConstant.UPLOAD_SUC_OPTION, json);
            // 保存操作日志 记录操作成功
            controlLog(ControlLogModel.CONTROL_RESULT_SUCCESS,
                CommonConstant.UPLOAD_SUC_OPTION,
                json).recordLog();
            LOGGER.debug(SystemContants.DEBUG_END);
        }
        
        // return this.sendMessage();
        return JSON.toJSONString(this.sendMessage());
    }
    
    /**
     * 〈一句话功能简述〉编辑危险品车辆信息 〈功能详细描述〉
     * 
     * @param fileIds
     *            fileIds
     * @param id
     *            id
     * @param form
     *            form
     * @param bindingResult
     *            bindingResult
     * @return object
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Object update(String[] fileIds, @PathVariable
    String id, @Validated(value = Update.class)
    DangerVehicleForm form, BindingResult bindingResult)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, form);
        // 权限判断
        // this.enterpriseEx();
        boolean isResult = this.addErorrs(bindingResult);
        // 1、后台表单验证
        if (isResult) {
            this.loggerFormValidate(LOGGER, form);
        }
        else {
            
            if (isSame()) {
                // 编辑前判断该条数据是否存在
                if (null == this.dangerVehicleService.findById(id)) {
                    throw new CustomException(BizExConstants.DATAISNOTEXIST);
                }
                else {
                    // 2、实体赋值
                    DangerVehicleEntity entity = new DangerVehicleEntity();
                    this.copyProperties(form, entity);
                    entity.setId(id);
                    entity.setUpdateBy(this.getUser().getId());
                    entity.setUpdateTime(new Date());
                    entity.setFileIds(fileIds);
                    // 3、调用接口
                    this.dangerVehicleService.update(entity);
                    // 4、打印日志
                    LOGGER.info(CommonConstant.UPDATE_SUC_OPTION, entity);
                    controlLog(ControlLogModel.CONTROL_RESULT_SUCCESS,
                        CommonConstant.UPDATE_SUC_OPTION,
                        entity).recordLog();
                    sendCode(CommonConstant.UPDATE_SUC_OPTION);
                }
            }
            else {
                throw new CustomException(BizConstants.DANGERCAR_UPDATE, form);
            }
        }
        LOGGER.debug(SystemContants.DEBUG_END, form);
        return this.sendMessage();
    }
    
    /**
     * 〈一句话功能简述〉分页查询危险品车辆信息 〈功能详细描述〉
     * 
     * @param form 危险品车辆表单对象
     * @return 返回数据信息
     * @throws Exception
     *             系统异常
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/findByPage", method = RequestMethod.POST)
    @ResponseBody
    public Object findByPage(DangerVehicleForm form)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        Map<String, Object> map = form.handlePageParas();
        //map.put("vehicleSrcFlag", "0");// 仅查询车辆
        map.put("remove", SystemContants.ON);
        
        List<String> roleCode = this.getUser().getRoleCodes();
        if (!roleCode.contains(DictConstant.GOVERNMENT_USER)) {
            map.put("corporateNo", this.getUser().getCorporateCode());
        }
        
        // 2、接口调用
        List<DangerVehicleEntity> list =
            this.dangerVehicleService.findByParams(map);
        
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
     * 通过车牌号或运单号查询车辆
     * 
     * @param licencePlateNo
     *            车牌号或运单号
     * @return object
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/findByLicencePlateNoOrWayBill")
    @ResponseBody
    public Object findByLicencePlateNoOrWayBill(String licencePlateNo)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        // 1、参数设置
        licencePlateNo =
            StringUtils.isNotEmpty(licencePlateNo) ? licencePlateNo.trim()
                : licencePlateNo;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("licencePlateNo", licencePlateNo);
        map.put("remove", SystemContants.ON);
        Page pageEntity = new Page();
        pageEntity.setCurrentPage(NumberContant.ONE);
        pageEntity.setPageSize(NumberContant.FIVE);
        map.put("page", pageEntity);
        
        // 2、接口调用
        List<Map<String, Object>> list =
            this.dangerVehicleService.findDangerVehicleByParams(map);
        
        // 3、数据返回
        setJQGrid(list, pageEntity);
        LOGGER.debug(SystemContants.DEBUG_END);
        return (ResultJQGrid) this.sendMessage();
    }
    
    /**
     * 〈一句话功能简述〉查询所有车辆 〈功能详细描述〉
     * 
     * @param licenseNo
     *            licenseNo
     * @return 返回数据信息
     * @throws Exception
     *             系统异常
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/findAllVehicle", method = RequestMethod.POST)
    @ResponseBody
    public Object findAllVehicle(String licenseNo)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        // 1、参数设置
        licenseNo =
            StringUtils.isNotEmpty(licenseNo) ? licenseNo.trim() : licenseNo;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("licencePlateNo", licenseNo);
        map.put("vehicleSrcFlag", "0");// 仅查询车辆
        map.put("remove", SystemContants.ON);
        // TODO 限制企业
        /*
         * List<String> roleCode = this.getUser().getRoleCodes(); if
         * (!roleCode.contains(DictConstant.GOVERNMENT_USER)) {
         * map.put("corporateNo", this.getUser().getCorporateCode()); }
         */
        
        // 2、接口调用
        List<DangerVehicleEntity> list =
            this.dangerVehicleService.findByParams(map);
        
        // 3、数据返回
        this.sendData(list, CommonConstant.FIND_SUC_OPTION);
        LOGGER.debug(SystemContants.DEBUG_END);
        return this.sendMessage();
    }
    
    /**
     * 〈一句话功能简述〉导航栏下车辆数据 〈功能详细描述〉
     * 
     * @return 返回数据信息
     * @throws Exception
     *             系统异常
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/findLeftManueName", method = RequestMethod.POST)
    @ResponseBody
    public Object findLeftManueName()
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        // 1、参数设置
        BizUserEntity user = this.getUser();
        String corporateCode = user.getCorporateCode();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("corporateCode", corporateCode);
        // 2、接口调用
        List<DangerVehicleEntity> list =
            this.dangerVehicleDao.findLeftManueName(map);
        
        // 3、数据返回
        this.sendData(list, CommonConstant.FIND_SUC_OPTION);
        LOGGER.debug(SystemContants.DEBUG_END);
        return this.sendMessage();
    }
    
    /**
     * 〈一句话功能简述〉通过id查询危险品车辆信息 〈功能详细描述〉
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
            
            DangerVehicleEntity val = this.dangerVehicleService.findById(id);
            
            sendData(val, CommonConstant.FIND_SUC_OPTION);
        }
        // 传入参数无效
        else {
            this.sendCode(CommonConstant.ARGS_INVALID, id);
        }
        
        LOGGER.debug(SystemContants.DEBUG_END);
        
        return sendMessage();
    }
    
    // 根据车牌号（车牌号是唯一的）查询车辆信息
    @RequestMapping(value = "/findByCarrierName")
    @ResponseBody
    public Object findByCarrierName(String carrierName)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        if (C503StringUtils.isNotEmpty(carrierName)) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("carrierName", carrierName);
            DangerVehicleEntity val =
                this.dangerVehicleService.findByCarrierName(carrierName);
            // findByName方法也有缺陷，查出来的车辆的车辆类型字段已被翻译成了文字
            // DangerVehicleEntity val =
            // this.dangerVehicleService.findByName(carrierName);
            sendData(val, CommonConstant.FIND_SUC_OPTION);
        }
        // 传入参数无效
        else {
            this.sendCode(CommonConstant.ARGS_INVALID, carrierName);
        }
        
        LOGGER.debug(SystemContants.DEBUG_END);
        
        return sendMessage();
    }
    
    // 查询当前登录物流企业下所有车辆的车牌号
    @RequestMapping(value = "/findCarrierName")
    @ResponseBody
    public Object findCarrierName(String carrierName)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        List<String> list = null;
        List<String> roles = this.getUser().getRoleCodes();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("corporateNo", this.getUser().getCorporateCode());
        map.put("carrierName", carrierName);
        if (roles.contains(DictConstant.LOGISTICS_ENTERPRISE_USER)) {
            list = this.dangerVehicleService.findCarrierName(map);
        }
        this.sendData(list, CommonConstant.FIND_SUC_OPTION);
        LOGGER.debug(SystemContants.DEBUG_END);
        
        return this.sendMessage();
    }
    
    /**
     * 〈一句话功能简述〉【通过车牌号查询车辆在线还是离线】 〈功能详细描述〉
     * 
     * @param id
     *            id
     * @return 返回成功 的数据信息
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/findOnlineStatusByCarrierName")
    @ResponseBody
    public Object findOnlineStatusByCarrierName(String carrierName)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        if (C503StringUtils.isNotEmpty(carrierName)) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("carrierName", carrierName);
            
            DangerVehicleEntity val =
                this.dangerVehicleService.findByCarrierName(carrierName);
            // 0表示在线 1表示离线
            String result = "1";
            if (null != val) {
                result = val.getStatus();
            }
            sendData(result, CommonConstant.FIND_SUC_OPTION);
        }
        // 传入参数无效
        else {
            this.sendCode(CommonConstant.ARGS_INVALID, carrierName);
        }
        
        LOGGER.debug(SystemContants.DEBUG_END);
        
        return sendMessage();
    }
    
    /**
     * 〈一句话功能简述〉导出
     * 〈功能详细描述〉
     * 
     * @param enterpriseName enterpriseName
     * @param licencePlateNo licencePlateNo
     * @param crossDomainType crossDomainType
     * @param response response
     * @return outPutStream
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/export", method = RequestMethod.GET)
    @ResponseBody
    public Object export(String enterpriseName, String licencePlateNo,
        String crossDomainType, HttpServletResponse response,
        HttpServletRequest request)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        // 1、参数设置
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isNotEmpty(enterpriseName)) {
            try {
                enterpriseName = URLDecoder.decode(enterpriseName, "UTF-8");
                enterpriseName =
                    new String(enterpriseName.getBytes("ISO-8859-1"), "UTF-8");
            }
            catch (Exception e) {
                LOGGER.info(NumberContant.THREE,
                    "decode enterpriseName exception");
            }
        }
        if (StringUtils.isNotEmpty(licencePlateNo)) {
            try {
                licencePlateNo = URLDecoder.decode(licencePlateNo, "UTF-8");
                licencePlateNo =
                    new String(licencePlateNo.getBytes("ISO-8859-1"), "UTF-8");
            }
            catch (Exception e) {
                LOGGER.info(NumberContant.THREE,
                    "decode licencePlateNo exception");
            }
        }
        map.put("enterpriseName", enterpriseName);
        map.put("licencePlateNo", licencePlateNo);
        map.put("crossDomainType", crossDomainType);
        
        // 2、接口调用
        if (isSame()) {
            map.put("corporateNo", this.getUser().getCorporateCode());
        }
        List<DangerVehicleEntity> list =
            this.dangerVehicleService.findByParams(map);
        
        for (DangerVehicleEntity d : list) {
            String sta = d.getStatus();
            if (StringUtils.equals(sta, "0")) {
                d.setStatus("在线");
            }
            else {
                d.setStatus("离线");
            }
        }
        
        ExportSheet sheet = this.createVal2Excel(list);
        C503ExcelUtils excelUtils = new C503ExcelUtils();
        excelUtils.addSheet(sheet);
        OutputStream out = response.getOutputStream();
        
        String excelName = "危险品车辆信息";
        // 设置响应文本类型
        response.setContentType("application;charset=gb2312");
        // 设置响应头
        response.setHeader("Content-Disposition", "attachment;filename="
            + compatibilityFileName(request, excelName, ".xls"));
        excelUtils.writeToStream(out);
        out.close();
        response.flushBuffer();
        // 记录操作成功信息
        LOGGER.info(CommonConstant.EXPORT_SUC_OPTION, sheet);
        // 保存操作日志 记录操作成功
        controlLog(ControlLogModel.CONTROL_RESULT_SUCCESS,
            CommonConstant.EXPORT_SUC_OPTION,
            map,
            sheet).recordLog();
        LOGGER.debug(SystemContants.DEBUG_END);
        
        return null;
    }
    
    @RequestMapping(value = "/findRealLocationByCarrierName/{carrierName}", method = RequestMethod.GET)
    @ResponseBody
    public Object findRealLocationByCarrierName(@PathVariable
    String carrierName)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        /*
         * carrierName = URLDecoder.decode(carrierName, "UTF-8");
         * carrierName =
         * new String(carrierName.getBytes("ISO-8859-1"), "UTF-8");
         */
        // 1、接口调用
        LocationEntity data =
            this.dangerVehicleService.findRealLocation(carrierName);
        // 3、数据返回
        this.sendData(data, CommonConstant.FIND_SUC_OPTION);
        LOGGER.debug(SystemContants.DEBUG_END);
        return this.sendMessage();
    }
    
    /**
     * 〈一句话功能简述〉地图初始化获取所有【在线车辆】的位置信息 〈功能详细描述〉
     * 
     * @throws Exception
     *             Exception
     * @return List
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/findRealLocationAll", method = RequestMethod.POST)
    @ResponseBody
    public Object findRealLocationAll()
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        // 1、接口调用
        List<LocationEntity> list =
            this.dangerVehicleService.findRealLocationAll();
        /*//获取redis缓存中的carNo2TerminalSourceCache
        ICache<String, String> cache =
            MANAGER.getCache("carNo2TerminalSourceCache");
        // 从协议获取车辆来源
        for (LocationEntity entity : list) {
            String carrierName = entity.getCarrierName();
            //TODO: for循环里调用terminalService的接口(这个接口又去调了嘉兴位置服务平台接口)非常不好，必须优化
            TerminalEntity terminalEntity =
                terminalService.findByCarrierName(carrierName);
            String terminalSource = cache.get(carrierName);
            // 终端来源为空
            if (C503StringUtils.isEmpty(terminalSource)) {
                terminalSource = getTerminalSourceJx();
            }
            entity.setOrgin(terminalSource);
        }*/
        Map<String, Object> map = terminalService.getCarNo2TerminalSourceMap();
        Map<String, String> map2 = new HashMap<String, String>();
        if(map.size()==0){
            PageEntity<com.c503.sc.jxwl.zcpt.bean.TerminalEntity> pageEntity =
                this.terminalService.findByParams(map2);
            if (null != pageEntity && 0 < pageEntity.getRows().size()) {
                List<TerminalEntity> rows =
                    (List<TerminalEntity>) pageEntity.getRows();
                String key = null;
                String value = null;
                for(TerminalEntity entity : rows){
                    key = entity.getCarrierName();
                    value = entity.getTerminalSource();
                    if(!StringUtils.isEmpty(key)){
                        map.put(key, value);
                    }
                }
            }
        }
        // 从协议获取车辆来源
        for (LocationEntity entity : list) {
            String carrierName = entity.getCarrierName();
            //TODO: for循环里调用terminalService的接口(这个接口又去调了嘉兴位置服务平台接口)非常不好，必须优化
            /*TerminalEntity terminalEntity =
                terminalService.findByCarrierName(carrierName);*/
            String terminalSource = (String) map.get(carrierName);
            // 终端来源为空
            if (C503StringUtils.isEmpty(terminalSource)) {
                terminalSource = getTerminalSourceJx();
            }
            entity.setOrgin(terminalSource);
        }
        
        // 3、数据返回
        this.sendData(list, CommonConstant.FIND_SUC_OPTION);
        LOGGER.debug(SystemContants.DEBUG_END);
        return this.sendMessage();
    }
    
    /**
     * 〈一句话功能简述〉获取业务数据库中车牌相关的运单、驾驶员、企业、车辆信息 〈功能详细描述〉
     * 
     * @param carNo
     *            carNo
     * @throws Exception
     *             Exception
     * @return List
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/findMapData", method = RequestMethod.POST)
    @ResponseBody
    public Object findMapData(String carNo)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        Map<String, Object> result =
            this.dangerVehicleService.findMapData(carNo);
        sendData(result, CommonConstant.FIND_SUC_OPTION);
        return sendMessage();
    }
    
    /**
     * 〈一句话功能简述〉获取业务数据库中车牌相关的运单、驾驶员、企业、车辆信息 〈功能详细描述〉
     * 
     * @param minLat
     *            minLat
     * @param minLon
     *            minLon
     * @param maxLat
     *            maxLat
     * @param maxLon
     *            maxLon
     * @throws Exception
     *             Exception
     * @return object
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/getRtLocation", method = RequestMethod.POST)
    @ResponseBody
    public Object getRtLocation(String minLat, String minLon, String maxLat,
        String maxLon)
        throws Exception {
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        
        Map<String, Object> map = new HashMap<>();
        
        Page pageEntity = new Page();
        pageEntity.setCurrentPage(NumberContant.ONE);
        pageEntity.setPageSize(NumberContant.THIRTY);
        map.put("page", pageEntity);
        map.put("minLat", minLat);
        map.put("minLon", minLon);
        map.put("maxLat", maxLat);
        map.put("maxLon", maxLon);
        Map<String, Object> data = this.dangerVehicleService.getRtLocation(map);
        result.add(data);
        sendData(result, CommonConstant.FIND_SUC_OPTION);
        return sendMessage();
    }
    
    /**
     * 〈一句话功能简述〉获取所有车辆总数 〈功能详细描述〉
     * 
     * @return Object Object
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/findTotal", method = RequestMethod.POST)
    @ResponseBody
    public Object findTotal()
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        // 1、接口调用
        int total = this.dangerVehicleService.findTotal();
        
        // 3、数据返回
        this.sendData(total, CommonConstant.FIND_SUC_OPTION);
        LOGGER.debug(SystemContants.DEBUG_END);
        return this.sendMessage();
    }
    
    /**
     * 〈一句话功能简述〉获取在线车辆总数 〈功能详细描述〉
     * 
     * @return int 在线总数
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/findOnLine", method = RequestMethod.POST)
    @ResponseBody
    public Object findOnLine()
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        // 1、接口调用
        List<LocationEntity> list = this.dangerVehicleService.findAll();
        int num = 0;
        Date now = new Date();
        for (int i = 0; i < list.size(); i++) {
            Date gpsTime = list.get(i).getGpstime();
            if (gpsTime == null) {
                continue;
            }
            long time = now.getTime() - gpsTime.getTime();
            long minutes =
                time
                    / (NumberContant.ONE_THOUSAND * NumberContant.SIX * NumberContant.TEN);
            if (minutes <= NumberContant.THIRTY) {
                num++;
            }
        }
        // 3、数据返回
        this.sendData(num, CommonConstant.FIND_SUC_OPTION);
        LOGGER.debug(SystemContants.DEBUG_END);
        return this.sendMessage();
    }
    
    /**
     * 〈一句话功能简述〉获取在线车辆总数 〈功能详细描述〉
     * 
     * 
     * @return int 在线总数
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/findEnpCarForWl")
    @ResponseBody
    public Object findEnpCarForWl()
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        List<WlEnpCarVo> list = null;
        
        List<String> roles = this.getUser().getRoleCodes();
        // 政府
        if (roles.contains(DictConstant.GOVERNMENT_USER)) {
            list = this.dangerVehicleService.findEnpCarForWl(null);
            // 化工
        }
        else if (roles.contains(DictConstant.LOGISTICS_ENTERPRISE_USER)) {
            list =
                this.dangerVehicleService.findEnpCarForWl(this.getUser()
                    .getCorporateCode());
        }
        // list = this.dangerVehicleService.findEnpCarForWl(null);
        
        this.sendData(list, CommonConstant.FIND_SUC_OPTION);
        LOGGER.debug(SystemContants.DEBUG_END);
        
        return this.sendMessage();
    }
    
    @SuppressWarnings("unchecked")
    @Override
    protected <T> IBaseService<T> getBaseService() {
        return (IBaseService<T>) this.dangerVehicleService;
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
     * 〈一句话功能简述〉为Excel创建数据 〈功能详细描述〉
     * 
     * @param list
     *            终端配备情况list对象
     * @return 返回ExportSheet
     * @see [类、类#方法、类#成员]
     */
    private ExportSheet createVal2Excel(List<DangerVehicleEntity> list) {
        // 创建所需要的对象
        ExportSheet sheet = new ExportSheet();
        Header header = new Header();
        Content content = new Content();
        
        // 表头字段
        Object[] headNames =
            new Object[] {"企业名称", "法人代码", "车牌号", "车辆类型", "车辆品牌", "品牌型号",
                "车辆排量(L)", "发动机号码", "底盘号码", "车辆颜色", "跨域类型", "车辆状态"};
        header.setHeadNames(headNames);
        header.setIsShowTitle(false);
        String[] fieldNames =
            new String[] {"enterpriseName", "corporateNo", "licencePlateNo",
                "vehicleType", "vehicleBrand", "vehicleBrandType",
                "vehicleOutput", "motorNo", "graduadedNo", "vehicleColor",
                "crossDomainType", "status"};
        content.setFieldNames(fieldNames);
        
        content.setDataList(list);
        sheet.setContent(content);
        sheet.setHeader(header);
        
        return sheet;
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
     * 
     * 〈一句话功能简述〉判断该用户是否有权限编辑或者新增或者编辑（新增只有物流企业用户才有权限） 〈功能详细描述〉
     * 
     * @return 该用户是否是物流企业用户
     * @see [类、类#方法、类#成员]
     */
    private boolean isSame() {
        
        boolean isRole = false;
        List<String> userRoles = new ArrayList<String>();
        try {
            userRoles = this.getUser().getRoleCodes();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        int j = userRoles.size();
        for (int i = 0; i < j; i++) {
            if (DictConstant.LOGISTICS_ENTERPRISE_USER.equals(userRoles.get(i))) {
                isRole = true;
                break;
            }
        }
        return isRole;
        
    }
}
