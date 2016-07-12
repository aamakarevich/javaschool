package com.tsystems.ecare.app.services.impl;

import com.tsystems.ecare.app.dao.CustomerDao;
import com.tsystems.ecare.app.model.Customer;
import com.tsystems.ecare.app.model.Role;
import com.tsystems.ecare.app.model.SearchResult;
import com.tsystems.ecare.app.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.tsystems.ecare.app.services.ValidationUtils.assertNotBlank;
import static org.springframework.util.Assert.notNull;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerDao customerDao;

    @Override
    @Transactional
    public void deleteCustomer(Customer customer) {
        customerDao.delete(customer);
    }

    @Override
    @Transactional
    public Customer getCustomer(Long id) {
        notNull(id, "id is mandatory");
        return customerDao.findById(Customer.class, id);
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
    public Customer saveNewCustomer(Customer customer) throws NoSuchAlgorithmException {

        customer.setId(null);
        customer.setPassword(sha256(customer.getLastName()));
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

    @Override
    @Transactional
    public Customer verifyUser(String email, String password) {
        return customerDao.findByEmailAndPassword(email, password);
    }

    @Override
    @Transactional
    public List<Customer> getAllCustomers() {
        return customerDao.findAll(Customer.class);
    }

    @Override
    @Transactional
    public List<Customer> getCustomersPaged(Integer pageNumber, Integer pageSize) {
        return customerDao.findAllPaged(Customer.class, pageNumber, pageSize);
    }

    @Override
    @Transactional
    public Long getCustomersCount() {
        return customerDao.getTotalCount(Customer.class);
    }

    @Override
    @Transactional
    public Customer updateCustomer(Customer customer) {
        customer = customerDao.save(customer);
        return customer;
    }

    @Override
    @Transactional
    public void activate(Integer id, Customer target, Customer user) {
        Role admin = new RoleServiceImpl().getRoleByTitle("admin");
        Role role = new RoleServiceImpl().getRoleById(id);
        if (user.getRoles().contains(admin)) {
            target.getRoles().remove(role);
            target.getRoles().add(role);
        }
        customerDao.save(target);
    }

    @Override
    @Transactional
    public void deactivate(Integer id, Customer target, Customer user) {
        Role admin = new RoleServiceImpl().getRoleByTitle("admin");
        Role role = new RoleServiceImpl().getRoleById(id);
        if (user.getRoles().contains(admin)) {
            target.getRoles().remove(role);
        }
        customerDao.save(target);
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
