package com.epam.esm.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * The type Database config.
 */
@Configuration
@RequiredArgsConstructor
public class DatabaseConfig {


    @Value("${spring.datasource.username}")
    private String jdbcName;

    @Value("${spring.datasource.url}")
    private String jdbcUrl;

    @Value("${spring.datasource.password}")
    private String jdbcPassword;

    @Value("${spring.datasource.driver-class-name}")
    private String jdbcDriver;


    @Value("${hibernate.dialect}")
    private String dialect;

    @Value("${hibernate.show_sql}")
    private String showSql;

    @Value("${hibernate.hbm2ddl.auto}")
    private String hbm2DdlAuto;

    @Value("${entitymanager.packagesToScan}")
    private String packagesToScan;

    /**
     * Gets data source.
     *
     * @return the data source
     */
    @Bean
    public DataSource getDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(jdbcUrl);
        config.setUsername(jdbcName);
        config.setPassword(jdbcPassword);
        config.setDriverClassName(jdbcDriver);
        return new HikariDataSource(config);
    }

    /**
     * local session factory bean.
     *
     * @return the local session factory bean
     */
    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(getDataSource());
        sessionFactory.setPackagesToScan(packagesToScan);
        Properties hibernateProperties = new Properties();
        hibernateProperties.put("hibernate.dialect", dialect);
        hibernateProperties.put("hibernate.show_sql", showSql);
        hibernateProperties.put("hibernate.hbm2ddl.auto", hbm2DdlAuto);
        sessionFactory.setHibernateProperties(hibernateProperties);
        return sessionFactory;
    }
}
