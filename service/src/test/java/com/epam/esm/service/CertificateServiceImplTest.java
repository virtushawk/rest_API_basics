package com.epam.esm.service;

import com.epam.esm.dao.CertificateDAO;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.PatchDTO;
import com.epam.esm.dto.QuerySpecificationDTO;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Page;
import com.epam.esm.entity.QuerySpecification;
import com.epam.esm.exception.CertificateNotFoundException;
import com.epam.esm.service.impl.CertificateServiceImpl;
import com.epam.esm.util.MapperDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class CertificateServiceImplTest {

    @InjectMocks
    private CertificateServiceImpl certificateService;

    @Mock
    private CertificateDAO certificateDAO;

    @Mock
    private TagDAO tagDAO;

    @Mock
    private MapperDTO mapperDTO;

    @Mock
    private ObjectMapper objectMapper;

    private static Certificate certificate;
    private static CertificateDTO certificateDTO;

    @BeforeEach
    public void initEach() {
        certificate = Certificate.builder()
                .name("test name")
                .description("test description")
                .price(new BigDecimal("10"))
                .duration(5)
                .tags(new HashSet<>())
                .build();
        certificateDTO = CertificateDTO.builder()
                .name("test name")
                .description("test description")
                .price(new BigDecimal("10"))
                .duration(5)
                .tags(new HashSet<>())
                .build();
    }

    @Test
    void createValid() {
        Mockito.when(certificateDAO.create(certificate)).thenReturn(certificate);
        Mockito.when(mapperDTO.convertCertificateToDTO(certificate)).thenReturn(certificateDTO);
        Mockito.when(mapperDTO.convertDTOToCertificate(certificateDTO)).thenReturn(certificate);
        CertificateDTO actual = certificateService.create(certificateDTO);
        Assertions.assertEquals(certificateDTO, actual);
    }

    @Test
    void findAllValid() {
        List<Certificate> certificates = new ArrayList<>();
        List<CertificateDTO> expected = new ArrayList<>();
        certificates.add(certificate);
        expected.add(certificateDTO);
        Page page = new Page();
        Mockito.when(mapperDTO.convertCertificateToDTO(certificate)).thenReturn(certificateDTO);
        Mockito.when(certificateDAO.findAll(page)).thenReturn(certificates);
        List<CertificateDTO> actual = certificateService.findAll(page);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findAllEmpty() {
        Page page = new Page();
        List<Certificate> certificates = new ArrayList<>();
        List<CertificateDTO> expected = new ArrayList<>();
        Mockito.when(certificateDAO.findAll(page)).thenReturn(certificates);
        List<CertificateDTO> actual = certificateService.findAll(page);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findByIdValid() {
        Optional<Certificate> optionalCertificate = Optional.of(certificate);
        Long id = 1L;
        Mockito.when(certificateDAO.findById(id)).thenReturn(optionalCertificate);
        Mockito.when(mapperDTO.convertCertificateToDTO(certificate)).thenReturn(certificateDTO);
        CertificateDTO actual = certificateService.findById(id);
        Assertions.assertEquals(certificateDTO, actual);
    }

    @Test
    void findByIdException() {
        Long id = 1L;
        Mockito.when(certificateDAO.findById(id)).thenReturn(Optional.empty());
        Assertions.assertThrows(CertificateNotFoundException.class, () -> {
            certificateService.findById(id);
        });
    }

    @Test
    void findAllQuerySpecificationValid() {
        QuerySpecificationDTO querySpecificationDTO = QuerySpecificationDTO.builder().build();
        QuerySpecification querySpecification = QuerySpecification.builder().build();
        Mockito.when(mapperDTO.convertDTOToQuery(querySpecificationDTO)).thenReturn(querySpecification);
        List<Certificate> certificates = new ArrayList<>();
        Page page = new Page();
        Mockito.when(certificateDAO.findAll(querySpecification, page)).thenReturn(certificates);
        List<CertificateDTO> expected = new ArrayList<>();
        List<CertificateDTO> actual = certificateService.findAll(querySpecificationDTO, page);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void updateException() {
        Long id = 1L;
        certificateDTO.setId(id);
        certificate.setId(id);
        Mockito.when(certificateDAO.findById(id)).thenReturn(Optional.empty());
        Assertions.assertThrows(CertificateNotFoundException.class, () -> certificateService.update(certificateDTO));
    }

    @Test
    void applyPatchException() {
        PatchDTO patchDTO = new PatchDTO();
        Long id = 1L;
        certificateDTO.setId(id);
        certificate.setId(id);
        Mockito.when(certificateDAO.findById(id)).thenReturn(Optional.empty());
        Assertions.assertThrows(CertificateNotFoundException.class, () -> {
            certificateService.applyPatch(id, patchDTO);
        });
    }

    @Test
    void deleteException() {
        Long id = 1L;
        Mockito.when(certificateDAO.findById(id)).thenReturn(Optional.empty());
        Assertions.assertThrows(CertificateNotFoundException.class, () -> {
            certificateService.delete(id);
        });
    }
}
