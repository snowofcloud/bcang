/**
 * 文件名：UserDefinedAreaController.java
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
import com.c503.sc.jxwl.zcpt.bean.UserDefinedArea;
import com.c503.sc.jxwl.zcpt.service.IUserDefinedAreaService;
import com.c503.sc.log.LoggingManager;
import com.c503.sc.utils.basetools.C503StringUtils;
import com.c503.sc.utils.common.SystemContants;

/**
 * 
 * 〈一句话功能简述〉用户自定义区域
 * 〈功能详细描述〉
 * 
 * @author guqh
 * @version [版本号, 2017-3-30]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Controller
@Scope(value = "prototype")
@RequestMapping(value = "/userDefinedArea")
public class UserDefinedAreaController extends ResultController {
    
    /** 日志器 */
    private static final LoggingManager LOGGER =
        LoggingManager.getLogger(UserDefinedAreaController.class);
    
    /** 自定义区域 */
    @Resource(name = "userDefinedAreaService")
    private IUserDefinedAreaService userDefinedAreaService;
    
    /**
     * 〈一句话功能简述〉自定义区域
     * 〈功能详细描述〉
     * 
     * @param areaName areaName
     * @param points pointList
     * @param pointsJSON pointSet
     * @return Object
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/limit", method = RequestMethod.POST)
    @ResponseBody
    public Object userDefinedArea(@RequestParam("areaName")
    String areaName, @RequestParam("pointSet")
    String pointsJSON, @RequestParam("pointList")
    String points)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        if (StringUtils.isNotEmpty(points) && StringUtils.isNotEmpty(areaName)) {
            String id = C503StringUtils.createUUID();
            UserDefinedArea userDefinedArea =
                new UserDefinedArea(id, this.getUser().getId(), points,
                    areaName);
            userDefinedArea.setCreateTime(new Date());
            this.userDefinedAreaService.saveUserDefinedArea(userDefinedArea);
            this.sendData(id, CommonConstant.SAVE_SUC_OPTION);
            LOGGER.info(CommonConstant.SAVE_SUC_OPTION, areaName);
        }
        // 传入参数无效
        else {
            this.sendCode(CommonConstant.ARGS_INVALID, areaName);
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
    @RequestMapping(value = "/delUserDefinedArea/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Object delUserDefinedArea(@PathVariable
    String id)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        if (StringUtils.isNotEmpty(id)) {
            this.userDefinedAreaService.deleteUserDefinedArea(id);
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
    @RequestMapping(value = "/findUserDefinedArea", method = RequestMethod.GET)
    @ResponseBody
    public Object findUserDefinedArea()
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        List<UserDefinedArea> list = null;
        try {
            list = this.userDefinedAreaService.findUserDefinedAreas();
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
    @RequestMapping(value = "/findByUserDefinedAreaId/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Object findByUserDefinedAreaId(@PathVariable
    String id)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        UserDefinedArea userDefinedArea = null;
        try {
            userDefinedArea =
                this.userDefinedAreaService.findUserDefinedAreaById(id);
        }
        catch (Exception e) {
            throw new CustomException(CommonConstant.SYS_EXCEPTION, e);
        }
        this.sendData(userDefinedArea, CommonConstant.FIND_SUC_OPTION);
        LOGGER.debug(SystemContants.DEBUG_END);
        
        return sendMessage();
    }
    
    /**
     * 〈一句话功能简述〉自定义区域
     * 〈功能详细描述〉
     * 
     * @param id id
     * @param areaName areaName
     * @param points pointList
     * @param pointsJSON pointSet
     * @return Object
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public Object updateUserDefinedArea(@RequestParam("id")
    String id, @RequestParam("areaName")
    String areaName, @RequestParam("pointSet")
    String pointsJSON, @RequestParam("pointList")
    String points)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        if (StringUtils.isNotEmpty(id) && StringUtils.isNotEmpty(areaName)) {
            UserDefinedArea userDefinedArea =
                new UserDefinedArea(id, this.getUser().getId(), points,
                    areaName);
            userDefinedArea.setUpdateTime(new Date());
            this.userDefinedAreaService.updateUserDefinedArea(userDefinedArea);
            this.sendData(id, CommonConstant.UPDATE_SUC_OPTION);
            LOGGER.info(CommonConstant.UPDATE_SUC_OPTION, id);
            // 传入参数无效
        }
        else {
            this.sendCode(CommonConstant.ARGS_INVALID, areaName);
        }
        LOGGER.debug(SystemContants.DEBUG_END);
        
        return sendMessage();
    }
    
    @SuppressWarnings("unchecked")
    @Override
    protected <T> IBaseService<T> getBaseService() {
        return (IBaseService<T>) this.userDefinedAreaService;
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
