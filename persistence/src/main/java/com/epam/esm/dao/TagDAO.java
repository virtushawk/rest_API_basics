package com.epam.esm.dao;

import com.epam.esm.entity.Tag;

import java.util.List;
import java.util.Optional;

/**
 * The interface Tag dao.
 */
public interface TagDAO extends BaseDAO<Tag, Long> {
    /**
     * Find all by certificate id.
     *
     * @param certificateId the certificate id
     * @return the list
     */
    List<Tag> findAllByCertificateId(Long certificateId);

    /**
     * Find by name optional.
     *
     * @param name the name
     * @return the optional
     */
    Optional<Tag> findByName(String name);

    Tag findOrCreate(Tag tag);

    /**
     * Find popular tag.
     *
     * @return the tag
     */
    Tag findPopular();
}
