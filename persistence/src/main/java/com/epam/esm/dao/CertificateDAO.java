package com.epam.esm.dao;

import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Page;
import com.epam.esm.entity.QuerySpecification;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The interface Certificate dao.
 */
public interface CertificateDAO extends BaseDAO<Certificate, Long> {
    /**
     * Find all list.
     *
     * @param querySpecification the query specification
     * @return the list
     */
    List<Certificate> findAll(QuerySpecification querySpecification, Page page);

    /**
     * Update certificate.
     *
     * @param certificate the certificate
     * @return the certificate
     */
    Certificate update(Certificate certificate,Certificate update);

    /**
     * Apply patch boolean.
     *
     * @param patchValues the patch values
     * @param id          the id
     * @return the boolean
     */
    Certificate applyPatch(Map<String, Object> patchValues, Certificate certificate);

    /**
     * Find certificates by order id
     *
     * @param id the id
     * @return the list
     */
    List<Certificate> findAllByOrderId(Long id);
}
