package com.tsystems.ecare.app.controllers;

import com.tsystems.ecare.app.dto.AllowFeatureDTO;
import com.tsystems.ecare.app.services.PlanService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("allow")
public class AllowedFeatureController extends AbstractController {

    @Autowired
    private PlanService planService;

    public AllowedFeatureController() {
        super(Logger.getLogger(AllowedFeatureController.class));
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.PUT)
    protected void changeAvailableFeature(@RequestBody AllowFeatureDTO afc) {
        planService.changeAvailableFeature(afc.getPlanId(), afc.getFeatureId(), afc.getAvailable());
    }
}
