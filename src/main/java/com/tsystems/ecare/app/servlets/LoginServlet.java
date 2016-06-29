package com.tsystems.ecare.app.servlets;

import com.tsystems.ecare.app.model.Customer;
import com.tsystems.ecare.app.services.impl.CustomerServiceImpl;
import com.tsystems.ecare.app.utils.HashUtil;
import com.tsystems.ecare.app.utils.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Andrei Makarevich
 */
public class LoginServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(true);

        if (request.getParameter("out") != null) {
            try {
                session.removeAttribute("currentuser");
                session.invalidate();
            } catch (IllegalStateException ex) {

            } finally {
                response.setStatus(HttpServletResponse.SC_OK);
                return;
            }
        }

        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals("currentuser")) {
                if(session.getAttribute("currentuser") == null) {
                    String ck = cookie.getValue().replace("%40", "@");
                    session.setAttribute("currentuser", ck.trim().equals("null") ? null : ck);
                }
            }
        }

        if (session.getAttribute("currentuser") != null ) {

            Customer customer = new CustomerServiceImpl().getCustomerByEmail((String) session.getAttribute("currentuser"));
            if (customer == null) {
                JsonUtil.writeObjectToJson(response, null);
                return;
            }
            final Map<String, String> userData = new HashMap<String, String>();
            userData.put("id", customer.getId().toString());
            userData.put("email", customer.getEmail());
            userData.put("username", customer.getFirstName() + " " + customer.getLastName());
            customer.getRoles().forEach(r -> userData.put(r.getTitle(), String.valueOf(true)));
            JsonUtil.writeObjectToJson(response, userData);
            return;
        }

        if(request.getParameter("email") != null && request.getParameter("password") != null) {
            Customer customer = new CustomerServiceImpl().verifyUser(
                    request.getParameter("email"),
                    HashUtil.getSHA256(request.getParameter("password")));
            if (customer != null) {
                request.getSession(true).setAttribute("currentuser", customer.getEmail());
                Map<String, String> userData = new HashMap<String, String>();
                userData.put("id", customer.getId().toString());
                userData.put("email", customer.getEmail());
                userData.put("username", customer.getFirstName() + " " + customer.getLastName());
                JsonUtil.writeObjectToJson(response, userData);
                return;
            }
        }

        JsonUtil.writeObjectToJson(response, null);
        return;
    }
}
