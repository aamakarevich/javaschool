package com.tsystems.ecare.app.dao.impl;

import com.tsystems.ecare.app.dao.GenericDao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.List;

public abstract class GenericDaoImpl <T, ID extends Serializable> implements GenericDao<T, ID> {

    @PersistenceContext
    EntityManager em;

    public T save(T entity) {
        em.persist(entity);
        return entity;
    }

    public T merge(T entity) {
        entity = this.em.merge(entity);
        return entity;
    }

    public void delete(T entity) {
        this.em.remove(entity);
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
        T entity;
        entity = (T)this.em.find(clazz, id);
        return entity;
    }

    public Long getTotalCount(Class clazz) {
        Query query = em.createQuery("select count(id) from " + clazz.getName());
        return (Long) query.getSingleResult();
    }

    @Override
    public void beginTransaction() {
        em.getTransaction().begin();
    }

    @Override
    public void commitTransaction() {
        em.getTransaction().commit();
    }

    @Override
    public void rollbackTransaction() {
        em.getTransaction().rollback();
    }
}
