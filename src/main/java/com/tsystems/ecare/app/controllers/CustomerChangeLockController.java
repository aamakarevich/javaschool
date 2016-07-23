package com.tsystems.ecare.app.controllers;

import com.tsystems.ecare.app.dto.ChangeLockDTO;
import com.tsystems.ecare.app.services.ContractService;
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
 * Provides REST-service for locking/unlocking customers contracts by managers.
 */
@Controller
@RequestMapping("/customer/lock")
public class CustomerChangeLockController extends AbstractController {

    @Autowired
    private ContractService contractService;

    public CustomerChangeLockController() {
        super(Logger.getLogger(CustomerChangeLockController.class));
    }

    /**
     * Locks/unlocks customers contracts (entry point for managers).
     *
     * @param changeLock DTO with numberlock change data
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.PUT)
    public void changeLock(@RequestBody ChangeLockDTO changeLock) {
        contractService.changeLock(changeLock.getContractId(), changeLock.isLocked(), null);
    }
}
