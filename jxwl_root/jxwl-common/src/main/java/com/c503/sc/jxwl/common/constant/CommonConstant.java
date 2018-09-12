/*
 * 文件名：CommonConstants.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉模块通用系统常量
 * 修改时间：2015年7月9日
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.common.constant;

/**
 * 〈一句话功能简述〉模块通用系统常量 〈功能详细描述〉
 * 
 * @author duanhy
 * @version [版本号, 2015年7月9日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public final class CommonConstant {
    
    /** 参数无效 */
    public static final int ARGS_INVALID = 110000001;
    
    /** 保存成功 */
    public static final int SAVE_SUC_OPTION = 110000002;
    
    /** 编辑成功 */
    public static final int UPDATE_SUC_OPTION = 110000003;
    
    /** 查询成功 */
    public static final int FIND_SUC_OPTION = 110000004;
    
    /** 删除成功 */
    public static final int DELETE_SUC_OPTION = 110000005;
    
    /** 导出成功 */
    public static final int EXPORT_SUC_OPTION = 110000006;
    
    /** 上传成功 */
    public static final int UPLOAD_SUC_OPTION = 110000007;
    
    /** 导入成功 */
    public static final int IMPORT_SUC_OPTION = 110000008;
    
    /** 提交成功 */
    public static final int SUBMIT_SUC_OPTION = 110000009;
    
    /** 审核成功 */
    public static final int VERIFY_SUC_OPTION = 110000010;
    
    /** 下达成功 */
    public static final int SENDDOWN_SUC_OPTION = 110000011;
    
    /** 确认成功 */
    public static final int CONFIRM_SUC_OPTION = 110000012;
    
    /** 注销成功 */
    public static final int CANCLE_SUC_OPTION = 110000013;
    
    /** 注册成功 */
    public static final int REGISTER_SUC_OPTION = 110000014;
    
    /** 升级成功 */
    public static final int UPGRADE_SUC_OPTION = 110000015;
    
    /** 终端开户成功 */
    public static final int OPEN_SUC_OPTION = 110000016;
    
    /** 终端参数设置成功 */
    public static final int SETPARAM_SUC_OPTION = 110000017;
    
    /** 消费成功 */
    public static final int CONSUMER_SUC_OPTION = 110000018;
    
    /** 一键同步成功 */
    public static final int SYNCHRONIZE_SUC_OPTION = 110000019;
    
    /** 一键同步失败 */
    public static final int SYNCHRONIZE_FAIL_OPTION = 310000018;
    
    /** 未运输运单，无法报警 */
    public static final int CANNOTE_ALARM = 310000019;
    
    /** 终端参数设置失败 */
    public static final int SETPARAM_FAIL_OPTION = 210000017;
    
    /** 存在取货中、送货中运单 */
    public static final int WAYBILL_STATUS = 110000018;
    
    /** 表单验证失败 */
    public static final int FORMVALID_FAIL_OPTION = 210000001;
    
    /** 保存失败 */
    public static final int SAVE_FAIL_OPTION = 210000002;
    
    /** 编辑失败 */
    public static final int UPDATE_FAIL_OPTION = 210000003;
    
    /** 删除失败 */
    public static final int DELETE_FAIL_OPTION = 210000004;
    
    /** 查询终端参数失败 */
    public static final int FINDPARAM_FAIL_OPTION = 210000005;
    
    /** 关键字段输入异常 */
    public static final int IMPORTANT_KEY_ENTER_E = 300000002;
    
    /** 关键字段未填写 */
    public static final int IMPORTANT_KEY_NOT_ENTER_E = 300000003;
    
    /** 数据重复录入 */
    public static final int DATA_ENTER_REPEAT_E = 300000004;
    
    /** 其他异常错误 */
    public static final int OTHER_E = 300000005;
    
    /** 登陆成功 */
    public static final int LOGIN_SUCESS = 100000002;
    
    /** 退出成功 */
    public static final int EXITLOGIN_SUCESS = 100000003;
    
    /** 密码错误 */
    public static final int LOGIN_ERROR_PASS = 3;
    
    /** 验证码错误 */
    public static final int LOGIN_ERROR_VALICODE = 5;
    
    /** 不存在该账号 */
    public static final int LOGIN_ERROR_NOUSER = 2;
    
    /** 非政府用户没有权限登录微信公众号 */
    public static final int LOGIN_ERROR_ONLY_GOVERN_ALLOWED = 200000004;
    
    /** 未运输运单，无法回复调度信息 */
    public static final int CARDISPATCH_ALARM = 200000005;
    
    /** 无权限删除 */
    public static final int DELETE_NOALLOW_EXCEPTION = 310000002;
    
    /** 无权限更新 */
    public static final int UPDATE_NOALLOW_EXCEPTION = 310000003;
    
    /** 无企业信息 */
    public static final int NOT_ENTERPRISE_INFO_E = 310000004;
    
    /** 无权限查看 */
    public static final int CHECK_NOALLOW_EXCEPTION = 310000005;
    
    /** 信息不存在 */
    public static final int NOT_INFO_E = 310000007;
    
    /** 密码长度8到16位 */
    public static final int PASSWORD_LENGTH = 300110008;
    
    /** 当前用户已下线 */
    public static final int USER_AREADY_LOGIN_OUT = 300110006;
    
    /** 该账号已注销 */
    public static final int NOT_ACCOUNT_E = 310000009;
    
    /** 该信息已审核 */
    public static final int ACCOUNT_HAS_VERIFY = 310000010;
    
    /** 系统异常，请联系管理员 */
    public static final int SYS_EXCEPTION = 300000001;
    
    /**
     * 左花括号
     */
    public static final String SYSTEM_CONTANT_BRACE_LEFT = "{";
    
    /**
     * 右花括号
     */
    public static final String SYSTEM_CONTANT_BRACE_RIGHT = "}";
    
    /**
     * 字符串默认值
     */
    public static final String DEFAULT_STRING_VALUE = "--";
    
    /**
     * 等号 =
     */
    public static final String SYSTEM_CONTANT_EQUALS = "=";
    
    /**
     * 逗号 ,
     */
    public static final String SYSTEM_CONTANT_COMMA = ",";
    
    /**
     * 空格
     */
    public static final String SYSTEM_CONTANT_BLANK = " ";
    
    /**
     * 方括号 [
     */
    public static final String SYSTEM_CONTANT_BRACKETS_LEFT = "[";
    
    /**
     * 方括号 ]
     */
    public static final String SYSTEM_CONTANT_BRACKETS_RIGHT = "]";
    
    /**
     * 单引号 '
     */
    public static final String SYSTEM_CONTANT_SINGLE_QUOTE = "'";
    
    /**
     * 圆括号 (
     */
    public static final String SYSTEM_CONTANT_PARENTHESES_LEFT = "(";
    
    /**
     * 圆括号 )
     */
    public static final String SYSTEM_CONTANT_PARENTHESES_RIGHT = ")";
    
    /**
     * 冒号:
     */
    public static final String SYSTEM_CONTANT_COLON = ":";
    
    /**
     * 分号 ;
     */
    public static final String SYSTEM_CONTANT_SEMICOLON = ";";
    
    /**
     * 问号 ?
     */
    public static final String SYSTEM_CONTANT_QUESTION_MARK = "?";
    
    /**
     * 反斜杠
     */
    public static final String SYSTEM_CONTANT_BACKSLASH = "/";
    
    /**
     * 中划线 -
     */
    public static final String SYSTEM_CONTANT_MIDDLE_LINE = "-";
    
    /**
     * 双引号
     */
    public static final String SYSTEM_CONTANT_DOUBLE_QUOTAION_MARK = "\"";
    
    /**
     * 正则表达式 竖线 |
     */
    public static final String SYSTEM_CONTANT_REGEX_VERTICAL_LINE = "[|]";
    
    /**
     * 数据类型的String
     */
    public static final String DATA_TYPE_STRING = "String";
    
    /**
     * 数据类型的BigDecimal
     */
    public static final String DATA_TYPE_BIGDECIMAL = "BigDecimal";
    
    /**
     * 数据类型的String
     */
    public static final String DB_DATA_TYPE_STRING = "1";
    
    /**
     * 数据类型的number
     */
    public static final String DB_DATA_TYPE_NUMBER = "2";
    
    /** SEVEN = 7 */
    public static final int SEVEN = 7;
    
    /** ONE_ONE = 11 */
    public static final int ONE_ONE = 11;
    
    public static final int FIVE_THOUSAND = 5000;
    
    /** FIFTEEN_THOUSAND */
    public static final int FIFTEEN_THOUSAND = 15000;
    
    /**
     * 数据类型的date
     */
    public static final String DB_DATA_TYPE_DATE = "3";
    
    /** 上传文件最大值 100M */
    public static final int MAX_FILE_UPLOAD = 104857600;
    
    /* 操作远程数据 */
    /** 远程操作--成功 */
    public static final String REMOTE1_OPERATE_SUCCESS = "001";
    
    /** 远程操作--关键字段输入异常 */
    public static final String REMOTE2_KEYWORD_FIELD = "002";
    
    /** 远程操作--关键字段未填写 */
    public static final String REMOTE3_KEYWORD_NOT_WRITE = "003";
    
    /** 远程操作--数据重复录入 */
    public static final String REMOTE4_VAL_ENTER_REPEAT = "004";
    
    /** 远程操作--其他异常错误 */
    public static final String REMOTE5_OPERATE_OTHER_ERROR = "005";
    
}
