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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Provides REST-full CRUD for Customer entity.
 */
@Controller
@RequestMapping("customer")
public class CustomerController extends AbstractController {

    @Autowired
    private CustomerService customerService;

    public CustomerController() {
        super(Logger.getLogger(CustomerController.class));
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public CustomerDTO getCustomer(@PathVariable Long id) {
        return CustomerDTO.mapFromCustomerEntity(customerService.getCustomer(id));
    }

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
}
