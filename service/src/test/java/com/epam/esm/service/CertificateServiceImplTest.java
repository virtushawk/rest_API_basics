package com.epam.esm.service;

import com.epam.esm.dao.CertificateDAO;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.PatchDTO;
import com.epam.esm.dto.QuerySpecificationDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.QuerySpecification;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.CertificateNotFoundException;
import com.epam.esm.service.impl.CertificateServiceImpl;
import com.epam.esm.util.MapperDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.*;

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

    @Test
    void createValid() {
        Certificate certificate = Certificate.builder().name("test name").description("test description").price(new BigDecimal("10"))
                .duration(5).tags(new HashSet<>()).build();
        certificate.getTags().add(Tag.builder().name("test tag").build());
        CertificateDTO expected = CertificateDTO.builder().name("test name").description("test description").price(new BigDecimal("10"))
                .duration(5).tags(new HashSet<>()).build();
        Mockito.when(certificateDAO.create(certificate)).thenReturn(certificate);
        Mockito.when(mapperDTO.convertCertificateToDTO(certificate)).thenReturn(expected);
        Mockito.when(mapperDTO.convertDTOToCertificate(expected)).thenReturn(certificate);
        CertificateDTO actual = certificateService.create(expected);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findAllValid() {
        Certificate certificate = Certificate.builder().name("test name").description("test description").price(new BigDecimal("10"))
                .duration(5).tags(new HashSet<>()).build();
        certificate.getTags().add(Tag.builder().name("test tag").build());
        CertificateDTO certificateDTO = CertificateDTO.builder().name("test name").description("test description").price(new BigDecimal("10"))
                .duration(5).tags(new HashSet<>()).build();
        List<Certificate> certificates = new ArrayList<>();
        List<CertificateDTO> expected = new ArrayList<>();
        certificates.add(certificate);
        expected.add(certificateDTO);
        Mockito.when(mapperDTO.convertCertificateToDTO(certificate)).thenReturn(certificateDTO);
        Mockito.when(certificateDAO.findAll()).thenReturn(certificates);
        List<CertificateDTO> actual = certificateService.findAll();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findAllEmpty() {
        List<Certificate> certificates = new ArrayList<>();
        List<CertificateDTO> expected = new ArrayList<>();
        Mockito.when(certificateDAO.findAll()).thenReturn(certificates);
        List<CertificateDTO> actual = certificateService.findAll();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findByIdValid() {
        Certificate certificate = Certificate.builder().name("test name").description("test description").price(new BigDecimal("10"))
                .duration(5).tags(new HashSet<>()).build();
        List<Tag> tags = new ArrayList<>();
        CertificateDTO expected = CertificateDTO.builder().name("test name").description("test description").price(new BigDecimal("10"))
                .duration(5).tags(new HashSet<>()).build();
        Tag tag = Tag.builder().name("test tag").build();
        TagDTO tagDTO = TagDTO.builder().name("test tag").build();
        tags.add(tag);
        Optional<Certificate> optionalCertificate = Optional.of(certificate);
        Long id = 1L;
        Mockito.when(certificateDAO.findById(id)).thenReturn(optionalCertificate);
        Mockito.when(tagDAO.findAllByCertificateId(id)).thenReturn(tags);
        Mockito.when(mapperDTO.convertCertificateToDTO(certificate)).thenReturn(expected);
        Mockito.when(mapperDTO.convertTagToDTO(tag)).thenReturn(tagDTO);
        CertificateDTO actual = certificateService.findById(id);
        Assertions.assertEquals(expected, actual);
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
        QuerySpecificationDTO querySpecificationDTO = QuerySpecificationDTO.builder().tag("new tag").build();
        QuerySpecification querySpecification = QuerySpecification.builder().tag("new tag").build();
        Mockito.when(mapperDTO.convertDTOToQuery(querySpecificationDTO)).thenReturn(querySpecification);
        List<Certificate> certificates = new ArrayList<>();
        Mockito.when(certificateDAO.findAll(querySpecification)).thenReturn(certificates);
        List<CertificateDTO> expected = new ArrayList<>();
        List<CertificateDTO> actual = certificateService.findAll(querySpecificationDTO);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findAllQuerySpecificationEmpty() {
        QuerySpecificationDTO querySpecificationDTO = new QuerySpecificationDTO();
        QuerySpecification querySpecification = new QuerySpecification();
        Mockito.when(mapperDTO.convertDTOToQuery(querySpecificationDTO)).thenReturn(querySpecification);
        List<Certificate> certificates = new ArrayList<>();
        Mockito.when(certificateDAO.findAll(querySpecification)).thenReturn(certificates);
        List<CertificateDTO> expected = new ArrayList<>();
        List<CertificateDTO> actual = certificateService.findAll(querySpecificationDTO);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void updateValid() {
        CertificateDTO expected = CertificateDTO.builder().name("new certificate").build();
        Certificate certificate = Certificate.builder().name("new certificate").build();
        Optional<Certificate> optionalCertificate = Optional.of(certificate);
        Long id = 1L;
        expected.setId(id);
        certificate.setId(id);
        Mockito.when(certificateDAO.findById(id)).thenReturn(optionalCertificate);
        Mockito.when(mapperDTO.convertCertificateToDTO(certificate)).thenReturn(expected);
        CertificateDTO actual = certificateService.update(expected);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void updateException() {
        CertificateDTO expected = CertificateDTO.builder().name("new certificate").build();
        Certificate certificate = Certificate.builder().name("new certificate").build();
        Long id = 1L;
        expected.setId(id);
        certificate.setId(id);
        Mockito.when(certificateDAO.findById(id)).thenReturn(Optional.empty());
        Assertions.assertThrows(CertificateNotFoundException.class, () -> {
            certificateService.update(expected);
        });
    }

    @Test
    void applyPatchValid() {
        PatchDTO patchDTO = new PatchDTO();
        Map<String, Object> patchMap = new HashMap<>();
        Mockito.when(objectMapper.convertValue(patchDTO, Map.class)).thenReturn(patchMap);
        CertificateDTO expected = CertificateDTO.builder().name("new certificate").build();
        Certificate certificate = Certificate.builder().name("new certificate").build();
        Optional<Certificate> optionalCertificate = Optional.of(certificate);
        Long id = 1L;
        patchDTO.setId(id);
        expected.setId(id);
        certificate.setId(id);
        Mockito.when(certificateDAO.findById(id)).thenReturn(optionalCertificate);
        Mockito.when(mapperDTO.convertCertificateToDTO(certificate)).thenReturn(expected);
        CertificateDTO actual = certificateService.applyPatch(patchDTO);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void applyPatchException() {
        PatchDTO patchDTO = new PatchDTO();
        Map<String, Object> patchMap = new HashMap<>();
        Mockito.when(objectMapper.convertValue(patchDTO, Map.class)).thenReturn(patchMap);
        CertificateDTO expected = CertificateDTO.builder().name("new certificate").build();
        Certificate certificate = Certificate.builder().name("new certificate").build();
        Long id = 1L;
        patchDTO.setId(id);
        expected.setId(id);
        certificate.setId(id);
        Mockito.when(certificateDAO.findById(id)).thenReturn(Optional.empty());
        Assertions.assertThrows(CertificateNotFoundException.class, () -> {
            certificateService.applyPatch(patchDTO);
        });
    }

    @Test
    void attachTagsValid() {
        CertificateDTO expected = new CertificateDTO();
        HashSet<TagDTO> tagDTOs = new HashSet<>();
        CertificateDTO actual = certificateService.attachTags(expected, tagDTOs);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void deleteValid() {
        Long id = 1L;
        Mockito.when(certificateDAO.delete(id)).thenReturn(true);
        boolean flag = certificateService.delete(id);
        Assertions.assertTrue(flag);
    }

    @Test
    void deleteException() {
        Long id = 1L;
        Mockito.when(certificateDAO.delete(id)).thenReturn(false);
        Assertions.assertThrows(CertificateNotFoundException.class, () -> {
            certificateService.delete(id);
        });
    }
}
