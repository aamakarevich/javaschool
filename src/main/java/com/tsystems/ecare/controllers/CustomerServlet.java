package com.tsystems.ecare.controllers;

import com.tsystems.ecare.entities.Customer;
import com.tsystems.ecare.services.CustomerService;
import com.tsystems.ecare.services.impl.CustomerServiceImpl;
import com.tsystems.ecare.utils.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Andrei Makarevich
 */
public class CustomerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (req.getParameter("count") != null) {
            JsonUtil.writeObjectToJson(resp, new CustomerServiceImpl().getCustomersCount());
            return;
        }

        if(req.getParameter("page") != null && req.getParameter("size") != null) {
            JsonUtil.writeObjectToJson(resp,
                    new CustomerServiceImpl().getCustomersPaged(
                            Integer.parseInt(req.getParameter("page")),
                            Integer.parseInt(req.getParameter("size"))));
            return;
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Customer customer = JsonUtil.getObjectFromJson(req, Customer.class);

        CustomerService customerService = new CustomerServiceImpl();
        customerService.saveNewCustomer(customer);

        resp.getWriter().write("ok");
    }
}
