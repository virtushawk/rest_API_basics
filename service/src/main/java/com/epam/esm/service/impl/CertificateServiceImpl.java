package com.epam.esm.service.impl;

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
import com.epam.esm.service.CertificateService;
import com.epam.esm.util.MapperDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;


/**
 * The type Certificate service.
 */
@Service
@AllArgsConstructor
@Slf4j
public class CertificateServiceImpl implements CertificateService {

    private final CertificateDAO certificateDAO;
    private final TagDAO tagDAO;
    private final MapperDTO mapperDTO;
    private final ObjectMapper objectMapper;

    private static final String CERTIFICATE_ID_COLUMN = "id";
    private static final String CERTIFICATE_TAGS_COLUMN = "tags";

    @Override
    @Transactional
    public CertificateDTO create(CertificateDTO certificateDTO) {
        Certificate certificate = mapperDTO.convertDTOToCertificate(certificateDTO);
        certificate = certificateDAO.create(certificate);
        certificateDTO = mapperDTO.convertCertificateToDTO(certificate);
        return checkForTags(certificateDTO);
    }

    @Override
    public List<CertificateDTO> findAll() {
        return certificateDAO.findAll().stream().map(mapperDTO::convertCertificateToDTO).collect(Collectors.toList());
    }

    @Override
    public CertificateDTO findById(Long id) {
        Optional<Certificate> certificate = certificateDAO.findById(id);
        if (certificate.isEmpty()) {
            throw new CertificateNotFoundException(id.toString());
        }
        Set<Tag> tags = new HashSet<>(tagDAO.findAllByCertificateId(id));
        CertificateDTO certificateDTO = mapperDTO.convertCertificateToDTO(certificate.get());
        Set<TagDTO> tagDTOs = tags.stream().map(mapperDTO::convertTagToDTO).collect(Collectors.toSet());
        certificateDTO.setTags(tagDTOs);
        return certificateDTO;
    }

    @Override
    public List<CertificateDTO> findAll(QuerySpecificationDTO querySpecificationDTO) {
        QuerySpecification querySpecification = mapperDTO.convertDTOToQuery(querySpecificationDTO);
        List<Certificate> certificates = certificateDAO.findAll(querySpecification);
        certificates.forEach(o -> o.setTags(new HashSet<>(tagDAO.findAllByCertificateId(o.getId()))));
        return certificates.stream().map(mapperDTO::convertCertificateToDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CertificateDTO update(CertificateDTO certificateDTO) {
        Certificate certificate = mapperDTO.convertDTOToCertificate(certificateDTO);
        certificateDAO.update(certificate);
        checkForTags(certificateDTO);
        return findById(certificateDTO.getId());
    }

    @Override
    @Transactional
    public CertificateDTO applyPatch(PatchDTO patchDTO) {
        Map<String, Object> patchMap = objectMapper.convertValue(patchDTO, Map.class);
        CertificateDTO certificateDTO = CertificateDTO.builder().tags(patchDTO.getTags()).id(patchDTO.getId()).build();
        patchMap.remove(CERTIFICATE_ID_COLUMN);
        patchMap.remove(CERTIFICATE_TAGS_COLUMN);
        certificateDAO.applyPatch(patchMap, patchDTO.getId());
        checkForTags(certificateDTO);
        return findById(patchDTO.getId());
    }

    @Override
    @Transactional
    public CertificateDTO attachTags(CertificateDTO certificateDTO, Set<TagDTO> tagDTOs) {
        Set<Tag> tags = tagDTOs.stream().map(mapperDTO::convertDTOToTag)
                .map(tagDAO::create)
                .collect(Collectors.toSet());
        tags.forEach(tag -> tagDAO.attachToCertificateById(tag.getId(), certificateDTO.getId()));
        tagDTOs = tags.stream().map(mapperDTO::convertTagToDTO).collect(Collectors.toSet());
        certificateDTO.setTags(tagDTOs);
        return certificateDTO;
    }

    @Transactional
    public boolean delete(Long id) {
        boolean flag = certificateDAO.delete(id);
        if (!flag) {
            throw new CertificateNotFoundException(id.toString());
        }
        return true;
    }

    private CertificateDTO checkForTags(CertificateDTO certificateDTO) {
        Set<TagDTO> tagDTOs = certificateDTO.getTags();
        if (!ObjectUtils.isEmpty(tagDTOs)) {
            certificateDTO = attachTags(certificateDTO, tagDTOs);
        }
        return certificateDTO;
    }
}
