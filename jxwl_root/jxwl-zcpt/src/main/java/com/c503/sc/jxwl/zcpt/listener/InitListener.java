/**
 * 文件名：InitListener.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2015年11月30日
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.zcpt.listener;

import javax.servlet.ServletContextEvent;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.c503.sc.jxwl.zcpt.init.DataServiceEngine;

/**
 * 〈一句话功能简述〉初始化上下文应用监听
 * 〈功能详细描述〉
 * 
 * @author duanhy
 * @version [版本号, 2015年12月1日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class InitListener extends ContextLoaderListener {
    
    /** 上下文应用 */
    private ApplicationContext apct = null;
    
    /** 数据服务引擎 */
    private DataServiceEngine dse = null;
    
    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public void contextInitialized(ServletContextEvent event) {
        super.contextInitialized(event);
        if (null == apct) {
            apct =
                WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
        }
        try {
            dse = (DataServiceEngine) apct.getBean("dse");
            dse.init();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        
    }
    
}
