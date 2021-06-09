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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class CertificateServiceImpl implements CertificateService {

    private final CertificateDAO certificateDAO;

    private final TagDAO tagDAO;

    private final MapperDTO mapperDTO;

    public CertificateServiceImpl(CertificateDAO certificateDAO, TagDAO tagDAO, MapperDTO mapperDTO) {
        this.certificateDAO = certificateDAO;
        this.tagDAO = tagDAO;
        this.mapperDTO = mapperDTO;
    }

    @Override
    @Transactional
    public CertificateDTO create(CertificateDTO certificateDTO) {
        Certificate certificate = mapperDTO.convertDTOToCertificate(certificateDTO);
        certificate = certificateDAO.create(certificate);
        Set<TagDTO> tagDTOS = certificateDTO.getTags();
        final Long certificateId = certificate.getId();
        Set<Tag> tags = tagDTOS.stream().map(mapperDTO::convertDTOToTag).collect(Collectors.toSet());
        tags = tags.stream().map(tagDAO::create).collect(Collectors.toSet());
        tags.forEach(tag -> tagDAO.attachToCertificateById(tag.getId(),certificateId));
        tagDTOS = tags.stream().map(mapperDTO::convertTagToDTO).collect(Collectors.toSet());
        CertificateDTO result = mapperDTO.convertCertificateToDTO(certificate);
        result.setTags(tagDTOS);
        return result;
    }

    @Override
    @Transactional
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
    public CertificateDTO update(CertificateDTO certificateDTO) {
        return null;
    }

    public boolean delete(Long id) {
        return false;
    }
}
