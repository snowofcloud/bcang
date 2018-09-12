/**
 * 文件名：EnterpriseController.java
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
import org.springframework.web.bind.annotation.ResponseBody;

import com.c503.sc.base.entity.Page;
import com.c503.sc.base.exception.CustomException;
import com.c503.sc.base.service.IBaseService;
import com.c503.sc.base.validation.Save;
import com.c503.sc.base.validation.Update;
import com.c503.sc.jxwl.common.constant.CommonConstant;
import com.c503.sc.jxwl.common.constant.DictConstant;
import com.c503.sc.jxwl.common.controller.IFileUploadValidate;
import com.c503.sc.jxwl.common.controller.ResultController;
import com.c503.sc.jxwl.transacation.bean.WayBillGoodsTempEntity;
import com.c503.sc.jxwl.transacation.bean.WayBillTempEntity;
import com.c503.sc.jxwl.transacation.constant.BizExConstant;
import com.c503.sc.jxwl.transacation.formbean.WayBillGoodsTempForm;
import com.c503.sc.jxwl.transacation.formbean.WayBillTempForm;
import com.c503.sc.jxwl.transacation.service.IWayBillGoodsTempService;
import com.c503.sc.jxwl.transacation.service.IWayBillTempService;
import com.c503.sc.jxwl.vehiclemonitor.bean.CarLocationEntity;
import com.c503.sc.log.ControlLogModel;
import com.c503.sc.log.LoggingManager;
import com.c503.sc.utils.basetools.C503DateUtils;
import com.c503.sc.utils.basetools.C503StringUtils;
import com.c503.sc.utils.common.SystemContants;
import com.c503.sc.utils.response.ResultJQGrid;

/**
 * 
 * 〈一句话功能简述〉模板信息Controller
 * 〈功能详细描述〉
 * 
 * @author wl
 * @version [版本号, 2016-08-30]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Controller
@Scope(value = "prototype")
@RequestMapping(value = "/wayBillTemp")
public class WayBillTempController extends ResultController implements
    IFileUploadValidate {
    /** 日志器 */
    private static final LoggingManager LOGGER =
        LoggingManager.getLogger(WayBillTempController.class);
    
    /** 运单信息业务接口 */
    @Resource
    private IWayBillTempService wayBillTempService;
    
    /** 运单信息---货物业务接口 */
    @Resource
    private IWayBillGoodsTempService wayBillGoodsTempService;
    
    /**
     * 〈一句话功能简述〉分页查询模板信息
     * 〈功能详细描述〉
     * 
     * @param checkstatus checkstatus
     * @param startTime startTime
     * @param endTime endTime
     * @param page page
     * @param rows rows
     * @return object
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/findByPage", method = RequestMethod.POST)
    @ResponseBody
    public Object findByPage(String checkstatus,
        @DateTimeFormat(pattern = "yyyy-MM-dd", iso = ISO.DATE)
        Date startTime, @DateTimeFormat(pattern = "yyyy-MM-dd", iso = ISO.DATE)
        Date endTime, Integer page, Integer rows)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        // 1、参数设置
        Map<String, Object> map = new HashMap<String, Object>();
        checkstatus = checkstatus == null ? null : checkstatus.trim();
        map.put("checkstatus", checkstatus);
        map.put("startTime", startTime);
        endTime = endTime == null ? null : C503DateUtils.getDay(1, endTime);
        map.put("endTime", endTime);
        Page pageEntity = new Page();
        pageEntity.setCurrentPage(page);
        pageEntity.setPageSize(rows);
        map.put("page", pageEntity);
        List<String> roleCodes = this.getUser().getRoleCodes();
        String corporateCode = this.getUser().getCorporateCode();
        if (roleCodes.contains(DictConstant.CHEMICAL_ENTERPRISE_USER)) {
            // 化工
            map.put("chemicalId", corporateCode);
        }
        else if (roleCodes.contains(DictConstant.LOGISTICS_ENTERPRISE_USER)) {
            // 物流
            map.put("logisticsId", corporateCode);
        }
        
        // 2、接口调用
        List<WayBillTempEntity> list =
            this.wayBillTempService.findByParams(map);
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
     * 〈一句话功能简述〉
     * 〈功能详细描述〉保存模板信息
     * 
     * @param form 实体
     * @param bindingResult 表单验证错误集合
     * @return object
     * @throws Exception 系统异常
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public Object save(@Validated(value = Save.class)
    WayBillTempForm form, BindingResult bindingResult)
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
            WayBillTempEntity entity = new WayBillTempEntity();
            this.copyProperties(form, entity);
            String returnId = C503StringUtils.createUUID();
            entity.setId(returnId);
            entity.setCreateBy(this.getUser().getId());
            entity.setCreateTime(new Date());
            entity.setUpdateBy(this.getUser().getId());
            entity.setUpdateTime(new Date());
            entity.setRemove(SystemContants.ON);
            entity.setChemicalId(this.getUser().getCorporateCode());
            // 3、调用接口信息
            this.wayBillTempService.save(entity);
            // 4、打印日志
            LOGGER.info(CommonConstant.SAVE_SUC_OPTION, entity);
            sendData(returnId, CommonConstant.SAVE_SUC_OPTION);
        }
        LOGGER.debug(SystemContants.DEBUG_END, form);
        return this.sendMessage();
    }
    
    /**
     * 〈一句话功能简述〉
     * 〈功能详细描述〉 保存运单信息
     * 
     * @param id id
     * @param form 实体
     * @param bindingResult 表单验证错误集合
     * @return Object
     * @throws Exception 系统异常
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/saveWayBill/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Object saveWayBill(@PathVariable
    String id, @Validated(value = Save.class)
    WayBillTempForm form, BindingResult bindingResult)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, form);
        boolean isResult = this.addErorrs(bindingResult);
        if (isResult) {
            this.loggerFormValidate(LOGGER, form);
        }
        else {
            // 2、实体赋值
            WayBillTempEntity entity = new WayBillTempEntity();
            this.copyProperties(form, entity);
            entity.setId(id);
            entity.setChemicalId(this.getUser().getCorporateCode());
            
            // 3、调用接口信息
            this.wayBillTempService.saveWayBill(entity);
            // 4、打印日志
            LOGGER.info(CommonConstant.SAVE_SUC_OPTION, entity);
            sendCode(CommonConstant.SAVE_SUC_OPTION);
        }
        LOGGER.debug(SystemContants.DEBUG_END, form);
        return this.sendMessage();
    }
    
    /**
     * 〈一句话功能简述〉删除货物信息
     * 〈功能详细描述〉
     * 
     * @param id id
     * @return object
     * @throws Exception Exception
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
            
            int line = this.wayBillTempService.delete(map);
            
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
     * 〈一句话功能简述〉修改模板信息
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
    WayBillTempForm form, BindingResult bindingResult)
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
                if (null == this.wayBillTempService.findById(id)) {
                    throw new CustomException(BizExConstant.WAYBILLTEMP_NOEXIST);
                }
                
                WayBillTempEntity ruleRegulation = new WayBillTempEntity();
                this.copyProperties(form, ruleRegulation);
                ruleRegulation.setId(id);
                ruleRegulation.setUpdateTime(new Date());
                ruleRegulation.setUpdateBy(this.getUser().getId());
                ruleRegulation.setRemove(SystemContants.ON);
                
                this.wayBillTempService.update(ruleRegulation);
                
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
     * 
     * 〈一句话功能简述〉通过id查询模板信息
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
            WayBillTempEntity data = this.wayBillTempService.findById(id);
            sendData(data, CommonConstant.FIND_SUC_OPTION);
        } // 传入参数无效
        else {
            this.sendCode(CommonConstant.ARGS_INVALID, id);
        }
        LOGGER.debug(SystemContants.DEBUG_END);
        return sendMessage();
    }
    
    /**
     * 〈一句话功能简述〉分页查询运单---货物
     * 〈功能详细描述〉
     * 
     * @param page page
     * @param rows rows
     * @param id id
     * @return object
     * @throws Exception Exception
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
        List<WayBillGoodsTempEntity> list =
            this.wayBillGoodsTempService.findByParams(map);
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
     * 〈一句话功能简述〉保存物流企业信息
     * 〈功能详细描述〉
     * 
     * @param waybillTempId waybillTempId
     * @param form WayBillGoodsTempForm
     * @param bindingResult BindingResult
     * @return Object
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/goodsSave", method = RequestMethod.POST)
    @ResponseBody
    public Object goodsSave(String waybillTempId,
        @Validated(value = Save.class)
        WayBillGoodsTempForm form, BindingResult bindingResult)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, form);
        boolean isResult = this.addErorrs(bindingResult);
        if (isResult) {
            this.loggerFormValidate(LOGGER, form);
        }
        else {
            if (StringUtils.isNotEmpty(waybillTempId)) {
                // 2、实体赋值
                WayBillGoodsTempEntity entity = new WayBillGoodsTempEntity();
                this.copyProperties(form, entity);
                entity.setWaybillTempId(waybillTempId);
                this.createCommonVal(entity, true, this.getUser().getId());
                // 3、调用接口信息
                this.wayBillGoodsTempService.save(entity);
                // 4、打印日志
                LOGGER.info(CommonConstant.SAVE_SUC_OPTION, entity);
                sendCode(CommonConstant.SAVE_SUC_OPTION);
            }
            else {
                this.sendCode(CommonConstant.ARGS_INVALID, "waybillTempId");
            }
        }
        LOGGER.debug(SystemContants.DEBUG_END, form);
        return this.sendMessage();
    }
    
    /**
     * 〈一句话功能简述〉删除货物信息
     * 〈功能详细描述〉
     * 
     * @param ids ids
     * @return 响应到前台的数据
     * @throws Exception Exception
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
            
            int line = this.wayBillGoodsTempService.delete(map);
            
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
     * 〈一句话功能简述〉通过id查询货物信息
     * 〈功能详细描述〉
     * 
     * @param id id
     * @return 返回成功 的数据信息
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/goodsFindById/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Object goodsfindById(@PathVariable
    String id)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        if (C503StringUtils.isNotEmpty(id)) {
            WayBillGoodsTempEntity data =
                this.wayBillGoodsTempService.findById(id);
            sendData(data, CommonConstant.FIND_SUC_OPTION);
        } // 传入参数无效
        else {
            this.sendCode(CommonConstant.ARGS_INVALID, id);
        }
        LOGGER.debug(SystemContants.DEBUG_END);
        return sendMessage();
    }
    
    /**
     * 〈一句话功能简述〉修改货物信息
     * 〈功能详细描述〉
     * 
     * @param id id
     * @param form 能管员信息
     * @param bindingResult 验错误信息结果集
     * @return 返回修改结果信息
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/goodsupdate/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Object goodsupdate(@PathVariable
    String id, @Validated(value = Update.class)
    WayBillGoodsTempForm form, BindingResult bindingResult)
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
                if (null == this.wayBillGoodsTempService.findById(id)) {
                    throw new CustomException(BizExConstant.WAYBILL_GOODS_EXIST);
                }
                
                WayBillGoodsTempEntity ruleRegulation =
                    new WayBillGoodsTempEntity();
                this.copyProperties(form, ruleRegulation);
                ruleRegulation.setId(id);
                ruleRegulation.setUpdateTime(new Date());
                ruleRegulation.setUpdateBy(this.getUser().getId());
                ruleRegulation.setRemove(SystemContants.ON);
                
                this.wayBillGoodsTempService.update(ruleRegulation);
                
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
     * 〈一句话功能简述〉查询运输货物的车辆的信息
     * 〈功能详细描述〉
     * 
     * @param carno carno
     * @param page page
     * @param rows rows
     * @return object
     * @throws Exception Exception
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
        List<WayBillTempEntity> list =
            this.wayBillTempService.findVehicleData(map);
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
     * 〈一句话功能简述〉通过车牌号查询车辆信息
     * 〈功能详细描述〉
     * 
     * @param carrierName carrierName
     * @return CarLocationEntity
     * @throws Exception Exception
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
                this.wayBillTempService.findByCarrierName(carrierName);
            sendData(data, CommonConstant.FIND_SUC_OPTION);
        } // 传入参数无效
        else {
            this.sendCode(CommonConstant.ARGS_INVALID, carrierName);
        }
        LOGGER.debug(SystemContants.DEBUG_END);
        return sendMessage();
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
    @RequestMapping(value = "/isExistTempName", method = RequestMethod.POST)
    @ResponseBody
    public Object isExistTempName(String tempName)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, tempName);
        if (null != tempName) {
            boolean flag = this.wayBillTempService.isExistTempName(tempName);
            
            sendData(flag, CommonConstant.FIND_SUC_OPTION);
        }
        else {
            this.sendCode(CommonConstant.ARGS_INVALID, tempName);
        }
        // 4、打印日志
        LOGGER.info(CommonConstant.FIND_SUC_OPTION, tempName);
        return this.sendMessage();
    }
    
    @RequestMapping(value = "/placeAndPoi", method = {RequestMethod.POST,
    		RequestMethod.GET} )
    @ResponseBody
    public Object placeAndPoi(String orderNo)
        throws Exception {
    	List<Map<String, Object>> list = null;
        LOGGER.debug(SystemContants.DEBUG_START, orderNo);
        if (null != orderNo) {
            list= this.wayBillTempService.placeAndPoi(orderNo);
            
            sendData(list, CommonConstant.FIND_SUC_OPTION);
        }
        else {
            this.sendCode(CommonConstant.ARGS_INVALID);
        }
        // 4、打印日志
        LOGGER.info(CommonConstant.FIND_SUC_OPTION, list);
        return this.sendMessage();
    }
    
    @SuppressWarnings("unchecked")
    @Override
    protected <T> IBaseService<T> getBaseService() {
        return (IBaseService<T>) this.wayBillTempService;
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
