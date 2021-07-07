package com.epam.esm.dao.impl;

import com.epam.esm.dao.OrderDAO;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.Page;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

/**
 * The type Order dao.
 */
@Repository
@AllArgsConstructor
public class OrderDAOImpl implements OrderDAO {

    private final EntityManager entityManager;

    private static final String JPA_SELECT_ALL = "SELECT a FROM gift_order a";

    @Override
    public List<Order> findAll(Page page) {
        return entityManager.createQuery(JPA_SELECT_ALL, Order.class)
                .setFirstResult(page.getPage() * page.getSize())
                .setMaxResults(page.getSize())
                .getResultList();
    }

    @Override
    public Optional<Order> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Order.class, id));
    }

    @Override
    public Order create(Order order) {
        entityManager.persist(order);
        return order;
    }

    @Override
    public void delete(Order order) {
        throw new UnsupportedOperationException("method not allowed");
    }

}
