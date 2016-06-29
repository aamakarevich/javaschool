package com.tsystems.ecare.app.dao;

import com.tsystems.ecare.app.model.Customer;

/**
 * @author Andrei Makarevich
 */
public interface CustomerDao extends GenericDao<Customer, Integer> {

    Customer findByEmail(String email);

    Customer findByEmailAndPassword(String email, String password);
}
