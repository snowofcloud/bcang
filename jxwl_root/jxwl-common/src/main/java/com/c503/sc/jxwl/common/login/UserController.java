/*
 * 文件名：LoginController.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2015年11月2日
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.common.login;

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
import com.c503.sc.jxwl.common.bean.EnterpriseEntity;
import com.c503.sc.jxwl.common.constant.CommonConstant;
import com.c503.sc.jxwl.common.constant.DictConstant;
import com.c503.sc.jxwl.common.controller.ResultController;
import com.c503.sc.jxwl.common.filter.GGYYPTcfg;
import com.c503.sc.jxwl.common.service.IEnterpriseInfoService;
import com.c503.sc.jxwl.common.service.ISysMenuService;
import com.c503.sc.jxwl.orgdata.service.ISysOrgTypeService;
import com.c503.sc.log.ControlLogModel;
import com.c503.sc.log.LoggingManager;
import com.c503.sc.log.resource.ResourceManager;
import com.c503.sc.utils.basetools.C503PropertiesUtils;
import com.c503.sc.utils.basetools.C503StringUtils;
import com.c503.sc.utils.basetools.C503ValidCodeUtils;
import com.c503.sc.utils.cache.CacheManager;
import com.c503.sc.utils.cache.ICache;
import com.c503.sc.utils.common.NumberContant;
import com.c503.sc.utils.common.SystemContants;
import com.c503.sc.utils.response.ResultMessage;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import sc.c503.authclient.cache.CacheManagerFactory;
import sc.c503.authclient.service.AuthFactory;
import sc.c503.authclient.service.IAuthService;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//import com.c503.sc.jxwl.common.service.IEnterpriseService;

/**
 * 〈一句话功能简述〉登陆跳转控制层 〈功能详细描述〉
 *
 * @author zz
 * @version [版本号, 2015年11月2日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Controller
@Scope(value = "prototype")
@RequestMapping(value = "/user")
public class UserController extends ResultController {
    static {
        System.setProperty("java.awt.headless", "true");
    }

    /**
     * 日志器
     */
    private static final LoggingManager LOGGER = LoggingManager.getLogger(UserController.class);

    /**
     * 缓存管理器
     */
    private static final CacheManager MANAGER = CacheManagerFactory.getInstence().getCacheManager();

    /**
     * 企业信息业务接口
     */
    @Resource
    private IEnterpriseInfoService enterpriseInfoService;

    /**
     * 获取request
     */
    @Autowired
    private HttpServletRequest request;

    /**
     * 获取权限业务务处理接口申明
     */
    @SuppressWarnings("unused")
    @Resource(name = "sysMenuService")
    private ISysMenuService sysMenuService;

    @Resource(name = "sysOrgTypeService")
    private ISysOrgTypeService sysOrgTypeService;

    //
    // /**
    // * 〈一句话功能简述〉获取session 〈功能详细描述〉
    // *
    // * @return session对象
    // * @see [类、类#方法、类#成员]
    // */
    // protected HttpSession getSession() {
    // return getRequest().getSession();
    // }

    @RequestMapping(value = "/toMain")
    public String toMain(){
        return "redirect:/index.html";
    }



    //加载登录页面返回是否需要验证码

    /**
     * 〈一句话功能简述〉加载登录页面返回是否需要验证码
     * 〈功能详细描述〉
     *
     * @param
     * @return 向前台响应查询结果
     * @throws Exception 系统异常
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/findHasValidate", method = RequestMethod.POST)
    @ResponseBody
    public Object findHasValidate() throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        boolean hasValidate = Boolean.valueOf(ResourceManager.getMessage("useValidCode"));
        // 记录操作成功信息
        LOGGER.info(CommonConstant.FIND_SUC_OPTION);
        // 记录程序执行方法结束调试日志
        LOGGER.debug(SystemContants.DEBUG_END);
        sendData(hasValidate, CommonConstant.FIND_SUC_OPTION);
        // 发送响应消息
        return sendMessage();
    }

    //获取验证码图形信息

    /**
     * 〈一句话功能简述〉获取验证码图形信息 〈功能详细描述〉
     *
     * @param response 响应对象
     * @param hostId   请求主机标识符
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
        C503ValidCodeUtils randomValidateCode = new C503ValidCodeUtils(width, height, lineSize, stringNum);
        // 绘图-BufferedImage类是具有缓冲区的Image类,Image类是用于描述图像信息的类
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
        // 产生Image对象的Graphics对象,该对象可以在图像上进行各种绘制操作
        Graphics g = image.getGraphics();
        g.fillRect(NumberContant.ZERO, NumberContant.ZERO, width, height);
        g.setFont(new Font("Times New Roman", Font.ROMAN_BASELINE, NumberContant.EIGHTTEEN));
        g.setColor(randomValidateCode.getRandColor(NumberContant.ONE_ONE_ZERO, NumberContant.ONE_THREE_THREE));
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
            ICache<String, String> cache = MANAGER.getCache(SystemContants.SESSION_KEY_VALIDCODE);
            cache.put(hostId, randomString);
            g.dispose();
            // 将内存中的图片通过流动形式输出到客户端
            ImageIO.write(image, "JPEG", response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //登陆

    /**
     * 〈一句话功能简述〉登陆
     * 〈功能详细描述〉
     *
     * @param loginPara 登录用户账户
     * @param hostId    请求主机标识符
     * @param response  响应
     * @param isPC      isPC
     * @return ResultMessage 响应消息
     * @throws Exception 系统异常
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public ResultMessage login(SysLoginParaEntity loginPara, String hostId, String isPC, HttpServletResponse response)
            throws Exception {
        // 记录程序进入方法调试日志
        LOGGER.debug(SystemContants.DEBUG_START, loginPara);
        IAuthService authService = AuthFactory.getAuthService();
        Map<String, Object> result = new HashMap<String, Object>();
        String url = GGYYPTcfg.getProperty("unauthorized-url");
        try {
            checkLoginPara(loginPara);
            // 获取系统注册token
            String systemToken =
                    C503PropertiesUtils.getValue(CommonConstants.CLIENT_CONFIG_FILENAME,
                            "bizsystem.auth.systemtoken");
            loginPara.setSystemToken(systemToken);
            // 清理cookie
            clearOldCache(authService);
            // 验证登陆
            validateLogin(loginPara, hostId, authService, response);
        } catch (ValidCodeException ve) {
            // 验证码错误；登陆失败
            LOGGER.error(CommonConstant.LOGIN_ERROR_VALICODE, ve, loginPara);
            // 设置响应消息
            sendCode(CommonConstant.LOGIN_ERROR_VALICODE, "验证码错误");
            result.put("url", url);
            result.put("systermTime", getSystermTime());
            sendData(result, CommonConstant.LOGIN_ERROR_VALICODE, "验证码错误");
        } catch (UnknownAccountException ue) {
            // 不存在该账号； 登陆失败
            LOGGER.error(CommonConstant.LOGIN_ERROR_NOUSER, ue, loginPara);
            // 设置响应消息
            sendCode(CommonConstant.LOGIN_ERROR_NOUSER, "不存在该账号");
            result.put("url", url);
            result.put("systermTime", getSystermTime());
            sendData(result, CommonConstant.LOGIN_ERROR_NOUSER, "不存在该账号");
        } catch (PasswordErrorException pe) {
            // 用户名或密码错误；登陆失败
            LOGGER.error(CommonConstant.LOGIN_ERROR_PASS, pe, loginPara.getPassword());
            // 设置响应消息
            sendCode(CommonConstant.LOGIN_ERROR_PASS, "密码错误");
            result.put("url", url);
            result.put("systermTime", getSystermTime());
            sendData(result, CommonConstant.LOGIN_ERROR_PASS, "密码错误");
        } catch (Exception ne) {
            LOGGER.error(CommonConstant.SYS_EXCEPTION, ne, loginPara);
            sendCode(CommonConstant.SYS_EXCEPTION, "系统错误");
            result.put("url", url);
            result.put("systermTime", getSystermTime());
            sendData(result, CommonConstant.SYS_EXCEPTION, "系统错误");
        }

        LOGGER.debug(SystemContants.DEBUG_END, loginPara);
        return sendMessage();
    }

    //退出系统

    /**
     * 〈一句话功能简述〉退出系统
     * 〈功能详细描述〉
     *
     * @param response 响应
     * @return 响应消息
     * @throws Exception 系统异常
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/exit")
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
    }

    ///无权限请求转发

    /**
     * 〈一句话功能简述〉无权限请求转发
     * 〈功能详细描述〉非ajax请求
     *
     * @return 响应消息页面
     * @throws Exception 系统异常
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/noAuthToPage")
    public String noAuthToPage()
            throws Exception {
        return "redirect:/view/error/noAuth.html";
    }

    //无权限重定向

    /**
     * 〈一句话功能简述〉无权限重定向 （正常的ajax请求）〈功能详细描述〉
     *
     * @return 无权限错误码
     * @throws Exception 系统异常
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

    //tonken过期之后重新登录的请求转发

    /**
     * 〈一句话功能简述〉tonken过期之后重新登录的请求转发
     * 〈功能详细描述〉非ajax请求
     *
     * @return 登录页面
     * @throws Exception 系统异常
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/noLoginToPage")
    public String noLoginToPage()
            throws Exception {
        return "redirect:/login.html";
    }

    //用户未登录或session过期重定向

    /**
     * 〈一句话功能简述〉用户未登录或session过期重定向 （正常的ajax请求）〈功能详细描述〉
     *
     * @return 未登录错误码
     * @throws Exception 系统异常
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

    //获取已登录用户的权限信息（菜单）、角色、用户基本信息

    /**
     * 〈一句话功能简述〉获取已登录用户的权限信息（菜单）、角色、用户基本信息〈功能详细描述〉
     *
     * @return 未登录错误码
     * @throws Exception 系统异常
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
     */
    @Override
    protected LoggingManager logger() {
        return LOGGER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected <T> IBaseService<T> getBaseService() {
        return null;
    }

    //拦截路径重定向至登陆页面

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

    //记录操作日志

    /**
     * 〈一句话功能简述〉记录操作日志 〈功能详细描述〉
     *
     * @param resultCode 是否操作成功
     * @param msgCode    消息码
     * @param loginPara  账户
     * @param args       消息码参数
     * @return 操作日志对象
     * @throws Exception 系统异常
     * @see [类、类#方法、类#成员]
     */
    protected ControlLogModel controlLog(int resultCode, int msgCode,
                                         SysLoginParaEntity loginPara, Object... args)
            throws Exception {
        return new ControlLogModel(loginPara.getAccount(), null, resultCode,
                logger()).setControlMessage(msgCode, args);
    }

    //设置机构以及菜单信息

    /**
     * 〈一句话功能简述〉设置机构以及菜单信息
     * 〈功能详细描述〉
     *
     * @param userToken 用户token
     * @return 登录成功响应数据
     * @throws Exception 系统异常
     * @see [类、类#方法、类#成员]
     */
    private Object setOrgWithMenus(String userToken)
            throws Exception {
        String url = GGYYPTcfg.getProperty("unauthorized-url");
        Map<String, Object> result = new HashMap<String, Object>();
        BizUserEntity userInfo = getUser(userToken);
        SysModuleEntity module = this.getAllMenus(userToken);
        boolean flag = false;
        if (userToken == null || userInfo == null) {
            result.put("url", url);
            result.put("systermTime", getSystermTime());
            return result;
        }
        if (module == null) {
            result.put("url", url);
            result.put("systermTime", getSystermTime());
            return result;
        }
        //获取当前登录人的角色
        List<String> roles = userInfo.getRoleCodes();
        //当前角色下的用户进行查询 
        if (roles.indexOf(DictConstant.LOGISTICS_ENTERPRISE_USER) > -1
                || roles.indexOf(DictConstant.CHEMICAL_ENTERPRISE_USER) > -1) {
//          OrganizationEntity org = bizHandleService.findOrgByOrgId(orgId);
            String corporateCode = userInfo.getCorporateCode();
            if (StringUtils.isEmpty(corporateCode)) {
                //如果从认证获取的机构是空的，即机构本身已经被删除了，那么也判断为用户无权限登录
                result.put("url", url);
                result.put("systermTime", getSystermTime());
                return result;
            }
            List<EnterpriseEntity> enlist = enterpriseInfoService.findEnterpriseByCorporateCode(corporateCode);
            if (enlist.size() > 0) {
                flag = true;
            }
            if (!flag) {
                result.put("url", url);
                result.put("systermTime", getSystermTime());
                return result;
            }
        }
        // 登录成功后跳转地址
        String userAgent = request.getHeader("User-Agent");
        List<String> list = getRoleById(getUser(userToken).getId());
        if (StringUtils.isNotEmpty(userAgent)) {
            // pc端系统判断
            if (-1 != userAgent.indexOf("Windows")) {
                if (list != null && list.size() > 0
                        && "卡口员角色".equals(list.get(0))) {
                    result.put("url", request.getContextPath()
                            + "/view/bayonet/bayonetVerify.html");
                } else {
                    result.put("url", request.getContextPath()
                            + "/view/index.html");
                }

                // 手机端
            } else {
                result.put("url", request.getContextPath()
                        + "/appview/enterpriseMessage.html");
            }
        } else {
            result.put("url", request.getContextPath() + "/view/index.html");
        }
        result.put("userInfo", userInfo);
        result.put("module", module);
        result.put("systermTime", getSystermTime());
        return result;
    }

    //获取当前用户所有权限

    /**
     * 〈一句话功能简述〉 获取当前用户所有权限
     * 〈功能详细描述〉
     *
     * @param userToken 用户token
     * @return 用户权限
     * @throws Exception 系统异常
     * @see [类、类#方法、类#成员]
     */
    private SysModuleEntity getAllMenus(String userToken)
            throws Exception {
        IAuthService authService = AuthFactory.getAuthService();
        return authService.getBizUserRightAsTreeByUserToken(userToken);
    }

    //获得系统时间

    /**
     * 〈一句话功能简述〉获得系统时间
     * 〈功能详细描述〉
     *
     * @return 设置当前服务器时间
     * @see [类、类#方法、类#成员]
     */
    private Date getSystermTime() {
        return new Date();
    }

    //检查登陆信息是否完整

    /**
     * 检查登陆信息是否完整
     *
     * @param loginPara 登陆参数
     * @throws Exception 系统异常
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
        if (C503StringUtils.isBlank(loginPara.getValidateCode())) {
            throw new Exception("validCode is null or empty");
        }

    }

    //从cookie中清理原缓存

    /**
     * 从cookie中清理原缓存
     *
     * @param authService 认证服务类
     * @see [类、类#方法、类#成员]
     */
    private void clearOldCache(IAuthService authService) {
        // 判断token是否存在
        String oldtoken = getToken();
        // 清理原缓存
        if (!C503StringUtils.isBlank(oldtoken)) {
            try {
                authService.clearAuthInfo(oldtoken, true);
            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.error(CommonConstants.SYS_EXCEPTION, e);
            }
        }
    }

    //验证登陆

    /**
     * 验证登陆
     *
     * @param loginPara   登陆参数
     * @param hostId      请求主机标识符
     * @param authService 认证服务类
     * @param response    响应
     * @throws Exception 系统异常
     * @see [类、类#方法、类#成员]
     */
    private void validateLogin(SysLoginParaEntity loginPara, String hostId,
                               IAuthService authService, HttpServletResponse response)
            throws Exception {
        // 验证码验证
        ICache<String, String> validCodeCache =
                MANAGER.getCache(SystemContants.SESSION_KEY_VALIDCODE);
        if (null == hostId) {
            throw new ValidCodeException("ValidCodeError");
        }
        String realValidCode = validCodeCache.get(hostId);
        // 判断是否使用验证码
        boolean isUserValidCode =
                Boolean.valueOf(ResourceManager.getMessage("useValidCode"));
        if (isUserValidCode
                && !realValidCode.equalsIgnoreCase(loginPara.getValidateCode())) {
            throw new ValidCodeException("ValidCodeError");
        }
        // 2、鉴权操作、获取用户登录token
        String token = authService.authenticate(loginPara);
        // 3、将token保存到cookie中
        if (!C503StringUtils.isBlank(token)) {
            // 3.1将token放cooker.
            setCookie(response, token);
            // 3.2 返回成功消息
            controlLog(ControlLogModel.CONTROL_RESULT_SUCCESS,
                    CommonConstants.LOGIN_SUCESS,
                    loginPara).recordLog();
            // 获取当前用户信息及权限
            sendData(setOrgWithMenus(token), CommonConstants.LOGIN_SUCESS);
        } else {
            throw new Exception("user token is null or empty");
        }

    }

    //修改密码

    /**
     * 〈一句话功能简述〉改密
     * 〈功能详细描述〉
     *
     * @param oldPassword oldPassword
     * @param passwordOne passwordOne
     * @param passwordTwo passwordTwo
     * @param response    HttpServletResponse
     * @return ResultMessage
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/modifypassword", method = RequestMethod.POST)
    @ResponseBody
    public ResultMessage updatePassWord(String oldPassword, String passwordOne,
                                        String passwordTwo, HttpServletResponse response)
            throws Exception {
        // 记录程序进入方法调试日志
        LOGGER.debug(SystemContants.DEBUG_START, oldPassword);
        IAuthService authService = AuthFactory.getAuthService();
        int flag = 1;

        UserEntity user = null;
        // 2次的新密码 确定是否 一致
        if (!passwordOne.equals(passwordTwo)) {
            flag = CommonConstants.NEW_TWO_PASSWORD_ERROR;
        } else {
            String userToken = getToken();
            // 确认 当前 修改 用户 是登录 状态
            if (!C503StringUtils.isBlank(userToken)) {
                user = authService.getUserInfo(userToken);
                if (null == user) {
                    flag = CommonConstants.USER_AREADY_LOGIN_OUT;
                } else {
                    // 调用 authclient 的 业务层 设置密码 处理
                    try {
                        flag =
                                authService.setPassword(userToken,
                                        oldPassword,
                                        passwordOne);
                        System.out.println("flag is :=============************************++++++++++++"
                                + flag);
                    } catch (Exception e) {
                        throw new CustomException(CommonConstants.SYS_EXCEPTION);
                    }
                }
            }
        }
        switch (flag) {
            // 新密码 输入 异常
            case CommonConstants.NEW_TWO_PASSWORD_ERROR:
                throw new CustomException(
                        CommonConstants.NEW_TWO_PASSWORD_ERROR);
                // 用户已下线
            case CommonConstants.USER_AREADY_LOGIN_OUT:
                throw new CustomException(CommonConstants.USER_AREADY_LOGIN_OUT);
                // 访问 失败；
            case CommonConstants.USER_SETPASSWORD_TIMEOUT:
                throw new CustomException(
                        CommonConstants.USER_SETPASSWORD_TIMEOUT);
                // 当前用户的密码错误
            case CommonConstants.USER_PASSWORD_ERROR:
                throw new CustomException(CommonConstants.USER_PASSWORD_ERROR);
                // 操作失败
            case CommonConstants.FAIL_OPTION:
                throw new CustomException(CommonConstants.FAIL_OPTION);
            default:
                break;
        }
        if (flag == 1) {
            sendCode(flag);
        }

        LOGGER.debug(SystemContants.DEBUG_END);
        return sendMessage();
    }

    // /**
    // *
    // * 〈一句话功能简述〉判断手机端登录用户是否为政府账号，只有政府账号才可登录微信公众号，物流和化工账号都不能登录微信公众号
    // * 〈功能详细描述〉
    // *
    // * @param loginPara loginPara
    // * @param authService authService
    // * @return 该用户是否是政府账号
    // * @see [类、类#方法、类#成员]
    // */
    // private boolean isGovernment(SysLoginParaEntity loginPara,
    // IAuthService authService) {
    // boolean isRole = false;
    // List<String> userRoles = new ArrayList<String>();
    // try {
    // // userRoles = this.getUser().getRoleCodes();
    // String token = authService.authenticate(loginPara);
    // userRoles = this.getUser(token).getRoleCodes();
    // }
    // catch (Exception e) {
    // e.printStackTrace();
    // }
    // int j = userRoles.size();
    // for (int i = 0; i < j; i++) {
    // if (DictConstant.GOVERNMENT_USER.equals(userRoles.get(i))) {
    // isRole = true;
    // break;
    // }
    //
    // }
    // return isRole;
    // }
}
