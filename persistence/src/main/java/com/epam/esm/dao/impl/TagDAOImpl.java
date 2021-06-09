package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDAO;
import com.epam.esm.entity.Tag;
import com.epam.esm.mapper.TagMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Types;
import java.util.List;
import java.util.Optional;

@Repository
public class TagDAOImpl implements TagDAO {

    private final JdbcTemplate jdbcTemplate;
    private final TagMapper tagMapper;

    private static final String SQL_SELECT_TAGS_BY_CERTIFICATE_ID = "SELECT id,name FROM tag INNER JOIN tag_has_gift_certificate " +
            "ON id = tag_id WHERE gift_certificate_id = ?";
    private static final String SQL_INSERT_TAG = "INSERT INTO tag(name) VALUES(?)";
    private static final String SQL_INSERT_TAG_HAS_GIFT_CERTIFICATE = "INSERT INTO tag_has_gift_certificate(tag_id,gift_certificate_id) " +
            "VALUES(?,?)";
    private static final String SQL_SELECT_TAG_BY_NAME = "SELECT id,name FROM tag WHERE name = ?";

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
        Optional<Tag> optionalTag = findByName(tag.getName());
        if (optionalTag.isPresent()) {
            return optionalTag.get();
        }
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(SQL_INSERT_TAG, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, tag.getName());
            return preparedStatement;
        }, keyHolder);
        tag.setId(keyHolder.getKey().longValue());
        return tag;
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

    @Override
    public Optional<Tag> findByName(String name) {
        try {
            return Optional.of(jdbcTemplate.queryForObject(SQL_SELECT_TAG_BY_NAME,new Object[]{name},new int[] {Types.VARCHAR},tagMapper));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean attachToCertificateById(Long tagId, Long certificateId) {
        return jdbcTemplate.update(SQL_INSERT_TAG_HAS_GIFT_CERTIFICATE,tagId,certificateId) > 0;
    }
}
