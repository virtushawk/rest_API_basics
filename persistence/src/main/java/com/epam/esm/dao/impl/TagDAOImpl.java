package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDAO;
import com.epam.esm.entity.Page;
import com.epam.esm.entity.Tag;
import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

/**
 * The type Tag dao.
 */
@Repository
@AllArgsConstructor
public class TagDAOImpl implements TagDAO {

    private final EntityManager entityManager;

    private static final String JPA_SELECT_ALL = "SELECT a FROM tag a";
    private static final String JPA_SELECT_POPULAR_TAG = "select tag.id,tag.name FROM gift_order " +
            "INNER JOIN order_has_gift_certificate ON gift_order.id = gift_order_id " +
            "INNER JOIN tag_has_gift_certificate ON order_has_gift_certificate.gift_certificate_id = tag_has_gift_certificate.gift_certificate_id " +
            "INNER JOIN tag ON tag_id = tag.id GROUP BY user_id,tag_id ORDER BY sum(cost) DESC, count(tag_id) DESC " +
            "LIMIT 1";
    private static final String JPA_FIND_BY_NAME = "select e from tag e where e.name = :name";
    private static final String ATTRIBUTE_NAME = "name";

    @Override
    public List<Tag> findAll(Page page) {
        return entityManager.createQuery(JPA_SELECT_ALL, Tag.class)
                .setFirstResult(page.getPage() * page.getSize())
                .setMaxResults(page.getSize())
                .getResultList();
    }

    @Override
    public Tag create(Tag tag) {
        entityManager.persist(tag);
        return tag;
    }

    @Override
    public Optional<Tag> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Tag.class, id));
    }

    @Override
    public void delete(Tag tag) {
        entityManager.remove(tag);
    }

    @Override
    public Optional<Tag> findByName(String name) {
        List<Tag> tags = entityManager.createQuery(JPA_FIND_BY_NAME, Tag.class)
                .setParameter(ATTRIBUTE_NAME, name)
                .getResultList();
        if (ObjectUtils.isEmpty(tags)) {
            return Optional.empty();
        }
        return Optional.of(tags.get(0));
    }

    @Override
    public Tag findOrCreate(Tag tag) {
        return findByName(tag.getName())
                .orElseGet(() -> {
                    entityManager.persist(tag);
                    return tag;
                });
    }

    @Override
    public Tag findPopular() {
        return (Tag) entityManager.unwrap(Session.class)
                .createSQLQuery(JPA_SELECT_POPULAR_TAG)
                .addEntity(Tag.class)
                .getSingleResult();
    }

}
