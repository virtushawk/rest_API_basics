package com.epam.esm.dao.datajpa.impl;

import com.epam.esm.dao.UserDAO;
import com.epam.esm.dao.datajpa.JPAUserDAO;
import com.epam.esm.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
@Profile("jpaData")
public class DataUserDAOImpl implements UserDAO {

    private final JPAUserDAO jpaUserDAO;

    @Override
    public List<User> findAll(Pageable page) {
        return jpaUserDAO.findAll(page).getContent();
    }

    @Override
    public Optional<User> findById(Long id) {
        return jpaUserDAO.findById(id);
    }

    @Override
    public User create(User user) {
        return jpaUserDAO.save(user);
    }

    @Override
    public void delete(User user) {
        throw new UnsupportedOperationException("method not allowed");
    }

    @Override
    public Optional<User> findByName(String name) {
        return jpaUserDAO.findByName(name);
    }
}
