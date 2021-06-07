package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDAO;
import com.epam.esm.entity.Tag;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class TagDAOImpl implements TagDAO {

    @Override
    public Tag update(Tag tag) {
        return null;
    }

    @Override
    public Tag create(Tag tag) {
        return null;
    }

    @Override
    public Optional<Tag> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }
}
