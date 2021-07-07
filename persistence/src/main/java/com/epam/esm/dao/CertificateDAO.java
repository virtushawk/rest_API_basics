package com.epam.esm.dao;

import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Page;
import com.epam.esm.entity.QuerySpecification;

import java.util.List;
import java.util.Map;

/**
 * The interface Certificate dao.
 */
public interface CertificateDAO extends BaseDAO<Certificate, Long> {

    /**
     * Find all.
     *
     * @param querySpecification the query specification
     * @param page               the page
     * @return the list
     */
    List<Certificate> findAll(QuerySpecification querySpecification, Page page);

    /**
     * Update certificate.
     *
     * @param certificate the certificate
     * @param update      the update
     * @return the certificate
     */
    Certificate update(Certificate certificate, Certificate update);


    /**
     * Apply patch certificate.
     *
     * @param certificate the certificate
     * @param update      the update
     * @return the certificate
     */
    Certificate applyPatch(Certificate certificate, Certificate update);
}
