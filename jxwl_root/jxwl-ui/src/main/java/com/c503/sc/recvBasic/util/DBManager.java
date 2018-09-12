package com.c503.sc.recvBasic.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.c503.sc.log.LoggingManager;

/**
 * 〈一句话功能简述〉第三方数据库DBManager
 * 〈功能详细描述〉
 * 
 * @author zhongz
 * @version [版本号, 2016-12-28]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class DBManager {
    /** LOGGER */
    private static final LoggingManager LOGGER =
        LoggingManager.getLogger(DBManager.class);
    
    /** url */
    private static String url = null;
    
    // "jdbc:mysql://172.24.3.248:3306/acsz?useUnicode=true&characterEncoding=UTF-8";
    
    /** name */
    private static String name = null;
    
    // "com.mysql.jdbc.Driver";
    
    /** user */
    private static String user = null;;
    
    // private static String user = "root";
    
    /** password */
    private static String password = null;
    
    // private static String password = "123";
    
    /** conn */
    private Connection conn = null;
    
    /** pst */
    public PreparedStatement pst = null;
    /** 设置值 */
    static {
        url = GGYYPTcfg.getProperty("jdbc-url");
        name = GGYYPTcfg.getProperty("jdbc-name");
        user = GGYYPTcfg.getProperty("jdbc-user");
        password = GGYYPTcfg.getProperty("jdbc-password");
    }
    
    /**
     * 〈一句话功能简述〉构造器
     * 〈功能详细描述〉
     * 
     * @param sql sql
     * @see [类、类#方法、类#成员]
     */
    public DBManager(String sql) {
        try {
            Class.forName(name);
            conn = DriverManager.getConnection(url, user, password);
            pst = conn.prepareStatement(sql);
        }
        catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
    
    /**
     * 〈一句话功能简述〉关闭连接
     * 〈功能详细描述〉
     * 
     * @see [类、类#方法、类#成员]
     */
    public void close() {
        try {
            this.conn.close();
            this.pst.close();
        }
        catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
}
