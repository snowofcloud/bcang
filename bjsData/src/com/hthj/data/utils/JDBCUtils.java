package com.hthj.data.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JDBCUtils {

    private static final String driverClassName;
    private static final String url;
    private static final String username;
    private static final String password;

    static{
        /**获得属性文件中的内容*/
        Properties properties=new Properties();
        try {
            properties.load(new FileInputStream("src/db.properties"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        driverClassName=properties.getProperty("driverClassName");
        url=properties.getProperty("url");
        username=properties.getProperty("username");
        password=properties.getProperty("password");
    }

    /**注册驱动*/
    public static void loadDriver(){
        try {
            Class.forName(driverClassName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**获得链接*/
    public static Connection getConnection(){
        Connection connection=null;
        try {
            /**统一注册驱动*/
            loadDriver();
            /**获得链接*/
            connection= DriverManager.getConnection(url,username,password);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    /**释放资源*/
    public static void release(Statement statement,Connection connection){
        if (statement != null){
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            statement = null;
        }

        if (connection != null){
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
           connection = null;
        }
    }

}
