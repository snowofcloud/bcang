/**
 * 文件名：AccessPlatformController.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-7-26
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.vehiclemonitor.controller;

import java.util.HashMap;
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
import com.c503.sc.jxwl.vehiclemonitor.bean.AccessPlatformEntity;
import com.c503.sc.jxwl.vehiclemonitor.service.IAccessPlatformService;
import com.c503.sc.log.LoggingManager;
import com.c503.sc.utils.common.SystemContants;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * 
 * @author xiaoqx
 * @version [版本号, 2016-8-9]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Controller
@Scope(value = "prototype")
@RequestMapping(value = "/accessPlatform")
public class AccessPlatformController extends ResultController {
    
    /** 日志器 */
    private static final LoggingManager LOGGER =
        LoggingManager.getLogger(AccessPlatformController.class);
    
    /** 平台接入数据获取业务接口 */
    @Resource(name = "accessPlatformService")
    private IAccessPlatformService accessPlatformService;
    
    /**
     * 〈一句话功能简述〉
     * 〈功能详细描述〉
     * 
     * @param platformName platformName
     * @param page page
     * @param rows rows
     * @return 数据信息
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/findAccessPlatformData", method = RequestMethod.GET)
    @ResponseBody
    public Object findAccessPlatformData(String platformName, Integer page,
        Integer rows)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        // 1.参数设置
        Map<String, Object> map = new HashMap<String, Object>();
        Page pageEntity = new Page();
        pageEntity.setCurrentPage(page);
        pageEntity.setPageSize(rows);
        map.put("platformName", platformName);
        map.put("page", pageEntity);
        // 2.接口调用
        List<AccessPlatformEntity> result =
            accessPlatformService.findAccessPlatformData(map);
        // 3.数据返回
        setJQGrid(result,
            pageEntity.getTotalCount(),
            page,
            rows,
            CommonConstant.FIND_SUC_OPTION);
        LOGGER.debug(SystemContants.DEBUG_END);
        return sendMessage();
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
