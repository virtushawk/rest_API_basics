package com.epam.esm.dao;

import com.epam.esm.entity.Tag;

import java.util.Optional;

public interface TagDAO extends BaseDAO<Tag,Long> {
    Tag create(Tag tag);
    Optional<Tag> findById(Long id);
    boolean delete(Long id);
}
