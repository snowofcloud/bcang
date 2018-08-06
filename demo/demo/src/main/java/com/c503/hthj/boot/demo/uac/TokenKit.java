package com.c503.hthj.boot.demo.uac;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.c503.hthj.boot.demo.config.AppConfig;
import com.c503.sc.utils.response.ResultMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import sc.c503.authclient.model.ApiUserEntity;
import sc.c503.authclient.model.SysRscOperEntity;
import sc.c503.authclient.service.splitImpl.ApiRelationService;
import sc.c503.authclient.service.splitImpl.ApiUserService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Class title: 应用 Token 工具类 <br/>
 * Describe: <br/>
 * Date : 2018/6/8 09:03 <br/>
 * Project : asoco-zhyy-nav <br/>
 *
 * @author konbluesky <br/>
 */
@Component
public class TokenKit {
    
    private static Logger log = LoggerFactory.getLogger(TokenKit.class);
    
    @Autowired
    AppConfig appConfig;  //设置一些key，过期时间等（devModel，keyName，expired）
    
    @Autowired
    StringRedisTemplate redisTemplate;
    
    /**
     * 验证令牌
     *
     * @param token
     * @return
     * @throws IllegalArgumentException
     */
    public AppToken checkToken(String token) {
        //TODO 发布后 clean
        if(appConfig.isDevModel()){  //如果是开发者模式
            return new AppToken("zjhthj2018","admin-dev","0dd6426aa333420db0b801357c5a0d4e","admin-dev");
        }
        if(token==null||token=="null"){
            return null;
        }
        String key = new StringBuilder(appConfig.keyName()).append("-").append(token).toString();
        String val = redisTemplate.opsForValue().get(key);
        log.info("check uac:{}", token);
        if(val==null){
            // 从uac验证
         ApiUserService userService = UACServiceFactory.apiUserService(token);
          ResultMessage message=  userService.authToken();
          if(message.getCode()==0){
            ApiUserEntity user = userService.getUserInfo();
            if(user!=null){
                AppToken newToken =new AppToken(token,user.getAccount(),user.getId(),user.getName());
                saveToken(newToken);
                saveUserInfo(newToken);
                return newToken;
            }
          }

        }
        return JSON.parseObject(val, AppToken.class);
//        Preconditions.checkArgument(!Strings.isNullOrEmpty(val), "令牌不存在或已经过期");

    }
    
    /**
     * 保存token到redis
     *
     * @param tokenObj
     * @throws IllegalArgumentException
     */
    public void saveToken(AppToken tokenObj) {
        log.debug("save uac : username[{}]-userid[{}]-uac[{}]", tokenObj.getUsername(), tokenObj.getUserId(),
                  tokenObj.getToken());
        String key = new StringBuilder(appConfig.keyName()).append("-").append(tokenObj.getToken()).toString();
        redisTemplate.opsForValue().set(key, JSON.toJSONString(tokenObj), appConfig.getExpired(),
                                        TimeUnit.MINUTES);
    }
    
    /**
     * 从当前request中获取token
     * @return
     */
    public AppToken getToken(){
        HttpServletRequest request=((ServletRequestAttributes)(RequestContextHolder.currentRequestAttributes())).getRequest();
        String token=String.valueOf(request.getHeader(appConfig.keyName()));
        if(token==null||token=="null"){
            token = String.valueOf(request.getParameter(appConfig.keyName()));
        }
        return checkToken(token);
    }

    public JSONObject getUserInfo(){
        AppToken tokenObj =getToken();
        if(tokenObj==null){
            return null;
        }
        String key = new StringBuilder(appConfig.getApiKeyUserId()).append("-").append(tokenObj.getToken()).toString();
        String val = redisTemplate.opsForValue().get(key);
        return JSONObject.parseObject(val);
    }
    public void saveUserInfo(AppToken tokenObj){
        if(tokenObj==null){
           return ;
        }
        String token =tokenObj.getToken();
        // 从uac验证
        ApiUserService userService = UACServiceFactory.apiUserService(token);
        ApiRelationService relationService =UACServiceFactory.apiRelationService(token);
        ResultMessage message=  userService.authToken();
        if(message.getCode()==0){
            JSONObject data = new JSONObject();
            ApiUserEntity user = userService.getUserInfo();
            if(user!=null){
                data.put("account", user.getAccount());
                data.put("sex", user.getSex());
                data.put("address", user.getCommAddress());
                data.put("username", user.getName());
                data.put("userId", user.getId());
                data.put("token", token);
            }
            //获取权限
            JSONArray rights = new JSONArray();

            ApiRelationService svc = UACServiceFactory.apiRelationService(token);
            List<SysRscOperEntity> rList = svc.getRscByToken();
            for (SysRscOperEntity entity : rList) {
                JSONObject object = new JSONObject();
                object.put("moduleId",entity.getMid());
                object.put("modulePid",entity.getMpid());
                object.put("moduleName",entity.getMname());
                object.put("moduleCode",entity.getMtarget());
                object.put("moduleUrl",entity.getMurl());
                object.put("functionId", entity.getFid());
                object.put("functionName",entity.getFname());
                object.put("functionCode",entity.getFidentifying());
                rights.add(object);
            }
            data.put("rights", rights);
            String key = new StringBuilder(appConfig.getApiKeyUserId()).append("-").append(token).toString();
            redisTemplate.opsForValue().set(key, JSON.toJSONString(data), appConfig.getExpired(),
                    TimeUnit.MINUTES);
        }


        }


}
