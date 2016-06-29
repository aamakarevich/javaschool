package com.tsystems.ecare.app.dao.impl;

import com.tsystems.ecare.app.dao.GenericDao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.List;

public abstract class GenericRepository<T, ID extends Serializable> implements GenericDao<T, ID> {

    @PersistenceContext
    EntityManager em;

    public T save(T entity) {
        return em.merge(entity);
    }

    public void delete(T entity) {
        em.remove(entity);
    }

    public List findAll(Class clazz) {
        List entities;
        Query query = em.createQuery("from " + clazz.getName());
        entities = query.getResultList();
        return entities;
    }

    public List findAllPaged(Class clazz, Integer pageNumber, Integer pageSize) {
        List entities;
        Query query = em.createQuery("from " + clazz.getName());
        query.setFirstResult((pageNumber - 1) * pageSize);
        query.setMaxResults(pageSize);
        entities = query.getResultList();
        return entities;
    }

    public T findById(Class clazz, ID id) {
        return (T) em.find(clazz, id);
    }

    public Long getTotalCount(Class clazz) {
        Query query = em.createQuery("select count(id) from " + clazz.getName());
        return (Long) query.getSingleResult();
    }
}
