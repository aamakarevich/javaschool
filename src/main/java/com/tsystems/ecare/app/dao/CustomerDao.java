package com.tsystems.ecare.app.dao;

import com.tsystems.ecare.app.model.Customer;


public interface CustomerDao extends GenericDao<Customer, Long> {

    Customer findByEmail(String email);

    Customer findByEmailAndPassword(String email, String password);
}
