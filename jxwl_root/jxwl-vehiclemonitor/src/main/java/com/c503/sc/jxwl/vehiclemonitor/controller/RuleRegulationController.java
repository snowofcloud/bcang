/**
 * 文件名：RuleRegulationController.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-8-4
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.vehiclemonitor.controller;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
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
import com.c503.sc.jxwl.vehiclemonitor.bean.RuleRegulationEntity;
import com.c503.sc.jxwl.vehiclemonitor.constant.BizExConstants;
import com.c503.sc.jxwl.vehiclemonitor.formbean.RuleRegulationForm;
import com.c503.sc.jxwl.vehiclemonitor.service.IRuleRegulationService;
import com.c503.sc.log.ControlLogModel;
import com.c503.sc.log.LoggingManager;
import com.c503.sc.utils.basetools.C503DateUtils;
import com.c503.sc.utils.basetools.C503ExcelUtils;
import com.c503.sc.utils.basetools.C503StringUtils;
import com.c503.sc.utils.common.SystemContants;
import com.c503.sc.utils.exceltools.Content;
import com.c503.sc.utils.exceltools.ExportSheet;
import com.c503.sc.utils.exceltools.Header;
import com.c503.sc.utils.response.ResultJQGrid;

/**
 * 〈一句话功能简述〉规章制度Controller
 * 〈功能详细描述〉
 * 
 * @author xiaoqx
 * @version [版本号, 2016-8-8]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Controller
@Scope(value = "prototype")
@RequestMapping(value = "/rulesRegulations")
public class RuleRegulationController extends ResultController {
    
    /** 日志器 */
    private static final LoggingManager LOGGER =
        LoggingManager.getLogger(EmergencyInfoController.class);
    
    /** 规章制度业务接口 */
    @Resource(name = "ruleRegulationService")
    private IRuleRegulationService ruleRegulationService;
    
    /**
     * 〈一句话功能简述〉分页查询规章制度信息
     * 〈功能详细描述〉
     * 
     * @param form form
     * @return Object
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/findByPage", method = RequestMethod.POST)
    @ResponseBody
    public Object findByPage(RuleRegulationForm form)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        // 1、参数设置
        Map<String, Object> map = form.handlePageParas();
        
        // 2、接口调用
        List<RuleRegulationEntity> list =
            this.ruleRegulationService.findByParams(map);
        // 3、数据返回
        Page page = (Page) map.get("page");
        setJQGrid(list,
            page.getTotalCount(),
            page.getCurrentPage(),
            page.getPageSize(),
            CommonConstant.FIND_SUC_OPTION);
        LOGGER.debug(SystemContants.DEBUG_END);
        return (ResultJQGrid) this.sendMessage();
    }
    
    /**
     * 〈一句话功能简述〉保存规章制度信息
     * 〈功能详细描述〉
     * 
     * @param form form
     * @param bindingResult 校验错误信息结果集
     * @return Object
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public Object save(@Validated(value = Save.class)
    RuleRegulationForm form, BindingResult bindingResult)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, form);
        boolean isResult = this.addErorrs(bindingResult);
        // 1、后台表单验证
        if (isResult) {
            this.loggerFormFail(form);
        }
        else {
            if (isSame()) {
                // 2、实体赋值
                RuleRegulationEntity ruleRegulation =
                    new RuleRegulationEntity();
                this.copyProperties(form, ruleRegulation);
                ruleRegulation.setId(C503StringUtils.createUUID());
                ruleRegulation.setCreateBy(this.getUser().getId());
                ruleRegulation.setCreateTime(new Date());
                ruleRegulation.setUpdateBy(this.getUser().getId());
                ruleRegulation.setUpdateTime(new Date());
                ruleRegulation.setRemove(SystemContants.ON);
                
                // 3、调用接口信息
                this.ruleRegulationService.save(ruleRegulation);
                
                // 4、打印日志
                LOGGER.info(CommonConstant.SAVE_SUC_OPTION, ruleRegulation);
                controlLog(ControlLogModel.CONTROL_RESULT_SUCCESS,
                    CommonConstant.SAVE_SUC_OPTION,
                    ruleRegulation).recordLog();
                this.sendCode(CommonConstant.SAVE_SUC_OPTION);
            }
            else {
                throw new CustomException(BizConstants.GOVERNMENT_ADD_ALLOW,
                    form);
            }
        }
        LOGGER.debug(SystemContants.DEBUG_END, form);
        
        return this.sendMessage();
    }
    
    /**
     * 
     * 〈一句话功能简述〉通过id查询规章制度信息
     * 〈功能详细描述〉
     * 
     * @param id id
     * @return 返回成功 的数据信息
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/findById/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Object findById(@PathVariable
    String id)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        if (C503StringUtils.isNotEmpty(id)) {
            RuleRegulationEntity val = this.ruleRegulationService.findById(id);
            sendData(val, CommonConstant.FIND_SUC_OPTION);
        } // 传入参数无效
        else {
            this.sendCode(CommonConstant.ARGS_INVALID, id);
        }
        LOGGER.debug(SystemContants.DEBUG_END);
        return sendMessage();
    }
    
    /**
     * 〈一句话功能简述〉修改规章制度信息
     * 〈功能详细描述〉
     * 
     * @param id id
     * @param form 能管员信息
     * @param bindingResult 验错误信息结果集
     * @return 返回修改结果信息
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Object update(@PathVariable
    String id, @Validated(value = Update.class)
    RuleRegulationForm form, BindingResult bindingResult)
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
                
              //编辑前判断该条数据是否存在
                if(null == this.ruleRegulationService.findById(id)){
                    throw new CustomException(BizExConstants.DATAISNOTEXIST);
                }
                
                if (isSame()) {
                    
                    RuleRegulationEntity ruleRegulation =
                        new RuleRegulationEntity();
                    this.copyProperties(form, ruleRegulation);
                    ruleRegulation.setId(id);
                    ruleRegulation.setUpdateTime(new Date());
                    ruleRegulation.setUpdateBy(this.getUser().getId());
                    ruleRegulation.setRemove(SystemContants.ON);
                    
                    this.ruleRegulationService.update(ruleRegulation);
                    
                    LOGGER.info(CommonConstant.UPDATE_SUC_OPTION,
                        ruleRegulation);
                    controlLog(ControlLogModel.CONTROL_RESULT_SUCCESS,
                        CommonConstant.UPDATE_SUC_OPTION,
                        ruleRegulation).recordLog();
                    sendCode(CommonConstant.UPDATE_SUC_OPTION);
                }
                else {
                    throw new CustomException(
                        BizConstants.GOVERNMENT_UPDATE_ALLOW, id);
                }
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
     * 
     * 〈一句话功能简述〉 删除规章制度信息
     * 〈功能详细描述〉
     * 
     * @param id id
     * @return object
     * @throws Exception 系统异常
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public Object delete(@PathVariable
    String id)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, id);
        
      //删除前判断该条数据是否存在
        if(null == this.ruleRegulationService.findById(id)){
            throw new CustomException(BizExConstants.DATAISNOTEXIST);
        }
        
        if (isSame()) {
            // 1、实体赋值
            RuleRegulationEntity entity = new RuleRegulationEntity();
            entity.setId(id);
            entity.setUpdateBy(this.getUser().getId());
            entity.setUpdateTime(new Date());
            // 2、调用接口
            this.ruleRegulationService.delete(entity);
            // 3、打印日志
            LOGGER.info(CommonConstant.UPDATE_SUC_OPTION, entity);
            controlLog(ControlLogModel.CONTROL_RESULT_SUCCESS,
                CommonConstant.UPDATE_SUC_OPTION,
                entity).recordLog();
            sendCode(CommonConstant.UPDATE_SUC_OPTION);
        }
        else {
            throw new CustomException(BizConstants.GOVERNMENT_DELETE_ALLOW, id);
        }
        
        LOGGER.debug(SystemContants.DEBUG_END, id);
        return this.sendMessage();
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
    public Object export(RuleRegulationForm form, HttpServletResponse response, 
    		HttpServletRequest request)throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        // 1、参数设置
        Map<String, Object> map = form.handlePageParas();
        map.remove("page");
        // 2、接口调用
        List<RuleRegulationEntity> list =
            this.ruleRegulationService.findByParams(map);
        
        ExportSheet sheet = this.createVal2Excel(list);
        C503ExcelUtils excelUtils = new C503ExcelUtils();
        excelUtils.addSheet(sheet);
        OutputStream out = response.getOutputStream();
        
        String excelName = "规章制度";
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
     * @param eqlist 终端配备情况list对象
     * @return 返回ExportSheet
     * @see [类、类#方法、类#成员]
     */
    private ExportSheet createVal2Excel(List<RuleRegulationEntity> eqlist) {
        // 创建所需要的对象
        ExportSheet sheet = new ExportSheet();
        // Title title = new Title();
        Header header = new Header();
        Content content = new Content();
        
        // 表头字段
        Object[] headNames = new Object[] {"编号", "名称", "内容", "签发单位", "发布时间"};
        // title.setTitleName("通讯录");
        // sheet.setSheetName("第一个工作薄");
        header.setHeadNames(headNames);
        header.setIsShowTitle(false);
        String[] fieldNames =
            new String[] {"identifier", "ruleName", "sendContent", "company",
                "sendDateBak"};
        content.setFieldNames(fieldNames);
        
        int size = eqlist.size();
        for (int i = 0; i < size; i++) {
            RuleRegulationEntity eq = eqlist.get(i);
            eq.setSendDateBak(C503DateUtils.dateToStr(eq.getSendDate(),
                "yyyy-MM-dd"));
        }
        
        content.setDataList(eqlist);
        sheet.setContent(content);
        sheet.setHeader(header);
        
        return sheet;
    }
    
    /**
     * 〈一句话功能简述〉
     * 〈功能详细描述〉
     * 
     * @param object object
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    private void loggerFormFail(Object object)
        throws Exception {
        this.sendData(getValidErorrs(), CommonConstant.FORMVALID_FAIL_OPTION);
        // 记录操作失败信息（存入文件）
        LOGGER.info(CommonConstant.FORMVALID_FAIL_OPTION, object);
        // 保存操作日志 记录操作失败（存入数据库）
        this.controlLog(ControlLogModel.CONTROL_RESULT_FAIL,
            CommonConstant.FORMVALID_FAIL_OPTION,
            getValidErorrs())
            .setErrorMessage(CommonConstant.FORMVALID_FAIL_OPTION)
            .recordLog();
    }
    
    /**
     * 
     * 〈一句话功能简述〉判断该用户是否有权限编辑或者新增或者编辑（新增只有政府用户才有权限）
     * 〈功能详细描述〉
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
    
    @Override
    protected <T> IBaseService<T> getBaseService() {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    protected Object show() {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    protected LoggingManager logger() {
        return LOGGER;
    }
    
}
