package com.tsystems.ecare.app.dao;

import com.tsystems.ecare.app.model.Customer;

import java.util.List;
import java.util.Set;

/**
 * Data access object interface for Customer entity.
 */
public interface CustomerDao extends GenericDao<Customer, Long> {

    /**
     * Finds customer by id.
     *
     * @param id id of customer to find
     *
     * @return found customer or null if customer was not found
     */
    public Customer findById(Long id);

    /**
     * Finds customer by email.
     *
     * @param email email of customer to find
     *
     * @return found customer or null if customer was not found
     */
    public Customer findByEmail(String email);

    /**
     *  Searches for paginated bench of customers.
     *
     * @param itemsCount items on page count
     * @param pageNumber number of page to retreive
     * @param filteredIds ids to filter customers by
     *
     * @return paginated bench of customers
     */
    public List<Customer> findPaged(Integer itemsCount, Integer pageNumber, Set<Long> filteredIds);

    /**
     * Resolves string filter value. It searches filter value
     * in customers last and first name and in phone numbers.
     *
     * @param filter filtering value
     *
     * @return ids of customers found with filter
     */
    public Set<Long> getFilteredIds(String filter);
}
