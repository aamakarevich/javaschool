package com.tsystems.ecare.controllers;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * @author Andrei Makarevich
 */
public class AuthenticationFilter implements Filter {

    private ArrayList<String> allowedUrls;

    public void init(FilterConfig filterConfig) throws ServletException {
        String urls = filterConfig.getInitParameter("allowed-urls");
        StringTokenizer tokenizer = new StringTokenizer(urls, ",");
        allowedUrls = new ArrayList<String>();
        while (tokenizer.hasMoreTokens()) {
            allowedUrls.add(tokenizer.nextToken());
        }
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String url = req.getServletPath();
        if (!allowedUrls.contains(url)) {
            HttpSession session = req.getSession(false);
            if (null == session) {
                res.sendRedirect("login.html");
            }
        }

        chain.doFilter(request, response);
    }

    public void destroy() {

    }
}
