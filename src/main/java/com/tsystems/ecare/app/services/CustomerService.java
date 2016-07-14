package com.tsystems.ecare.app.services;

import com.tsystems.ecare.app.model.Customer;
import com.tsystems.ecare.app.model.SearchResult;

import java.util.Date;

/**
 * Service operations interface for Customer entity.
 */
public interface CustomerService {

    public void deleteCustomer(Long idTodelete);

    public Customer getCustomer(Long id);

    public Customer getCustomerByEmail(String email);

    public SearchResult<Customer> findCustomers(String filter, Integer itemsCount, Integer pageNumber);

    public void activateRole(Long roleId, Long customerId, boolean active);

    public Customer saveCustomer(Long id, String lastName, String firstName, Date birthdate, String passport, String city, String address1, String address2);
}
