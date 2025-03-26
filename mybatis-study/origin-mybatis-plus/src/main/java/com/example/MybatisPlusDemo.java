package com.example;


import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.MybatisSqlSessionFactoryBuilder;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.example.entity.User;
import com.example.mapper.UserMapper;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MybatisPlusDemo {
    public static void main(String[] args) {
        // 1. 创建数据源
        HikariDataSource dataSource = createDataSource();

        // 2. 配置 MyBatis-Plus
        SqlSessionFactory sqlSessionFactory = buildSqlSessionFactory(dataSource);

        // 3. 执行操作
        try (SqlSession session = sqlSessionFactory.openSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);

            // 查询所有
            User user = mapper.selectById(1l);
            System.out.println("查询结果: " + user);

            session.commit();
        }
    }

    private static HikariDataSource createDataSource() {
        try (InputStream input = MybatisPlusDemo.class.getClassLoader().getResourceAsStream("jdbc.properties")) {
            Properties props = new Properties();
            props.load(input);

            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(props.getProperty("jdbc.url"));
            config.setUsername(props.getProperty("jdbc.username"));
            config.setPassword(props.getProperty("jdbc.password"));
            config.setDriverClassName(props.getProperty("jdbc.driver"));
            return new HikariDataSource(config);
        } catch (IOException e) {
            throw new RuntimeException("加载数据库配置失败", e);
        }
    }

    private static SqlSessionFactory buildSqlSessionFactory(HikariDataSource dataSource) {
        // 事务工厂
        TransactionFactory transactionFactory = new JdbcTransactionFactory();

        // 环境配置
        Environment environment = new Environment("development", transactionFactory, dataSource);

        // MyBatis-Plus 增强配置
        MybatisConfiguration configuration = new MybatisConfiguration(environment);
        //configuration.setMapperRegistry(new MybatisMapperRegistry(configuration));

        // 注册 Mapper
        configuration.addMapper(UserMapper.class);

        // 添加分页插件
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        configuration.addInterceptor(interceptor);

        return new MybatisSqlSessionFactoryBuilder().build(configuration);
    }
}
