package com.tsystems.ecare.app.dao.impl;

import com.tsystems.ecare.app.dao.CustomerDao;
import com.tsystems.ecare.app.model.Customer;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;

@Repository
public class CustomerRepository extends GenericRepository<Customer, Long> implements CustomerDao {

    Logger logger = Logger.getLogger(CustomerRepository.class);

    @Override
    public Customer findByEmail(String email) {
        Query query = em.createQuery(
                "from " + Customer.class.getName() + " c where c.email = :email")
                .setParameter("email", email);
        try { /* return customer if have match and null if don't */
            return (Customer) query.getSingleResult();
        } catch (NoResultException ex) {
            logger.info("user is not found by email", ex);
            return null;
        }
    }

    @Override
    public Customer findByEmailAndPassword(String email, String password) {
        Query query = em.createQuery(
                "from " + Customer.class.getName() + " c where c.email = :email and c.password = :password")
                .setParameter("email", email)
                .setParameter("password", password);
        try { /* return customer if have match and null if don't */
            return (Customer) query.getSingleResult();
        } catch (NoResultException ex) {
            logger.info("user is not found by email/password pair", ex);
            return null;
        }
    }
}
