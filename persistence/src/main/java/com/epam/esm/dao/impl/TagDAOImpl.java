package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDAO;
import com.epam.esm.entity.Page;
import com.epam.esm.entity.Tag;
import com.epam.esm.mapper.TagMapper;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.persistence.EntityManager;
import java.sql.Types;
import java.util.List;
import java.util.Optional;

/**
 * The type Tag dao.
 */
@Repository
@AllArgsConstructor
public class TagDAOImpl implements TagDAO {

    private final JdbcTemplate jdbcTemplate;
    private final TagMapper tagMapper;
    private final EntityManager entityManager;

    private static final String SQL_SELECT_TAGS_BY_CERTIFICATE_ID = "SELECT id,name FROM tag INNER JOIN tag_has_gift_certificate " +
            "ON id = tag_id WHERE gift_certificate_id = ?";
    private static final String SQL_INSERT_TAG_HAS_GIFT_CERTIFICATE = "INSERT IGNORE INTO tag_has_gift_certificate(tag_id,gift_certificate_id) " +
            "VALUES(?,?)";

    @Override
    public List<Tag> findAll(Page page) {
        return entityManager.createQuery("SELECT a FROM tag a", Tag.class)
                .setFirstResult(page.getPage() * page.getSize())
                .setMaxResults(page.getSize())
                .getResultList();
    }

    @Override
    @Transactional
    public Tag create(Tag tag) {
        entityManager.persist(tag);
        return tag;
    }

    @Override
    public Optional<Tag> findById(Long id) {
        return Optional.of(entityManager.find(Tag.class, id));
    }

    @Override
    public boolean delete(Long id) {
        return entityManager.createQuery("delete from tag where id = :id")
                .setParameter("id", id)
                .executeUpdate() > 0;
    }

    @Override
    public List<Tag> findAllByCertificateId(Long certificateId) {
        return jdbcTemplate.query(SQL_SELECT_TAGS_BY_CERTIFICATE_ID, new Object[]{certificateId}, new int[]{Types.INTEGER}, tagMapper);
    }

    @Override
    public Optional<Tag> findByName(String name) {
        List<Tag> tags = entityManager.createQuery("select e from tag e where e.name = :name", Tag.class)
                .setParameter("name", name)
                .getResultList();
        if (ObjectUtils.isEmpty(tags)) {
            return Optional.empty();
        }
        return Optional.of(tags.get(0));
    }

    @Override
    public boolean attachToCertificateById(Long tagId, Long certificateId) {
        return jdbcTemplate.update(SQL_INSERT_TAG_HAS_GIFT_CERTIFICATE, tagId, certificateId) > 0;
    }

    @Override
    public Tag findOrCreate(Tag tag) {
        Optional<Tag> optionalTag = findByName(tag.getName());
        if (optionalTag.isPresent()) {
            return optionalTag.get();
        }
        entityManager.persist(tag);
        return tag;
    }
}
