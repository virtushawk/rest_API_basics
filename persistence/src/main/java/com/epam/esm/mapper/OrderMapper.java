package com.epam.esm.mapper;

import com.epam.esm.entity.Order;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * The type Order mapper.
 */
@Component
public class OrderMapper implements RowMapper<Order> {

    @Override
    public Order mapRow(ResultSet resultSet, int rowNum) throws SQLException {
       /* return Order.builder()
                .id(resultSet.getLong("id"))
                .userId(resultSet.getLong("user_id"))
                .cost(resultSet.getBigDecimal("cost"))
                .orderTime(ZonedDateTime.ofInstant(resultSet.getTimestamp("order_time").toInstant(),
                        ZoneId.systemDefault()))
                .build();*/
        return null;
    }
}
