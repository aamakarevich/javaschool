package com.tsystems.ecare.dao.impl;

import com.tsystems.ecare.dao.GenericDao;
import com.tsystems.ecare.utils.PersistenceProvider;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.List;

/**
 * @author Andrei Makarevich
 */
public abstract class GenericDaoImpl <T, ID extends Serializable> implements GenericDao<T, ID> {

    protected EntityManager entityManager = PersistenceProvider.getEntityManager();

    public T save(T entity) {
        entityManager.getTransaction().begin();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();
        return entity;
    }

    public T merge(T entity) {
        entityManager.getTransaction().begin();
        entity = this.entityManager.merge(entity);
        entityManager.getTransaction().commit();
        return entity;
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

    public List findAllPaged(Class clazz, Integer pageNumber, Integer pageSize) {
        List entities;
        Query query = entityManager.createQuery("from " + clazz.getName());
        query.setFirstResult((pageNumber - 1) * pageSize);
        query.setMaxResults(pageSize);
        entities = query.getResultList();
        return entities;
    }

    public T findById(Class clazz, ID id) {
        T entity;
        entity = (T)this.entityManager.find(clazz, id);
        return entity;
    }

    public Long getTotalCount(Class clazz) {
        Query query = entityManager.createQuery("select count(id) from " + clazz.getName());
        return (Long) query.getSingleResult();
    }
}
