package com.epam.esm.dao;

import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Query;

import java.util.List;
import java.util.Map;

public interface CertificateDAO extends BaseDAO<Certificate, Long> {
    List<Certificate> findAll(Query query);
    Certificate update(Certificate certificate);
    boolean applyPatch(Map<String,Object> patchValues,Long id);
}
