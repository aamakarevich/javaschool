package com.tsystems.ecare.controllers;

import com.google.gson.Gson;
import com.tsystems.ecare.entities.Customer;
import com.tsystems.ecare.services.CustomerService;
import com.tsystems.ecare.services.impl.CustomerServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Andrei Makarevich
 */
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Gson gson = new Gson();

        String method = req.getMethod();

        StringBuilder sb = new StringBuilder();
        String s;
        while ((s = req.getReader().readLine()) != null) {
            sb.append(s);
        }

        Customer customer = (Customer) gson.fromJson(sb.toString(), Customer.class);

        HttpSession session = req.getSession();
        session.setAttribute("currentuser", customer.getEmail());

        resp.setContentType("application/json");
        resp.getWriter().write(sb.toString());
    }

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {

        CustomerService customerService;
        customerService = new CustomerServiceImpl();
        Customer customer = customerService.getUser(1);

//        String json = new Gson().toJson(customer);

        Principal principal = request.getUserPrincipal();

//        Principal principal = request.getUserPrincipal();
//        System.out.println("Your username is: " + principal.getName() + "<BR>");

        Map<String,Object> model = new HashMap<String,Object>();
        model.put("id", UUID.randomUUID().toString());
        model.put("content", "Hello World");
        String json = new Gson().toJson(model);

        response.setContentType("application/json");
        response.getWriter().write(json);
    }
}
