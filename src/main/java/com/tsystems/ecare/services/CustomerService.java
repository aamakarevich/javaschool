package com.tsystems.ecare.services;

import com.tsystems.ecare.entities.Customer;

/**
 * @author Andrei Makarevich
 */
public interface CustomerService {

    Customer getUser(Integer id);

    Customer saveNewCustomer(Customer customer);

    Customer verifyUser(String email, String password);

    Customer merge(Customer customer);
}
