package com.c503.hthj.boot.demo.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by huangr on 2018/7/2.
 */
@Configuration
@PropertySource({ "classpath:application.yml" })
public class AppConfig {

    @Value("${app.devModel}")
    private String devModel;

    @Value("${app.uac.token.keyName}")
    private String apiKeyName;

    @Value("${app.uac.token.keyUserId}")
    private String apiKeyUserId;

    @Value("${app.uac.token.expire}")
    private Long expired;

    @Value("${app.uac.authUrl}")
    private String authUrl;

    public final static String pageSize ="10";


    public String keyName(){ return apiKeyName; }//获取token

    public String getApiKeyUserId() {
        return apiKeyUserId;
    }//获取用户id

    public boolean isDevModel() {
        return Boolean.parseBoolean(devModel);//是否为开发者模式
    }
    public Long getExpired() {
        return expired;
    }//设置过期时间
    public String getAuthUrl() {
        return authUrl;
    }//返回路径
}
