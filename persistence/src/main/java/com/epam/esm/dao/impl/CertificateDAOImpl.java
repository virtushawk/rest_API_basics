package com.epam.esm.dao.impl;

import com.epam.esm.dao.CertificateDAO;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Page;
import com.epam.esm.entity.QuerySpecification;
import com.epam.esm.entity.Tag;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

/**
 * The type Certificate dao.
 */
@Repository
@AllArgsConstructor
public class CertificateDAOImpl implements CertificateDAO {

    private final EntityManager entityManager;

    private static final String SQL_UPDATE_BY_ID = "UPDATE gift_certificate SET %s = %s WHERE id = :id";
    private static final String JPA_SELECT_ALL = "SELECT a FROM gift_certificate a";
    private static final String CERTIFICATE_TAGS_ATTRIBUTE_NAME = "tags";
    private static final String NAME_ATTRIBUTE = "name";
    private static final String LIKE_OPERATOR_FORMAT = "%%%s%%";
    private static final String DESCRIPTION_ATTRIBUTE = "description";
    private static final String SQL_ASC = "ASC";

    @Override
    public List<Certificate> findAll(QuerySpecification querySpecification, Page page) {
        CriteriaQuery<Certificate> criteriaQuery = createCriteriaQueryFromQuerySpecification(querySpecification);
        return entityManager.createQuery(criteriaQuery)
                .setFirstResult(page.getPage() * page.getSize())
                .setMaxResults(page.getSize())
                .getResultList();
    }

    @Override
    public Certificate create(Certificate certificate) {
        entityManager.persist(certificate);
        return certificate;
    }

    @Override
    public List<Certificate> findAll(Page page) {
        return entityManager.createQuery(JPA_SELECT_ALL, Certificate.class)
                .setFirstResult(page.getPage() * page.getSize())
                .setMaxResults(page.getSize())
                .getResultList();
    }

    @Override
    public Optional<Certificate> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Certificate.class, id));
    }

    @Override
    public Certificate update(Certificate certificate, Certificate update) {
        return certificate;
    }

    @Override
    public Certificate applyPatch(Map<String, Object> patchValues, Certificate certificate) {
        patchValues.forEach((key, value) -> {
            String formattedSQL = String.format(SQL_UPDATE_BY_ID, key, ":" + key);
            entityManager.createQuery(formattedSQL)
                    .setParameter(key, value)
                    .setParameter("id", certificate.getId())
                    .executeUpdate();
        });
        entityManager.refresh(certificate);
        return certificate;
    }

    @Override
    public void delete(Certificate certificate) {
        entityManager.remove(certificate);
    }

    private CriteriaQuery<Certificate> createCriteriaQueryFromQuerySpecification(QuerySpecification querySpecification) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Certificate> criteriaQuery = criteriaBuilder.createQuery(Certificate.class);
        Root<Certificate> root = criteriaQuery.from(Certificate.class);
        List<Predicate> list = new ArrayList<>();
        if (!ObjectUtils.isEmpty(querySpecification.getTags())) {
            Join<Certificate, Tag> join = root.join(CERTIFICATE_TAGS_ATTRIBUTE_NAME, JoinType.INNER);
            list.add(criteriaBuilder.in(join.get(NAME_ATTRIBUTE)).value(querySpecification.getTags()));
            criteriaQuery.groupBy(root);
            criteriaQuery.having(criteriaBuilder.equal(criteriaBuilder.count(root), querySpecification.getTags().size()));
        }
        if (!ObjectUtils.isEmpty(querySpecification.getText())) {
            list.add(criteriaBuilder.or(criteriaBuilder.like(root.get(NAME_ATTRIBUTE),
                    String.format(LIKE_OPERATOR_FORMAT, querySpecification.getText())),
                    criteriaBuilder.like(root.get(DESCRIPTION_ATTRIBUTE),
                            String.format(LIKE_OPERATOR_FORMAT, querySpecification.getText()))));
        }
        if (!ObjectUtils.isEmpty(querySpecification.getOrder())) {
            List<Order> ordersList = new ArrayList<>();
            querySpecification.getOrder()
                    .forEach(s -> ordersList.add((ObjectUtils.isEmpty(querySpecification.getSort())
                            || querySpecification.getSort().remove(0).toUpperCase(Locale.ROOT).equals(SQL_ASC))
                            ? criteriaBuilder.asc(root.get(s)) : criteriaBuilder.desc(root.get(s))));
            Order[] orders = new Order[ordersList.size()];
            criteriaQuery.orderBy(ordersList.toArray(orders));
        }
        Predicate[] predicates = new Predicate[list.size()];
        return criteriaQuery.select(root).where(list.toArray(predicates));
    }
}
