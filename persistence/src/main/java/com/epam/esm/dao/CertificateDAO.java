package com.epam.esm.dao;

import com.epam.esm.entity.Certificate;

import java.util.Map;

public interface CertificateDAO extends BaseDAO<Certificate, Long> {
    Certificate update(Certificate certificate);
    boolean applyPatch(Map<String,Object> patchValues,Long id);
}
