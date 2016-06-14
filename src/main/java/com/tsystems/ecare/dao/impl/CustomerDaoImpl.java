package com.tsystems.ecare.dao.impl;

import com.tsystems.ecare.dao.CustomerDao;
import com.tsystems.ecare.entities.Customer;

import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 * @author Andrei Makarevich
 */
public class CustomerDaoImpl extends GenericDaoImpl<Customer, Integer> implements CustomerDao {

    public Customer findByEmail(String email) {
        Query query = entityManager.createQuery(
                "from " + Customer.class.getName() + " c where c.email = :email")
                .setParameter("email", email);
        try { /* return customer if have match and null if don't */
            return (Customer) query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    public Customer findByEmailAndPassword(String email, String password) {
        Query query = entityManager.createQuery(
                "from " + Customer.class.getName() + " c where c.email = :email and c.password = :password")
                .setParameter("email", email)
                .setParameter("password", password);
        try { /* return customer if have match and null if don't */
            return (Customer) query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }
}
