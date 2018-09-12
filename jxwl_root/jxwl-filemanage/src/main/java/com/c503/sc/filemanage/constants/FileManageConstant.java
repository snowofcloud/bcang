/**
 * 文件名：Constants.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2015年8月19日
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.filemanage.constants;

/**
 *  *
 *  * 〈一句话功能简述〉文件管理的常量 〈功能详细描述〉
 * 
 * @author zz
 * @version [版本号, 2015年8月24日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public final class FileManageConstant {
    /** 在windows服务器存放下载文件的临时压缩包路径 */
    public static final String W_ZIP_TEMP = "WZipTemp";
    
    /** 在Linux服务器存放下载文件的临时压缩包路径 */
    public static final String L_ZIP_TEMP = "LZipTemp";
    
    /** 在Linux服务器存放下载文件的临时压缩包路径 */
    public static final int FILE_NOT_EXSITS = 300910000;
    
    /** 文件上传失败 */
    public static final int FILE_UPLOAD_FAIL_E = 300910001;
    
    /** 无文件可上传 */
    public static final int FILE_NOT_FILE_UPLOAD_E = 300910002;
    
    /** 成功 */
    public static final int SUC_OPTION = 100900001;
    
    /** 失败 */
    public static final int FAIL_OPTION = 200900001;
    
    /** fastdfs的NameValuePair长度 three */
    public static final int THREE = 3;
    
    /** fastdfs的字符集 */
    public static final String CHARSET = "charset";
    
    /** fastdfs的连接超时的时限，单位为毫秒 */
    public static final String CONNECT_TIMEOUT = "connect_timeout";
    
    /** fastdfs的网络超时的时限，单位为毫秒 */
    public static final String NETWORK_TIMEOUT = "network_timeout";
    
    /** fastdfs的密钥 */
    public static final String SECRET_KEY = "secret_key";
    
    /** fastdfs的Tracker Server提供HTTP服务的端口 */
    public static final String TRACKER_HTTP_PORT = "tracker_http_port";
    
    /** fastdfs的tracker_server地址 */
    public static final String HOST_NAME_ADDR = "host_name_addr";
    
    /** fastdfs的tracker_server端口 */
    public static final String HOST_NAME_PORT = "host_name_port";
    
}
