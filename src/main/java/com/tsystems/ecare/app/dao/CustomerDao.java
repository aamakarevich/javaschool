package com.tsystems.ecare.app.dao;

import com.tsystems.ecare.app.model.Customer;

import java.util.List;
import java.util.Set;


public interface CustomerDao extends GenericDao<Customer, Long> {

    Set<Long> getFilteredIds(String filter);

    List<Customer> findPaged(Integer itemsCount, Integer pageNumber, Set<Long> filteredIds);

    Customer findByEmail(String email);

    Customer findByEmailAndPassword(String email, String password);
}
