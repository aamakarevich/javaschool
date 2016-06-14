package com.tsystems.ecare.dao;

import com.tsystems.ecare.entities.Customer;

/**
 * @author Andrei Makarevich
 */
public interface CustomerDao extends GenericDao<Customer, Integer> {

    Customer findByEmail(String email);

    Customer findByEmailAndPassword(String email, String password);
}
