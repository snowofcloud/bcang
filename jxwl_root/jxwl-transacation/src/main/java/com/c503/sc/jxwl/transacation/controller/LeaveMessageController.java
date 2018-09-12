/**
 * 文件名：LeaveMessageController.java
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
import com.c503.sc.jxwl.common.constant.BizConstants;
import com.c503.sc.jxwl.common.constant.CommonConstant;
import com.c503.sc.jxwl.common.constant.DictConstant;
import com.c503.sc.jxwl.common.controller.ResultController;
import com.c503.sc.jxwl.transacation.constant.BizExConstant;
import com.c503.sc.jxwl.transacation.bean.LeaveMessageEntity;
import com.c503.sc.jxwl.transacation.formbean.LeaveMessageForm;
import com.c503.sc.jxwl.transacation.service.ILeaveMessageService;
import com.c503.sc.log.ControlLogModel;
import com.c503.sc.log.LoggingManager;
import com.c503.sc.utils.basetools.C503DateUtils;
import com.c503.sc.utils.basetools.C503StringUtils;
import com.c503.sc.utils.common.SystemContants;
import com.c503.sc.utils.response.ResultJQGrid;

/**
 * 
 * 〈一句话功能简述〉留言管理Controller
 * 〈功能详细描述〉
 * 
 * @author xinzhiwei
 * @version [版本号, 2016-8-30]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Controller
@Scope(value = "prototype")
@RequestMapping(value = "/leaveMessage")
public class LeaveMessageController extends ResultController {
    /** 日志器 */
    private static final LoggingManager LOGGER =
        LoggingManager.getLogger(LeaveMessageController.class);
    
    /** 留言管理信息业务接口 */
    @Resource
    private ILeaveMessageService leaveMessageService;
    
    /**
     * 〈一句话功能简述〉删除留言
     * 〈功能详细描述〉
     * 
     * @param id 留言id
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
            // 删除留言之前先判断该条留言是否已删除
            boolean flag = this.leaveMessageService.isLeaveMessageDeleted(id);
            if (flag) {
                throw new CustomException(
                    BizExConstant.LEAVEMESSAGE_DELETE_NOTALLOWD);
            }
            // 只有物流企业用户才可以删除留言
            if (isSame()) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("id", id);
                map.put("updateBy", this.getUser().getId());
                map.put("updateTime", new Date());
                // map.put("conporateNo",this.getUser().getCorporateCode());
                
                int line = this.leaveMessageService.delete(map);
                
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
     * 〈一句话功能简述〉新增留言信息
     * 〈功能详细描述〉
     * 
     * @param form form
     * @param bindingResult bindingResult
     * @return object
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public Object save(@Validated(value = Save.class) LeaveMessageForm form,
        BindingResult bindingResult)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, form);
        
        boolean isResult = this.addErorrs(bindingResult);
        if (isResult) {
            this.loggerFormValidate(LOGGER, form);
        } else {
            String wlEnterprise = this.getUser().getCorporateCode();
            // 只有物流企业用户才可以新增留言
            if (!isSame()) {
                throw new CustomException(
                    BizExConstant.LEAVEMESSAGE_ADD_NOT_ALLOW, form);
            }
            
            if (!leaveMessageService.checkGoodsStatus(form.getGoodsNo())) {
                throw new CustomException(
                    BizExConstant.LEAVEMESSAGE_STATUS_NOT_ALLOW, form);
            }
            if (leaveMessageService.findLeaveMessageExist(wlEnterprise,
                form.getGoodsNo())) {
                throw new CustomException(
                    BizExConstant.LEAVEMESSAGE_TIMES_NOT_ALLOW, form);
            }
            LeaveMessageEntity entity = new LeaveMessageEntity();
            this.copyProperties(form, entity);
            entity.setId(C503StringUtils.createUUID());
            entity.setLogisticsEnterprise(wlEnterprise);
            String chemicalEnterprise =
                leaveMessageService.findChemicalEnterpriseByGoodNo(form.getGoodsNo());
            entity.setChemicalEnterprise(chemicalEnterprise);
            entity.setCreatedTime(new Date());
            entity.setCreateBy(this.getUser().getId());
            entity.setCreateTime(new Date());
            entity.setUpdateBy(this.getUser().getId());
            entity.setUpdateTime(new Date());
            entity.setRemove(SystemContants.ON);
            
            Object result = this.leaveMessageService.save(entity);
            
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
     * 〈一句话功能简述〉编辑留言信息
     * 〈功能详细描述〉
     * 
     * @param id id
     * @param form form
     * @param bindingResult bindingResult
     * @return object
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Object update(@PathVariable String id,
        @Validated(value = Update.class) LeaveMessageForm form,
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
            // 只有物流企业用户才可以修改留言
            if (isSame()) {
                // 2、实体赋值
                LeaveMessageEntity entity = new LeaveMessageEntity();
                // 这一句就是将前台对应的form对象复制到后台对应的entity对象
                this.copyProperties(form, entity);
                // 后台的entity对象比前台的form对象多4个属性，下面4行赋值就是给多出的4个属性字段赋值
                entity.setId(id);
                entity.setUpdateBy(this.getUser().getId());
                entity.setUpdateTime(new Date());
                entity.setCreatedTime(new Date());
                // 3、调用接口
                this.leaveMessageService.update(entity);
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
     * 〈一句话功能简述〉分页查询留言
     * 〈功能详细描述〉
     * 
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param page 页数
     * @param rows 行数
     * @return 返回数据信息
     * @throws Exception 系统异常
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/findByPage", method = RequestMethod.POST)
    @ResponseBody
    public Object findByPage(
        @DateTimeFormat(pattern = "yyyy-MM-dd", iso = ISO.DATE) Date startTime,
        @DateTimeFormat(pattern = "yyyy-MM-dd", iso = ISO.DATE) Date endTime,
        Integer page, Integer rows)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        // 1、参数设置
        /*
         * startTime =
         * StringUtils.isNotEmpty(startTime) ? startTime.trim()
         * : startTime;
         * endTime =
         * StringUtils.isNotEmpty(endTime) ? endTime.trim()
         * : endTime;
         */
        Map<String, Object> map = new HashMap<String, Object>();
        String corporateNo = this.getUser().getCorporateCode();
        map.put("startTime", startTime);
        endTime = endTime == null ? null : C503DateUtils.getDay(1, endTime);
        map.put("endTime", endTime);
        map.put("corporateNo", corporateNo);
        
        Page pageEntity = new Page();
        pageEntity.setCurrentPage(page);
        pageEntity.setPageSize(rows);
        map.put("page", pageEntity);
        
        /*
         * List<String> roleCode = this.getUser().getRoleCodes();
         * if (!roleCode.contains(DictConstant.GOVERNMENT_USER)) {
         * map.put("corporateNo", this.getUser().getCorporateCode());
         * }
         */
        
        // 2、接口调用
        List<LeaveMessageEntity> list =
            this.leaveMessageService.findByParams(map);
        
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
     * 〈一句话功能简述〉通过id查询留言
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
        // 获取登录用户角色
        List<String> roleCode = this.getUser().getRoleCodes();
        String corporateNo = this.getUser().getCorporateCode();
        if (C503StringUtils.isNotEmpty(id)) {
            if (StringUtils.isNotEmpty(corporateNo)) {
                // 登录用户为物流企业时交易对象为对应化工企业
                if (roleCode.contains(DictConstant.LOGISTICS_ENTERPRISE_USER)) {
                    LeaveMessageEntity val =
                        this.leaveMessageService.findById(id, null, corporateNo);
                    sendData(val, CommonConstant.FIND_SUC_OPTION);
                }
                // 登录用户为化工企业时交易对象为对应物流企业
                else if (roleCode.contains(DictConstant.CHEMICAL_ENTERPRISE_USER)) {
                    LeaveMessageEntity val =
                        this.leaveMessageService.findById(id, corporateNo, null);
                    sendData(val, CommonConstant.FIND_SUC_OPTION);
                }
                
            }
            
        } // 传入参数无效
        else {
            this.sendCode(CommonConstant.ARGS_INVALID, id);
        }
        LOGGER.debug(SystemContants.DEBUG_END);
        
        return sendMessage();
    }
    
    /**
     * 
     * 〈一句话功能简述〉判断该用户是否是物流企业用户（只有物流企业用户才有新增、编辑和删除权限）
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
        return (IBaseService<T>) this.leaveMessageService;
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
