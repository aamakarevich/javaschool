package com.tsystems.ecare.app.controllers;

import com.tsystems.ecare.app.dto.CustomerDTO;
import com.tsystems.ecare.app.dto.CustomersDTO;
import com.tsystems.ecare.app.model.Customer;
import com.tsystems.ecare.app.model.SearchResult;
import com.tsystems.ecare.app.services.CustomerService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Provides REST-full CRUD for Customer entity.
 */
@Controller
@RequestMapping("/customer")
public class CustomerController extends AbstractController {

    @Autowired
    private CustomerService customerService;

    public CustomerController() {
        super(Logger.getLogger(CustomerController.class));
    }

    /**
     * Creates new customer.
     *
     * @param customer DTO with data to create new customer
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.POST)
    protected void addCustomer(@RequestBody CustomerDTO customer) {
        customerService.saveCustomer(
                null,
                customer.getLastName(),
                customer.getFirstName(),
                customer.getBirthdate(),
                customer.getPassport(),
                customer.getAddress().getCity(),
                customer.getAddress().getAddress1(),
                customer.getAddress().getAddress2());
    }

    /**
     * Returns single customer data.
     *
     * @param id id of dastomer to retreive data from
     * @return DTO with customer data
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public CustomerDTO getCustomer(@PathVariable Long id) {
        return CustomerDTO.mapFromCustomerEntity(customerService.getCustomer(id));
    }

    /**
     * Returns paginated bench of customers.
     *
     * @param filter query to filter customers by name or phone number
     * @param itemsCount number of items on page
     * @param pageNumber number of page
     * @return DTO with customers data
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET)
    public CustomersDTO searchCustomers(
            @RequestParam(value = "filter", required = false) String filter,
            @RequestParam(value = "itemsCount") Integer itemsCount,
            @RequestParam(value = "pageNumber") Integer pageNumber) {

        SearchResult<Customer> result = customerService.findCustomers(filter, itemsCount, pageNumber);

        Long resultsCount = result.getResultsCount();
        Long totalPages = resultsCount / itemsCount;
        if (resultsCount % itemsCount > 0) {
            totalPages++;
        }

        return new CustomersDTO(pageNumber, totalPages, CustomerDTO.mapFromCustomersEntities(result.getResult()));
    }

    /**
     * Updates data of an existing customer.
     *
     * @param customer DTO with customer data to update
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.PUT)
    public void updateCustomer(@RequestBody CustomerDTO customer) {
        customerService.saveCustomer(
                customer.getId(),
                customer.getLastName(),
                customer.getFirstName(),
                customer.getBirthdate(),
                customer.getPassport(),
                customer.getAddress().getCity(),
                customer.getAddress().getAddress1(),
                customer.getAddress().getAddress2());
    }

    /**
     * Deletes an existing customer.
     *
     * @param id id of customer to delete
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
    }
}
