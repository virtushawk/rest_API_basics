package com.epam.esm.mapper;

import com.epam.esm.entity.Tag;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The type Tag mapper.
 */
@Component
public class TagMapper implements RowMapper<Tag> {

    @Override
    public Tag mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return Tag.builder()
                .id(resultSet.getLong("id"))
                .name(resultSet.getString("name"))
                .build();
    }
}