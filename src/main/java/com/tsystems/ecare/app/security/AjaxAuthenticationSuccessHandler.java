package com.tsystems.ecare.app.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Authentication success handler. Detects if its a ajax login request, and
 * if so sends a customized response in the body, otherwise defaults
 * to the existing behaviour for none-ajax login attempts.
 */
public class AjaxAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private AuthenticationSuccessHandler defaultHandler;

    public AjaxAuthenticationSuccessHandler(AuthenticationSuccessHandler defaultHandler) {
        this.defaultHandler = defaultHandler;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        if ("true".equals(request.getHeader("X-Login-Ajax-call"))) {
            RichUser user = (RichUser) authentication.getPrincipal();
            response.addCookie(new Cookie("ecare.username", ((RichUser) authentication.getPrincipal()).getUsername()));
            response.addCookie(new Cookie("ecare.firstname", user.getFirstName()));
            response.addCookie(new Cookie("ecare.lastname", user.getLastName()));
            if (user.isAdmin()) {
                response.addCookie(new Cookie("ecare.admin", "true"));
            }
            if (user.isManager()) {
                response.addCookie(new Cookie("ecare.manager", "true"));
            }
            response.getWriter().print("ok");
            response.getWriter().flush();
        }
        else {
            defaultHandler.onAuthenticationSuccess(request, response, authentication);
        }

    }
}