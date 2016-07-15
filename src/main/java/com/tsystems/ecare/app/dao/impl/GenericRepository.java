package com.tsystems.ecare.app.dao.impl;

import com.tsystems.ecare.app.dao.GenericDao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.List;

/**
 * Spring specific GenericDao implementation.
 *
 * @param <T> class of entity
 * @param <ID> class used for entities ID representation
 */
public abstract class GenericRepository<T, ID extends Serializable> implements GenericDao<T, ID> {

    @PersistenceContext
    EntityManager em;

    /**
     * Saves entity.
     *
     * @param entity entity to save
     *
     * @return saved entity
     */
    @Override
    public T save(T entity) {
        return em.merge(entity);
    }

    /**
     * Finds entity by id.
     *
     * @param clazz class that represents entity
     * @param id id of entity to find
     *
     * @return found entity
     */
    @Override
    public T findById(Class clazz, ID id) {
        return (T) em.find(clazz, id);
    }

    /**
     * Finds all entities of some class.
     *
     * @param clazz class that represents entities
     *
     * @return found entities
     */
    @Override
    public List findAll(Class clazz) {
        List entities;
        Query query = em.createQuery("from " + clazz.getName());
        entities = query.getResultList();
        return entities;
    }

    /**
     * Deletes entity by id.
     *
     * @param id id of entity to find
     */
    @Override
    public void delete(T entity) {
        em.remove(entity);
    }

    /**
     * Counts all entities of some class.
     *
     * @param clazz class that represents entities
     *
     * @return entities total amount
     */
    @Override
    public Long getTotalCount(Class clazz) {
        Query query = em.createQuery("select count(id) from " + clazz.getName());
        return (Long) query.getSingleResult();
    }
}
