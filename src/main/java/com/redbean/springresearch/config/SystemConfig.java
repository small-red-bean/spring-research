package com.redbean.springresearch.config;


import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class SystemConfig {

    @Bean
    @ConfigurationProperties(prefix = "djadmin.datasource.dbcp2")
    @Primary
    public DataSource qmc(){
        return DataSourceBuilder.create().type(BasicDataSource.class).build();
    }
}
