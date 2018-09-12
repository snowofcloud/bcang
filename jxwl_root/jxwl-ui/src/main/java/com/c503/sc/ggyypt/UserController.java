package com.c503.sc.ggyypt;
/*
 * 文件名：LoginController.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2015年11月2日
 * 修改内容：〈修改内容〉
 */

import com.c503.sc.base.service.IBaseService;
import com.c503.sc.jxwl.common.controller.ResultController;
import com.c503.sc.log.LoggingManager;
import com.c503.sc.recvBasic.util.DBManager;
import com.c503.sc.utils.common.SystemContants;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.jasig.cas.client.util.AbstractCasFilter;
import org.jasig.cas.client.validation.Assertion;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * 〈一句话功能简述〉登陆跳转控制层 〈功能详细描述〉
 *
 * @author zz
 * @version [版本号, 2015年11月2日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Controller("ggyyptUserController")
@Scope(value = "prototype")
@RequestMapping(value = "/ggyypt")
public class UserController extends ResultController {

    /**
     * 日志器
     */
    private static final LoggingManager LOGGER = LoggingManager.getLogger(UserController.class);

    @Override
    protected <T> IBaseService<T> getBaseService() {
        return null;
    }

    //    /** 缓存管理器 */
    //    private static final CacheManager MANAGER =
    //        CacheManagerFactory.getInstence().getCacheManager();

    //    /** 获取request */
    //    @Autowired
    //    private HttpServletRequest request;

    /**
     * @return
     * @描述：同步用户信息
     * @author 胡荣
     * @号码：18770031541
     */
    @RequestMapping(value = "/synchronismUser", method = RequestMethod.GET)
    public @ResponseBody
    boolean synchronismUser(HttpServletRequest request) {
        Assertion assertion = (Assertion) request.getSession().getAttribute(AbstractCasFilter.CONST_CAS_ASSERTION);
        AttributePrincipal principal = assertion.getPrincipal();
        String userName = principal.getName();
//		User u = findByUserName(userName);
//		if(u == null){
        Map<String, String> userInfo = getUserBaseInfoFromAcs(userName);
        if (userInfo == null) {
            return false;
        } else {
//				u = new User();
//				u.setId(userInfo.get("userId"));
//				u.setUserName(userInfo.get("loginName"));
//				u.setPassword(userInfo.get("passWord"));
//				u.setRealName(userInfo.get("userName"));
//				addUser(u);

            UserSyncUtil.syncUser(userInfo);
            return true;
        }
//		}else{
//			return true;
//		}
    }

    /**
     * @return
     * @描述：从服务端获取用户基本信息
     * @author 曾斌
     * @号码：15279103623
     */
    public Map<String, String> getUserBaseInfoFromAcs(String userName) {
        Map<String, String> userInfo = null;
        String sql = "SELECT * FROM ac_user r WHERE r.loginName = '" + userName + "'";
        DBManager db = new DBManager(sql);
        try {
            ResultSet set = db.pst.executeQuery();
            userInfo = new HashMap<String, String>();
            while (set.next()) {
                userInfo.put("userName", set.getString("userName"));
                userInfo.put("userId", set.getString("userId"));
                userInfo.put("passWord", set.getString("passWord"));
                userInfo.put("loginName", set.getString("loginName"));
            }
            set.close();
            db.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userInfo;
    }

    @Override
    protected Object show() {
        LOGGER.debug(SystemContants.DEBUG_START);
        return "redirect:/login.html";
    }


    //	@Override
    protected LoggingManager logger() {
        return LOGGER;
    }
}
