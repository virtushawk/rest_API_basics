package com.epam.esm.service;

import java.util.List;

public interface BaseService<T, K> {
    List<T> findAll();

    T findById(K k);

    T create(T t);

    boolean delete(K k);
}
