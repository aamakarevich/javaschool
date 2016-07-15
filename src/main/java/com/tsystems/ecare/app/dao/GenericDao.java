package com.tsystems.ecare.app.dao;

import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * Generic data access object interface common for all entities.
 */
@Repository
public interface GenericDao<T, ID extends Serializable> {

    /**
     * Saves entity.
     *
     * @param entity entity to save
     *
     * @return saved entity
     */
    T save(T entity);

    /**
     * Finds entity by id.
     *
     * @param clazz class that represents entity
     * @param id id of entity to find
     *
     * @return found entity
     */
    T findById(Class clazz, ID id);

    /**
     * Finds all entities of some class.
     *
     * @param clazz class that represents entities
     *
     * @return found entities
     */
    List findAll(Class clazz);

    /**
     * Deletes entity by id.
     *
     * @param id id of entity to find
     */
    void delete(T entity);

    /**
     * Counts all entities of some class.
     *
     * @param clazz class that represents entities
     *
     * @return entities total amount
     */
    Long getTotalCount(Class clazz);
}
