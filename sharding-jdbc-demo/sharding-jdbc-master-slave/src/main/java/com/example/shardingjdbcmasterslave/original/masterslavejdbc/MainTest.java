package com.example.shardingjdbcmasterslave.original.masterslavejdbc;

import javax.sql.DataSource;
import java.sql.*;

/**
 * 使用sharding-jdbc实现读写分离
 */
public class MainTest {
    public static void main(String[] args) throws Exception {
        ResultSet resultSet = null;
        try {
            //获取数据源 MasterSlaveDataSource
            DataSource masterSalveDataSouce = MasterSalveDataSouce.getMasterSalveDataSouce();
            //获取数据库连接对象 MasterSlaveConnection
            Connection connection = masterSalveDataSouce.getConnection();
            //通过connection获取操作类 MasterSlaveStatement
            Statement statement = connection.createStatement();
            //执行sql语句
            resultSet = statement.executeQuery("select * from hotnews");
            //遍历resultSet打印数据
            while (resultSet.next()) {
                System.out.println(resultSet.getString("id") + "：" + resultSet.getString("title"));
            }

            //获取数据库连接对象 MasterSlaveConnection
            Connection connection2 = masterSalveDataSouce.getConnection();
            //通过connection获取操作类 MasterSlaveStatement
            Statement statement2 = connection2.createStatement();
            //执行sql语句
            resultSet = statement2.executeQuery("select * from hotnews");
            //遍历resultSet打印数据
            while (resultSet.next()) {
                System.out.println(resultSet.getString("id") + "：" + resultSet.getString("title"));
            }
        } finally {
            if (resultSet != null) resultSet.close();
        }
    }
}
