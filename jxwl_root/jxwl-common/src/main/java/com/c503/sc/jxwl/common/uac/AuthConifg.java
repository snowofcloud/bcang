package com.c503.sc.jxwl.common.uac;

public class AuthConifg {

    private static final String apiKeyName = "token123";

    private static final String apiKeyUserId = "userInfo";

    /**30是毫秒值，还是分钟？？？？？*/
    private static final Long expired = 30L;

    private static final String authUrl =  "https://nav.asoco.com.cn/#/home";

    public static String keyName(){
        return apiKeyName;
    }

    public static String getApiKeyUserId(){
        return apiKeyUserId;
    }

    public static Long getExpired(){
        return expired;
    }

    public static String getAuthUrl(){
        return authUrl;
    }
}
