package com.example.jdbc;

import java.sql.*;

/**
 * Created by hy on 2017/11/12.
 */
public class PostgreSQLJDBC {

    public static void main(String[] args) {
        Connection conn = getConn();
        String sql = "select * from student";
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                System.out.println(rs.getString("username") + rs.getString("type"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConn() {
        Connection conn = null;
        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgressql://192.168.3.222:5432/dbtest";
            try {
                conn = DriverManager.getConnection(url, "postgres", "root");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return conn;
    }
}
