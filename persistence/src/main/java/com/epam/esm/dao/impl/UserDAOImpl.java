package com.epam.esm.dao.impl;

import com.epam.esm.dao.UserDAO;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;
import com.epam.esm.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.sql.Types;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class UserDAOImpl implements UserDAO {

    private final JdbcTemplate jdbcTemplate;
    private final UserMapper userMapper;
    private final EntityManager entityManager;

    private static final String SQL_SELECT_USER_BY_ID = "SELECT id,name FROM user WHERE id = ?";
    private static final String SQL_SELECT_ALL_USERS = "SELECT id,name FROM user";

    @Override
    public List<User> findAll() {
        return entityManager.createQuery("SELECT a FROM user a", User.class).getResultList();
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.of(entityManager.find(User.class,id));
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
