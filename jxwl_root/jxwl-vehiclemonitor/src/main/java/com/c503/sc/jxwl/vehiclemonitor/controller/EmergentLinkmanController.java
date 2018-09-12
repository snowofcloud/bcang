/**
 * 文件名：EmergentLinkmanController.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-8-5
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.vehiclemonitor.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
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
import com.c503.sc.jxwl.vehiclemonitor.bean.EmergentLinkmanEntity;
import com.c503.sc.jxwl.vehiclemonitor.constant.BizExConstants;
import com.c503.sc.jxwl.vehiclemonitor.formbean.EmergentLinkmanForm;
import com.c503.sc.jxwl.vehiclemonitor.service.IEmergentLinkmanService;
import com.c503.sc.log.ControlLogModel;
import com.c503.sc.log.LoggingManager;
import com.c503.sc.utils.basetools.C503StringUtils;
import com.c503.sc.utils.common.SystemContants;
import com.c503.sc.utils.response.ResultJQGrid;

/**
 * 〈一句话功能简述〉紧急联系人Controller
 * 〈功能详细描述〉
 * 
 * @author xiaoqx
 * @version [版本号, 2016-8-5]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Controller
@Scope(value = "prototype")
@RequestMapping(value = "/emergentLinkman")
public class EmergentLinkmanController extends ResultController {
    /** 日志器 */
    private static final LoggingManager LOGGER =
        LoggingManager.getLogger(EmergencyInfoController.class);
    
    /** 紧急联系人业务接口 */
    @Resource(name = "emergentLinkmanService")
    private IEmergentLinkmanService emergentLinkmanService;
    
    /**
     * 〈一句话功能简述〉分页查询紧急联系人信息
     * 〈功能详细描述〉
     * 
     * @param form form
     * @return object
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/findByPage", method = RequestMethod.POST)
    @ResponseBody
    public Object findByPage(EmergentLinkmanForm form)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        // 1、参数设置
        Map<String, Object> map = form.handlePageParas();
        
        String personName = form.getPersonName() == null ? null : form.getPersonName().trim();
        
        
        //String personName = form.getPersonName().trim();
        map.put("personName", StringUtils.isEmpty(personName)?null:"%"+personName+"%");
        // 2、接口调用
        List<EmergentLinkmanEntity> list =
            this.emergentLinkmanService.findByParams(map);
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
     * 〈一句话功能简述〉保存紧急联系人信息
     * 〈功能详细描述〉
     * 
     * @param form form
     * @param bindingResult BindingResult
     * @return Object
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public Object save(@Validated(value = Save.class) EmergentLinkmanForm form,
        BindingResult bindingResult)
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
                EmergentLinkmanEntity emergentLinkman =
                    new EmergentLinkmanEntity();
                this.copyProperties(form, emergentLinkman);
                emergentLinkman.setId(C503StringUtils.createUUID());
                emergentLinkman.setCreateBy(this.getUser().getId());
                emergentLinkman.setCreateTime(new Date());
                emergentLinkman.setUpdateBy(this.getUser().getId());
                emergentLinkman.setUpdateTime(new Date());
                emergentLinkman.setRemove(SystemContants.ON);
                
                // 3、调用接口信息
                this.emergentLinkmanService.save(emergentLinkman);
                
                // 4、打印日志
                LOGGER.info(CommonConstant.SAVE_SUC_OPTION, emergentLinkman);
                controlLog(ControlLogModel.CONTROL_RESULT_SUCCESS,
                    CommonConstant.SAVE_SUC_OPTION,
                    emergentLinkman).recordLog();
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
     * 〈一句话功能简述〉通过id查询紧急联系人信息
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
            EmergentLinkmanEntity val =
                this.emergentLinkmanService.findById(id);
            sendData(val, CommonConstant.FIND_SUC_OPTION);
        } // 传入参数无效
        else {
            this.sendCode(CommonConstant.ARGS_INVALID, id);
        }
        LOGGER.debug(SystemContants.DEBUG_END);
        return sendMessage();
    }
    
    /**
     * 〈一句话功能简述〉修改紧急联系人信息
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
    public Object update(@PathVariable String id,
        @Validated(value = Update.class) EmergentLinkmanForm form,
        BindingResult bindingResult)
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
                if(null == this.emergentLinkmanService.findById(id)){
                    throw new CustomException(BizExConstants.DATAISNOTEXIST);
                }
                
                if (isSame()) {
                    EmergentLinkmanEntity emergentLinkman =
                        new EmergentLinkmanEntity();
                    this.copyProperties(form, emergentLinkman);
                    emergentLinkman.setId(id);
                    emergentLinkman.setUpdateTime(new Date());
                    emergentLinkman.setUpdateBy(this.getUser().getId());
                    emergentLinkman.setRemove(SystemContants.ON);
                    
                    this.emergentLinkmanService.update(emergentLinkman);
                    
                    LOGGER.info(CommonConstant.UPDATE_SUC_OPTION,
                        emergentLinkman);
                    controlLog(ControlLogModel.CONTROL_RESULT_SUCCESS,
                        CommonConstant.UPDATE_SUC_OPTION,
                        emergentLinkman).recordLog();
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
     * 〈一句话功能简述〉删除紧急联系人信息
     * 〈功能详细描述〉
     * 
     * @param id id
     * @return object
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public Object delete(@PathVariable String id)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, id);
        
      //删除前判断该条数据是否存在
        if(null == this.emergentLinkmanService.findById(id)){
            throw new CustomException(BizExConstants.DATAISNOTEXIST);
        }
        
        if (isSame()) {
            // 1、实体赋值
            EmergentLinkmanEntity entity = new EmergentLinkmanEntity();
            entity.setId(id);
            entity.setUpdateBy(this.getUser().getId());
            entity.setUpdateTime(new Date());
            // 2、调用接口
            this.emergentLinkmanService.delete(entity);
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
     * 〈一句话功能简述〉查询所有紧急联系人（APP访问使用）
     * 〈功能详细描述〉
     * 
     * @param personName personName
     * @return object
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/findAll", method = RequestMethod.POST)
    @ResponseBody
    public Object findAllContact(String personName)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, personName);
        // 1、参数设置
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("personName", personName);
        
        // 2、接口调用
        List<EmergentLinkmanEntity> list =
            this.emergentLinkmanService.findByParams(map);
        // 3、装数据
        this.sendData(list, CommonConstant.FIND_SUC_OPTION, "Ok");
        LOGGER.debug(SystemContants.DEBUG_END, personName);
        
        return this.sendMessage();
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
