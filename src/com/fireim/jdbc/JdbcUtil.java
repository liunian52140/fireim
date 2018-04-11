package com.fireim.jdbc;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

/**
 * 
 * @time 2018-04-01
 * @author TestP
 *
 */
public class JdbcUtil {
    static String url;
    static String username;
    static String password;
    static {
        try {
            Class.forName(ResourceBundle.getBundle("jdbc").getString(
                    "driverClass"));
            url = ResourceBundle.getBundle("jdbc").getString("url");
            username = ResourceBundle.getBundle("jdbc").getString("username");
            password = ResourceBundle.getBundle("jdbc").getString("password");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 获取连接
    public static Connection getConnection() {
        try {
            Connection con = DriverManager.getConnection(url, username,
                    password);
            return con;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("获取连接失败");
        }
    }

    
    public static void close(Connection con) {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // 关闭资源
    public static void close(Connection con, PreparedStatement ps) {
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        close(con);

    }

    // ResultSet 存放数据库查询结果集
    public static void close(Connection con, PreparedStatement ps, ResultSet rs) {
        if (rs != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        close(con, ps);
    }

}
