package com.epam.esm.service;

import com.epam.esm.dto.CertificateDTO;

public interface CertificateService extends BaseService<CertificateDTO, Long> {

    CertificateDTO update(CertificateDTO certificateDTO);
}
