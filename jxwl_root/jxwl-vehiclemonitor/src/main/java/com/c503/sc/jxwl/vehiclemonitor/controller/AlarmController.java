/*
 * 文件名：AlarmController.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-8-3
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.vehiclemonitor.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.c503.sc.base.entity.Page;
import com.c503.sc.base.service.IBaseService;
import com.c503.sc.jxwl.common.constant.CommonConstant;
import com.c503.sc.jxwl.common.controller.ResultController;
import com.c503.sc.jxwl.zcpt.constant.ReturnMsg;
import com.c503.sc.jxwl.zcpt.service.ITerminalService;
import com.c503.sc.log.LoggingManager;
import com.c503.sc.utils.basetools.C503StringUtils;
import com.c503.sc.utils.common.SystemContants;
import com.c503.sc.utils.response.ResultJQGrid;

/**
 * 报警信息
 * 
 * @author qianxq
 * @version [版本号, 2016-8-3]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Deprecated
@Controller
@RequestMapping(value = "/alarmRecord")
public class AlarmController extends ResultController {
    
    /** 日志器 */
    private static final LoggingManager LOGGER =
        LoggingManager.getLogger(AlarmController.class);
    
    /** 终端服务 */
    @Resource(name = "terminalService")
    private ITerminalService terminalService;
    
    /**
     * 〈一句话功能简述〉分页查询报警记录〈功能详细描述〉
     * 
     * @param page
     *            前台数据-页数
     * @param rows
     *            前台数据-每一页显示的行数
     * @param carrierName
     *            carrierName
     * @return 返回查询的所有记录（分页数据）
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/findByPage", method = RequestMethod.POST)
    @ResponseBody
    public Object findByPage(Integer page, Integer rows, String carrierName)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, carrierName);
        carrierName = carrierName == null ? null : carrierName.trim();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("carrierName", carrierName);
        
        Page pageEntity = new Page();
        pageEntity.setCurrentPage(page);
        pageEntity.setPageSize(rows);
        map.put("page", pageEntity);
        
        // try {
        // List<AlarmRecord> list = this.getInfoService.findAlarmRecords(map);
        // setJQGrid(list,
        // pageEntity.getTotalCount(),
        // page,
        // rows,
        // CommonConstant.FIND_SUC_OPTION);
        //
        // }
        // catch (Exception e) {
        // throw new CustomException(CommonConstant.SYS_EXCEPTION, e);
        // }
        LOGGER.info(CommonConstant.FIND_SUC_OPTION, "查询报警记录");
        LOGGER.debug(SystemContants.DEBUG_END);
        
        // 发送响应消息
        return (ResultJQGrid) sendMessage();
    }
    
    /**
     * 
     * 〈一句话功能简述〉通过id查询报警记录 〈功能详细描述〉
     * 
     * @param id
     *            id
     * @return 返回成功 的数据信息
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/findById/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Object findById(@PathVariable String id)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        if (C503StringUtils.isNotEmpty(id)) {
            LOGGER.info(0);
            // try {
            // AlarmRecord val = this.getInfoService.findAlarmById(id);
            // sendData(val, CommonConstant.FIND_SUC_OPTION);
            // }
            // catch (Exception e) {
            // throw new CustomException(CommonConstant.SYS_EXCEPTION);
            // }
        } // 传入参数无效
        else {
            this.sendCode(CommonConstant.ARGS_INVALID, id);
        }
        LOGGER.debug(SystemContants.DEBUG_END);
        
        return sendMessage();
    }
    
    /**
     * 〈一句话功能简述〉获取实时数据（报警、上下线） 〈功能详细描述〉
     * 
     * @return map
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "getRealValue", method = {RequestMethod.GET,
        RequestMethod.POST})
    @ResponseBody
    public Object findAlarm() {
        this.sendData(ReturnMsg.getAlarmMap(), CommonConstant.FIND_SUC_OPTION);
        // System.out.println(JSON.toJSONString(ReturnMsg.getAlarmMap()));
        return sendMessage();
        
    }
    
    @SuppressWarnings("unchecked")
    @Override
    protected <T> IBaseService<T> getBaseService() {
        return (IBaseService<T>) this.terminalService;
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
