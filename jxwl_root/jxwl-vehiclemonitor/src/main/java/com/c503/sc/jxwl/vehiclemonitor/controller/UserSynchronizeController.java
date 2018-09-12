/**
 * 文件名：UserSynchronizeController.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2017-5-16
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.vehiclemonitor.controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.jasig.cas.client.util.CommonUtils;
import org.jasig.cas.client.util.JsonUtil;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.c503.sc.base.entity.Page;
import com.c503.sc.base.service.IBaseService;
import com.c503.sc.jxwl.common.constant.CommonConstant;
import com.c503.sc.jxwl.common.controller.ResultController;
import com.c503.sc.jxwl.vehiclemonitor.bean.UserSynchronizeEntity;
import com.c503.sc.jxwl.vehiclemonitor.service.IUserSynchronizeService;
import com.c503.sc.log.LoggingManager;
import com.c503.sc.utils.basetools.C503StringUtils;
import com.c503.sc.utils.common.SystemContants;
import com.zx.framework.net.examples.nntp.newsgroups;

/**
 * 
 * 〈一句话功能简述〉用户同步
 * 〈功能详细描述〉
 * 
 * @author guqh
 * @version [版本号, 2017-5-16]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */

@Controller
@Scope(value = "prototype")
@RequestMapping(value = "/userSynchronize")
public class UserSynchronizeController extends ResultController {
    
    /** 企业信息Dao */
    /*
     * @Resource(name = "sysOrgTypeService")
     * private ISysOrgTypeService sysOrgTypeService;
     */
    
    /**
     * Service
     */
    @Resource(name = "userSynchronizeService")
    private IUserSynchronizeService userSynchronizeService;
    
    /** 日志器 */
    private static final LoggingManager LOGGER =
        LoggingManager.getLogger(UserSynchronizeController.class);
    
    /** 同步用户信息接口地址地址 */
    private String userUrl;
    
    /** 命名空间 */
    private String userNameSpace;
    
    /** 读取propertis文件对象 */
    private Properties propertis = new Properties();
    
    /**
     * 
     * 〈一句话功能简述〉 构造方法 〈功能详细描述〉
     * 
     * @see [类、类#方法、类#成员]
     */
    public UserSynchronizeController() {
        
        try {
            InputStream synchronizeIn =
                EnterpriseController.class.getResourceAsStream("/synchronize.properties");
            propertis.load(synchronizeIn);
            userUrl = propertis.getProperty("user.url");
            userNameSpace = propertis.getProperty("user.namespace");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
    /**
     * 
     * 〈一句话功能简述〉用户同步
     * 〈功能详细描述〉
     * 
     * @param request request
     * @return String String
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/synchronize", method = RequestMethod.GET)
    @ResponseBody
    // 如果不定义返回类型为【ResponseBody】即Object，返回类型默认为String，
    // 就会被前端当做视图页面的url地址进行页面跳转！
    public Object synchronize(HttpServletRequest request)
        throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        // http://172.31.2.193:8888
        String url = userUrl + userNameSpace;
        String result = null;
        try
        {
            result = CommonUtils.getResponseFromServer(url, "UTF-8");
        } catch (Exception e) {
            LOGGER.info(SystemContants.DEBUG_START, url+"连接访问失败！");
            e.printStackTrace();
            sendData(new ArrayList<String>(), CommonConstant.SYNCHRONIZE_FAIL_OPTION);
            return sendMessage();
        }
//        if (result == null) {
//            resultMap.put("code", 3);
//            resultMap.put("msg", "network error");
//        }
        List<Map<String, String>> list = JsonUtil.jsonToList(result);
        // 已经存在的用户不在同步 start
        Map<String, Object> map = new HashMap<String, Object>();
        // 同步标志 “0”代表为从其他系统同步的用户数据
        map.put("flag", SystemContants.ON);
        map.put("remove", SystemContants.ON);
        List<UserSynchronizeEntity> listPre =
            this.userSynchronizeService.findNewUserByParams(map);
        if (null != listPre) {
            for (UserSynchronizeEntity entity : listPre) {
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).get("userName").equals(entity.getName())) {
                        list.remove(i);
                    }
                }
            }
        }
        // 已经存在的用户不在同步 end
        List<UserSynchronizeEntity> users =
            new ArrayList<UserSynchronizeEntity>();
        for (int i = 0; i < list.size(); i++) {
            UserSynchronizeEntity user = new UserSynchronizeEntity();
            user.setId(C503StringUtils.createUUID());
            user.setName(list.get(i).get("userName"));
            user.setAccount(list.get(i).get("loginName"));
            user.setMobile(list.get(i).get("telPhone"));
            user.setPassword(list.get(i).get("passWord"));
            //user.setOrganId(list.get(i).get("organId"));
            //user.setSex(list.get(i).get("sex"));
            user.setCreateBy(this.getUser().getId());
            user.setCreateTime(new Date());
            user.setUpdateBy(this.getUser().getId());
            user.setUpdateTime(new Date());
            user.setRemove(SystemContants.ON);
            user.setFlag(SystemContants.ON);
            users.add(user);
        }
        // 保存或更新数据
        String json = null;
        // try {
        // 保存认证数据库
        //users.size()==0也是同步正确的情况，不应判别为failure
//        if (users.size() > 0 || users.size() == 0) {
//            this.userSynchronizeService.batchSynchronize(users);
//            resultMap.put("statu", "success");
//            resultMap.put("msg", "success");
//            json = JsonUtil.beanToJson(resultMap);
//        }
//        else {
//            resultMap.put("statu", "failure");
//            resultMap.put("msg", "save or update error");
//            json = JsonUtil.beanToJson(resultMap);
//            
//        }
     // }
        /*
         * catch (Exception e) {
         * e.getStackTrace();
         * }
         */
//        return json;//返回的是json字符串，前端接收到后会把它认为是页面的url地址进行跳转！
        if(users.size() > 0){
            try
            {
                this.userSynchronizeService.batchSynchronize(users);
            }
            catch (Exception e)
            {
                sendData(new ArrayList<String>(), CommonConstant.SYNCHRONIZE_FAIL_OPTION);
                return sendMessage();
            }
        }
        sendData(new ArrayList<String>(), CommonConstant.SYNCHRONIZE_SUC_OPTION);
        return sendMessage();
    }
    
    /**
     * 
     * 〈一句话功能简述〉 分页查询
     * 〈功能详细描述〉
     * 
     * @param page 页码
     * @param rows 行
     * @param name 名称
     * @param tradeTypeCode 行业
     * @return 列表
     * @throws Exception 异常
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/findNewUserByPage", method = RequestMethod.POST)
    @ResponseBody
    public Object findNewUserByParams(Integer page, Integer rows, String name,
        String tradeTypeCode)
        throws Exception {
        
        // enterpriseEx();
        
        LOGGER.debug(SystemContants.DEBUG_START);
        Map<String, Object> map = new HashMap<String, Object>();
        // 同步标志 “0”代表为从其他系统同步的用户数据
        map.put("flag", SystemContants.ON);
        map.put("remove", SystemContants.ON);
        map.put("name", name);
        Page pageEntity = new Page();
        pageEntity.setCurrentPage(page);
        pageEntity.setPageSize(rows);
        map.put("page", pageEntity);
        
        List<UserSynchronizeEntity> list =
            this.userSynchronizeService.findNewUserByParams(map);
        
        setJQGrid(list,
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
