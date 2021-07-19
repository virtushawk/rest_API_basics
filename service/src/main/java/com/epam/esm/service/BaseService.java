package com.epam.esm.service;

import com.epam.esm.entity.Page;

import java.util.List;

/**
 * The interface Base service.
 *
 * @param <T> the type parameter
 * @param <K> the type parameter
 */
public interface BaseService<T, K> {
    /**
     * Find all.
     *
     * @return the list
     */
    List<T> findAll(Page page);

    /**
     * Find by id.
     *
     * @param k the k
     * @return the t
     */
    T findById(K k);

    /**
     * Create t.
     *
     * @param t the t
     * @return the t
     */
    T create(T t);

    /**
     * Delete.
     *
     * @param k the k
     */
    void delete(K k);
}
