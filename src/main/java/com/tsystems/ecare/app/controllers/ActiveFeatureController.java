package com.tsystems.ecare.app.controllers;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("activate")
public class ActiveFeatureController extends AbstractController {

    public ActiveFeatureController() {
        super(Logger.getLogger(ActiveFeatureController.class));
    }
}
