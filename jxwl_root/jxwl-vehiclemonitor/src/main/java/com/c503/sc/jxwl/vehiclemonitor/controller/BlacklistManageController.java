/**
 * 文件名：BlacklistManageController.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-7-26
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.vehiclemonitor.controller;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.c503.sc.base.entity.Page;
import com.c503.sc.base.exception.CustomException;
import com.c503.sc.base.service.IBaseService;
import com.c503.sc.base.validation.Save;
import com.c503.sc.base.validation.Update;
import com.c503.sc.jxwl.common.constant.BizConstants;
import com.c503.sc.jxwl.common.constant.CommonConstant;
import com.c503.sc.jxwl.common.constant.DictConstant;
import com.c503.sc.jxwl.common.controller.ResultController;
import com.c503.sc.jxwl.vehiclemonitor.bean.BlacklistManageEntity;
import com.c503.sc.jxwl.vehiclemonitor.bean.EnterpriseEntity;
import com.c503.sc.jxwl.vehiclemonitor.bean.OccupationPersonEntity;
import com.c503.sc.jxwl.vehiclemonitor.constant.BizExConstants;
import com.c503.sc.jxwl.vehiclemonitor.dao.IBlacklistManageDao;
import com.c503.sc.jxwl.vehiclemonitor.dao.IDangerVehicleDao;
import com.c503.sc.jxwl.vehiclemonitor.dao.IEnterpriseDao;
import com.c503.sc.jxwl.vehiclemonitor.dao.IOccupationPersonDao;
import com.c503.sc.jxwl.vehiclemonitor.formbean.BlacklistManageForm;
import com.c503.sc.jxwl.vehiclemonitor.service.IBlacklistManageService;
import com.c503.sc.jxwl.vehiclemonitor.service.IEnterpriseService;
import com.c503.sc.jxwl.vehiclemonitor.vo.AlarmNumVo;
import com.c503.sc.log.ControlLogModel;
import com.c503.sc.log.LoggingManager;
import com.c503.sc.utils.basetools.C503ExcelUtils;
import com.c503.sc.utils.basetools.C503StringUtils;
import com.c503.sc.utils.common.SystemContants;
import com.c503.sc.utils.exceltools.ExportSheet;
import com.c503.sc.utils.response.ResultJQGrid;

/**
 * 
 * 〈一句话功能简述〉黑名单管理Controller 〈功能详细描述〉
 * 
 * @author guqh
 * @version [版本号, 2016-7-26]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Controller
@Scope(value = "prototype")
@RequestMapping(value = "/blacklistManage")
public class BlacklistManageController extends ResultController {
    /** 日志器 */
    private static final LoggingManager LOGGER =
        LoggingManager.getLogger(BlacklistManageController.class);
    
    /** 黑名单信息业务接口 */
    @Resource
    private IBlacklistManageService blacklistManagService;
    
    /** 物流企业信息业务接口 */
    @Resource
    private IEnterpriseService enterpriseService;
    
    /** 物流企业信息业务接口 */
    @Resource
    private IBlacklistManageDao blacklistManageDao;
    
    /** 物流企业信息Dao */
    @Resource
    private IEnterpriseDao enterpriseDao;
    
    /** 危险品车辆信息Dao */
    @Resource
    private IDangerVehicleDao dangerVehicleDao;
    
    /** 驾驶员信息Dao */
    @Resource
    private IOccupationPersonDao occupationPersonDao;
    
    /**
     * 
     * 〈一句话功能简述〉 〈功能详细描述〉保存黑名单信息
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
    BlacklistManageForm form, BindingResult bindingResult)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, form);
        boolean isResult = this.addErorrs(bindingResult);
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
            BlacklistManageEntity entity = new BlacklistManageEntity();
            if (isSame()) {
                this.copyProperties(form, entity);
                // 查询拉黑的企业信息是否存在企业信息表中
                String company = entity.getEnterpriseName();
                String code = null;
                if (company != null) {
                    List<String> codes =
                        this.enterpriseDao.findCopmanyHasExist(company);
                    if (codes.size() > 0) {
                        code = codes.get(0);
                    }
                    else {
                        throw new CustomException(BizExConstants.COMPANYNOEXIST);
                    }
                    
                }
                // 查询拉黑的车辆信息是否存在车辆信息表中
                String carNo = entity.getVehicle();
                String carCodeNo = null;
                if (carNo != null) {
                    carCodeNo =
                        this.dangerVehicleDao.findCodeByLicencePlateNo(carNo);
                    if (carCodeNo == null) {
                        throw new CustomException(
                            BizExConstants.VEHICLE_CARNOEXIST);
                    }
                }
                
                // 查询拉黑的人员信息是否存在驾驶员信息表中
                String driver = entity.getDriver();
                String identificationCardNo = null;
                String driverCodeNo = null;
                if (driver != null) {
                    List<OccupationPersonEntity> list =
                        this.occupationPersonDao.findDriverExist(driver);
                    if ((list.size() < 1) && driver != null) {
                        throw new CustomException(
                            BizExConstants.OCCUPATIONPERSON_DRIVERNOEXIST);
                    }
                    identificationCardNo =
                        list.get(0).getIdentificationCardNo();
                    driverCodeNo = list.get(0).getCorporateNo();
                }
                
                // 查询拉黑的数据是否已经存在黑名单表中
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("driver", driver);
                map.put("identificationCardNo", identificationCardNo);
                
                map.put("corporateNo", code);
                map.put("vehicle", carNo);
                map.put("objectType", entity.getObjectType());
                List<BlacklistManageEntity> list =
                    this.blacklistManageDao.findBlackExist(map);
                if ((list.size() >= 1) && (code != null)) {
                    throw new CustomException(BizExConstants.BLACK_COMPANY);
                }
                if ((list.size() >= 1) && (entity.getDriver() != null)) {
                    throw new CustomException(BizExConstants.BLACK_DRIVER);
                }
                if ((list.size() >= 1) && (entity.getVehicle() != null)) {
                    throw new CustomException(BizExConstants.IBLACK_CAR);
                }
                
                // 验证黑名单是否已经有该企业 根据法人代码在黑名单表中查询
                // String corporateNo = entity.getCorporateNo();
                /*
                 * if( this.blacklistManagService.findNumById(corporateNo) == 0
                 * ){
                 */
                entity.setBlacklistType(BizExConstants.BLACKLISTTYPE);
                entity.setId(C503StringUtils.createUUID());
                entity.setBlacklistDate(new Date());
                entity.setOperatePerson(this.getUser().getName());
                entity.setCreateBy(this.getUser().getId());
                entity.setCreateTime(new Date());
                entity.setUpdateBy(this.getUser().getId());
                entity.setUpdateTime(new Date());
                entity.setRemove(SystemContants.ON);
                if (entity.getEnterpriseName() != null) {
                    entity.setCorporateNo(code);
                }
                // 拉黑驾驶员或者车辆时保存的法人代码
                if (entity.getDriver() != null) {
                    entity.setCorporateNo(driverCodeNo);
                    entity.setIdentificationCardNo(identificationCardNo);
                }
                if (entity.getVehicle() != null) {
                    entity.setCorporateNo(carCodeNo);
                }
                // 3、调用接口信息
                this.blacklistManagService.save(entity);
                // 4、打印日志
                LOGGER.info(CommonConstant.SAVE_SUC_OPTION, entity);
                controlLog(ControlLogModel.CONTROL_RESULT_SUCCESS,
                    CommonConstant.SAVE_SUC_OPTION,
                    entity).recordLog();
                sendCode(CommonConstant.SAVE_SUC_OPTION);
                /*
                 * }else{ throw new
                 * CustomException(BizExConstants.BLACKLIST_HAS, form); }
                 */
            }
            else {
                throw new CustomException(BizConstants.ADD_BLACK_NOT_ALLOW,
                    form);
            }
        }
        LOGGER.debug(SystemContants.DEBUG_END, form);
        return this.sendMessage();
    }
    
    /**
     * 
     * 〈一句话功能简述〉 〈功能详细描述〉修改数据信息
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
    BlacklistManageForm form, BindingResult bindingResult)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, form);
        // 权限判断
        // this.enterpriseEx();
        boolean isResult = this.addErorrs(bindingResult);
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
            BlacklistManageEntity entity = new BlacklistManageEntity();
            if (isSame()) {
                this.copyProperties(form, entity);
                entity.setId(id);
                entity.setUpdateBy(this.getUser().getId());
                entity.setUpdateTime(new Date());
                entity.setOperatePerson(this.getUser().getName());
                // 3、调用接口
                this.blacklistManagService.update(entity);
                // 4、打印日志
                LOGGER.info(CommonConstant.UPDATE_SUC_OPTION, entity);
                controlLog(ControlLogModel.CONTROL_RESULT_SUCCESS,
                    CommonConstant.UPDATE_SUC_OPTION,
                    entity).recordLog();
                sendCode(CommonConstant.UPDATE_SUC_OPTION);
            }
            else {
                throw new CustomException(
                    BizConstants.ENTERPRISE_ADD_NOT_ALLOW, form);
            }
        }
        LOGGER.debug(SystemContants.DEBUG_END, form);
        return this.sendMessage();
    }
    
    /**
     * 〈一句话功能简述〉删除 〈功能详细描述〉
     * 
     * @param ids
     *            ids
     * @return object
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/delete/{ids}", method = RequestMethod.DELETE)
    @ResponseBody
    public Object delete(@PathVariable
    String... ids)
        throws Exception {
        // 政府操作异常
        LOGGER.debug(SystemContants.DEBUG_START, ids);
        if (!isSame()) {
            throw new CustomException(BizConstants.ADD_BLACK_NOT_DELETE, ids);
        }
        if (null != ids && 0 < ids.length) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("ids", ids);
            map.put("updateBy", this.getUser().getId());
            map.put("updateTime", new Date());
            
            int line = this.blacklistManagService.delete(map);
            
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
     * 〈一句话功能简述〉分页查询黑名单信息 〈功能详细描述〉
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
    public Object findByPage(BlacklistManageForm form)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        Map<String, Object> params = form.handlePageParas();
        // 1、参数设置
        params.put("objectType", form.getObjectType());
        params.put("enterpriseName", form.getEnterpriseName() == null ? null
            : form.getEnterpriseName().trim());
        params.put("driver", form.getDriver() == null ? null : form.getDriver()
            .trim());
        params.put("vehicle", form.getVehicle() == null ? null
            : form.getVehicle().trim());
        params.put("blacklistDateStart", form.getBlacklistDateStart());
        params.put("blacklistDateEnd", form.getBlacklistDateEnd());
        params.put("remove", SystemContants.ON);
        
        /*List<String> roleCode = this.getUser().getRoleCodes();
        if (!roleCode.contains(DictConstant.GOVERNMENT_USER)) {//如果账号不是政府账号，则只能查看本企业的黑名单信息
            params.put("corporateNo", this.getUser().getCorporateCode());
        }*/
        
        // 2、接口调用
        List<BlacklistManageEntity> list =
            this.blacklistManagService.findByParams(params);
        Page page = (Page) params.get("page");
        
        Page pageEntity = new Page();
        
        pageEntity.setPageSize(form.getRows());
        // 若果为政府人员可以看见所有的黑名单信息
        setJQGrid(list,
            page.getTotalCount(),
            page.getCurrentPage(),
            form.getRows(),
            CommonConstant.FIND_SUC_OPTION);
        
        LOGGER.debug(SystemContants.DEBUG_END);
        return (ResultJQGrid) this.sendMessage();
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
            
            BlacklistManageEntity val = this.blacklistManagService.findById(id);
            if (!C503StringUtils.isNotEmpty(val.getId())) {
                throw new CustomException(BizConstants.ADD_BLACK_NOT_EXIST, id);
            }
            
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
     * 〈一句话功能简述〉导出黑名单信息 〈功能详细描述〉
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
    public void exportExcel(BlacklistManageForm entity,
        HttpServletResponse response, HttpServletRequest request)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, entity);
        
        Map<String, Object> map = entity.handlePageParas();
        map.put("deptId", getUser().getDeptId());
        C503ExcelUtils excelUtils = new C503ExcelUtils();
        
        Map<String, Object> recvMap =
            this.blacklistManagService.exportExcel(map);
        ExportSheet sheet = (ExportSheet) recvMap.get("sheet");
        excelUtils.addSheet(sheet);
        OutputStream out = response.getOutputStream();
        // 设置响应文本类型
        response.setContentType("application;charset=gb2312");
        // 设置响应文件名
        String excelName = (String) recvMap.get("excelName");
        response.setHeader("Content-Disposition", "attachment;filename="
            + compatibilityFileName(request, excelName,".xls"));
        
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
    
    /**
     * 
     * 〈一句话功能简述〉 〈功能详细描述〉查询企业id及企业名称
     * 
     * @return 返回数据信息
     * @throws Exception
     *             系统异常
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/findEnterprise", method = RequestMethod.POST)
    @ResponseBody
    public Object findEnterprise()
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        // 1、参数设置
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("remove", SystemContants.ON);
        // 2、接口调用
        List<EnterpriseEntity> list = this.enterpriseService.findByParams(map);
        // 3、数据返回
        setJQGrid(list, 0, 0, 0, CommonConstant.FIND_SUC_OPTION);
        LOGGER.debug(SystemContants.DEBUG_END);
        return (ResultJQGrid) this.sendMessage();
    }
    
    /**
     * 
     * 〈一句话功能简述〉 〈功能详细描述〉提交自动拉黑设置
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
    @RequestMapping(value = "/updateAlarmNum/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Object updateAlarmNum(@PathVariable
    String id, @Validated(value = Update.class)
    AlarmNumVo form, BindingResult bindingResult)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, form);
        boolean isResult = this.addErorrs(bindingResult);
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
            if (isSame()) {
                // 2、实体赋值
                AlarmNumVo vo = new AlarmNumVo();
                this.copyProperties(form, vo);
                vo.setUpdateTime(new Date());
                vo.setId(id);
                // 3、调用接口
                this.blacklistManagService.updateAlarmNum(vo);
                // 4、打印日志
                LOGGER.info(CommonConstant.UPDATE_SUC_OPTION, vo);
                sendCode(CommonConstant.UPDATE_SUC_OPTION);
            }
            else {
                throw new CustomException(BizConstants.ADD_BLACK_NOT_ALLOW,
                    form);
            }
        }
        LOGGER.debug(SystemContants.DEBUG_END, form);
        return this.sendMessage();
    }
    
    /**
     * 〈一句话功能简述〉通过id查询 〈功能详细描述〉
     * 
     * @return object
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/findAlarmNumById", method = RequestMethod.GET)
    @ResponseBody
    public Object findAlarmNumById()
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        Map<String, Object> map = new HashMap<String, Object>();
        
        AlarmNumVo val = this.blacklistManagService.findAlarmNumById(map);
        sendData(val, CommonConstant.FIND_SUC_OPTION);
        
        LOGGER.debug(SystemContants.DEBUG_END);
        
        return sendMessage();
    }
    
    /**
     * 
     * 〈一句话功能简述〉判断该用户是否有权限编辑或者新增或者编辑（新增只有政府用户才有权限） 〈功能详细描述〉
     * 
     * @return 该用户是否是政府用户
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
            if (DictConstant.GOVERNMENT_USER.equals(userRoles.get(i))) {
                isRole = true;
                break;
            }
            
        }
        return isRole;
        
    }
    
    @SuppressWarnings("unchecked")
    @Override
    protected <T> IBaseService<T> getBaseService() {
        return (IBaseService<T>) this.blacklistManagService;
    }
    
    @Override
    protected Object show() {
        return null;
    }
    
    @Override
    protected LoggingManager logger() {
        return LOGGER;
    }
}
