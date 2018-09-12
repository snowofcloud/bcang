package com.c503.sc.jxwl.pipelinemangnt.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.c503.sc.base.entity.Page;
import com.c503.sc.base.service.IBaseService;
import com.c503.sc.jxwl.common.constant.CommonConstant;
import com.c503.sc.jxwl.common.constant.NumberContant;
import com.c503.sc.jxwl.common.controller.ResultController;
import com.c503.sc.jxwl.pipelinemangnt.bean.PipeLineEntity;
import com.c503.sc.jxwl.pipelinemangnt.bean.PipeLineLocationEntity;
import com.c503.sc.jxwl.pipelinemangnt.bean.PipeLineStatusEntity;
import com.c503.sc.jxwl.pipelinemangnt.service.IPipeLineService;
import com.c503.sc.log.LoggingManager;
import com.c503.sc.utils.basetools.C503StringUtils;
import com.c503.sc.utils.common.SystemContants;
import com.c503.sc.utils.response.ResultJQGrid;

/**
 * 〈一句话功能简述〉PipeLineController
 * 〈功能详细描述〉
 * 
 * @author
 * @version [版本号, 2017-1-3]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Controller
@Scope(value = "prototype")
@RequestMapping(value = "/pipeline")
public class PipeLineController extends ResultController {
    
    /** 日志器 */
    private static final LoggingManager LOGGER =
        LoggingManager.getLogger(PipeLineController.class);
    
    /** 路径规划service */
    @Resource(name = "pipeLineService")
    private IPipeLineService pipeLineService;

    /**
     * 〈一句话功能简述〉findByPage
     * 〈功能详细描述〉
     * 
     * @param pipeName pipeName
     * @param pipeType pipeType
     * @param page page
     * @param dangerName dangerName
     * @param rows rows
     * @return ResultJQGrid
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/findByPage", method = RequestMethod.POST)
    @ResponseBody
    public ResultJQGrid findByPage(String pipeName, String pipeType,
        String dangerName, Integer page, Integer rows)
        throws Exception {
        
        LOGGER.debug(SystemContants.DEBUG_START);
        
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("dangerName", dangerName == null ? null : dangerName.trim());
        map.put("pipeName", pipeName == null ? null : pipeName.trim());
        map.put("pipeType", pipeType == null ? null : pipeType.trim());
        Page pageEntity = new Page();
        pageEntity.setCurrentPage(page);
        pageEntity.setPageSize(rows);
        map.put("page", pageEntity);
        List<PipeLineEntity> list = this.pipeLineService.findByParams(map);
        
        setJQGrid(list,
            pageEntity.getTotalCount(),
            page,
            rows,
            CommonConstant.FIND_SUC_OPTION);
        LOGGER.debug(SystemContants.DEBUG_END);
        
        return (ResultJQGrid) this.sendMessage();
    }
    
    /**
     * 〈一句话功能简述〉findById
     * 〈功能详细描述〉
     * 
     * @param id id
     * @return Object
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
            Map<String, Object> map = new HashMap<>();
            map.put("pipeid", id);
            PipeLineEntity bayone = this.pipeLineService.findById(map);
            sendData(bayone, CommonConstant.FIND_SUC_OPTION);
        } // 传入参数无效
        else {
            this.sendCode(CommonConstant.ARGS_INVALID, id);
        }
        LOGGER.debug(SystemContants.DEBUG_END);
        return sendMessage();
    }
    
    /**
     * 
     * 〈一句话功能简述〉获取所有的管道、储罐的位置信息
     * 〈功能详细描述〉
     * 
     * @return list
     * @throws Exception 异常
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/findPipeLocations")
    @ResponseBody
    public Object findPipeLocations(String id)
        throws Exception {
        
        LOGGER.debug(SystemContants.DEBUG_START);
        
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);
        Page pageEntity = new Page();
        pageEntity.setCurrentPage(1);
        pageEntity.setPageSize(NumberContant.ONE_THOUSAND);
        map.put("page", pageEntity);
        List<PipeLineLocationEntity> list =
            this.pipeLineService.findPipeLocations(map);
        
        sendData(list, CommonConstant.FIND_SUC_OPTION);
        LOGGER.debug(SystemContants.DEBUG_END);
        return sendMessage();
    }
    
    /**
     * 〈一句话功能简述〉 findLocationById
     * 〈功能详细描述〉
     * 
     * @param id id
     * @param pipeType pipeType
     * @return ResultJQGrid
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/findLiquid", method = RequestMethod.POST)
    @ResponseBody
    public ResultJQGrid findLiquid(String id, String pipeType)
        throws Exception {
        
        LOGGER.debug(SystemContants.DEBUG_START);
        
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("pipeid", id);
        map.put("pipeType", pipeType);
        Page pageEntity = new Page();
        pageEntity.setCurrentPage(1);
        pageEntity.setPageSize(NumberContant.ONE_THOUSAND);
        map.put("page", pageEntity);
        List<PipeLineStatusEntity> list = this.pipeLineService.findLiquid(map);
        
        setJQGrid(list,
            pageEntity.getTotalCount(),
            1,
            NumberContant.ONE_THOUSAND,
            CommonConstant.FIND_SUC_OPTION);
        LOGGER.debug(SystemContants.DEBUG_END);
        
        return (ResultJQGrid) this.sendMessage();
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
