package com.epam.esm.dao.impl;

import com.epam.esm.dao.UserDAO;
import com.epam.esm.entity.User;
import com.epam.esm.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class UserDAOImpl implements UserDAO {

    private final JdbcTemplate jdbcTemplate;
    private final UserMapper userMapper;

    private static final String SQL_SELECT_USER_BY_ID = "SELECT id,name FROM user WHERE id = ?";
    private static final String SQL_SELECT_ALL_USERS = "SELECT id,name FROM user";

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query(SQL_SELECT_ALL_USERS, userMapper);
    }

    @Override
    public Optional<User> findById(Long id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SQL_SELECT_USER_BY_ID, new Object[]{id}, new int[]{Types.INTEGER},
                    userMapper));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public User create(User user) {
        throw new UnsupportedOperationException("method not allowed");
    }

    @Override
    public boolean delete(Long id) {
        throw new UnsupportedOperationException("method not allowed");
    }
}
