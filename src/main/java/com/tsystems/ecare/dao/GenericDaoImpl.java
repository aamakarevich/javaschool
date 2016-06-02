package com.tsystems.ecare.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.List;

/**
 * @author Andrei Makarevich
 */
public abstract class GenericDaoImpl <T, ID extends Serializable> implements GenericDao<T, ID>  {

    @PersistenceContext
    protected EntityManager entityManager;

    public T save(T entity) {
        this.entityManager.persist(entity);
        return entity;
    }

    public T merge(T entity) {
        return this.entityManager.merge(entity);
    }

    public void delete(T entity) {
        this.entityManager.remove(entity);
    }

    public List findAll(Class clazz) {
        List entities;
        Query query = entityManager.createQuery("from " + clazz.getName());
        entities = query.getResultList();
        return entities;
    }

    public T findByID(Class clazz, ID id) {
        T entity;
        entity = (T)this.entityManager.find(clazz, id);
        return entity;
    }
}
