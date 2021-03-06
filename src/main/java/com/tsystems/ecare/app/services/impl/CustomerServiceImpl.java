package com.tsystems.ecare.app.services.impl;

import com.tsystems.ecare.app.dao.CustomerDao;
import com.tsystems.ecare.app.dao.RoleDao;
import com.tsystems.ecare.app.model.Address;
import com.tsystems.ecare.app.model.Customer;
import com.tsystems.ecare.app.model.Role;
import com.tsystems.ecare.app.model.SearchResult;
import com.tsystems.ecare.app.services.CustomerService;
import com.tsystems.ecare.app.utils.HashUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Set;

import static com.tsystems.ecare.app.utils.ValidationUtils.assertMaximumLength;
import static com.tsystems.ecare.app.utils.ValidationUtils.assertNotBlank;
import static org.springframework.util.Assert.notNull;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private RoleDao roleDao;

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
    @Override
    @Transactional
    public Customer saveCustomer(Long id, String lastName, String firstName, Date birthdate, String passport, String city, String address1, String address2) {
        Customer customer;
        assertNotBlank(lastName, "lastName must not be blank");
        assertMaximumLength(lastName, 40, "lastName must be not more then 40 characters");
        assertNotBlank(firstName, "firstName must not be blank");
        assertMaximumLength(firstName, 40, "firstName must be not more then 40 characters");
        if (id != null) {
            customer = customerDao.findById(id);
            notNull(customer, "customer is not found by id");
        } else {
            customer = new Customer();
            customer.setPassword(HashUtils.sha256(HashUtils.generatePassword()));
            String uniqueEmail = firstName.toLowerCase().substring(0, 1) + (lastName.length() > 7 ? lastName.substring(0, 6) : lastName).toLowerCase();
            int counter = 1;
            while (customerDao.findByEmail(uniqueEmail + "@ecare.com") != null) {
                if (counter != 1) {
                    uniqueEmail = uniqueEmail.substring(0, uniqueEmail.length() - Integer.toString(counter - 1).length());
                }
                uniqueEmail += Integer.toString(counter);
                counter++;
            }
            uniqueEmail += "@ecare.com";
            customer.setEmail(uniqueEmail);
        }
        notNull(birthdate, "birthdate is mandatory");
        assertNotBlank(passport, "passport must not be blank");
        assertMaximumLength(passport, 60, "passport must be not more then 60 characters");
        assertNotBlank(city, "city must not be blank");
        assertMaximumLength(city, 40, "city must be not more then 40 characters");
        assertNotBlank(address1, "address1 must not be blank");
        assertMaximumLength(address1, 100, "address1 must be not more then 100 characters");
        if (address2 != null) {
            assertMaximumLength(address2, 100, "address2 must be not more then 100 characters");
        }

        customer.setAddress(new Address());
        customer.getAddress().setCity(city);
        customer.getAddress().setAddress1(address1);
        customer.getAddress().setAddress2(address2);

        customer.setLastName(lastName);
        customer.setFirstName(firstName);
        customer.setPassport(passport);
        customer.setBirthdate(birthdate);

        return customerDao.save(customer);
    }

    /**
     * Searches customer in database by id.
     *
     * @param id id of customer
     *
     * @return entity with customer data
     */
    @Override
    @Transactional
    public Customer getCustomer(Long id) {
        notNull(id, "id is mandatory");
        Customer customer = customerDao.findById(id);
        notNull(customer, "customer is not found by id");
        return customer;
    }

    /**
     * Searches customer in database by email.
     *
     * @param email email of customer
     *
     * @return entity with customer data
     */
    @Override
    @Transactional
    public Customer getCustomerByEmail(String email) {
        assertNotBlank(email, "email is mandatory and can't be empty string");
        Customer customer = customerDao.findByEmail(email);
        notNull(customer, "customer is not found by email");
        return customer;
    }

    /**
     * Searches customers by name and phone number.
     *
     * @param filter query to filter customers
     * @param itemsCount number of items on page
     * @param pageNumber number of page
     *
     * @return searched customers data and total count
     */
    @Override
    @Transactional(readOnly = true)
    public SearchResult<Customer> findCustomers(String filter, Integer itemsCount, Integer pageNumber) {
        if (itemsCount == null || pageNumber == null) {
            throw new IllegalArgumentException("both itemsCount and pageNumber are needed");
        }

        Set<Long> filteredIds;
        Long total;
        if (filter == null) {
            filteredIds = null;
            total = customerDao.getTotalCount(Customer.class);
        } else {
            filteredIds = customerDao.getFilteredIds(filter);
            total = (long) filteredIds.size();
        }

        List<Customer> customers = customerDao.findPaged(itemsCount, pageNumber, filteredIds);

        return new SearchResult<>(total, customers);
    }

    /**
     * Deletes customer from database.
     *
     * @param idTodelete id of customer to delete
     */
    @Override
    @Transactional
    public void deleteCustomer(Long idTodelete) {
        notNull(idTodelete, "idTodelete is mandatory");
        Customer customer = customerDao.findById(idTodelete);
        notNull(customer, "customer is not found by id");
        customerDao.delete(customer);
    }

    /**
     * Activates or deactivates role for customer.
     *
     * @param roleId id of role
     * @param customerId id of customer
     * @param active true if role must be activated and false otherwise
     */
    @Override
    @Transactional
    public void activateRole(Long roleId, Long customerId, boolean active) {
        notNull(roleId, "roleId is mandatory");
        notNull(customerId, "customerId is mandatory");

        Role role = roleDao.findById(Role.class, roleId);
        notNull(role, "role is not found by id");

        Customer customer = customerDao.findById(customerId);
        notNull(customer, "customer is not found by id");

        customer.getRoles().remove(role);
        if (active) {
            customer.getRoles().add(role);
        }
        customerDao.save(customer);
    }
}
