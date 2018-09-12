/**
 * 
 * 文件名：EmergencyInfoController.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-7-27
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.vehiclemonitor.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

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
import com.c503.sc.jxwl.vehiclemonitor.bean.EmergencyEntity;
import com.c503.sc.jxwl.vehiclemonitor.constant.BizExConstants;
import com.c503.sc.jxwl.vehiclemonitor.formbean.EmergencyInfoForm;
import com.c503.sc.jxwl.vehiclemonitor.service.IEmergencyInfoService;
import com.c503.sc.log.ControlLogModel;
import com.c503.sc.log.LoggingManager;
import com.c503.sc.utils.basetools.C503StringUtils;
import com.c503.sc.utils.common.SystemContants;
import com.c503.sc.utils.response.ResultJQGrid;

/**
 * 〈一句话功能简述〉突发信息Controller
 * 〈功能详细描述〉
 * 
 * @author xiaoqx
 * @version [版本号, 2016-8-9]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Controller
@Scope(value = "prototype")
@RequestMapping(value = "/suddenlyTrouble")
public class EmergencyInfoController extends ResultController {
    
    /** 日志器 */
    private static final LoggingManager LOGGER =
        LoggingManager.getLogger(EmergencyInfoController.class);
    
    /** 突发信息业务接口 */
    @Resource(name = "emergencyInfoService")
    private IEmergencyInfoService emergencyInfoService;
    
    /**
     * 〈一句话功能简述〉分页查询突发信息
     * 〈功能详细描述〉
     * 
     * @param startTime startTime
     * @param endTime endTime
     * @param page page
     * @param rows rows
     * @return Object
     * @throws Exception Exception
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
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        
        Page pageEntity = new Page();
        pageEntity.setCurrentPage(page);
        pageEntity.setPageSize(rows);
        map.put("page", pageEntity);
        
        // 2、接口调用
        List<EmergencyEntity> list =
            this.emergencyInfoService.findByParams(map);
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
     * 〈一句话功能简述〉保存突发信息
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
    public Object save(@Validated(value = Save.class) EmergencyInfoForm form,
        BindingResult bindingResult)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, form);
        boolean isResult = this.addErorrs(bindingResult);
        // 1、后台表单验证
        if (isResult) {
            this.loggerFormFail(form);
        } else {
            if (isSame()) {
                // 2、实体赋值
                final EmergencyEntity emergency = new EmergencyEntity();
                this.copyProperties(form, emergency);
                this.createMolVal(emergency, true);
                String sendTimesStr = emergency.getSendTimes();
                sendTimesStr = C503StringUtils.isEmpty(sendTimesStr)?"0":sendTimesStr;
                //获得发送次速
                final int sendTimes = Integer.parseInt(sendTimesStr);
                String sendFrequencyStr = emergency.getSendFrequency();
                sendFrequencyStr = C503StringUtils.isEmpty(sendFrequencyStr)?"0":sendFrequencyStr;
                //获得发送频率(s)
                final int sendFrequency = Integer.parseInt(sendFrequencyStr);
                // 3、调用接口信息
                this.emergencyInfoService.save(emergency);
                // 推送至所有驾驶员app
              
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                            	for (int i = 0; i < sendTimes; i++) {
                            		emergencyInfoService.miPushEmergency(emergency);
                            		Thread.sleep(sendFrequency*1000);
								}
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                
                // 4、打印日志
                LOGGER.info(CommonConstant.SAVE_SUC_OPTION, emergency);
                controlLog(ControlLogModel.CONTROL_RESULT_SUCCESS,
                    CommonConstant.SAVE_SUC_OPTION,
                    emergency).recordLog();
                this.sendCode(CommonConstant.SAVE_SUC_OPTION);
            } else {
                throw new CustomException(BizConstants.GOVERNMENT_ADD_ALLOW,
                    form);
            }
        }
        LOGGER.debug(SystemContants.DEBUG_END, form);
        
        return this.sendMessage();
    }
    
    /**
     * 
     * 〈一句话功能简述〉通过id查询突发信息
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
            EmergencyEntity val = this.emergencyInfoService.findById(id);
            sendData(val, CommonConstant.FIND_SUC_OPTION);
        } // 传入参数无效
        else {
            this.sendCode(CommonConstant.ARGS_INVALID, id);
        }
        LOGGER.debug(SystemContants.DEBUG_END);
        return sendMessage();
    }
    
    /**
     * 〈一句话功能简述〉删除突发信息
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
        // 删除前判断该条数据是否存在
        if (null == this.emergencyInfoService.findById(id)) {
            throw new CustomException(BizExConstants.DATAISNOTEXIST);
        }
        
        if (isSame()) {
            // 1、实体赋值
            EmergencyEntity entity = new EmergencyEntity();
            entity.setId(id);
            entity.setUpdateBy(this.getUser().getId());
            entity.setUpdateTime(new Date());
            // 2、调用接口
            this.emergencyInfoService.delete(entity);
            // 3、打印日志
            LOGGER.info(CommonConstant.UPDATE_SUC_OPTION, entity);
            controlLog(ControlLogModel.CONTROL_RESULT_SUCCESS,
                CommonConstant.UPDATE_SUC_OPTION,
                entity).recordLog();
            sendCode(CommonConstant.UPDATE_SUC_OPTION);
        } else {
            throw new CustomException(BizConstants.GOVERNMENT_DELETE_ALLOW, id);
        }
        
        LOGGER.debug(SystemContants.DEBUG_END, id);
        return this.sendMessage();
    }
    
    /**
     * 〈一句话功能简述〉修改突发信息
     * 〈功能详细描述〉
     * 
     * @param id id
     * @param form 突发信息
     * @param bindingResult 验错误信息结果集
     * @return 返回修改结果信息
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Object update(@PathVariable String id,
        @Validated(value = Update.class) EmergencyInfoForm form,
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
                if(null == this.emergencyInfoService.findById(id)){
                    throw new CustomException(BizExConstants.DATAISNOTEXIST);
                }
                if (isSame()) {
                	EmergencyEntity emergency =
                        new EmergencyEntity();
                    this.copyProperties(form, emergency);
                    emergency.setId(id);
                    emergency.setUpdateTime(new Date());
                    emergency.setUpdateBy(this.getUser().getId());
                    emergency.setRemove(SystemContants.ON);
                    
                    this.emergencyInfoService.update(emergency);
                    
                    LOGGER.info(CommonConstant.UPDATE_SUC_OPTION,
                    		emergency);
                    controlLog(ControlLogModel.CONTROL_RESULT_SUCCESS,
                        CommonConstant.UPDATE_SUC_OPTION,
                        emergency).recordLog();
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
     * 〈一句话功能简述〉
     * 〈功能详细描述〉
     * 
     * @param emergency EmergencyEntity
     * @param saveOrUpdate true：保存, false：修改
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    private void createMolVal(EmergencyEntity emergency, boolean saveOrUpdate)
        throws Exception {
        Date curDate = new Date();
        String userId = this.getUser().getId();
        
        emergency.setUpdateBy(userId);
        emergency.setUpdateTime(curDate);
        if (saveOrUpdate) {
            emergency.setId(C503StringUtils.createUUID());
            emergency.setCreateBy(userId);
            emergency.setCreateTime(curDate);
            emergency.setRemove(SystemContants.ON);
            emergency.setPublishDate(curDate);
        }
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
        } catch (Exception e) {
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
        return null;
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
