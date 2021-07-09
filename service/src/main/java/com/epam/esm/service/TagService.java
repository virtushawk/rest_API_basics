package com.epam.esm.service;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Page;

import java.util.List;

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
    List<TagDTO> findAllByCertificateId(Long id, Page page);

    /**
     * Find popular tag dto.
     *
     * @return the tag dto
     */
    TagDTO findPopular();
}
