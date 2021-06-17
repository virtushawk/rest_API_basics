package com.epam.esm.dao;

import java.util.List;
import java.util.Optional;

/**
 * The interface Base dao.
 *
 * @param <T> the type parameter
 * @param <K> the key parameter
 */
public interface BaseDAO<T, K> {
    /**
     * Find all objects
     *
     * @return the list
     */
    List<T> findAll();

    /**
     * Find object by id
     *
     * @param k the key object
     * @return the optional
     */
    Optional<T> findById(K k);

    /**
     * Create object
     *
     * @param t the type object
     * @return the type object
     */
    T create(T t);

    /**
     * Delete object By id
     *
     * @param k the key object
     * @return the boolean
     */
    boolean delete(K k);
}