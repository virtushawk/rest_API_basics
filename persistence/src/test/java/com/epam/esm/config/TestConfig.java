package com.epam.esm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@Import({DatabaseConfig.class})
@ComponentScan("com.epam.esm")
@PropertySource("classpath:property/dataSource-dev.properties")
public class TestConfig {

    @Autowired
    private Environment environment;

    @Bean
    @Profile("dev")
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(environment.getProperty("datasource.driverClassName"));
        dataSource.setUrl(environment.getProperty("datasource.url"));
        dataSource.setUsername(environment.getProperty("datasource.username"));
        dataSource.setPassword(environment.getProperty("datasource.password"));
        return dataSource;
    }

}
