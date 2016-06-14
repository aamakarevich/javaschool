package com.tsystems.ecare.services.impl;

import com.tsystems.ecare.dao.CustomerDao;
import com.tsystems.ecare.dao.impl.CustomerDaoImpl;
import com.tsystems.ecare.entities.Customer;
import com.tsystems.ecare.services.CustomerService;
import com.tsystems.ecare.utils.HashUtil;
import com.tsystems.ecare.utils.PersistenceProvider;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * @author Andrei Makarevich
 */
public class CustomerServiceImpl implements CustomerService {

    private CustomerDao customerDao = new CustomerDaoImpl();

    public Customer getUser(Integer id) {
        Customer customer = customerDao.findById(Customer.class, id);
        return customer;
    }

    public Customer getUserByEmail(String email) {
        Customer customer = customerDao.findByEmail(email);
        return customer;
    }

    public Customer saveNewCustomer(Customer customer) {
        customer.setPassword(HashUtil.getSHA256(customer.getPassword()));
        customer = customerDao.save(customer);
        return customer;
    }

    public Customer verifyUser(String email, String password) {
        Customer customer = customerDao.findByEmailAndPassword(email, password);
        return customer;
    }

    public Customer merge(Customer customer) {
        customer = customerDao.merge(customer);
        return customer;
    }

    public List<Customer> getCustomersPaged(Integer pageNumber, Integer pageSize) {
        return customerDao.findAllPaged(Customer.class, pageNumber, pageSize);
    }

    public Long getCustomersCount() {
        return customerDao.getTotalCount(Customer.class);
    }
}
