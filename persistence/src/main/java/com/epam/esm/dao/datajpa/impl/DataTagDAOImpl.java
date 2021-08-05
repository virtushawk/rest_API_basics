package com.epam.esm.dao.datajpa.impl;

import com.epam.esm.dao.TagDAO;
import com.epam.esm.dao.datajpa.JPATagDAO;
import com.epam.esm.entity.Tag;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
@Profile("jpaData")
public class DataTagDAOImpl implements TagDAO {

    private final JPATagDAO jpaTagDAO;

    @Override
    public List<Tag> findAll(Pageable page) {
        return jpaTagDAO.findAll(page).getContent();
    }

    @Override
    public Optional<Tag> findById(Long id) {
        return jpaTagDAO.findById(id);
    }

    @Override
    public Tag create(Tag tag) {
        return jpaTagDAO.save(tag);
    }

    @Override
    public void delete(Tag tag) {
        jpaTagDAO.delete(tag);
    }

    @Override
    public Optional<Tag> findByName(String name) {
        return jpaTagDAO.findByName(name);
    }

    @Override
    public Tag findOrCreate(Tag tag) {
        return jpaTagDAO.findByName(tag.getName())
                .orElseGet(() -> jpaTagDAO.save(tag));
    }

    @Override
    public Tag findPopular() {
        return jpaTagDAO.findPopular();
    }
}
