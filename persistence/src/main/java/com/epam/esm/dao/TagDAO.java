package com.epam.esm.dao;

import com.epam.esm.entity.Page;
import com.epam.esm.entity.Tag;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * The interface Tag dao.
 */
public interface TagDAO extends BaseDAO<Tag, Long> {

    /**
     * Find by name.
     *
     * @param name the name
     * @return the optional
     */
    Optional<Tag> findByName(String name);

    /**
     * Find or create tag.
     *
     * @param tag the tag
     * @return the tag
     */
    Tag findOrCreate(Tag tag);

    /**
     * Find popular tag.
     *
     * @return the tag
     */
    Tag findPopular();
}
