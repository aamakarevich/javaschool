package com.tsystems.ecare.app.services.impl;

import com.tsystems.ecare.app.dao.CustomerDao;
import com.tsystems.ecare.app.dao.RoleDao;
import com.tsystems.ecare.app.model.Customer;
import com.tsystems.ecare.app.model.Role;
import com.tsystems.ecare.app.model.SearchResult;
import com.tsystems.ecare.app.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static com.tsystems.ecare.app.services.ValidationUtils.assertMaximumLength;
import static com.tsystems.ecare.app.services.ValidationUtils.assertNotBlank;
import static org.springframework.util.Assert.notNull;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private RoleDao roleDao;

    @Override
    @Transactional
    public void deleteCustomer(Long idTodelete) {
        notNull(idTodelete, "idTodelete is mandatory");
        Customer customer = customerDao.findById(idTodelete);
        notNull(customer, "customer is not found by id");
        customerDao.delete(customer);
    }

    @Override
    @Transactional
    public Customer getCustomer(Long id) {
        notNull(id, "id is mandatory");
        return customerDao.findById(id);
    }

    @Override
    @Transactional
    public Customer getCustomerByEmail(String email) {
        assertNotBlank(email, "email is mandatory and can't be empty string");
        return customerDao.findByEmail(email);
    }

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
        if(active) {
            customer.getRoles().add(role);
        }
        customerDao.save(customer);
    }

    @Override
    @Transactional
    public Customer saveCustomer(Long id, String lastName, String firstName, Date birthdate, String passport, String city, String address1, String address2) {
        notNull(id, "id is mandatory, use another service method to create new customer");
        assertNotBlank(lastName, "lastName must not be blank");
        assertMaximumLength(lastName, 40, "lastName must be not more then 40 characters");
        assertNotBlank(firstName, "firstName must not be blank");
        assertMaximumLength(firstName, 40, "firstName must be not more then 40 characters");
        notNull(birthdate, "birthdate is mandatory");
        assertNotBlank(passport, "passport must not be blank");
        assertMaximumLength(passport, 60, "passport must be not more then 60 characters");
        assertNotBlank(city, "city must not be blank");
        assertMaximumLength(city, 40, "city must be not more then 40 characters");
        assertNotBlank(address1, "address1 must not be blank");
        assertMaximumLength(address1, 100, "address1 must be not more then 100 characters");

        Customer customer = customerDao.findById(id);
        notNull(customer, "customer is not found by id");

        customer.getAddress().setCity(city);
        customer.getAddress().setAddress1(address1);
        customer.getAddress().setAddress2(address2);

        customer.setLastName(lastName);
        customer.setFirstName(firstName);
        customer.setPassport(passport);
        customer.setBirthdate(birthdate);

        return customerDao.save(customer);
    }

    @Override
    @Transactional
    public Customer saveNewCustomer(Customer customer)  {

        customer.setId(null);
        try {
            customer.setPassword(sha256(customer.getLastName()));
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException("unsupported encryption type");
        }
        String lastName = customer.getLastName().toLowerCase();
        lastName = lastName.length() > 7 ? lastName.substring(0, 7) : lastName;
        String uniqueEmail = customer.getFirstName().toLowerCase().substring(0, 1) + lastName;
        int counter = 1;
        while (customerDao.findByEmail(uniqueEmail + "@ecare.com") != null) {
            if(counter != 1) {
                uniqueEmail = uniqueEmail.substring(0, uniqueEmail.length() - Integer.toString(counter-1).length());}
            uniqueEmail += Integer.toString(counter);
            counter++;
        }
        uniqueEmail += "@ecare.com";
        customer.setEmail(uniqueEmail);
        customer = customerDao.save(customer);
        return customer;
    }

    static String sha256(String input) throws NoSuchAlgorithmException {
        MessageDigest mDigest = MessageDigest.getInstance("SHA-256");
        byte[] result = mDigest.digest(input.getBytes());
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < result.length; i++) {
            sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }
}