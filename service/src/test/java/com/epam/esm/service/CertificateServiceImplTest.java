package com.epam.esm.service;

import com.epam.esm.dao.CertificateDAO;
import com.epam.esm.dao.OrderDAO;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.PatchDTO;
import com.epam.esm.dto.QuerySpecificationDTO;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.Page;
import com.epam.esm.entity.QuerySpecification;
import com.epam.esm.exception.CertificateNotFoundException;
import com.epam.esm.exception.OrderNotFoundException;
import com.epam.esm.service.impl.CertificateServiceImpl;
import com.epam.esm.util.MapperDTO;
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
    private OrderDAO orderDAO;

    @Mock
    private MapperDTO mapperDTO;

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
                .isActive(true)
                .build();
        certificateDTO = CertificateDTO.builder()
                .id(1L)
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
    void updateValid() {
        Long id = 1L;
        Certificate update = Certificate.builder().name("wow").build();
        Mockito.when(certificateDAO.findById(id)).thenReturn(Optional.of(certificate));
        Mockito.when(mapperDTO.convertDTOToCertificate(certificateDTO)).thenReturn(update);
        Mockito.when(certificateDAO.update(certificate, update)).thenReturn(certificate);
        Mockito.when(mapperDTO.convertCertificateToDTO(certificate)).thenReturn(certificateDTO);
        CertificateDTO actual = certificateService.update(certificateDTO);
        Assertions.assertEquals(actual.getName(), certificateDTO.getName());
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
    void applyPatchValid() {
        Long id = 1L;
        Certificate update = Certificate.builder().name("wow").build();
        PatchDTO patchDTO = PatchDTO.builder().name("wow").build();
        Mockito.when(certificateDAO.findById(id)).thenReturn(Optional.of(certificate));
        Mockito.when(mapperDTO.convertPatchDTOToCertificate(patchDTO)).thenReturn(update);
        Mockito.when(certificateDAO.applyPatch(certificate, update)).thenReturn(certificate);
        Mockito.when(mapperDTO.convertCertificateToDTO(certificate)).thenReturn(certificateDTO);
        CertificateDTO actual = certificateService.applyPatch(id, patchDTO);
        Assertions.assertEquals(actual.getName(), certificateDTO.getName());
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
    void findAllByOrderIdValid() {
        Long id = 1L;
        Order order = Order.builder().certificates(new ArrayList<>()).build();
        Mockito.when(orderDAO.findById(id)).thenReturn(Optional.of(order));
        List<CertificateDTO> actual = certificateService.findAllByOrderId(id);
        Assertions.assertTrue(actual.isEmpty());
    }

    @Test
    void findAllByOrderIdException() {
        Long id = 1L;
        Mockito.when(orderDAO.findById(id)).thenReturn(Optional.empty());
        Assertions.assertThrows(OrderNotFoundException.class, () -> certificateService.findAllByOrderId(id));
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
