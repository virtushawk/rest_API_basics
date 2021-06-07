package com.epam.esm.service;

import com.epam.esm.entity.Certificate;

import java.util.Optional;

public interface CertificateService {

    Certificate create(Certificate certificate);
    Optional<Certificate> findById(Long id);
    Certificate modify(Certificate certificate);
    boolean delete(long id);
}
