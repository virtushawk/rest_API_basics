package com.epam.esm.service;

import com.epam.esm.config.TestConfig;
import com.epam.esm.dao.CertificateDAO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Sql({"classpath:drop_schema.sql","classpath:create_schema.sql"})
@Sql(scripts = {"classpath:insert_data_certificate.sql","classpath:insert_data_tag.sql"})
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
class CertificateServiceTest {

    @Autowired
    private CertificateDAO certificateDAO;

    @Test
    void createCertificateTest() {

    }

}
