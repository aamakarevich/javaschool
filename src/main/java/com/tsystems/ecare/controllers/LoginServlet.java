package com.tsystems.ecare.controllers;

import com.google.gson.Gson;
import com.tsystems.ecare.entities.Customer;
import com.tsystems.ecare.services.CustomerService;
import com.tsystems.ecare.services.CustomerServiceImpl;
import com.tsystems.ecare.utils.PersistenceProvider;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Andrei Makarevich
 */
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {

        CustomerService customerService;
        customerService = new CustomerServiceImpl();
        Customer customer = customerService.getUser(1);

        String json = new Gson().toJson(customer);
        response.setContentType("application/json");
        response.getWriter().write(json);
    }
}
