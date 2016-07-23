package com.tsystems.ecare.app.controllers;

import com.tsystems.ecare.app.dto.ActivateRoleDTO;
import com.tsystems.ecare.app.services.CustomerService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Provides REST-service for activating/deactivating roles for users.
 */
@Controller
@RequestMapping("/role/activate")
public class RoleController extends AbstractController {

    @Autowired
    private CustomerService customerService;

    public RoleController() {
        super(Logger.getLogger(RoleController.class));
    }

    /**
     * Activates/deactivates role for customer.
     *
     * @param activateRole DTO with role activating data
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.PUT)
    protected void activateRole(@RequestBody ActivateRoleDTO activateRole) {
        customerService.activateRole(activateRole.getRoleId(), activateRole.getCustomerId(), activateRole.isActive());
    }
}
