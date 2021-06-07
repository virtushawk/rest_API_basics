package com.epam.esm.service;

import com.epam.esm.model.Certificate;

public interface CertificateService {

    Certificate createCertificate(Certificate certificate);
    Certificate findCertificate(long id);
    Certificate changeCertificate(Certificate certificate);
    Certificate deleteCertificate(long id);
}
