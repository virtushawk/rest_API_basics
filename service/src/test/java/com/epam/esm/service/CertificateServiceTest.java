package com.epam.esm.service;

import com.epam.esm.config.TestConfig;
import com.epam.esm.dao.CertificateDAO;
import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Certificate;
import com.epam.esm.util.MapperDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.HashSet;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
@ActiveProfiles("dev")
class CertificateServiceTest {

    @Mock
    private CertificateDAO certificateDAO;

    @Autowired
    private MapperDTO mapperDTO;

    @Autowired
    @InjectMocks
    private CertificateService certificateService;

    @Test
    void createCertificateTest() {
        CertificateDTO expected = CertificateDTO.builder().name("test name").description("test description")
                .price(new BigDecimal("0.2")).duration(2).tags(new HashSet<>()).build();
        expected.getTags().add(TagDTO.builder().name("money").build());
        Certificate certificate = mapperDTO.convertDTOToCertificate(expected);
        Mockito.when(certificateDAO.create(certificate)).thenReturn(certificate);
        CertificateDTO actual = certificateService.create(expected);
        Assertions.assertEquals(actual,expected);
    }



}
