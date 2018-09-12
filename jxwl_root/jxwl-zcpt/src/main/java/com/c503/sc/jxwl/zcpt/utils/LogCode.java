/**
 * 文件名：LogCode.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2015年10月30日
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.zcpt.utils;

/**
 * 
 * 日志记录代号:前两位为错误类型，中间两位为模块号，后两位为错误码
 * 00为系统异常，非00为运行逻辑异常：01为主线程模块，02为实时位置模块，03为历史轨迹模块
 * 
 * @author zhangjy
 * @version [版本号, 2015年11月4日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class LogCode {
    /** 系统异常 */
    public static final long SYSTEM_ERROR = 000000;
    
    /** 读取配置异常 */
    public static final long CONFIG_READ_ERROR = 010100;
    
    /** 读取配置成功 */
    public static final long CONFIG_READ_SUCCESS = 010101;
    
    /** kafka消费异常 */
    public static final long KAFKA_CONSUME_ERROR = 010200;
    
    /** 船舶轨迹查询成功 */
    public static final long TRACE_SHIP_SUCCESS = 030101;
    
    /** 船舶轨迹查询失败 */
    public static final long TRACE_SHIP_FAILED = 030100;
    
    /** 船舶轨迹存储失败 */
    public static final long SAVE_TRACE_FAILED = 030200;
    
    /** 区域轨迹查询成功 */
    public static final long TRACE_AREA_SUCCESS = 030301;
    
    /** 区域轨迹查询失败 */
    public static final long TTACE_ATEA_FAILED = 030300;
    /***/
}
