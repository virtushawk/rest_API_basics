package com.epam.esm.mapper;

import com.epam.esm.entity.Certificate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Component
public class CertificateMapper implements RowMapper<Certificate> {

    @Override
    public Certificate mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Certificate.builder().id(rs.getLong("id")).name(rs.getString("name"))
                 .description(rs.getString("description")).price(rs.getBigDecimal("price"))
                 .duration(rs.getInt("duration"))
                .createDate(ZonedDateTime.ofInstant(rs.getTimestamp("create_date").toInstant(), ZoneId.of("UTC")))
                 .lastUpdateDate(ZonedDateTime.ofInstant(rs.getTimestamp("last_update_date").toInstant(), ZoneId.of("UTC"))).build();
    }
}
