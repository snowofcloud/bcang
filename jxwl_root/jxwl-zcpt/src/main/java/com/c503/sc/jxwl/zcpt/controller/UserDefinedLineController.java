/**
 * 文件名：UserDefinedController.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2017-4-13
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
import com.c503.sc.jxwl.zcpt.bean.UserDefinedLine;
import com.c503.sc.jxwl.zcpt.service.IUserDefinedLineService;
import com.c503.sc.log.LoggingManager;
import com.c503.sc.utils.basetools.C503StringUtils;
import com.c503.sc.utils.common.SystemContants;

/**
 * 
 * 〈一句话功能简述〉自定义线条
 * 〈功能详细描述〉
 * 
 * @author guqh
 * @version [版本号, 2017-4-13]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Controller
@Scope(value = "prototype")
@RequestMapping(value = "/userDefinedLine")
public class UserDefinedLineController extends ResultController {
    /** 日志器 */
    private static final LoggingManager LOGGER =
        LoggingManager.getLogger(UserDefinedLineController.class);
    
    /** 自定义线条 */
    @Resource(name = "userDefinedLineService")
    private IUserDefinedLineService userDefinedLineService;
    
    /**
     * 〈一句话功能简述〉自定义线条
     * 〈功能详细描述〉
     * 
     * @param lineName lineName
     * @param points pointList
     * @param pointsJSON pointSet
     * @return Object
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/saveLine", method = RequestMethod.POST)
    @ResponseBody
    public Object saveDefinedLine(@RequestParam("lineName")
    String lineName, @RequestParam("pointSet")
    String pointsJSON, @RequestParam("pointList")
    String points, @RequestParam("startLat")
    String startLat, @RequestParam("startLng")
    String startLng, @RequestParam("endLat")
    String endLat, @RequestParam("endLng")
    String endLng)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        if (StringUtils.isNotEmpty(points) && StringUtils.isNotEmpty(lineName)) {
            String id = C503StringUtils.createUUID();
            UserDefinedLine userDefinedLine =
                new UserDefinedLine(id, this.getUser().getId(), points,
                    lineName, startLat, startLng, endLat, endLng);
            userDefinedLine.setCreateTime(new Date());
            this.userDefinedLineService.saveUserDefinedLine(userDefinedLine);
            this.sendData(id, CommonConstant.SAVE_SUC_OPTION);
            LOGGER.info(CommonConstant.SAVE_SUC_OPTION, lineName);
        }
        // 传入参数无效
        else {
            this.sendCode(CommonConstant.ARGS_INVALID, lineName);
        }
        LOGGER.debug(SystemContants.DEBUG_END);
        
        return sendMessage();
    }
    
    /**
     * 〈一句话功能简述〉自定义线条
     * 〈功能详细描述〉
     * 
     * @param id id
     * @return Object
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/delUserDefinedLine/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Object delUserDefinedLine(@PathVariable
    String id)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        if (StringUtils.isNotEmpty(id)) {
            this.userDefinedLineService.deleteUserDefinedLine(id);
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
     * 〈一句话功能简述〉自定义线条
     * 〈功能详细描述〉
     * 
     * @return Object
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/findUserDefinedLine", method = RequestMethod.GET)
    @ResponseBody
    public Object findUserDefinedLine()
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        List<UserDefinedLine> list = null;
        try {
            list = this.userDefinedLineService.findUserDefinedLines();
        }
        catch (Exception e) {
            throw new CustomException(CommonConstant.SYS_EXCEPTION, e);
        }
        this.sendData(list, CommonConstant.FIND_SUC_OPTION);
        LOGGER.debug(SystemContants.DEBUG_END);
        
        return sendMessage();
    }
    
    /**
     * 〈一句话功能简述〉查看自定义线条
     * 〈功能详细描述〉
     * 
     * @param id id
     * @return object
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/findByUserDefinedLineId/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Object findByUserDefinedLineId(@PathVariable
    String id)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        UserDefinedLine userDefinedLine = null;
        try {
            userDefinedLine =
                this.userDefinedLineService.findUserDefinedLineById(id);
        }
        catch (Exception e) {
            throw new CustomException(CommonConstant.SYS_EXCEPTION, e);
        }
        this.sendData(userDefinedLine, CommonConstant.FIND_SUC_OPTION);
        LOGGER.debug(SystemContants.DEBUG_END);
        
        return sendMessage();
    }
    
    /**
     * 〈一句话功能简述〉自定义线条
     * 〈功能详细描述〉
     * 
     * @param id id
     * @param lineName lineName
     * @param points pointList
     * @param pointsJSON pointSet
     * @return Object
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public Object updateUserDefinedLine(@RequestParam("id")
    String id, @RequestParam("lineName")
    String lineName, @RequestParam("pointSet")
    String pointsJSON, @RequestParam("pointList")
    String points, String startLat, @RequestParam("startLng")
    String startLng, @RequestParam("endLat")
    String endLat, @RequestParam("endLng")
    String endLng)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        if (StringUtils.isNotEmpty(id) && StringUtils.isNotEmpty(lineName)) {
            UserDefinedLine userDefinedLine =
                new UserDefinedLine(id, this.getUser().getId(), points,
                    lineName, startLat, startLng, endLat, endLng);
            userDefinedLine.setUpdateTime(new Date());
            this.userDefinedLineService.updateUserDefinedLine(userDefinedLine);
            this.sendData(id, CommonConstant.UPDATE_SUC_OPTION);
            LOGGER.info(CommonConstant.UPDATE_SUC_OPTION, id);
            // 传入参数无效
        }
        else {
            this.sendCode(CommonConstant.ARGS_INVALID, lineName);
        }
        LOGGER.debug(SystemContants.DEBUG_END);
        
        return sendMessage();
    }
    
    @SuppressWarnings("unchecked")
    @Override
    protected <T> IBaseService<T> getBaseService() {
        return (IBaseService<T>) this.userDefinedLineService;
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
