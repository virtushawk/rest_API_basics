package com.epam.esm.dao;

import com.epam.esm.config.TestConfig;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Page;
import com.epam.esm.entity.QuerySpecification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest(classes = {TestConfig.class})
@ActiveProfiles("dev")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Sql(scripts = "classpath:/insert_data_certificate.sql")
class CertificateDAOImplTest {

    @Autowired
    private CertificateDAO certificateDAO;

    @Test
    void findAllQuerySpecification() {
        QuerySpecification querySpecification = QuerySpecification.builder().build();
        querySpecification.setTags(new ArrayList<>());
        querySpecification.getTags().add("IT");
        Page page = new Page();
        List<Certificate> certificates = certificateDAO.findAll(querySpecification, page);
        Assertions.assertFalse(certificates.isEmpty());
    }

    @Test
    void findAllQuerySpecificationEmptySpecification() {
        QuerySpecification querySpecification = new QuerySpecification();
        Page page = new Page();
        List<Certificate> certificates = certificateDAO.findAll(querySpecification, page);
        Assertions.assertFalse(certificates.isEmpty());
    }

    @Test
    void findAllQuerySpecificationEmpty() {
        QuerySpecification querySpecification = QuerySpecification.builder()
                .tags(new ArrayList<>())
                .build();
        querySpecification.getTags().add("new tag");
        Page page = new Page();
        List<Certificate> certificates = certificateDAO.findAll(querySpecification, page);
        Assertions.assertTrue(certificates.isEmpty());
    }

    @Test
    void findAllQuerySpecificationNotActive() {
        QuerySpecification querySpecification = QuerySpecification.builder()
                .tags(new ArrayList<>())
                .build();
        querySpecification.getTags().add("Hello");
        Page page = new Page();
        List<Certificate> certificates = certificateDAO.findAll(querySpecification, page);
        Assertions.assertTrue(certificates.isEmpty());
    }

    @Test
    @Transactional
    void createValid() {
        Certificate certificate = Certificate.builder()
                .name("test name")
                .description("Test description")
                .price(new BigDecimal("11"))
                .duration(4)
                .build();
        Certificate actual = certificateDAO.create(certificate);
        Assertions.assertEquals(certificate.getName(), actual.getName());
    }

    @Test
    void findAllValid() {
        Page page = new Page();
        List<Certificate> certificates = certificateDAO.findAll(page);
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

    @Test
    void updateValid() {
        Certificate certificate = Certificate.builder()
                .name("test name")
                .description("Test description")
                .price(new BigDecimal("11"))
                .duration(4)
                .isActive(true)
                .build();
        Certificate update = Certificate.builder()
                .name("new name")
                .description("Test description")
                .price(new BigDecimal("11"))
                .duration(4)
                .build();
        Certificate actual = certificateDAO.update(certificate, update);
        Assertions.assertEquals(update.getName(), actual.getName());
    }

    @Test
    void applyPatchValid() {
        Certificate certificate = Certificate.builder()
                .name("test name")
                .description("Test description")
                .price(new BigDecimal("11"))
                .duration(4)
                .isActive(true)
                .build();
        Certificate update = Certificate.builder()
                .name("new name")
                .description("Test description")
                .price(new BigDecimal("11"))
                .duration(4)
                .build();
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
