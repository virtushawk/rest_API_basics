package com.epam.esm.dao;

public interface BaseDAO <T,K> {
    T create(T t);
    T update(T t);
    boolean delete(K k);
}
