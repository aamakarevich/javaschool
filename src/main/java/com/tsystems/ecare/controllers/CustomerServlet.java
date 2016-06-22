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
import java.util.List;

/**
 * @author Andrei Makarevich
 */
public class CustomerServlet extends HttpServlet {

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Integer idToDelete = Integer.parseInt(req.getPathInfo().replace("/", ""));
        CustomerService service = new CustomerServiceImpl();
        Customer customer = service.getCustomer(idToDelete);
        if (customer != null) {
            service.deleteCustomer(customer);
            resp.setStatus(HttpServletResponse.SC_OK);
            return;
        }
        resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (req.getParameter("count") != null) {
            JsonUtil.writeObjectToJson(resp, new CustomerServiceImpl().getCustomersCount());
            return;
        }

        if (req.getParameter("page") != null && req.getParameter("size") != null) {
            List<Customer> customers = new CustomerServiceImpl().getCustomersPaged(
                    Integer.parseInt(req.getParameter("page")),
                    Integer.parseInt(req.getParameter("size")));
            JsonUtil.writeObjectToJson(resp,
                    customers);
            return;
        }

        if (!req.getPathInfo().equals("/")) {
            Integer idToGet = Integer.parseInt(req.getPathInfo().replace("/", ""));
            Customer customer = new CustomerServiceImpl().getCustomer(idToGet);
            JsonUtil.writeObjectToJson(resp, customer);
            return;
        }

//        JsonUtil.writeObjectToJson(resp, new CustomerServiceImpl().getAllCustomers());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Customer customer = JsonUtil.getObjectFromJson(req, Customer.class);
        new CustomerServiceImpl().saveNewCustomer(customer);
        resp.setStatus(HttpServletResponse.SC_CREATED);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Customer gotCustomer = JsonUtil.getObjectFromJson(req, Customer.class);

        Customer customer = new CustomerServiceImpl().getCustomer(gotCustomer.getId());
        customer.setFirstName(gotCustomer.getFirstName());
        customer.setLastName(gotCustomer.getLastName());
        customer.setBirthdate(gotCustomer.getBirthdate());
        customer.setPassport(gotCustomer.getPassport());
        customer.setAddress(gotCustomer.getAddress());

        new CustomerServiceImpl().updateCustomer(customer);

        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
