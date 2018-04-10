package com.yi.d1.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "com.yi.d1.mapper")
public class DatasourceConfig {
    @Bean
    @ConfigurationProperties(prefix = "druid")
    public DataSource dataSource() {
        return new DruidDataSource();
    }
}
