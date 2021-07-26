package com.epam.esm.dao;

import com.epam.esm.entity.User;

import java.util.Optional;

/**
 * The interface User dao.
 */
public interface UserDAO extends BaseDAO<User, Long> {
    Optional<User> findByName(String name);
}
