package com.epam.esm.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:property/dataSource.properties")
public class DatabaseConfig {

    private final Environment environment;

    public DatabaseConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public DataSource getDataSource() {
        HikariConfig  config = new HikariConfig();
        config.setJdbcUrl(environment.getProperty("url"));
        config.setUsername(environment.getProperty("user"));
        config.setPassword(environment.getProperty("password"));
        config.setDriverClassName(environment.getProperty("driverClassName"));
        config.setMaximumPoolSize(Integer.parseInt(environment.getProperty("maximum-pool-size")));
        return new HikariDataSource(config);
    }
}
