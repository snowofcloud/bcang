package com.c503.sc.jxwl.common.util.network;

import java.io.IOException;
import java.net.SocketException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import com.c503.sc.base.exception.CustomException;
import com.c503.sc.jxwl.common.constant.CommonConstant;
import com.c503.sc.log.LoggingManager;
import com.c503.sc.utils.common.SystemContants;

/**
 * 〈一句话功能简述〉http客户端工具类
 * 〈功能详细描述〉
 * 
 * @author duanhy
 * @version [版本号, 2017-8-22]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class JXWLHttpClientUtils {
    
    private static final LoggingManager LOGGER = LoggingManager.getLogger(JXWLHttpClientUtils.class);
    
    /** 客户端构建器 */
    private static HttpClientBuilder builder = null;
    
    /** 客户端类 */
    private static CloseableHttpClient client = null;
    
    /** 请求配置 */
    private static RequestConfig requestConfig = initConfig();
    
    /** 初始化开关 **/
    private static boolean isInit = false;
    
    /** socket链接超时 */
    private static int socketTimeout = 300000;
    
    /** 链接超时 */
    private static int connectTimeout = 300000;
    
    /** 链接请求超时设置 */
    private static int connectionRequestTimeout = 300000;
    
    /** 检测链接是否可用 */
    private static boolean staleConnectionCheckEnabled = true;
    
    /**
     * 发送get请求
     * 
     * @param url
     * @return 响应字符串
     * @throws Exception
     */
    public static String doGet(String url)
        throws Exception {
        if (StringUtils.isEmpty(url)) {
            CustomException ce = new CustomException(CommonConstant.SYS_EXCEPTION,
                    "JXWLHttpClientUtils.doGetStr 参数 url isEmpty");
            throw ce;
        }
        LOGGER.debug(SystemContants.DEBUG_START, url);
        HttpGet get = new HttpGet(url);
        get.setConfig(requestConfig);
        CloseableHttpResponse res = null;
        String result = null;
        try {
            res = getHttpClient().execute(get);
            HttpEntity entity = res.getEntity();
            result = EntityUtils.toString(entity);
        }
        catch (Exception ex) {
            LOGGER.error(CommonConstant.SYS_EXCEPTION, ex);
        }
        finally {
            close(res);
        }
        LOGGER.debug(SystemContants.DEBUG_END, result);
        return result;
    }
    
    /**
     * 发送Post请求
     * 
     * @param uri
     * @param params
     * @return
     * @throws Exception
     */
    public static String doPost(String uri, List<NameValuePair> params)
        throws Exception {
        HttpEntity httpEntity = null;
        if (params != null) {
            httpEntity = new UrlEncodedFormEntity(params,"UTF-8");
        }
        return doPostStr(uri, httpEntity);
    }
    
    /**
     * 发送Post请求
     * 
     * @param uri
     * @param params
     * @return
     * @throws Exception
     */
    public static String doPostStr(String uri, String params)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, uri);
        HttpEntity httpEntity = null;
        if (StringUtils.isEmpty(uri)) {
            CustomException ce =
                new CustomException(CommonConstant.SYS_EXCEPTION,
                    "doPostStr 参数 uri isEmpty");
            throw ce;
        }
        if (params != null) {
            httpEntity = new StringEntity(params);
        }
        return doPostStr(uri, httpEntity);
    }
    
    /**
     * 〈一句话功能简述〉post请求执行方法
     * 〈功能详细描述〉
     * 
     * @param uri 请求路径
     * @param httpEntity 请求参数实体
     * @return 请求结果
     * @throws Exception 系统异常
     * @see [类、类#方法、类#成员]
     */
    private static String doPostStr(String uri, HttpEntity httpEntity)
        throws Exception {
        HttpPost post = new HttpPost(uri);
        post.setConfig(requestConfig);
        if (httpEntity != null) {
            post.setEntity(httpEntity);
        }
        CloseableHttpResponse res = null;
        String result = null;
        try {
            res = getHttpClient().execute(post);
            HttpEntity entity = res.getEntity();
            result = EntityUtils.toString(entity);
        }
        catch (Exception ex) {
            LOGGER.error(CommonConstant.SYS_EXCEPTION, ex);
        }
        finally {
            close(res);
        }
        LOGGER.debug(SystemContants.DEBUG_END, result);
        return result;
    }
    
    /**
     * 〈一句话功能简述〉初始化客户端
     * 〈功能详细描述〉
     * 
     * @see [类、类#方法、类#成员]
     */
    private static synchronized void initClient() {
        if (!isInit) {
            builder = HttpClientBuilder.create();
            // fix the fucking apache http connection invalid bug
            // use the stupid retry strategy
            builder.setRetryHandler(new HttpRequestRetryHandler() {
                @Override
                public boolean retryRequest(IOException e, int count,
                    HttpContext arg2) {
                    if (count >= 3) {
                        return false;
                    }
                    if (e instanceof NoHttpResponseException) {
                        return true;
                    }
                    if (e instanceof SocketException) {
                        return true;
                    }
                    
                    return false;
                }
            });
            client = builder.build();
        }
    }
    
    /**
     * 〈一句话功能简述〉初始化httpclient参数设置
     * 〈功能详细描述〉
     * 
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static RequestConfig initConfig() {
        RequestConfig requestConfig =
            RequestConfig.custom()
                .setSocketTimeout(socketTimeout)
                .setConnectTimeout(connectTimeout)
                .setConnectionRequestTimeout(connectionRequestTimeout)
                .setStaleConnectionCheckEnabled(staleConnectionCheckEnabled)
                .build();
        return requestConfig;
    }
    
    /**
     * 〈一句话功能简述〉获取http客户端对象
     * 〈功能详细描述〉
     * 
     * @return httpclient对象
     * @see [类、类#方法、类#成员]
     */
    private static synchronized CloseableHttpClient getHttpClient() {
        if (client == null) {
            initClient();
        }
        return client;
    }
    
    /**
     * 〈一句话功能简述〉关闭响应
     * 〈功能详细描述〉
     * 
     * @param response 响应对象
     * @throws IOException IO异常
     * @see [类、类#方法、类#成员]
     */
    private static void close(CloseableHttpResponse response)
        throws IOException {
        if (response != null) {
            response.close();
        }
    }
    
}
