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

import java.security.Principal;

@Controller
@RequestMapping("profile/lock")
public class ProfileChangeLockController extends AbstractController {

    @Autowired
    private ContractService contractService;

    public ProfileChangeLockController() {
        super(Logger.getLogger(ProfileChangeLockController.class));
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.PUT)
    public void changeLock(Principal principal, @RequestBody ChangeLockDTO changeLock) {
        contractService.changeLock(changeLock.getContractId(), changeLock.isLocked(), principal.getName());
    }
}
