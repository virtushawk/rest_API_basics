package com.epam.esm.dao.impl;

import com.epam.esm.dao.CertificateDAO;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Page;
import com.epam.esm.entity.QuerySpecification;
import com.epam.esm.entity.Tag;
import com.epam.esm.mapper.CertificateMapper;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.sql.Types;
import java.time.ZoneId;
import java.time.ZonedDateTime;
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

    private final JdbcTemplate jdbcTemplate;

    private final CertificateMapper certificateMapper;

    private final EntityManager entityManager;

    private static final String SQL_UPDATE_BY_ID = "UPDATE gift_certificate SET %s = %s WHERE id = :id";
    private static final String SELECT_CERTIFICATES = "SELECT gift_certificate.id,gift_certificate.name,gift_certificate.description,gift_certificate.price,gift_certificate.duration,gift_certificate.create_date,gift_certificate.last_update_date FROM gift_certificate";
    private static final String SQL_SELECT_CERTIFICATES_BY_ORDER_ID = "SELECT id,name,description,price,duration,create_date,last_update_date FROM orders_has_gift_certificate INNER JOIN gift_certificate ON id = gift_certificate_id WHERE orders_id = ?";

    @Override
    public List<Certificate> findAll(QuerySpecification querySpecification, Page page) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Certificate> criteriaQuery = criteriaBuilder.createQuery(Certificate.class);
        Root<Certificate> root = criteriaQuery.from(Certificate.class);
        List<Predicate> list = new ArrayList<>();
        if (!ObjectUtils.isEmpty(querySpecification.getTags())) {
            Join<Certificate, Tag> join = root.join("tags", JoinType.INNER);
            list.add(criteriaBuilder.in(join.get("name")).value(querySpecification.getTags()));
            criteriaQuery.groupBy(root);
            criteriaQuery.having(criteriaBuilder.equal(criteriaBuilder.count(root), querySpecification.getTags().size()));
        }
        if (!ObjectUtils.isEmpty(querySpecification.getText())) {
            list.add(criteriaBuilder.or(criteriaBuilder.like(root.get("name"), '%' + querySpecification.getText() + '%'),
                    criteriaBuilder.like(root.get("description"), '%' + querySpecification.getText() + '%')));
        }
        if (!ObjectUtils.isEmpty(querySpecification.getOrder())) {
            List<Order> ordersList = new ArrayList<>();
            querySpecification.getOrder()
                    .forEach(s -> ordersList.add((ObjectUtils.isEmpty(querySpecification.getSort()) || querySpecification.getSort().remove(0).toUpperCase(Locale.ROOT).equals("ASC")) ? criteriaBuilder.asc(root.get(s)) : criteriaBuilder.desc(root.get(s))));
            Order[] orders = new Order[ordersList.size()];
            criteriaQuery.orderBy(ordersList.toArray(orders));
        }
        Predicate[] predicates = new Predicate[list.size()];
        criteriaQuery.select(root).where(list.toArray(predicates));
        TypedQuery<Certificate> query = entityManager.createQuery(criteriaQuery);
        query.setFirstResult(page.getPage() * page.getSize());
        query.setMaxResults(page.getSize());
        return query.getResultList();
    }

    @Override
    public Certificate create(Certificate certificate) {
        certificate.setCreateDate(ZonedDateTime.now(ZoneId.systemDefault()));
        certificate.setLastUpdateDate(ZonedDateTime.now(ZoneId.systemDefault()));
        entityManager.persist(certificate);
        return certificate;
    }

    @Override
    public List<Certificate> findAll(Page page) {
        return jdbcTemplate.query(SELECT_CERTIFICATES, certificateMapper);
    }

    @Override
    public Optional<Certificate> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Certificate.class, id));
    }

    @Override
    public Certificate update(Certificate certificate, Certificate update) {
        certificate.setName(update.getName());
        certificate.setDescription(update.getDescription());
        certificate.setPrice(update.getPrice());
        certificate.setDuration(update.getDuration());
        certificate.setLastUpdateDate(ZonedDateTime.now(ZoneId.systemDefault()));
        return certificate;
    }

    @Override
    public boolean applyPatch(Map<String, Object> patchValues, Long id) {
        patchValues.forEach((key, value) -> {
            String formattedSQL = String.format(SQL_UPDATE_BY_ID, key, ":" + key);
            entityManager.createQuery(formattedSQL)
                    .setParameter(key, value)
                    .setParameter("id", id)
                    .executeUpdate();
        });
        return true;
    }

    @Override
    public List<Certificate> findAllByOrderId(Long id) {
        return jdbcTemplate.query(SQL_SELECT_CERTIFICATES_BY_ORDER_ID, new Object[]{id}, new int[]{Types.INTEGER}, certificateMapper);
    }

    @Override
    public boolean delete(Long id) {
        return entityManager.createQuery("delete from gift_certificate where id = :id")
                .setParameter("id", id)
                .executeUpdate() > 0;
    }

}
