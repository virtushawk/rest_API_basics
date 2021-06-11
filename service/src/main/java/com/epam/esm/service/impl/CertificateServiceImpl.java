package com.epam.esm.service.impl;

import com.epam.esm.dao.CertificateDAO;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.CertificateNotFoundException;
import com.epam.esm.service.CertificateService;
import com.epam.esm.util.MapperDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class CertificateServiceImpl implements CertificateService {

    private final CertificateDAO certificateDAO;
    private final TagDAO tagDAO;
    private final MapperDTO mapperDTO;

    @Override
    @Transactional
    public CertificateDTO create(CertificateDTO certificateDTO) {
        Certificate certificate = mapperDTO.convertDTOToCertificate(certificateDTO);
        certificate = certificateDAO.create(certificate);
        Set<TagDTO> tagDTOs = certificateDTO.getTags();
        certificateDTO = mapperDTO.convertCertificateToDTO(certificate);
        if (tagDTOs != null) {
            certificateDTO = attachTags(certificateDTO, tagDTOs);
        }
        return certificateDTO;
    }

    @Override
    public List<CertificateDTO> findAll() {
        certificateDAO.findAll();
        return null;
    }

    @Override
    @Transactional
    public CertificateDTO findById(Long id) {
        Optional<Certificate> certificate = certificateDAO.findById(id);
        if (certificate.isEmpty()) {
            throw new CertificateNotFoundException("Requested certificate not found, id : " + id);
        }
        Set<Tag> tags = new HashSet<>(tagDAO.findAllByCertificateId(id));
        CertificateDTO certificateDTO = mapperDTO.convertCertificateToDTO(certificate.get());
        Set<TagDTO> tagDTOs = tags.stream().map(mapperDTO::convertTagToDTO).collect(Collectors.toSet());
        certificateDTO.setTags(tagDTOs);
        return certificateDTO;
    }

    @Override
    public List<CertificateDTO> findAll(Map<String, String> parameters) {
        return null;
    }

    @Override
    public CertificateDTO update(CertificateDTO certificateDTO) {
        return null;
    }

    @Override
    @Transactional
    public CertificateDTO applyPatch(Map<String, Object> patchValues, Long id) {
        if (patchValues.containsKey("tags")) {
            Set<Tag> tags = new ObjectMapper().convertValue(patchValues.remove("tags"), new TypeReference<Set<Tag>>() {
            });
            tags = tags.stream().map(tagDAO::create).collect(Collectors.toSet());
            tags.forEach(tag -> tagDAO.attachToCertificateById(tag.getId(), id));
        }
        certificateDAO.applyPatch(patchValues, id);
        return findById(id);
    }

    @Override
    @Transactional
    public CertificateDTO attachTags(CertificateDTO certificateDTO, Set<TagDTO> tagDTOs) {
        Set<Tag> tags = tagDTOs.stream().map(mapperDTO::convertDTOToTag)
                .map(tagDAO::create).collect(Collectors.toSet());
        tags.forEach(tag -> tagDAO.attachToCertificateById(tag.getId(), certificateDTO.getId()));
        tagDTOs = tags.stream().map(mapperDTO::convertTagToDTO).collect(Collectors.toSet());
        certificateDTO.setTags(tagDTOs);
        return certificateDTO;
    }

    @Transactional
    public boolean delete(Long id) {
        boolean flag = certificateDAO.delete(id);
        if (!flag) {
            throw new CertificateNotFoundException("Requested certificate not found, id : " + id);
        }
        return true;
    }
}
