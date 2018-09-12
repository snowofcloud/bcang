/**
 * 文件名：CommonConstants.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2015年9月29日
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.zcpt.constant;

/**
 * 〈一句话功能简述〉业务异常码常量定义 〈功能详细描述〉
 * 
 * @author zz
 * @version [版本号, 2016年7月1日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class BizExConstants {
    
    /** 位置服务平台异常 */
    public static final int POSITION_PLATEFORM_E = 330000000;
    
    /** 参数异常 */
    public static final int PARAM_E = 333000000;
    
    /** 7 */
    public static final int SEVEN = 7;
    
    /** 手机号码异常 没有11位 */
    public static final int CARDNUM_E = 333000001;
    
    /** 终端管理-此终端升级现在不支持 */
    public static final int TERMINAL_UPGRADE_UNSUPPORT_EXP = 321410001;
    
    /** 终端管理-此终端参数设置现在不支持 */
    public static final int TERMINAL_SET_PARAMS_UNSUPPORT_EXP = 321410002;
    
    /** 终端管理-查询车辆历史轨迹异常,格式化位置服务平台响应数据出错 */
    public static final int TERMINAL_HISTORY_TRACK_SEARCH_EXP = 321410003;
    
    /** 终端管理-查询终端异常,格式化位置服务平台响应数据出错 */
    public static final int TERMINAL_SEARCH_EXP = 321410005;
    
    /** 终端管理-查看终端异常,格式化位置服务平台响应数据出错 */
    public static final int TERMINAL_SEE_EXP = 321410006;
    
    /** 终端管理-位置服务平台异常,接口尚未定义的返回值 */
    public static final int TERMINAL_POSITION_PLAT_NO_RETURN_EXP = 321410007;
    
    /** 报警管理-报警参数设置现在不支持 */
    public static final int WARN_SET_PARAMS_UNSUPPORT_EXP = 322110001;
    
}
