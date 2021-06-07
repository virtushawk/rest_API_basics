package com.epam.esm.service.impl;

import com.epam.esm.model.Certificate;
import com.epam.esm.service.CertificateService;
import org.springframework.stereotype.Service;


@Service
public class CertificateServiceImpl implements CertificateService {


    @Override
    public Certificate createCertificate(Certificate certificate) {
        return certificate;
    }

    @Override
    public Certificate findCertificate(long id) {
        Certificate certificate = new Certificate();
        certificate.setId(id);
        return certificate;
    }

    @Override
    public Certificate changeCertificate(Certificate certificate) {
        return null;
    }

    @Override
    public Certificate deleteCertificate(long id) {
        return null;
    }
}
