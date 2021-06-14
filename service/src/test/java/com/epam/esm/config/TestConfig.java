package com.epam.esm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
@Import({DatabaseConfig.class})
@Configuration
@ComponentScan("com.epam.esm")
@PropertySource("classpath:property/dataSource-dev.properties")
public class TestConfig {

    @Autowired
    private final Environment environment;

    public TestConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean
    @Profile("dev")
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(environment.getProperty("driverClassName"));
        dataSource.setUrl(environment.getProperty("url"));
        return dataSource;
    }

}
