package com.epam.esm.dao.impl;

import com.epam.esm.dao.OrderDAO;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.Page;
import com.epam.esm.mapper.OrderMapper;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.sql.Types;
import java.util.List;
import java.util.Optional;

/**
 * The type Order dao.
 */
@Repository
@AllArgsConstructor
public class OrderDAOImpl implements OrderDAO {

    private final EntityManager entityManager;

    private static final String SQL_SELECT_ORDER_BY_CERTIFICATE_ID = "SELECT id,user_id,cost,order_time " +
            "FROM orders WHERE certificate_id = ?";
    private static final String SQL_SELECT_ORDERS_BY_USER_ID = "SELECT id,user_id,cost,order_time FROM orders " +
            "WHERE user_id = ?";
    private static final String SQL_INSERT_ORDERS_HAS_GIFT_CERTIFICATE = "INSERT INTO orders_has_gift_certificate(orders_id,gift_certificate_id) VALUES(?,?)";

    private final JdbcTemplate jdbcTemplate;

    private final OrderMapper orderMapper;

    @Override
    public List<Order> findAll(Page page) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> root = criteriaQuery.from(Order.class);
        criteriaQuery.select(root);
        TypedQuery<Order> query = entityManager.createQuery(criteriaQuery);
        query.setFirstResult(page.getPage() * page.getSize());
        query.setMaxResults(page.getSize());
        return query.getResultList();
    }

    @Override
    public Optional<Order> findById(Long id) {
        return Optional.of(entityManager.find(Order.class, id));
    }

    @Override
    public Order create(Order order) {
        entityManager.persist(order);
        return order;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public Optional<Order> findByCertificateId(Long id) {
        try {
            return Optional.of(jdbcTemplate.queryForObject(SQL_SELECT_ORDER_BY_CERTIFICATE_ID, new Object[]{id}, new int[]{Types.INTEGER}, orderMapper));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Order> findAllByUserId(Long id) {
        return jdbcTemplate.query(SQL_SELECT_ORDERS_BY_USER_ID, new Object[]{id}, new int[]{Types.INTEGER}, orderMapper);
    }

    @Override
    public boolean attachCertificate(Long orderId, Long certificateId) {
        return jdbcTemplate.update(SQL_INSERT_ORDERS_HAS_GIFT_CERTIFICATE, orderId, certificateId) > 0;
    }
}
