package com.tsystems.ecare.controllers;

import com.google.gson.Gson;
import com.tsystems.ecare.entities.Customer;
import com.tsystems.ecare.services.CustomerService;
import com.tsystems.ecare.services.impl.CustomerServiceImpl;
import com.tsystems.ecare.utils.HashUtil;
import com.tsystems.ecare.utils.JsonUtil;

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

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(true);
        if (session.getAttribute("currentuser") != null) {
            Customer customer = new CustomerServiceImpl().getUserByEmail((String) session.getAttribute("currentuser"));
            JsonUtil.writeObjectToJson(response, customer);
            return;
        }

        if(request.getParameter("email") != null && request.getParameter("password") != null) {
            Customer customer = new CustomerServiceImpl().verifyUser(
                    request.getParameter("email"),
                    HashUtil.getSHA256(request.getParameter("password")));
            if (customer != null) {
                request.getSession(true).setAttribute("currentuser", customer.getEmail());
            }
            JsonUtil.writeObjectToJson(response, customer);
            return;
        }

        JsonUtil.writeObjectToJson(response, null);
        return;
    }
}
