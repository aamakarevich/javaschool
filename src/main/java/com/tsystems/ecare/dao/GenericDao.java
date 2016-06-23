package com.tsystems.ecare.dao;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;

/**
 * @author Andrei Makarevich
 */
public interface GenericDao<T, ID extends Serializable> {

    T save(T entity);

    T merge(T entity);

    void delete(T entity);

    List findAll(Class clazz);

    List findAllPaged(Class clazz, Integer pageNumber, Integer pageSize);

    T findById(Class clazz, ID id);

    Long getTotalCount(Class clazz);

    /* API for transaction control from service level */
    void beginTransaction();
    void commitTransaction();
    void rollbackTransaction();
}
