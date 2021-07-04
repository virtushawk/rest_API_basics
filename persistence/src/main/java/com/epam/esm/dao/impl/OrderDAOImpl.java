package com.epam.esm.dao.impl;

import com.epam.esm.dao.OrderDAO;
import com.epam.esm.entity.Order;
import com.epam.esm.mapper.OrderMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

/**
 * The type Order dao.
 */
@Repository
@AllArgsConstructor
@Slf4j
public class OrderDAOImpl implements OrderDAO {

    private static final String SQL_INSERT_ORDER = "INSERT INTO orders(user_id,cost,order_time) VALUES(?,?,?)";
    private static final String SQL_SELECT_ORDER_BY_CERTIFICATE_ID = "SELECT id,user_id,cost,order_time " +
            "FROM orders WHERE certificate_id = ?";
    private static final String SQL_SELECT_ORDERS_BY_USER_ID = "SELECT id,user_id,cost,order_time FROM orders " +
            "WHERE user_id = ?";
    private static final String SQL_INSERT_ORDERS_HAS_GIFT_CERTIFICATE = "INSERT INTO orders_has_gift_certificate(orders_id,gift_certificate_id) VALUES(?,?)";
    private static final String SQL_SELECT_ORDERS = "SELECT id,user_id,cost,order_time FROM orders";
    private static final String SQL_SELECT_ORDER_BY_ID = "SELECT id,user_id,cost,order_time FROM orders WHERE id = ?";

    private final JdbcTemplate jdbcTemplate;

    private final OrderMapper orderMapper;

    @Override
    public List<Order> findAll() {
        return jdbcTemplate.query(SQL_SELECT_ORDERS, orderMapper);
    }

    @Override
    public Optional<Order> findById(Long id) {
        try {
            return Optional.of(jdbcTemplate.queryForObject(SQL_SELECT_ORDER_BY_ID, new Object[]{id}, new int[]{Types.INTEGER}, orderMapper));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Order create(Order order) {
        /*KeyHolder keyHolder = new GeneratedKeyHolder();
        order.setOrderTime(ZonedDateTime.now(ZoneId.systemDefault()));
        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(SQL_INSERT_ORDER, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, order.getUserId());
            preparedStatement.setBigDecimal(2, order.getCost());
            preparedStatement.setTimestamp(3, Timestamp.from(order.getOrderTime().toInstant()));
            return preparedStatement;
        }, keyHolder);
        order.setId(keyHolder.getKey().longValue());*/
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
