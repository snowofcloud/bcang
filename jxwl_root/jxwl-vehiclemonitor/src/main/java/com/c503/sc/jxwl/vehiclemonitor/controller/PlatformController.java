/**
 * 文件名：PlatformController.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-8-8 
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.vehiclemonitor.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.c503.sc.base.entity.Page;
import com.c503.sc.base.service.IBaseService;
import com.c503.sc.jxwl.common.constant.CommonConstant;
import com.c503.sc.jxwl.common.controller.ResultController;
import com.c503.sc.jxwl.vehiclemonitor.bean.PlatformEntity;
import com.c503.sc.jxwl.vehiclemonitor.formbean.PlatformForm;
import com.c503.sc.jxwl.vehiclemonitor.service.IplatformService;
import com.c503.sc.log.LoggingManager;
import com.c503.sc.utils.common.SystemContants;
import com.c503.sc.utils.response.ResultJQGrid;

/**
 * 〈一句话功能简述〉平台信息Controller
 * 〈功能详细描述〉
 * @author    xiaoqx
 * @version   [版本号, 2016-8-8]
 * @see       [相关类/方法]
 * @since     [产品/模块版本] 
 */
@Controller
@Scope(value = "prototype")
@RequestMapping(value = "/platform")
public class PlatformController extends ResultController {
    /** 日志器 */
    private static final LoggingManager LOGGER =
        LoggingManager.getLogger(PlatformController.class);
    
    /** 平台信息业务接口 */
    @Resource(name = "platformService")
    private IplatformService platformService;
    
    
    /**
     *〈一句话功能简述〉分页查询平台信息
     * 〈功能详细描述〉
     * @param form form
     * @return object
     * @throws Exception Exception
     * @see  [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/findByPage", method = RequestMethod.POST)
    @ResponseBody
    public Object findByPage(PlatformForm form)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        // 1、参数设置
        Map<String, Object> map = form.handlePageParas();
        
        // 2、接口调用
        List<PlatformEntity> list =
            this.platformService.findByParams(map);
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
