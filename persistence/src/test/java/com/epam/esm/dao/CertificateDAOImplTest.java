package com.epam.esm.dao;

import com.epam.esm.config.TestConfig;
import com.epam.esm.creator.EntityCreator;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Page;
import com.epam.esm.entity.QuerySpecification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@SpringBootTest(classes = {TestConfig.class})
@ActiveProfiles("dev")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Sql(scripts = "classpath:/insert_data_certificate.sql")
class CertificateDAOImplTest {

    @Autowired
    private CertificateDAO certificateDAO;

    public static Object[][] querySpecificationData() {
        return new Object[][]{
                {EntityCreator.validQuerySpecification, false},
                {EntityCreator.emptyQuerySpecification, false},
                {EntityCreator.invalidQuerySpecification, true}
        };
    }

    public static Object[][] certificateData() {
        return new Object[][]{
                {EntityCreator.certificate, EntityCreator.update}
        };
    }

    @ParameterizedTest
    @MethodSource("querySpecificationData")
    void findAllQuerySpecification(QuerySpecification querySpecification, boolean expected) {
        List<Certificate> certificates = certificateDAO.findAll(querySpecification, new Page());
        Assertions.assertEquals(expected, certificates.isEmpty());
    }

    @ParameterizedTest
    @MethodSource("certificateData")
    @Transactional
    void createValid(Certificate certificate) {
        Certificate actual = certificateDAO.create(certificate);
        Assertions.assertEquals(certificate.getName(), actual.getName());
    }

    @Test
    void findAllValid() {
        List<Certificate> certificates = certificateDAO.findAll(new Page());
        Assertions.assertFalse(certificates.isEmpty());
    }

    @Test
    void findByIdValid() {
        Long id = 1L;
        Optional<Certificate> actual = certificateDAO.findById(id);
        Assertions.assertFalse(actual.isEmpty());
    }

    @Test
    void findByIdNotExists() {
        Long id = 1244L;
        Optional<Certificate> actual = certificateDAO.findById(id);
        Assertions.assertTrue(actual.isEmpty());
    }

    @ParameterizedTest
    @MethodSource("certificateData")
    void updateValid(Certificate certificate, Certificate update) {
        Certificate actual = certificateDAO.update(certificate, update);
        Assertions.assertEquals(update.getName(), actual.getName());
    }

    @ParameterizedTest
    @MethodSource("certificateData")
    void applyPatchValid(Certificate certificate, Certificate update) {
        Certificate actual = certificateDAO.applyPatch(certificate, update);
        Assertions.assertEquals(update.getName(), actual.getName());
    }

    @Test
    void deleteValid() {
        Long id = 1L;
        Optional<Certificate> certificate = certificateDAO.findById(id);
        certificateDAO.delete(certificate.get());
        Assertions.assertFalse(certificate.get().isActive());
    }
}
