package com.tsystems.ecare.app.dao;

import com.tsystems.ecare.app.model.Customer;

import java.util.List;
import java.util.Set;

/**
 * Data access object interface for Customer entity.
 */
public interface CustomerDao extends GenericDao<Customer, Long> {

    public Set<Long> getFilteredIds(String filter);

    public List<Customer> findPaged(Integer itemsCount, Integer pageNumber, Set<Long> filteredIds);

    public Customer findById(Long id);

    public Customer findByEmail(String email);

    public Customer findByEmailAndPassword(String email, String password);
}
