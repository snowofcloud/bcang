package com.c503.hthj.boot.demo.uac;

import com.c503.hthj.boot.demo.config.AppConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Class title: 权限验证拦截器
 * Describe: <br/>
 * Date : 2018/6/6 16:25 <br/>
 * Project : asoco-zhyy-nav <br/>
 *
 * @author konbluesky
 */
@Component
public class AuthInterceptor implements HandlerInterceptor {

    private static Logger log = LoggerFactory.getLogger(AuthInterceptor.class);
    @Autowired
    AppConfig appConfig;//设置一些key，过期时间等（devModel，keyName，expired）
    @Autowired
    TokenKit tokenKit;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        System.out.println("1111");
        //本身的系统header中获取token
        String token = (String) request.getHeader(appConfig.keyName());
        // System.out.println(token.toString());
        System.out.println(token);
        System.out.println("2222");
        if (token == null) {
            //从 requestParam中获取token
            token = request.getParameter(appConfig.keyName());
            System.out.println(appConfig.keyName());//token的key
            System.out.println(token);//token的
            System.out.println("3333");
        }

        //以上两种方式可以获取token
        AppToken appToken = tokenKit.checkToken(token);
        //跳转到导航大厅登录页面,并将返回参数拿回来
        System.out.println(appToken);
        System.out.println("4444");
        if (appToken == null) {   ////？？？？？？？？？？ "?backType=api&backUrl="
            String redir = appConfig.getAuthUrl() + "?backType=api&backUrl=" + request.getRequestURL();
            response.sendRedirect(redir);
            System.out.println(redir);
            System.out.println("5555");
            return false;
        }
        // 获得正确的token后保存
        tokenKit.saveToken(appToken);
        System.out.println("6666");
        return true;

    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        log.info("拦截器postHandle");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                Exception ex) throws Exception {
        log.info("拦截器afterCompletion");
    }
}
