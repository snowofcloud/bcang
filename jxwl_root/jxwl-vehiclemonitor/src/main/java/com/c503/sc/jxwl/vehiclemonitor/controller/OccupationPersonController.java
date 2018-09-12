/**
 * 文件名：OccupationPersonController.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-7-22
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
import com.c503.sc.jxwl.common.constant.BizConstants;
import com.c503.sc.jxwl.common.constant.CommonConstant;
import com.c503.sc.jxwl.common.constant.DictConstant;
import com.c503.sc.jxwl.common.controller.ResultController;
import com.c503.sc.jxwl.vehiclemonitor.bean.EnterpriseEntity;
import com.c503.sc.jxwl.vehiclemonitor.bean.OccupationPersonEntity;
import com.c503.sc.jxwl.vehiclemonitor.constant.BizExConstants;
import com.c503.sc.jxwl.vehiclemonitor.dao.IOccupationPersonDao;
import com.c503.sc.jxwl.vehiclemonitor.formbean.OccupationPersonForm;
import com.c503.sc.jxwl.vehiclemonitor.service.IEnterpriseService;
import com.c503.sc.jxwl.vehiclemonitor.service.IOccupationPersonService;
import com.c503.sc.log.ControlLogModel;
import com.c503.sc.log.LoggingManager;
import com.c503.sc.utils.basetools.C503ExcelUtils;
import com.c503.sc.utils.basetools.C503StringUtils;
import com.c503.sc.utils.common.SystemContants;
import com.c503.sc.utils.exceltools.Content;
import com.c503.sc.utils.exceltools.ExportSheet;
import com.c503.sc.utils.exceltools.Header;
import com.c503.sc.utils.response.ResultJQGrid;

/**
 * 
 * 〈一句话功能简述〉从业人员Controller
 * 〈功能详细描述〉
 * 
 * @author guqh
 * @version [版本号, 2016-7-22]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Controller
@Scope(value = "prototype")
@RequestMapping(value = "/employee")
public class OccupationPersonController extends ResultController {
    /** 日志器 */
    private static final LoggingManager LOGGER =
        LoggingManager.getLogger(OccupationPersonController.class);
    
    /** 从业人员信息业务接口 */
    @Resource
    private IOccupationPersonService occupationPersonService;
    
    /** 物流企业信息业务接口 */
    @Resource
    private IEnterpriseService enterpriseService;
    
    /** 从业人员信息Dao */
    @Resource
    private IOccupationPersonDao occupationPersonDao;
    
    /**
     * 
     * 〈一句话功能简述〉
     * 〈功能详细描述〉保存从业人员信息
     * 
     * @param form 实体
     * @param bindingResult 表单验证错误集合
     * @return object
     * @throws Exception 系统异常
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public Object save(
        @Validated(value = Save.class) OccupationPersonForm form,
        BindingResult bindingResult)
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
        } else {
            if (isSame()) {
                // 2、实体赋值
                OccupationPersonEntity entity = new OccupationPersonEntity();
                this.copyProperties(form, entity);
                entity.setId(C503StringUtils.createUUID());
                entity.setCreateBy(this.getUser().getId());
                entity.setCreateTime(new Date());
                entity.setUpdateBy(this.getUser().getId());
                entity.setUpdateTime(new Date());
                entity.setRemove(SystemContants.ON);
                entity.setCorporateNo(this.getUser().getCorporateCode());
                
                // 判断身份证好号码是否存在
                if (StringUtils.isNotEmpty(this.occupationPersonDao.findIdCardNoExist(entity.getIdentificationCardNo(),
                    null))) {
                    throw new CustomException(
                        BizExConstants.OCCUPATIONPERSON_IDCARDNOEXIST);
                }
                
                // 3、调用接口信息
                this.occupationPersonService.save(entity);
                // 4、打印日志
                LOGGER.info(CommonConstant.SAVE_SUC_OPTION, entity);
                controlLog(ControlLogModel.CONTROL_RESULT_SUCCESS,
                    CommonConstant.SAVE_SUC_OPTION,
                    entity).recordLog();
                sendCode(CommonConstant.SAVE_SUC_OPTION);
            } else {
                throw new CustomException(
                    BizConstants.ENTERPRISE_ADD_NOT_ALLOW, form);
            }
            
        }
        LOGGER.debug(SystemContants.DEBUG_END, form);
        return this.sendMessage();
    }
    
    /**
     * 
     * 〈一句话功能简述〉
     * 〈功能详细描述〉修改数据信息
     * 
     * @param id id
     * @param form 实体信息
     * @param bindingResult 错误集合
     * @return object
     * @throws Exception 系统异常
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Object update(@PathVariable String id,
        @Validated(value = Update.class) OccupationPersonForm form,
        BindingResult bindingResult)
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
        } else {
            if (isSame()) {
                if (null == this.occupationPersonService.findById(id)) {
                    throw new CustomException(BizExConstants.DATAISNOTEXIST);
                } else {
                    
                    // 2、实体赋值
                    OccupationPersonEntity entity =
                        new OccupationPersonEntity();
                    this.copyProperties(form, entity);
                    entity.setId(id);
                    entity.setUpdateBy(this.getUser().getId());
                    entity.setUpdateTime(new Date());
                    
                    // 判断法人代码是否存在
                    if (StringUtils.isNotEmpty(this.occupationPersonDao.findIdCardNoExist(entity.getIdentificationCardNo(),
                        entity.getId()))) {
                        throw new CustomException(
                            BizExConstants.ENTERPRISE_CONRPORATREXIST);
                    }
                    
                    // 3、调用接口
                    this.occupationPersonService.update(entity);
                    // 4、打印日志
                    LOGGER.info(CommonConstant.UPDATE_SUC_OPTION, entity);
                    controlLog(ControlLogModel.CONTROL_RESULT_SUCCESS,
                        CommonConstant.UPDATE_SUC_OPTION,
                        entity).recordLog();
                    sendCode(CommonConstant.UPDATE_SUC_OPTION);
                }
            } else {
                throw new CustomException(
                    BizConstants.ENTERPRISE_UPDATE_NOT_ALLOW, form);
            }
        }
        LOGGER.debug(SystemContants.DEBUG_END, form);
        return this.sendMessage();
    }
    
    /**
     * 〈一句话功能简述〉删除从业人员信息
     * 〈功能详细描述〉
     * 
     * @param ids ids
     * @return 响应到前台的数据
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/delete/{ids}", method = RequestMethod.DELETE)
    @ResponseBody
    public Object delete(@PathVariable String... ids)
        throws Exception {
        
        LOGGER.debug(SystemContants.DEBUG_START, ids);
        if (null != ids && 0 < ids.length) {
            if (isSame()) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("ids", ids);
                map.put("updateBy", this.getUser().getId());
                map.put("updateTime", new Date());
                // 通过ID查询驾驶员姓名
                OccupationPersonEntity val =
                    this.occupationPersonService.findById(ids[0]);
                
                String name = val.getPersonName();
                // 查询黑名单中是否存在驾驶员，如存在先删除黑名单中信息在删除从业人员中
                if (this.occupationPersonDao.findBlackListName(name) != null) {
                    this.occupationPersonDao.deleteBlackList(name);
                }
                
                int line = this.occupationPersonService.delete(map);
                LOGGER.info(CommonConstant.DELETE_SUC_OPTION, line);
                // 人员删除后 在资质管理中的人员审核状态改为已注销
                this.occupationPersonDao.updateLicenceInfo(val.getIdentificationCardNo());
                // 保存操作日志 记录操作成功
                controlLog(ControlLogModel.CONTROL_RESULT_SUCCESS,
                    CommonConstant.DELETE_SUC_OPTION,
                    map).recordLog();
                sendCode(CommonConstant.DELETE_SUC_OPTION);
            } else {
                throw new CustomException(
                    BizConstants.ENTERPRISE_DELETE_NOT_ALLOW, ids);
            }
        }
        // 传入参数无效
        else {
            this.sendCode(CommonConstant.ARGS_INVALID, (Object[]) ids);
        }
        LOGGER.debug(SystemContants.DEBUG_END);
        
        return sendMessage();
        
    }
    
    /**
     * 〈一句话功能简述〉分页查询从业人员
     * 〈功能详细描述〉
     * 
     * @param enterpriseName enterpriseName
     * @param personName personName
     * @param occupationPersonType occupationPersonType
     * @param page page
     * @param rows rows
     * @return object
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/findByPage", method = RequestMethod.POST)
    @ResponseBody
    public Object findByPage(String enterpriseName, String personName,
        String occupationPersonType, Integer page, Integer rows)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        // 1、参数设置
        enterpriseName =
            StringUtils.isNotEmpty(enterpriseName) ? enterpriseName.trim()
                : enterpriseName;
        personName =
            StringUtils.isNotEmpty(personName) ? personName.trim() : personName;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("enterpriseName", enterpriseName);
        map.put("personName", personName);
        map.put("occupationPersonType", occupationPersonType);
        map.put("remove", SystemContants.ON);
        Page pageEntity = new Page();
        pageEntity.setCurrentPage(page);
        pageEntity.setPageSize(rows);
        map.put("page", pageEntity);
        
        List<String> roleCode = this.getUser().getRoleCodes();
        if (!roleCode.contains(DictConstant.GOVERNMENT_USER)) {
            map.put("corporateNo", this.getUser().getCorporateCode());
        }
        
        // 2、接口调用
        List<OccupationPersonEntity> list =
            this.occupationPersonService.findByParams(map);
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
     * 〈一句话功能简述〉分页查询从业人员
     * 〈功能详细描述〉
     * 
     * @param personName personName
     * @return object
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/findAllDriver", method = RequestMethod.POST)
    @ResponseBody
    public Object findAllDriver(String personName)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        // 1、参数设置
        personName =
            StringUtils.isNotEmpty(personName) ? personName.trim() : personName;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("personName", personName);
        // 只查询驾驶员信息
        String type = "101001001";
        map.put("occupationPersonType", type);
        map.put("remove", SystemContants.ON);
        
        /*
         * List<String> roleCode = this.getUser().getRoleCodes();
         * if (!roleCode.contains(DictConstant.GOVERNMENT_USER)) {
         * map.put("corporateNo", this.getUser().getCorporateCode());
         * }
         */
        
        // 2、接口调用
        List<OccupationPersonEntity> list =
            this.occupationPersonService.findByParams(map);
        // 3、数据返回
        this.sendData(list, CommonConstant.FIND_SUC_OPTION);
        
        LOGGER.debug(SystemContants.DEBUG_END);
        
        return this.sendMessage();
    }
    
    /**
     * 
     * 〈一句话功能简述〉通过id查询企业信息 〈功能详细描述〉
     * 
     * @param id id
     * @return 返回成功 的数据信息
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/findById/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Object findById(@PathVariable String id)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        if (C503StringUtils.isNotEmpty(id)) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", id);
            
            OccupationPersonEntity val =
                this.occupationPersonService.findById(id);
            
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
     * 〈一句话功能简述〉
     * 〈功能详细描述〉查询企业id及企业名称
     * 
     * @return 返回数据信息
     * @throws Exception 系统异常
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
     * 〈一句话功能简述〉导出
     * 〈功能详细描述〉
     * 
     * @param enterpriseName enterpriseName
     * @param personName personName
     * @param occupationPersonType occupationPersonType
     * @param response response
     * @return object
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/export", method = RequestMethod.GET)
    @ResponseBody
    public Object export(String enterpriseName, String personName,
        String occupationPersonType, HttpServletResponse response, 
        HttpServletRequest request)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        // 1、参数设置
        if (StringUtils.isNotEmpty(enterpriseName)) {
            try {
                enterpriseName = URLDecoder.decode(enterpriseName, "UTF-8");
                enterpriseName =
                    new String(enterpriseName.getBytes("ISO-8859-1"), "UTF-8");
            } catch (Exception e) {
                LOGGER.info(NumberContant.THREE,
                    "decode enterpriseName exception");
            }
        }
        if (StringUtils.isNotEmpty(personName)) {
            try {
                personName = URLDecoder.decode(personName, "UTF-8");
                personName =
                    new String(personName.getBytes("ISO-8859-1"), "UTF-8");
            } catch (Exception e) {
                LOGGER.info(NumberContant.THREE,
                    "decode enterpriseName exception");
            }
        }
        Map<String, Object> map = new HashMap<>();
        map.put("enterpriseName", enterpriseName);
        map.put("personName", personName);
        map.put("occupationPersonType", occupationPersonType);
        
        List<String> roleCode = this.getUser().getRoleCodes();
        if (!roleCode.contains(DictConstant.GOVERNMENT_USER)) {
            map.put("corporateNo", this.getUser().getCorporateCode());
        } else {
            map.put("corporateNo", null);
        }
        // 2、接口调用
        List<OccupationPersonEntity> list;
        list = this.occupationPersonService.findByParams(map);
        
        for (OccupationPersonEntity occ : list) {
            String crime = occ.getCrimeRecord();
            String acci = occ.getTrafficAccident();
            if (StringUtils.equals("002001001", crime)) {
                occ.setCrimeRecord("有");
            } else {
                occ.setCrimeRecord("无");
            }
            if (StringUtils.equals("003001001", acci)) {
                occ.setTrafficAccident("有");
            } else {
                occ.setTrafficAccident("无");
            }
        }
        
        ExportSheet sheet = this.createVal2Excel(list);
        C503ExcelUtils excelUtils = new C503ExcelUtils();
        excelUtils.addSheet(sheet);
        OutputStream out = response.getOutputStream();
        
        String excelName = "从业人员信息";
        // 设置响应文本类型
        response.setContentType("application;charset=gb2312");
        // 设置响应头
        response.setHeader("Content-Disposition", "attachment;filename="
            + compatibilityFileName(request, excelName,".xls"));
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
    
    /**
     * 〈一句话功能简述〉为Excel创建数据
     * 〈功能详细描述〉
     * 
     * @param list 终端配备情况list对象
     * @return 返回ExportSheet
     * @see [类、类#方法、类#成员]
     */
    private ExportSheet createVal2Excel(List<OccupationPersonEntity> list) {
        // 创建所需要的对象
        ExportSheet sheet = new ExportSheet();
        Header header = new Header();
        Content content = new Content();
        
        // 表头字段
        Object[] headNames =
            new Object[] {"企业名称", "法人代码", "姓名", "从业人员类型", "性别", "民族", "文化程度",
                "身份证号码", "联系方式", "犯罪记录", "重大交通事故", "准驾车型", "从业证编号", "驾驶证编号",
                "健康状况"};
        header.setHeadNames(headNames);
        header.setIsShowTitle(false);
        String[] fieldNames =
            new String[] {"enterpriseName", "corporateNo", "personName",
                "occupationPersonType", "personSex", "nation",
                "educationDegree", "identificationCardNo", "telephone",
                "crimeRecord", "trafficAccident", "driveVehicleType",
                "occupationNo", "driveCardNo", "healthCondition"};
        content.setFieldNames(fieldNames);
        
        content.setDataList(list);
        sheet.setContent(content);
        sheet.setHeader(header);
        
        return sheet;
    }
    
    /**
     * 
     * 〈一句话功能简述〉判断该用户是否有权限编辑或者新增或者编辑（新增只有物流企业用户才有权限）
     * 〈功能详细描述〉
     * 
     * @return 该用户是否是物流企业用户
     * @see [类、类#方法、类#成员]
     */
    private boolean isSame() {
        
        boolean isRole = false;
        List<String> userRoles = new ArrayList<String>();
        try {
            userRoles = this.getUser().getRoleCodes();
        } catch (Exception e) {
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
    
    @SuppressWarnings("unchecked")
    @Override
    protected <T> IBaseService<T> getBaseService() {
        return (IBaseService<T>) this.occupationPersonService;
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
