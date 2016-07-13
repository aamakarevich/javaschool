package com.tsystems.ecare.app.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Handler to add ajax logout support.
 */
public class AjaxLogoutSuccessHandler implements LogoutSuccessHandler {

    private LogoutSuccessHandler defaultHandler;

    public AjaxLogoutSuccessHandler(LogoutSuccessHandler defaultHandler) {
        this.defaultHandler = defaultHandler;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if ("true".equals(request.getHeader("X-Login-Ajax-call"))) {
            response.getWriter().print("ok");
            response.getWriter().flush();
        }
    }
}
