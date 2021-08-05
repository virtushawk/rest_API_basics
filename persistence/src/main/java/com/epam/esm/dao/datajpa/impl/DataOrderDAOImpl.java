package com.epam.esm.dao.datajpa.impl;

import com.epam.esm.dao.OrderDAO;
import com.epam.esm.dao.datajpa.JPAOrderDAO;
import com.epam.esm.entity.Order;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
@Profile("jpaData")
public class DataOrderDAOImpl implements OrderDAO {

    private final JPAOrderDAO jpaOrderDAO;

    @Override
    public List<Order> findAll(Pageable page) {
        return jpaOrderDAO.findAll(page).getContent();
    }

    @Override
    public Optional<Order> findById(Long id) {
        return jpaOrderDAO.findById(id);
    }

    @Override
    public Order create(Order order) {
        return jpaOrderDAO.save(order);
    }

    @Override
    public void delete(Order order) {
        throw new UnsupportedOperationException("method not allowed");
    }
}
