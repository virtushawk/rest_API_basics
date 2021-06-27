package com.epam.esm.dao;

import com.epam.esm.entity.Order;

import java.util.List;
import java.util.Optional;

/**
 * The interface Order dao.
 */
public interface OrderDAO extends BaseDAO<Order, Long> {

    /**
     * Find order by certificate id
     *
     * @param id the id
     * @return the optional
     */
    Optional<Order> findByCertificateId(Long id);

    /**
     * Find orders by user id
     *
     * @param id the id
     * @return the list
     */
    List<Order> findAllByUserId(Long id);
}
