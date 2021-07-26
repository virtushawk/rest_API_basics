package com.epam.esm.dao.jpa;

import com.epam.esm.dao.UserDAO;
import com.epam.esm.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
@Profile("jpa")
public class UserDAOImpl implements UserDAO {

    private final EntityManager entityManager;

    private static final String JPA_SELECT_ALL = "SELECT a FROM user a";
    private static final String JPA_FIND_BY_NAME = "SELECT a FROM user a WHERE a.name = :name";
    private static final String ATTRIBUTE_NAME = "name";

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

    @Override
    public Optional<User> findByName(String name) {
        List<User> users = entityManager.createQuery(JPA_FIND_BY_NAME, User.class)
                .setParameter(ATTRIBUTE_NAME, name)
                .getResultList();
        if (ObjectUtils.isEmpty(users)) {
            return Optional.empty();
        }
        return Optional.of(users.get(0));
    }
}
