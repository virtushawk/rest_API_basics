package com.epam.esm.dao.jpa;

import com.epam.esm.dao.OrderDAO;
import com.epam.esm.entity.Order;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

/**
 * The type Order dao.
 */
@Repository
@AllArgsConstructor
@Profile("jpa")
public class OrderDAOImpl implements OrderDAO {

    private final EntityManager entityManager;

    private static final String JPA_SELECT_ALL = "SELECT a FROM gift_order a";

    @Override
    public List<Order> findAll(Pageable page) {
        return entityManager.createQuery(JPA_SELECT_ALL, Order.class)
                .setFirstResult(page.getPageNumber() * page.getPageSize())
                .setMaxResults(page.getPageSize())
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
