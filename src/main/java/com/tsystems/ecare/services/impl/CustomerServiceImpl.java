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

    @Override
    public void deleteCustomer(Customer customer) {
        customerDao.delete(customer);
    }

    public Customer getCustomer(Integer id) {
        return customerDao.findById(Customer.class, id);
    }

    public Customer getCustomerByEmail(String email) {
        return customerDao.findByEmail(email);
    }

    public Customer saveNewCustomer(Customer customer) {
        customerDao.beginTransaction();
        customer.setPassword(HashUtil.getSHA256(customer.getPassword()));
        customer = customerDao.save(customer);
        customerDao.commitTransaction();
        return customer;
    }

    public Customer verifyUser(String email, String password) {
        return customerDao.findByEmailAndPassword(email, password);
    }

    public List<Customer> getCustomersPaged(Integer pageNumber, Integer pageSize) {
        return customerDao.findAllPaged(Customer.class, pageNumber, pageSize);
    }

    public Long getCustomersCount() {
        return customerDao.getTotalCount(Customer.class);
    }

    @Override
    public Customer updateCustomer(Customer customer) {
        customerDao.beginTransaction();
        customer = customerDao.merge(customer);
        customerDao.commitTransaction();
        return customer;
    }
}
