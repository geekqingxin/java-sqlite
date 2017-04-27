package com.taione.utlis;

import java.sql.*;

/**
 * Created by td on 2017/4/25.
 */
public class ConnectionUtlis {

    /**
     * 根据获取的数据库驱动信息来创建数据库连接对象并返回
     * @return 连接对象
     * @throws Exception
     */
    public static Connection getConnection(String jdbcUrl) throws Exception{
        Connection c = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection(jdbcUrl);

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Opened database successfully");
        return  c;
    }

    /**
     * 统一关闭JDBC资源的方法
     * @param rs 结果集对象
     * @param stmt 语句对象
     * @param conn 连接对象
     * @throws SQLException
     */
    public static void close(ResultSet rs, Statement stmt, Connection conn) throws SQLException {
        if(rs != null){
            rs.close();
            rs = null;
        }

        if(stmt != null){
            stmt.close();
            stmt = null;
        }

        if(conn != null){
            conn.close();
            conn = null;
        }
    }
}
