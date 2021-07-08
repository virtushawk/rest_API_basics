package com.epam.esm.service.impl;

import com.epam.esm.dao.CertificateDAO;
import com.epam.esm.dao.OrderDAO;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.PatchDTO;
import com.epam.esm.dto.QuerySpecificationDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Page;
import com.epam.esm.entity.QuerySpecification;
import com.epam.esm.exception.CertificateNotFoundException;
import com.epam.esm.exception.OrderNotFoundException;
import com.epam.esm.service.CertificateService;
import com.epam.esm.util.MapperDTO;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
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

    @Override
    @Transactional
    public CertificateDTO create(CertificateDTO certificateDTO) {
        Certificate certificate = mapperDTO.convertDTOToCertificate(certificateDTO);
        certificate.setTags(new HashSet<>());
        attachTags(certificate, certificateDTO.getTags());
        certificate = certificateDAO.create(certificate);
        return mapperDTO.convertCertificateToDTO(certificate);
    }

    @Override
    public List<CertificateDTO> findAll(Page page) {
        return certificateDAO.findAll(page)
                .stream()
                .map(mapperDTO::convertCertificateToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CertificateDTO findById(Long id) {
        Optional<Certificate> certificate = certificateDAO.findById(id);
        if (certificate.isEmpty() || !certificate.get().isActive()) {
            throw new CertificateNotFoundException(id.toString());
        }
        return mapperDTO.convertCertificateToDTO(certificate.get());
    }

    @Override
    public List<CertificateDTO> findAll(QuerySpecificationDTO querySpecificationDTO, Page page) {
        QuerySpecification querySpecification = mapperDTO.convertDTOToQuery(querySpecificationDTO);
        return certificateDAO.findAll(querySpecification, page)
                .stream()
                .map(mapperDTO::convertCertificateToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CertificateDTO update(CertificateDTO updateDTO) {
        Optional<Certificate> optional = certificateDAO.findById(updateDTO.getId());
        if (optional.isEmpty() || !optional.get().isActive()) {
            throw new CertificateNotFoundException(updateDTO.getId().toString());
        }
        Certificate certificate = optional.get();
        Certificate update = mapperDTO.convertDTOToCertificate(updateDTO);
        certificate = certificateDAO.update(certificate, update);
        attachTags(certificate, updateDTO.getTags());
        return mapperDTO.convertCertificateToDTO(certificate);
    }

    @Override
    @Transactional
    public CertificateDTO applyPatch(Long id, PatchDTO patchDTO) {
        Optional<Certificate> optional = certificateDAO.findById(id);
        if (optional.isEmpty() || !optional.get().isActive()) {
            throw new CertificateNotFoundException(id.toString());
        }
        Certificate certificate = optional.get();
        Certificate update = mapperDTO.convertPatchDTOToCertificate(patchDTO);
        certificate = certificateDAO.applyPatch(certificate, update);
        attachTags(certificate, patchDTO.getTags());
        return mapperDTO.convertCertificateToDTO(certificate);
    }

    @Override
    public List<CertificateDTO> findAllByOrderId(Long id) {
        return orderDAO
                .findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id.toString()))
                .getCertificates()
                .stream()
                .distinct()
                .map(mapperDTO::convertCertificateToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        certificateDAO.delete(certificateDAO.findById(id)
                .orElseThrow(() -> new CertificateNotFoundException(id.toString())));
    }

    private void attachTags(Certificate certificate, Set<TagDTO> tags) {
        if (!ObjectUtils.isEmpty(tags)) {
            tags.forEach(o -> certificate.getTags().add(tagDAO.findOrCreate(mapperDTO.convertDTOToTag(o))));
        }
    }
}
