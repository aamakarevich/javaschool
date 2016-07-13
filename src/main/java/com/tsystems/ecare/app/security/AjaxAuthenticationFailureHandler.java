package com.tsystems.ecare.app.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Authentication failure handler. Returns "UNAUTHORIZED"
 * to ajax authentication requests if authentication failed.
 */
public class AjaxAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private AuthenticationFailureHandler defaultHandler;

    public AjaxAuthenticationFailureHandler(AuthenticationFailureHandler defaultHandler) {
        this.defaultHandler = defaultHandler;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        if ("true".equals(request.getHeader("X-Login-Ajax-call"))) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().print("failure");
            response.getWriter().flush();
        }
        else {
            defaultHandler.onAuthenticationFailure(request, response, exception);
        }
    }
}
