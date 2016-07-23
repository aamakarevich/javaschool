package com.tsystems.ecare.app.controllers;

import com.tsystems.ecare.app.dto.CustomerDTO;
import com.tsystems.ecare.app.services.CustomerService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.security.Principal;

/**
 * Provides REST-service for retreiving profile information.
 */
@Controller
@RequestMapping("/profile")
public class ProfileController extends AbstractController {

    @Autowired
    private CustomerService customerService;

    public ProfileController() {
        super(Logger.getLogger(ProfileController.class));
    }

    /**
     * Returns profile information.
     *
     * @param principal current user
     * @return DTO with data of current customer
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.PUT)
    public CustomerDTO getProfile(Principal principal) {
        return CustomerDTO.mapFromCustomerEntity(customerService.getCustomerByEmail(principal.getName()));
    }
}
