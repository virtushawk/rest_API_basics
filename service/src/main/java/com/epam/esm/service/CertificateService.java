package com.epam.esm.service;

import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.PatchDTO;
import com.epam.esm.dto.QuerySpecificationDTO;
import com.epam.esm.entity.Page;

import java.util.List;

/**
 * The interface Certificate service.
 */
public interface CertificateService extends BaseService<CertificateDTO, Long> {

    /**
     * Find all.
     *
     * @param querySpecificationDTO the query specification dto
     * @param page                  the page
     * @return the list
     */
    List<CertificateDTO> findAll(QuerySpecificationDTO querySpecificationDTO, Page page);

    /**
     * Update certificate dto.
     *
     * @param certificateDTO the certificate dto
     * @return the certificate dto
     */
    CertificateDTO update(CertificateDTO certificateDTO);

    /**
     * Apply patch certificate dto.
     *
     * @param patchDTO the patch dto
     * @return the certificate dto
     */
    CertificateDTO applyPatch(Long id, PatchDTO patchDTO);


    /**
     * Find all by order id list.
     *
     * @param id   the id
     * @param page the page
     * @return the list
     */
    List<CertificateDTO> findAllByOrderId(Long id, Page page);
}
