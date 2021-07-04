package com.epam.esm.service;

import com.epam.esm.dto.TagDTO;

import java.util.List;
import java.util.Set;

/**
 * The interface Tag service.
 */
public interface TagService extends BaseService<TagDTO, Long> {

    /**
     * Find tags by certificate id
     *
     * @param id the id
     * @return the list
     */
    List<TagDTO> findByCertificateId(Long id);
}
