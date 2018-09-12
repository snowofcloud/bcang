/**
 * 文件名：DangerVehicleController.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-7-20
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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.c503.sc.base.entity.Page;
import com.c503.sc.base.exception.CustomException;
import com.c503.sc.base.service.IBaseService;
import com.c503.sc.base.validation.Save;
import com.c503.sc.base.validation.Update;
import com.c503.sc.filemanage.bean.FileInfoEntity;
import com.c503.sc.filemanage.service.IFileManageService;
import com.c503.sc.jxwl.common.constant.BizConstants;
import com.c503.sc.jxwl.common.constant.CommonConstant;
import com.c503.sc.jxwl.common.constant.DictConstant;
import com.c503.sc.jxwl.common.controller.ResultController;
import com.c503.sc.jxwl.vehiclemonitor.formbean.DangerVehicleForm;
import com.c503.sc.jxwl.zcpt.bean.DangerVehicleEntity;
import com.c503.sc.jxwl.zcpt.service.IDangerVehicleService;
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
 * 〈一句话功能简述〉危险品车辆信息Controller
 * 〈功能详细描述〉
 * 
 * @author guqh
 * @version [版本号, 2016-7-20]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Controller
@Scope(value = "prototype")
@RequestMapping(value = "/vehicleSource")
public class VehicleSourceController extends ResultController {
    /** 日志器 */
    private static final LoggingManager LOGGER =
        LoggingManager.getLogger(VehicleSourceController.class);
    
    /** 危险品车辆信息业务接口 */
    @Resource
    private IDangerVehicleService dangerVehicleService;
    
    /** 附件接口 */
    @Autowired
    private IFileManageService fileManageService;
    
    /**
     * 〈一句话功能简述〉保存危险品车辆信息
     * 〈功能详细描述〉
     * 
     * @param fileIds 附件ids
     * @param form 项目管理信息
     * @param bindingResult 校验错误信息结果集
     * @return 返回保存信息结果
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public Object save(String[] fileIds,String id,  //入参增加id字段的意义：id是当前新增的车源对应的车辆在数据库表记录的id字段值
        @Validated(value = Save.class) DangerVehicleForm form,
        BindingResult bindingResult)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, form);
        
        boolean isResult = this.addErorrs(bindingResult);
        if (isResult) {
            this.loggerFormValidate(LOGGER, form);
        } else {
            if (isSame()) {
                DangerVehicleEntity dangerVehicle = new DangerVehicleEntity();
                this.copyProperties(form, dangerVehicle);
                Date curDate = new Date();
                // String[] goodstypes = form.getGoodstype().split(",");
                // String[] quantitys = form.getQuantity().split(",");
                dangerVehicle.setUpdateBy(this.getUser().getId());
                dangerVehicle.setUpdateTime(curDate);
                dangerVehicle.setFileIds(fileIds);
                dangerVehicle.setVehicleSrcFlag("1");
                dangerVehicle.setId(id);
                //Object result = this.dangerVehicleService.save(dangerVehicle);
                //新增车源信息实际上是修改数据库中已存在的某个车辆信息的某些字段，不能调用save方法，否则导致判断车牌号重复且导致企业下车辆数量计算错误
                Object result = this.dangerVehicleService.update(dangerVehicle);
                
                LOGGER.info(CommonConstant.SAVE_SUC_OPTION, result);
                // 保存操作日志 记录操作成功
                this.controlLog(ControlLogModel.CONTROL_RESULT_SUCCESS,
                    CommonConstant.SAVE_SUC_OPTION,
                    result).recordLog();
                
                this.sendCode(CommonConstant.SAVE_SUC_OPTION);
            } else {
                throw new CustomException(BizConstants.DANGERCAR_ADD, form);
            }
        }
        LOGGER.debug(SystemContants.DEBUG_END, form);
        
        return this.sendMessage();
    }
    
    /**
     * 〈一句话功能简述〉删除危险品车辆信息
     * 〈功能详细描述〉
     * 
     * @param id 危险品车辆id
     * @return 响应到前台的数据
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public Object delete(@PathVariable String id)
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
                map.put("vehicleSrcFlag", "0");
                //删除车源信息的规则：不是将车源对应的车辆信息remove置为1，而是将车源标记从‘车源’改为‘车辆’，即表示车源被删除
                int line = this.dangerVehicleService.updateVehicleSrcFlag(map);
                
                LOGGER.info(CommonConstant.DELETE_SUC_OPTION, line);
                // 保存操作日志 记录操作成功
                controlLog(ControlLogModel.CONTROL_RESULT_SUCCESS,
                    CommonConstant.DELETE_SUC_OPTION,
                    map).recordLog();
                sendCode(CommonConstant.DELETE_SUC_OPTION);
            } else {
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
     * 〈一句话功能简述〉上传附件
     * 〈功能详细描述〉
     * 
     * @param fileCode 文件类型
     * @param request MultipartHttpServletRequest
     * @return success :{"code":1,msg:"上传成功",data:[ids,names]}
     *         exception:{"code":3,"msg":"xxx",data:null}
     * @throws Exception Exception
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
        } else {
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
        
//        return this.sendMessage();
        return JSON.toJSONString(this.sendMessage());
    }
    
    /**
     * 〈一句话功能简述〉编辑危险品车辆信息
     * 〈功能详细描述〉
     * 
     * @param fileIds fileIds
     * @param id id
     * @param form form
     * @param bindingResult bindingResult
     * @return object
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Object update(String[] fileIds, @PathVariable String id,
        @Validated(value = Update.class) DangerVehicleForm form,
        BindingResult bindingResult)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, form);
        // 权限判断
        // this.enterpriseEx();
        boolean isResult = this.addErorrs(bindingResult);
        // 1、后台表单验证
        if (isResult) {
            this.loggerFormValidate(LOGGER, form);
        } else {
            if (isSame()) {
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
            } else {
                throw new CustomException(BizConstants.DANGERCAR_UPDATE, form);
            }
        }
        LOGGER.debug(SystemContants.DEBUG_END, form);
        return this.sendMessage();
    }
    
    /**
     * 〈一句话功能简述〉分页查询危险品车辆信息
     * 〈功能详细描述〉
     * 
     * @param enterpriseName enterpriseName
     * @param licenseNo licenseNo
     * @param crossDomainType crossDomainType
     * @param page page
     * @param rows rows
     * @return object
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/findByPage", method = RequestMethod.POST)
    @ResponseBody
    public Object findByPage(String enterpriseName, String licenseNo,
        String crossDomainType, Integer page, Integer rows)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        // 1、参数设置
        enterpriseName =
            StringUtils.isNotEmpty(enterpriseName) ? enterpriseName.trim()
                : enterpriseName;
        licenseNo =
            StringUtils.isNotEmpty(licenseNo) ? licenseNo.trim() : licenseNo;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("enterpriseName", enterpriseName);
        map.put("licencePlateNo", licenseNo);
        map.put("crossDomainType", crossDomainType);
        map.put("vehicleSrcFlag", "1");//仅查询车源
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
        List<DangerVehicleEntity> list =
            this.dangerVehicleService.findByParams(map);
        
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
     * 〈一句话功能简述〉查询公司名字
     * 〈功能详细描述〉
     * 
     * @return object
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/findNameByConporateNo", method = RequestMethod.POST)
    @ResponseBody
    public Object findNameByConporateNo()
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        
        String conporateNo = this.getUser().getCorporateCode();
        // 2、接口调用
        String entename = this.dangerVehicleService.findByco(conporateNo);
        sendData(entename, CommonConstant.FIND_SUC_OPTION);
        
        LOGGER.debug(SystemContants.DEBUG_END);
        return sendMessage();
    }
    
    /**
     * 〈一句话功能简述〉通过id查询危险品车辆信息
     * 〈功能详细描述〉
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
    
    /**
     * 
     * 〈一句话功能简述〉导出
     * 〈功能详细描述〉
     * 
     * @param form form
     * @param response response
     * @return object
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/export", method = RequestMethod.GET)
    @ResponseBody
    public Object export(DangerVehicleForm form, HttpServletResponse response, 
    		HttpServletRequest request)throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        // 1、参数设置
        Map<String, Object> map = form.handlePageParas();
        map.remove("page");
        // 2、接口调用
        List<DangerVehicleEntity> list =
            this.dangerVehicleService.findByParams(map);
        
        ExportSheet sheet = this.createVal2Excel(list);
        C503ExcelUtils excelUtils = new C503ExcelUtils();
        excelUtils.addSheet(sheet);
        OutputStream out = response.getOutputStream();
        
        String excelName = "危险品车辆信息";
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
    private ExportSheet createVal2Excel(List<DangerVehicleEntity> list) {
        // 创建所需要的对象
        ExportSheet sheet = new ExportSheet();
        Header header = new Header();
        Content content = new Content();
        
        // 表头字段
        Object[] headNames =
            new Object[] {"车牌号", "车辆类型", "车辆排量(L)", "发动机号码", "底盘号码", "企业名称",
                "跨域类型", "车辆状态"};
        header.setHeadNames(headNames);
        header.setIsShowTitle(false);
        String[] fieldNames =
            new String[] {"licencePlateNo", "vehicleType", "vehicleOutput",
                "motorNo", "graduadedNo", "enterpriseName", "crossDomainType",
                "status"};
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
    
}
