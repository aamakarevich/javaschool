package com.tsystems.ecare.app.servlets;

import com.tsystems.ecare.app.model.Customer;
import com.tsystems.ecare.app.services.CustomerService;
import com.tsystems.ecare.app.utils.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Provides advanced REST-full CRUD for Customer entity.
 *
 * @author Andrei Makarevich
 */
public class CustomerServlet extends HttpServlet {

    /**
     * Create
     * /rest/customer   POST creates new customer
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Customer customer = JsonUtil.getObjectFromJson(req, Customer.class);
        new CustomerService().saveNewCustomer(customer);
        resp.setStatus(HttpServletResponse.SC_CREATED);
    }

    /**
     * Read
     * /rest/customer                   GET returns all customers
     * /rest/customer/ID                GET returns customer with id = ID
     * /rest/customer/?count            GET returns total count of customers
     * /rest/customer/?page=P&size=S    GET returns paginated list of all customers
     *                                      counting page size = P and page number = P
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (req.getParameter("count") != null) {
            JsonUtil.writeObjectToJson(resp, new CustomerService().getCustomersCount());
            return;
        }

        if (req.getParameter("page") != null && req.getParameter("size") != null) {
            List<Customer> customers = new CustomerService().getCustomersPaged(
                    Integer.parseInt(req.getParameter("page")),
                    Integer.parseInt(req.getParameter("size")));
            JsonUtil.writeObjectToJson(resp, customers);
            return;
        }

        if (!req.getPathInfo().equals("/")) {
            Integer idToGet = Integer.parseInt(req.getPathInfo().replace("/", ""));
            Customer customer = new CustomerService().getCustomer(idToGet);
            JsonUtil.writeObjectToJson(resp, customer);
            return;
        }

        JsonUtil.writeObjectToJson(resp, new CustomerService().getAllCustomers());
    }

    /**
     * Update
     * /rest/customer/                      PUT updates customer
     * /rest/customer/?activate=R&for=ID    PUT activates role with id = R for customer with id = ID
     * /rest/customer/?deactivate=R&for=ID  PUT deactivates role with id = R for customer with id = ID
     */
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession(true);
        Customer user = new CustomerService().getCustomerByEmail((String) session.getAttribute("currentuser"));

        if (req.getParameter("activate") != null) {
            Customer target = new CustomerService().getCustomer(Integer.parseInt(req.getParameter("for")));
            new CustomerService().activate(Integer.parseInt(req.getParameter("activate")), target, user);
            resp.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        if (req.getParameter("deactivate") != null) {
            Customer target = new CustomerService().getCustomer(Integer.parseInt(req.getParameter("for")));
            new CustomerService().deactivate(Integer.parseInt(req.getParameter("deactivate")), target, user);
            resp.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        Customer gotCustomer = JsonUtil.getObjectFromJson(req, Customer.class);

        Customer customer = null;// new CustomerService().getCustomer(gotCustomer.getId());
        customer.setFirstName(gotCustomer.getFirstName());
        customer.setLastName(gotCustomer.getLastName());
        customer.setBirthdate(gotCustomer.getBirthdate());
        customer.setPassport(gotCustomer.getPassport());
        customer.setAddress(gotCustomer.getAddress());

        new CustomerService().updateCustomer(customer);

        resp.setStatus(HttpServletResponse.SC_OK);
    }

    /**
     * Delete
     * /rest/customer/ID    DELETE deletes customer with id = ID
     */
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Integer idToDelete = Integer.parseInt(req.getPathInfo().replace("/", ""));
        CustomerService service = new CustomerService();
        Customer customer = service.getCustomer(idToDelete);
        if (customer != null) {
            service.deleteCustomer(customer);
            resp.setStatus(HttpServletResponse.SC_OK);
            return;
        }
        resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
    }
}
