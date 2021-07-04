package com.epam.esm.service.impl;

import com.epam.esm.dao.CertificateDAO;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.PatchDTO;
import com.epam.esm.dto.QuerySpecificationDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Page;
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
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
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

    private static final String CERTIFICATE_TAGS_COLUMN = "tags";

    @Override
    @Transactional
    public CertificateDTO create(CertificateDTO certificateDTO) {
        Certificate certificate = mapperDTO.convertDTOToCertificate(certificateDTO);
        Set<Tag> tags = new HashSet<>();
        if (!ObjectUtils.isEmpty(certificateDTO.getTags())) {
            certificate.getTags().forEach(o -> {
                Optional<Tag> temp = tagDAO.findByName(o.getName());
                tags.add(temp.isEmpty() ? tagDAO.create(o) : temp.get());
            });
        }
        certificate.setTags(null);
        certificate = certificateDAO.create(certificate);
        certificate.setTags(tags);
        return mapperDTO.convertCertificateToDTO(certificate);
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
        return mapperDTO.convertCertificateToDTO(certificate.get());
    }

    @Override
    public List<CertificateDTO> findAll(QuerySpecificationDTO querySpecificationDTO, Page page) {
        QuerySpecification querySpecification = mapperDTO.convertDTOToQuery(querySpecificationDTO);
        return certificateDAO.findAll(querySpecification, page).stream().map(mapperDTO::convertCertificateToDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CertificateDTO update(CertificateDTO updateDTO) {
        Optional<Certificate> certificate = certificateDAO.findById(updateDTO.getId());
        if (certificate.isEmpty()) {
            throw new CertificateNotFoundException(updateDTO.getId().toString());
        }
        Certificate update = mapperDTO.convertDTOToCertificate(updateDTO);
        if (!ObjectUtils.isEmpty(update.getTags())) {
            update.getTags().forEach(o -> {
                Optional<Tag> temp = tagDAO.findByName(o.getName());
                certificate.get().getTags().add(temp.isEmpty() ? tagDAO.create(o) : temp.get());
            });
        }
        return mapperDTO.convertCertificateToDTO(certificateDAO.update(certificate.get(), update));
    }

    @Override
    @Transactional
    public CertificateDTO applyPatch(Long id, PatchDTO patchDTO) {
        Optional<Certificate> optionalCertificate = certificateDAO.findById(id);
        if (optionalCertificate.isEmpty()) {
            throw new CertificateNotFoundException(id.toString());
        }
        Certificate certificate = optionalCertificate.get();
        Map<String, Object> patchMap = objectMapper.convertValue(patchDTO, Map.class);
        patchMap.remove(CERTIFICATE_TAGS_COLUMN);
        patchMap.remove("id");
       certificateDAO.applyPatch(patchMap, id);
        certificate.setLastUpdateDate(ZonedDateTime.now(ZoneId.systemDefault()));
        return mapperDTO.convertCertificateToDTO(certificate);
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

    @Override
    public List<CertificateDTO> findAllByOrderId(Long id) {
        return certificateDAO.findAllByOrderId(id).stream()
                .map(o -> {
                    o.setTags(new HashSet<>(tagDAO.findAllByCertificateId(o.getId())));
                    return o;
                })
                .map(mapperDTO::convertCertificateToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public boolean delete(Long id) {
        Optional<Certificate> certificate = certificateDAO.findById(id);
        if (certificate.isEmpty()) {
            throw new CertificateNotFoundException(id.toString());
        }
        return certificateDAO.delete(id);
    }

    private CertificateDTO checkForTags(CertificateDTO certificateDTO) {
        Set<TagDTO> tagDTOs = certificateDTO.getTags();
        if (!ObjectUtils.isEmpty(tagDTOs)) {
            certificateDTO = attachTags(certificateDTO, tagDTOs);
        }
        return certificateDTO;
    }
}
