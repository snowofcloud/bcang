package com.c503.hthj.boot.demo.uac;

import com.c503.hthj.boot.demo.uac.AuthInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.MappedInterceptor;

import javax.servlet.MultipartConfigElement;

/**
 * Describe: 使用springboot 编程式api配置
 * <pre>
 *     1. 配置权限拦截器
 *     2. 配置swaggerui静态资源
 * </pre>
 * Date : 2018/6/5 17:17 <br/>
 * Project : asoco-zhyy-nav <br/>
 *
 * @author konbluesky <br/>
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {
    
    private static Logger log = LoggerFactory.getLogger(WebConfig.class);
    
    @Autowired
    AuthInterceptor authInterceptor;
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 1. 增加权限拦截
        // 直接注册则对全部url生效
//        registry.addInterceptor(authInterceptor);
        // 灵活通过正则指定请求和拦截器
        String[] includes = new String[] {"/api/**" };
        String[] exclude = new String[] { "/swagger**","/api/user/**" };
        MappedInterceptor mappedInterceptor = new MappedInterceptor(includes, exclude, authInterceptor);
        registry.addInterceptor(mappedInterceptor);
    }
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations(
                "classpath:/static/");
        registry.addResourceHandler("swagger-ui.html").addResourceLocations(
                "classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations(
                "classpath:/META-INF/resources/webjars/");
    }
    
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        AntPathMatcher matcher = new AntPathMatcher();
        //忽略大小写
        matcher.setCaseSensitive(false);
        configurer.setPathMatcher(matcher);
    }

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //上传10mb
        factory.setMaxFileSize(10 * 1024L * 1024L);
        return factory.createMultipartConfig();
    }
    
}
