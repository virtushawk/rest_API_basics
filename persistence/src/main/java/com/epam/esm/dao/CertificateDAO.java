package com.epam.esm.dao;

import com.epam.esm.entity.Certificate;

public interface CertificateDAO extends BaseDAO<Certificate, Long> {
    Certificate update(Certificate certificate);
}
