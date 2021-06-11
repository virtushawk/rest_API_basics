package com.epam.esm.service;

import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.TagDTO;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface CertificateService extends BaseService<CertificateDTO, Long> {

    List<CertificateDTO> findAll(Map<String, String> parameters);

    CertificateDTO update(CertificateDTO certificateDTO);

    CertificateDTO applyPatch(Map<String, Object> patchValues, Long id);

    CertificateDTO attachTags(CertificateDTO certificateDTO, Set<TagDTO> tagDTOSet);
}
