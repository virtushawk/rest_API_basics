package com.epam.esm.mapper;

import com.epam.esm.entity.Certificate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * The type Certificate mapper.
 */
@Component
public class CertificateMapper implements RowMapper<Certificate> {

    @Override
    public Certificate mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return Certificate.builder()
                .id(resultSet.getLong("id"))
                .name(resultSet.getString("name"))
                .description(resultSet.getString("description"))
                .price(resultSet.getBigDecimal("price"))
                .duration(resultSet.getInt("duration"))
                .createDate(ZonedDateTime.ofInstant(resultSet.getTimestamp("create_date").toInstant(),
                        ZoneId.systemDefault()))
                .lastUpdateDate(ZonedDateTime.ofInstant(resultSet.getTimestamp("last_update_date").toInstant(),
                        ZoneId.systemDefault()))
                .build();
    }
}
