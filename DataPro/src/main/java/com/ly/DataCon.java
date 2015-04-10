package com.ly;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Created by ljc10860 on 2015/4/9.
 */
public class DataCon {
         //连接数据库
    String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    String url = "jdbc:sqlserver://10.5.32.30:1433;DatabaseName=TCMapBarDataClassUpdate;SelectMethod=cursor";
    String username = "TCUSSAllDatabaseUpdate";
    String password = "3!#vO*9P";

    public Connection getConnection() {
        Connection conn=null;
        try {
            Class.forName(driver);  //加载数据库驱动
              conn = DriverManager.getConnection(url, username, password);
            System.out.print("数据库连接成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
















}