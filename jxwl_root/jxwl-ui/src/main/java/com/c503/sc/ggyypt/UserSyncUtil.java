package com.c503.sc.ggyypt;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.c503.sc.base.common.NumberContant;
import com.c503.sc.jxwl.common.constant.DictConstant;
import com.c503.sc.jxwl.common.constant.SysCommonConstant;
import com.c503.sc.jxwl.orgdata.bean.BizUserEntity;
import com.c503.sc.jxwl.orgdata.dao.SysUserDao;
import com.c503.sc.recvBasic.util.DBManager;
import com.c503.sc.utils.basetools.C503StringUtils;
import com.c503.sc.utils.basetools.C503UserUtil;

/**
 * 公共应用平台用户同步工具类
 *
 * @author huangtw
 */

@Component
public class UserSyncUtil {
    private static UserSyncUtil util = null;

    @Resource(name = "sysUserDao")
    private SysUserDao sysUserDao;

    public UserSyncUtil() {
        super();
        util = this;
    }

    /**
     * 同步用户
     *
     * @param user
     */
    public static synchronized void syncUser(Map<String, String> user) {
        if (util == null) {
            // 系统尚未初始化完成
            return;
        }
        if (user == null) {
            // TODO 参数无效
            return;
        }
        // 同步用户信息，当前提供用户不提供企业信息，默认是政府用户
        String loginName = user.get("loginName");
        if (C503StringUtils.isBlank(loginName)) {
            // TODO 参数无效
            return;
        }

        // 注意账号的 机构ID、系统ID,默认11111111
        BizUserEntity dbUser = util.sysUserDao.getUserByAccount(loginName,
                DictConstant.SYS_ID);

        BizUserEntity bizuser = new BizUserEntity();
        bizuser.setAccount(user.get("loginName"));
        bizuser.setName(user.get("userName"));
        // 設置系統id
        bizuser.setSysId(DictConstant.SYS_ID);
        // 设置初始密码11111111
        // 化工企业上级机构（pid）
        bizuser.setOrgId(DictConstant.CHEMICAL_PID);
        if (dbUser == null) {
            // 新增用户
            //TODO 初始密码这里有问题，
            Random ran = new Random();
            int tmp = ran.nextInt(NumberContant.ONE_ZERO_ZERO) + NumberContant.ONE_THOUSAND;
            bizuser.setId(C503StringUtils.createUUID());
            String salt = String.valueOf(tmp);
            String pwd = C503UserUtil.createPassword(SysCommonConstant.INIT_PASSWORD, salt);
            bizuser.setSalt(salt);
            bizuser.setPassword(pwd);
            bizuser.setType(SysCommonConstant.COMMON_USER_TYPE);
//			String companyId = user.get("companyId");//企业ID
            util.sysUserDao.save(bizuser);
            //TODO 获取企业信息，根据企业信息设置角色
            String roleId = DictConstant.GOVERNMENT_USER;
            // 添加用户角色信息
            util.sysUserDao.saveUserRole(C503StringUtils.createUUID(), bizuser.getId(), roleId);
        } else {
            // 更新用户，只更新四个字段
            util.sysUserDao.update(bizuser);
            //TODO 根据企业信息，更新用户角色

        }

    }

    /**
     * 同步用户，当用户不存在的时候
     *
     * @param userName 要同步的用户账号
     */
    public static void syncUser4nonExistent(String userName) {
        if (util == null) {
            // 系统尚未初始化完成
            return;
        }
        if (C503StringUtils.isBlank(userName)) {
            // TODO 参数无效
            return;
        }
        // 同步用户信息，当前提供用户不提供企业信息，默认是政府用户
        BizUserEntity user = util.sysUserDao.getUserByAccount(userName,
                DictConstant.SYS_ID);
        if (user == null) {
            syncUser(util.getUserBaseInfoFromAcs(userName));
        }
    }

    /**
     * @return
     * @描述：从服务端获取用户基本信息
     * @author 曾斌
     * @号码：15279103623
     */

    public Map<String, String> getUserBaseInfoFromAcs(String userName) {
        Map<String, String> userInfo = null;
        String sql = "SELECT * FROM ac_user r WHERE r.loginName = '" + userName
                + "'";
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
}
