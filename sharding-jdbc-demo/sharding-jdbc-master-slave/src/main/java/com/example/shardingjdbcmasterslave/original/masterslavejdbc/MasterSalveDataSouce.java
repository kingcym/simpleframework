package com.example.shardingjdbcmasterslave.original.masterslavejdbc;


import com.alibaba.druid.pool.DruidDataSource;
import org.apache.shardingsphere.api.config.masterslave.MasterSlaveRuleConfiguration;
import org.apache.shardingsphere.shardingjdbc.api.MasterSlaveDataSourceFactory;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static com.example.shardingjdbcmasterslave.original.common.Constrant.*;

/**
 * 配置主从的datasource
 */
public class MasterSalveDataSouce {

    public static DataSource getMasterSalveDataSouce() throws SQLException {
        // 配置真实数据源
        Map<String, DataSource> dataSourceMap = new HashMap<>();

        // 配置主库
        DruidDataSource masterDataSource = new DruidDataSource();
        masterDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        masterDataSource.setUrl(masterurl);
        masterDataSource.setUsername(masteruser);
        masterDataSource.setPassword(masterpassword);
        dataSourceMap.put("ds_master", masterDataSource);

        // 配置第一个从库
        DruidDataSource slaveDataSource1 = new DruidDataSource();
        slaveDataSource1.setDriverClassName("com.mysql.jdbc.Driver");
        slaveDataSource1.setUrl(salve1url);
        slaveDataSource1.setUsername(salve1user);
        slaveDataSource1.setPassword(salve1password);
        dataSourceMap.put("ds_slave1", slaveDataSource1);

        // 配置第二个从库
        DruidDataSource slaveDataSource2 = new DruidDataSource();
        slaveDataSource2.setDriverClassName("com.mysql.jdbc.Driver");
        slaveDataSource2.setUrl(salver2url);
        slaveDataSource2.setUsername(salver2user);
        slaveDataSource2.setPassword(salver2password);
        dataSourceMap.put("ds_slave2", slaveDataSource2);

        // 配置读写分离规则
        MasterSlaveRuleConfiguration masterSlaveRuleConfig =
                new MasterSlaveRuleConfiguration(
                        "ds_master_slave",
                        "ds_master",
                        Arrays.asList("ds_slave1", "ds_slave2"));

        // 获取数据源对象
        DataSource dataSource = MasterSlaveDataSourceFactory.createDataSource(dataSourceMap, masterSlaveRuleConfig, new Properties());
        return dataSource;
    }
}
