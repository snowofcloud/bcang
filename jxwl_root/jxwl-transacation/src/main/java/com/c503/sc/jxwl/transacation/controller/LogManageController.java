/**
 * 文件名：LogManageController.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2017-4-6
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.transacation.controller;

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
import com.c503.sc.jxwl.common.constant.DictConstant;
import com.c503.sc.jxwl.common.controller.ResultController;
import com.c503.sc.jxwl.transacation.bean.LogManage;
import com.c503.sc.jxwl.transacation.service.ILogManageService;
import com.c503.sc.log.LoggingManager;
import com.c503.sc.utils.basetools.C503StringUtils;
import com.c503.sc.utils.common.SystemContants;
import com.c503.sc.utils.response.ResultJQGrid;

@Controller
@Scope(value = "prototype")
@RequestMapping(value = "/logManage")
public class LogManageController extends ResultController {
    /** 日志器 */
    private static final LoggingManager LOGGER =
        LoggingManager.getLogger(LogManageController.class);
    
    /** 订单日志业务接口 */
    @Resource(name = "logManageService")
    private ILogManageService logManageService;
    
    /**
     * 
     * 〈一句话功能简述〉分页查询订单日志信息
     * 〈功能详细描述〉
     * 
     * @param page 页数
     * @param rows 列数
     * @return 日志信息集合
     * @throws Exception 异常
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/findByPage", method = RequestMethod.POST)
    @ResponseBody
    public Object findByPage(String orderNo, Integer page, Integer rows)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        // 1、参数设置
        Map<String, Object> map = new HashMap<String, Object>();
        Page pageEntity = new Page();
        pageEntity.setCurrentPage(page);
        pageEntity.setPageSize(rows);
        map.put("page", pageEntity);
        map.put("orderNo", orderNo);
     // 2、判断是物流用户还是化工用户（本企业只能查看自己企业的信息）
        List<String> roleCode = this.getUser().getRoleCodes();
        String corporateName = this.getUser().getName();;
        if (roleCode.contains(DictConstant.LOGISTICS_ENTERPRISE_USER)) {
        	map.put("wlEnterpriseName", corporateName);
        }
        else if (roleCode.contains(DictConstant.CHEMICAL_ENTERPRISE_USER)) {
        	 map.put("hgEnterpriseName", corporateName);
        } 
        // 3、接口调用
        List<LogManage> list = this.logManageService.findByParams(map);
        // 4、数据返回
        setJQGrid(list,
            pageEntity.getTotalCount(),
            page,
            rows,
            CommonConstant.FIND_SUC_OPTION);
        LOGGER.debug(SystemContants.DEBUG_END);
        
        return (ResultJQGrid) this.sendMessage();
    }
    
    @RequestMapping(value = "/findById/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Object findById(@PathVariable
    String id)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        // 数据校验
        if (C503StringUtils.isNotEmpty(id)) {
            LogManage val = this.logManageService.findById(id);
            sendData(val, CommonConstant.FIND_SUC_OPTION);
        } // 传入参数无效
        else {
            this.sendCode(CommonConstant.ARGS_INVALID, id);
        }
        LOGGER.debug(SystemContants.DEBUG_END);
        return sendMessage();
        
    }
    
    @Override
    protected Object show() {
        return null;
    }
    
    @Override
    protected LoggingManager logger() {
        return LOGGER;
    }
    
    @Override
    protected <T> IBaseService<T> getBaseService() {
        return null;
    }
}
