package com.tsystems.ecare.services;

import com.tsystems.ecare.dao.CustomerDao;
import com.tsystems.ecare.dao.CustomerDaoImpl;
import com.tsystems.ecare.entities.Customer;
import com.tsystems.ecare.utils.HashUtil;
import com.tsystems.ecare.utils.PersistenceProvider;

import javax.persistence.EntityManager;

/**
 * @author Andrei Makarevich
 */
public class CustomerServiceImpl implements CustomerService {

    CustomerDao customerDao = new CustomerDaoImpl();

    public Customer getUser(Integer id) {
        EntityManager em = PersistenceProvider.getEntityManager();
        Customer customer = customerDao.findById(Customer.class, id);
        return customer;
    }

    public Customer saveNewCustomer(Customer customer) {

        EntityManager em = PersistenceProvider.getEntityManager();
        customer.setPassword(HashUtil.getSHA256(customer.getPassword()));
        customer = customerDao.save(customer);
        return customer;
    }

    public Customer verifyUser(String email, String password) {
        EntityManager em = PersistenceProvider.getEntityManager();
        Customer customer = customerDao.findByEmailAndPassword(email, password);
        return customer;
    }

    public Customer merge(Customer customer) {
        EntityManager em = PersistenceProvider.getEntityManager();
        customer = customerDao.merge(customer);
        return customer;
    }
}
