package com.example.shardingjdbcmasterslave.original.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import static com.example.shardingjdbcmasterslave.original.common.Constrant.*;

/**
 * 原生jdbc操作数据库
 * * 1.注册驱动
 * * 		告知JVM使用的是哪一个数据库的驱动
 * * 2.获得连接
 * * 		使用JDBC中的类,完成对mysql数据库的连接(TCP协议)
 * * 3.获得语句执行平台
 * * 		通过连接对象获取对SQL语句的执行者对象
 * * 4.执行sql语句
 * * 		使用执行者对象,向数据库执行SQL语句
 * * 		获取数据库的执行后的结果
 * * 5.处理结果
 * * 6.释放资源
 */
public class JDBCOperationDb {
    //定义连接数据库的参数
    private static final String url = masterurl;
    private static final String user = masteruser;
    private static final String password = masterpassword;

    public static void main(String[] args) throws Exception {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            // 1. 注册驱动
            Class.forName("com.mysql.jdbc.Driver");

            //获取数据库连接对象
            connection = DriverManager.getConnection(url, user, password);
            //通过connection获取操作类Statement
            statement = connection.createStatement();
            //执行sql语句
            resultSet = statement.executeQuery("select * from user");
            //遍历resultSet打印数据
            while (resultSet.next()) {
                System.out.println(resultSet.getString("name") +"："+resultSet.getInt("age"));
            }
        } finally {
            //关闭连接资源
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (Exception e) {}
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (Exception e) {}
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {}
            }
        }
    }
}
