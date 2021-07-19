package com.lhl.utils;

import java.sql.*;

public class JdbcUtil {
    //解决乱码的问题
    private static final String url = "jdbc:mysql://localhost:3306/[数据库名]?serverTimezone=UTC&characterEncoding=utf8&useUnicode=true&useSSL=false";
    private static final String userName = "[DB用户名]";
    private static final String password = "[DB用户密码]";


    static {
        try {
            //1、加载驱动，交给DriverManager管理
            Class.forName("com.mysql.cj.jdbc.Driver");//mysql 8.x的驱动
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //获取connection
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(url, userName, password);
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static Connection getAliyunConnection() {
        try {
            return DriverManager.getConnection(aliyunUrl, aliyunUserName, aliyunPassword);
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }

    }


    public static void close(Connection conn) {
        try {
            if (conn != null)
                conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void close(Statement stmt) {
        try {
            if (stmt != null)
                stmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void close(ResultSet rs) {
        try {
            if (rs != null)
                rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void close(ResultSet rs, Statement stmt, Connection conn) {
        close(rs);
        close(stmt);
        close(conn);
    }

    public static Boolean testConnect(String server) {
        assert server.equals("localDB")||server.equals("aliyunDB"):"localDB and aliyunDB is allowed";
        Boolean connectResult = Boolean.FALSE;
        Connection conn;
        if (server.equals("localDB")) {
            conn = getConnection();
        } else {
            conn = getAliyunConnection();
        }
        if (conn != null) {
            connectResult = Boolean.TRUE;
        } else {
            connectResult = Boolean.FALSE;
        }
        if (connectResult) {
            System.out.println(server + " connect success!");
            return true;
        } else {
            System.out.println(server + " error connect fail!");
            return false;
        }
    }

    public static void main(String[] args) throws Exception {
    }

}
