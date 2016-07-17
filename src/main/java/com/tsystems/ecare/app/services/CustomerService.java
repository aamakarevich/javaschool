package com.tsystems.ecare.app.services;

import com.tsystems.ecare.app.model.Customer;
import com.tsystems.ecare.app.model.SearchResult;

import java.util.Date;

/**
 * Service operations interface for Customer entity.
 */
public interface CustomerService {

    /**
     * Saves customer (new or not) to database.
     *
     * @param id id of customer
     * @param lastName last name of customer
     * @param firstName first name of customer
     * @param birthdate date of birth of customer
     * @param passport passwort data of customer
     * @param city city part of customer's address
     * @param address1 first part of customer's address
     * @param address2 second part of customer's address
     *
     * @return entity with saved customer info
     */
    public Customer saveCustomer(Long id, String lastName, String firstName, Date birthdate, String passport, String city, String address1, String address2);

    /**
     * Searches customer in database by id.
     *
     * @param id id of customer
     *
     * @return entity with customer data
     */
    public Customer getCustomer(Long id);

    /**
     * Searches customer in database by email.
     *
     * @param email email of customer
     *
     * @return entity with customer data
     */
    public Customer getCustomerByEmail(String email);

    /**
     * Searches customers by name and phone number.
     *
     * @param filter query to filter customers
     * @param itemsCount number of items on page
     * @param pageNumber number of page
     *
     * @return searched customers data and total count
     */
    public SearchResult<Customer> findCustomers(String filter, Integer itemsCount, Integer pageNumber);

    /**
     * Deletes customer from database.
     *
     * @param idTodelete id of customer to delete
     */
    public void deleteCustomer(Long idTodelete);

    /**
     * Activates or deactivates role for customer.
     *
     * @param roleId id of role
     * @param customerId id of customer
     * @param active true if role must be activated and false otherwise
     */
    public void activateRole(Long roleId, Long customerId, boolean active);
}
