package com.epam.esm.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

/**
 * The type Database config.
 */
@Configuration
@PropertySource("classpath:property/dataSource-prod.properties")
@AllArgsConstructor
public class DatabaseConfig {

    private final Environment environment;

    /**
     * Gets data source.
     *
     * @return the data source
     */
    @Bean
    @Profile("prod")
    public DataSource getDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(environment.getProperty("url"));
        config.setUsername(environment.getProperty("user"));
        config.setPassword(environment.getProperty("password"));
        config.setDriverClassName(environment.getProperty("driverClassName"));
        config.setMaximumPoolSize(Integer.parseInt(environment.getProperty("maximum-pool-size")));
        return new HikariDataSource(config);
    }

    /**
     * Jdbc template.
     *
     * @param dataSource the data source
     * @return the jdbc template
     */
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    /**
     * Named parameter jdbc template.
     *
     * @param dataSource the data source
     * @return the named parameter jdbc template
     */
    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }
}
