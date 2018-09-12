package com.c503.sc.jxwl.common.filter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.c503.sc.base.common.CommonConstants;
import com.c503.sc.log.LoggingManager;

/**
 * 〈一句话功能简述〉GGYYPTcfg
 * 〈功能详细描述〉
 * 
 * @author
 * @version [版本号, 2016-12-28]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class GGYYPTcfg {
    /** LOGGER */
    private static final LoggingManager LOGGER =
        LoggingManager.getLogger(DBManager.class);
    
    /** Properties */
    private static Properties cfg = new Properties();
    /** init */
    static {
        init();
    }
    
    /**
     * 〈一句话功能简述〉init
     * 〈功能详细描述〉
     * 
     * @see [类、类#方法、类#成员]
     */
    private static void init() {
        InputStream is =
            DBManager.class.getResourceAsStream("/ggyypt-cfg.properties");
        try {
            cfg.load(is);
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error(CommonConstants.SYS_EXCEPTION, e, "读取报警描述的配置失败");
        }
    }
    
    /**
     * 〈一句话功能简述〉getProperty
     * 〈功能详细描述〉
     * 
     * @param key key
     * @return String
     * @see [类、类#方法、类#成员]
     */
    public static String getProperty(String key) {
        return cfg.getProperty(key);
    }
}
