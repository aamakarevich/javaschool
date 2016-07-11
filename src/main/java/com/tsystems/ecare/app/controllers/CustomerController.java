package com.tsystems.ecare.app.controllers;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("user")
public class CustomerController extends AbstractController {

    public CustomerController() {
        super(Logger.getLogger(CustomerController.class));
    }
}
