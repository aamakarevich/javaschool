package com.tsystems.ecare.app.security;

import com.tsystems.ecare.app.dao.CustomerDao;
import com.tsystems.ecare.app.model.Customer;
import com.tsystems.ecare.app.model.Role;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * UserDetails service that reads the user credentials from the database, using a JPA repository.
 */
@Service
public class SecurityUserDetailsService implements UserDetailsService {

    private static final Logger logger = Logger.getLogger(SecurityUserDetailsService.class);

    @Autowired
    private CustomerDao customerDao;

    /**
     * Retreives details about user.
     *
     * @param username username to find
     * @return details of found user
     * @throws UsernameNotFoundException if user doesn't exist
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer = customerDao.findByEmail(username);
        if (customer == null) {
            String message = "Username not found: " + username;
            logger.info(message);
            throw new UsernameNotFoundException(message);
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        boolean isAdmin = false;
        boolean isManager = false;
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        for (Role role : customer.getRoles()) {
            if ("manager".equals(role.getTitle())) {
                authorities.add(new SimpleGrantedAuthority("ROLE_MANAGER"));
                isManager = true;
                continue;
            }
            if ("admin".equals(role.getTitle())) {
                authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
                isAdmin = true;
                continue;
            }
        }
        logger.info("Found user in database: " + username);
        return new RichUser(username, customer.getPassword(), authorities,
                customer.getFirstName(), customer.getLastName(), isAdmin, isManager);
    }
}
