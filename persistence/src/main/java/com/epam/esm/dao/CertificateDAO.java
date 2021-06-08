package com.epam.esm.dao;

import com.epam.esm.entity.Certificate;

import java.util.List;
import java.util.Optional;

public interface CertificateDAO extends BaseDAO<Certificate,Long> {
    List<Certificate> findAll();
    Certificate create(Certificate certificate);
    Optional<Certificate> findById(Long id);
    Certificate update(Certificate certificate);
    boolean delete(Long id);
}
