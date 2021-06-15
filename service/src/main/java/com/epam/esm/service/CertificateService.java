package com.epam.esm.service;

import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.PatchDTO;
import com.epam.esm.dto.QuerySpecificationDTO;
import com.epam.esm.dto.TagDTO;

import java.util.List;
import java.util.Set;

/**
 * The interface Certificate service.
 */
public interface CertificateService extends BaseService<CertificateDTO, Long> {

    /**
     * Find all list.
     *
     * @param querySpecificationDTO the query specification dto
     * @return the list
     */
    List<CertificateDTO> findAll(QuerySpecificationDTO querySpecificationDTO);

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
    CertificateDTO applyPatch(PatchDTO patchDTO);

    /**
     * Attach tags certificate dto.
     *
     * @param certificateDTO the certificate dto
     * @param tagDTOSet      the tag dto set
     * @return the certificate dto
     */
    CertificateDTO attachTags(CertificateDTO certificateDTO, Set<TagDTO> tagDTOSet);
}
