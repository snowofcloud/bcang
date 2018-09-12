/**
 * 文件名：UserDefinedFacilityController.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2017-3-30
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.zcpt.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.c503.sc.base.common.CommonConstants;
import com.c503.sc.base.exception.CustomException;
import com.c503.sc.base.service.IBaseService;
import com.c503.sc.jxwl.common.constant.CommonConstant;
import com.c503.sc.jxwl.common.controller.ResultController;
import com.c503.sc.jxwl.zcpt.bean.UserDefinedFacility;
import com.c503.sc.jxwl.zcpt.service.IUserDefinedFacilityService;
import com.c503.sc.log.LoggingManager;
import com.c503.sc.utils.basetools.C503StringUtils;
import com.c503.sc.utils.common.SystemContants;

/**
 * 
 * 〈一句话功能简述〉自定义设施
 * 〈功能详细描述〉
 * 
 * @author guqh
 * @version [版本号, 2017-3-30]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Controller
@Scope(value = "prototype")
@RequestMapping(value = "/userDefinedFacility")
public class UserDefinedFacilityController extends ResultController {
    
    /** 日志器 */
    private static final LoggingManager LOGGER =
        LoggingManager.getLogger(UserDefinedFacilityController.class);
    
    /** 自定义区域 */
    @Resource(name = "userDefinedFacilityService")
    private IUserDefinedFacilityService userDefinedFacilityService;
    
    /**
     * 〈一句话功能简述〉自定义区域
     * 〈功能详细描述〉
     * 
     * @param facilityName facilityName
     * @param iconId iconId
     * @param points pointList
     * @param pointsJSON pointSet
     * @return Object
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/saveFacility", method = RequestMethod.POST)
    @ResponseBody
    public Object saveDefinedFacility(@RequestParam("facilityName")
    String facilityName, @RequestParam("iconId")
    String iconId, @RequestParam("points")
    String points, @RequestParam("longitude")
    String longitude, @RequestParam("latitude")
    String latitude)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        if (StringUtils.isNotEmpty(facilityName)) {
            String id = C503StringUtils.createUUID();
            UserDefinedFacility userDefinedFacility =
                new UserDefinedFacility(id, this.getUser().getId(), iconId,
                    facilityName, points, longitude, latitude);
            userDefinedFacility.setCreateTime(new Date());
            this.userDefinedFacilityService.saveUserDefinedFacility(userDefinedFacility);
            this.sendData(id, CommonConstant.SAVE_SUC_OPTION);
            LOGGER.info(CommonConstant.SAVE_SUC_OPTION, facilityName);
        }
        // 传入参数无效
        else {
            this.sendCode(CommonConstant.ARGS_INVALID, facilityName);
        }
        LOGGER.debug(SystemContants.DEBUG_END);
        
        return sendMessage();
    }
    
    /**
     * 〈一句话功能简述〉自定义区域
     * 〈功能详细描述〉
     * 
     * @param id id
     * @return Object
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/delUserDefinedFacility/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Object delUserDefinedFacility(@PathVariable
    String id)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        if (StringUtils.isNotEmpty(id)) {
            this.userDefinedFacilityService.deleteUserDefinedFacility(id);
            this.sendCode(CommonConstant.DELETE_SUC_OPTION);
            LOGGER.info(CommonConstants.SUC_OPTION, id);
            // 传入参数无效
        }
        else {
            this.sendCode(CommonConstant.ARGS_INVALID, id);
        }
        LOGGER.debug(SystemContants.DEBUG_END);
        
        return sendMessage();
    }
    
    /**
     * 〈一句话功能简述〉自定义区域
     * 〈功能详细描述〉
     * 
     * @return Object
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/findUserDefinedFacility", method = RequestMethod.GET)
    @ResponseBody
    public Object findUserDefinedFacility()
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        List<UserDefinedFacility> list = null;
        try {
            list = this.userDefinedFacilityService.findUserDefinedFacilitys();
        }
        catch (Exception e) {
            throw new CustomException(CommonConstant.SYS_EXCEPTION, e);
        }
        this.sendData(list, CommonConstant.FIND_SUC_OPTION);
        LOGGER.debug(SystemContants.DEBUG_END);
        
        return sendMessage();
    }
    
    /**
     * 〈一句话功能简述〉查看自定义区域
     * 〈功能详细描述〉
     * 
     * @param id id
     * @return object
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/findByUserDefinedFacilityId/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Object findByUserDefinedFacilityId(@PathVariable
    String id)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        UserDefinedFacility userDefinedFacility = null;
        try {
            userDefinedFacility =
                this.userDefinedFacilityService.findUserDefinedFacilityById(id);
        }
        catch (Exception e) {
            throw new CustomException(CommonConstant.SYS_EXCEPTION, e);
        }
        this.sendData(userDefinedFacility, CommonConstant.FIND_SUC_OPTION);
        LOGGER.debug(SystemContants.DEBUG_END);
        
        return sendMessage();
    }
    
    /**
     * 〈一句话功能简述〉自定义区域
     * 〈功能详细描述〉
     * 
     * @param id id
     * @param facilityName facilityName
     * @param iconId iconId
     * @param points pointList
     * @param pointsJSON pointSet
     * @return Object
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public Object updateUserDefinedFacility(@RequestParam("id")
    String id, @RequestParam("facilityName")
    String facilityName, @RequestParam("iconId")
    String iconId, @RequestParam("points")
    String points, @RequestParam("longitude")
    String longitude, @RequestParam("latitude")
    String latitude)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        if (StringUtils.isNotEmpty(id) && StringUtils.isNotEmpty(facilityName)) {
            UserDefinedFacility userDefinedFacility =
                new UserDefinedFacility(id, this.getUser().getId(), iconId,
                    facilityName, points, longitude, latitude);
            userDefinedFacility.setUpdateTime(new Date());
            this.userDefinedFacilityService.updateUserDefinedFacility(userDefinedFacility);
            this.sendData(id, CommonConstant.UPDATE_SUC_OPTION);
            LOGGER.info(CommonConstant.UPDATE_SUC_OPTION, id);
            // 传入参数无效
        }
        else {
            this.sendCode(CommonConstant.ARGS_INVALID, facilityName);
        }
        LOGGER.debug(SystemContants.DEBUG_END);
        
        return sendMessage();
    }
    
    @SuppressWarnings("unchecked")
    @Override
    protected <T> IBaseService<T> getBaseService() {
        return (IBaseService<T>) this.userDefinedFacilityService;
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