package com.tsystems.ecare.app.dao;

import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * Generic data access object interface common for all entities.
 */
@Repository
public interface GenericDao<T, ID extends Serializable> {

    T save(T entity);

    void delete(T entity);

    List findAll(Class clazz);

    T findById(Class clazz, ID id);

    Long getTotalCount(Class clazz);
}
