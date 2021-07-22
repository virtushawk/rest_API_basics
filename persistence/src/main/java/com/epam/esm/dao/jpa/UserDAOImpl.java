package com.epam.esm.dao.jpa;

import com.epam.esm.dao.UserDAO;
import com.epam.esm.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
@Profile("jpa")
public class UserDAOImpl implements UserDAO {

    private final EntityManager entityManager;

    private static final String JPA_SELECT_ALL = "SELECT a FROM user a";

    @Override
    public List<User> findAll(Pageable page) {
        return entityManager.createQuery(JPA_SELECT_ALL, User.class)
                .setFirstResult(page.getPageNumber() * page.getPageSize())
                .setMaxResults(page.getPageSize())
                .getResultList();
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(entityManager.find(User.class, id));
    }

    @Override
    public User create(User user) {
        throw new UnsupportedOperationException("method not allowed");
    }

    @Override
    public void delete(User user) {
        throw new UnsupportedOperationException("method not allowed");
    }
}
