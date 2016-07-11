package com.tsystems.ecare.app.services;

import com.tsystems.ecare.app.model.Customer;

import java.util.List;

public interface CustomerService {

    public void deleteCustomer(Customer customer);

    public Customer getCustomer(Long id);

    public Customer getCustomerByEmail(String email);

    public Customer saveNewCustomer(Customer customer);

    public Customer verifyUser(String email, String password);

    public List<Customer> getAllCustomers();

    public List<Customer> getCustomersPaged(Integer pageNumber, Integer pageSize);

    public Long getCustomersCount();

    public Customer updateCustomer(Customer customer);

    public void activate(Integer id, Customer target, Customer user);

    public void deactivate(Integer id, Customer target, Customer user);
}
