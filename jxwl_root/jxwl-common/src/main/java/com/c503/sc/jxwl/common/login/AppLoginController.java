/*
 * 文件名：LoginController.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2015年11月2日
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.common.login;

import java.awt.Font;
import com.c503.sc.jxwl.common.constant.CommonConstant;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jasig.cas.client.authentication.CasToken;
import org.jasig.cas.client.authentication.RemoteAuthentication;
import org.jasig.cas.client.util.MD5Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import sc.c503.authclient.cache.CacheManagerFactory;
import sc.c503.authclient.service.AuthFactory;
import sc.c503.authclient.service.IAuthService;

import com.c503.sc.base.common.ClientConstants;
import com.c503.sc.base.common.CommonConstants;
import com.c503.sc.base.entity.SysLoginParaEntity;
import com.c503.sc.base.entity.SysModuleEntity;
import com.c503.sc.base.entity.common.UserEntity;
import com.c503.sc.base.exception.CustomException;
import com.c503.sc.base.exception.PasswordErrorException;
import com.c503.sc.base.exception.UnknownAccountException;
import com.c503.sc.base.exception.ValidCodeException;
import com.c503.sc.base.service.IBaseService;
import com.c503.sc.jxwl.common.bean.BizUserEntity;
import com.c503.sc.jxwl.common.constant.BizConstants;
import com.c503.sc.jxwl.common.constant.CommonConstant;
import com.c503.sc.jxwl.common.constant.DictConstant;
import com.c503.sc.jxwl.common.constant.SysCommonConstant;
import com.c503.sc.jxwl.common.controller.BaseController;
import com.c503.sc.jxwl.common.controller.ResultController;
import com.c503.sc.jxwl.common.dao.IManagerDao;
import com.c503.sc.jxwl.common.service.IEnterpriseInfoService;
import com.c503.sc.jxwl.common.service.IMiPushService;
import com.c503.sc.jxwl.common.service.ISysMenuService;
import com.c503.sc.jxwl.orgdata.dao.SysUserDao;
import com.c503.sc.jxwl.common.websocket.MonitorWebsocket;
import com.c503.sc.log.ControlLogModel;
import com.c503.sc.log.LoggingManager;
import com.c503.sc.log.resource.ResourceManager;
import com.c503.sc.utils.basetools.C503PropertiesUtils;
import com.c503.sc.utils.basetools.C503StringUtils;
import com.c503.sc.utils.basetools.C503UserUtil;
import com.c503.sc.utils.basetools.C503ValidCodeUtils;
import com.c503.sc.utils.cache.CacheException;
import com.c503.sc.utils.cache.CacheManager;
import com.c503.sc.utils.cache.ICache;
import com.c503.sc.utils.common.NumberContant;
import com.c503.sc.utils.common.SystemContants;
import com.c503.sc.utils.response.ResultMessage;
import com.xiaomi.xmpush.server.Message;
import com.xiaomi.xmpush.server.Result;

/**
 * 〈一句话功能简述〉登陆跳转控制层 〈功能详细描述〉只有驾驶员才能登录
 * 
 * @author zz
 * @version [版本号, 2015年11月2日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Controller
@Scope(value = "prototype")
@RequestMapping(value = "/appLogin")
public class AppLoginController extends ResultController {
    static {
        System.setProperty("java.awt.headless", "true");
    }
    
    /** 日志器 */
    private static final LoggingManager LOGGER =
        LoggingManager.getLogger(AppLoginController.class);
    
    /** 缓存管理器 */
    private static final CacheManager MANAGER =
        CacheManagerFactory.getInstence().getCacheManager();
    
    /** 获取request */
    @Autowired
    private HttpServletRequest request;
    
    /** 获取权限业务务处理接口申明 */
    @Resource(name = "sysMenuService")
    private ISysMenuService sysMenuService;
    
    /** sysUserDao */
    @Resource(name = "sysUserDao")
    private SysUserDao sysUserDao;
    
    /** sysUserDao */
    @Resource(name = "managerDao")
    private IManagerDao managerDao;
    
    /** 企业相关信息服务 */
    @SuppressWarnings("unused")
    @Resource(name = "enterpriseInfoService")
    private IEnterpriseInfoService enterpriseInfoService;
    
    /** miPushService的实例 */
    @Resource(name = "miPushService")
    private IMiPushService miPushService;
    
    /** app账号 */
    /*
     * private String account = null;
     * 
     * public String getAccount() {
     * return account;
     * }
     * 
     * public void setAccount(String account) {
     * this.account = account;
     * }
     */
    
    /**
     * 〈一句话功能简述〉设置app账号 〈功能详细描述〉
     * 
     * @param driverId 身份证id
     * @return 向前台响应查询结果
     * @throws Exception
     *             系统异常
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/setAppAccount", method = RequestMethod.POST)
    @ResponseBody
    public Object setAppAccount(String driverId)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        if (null != driverId && !driverId.equals("")) {
            // 查询account
            String account = this.managerDao.setAppAccount(driverId);
            if (null != account) {
                // 赋值account
                AccountValue.setAccount(account);
            }
        }
        else {
            this.sendCode(CommonConstant.ARGS_INVALID, driverId);
        }
        sendData("ok", CommonConstant.FIND_SUC_OPTION);
        // 发送响应消息
        return sendMessage();
    }
    
    /**
     * 〈一句话功能简述〉设置app账号 〈功能详细描述〉
     * 
     * @param driverId 身份证id
     * @return 向前台响应查询结果
     * @throws Exception
     *             系统异常
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/clearAccountAndCarrierName", method = RequestMethod.POST)
    @ResponseBody
    public Object clearAccountAndCarrierName()
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        AccountValue.setAccount(null);
        AccountValue.setCarrierName(null);
        sendData("ok", CommonConstant.FIND_SUC_OPTION);
        // 发送响应消息
        return sendMessage();
    }
    
    /**
     * 〈一句话功能简述〉设置app账号 〈功能详细描述〉
     * 
     * @param driverId 身份证id
     * @return 向前台响应查询结果
     * @throws Exception
     *             系统异常
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/setCarrierName", method = RequestMethod.POST)
    @ResponseBody
    public Object setCarrierName(String carrierName)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        if (null != carrierName && !carrierName.equals("")) {
            AccountValue.setCarrierName(carrierName);
        }
        else {
            this.sendCode(CommonConstant.ARGS_INVALID, carrierName);
        }
        sendData("ok", CommonConstant.FIND_SUC_OPTION);
        // 发送响应消息
        return sendMessage();
    }
    
    /**
     * 〈一句话功能简述〉加载登录页面返回是否需要验证码 〈功能详细描述〉
     * 
     * @param
     * @return 向前台响应查询结果
     * @throws Exception
     *             系统异常
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/findHasValidate", method = RequestMethod.POST)
    @ResponseBody
    public Object findHasValidate()
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        boolean hasValidate =
            Boolean.valueOf(ResourceManager.getMessage("useValidCode"));
        // 记录操作成功信息
        LOGGER.info(CommonConstant.FIND_SUC_OPTION);
        // 记录程序执行方法结束调试日志
        LOGGER.debug(SystemContants.DEBUG_END);
        sendData(hasValidate, CommonConstant.FIND_SUC_OPTION);
        // 发送响应消息
        return sendMessage();
    }
    
    /**
     * 〈一句话功能简述〉获取验证码图形信息 〈功能详细描述〉
     * 
     * @param response
     *            响应对象
     * @param hostId
     *            请求主机标识符
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/validCode", method = RequestMethod.GET)
    @ResponseBody
    public void validCode(HttpServletResponse response, String hostId) {
        /** 图片宽 */
        int width = NumberContant.EIGTY;
        /** 图片高 */
        int height = NumberContant.TWENTY_SIX;
        /** 干扰线数量 */
        int lineSize = NumberContant.FOURTY;
        /** 随机产生字符数量 */
        int stringNum = NumberContant.FOUR;
        C503ValidCodeUtils randomValidateCode =
            new C503ValidCodeUtils(width, height, lineSize, stringNum);
        // 绘图-BufferedImage类是具有缓冲区的Image类,Image类是用于描述图像信息的类
        BufferedImage image =
            new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
        // 产生Image对象的Graphics对象,该对象可以在图像上进行各种绘制操作
        Graphics g = image.getGraphics();
        g.fillRect(NumberContant.ZERO, NumberContant.ZERO, width, height);
        g.setFont(new Font("Times New Roman", Font.ROMAN_BASELINE,
            NumberContant.EIGHTTEEN));
        g.setColor(randomValidateCode.getRandColor(NumberContant.ONE_ONE_ZERO,
            NumberContant.ONE_THREE_THREE));
        // 绘制干扰线
        for (int i = 0; i <= lineSize; i++) {
            randomValidateCode.drowLine(g);
        }
        // 绘制随机字符
        String randomString = "";
        for (int i = 1; i <= stringNum; i++) {
            randomString = randomValidateCode.drawString(g, randomString, i);
        }
        try {
            // 存储至缓存
            ICache<String, String> cache =
                MANAGER.getCache(SystemContants.SESSION_KEY_VALIDCODE);
            cache.put(hostId, randomString);
            g.dispose();
            // 将内存中的图片通过流动形式输出到客户端
            ImageIO.write(image, "JPEG", response.getOutputStream());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 
     * 〈一句话功能简述〉登陆 〈功能详细描述〉
     * 
     * @param loginPara
     *            登录用户账户
     * @param hostId
     *            请求主机标识符
     * @param response
     *            响应
     * @param regId
     *            设备id
     * @return ResultMessage 响应消息
     * @throws Exception
     *             系统异常
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/login")
    @ResponseBody
    public ResultMessage login(SysLoginParaEntity loginPara, String hostId,
        HttpServletResponse response, String regId)
        throws Exception {
        // 记录程序进入方法调试日志
        LOGGER.debug(SystemContants.DEBUG_START, loginPara);
        /*
         * 检查业务数据库是否存在账号 审核状态是什么 待审核 则审核 已拒绝 则 拒绝 注册 则继续流程
         */
        List<HashMap<String, Object>> result =
            new ArrayList<HashMap<String, Object>>();
        result = this.checkVerifyStatus(loginPara);
        // 账号不存在
        if (result == null || result.size() <= 0) {
            sendCode(BizConstants.ACCOUNT_NOT_EXIST);
            return sendMessage();
            // 账号待审核
        }
        // 是否处于待审核中
        for (int i = 0; i < result.size(); i++) {
            HashMap<String, Object> temp = result.get(i);
            if (temp.get("VERIFY_STATUS")
                .equals(DictConstant.ACCOUNT_VERIFY_WAIT)) {
                sendCode(BizConstants.ACCOUNT_VERIFY_WAIT);
                return sendMessage();
            }
        }
        boolean rejectFlag = false;
        String rejectReason = "";
        // 是否处于拒绝中
        for (int j = 0; j < result.size(); j++) {
            HashMap<String, Object> temp = result.get(j);
            if (temp.get("VERIFY_STATUS")
                .equals(DictConstant.ACCOUNT_VERIFY_REJECT)) {
                rejectReason = (String) temp.get("REJECT_REASON");
                
                rejectFlag = true;
            }
            if (temp.get("VERIFY_STATUS")
                .equals(DictConstant.ACCOUNT_VERIFY_PASS)) {
                rejectFlag = false;
                break;
            }
        }
        if (rejectFlag) {
            sendData(rejectReason, BizConstants.ACCOUNT_VERIFY_REJECTION);
            return this.sendMessage();
        }
        // 账号已通过注册
        IAuthService authService = AuthFactory.getAuthService();
        try {
            // 首先验证所有参数不能为空
            this.checkLoginPara(loginPara);
            // 获取系统注册token
            String systemToken =
                C503PropertiesUtils.getValue(CommonConstants.CLIENT_CONFIG_FILENAME,
                    "bizsystem.auth.systemtoken");
            loginPara.setSystemToken(systemToken);
            // 清理cookie
            // this.clearOldCache(authService);
            // 验证登陆
            this.validateLogin(loginPara, hostId, authService, response);
        }
        catch (ValidCodeException ve) {
            // 验证码错误；登陆失败
            LOGGER.error(CommonConstants.LOGIN_ERROR_VALICODE, ve, loginPara);
            // 设置响应消息
            sendCode(CommonConstants.LOGIN_ERROR_VALICODE);
            return sendMessage();
        }
        catch (UnknownAccountException ue) {
            // 不存在该账号； 登陆失败
            LOGGER.error(CommonConstants.LOGIN_ERROR_NOUSER, ue, loginPara);
            // 设置响应消息
            sendCode(BizConstants.ACCOUNT_NOT_EXIST);
            return sendMessage();
        }
        catch (PasswordErrorException pe) {
            // 用户名或密码错误；登陆失败
            LOGGER.error(CommonConstants.LOGIN_ERROR_PASS,
                pe,
                loginPara.getPassword());
            // 设置响应消息
            sendCode(BizConstants.ACCOUNT_PASSWOR_CONFLICT);
            return sendMessage();
        }
        catch (Exception ne) {
            LOGGER.error(CommonConstants.LOGIN_ERROR_NOUSER, ne, loginPara);
            // 设置响应消息
            sendCode(BizConstants.SYSTEM_WRONG);
            return sendMessage();
        }
        LOGGER.debug(SystemContants.DEBUG_END, loginPara);
        
        String loginAccount = loginPara.getAccount();
        String preLoginState = this.managerDao.findLoginStateByAccount(loginAccount);
        // 更改app用户注册表的登录状态 为0 表示已登录
        this.managerDao.updateLoginState(loginAccount);
        // 抢占式登录处理 根据account查询regid 如果一样 则直接登录 如果不一样则登录且通知之前登录用户
        String regIdFromDB = this.managerDao.findRegIdByAccount(loginAccount);
        LOGGER.info(SystemContants.DEBUG_END, "regIdFromDB  " + regIdFromDB);
        // 更新
        if (regId != null && regIdFromDB != null && !regId.equals(regIdFromDB) && "0".equals(preLoginState)) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("regId", regId);
            map.put("account", loginPara.getAccount());
            this.managerDao.updateRegId(map);
            final String regIdValue = regIdFromDB;
            final String account = loginAccount;
            // 推送离线
            if (regIdValue != null) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            miPushOff(regIdValue,account);
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                
            }
        }
        Map<String, String> map0 = new HashMap<String, String>();
        map0.put("regId", regId);
        map0.put("account", loginPara.getAccount());
        //没有发生抢占登录时也必须保存本次登录的regId，而且要位于抢占登录处理之后，
        //原因在于抢占登录必须先获取旧的regId即regIdFromDB
        this.managerDao.clearAllSameRegIds(regId,loginAccount);
        this.managerDao.updateRegId(map0);
        // websocket推送
        if (!AccountValue.isCarrierNameNull()
            && loginAccount.equals(AccountValue.getAccount())) {
            String loginState = "0";
            MonitorWebsocket.notifyLoginState(loginState);
        }
        // sendData(loginPara, CommonConstant.FIND_SUC_OPTION);
        return sendMessage();
    }
    
    /**
     * 
     * 〈一句话功能简述〉退出系统 〈功能详细描述〉
     * 
     * @param account
     *            account
     * @return 响应消息
     * @throws Exception
     *             系统异常
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/exitForApp")
    @ResponseBody
    public ResultMessage exitForApp(String account)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        // 更改app用户注册表的登录状态 为1 表示退出
        this.managerDao.updateExitState(account);
        // websocket推送
        if (!AccountValue.isAccountNull()
            && account.equals(AccountValue.getAccount())) {
            String loginState = "1";
            MonitorWebsocket.notifyLoginState(loginState);
        }
        // 操作成功
        sendCode(CommonConstants.SUC_OPTION, CommonConstant.CANCLE_SUC_OPTION);
        LOGGER.debug(SystemContants.DEBUG_END);
        return sendMessage();
    }
    
    /**
     * 
     * 〈一句话功能简述〉查询app登录状态 〈功能详细描述〉
     * 
     * @param driverid
     *            driverid
     * @return 响应消息
     * @throws Exception
     *             系统异常
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/findLoginState")
    @ResponseBody
    public ResultMessage findLoginState(String driverid)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        // 查询登录状态
        String result = this.managerDao.findLoginState(driverid);
        if (result == null) {
            result = "1";
        }
        // 操作成功
        sendData(result, CommonConstants.SUC_OPTION);
        LOGGER.debug(SystemContants.DEBUG_END);
        return sendMessage();
    }
    
    /**
     * 
     * 〈一句话功能简述〉退出系统 〈功能详细描述〉
     * 
     * @param response
     *            响应
     * @return 响应消息
     * @throws Exception
     *             系统异常
     * @see [类、类#方法、类#成员]
     */
    /*
     * @RequestMapping(value = "/exit")
    @ResponseBody
    public ResultMessage exitLogin(HttpServletResponse response)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        // 判断token是否存在
        String oldtoken = getToken();
        // 清理原缓存
        if (!C503StringUtils.isBlank(oldtoken)) {
            IAuthService authService = AuthFactory.getAuthService();
            authService.clearAuthInfo(oldtoken, true);
        }
        // 退出之前或跳转之前先获取cookie
        Cookie[] cookies = getRequest().getCookies();
        if (null != cookies && cookies.length > NumberContant.ZERO) {
            for (Cookie cookie : cookies) {
                if (C503StringUtils.equals(CommonConstants.COOKIE_TOKEN,
                    cookie.getName())) {
                    // 设置cookies过期
                    cookie.setMaxAge(NumberContant.ZERO);
                    response.addCookie(cookie);
                }
            }
        }
        // 操作成功
        sendCode(CommonConstants.SUC_OPTION);
        LOGGER.debug(SystemContants.DEBUG_END);
        return sendMessage();
    }*/
    
    /**
     * 
     * 〈一句话功能简述〉无权限请求转发 〈功能详细描述〉非ajax请求
     * 
     * @return 响应消息页面
     * @throws Exception
     *             系统异常
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/noAuthToPage")
    public String noAuthToPage()
        throws Exception {
        return "redirect:/view/error/noAuth.html";
    }
    
    /**
     * 〈一句话功能简述〉无权限重定向 （正常的ajax请求）〈功能详细描述〉
     * 
     * @return 无权限错误码
     * @throws Exception
     *             系统异常
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/noAuthReturn")
    @ResponseBody
    public Object noAuthReturn()
        throws Exception {
        ResultMessage result = new ResultMessage();
        result.setCode(NumberContant.FOUR);
        result.setMsg("you has no authority to access here!");
        result.setData("/view/error/noAuth.html");
        return result;
    }
    
    /**
     * 〈一句话功能简述〉tonken过期之后重新登录的请求转发 〈功能详细描述〉非ajax请求
     * 
     * @return 登录页面
     * @throws Exception
     *             系统异常
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/noLoginToPage")
    public String noLoginToPage()
        throws Exception {
        return "redirect:/login.html";
    }
    
    /**
     * 〈一句话功能简述〉用户未登录或session过期重定向 （正常的ajax请求）〈功能详细描述〉
     * 
     * @return 未登录错误码
     * @throws Exception
     *             系统异常
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/noLoginReturn")
    @ResponseBody
    public Object noLoginReturn()
        throws Exception {
        ResultMessage result = new ResultMessage();
        result.setCode(NumberContant.FIVE);
        result.setMsg("账号已过期,请重新登录!");
        result.setData("login.html");
        return result;
    }
    
    /**
     * 〈一句话功能简述〉获取已登录用户的权限信息（菜单）、角色、用户基本信息〈功能详细描述〉
     * 
     * @return 未登录错误码
     * @throws Exception
     *             系统异常
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/loginUser")
    @ResponseBody
    public Object getLoginUser()
        throws Exception {
        return sendData(setOrgWithMenus(getToken()),
            CommonConstants.LOGIN_SUCESS);
    }
    
    /**
     * {@inheritDoc}
     * 
     */
    @Override
    protected LoggingManager logger() {
        return LOGGER;
    }
    
    /**
     * {@inheritDoc}
     * 
     */
    @Override
    protected <T> IBaseService<T> getBaseService() {
        return null;
    }
    
    /**
     * 〈一句话功能简述〉拦截路径重定向至登陆页面 〈功能详细描述〉session过期或者直接输入地址的拦截跳转
     * 
     * @return 重定向页面
     * @see [类、类#方法、类#成员]
     */
    @Override
    protected String show() {
        // 记录程序进入方法调试日志
        LOGGER.debug(SystemContants.DEBUG_START);
        return "redirect:/login.html";
    }
    
    /**
     * 
     * 〈一句话功能简述〉记录操作日志 〈功能详细描述〉
     * 
     * @param resultCode
     *            是否操作成功
     * @param msgCode
     *            消息码
     * @param loginPara
     *            账户
     * @param args
     *            消息码参数
     * @return 操作日志对象
     * @throws Exception
     *             系统异常
     * @see [类、类#方法、类#成员]
     */
    protected ControlLogModel controlLog(int resultCode, int msgCode,
        SysLoginParaEntity loginPara, Object... args)
        throws Exception {
        return new ControlLogModel(loginPara.getAccount(), null, resultCode,
            logger()).setControlMessage(msgCode, args);
    }
    
    /**
     * 
     * 〈一句话功能简述〉推送被离线消息 〈功能详细描述〉
     * 
     * @param regId
     *            regId
     * @throws Exception
     *             异常
     * @see [类、类#方法、类#成员]
     */
    private void miPushOff(String regId,String account)
        throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("account", account);
        String messagePayload = this.miPushService.getPayload(map,"4");
        String title = "离线";
        String des = "off";
        // 得到message
        Message message = null;
        try {
            message =
                this.miPushService.messageHandle(title,
                    des,
                    messagePayload,
                    false);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        // 发送message 并得到result
        Result result =
            this.miPushService.sendByRegId(message, regId, NumberContant.FIVE);
    }
    
    /**
     * 〈一句话功能简述〉设置机构以及菜单信息 〈功能详细描述〉
     * 
     * @param userToken
     *            用户token
     * @return 登录成功响应数据
     * @throws Exception
     *             系统异常
     * @see [类、类#方法、类#成员]
     */
    private Object setOrgWithMenus(String userToken)
        throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        
        result.put("userInfo", getUser(userToken));
        // 登录成功后跳转地址
        result.put("url", request.getContextPath() + "/view/index.html");
        
        result.put("module", getAllMenus(userToken));
        result.put("systermTime", getSystermTime());
        //单点登录返回token
        result.put("token", userToken);
        
        return result;
    }
    
    /**
     * 〈一句话功能简述〉 获取当前用户所有权限 〈功能详细描述〉
     * 
     * @param userToken
     *            用户token
     * @return 用户权限
     * @throws Exception
     *             系统异常
     * @see [类、类#方法、类#成员]
     */
    private SysModuleEntity getAllMenus(String userToken)
        throws Exception {
        IAuthService authService = AuthFactory.getAuthService();
        
        return authService.getBizUserRightAsTreeByUserToken(userToken);
    }
    
    /**
     * 〈一句话功能简述〉获得系统时间 〈功能详细描述〉
     * 
     * @return 设置当前服务器时间
     * @see [类、类#方法、类#成员]
     */
    private Date getSystermTime() {
        return new Date();
    }
    
    /**
     * 〈一句话功能简述〉checkVerifyStatus 〈功能详细描述〉
     * 
     * @param loginPara
     *            SysLoginParaEntity
     * @return Map<String, Object>
     * @see [类、类#方法、类#成员]
     */
    private List<HashMap<String, Object>> checkVerifyStatus(
        SysLoginParaEntity loginPara) {
        Map<String, Object> map = new HashMap<String, Object>();
        List<HashMap<String, Object>> result =
            new ArrayList<HashMap<String, Object>>();
        map.put("account", loginPara.getAccount());
        result = this.sysMenuService.findStatus(map);
        return result;
    }
    
    /**
     * 检查登陆信息是否完整
     * 
     * @param loginPara
     *            登陆参数
     * @throws Exception
     *             系统异常
     * @see [类、类#方法、类#成员]
     */
    private void checkLoginPara(SysLoginParaEntity loginPara)
        throws Exception {
        
        if (C503StringUtils.isBlank(loginPara.getAccount())) {
            throw new Exception("account is null or empty");
        }
        if (C503StringUtils.isBlank(loginPara.getPassword())) {
            throw new Exception("password is null or empty");
        }
        // if (C503StringUtils.isBlank(loginPara.getValidateCode())) {
        // throw new Exception("validCode is null or empty");
        // }
        
    }
    
    /**
     * 从cookie中清理原缓存
     * 
     * @param authService
     *            认证服务类
     * @see [类、类#方法、类#成员]
     */
    private void clearOldCache(IAuthService authService) {
        // 判断token是否存在
        String oldtoken = getToken();
        // 清理原缓存
        if (!C503StringUtils.isBlank(oldtoken)) {
            try {
                authService.clearAuthInfo(oldtoken, true);
            }
            catch (Exception e) {
                e.printStackTrace();
                LOGGER.error(CommonConstants.SYS_EXCEPTION, e);
            }
        }
    }
    
    /**
     * 验证登陆
     * 
     * @param loginPara
     *            登陆参数
     * @param hostId
     *            请求主机标识符
     * @param authService
     *            认证服务类
     * @param response
     *            响应
     * @throws Exception
     *             系统异常
     * @see [类、类#方法、类#成员]
     */
    private void validateLogin(SysLoginParaEntity loginPara, String hostId,
        IAuthService authService, HttpServletResponse response)
        throws Exception {
        // 验证码验证
//         ICache<String, String> validCodeCache =
//         MANAGER.getCache(SystemContants.SESSION_KEY_VALIDCODE);
//        if (null == hostId) {
//       throw new ValidCodeException("ValidCodeError");
//        }
//       String realValidCode = validCodeCache.get(hostId);
//         // 判断是否使用验证码
//         boolean isUserValidCode =
//        Boolean.valueOf(ResourceManager.getMessage("useValidCode"));
//         if (isUserValidCode
//         && !realValidCode.equalsIgnoreCase(loginPara.getValidateCode())) {
//         throw new ValidCodeException("ValidCodeError");
//        }
       
        try {
            // TODO 验证登录已到Service层
            /*
             * 验证登录逻辑 a、到大数据中心验证 b、如果大数据中心服务不可用怎使用本地验证
             */
            // 2、鉴权操作、获取用户登录token
            // String token = authService.authenticate(loginPara);
            // 单点登录校验start
            // 生成token
            String username = loginPara.getAccount();
            String password = loginPara.getPassword();
            String token = getToken();
            clearOldCache(request,authService);
          //获取业务系统的登录用户
            UserEntity user1 = getUser((HttpServletRequest)request);
            //公共应用平台登录了，业务系统尚未登录或者登录用户不同
            if(user1==null || !username.equals(user1.getAccount())){
                //自动登录
                token = autoLogin(request,  username,password);
            }
            
//            // 单点登录CAS认证逻辑
//            MD5Encoder md5Encoder = new MD5Encoder();
//            String MD5Password = md5Encoder.encode(password);
//            CasToken casToken = new CasToken(username, MD5Password);
//            String token = CasToken.encode(casToken);
//            //  调用接口校验
//            Map<String, Object> map = RemoteAuthentication.authentication(token);
//            // 校验完后更新token
//            System.out.println(map.get("ret"));// 校验结果，ret=0指校验成功，ret=-1指token已经过期，ret=-2指密码错误
//            System.out.println(map.get("msg"));
//            System.out.println(map.get("casToken"));// 校验成功后获取新的token
//            token = map.get("casToken").toString(); 
//            
//            // 单点登录校验end
//            
//            boolean success = map.get("ret").toString().equals("0");
            boolean success = token!=null && token!=""; 
            // 登录成功
            if(success)
            {
                // 3、将token保存到cookie中
                if (!C503StringUtils.isBlank(token)) {
                    // 判断是否是驾驶员
                    BizUserEntity user = this.getUser(token);
                    if (null != user) {
                        if (null != user.getRoleCodes()
                            && !user.getRoleCodes().isEmpty()
                            && !user.getRoleCodes().contains(DictConstant.DRIVER_USER)) {
                            this.sendCode(1, "只有驾驶员才能登录！");
                            return;
                        }
                    }
                    // 3.1将token放cooker.
                    setCookie(response, token);
                    // 获取当前用户信息及权限
                    this.sendData(this.setOrgWithMenus(token),
                        CommonConstants.LOGIN_SUCESS);
                    // 回写Session App 标记
                    request.getSession().setAttribute("AppUser", username);
                    request.getSession().setAttribute("AppFlag", "1");
                }
                else {
                    throw new Exception("user token is null or empty");
                }
            }
            else
            {
                throw new PasswordErrorException("密码错误！");
                
            }
        }
        catch (Exception ex) {
            throw ex;
        }
    }
    
    /**
     * 〈一句话功能简述〉改密 〈功能详细描述〉
     * 
     * @param account
     *            account
     * @param oldPassword
     *            oldPassword
     * @param passwordOne
     *            passwordOne
     * @param passwordTwo
     *            passwordTwo
     * @param response
     *            HttpServletResponse
     * @return ResultMessage
     * @throws Exception
     *             Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/modifypassword", method = {RequestMethod.POST,
        RequestMethod.GET})
    @ResponseBody
    public ResultMessage updatePassWord(String account, String oldPassword,
        String passwordOne, String passwordTwo, HttpServletResponse response)
        throws Exception {
        // 记录程序进入方法调试日志
        LOGGER.debug(SystemContants.DEBUG_START, oldPassword);
        // 判断该账号所属人员以及企业是否存在
        if (!this.sysMenuService.isExist(account)) {
            this.sendCode(CommonConstant.NOT_ACCOUNT_E);
            return sendMessage();
        }
        IAuthService authService = AuthFactory.getAuthService();
        int flag = 1;
        // 判断旧密码是否正确
        Map<String, Object> map =
            this.sysMenuService.getUserByAccount(account, DictConstant.SYS_ID);
        String salt = (String) map.get("salt");
        String oldPwd = (String) map.get("password");
        String pwd = C503UserUtil.createPassword(oldPassword, salt);
        if (!pwd.equals(oldPwd)) {
            sendCode(BizConstants.ACCOUNT_PASSWOR_WRONG);
            return sendMessage();
        }
        
        UserEntity user = null;
        // 2次的新密码 确定是否 一致
        if (!passwordOne.equals(passwordTwo)) {
            flag = CommonConstants.NEW_TWO_PASSWORD_ERROR;
        }
        else {
            String userToken = getToken();
            // 确认 当前 修改 用户 是登录 状态
            if (!C503StringUtils.isBlank(userToken)) {
                /*
                 * user = authService.getUserInfo(userToken); if (null == user)
                 * { flag = CommonConstants.USER_AREADY_LOGIN_OUT; } else {
                 */
                // 调用 authclient 的 业务层 设置密码 处理
                try {
                    flag =
                        authService.setPassword(userToken,
                            oldPassword,
                            passwordOne);
                    System.out.println("flag is :=============******************++++++++++++"
                        + flag);
                }
                catch (Exception e) {
                    throw new CustomException(CommonConstants.SYS_EXCEPTION);
                }
                /* } */
            }
        }
        switch (flag) {
        // 新密码 输入 异常
            case CommonConstants.NEW_TWO_PASSWORD_ERROR:
                sendCode(CommonConstants.NEW_TWO_PASSWORD_ERROR);
                // 用户已下线
            case CommonConstants.USER_AREADY_LOGIN_OUT:
                sendCode(CommonConstants.USER_AREADY_LOGIN_OUT);
                // 访问 失败；
            case CommonConstants.USER_SETPASSWORD_TIMEOUT:
                sendCode(CommonConstants.USER_SETPASSWORD_TIMEOUT);
                // 当前用户的密码错误
            case CommonConstants.USER_PASSWORD_ERROR:
                sendCode(CommonConstants.USER_PASSWORD_ERROR);
                // 操作失败
            case CommonConstants.FAIL_OPTION:
                sendCode(CommonConstants.FAIL_OPTION);
            default:
                break;
        }
        if (flag == 1) {
            // 更新业务数据库表
            map.clear();
            map.put("account", account);
            map.put("password", passwordOne);
            this.sysMenuService.updatePassword(map);
            sendCode(BizConstants.ACCOUNT_PASSWOR_RIGHT);
        }
        
        LOGGER.debug(SystemContants.DEBUG_END);
        return sendMessage();
    }
    
    
    /**
     * 获取当前登录用户信息(业务系统内部的登录用户)
     * 
     * @param token
     *            登录用户token
     * @return boolean true存在登录用户信息 false不存在
     * @see [类、类#方法、类#成员]
     */
    private UserEntity getUser(HttpServletRequest request) {
        UserEntity user = null;
        try {
            String token = BaseController.getToken(request);
            if (!C503StringUtils.isBlank(token)) {
                ICache<String, UserEntity> userCache =
                    MANAGER.getCache(ClientConstants.CACHE_LOGIN_USER);
                user = userCache.get(token);
            }
        }
        catch (CacheException e) {
            e.printStackTrace();
        }
        return user;
    }
    
    
    /**
     * 自动登录，参考 {@link UserController}
     * 
     * @param request
     * @param loginName
     * @throws Exception 
     */
    private String  autoLogin(HttpServletRequest request,String loginName,String password) throws Exception{
        IAuthService authService = AuthFactory.getAuthService();
        SysLoginParaEntity param = new SysLoginParaEntity();
        param.setAccount(loginName);
        param.setPassword(password);//默认8个1，临时
        String systemToken =
                C503PropertiesUtils.getValue(CommonConstants.CLIENT_CONFIG_FILENAME,
                        "bizsystem.auth.systemtoken");
        //设置业务系统token
        param.setSystemToken(systemToken);
        //清除历史token
        clearOldCache(request, authService);
        //登录
        String token = authService.authenticate(param);
        if (!C503StringUtils.isBlank(token)) {
            //缓存当前自动登录的token，用于第一次自动登录时，无法通过cookie获取token的问题
            request.getSession().setAttribute("tokenTemp", token);
        }
        return token ;
    
    }
    /**
     * 从cookie中清理原缓存
     * 
     * @param authService 认证服务类
     * @see [类、类#方法、类#成员]
     */
    private void clearOldCache(HttpServletRequest request,IAuthService authService) {
        // 判断token是否存在
        String oldtoken = BaseController.getToken(request);
        // 清理原缓存
        if (!C503StringUtils.isBlank(oldtoken)) {
            try {
                authService.clearAuthInfo(oldtoken, true);
                request.getSession().removeAttribute("tokenTemp");
            }
            catch (Exception e) {
                e.printStackTrace();
                LOGGER.error(CommonConstants.SYS_EXCEPTION, e);
            }
        }
    }
    
  
}
