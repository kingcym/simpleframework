package com.example.shardingjdbcmasterslave.original.common;

public class Constrant {
    //定义连接数据库的参数
    public static final String masterurl = "jdbc:mysql://172.16.248.10:3306/db1";
    public static final String masteruser = "root";
    public static final String masterpassword = "root";

    public static final String salve1url = "jdbc:mysql://172.16.248.10:3306/db2";
    public static final String salve1user = "root";
    public static final String salve1password = "root";

    public static final String salver2url = "jdbc:mysql://172.16.248.10:3306/db3";
    public static final String salver2user = "root";
    public static final String salver2password = "root";
}
