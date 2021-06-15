package com.epam.esm.dao;

import java.util.List;
import java.util.Optional;

/**
 * The interface Base dao.
 *
 * @param <T> the type parameter
 * @param <K> the type parameter
 */
public interface BaseDAO<T, K> {
    /**
     * Find all list.
     *
     * @return the list
     */
    List<T> findAll();

    /**
     * Find by id optional.
     *
     * @param k the k
     * @return the optional
     */
    Optional<T> findById(K k);

    /**
     * Create t.
     *
     * @param t the t
     * @return the t
     */
    T create(T t);

    /**
     * Delete boolean.
     *
     * @param k the k
     * @return the boolean
     */
    boolean delete(K k);
}
