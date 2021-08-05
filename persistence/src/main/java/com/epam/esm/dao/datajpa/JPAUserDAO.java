package com.epam.esm.dao.datajpa;

import com.epam.esm.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * The interface Jpa user dao.
 */
public interface JPAUserDAO extends JpaRepository<User, Long> {
    /**
     * Find by name optional.
     *
     * @param name the name
     * @return the optional
     */
    Optional<User> findByName(String name);
}
