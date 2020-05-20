package com.example.shardingjdbcmasterslave.mybatits;


import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.shardingsphere.api.config.masterslave.MasterSlaveRuleConfiguration;
import org.apache.shardingsphere.shardingjdbc.api.MasterSlaveDataSourceFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static com.example.shardingjdbcmasterslave.original.common.Constrant.*;

/**
 * shardingjdbc + mybatits 实现 数据库读写分离
 */
@Configuration
@EnableTransactionManagement //开启事务管理
@MapperScan(basePackages = "com.example.shardingjdbcmasterslave.mybatits") //扫描对应的dao层
public class MasterSlaveConfiguration {

    @Bean(name = "masterSlaveDataSource")
    public DataSource getDataSource () throws SQLException {
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

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactoryBean(DataSource masterSlaveDataSource) {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(masterSlaveDataSource);
   //     bean.setTypeAliasesPackage("tk.mybatis.springboot.model");

        //分页插件
//        PageHelper pageHelper = new PageHelper();
//        Properties properties = new Properties();
//        properties.setProperty("reasonable", "true");
//        properties.setProperty("supportMethodsArguments", "true");
//        properties.setProperty("returnPageInfo", "check");
//        properties.setProperty("params", "count=countSql");
//        pageHelper.setProperties(properties);
//
//        //添加插件
//        bean.setPlugins(new Interceptor[]{pageHelper});

        //添加mapper操作数据库XML目录
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            bean.setMapperLocations(resolver.getResources("classpath:mapper/*.xml"));
            return bean.getObject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /*spring通过SqlSessionTemplate对象去操作sqlsession语句*/
    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
    /*配置事务管理器*/
    @Bean
    public PlatformTransactionManager annotationDrivenTransactionManager(DataSource masterSlaveDataSource) {
        return new DataSourceTransactionManager(masterSlaveDataSource);
    }

}
