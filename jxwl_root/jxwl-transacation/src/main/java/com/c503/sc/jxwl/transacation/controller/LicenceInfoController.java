/**
 * 文件名：licenceInfoController.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-7-20
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.transacation.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.c503.sc.base.entity.Page;
import com.c503.sc.base.exception.CustomException;
import com.c503.sc.base.service.IBaseService;
import com.c503.sc.base.validation.Save;
import com.c503.sc.filemanage.bean.FileInfoEntity;
import com.c503.sc.filemanage.service.IFileManageService;
import com.c503.sc.jxwl.common.constant.CommonConstant;
import com.c503.sc.jxwl.common.constant.DictConstant;
import com.c503.sc.jxwl.common.controller.IFileUploadValidate;
import com.c503.sc.jxwl.common.controller.ResultController;
import com.c503.sc.jxwl.transacation.bean.LicenceEntity;
import com.c503.sc.jxwl.transacation.constant.BizExConstant;
import com.c503.sc.jxwl.transacation.formbean.LicenceForm;
import com.c503.sc.jxwl.transacation.service.ILicenceInfoService;
import com.c503.sc.jxwl.vehiclemonitor.bean.EnterpriseEntity;
import com.c503.sc.jxwl.vehiclemonitor.bean.OccupationPersonEntity;
import com.c503.sc.jxwl.zcpt.bean.DangerVehicleEntity;
import com.c503.sc.jxwl.zcpt.service.IDangerVehicleService;
import com.c503.sc.log.ControlLogModel;
import com.c503.sc.log.LoggingManager;
import com.c503.sc.utils.basetools.C503StringUtils;
import com.c503.sc.utils.common.SystemContants;
import com.c503.sc.utils.response.ResultJQGrid;

/**
 * 
 * 〈一句话功能简述〉 〈功能详细描述〉
 * 
 * @author wangwenhuan
 * @version [版本号, 2016-12-13]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Controller
@Scope(value = "prototype")
@RequestMapping(value = "/licenceInfo")
public class LicenceInfoController extends ResultController implements
    IFileUploadValidate {
    /** 日志器 */
    private static final LoggingManager LOGGER =
        LoggingManager.getLogger(LicenceInfoController.class);
    
    /** 企业资质业务接口 */
    @Resource(name = "licenceInfoService")
    private ILicenceInfoService licenceInfoService;
    
    /** 危险品车辆信息业务接口 */
    @Resource
    private IDangerVehicleService dangerVehicleService;
    
    /** 附件接口 */
    @Autowired
    private IFileManageService fileManageService;
    
    /**
     * 〈一句话功能简述〉分页查询企业资质入口 〈功能详细描述〉
     * 
     * @param page
     *            前台数据-页数
     * @param rows
     *            前台数据-每一页显示的行数
     * @param enterpriseName
     *            企业名称
     * @param licenceType
     *            资质类型
     * @param carNo
     *            车牌号
     * @param personName
     *            人名
     * @param verifyStatus
     *            状态
     * @return 返回查询的所有记录（分页数据）
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/findByPage", method = {RequestMethod.POST,
        RequestMethod.GET})
    @ResponseBody
    public Object findByPage(Integer page, Integer rows, String enterpriseName,
        String licenceType, String carNo, String personName, String verifyStatus)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        // 封装数据
        Map<String, Object> map = new HashMap<>();
        enterpriseName = enterpriseName == null ? null : enterpriseName.trim();
        licenceType = licenceType == null ? null : licenceType.trim();
        carNo = carNo == null ? null : carNo.trim();
        personName = personName == null ? null : personName.trim();
        verifyStatus = verifyStatus == null ? null : verifyStatus.trim();
        map.put("enterpriseName", enterpriseName);
        map.put("licenceType", licenceType);
        map.put("carNo", carNo);
        map.put("personName", personName);
        map.put("verifyStatus", verifyStatus);
        Page pageEntity = new Page();
        pageEntity.setCurrentPage(page);
        pageEntity.setPageSize(rows);
        map.put("page", pageEntity);
        List<String> roleList = this.getUser().getRoleCodes();
        // 权限判断
        if (!roleList.contains(DictConstant.GOVERNMENT_USER)) {// 非政府权限
            map.put("corporateNo", this.getUser().getCorporateCode());
        }
        List<LicenceEntity> list = this.licenceInfoService.findByParams(map);
        setJQGrid(list,
            pageEntity.getTotalCount(),
            page,
            rows,
            CommonConstant.FIND_SUC_OPTION);
        LOGGER.debug(SystemContants.DEBUG_END);
        
        return (ResultJQGrid) this.sendMessage();
    }
    
    /**
     * 〈一句话功能简述〉通过id查询资质信息 〈功能详细描述〉
     * 
     * @param licenceType
     *            资质类型
     * @param id
     *            id
     * @return Object
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/findById/{licenceType}/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Object findById(@PathVariable
    String licenceType, @PathVariable
    String id)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        // 数据校验
        if (C503StringUtils.isNotEmpty(id)
            || C503StringUtils.isNotEmpty(licenceType)) {
            LicenceEntity val =
                this.licenceInfoService.findById(licenceType, id);
            sendData(val, CommonConstant.FIND_SUC_OPTION);
        } // 传入参数无效
        else {
            this.sendCode(CommonConstant.ARGS_INVALID, id + ":" + licenceType);
        }
        LOGGER.debug(SystemContants.DEBUG_END);
        return sendMessage();
        
    }
    
    /**
     * 〈一句话功能简述〉保存资质信息 〈功能详细描述〉
     * 
     * @param form
     *            项目管理信息
     * @param bindingResult
     *            校验错误信息结果集
     * @return Object
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public Object upload(@Validated(value = Save.class)
    LicenceForm form, BindingResult bindingResult)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, form);
        boolean isResult = this.addErorrs(bindingResult);
        if (isResult) {
            this.loggerFormValidate(LOGGER, form);
        }
        else {
            LicenceEntity entity = new LicenceEntity();
            this.copyProperties(form, entity);
            entity.setCorporateNo(this.getUser().getCorporateCode());
            Object result = this.licenceInfoService.save(entity);
            LOGGER.info(CommonConstant.SAVE_SUC_OPTION, result);
            // 保存操作日志 记录操作成功
            this.controlLog(ControlLogModel.CONTROL_RESULT_SUCCESS,
                CommonConstant.SAVE_SUC_OPTION,
                result).recordLog();
            
            this.sendCode(CommonConstant.SAVE_SUC_OPTION);
        }
        LOGGER.debug(SystemContants.DEBUG_END, form);
        
        return this.sendMessage();
    }
    
    /**
     * 〈一句话功能简述〉更新资质信息 〈功能详细描述〉
     * 
     * @param form
     *            信息
     * @param bindingResult
     *            校验错误信息结果集
     * @return Object
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public Object update(@Validated(value = Save.class)
    LicenceForm form, BindingResult bindingResult)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, form);
        boolean isResult = this.addErorrs(bindingResult);
        if (isResult) {
            this.loggerFormValidate(LOGGER, form);
        }
        else {
            // 是否存在
            if (!this.isExit(form.getLicenceType(), form.getId())) {
                this.sendCode(CommonConstant.NOT_ACCOUNT_E);
                return sendMessage();
            }
            
            LicenceEntity entity = new LicenceEntity();
            this.copyProperties(form, entity);
            entity.setId(form.getId());
            entity.setUpdateBy(this.getUser().getId());
            entity.setUpdateTime(new Date());
            entity.setVerifyStatus(DictConstant.LICENCE_VERIFY_WAIT);
            entity.setCorporateNo(this.getUser().getCorporateCode());
            String action = "update";
            int result = this.licenceInfoService.update(entity, action);
            LOGGER.info(CommonConstant.SAVE_SUC_OPTION, result);
            // 保存操作日志 记录操作成功
            this.controlLog(ControlLogModel.CONTROL_RESULT_SUCCESS,
                CommonConstant.SAVE_SUC_OPTION,
                result).recordLog();
            
            this.sendCode(CommonConstant.SAVE_SUC_OPTION);
        }
        LOGGER.debug(SystemContants.DEBUG_END, form);
        
        return this.sendMessage();
    }
    
    /**
     * 〈一句话功能简述〉更新资质信息 〈功能详细描述〉
     * 
     * @param form
     *            信息
     * @param bindingResult
     *            校验错误信息结果集
     * @return Object
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/verify", method = RequestMethod.POST)
    @ResponseBody
    public Object verify(@Validated(value = Save.class)
    LicenceForm form, BindingResult bindingResult)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, form);
        boolean isResult = this.addErorrs(bindingResult);
        if (isResult) {
            this.loggerFormValidate(LOGGER, form);
        }
        else {
            LicenceEntity entity = new LicenceEntity();
            entity.setUpdateBy(this.getUser().getId());
            entity.setUpdateTime(new Date());
            entity.setVerifyStatus(form.getVerifyStatus());
            entity.setLicenceType(form.getLicenceType());
            if (form.getVerifyStatus()
                .equals(DictConstant.LICENCE_VERIFY_REJECT)) {
                entity.setRejectReason(form.getRejectReason());
            }
            
            // 是否存在
            if (!this.isExit(form.getLicenceType(), form.getId())) {
                this.sendCode(CommonConstant.NOT_ACCOUNT_E);
                return sendMessage();
            }
            // 是否已审核过
            LicenceEntity val =
                this.licenceInfoService.findById(form.getLicenceType(),
                    form.getId());
            if (!val.getVerifyStatus().equals(DictConstant.LICENCE_VERIFY_WAIT)) {
                throw new CustomException(BizExConstant.INFORMATION_VERIFIED);
            }
            entity.setId(form.getId());
            String action = "verify";
            int result = this.licenceInfoService.update(entity, action);
            LOGGER.info(CommonConstant.SAVE_SUC_OPTION, result);
            // 保存操作日志 记录操作成功
            this.controlLog(ControlLogModel.CONTROL_RESULT_SUCCESS,
                CommonConstant.SAVE_SUC_OPTION,
                result).recordLog();
            
            this.sendCode(CommonConstant.SAVE_SUC_OPTION);
        }
        LOGGER.debug(SystemContants.DEBUG_END, form);
        
        return this.sendMessage();
    }
    
    /**
     * 〈一句话功能简述〉删除资质信息 〈功能详细描述〉
     * 
     * @param licenceType
     *            licenceType
     * @param id
     *            企业id
     * @return 响应到前台的数据
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/delete/{licenceType}/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public Object delete(@PathVariable
    String licenceType, @PathVariable
    String id)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        // 1.参数校验
        if (C503StringUtils.isNotEmpty(id)) {
            // 是否存在
            if (!this.isExit(licenceType, id)) {
                this.sendCode(CommonConstant.NOT_ACCOUNT_E);
                return sendMessage();
            }
            int line = this.licenceInfoService.delete(id);
            LOGGER.info(CommonConstant.DELETE_SUC_OPTION, line);
            // 保存操作日志 记录操作成功
            controlLog(ControlLogModel.CONTROL_RESULT_SUCCESS,
                CommonConstant.DELETE_SUC_OPTION,
                id).recordLog();
            sendCode(CommonConstant.DELETE_SUC_OPTION);
        }
        // 传入参数无效
        else {
            this.sendCode(CommonConstant.ARGS_INVALID, id);
        }
        LOGGER.debug(SystemContants.DEBUG_END);
        
        return sendMessage();
    }
    
    /**
     * 〈一句话功能简述〉联想查询人员 〈功能详细描述〉
     * 
     * @param personName
     *            personName
     * @return Object
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/findPerson", method = RequestMethod.POST)
    @ResponseBody
    public Object findPerson(String personName)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        // 1.参数校验
        if (C503StringUtils.isNotEmpty(personName)) {
            Map<String, Object> map = new HashMap<>();
            map.put("personName", personName);
            map.put("corporateNo", this.getUser().getCorporateCode());
            List<OccupationPersonEntity> list =
                this.licenceInfoService.findPerson(map);
            LOGGER.info(CommonConstant.FIND_SUC_OPTION, list);
            sendData(list, CommonConstant.FIND_SUC_OPTION);
        }
        // 传入参数无效
        else {
            this.sendCode(CommonConstant.ARGS_INVALID, personName);
        }
        LOGGER.debug(SystemContants.DEBUG_END);
        
        return sendMessage();
    }
    
    /**
     * 〈一句话功能简述〉联想查询车辆 〈功能详细描述〉
     * 
     * @param licencePlateNo
     *            licencePlateNo
     * @return Object
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/findVehicle", method = RequestMethod.POST)
    @ResponseBody
    public Object findVehicle(String licencePlateNo)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        // 1.参数校验
        if (C503StringUtils.isNotEmpty(licencePlateNo)) {
            Map<String, Object> map = new HashMap<>();
            map.put("licencePlateNo", licencePlateNo);
            map.put("corporateNo", this.getUser().getCorporateCode());
            List<DangerVehicleEntity> list =
                this.dangerVehicleService.findVehicle(map);
            LOGGER.info(CommonConstant.FIND_SUC_OPTION, list);
            sendData(list, CommonConstant.FIND_SUC_OPTION);
        }
        // 传入参数无效
        else {
            this.sendCode(CommonConstant.ARGS_INVALID, licencePlateNo);
        }
        LOGGER.debug(SystemContants.DEBUG_END);
        
        return sendMessage();
    }
    
    /**
     * 〈一句话功能简述〉联想查询企业 〈功能详细描述〉
     * 
     * @return Object
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/findEnterprise", method = RequestMethod.GET)
    @ResponseBody
    public Object findEnterprise()
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        // 1.参数校验
        Map<String, Object> map = new HashMap<>();
        map.put("corporateNo", this.getUser().getCorporateCode());
        List<EnterpriseEntity> list =
            this.licenceInfoService.findEnterprise(map);
        LOGGER.info(CommonConstant.FIND_SUC_OPTION, list);
        sendData(list, CommonConstant.FIND_SUC_OPTION);
        // 传入参数无效
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
        
        Object obj = this.uploadFiles(this.getFileUploadService(), request);
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
     * 
     * 〈一句话功能简述〉公共函数用于判断是否存在〈功能详细描述〉
     * 
     * @param licenceType licenceType
     * @param id id
     * @return boolean
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    public boolean isExit(String licenceType, String id)
        throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("licenceType", licenceType);
        map.put("id", id);
        return this.licenceInfoService.isExit(map);
    }
    
    /**
     * 〈一句话功能简述〉实现文件上传验证的接口 〈功能详细描述〉
     * 
     * @return 当前对象作为实现类
     * @see [类、类#方法、类#成员]
     */
    private IFileUploadValidate getFileUploadService() {
        return this;
    }
    
    @Override
    protected Object show() {
        return null;
    }
    
    @Override
    protected LoggingManager logger() {
        return LOGGER;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    protected <T> IBaseService<T> getBaseService() {
        return (IBaseService<T>) this.licenceInfoService;
    }
}
