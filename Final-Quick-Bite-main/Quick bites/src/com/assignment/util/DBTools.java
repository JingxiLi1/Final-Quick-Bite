package com.assignment.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 *Database connect
 */
public class DBTools {
    private static String driver="com.mysql.cj.jdbc.Driver";  
    private static String url="jdbc:mysql://localhost:3306/dining_room?characterEncoding=utf8&serverTimezone=GMT%2B8&useSSL=false";  
    private static String user="root"; 
    private static String password="root";  

    static {
        try {
            try {
                //加载数据库驱动类到程序中
                Class.forName(driver);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                throw new RuntimeException("Failed to load driver", e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Configuration file not found", e);
        }
    }

    
  
    public static Connection getConnetion() throws SQLException {
        //通过DriverManager获得数据库连接
        return DriverManager.getConnection(url, user, password);
    }

    /**
    
     *
     * @param con
     */
    public static void close(Connection con) {
        if (con != null) { 
            try {
               
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("Database shutdown failure", e);
            }
        }
    }

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        Connection con = null;
        try {
            con = DBTools.getConnetion();
            System.out.println("Database connection successful!");
        } catch (SQLException e) {
            System.out.println("Database connection failure!");
            e.printStackTrace();
        } finally {
            DBTools.close(con);
        }
    }
}
