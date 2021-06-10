package com.epam.esm.service;

import com.epam.esm.dto.CertificateDTO;

import java.util.Map;

public interface CertificateService extends BaseService<CertificateDTO, Long> {

    CertificateDTO update(CertificateDTO certificateDTO);
    CertificateDTO applyPatch(Map<String,Object> patchValues,Long id);
}
