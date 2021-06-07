package com.epam.esm.dao;

import com.epam.esm.entity.Certificate;

import java.util.Optional;

public interface CertificateDAO extends BaseDAO<Certificate,Long> {
    Certificate create(Certificate certificate);
    Optional<Certificate> findById(Long id);
    Certificate update(Certificate certificate);
    boolean delete(Long id);
}
