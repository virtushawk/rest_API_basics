package com.epam.esm.service.impl;

import com.epam.esm.dao.CertificateDAO;
import com.epam.esm.dao.OrderDAO;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.PatchDTO;
import com.epam.esm.dto.QuerySpecificationDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.Page;
import com.epam.esm.entity.QuerySpecification;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.CertificateNotFoundException;
import com.epam.esm.exception.OrderNotFoundException;
import com.epam.esm.service.CertificateService;
import com.epam.esm.util.MapperDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class CertificateServiceImpl implements CertificateService {

    private final CertificateDAO certificateDAO;
    private final TagDAO tagDAO;
    private final OrderDAO orderDAO;
    private final MapperDTO mapperDTO;
    private final ObjectMapper objectMapper;

    private static final String CERTIFICATE_TAGS_COLUMN = "tags";

    @Override
    @Transactional
    public CertificateDTO create(CertificateDTO certificateDTO) {
        Certificate certificate = mapperDTO.convertDTOToCertificate(certificateDTO);
        Set<Tag> tags = new HashSet<>();
        if (!ObjectUtils.isEmpty(certificateDTO.getTags())) {
            certificate.getTags().forEach(o -> tags.add(tagDAO.findOrCreate(o)));
        }
        certificate.setTags(null);
        certificate = certificateDAO.create(certificate);
        certificate.setTags(tags);
        return mapperDTO.convertCertificateToDTO(certificate);
    }

    @Override
    public List<CertificateDTO> findAll(Page page) {
        return certificateDAO.findAll(page).stream().map(mapperDTO::convertCertificateToDTO).collect(Collectors.toList());
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
            update.getTags().forEach(o -> certificate.get().getTags().add(tagDAO.findOrCreate(o)));
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
        Optional<Order> optionalOrder = orderDAO.findById(id);
        if (optionalOrder.isEmpty()) {
            throw new OrderNotFoundException(id.toString());
        }
        return optionalOrder.get().getCertificates().stream().distinct().map(mapperDTO::convertCertificateToDTO).collect(Collectors.toList());
    }

    @Transactional
    public boolean delete(Long id) {
        Optional<Certificate> certificate = certificateDAO.findById(id);
        if (certificate.isEmpty()) {
            throw new CertificateNotFoundException(id.toString());
        }
        return certificateDAO.delete(id);
    }

}
