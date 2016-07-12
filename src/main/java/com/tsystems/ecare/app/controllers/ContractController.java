package com.tsystems.ecare.app.controllers;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Provides REST-full CRUD for Contract entity.
 */
@Controller
@RequestMapping("contract")
public class ContractController extends AbstractController {

    public ContractController() {
        super(Logger.getLogger(ContractController.class));
    }


}
