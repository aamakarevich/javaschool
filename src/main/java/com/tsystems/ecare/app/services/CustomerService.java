package com.tsystems.ecare.app.services;

import com.tsystems.ecare.app.dao.impl.CustomerRepository;
import com.tsystems.ecare.app.model.Customer;
import com.tsystems.ecare.app.model.Role;
import com.tsystems.ecare.app.utils.HashUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository repository;
    
    public void deleteCustomer(Customer customer) {
        repository.delete(customer);
    }

    public Customer getCustomer(Integer id) {
        return repository.findById(Customer.class, id);
    }

    public Customer getCustomerByEmail(String email) {
        return repository.findByEmail(email);
    }

    public Customer saveNewCustomer(Customer customer) {

        customer.setId(null);
        customer.setPassword(HashUtil.getSHA256(customer.getLastName()));
        String lastName = customer.getLastName().toLowerCase();
        lastName = lastName.length() > 7 ? lastName.substring(0, 7) : lastName;
        String uniqueEmail = customer.getFirstName().toLowerCase().substring(0, 1) + lastName;
        int counter = 1;
        while (repository.findByEmail(uniqueEmail + "@ecare.com") != null) {
            if(counter != 1) uniqueEmail = uniqueEmail.substring(0, uniqueEmail.length() - 1);
            uniqueEmail += counter;
            counter++;
        }
        uniqueEmail += "@ecare.com";
        customer.setEmail(uniqueEmail);
        customer = repository.save(customer);
        return customer;
    }

    public Customer verifyUser(String email, String password) {
        return repository.findByEmailAndPassword(email, password);
    }
    
    public List<Customer> getAllCustomers() {
        return repository.findAll(Customer.class);
    }

    public List<Customer> getCustomersPaged(Integer pageNumber, Integer pageSize) {
        return repository.findAllPaged(Customer.class, pageNumber, pageSize);
    }

    public Long getCustomersCount() {
        return repository.getTotalCount(Customer.class);
    }
    
    public Customer updateCustomer(Customer customer) {
        customer = repository.save(customer);
        return customer;
    }

    
    public void activate(Integer id, Customer target, Customer user) {
        Role admin = new RoleService().getRoleByTitle("admin");
        Role role = new RoleService().getRoleById(id);
        if (user.getRoles().contains(admin)) {
            target.getRoles().remove(role);
            target.getRoles().add(role);
        }
        repository.save(target);
    }
    
    public void deactivate(Integer id, Customer target, Customer user) {
        Role admin = new RoleService().getRoleByTitle("admin");
        Role role = new RoleService().getRoleById(id);
        if (user.getRoles().contains(admin)) {
            target.getRoles().remove(role);
        }
        repository.save(target);
    }
}
