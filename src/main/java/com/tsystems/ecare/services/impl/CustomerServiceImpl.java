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
        customerDao.beginTransaction();
        customerDao.delete(customer);
        customerDao.commitTransaction();
    }

    public Customer getCustomer(Integer id) {
        return customerDao.findById(Customer.class, id);
    }

    public Customer getCustomerByEmail(String email) {
        return customerDao.findByEmail(email);
    }

    public Customer saveNewCustomer(Customer customer) {
        customerDao.beginTransaction();

        customer.setId(null);
        customer.setPassword(HashUtil.getSHA256(customer.getLastName()));
        String lastName = customer.getLastName().toLowerCase();
        lastName = lastName.length() > 7 ? lastName.substring(0, 7) : lastName;
        String uniqueEmail = customer.getFirstName().toLowerCase().substring(0, 1) + lastName;
        int counter = 1;
        while (customerDao.findByEmail(uniqueEmail + "@ecare.com") != null) {
            if(counter != 1) uniqueEmail = uniqueEmail.substring(0, uniqueEmail.length() - 1);
            uniqueEmail += counter;
            counter++;
        }
        uniqueEmail += "@ecare.com";
        customer.setEmail(uniqueEmail);
        customer = customerDao.save(customer);
        customerDao.commitTransaction();
        return customer;
    }

    public Customer verifyUser(String email, String password) {
        return customerDao.findByEmailAndPassword(email, password);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerDao.findAll(Customer.class);
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
