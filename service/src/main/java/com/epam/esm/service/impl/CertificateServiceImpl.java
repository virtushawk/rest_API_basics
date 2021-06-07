package com.epam.esm.service.impl;

import com.epam.esm.dao.CertificateDAO;
import com.epam.esm.entity.Certificate;
import com.epam.esm.service.CertificateService;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class CertificateServiceImpl implements CertificateService {

    private final CertificateDAO certificateDAO;

    public CertificateServiceImpl(CertificateDAO certificateDAO) {
        this.certificateDAO = certificateDAO;
    }

    @Override
    public Certificate create(Certificate certificate) {
        return certificate;
    }

    @Override
    public Optional<Certificate> findById(Long id) {
        return certificateDAO.findById(id);
    }

    @Override
    public Certificate modify(Certificate certificate) {
        return null;
    }

    @Override
    public boolean delete(long id) {
        return false;
    }
}
