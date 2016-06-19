package com.tsystems.ecare.services;

import com.tsystems.ecare.entities.Customer;

import java.util.List;

/**
 * @author Andrei Makarevich
 */
public interface CustomerService {

    void deleteCustomer(Customer customer);

    Customer getCustomer(Integer id);

    Customer getCustomerByEmail(String email);

    Customer saveNewCustomer(Customer customer);

    Customer verifyUser(String email, String password);

    Customer merge(Customer customer);

    List<Customer> getCustomersPaged(Integer pageNumber, Integer pageSize);

    Long getCustomersCount();

    Customer updateCustomer(Customer customer);
}
