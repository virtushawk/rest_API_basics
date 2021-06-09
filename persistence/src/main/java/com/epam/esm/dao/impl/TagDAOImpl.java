package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDAO;
import com.epam.esm.entity.Tag;
import com.epam.esm.mapper.TagMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.List;
import java.util.Optional;

@Repository
public class TagDAOImpl implements TagDAO {

    private final JdbcTemplate jdbcTemplate;
    private final TagMapper tagMapper;

    private static final String SQL_SELECT_TAGS_BY_CERTIFICATE_ID = "SELECT id,name FROM tag INNER JOIN tag_has_gift_certificate " +
            "ON id = tag_id WHERE gift_certificate_id = ?";

    public TagDAOImpl(JdbcTemplate jdbcTemplate, TagMapper tagMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.tagMapper = tagMapper;
    }

    @Override
    public List<Tag> findAll() {
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

    @Override
    public List<Tag> findAllByCertificateId(Long certificateId) {
        return jdbcTemplate.query(SQL_SELECT_TAGS_BY_CERTIFICATE_ID,new Object[]{certificateId},new int[] {Types.INTEGER},tagMapper);
    }
}
